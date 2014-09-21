package sdt.data;

import java.io.*;

import sdt.define.*;
import sdt.geometry.element.*;
import sdt.stepb.*;
import java.awt.Color;

public class DataTransition extends DataLine
{
    private double _middleInnerPtX_XZ;
    private double _middleInnerPtY_XZ;

    private double _middleInnerPtX_YZ;
    private double _middleInnerPtY_YZ;

    private double _middleOuterPtX_XZ;
    private double _middleOuterPtY_XZ;

    private double _middleOuterPtX_YZ;
    private double _middleOuterPtY_YZ;

    private double _ratioInner;
    private double _ratioOuter;

    private ElementPoint _elementPtXZMiddleInner;
    private ElementPoint _elementPtYZMiddleInner;

    private ElementPoint _elementPtXZMiddleOuter;
    private ElementPoint _elementPtYZMiddleOuter;

    private stepb_cartesian_point[][] _ptSetsContact;


    public stepb_cartesian_point[][] getPtSetsContact()  { return this._ptSetsContact;}

    public DataTransition(SDT_DataManager dataManager)
    {
        super(dataManager);

        _dataType = this.TYPE_TRANSITION;
        _point3DStartSection1 = new stepb_cartesian_point(1.2, 0, 0);
        _point3DEndSection1 = new stepb_cartesian_point(1.5, 0, 0);
        _point3DStartSection2 = new stepb_cartesian_point(0, 1.2, 0);
        _point3DEndSection2 = new stepb_cartesian_point(0, 1.5, 0);

        _countGirth = dataManager.getCountGirth();
        _countRadial = 4;
        _countPeriodical =  16;
        _countCircumferential = _countGirth / _countPeriodical +1; //11
        this._thickness = 0.05;
        this._ratioInner = 0.5;
        this._ratioOuter = 0.7;

        this._colorBody = this._dataManager.getColorTransition();
        this._material  = this._dataManager.getMaterialManager().getMaterial(this._dataType);

        this._middleInnerPtX_XZ = 1.3;
        this._middleInnerPtY_XZ = 0;
        this._middleInnerPtX_YZ = 1.3;
        this._middleInnerPtY_YZ = 0;

        this._middleOuterPtX_XZ = 1.4;
        this._middleOuterPtY_XZ = 0;

        this._middleOuterPtX_YZ = 1.4;
        this._middleOuterPtY_YZ = 0;

        this._elementPtXZMiddleInner = new ElementPoint(this._middleInnerPtX_XZ, this._middleInnerPtY_XZ, _colorPt, this );
        this._elementPtYZMiddleInner = new ElementPoint(this._middleInnerPtX_YZ, this._middleInnerPtY_YZ, _colorPt, this );

        this._elementPtXZMiddleOuter = new ElementPoint(this._middleOuterPtX_XZ, this._middleOuterPtY_XZ, _colorPt, this );
        this._elementPtYZMiddleOuter = new ElementPoint(this._middleOuterPtX_YZ, this._middleOuterPtY_YZ, _colorPt, this );



    }

    protected void setElementXYToData()
    {

    }

    protected void setDataToElementXZ()
    {
        if (this._elementPtXZStart == null)
            this._elementPtXZStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Z(), _colorPt, this );
        if (this._elementPtXZEnd == null)
            this._elementPtXZEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Z(), _colorPt, this );
        this._elmementXZ = new ElementTransition(this, this.XZView, _colorBody);
        _isXYupdateNeed = true;
    }

    protected void setDataToElementYZ()
    {
        if (this._elementPtYZStart == null)
            this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt, this );
        if (this._elementPtYZEnd == null)
            this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt, this );
        this._elmementYZ = new ElementTransition(this, this.YZView, _colorBody);
        _isXYupdateNeed = true;
    }

    protected void createDataPointPeriodically(stepb_cartesian_point[][] ptSets)
    {

        System.out.println("createDataPointPeriodically @ DataTransition");
        int kCount = this._countPeriodical; //ptSets.length;
        int jCount = this._countCircumferential; //ptSets[0].length;
        int iCount = this._countRadial; //ptSets[0][0].length;

        double thetaIntervalK = 2 * Math.PI / kCount;
        double thetaIntervalJ = 2 * Math.PI / kCount / (jCount - 1);
        double theta = 0;

        double rS = this._point3DStartSection1.X();
        double rE = this._point3DEndSection1.X();

        double hS = this._point3DStartSection1.Z();
        double hE = this._point3DEndSection1.Z();

        double rMI = _elementPtXZMiddleInner.X();
        double rMO = _elementPtXZMiddleOuter.X();
        double hMI = _elementPtXZMiddleInner.Y();
        double hMO = _elementPtXZMiddleOuter.Y();


        if (this._ptSetsContact == null || this._ptSetsContact.length != kCount * (jCount - 1)
            || this._ptSetsContact[0].length != 2)
        {
            this._ptSetsContact = new stepb_cartesian_point[kCount * (jCount - 1)][2];
        }



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

                double rInterval =  (rE - rS) / (iCount - 1);
                int    irMI  = -1;
                int    irMO  = -1;
                for (int i = 1; i < iCount - 1; i++)
                {


                    double rM = rS + rInterval * i;
                    double hM = hS + (hE - hS) / (iCount - 1) * i;

                    if (Math.abs(rM - rMI) < Math.abs(rInterval) && irMI == -1)
                    {
                        rM = rMI;
                        hM = hMI;
                        irMI = i;
                    }

                    else if (Math.abs(rM - rMO) < Math.abs(rInterval) && irMO == -1)
                    {
                        rM = rMO;
                        hM = hMO;
                        irMO = i;
                    }

                    if (i == iCount - 3 && irMI == -1)
                    {
                        rM = rMI;
                        hM = hMI;
                        irMI = i;
                    }

                    double xM = rM * Math.cos(theta);
                    double yM = rM * Math.sin(theta);
                    double zM = hM;

                    stepb_cartesian_point cPtM = new stepb_cartesian_point(xM, yM, zM);

                    ptSets[k * (jCount - 1) + j][i] = cPtM;

                    if(i == irMI)
                    {
                        this._ptSetsContact[k * (jCount - 1) + j][0] = cPtM;
                    }
                    if(i == irMO)
                    {
                        this._ptSetsContact[k * (jCount - 1) + j][1] = cPtM;
                    }

                }
            }
        }
    }
    protected void createDataPointCircumferential(stepb_cartesian_point[][] ptSets)
   {
       System.out.println("createDataPointCircumferential @ DataTransition");
       int jCount = ptSets.length;
       int iCount = ptSets[0].length;

       double thetaIntervalJ = 2 * Math.PI / jCount;
       double theta = 0;

       double rS = this._point3DStartSection1.X();
       double rE = this._point3DEndSection1.X();

       double hS = this._point3DStartSection1.Z();
       double hE = this._point3DEndSection1.Z();

       double rMI = _elementPtXZMiddleInner.X();
       double rMO = _elementPtXZMiddleOuter.X();
       double hMI = _elementPtXZMiddleInner.Y();
       double hMO = _elementPtXZMiddleOuter.Y();
       if (this._ptSetsContact == null || this._ptSetsContact.length !=jCount
           || this._ptSetsContact[0].length != 2)
       {
           this._ptSetsContact = new stepb_cartesian_point[jCount][2];
       }

       for (int j = 0; j < jCount; j++)
       {
           theta = j * thetaIntervalJ;
           if (!this._dataManager.getIsStartFromAxis())
               theta += thetaIntervalJ / 2.0;
           int index[] = new int[2];
           ptSets[j] = this.setLinePoint3D_Round(rS, rE, hS, hE, rMI, rMO, hMI, hMO, theta, iCount, index);

           this._ptSetsContact[j][0] = ptSets[j][index[0]];
           this._ptSetsContact[j][1] = ptSets[j][index[1]];
       }
   }
   protected void createDataPointCircumferentialRunway(stepb_cartesian_point[][] ptSetsCircumferential)
   {

       System.out.println("createDataPointCircumferentialRunway @ DataTransition");
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

   public stepb_cartesian_point[] setLinePoint3D_RunwayFan(ElementPoint startPt, ElementPoint endPt,
           double hS, double hE, int iiCount)
   {
       double dx = endPt.X() - startPt.X();
       double dy = endPt.Y() - startPt.Y();
       double theta = this.calculateTheta(dx, dy);
       double width = Math.sqrt(dx * dx + dy * dy);

       stepb_cartesian_point ptsArray[] = new stepb_cartesian_point[iiCount];

       double rIncrement = width / (double) (iiCount - 1);
       double hIncrement = (hE - hS) / (double) (iiCount - 1);

       for (int ii = 0; ii < iiCount; ii++)
       {
           double x = startPt.X() + rIncrement * ii * Math.cos(theta);
           double y = startPt.Y() + rIncrement * ii * Math.sin(theta);
           double z = hS + hIncrement * ii;

           stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
           ptsArray[ii] = cPt;
       }
       return ptsArray;
   }

   public stepb_cartesian_point[] setLinePoint3D_RunwayTri(ElementPoint startPt, ElementPoint endPt,
           double hS, double hE, int iiCount)
   {
       ElementPoint section2EndPt = new ElementPoint(0, endPt.Y(), Color.WHITE, this );
       ElementPoint section2StartPt = new ElementPoint(0, startPt.Y(), Color.WHITE, this );

       stepb_cartesian_point section2PtsArray[] = this.setLinePoint3D_RunwayFan(section2StartPt, section2EndPt, hS, hE, iiCount);

       double dx = endPt.X() - startPt.X();
       double dy = endPt.Y() - startPt.Y();
       double theta = this.calculateTheta(dx, dy);

       stepb_cartesian_point triSectionPtArray[] = new stepb_cartesian_point[iiCount];
       for (int i = 0; i < iiCount; i++)
       {
           double y = section2PtsArray[i].Y();
           double x = startPt.X() + (y - startPt.Y()) / Math.tan(theta);
           double z = section2PtsArray[i].Z();

           stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
           triSectionPtArray[i] = cPt;
       }
       return triSectionPtArray;
   }

   public stepb_cartesian_point[] setLinePoint3D_Round(double rS, double rE, double hS, double hE,
           double rMI, double rMO, double hMI, double hMO, double theta, int iiCount, int[] index)
   {
       stepb_cartesian_point ptsArray[] = new stepb_cartesian_point[iiCount];

       double rIncrement = (rE - rS) / (double) (iiCount - 1);
       double hIncrement = (hE - hS) / (double) (iiCount - 1);
       double rM = 0;
       double hM = 0;
       int irMI = -1;
       int irMO = -1;

       for (int ii = 0; ii < iiCount; ii++)
       {
           rM = rS + rIncrement * ii;
           hM = hS + hIncrement * ii;

           if (Math.abs(rM - rMI) < Math.abs(rIncrement) && irMI == -1)
           {
               rM = rMI;
               hM = hMI;
               irMI = ii;
           }

           else if (Math.abs(rM - rMO) < Math.abs(rIncrement) && irMO == -1)
           {
               rM = rMO;
               hM = hMO;
               irMO = ii;
           }

           if (ii == iiCount - 3 && irMI == -1)
           {
               rM = rMI;
               hM = hMI;
               irMI = ii;
           }

           double x = rM * Math.cos(theta);
           double y = rM * Math.sin(theta);
           double z = hM;

           stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
           index[0] = irMI;
           index[1] = irMO;
           ptsArray[ii] = cPt;
       }
       return ptsArray;
   }


   /**
    * 得到Transition中間接Coil靠內層的ElementPoint
    * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
    * @return Transition中間內接點的ElementPoint
    */
   public ElementPoint getElementPointMiddleOuter(int sectionNumber)
   {
       switch (sectionNumber)
       {
           case DefineSystemConstant.XZView:
               return this._elementPtXZMiddleOuter;
           case DefineSystemConstant.YZView:
               return this._elementPtYZMiddleOuter;
           default:
               return this._elementPtXZMiddleOuter;
       }
   }

   /**
    * 得到Transition中間接Coil靠外層的ElementPoint
    * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
    * @return Transition中間外接點的ElementPoint
    */
   public ElementPoint getElementPointMiddleInner(int sectionNumber)
   {
       switch (sectionNumber)
       {
           case DefineSystemConstant.XZView:
               return this._elementPtXZMiddleInner;
           case DefineSystemConstant.YZView:
               return this._elementPtYZMiddleInner;
           default:
               return this._elementPtXZMiddleInner;
       }
   }

   public void setElementPointMiddle(int sectionNumber, double xInnerValue, double xOuterValue)
   {
       double sPtXValue;
       double sPtYValue;
       double ePtXValue;
       double ePtYValue;
       double yInnerValue;
       double yOuterValue;

       switch (sectionNumber)
       {
           case DefineSystemConstant.XZView:

               sPtXValue = this._elementPtXZStart.X();
               sPtYValue = this._elementPtXZStart.Y();
               ePtXValue = this._elementPtXZEnd.X();
               ePtYValue = this._elementPtXZEnd.Y();

               yOuterValue = sPtYValue + (ePtYValue - sPtYValue) * (xOuterValue - sPtXValue) / (ePtXValue - sPtXValue);
               yInnerValue = sPtYValue + (ePtYValue - sPtYValue) * (xInnerValue - sPtXValue) / (ePtXValue - sPtXValue);

               this._elementPtXZMiddleInner.setCoordinate(xInnerValue, yInnerValue);
               this._elementPtXZMiddleOuter.setCoordinate(xOuterValue, yOuterValue);

           case DefineSystemConstant.YZView:

                sPtXValue = this._elementPtYZStart.X();
                sPtYValue = this._elementPtYZStart.Y();
                ePtXValue = this._elementPtYZEnd.X();
                ePtYValue = this._elementPtYZEnd.Y();

                yOuterValue = sPtYValue + (ePtYValue - sPtYValue) * (xOuterValue - sPtXValue) / (ePtXValue - sPtXValue);
                yInnerValue = sPtYValue + (ePtYValue - sPtYValue) * (xInnerValue - sPtXValue) / (ePtXValue - sPtXValue);



               this._elementPtYZMiddleInner.setCoordinate(xInnerValue, yInnerValue);
               this._elementPtYZMiddleOuter.setCoordinate(xOuterValue, yOuterValue);
           default:
       }
   }




   public double getRatioInner()
   {
       return this._ratioInner;
   }

   public double getRatioOuter()
   {
       return this._ratioOuter;
   }

   public void setRatioInner(double ratioInner)
   {
       this._ratioInner= ratioInner;
   }

   public void getRatioOuter(double ratioOuter)
   {
       this._ratioOuter = ratioOuter;
   }







    public void save(FileWriter fw) throws IOException
    {
        String className = this.getClass().getCanonicalName();
        fw.write("**********************"+className+"**********************\n");

        //Transition XZ Section
        fw.write("XZ Start(X): ," + _elementPtXZStart.X()   + "\n");
        fw.write("XZ Start(Z): ," + _elementPtXZStart.Y()   + "\n");
        fw.write("  XZ End(X): ," + _elementPtXZEnd.X()     + "\n");
        fw.write("  XZ End(Z): ," + _elementPtXZEnd.Y()     + "\n");
        fw.write("  XZ MiddleInner(X): ," + _elementPtXZMiddleInner.X()     + "\n");
        fw.write("  XZ MiddleOuter(X): ," + _elementPtXZMiddleOuter.X()     + "\n");



        //Transition YZ Section
        fw.write("YZ Start(Y): ," + _elementPtYZStart.X()   + "\n");
        fw.write("YZ Start(Z): ," + _elementPtYZStart.Y()   + "\n");
        fw.write("  YZ End(Y): ," + _elementPtYZEnd.X()     + "\n");
        fw.write("  YZ End(Z): ," + _elementPtYZEnd.Y()     + "\n");
        fw.write("  YZ MiddleInner(X): ," + _elementPtYZMiddleInner.X()     + "\n");
        fw.write("  YZ MiddleOuter(X): ," + _elementPtYZMiddleOuter.X()     + "\n");

        //Transition Mesh Count
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

        double tranStartPtXZ_X =           Double.parseDouble(this.readLastString(br.readLine().trim()));//3_1
        double tranStartPtXZ_Y =           Double.parseDouble(this.readLastString(br.readLine().trim()));//3_2
        double tranEndPtXZ_X =             Double.parseDouble(this.readLastString(br.readLine().trim()));//3_3
        double tranEndPtXZ_Y =             Double.parseDouble(this.readLastString(br.readLine().trim()));//3_4
        double tranMiddleInPtXZ_X =        Double.parseDouble(this.readLastString(br.readLine().trim())); //fw.write("  XZ MiddleInner(X): ," + _elementPtXZMiddleInner.X()     + "\n");
        double tranMiddleOutPtXZ_X =       Double.parseDouble(this.readLastString(br.readLine().trim()));

        double tranStartPtYZ_X =           Double.parseDouble(this.readLastString(br.readLine().trim()));//3_5
        double tranStartPtYZ_Y =           Double.parseDouble(this.readLastString(br.readLine().trim()));//3_6
        double tranEndPtYZ_X =             Double.parseDouble(this.readLastString(br.readLine().trim()));//3_7
        double tranEndPtYZ_Y =             Double.parseDouble(this.readLastString(br.readLine().trim()));//3_8
        double tranMiddleInPtYZ_X =        Double.parseDouble(this.readLastString(br.readLine().trim())); //fw.write("  XZ MiddleInner(X): ," + _elementPtXZMiddleInner.X()     + "\n");
        double tranMiddleOutPtYZ_X =       Double.parseDouble(this.readLastString(br.readLine().trim()));


        this.setElementPointStartCoordinate(sdt.define.DefineSystemConstant.XZView, tranStartPtXZ_X, tranStartPtXZ_Y);
        this.setElementPointEndCoordinate  (sdt.define.DefineSystemConstant.XZView, tranEndPtXZ_X, tranEndPtXZ_Y);
        this.setElementPointMiddle(sdt.define.DefineSystemConstant.XZView, tranMiddleInPtXZ_X, tranMiddleOutPtXZ_X);

        this.setElementPointStartCoordinate(sdt.define.DefineSystemConstant.YZView, tranStartPtYZ_X, tranStartPtYZ_Y);
        this.setElementPointEndCoordinate  (sdt.define.DefineSystemConstant.YZView, tranEndPtYZ_X, tranEndPtYZ_Y);
        this.setElementPointMiddle(sdt.define.DefineSystemConstant.YZView, tranMiddleInPtYZ_X, tranMiddleOutPtYZ_X);


        this._isPerodical            =       Boolean.parseBoolean(this.readLastString(br.readLine().trim()));
        this._countRadial            =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countCircumferential   =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countPeriodical        =       Integer.parseInt(this.readLastString(br.readLine().trim()));

        this._thickness              =        Double.parseDouble(this.readLastString(br.readLine().trim()));
        this._material.open(br);
    }
}
