package sdt.geometry;

import java.awt.Point;
import java.io.Serializable;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D;



public class ObjectPoint implements Serializable
{
    public double x;
    public double y;

    public ObjectPoint()
    {
        this.x = 0;
        this.y = 0;
        //super();
    }
    public ObjectPoint(double ix,double iy)
    {

        this.x = ix;
        this.y = iy;

        //super(x,y);
    }
    public void setLocation(double ix,double iy)
    {
        this.x = ix;
        this.y = iy;

    }
    public double distance(ObjectPoint dpt)
    {
        double a = dpt.x - this.x;
        double b = dpt.y - this.y;

        double result = Math.sqrt(a*a+b*b);

        return result;
    }
    public void translate(double tx,double ty)
    {
        this.x +=tx;
        this.y +=ty;
    }
    public void AddVector(MathVector2D vec)
    {
        this.x+=vec.x;
        this.y+=vec.y;
    }
    public void Print()
    {
        System.out.println("point2Double( " +this.x +" , " +this.y + " ) ");
    }


}
