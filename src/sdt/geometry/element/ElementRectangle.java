package sdt.geometry.element;

import java.awt.*;

import sdt.geometry.*;
import sdt.panel.drawpanel.*;
import sdt.data.DataBase;

public class ElementRectangle extends ElementBase
{
    public ObjectPoint _startPt;
    public ObjectPoint _endPt;
    public double leftBound;
    public double rightBound;
    public double upBound;
    public double downBound;

    public ElementRectangle(ObjectPoint spt, ObjectPoint ept, Color c,DataBase data)
    {
        super(c, data);
        this._startPt = spt;
        this._endPt = ept;

        //super.SetOrigin();
        this.origin = spt;
        this.setBoundary();
    }

    public void draw(Graphics g,SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        this.setBoundary();

        Color colorLine = g.getColor();

        g.setColor(this.color);
        int showStartXY[] = tran.setPointRealToShow(rec.x, rec.y);
        int showLengthXY[] = tran.setLengthRealToShow(rec.width, rec.height);


        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(_data.getDataManager().getStrokeLine());

        //g.drawRect(showStartXY[0], showStartXY[1], showLengthXY[0], showLengthXY[1]);
        g.fillRect(showStartXY[0], showStartXY[1], showLengthXY[0], showLengthXY[1]);

        g.setColor(colorLine) ;
        g.drawRect(showStartXY[0], showStartXY[1], showLengthXY[0], showLengthXY[1]);

        /*int show2[] = tran.setPointRealToShow(rec.x + rec.width, rec.y);
        int show3[] = tran.setPointRealToShow(rec.x , rec.y - rec.height);
        int show4[] = tran.setPointRealToShow(rec.x + rec.width, rec.y - rec.height);


        g.setColor(colorLine) ;
        g.drawLine(showStartXY[0], showStartXY[1], show2[0], show2[1]);
        g.drawLine(show3[0], show3[1], show4[0], show4[1]);
        g.drawLine(showStartXY[0], showStartXY[1], show3[0], show3[1]);
        g.drawLine(show2[0], show2[1], show4[0], show4[1]);*/

    }

    public void move(double dx, double dy)
    {
        this.origin.translate(dx, dy);
        this.rec.translate(dx, dy);
        this._startPt.translate(dx, dy);
        this._endPt.translate(dx, dy);
        this.setBoundary();
    }

    public boolean setBoundary()
    {
        ObjectPoint _leftTop = new ObjectPoint();

        if (this._startPt.x <= this._endPt.x)
            _leftTop.x = this._startPt.x;
        else
            _leftTop.x = this._endPt.x;

        if(this._startPt.y >= this._endPt.y)
            _leftTop.y = this._startPt.y;
        else
            _leftTop.y = this._endPt.y;



        this.rec.setLocation(_leftTop);
        this.rec.width = Math.abs(this._startPt.x - this._endPt.x);
        this.rec.height = Math.abs(this._startPt.y - this._endPt.y);

         return false;
    }

    // check if the pt is in rectangle or not
    public boolean isPtInBoundary(ObjectPoint pt)
    {
        if (this.rec.contains(pt))
            return true;
        else
            return false;
    }

    public void setConstraint()
    {
    }

    public void setStartEndPt(ObjectPoint spt, ObjectPoint ept)
   {
       this.origin = spt;
       this._startPt = spt;
       this._endPt = ept;
       this.setBoundary();
   }

   public void setWidthLength(double w, double l)
   {

   }

   public double getLeftBound()
   {
       if(this._startPt.x <= this._endPt.x)
           return this._startPt.x;
       else
           return this._endPt.x;
   }

   public double getRightBound()
   {
       if(this._startPt.x <= this._endPt.x)
           return this._endPt.x;
       else
           return this._startPt.x;
   }

   public double getUpBound()
   {
       if(this._startPt.y >= this._endPt.y)
           return this._startPt.y;
       else
           return this._endPt.y;
   }

   public double getDownBound()
   {
       if(this._startPt.y >= this._endPt.y)
           return this._endPt.y;
       else
           return this._startPt.y;
   }

}
