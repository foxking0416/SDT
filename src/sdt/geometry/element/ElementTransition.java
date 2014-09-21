package sdt.geometry.element;
import java.awt.*;

import sdt.data.*;
import sdt.geometry.*;
import sdt.panel.drawpanel.*;
import sdt.define.DefineSystemConstant;

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
public class ElementTransition extends ElementCommon
{
    protected ElementPoint _ptStart;
    protected ElementPoint _ptEnd;
    private   ElementPoint _ptMiddleInner;
    private   ElementPoint _ptMiddleOuter;

    protected ElementLine _transitionLine;
    private   double      _ratioInner;
    private   double      _ratioOuter;



    public ElementTransition( DataTransition dataTran, int viewType, Color c)
    {
        super(c, dataTran, viewType);
        this._ptStart = dataTran.getElementPointStart(viewType);
        this._ptEnd = dataTran.getElementPointEnd(viewType);
        this._elementType = this.TYPE_TRANSITION;

        this._ptMiddleInner = dataTran.getElementPointMiddleInner(viewType);
        this._ptMiddleOuter = dataTran.getElementPointMiddleOuter(viewType);
        this._ratioInner   = dataTran.getRatioInner();
        this._ratioOuter   = dataTran.getRatioOuter();


    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this.setDataRecover();
        this.setBoundary();

        this._transfer = tran;

        this._transitionLine.draw(g, this._transfer);
        this._ptMiddleInner.draw(g, this._transfer);
        this._ptMiddleOuter.draw(g, this._transfer);
        //System.out.println("transition draw");

    }



    public boolean setBoundary()
    {
        //System.out.println("transition setBoundary");
        boolean isLimit = false;

        ObjectPoint objSpt = new ObjectPoint(this._ptStart.X(), this._ptStart.Y());
        ObjectPoint objEpt = new ObjectPoint(this._ptEnd.X(), this._ptEnd.Y());
        double slope = (this._ptEnd.Y() - this._ptStart.Y()) /  (this._ptEnd.X()- this._ptStart.X() );
        double innerX = this._ptMiddleInner.X();
        double outerX = this._ptMiddleOuter.X();

        if (innerX <= this._ptStart.X())
            innerX = this._ptStart.X();
        if (outerX <= this._ptStart.X() + 0.1)
            outerX = this._ptStart.X() + 0.1;
        if (outerX >= this._ptEnd.X())
            outerX = this._ptEnd.X();
        if (innerX >= this._ptEnd.X() -0.1)
            innerX = this._ptEnd.X() -0.1;
        if (outerX <= innerX + 0.001)
            outerX = innerX + 0.001;

        double innerY = (innerX - this._ptStart.X()) * slope + this._ptStart.Y();
        double outerY = (outerX - this._ptEnd.X()  ) * slope + this._ptEnd.Y();

        this._ptMiddleInner.setCoordinate(innerX,innerY);
        this._ptMiddleOuter.setCoordinate(outerX,outerY);


        if (this._transitionLine == null)
            this._transitionLine = new ElementLine(objSpt, objEpt, this.color,_data);
        else
            this._transitionLine.setStartEndPt(objSpt, objEpt);



        return isLimit;
    }

    public boolean isPtInBoundary(ObjectPoint point)
    {

        if (this._transitionLine != null)
        {
            if (this._transitionLine.isPtInBoundary(point) == true)
                return true;
            else
                return false;
        }
        else
            return false;


    }

    public void move(double dx, double dy)
    {
        this._ptStart.move(dx, dy);
        this._ptEnd.move(dx, dy);
//System.out.println("move transition");
        this._ptMiddleInner.move(dx, dy);
        this._ptMiddleOuter.move(dx, dy);


        this.rec.translate(dx, dy);
    }

    public void setColor(Color c)
    {
        this.color = c;

        if (this._transitionLine != null)
            this._transitionLine.setColor(this.color);
    }

    public void setConstraint()
    {
    }

    public void setElementType(int type)
    {
        this._elementType = type;
    }

}
