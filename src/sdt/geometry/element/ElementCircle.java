
package sdt.geometry.element;

import java.awt.*;

import sdt.geometry.*;
import sdt.panel.drawpanel.*;
import sdt.data.DataBase;

public class ElementCircle extends ElementBase
{

    private double _radius;
    private ObjectPoint _endPt;
    private ObjectPoint _centerPt;
    private ObjectRectangle _recBoundary;


    public ElementCircle(ObjectPoint cpt, ObjectPoint ept, Color c, DataBase data)
    {
        super(c, data);
        this._endPt = ept;
        this._centerPt = cpt;
        this._recBoundary = new ObjectRectangle();
    }

    public boolean setBoundary()
    {
        ObjectPoint leftTop = new ObjectPoint();

        this._radius = Math.sqrt(Math.pow(this._endPt.x - this._centerPt.x, 2) + Math.pow(this._endPt.y - this._centerPt.y, 2));
        leftTop.x = this._centerPt.x - this._radius;
        leftTop.y = this._centerPt.y + this._radius;

        this.rec.setLocation(leftTop);//這個rec是用來畫圖的
        this.rec.width = 2 * this._radius;
        this.rec.height = 2 * this._radius;
        return false;

    }

    public void draw(Graphics g,SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        this.setBoundary();

        g.setColor(this.color);


        int showRecXY[] = tran.setPointRealToShow(this.rec.x, this.rec.y);
        int showRecWH[] = tran.setLengthRealToShow(this.rec.width, this.rec.height);

        g.drawArc(showRecXY[0], showRecXY[1], showRecWH[0], showRecWH[1], 0, 360);

    }



    public void setStartEndPt(ObjectPoint cpt, ObjectPoint ept )
    {
        this._endPt = ept;
        this._centerPt = (ObjectPoint) cpt;
    }

    // check if the pt is in circle or not
    public boolean isPtInBoundary(ObjectPoint pt)
    {
        double distance = Math.sqrt( Math.pow(pt.x - this._centerPt.x, 2) + Math.pow(pt.y - this._centerPt.y, 2));

        if(Math.abs(distance - this._radius) < this._transfer.setLengthShowToReal(5))
          // && pt.x < this.endPt.x && pt.x > this.startPt.x && pt.y > this.endPt.y && pt.y < this.startPt.y)
            return true;
        else
            return false;
    }

    public void setColor(Color c)
    {
        this.color = c;
    }

    public void setConstraint()
    {
    }

    public boolean setDataBack()
    {
        return false;
    }


}
