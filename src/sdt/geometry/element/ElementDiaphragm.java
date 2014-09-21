package sdt.geometry.element;

import java.awt.*;

import sdt.data.*;
import sdt.define.*;
import sdt.geometry.*;
import sdt.panel.drawpanel.*;

public class ElementDiaphragm extends ElementCommon
{
    private ElementPoint _ptStart;
    private ElementPoint _ptEnd;
    private ElementPoint _ptRidge;
    private ElementLine _diaphragmLine;
    private ElementLine _diaphragmLineIn;
    private ElementLine _diaphragmLineOut;
    private ElementArc _diaphragmArc;

    private double _ridgePtX;
    private double _ridgePtY;
    private double _radius;
    private int _diaphragmType;
    private DataDiaphragm _dataDiaphrqagm;
    private int _viewType;


    public ElementDiaphragm(DataDiaphragm data, int viewType, Color c)
    {
        super(c, data, viewType);
        this._viewType = viewType;
        this._dataDiaphrqagm = data;
        this._ptStart = data.getElementPointStart(viewType);
        this._ptEnd = data.getElementPointEnd(viewType);
        this._elementType = this.TYPE_DIAPHRAGM;

        this._ptRidge = data.getElementPointRidge(viewType);

        this._diaphragmType = data.getGeometryType();
        this._radius = data.getDiaphragmRadius(viewType);
    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this.setDataRecover();
        this.setBoundary();

        this._transfer = tran;

        if (this._diaphragmType == DefineSystemConstant.DIAPHRAGM_TYPE1)// || this._viewType == DefineSystemConstant.YZView)
        {
            this._diaphragmLine.draw(g, this._transfer);
        }
        else if (this._diaphragmType == DefineSystemConstant.DIAPHRAGM_TYPE2)
        {
            if(this._viewType == DefineSystemConstant.YZView)
                this._diaphragmLine.draw(g, this._transfer);
            else
            {

                this._diaphragmLineIn.draw(g, this._transfer);
                this._diaphragmLineOut.draw(g, this._transfer);
                this._diaphragmArc.draw(g, this._transfer);
            }
        }
    }

    public boolean setBoundary()
    {
        boolean isLimit = false;

        ObjectPoint objSpt = new ObjectPoint(this._ptStart.X(), this._ptStart.Y());
        ObjectPoint objEpt = new ObjectPoint(this._ptEnd.X(), this._ptEnd.Y());

        double arcCenterY = this._ptRidge.Y() - this._radius;
        double arcCenterX = this._ptRidge.X();

        double lengthSPt2CPt = Math.sqrt(Math.pow(arcCenterX - this._ptStart.X(), 2) + Math.pow(arcCenterY - this._ptStart.Y(), 2));
        double lengthEPt2CPt = Math.sqrt(Math.pow(arcCenterX - this._ptEnd.X(), 2) + Math.pow(arcCenterY - this._ptEnd.Y(), 2));
        double lengthSPt2TangentPt = Math.sqrt(Math.pow(lengthSPt2CPt, 2) - Math.pow(this._radius, 2));
        double lengthEPt2TangentPt = Math.sqrt(Math.pow(lengthEPt2CPt, 2) - Math.pow(this._radius, 2));

        double angleSPt2CPt = this._dataDiaphrqagm.calculateTheta(arcCenterX - this._ptStart.X(), arcCenterY - this._ptStart.Y());
        double angleEPt2CPt = this._dataDiaphrqagm.calculateTheta(arcCenterX - this._ptEnd.X(), arcCenterY - this._ptEnd.Y());
        double angleTangentStartCenter = Math.acos(lengthSPt2TangentPt / lengthSPt2CPt);
        double angleTangentEndCenter = Math.acos(lengthEPt2TangentPt / lengthEPt2CPt);

        double tangentPt1X = this._ptStart.X() + lengthSPt2TangentPt * Math.cos(angleTangentStartCenter + angleSPt2CPt);
        double tangentPt1Y = this._ptStart.Y() + lengthSPt2TangentPt * Math.sin(angleTangentStartCenter + angleSPt2CPt);

        double tangentPt2X = this._ptEnd.X() + lengthEPt2TangentPt * Math.cos(angleEPt2CPt - angleTangentEndCenter);
        double tangentPt2Y = this._ptEnd.Y() + lengthEPt2TangentPt * Math.sin(angleEPt2CPt - angleTangentEndCenter);


        ObjectPoint objTangentPt1 = new ObjectPoint(tangentPt1X, tangentPt1Y);
        ObjectPoint objTangentPt2 = new ObjectPoint(tangentPt2X, tangentPt2Y);
        ObjectPoint objArcCenterPt = new ObjectPoint(arcCenterX, arcCenterY);

        if (this._diaphragmType == DefineSystemConstant.DIAPHRAGM_TYPE1)
        {
            if (this._diaphragmLine == null)
                this._diaphragmLine = new ElementLine(objSpt, objEpt, this.color, _data);
            else
                this._diaphragmLine.setStartEndPt(objSpt, objEpt);
        }
        else
        {
            if (this._viewType == DefineSystemConstant.XZView)
            {
                if (this._diaphragmLineIn == null)
                    this._diaphragmLineIn = new ElementLine(objSpt, objTangentPt1, this.color,_data);
                else
                    this._diaphragmLineIn.setStartEndPt(objSpt, objTangentPt1);

                if (this._diaphragmLineOut == null)
                    this._diaphragmLineOut = new ElementLine(objTangentPt2, objEpt, this.color, _data);
                else
                    this._diaphragmLineOut.setStartEndPt(objTangentPt2, objEpt);

                if (this._diaphragmArc == null)
                    this._diaphragmArc = new ElementArc(objTangentPt2, objTangentPt1, objArcCenterPt, this.color, _data);
                else
                this._diaphragmArc.setStartEndPt(objTangentPt2, objTangentPt1, objArcCenterPt);
            }
            else
            {
                if (this._diaphragmLine == null)
                    this._diaphragmLine = new ElementLine(objSpt, objEpt, this.color, _data);
                else
                    this._diaphragmLine.setStartEndPt(objSpt, objEpt);
            }
        }

        return isLimit;
    }

    public boolean isPtInBoundary(ObjectPoint point)
    {
        if (this._diaphragmType == DefineSystemConstant.DIAPHRAGM_TYPE1)
        {
            if (this._diaphragmLine != null)
            {
                if (this._diaphragmLine.isPtInBoundary(point) == true)
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        else
        {
            if (this._viewType == DefineSystemConstant.XZView)
            {
                if (this._diaphragmLineIn != null && this._diaphragmLineOut != null && this._diaphragmArc != null)
                {
                    if (this._diaphragmLineIn.isPtInBoundary(point) == true || this._diaphragmLineOut.isPtInBoundary(point) == true
                        || this._diaphragmArc.isPtInBoundary(point) == true)
                        return true;
                    else
                        return false;
                }
                else
                    return false;
            }
            else
            {
                if (this._diaphragmLine != null)
                {
                    if (this._diaphragmLine.isPtInBoundary(point) == true)
                        return true;
                    else
                        return false;
                }
                else
                    return false;
            }
        }
    }

    public void move(double dx, double dy)
    {
        this._ptStart.move(dx, dy);
        this._ptEnd.move(dx, dy);
        this._ptRidge.move(dx, dy);

        this.rec.translate(dx, dy);
    }

    public void setColor(Color c)
    {
        this.color = c;
        if (this._diaphragmType == DefineSystemConstant.DIAPHRAGM_TYPE1)
        {
            if (this._diaphragmLine != null)
                this._diaphragmLine.setColor(this.color);
        }
        else
        {
            if (this._viewType == DefineSystemConstant.XZView){
                if (this._diaphragmLineIn != null)
                    this._diaphragmLineIn.setColor(this.color);

                if (this._diaphragmLineOut != null)
                    this._diaphragmLineOut.setColor(this.color);

                if (this._diaphragmArc != null)
                    this._diaphragmArc.setColor(this.color);
            }
            else
            {
                if (this._diaphragmLine != null)
                this._diaphragmLine.setColor(this.color);
            }
        }
    }

    public void setConstraint()
    {
    }

    public void setElementType(int type)
    {
        this._elementType = type;
    }


}
