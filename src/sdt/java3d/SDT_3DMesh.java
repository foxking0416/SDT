package sdt.java3d;

import sdt.stepb.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SDT_3DMesh
{
    private int _elementID = -1;
    private stepb_cartesian_point point1;
    private stepb_cartesian_point point2;
    private stepb_cartesian_point point3;
    private stepb_cartesian_point point4;
    private double  _sectionZArea;

    public stepb_cartesian_point GetPoint1() {return this.point1;}
    public stepb_cartesian_point GetPoint2() {return this.point2;}
    public stepb_cartesian_point GetPoint3() {return this.point3;}
    public stepb_cartesian_point GetPoint4() {return this.point4;}

    private math_vector3d       normal1;
    private math_vector3d       normal2;
    private math_vector3d       normal3;
    private math_vector3d       normal4;

     public math_vector3d  GetNormal1() {return this. normal1;}
     public math_vector3d  GetNormal2() {return this. normal2;}
     public math_vector3d  GetNormal3() {return this. normal3;}
     public math_vector3d  GetNormal4() {return this. normal4;}

     /**
     *   4 --- 3
     *   |     |
     *   1 --- 2
     */
    public SDT_3DMesh(stepb_cartesian_point cpt1,stepb_cartesian_point cpt2,stepb_cartesian_point cpt3,stepb_cartesian_point cpt4)
    {
        point1 = cpt1;
        point2 = cpt2;
        point3 = cpt3;
        point4 = cpt4;

       // elementID = point1.GetIDNumber();
        calculateNormal();
        calculateArea();
    }

    /**
    *      3
    *    /    \
    *   1 --- 2
    */
    public SDT_3DMesh(stepb_cartesian_point cpt1, stepb_cartesian_point cpt2, stepb_cartesian_point cpt3)
    {
        point1 = cpt1;
        point2 = cpt2;
        point3 = cpt3;

       // elementID = point2.GetIDNumber();
        calculateNormal();
        calculateArea();
    }

    public void SetElementID(int elementID)
    {
        this._elementID = elementID;
    }




    private void calculateNormal()
    {
        math_vector3d vector3d12 = null;
        math_vector3d vector3d13 = null;
        math_vector3d vector3d14 = null;
        math_vector3d vector3d23 = null;
        math_vector3d vector3d34 = null;

        math_vector3d vector3d21 = null;
        math_vector3d vector3d31 = null;
        math_vector3d vector3d41 = null;
        math_vector3d vector3d32 = null;
        math_vector3d vector3d43 = null;




        vector3d12 = new math_vector3d(point2.X() - point1.X(), point2.Y() - point1.Y(), point2.Z() - point1.Z());
        vector3d32 = new math_vector3d(point2.X() - point3.X(), point2.Y() - point3.Y(), point2.Z() - point3.Z());
        if (point4 != null)
        {
            vector3d14 = new math_vector3d(point4.X() - point1.X(), point4.Y() - point1.Y(), point4.Z() - point1.Z());
            vector3d34 = new math_vector3d(point4.X() - point3.X(), point4.Y() - point3.Y(), point4.Z() - point3.Z());
        }
        else
        {
            vector3d13 = new math_vector3d(point3.X() - point1.X(), point3.Y() - point1.Y(), point3.Z() - point1.Z());
        }



       vector3d21 = vector3d12.Multiply( -1);
       vector3d23 = vector3d32.Multiply( -1);
       if (point4 != null)
       {
           vector3d41 = vector3d14.Multiply( -1);
           vector3d43 = vector3d34.Multiply( -1);
       }
       else
       {
           vector3d31 = vector3d13.Multiply( -1);
       }



        if(point4 != null)
        {
            normal1 = (vector3d12.CrossProduct(vector3d14)).Normalize();
            normal2 = (vector3d23.CrossProduct(vector3d21)).Normalize();
            normal3 = (vector3d34.CrossProduct(vector3d32)).Normalize();
            normal4 = (vector3d41.CrossProduct(vector3d43)).Normalize();
        }
        else
        {
            normal1 = (vector3d12.CrossProduct(vector3d13)).Normalize();
            normal2 = (vector3d23.CrossProduct(vector3d21)).Normalize();
            normal3 = (vector3d31.CrossProduct(vector3d32)).Normalize();

        }
    }
    public void Print()
    {
         System.out.println(this.printStrCW());
    }
    public String printStrCW()
    {
       // return (this.elementID + " , " + point1.GetIDNumber() +  + " , " +  point3.GetIDNumber() + " , " + point2.GetIDNumber()" , " +  point4.GetIDNumber());
       /***
        * Reverse Direction  for face downward
        */
       if(point4 != null)
           return (this._elementID + " , " + point1.GetIDNumber() + " , " +  point4.GetIDNumber() + " , " +  point3.GetIDNumber() + " , " + point2.GetIDNumber());
       else
           return ((this._elementID) + " , " + point1.GetIDNumber() + " , " +   point3.GetIDNumber() + " , " + point2.GetIDNumber());
    }

    public String printStrCCW()
    {
        // return (this.elementID + " , " + point1.GetIDNumber() +  + " , " +  point3.GetIDNumber() + " , " + point2.GetIDNumber()" , " +  point4.GetIDNumber());
        /***
         * Reverse Direction  for face downward
         */
        if (point4 != null)
            return (this._elementID + " , " + point1.GetIDNumber() + " , " + point2.GetIDNumber() + " , " + point3.GetIDNumber() + " , " + point4.GetIDNumber());
        else
            return (( this._elementID) + " , " + point1.GetIDNumber() + " , " + point2.GetIDNumber() + " , " + point3.GetIDNumber());
    }

    public void calculateArea()
    {
        double triArea = calculateAreaTri(this.point1, this.point2,this.point3);
        double triArea2 = 0;
        if (point4 != null)
        {
            triArea2 = calculateAreaTri(this.point1, this.point3, this.point4);
        }
        this._sectionZArea = triArea + triArea2;
    }

    private double calculateAreaTri(stepb_cartesian_point pt1,
                                    stepb_cartesian_point pt2,
                                    stepb_cartesian_point pt3)
    {
        double pt1x = pt1.X();
        double pt1y = pt1.Y();
        double pt2x = pt2.X();
        double pt2y = pt2.Y();
        double pt3x = pt3.X();
        double pt3y = pt3.Y();

        double triOut12 = Math.abs(pt2x - pt1x) * Math.abs(pt2y - pt1y) / 2.0;
        double triOut23 = Math.abs(pt3x - pt2x) * Math.abs(pt3y - pt2y) / 2.0;
        double triOut31 = Math.abs(pt3x - pt1x) * Math.abs(pt3y - pt1y) / 2.0;

        double outXmin = pt1x;
        double outXmax = pt1x;
        double outYmin = pt1y;
        double outYmax = pt1y;

        if(pt2x > outXmax)
            outXmax = pt2x;
        if(pt3x > outXmax)
            outXmax = pt3x;
        if(pt2x < outXmin)
            outXmin = pt2x;
        if(pt3x < outXmin)
            outXmin = pt3x;
         if(pt2y > outYmax)
             outYmax = pt2y;
         if(pt3y > outYmax)
             outYmax = pt3y;
         if(pt2y < outYmin)
             outYmin = pt2y;
         if(pt3y < outYmin)
             outYmin = pt3y;
        double squareOut =  (outXmax - outXmin) * (outYmax - outYmin);
        double triArea = squareOut - triOut12 - triOut23 -triOut31;
        return triArea;
    }
    public double getSectionZArea()
    {
        return this._sectionZArea;
    }
    public int getElementID()
    {
        return this._elementID;
    }


}
