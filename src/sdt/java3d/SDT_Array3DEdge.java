package sdt.java3d;

import sdt.stepb.*;
import java.util.Vector;
import java.awt.*;
import sdt.geometry.*;
import java.util.ArrayList;

public class SDT_Array3DEdge
{
      protected Vector vector;

    public SDT_Array3DEdge(stepb_manifold_solid_brep smodel)
    {
        vector = new Vector();
        stepb_edge_curve_array ecArray = smodel.getEcArray();
        /*for(int i = 0; i < ecArray.size(); i++)
                 {
            stepb_edge_curve ec = ecArray.get(i);
            SDT_3DEdge ee = new SDT_3DEdge(ec);
            this.add(ee);
                 }*/


        ArrayList pointArray = new ArrayList();

        double[][] pointData = new double[ecArray.size() + 1][3];
        math_vector3d startPt = null;
        math_vector3d endPt = null;
        math_vector3d startPtLast = null;
        math_vector3d endPtLast = null;

      ArrayList crvArray = new ArrayList();
      for (int i = 0; i < ecArray.size() ; i++)
      {
          startPt = ecArray.get(i).GetStartPoint().GetCartesianPoint().GetMathVector3d();
          endPt = ecArray.get(i).GetEndPoint().GetCartesianPoint().GetMathVector3d();
          if(endPtLast ==null ||
             (   (Math.abs(startPt.Subtract(endPtLast).Length()) > 10E-7)&&
                 (Math.abs(startPt.Subtract(startPtLast).Length()) > 10E-7)
                  )
            )
          {
              crvArray.add(new Integer(pointArray.size()));
              pointArray.add(startPt);
          }
          pointArray.add(endPt);

          startPtLast = startPt;
          endPtLast =  endPt;
      }
      crvArray.add(new Integer(pointArray.size()));

      for(int i = 0  ; i <crvArray.size()-1; i++)
      {
          int startIndex = ((Integer)crvArray.get(i)).intValue();
          int endIndex = ((Integer)crvArray.get(i + 1 )).intValue() -1;

          int pointCount = endIndex - startIndex +1;

          double[][] pointToCrvData = new double[pointCount][3];
          for(int j = 0 ; j < pointCount; j++)
          {
              pointToCrvData[j][0] = ((math_vector3d)(pointArray.get(startIndex+j))).X();
              pointToCrvData[j][1] =  ((math_vector3d)(pointArray.get(startIndex+j))).Y();
              pointToCrvData[j][2] = ((math_vector3d)(pointArray.get(startIndex+j))).Z();
          }
          SDT_3DEdge ee = new SDT_3DEdge(pointToCrvData);
          this.add(ee);
      }
    }

    public void add(SDT_3DEdge ee)
    {
        vector.add(ee);
    }

    public SDT_3DEdge get(int index)
    {
        return (SDT_3DEdge) vector.get(index);
    }
    public int Size()
    {
        return this.vector.size();
    }





}
