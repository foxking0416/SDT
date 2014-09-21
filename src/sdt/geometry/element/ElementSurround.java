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
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ElementSurround extends ElementCommon
{
    private ElementPoint _surroundInPt;
    private ElementPoint _surroundOutPt;
    private ElementPoint _oldSurroundInPt;
    private ElementPoint _oldSurroundOutPt;
    private ElementPoint _innerCenter;
    private ElementPoint _outerCenter;
    private ElementPoint _tangentPt;
    private ElementPoint _singleCenter;
    private ElementArc _innerArc;
    private ElementArc _outerArc;
    private ElementArc _singleArc;
    private ElementLine _innerLine;
    private ElementLine _middleLineIn;
    private ElementLine _middleLineOut;
    private ElementLine _outerLine;
    private ElementLine _singleInnerLine;
    private ElementLine _singleOuterLine;

    private double _radiusOut;
    private double _radiusIn;
    private double _hTotal;
    private double _radiusSingle;


    private DataSurround _dataSurround;
    private int _type;

    public ElementSurround(DataSurround dataSurround, int viewType, Color c)
    {
        super(c, dataSurround, viewType);
        this._dataSurround = dataSurround;
        this._surroundInPt = dataSurround.getElementPointStart(_viewType);
        this._surroundOutPt = dataSurround.getElementPointEnd(_viewType);
        this._radiusOut = dataSurround.getSurroundDoubleArcOuterRadius(_viewType);
        this._radiusIn = dataSurround.getSurroundDoubleArcInnerRadius(_viewType);
        this._radiusSingle = dataSurround.getSurroundSingleArcRadius(_viewType);
        this._hTotal = dataSurround.getSurroundHeight(_viewType);
        this._elementType = this.TYPE_SURROUND;

        this._oldSurroundInPt = new ElementPoint(this._surroundInPt.X(), this._surroundInPt.Y(), c,_data);
        this._oldSurroundOutPt = new ElementPoint(this._surroundOutPt.X(), this._surroundOutPt.Y(), c,_data);


        this._type = dataSurround.getGeometryType();


    }


    public void setElementColor(Color c)
    {
        this.color = c;
    }

    public Color getElementColor()
    {
        return this.color;
    }

    private boolean calculateSurround_DoubleArc(double hTransition, double surroundWidth, double hTotal, double innerRadius, double outerRadius)
    {
        double defaultInnerCenterPtY;
        double defaultInnerCenterPtX;
        double a;
        double b;
        double tempJudgeEquation;
        double defaultOuterCenterPtY;
        double defaultOuterCenterPtX;
        double defaultTangentPtX;
        double defaultTangentPtY;
        double dx;
        double dy;
        double theta;
        double thetaOutCenterToSurroundOut;
        double thetaInCenterToSurroundIn;
        boolean judgeRule=true;

        if(hTransition < 0)//modify @ 2012_06_07 important
            hTotal = hTotal + hTransition;

        //原式中的 C1y
        defaultInnerCenterPtY = hTotal - innerRadius;

        //原式中的C1x
        defaultInnerCenterPtX = Math.sqrt(Math.pow(innerRadius, 2) - Math.pow(hTransition, 2) - Math.pow(defaultInnerCenterPtY, 2) + 2 * hTransition * defaultInnerCenterPtY);

        a = (Math.pow(innerRadius,2) - 2*innerRadius*outerRadius - Math.pow(defaultInnerCenterPtX,2) - Math.pow(defaultInnerCenterPtY,2) + Math.pow(surroundWidth,2)) / (2*surroundWidth - 2*defaultInnerCenterPtX);
        b = 2*defaultInnerCenterPtY / (2*surroundWidth - 2*defaultInnerCenterPtX);
        tempJudgeEquation = Math.pow((2*a*b-2*surroundWidth*b),2) - 4*(Math.pow(b,2) + 1) * (Math.pow(surroundWidth - a, 2) - Math.pow(outerRadius,2));

        if(innerRadius <= outerRadius)
        {
            defaultOuterCenterPtY = (2*surroundWidth*b - 2*a*b - Math.sqrt(tempJudgeEquation)) / (2*(Math.pow(b,2) + 1));
            defaultOuterCenterPtX = a + b * defaultOuterCenterPtY;
            dx = defaultInnerCenterPtX - defaultOuterCenterPtX;
            dy = defaultInnerCenterPtY - defaultOuterCenterPtY;

        }
        else
        {
            defaultOuterCenterPtY = (2*surroundWidth*b - 2*a*b + Math.sqrt(tempJudgeEquation)) / (2*(Math.pow(b,2) + 1));
            defaultOuterCenterPtX = a + b * defaultOuterCenterPtY;
            dx = defaultOuterCenterPtX - defaultInnerCenterPtX;
            dy = defaultOuterCenterPtY - defaultInnerCenterPtY;
        }


        theta = this._dataSurround.calculateTheta(dx, dy);

        thetaOutCenterToSurroundOut = this._dataSurround.calculateTheta((surroundWidth - defaultOuterCenterPtX),  (0 - defaultOuterCenterPtY));
        thetaInCenterToSurroundIn = this._dataSurround.calculateTheta((0 - defaultInnerCenterPtX), (hTransition - defaultInnerCenterPtY));

        if(Math.pow(innerRadius, 2) - Math.pow(hTransition, 2) - Math.pow(defaultInnerCenterPtY, 2) + 2 * hTransition * defaultInnerCenterPtY < 0
                || tempJudgeEquation < 0 || theta > (Math.PI / 2.0)
                //|| thetaOutCenterToSurroundOut > (Math.PI / 2.0)
                //|| thetaInCenterToSurroundIn > Math.PI
                )
        {
            judgeRule = false;
        }


        if(judgeRule == false)
        {
            judgeRule = true;
            defaultOuterCenterPtY = hTotal - outerRadius;
            defaultOuterCenterPtX = surroundWidth - Math.sqrt(Math.pow(outerRadius,2) - Math.pow(defaultOuterCenterPtY,2));
            a = (Math.pow(defaultOuterCenterPtX,2) + Math.pow(defaultOuterCenterPtY,2) + 2*innerRadius*outerRadius - Math.pow(outerRadius,2) - Math.pow(hTransition,2)) / (2*defaultOuterCenterPtX);
            b = (hTransition - defaultOuterCenterPtY) / defaultOuterCenterPtX;
            tempJudgeEquation = Math.pow((a*b - hTransition),2) - (b*b+1)*(Math.pow(a,2)+Math.pow(hTransition,2)-Math.pow(innerRadius,2));

            if(innerRadius <= outerRadius)
            {
                defaultInnerCenterPtY = ((hTransition-a*b)+Math.sqrt(tempJudgeEquation))/(b*b+1);
                defaultInnerCenterPtX = a + b*defaultInnerCenterPtY;
                dx = defaultInnerCenterPtX - defaultOuterCenterPtX;
                dy = defaultInnerCenterPtY - defaultOuterCenterPtY;
            }
            else
            {
                defaultInnerCenterPtY = ((hTransition-a*b)-Math.sqrt(tempJudgeEquation))/(b*b+1);
                defaultInnerCenterPtX = a + b*defaultInnerCenterPtY;
                dx = defaultOuterCenterPtX - defaultInnerCenterPtX;
                dy = defaultOuterCenterPtY - defaultInnerCenterPtY;
            }

            theta = this._dataSurround.calculateTheta(dx, dy);

            thetaOutCenterToSurroundOut = this._dataSurround.calculateTheta((surroundWidth - defaultOuterCenterPtX),  (0 - defaultOuterCenterPtY));
            thetaInCenterToSurroundIn = this._dataSurround.calculateTheta((0 - defaultInnerCenterPtX), (hTransition - defaultInnerCenterPtY));



            //System.out.println("tangent outter");
            if(Math.pow(outerRadius,2) - Math.pow(defaultOuterCenterPtY,2) < 0
               || tempJudgeEquation <0 || theta < (Math.PI / 2.0) || theta > Math.PI
               //|| thetaOutCenterToSurroundOut > (Math.PI / 2.0)
               //|| thetaInCenterToSurroundIn > Math.PI
               )
            {
                judgeRule = false;
            }
        }

        defaultTangentPtX = defaultInnerCenterPtX + innerRadius * Math.cos(theta);
        defaultTangentPtY = defaultInnerCenterPtY + innerRadius * Math.sin(theta);

        double innerCenterX = defaultInnerCenterPtX + this._surroundInPt.X();
        double innerCenterY = defaultInnerCenterPtY + this._surroundOutPt.Y();

        double outerCenterX = defaultOuterCenterPtX + this._surroundInPt.X();
        double outerCenterY = defaultOuterCenterPtY + this._surroundOutPt.Y();

        double tangentPtX = defaultTangentPtX + this._surroundInPt.X();
        double tangentPtY = defaultTangentPtY + this._surroundOutPt.Y();

        if(defaultInnerCenterPtX < 0)
        {}

        if (hTransition > hTotal || judgeRule == false
            || innerCenterY > this._surroundInPt.Y() || outerCenterY > this._surroundOutPt.Y()
            || defaultInnerCenterPtX < 0 )
        {
            this._surroundInPt.setCoordinate(this._oldSurroundInPt.X(), this._oldSurroundInPt.Y());
            this._surroundOutPt.setCoordinate(this._oldSurroundOutPt.X(), this._oldSurroundOutPt.Y());

            return true;
        }
        /*
        if(innerCenterY > this._surroundInPt.Y() || outerCenterY > this._surroundOutPt.Y())
        {
            this._surroundInPt.setCoordinate(this._oldSurroundInPt.X(), this._oldSurroundInPt.Y());
            this._surroundOutPt.setCoordinate(this._oldSurroundOutPt.X(), this._oldSurroundOutPt.Y());

            return true;
        }*/


        this._oldSurroundInPt.setCoordinate(this._surroundInPt.X(), this._surroundInPt.Y());
        this._oldSurroundOutPt.setCoordinate(this._surroundOutPt.X(), this._surroundOutPt.Y());


        this._tangentPt = new ElementPoint(tangentPtX, tangentPtY, this._surroundInPt.getColor(), _data);
        this._innerCenter = new ElementPoint(innerCenterX, innerCenterY, this._surroundInPt.getColor(), _data);
        this._outerCenter = new ElementPoint(outerCenterX, outerCenterY, this._surroundInPt.getColor(), _data);

        return false;
    }


    private boolean calculateSurround_SingleArc(double surroundWidth, double hTransition, double radius)
       {
           double lengthIn2Out = Math.sqrt(Math.pow(surroundWidth,2) + Math.pow(hTransition,2));
           if(lengthIn2Out > 2* radius)
           {
               this._surroundInPt.setCoordinate(this._oldSurroundInPt.X(), this._oldSurroundInPt.Y());
               this._surroundOutPt.setCoordinate(this._oldSurroundOutPt.X(), this._oldSurroundOutPt.Y());
               return true;
           }
           this._oldSurroundInPt.setCoordinate(this._surroundInPt.X(), this._surroundInPt.Y());
           this._oldSurroundOutPt.setCoordinate(this._surroundOutPt.X(), this._surroundOutPt.Y());

           double theta = this._dataSurround.calculateAngle(radius, lengthIn2Out, radius);
           double angleIn2Out = this._dataSurround.calculateTheta(surroundWidth, -hTransition);
           double angleIn2Center = angleIn2Out - theta;
           double centerPtX = radius * Math.cos(angleIn2Center)+ this._surroundInPt.X();
           double centerPtY = hTransition + radius * Math.sin(angleIn2Center) + this._surroundOutPt.Y();
           double fakeTangentPtX = surroundWidth;
           double fakeTangentPtY = 0;
           double fakeOuterCenterPtX = centerPtX;
           double fakeOuterCenterPtY = centerPtY;



           this._singleCenter = new ElementPoint(centerPtX, centerPtY, this._surroundInPt.getColor(), _data);


           return false;

    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this.setDataRecover();
        this._transfer = tran;
        this.setBoundary();

        if(this._isShowDetail)
            this.drawDetail( g );
        else
            this.drawProfile( g);
    }

    /**
     * 僅繪製Surround輪廓
     * @param g Graphics
     */
    public void drawProfile(Graphics g)
    {
        if (this._type == DefineSystemConstant.SURROUND_SINGLE_ARC)
        {
            this._singleArc.draw(g, this._transfer);
        }
        else if (this._type == DefineSystemConstant.SURROUND_DOUBLE_ARC)
        {
            this._innerArc.draw(g, this._transfer);
            this._outerArc.draw(g, this._transfer);
            this._tangentPt.draw(g, this._transfer);
        }
    }


    /**
     * 繪製Surround輪廓以及到中心點的連線
     * @param g Graphics
     */
    public void drawDetail(Graphics g)
    {
        if (this._type == DefineSystemConstant.SURROUND_SINGLE_ARC)
        {
            this._singleArc.draw(g, this._transfer);

            this._singleInnerLine.drawDashLine(g, this._transfer);
            this._singleOuterLine.drawDashLine(g, this._transfer);

            this._singleCenter.draw(g, this._transfer);
        }
        else if (this._type == DefineSystemConstant.SURROUND_DOUBLE_ARC)
        {
            this._innerArc.draw(g, this._transfer);
            this._outerArc.draw(g, this._transfer);
            this._tangentPt.draw(g, this._transfer);

            this._innerLine.drawDashLine(g, this._transfer);
            this._outerLine.drawDashLine(g, this._transfer);
            this._middleLineIn.drawDashLine(g, this._transfer);
            this._middleLineOut.drawDashLine(g, this._transfer);

            this._innerCenter.draw(g, this._transfer);
            this._outerCenter.draw(g, this._transfer);
        }
    }

    public void setConstraint()
    { }

    public boolean setBoundary()
    {
        boolean outOfConstraint= true;
        double hTransition = this._surroundInPt.Y() - this._surroundOutPt.Y();
        double surroundWidth = this._surroundOutPt.X() - this._surroundInPt.X();
        this._radiusSingle = this._dataSurround.getSurroundSingleArcRadius(_viewType);
        this._hTotal = this._dataSurround.getSurroundHeight(_viewType);
        this._radiusOut = this._dataSurround.getSurroundDoubleArcOuterRadius(_viewType);
        this._radiusIn = this._dataSurround.getSurroundDoubleArcInnerRadius(_viewType);

        if(this._dataSurround.getGeometryType() == DefineSystemConstant.SURROUND_SINGLE_ARC)
        {
            outOfConstraint = this.calculateSurround_SingleArc(surroundWidth, hTransition, this._radiusSingle);
            if(outOfConstraint == true)
           {
               if (this._dataSurround.ifSwitchSurroundType(_viewType) == true) //表示剛切換完surround的型式
               {
                   double newRadius = this.backwardCalSurSingleArc(surroundWidth, hTransition, this._radiusIn, this._radiusOut);
                   this._dataSurround.setSurroundSingleArcRadius(_viewType, newRadius);
                   outOfConstraint = this.calculateSurround_SingleArc(surroundWidth, hTransition, newRadius);
               }
               else
                   return true;
           }
           this._dataSurround.setIfSwitchSurroundType(_viewType, false);

            ObjectPoint singleArcStartPt = new ObjectPoint(this._surroundInPt.X(), this._surroundInPt.Y());
            ObjectPoint singleArcEndPt = new ObjectPoint(this._surroundOutPt.X(), this._surroundOutPt.Y());
            ObjectPoint singleArcCenterPt = new ObjectPoint(this._singleCenter.X(), this._singleCenter.Y());

            if (this._singleArc == null)
                this._singleArc = new ElementArc(singleArcEndPt, singleArcStartPt, singleArcCenterPt, this.color, _data); //, 90, 90 + this.thetaDegree_1);
            else
                this._singleArc.setStartEndPt(singleArcEndPt, singleArcStartPt, singleArcCenterPt);

            if (this._singleInnerLine == null)
                this._singleInnerLine = new ElementLine(singleArcStartPt, singleArcCenterPt, this.color, _data);
            else
                this._singleInnerLine.setStartEndPt(singleArcStartPt, singleArcCenterPt);

            if (this._singleOuterLine == null)
                this._singleOuterLine = new ElementLine(singleArcEndPt, singleArcCenterPt, this.color, _data);
            else
                this._singleOuterLine.setStartEndPt(singleArcEndPt, singleArcCenterPt);

            //this.rec.setLocation(this._surroundInPt.X(), this._tangentPt.Y());
            //this.rec.width = Math.abs(this._surroundOutPt.X() - this._surroundInPt.X());
            //this.rec.height = Math.abs(this._tangentPt.Y() - this._surroundOutPt.Y());

        }
        else if(this._dataSurround.getGeometryType() == DefineSystemConstant.SURROUND_DOUBLE_ARC)
        {
            outOfConstraint = this.calculateSurround_DoubleArc(hTransition, surroundWidth, this._hTotal, this._radiusIn, this._radiusOut);

            if (outOfConstraint == true)
            {
                if (this._dataSurround.ifSwitchSurroundType(_viewType) == true) //表示剛切換完surround的型式
                {
                    double[] newRadius = this.backwardCalSurDoubleArc(surroundWidth, hTransition, this._radiusSingle);
                    this._dataSurround.setSurroundHeightInOutRadius(_viewType, newRadius[2], newRadius[0], newRadius[1]);
                    outOfConstraint = this.calculateSurround_DoubleArc(hTransition, surroundWidth, newRadius[2], newRadius[0], newRadius[1]);
                }
                else if(this._dataSurround.ifSetFromDialog(_viewType) == true)
                {
                    double[] newRadius = this.backwardCalSurDoubleArcByHeight(surroundWidth, hTransition, this._hTotal);
                    if(newRadius[0] < 0 || newRadius[1] < 0)
                        return true;
                    this._dataSurround.setSurroundHeightInOutRadius(_viewType, this._hTotal, newRadius[0], newRadius[1]);
                    outOfConstraint = this.calculateSurround_DoubleArc(hTransition, surroundWidth, this._hTotal, newRadius[0], newRadius[1]);
                }
                else
                    return true;
            }
            if (this._innerCenter.Y() > this._surroundInPt.Y() || this._outerCenter.Y() > this._surroundOutPt.Y())
            {
                return true;
            }


            this._dataSurround.setIfSwitchSurroundType(_viewType, false);
            this._dataSurround.setIfSetFromDialog(_viewType, false);

            ObjectPoint innerArcStartPt = new ObjectPoint(this._surroundInPt.X(), this._surroundInPt.Y());
            ObjectPoint innerArcEndPt = new ObjectPoint(this._tangentPt.X(), this._tangentPt.Y());
            ObjectPoint innerArcCenterPt = new ObjectPoint(this._innerCenter.X(), this._innerCenter.Y());

            if (this._innerArc == null)
                this._innerArc = new ElementArc(innerArcEndPt, innerArcStartPt, innerArcCenterPt, this.color, _data); //, 90, 90 + this.thetaDegree_1);
            else
                this._innerArc.setStartEndPt(innerArcEndPt, innerArcStartPt, innerArcCenterPt);

            ObjectPoint outerArcStartPt = new ObjectPoint(this._tangentPt.X(), this._tangentPt.Y());
            ObjectPoint outerArcEndPt = new ObjectPoint(this._surroundOutPt.X(), this._surroundOutPt.Y());
            ObjectPoint outerArcCenterPt = new ObjectPoint(this._outerCenter.X(), this._outerCenter.Y());

            if (this._outerArc == null)
                this._outerArc = new ElementArc(outerArcEndPt, outerArcStartPt, outerArcCenterPt, this.color, _data); //, 90 - this.thetaDegree_2, 90);
            else
                this._outerArc.setStartEndPt(outerArcEndPt, outerArcStartPt, outerArcCenterPt);

            if (this._innerLine == null)
                this._innerLine = new ElementLine(innerArcStartPt, innerArcCenterPt, this.color, _data);
            else
                this._innerLine.setStartEndPt(innerArcStartPt, innerArcCenterPt);

            if (this._outerLine == null)
                this._outerLine = new ElementLine(outerArcEndPt, outerArcCenterPt, this.color, _data);
            else
                this._outerLine.setStartEndPt(outerArcEndPt, outerArcCenterPt);

            if (this._middleLineIn == null)
                this._middleLineIn = new ElementLine(innerArcEndPt, innerArcCenterPt, this.color, _data);
            else
                this._middleLineIn.setStartEndPt(innerArcEndPt, innerArcCenterPt);

            if (this._middleLineOut == null)
                this._middleLineOut = new ElementLine(innerArcEndPt, outerArcCenterPt, this.color, _data);
            else
                this._middleLineOut.setStartEndPt(innerArcEndPt, outerArcCenterPt);

            this.rec.setLocation(this._surroundInPt.X(), this._tangentPt.Y());
            this.rec.width = Math.abs(this._surroundOutPt.X() - this._surroundInPt.X());
            this.rec.height = Math.abs(this._tangentPt.Y() - this._surroundOutPt.Y());

        }



        return outOfConstraint;
    }


    public boolean isPtInBoundary(ObjectPoint point)
    {

        if (this._type == DefineSystemConstant.SURROUND_DOUBLE_ARC)
        {
            if (_innerArc == null || _outerArc == null)
                return false;
            if (this._innerArc.isPtInBoundary(point) == true || this._outerArc.isPtInBoundary(point) == true)
                return true;
            else
                return false;
        }
        else
        {
            if(this._singleArc == null)
                return false;
            if(this._singleArc.isPtInBoundary(point) == true)
                return true;
            else
                return false;
        }
    }

    public void setColor(Color c)
    {
        this.color = c;
        if (this._type == DefineSystemConstant.SURROUND_SINGLE_ARC)
        {
            if (this._singleArc != null)
                this._singleArc.setColor(this.color);
        }
        else if (this._type == DefineSystemConstant.SURROUND_DOUBLE_ARC)
        {
            if (this._innerArc != null)
                this._innerArc.setColor(this.color);
            if (this._outerArc != null)
                this._outerArc.setColor(this.color);
        }
    }


    public void move(double dx, double dy)
    {
        this._surroundInPt.move(dx, dy);
        this._surroundOutPt.move(dx, dy);

        this.rec.translate(dx, dy);
    }

    /**
     * 如果Single_Arc的surround outofConstraint，則根據Double_Arc的surround參數去計算出符合條件的新的Single_Arc半徑
     * @param surroundW Width of surround
     * @param transitionH surround_In 相對於 surround_Out的高度差; surround_In 高於 surround_Out為正
     * @param innerRadius Double_Arc內圓弧半徑
     * @param outerRadius Double_Arc外圓弧半徑
     * @return 新的Single_Arc 半徑
     */
    private double backwardCalSurSingleArc(double surroundW, double transitionH, double innerRadius, double outerRadius)
    {
        double length = Math.sqrt(surroundW * surroundW + transitionH * transitionH);
        double newRadius;
        if(innerRadius + outerRadius > length)
            newRadius = (innerRadius + outerRadius) / 2.0;
        else
            newRadius = length * 1.5 / 2.0;
        return newRadius;
    }

    /**
     * 如果Double_Arc的surround outofConstraint，則根據Single_Arc的surround參數去計算出符合條件的新的Double_Arc半徑
     * @param surroundW Width of surround
     * @param transitionH surround_In 相對於 surround_Out的高度差; surround_In 高於 surround_Out為正
     * @param singleRadius Single_Arc圓弧半徑
     * @return [0]:新的內圓弧半徑 [1]:新的外圓弧半徑 [3]:新的totalHeight
     */
    private double[] backwardCalSurDoubleArc(double surroundW, double transitionH, double singleRadius)
    {
        double[] newParameter = new double[3];

        double length = Math.sqrt(surroundW * surroundW + transitionH * transitionH);
        double newInnerRadius = 0.4 * length;
        double newOuterRadius = 0.65 * length;

        double angleIn2Out = this._dataSurround.calculateTheta(surroundW, -transitionH);
        double innerCenterPtX = newInnerRadius * Math.cos(angleIn2Out);
        double innerCenterPtY = transitionH + newInnerRadius * Math.sin(angleIn2Out);
        double tempTheta1 = this._dataSurround.calculateAngle(2.5 , 6.5, 6);
        double tempTheta2 = tempTheta1 + Math.PI + angleIn2Out;

        double outerCenterPtX = surroundW + newOuterRadius * Math.cos(tempTheta2);
        double outerCenterPtY = newOuterRadius * Math.sin(tempTheta2);

        newParameter[0] = newInnerRadius;
        newParameter[1] = newOuterRadius;

        if(transitionH >= 0)
            newParameter[2]  =innerCenterPtY + newInnerRadius;
        else
            newParameter[2] = outerCenterPtY + newOuterRadius;


        return newParameter;
    }

    private double[] backwardCalSurDoubleArcByHeight(double surroundW, double transitionH, double totalH)
    {
        double[] newParameter = new double[2];
        double innerRadius = 0;
        double outerRadius = 0;


        if(transitionH >= 0)
        {
            innerRadius = totalH  - transitionH;
            outerRadius = (Math.pow(surroundW - innerRadius, 2) + Math.pow(totalH, 2)) / 2 / totalH;
            if(totalH > outerRadius)
                if(surroundW - 2* innerRadius <= 0.001)
                    outerRadius = -1;
                else
                    outerRadius = (Math.pow(transitionH, 2) + Math.pow(surroundW, 2) - 2 * innerRadius * surroundW) / 2 / (surroundW - 2* innerRadius);
        }
        else
        {
            outerRadius = totalH - Math.abs(transitionH);
            innerRadius = (Math.pow(surroundW - outerRadius, 2) + Math.pow(totalH, 2)) / 2 / totalH;
            if(totalH > innerRadius)
                if(surroundW - 2 * outerRadius <= 0.001)
                    innerRadius = -1;
                else
                    innerRadius = (Math.pow(transitionH, 2) + Math.pow(surroundW, 2) - 2 * outerRadius * surroundW) / 2 / (surroundW - 2* outerRadius);
        }

        newParameter[0] = innerRadius;
        newParameter[1] = outerRadius;

        return newParameter;
    }

}
