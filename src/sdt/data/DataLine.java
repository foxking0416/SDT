package sdt.data;

import sdt.stepb.stepb_cartesian_point;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.awt.Color;
import sdt.geometry.element.ElementPoint;

public abstract class DataLine extends DataShell
{

    public DataLine(SDT_DataManager dataManager)
    {
        super(dataManager);
    }




    protected void setElementXYToData()
    {
    }

    protected void setDataToElementXZ()
    {
    }

    protected void setDataToElementYZ()
    {
    }

    protected void createDataPointPeriodically(stepb_cartesian_point[][] ptSets)
    {
        System.out.println("createDataPointPeriodically @ DataLine");
        int kCount = this._countPeriodical ;//ptSets.length;
        int jCount = this._countCircumferential ;//ptSets[0].length;
        int iCount = this._countRadial;//ptSets[0][0].length;

        double thetaIntervalK = 2 * Math.PI / kCount;
        double thetaIntervalJ = 2 * Math.PI / kCount / (jCount - 1);
        double theta = 0;


        double rS = this._point3DStartSection1.X();
        double rE = this._point3DEndSection1.X();

        double hS = this._point3DStartSection1.Z();
        double hE = this._point3DEndSection1.Z();

        for (int k = 0; k < kCount; k++)
        {
            for (int j = 0; j < jCount - 1; j++)
            {
                theta = k * thetaIntervalK + j * thetaIntervalJ;
                if (!this._dataManager.getIsStartFromAxis())
                    theta += thetaIntervalJ / 2.0;
                double xS = rS * Math.cos(theta);
                double yS = rS * Math.sin(theta);
                double zS = hS;
                double xE = rE * Math.cos(theta);
                double yE = rE * Math.sin(theta);
                double zE = hE;
                stepb_cartesian_point cPtS = new stepb_cartesian_point(xS, yS, zS);
                stepb_cartesian_point cPtE = new stepb_cartesian_point(xE, yE, zE);

                ptSets[k * (jCount - 1) + j][0] = cPtS;
                ptSets[k * (jCount - 1) + j][iCount - 1] = cPtE;

                for (int i = 1; i < iCount - 1; i++)
                {
                    double rM = rS + (rE - rS) / (iCount - 1) * i;
                    double hM = hS + (hE - hS) / (iCount - 1) * i;

                    double xM = rM * Math.cos(theta);
                    double yM = rM * Math.sin(theta);
                    double zM = hM;

                    stepb_cartesian_point cPtM = new stepb_cartesian_point(xM, yM, zM);

                    ptSets[k * (jCount - 1) + j][i] = cPtM;
                }
            }
        }
    }

    protected void createDataPointCircumferential(stepb_cartesian_point[][] ptSets)
    {
        System.out.println("createDataPointCircumferential @ DataLine");
        int jCount = ptSets.length;
        int iCount = ptSets[0].length;

        double thetaIntervalJ = 2 * Math.PI / jCount;
        double theta = 0;


        double rS = this._point3DStartSection1.X();
        double rE = this._point3DEndSection1.X();

        double hS = this._point3DStartSection1.Z();
        double hE = this._point3DEndSection1.Z();

        for (int j = 0; j < jCount; j++)
        {
            theta = j * thetaIntervalJ;
            if(!this._dataManager.getIsStartFromAxis())
               theta+=  thetaIntervalJ/2.0;
            ptSets[j] = this.setLinePoint3D_Round(rS, rE, hS, hE,  theta, iCount);
        }
    }

    protected void createDataPointCircumferentialRunway(stepb_cartesian_point[][] ptSetsCircumferential)
    {

        System.out.println("createDataPointCircumferentialRunway @ DataLine");
        this.detectedIsRoundRunway();

        if (this._isRoundRunway) //Inner is round
        {

            this.setCircumferentialNumber(true, true);

            this.setRoundInner(); //elementArrayInner is done
            this.setRunwayOuter();
            //System.out.println("Inner is round.");
        }
        else //Inner is runway
        {
            //this.setCircumferentialNumber(true, false);
            //    this.setRunwayInner();

            if (this._dataManager.getDataCap().getGeometryType() == this.CAP_TYPE_ROUNDCUT)
            {
                this.setCircumferentialNumber(true, true);
                this.setRoundCutInner();
                this.setRoundCutOuter();

            }
            else
            {
                this.setCircumferentialNumber(true, false);
                this.setRunwayInner();
                this.setRunwayOuter();
            }
        }



        double hS = this._point3DStartSection1.Z();//transition start point absolute height (means coordinate)
        double hE = this._point3DEndSection1.Z();//transition end point absolute height(means coordinate)

        if (this._dataManager.getDataCap().getGeometryType() == this.CAP_TYPE_ROUNDCUT)
        {
             this.setPointCircumferential_RoundCut(ptSetsCircumferential, hS, hE);
        }
        else
        {
            this.setPointCircumferential_Runway(ptSetsCircumferential, hS, hE);
        }
    }

    public void setPointCircumferential_Runway(stepb_cartesian_point[][] ptSetsCircumferential, double hS, double hE)
    {
        int iiCount = ptSetsCircumferential[0].length;
        int jCount = ptSetsCircumferential.length;

        for (int j = 0; j < this._numberFan / 2; j++) //1
        {
            ptSetsCircumferential[j] = this.setLinePoint3D_RunwayFan(this._elementArrayInner[j], this._elementArrayOuter[j], hS, hE, iiCount);
        }

        for (int j = this._numberFan / 2; j < this._numberFan / 2 + this._numberTri + 1; j++) //2nd area
        {
            ptSetsCircumferential[j] = this.setLinePoint3D_RunwayTri(this._elementArrayInner[j], this._elementArrayOuter[j], hS, hE, iiCount);
        }

        for (int j = this._numberFan / 2 + this._numberTri + 1;
                     j < this._numberFan / 2 + this._numberTri + this._numberFan; j++) //3rd area
        {
            ptSetsCircumferential[j] = this.setLinePoint3D_RunwayFan(this._elementArrayInner[j], this._elementArrayOuter[j], hS, hE, iiCount);
        }

        for (int j = this._numberFan / 2 + this._numberTri + this._numberFan;
                     j < this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j++) //4th area
        {
            ptSetsCircumferential[j] = this.setLinePoint3D_RunwayTri(this._elementArrayInner[j], this._elementArrayOuter[j], hS, hE, iiCount);
        }

        for (int j = this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j < jCount; j++) //5th area
        {
            ptSetsCircumferential[j] = this.setLinePoint3D_RunwayFan(this._elementArrayInner[j], this._elementArrayOuter[j], hS, hE, iiCount);
        }

    }

    public void setPointCircumferential_RoundCut(stepb_cartesian_point[][] ptSetsCircumferential, double hS, double hE)
    {
        int iiCount = ptSetsCircumferential[0].length;
        int jCount = ptSetsCircumferential.length;
        for (int j = 0; j < jCount; j++)
        {
            ptSetsCircumferential[j] = this.setLinePoint3D_RunwayFan(this._elementArrayInner[j], this._elementArrayOuter[j], hS, hE, iiCount);
        }
    }





}
