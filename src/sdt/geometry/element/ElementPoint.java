package sdt.geometry.element;

import java.awt.*;

import sdt.geometry.*;
import sdt.panel.drawpanel.*;
import sdt.data.DataBase;

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
public class ElementPoint extends ElementBase
{
    ObjectPoint point;
    ;

    public ElementPoint(double x, double y, Color c,DataBase data)
    {
        super(c, data);
        this._elementType = this.TYPE_POINT;
        this.point = new ObjectPoint(x, y);
    }

    public ElementPoint(ObjectPoint pt, Color c,DataBase data)
    {
        super(c, data);
        this._elementType = this.TYPE_POINT;
        this.point = pt;

    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        int[] showValue = this._transfer.setPointRealToShow(this.point.x, this.point.y);
        int ptSize = _data.getDataManager().getStrokePt();
        g.setColor(this.color);
        g.fillArc(showValue[0] - ptSize / 2, showValue[1] - (ptSize - ptSize/ 2), ptSize, ptSize, 0, 359);
    }
    public void drawBig(Graphics g, SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        int[] showValue = this._transfer.setPointRealToShow(this.point.x, this.point.y);
        g.setColor(this.color);
        g.fillArc(showValue[0] - 4, showValue[1] - 4, 8, 8, 0, 359);
    }

    public boolean setBoundary()
    {
        return false;
    }

    public boolean isPtInBoundary(ObjectPoint pt)
    {
        if(this._transfer == null)
            return false;
        if (Math.abs(pt.x - this.point.x) < this._transfer.setLengthShowToReal(8) && Math.abs(pt.y - this.point.y) < this._transfer.setLengthShowToReal(8))
            return true;
        else
            return false;
    }

    public void move(double dx, double dy)
    {
            this.point.translate(dx, dy);
    }

    public void setCoordinate(double dx, double dy)
    {
        this.point.x = dx;
        this.point.y = dy;
    }


    public double[] getCoordinate()
    {
        double[] returnValue = new double[2];
        returnValue[0] = this.point.x;
        returnValue[1] = this.point.y;

        return returnValue;
    }

    public double X()
    {
        return this.point.x;
    }

    public double Y()
    {
        return this.point.y;
    }

    public void setConstraint()
    {
    }



}
