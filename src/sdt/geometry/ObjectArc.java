package sdt.geometry;

import java.awt.geom.Arc2D;
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
public class ObjectArc extends  Arc2D.Double implements Serializable
{
   public ObjectArc()
   {

   }
    public ObjectArc(double x, double y, double w, double h,
		      double start, double extent, int type)
    {
       // super(x,y,w,h,start,extent,type);
        this.setArc(x,y,w,h,start,extent,type);

    }


}
