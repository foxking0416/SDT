package sdt.java3d;

import sdt.geometry.*;
import sdt.stepb.*;
import java.util.ArrayList;


public class SDT_3DEdge
{

    public double pointLineStart[]  = new double[3];
    public double pointLineEnd[]    = new double[3];
    private math_vector3d theStartPt;
    private math_vector3d theEndPt;
    private math_vector3d theOrigin;

    private SDT_ArrayCrvpt  crvPtArray;

    private double              crvData[][];
    private double              pointData[][];
    private double             theRadius;
    private int                theCurveType = 0; //0 unkonwn, 1 line, 2 circle
    private double scale = 1;

    public SDT_3DEdge(stepb_edge_curve ec)
    {
        stepb_curve        theCurve;
        stepb_line         theLine;
        stepb_circle       theCircle;



        theCurve   = ec.GetCurve();

        if(theCurve.GetName().equals("STEP_LINE") )
        {
            theCurveType = 1;
            theLine = (stepb_line)theCurve;
            theStartPt = ec.GetStartPoint().GetCartesianPoint().GetMathVector3d();
            pointLineStart[0] =  theStartPt.X()*scale ;//-scale;
            pointLineStart[1] =  theStartPt.Y()*scale;//-scale;
            pointLineStart[2] = theStartPt.Z()*scale ;//-scale;
            theEndPt   = ec.GetEndPoint().GetCartesianPoint().GetMathVector3d();
            pointLineEnd[0] =  theEndPt.X()*scale ;//-scale;
            pointLineEnd[1] =  theEndPt.Y()*scale;//-scale;
            pointLineEnd[2] =  theEndPt.Z()*scale;//-scale;


        }
        else if (theCurve.GetName().equals("STEP_CIRCLE") )
        {
            theCurveType = 2;
            theCircle = (stepb_circle) theCurve;
            theStartPt = ec.GetStartPoint().GetCartesianPoint().GetMathVector3d();
            theEndPt = ec.GetEndPoint().GetCartesianPoint().GetMathVector3d();
            theOrigin = theCircle.GetAxis().GetOrigin().GetMathVector3d();

            theRadius = theCircle.GetRadius();
            crvPtArray = new SDT_ArrayCrvpt();
        }
        this.SetData();
    }

    public SDT_3DEdge(double[][] crvDataInput)
    {
        int count = crvDataInput.length;
        this.crvData = new double[count][3];
        for(int i = 0 ; i < count ; i++)
        {
            this.crvData[i][0] = crvDataInput[i][0]*scale;
            this.crvData[i][1] = crvDataInput[i][1]*scale;
            this.crvData[i][2] = crvDataInput[i][2]*scale;
        }
        pointData = new double[2][3];
        pointData[0][0] = this.crvData[0][0]*scale;
        pointData[0][1] = this.crvData[0][1]*scale;
        pointData[0][2] = this.crvData[0][2]*scale;
        pointData[1][0] = this.crvData[count - 1][0]*scale;
        pointData[1][1] = this.crvData[count - 1][1]*scale;
        pointData[1][2] = this.crvData[count - 1][2]*scale;

    }



    private void SetData()
    {
        if( theCurveType ==  1 )
        {
            this.crvData = new double[2][3];
            crvData[0][0] = this.pointLineStart[0];
            crvData[0][1] = this.pointLineStart[1];
            crvData[0][2] = this.pointLineStart[2];
            crvData[1][0] = this.pointLineEnd[0];
            crvData[1][1] = this.pointLineEnd[1];
            crvData[1][2] = this.pointLineEnd[2];

            pointData = new double[2][3];
            pointData[0][0] = this.pointLineStart[0];
            pointData[0][1] = this.pointLineStart[1];
            pointData[0][2] = this.pointLineStart[2];
            pointData[1][0] = this.pointLineEnd[0];
            pointData[1][1] = this.pointLineEnd[1];
            pointData[1][2] = this.pointLineEnd[2];


        }
        else if ( theCurveType ==  2 )
        {
            //SetCrvData(0.2);
            SetCrvData(this.theRadius/ 8.0);
            int count = this.crvPtArray.Size();
            //------------------------------
            pointData = new double[2][3];
            pointData[0][0] = this.crvData[0][0];
            pointData[0][1] = this.crvData[0][1];
            pointData[0][2] = this.crvData[0][2];
            pointData[1][0] = this.crvData[count - 1][0];
            pointData[1][1] = this.crvData[count - 1][1];
            pointData[1][2] = this.crvData[count - 1][2];
        }
    }




    public double[][] GetPts()
        {
            return this.pointData;
    }


    public double[][] GetCrvs()
    {
        return this.crvData;
    }

    private void SetCrvData(double minDis)
    {

        crvPtArray.Add(this.theStartPt);
        crvPtArray.Add(this.theEndPt);

        math_vector3d tempPt,firstPt,secondPt;
        firstPt = this.theStartPt;
        secondPt = this.theEndPt;

        tempPt = this.TwoPointGetCenterArcPoint(firstPt,secondPt);
        crvPtArray.InsertAfter(firstPt,tempPt);

        while(tempPt.Distance(firstPt)>= minDis)
        {
            int count = this.crvPtArray.Size();
            for (int i = 0; i < count + count -2; i++)
            {
                firstPt = (math_vector3d) crvPtArray.Get(i);
                secondPt = (math_vector3d) crvPtArray.Get(i + 1);
                tempPt = this.TwoPointGetCenterArcPoint(firstPt, secondPt);
                crvPtArray.InsertAfter(firstPt, tempPt);
                i++;
            }
        }

        int count = this.crvPtArray.Size();

        System.out.println("crv count: " + count);
        double diff = this.theEndPt.Z()-this.theStartPt.Z();
        firstPt = (math_vector3d) crvPtArray.Get(0);
        for(int i=1 ; i < count-1 ; i++)
        {
            tempPt =  (math_vector3d) crvPtArray.Get(i);
            tempPt.SetZ(firstPt.Z() + diff / (count -1) * i) ;
        }



        this.crvData = new double[count][3];
        for(int i = 0 ; i < count; i++)
        {
            tempPt = (math_vector3d)crvPtArray.Get(i);
            crvData[i][0] = tempPt.X() *scale -scale;
            crvData[i][1] = tempPt.Y()*scale -scale;
            crvData[i][2] = tempPt.Z()*scale -scale;

        }


    }

    private math_vector3d TwoPointGetCenterArcPoint(math_vector3d start, math_vector3d end)
    {
        /*math_vector3d center = null;
        math_vector3d temp;
        temp = end.Subtract(start);
        temp = temp.Divide(2.0);
        temp = start.Add(temp);
        temp.Subtract2(this.theOrigin);
        double tempL = temp.Length();
        temp = temp.Divide(tempL);
        center = this.theOrigin.Add(temp,this.theRadius);
        return center;*/

    //-----------------------


    ObjectPoint center2d = new ObjectPoint((start.X() + end.X()) / 2.0,(start.Y() + end.Y()) / 2.0);
    ObjectPoint orgin2d  = new ObjectPoint(this.theOrigin.X(),this.theOrigin.Y());


    MathVector2D orginTocenter = new MathVector2D(orgin2d, center2d);

    orginTocenter = orginTocenter.GetUnit();
    orginTocenter = orginTocenter.multiply(this.theRadius);
    orgin2d.AddVector(orginTocenter);

    math_vector3d center = new math_vector3d(orgin2d.x,orgin2d.y,0);
    return center;
    }

}
