package sdt.java3d.BTGgroup;

import sdt.java3d.shape3d.*;
import java.awt.*;
import sdt.geometry.*;
import javax.vecmath.*;
import sdt.java3d.*;
import sdt.stepb.stepb_cartesian_point_array;
import sdt.stepb.stepb_cartesian_point;
import sdt.stepb.math_vector3d;


public class SDT_Object extends SDT_TransformGroup
{
    public double max[]= {0.0,0.0,0.0};
    public double min[]= {0.0,0.0,0.0};
    public SDT_Object()
    {
        super();
        //maxMin = {0.0,0.0,0.0,0.0,0.0,0.0};
    }

    public SDT_Shape AddObjEdgeCurve(SDT_3DEdge edgeData, Color c)
    {
        double[][] ecPts = edgeData.GetCrvs();
        int pnumber = ecPts.length;
        if (pnumber <= 1)
           return null;

        double[][] vertexs = edgeData.GetPts();
        for (int i = 0; i < vertexs.length; i++)
        {
           // Point3d pt3d = new Point3d(vertexs[i]);
         //   spring_point ptToShow = new spring_point(pt3d, Color.yellow);
          //  spring_shape3d vertexShape = new spring_shape3d(ptToShow);
          //  this.addChild(vertexShape);
            if (max[0] < vertexs[i][0])
                max[0] = vertexs[i][0];
            if (max[1] < vertexs[i][1])
                max[1] = vertexs[i][1];
            if (max[2] < vertexs[i][2])
                max[2] = vertexs[i][2];

            if (min[0] > vertexs[i][0])
                min[0] = vertexs[i][0];
            if (min[1] > vertexs[i][1])
                min[1] = vertexs[i][1];
            if (min[2] > vertexs[i][2])
                min[2] = vertexs[i][2];

        }

        SDT_ShapeCurve curveToShow = new SDT_ShapeCurve(pnumber, c);
        Point3d[] pts = new Point3d[pnumber];
        for (int j = 0; j < pnumber; j++)
        {
            pts[j] = new Point3d(ecPts[j][0], ecPts[j][1], ecPts[j][2]);
        }
        curveToShow.setCoordinates(0, pts);

        SDT_Shape edgeShape = new SDT_Shape(curveToShow);
        this.addChild(edgeShape);
        return edgeShape;
    }
    public SDT_Shape AddObjMesh(SDT_3DMesh edgeMesh, Color c)
    {
        stepb_cartesian_point pt1 = edgeMesh.GetPoint1();
        stepb_cartesian_point pt2 = edgeMesh.GetPoint2();
        stepb_cartesian_point pt3 = edgeMesh.GetPoint3();
        stepb_cartesian_point pt4 = edgeMesh.GetPoint4();
        SDT_ShapeFace faceToShow = null;

        if (pt4 != null)
        {
            faceToShow = new SDT_ShapeFace(2 * 3, c);
        }
        else
        {
            faceToShow = new SDT_ShapeFace(3, c);
        }

        Point3d[] pts = null;
        if (pt4 != null)
        {
            pts = new Point3d[2 * 3];
        }
        else
        {
            pts = new Point3d[3];
        }
        pts[0] = new Point3d(pt1.X(), pt1.Y(), pt1.Z());
        pts[1] = new Point3d(pt2.X(), pt2.Y(), pt2.Z());
        pts[2] = new Point3d(pt3.X(), pt3.Y(), pt3.Z());

        if (pt4 != null)
        {
            pts[3] = new Point3d(pt1.X(), pt1.Y(), pt1.Z());
            pts[4] = new Point3d(pt3.X(), pt3.Y(), pt3.Z());
            pts[5] = new Point3d(pt4.X(), pt4.Y(), pt4.Z());
        }
        for (int i = 0; i < pts.length; i++)
       {
           if (max[0] < pts[i].x)
               max[0] = pts[i].x;
           if (max[1] < pts[i].y)
               max[1] = pts[i].y;
           if (max[2] < pts[i].z)
               max[2] = pts[i].z;

           if (min[0] > pts[i].x)
               min[0] = pts[i].x;
           if (min[1] > pts[i].y)
               min[1] = pts[i].y;
           if (min[2] > pts[i].z)
               min[2] = pts[i].z;

       }




        math_vector3d normal1 = edgeMesh.GetNormal1();
        math_vector3d normal2 = edgeMesh.GetNormal2();
        math_vector3d normal3 = edgeMesh.GetNormal3();
        math_vector3d normal4 = null;
        if (pt4 != null)
        {
            normal4 = edgeMesh.GetNormal4();
        }

        Vector3f n1 = new Vector3f((float) normal1.X(), (float) normal1.Y(), (float) normal1.Z());
        Vector3f n2 = new Vector3f((float) normal2.X(), (float) normal2.Y(), (float) normal2.Z());
        Vector3f n3 = new Vector3f((float) normal3.X(), (float) normal3.Y(), (float) normal3.Z());
        Vector3f n4 = null;
        if (pt4 != null)
        {
            n4 = new Vector3f((float) normal4.X(), (float) normal4.Y(), (float) normal4.Z());
        }

         faceToShow.setCoordinates(0, pts);

        faceToShow.setNormal(0,n1);
        faceToShow.setNormal(1,n2);
        faceToShow.setNormal(2,n3);
        if(pt4 != null)
        {
            faceToShow.setNormal(3, n1);
            faceToShow.setNormal(4, n3);
            faceToShow.setNormal(5, n4);
        }
        faceToShow.SetColor(c);



        SDT_Shape meshShape = new SDT_Shape(faceToShow);
        this.addChild(meshShape);
        return meshShape;

    }

}
