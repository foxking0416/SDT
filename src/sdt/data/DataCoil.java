package sdt.data;

import java.io.*;

import sdt.define.*;
import sdt.geometry.element.*;
import sdt.stepb.*;
import java.awt.Color;

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
public class DataCoil extends DataSolid
{
    //int _coilType;

    ElementPoint _elementPtXZOuterStart;
    ElementPoint _elementPtXZOuterEnd;
    ElementPoint _elementPtYZOuterStart;
    ElementPoint _elementPtYZOuterEnd;

    ElementPoint _elementPtXZCoilStart;
    ElementPoint _elementPtXZCoilEnd;

    ElementPoint _elementPtXZCoilOuterStart;
    ElementPoint _elementPtXZCoilOuterEnd;

    ElementPoint _elementPtYZCoilStart;
    ElementPoint _elementPtYZCoilEnd;

    ElementPoint _elementPtYZCoilOuterStart;
    ElementPoint _elementPtYZCoilOuterEnd;

    double _StartOuterPtY;
    double _coilStartPtY;
    double _coilEndPtY;
    double _thicknessFormer;
    private Color _colorFormer;

    double _thickness = -1;

    public void setColorFormer(Color colorFormer)   {this._colorFormer =  colorFormer ; }

    public DataCoil(SDT_DataManager dataManager)
    {
        super(dataManager);

        _dataType = this.TYPE_COIL;
        _point3DStartSection1 = new stepb_cartesian_point(1.4, 0, 0);
        _point3DEndSection1 = new stepb_cartesian_point(1.4, 0, -1.6);
        _point3DStartSection2 = new stepb_cartesian_point(0, 1.4, 0);
        _point3DEndSection2 = new stepb_cartesian_point(0, 1.4, -1.6);

        _countGirth = dataManager.getCountGirth();
        _countRadial = 8;
        _countPeriodical = 16;
        _countCircumferential = _countGirth / _countPeriodical + 1; //11
        _countThickness = 2; //11

        //this._coilType = DefineSystemConstant.COIL_TYPE1;
        this._geometryType = DefineSystemConstant.COIL_TYPE1;

        this._coilStartPtY = -1;
        this._coilEndPtY = -1.5;
        this._thickness  =0.1;
        this._thicknessFormer = 0.1;

        this._colorBody = this._dataManager.getColorCoil();
        this._colorFormer = this._dataManager.getColorFormer();
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);


        this.setAllElementPointsByType();
        /*this._elementPtXZOuterStart = new ElementPoint(this._point3DStartSection1.X()+this._thickness, this._point3DStartSection1.Z(), _colorPt);
        this._elementPtXZOuterEnd   = new ElementPoint(this._point3DEndSection1.X()+this._thickness, this._point3DEndSection1.Z(), _colorPt);

        this._elementPtXZCoilStart = new ElementPoint(this._point3DStartSection1.X()+this._thickness, this._coilStartPtY, _colorPt);
        this._elementPtXZCoilEnd = new ElementPoint(this._point3DStartSection1.X()+this._thickness, this._coilEndPtY, _colorPt);
        this._elementPtXZCoilOuterStart = new ElementPoint(this._point3DStartSection1.X()+this._thickness +this._thickness, this._coilStartPtY, _colorPt);
        this._elementPtXZCoilOuterEnd = new ElementPoint(this._point3DStartSection1.X()+this._thickness+this._thickness, this._coilEndPtY, _colorPt);

        this._elementPtYZOuterStart = new ElementPoint(this._point3DStartSection2.Y()+this._thickness,  this._point3DStartSection2.Z(), _colorPt);
        this._elementPtYZOuterEnd   = new ElementPoint(this._point3DEndSection2.Y()+this._thickness,  this._point3DEndSection2.Z(), _colorPt);

        this._elementPtYZCoilStart = new ElementPoint(this._point3DStartSection2.Y()+this._thickness, this._coilStartPtY, _colorPt);
        this._elementPtYZCoilEnd = new ElementPoint(this._point3DStartSection2.Y()+this._thickness, this._coilEndPtY, _colorPt);
        this._elementPtYZCoilOuterStart = new ElementPoint(this._point3DStartSection2.Y()+this._thickness +this._thickness, this._coilStartPtY, _colorPt);
        this._elementPtYZCoilOuterEnd = new ElementPoint(this._point3DStartSection2.Y()+this._thickness +this._thickness, this._coilEndPtY, _colorPt);
        */
        System.out.println("new Data Coil");

    }
    private void setAllElementPointsByType()
    {
        double thicknessInnerOuter = -1;
        if (this._geometryType == DefineSystemConstant.COIL_TYPE1)
        {
            thicknessInnerOuter = _thickness;
        }
        else
        {
            thicknessInnerOuter = _thicknessFormer;
        }




        if (this._elementPtXZOuterStart == null)
            this._elementPtXZOuterStart = new ElementPoint(this._point3DStartSection1.X() + thicknessInnerOuter, this._point3DStartSection1.Z(), _colorPt, this);
        else
            this._elementPtXZOuterStart.setCoordinate(this._point3DStartSection1.X() + thicknessInnerOuter, this._point3DStartSection1.Z());

        if (this._elementPtXZOuterEnd == null)
            this._elementPtXZOuterEnd = new ElementPoint(this._point3DEndSection1.X() + thicknessInnerOuter, this._point3DEndSection1.Z(), _colorPt, this);
        else
            this._elementPtXZOuterEnd.setCoordinate(this._point3DEndSection1.X() + thicknessInnerOuter, this._point3DEndSection1.Z());

        if (this._elementPtXZCoilStart == null)
            this._elementPtXZCoilStart = new ElementPoint(this._point3DStartSection1.X() + thicknessInnerOuter, this._coilStartPtY, _colorPt, this);
        else
            this._elementPtXZCoilStart.setCoordinate(this._point3DStartSection1.X() + thicknessInnerOuter, this._elementPtXZCoilStart.Y());

        if (this._elementPtXZCoilEnd == null)
            this._elementPtXZCoilEnd = new ElementPoint(this._point3DStartSection1.X() + thicknessInnerOuter, this._coilEndPtY, _colorPt, this);
        else
            this._elementPtXZCoilEnd.setCoordinate(this._point3DStartSection1.X() + thicknessInnerOuter, this._elementPtXZCoilEnd.Y());
        //------------------
        if (this._elementPtYZOuterStart == null)
            this._elementPtYZOuterStart = new ElementPoint(this._point3DStartSection2.Y() + thicknessInnerOuter, this._point3DStartSection2.Z(), _colorPt, this);
        else
            this._elementPtYZOuterStart.setCoordinate(this._point3DStartSection2.Y() + thicknessInnerOuter, this._point3DStartSection2.Z());

        if (this._elementPtYZOuterEnd == null)
            this._elementPtYZOuterEnd = new ElementPoint(this._point3DEndSection2.Y() + thicknessInnerOuter, this._point3DEndSection2.Z(), _colorPt, this);
        else
            this._elementPtYZOuterEnd.setCoordinate(this._point3DEndSection2.Y() + thicknessInnerOuter, this._point3DEndSection2.Z());

        if (this._elementPtYZCoilStart == null)
            this._elementPtYZCoilStart = new ElementPoint(this._point3DStartSection2.Y() + thicknessInnerOuter, this._coilStartPtY, _colorPt, this);
        else
            this._elementPtYZCoilStart.setCoordinate(this._point3DStartSection2.Y() + thicknessInnerOuter,this._elementPtYZCoilStart.Y());

        if (this._elementPtYZCoilEnd == null)
            this._elementPtYZCoilEnd = new ElementPoint(this._point3DStartSection2.Y() + thicknessInnerOuter, this._coilEndPtY, _colorPt, this);
        else
            this._elementPtYZCoilEnd.setCoordinate(this._point3DStartSection2.Y() + thicknessInnerOuter, this._elementPtYZCoilEnd.Y());

        if (this._elementPtXZCoilOuterStart == null)
            this._elementPtXZCoilOuterStart = new ElementPoint(this._point3DStartSection1.X() + this._thicknessFormer + this._thickness,  this._elementPtXZCoilStart.Y(), _colorPt, this);
        else
            this._elementPtXZCoilOuterStart.setCoordinate(this._point3DStartSection1.X() + this._thicknessFormer + this._thickness, this._elementPtXZCoilStart.Y());

        if (this._elementPtXZCoilOuterEnd == null)
            this._elementPtXZCoilOuterEnd = new ElementPoint(this._point3DStartSection1.X() + this._thicknessFormer + this._thickness, this._elementPtXZCoilEnd.Y(), _colorPt, this);
        else
            this._elementPtXZCoilOuterEnd.setCoordinate(this._point3DStartSection1.X() + this._thicknessFormer + this._thickness,  this._elementPtXZCoilEnd.Y());

        if (this._elementPtYZCoilOuterStart == null)
            this._elementPtYZCoilOuterStart = new ElementPoint(this._point3DStartSection2.Y() + this._thicknessFormer + this._thickness,  this._elementPtYZCoilStart.Y(), _colorPt, this);
        else
            this._elementPtYZCoilOuterStart.setCoordinate(this._point3DStartSection2.Y() + this._thicknessFormer + this._thickness, this._elementPtYZCoilStart.Y());

        if (this._elementPtYZCoilOuterEnd == null)
            this._elementPtYZCoilOuterEnd = new ElementPoint(this._point3DStartSection2.Y() + this._thicknessFormer + this._thickness, this._coilEndPtY, _colorPt, this);
        else
            this._elementPtYZCoilOuterEnd.setCoordinate(this._point3DStartSection2.Y() + this._thicknessFormer + this._thickness, this._coilEndPtY);

    }
    protected void setElementXYToData()
    {

    }


    protected void setDataToElementXZ()
    {
        if (this._elementPtXZStart == null)
            this._elementPtXZStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Z(), _colorPt, this);
        if (this._elementPtXZEnd == null)
            this._elementPtXZEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Z(), _colorPt, this);
        this._elmementXZ = new ElementCoil(this, this.XZView, this._colorBody, this._colorFormer);
        _isXYupdateNeed = true;

    }

    protected void setDataToElementYZ()
    {
        if (this._elementPtYZStart == null)
            this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt, this);
        if (this._elementPtYZEnd == null)
            this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt, this);
        this._elmementYZ = new ElementCoil(this, this.YZView, this._colorBody, this._colorFormer);
        _isXYupdateNeed = true;
    }

    protected void createDataPointPeriodically()//stepb_cartesian_point[][][] ptSets)
    {
        System.out.println("createDataPointPeriodically @ DataCoil");
        int kCount = this._countPeriodical;         //ptSets.length;
        int jCount = this._countCircumferential;    //ptSets[0].length;
        int iCount = this._countRadial;             //ptSets[0][0].length;
        int tCount = this._countThickness;          //ptSets[0][0][0].length;

        stepb_cartesian_point[][][] ptSetsType1;    //Type1Coil + Type2Former
        stepb_cartesian_point[][][] ptSetsType2;    //Type2Coil
        stepb_cartesian_point[][] ptSetsContact;

        ptSetsType1 = new stepb_cartesian_point[_countPeriodical * (_countCircumferential - 1)][_countRadial][_countThickness] ;
        //= new stepb_cartesian_point[_countPeriodical * (_countCircumferential - 1)][_countRadial][_countThickness] ;


        double thetaIntervalK = 2 * Math.PI / kCount;
        double thetaIntervalJ = 2 * Math.PI / kCount / (jCount - 1);
        double theta = 0;

        double rS = this._point3DStartSection1.X();
        double rE = rS;

        double hS = this._point3DStartSection1.Z();
        double hE = this._point3DEndSection1.Z();

        double tSE = this._elementPtXZOuterStart.X() - this._elementPtXZStart.X();
        double hIncrement = (hE - hS) / (double)(iCount - 1);
        int indexCoilStart = -1;
        int indexCoilEnd = -1;

        if(this._dataType == DefineSystemConstant.COIL_TYPE2)
        {
            for (int i = 0; i < iCount; i++)
            {
                double zM = hS + hIncrement * i;
                if (Math.abs(this._elementPtXZCoilStart.Y() - zM) < Math.abs(hIncrement) && indexCoilStart == -1)
                    indexCoilStart = i;
                if (Math.abs(this._elementPtXZCoilEnd.Y() - zM) < Math.abs(hIncrement) && indexCoilEnd == -1)
                    indexCoilEnd = i;
            }
        }
        double hIncrement2 = (this._elementPtXZCoilStart.Y() - this._elementPtXZCoilEnd.Y()) / (double)(indexCoilStart - indexCoilEnd);


        for (int k = 0; k < kCount; k++)
        {
            for (int j = 0; j < jCount - 1; j++)
            {
                theta = k * thetaIntervalK + j * thetaIntervalJ;
                if (!this._dataManager.getIsStartFromAxis())
                    theta += thetaIntervalJ / 2.0;

                for (int i = 0; i < iCount ; i++)
                {
                    double zM;

                    if(i >= indexCoilStart && i <= indexCoilEnd)
                        zM = this._elementPtXZCoilStart.Y() + (i - indexCoilStart) * hIncrement2;
                    else
                        zM = hS + hIncrement * i;

                    for(int t = 0 ; t < tCount ; t++)
                    {
                        double rM = rS + tSE * (double)t / (double)(tCount -1);
                        double xM = rM * Math.cos(theta);
                        double yM = rM * Math.sin(theta);

                        stepb_cartesian_point cPtM = new stepb_cartesian_point(xM, yM, zM);
                        ptSetsType1[k * (jCount - 1) + j][i][t] = cPtM;
                    }
                }
            }
        }
        CartesianPointSetsBrick ptSetsBrick = new CartesianPointSetsBrick(ptSetsType1);
        this._arrayPtSets.add(ptSetsBrick);

        if(this._geometryType == DefineSystemConstant.COIL_TYPE2)
        {
            int countRadialPart = indexCoilEnd - indexCoilStart + 1 ;
            ptSetsType2 = new stepb_cartesian_point[_countPeriodical * (_countCircumferential - 1)][countRadialPart][_countThickness] ;
            ptSetsContact = new stepb_cartesian_point[_countPeriodical * (_countCircumferential - 1)][countRadialPart];
            //Caculation
            double r2S = this._elementPtXZCoilStart.X();
            double r2E = this._elementPtXZCoilEnd.X();

            double h2S = this._elementPtXZCoilStart.Y();
            double h2E = this._elementPtXZCoilEnd.Y();

            double t2SE = this._elementPtXZCoilOuterStart.X() - this._elementPtXZCoilStart.X();
            double h2Interval = (h2E - h2S) / (countRadialPart - 1);


            for (int k = 0; k < kCount; k++)
            {
                for (int j = 0; j < jCount - 1; j++)
                {

                    theta = k * thetaIntervalK + j * thetaIntervalJ;
                    if (!this._dataManager.getIsStartFromAxis())
                        theta += thetaIntervalJ / 2.0;

                    for (int i = 0; i < countRadialPart; i++)
                    {
                        //double rMPre = r2S + (r2E - r2S) / (countRadialPart - 1) * i;
                        double h2M = h2S + h2Interval * i;

                        double xM = 0;
                        double yM = 0;
                        double zM = h2M;
                        double rM = 0;

                        for (int t = 0; t < tCount; t++)
                        {
                            rM = r2S + t2SE * (double) t / (double) (tCount - 1);
                            xM = rM * Math.cos(theta);
                            yM = rM * Math.sin(theta);
                            stepb_cartesian_point cPtM = new stepb_cartesian_point(xM, yM, zM);
                            ptSetsType2[k * (jCount - 1) + j][i][t] = cPtM;

                            if(t == 0)
                                ptSetsContact[k * (jCount - 1) + j][i] = cPtM;
                        }
                    }
                }
            }

            CartesianPointSetsBrick ptSetsBrick2 = new CartesianPointSetsBrick(ptSetsType2);
            this._arrayPtSets.add(ptSetsBrick2);

            CartesianPointSetsBrick ptSetsBrick1 =  (CartesianPointSetsBrick)this._arrayPtSets.get(0);
            ptSetsBrick1.setContactNodeFromOther(ptSetsContact);
        }
    }


    protected void createDataPointCircumferential()
    {

        int jCount = this._countCircumferential;  //ptSets.length;
        int iCount = this._countRadial;           //ptSets[0].length;
        int tCount = this._countThickness;        //ptSets[0][0].length;

        stepb_cartesian_point[][][] ptSetsType1;    //Type1Coil + Type2Former
        stepb_cartesian_point[][][] ptSetsType2;    //Type2Coil
        stepb_cartesian_point[][] ptSetsContact;

        ptSetsType1 = new stepb_cartesian_point[jCount][iCount][tCount];

        double thetaIntervalJ = 2 * Math.PI / jCount;
        double theta = 0;

        double rS = this._point3DStartSection1.X();
        double rE = rS;

        double hS = this._point3DStartSection1.Z();
        double hE = this._point3DEndSection1.Z();

        double tSE = this._elementPtXZOuterStart.X() - this._elementPtXZStart.X();
        double hIncrement = (hE - hS) / (double)(iCount - 1);
        int indexCoilStart = -1;
        int indexCoilEnd = -1;
        if(this._dataType == DefineSystemConstant.COIL_TYPE2)
        {
            for (int i = 0; i < iCount; i++)
            {
                double zM = hS + hIncrement * i;
                if (Math.abs(this._elementPtXZCoilStart.Y() - zM) < Math.abs(hIncrement) && indexCoilStart == -1)
                    indexCoilStart = i;
                if (Math.abs(this._elementPtXZCoilEnd.Y() - zM) < Math.abs(hIncrement) && indexCoilEnd == -1)
                    indexCoilEnd = i;
            }
        }
        double hIncrement2 = (this._elementPtXZCoilStart.Y() - this._elementPtXZCoilEnd.Y()) / (double)(indexCoilStart - indexCoilEnd);

        for (int j = 0; j < jCount; j++)
        {
            theta = j * thetaIntervalJ;
            if (!this._dataManager.getIsStartFromAxis())
                theta += thetaIntervalJ / 2.0;
            //ptSetsType1[j] = this.setLinePoint3D_Round(rS, rE, hS, hE, h2S, h2E, tSE, theta, iCount, tCount);

            for (int i = 0; i < iCount; i++)
            {
                double zM;
                if (i >= indexCoilStart && i <= indexCoilEnd)
                    zM = this._elementPtXZCoilStart.Y() + (i - indexCoilStart) * hIncrement2;
                else
                    zM = hS + hIncrement * i;

                for (int t = 0; t < tCount; t++)
                {
                    double rM = rS + tSE * (double) t / (double) (tCount - 1);
                    double xM = rM * Math.cos(theta);
                    double yM = rM * Math.sin(theta);

                    stepb_cartesian_point cPtM = new stepb_cartesian_point(xM, yM, zM);
                    ptSetsType1[j][i][t] = cPtM;
                }
            }
        }
        CartesianPointSetsBrick ptSetsBrick = new CartesianPointSetsBrick(ptSetsType1);
        this._arrayPtSets.add(ptSetsBrick);


        if(this._geometryType == DefineSystemConstant.COIL_TYPE2)
        {
            int countRadialPart = indexCoilEnd - indexCoilStart + 1;
            ptSetsType2 = new stepb_cartesian_point[jCount][countRadialPart][tCount];
            ptSetsContact = new stepb_cartesian_point[jCount][countRadialPart];
            //Caculation
            double r2S = this._elementPtXZCoilStart.X();
            double r2E = this._elementPtXZCoilEnd.X();

            double h2S = this._elementPtXZCoilStart.Y();
            double h2E = this._elementPtXZCoilEnd.Y();

            double t2SE = this._elementPtXZCoilOuterStart.X() - this._elementPtXZCoilStart.X();
            double h2Interval = (h2E - h2S) / (countRadialPart - 1);


            for (int j = 0; j < jCount - 1; j++)
            {
                theta = j * thetaIntervalJ;
                if (!this._dataManager.getIsStartFromAxis())
                theta += thetaIntervalJ / 2.0;

                for (int i = 0; i < countRadialPart; i++)
                {
                    //double rMPre = r2S + (r2E - r2S) / (countRadialPart - 1) * i;
                    double h2M = h2S + h2Interval * i;

                    double xM = 0;
                    double yM = 0;
                    double zM = h2M;
                    double rM = 0;

                    for (int t = 0; t < tCount; t++)
                    {
                        rM = r2S + t2SE * (double) t / (double) (tCount - 1);
                        xM = rM * Math.cos(theta);
                        yM = rM * Math.sin(theta);
                        stepb_cartesian_point cPtM = new stepb_cartesian_point(xM, yM, zM);
                        ptSetsType2[j][i][t] = cPtM;

                        if (t == 0)
                            ptSetsContact[j][i] = cPtM;
                    }
                }
            }
            CartesianPointSetsBrick ptSetsBrick2 = new CartesianPointSetsBrick(ptSetsType2);
            this._arrayPtSets.add(ptSetsBrick2);

            CartesianPointSetsBrick ptSetsBrick1 = (CartesianPointSetsBrick)this._arrayPtSets.get(0);
            ptSetsBrick1.setContactNodeFromOther(ptSetsContact);


        }
    }

    /*
    public stepb_cartesian_point[][] setLinePoint3D_Round(double rS, double rE, double hS, double hE,
                                                          double h2S, double h2E, double tSE,
                                                          double theta, int iiCount, int tCount)
    {
        stepb_cartesian_point ptsArray[][] = new stepb_cartesian_point[iiCount][tCount];

        double hIncrement = (hE - hS) / (double) (iiCount - 1);
        int indexCoilStart = -1;
        int indexCoilEnd = -1;

        for (int i = 0; i < iiCount; i++)
        {
            double zM = hS + hIncrement * i;
            if (Math.abs(this._elementPtXZCoilStart.Y() - zM) < Math.abs(hIncrement) && indexCoilStart == -1)
                indexCoilStart = i;
            if (Math.abs(this._elementPtXZCoilEnd.Y() - zM) < Math.abs(hIncrement) && indexCoilEnd == -1)
                indexCoilEnd = i;
        }
        double hIncrement2 = (this._elementPtXZCoilStart.Y() - this._elementPtXZCoilEnd.Y()) / (double) (indexCoilStart - indexCoilEnd);


        for (int ii = 0; ii < iiCount; ii++)
        {
            double z;
            if(ii >= indexCoilStart && ii <= indexCoilEnd)
                z = this._elementPtXZCoilStart.Y() + (ii - indexCoilStart) * hIncrement2;
            else
                z = hS + hIncrement * ii;

            for (int t = 0; t < tCount; t++)
            {
                //x += tSE * t / (tCount - 1);
                //r = (rS + rIncrement * ii) + tSE * (double) t / (double) (tCount - 1);
                double r = rS + tSE * (double) t / (double) (tCount - 1);
                double x = r * Math.cos(theta);
                double y = r * Math.sin(theta);
                stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                ptsArray[ii][t] = cPt;
            }
        }
        return ptsArray;
    }*/

    protected void createDataPointCircumferentialRunway()
    {
        int iCount = this._countRadial;         //ptSetsCircumferential[0].length;
        int jCount = this._countCircumferential;//ptSetsCircumferential.length;
        int tCount = this._countThickness;      //ptSetsCircumferential[0][0].length;


         stepb_cartesian_point[][][] ptSetsType1;    //Type1Coil + Type2Former
         stepb_cartesian_point[][][] ptSetsType2;    //Type2Coil

         ptSetsType1 = new stepb_cartesian_point[jCount][iCount][tCount];


        this.setCircumferentialNumber(true, true);
        this.setRunwayInner();
        this.setRunwayOuter();

        double hS = this._point3DStartSection1.Z(); //transition start point absolute height
        double hE = this._point3DEndSection1.Z(); //transition end point absolute height

        double tSE = this._elementPtXZOuterStart.X() - this._elementPtXZStart.X();
        double hIncrement = (hE - hS) / (double) (iCount - 1);
        int indexCoilStart = -1;
        int indexCoilEnd = -1;

        for (int i = 0; i < iCount ; i++)
        {
            double zM = hS + hIncrement * i;
            if (Math.abs(this._elementPtXZCoilStart.Y() - zM) < Math.abs(hIncrement) && indexCoilStart == -1)
                indexCoilStart = i;
            if (Math.abs(this._elementPtXZCoilEnd.Y() - zM) < Math.abs(hIncrement) && indexCoilEnd == -1)
                indexCoilEnd = i;
        }
        double hIncrement2 = (this._elementPtXZCoilStart.Y() - this._elementPtXZCoilEnd.Y()) / (double)(indexCoilStart - indexCoilEnd);



        for (int j = 0; j < jCount; j++)
        {
            //stepb_cartesian_point ptsArray[][] = new stepb_cartesian_point[iCount][tCount];

            for (int i = 0; i < iCount; i++)
            {
                double z;
                if (i >= indexCoilStart && i <= indexCoilEnd)
                    z = this._elementPtXZCoilStart.Y() + (i - indexCoilStart) * hIncrement2;
                else
                    z = hS + hIncrement * i;

                double x = this._elementArrayInner[j].X();
                double y = this._elementArrayInner[j].Y();
                double x2 = this._elementArrayOuter[j].X();
                double y2 = this._elementArrayOuter[j].Y();

                for (int t = 0; t < tCount; t++)
                {
                    x += Math.abs(x - x2) * t / (tCount - 1);  //tSE * t / (tCount - 1);
                    y += Math.abs(y - y2) * t / (tCount - 1);//tSE * t / (tCount - 1);
                    stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                    //ptsArray[ii][t] = cPt;
                    ptSetsType1[j][i][t] = cPt;
                }
            }
            //ptSetsType1[j] = ptsArray;
        }
        CartesianPointSetsBrick ptSetsBrick = new CartesianPointSetsBrick(ptSetsType1);
        this._arrayPtSets.add(ptSetsBrick);

        if(this._geometryType == DefineSystemConstant.COIL_TYPE2)
        {

        }

    }




    /**
     * 根據截面方向得到Coil Type2的Coil起點
     * <br>
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/coilStartPt.png"  align="center" class="border" width="250" height="208" />
     * </pre>
     * <br>
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @return Coil Type2的Coil起點
     */
    public ElementPoint getElementPointCoilStart(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZCoilStart;
            case DefineSystemConstant.YZView:
                return this._elementPtYZCoilStart;
            default:
                return this._elementPtXZCoilStart;
        }
    }

    public ElementPoint getElementPointCoilOuterStart(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZCoilOuterStart;
            case DefineSystemConstant.YZView:
                return this._elementPtYZCoilOuterStart;
            default:
                return this._elementPtXZCoilOuterStart;
        }
    }

    /**
     * 根據截面方向得到Coil Type2的Coil終點
     * <br>
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/coilEndPt.png"  align="center" class="border" width="250" height="208" />
     * </pre>
     * <br>
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @return Coil Type2的Coil終點
     */
    public ElementPoint getElementPointCoilEnd(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZCoilEnd;
            case DefineSystemConstant.YZView:
                return this._elementPtYZCoilEnd;
            default:
                return this._elementPtXZCoilEnd;
        }
    }
    public ElementPoint getElementPointCoilOuterEnd(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZCoilOuterEnd;
            case DefineSystemConstant.YZView:
                return this._elementPtYZCoilOuterEnd;
            default:
                return this._elementPtXZCoilOuterEnd;
        }
    }

    public ElementPoint getElementPointOuterStart(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZOuterStart;
            case DefineSystemConstant.YZView:
                return this._elementPtYZOuterStart;
            default:
                return this._elementPtXZOuterStart;
        }
    }

    public ElementPoint getElementPointOuterEnd(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZOuterEnd;
            case DefineSystemConstant.YZView:
                return this._elementPtYZOuterEnd;
            default:
                return this._elementPtXZOuterEnd;
        }
    }




    /**
     * 根據截面方向設定Coil Type2的Coil起點座標
     * <br>
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/coilStartPt.png"  align="center" class="border" width="250" height="208" />
     * </pre>
     * <br>
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @param startX 起點X座標
     * @param startY 起點Y座標
     */
    public void setElementPointCoilStartCoordinate(int sectionNumber, double startX, double startY)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZCoilStart.setCoordinate(startX, startY);
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZCoilStart.setCoordinate(startX, startY);
                break;
        }
    }

    public void setElementPointCoilOuterStartCoordinate(int sectionNumber, double endX, double endY)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZCoilOuterStart.setCoordinate(endX, endY);
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZCoilOuterStart.setCoordinate(endX, endY);
                break;
        }
    }


    /**
     * 根據截面方向設定Coil Type2的Coil終點座標
     * <br>
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/coilEndPt.png"  align="center" class="border" width="250" height="208" />
     * </pre>
     * <br>
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @param endX 終點X座標
     * @param endY 終點Y座標
     */
    public void setElementPointCoilEndCoordinate(int sectionNumber, double endX, double endY)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZCoilEnd.setCoordinate(endX, endY);
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZCoilEnd.setCoordinate(endX, endY);
                break;
        }
    }

    public void setElementPointCoilOuterEndCoordinate(int sectionNumber, double endX, double endY)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZCoilOuterEnd.setCoordinate(endX, endY);
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZCoilOuterEnd.setCoordinate(endX, endY);
                break;
        }
    }

    public void setElementPointOuterStart(ElementPoint startPt, int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZOuterStart = startPt;
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZOuterStart = startPt;
                break;
            case DefineSystemConstant.XYView:
                break;
        }
    }



    public void save(FileWriter fw) throws IOException
    {
        String className = this.getClass().getCanonicalName();
        fw.write("**********************"+className+"**********************\n");
        //Coil Type
        fw.write("     Coil Type : , " + _geometryType + "\n");
        //Coil XZ Section
        fw.write("        XZ End(X): ," + _elementPtXZEnd.X()     + "\n");
        fw.write("        XZ End(Z): ," + _elementPtXZEnd.Y()     + "\n");
        fw.write(" XZ Coil Start(X): ," + _elementPtXZCoilStart.X() + "\n");
        fw.write(" XZ Coil Start(Z): ," + _elementPtXZCoilStart.Y() + "\n");
        fw.write("   XZ Coil End(X): ," + _elementPtXZCoilEnd.X()   + "\n");
        fw.write("   XZ Coil End(Z): ," + _elementPtXZCoilEnd.Y()   + "\n");

        //Coil YZ Section
        fw.write("        YZ End(Y): ," + _elementPtYZEnd.X()     + "\n");
        fw.write("        YZ End(Z): ," + _elementPtYZEnd.Y()     + "\n");
        fw.write(" YZ Coil Start(Y): ," + _elementPtYZCoilStart.X() + "\n");
        fw.write(" YZ Coil Start(Z): ," + _elementPtYZCoilStart.Y() + "\n");
        fw.write("   YZ Coil End(Y): ," + _elementPtYZCoilEnd.X()   + "\n");
        fw.write("   YZ Coil End(Z): ," + _elementPtYZCoilEnd.Y()   + "\n");

        //Coil Mesh Count
        fw.write("          Is Perodical: , " + this._isPerodical           + "\n");
        fw.write("          Count Radial: , " + this._countRadial           + "\n");
        fw.write(" Count Circumferential: , " + this._countCircumferential  + "\n");
        fw.write("      Count Periodical: , " + this._countPeriodical       + "\n");

        fw.write("             Thickness: , " + this._thickness       + "\n");
        fw.write("      Thickness Former: , " + this._thicknessFormer   + "\n");

        this._material.save(fw);
    }

    public void open(BufferedReader br) throws IOException
    {
        br.readLine().trim();

        this._geometryType        =       Integer.parseInt(this.readLastString(br.readLine().trim()));

        double tubeEndPtXZ_X =        Double.parseDouble(this.readLastString(br.readLine().trim()));//6_2
        double tubeEndPtXZ_Y =        Double.parseDouble(this.readLastString(br.readLine().trim()));//6_3
        double coilStartPtXZ_X =      Double.parseDouble(this.readLastString(br.readLine().trim()));//6_4
        double coilStartPtXZ_Y =      Double.parseDouble(this.readLastString(br.readLine().trim()));//6_5
        double coilEndPtXZ_X =        Double.parseDouble(this.readLastString(br.readLine().trim()));//6_6
        double coilEndPtXZ_Y =        Double.parseDouble(this.readLastString(br.readLine().trim()));//6_7

        double tubeEndPtYZ_X =        Double.parseDouble(this.readLastString(br.readLine().trim()));//6_8
        double tubeEndPtYZ_Y =        Double.parseDouble(this.readLastString(br.readLine().trim()));//6_9
        double coilStartPtYZ_X =      Double.parseDouble(this.readLastString(br.readLine().trim()));//6_10
        double coilStartPtYZ_Y =      Double.parseDouble(this.readLastString(br.readLine().trim()));//6_11
        double coilEndPtYZ_X =        Double.parseDouble(this.readLastString(br.readLine().trim()));//6_12
        double coilEndPtYZ_Y =        Double.parseDouble(this.readLastString(br.readLine().trim()));//6_13


        this._isPerodical            =       Boolean.parseBoolean(this.readLastString(br.readLine().trim()));
        this._countRadial            =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countCircumferential   =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countPeriodical        =       Integer.parseInt(this.readLastString(br.readLine().trim()));

        this._thickness              =        Double.parseDouble(this.readLastString(br.readLine().trim()));
        this._thicknessFormer        =        Double.parseDouble(this.readLastString(br.readLine().trim()));

        double thicknessInnerOuter = 0;
        if (this._geometryType == DefineSystemConstant.COIL_TYPE1)
        {
            thicknessInnerOuter = _thickness;
        }
        else
        {
            thicknessInnerOuter = _thicknessFormer;
        }


        this.setElementPointEndCoordinate       (sdt.define.DefineSystemConstant.XZView, tubeEndPtXZ_X, tubeEndPtXZ_Y);
        this.setElementPointCoilStartCoordinate (sdt.define.DefineSystemConstant.XZView, coilStartPtXZ_X, coilStartPtXZ_Y);
        this.setElementPointCoilEndCoordinate   (sdt.define.DefineSystemConstant.XZView, coilEndPtXZ_X, coilEndPtXZ_Y);
        this.setElementPointCoilOuterStartCoordinate (sdt.define.DefineSystemConstant.XZView, coilStartPtXZ_X + _thickness+_thicknessFormer, coilStartPtXZ_Y);
        this.setElementPointCoilOuterEndCoordinate (sdt.define.DefineSystemConstant.XZView, coilEndPtXZ_X + _thickness+_thicknessFormer, coilEndPtXZ_Y);


        this.setElementPointEndCoordinate       (sdt.define.DefineSystemConstant.YZView, tubeEndPtYZ_X, tubeEndPtYZ_Y);
        this.setElementPointCoilStartCoordinate (sdt.define.DefineSystemConstant.YZView, coilStartPtYZ_X, coilStartPtYZ_Y);
        this.setElementPointCoilEndCoordinate   (sdt.define.DefineSystemConstant.YZView, coilEndPtYZ_X, coilEndPtYZ_Y);
        this.setElementPointCoilOuterStartCoordinate (sdt.define.DefineSystemConstant.YZView, coilStartPtYZ_X + _thickness+_thicknessFormer, coilStartPtYZ_Y);
        this.setElementPointCoilOuterEndCoordinate (sdt.define.DefineSystemConstant.YZView, coilEndPtYZ_X + _thickness+_thicknessFormer, coilEndPtYZ_Y);

        this._elementPtXZOuterEnd.setCoordinate(tubeEndPtXZ_X + thicknessInnerOuter, tubeEndPtXZ_Y);
        this._elementPtYZOuterEnd.setCoordinate(tubeEndPtYZ_X + thicknessInnerOuter, tubeEndPtYZ_Y);



        this._material.open(br);
    }
    public double getFormerThickness()
    {
        if (this._geometryType == DefineSystemConstant.COIL_TYPE2)
            _thicknessFormer = _elementPtXZOuterStart.X() - this._point3DStartSection1.X();
        return this._thicknessFormer;
    }


    public void setFormerThickness(double thick)
    {
        this._thicknessFormer = thick;
         this.setAllElementPointsByType();
    }
    public double getThickness()
    {
        if (this._geometryType == DefineSystemConstant.COIL_TYPE1)
            _thickness = _elementPtXZOuterStart.X() - this._point3DStartSection1.X();
        else
            _thickness = _elementPtXZCoilOuterStart.X() - _elementPtXZOuterStart.X();

        return this._thickness;
    }

    public void setThickness(double thick)
    {
        this._thickness = thick;
        this.setAllElementPointsByType();

    }




}
