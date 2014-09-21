package sdt.geometry.element;

import java.awt.*;

import sdt.data.*;
import sdt.geometry.*;
import sdt.panel.drawpanel.*;
import java.util.ArrayList;
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
public class ElementYoke extends ElementCommon
{
    public ElementPoint _startPt;
    public ElementPoint _endPt;
    public ElementPoint _leftDownPt;
    public ElementPoint _rightUpPt;
    public ElementPoint _MiddleUpPt;
    public ElementPoint _MiddleDownPt;

    public ElementLine _lineSM;
    public ElementLine _lineMM;
    public ElementLine _lineMR;
    public ElementLine _lineRE;
    public ElementLine _lineEL;
    public ElementLine _lineLS;
    public ElementRectangle _recHorizontal;
    public ElementRectangle _recVertical;


    public ElementYoke(DataYoke data, int viewType, Color c)
    {
        super(c, data, viewType);

        this._startPt = data.getElementPointStart(_viewType);
        this._endPt = data.getElementPointEnd(_viewType);
        this._leftDownPt = data.getElementPointLeftDown(viewType);
        this._rightUpPt = data.getElementPointRightUp(viewType);
        this._MiddleUpPt = data.getElementPointMiddleUp(viewType);
        this._MiddleDownPt = data.getElementPointMiddleDown(viewType);

        this._elementType = this.TYPE_YOKE;
    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        this.setDataRecover();
        this.setConstraint();
        this.setBoundary();

        Polygon poly = new Polygon();
         int pt1[] = tran.setPointRealToShow(_startPt.X(), _startPt.Y());
         int pt2[] = tran.setPointRealToShow(_MiddleDownPt.X(), _MiddleDownPt.Y());
         int pt3[] = tran.setPointRealToShow(_MiddleUpPt.X(), _MiddleUpPt.Y());
         int pt4[] = tran.setPointRealToShow(_rightUpPt.X(), _rightUpPt.Y());
         int pt5[] = tran.setPointRealToShow(_endPt.X(), _endPt.Y());
         int pt6[] = tran.setPointRealToShow(_leftDownPt.X(), _leftDownPt.Y());

         g.setColor(this.color);
         poly.addPoint(pt1[0], pt1[1]);
         poly.addPoint(pt2[0], pt2[1]);
         poly.addPoint(pt3[0], pt3[1]);
         poly.addPoint(pt4[0], pt4[1]);
         poly.addPoint(pt5[0], pt5[1]);
         poly.addPoint(pt6[0], pt6[1]);




         g.fillPolygon(poly);

         Graphics2D g2d = (Graphics2D)g;
         g2d.setStroke(_data.getDataManager().getStrokeLine());
         g.setColor(_data.getDataManager().getColorLine());

         g.drawLine(pt1[0],pt1[1],pt2[0],pt2[1]);
         g.drawLine(pt2[0],pt2[1],pt3[0],pt3[1]);
         g.drawLine(pt3[0],pt3[1],pt4[0],pt4[1]);
         g.drawLine(pt4[0],pt4[1],pt5[0],pt5[1]);
         g.drawLine(pt5[0],pt5[1],pt6[0],pt6[1]);
         g.drawLine(pt6[0],pt6[1],pt1[0],pt1[1]);

         /*this._lineSM.draw(g, tran);
         this._lineMM.draw(g, tran);
         this._lineMR.draw(g, tran);
         this._lineRE.draw(g, tran);
         this._lineEL.draw(g, tran);
         this._lineLS.draw(g, tran);
*/
    }

    public boolean checkSelfConstraint(boolean ifSet)
    {
        boolean isLimit = false;
        double xS = this._startPt.X();
        double yS = this._startPt.Y();
        double xE = this._endPt.X();
        double yE = this._endPt.Y();
        double xM = this._MiddleUpPt.X();
        double yM = this._MiddleUpPt.Y();
        double xMd = this._MiddleDownPt.X();
        double yMd = this._MiddleDownPt.Y();

        double xL = this._leftDownPt.X();
        double yL = this._leftDownPt.Y();
        double xR = this._rightUpPt.X();
        double yR = this._rightUpPt.Y();

        double oldLeftBound;
        double oldRightBound;
        double oldUpBound;
        double oldDownBound;
        double oldMiddleHorizontal;
        double oldMiddleVertical;



        if (this._recHorizontal == null || this._recVertical == null)
        {
            oldLeftBound = xS;
            oldRightBound = xE;
            oldMiddleVertical = xM;

            oldUpBound = yS;
            oldDownBound = yE;
            oldMiddleHorizontal = yMd;
        }
        else
        {
            oldLeftBound = this._recHorizontal.getLeftBound();
            oldRightBound = this._recVertical.getRightBound();
            oldMiddleVertical = this._recVertical.getLeftBound();

            oldUpBound = this._recVertical.getUpBound();
            oldDownBound = this._recHorizontal.getDownBound();
            oldMiddleHorizontal = this._recHorizontal.getUpBound();
        }

        if (xS != oldLeftBound || yS != oldMiddleHorizontal) //移動 Start Pt
        {
            if (this._elementType == this.TYPE_YOKEBASE)
                xS = 0;
            else if (xS > oldMiddleVertical - 0.03)
            {
                xS = oldMiddleVertical - 0.03;
                isLimit = true;
            }

            if (yS > oldUpBound - 0.03)
            {
                yS = oldUpBound - 0.03;
                isLimit = true;
            }
            else if (yS < oldDownBound + 0.03)
            {
                yS = oldDownBound + 0.03;
                isLimit = true;
            }

            xL = xS;
            yMd = yS;
        }

        if (xL != oldLeftBound || yL != oldDownBound) //移動 LeftDown Pt
        {
            if (this._elementType == this.TYPE_YOKEBASE)
                xL = 0;
            else if (xL > oldMiddleVertical - 0.03)
            {
                xL = oldMiddleVertical - 0.03;
                isLimit = true;
            }

            if (yL > oldMiddleHorizontal - 0.03)
            {
                yL = oldMiddleHorizontal - 0.03;
                isLimit = true;
            }

            xS = xL;
            yE = yL;

        }

        if (xM != oldMiddleVertical || yM != oldUpBound) //移動 MiddleUp Pt
        {
            if (xM < oldLeftBound + 0.03)
            {
                xM = oldLeftBound + 0.03;
                isLimit = true;
            }
            else if (xM > oldRightBound - 0.03)
            {
                xM = oldRightBound - 0.03;
                isLimit = true;
            }

            if (yM < oldMiddleHorizontal + 0.03)
            {
                yM = oldMiddleHorizontal + 0.03;
                isLimit = true;
            }

            xMd = xM;
            yR = yM;

        }

        if (xMd != oldMiddleVertical || yMd != oldMiddleHorizontal) //移動 MiddleDown Pt
        {
            if (xMd < oldLeftBound + 0.03)
            {
                xMd = oldLeftBound + 0.03;
                isLimit = true;
            }
            else if (xMd > oldRightBound - 0.03)
            {
                xMd = oldRightBound - 0.03;
                isLimit = true;
            }

            if (yMd < oldDownBound + 0.03)
            {
                yMd = oldDownBound + 0.03;
                isLimit = true;
            }
            else if (yMd > oldUpBound - 0.03)
            {
                yMd = oldUpBound - 0.03;
                isLimit = true;
            }

            xM = xMd;
            yS = yMd;

        }

        if (xR != oldRightBound || yR != oldUpBound) //移動 RightUp Pt
        {
            if (xR < oldMiddleVertical + 0.03)
            {
                xR = oldMiddleVertical + 0.03;
                isLimit = true;
            }

            if (yR < oldMiddleHorizontal + 0.03)
            {
                yR = oldMiddleHorizontal + 0.03;
                isLimit = true;
            }

            xE = xR;
            yM = yR;
        }

        if (xE != oldRightBound || yE != oldDownBound) //移動 End Pt
        {
            if (xE < oldMiddleVertical + 0.03)
            {
                xE = oldMiddleVertical + 0.03;
                isLimit = true;
            }

            if (yE > oldMiddleHorizontal - 0.03)
            {
                yE = oldMiddleHorizontal - 0.03;
                isLimit = true;
            }

            xR = xE;
            yL = yE;

        }

        if (ifSet == true)
        {
            this._startPt.setCoordinate(xS, yS);
            this._endPt.setCoordinate(xE, yE);
            this._MiddleUpPt.setCoordinate(xM, yM);

            this._leftDownPt.setCoordinate(xL, yL);
            this._rightUpPt.setCoordinate(xR, yR);
            this._MiddleDownPt.setCoordinate(xMd, yMd);
        }

        return isLimit;
    }


    public boolean setBoundary()
    {
        double xS = this._startPt.X();
        double yS = this._startPt.Y();
        double xE = this._endPt.X();
        double yE = this._endPt.Y();
        double xM = this._MiddleUpPt.X();
        double yM = this._MiddleUpPt.Y();
        double xMd = this._MiddleDownPt.X();
        double yMd = this._MiddleDownPt.Y();

        double xL = this._leftDownPt.X();
        double yL = this._leftDownPt.Y();
        double xR = this._rightUpPt.X();
        double yR = this._rightUpPt.Y();

        double oldLeftBound;
        double oldRightBound;
        double oldUpBound;
        double oldDownBound;
        double oldMiddleHorizontal;
        double oldMiddleVertical;

        if (this._recHorizontal == null || this._recVertical == null)
        {
            oldLeftBound = xS;
            oldRightBound = xE;
            oldMiddleVertical = xM;

            oldUpBound = yM;
            oldDownBound = yE;
            oldMiddleHorizontal = yMd;
        }
        else
        {
            oldLeftBound = this._recHorizontal.getLeftBound();
            oldRightBound = this._recVertical.getRightBound();
            oldMiddleVertical = this._recVertical.getLeftBound();

            oldUpBound = this._recVertical.getUpBound();
            oldDownBound = this._recHorizontal.getDownBound();
            oldMiddleHorizontal = this._recHorizontal.getUpBound();
        }

        boolean isLimit = this.checkSelfConstraint(true);


       //被其他element限制的constraint
       if (this._elementType == this.TYPE_YOKESTAGE2)
       {
           ArrayList adjacentElement = this.getLinkedElementArray();
           for (int i = 0; i < adjacentElement.size(); i++)
           {
               ElementBase element = (ElementBase) adjacentElement.get(i);

               if (element._elementType == this.TYPE_YOKESTAGE1)
               {
                   if (((ElementYoke) element).checkSelfConstraint(false) == true)
                   {
                       this._startPt.setCoordinate(oldLeftBound, this._startPt.Y());
                       this._leftDownPt.setCoordinate(oldLeftBound, oldDownBound);
                       this._endPt.setCoordinate(this._endPt.X(), oldDownBound);
                       this._MiddleUpPt.setCoordinate(oldMiddleVertical, oldUpBound);// add
                       this._MiddleDownPt.setCoordinate(oldMiddleVertical, oldMiddleHorizontal);// add
                       this._rightUpPt.setCoordinate(oldRightBound, oldUpBound);// add
                   }
               }
           }
       }


       //被其他element限制的constraint
       if (this._elementType == this.TYPE_YOKESTAGE1)
       {
           ArrayList adjacentElement = this.getLinkedElementArray();
           for (int i = 0; i < adjacentElement.size(); i++)
           {
               ElementBase element = (ElementBase) adjacentElement.get(i);

               if (element._elementType == this.TYPE_YOKESTAGE2)
               {
                   DataYokeStage2 dataYokeStage2 = (DataYokeStage2)((ElementYoke)element)._data;
                   if(dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
                   {
                       if (((ElementYoke) element).checkSelfConstraint(false) == true)
                       {
                           this._MiddleUpPt.setCoordinate(oldMiddleVertical, oldUpBound);
                           this._MiddleDownPt.setCoordinate(oldMiddleVertical, this._MiddleDownPt.Y());
                           this._rightUpPt.setCoordinate(this._rightUpPt.X(), oldUpBound);
                           this._startPt.setCoordinate(oldLeftBound, oldMiddleHorizontal);// add
                           this._leftDownPt.setCoordinate(oldLeftBound, oldDownBound);// add
                           this._endPt.setCoordinate(oldRightBound, oldDownBound);// add
                       }
                   }
                   else
                   {
                       ((ElementYoke)element)._startPt.     move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);
                       ((ElementYoke)element)._endPt.       move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);
                       ((ElementYoke)element)._MiddleUpPt.  move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);
                       ((ElementYoke)element)._MiddleDownPt.move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);
                       ((ElementYoke)element)._rightUpPt.   move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);

                   }
               }
               if (element._elementType == this.TYPE_YOKEBASE)
               {
                   if (((ElementYoke) element).checkSelfConstraint(false) == true)
                   {
                       this._startPt.setCoordinate(oldLeftBound, this._startPt.Y());
                       this._leftDownPt.setCoordinate(oldLeftBound, oldDownBound);
                       this._endPt.setCoordinate(this._endPt.X(), oldDownBound);
                       this._MiddleUpPt.setCoordinate(oldMiddleVertical, oldUpBound);// add
                       this._MiddleDownPt.setCoordinate(oldMiddleVertical, oldMiddleHorizontal);// add
                       this._rightUpPt.setCoordinate(oldRightBound, oldUpBound);// add
                   }
               }

           }
       }

       //被其他element限制的constraint
       if (this._elementType == this.TYPE_YOKEBASE)
       {
           ArrayList adjacentElement = this.getLinkedElementArray();
           for (int i = 0; i < adjacentElement.size(); i++)
           {
               ElementBase element = (ElementBase) adjacentElement.get(i);

               if (element._elementType == this.TYPE_YOKESTAGE1)
               {
                   DataYokeStage1 dataYokeStage1 = (DataYokeStage1) ((ElementYoke) element)._data;
                   if (dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
                   {
                       if (((ElementYoke) element).checkSelfConstraint(false) == true)
                       {
                           this._MiddleUpPt.setCoordinate(oldMiddleVertical, oldUpBound);
                           this._MiddleDownPt.setCoordinate(oldMiddleVertical, this._MiddleDownPt.Y());
                           this._rightUpPt.setCoordinate(this._rightUpPt.X(), oldUpBound);
                           this._startPt.setCoordinate(oldLeftBound, oldMiddleHorizontal); // add
                           this._leftDownPt.setCoordinate(oldLeftBound, oldDownBound); // add
                           this._endPt.setCoordinate(oldRightBound, oldDownBound); // add


                       }
                   }
                   else
                   {
                       ((ElementYoke)element)._startPt.     move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);
                       ((ElementYoke)element)._endPt.       move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);
                       ((ElementYoke)element)._MiddleUpPt.  move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);
                       ((ElementYoke)element)._MiddleDownPt.move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);
                       ((ElementYoke)element)._rightUpPt.   move(this._startPt.X() - oldLeftBound, this._MiddleUpPt.Y() - oldUpBound);

                   }
               }
               if (element._elementType == this.TYPE_MAGNET)
               {
                   if (((ElementMagnet) element).checkSelfConstraint(false) == true)
                   {
                       this._startPt.setCoordinate(0, oldMiddleHorizontal);
                       this._MiddleDownPt.setCoordinate(this._MiddleDownPt.X(), oldMiddleHorizontal);

                   }
               }
           }
       }




        ObjectPoint objStartPt = new ObjectPoint(this._startPt.X(), this._startPt.Y());
        ObjectPoint objEndPt = new ObjectPoint(this._endPt.X(), this._endPt.Y());
        ObjectPoint objMiddleUpPt = new ObjectPoint(this._MiddleUpPt.X(), this._MiddleUpPt.Y());
        ObjectPoint objMiddleDownPt = new ObjectPoint(this._MiddleDownPt.X(), this._MiddleDownPt.Y());
        ObjectPoint objLeftDownPt = new ObjectPoint(this._leftDownPt.X(), this._leftDownPt.Y());
        ObjectPoint objRightUpPt = new ObjectPoint(this._rightUpPt.X(), this._rightUpPt.Y());

        if (this._recHorizontal == null)
        {
            this._recHorizontal = new ElementRectangle(objStartPt, objEndPt, this.color, _data);
        }
        else
        {
            this._recHorizontal.setStartEndPt(objStartPt, objEndPt);
        }

        if (this._recVertical == null)
        {
            this._recVertical = new ElementRectangle(objMiddleUpPt, objEndPt, this.color, _data);
        }
        else
        {
            this._recVertical.setStartEndPt(objMiddleUpPt, objEndPt);
        }

        if (this._lineSM == null)
        {
            this._lineSM = new ElementLine(objStartPt, objMiddleDownPt, this.color, _data);
        }
        else
        {
            this._lineSM.setStartEndPt(objStartPt, objMiddleDownPt);
        }

        if (this._lineMM == null)
        {
            this._lineMM = new ElementLine(objMiddleDownPt, objMiddleUpPt, this.color, _data);
        }
        else
        {
            this._lineMM.setStartEndPt(objMiddleDownPt, objMiddleUpPt);
        }

        if (this._lineMR == null)
        {
            this._lineMR = new ElementLine(objMiddleUpPt, objRightUpPt, this.color, _data);
        }
        else
        {
            this._lineMR.setStartEndPt(objMiddleUpPt, objRightUpPt);
        }

        if (this._lineRE == null)
        {
            this._lineRE = new ElementLine(objRightUpPt, objEndPt, this.color, _data);
        }
        else
        {
            this._lineRE.setStartEndPt(objRightUpPt, objEndPt);
        }

        if (this._lineEL == null)
        {
            this._lineEL = new ElementLine(objEndPt, objLeftDownPt, this.color, _data);
        }
        else
        {
            this._lineEL.setStartEndPt(objEndPt, objLeftDownPt);
        }

        if (this._lineLS == null)
        {
            this._lineLS = new ElementLine(objLeftDownPt, objStartPt, this.color, _data);
        }
        else
        {
            this._lineLS.setStartEndPt(objLeftDownPt, objStartPt);
        }

        this.rec.setLocation(this._startPt.X(), this._MiddleUpPt.Y());
        this.rec.width = Math.abs(this._endPt.X() - this._startPt.X());
        this.rec.height = Math.abs(this._MiddleUpPt.Y() - this._endPt.Y());

        return isLimit;
    }

    public void setConstraint()
    {
    }

    public boolean isPtInBoundary(ObjectPoint point)
    {
        if (this._recVertical == null || this._recHorizontal == null)
        {
            return false;
        }
        if (this._recVertical.isPtInBoundary(point) == true || this._recHorizontal.isPtInBoundary(point))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setColor(Color c)
    {
        this.color = c;
        if (this._lineSM != null)
        {
            this._lineSM.setColor(this.color);
            this._lineMM.setColor(this.color);
            this._lineMR.setColor(this.color);
            this._lineRE.setColor(this.color);
            this._lineEL.setColor(this.color);
            this._lineLS.setColor(this.color);
        }
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
        double oldMiddleUpPtX = this._MiddleUpPt.X();
        double oldMiddleUpPtY = this._MiddleUpPt.Y();
        double oldMiddleDownPtX = this._MiddleDownPt.X();
        double oldMiddleDownPtY = this._MiddleDownPt.Y();


        if(this._elementType == this.TYPE_YOKEBASE)
        {
            this._startPt.move(0, dy);
            this._endPt.move(0, dy);
            this._leftDownPt.move(0, dy);
            this._rightUpPt.move(0, dy);
            this._MiddleUpPt.move(0, dy);
            this._MiddleDownPt.move(0, dy);
            this._recHorizontal.move(0, dy);
            this._recVertical.move(0, dy);

            ArrayList adjacentElement = this.getLinkedElementArray();
            for (int i = 0; i < adjacentElement.size(); i++)
            {
                ElementBase element = (ElementBase) adjacentElement.get(i);
                if (element._elementType == this.TYPE_YOKESTAGE1)
                {
                    DataYokeStage1 dataYokeStage1 = (DataYokeStage1) ((ElementYoke) element)._data;
                    if (dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
                    {
                        //this.setBoundary();

                        if (((ElementYoke) element).setBoundary() == true)
                        {
                            this._startPt.setCoordinate(oldStartPtX, oldStartPtY);
                            this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                            this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                            this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);
                            this._MiddleUpPt.setCoordinate(oldMiddleUpPtX, oldMiddleUpPtY);
                            this._MiddleDownPt.setCoordinate(oldMiddleDownPtX, oldMiddleDownPtY);

                            ObjectPoint spt = this._startPt.point;
                            ObjectPoint ept = this._endPt.point;
                            ObjectPoint mpt = this._MiddleUpPt.point;

                            this._recHorizontal.setStartEndPt(spt, ept);
                            this._recVertical.setStartEndPt(mpt, ept);

                            this.rec.translate( -dx, -dy);
                        }
                    }
                }
                else if(element._elementType == this.TYPE_MAGNET)
                {
                    //this.setBoundary();

                    if (((ElementMagnet) element).setBoundary() == true)
                    {
                        this._startPt.setCoordinate(oldStartPtX, oldStartPtY);
                        this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                        this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                        this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);
                        this._MiddleUpPt.setCoordinate(oldMiddleUpPtX, oldMiddleUpPtY);
                        this._MiddleDownPt.setCoordinate(oldMiddleDownPtX, oldMiddleDownPtY);

                        ObjectPoint spt = this._startPt.point;
                        ObjectPoint ept = this._endPt.point;
                        ObjectPoint mpt = this._MiddleUpPt.point;

                        this._recHorizontal.setStartEndPt(spt, ept);
                        this._recVertical.setStartEndPt(mpt, ept);

                        this.rec.translate( -dx, -dy);
                    }
                }
            }
        }
        else if(this._elementType == this.TYPE_YOKESTAGE1)
        {
            this._startPt.move(dx, dy);
            this._endPt.move(dx, dy);
            this._leftDownPt.move(dx, dy);
            this._rightUpPt.move(dx, dy);
            this._MiddleUpPt.move(dx, dy);
            this._MiddleDownPt.move(dx, dy);
            this._recHorizontal.move(dx, dy);
            this._recVertical.move(dx, dy);

            ArrayList adjacentElement = this.getLinkedElementArray();
            for (int i = 0; i < adjacentElement.size(); i++)
            {
                ElementBase element = (ElementBase) adjacentElement.get(i);
                if(element._elementType == this.TYPE_YOKEBASE)
                {
                    if (((ElementYoke) element).setBoundary() == true)
                       {
                           this._startPt.setCoordinate(oldStartPtX, oldStartPtY);
                           this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                           this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                           this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);
                           this._MiddleUpPt.setCoordinate(oldMiddleUpPtX, oldMiddleUpPtY);
                           this._MiddleDownPt.setCoordinate(oldMiddleDownPtX, oldMiddleDownPtY);

                           ObjectPoint spt = this._startPt.point;
                           ObjectPoint ept = this._endPt.point;
                           ObjectPoint mpt = this._MiddleUpPt.point;

                           this._recHorizontal.setStartEndPt(spt, ept);
                           this._recVertical.setStartEndPt(mpt, ept);

                           this.rec.translate( -dx, -dy);
                       }
                }
                else if (element._elementType == this.TYPE_YOKESTAGE2)
                {
                    //this.setBoundary();
                    DataYokeStage2 dataYokeStage2 = (DataYokeStage2) ((ElementYoke) element)._data;
                    if (dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
                    {
                        if (((ElementYoke) element).setBoundary() == true)
                        {
                            this._startPt.setCoordinate(oldStartPtX, oldStartPtY);
                            this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                            this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                            this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);
                            this._MiddleUpPt.setCoordinate(oldMiddleUpPtX, oldMiddleUpPtY);
                            this._MiddleDownPt.setCoordinate(oldMiddleDownPtX, oldMiddleDownPtY);

                            ObjectPoint spt = this._startPt.point;
                            ObjectPoint ept = this._endPt.point;
                            ObjectPoint mpt = this._MiddleUpPt.point;

                            this._recHorizontal.setStartEndPt(spt, ept);
                            this._recVertical.setStartEndPt(mpt, ept);

                            this.rec.translate( -dx, -dy);
                        }
                    }
                }
            }
        }
        else if (this._elementType == this.TYPE_YOKESTAGE2)
        {
            this._startPt.move(dx, dy);
            this._endPt.move(dx, dy);
            this._leftDownPt.move(dx, dy);
            this._rightUpPt.move(dx, dy);
            this._MiddleUpPt.move(dx, dy);
            this._MiddleDownPt.move(dx, dy);
            this._recHorizontal.move(dx, dy);
            this._recVertical.move(dx, dy);

            ArrayList adjacentElement = this.getLinkedElementArray();
            for (int i = 0; i < adjacentElement.size(); i++)
            {
                ElementBase element = (ElementBase) adjacentElement.get(i);
                if (element._elementType == this.TYPE_YOKESTAGE1)
                {
                    //this.setBoundary();

                    if (((ElementYoke) element).setBoundary() == true)
                    {
                        this._startPt.setCoordinate(oldStartPtX, oldStartPtY);
                        this._endPt.setCoordinate(oldEndPtX, oldEndPtY);
                        this._leftDownPt.setCoordinate(oldLeftDownPtX, oldLeftDownPtY);
                        this._rightUpPt.setCoordinate(oldRightUpPtX, oldRightUpPtY);
                        this._MiddleUpPt.setCoordinate(oldMiddleUpPtX, oldMiddleUpPtY);
                        this._MiddleDownPt.setCoordinate(oldMiddleDownPtX, oldMiddleDownPtY);

                        ObjectPoint spt = this._startPt.point;
                        ObjectPoint ept = this._endPt.point;
                        ObjectPoint mpt = this._MiddleUpPt.point;

                        this._recHorizontal.setStartEndPt(spt, ept);
                        this._recVertical.setStartEndPt(mpt, ept);

                        this.rec.translate( -dx, -dy);
                    }
                }
            }
        }

    }
    }
