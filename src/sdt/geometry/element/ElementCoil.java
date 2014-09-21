package sdt.geometry.element;

import java.awt.*;

import sdt.data.*;
import sdt.define.*;
import sdt.geometry.*;
import sdt.panel.drawpanel.*;

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
public class ElementCoil extends ElementCommon
{
    private ElementPoint _ptStart;
    private ElementPoint _ptEnd;
    private ElementPoint _ptOuterStart;
    private ElementPoint _ptOuterEnd;
    private ElementPoint _ptCoilStart;
    private ElementPoint _ptCoilEnd;
    private ElementPoint _ptCoilOuterStart;
    private ElementPoint _ptCoilOuterEnd;

    private ElementRectangle _recCoilBig;
    private ElementRectangle _recCoilSmall;

    private int _type;
    private DataCoil _dataCoil;
    private int _viewType;
     boolean isHightLight = false;
    private Color colorTemp;

    public ElementCoil(DataCoil dataCoil, int viewType, Color c, Color c2)
    {
        super(c, c2, dataCoil, viewType);
        this._dataCoil = dataCoil;
        this._elementType = this.TYPE_COIL;
        this._ptStart = dataCoil.getElementPointStart(viewType);
        this._ptEnd = dataCoil.getElementPointEnd(viewType);
        this._ptOuterStart = dataCoil.getElementPointOuterStart(viewType);
        this._ptOuterEnd = dataCoil.getElementPointOuterEnd(viewType);

        this._ptCoilStart = dataCoil.getElementPointCoilStart(viewType);
        this._ptCoilEnd = dataCoil.getElementPointCoilEnd(viewType);
        this._ptCoilOuterStart = dataCoil.getElementPointCoilOuterStart(viewType);
        this._ptCoilOuterEnd = dataCoil.getElementPointCoilOuterEnd(viewType);


        this._viewType = viewType;
        this._type = dataCoil.getGeometryType();
    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this.setDataRecover();
        this.setCoilDataRecover();
        this.setBoundary();

        this._transfer = tran;



            Polygon poly = new Polygon();

            int pt1[] = tran.setPointRealToShow(_ptStart.X(), _ptStart.Y());
            int pt2[] = tran.setPointRealToShow(_ptEnd.X(), _ptEnd.Y());
            int pt3[] = tran.setPointRealToShow(_ptOuterEnd.X(), _ptOuterEnd.Y());
            int pt4[] = tran.setPointRealToShow(_ptOuterStart.X(), _ptOuterStart.Y());

            g.setColor(this.color);
            poly.addPoint(pt1[0], pt1[1]);
            poly.addPoint(pt2[0], pt2[1]);
            poly.addPoint(pt3[0], pt3[1]);
            poly.addPoint(pt4[0], pt4[1]);
            g.fillPolygon(poly);


            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(_data.getDataManager().getStrokeLine());
            g.setColor(this._data.getDataManager().getColorLine());

            g.drawLine(pt1[0], pt1[1],pt2[0], pt2[1]);
            g.drawLine(pt2[0], pt2[1],pt3[0], pt3[1]);
            g.drawLine(pt3[0], pt3[1],pt4[0], pt4[1]);
            g.drawLine(pt4[0], pt4[1],pt1[0], pt1[1]);

        if (this._type == DefineSystemConstant.COIL_TYPE2)
        {
            Polygon poly2 = new Polygon();


            int pt5[] = tran.setPointRealToShow(_ptCoilStart.X(), _ptCoilStart.Y());
            int pt6[] = tran.setPointRealToShow(_ptCoilEnd.X(), _ptCoilEnd.Y());
            int pt7[] = tran.setPointRealToShow(_ptCoilOuterEnd.X(), _ptCoilOuterEnd.Y());
            int pt8[] = tran.setPointRealToShow(_ptCoilOuterStart.X(), _ptCoilOuterStart.Y());
            g.setColor(this.color2);
            poly2.addPoint(pt5[0], pt5[1]);
            poly2.addPoint(pt6[0], pt6[1]);
            poly2.addPoint(pt7[0], pt7[1]);
            poly2.addPoint(pt8[0], pt8[1]);
            g.fillPolygon(poly2);


             g2d = (Graphics2D) g;
             g2d.setStroke(_data.getDataManager().getStrokeLine());
             g.setColor(this._data.getDataManager().getColorLine());

             g.drawRect(pt5[0],pt5[1],Math.abs( pt7[0] - pt5[0]),  Math.abs(pt5[1] - pt7[1]) ) ;


        }
    }

    public boolean setBoundary()
    {

        double xStart = this._ptStart.X();
        double yStart = this._ptStart.Y();
        double xEnd = this._ptEnd.X();
        double yEnd = this._ptEnd.Y();

        double xOuterStart = this._ptOuterStart.X();
        double yOuterStart = this._ptOuterStart.Y();
        double xOuterEnd = this._ptOuterEnd.X();
        double yOuterEnd = this._ptOuterEnd.Y();

        double xCoilStart = this._ptCoilStart.X();
        double yCoilStart = this._ptCoilStart.Y();
        double xCoilEnd = this._ptCoilEnd.X();
        double yCoilEnd = this._ptCoilEnd.Y();

        double xCoilOuterStart = this._ptCoilOuterStart.X();
        double yCoilOuterStart = this._ptCoilOuterStart.Y();
        double xCoilOuterEnd = this._ptCoilOuterEnd.X();
        double yCoilOuterEnd = this._ptCoilOuterEnd.Y();


        double oldLeftBound;
        double oldRightBound;
        double oldMidVertical;
        double oldLeftUpBound;
        double oldDownBound;
        double oldCoilUpBound;
        double oldCoilDownBound;



        if (this._recCoilBig == null || this._recCoilSmall == null)
        {
            oldLeftBound      = xStart;
            oldRightBound     = xCoilOuterStart;
            oldMidVertical    = xOuterStart;

            oldLeftUpBound    = yStart;
            oldDownBound      = yEnd;
            oldCoilUpBound    = yCoilStart;
            oldCoilDownBound  = yCoilEnd;
        }
        else
        {
            oldLeftBound      = this._recCoilBig.getLeftBound();
            oldRightBound     = this._recCoilSmall.getRightBound();
            oldMidVertical    = this._recCoilBig.getRightBound();

            oldLeftUpBound        = this._recCoilBig.getUpBound();
            oldDownBound      = this._recCoilBig.getDownBound();
            oldCoilUpBound    = this._recCoilSmall.getUpBound();
            oldCoilDownBound  = this._recCoilSmall.getDownBound();
        }

        boolean isLimit = false;
        if (this._type == DefineSystemConstant.COIL_TYPE1)
        {

            if (xEnd != oldLeftBound || yEnd != oldDownBound) //移動 ptEnd
            {
                if (yStart <= yOuterStart)
                {
                    if (yEnd > yStart - 0.03)
                        yEnd = yStart - 0.03;
                }
                else
                {
                    if (yEnd > yOuterStart - 0.03)
                        yEnd = yOuterStart - 0.03;
                }

                yOuterEnd = yEnd;
            }

            if(xOuterEnd != oldMidVertical || yOuterEnd != oldDownBound) //移動 ptOuterEnd
            {
                if (yStart <= yOuterStart)
                {
                    if (yOuterEnd > yStart - 0.03)
                        yOuterEnd = yStart - 0.03;
                }
                else
                {
                    if (yOuterEnd > yOuterStart - 0.03)
                        yOuterEnd = yOuterStart - 0.03;
                }

                 yEnd = yOuterEnd;
            }


            this._ptEnd.setCoordinate(xStart, yEnd);
            this._ptOuterEnd.setCoordinate(xOuterStart, yOuterEnd);

        }
        else
        {
            if (xEnd != oldLeftBound || yEnd != oldDownBound) //移動 ptEnd
            {
                if (yEnd > yCoilEnd - 0.03)
                    yEnd = yCoilEnd - 0.03;

                yOuterEnd = yEnd;
            }

            if (xOuterEnd != oldMidVertical || yOuterEnd != oldDownBound) //移動 ptOuterEnd
            {
                if (yOuterEnd > yCoilEnd - 0.03)
                    yOuterEnd = yCoilEnd - 0.03;

                yEnd = yOuterEnd;
            }


            if(xCoilStart != oldMidVertical || yCoilStart != oldCoilUpBound) //移動 ptCoilStart
            {
                if(yCoilStart > yStart - 0.03 || yCoilStart > yOuterStart - 0.03)
                {
                    if(yCoilStart > yStart - 0.03)
                        yCoilStart = yStart - 0.03;
                    else if(yCoilStart > yOuterStart - 0.03)
                        yCoilStart = yOuterStart - 0.03;

                }
                else if(yCoilStart < yCoilEnd + 0.03)
                    yCoilStart = yCoilEnd + 0.03;

                xCoilStart = xOuterStart;
                yCoilOuterStart = yCoilStart;

            }

            if(xCoilOuterStart != oldRightBound || yCoilOuterStart != oldCoilUpBound) //移動 ptCoilOuterStart
            {
                if (yCoilOuterStart > yStart - 0.03 || yCoilOuterStart > yOuterStart - 0.03)
                {
                    if(yCoilOuterStart > yStart - 0.03)
                        yCoilOuterStart = yStart - 0.03;
                    else if(yCoilOuterStart > yOuterStart - 0.03)
                        yCoilOuterStart = yOuterStart - 0.03;
                }
                else if(yCoilOuterStart < yCoilEnd + 0.03)
                    yCoilOuterStart = yCoilEnd + 0.03;

                if(xCoilOuterStart < xCoilStart + 0.03)
                    xCoilOuterStart = xCoilStart + 0.03;

                yCoilStart = yCoilOuterStart;
                xCoilOuterEnd = xCoilOuterStart;
            }

            if(xCoilEnd != oldMidVertical || yCoilEnd != oldCoilDownBound) //移動 ptCoilEnd
            {
                if(yCoilEnd > yCoilStart - 0.03)
                    yCoilEnd =  yCoilStart - 0.03;
                else if(yCoilEnd < yOuterEnd + 0.03)
                    yCoilEnd = yOuterEnd + 0.03;

                xCoilEnd = xOuterStart;
                yCoilOuterEnd = yCoilEnd;
            }

            if(xCoilOuterEnd != oldRightBound || yCoilOuterEnd != oldCoilDownBound) //移動ptCoilOuterEnd
            {
                if(yCoilOuterEnd > yCoilOuterStart - 0.03)
                    yCoilOuterEnd = yCoilOuterStart - 0.03;
                else if(yCoilOuterEnd < yOuterEnd + 0.03)
                    yCoilOuterEnd = yOuterEnd + 0.03;

                if(xCoilOuterEnd < xCoilEnd + 0.03)
                    xCoilOuterEnd = xCoilEnd + 0.03;

                xCoilOuterStart = xCoilOuterEnd;
                yCoilEnd = yCoilOuterEnd;
            }
            //針對一開始建立時coil就是內凹狀
            if (xCoilOuterStart < xCoilStart + 0.03)
            {
                xCoilOuterStart = xCoilStart + 0.03;
                xCoilOuterEnd = xCoilOuterStart;
            }

            this._ptEnd.setCoordinate(xStart, yEnd);
            this._ptOuterEnd.setCoordinate(xOuterStart, yOuterEnd);
            this._ptCoilStart.setCoordinate(xCoilStart, yCoilStart);
            this._ptCoilOuterStart.setCoordinate(xCoilOuterStart, yCoilOuterStart);
            this._ptCoilEnd.setCoordinate(xCoilEnd, yCoilEnd);
            this._ptCoilOuterEnd.setCoordinate(xCoilOuterEnd, yCoilOuterEnd);

        }



        ObjectPoint objSpt = new ObjectPoint(this._ptStart.X(), this._ptStart.Y());
        ObjectPoint objEpt = new ObjectPoint(this._ptStart.X(), this._ptEnd.Y());
        ObjectPoint objOuterSpt = new ObjectPoint(this._ptOuterStart.X(), this._ptOuterStart.Y());
        ObjectPoint objOuterEpt = new ObjectPoint(this._ptOuterStart.X(), this._ptEnd.Y());


        ObjectPoint objCoilSpt = new ObjectPoint(this._ptOuterStart.X(), this._ptCoilStart.Y());
        ObjectPoint objCoilEpt = new ObjectPoint(this._ptOuterStart.X(), this._ptCoilEnd.Y());
        ObjectPoint objCoilOuterSpt = new ObjectPoint(this._ptCoilOuterStart.X(), this._ptCoilStart.Y());
        ObjectPoint objCoilOuterEpt = new ObjectPoint(this._ptCoilOuterStart.X(), this._ptCoilEnd.Y());



        if (this._recCoilBig == null)
        {
            this._recCoilBig = new ElementRectangle(objSpt, objOuterEpt, this.color,_data);
        }
        else
        {
            this._recCoilBig.setStartEndPt(objSpt, objOuterEpt);
         }

         if (this._recCoilSmall == null)
         {
             this._recCoilSmall = new ElementRectangle(objCoilSpt, objCoilOuterEpt, this.color,_data);
         }
         else
         {
             this._recCoilSmall.setStartEndPt(objCoilSpt, objCoilOuterEpt);
         }


        return isLimit;
    }

    public void setConstraint()
    {
    }

    public void setColor(Color c, Color c2)
    {
        this.color = c;
        this.color2 = c2;

        if (this._type == DefineSystemConstant.COIL_TYPE1)
        {

        }
    }

    public void setColor(Color c)
    {

        if (c.equals(Color.white))
        {
            isHightLight = true;
            colorTemp = this.color2;
            this.color = c;
            this.color2 = c;

        }

        if (c != Color.WHITE && isHightLight)
        {
            this.color = c;
            this.color2 = colorTemp;
        }

    }


    public boolean isPtInBoundary(ObjectPoint pt)
    {
        if (this._type == DefineSystemConstant.COIL_TYPE1)
        {
            if (pt.x >= this._ptStart.X() && pt.x <= this._ptOuterStart.X() &&
                pt.y >= this._ptEnd.Y() && pt.y <= this._ptStart.Y())
                return true;
            else
                return false;
        }
        else
        {
            if( pt.x >= this._ptStart.X() && pt.x <= this._ptOuterStart.X() &&
                  pt.y >= this._ptEnd.Y()   && pt.y <= this._ptStart.Y()  )
                  return true;
             else if(pt.x >= this._ptCoilStart.X() && pt.x <=this._ptCoilOuterStart.X() &&
                     pt.y >= this._ptCoilOuterEnd.Y() && pt.y <= this._ptCoilStart.Y())
                 return true;
             else
                 return false;
         }
    }
    /**
     * Move ptStart, ptEnd, ptCoilStart, ptCoilEnd
     * @param dx displacement of X
     * @param dy double
     */

    public void move(double dx, double dy)
    {
        this._ptStart.move(dx, dy);
        this._ptEnd.move(dx, dy);

        this._ptOuterStart.move(dx, dy);
        this._ptOuterEnd.move(dx, dy);

        this._ptCoilStart.move(dx, dy);
        this._ptCoilEnd.move(dx, dy);

        this._ptCoilOuterStart.move(dx, dy);
        this._ptCoilOuterEnd.move(dx, dy);


        this.rec.translate(dx, dy);
    }

    public void setElementType(int type)
    {
        this._elementType = type;
    }

    /**
     * Used to set Coil startPt and endPt value to another section view, the way similar with elementCommon.setDataRecover()
     */
    public void setCoilDataRecover()
    {
        double oldX;

        switch(this._viewType)
        {
            case DefineSystemConstant.XZView:
                ElementPoint ptCoilStartYZ = this._dataCoil.getElementPointCoilStart(DefineSystemConstant.YZView);
                ElementPoint ptCoilEndYZ   = this._dataCoil.getElementPointCoilEnd(DefineSystemConstant.YZView);
                oldX = ptCoilStartYZ.X();
                ptCoilStartYZ.setCoordinate(oldX, this._ptCoilStart.Y());
                ptCoilEndYZ.setCoordinate(oldX, this._ptCoilEnd.Y());
                break;
            case DefineSystemConstant.YZView:
                ElementPoint ptCoilStartXZ = this._dataCoil.getElementPointCoilStart(DefineSystemConstant.XZView);
                ElementPoint ptCoilEndXZ   = this._dataCoil.getElementPointCoilEnd(DefineSystemConstant.XZView);
                oldX = ptCoilStartXZ.X();
                ptCoilStartXZ.setCoordinate(oldX, this._ptCoilStart.Y());
                ptCoilEndXZ.setCoordinate(oldX, this._ptCoilEnd.Y());
                break;
        }
    }

}
