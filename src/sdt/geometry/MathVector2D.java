package sdt.geometry;

import java.awt.*;
import java.io.Serializable;

public class MathVector2D implements Serializable
{
    public double x;
    public double y;

    private  double longitude;

    public  double GetLongitude()    {return this.longitude;}
    public  void   SetLongitude()    { longitude  = Math.sqrt( x*x + y*y);}
    public MathVector2D(double ix, double iy)
    {
        x = ix;
        y = iy;
        this.SetLongitude();
    }

    public MathVector2D(ObjectPoint sPt,ObjectPoint ePt)
    {
        longitude = sPt.distance(ePt) ;

        this.x = ePt.x - sPt.x;
        this.y = ePt.y - sPt.y;
    }
    public MathVector2D GetUnit()
    {
         MathVector2D unit = new MathVector2D(x,y);
        double unitlongitude  = Math.sqrt( x*x + y*y);
        unit.x /= unitlongitude;
        unit.y /= unitlongitude;
        unit.longitude /= unitlongitude;
        return unit;
    }


    public MathVector2D GetNormal()
    {
        MathVector2D normal = new MathVector2D(y,-x);
        return normal;
    }
    public ObjectPoint ToThePoint(ObjectPoint start,double distance)
    {
        ObjectPoint target;
        double tx = start.x + distance / this.longitude * this.x;
        double ty = start.y + distance / this.longitude * this.y;
        target = new ObjectPoint(tx, ty);
        return target;
    }
    public MathVector2D GetReverse()
    {
        MathVector2D normal = new MathVector2D(-x,-y);
        return normal;
    }
    public double GetTheta(MathVector2D vector)
    {
        double rad = 0.0;

        MathVector2D u1,u2;
        u1 = this.GetUnit();
        u2 = vector.GetUnit();
        double cosTheta = u1.x * u2.x + u1.y * u2.y;
        if (Math.abs(cosTheta - 1.0) < 10E-5)
            rad = 0;
        else  if(Math.abs(cosTheta + 1.0) < 10E-5)
        {
            rad = Math.PI;
        }
        else
            rad = Math.acos(cosTheta);

        if (u1.y > 0 && u2.y > 0)
        {
            if (u1.x < u2.x) //cw
                rad = Math.abs(rad) * -1;
            else
                rad = Math.abs(rad) * 1;
        }
        else if (u1.y < 0 && u2.y < 0)
        {
            if (u1.x > u2.x) //cw
                rad = Math.abs(rad) * -1;
            else
                rad = Math.abs(rad) * 1;
        }
        else if (u1.y > 0 && u2.y < 0)
            rad = Math.abs(rad) * -1;
        else if (u1.y < 0 && u2.y > 0)
            rad = Math.abs(rad) * 1;
        return rad;
    }

    public double Cross(MathVector2D vec)
    {
        double Z;
        Z = x*vec.y - y*vec.x;
        return Z;
    }

    public boolean isSame(MathVector2D vec)
    {
        boolean bool = false;
        if (Math.abs(this.x - vec.x) < 10E4 && Math.abs(this.y - vec.y) < 10E4)
            bool = true;

        return bool;
    }
    public void Print()
    {
        System.out.println("math_vector2d( " +this.x +" , " +this.y + " ) "+"( "+ this.longitude + " )");

    }
    public MathVector2D ProjectToVect(MathVector2D vect)
    {
        MathVector2D result = new MathVector2D(0,0);
        MathVector2D unitV = vect.GetUnit();
        double thetaDirChange = this.GetTheta(vect);
        double cosValue = Math.cos(thetaDirChange);

        //this.longitude = this.longitude * cosValue;
        //this.x = unitV.x * this.longitude;
        //this.y = unitV.y * this.longitude;

        result.x = unitV.x * this.longitude* cosValue;
        result.y = unitV.y * this.longitude* cosValue;
        result.SetLongitude();
        return result;

    }
    public void RotateToVect(MathVector2D vect)
   {
       MathVector2D unitV = vect.GetUnit();
       this.x = unitV.x * this.longitude;
       this.y = unitV.y * this.longitude;
   }


    public void AddVector(MathVector2D vect)
    {
        this.x += vect.x ;
        this.y += vect.y ;
        this.SetLongitude();
    }

    public double GetAngle()
    {
        MathVector2D u1 = this.GetUnit();
        double u1Theta = 0.0;
         double u1ThetaC = Math.acos(u1.x);
         double u1ThetaS = Math.asin(u1.y);
         double tol = 10E-4;

         if (u1ThetaC > tol) // first quarter, fourth quarter, x postive
         {
             if (u1ThetaS > tol) //first quarter
                 u1Theta = u1ThetaC;

             else //fourth quarter
                 u1Theta = -u1ThetaC;
         }
         else if (Math.abs(u1ThetaC - 0) < tol) //y postive, y negative
         {
             if(u1ThetaS - 0 > tol) //y postive
                 u1Theta = Math.PI /2.0;
             else if (Math.abs(u1ThetaS - 0) < tol) //x postive
                 u1Theta = 0;
             else
                 u1Theta = -Math.PI /2.0;
         }
         else //(u1ThetaC <0) //second quarter, third quarter x negetive
         {
             if (u1ThetaS - 0 > tol) //second quarter
                 u1Theta = u1ThetaC;
             else if (Math.abs(u1ThetaS - 0) < tol) //x negative
                 u1Theta = Math.PI;
             else //third quarter
                 u1Theta = -u1ThetaC;
         }
         return u1Theta;


    }

    public MathVector2D RotateAngle(double radCCW)  //ccw is postive
    {
        MathVector2D u2;


        double u1Theta = 0.0;
        u1Theta = this.GetAngle();

        double u2Theta = u1Theta+ radCCW;

        u2 = new MathVector2D(Math.cos(u2Theta)*this.longitude,Math.sin(u2Theta)*longitude);

        return u2;
    }
    public MathVector2D multiply(double d)
    {
        MathVector2D result = null;

        result = new MathVector2D(this.x*d,this.y*d);
        return result;



    }

}
