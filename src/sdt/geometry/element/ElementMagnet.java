package sdt.geometry.element;

import java.awt.*;

import sdt.data.*;
import sdt.geometry.*;
import sdt.panel.drawpanel.*;
import sdt.define.DefineSystemConstant;
import java.util.ArrayList;

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
public class ElementMagnet extends ElementCommon
{
    public ElementPoint _startPt;
    public ElementPoint _endPt;
    public ElementPoint _leftDownPt;
    public ElementPoint _rightUpPt;
    public ElementRectangle _recMagnet;


    public ElementMagnet(DataMagnet data, int viewType, Color c)
    {
        super(c,data,viewType);

        this._startPt = data.getElementPointStart(_viewType);
        this._endPt = data.getElementPointEnd(_viewType);
        this._leftDownPt = data.getElementPointLeftDown(viewType);
        this._rightUpPt  = data.getElementPointRightUp(viewType);

        this._elementType = this.TYPE_MAGNET;
                //System.out.println("new Element Magnet" + this._elementType);
    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        this.setDataRecover();
        this.setConstraint();
        this.setBoundary();


        g.setColor(_data.getDataManager().getColorLine());
        this._recMagnet.draw(g, tran);

    }


    public boolean checkSelfConstraint(boolean ifSet)
    {
        boolean isLimit = false;
        double xS = this._startPt.X();
        double yS = this._startPt.Y();
        double xE = this._endPt.X();
        double yE = this._endPt.Y();
        double xL = this._leftDownPt.X();
        double yL = this._leftDownPt.Y();
        double xR = this._rightUpPt.X();
        double yR = this._rightUpPt.Y();

        double oldLeftBound;
        double oldRightBound;
        double oldUpBound;
        double oldDownBound;

        if (this._recMagnet == null)
        {
            oldLeftBound = xS;
            oldRightBound = xE;
            oldUpBound = yS;
            oldDownBound = yE;
        }
        else
        {
            oldLeftBound = this._recMagnet.getLeftBound();
            oldRightBound = this._recMagnet.getRightBound();
            oldUpBound = this._recMagnet.getUpBound();
            oldDownBound = this._recMagnet.getDownBound();
        }

        if (xS != oldLeftBound || yS != oldUpBound) //移動左上點
        {
            if (yS < oldDownBound + 0.03)
            {
                yS = oldDownBound + 0.03;
                isLimit = true;
            }

            if (this._elementType != this.TYPE_MAGNETOUTER)
                xS = 0;
            else
            {
                if (xS > oldRightBound - 0.03)
                    xS = oldRightBound - 0.03;
            }
            xL = xS;
            yR = yS;
        }
        if (xL != oldLeftBound || yL != oldDownBound) //移動左下點
        {
            if (yL > oldUpBound - 0.03)
            {
                yL = oldUpBound - 0.03;
                isLimit = true;
            }

            if (this._elementType != this.TYPE_MAGNETOUTER)
                xL = 0;
            else
            {
                if (xL > oldRightBound - 0.03)
                    xL = oldRightBound - 0.03;
            }

            xS = xL;
            yE = yL;
        }
        if (xR != oldRightBound || yR != oldUpBound) //移動右上點
        {

            if (xR < 0 + 0.03) //oldLeftBound
            {
                xR = 0 + 0.03;
                isLimit = true;
            }

            if (yR < oldDownBound + 0.03)
            {
                yR = oldDownBound + 0.03;
                isLimit = true;
            }

            xE = xR;
            yS = yR;
        }
        if (xE != oldRightBound || yE != oldDownBound) //移動右下點
        {
            if (xE < 0 + 0.03)
            {
                xE = 0 + 0.03;
                isLimit = true;
            }

            if (yE > oldUpBound - 0.03)
            {
                yE = oldUpBound - 0.03;
                isLimit = true;
            }

            xR = xE;
            yL = yE;
        }

        if(ifSet == true)
        {
            this._startPt.setCoordinate(xS, yS);
            this._leftDownPt.setCoordinate(xL, yL);
            this._rightUpPt.setCoordinate(xR, yR);
            this._endPt.setCoordinate(xE, yE);
        }

        return isLimit;
    }


    int callCounts = 1;

    public boolean setBoundary()
    {
        //System.out.println(this._elementType +"   " +  callCounts);

        callCounts++;

        double xS = this._startPt.X();
        double yS = this._startPt.Y();
        double xE = this._endPt.X();
        double yE = this._endPt.Y();
        double xL = this._leftDownPt.X();
        double yL = this._leftDownPt.Y();
        double xR = this._rightUpPt.X();
        double yR = this._rightUpPt.Y();

        double oldLeftBound;
        double oldRightBound;
        double oldUpBound;
        double oldDownBound;

        if (this._recMagnet == null)
        {
            oldLeftBound = xS;
            oldRightBound = xE;
            oldUpBound = yS;
            oldDownBound = yE;
        }
        else
        {
            oldLeftBound = this._recMagnet.getLeftBound();
            oldRightBound = this._recMagnet.getRightBound();
            oldUpBound = this._recMagnet.getUpBound();
            oldDownBound = this._recMagnet.getDownBound();
        }


        boolean isLimit = checkSelfConstraint(true);


        //被其他element限制的constraint
        /*
        if (this._elementType == this.TYPE_MAGNET)
        {
            ArrayList adjacentElement = this.getLinkedElementArray();
            for (int i = 0; i < adjacentElement.size(); i++)
            {
                ElementBase element = (ElementBase) adjacentElement.get(i);

                if (element._elementType == this.TYPE_TOPPLATE)
                {
                    if (((ElementMagnet) element).checkSelfConstraint(false) == true)
                    {
                        this._startPt.setCoordinate(this._startPt.X(), oldUpBound);
                        this._rightUpPt.setCoordinate(this._rightUpPt.X(), oldUpBound);
                    }
                }
                if (element._elementType == this.TYPE_YOKEBASE)
                {
                    if (((ElementYoke) element).checkSelfConstraint(false) == true)
                    {
                        this._endPt.setCoordinate(this._endPt.X(), oldDownBound);
                        this._leftDownPt.setCoordinate(this._leftDownPt.X(), oldDownBound);
                    }
                }
            }
        }

        //被其他element限制的constraint
        if (this._elementType == this.TYPE_TOPPLATE)
        {
            ArrayList adjacentElement = this.getLinkedElementArray();
            for (int i = 0; i < adjacentElement.size(); i++)
            {
                ElementBase element = (ElementBase) adjacentElement.get(i);

                if (element._elementType == this.TYPE_MAGNETTOP)
                {
                    DataMagnetTop dataMagnetTop = (DataMagnetTop)((ElementMagnet)element)._data;
                    if(dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
                    {
                        if (((ElementMagnet) element).checkSelfConstraint(false) == true)
                        {
                            this._startPt.setCoordinate(this._startPt.X(), oldUpBound);
                            this._rightUpPt.setCoordinate(this._rightUpPt.X(), oldUpBound);
                        }
                    }
                    else
                    {
                        ((ElementMagnet)element)._startPt.move(0, this._startPt.Y() - oldUpBound);
                        ((ElementMagnet)element)._endPt.move(0, this._startPt.Y() - oldUpBound);
                        ((ElementMagnet)element)._rightUpPt.move(0, this._startPt.Y() - oldUpBound);

                    }
                }
                if (element._elementType == this.TYPE_MAGNET)
                {
                    if (((ElementMagnet)element).checkSelfConstraint(false) == true)
                    {
                        this._endPt.setCoordinate(this._endPt.X(), oldDownBound);
                        this._leftDownPt.setCoordinate(this._leftDownPt.X(), oldDownBound);
                    }
                }
            }
        }

        //被其他element限制的constraint
        if (this._elementType == this.TYPE_MAGNETTOP)
        {
            ArrayList adjacentElement = this.getLinkedElementArray();
            for (int i = 0; i < adjacentElement.size(); i++)
            {
                ElementBase element = (ElementBase) adjacentElement.get(i);

                if (element._elementType == this.TYPE_TOPPLATE)
                {
                    if (((ElementMagnet) element).checkSelfConstraint(false) == true)
                    {
                        this._endPt.setCoordinate(this._endPt.X(), oldDownBound);
                        this._leftDownPt.setCoordinate(this._leftDownPt.X(), oldDownBound);
                    }
                }
            }
        }*/



        ObjectPoint objStartPt = new ObjectPoint(this._startPt.X(), this._startPt.Y());
        ObjectPoint objEndPt = new ObjectPoint(this._endPt.X(), this._endPt.Y());


        if (this._recMagnet == null)
        {
            this._recMagnet = new ElementRectangle(objEndPt, objStartPt, this.color, _data);
        }
        else
        {
            this._recMagnet.setStartEndPt(objStartPt, objEndPt);
         }

        this.rec.setLocation(this._startPt.X(), this._startPt.Y());
        this.rec.width = Math.abs(this._endPt.X() - this._startPt.X());
        this.rec.height = Math.abs(this._startPt.Y() - this._endPt.Y());


        return isLimit;

    }



    public void setConstraint()
    {
    }

    public boolean isPtInBoundary(ObjectPoint point)
    {
        if (this._recMagnet == null)
            return false;
        if (this._recMagnet.isPtInBoundary(point) == true)
            return true;
        else
            return false;
    }

    public void setColor(Color c)
    {
        this.color = c;
        if (this._recMagnet != null)
            this._recMagnet.setColor(this.color);
    }

    public void move(double dx, double dy)
    {
        double oldStartPtX = this._startPt.X();
        double oldStartPtY = this._startPt.Y();
        double oldEndPtX = this._endPt.X();
        double oldEndPtY = this._endPt.Y();
        double oldLeftDownPtX = this._leftDownPt.X();
        double oldLeftDownPtY = this._leftDownPt.Y();
        double oldRightUpPtX = this._rightUpPt.X();
        double oldRightUpPtY = this._rightUpPt.Y();

        if (this._elementType == this.TYPE_TOPPLATE)
        {
            this._startPt.move(0, dy);
            this._endPt.move(0, dy);
            this._leftDownPt.move(0, dy);
            this._rightUpPt.move(0, dy);
            this._recMagnet.move(0, dy);
            this.rec.translate(0, dy);

            ArrayList adjacentElement = this.getLinkedElementArray();
            for (int i = 0; i < adjacentElement.size(); i++)
            {
                ElementBase element = (ElementBase) adjacentElement.get(i);
                if (element._elementType == this.TYPE_MAGNETTOP)
                {
                    DataMagnetTop dataMagnetTop = (DataMagnetTop) ((ElementMagnet) element)._data;
                    if (dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
                    {
                        if (((ElementMagnet) element).setBoundary() == true)
                        {
                            this._startPt.setCoordinate(oldStartPtX, oldStartPtY);
                            this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                            this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                            this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);

                            ObjectPoint spt = this._startPt.point;
                            ObjectPoint ept = this._endPt.point;
                            this._recMagnet.setStartEndPt(spt, ept);
                            this.rec.translate( 0, -dy);
                        }
                    }
                    else //移動Top Plate的時候不受到Magnet的影響
                    {
                        ((ElementMagnet)element)._startPt.move(0, dy);
                        ((ElementMagnet)element)._endPt.move(0, dy);
                        ((ElementMagnet)element)._rightUpPt.move(0, dy);
                        ((ElementMagnet)element)._leftDownPt.move(0, dy);
                        //((ElementMagnet)element)._recMagnet.move(0, dy);
                    }
                }
                else if (element._elementType == this.TYPE_MAGNET)
                {
                    if (((ElementMagnet) element).setBoundary() == true)
                    {
                        this._startPt.setCoordinate(oldStartPtX, oldStartPtY);

                        this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                        this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                        this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);

                        ObjectPoint spt = this._startPt.point;
                        ObjectPoint ept = this._endPt.point;
                        this._recMagnet.setStartEndPt(spt, ept);
                        this.rec.translate( 0, -dy);
                    }
                }
            }
        }
        else if (this._elementType == this.TYPE_MAGNET || this._elementType == this.TYPE_MAGNETTOP)
        {
            this._startPt.move(0, dy);
            this._endPt.move(0, dy);
            this._leftDownPt.move(0, dy);
            this._rightUpPt.move(0, dy);
            this._recMagnet.move(0, dy);
            this.rec.translate(0, dy);

            ArrayList adjacentElement = this.getLinkedElementArray();
            for (int i = 0; i < adjacentElement.size(); i++)
            {
                ElementBase element = (ElementBase) adjacentElement.get(i);
                if (element._elementType == this.TYPE_TOPPLATE)
                {

                    if (((ElementMagnet) element).setBoundary() == true)
                    {
                        this._startPt.setCoordinate(oldStartPtX, oldStartPtY);

                        this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                        this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                        this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);

                        ObjectPoint spt = this._startPt.point;
                        ObjectPoint ept = this._endPt.point;
                        this._recMagnet.setStartEndPt(spt, ept);
                        this.rec.translate( 0, -dy);
                    }
                }
                else if (element._elementType == this.TYPE_YOKEBASE)
                {
                    if (((ElementYoke) element).setBoundary() == true)
                    {
                        this._startPt.setCoordinate(oldStartPtX, oldStartPtY);
                        this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                        this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                        this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);

                        ObjectPoint spt = this._startPt.point;
                        ObjectPoint ept = this._endPt.point;
                        this._recMagnet.setStartEndPt(spt, ept);
                        this.rec.translate( 0, -dy);
                    }
                }
            }
        }
        else if(this._elementType == this.TYPE_MAGNETOUTER)
        {
            this._startPt.move(dx, dy);
            this._endPt.move(dx, dy);
            this._leftDownPt.move(dx, dy);
            this._rightUpPt.move(dx, dy);
            this._recMagnet.move(dx, dy);
            this.rec.translate(dx, dy);


            ArrayList adjacentElement = this.getLinkedElementArray();
            for (int i = 0; i < adjacentElement.size(); i++)
            {
                ElementBase element = (ElementBase) adjacentElement.get(i);

                if (element._elementType == this.TYPE_YOKESTAGE1)
                {
                    if (((ElementYoke) element).setBoundary() == true)
                    {
                        this._startPt.setCoordinate(oldStartPtX, oldStartPtY);
                        this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                        this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                        this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);

                        ObjectPoint spt = this._startPt.point;
                        ObjectPoint ept = this._endPt.point;
                        this._recMagnet.setStartEndPt(spt, ept);
                        this.rec.translate( -dx, -dy);
                    }
                }
            }
        }
    }
}
