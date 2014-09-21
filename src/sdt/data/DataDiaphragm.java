package sdt.data;

import java.awt.*;
import java.io.*;

import sdt.define.*;
import sdt.geometry.element.*;
import sdt.stepb.*;

public class DataDiaphragm extends DataLine
{
    ElementPoint _elementPtXZRidge;
    ElementPoint _elementPtYZRidge;

    double _ridgePtY_XZ;
    double _ridgePtX_XZ;
    double _radiusXZ;

    double _ridgePtX_YZ;
    double _ridgePtY_YZ;
    double _radiusYZ;


    public DataDiaphragm(SDT_DataManager dataManager)
    {
        super(dataManager);
        _dataType = this.TYPE_DIAPHRAGM;
        _point3DStartSection1 = new stepb_cartesian_point(1.5, 0, 0);
        _point3DEndSection1 = new stepb_cartesian_point(2.5, 0, 0.4);
        _point3DStartSection2 = new stepb_cartesian_point(0, 1.5, 0);
        _point3DEndSection2 = new stepb_cartesian_point(0, 2.5, 0.4);

        _countGirth = dataManager.getCountGirth();
        _countRadial = 4;
        _countPeriodical = 16;
        _countCircumferential = _countGirth / _countPeriodical + 1; //11
        _thickness = 0.05;


        this._colorBody       = this._dataManager.getColorDiaphragm();
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);

        this._ridgePtX_XZ = 2;
        this._ridgePtY_XZ = 0.5;
        this._radiusXZ = 0.15;

        this._ridgePtX_YZ = 2;
        this._ridgePtY_YZ = 0.5;
        this._radiusYZ = 0.15;

        this._geometryType = DefineSystemConstant.DIAPHRAGM_TYPE1;
        this._elementPtXZRidge = new ElementPoint(this._ridgePtX_XZ, this._ridgePtY_XZ, _colorPt,this);
        this._elementPtYZRidge = new ElementPoint(this._ridgePtX_YZ, this._ridgePtY_YZ, _colorPt,this);

    }

    protected void setDataToElementXZ()
    {
        if (this._elementPtXZStart == null)
            this._elementPtXZStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Z(), _colorPt,this);
        if (this._elementPtXZEnd == null)
            this._elementPtXZEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Z(), _colorPt,this);
        this._elmementXZ = new ElementDiaphragm(this, this.XZView, this._colorBody);
        _isXYupdateNeed = true;

    }

    protected void setDataToElementYZ()
    {
        if (this._elementPtYZStart == null)
            this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt,this);
        if (this._elementPtYZEnd == null)
            this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt,this);
        this._elmementYZ = new ElementDiaphragm(this, this.YZView, this._colorBody);
        _isXYupdateNeed = true;
    }



    protected void createDataPointCircumferentialRunway(stepb_cartesian_point[][] ptSetsCircumferential)
    {
        this.detectedIsRoundRunway();

        if (this._isRoundRunway) //Inner is round
        {
            this.setCircumferentialNumber(true, true);
            this.setRoundInner(); //elementArrayInner is done
          }
        else //Inner is runway(包含圓切邊型以及跑道型)
        {
            if (this._dataManager.getDataCap().getGeometryType() == this.CAP_TYPE_ROUNDCUT)
            {
                this.setCircumferentialNumber(true, true);
                this.setRoundCutInner();
            }
            else
            {
                this.setCircumferentialNumber(true, false);
                this.setRunwayInner();
            }
        }
        this.setRunwayOuter();


        double hS = this._point3DStartSection1.Z(); //transition start point absolute height
        double hE = this._point3DEndSection1.Z();   //transition end point absolute height

        if (this._geometryType == DefineSystemConstant.DIAPHRAGM_TYPE2) //元寶型
            this.setPointCircumferential_Type2(ptSetsCircumferential, hS, hE);
        else
            this.setPointCircumferential_Runway(ptSetsCircumferential, hS, hE);
    }

    /**
     * Sweep out 元寶型 diaphragm
     * @param ptSetsCircumferential cartesian point array of Diaphragm (using i & j)
     * @param hS diaphragm Start Point Y coordinate
     * @param hE diaphragm End Point Y coordinate
     */
    public void setPointCircumferential_Type2(stepb_cartesian_point[][] ptSetsCircumferential, double hS, double hE)
    {
        int iiCount = ptSetsCircumferential[0].length;
        int jCount = ptSetsCircumferential.length;


        ElementPoint elementTopPtAtZeroDegree = new ElementPoint(this._elementPtXZRidge.X(), 0, this._colorPt,this);

        int[] meshDensity = this.calculateMeshDensity(this._elementArrayInner[0], this._elementArrayOuter[0], elementTopPtAtZeroDegree, this._elementPtXZRidge.Y(), hS, hE, this._radiusXZ, iiCount);

        double outerCriticalWidth = this._point3DEndSection1.X() - this._point3DEndSection2.Y();
        double innerCriticalWidth = this._point3DStartSection1.X() - this._point3DStartSection2.Y();

        ElementPoint sectionXZStartPt = new ElementPoint(this._point3DStartSection1.X(), 0, Color.WHITE,this);
        ElementPoint sectionXZEndPt   = new ElementPoint(this._point3DEndSection1.X(), 0, Color.WHITE,this);

        stepb_cartesian_point[] sectionXZPtsArray = this.setLinePoint3D_RunwayFan_Type2(sectionXZStartPt, sectionXZEndPt, elementTopPtAtZeroDegree, this._elementPtXZRidge.Y(), hS, hE, this._radiusXZ, iiCount, meshDensity[0],
          meshDensity[1], meshDensity[2]);

        double[] lengthScale = new double[sectionXZPtsArray.length];
        for(int i = 0; i < sectionXZPtsArray.length; i++)
        {
            lengthScale[i] = (sectionXZPtsArray[i].X() - sectionXZPtsArray[0].X()) / (sectionXZPtsArray[sectionXZPtsArray.length - 1].X() - sectionXZPtsArray[0].X());
        }


        ElementPoint sectionYZStartPt = new ElementPoint(0, this._point3DStartSection2.Y(), Color.WHITE,this);
        ElementPoint sectionYZEndPt = new ElementPoint(0, this._point3DEndSection2.Y(), Color.WHITE,this);

        stepb_cartesian_point[] sectionYZPtsArray = this.setLinePoint3D_YZViewReferToXZ(sectionYZStartPt, sectionYZEndPt, lengthScale, hS, hE, iiCount);


        for (int j = 0; j < this._numberFan / 2; j++) //1
        {
            ////////////////////
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];

            for (int i = 0; i < iiCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                double sectionXZ_X = cartesianPtXZ.X();
                double sectionXZ_Y = cartesianPtXZ.Z();
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iiCount - 1];
                double h = cartesianPtYZ.Y();


                double w = innerCriticalWidth + (outerCriticalWidth - innerCriticalWidth) * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

                //////////////////////各象限唯一不同的地方////////////////////////////
                double tempValue = (this._elementArrayInner[j].Y() + lineAngle * w - lineAngle * this._elementArrayInner[j].X()) /
                                   (h * Math.sqrt(1 + lineAngle * lineAngle));

                double theta;
                if ((lineAngleTheta >= 0 && lineAngleTheta <= Math.PI / 2) || (lineAngleTheta >= Math.PI * 3 / 2 && lineAngleTheta <= 2 * Math.PI))
                    theta = Math.asin(tempValue) + lineAngleTheta;
                else
                    theta = Math.asin( -1 * tempValue) + lineAngleTheta;

                double x = w + h * Math.cos(theta);
                double y = h * Math.sin(theta);
                ////////////////////////////////////////////////////////////////////

                double thetaModify = theta;
                thetaModify = this.modifyAngle(thetaModify);
                if (thetaModify > Math.PI / 2 && thetaModify <= Math.PI)
                    thetaModify = Math.PI - thetaModify;
                else if (thetaModify > Math.PI && thetaModify <= Math.PI * 3 / 2.0)
                    thetaModify = thetaModify - Math.PI;
                else if (thetaModify > Math.PI * 3 / 2.0 && thetaModify <= Math.PI * 2)
                    thetaModify = 2 * Math.PI - thetaModify;

                double heightDelta = sectionXZ_Y - sectionYZ_Y;
                double z = sectionXZ_Y - heightDelta * Math.abs(thetaModify) / (Math.PI / 2.0);

                stepb_cartesian_point fanCartesianPoint = new stepb_cartesian_point(x, y, z);
                ptArray[i] = fanCartesianPoint;
            }
            ptSetsCircumferential[j] = ptArray;
            ////////////////////

        }

        for (int j = this._numberFan / 2; j < this._numberFan / 2 + this._numberTri + 1; j++) //2nd area
        {

            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];

            for (int i = 0; i < iiCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                double sectionXZ_X = cartesianPtXZ.X();
                double sectionXZ_Y = cartesianPtXZ.Z();
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                double y = cartesianPtYZ.Y();
                double z = cartesianPtYZ.Z();
                double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;
                stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z); //三角形區域中的點

                stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
              stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iiCount - 1];
              double h = cartesianPtYZ.Y();
              double w = innerCriticalWidth + (outerCriticalWidth - innerCriticalWidth) * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

              double newX;
              double newY;
              double newZ;
              double theta;

              if (this._elementArrayInner[j].X() >= 0)
              {
                  double tempValue = (this._elementArrayInner[j].Y() + lineAngle * w - lineAngle * this._elementArrayInner[j].X()) /
                                     (h * Math.sqrt(1 + lineAngle * lineAngle));
                  if ((lineAngleTheta >= 0 && lineAngleTheta <= Math.PI / 2) || (lineAngleTheta >= Math.PI * 3 / 2 && lineAngleTheta <= 2 * Math.PI))
                      theta = Math.asin(tempValue) + lineAngleTheta;
                  else
                      theta = Math.asin( -1 * tempValue) + lineAngleTheta;

                  newX = w + h * Math.cos(theta);
                  newY = h * Math.sin(theta);
              }
              else
              {
                  double tempValue = (this._elementArrayInner[j].Y() - lineAngle * w - lineAngle * this._elementArrayInner[j].X()) /
                                     (h * Math.sqrt(1 + lineAngle * lineAngle));

                  if ((lineAngleTheta >= 0 && lineAngleTheta <= Math.PI / 2) || (lineAngleTheta >= Math.PI * 3 / 2 && lineAngleTheta <= 2 * Math.PI))
                      theta = Math.asin(tempValue) + lineAngleTheta;
                  else
                      theta = Math.asin( -1 * tempValue) + lineAngleTheta;

                  newX = -w + h * Math.cos(theta);
                  newY = h * Math.sin(theta);
              }

              double thetaModify = theta;
              thetaModify = this.modifyAngle(thetaModify);
              if (thetaModify > Math.PI / 2 && thetaModify <= Math.PI)
                  thetaModify = Math.PI - thetaModify;
              else if (thetaModify > Math.PI && thetaModify <= Math.PI * 3 / 2.0)
                  thetaModify = thetaModify - Math.PI;
              else if (thetaModify > Math.PI * 3 / 2.0 && thetaModify <= Math.PI * 2)
                  thetaModify = 2 * Math.PI - thetaModify;

              double heightDelta = sectionXZ_Y - sectionYZ_Y;
              newZ = sectionXZ_Y - heightDelta * Math.abs(thetaModify) / (Math.PI / 2.0);

              stepb_cartesian_point fanCartesianPoint = new stepb_cartesian_point(newX, newY, newZ);

              if (x > w || x < -w)
              {
                  ptArray[i] = fanCartesianPoint;
              }
              else
              {
                  ptArray[i] = triCartesianPoint;
              }
            }
            ptSetsCircumferential[j] = ptArray;
        }

        for (int j = this._numberFan / 2 + this._numberTri + 1;
                     j < this._numberFan / 2 + this._numberTri + this._numberFan; j++) //3rd area
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];
            for (int i = 0; i < iiCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                double sectionXZ_X = cartesianPtXZ.X();
                double sectionXZ_Y = cartesianPtXZ.Z();
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iiCount - 1];
                double h = cartesianPtYZ.Y();
                double w = innerCriticalWidth + (outerCriticalWidth - innerCriticalWidth) * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

                //////////////////////各象限唯一不同的地方////////////////////////////
                double tempValue = (this._elementArrayInner[j].Y() - lineAngle * w - lineAngle * this._elementArrayInner[j].X()) /
                                   (h * Math.sqrt(1 + lineAngle * lineAngle));

                double theta;

                if ((lineAngleTheta >= 0 && lineAngleTheta <= Math.PI / 2) || (lineAngleTheta >= Math.PI * 3 / 2 && lineAngleTheta <= 2 * Math.PI))
                    theta = Math.asin(tempValue) + lineAngleTheta;
                else
                    theta = Math.asin( -1 * tempValue) + lineAngleTheta;

                double x = -w + h * Math.cos(theta);
                double y = h * Math.sin(theta);
                ////////////////////////////////////////////////////////////////////


                double thetaModify = theta;
                thetaModify = this.modifyAngle(thetaModify);
                if (thetaModify > Math.PI / 2 && thetaModify <= Math.PI)
                    thetaModify = Math.PI - thetaModify;
                else if (thetaModify > Math.PI && thetaModify <= Math.PI * 3 / 2.0)
                    thetaModify = thetaModify - Math.PI;
                else if (thetaModify > Math.PI * 3 / 2.0 && thetaModify <= Math.PI * 2)
                    thetaModify = 2 * Math.PI - thetaModify;

                double heightDelta = sectionXZ_Y - sectionYZ_Y;
                double z = sectionXZ_Y - heightDelta * Math.abs(thetaModify) / (Math.PI / 2.0);

                stepb_cartesian_point fanCartesianPoint = new stepb_cartesian_point(x, y, z);
                ptArray[i] = fanCartesianPoint;
            }
            ptSetsCircumferential[j] = ptArray;
        }

        for (int j = this._numberFan / 2 + this._numberTri + this._numberFan;
                     j < this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j++) //4th area
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];
            for (int i = 0; i < iiCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

               stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
               stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

               double sectionXZ_X = cartesianPtXZ.X();
               double sectionXZ_Y = cartesianPtXZ.Z();
               double sectionYZ_X = cartesianPtYZ.Y();
               double sectionYZ_Y = cartesianPtYZ.Z();

               double y = -1 * cartesianPtYZ.Y();
               double z = cartesianPtYZ.Z();
               double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;

               stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z);

               stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
               stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iiCount - 1];
               double h = cartesianPtYZ.Y();
               double w = innerCriticalWidth + (outerCriticalWidth - innerCriticalWidth) * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());


               double newX;
               double newY;
               double newZ;
               if (lineAngle < 0)
               {
                   double tempCalculateApha = Math.asin(lineAngle / Math.sqrt(1 + lineAngle * lineAngle));
                   double tempValue = (lineAngle * w - lineAngle * this._elementArrayInner[j].X() + this._elementArrayInner[j].Y()) /
                                      (h * Math.sqrt(1 + lineAngle * lineAngle));

                   double theta = Math.asin(tempValue) + tempCalculateApha;

                   newX = w + h * Math.cos(theta);
                   newY = h * Math.sin(theta);

                   double heightDelta = sectionXZ_Y - sectionYZ_Y;
                   newZ = sectionXZ_Y - heightDelta * Math.abs(theta) / (Math.PI / 2.0);

               }
               else
               {
                   double tempCalculateApha = Math.asin(lineAngle / Math.sqrt(1 + lineAngle * lineAngle));
                   double tempValue = (this._elementArrayInner[j].Y() - lineAngle * w - lineAngle * this._elementArrayInner[j].X()) /
                                      (h * Math.sqrt(1 + lineAngle * lineAngle));

                   double theta = Math.asin(tempValue) - tempCalculateApha;

                   newX = -w - h * Math.cos(theta);
                   newY = h * Math.sin(theta);

                   double heightDelta = sectionXZ_Y - sectionYZ_Y;
                   newZ = sectionXZ_Y - heightDelta * Math.abs(theta) / (Math.PI / 2.0);

               }
               stepb_cartesian_point fanCartesianPoint = new stepb_cartesian_point(newX, newY, newZ);

               if ( -x > w || -x < -w)
               {
                   ptArray[i] = fanCartesianPoint;
               }
               else
               {
                   ptArray[i] = triCartesianPoint;
               }
           }
           ptSetsCircumferential[j] = ptArray;

        }
        for (int j = this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j < jCount; j++) //5th area
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];
            for (int i = 0; i < iiCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                double sectionXZ_X = cartesianPtXZ.X();
                double sectionXZ_Y = cartesianPtXZ.Z();
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iiCount - 1];

                double h = cartesianPtYZ.Y();
                double w = innerCriticalWidth + (outerCriticalWidth - innerCriticalWidth) * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());


                //////////////////////各象限唯一不同的地方////////////////////////////
                double tempValue = (this._elementArrayInner[j].Y() + lineAngle * w - lineAngle * this._elementArrayInner[j].X()) /
                                  (h * Math.sqrt(1 + lineAngle * lineAngle));

               double theta;
               if ((lineAngleTheta >= 0 && lineAngleTheta <= Math.PI / 2) || (lineAngleTheta >= Math.PI * 3 / 2 && lineAngleTheta <= 2 * Math.PI))
                   theta = Math.asin(tempValue) + lineAngleTheta;
               else
                   theta = Math.asin( -1 * tempValue) + lineAngleTheta;


               double x = w + h * Math.cos(theta);
               double y = h * Math.sin(theta);
               ////////////////////////////////////////////////////////////////////

               double thetaModify = theta;
               thetaModify = this.modifyAngle(thetaModify);
               if(thetaModify > Math.PI / 2 && thetaModify <= Math.PI )
                   thetaModify = Math.PI - thetaModify;
               else if(thetaModify > Math.PI && thetaModify <= Math.PI * 3 / 2.0)
                   thetaModify = thetaModify - Math.PI;
               else if(thetaModify > Math.PI * 3 / 2.0 && thetaModify <= Math.PI * 2 )
                   thetaModify =  2 * Math.PI - thetaModify;


               double heightDelta = sectionXZ_Y - sectionYZ_Y;
               double z = sectionXZ_Y - heightDelta * Math.abs(thetaModify) / (Math.PI / 2.0);
               stepb_cartesian_point fanCartesianPoint = new stepb_cartesian_point(x, y, z);
               ptArray[i] = fanCartesianPoint;
            }
            ptSetsCircumferential[j] = ptArray;
        }
    }


    public stepb_cartesian_point[] setLinePoint3D_RunwayFan_Type2(ElementPoint startPt, ElementPoint endPt,
            ElementPoint ridgePt, double hT,
            double hS, double hE, double radius, int iiCount, int iCountIn, int iCountArc, int iCountOut)
    {
        stepb_cartesian_point ptsArray[] = new stepb_cartesian_point[iiCount];

        double dx = endPt.X() - startPt.X();
        double dy = endPt.Y() - startPt.Y();
        double theta = this.calculateTheta(dx, dy);
        double width = Math.sqrt(dx * dx + dy * dy);

        double dxIn = ridgePt.X() - startPt.X();
        double dyIn = ridgePt.Y() - startPt.Y();
        double widthIn = Math.sqrt(dxIn * dxIn + dyIn * dyIn);

        double dxOut = endPt.X() - ridgePt.X();
        double dyOut = endPt.Y() - ridgePt.Y();
        double widthOut = Math.sqrt(dxOut * dxOut + dyOut * dyOut);

        /////////////////////////////////////////////////
        double arcCenterY = hT - radius;
        double arcCenterX = widthIn;

        double lengthSPt2CPt = Math.sqrt(Math.pow(widthIn, 2) + Math.pow(arcCenterY - hS, 2));
        double lengthEPt2CPt = Math.sqrt(Math.pow(widthOut, 2) + Math.pow(arcCenterY - hE, 2));
        double lengthSPt2TangentPt = Math.sqrt(Math.pow(lengthSPt2CPt, 2) - Math.pow(radius, 2));
        double lengthEPt2TangentPt = Math.sqrt(Math.pow(lengthEPt2CPt, 2) - Math.pow(radius, 2));

        double angleSPt2CPt = this.calculateTheta(widthIn, arcCenterY - hS);
        double angleEPt2CPt = this.calculateTheta(-widthOut, arcCenterY - hE);
        double angleTangentStartCenter = Math.acos(lengthSPt2TangentPt / lengthSPt2CPt);
        double angleTangentEndCenter = Math.acos(lengthEPt2TangentPt / lengthEPt2CPt);

        double tangentPt1X = lengthSPt2TangentPt * Math.cos(angleTangentStartCenter + angleSPt2CPt);
        double tangentPt1Y = hS + lengthSPt2TangentPt * Math.sin(angleTangentStartCenter + angleSPt2CPt);

        double tangentPt2X = widthIn + widthOut + lengthEPt2TangentPt * Math.cos(angleEPt2CPt - angleTangentEndCenter);
        double tangentPt2Y = hE + lengthEPt2TangentPt * Math.sin(angleEPt2CPt- angleTangentEndCenter);


        double angleCPt2TangentPt1 = this.calculateTheta(tangentPt1X - arcCenterX, tangentPt1Y - arcCenterY);
        double angleCPt2TangentPt2 = this.calculateTheta(tangentPt2X - arcCenterX, tangentPt2Y - arcCenterY);
        double angleTangentCPtTangent = angleCPt2TangentPt1 - angleCPt2TangentPt2;


       double rIncrementIn = tangentPt1X / (double)iCountIn;
       double hIncrementIn = (tangentPt1Y - hS) / (double)iCountIn;


       double anglePeriod = angleTangentCPtTangent / (double)iCountArc;

       double rIncrementOut = (width - tangentPt2X)/ (double)iCountOut;
       double hIncrementOut = (hE - tangentPt2Y) / (double)iCountOut;




       for (int ii = 0; ii < iCountIn; ii++)//In
       {
           double x = startPt.X() + rIncrementIn * ii * Math.cos(theta);
           double y = startPt.Y() + rIncrementIn * ii * Math.sin(theta);
           double z = hS +          hIncrementIn * ii;

           stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);

           ptsArray[ii] = cPt;
       }

       for (int ii = iCountIn; ii < iCountIn + iCountArc; ii++)//Arc
       {
           double x = startPt.X() + (tangentPt1X + (arcCenterX - tangentPt1X) + radius * Math.cos(angleCPt2TangentPt1 - anglePeriod * (ii - iCountIn))) * Math.cos(theta);
           double y = startPt.Y() + (tangentPt1X + (arcCenterX - tangentPt1X) + radius * Math.cos(angleCPt2TangentPt1 - anglePeriod * (ii - iCountIn))) * Math.sin(theta);
           double z = hS +          (tangentPt1Y - hS) + (arcCenterY - tangentPt1Y)+ radius * Math.sin(angleCPt2TangentPt1 - anglePeriod * (ii - iCountIn));

           stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);

           ptsArray[ii] = cPt;
       }

       for (int ii = iCountIn + iCountArc; ii < iiCount; ii++)//Out
       {
           double x = startPt.X() + (tangentPt2X + rIncrementOut * (ii - iCountIn - iCountArc)) * Math.cos(theta);
           double y = startPt.Y() + (tangentPt2X + rIncrementOut * (ii - iCountIn - iCountArc)) * Math.sin(theta);
           double z = hS +          (tangentPt1Y - hS) + (tangentPt2Y - tangentPt1Y) + hIncrementOut * (ii - iCountIn - iCountArc);

           stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);

           ptsArray[ii] = cPt;
       }

        return ptsArray;
    }

    /**
     * 因為Type2的Diaphragm的Ridge部分需要比較密的mesh來描述特徵
     * 因此即使Diaphragm在YZ方向的剖面是單純一條直線，mesh理應是等份，但是為了配合XZ方向Ridge造成的Mesh疏密不同
     * 重新計算了YZ方向mesh
     *  <br>
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/setLinePoint3D_YZViewReferToXZ.png"  align="center" class="border" width="400" height="259" />
     * </pre>
     * <br>
     * @param startPt XY_View的Start的ElementPoint
     * @param endPt XY_View的End的ElementPoint
     * @param ptPosScale 將XZ方向各個點的位置比例傳進來，請參閱圖
     * @param hS diaphragm Start Point Y coordinate
     * @param hE diaphragm End Point Y coordinate
     * @param iiCount Total node numbers of Diaphragm on radial
     * @return stepb_cartesian_point[]
     */
    public stepb_cartesian_point[] setLinePoint3D_YZViewReferToXZ(ElementPoint startPt, ElementPoint endPt,
           double[] ptPosScale, double hS, double hE, int iiCount)
    {
        double dx = endPt.X() - startPt.X();
        double dy = endPt.Y() - startPt.Y();
        double theta = this.calculateTheta(dx, dy);
        double width = Math.sqrt(dx * dx + dy * dy);

        stepb_cartesian_point ptsArray[] = new stepb_cartesian_point[iiCount];

        for (int ii = 0; ii < iiCount; ii++)
        {
            double rIncrement = width * ptPosScale[ii];
            double hIncrement = (hE - hS) * ptPosScale[ii];

            double x = startPt.X() + rIncrement * Math.cos(theta);
            double y = startPt.Y() + rIncrement * Math.sin(theta);
            double z = hS + hIncrement;

            stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);

            ptsArray[ii] = cPt;
        }
        return ptsArray;
    }



    /**
     * Get the ridge point X value
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @return X value of diaphragm ridge point
     */
    public double getDiaphragmRidgePtX(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZRidge.X();
            case DefineSystemConstant.YZView:
                return this._elementPtYZRidge.X();
            default:
                return -1;
        }
    }

    /**
     * Get the ridge point Y value
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @return Y value of diaphragm ridge point
     */
    public double getDiaphragmRidgePtY(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZRidge.Y();
            case DefineSystemConstant.YZView:
                return this._elementPtYZRidge.Y();
            default:
                return -1;
        }
    }

    /**
     * Get the radius of ridge
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @return Radius of ridge
     */
    public double getDiaphragmRadius(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._radiusXZ;

            case DefineSystemConstant.YZView:
                return this._radiusYZ;
            default:
                return -1;
        }
    }

    /**
     * Set the ridge point coordinate and ridge radius
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @param ridgePtX X value of diaphragm ridge point
     * @param ridgePtY Y value of diaphragm ridge point
     * @param radius Radius of ridge
     */
    public void setRidgePtAndRadius(int sectionNumber, double ridgePtX, double ridgePtY, double radius)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZRidge.setCoordinate(ridgePtX, ridgePtY);
                this._radiusXZ = radius;
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZRidge.setCoordinate(ridgePtX, ridgePtY);
                this._radiusYZ = radius;
                break;
        }
    }

    /**
     * Get ElementPoint of the ridge point
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @return ElementPoint
     */
    public ElementPoint getElementPointRidge(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZRidge;
            case DefineSystemConstant.YZView:
                return this._elementPtYZRidge;
            default:
                return this._elementPtXZRidge;
        }
    }

    /**
     *
     * @param startPt ElementPoint of側視方向的Start Point
     * @param endPt ElementPoint of側視方向的End Point
     * @param ridgePt ElementPoint of側視方向的
     * @param hT diaphragm Ridge Point Y coordinaRidge Pointte
     * @param hS diaphragm Start Point Y coordinate
     * @param hE diaphragm End Point Y coordinate
     * @param radius Ridge的半徑
     * @param iiCount Mesh總數
     * @return int[]
     */
    public int[] calculateMeshDensity(ElementPoint startPt, ElementPoint endPt,
            ElementPoint ridgePt, double hR,
            double hS, double hE, double radius, int iiCount)
    {
        double dxIn = ridgePt.X() - startPt.X();
        double dyIn = ridgePt.Y() - startPt.Y();
        double widthIn = Math.sqrt(dxIn * dxIn + dyIn * dyIn);

        double dxOut = endPt.X() - ridgePt.X();
        double dyOut = endPt.Y() - ridgePt.Y();
        double widthOut = Math.sqrt(dxOut * dxOut + dyOut * dyOut);

        double arcCenterY = hR - radius;
        double arcCenterX = widthIn;

        double lengthSPt2CPt = Math.sqrt(Math.pow(widthIn, 2) + Math.pow(arcCenterY - hS, 2));
        double lengthEPt2CPt = Math.sqrt(Math.pow(widthOut, 2) + Math.pow(arcCenterY - hE, 2));

        double lengthSPt2TangentPt = Math.sqrt(Math.pow(lengthSPt2CPt, 2) - Math.pow(radius, 2));
        double lengthEPt2TangentPt = Math.sqrt(Math.pow(lengthEPt2CPt, 2) - Math.pow(radius, 2));

        double angleSPt2CPt = this.calculateTheta(widthIn, arcCenterY - hS);
        double angleEPt2CPt = this.calculateTheta( -widthOut, arcCenterY - hE);
        double angleTangentStartCenter = Math.acos(lengthSPt2TangentPt / lengthSPt2CPt);
        double angleTangentEndCenter = Math.acos(lengthEPt2TangentPt / lengthEPt2CPt);

        double tangentPt1X = lengthSPt2TangentPt * Math.cos(angleTangentStartCenter + angleSPt2CPt);
        double tangentPt1Y = hS + lengthSPt2TangentPt * Math.sin(angleTangentStartCenter + angleSPt2CPt);

        double tangentPt2X = widthIn + widthOut + lengthEPt2TangentPt * Math.cos(angleEPt2CPt - angleTangentEndCenter);
        double tangentPt2Y = hE + lengthEPt2TangentPt * Math.sin(angleEPt2CPt - angleTangentEndCenter);


        double angleCPt2TangentPt1 = this.calculateTheta(tangentPt1X - arcCenterX, tangentPt1Y - arcCenterY);
        double angleCPt2TangentPt2 = this.calculateTheta(tangentPt2X - arcCenterX, tangentPt2Y - arcCenterY);
        double angleTangentCPtTangent = angleCPt2TangentPt1 - angleCPt2TangentPt2;

        double totalLength = lengthSPt2TangentPt + lengthEPt2TangentPt + 2 * radius * angleTangentCPtTangent;
        double period = totalLength / iiCount;


        int iCountIn = (int)(lengthSPt2TangentPt / period);
        int iCountArc = (int)(2 * radius * angleTangentCPtTangent / period) + 1;
        int iCountOut = iiCount - iCountIn - iCountArc - 1;
        if(iiCount < 4)
         {
             iCountIn = 1;
             iCountArc = 0;
             iCountOut = 1;
         }

         if(iCountIn < 1)
         {
             iCountIn = 1;
             if(iCountArc > iCountOut)
                 iCountArc = iiCount - iCountIn - iCountOut - 1;
             else
                 iCountOut = iiCount - iCountIn - iCountArc - 1;
         }

         if(iCountArc < 1)
         {
             iCountArc = 1;
             if(iCountIn > iCountOut)
                 iCountIn = iiCount - iCountArc - iCountOut - 1;
             else
                 iCountOut = iiCount - iCountArc - iCountIn - 1;
         }

         if(iCountOut < 1)
         {
             iCountOut = 1;
             if(iCountIn > iCountArc)
                 iCountIn = iiCount - iCountOut - iCountArc - 1;
             else
                 iCountArc = iiCount - iCountOut - iCountIn - 1;
       }

       int meshDensity[] = new int[3];
       meshDensity[0] = iCountIn;
       meshDensity[1] = iCountArc;
       meshDensity[2] = iCountOut;
       return meshDensity;

    }



    public void save(FileWriter fw) throws IOException
    {
        String className = this.getClass().getCanonicalName();
        fw.write("**********************"+className+"**********************\n");
        //Diaphragm Type
        fw.write("DiaphragmType , " + this._geometryType + "\n"); //4_1
        //Diaphragm XZ Section
        fw.write("XZ Start(X): ," + _elementPtXZStart.X()   + "\n");
        fw.write("XZ Start(Z): ," + _elementPtXZStart.Y()   + "\n");
        fw.write("XZ Ridge(X): ," + _elementPtXZRidge.X()   + "\n");
        fw.write("XZ Ridge(Z): ," + _elementPtXZRidge.Y()   + "\n");
        fw.write("  XZ End(X): ," + _elementPtXZEnd.X()     + "\n");
        fw.write("  XZ End(Z): ," + _elementPtXZEnd.Y()     + "\n");
        fw.write("  XZ Radius: ," + _radiusXZ               + "\n");

        //Diaphragm YZ Section
        fw.write("YZ Start(Y): ," + _elementPtYZStart.X()   + "\n");
        fw.write("YZ Start(Z): ," + _elementPtYZStart.Y()   + "\n");
        fw.write("YZ Ridge(Y): ," + _elementPtYZRidge.X()   + "\n");
        fw.write("YZ Ridge(Z): ," + _elementPtYZRidge.Y()   + "\n");
        fw.write("  YZ End(Y): ," + _elementPtYZEnd.X()     + "\n");
        fw.write("  YZ End(Z): ," + _elementPtYZEnd.Y()     + "\n");
        fw.write("  YZ Radius: ," + _radiusYZ               + "\n");

        //Diaphragm Mesh Count
        fw.write("          Is Perodical: , " + this._isPerodical           + "\n");
        fw.write("          Count Radial: , " + this._countRadial           + "\n");
        fw.write(" Count Circumferential: , " + this._countCircumferential  + "\n");
        fw.write("      Count Periodical: , " + this._countPeriodical       + "\n");

        fw.write("             Thickness: , " + this._thickness       + "\n");
        this._material.save(fw);
    }

    public void open(BufferedReader br) throws IOException
    {
        br.readLine().trim();

        this._geometryType =                   Integer.parseInt(this.readLastString(br.readLine().trim()));
        double diaphragmStartPtXZ_X =           Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmStartPtXZ_Y =           Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmTopPtXZ_X =             Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmTopPtXZ_Y =             Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmEndPtXZ_X =             Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmEndPtXZ_Y =             Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmRadiusXZ =              Double.parseDouble(this.readLastString(br.readLine().trim()));

        double diaphragmStartPtYZ_X =           Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmStartPtYZ_Y =           Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmTopPtYZ_X =             Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmTopPtYZ_Y =             Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmEndPtYZ_X =             Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmEndPtYZ_Y =             Double.parseDouble(this.readLastString(br.readLine().trim()));
        double diaphragmRadiusYZ =              Double.parseDouble(this.readLastString(br.readLine().trim()));


        this._isPerodical            =       Boolean.parseBoolean(this.readLastString(br.readLine().trim()));
        this._countRadial            =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countCircumferential   =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countPeriodical        =       Integer.parseInt(this.readLastString(br.readLine().trim()));

        this._thickness              =        Double.parseDouble(this.readLastString(br.readLine().trim()));
        this._material.open(br);

        this.setElementPointStartCoordinate(sdt.define.DefineSystemConstant.XZView, diaphragmStartPtXZ_X, diaphragmStartPtXZ_Y);
        this.setElementPointEndCoordinate  (sdt.define.DefineSystemConstant.XZView, diaphragmEndPtXZ_X, diaphragmEndPtXZ_Y);
        this.setRidgePtAndRadius             (sdt.define.DefineSystemConstant.XZView, diaphragmTopPtXZ_X, diaphragmTopPtXZ_Y, diaphragmRadiusXZ);

        this.setElementPointStartCoordinate(sdt.define.DefineSystemConstant.YZView, diaphragmStartPtYZ_X, diaphragmStartPtYZ_Y);
        this.setElementPointEndCoordinate  (sdt.define.DefineSystemConstant.YZView, diaphragmEndPtYZ_X, diaphragmEndPtYZ_Y);
        this.setRidgePtAndRadius             (sdt.define.DefineSystemConstant.YZView, diaphragmTopPtYZ_X, diaphragmTopPtYZ_Y, diaphragmRadiusYZ);
    }
}
