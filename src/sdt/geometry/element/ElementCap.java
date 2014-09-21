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
public class ElementCap extends ElementCommon
{
    private double _radius;
    private double _theata;
    private ElementPoint _ptStartOld;
    private ElementPoint _ptEndOld;
    private ElementPoint _startPt;
    private ElementPoint _endPt;
    private ElementPoint _centerPt;
    private ElementPoint _ptXZCapsule;
    private ElementPoint _ptYZRoundCut;
    private ElementPoint _ptYZRoundCutL;



    private ElementArc  _capArc;
    private ElementLine _radiusLine1;
    private ElementLine _radiusLine2;
    private int         _capType;


  //  private boolean moveLimit = false;

    public ElementCap(DataCap data, int viewType, Color c)
    {
        super(c, data, viewType);

        this._startPt = data.getElementPointStart(viewType);
        this._endPt = data.getElementPointEnd(viewType);
        this._ptXZCapsule = data.getCapPtXZCapsule();
        this._ptYZRoundCut = data.getCapPtYZRoundCut();
        this._ptYZRoundCutL = data.getCapPtYZRoundCutL();

        _ptStartOld = new ElementPoint(this._startPt.X(),this._startPt.Y(),c ,  _data);
        _ptEndOld = new ElementPoint(this._endPt.X(),this._endPt.Y(),c,  _data);
        this._elementType = this.TYPE_CAP;
        this._capType = data.getGeometryType();
    }



    public void draw(Graphics g, SDT_DrawTransfer tran)
    {

        this.setDataRecover();
        this.setConstraint();
        this.setBoundary();

        this._transfer = tran;


        if (this._isShowDetail)
            this.drawDetail(g);
        else
            this.drawProfile(g);
        //this._data.regenerateElementXY();
        //this._data.getDataManager().getDataTran().getElementPointXZStart().setCoordinate(this._endPt.X(),this._endPt.Y());
    }

    public void drawProfile(Graphics g)
    {
        this._capArc.draw(g, this._transfer);
        if(this._capType == DefineSystemConstant.CAP_TYPE_CAPSULE && this._viewType == DefineSystemConstant.XZView)
        {
            this._ptXZCapsule.draw(g, this._transfer);
            System.out.println("_ptXZCapsule = " + this._ptXZCapsule.X());
            ElementLine line = new ElementLine(_ptXZCapsule.point,_startPt.point,this.color,  _data);
            line.draw(g, this._transfer);
        }
        else if (this._capType == DefineSystemConstant.CAP_TYPE_ROUNDCUT && this._viewType == DefineSystemConstant.YZView)
        {
            this._ptYZRoundCutL.draw(g, this._transfer);
            this._ptYZRoundCut.draw(g, this._transfer);
            ElementLine line = new ElementLine(_ptYZRoundCut.point, _ptYZRoundCutL.point, this.color,  _data);
            line.draw(g, this._transfer);
        }
    }


    public void drawDetail(Graphics g)
    {
        this._capArc.draw(g, this._transfer);

        if (this._capType == DefineSystemConstant.CAP_TYPE_CAPSULE && this._viewType == DefineSystemConstant.XZView)
        {
            this._ptXZCapsule.draw(g, this._transfer);
            ElementLine line = new ElementLine(_ptXZCapsule.point, _startPt.point, this.color,  _data);
            line.draw(g, this._transfer);
        }
        else if (this._capType == DefineSystemConstant.CAP_TYPE_ROUNDCUT&& this._viewType == DefineSystemConstant.YZView)
        {
            this._ptYZRoundCutL.draw(g, this._transfer);
            this._ptYZRoundCut.draw(g, this._transfer);
            ElementLine line = new ElementLine(_ptYZRoundCut.point, _ptYZRoundCutL.point, this.color,  _data);
            line.draw(g, this._transfer);
        }





        this._radiusLine1.drawDashLine(g, this._transfer);
        this._radiusLine2.drawDashLine(g, this._transfer);

        this._centerPt.draw(g, this._transfer);
    }


    public void setConstraint()
    {

    }


    public boolean setBoundary()
    {
        boolean isLimit = false;
        /**
         * self geometric constraint
         */
        double xS = this._startPt.X();
        double yS = this._startPt.Y();
        double xE = this._endPt.X();
        double yE = this._endPt.Y();

        if (yS <= yE + 0.1)
            yS = 0.1 + yE;
        if (xS != 0 && (_capType == DefineSystemConstant.CAP_TYPE_REGULAR || _capType == DefineSystemConstant.CAP_TYPE_ROUNDCUT))
        {
            xS = 0;
            this._startPt.point.setLocation(xS, yS);
        }
        if(_capType == DefineSystemConstant.CAP_TYPE_ROUNDCUT)
        {
            this._endPt = ((DataCap)this._data).getElementPointEnd(this.XZView);

            ((DataCap)this._data).calculateRoundCutPt(this._ptYZRoundCutL.X());
            this._ptYZRoundCut = ((DataCap)this._data).getCapPtYZRoundCut();

            this._endPt.setCoordinate(this._endPt.X(),this._ptYZRoundCutL.Y());
        }


        double[] thetaRadius = getRadiusAndTheta(this._startPt, this._endPt);
        _theata = thetaRadius[0];

        if (_theata >= (Math.PI) / 2.0)
        //if (_theata <= ((10.0 / 180.0) * Math.PI) || _theata >= (Math.PI) / 2.0)
        {
            isLimit = true;
            System.out.println("Limit-->");

            this._endPt.setCoordinate(this._ptEndOld.X(), this._ptEndOld.Y());
            this._startPt.point.setLocation(this._ptStartOld.X(), this._ptStartOld.Y());
            return isLimit;

            //this._startPt._moveLimit = false;
            /*
                         if (_ptEndOld.Y() != this._endPt.Y() || _ptEndOld.X() != this._endPt.X())
                         {
                _endPt.setCoordinate(_ptEndOld.X(), _ptEndOld.Y());
                         }
                         if (_ptStartOld.Y() != this._startPt.Y() || _ptStartOld.X() != this._startPt.X())
                         {
                _startPt.setCoordinate(_ptStartOld.X(), _ptStartOld.Y());
                         }*/
        }
        _ptEndOld.setCoordinate(_endPt.X(), _endPt.Y());
        _ptStartOld.setCoordinate(_startPt.X(), _startPt.Y());

        this._radius = thetaRadius[1];
        //--------------

        ObjectPoint objStartPt = new ObjectPoint(this._startPt.X(), this._startPt.Y());
        ObjectPoint objEndPt = new ObjectPoint(this._endPt.X(), this._endPt.Y());
        ObjectPoint objCenterPt = new ObjectPoint(this._startPt.X(), this._startPt.Y() - this._radius);

        if (this._capArc == null)
        {
            this._capArc = new ElementArc(objEndPt, objStartPt, objCenterPt, this.color, _data); //, angleStart, angleEnd);
        }
        else
        {
            this._capArc.setStartEndPt(objEndPt, objStartPt, objCenterPt);
        }

        if (this._radiusLine1 == null)
            this._radiusLine1 = new ElementLine(objEndPt, objCenterPt, this.color,  _data);
        else
            this._radiusLine1.setStartEndPt(objEndPt, objCenterPt);

        if (this._radiusLine2 == null)
            this._radiusLine2 = new ElementLine(objStartPt, objCenterPt, this.color,  _data);
        else
            this._radiusLine2.setStartEndPt(objStartPt, objCenterPt);

        this._centerPt = new ElementPoint(objCenterPt, this._startPt.getColor(),  _data);

        if (_capType == DefineSystemConstant.CAP_TYPE_CAPSULE)
        {
            this._ptXZCapsule.setCoordinate(0, _startPt.Y());
            if (this._viewType == DefineSystemConstant.XZView)
            {
                ElementPoint startPtYZ = ((DataCap)_data).getElementPointStart( DefineSystemConstant.YZView);
                ElementPoint endPtYZ = ((DataCap)_data).getElementPointEnd( DefineSystemConstant.YZView);

                startPtYZ.setCoordinate(0, _startPt.Y());
                endPtYZ.setCoordinate(_endPt.X() - _startPt.X(), _endPt.Y());

            }


        }
        if (_capType == DefineSystemConstant.CAP_TYPE_ROUNDCUT)
        {


        //    this._ptYZRoundCutL.setCoordinate(this._ptYZRoundCut.X(), _endPt.Y());
        }



        this.rec.setLocation(this._startPt.X(), this._startPt.Y());
        this.rec.width = Math.abs(this._endPt.X() - this._startPt.X());
        this.rec.height = Math.abs(this._startPt.Y() - this._endPt.Y());
        return isLimit;
    }

    private double[] getRadiusAndTheta(ElementPoint spt, ElementPoint ept)
    {
        double[] theta_Radius = new double[2];
        double h = Math.abs(spt.Y() - ept.Y());
        double w = Math.abs(spt.X() - ept.X());
        this.rec.setLocation(spt.X(), spt.Y());
        this.rec.width = w;
        this.rec.height = h;
        double phi = Math.atan(w / h);
        theta_Radius[0] = Math.PI - 2 * phi;
        theta_Radius[1] = h + w / Math.tan(theta_Radius[0]);
        return theta_Radius;
    }


    public void setColor(Color c)
    {
        this.color=c;
        if(this._capArc!= null)
            this._capArc.setColor(this.color);
    }

    public boolean isPtInBoundary(ObjectPoint point)
    {
        if (this._capArc == null)
            return false;
        if (this._capArc.isPtInBoundary(point) == true)
            return true;
        else
            return false;
    }

    public void move(double dx, double dy)
    {
            this._startPt.move(dx, dy);
            this._endPt.move(dx, dy);
            this._capArc.move(dx, dy);
    }
}
