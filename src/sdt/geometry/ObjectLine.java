package sdt.geometry;

import java.io.Serializable;
import java.awt.geom.*;

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
public class ObjectLine extends  Line2D.Double implements Serializable
{
    public ObjectLine()
    {
    }

    public ObjectLine(double X1, double Y1, double X2, double Y2)
    {
        setLine(X1, Y1, X2, Y2);
    }


}
