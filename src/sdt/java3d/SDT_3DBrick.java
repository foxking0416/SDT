package sdt.java3d;

import sdt.stepb.*;
import sdt.define.DefineSystemConstant;


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
public class SDT_3DBrick
{
    private int elementID = -1;
    private int elementPositionType = DefineSystemConstant.BRICK_FACE_CENTER;   //Center:4 ,  Up:1 ,  Down:2 ,  Inner:0 ,  Outer:1

    private stepb_cartesian_point point1;
    private stepb_cartesian_point point2;
    private stepb_cartesian_point point3;
    private stepb_cartesian_point point4;
    private stepb_cartesian_point point5;
    private stepb_cartesian_point point6;
    private stepb_cartesian_point point7;
    private stepb_cartesian_point point8;


    public stepb_cartesian_point GetPoint1() {return this.point1;}
    public stepb_cartesian_point GetPoint2() {return this.point2;}
    public stepb_cartesian_point GetPoint3() {return this.point3;}
    public stepb_cartesian_point GetPoint4() {return this.point4;}
    public stepb_cartesian_point GetPoint5() {return this.point5;}
    public stepb_cartesian_point GetPoint6() {return this.point6;}
    public stepb_cartesian_point GetPoint7() {return this.point7;}
    public stepb_cartesian_point GetPoint8() {return this.point8;}

    private math_vector3d       normal1;
    private math_vector3d       normal2;
    private math_vector3d       normal3;
    private math_vector3d       normal4;

     public math_vector3d  GetNormal1() {return this. normal1;}
     public math_vector3d  GetNormal2() {return this. normal2;}
     public math_vector3d  GetNormal3() {return this. normal3;}
     public math_vector3d  GetNormal4() {return this. normal4;}

     /**
     *          5 ------ 6
     *        / |      / |
     *      /   |    /   |
     *    /     8- / --- 7
     *  1 ------ 2     /
     *  |   /    |   /
     *  | /      | /
     *  4 ------ 3
     */
    public SDT_3DBrick(stepb_cartesian_point cpt1,stepb_cartesian_point cpt2,
                       stepb_cartesian_point cpt3,stepb_cartesian_point cpt4,
                       stepb_cartesian_point cpt5,stepb_cartesian_point cpt6,
                       stepb_cartesian_point cpt7,stepb_cartesian_point cpt8)
    {
        point1 = cpt1;
        point2 = cpt2;
        point3 = cpt3;
        point4 = cpt4;
        point5 = cpt5;
        point6 = cpt6;
        point7 = cpt7;
        point8 = cpt8;

        // elementID = point1.GetIDNumber();
        CalculateNormal();
    }


    public void SetElementID(int elementID)
    {
        this.elementID = elementID;
    }

    public int getElementID()
    {
        return this.elementID;
    }

    public void setPosition(int positionType)
    {
        this.elementPositionType = positionType;
    }
    public boolean isPositionMatch(int positionType)
    {
        return (elementPositionType == positionType);
    }


    private void CalculateNormal()
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
         System.out.println(this.PrintStrCW());
    }
    public String PrintStrCW()
    {
       // return (this.elementID + " , " + point1.GetIDNumber() +  + " , " +  point3.GetIDNumber() + " , " + point2.GetIDNumber()" , " +  point4.GetIDNumber());
       /***
        * Reverse Direction  for face downward
        */
       if(point4 != point5)
           return (this.elementID + " , " + point1.GetIDNumber() + " , " +  point2.GetIDNumber() + " , " +  point3.GetIDNumber() + " , " + point4.GetIDNumber()
                   + " , " + point5.GetIDNumber() + " , " +  point6.GetIDNumber() + " , " +  point7.GetIDNumber() + " , " + point8.GetIDNumber());
       else
           return ((this.elementID) + " , " + point1.GetIDNumber() + " , " +  point2.GetIDNumber() + " , " +  point3.GetIDNumber() + " , " + point4.GetIDNumber()
                   + " , " +  point6.GetIDNumber() +  " , " + point7.GetIDNumber());

    }





}
