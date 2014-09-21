package sdt.geometry.element;

import java.awt.Color;
import sdt.data.DataMagnet;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ElementTopPlate extends ElementMagnet
{
    public ElementTopPlate(DataMagnet data, int viewType, Color c)
    {
        super(data, viewType, c);
        this._elementType = this.TYPE_TOPPLATE;
    }
}
