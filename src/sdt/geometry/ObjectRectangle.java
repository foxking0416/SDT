package sdt.geometry;

import java.io.Serializable;

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
public class ObjectRectangle implements Serializable
{
    public ObjectPoint ptStart;
    public ObjectPoint ptEnd;

    public double x;

   public double y;

   public double width;

   public double height;


    public ObjectRectangle()
    {
        ptStart = new ObjectPoint();
        ptEnd = new ObjectPoint();
    }

    /**
     * 設定ObjectRectangle參數
     * @param x 左上點的X
     * @param y 左上點的Y
     * @param width rectangle的寬度
     * @param height rentangle的高度
     */
    public ObjectRectangle(double x, double y, double width, double height)
    {
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
     }

     public ObjectRectangle(double width, double height)
     {
         this(0, 0, width, height);
     }
     public void setLocation(double x,double y)
     {
         this.x = x;
         this.y = y;
     }
     public void setLocation(ObjectPoint point)
     {
         this.x = point.x;
         this.y = point.y;
     }

     public void translate(double dx, double dy)
     {
         this.x += dx;
         this.y += dy;
     }

     public boolean contains(ObjectPoint point)
     {
         return contains(point.x, point.y);
     }

     public boolean contains(double X, double Y)
     {
         double w = this.width;
         double h = this.height;
         if (w <= 0.0001 || h <= 0.0001)
         {
             // At least one of the dimensions is negative...
             return false;
         }
         // Note: if either dimension is zero, tests below must return false...
         double x = this.x;
         double y = this.y;

         if(X >= this.x && X <= this.x + w && Y <= this.y && Y >= this.y - h)
             return true;
         else
             return false;
         /*
         if (X < x || Y < y)
         {
             return false;
         }
         w += x;
         h += y;
         //    overflow || intersect
         return ((w < x || w > X) &&
                 (h < y || h > Y));
*/
     }

}
