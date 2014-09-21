package sdt.data;

import java.io.*;
import java.util.*;

import sdt.define.*;
import sdt.geometry.*;
import sdt.geometry.element.*;
import sdt.stepb.*;

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
public class DataAir extends DataSolid
{

    SDT_DataManager _dataManager;
    DataCap         _dataCap;
    DataTransition  _dataTransition;
    DataDiaphragm   _dataDiaphragm;
    DataSurround    _dataSurround;
    DataCoil        _dataCoil;
    DataMagnet      _dataMagnet;
    DataMagnetTop   _dataMagnetTop;
    DataTopPlate    _dataTopPlate;
    DataMagnetOuter _dataMagnetOuter;
    DataYokeBase    _dataYokeBase;
    DataYokeStage1  _dataYokeStage1;
    DataYokeStage2  _dataYokeStage2;

    double _outestAreaW = 0;
    double _outestAreaL = 0;

    double _unitWidth         = 0;
    double _factorCircle      = 2;
    double _factorCircleBottom= 2;

    double _vCircle[]       = new double[12];
    double _hCircle[]       = new double[13];
    int    _wCircle[]       = new int[12];
    int    _lCircle[]       = new int[12];
    double _wCircleValue[]  = new double[12];
    double _lCircleValue[]  = new double[12];

    public double[] getWCircleValue()          {   return this._wCircleValue;  }
    public double[] getVCircle()               {   return this._vCircle;  }





    public DataAir(SDT_DataManager dataManger)
    {
        super(dataManger);

        this._dataManager = dataManger;
        this._dataCap         = this._dataManager.getDataCap();
        this._dataTransition  = this._dataManager.getDataTran();
        this._dataDiaphragm   = this._dataManager.getDataDiaphragm();
        this._dataSurround    = this._dataManager.getDataSurround();
        this._dataCoil        = this._dataManager.getDataCoil();
        this._dataMagnet      = this._dataManager.getDataMagnet();
        this._dataTopPlate    = this._dataManager.getDataTopPlate();
        this._dataMagnetTop   = this._dataManager.getDataMagnetTop();
        this._dataYokeBase    = this._dataManager.getDataYokeBase();
        this._dataYokeStage1  = this._dataManager.getDataYokeStage1();
        this._dataYokeStage2  = this._dataManager.getDataYokeStage2();
        this._dataMagnetOuter = this._dataManager.getDataMagnetOuter();

        this._dataType = this.TYPE_AIR;

        this._colorBody = this._dataManager.getColorAir();
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);

        this._point3DStartSection1 = new stepb_cartesian_point(0, 0, 0);
        this._point3DEndSection1 = new stepb_cartesian_point(0, 0, 0);
        this._point3DStartSection2 = new stepb_cartesian_point(0, 0, 0);
        this._point3DEndSection2 = new stepb_cartesian_point(0, 0, 0);


        this._hCircle[0] = -1;
        //this.getSurroundContour();
    }


    public ArrayList getElementAirGrid2()
    {
        ArrayList arrGridPoint = new ArrayList();


        //Cap
        ElementPoint capStartPt          = this._dataCap.getElementPointStart(this.XZView);
        ElementPoint capEndPt            = this._dataCap.getElementPointEnd(this.XZView);

        //MagnetTop
        ElementPoint magnetTopStartPt    = this._dataMagnetTop.getElementPointStart(this.XZView);
        ElementPoint magnetTopEndPt      = this._dataMagnetTop.getElementPointEnd(this.XZView);

        //TopPlate
        ElementPoint topPlateStartPt     = this._dataTopPlate.getElementPointStart(this.XZView);
        ElementPoint topPlateEndPt       = this._dataTopPlate.getElementPointEnd(this.XZView);

        //Magnet
        ElementPoint magnetStartPt       = this._dataMagnet.getElementPointStart(this.XZView);
        ElementPoint magnetEndPt         = this._dataMagnet.getElementPointEnd(this.XZView);

        //Coil
        ElementPoint coilStartPt         = this._dataCoil.getElementPointStart(this.XZView);
        ElementPoint coilEndPt           = this._dataCoil.getElementPointOuterEnd(this.XZView);

        //YokeBase
        ElementPoint yokeBaseStartPt     = this._dataYokeBase.getElementPointStart(this.XZView);
        ElementPoint yokeBaseMidUpPt   = this._dataYokeBase.getElementPointMiddleUp(this.XZView);
        ElementPoint yokeBaseEndPt       = this._dataYokeBase.getElementPointEnd(this.XZView);

        //YokeStage1
        ElementPoint yokeStage1StartPt   = this._dataYokeStage1.getElementPointStart(this.XZView);
        ElementPoint yokeStage1MidUpPt = this._dataYokeStage1.getElementPointMiddleUp(this.XZView);
        ElementPoint yokeStage1EndPt     = this._dataYokeStage1.getElementPointEnd(this.XZView);

        //YokeStage2
        ElementPoint yokeStage2StartPt   = this._dataYokeStage2.getElementPointStart(this.XZView);
        ElementPoint yokeStage2MidUpPt = this._dataYokeStage2.getElementPointMiddleUp(this.XZView);
        ElementPoint yokeStage2EndPt     = this._dataYokeStage2.getElementPointEnd(this.XZView);

        //MagnetOuter
        ElementPoint magnetOuterStartPt  = this._dataMagnetOuter.getElementPointStart(this.XZView);
        ElementPoint magnetOuterEndPt    = this._dataMagnetOuter.getElementPointEnd(this.XZView);


        double h1 = magnetTopStartPt.Y();
        double h2 = topPlateStartPt.Y();
        double h3 = magnetStartPt.Y();
        double h4 = yokeBaseStartPt.Y();
        double h5 = coilEndPt.Y();
        double h6 = yokeStage1StartPt.Y();
        double h7 = magnetOuterStartPt.Y();
        double h8 = yokeStage2StartPt.Y();
        double h9 = yokeStage2MidUpPt.Y();

        double v0 = magnetTopStartPt.X();
        double v1 = magnetTopEndPt.X();
        double v2 = topPlateEndPt.X();
        double v3 = magnetEndPt.X();
        double v4 = coilStartPt.X();
        double v5 = coilEndPt.X();
        double v6 = yokeBaseMidUpPt.X();
        double v7 = magnetOuterStartPt.X();
        double v8 = yokeStage1MidUpPt.X();
        double v9 = yokeStage2MidUpPt.X();

        //double innerAirGap = v4 - v2;
        //double coilThickness = v5 - v4;
        //double outerAirGap = v6 - v5;
        double fundamentalWidth = v5 - v4;
        double factor = 2;

        double h0 = capEndPt.Y() - fundamentalWidth;

        int divide_L1_number = (int)Math.round(Math.abs(h0 - h1) / (factor * fundamentalWidth));
        if(divide_L1_number == 0)
            divide_L1_number = 1;
        double divide_L1_value = Math.abs(h0 - h1) / (double)divide_L1_number;

        int divide_L2_number = (int)Math.round(Math.abs(h1 - h2) / (factor * fundamentalWidth));
        if(divide_L2_number == 0)
            divide_L2_number = 1;
        double divide_L2_value = Math.abs(h1 - h2) / (double)divide_L2_number;

        int divide_L3_number = (int)Math.round(Math.abs(h2 - h3) / fundamentalWidth);
        if(divide_L3_number == 0)
            divide_L3_number = 1;
        double divide_L3_value = Math.abs(h2 - h3) / (double)divide_L3_number;

        int divide_L4_number = (int)Math.round(Math.abs(h3 - h5) / fundamentalWidth);
        if(divide_L4_number == 0)
            divide_L4_number = 1;
        double divide_L4_value = Math.abs(h3 - h5) / (double)divide_L4_number;

        int divide_L5_number = (int)Math.round(Math.abs(h5 - h4) / fundamentalWidth);
        if(divide_L5_number == 0)
            divide_L5_number = 1;
        double divide_L5_value = Math.abs(h5 - h4) / (double)divide_L5_number;

        int divide_L6_number = (int)Math.round(Math.abs(h6 - h5) / fundamentalWidth);
        if(divide_L6_number == 0)
            divide_L6_number = 1;
        double divide_L6_value = Math.abs(h6 - h5) / (double)divide_L6_number;

        int divide_L7_number = (int)Math.round(Math.abs(h7 - h6) / (factor * fundamentalWidth));
        if(divide_L7_number == 0)
            divide_L7_number = 1;
        double divide_L7_value = Math.abs(h7 - h6) / (double)divide_L7_number;

        int divide_L8_number = (int)Math.round(Math.abs(h8 - h7) / (factor * fundamentalWidth));
        if(divide_L8_number == 0)
            divide_L8_number = 1;
        double divide_L8_value = Math.abs(h8 - h7) / (double)divide_L8_number;

        int divide_L9_number = (int)Math.round(Math.abs(h9 - h8) / (factor * fundamentalWidth));
        if(divide_L9_number == 0)
            divide_L9_number = 1;
        double divide_L9_value = Math.abs(h9 - h8) / (double)divide_L9_number;

        int divide_W1_number = (int)Math.round(Math.abs(v1 - v0) / (factor * fundamentalWidth));
        if(divide_W1_number == 0)
            divide_W1_number = 1;
        double divide_W1_value = Math.abs(v1 - v0) / (double)divide_W1_number;

        int divide_W2_number = (int)Math.round(Math.abs(v2 - v1) / (factor * fundamentalWidth));
        if(divide_W2_number == 0)
            divide_W2_number = 1;
        double divide_W2_value = Math.abs(v2 - v1) / (double)divide_W2_number;

        int divide_W3_number = (int)Math.round(Math.abs(v4 - v2) / (factor * fundamentalWidth));
        if(divide_W3_number == 0)
            divide_W3_number = 1;
        double divide_W3_value = Math.abs(v4 - v2) / (double)divide_W3_number;

        int divide_W4_number = (int)Math.round(Math.abs(v2 - v3) / fundamentalWidth);
        if(divide_W4_number == 0)
            divide_W4_number = 1;
        double divide_W4_value = Math.abs(v2 - v3) / (double)divide_W4_number;

        int divide_W5_number = (int)Math.round(Math.abs(v5 - v4) / (factor * fundamentalWidth));
        if(divide_W5_number == 0)
            divide_W5_number = 1;
        double divide_W5_value = Math.abs(v5 - v4) / (double)divide_W5_number;

        int divide_W6_number = (int)Math.round(Math.abs(v6 - v5) / (factor * fundamentalWidth));
        if(divide_W6_number == 0)
            divide_W6_number = 1;
        double divide_W6_value = Math.abs(v6 - v5) / (double)divide_W6_number;

        int divide_W7_number = (int)Math.round(Math.abs(v7 - v6) / (factor * fundamentalWidth));
        if(divide_W7_number == 0)
            divide_W7_number = 1;
        double divide_W7_value = Math.abs(v7 - v6) / (double)divide_W7_number;

        int divide_W8_number = (int)Math.round(Math.abs(v8 - v7) / (factor * fundamentalWidth));
        if(divide_W8_number == 0)
            divide_W8_number = 1;
        double divide_W8_value = Math.abs(v8 - v7) / (double)divide_W8_number;

        int divide_W9_number = (int)Math.round(Math.abs(v9 - v8) / (factor * fundamentalWidth));
        if(divide_W9_number == 0)
            divide_W9_number = 1;
        double divide_W9_value = Math.abs(v9 - v8) / (double)divide_W9_number;

        //area 1  L1 & W1


        for(int i = 0; i <= divide_W1_number ; i++)
        {
            for(int j = 0; j <= divide_L1_number ; j++)
            {
                ObjectPoint objPt = new ObjectPoint(divide_W1_value * i, h0 - divide_L1_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);

            }
        }

        //area 2  L1 & W2
        for(int i = 1; i <= divide_W2_number ; i++)
        {
            for(int j = 0; j <= divide_L1_number ; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v1 + divide_W2_value * i, h0 - divide_L1_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 3  L1 & W3
        for(int i = 1; i <= divide_W3_number ; i++)
        {
            for(int j = 0; j <= divide_L1_number ; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v2 + divide_W3_value * i, h0 - divide_L1_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 4  L2 & W2
        for (int i = 0; i <= divide_W2_number; i++)
        {
            for (int j = 1; j <= divide_L2_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v1 + divide_W2_value * i, h1 - divide_L2_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 5  L2 & W3
        for (int i = 1; i <= divide_W3_number; i++)
        {
            for (int j = 1; j <= divide_L2_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v2 + divide_W3_value * i, h1 - divide_L2_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 6  L3 & W3
        for (int i = 0; i <= divide_W3_number; i++)
        {
            for (int j = 1; j <= divide_L3_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v2 + divide_W3_value * i, h2 - divide_L3_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 7  L4 & W4
        for (int i = 0; i <= divide_W4_number; i++)
        {
            for (int j = 0; j <= divide_L4_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v3 + divide_W4_value * i, h3 - divide_L4_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 8  L4 & W3
        for (int i = 1; i <= divide_W3_number; i++)
        {
            for (int j = 1; j <= divide_L4_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v2 + divide_W3_value * i, h3 - divide_L4_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 9  L5 & W4
        for (int i = 0; i <= divide_W4_number; i++)
        {
            for (int j = 1; j <= divide_L5_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v3 + divide_W4_value * i, h5 - divide_L5_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 10  L5 & W3
        for (int i = 1; i <= divide_W3_number; i++)
        {
            for (int j = 1; j <= divide_L5_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v2 + divide_W3_value * i, h5 - divide_L5_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 11  L5 & W5
        for (int i = 1; i <= divide_W5_number; i++)
        {
            for (int j = 0; j <= divide_L5_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v4 + divide_W5_value * i, h5 - divide_L5_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 12  L5 & W6
        for (int i = 1; i <= divide_W6_number; i++)
        {
            for (int j = 1; j <= divide_L5_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v5 + divide_W6_value * i, h5 - divide_L5_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 13  L6 & W6
        for (int i = 0; i <= divide_W6_number; i++)
        {
            for (int j = 0; j <= divide_L6_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v5 + divide_W6_value * i, h6 - divide_L6_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 14  L7 & W6
        for (int i = 0; i <= divide_W6_number; i++)
        {
            for (int j = 0; j <= divide_L7_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v5 + divide_W6_value * i, h7 - divide_L7_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 15  L8 & W6
        for (int i = 0; i <= divide_W6_number; i++)
        {
            for (int j = 0; j <= divide_L8_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v5 + divide_W6_value * i, h8 - divide_L8_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 16  L9 & W6
        for (int i = 0; i <= divide_W6_number; i++)
        {
            for (int j = 0; j <= divide_L9_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v5 + divide_W6_value * i, h9 - divide_L9_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 17  L7 & W7
        for (int i = 0; i <= divide_W7_number; i++)
        {
            for (int j = 0; j <= divide_L7_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v6 + divide_W7_value * i, h7 - divide_L7_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 18  L8 & W7
        for (int i = 0; i <= divide_W7_number; i++)
        {
            for (int j = 0; j <= divide_L8_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v6 + divide_W7_value * i, h8 - divide_L8_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 19  L9 & W7
        for (int i = 0; i <= divide_W7_number; i++)
        {
            for (int j = 0; j <= divide_L9_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v6 + divide_W7_value * i, h9 - divide_L9_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 20  L8 & W8
        for (int i = 0; i <= divide_W8_number; i++)
        {
            for (int j = 0; j <= divide_L8_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v7 + divide_W8_value * i, h8 - divide_L8_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 21  L9 & W8
        for (int i = 0; i <= divide_W8_number; i++)
        {
            for (int j = 0; j <= divide_L9_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v7 + divide_W8_value * i, h9 - divide_L9_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }

        //area 22  L9 & W9
        for (int i = 0; i <= divide_W9_number; i++)
        {
            for (int j = 0; j <= divide_L9_number; j++)
            {
                ObjectPoint objPt = new ObjectPoint(v8 + divide_W9_value * i, h9 - divide_L9_value * j);
                ElementPoint elementPt = new ElementPoint(objPt, _colorBody, this);
                arrGridPoint.add(elementPt);
            }
        }




        return arrGridPoint;
    }


    public void save(FileWriter fw) throws IOException
    {
    }

    public void open(BufferedReader br) throws IOException
    {
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

        this._isXYupdateNeed = true;
        this._elmementXZ = new ElementAir(this, this.XZView, _colorBody);
    }

    protected void setDataToElementYZ()
    {
        if (this._elementPtYZStart == null)
            this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt, this);
        if (this._elementPtYZEnd == null)
            this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt, this);
        this._elmementYZ = new ElementAir(this, this.YZView, _colorBody);
        this._isXYupdateNeed = true;

    }

    protected void createDataPointPeriodically()
    {
        this.createDataPointCircumferential();
    }

    protected void createDataPointCircumferential()
    {

        int jCount = this._dataSurround._countGirth;
        //double totalVolume = 0;
        double volumeCavityCap = 0;
        double volumeAirGapIn = 0;
        double volumeAirGapOut = 0;
        double volumeCavityMagnetYoke = 0;
        double volumeCavityDiaphSurr = 0;

        double thetaIntervalJ = 2 * Math.PI / jCount;

        if(this._hCircle[0] == -1 )
            getDivisionInfo();


        stepb_cartesian_point[][][] ptArea_1  = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_2  = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_3  = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_4  = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_5  = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_6  = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_7  = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_8  = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_9  = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_10 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_11 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_12 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_13 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_14 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_15 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_16 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_17 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_18 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_19 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_20 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_21 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_22 = new stepb_cartesian_point[jCount][][];

        stepb_cartesian_point[][][] ptArea_23 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_24 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_25 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_26 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_27 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_28 = new stepb_cartesian_point[jCount][][];

        stepb_cartesian_point[][][] ptArea_Cap ;
        stepb_cartesian_point[][][] ptArea_Tran_Inner;
        stepb_cartesian_point[][][] ptArea_Tran_Outer;
        stepb_cartesian_point[][][] ptArea_Diaphragm ;
        stepb_cartesian_point[][][] ptArea_Surround ;


        ptArea_Cap          = this.calculateShellUnderPt(_dataCap,_hCircle[0]);
        ptArea_Tran_Inner   = this.calculateShellUnderPt_Tran(_dataTransition,_hCircle[0],true);
        ptArea_Tran_Outer   = this.calculateShellUnderPt_Tran(_dataTransition,_hCircle[12],false);
        ptArea_Diaphragm    = this.calculateShellUnderPt(_dataDiaphragm,_hCircle[12]);
        ptArea_Surround     = this.calculateShellUnderPt(_dataSurround,_hCircle[12]);

        for(int j = 0 ; j < jCount ; j++)
        {
            double theta = j * thetaIntervalJ;

            //area 0
            //ptArea_0[j] = this.calculateCapPt(theta, divide_W0_number, divide_W0_value, divide_L0_number, divide_L0_value, 0, h00);


            //area 1  L1 & W1
            ptArea_1[j] = this.calculatePt(theta, _wCircle[0], _wCircleValue[0], _lCircle[0] , _lCircleValue[0], 0, _hCircle[0]);
            //area 2  L1 & W2
            ptArea_2[j] = this.calculatePt(theta, _wCircle[1], _wCircleValue[1], _lCircle[0] , _lCircleValue[0], _vCircle[1], _hCircle[0]);
            //area 3  L1 & W3
            ptArea_3[j] = this.calculatePt(theta, _wCircle[2], _wCircleValue[2], _lCircle[0] , _lCircleValue[0], _vCircle[2], _hCircle[0]);
            //area 4  L2 & W2
            ptArea_4[j] = this.calculatePt(theta, _wCircle[1], _wCircleValue[1], _lCircle[1] , _lCircleValue[1], _vCircle[1], _hCircle[1]);
            //area 5  L2 & W3
            ptArea_5[j] = this.calculatePt(theta, _wCircle[2], _wCircleValue[2], _lCircle[1] , _lCircleValue[1], _vCircle[2], _hCircle[1]);
            //area 6  L3 & W3
            ptArea_6[j] = this.calculatePt(theta, _wCircle[2], _wCircleValue[2], _lCircle[2], _lCircleValue[2], _vCircle[2], _hCircle[2]);
            //area 7  L4 & W4
            ptArea_7[j] = this.calculatePt(theta, _wCircle[3], _wCircleValue[3], _lCircle[3], _lCircleValue[3], _vCircle[3], _hCircle[3]);
            //area 8  L4 & W3
            ptArea_8[j] = this.calculatePt(theta, _wCircle[2], _wCircleValue[2], _lCircle[3], _lCircleValue[3], _vCircle[2], _hCircle[3]);
            //area 9  L5 & W4
            ptArea_9[j] = this.calculatePt(theta, _wCircle[3], _wCircleValue[3], _lCircle[4], _lCircleValue[4], _vCircle[3], _hCircle[5]);
            //area 10  L5 & W3
            ptArea_10[j] = this.calculatePt(theta, _wCircle[2], _wCircleValue[2], _lCircle[4], _lCircleValue[4], _vCircle[2], _hCircle[5]);
            //area 11  L5 & W5
            ptArea_11[j] = this.calculatePt(theta, _wCircle[4], _wCircleValue[4], _lCircle[4], _lCircleValue[4], _vCircle[4], _hCircle[5]);
            //area 12  L5 & W6
            ptArea_12[j] = this.calculatePt(theta, _wCircle[5], _wCircleValue[5], _lCircle[4], _lCircleValue[4], _vCircle[5], _hCircle[5]);
            //area 13  L6 & W6
            ptArea_13[j] = this.calculatePt(theta, _wCircle[5], _wCircleValue[5], _lCircle[5], _lCircleValue[5], _vCircle[5], _hCircle[8]);
            //area 14  L7 & W6
            ptArea_14[j] = this.calculatePt(theta, _wCircle[5], _wCircleValue[5], _lCircle[6], _lCircleValue[6], _vCircle[5], _hCircle[9]);
            //area 15  L8 & W6
            ptArea_15[j] = this.calculatePt(theta, _wCircle[5], _wCircleValue[5], _lCircle[7], _lCircleValue[7], _vCircle[5], _hCircle[10]);
            //area 16  L9 & W6
            ptArea_16[j] = this.calculatePt(theta, _wCircle[5], _wCircleValue[5], _lCircle[8], _lCircleValue[8], _vCircle[5], _hCircle[11]);
            //area 17  L7 & W7
            ptArea_17[j] = this.calculatePt(theta, _wCircle[7], _wCircleValue[7], _lCircle[6], _lCircleValue[6], _vCircle[8], _hCircle[9]);
            //area 18  L8 & W7
            ptArea_18[j] = this.calculatePt(theta, _wCircle[7], _wCircleValue[7], _lCircle[7], _lCircleValue[7], _vCircle[8], _hCircle[10]);
            //area 19  L9 & W7
            ptArea_19[j] = this.calculatePt(theta, _wCircle[7], _wCircleValue[7], _lCircle[8], _lCircleValue[8], _vCircle[8], _hCircle[11]);
            //area 20  L8 & W8
            ptArea_20[j] = this.calculatePt(theta, _wCircle[8], _wCircleValue[8], _lCircle[7], _lCircleValue[7], _vCircle[9], _hCircle[10]);
            //area 21  L9 & W8
            ptArea_21[j] = this.calculatePt(theta, _wCircle[8], _wCircleValue[8], _lCircle[8], _lCircleValue[8], _vCircle[9], _hCircle[11]);
            //area 22  L9 & W9
            ptArea_22[j] = this.calculatePt(theta, _wCircle[9], _wCircleValue[9], _lCircle[4], _lCircleValue[8], _vCircle[10], _hCircle[11]);

            //area 23  L10 & W6_1
            ptArea_23[j] = this.calculatePt(theta, _wCircle[6], _wCircleValue[6], _lCircle[9], _lCircleValue[9], _vCircle[6], _hCircle[12]);
            //area 24  L11 & W6_1
            ptArea_24[j] = this.calculatePt(theta, _wCircle[6], _wCircleValue[6], _lCircle[10], _lCircleValue[10], _vCircle[6], _hCircle[7]);
            //area 25  L5 & W6_1
            ptArea_25[j] = this.calculatePt(theta, _wCircle[6], _wCircleValue[6], _lCircle[4], _lCircleValue[4], _vCircle[6], _hCircle[5]);

        }

        System.out.println("v : " + _vCircle[0] + ", " + _vCircle[1] + ", " + _vCircle[2] + ", "+ _vCircle[3] + ", "+ _vCircle[4] + ", "+ _vCircle[5] + ", "+ _vCircle[6] + ", "+ _vCircle[7] + ", "+ _vCircle[8] + ", "+ _vCircle[9]+ ", "+ _vCircle[10]);
        System.out.println("h : " + _hCircle[0] + ", " + _hCircle[1] + ", " + _hCircle[2] + ", "+ _hCircle[3] + ", "+ _hCircle[4] + ", "+ _hCircle[5] + ", "+ _hCircle[6] + ", "+ _hCircle[7] + ", "+ _hCircle[8] + ", "+ _hCircle[9]+ ", "+ _hCircle[10] + ", "+ _hCircle[11]+ ", "+ _hCircle[12]);
        System.out.println("W number: " + _wCircle[0] + ", " + _wCircle[1] + ", " + _wCircle[2] + ", "+ _wCircle[3] + ", "+ _wCircle[4] + ", "+ _wCircle[5] + ", "+ _wCircle[6] + ", "+ _wCircle[7] + ", "+ _wCircle[8] + ", "+ _wCircle[9]);
        System.out.println("W value: " + _wCircleValue[0] + ", " + _wCircleValue[1] + ", " + _wCircleValue[2] + ", "+ _wCircleValue[3] + ", "+ _wCircleValue[4] + ", "+ _wCircleValue[5] + ", "+ _wCircleValue[6] + ", "+ _wCircleValue[7] + ", "+ _wCircleValue[8] + ", "+ _wCircleValue[9]);
        System.out.println("l number: " + _lCircle[0] + ", " + _lCircle[1] + ", " + _lCircle[2] + ", "+ _lCircle[3] + ", "+ _lCircle[4] + ", "+ _lCircle[5] + ", "+ _lCircle[6] + ", "+ _lCircle[7] + ", "+ _lCircle[8] + ", "+ _lCircle[9] + ", "+ _lCircle[10]);
        System.out.println("l value: " + _lCircleValue[0] + ", " + _lCircleValue[1] + ", " + _lCircleValue[2] + ", "+ _lCircleValue[3] + ", "+ _lCircleValue[4] + ", "+ _lCircleValue[5] + ", "+ _lCircleValue[6] + ", "+ _lCircleValue[7] + ", "+ _lCircleValue[8] + ", "+ _lCircleValue[9] + ", "+ _lCircleValue[10]);


        CartesianPointSetsBrick ptSetsBrick1  = new CartesianPointSetsBrick(ptArea_1);
        CartesianPointSetsBrick ptSetsBrick2  = new CartesianPointSetsBrick(ptArea_2);
        CartesianPointSetsBrick ptSetsBrick3  = new CartesianPointSetsBrick(ptArea_3);
        CartesianPointSetsBrick ptSetsBrick4  = new CartesianPointSetsBrick(ptArea_4);
        CartesianPointSetsBrick ptSetsBrick5  = new CartesianPointSetsBrick(ptArea_5);
        CartesianPointSetsBrick ptSetsBrick6  = new CartesianPointSetsBrick(ptArea_6);
        CartesianPointSetsBrick ptSetsBrick7  = new CartesianPointSetsBrick(ptArea_7);
        CartesianPointSetsBrick ptSetsBrick8  = new CartesianPointSetsBrick(ptArea_8);
        CartesianPointSetsBrick ptSetsBrick9  = new CartesianPointSetsBrick(ptArea_9);
        CartesianPointSetsBrick ptSetsBrick10 = new CartesianPointSetsBrick(ptArea_10);
        CartesianPointSetsBrick ptSetsBrick11 = new CartesianPointSetsBrick(ptArea_11);
        CartesianPointSetsBrick ptSetsBrick12 = new CartesianPointSetsBrick(ptArea_12);
        CartesianPointSetsBrick ptSetsBrick13 = new CartesianPointSetsBrick(ptArea_13);
        CartesianPointSetsBrick ptSetsBrick14 = new CartesianPointSetsBrick(ptArea_14);
        CartesianPointSetsBrick ptSetsBrick15 = new CartesianPointSetsBrick(ptArea_15);
        CartesianPointSetsBrick ptSetsBrick16 = new CartesianPointSetsBrick(ptArea_16);
        CartesianPointSetsBrick ptSetsBrick17 = new CartesianPointSetsBrick(ptArea_17);
        CartesianPointSetsBrick ptSetsBrick18 = new CartesianPointSetsBrick(ptArea_18);
        CartesianPointSetsBrick ptSetsBrick19 = new CartesianPointSetsBrick(ptArea_19);
        CartesianPointSetsBrick ptSetsBrick20 = new CartesianPointSetsBrick(ptArea_20);
        CartesianPointSetsBrick ptSetsBrick21 = new CartesianPointSetsBrick(ptArea_21);
        CartesianPointSetsBrick ptSetsBrick22 = new CartesianPointSetsBrick(ptArea_22);
        CartesianPointSetsBrick ptSetsBrick23 = new CartesianPointSetsBrick(ptArea_23);
        CartesianPointSetsBrick ptSetsBrick24 = new CartesianPointSetsBrick(ptArea_24);
        CartesianPointSetsBrick ptSetsBrick25 = new CartesianPointSetsBrick(ptArea_25);


        ptSetsBrick1  .setSectionType(SECTION_AIR_ZONE01);
        ptSetsBrick2  .setSectionType(SECTION_AIR_ZONE02);
        ptSetsBrick3  .setSectionType(SECTION_AIR_ZONE03);
        ptSetsBrick4  .setSectionType(SECTION_AIR_ZONE04);
        ptSetsBrick5  .setSectionType(SECTION_AIR_ZONE05);
        ptSetsBrick6  .setSectionType(SECTION_AIR_ZONE06);
        ptSetsBrick7  .setSectionType(SECTION_AIR_ZONE07);
        ptSetsBrick8  .setSectionType(SECTION_AIR_ZONE08);
        ptSetsBrick9  .setSectionType(SECTION_AIR_ZONE09);
        ptSetsBrick10 .setSectionType(SECTION_AIR_ZONE10);
        ptSetsBrick11 .setSectionType(SECTION_AIR_ZONE11);
        ptSetsBrick12 .setSectionType(SECTION_AIR_ZONE12);
        ptSetsBrick13 .setSectionType(SECTION_AIR_ZONE13);
        ptSetsBrick14 .setSectionType(SECTION_AIR_ZONE14);
        ptSetsBrick15 .setSectionType(SECTION_AIR_ZONE15);
        ptSetsBrick16 .setSectionType(SECTION_AIR_ZONE16);
        ptSetsBrick17 .setSectionType(SECTION_AIR_ZONE17);
        ptSetsBrick18 .setSectionType(SECTION_AIR_ZONE18);
        ptSetsBrick19 .setSectionType(SECTION_AIR_ZONE19);
        ptSetsBrick20 .setSectionType(SECTION_AIR_ZONE20);
        ptSetsBrick21 .setSectionType(SECTION_AIR_ZONE21);
        ptSetsBrick22 .setSectionType(SECTION_AIR_ZONE22);
        ptSetsBrick23 .setSectionType(SECTION_AIR_ZONE23);
        ptSetsBrick24 .setSectionType(SECTION_AIR_ZONE24);
        ptSetsBrick25 .setSectionType(SECTION_AIR_ZONE25);


        CartesianPointSetsBrick ptSetsBrickCap        = new CartesianPointSetsBrick( ptArea_Cap      );
        CartesianPointSetsBrick ptSetsBrickTranInner  = new CartesianPointSetsBrick( ptArea_Tran_Inner     );
        CartesianPointSetsBrick ptSetsBrickTranOuter  = new CartesianPointSetsBrick( ptArea_Tran_Outer     );
        CartesianPointSetsBrick ptSetsBrickDiaphragm  = new CartesianPointSetsBrick( ptArea_Diaphragm);
        CartesianPointSetsBrick ptSetsBrickSurround   = new CartesianPointSetsBrick( ptArea_Surround );




        ptSetsBrickCap      .setSectionType(SECTION_AIR_CAP);
        ptSetsBrickTranInner.setSectionType(SECTION_AIR_TRAN_IN);
        ptSetsBrickTranOuter.setSectionType(SECTION_AIR_TRAN_OUT);
        ptSetsBrickDiaphragm.setSectionType(SECTION_AIR_DIAPHRAGM);
        ptSetsBrickSurround .setSectionType(SECTION_AIR_SURROUND);

        /**
         *  10.17. Roger
         *          Assgin for node connectivity
         *
         */
        ptSetsBrickTranInner.setContactNodeFromOtherH(ptArea_Cap, this.BRICK_FACE_OUTER);
        ptSetsBrickTranOuter.setContactNodeFromOtherH(ptArea_Diaphragm, this.BRICK_FACE_INNER);
        ptSetsBrickDiaphragm.setContactNodeFromOtherH(ptArea_Surround, this.BRICK_FACE_INNER);

        /**
         * 10.19. Roger
         *        Assgin Elements And Sets For Tie
         */
        ptSetsBrickCap          .setTUp(ptArea_Cap[0][0].length -2);
        ptSetsBrickTranInner    .setTUp(ptArea_Tran_Inner[0][0].length -2);
        ptSetsBrickTranOuter    .setTUp(ptArea_Tran_Outer[0][0].length -2);
        ptSetsBrickDiaphragm    .setTUp(ptArea_Diaphragm[0][0].length -2);
        ptSetsBrickSurround     .setTUp(ptArea_Surround[0][0].length -2 );
        ptSetsBrickCap          .setTDown(0);
        ptSetsBrickTranInner    .setTDown(0);
        ptSetsBrickTranOuter    .setTDown(0);
        ptSetsBrickDiaphragm    .setTDown(0);
        ptSetsBrickSurround     .setTDown(0);
        ptSetsBrickTranInner    .setIOuter(ptArea_Tran_Inner[0].length -2);
        ptSetsBrickTranOuter    .setIInner(0);
        /**
         *  End
         */
        this._arrayPtSets.add( ptSetsBrickCap);
        this._arrayPtSets.add( ptSetsBrickTranInner);
        this._arrayPtSets.add( ptSetsBrickTranOuter);
        if(this._dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
            this._arrayPtSets.add( ptSetsBrickDiaphragm);
        this._arrayPtSets.add( ptSetsBrickSurround);

        volumeCavityCap += this.calculateRegionVolume(ptArea_Cap,0 );
        volumeCavityCap += this.calculateRegionVolume(ptArea_Tran_Inner, 0);
        volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_Tran_Outer, 0);
        if(this._dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
            volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_Diaphragm, 0);
        volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_Surround, 0);
/*
        System.out.println("Volume Cap: " + this.calculateRegionVolume(ptArea_Cap, 0));
        System.out.println("Volume Tran_Inner: " + this.calculateRegionVolume(ptArea_Tran_Inner, 0));
        System.out.println("Volume Tran_Outer: " + this.calculateRegionVolume(ptArea_Tran_Outer, 0));
        System.out.println("Volume Diaphragm: " + this.calculateRegionVolume(ptArea_Diaphragm, 0));
        System.out.println("Volume Surround: " + this.calculateRegionVolume(ptArea_Surround, 0));*/

        //有上磁鐵
        if (this._dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
        {

            ptSetsBrick1.setContactNodeFromOther(ptArea_2, this.BRICK_FACE_INNER);
            ptSetsBrick2.setContactNodeFromOther(ptArea_3, this.BRICK_FACE_INNER);
            ptSetsBrick2.setContactNodeFromOther(ptArea_4, this.BRICK_FACE_UP);
            ptSetsBrick4.setContactNodeFromOther(ptArea_5, this.BRICK_FACE_INNER);
            ptSetsBrick3.setContactNodeFromOther(ptArea_5, this.BRICK_FACE_UP);
            ptSetsBrick5.setContactNodeFromOther(ptArea_6, this.BRICK_FACE_UP);

            /**
             * 10.19. Roger
             *        Assgin Elements And Sets For Tie
             */
            ptSetsBrick1.setIUp(0);
            ptSetsBrick2.setIUp(0);
            ptSetsBrick3.setIUp(0);
            ptSetsBrick3.setTOuter(ptArea_3[0][0].length - 2);
            ptSetsBrick5.setTOuter(ptArea_5[0][0].length - 2);
            /**
             *  End
             */
            this._arrayPtSets.add(ptSetsBrick1);
            this._arrayPtSets.add(ptSetsBrick2);
            this._arrayPtSets.add(ptSetsBrick3);
            this._arrayPtSets.add(ptSetsBrick4);
            this._arrayPtSets.add(ptSetsBrick5);

            volumeCavityCap += this.calculateRegionVolume(ptArea_1, 1);
            volumeCavityCap += this.calculateRegionVolume(ptArea_2, 1);
            volumeCavityCap += this.calculateRegionVolume(ptArea_3, 1);
            volumeCavityCap += this.calculateRegionVolume(ptArea_4, 1);
            volumeCavityCap += this.calculateRegionVolume(ptArea_5, 1);
/*
            System.out.println("Volume 1: " + this.calculateRegionVolume(ptArea_1, 1));
            System.out.println("Volume 2: " + this.calculateRegionVolume(ptArea_2, 1));
            System.out.println("Volume 3: " + this.calculateRegionVolume(ptArea_3, 1));
            System.out.println("Volume 4: " + this.calculateRegionVolume(ptArea_4, 1));
            System.out.println("Volume 5: " + this.calculateRegionVolume(ptArea_5, 1));*/
        }
        else//無上磁鐵
        {
            ptSetsBrick1.setContactNodeFromOther(ptArea_3, this.BRICK_FACE_INNER);
            ptSetsBrick3.setContactNodeFromOther(ptArea_6, this.BRICK_FACE_UP);
            /**
            * 10.19. Roger
            *        Assgin Elements And Sets For Tie
            */
           ptSetsBrick1.setIUp(0);
           ptSetsBrick3.setIUp(0);
           ptSetsBrick3.setTOuter(ptArea_3[0][0].length - 2);
           /**
            *  End
            */

            this._arrayPtSets.add(ptSetsBrick1);
            this._arrayPtSets.add(ptSetsBrick3);

            volumeCavityCap += this.calculateRegionVolume(ptArea_1, 1);
            volumeCavityCap += this.calculateRegionVolume(ptArea_3, 1);
        }

        //預先分類 2012.11.01 by fox
        int airType;
        if (this._dataCoil.getGeometryType() == this.COIL_TYPE1) // coil type 1
        {
            if (this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE) // 有YokeStage1
            {
                if (this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE) // 有YokeStage2
                {
                    if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE) //coil type 1 ; 有YokeStage2  有YokeStage1 有MagnetOuter
                        airType = DefineSystemConstant.AIRTYPE_COIL1_YOKE1_YOKE2_MAGNETOUT;
                    else
                        airType = DefineSystemConstant.AIRTYPE_COIL1_YOKE1_YOKE2;
                }
                else // 無YokeStage2
                {
                    if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE) //coil type 1 ; 無YokeStage2  有YokeStage1 有MagnetOuter
                        airType = DefineSystemConstant.AIRTYPE_COIL1_YOKE1_MAGNETOUT;
                    else
                        airType = DefineSystemConstant.AIRTYPE_COIL1_YOKE1;
                }
            }
            else
                airType = DefineSystemConstant.AIRTYPE_COIL1;
        }
        else
        {
            if (this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE) // 有YokeStage1
            {
                if (this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE) // 有YokeStage2
                {
                    if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE) //coil type 2 ; 有YokeStage2  有YokeStage1 有MagnetOuter
                        airType = DefineSystemConstant.AIRTYPE_COIL2_YOKE1_YOKE2_MAGNETOUT;
                    else
                        airType = DefineSystemConstant.AIRTYPE_COIL2_YOKE1_YOKE2;
                }
                else // 無YokeStage2
                {
                    if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE) //coil type 1 ; 無YokeStage2  有YokeStage1 有MagnetOuter
                        airType = DefineSystemConstant.AIRTYPE_COIL2_YOKE1_MAGNETOUT;
                    else
                        airType = DefineSystemConstant.AIRTYPE_COIL2_YOKE1;
                }
            }
            else
                airType = DefineSystemConstant.AIRTYPE_COIL2;
        }

        ptSetsBrick6.setContactNodeFromOther(ptArea_8, this.BRICK_FACE_UP);
        ptSetsBrick7.setContactNodeFromOther(ptArea_8, this.BRICK_FACE_INNER);
        ptSetsBrick7.setContactNodeFromOther(ptArea_9, this.BRICK_FACE_UP);
        ptSetsBrick8.setContactNodeFromOther(ptArea_10, this.BRICK_FACE_UP);
        ptSetsBrick9.setContactNodeFromOther(ptArea_10, this.BRICK_FACE_INNER);
        ptSetsBrick10.setContactNodeFromOther(ptArea_11, this.BRICK_FACE_INNER);


        /**
         * 10.19. Roger
         *        Assgin Elements And Sets For Tie
         */
        ptSetsBrick6.setTOuter(ptArea_6[0][0].length - 2);;
        ptSetsBrick8.setTOuter(ptArea_8[0][0].length - 2);
        ptSetsBrick11.setIUp(0);
        /**
         *  End
         */


        this._arrayPtSets.add(ptSetsBrick6);
        this._arrayPtSets.add(ptSetsBrick7);
        this._arrayPtSets.add(ptSetsBrick8);
        this._arrayPtSets.add(ptSetsBrick9);
        this._arrayPtSets.add(ptSetsBrick10);
        this._arrayPtSets.add(ptSetsBrick11);


        volumeAirGapIn += this.calculateRegionVolume(ptArea_6, 1);
        volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_7, 1);
        volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_8, 1);
        volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_9, 1);
        volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_10, 1);
        volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_11, 1);
        volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_12, 1);



        CartesianPointSetsBrick ptSetsBrick26;
        CartesianPointSetsBrick ptSetsBrick27;
        CartesianPointSetsBrick ptSetsBrick28;

        ArrayList array1 = new ArrayList();
        ArrayList array2 = new ArrayList();
        ArrayList array3 = new ArrayList();

        double volumeCavity13;
        double volumeCavity27;

        switch(airType)
        {
            case DefineSystemConstant.AIRTYPE_COIL1_YOKE1_YOKE2_MAGNETOUT://type 1

                //設定相接面共用點
                ptSetsBrick11.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);
                ptSetsBrick13.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_DOWN);
                ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_DOWN);
                ptSetsBrick14.setContactNodeFromOther(ptArea_17, this.BRICK_FACE_INNER);
                ptSetsBrick17.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                ptSetsBrick18.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_DOWN);
                ptSetsBrick16.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_INNER);
                ptSetsBrick18.setContactNodeFromOther(ptArea_20, this.BRICK_FACE_INNER);
                ptSetsBrick20.setContactNodeFromOther(ptArea_21, this.BRICK_FACE_DOWN);
                ptSetsBrick19.setContactNodeFromOther(ptArea_21, this.BRICK_FACE_INNER);
                ptSetsBrick22.setContactNodeFromOther(ptArea_22, this.BRICK_FACE_INNER);

                /**
                 * 10.19. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick13.setTInner(0);
                ptSetsBrick14.setTInner(0);
                ptSetsBrick15.setTInner(0);
                ptSetsBrick16.setTInner(0);
                ptSetsBrick16.setIUp(0);
                ptSetsBrick19.setIUp(0);
                ptSetsBrick21.setIUp(0);
                ptSetsBrick22.setIUp(0);
                /**
                 *  End
                 */


                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);
                this._arrayPtSets.add(ptSetsBrick14);
                this._arrayPtSets.add(ptSetsBrick15);
                this._arrayPtSets.add(ptSetsBrick16);
                this._arrayPtSets.add(ptSetsBrick17);
                this._arrayPtSets.add(ptSetsBrick18);
                this._arrayPtSets.add(ptSetsBrick19);
                this._arrayPtSets.add(ptSetsBrick20);
                this._arrayPtSets.add(ptSetsBrick21);
                this._arrayPtSets.add(ptSetsBrick22);

                volumeAirGapOut += this.calculateRegionVolume(ptArea_13, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_14, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_15, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_16, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_17, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_18, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_19, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_20, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_21, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_22, 1);

                break;


            case DefineSystemConstant.AIRTYPE_COIL1_YOKE1_YOKE2://type 2

                //設定相接面共用點
                ptSetsBrick11.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);
                ptSetsBrick13.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                ptSetsBrick18.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_DOWN);
                ptSetsBrick16.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_INNER);
                ptSetsBrick19.setContactNodeFromOther(ptArea_22, this.BRICK_FACE_INNER);

                /**
                 * 10.31. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick13.setTInner(0);
                ptSetsBrick15.setTInner(0);
                ptSetsBrick16.setTInner(0);
                ptSetsBrick16.setIUp(0);
                ptSetsBrick19.setIUp(0);
                ptSetsBrick22.setIUp(0);
                /**
                 *  End
                 */

                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);
                this._arrayPtSets.add(ptSetsBrick15);
                this._arrayPtSets.add(ptSetsBrick16);
                this._arrayPtSets.add(ptSetsBrick18);
                this._arrayPtSets.add(ptSetsBrick19);
                this._arrayPtSets.add(ptSetsBrick22);

                volumeAirGapOut += this.calculateRegionVolume(ptArea_13, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_15, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_16, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_18, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_19, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_22, 1);

                break;


            case DefineSystemConstant.AIRTYPE_COIL1_YOKE1_MAGNETOUT://type 3

                //設定相接面共用點
                ptSetsBrick11.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);
                ptSetsBrick13.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_DOWN);
                ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                ptSetsBrick14.setContactNodeFromOther(ptArea_17, this.BRICK_FACE_INNER);
                ptSetsBrick17.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                ptSetsBrick18.setContactNodeFromOther(ptArea_20, this.BRICK_FACE_INNER);

                /**
                 * 10.31. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick13.setTInner(0);
                ptSetsBrick14.setTInner(0);
                ptSetsBrick15.setTInner(0);
                ptSetsBrick15.setIUp(0);
                ptSetsBrick18.setIUp(0);
                ptSetsBrick20.setIUp(0);


                /**
                 *  End
                 */

                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);
                this._arrayPtSets.add(ptSetsBrick14);
                this._arrayPtSets.add(ptSetsBrick15);
                this._arrayPtSets.add(ptSetsBrick17);
                this._arrayPtSets.add(ptSetsBrick18);
                this._arrayPtSets.add(ptSetsBrick20);

                volumeAirGapOut += this.calculateRegionVolume(ptArea_13, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_14, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_15, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_17, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_18, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_20, 1);

                break;


            case DefineSystemConstant.AIRTYPE_COIL1_YOKE1://type 4

                //設定相接面共用點
                ptSetsBrick11.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);
                ptSetsBrick13.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);

                /**
                 * 10.31. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick13.setTInner(0);
                ptSetsBrick15.setTInner(0);
                ptSetsBrick15.setIUp(0);
                ptSetsBrick18.setIUp(0);
                /**
                 *  End
                 */

                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);
                this._arrayPtSets.add(ptSetsBrick15);
                this._arrayPtSets.add(ptSetsBrick18);

                volumeAirGapOut += this.calculateRegionVolume(ptArea_13, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_15, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_18, 1);

                break;


            case DefineSystemConstant.AIRTYPE_COIL1://type 5
                ptSetsBrick11.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);

                ptSetsBrick13.setTInner(0);
                ptSetsBrick13.setIUp(0);
                /**
                 * 10.31. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick13.setTInner(0);
                ptSetsBrick13.setIUp(0);
                /**
                 *  End
                 */

                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);

                volumeAirGapOut += this.calculateRegionVolume(ptArea_13, 1);

                break;
            case DefineSystemConstant.AIRTYPE_COIL2_YOKE1_YOKE2_MAGNETOUT://type 6

                array1.add(ptArea_23);

                array2.add(ptArea_24);
                array2.add(ptArea_25);

                array3.add(ptArea_16);//////////////////
                array3.add(ptArea_15);//
                array3.add(ptArea_14);//順序不能重複
                array3.add(ptArea_13);//
                array3.add(ptArea_12);//////////////////

                for(int j = 0 ; j < jCount ; j++)
                {
                   ptArea_26[j] = this.calculatePt3(1, array1, array2, array3, _hCircle[6], _hCircle[7], j);
                   ptArea_27[j] = this.calculatePt3(2, array1, array2, array3, _hCircle[6], _hCircle[7], j);
                   ptArea_28[j] = this.calculatePt3(3, array1, array2, array3, _hCircle[6], _hCircle[7], j);
                }

                ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);
                ptSetsBrick26 .setSectionType(SECTION_AIR_ZONE26);
                ptSetsBrick27 = new CartesianPointSetsBrick(ptArea_27);
                ptSetsBrick27 .setSectionType(SECTION_AIR_ZONE27);
                ptSetsBrick28 = new CartesianPointSetsBrick(ptArea_28);
                ptSetsBrick28 .setSectionType(SECTION_AIR_ZONE28);


                //設定相接面共用點
                ptSetsBrick11.setContactNodeFromOther(ptArea_25, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);
                ptSetsBrick13.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_DOWN);
                ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_DOWN);
                ptSetsBrick14.setContactNodeFromOther(ptArea_17, this.BRICK_FACE_INNER);
                ptSetsBrick17.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                ptSetsBrick18.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_DOWN);
                ptSetsBrick16.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_INNER);
                ptSetsBrick18.setContactNodeFromOther(ptArea_20, this.BRICK_FACE_INNER);
                ptSetsBrick20.setContactNodeFromOther(ptArea_21, this.BRICK_FACE_DOWN);
                ptSetsBrick19.setContactNodeFromOther(ptArea_21, this.BRICK_FACE_INNER);
                ptSetsBrick21.setContactNodeFromOther(ptArea_22, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_24, this.BRICK_FACE_DOWN);

                //modify by fox 2012.11.02
                ptSetsBrick28.setContactNodeFromOther(ptArea_27, this.BRICK_FACE_DOWN);
                ptSetsBrick27.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_DOWN);

                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);

                ptSetsBrick26.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick27.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick28.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);


                /**
                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick14.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick15.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick16.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                */
               //end modify


                /**
                 * 10.31. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick24.setTInner(0);
                ptSetsBrick23.setTInner(0);
                ptSetsBrick27.setTInner(0);
                ptSetsBrick23.setIUp(0);
                ptSetsBrick26.setIUp(0);
                ptSetsBrick16.setIUp(0);
                ptSetsBrick19.setIUp(0);
                ptSetsBrick21.setIUp(0);
                ptSetsBrick22.setIUp(0);
                /**
                 *  End
                 */


                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);
                this._arrayPtSets.add(ptSetsBrick14);
                this._arrayPtSets.add(ptSetsBrick15);
                this._arrayPtSets.add(ptSetsBrick16);
                this._arrayPtSets.add(ptSetsBrick17);
                this._arrayPtSets.add(ptSetsBrick18);
                this._arrayPtSets.add(ptSetsBrick19);
                this._arrayPtSets.add(ptSetsBrick20);
                this._arrayPtSets.add(ptSetsBrick21);
                this._arrayPtSets.add(ptSetsBrick22);
                this._arrayPtSets.add(ptSetsBrick23);
                this._arrayPtSets.add(ptSetsBrick24);
                this._arrayPtSets.add(ptSetsBrick25);
                this._arrayPtSets.add(ptSetsBrick26);
                this._arrayPtSets.add(ptSetsBrick27);
                this._arrayPtSets.add(ptSetsBrick28);

                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_25, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_24, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_28, 1);

                //volumeAirGapOut += this.calculateRegionVolume(ptArea_27, 1);

                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_14, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_15, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_16, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_17, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_18, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_19, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_20, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_21, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_22, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_23, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_26, 1);

                volumeCavity13 = this.calculateRegionVolume(ptArea_13, 1);
                volumeCavity27 = this.calculateRegionVolume(ptArea_27, 1);

                if (_hCircle[8] > _hCircle[7])
                {
                    double volumeAirGapOut1;
                    double volumeAirGapOut2;
                    if (_hCircle[8] < _hCircle[6])
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[8]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8] * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[8]  - _hCircle[7] );
                    }
                    else
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[6]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8]  * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[6]  - _hCircle[7] );
                    }
                    volumeCavity27 -= volumeAirGapOut1;
                    volumeCavity13 -= volumeAirGapOut2;

                    volumeAirGapOut += volumeAirGapOut1;
                    volumeAirGapOut += volumeAirGapOut2;
                }

                volumeCavityMagnetYoke += volumeCavity13;
                volumeCavityDiaphSurr += volumeCavity27;


                break;


            case DefineSystemConstant.AIRTYPE_COIL2_YOKE1_YOKE2://type 7

                array1.add(ptArea_23);

                array2.add(ptArea_24);
                array2.add(ptArea_25);

                array3.add(ptArea_16); //////////////////
                array3.add(ptArea_15); //順序不能重複
                array3.add(ptArea_13); //
                array3.add(ptArea_12); //////////////////

                for(int j = 0 ; j < jCount ; j++)
                {
                   ptArea_26[j] = this.calculatePt3(1, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                   ptArea_27[j] = this.calculatePt3(2, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                   ptArea_28[j] = this.calculatePt3(3, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                }

                ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);
                ptSetsBrick26 .setSectionType(SECTION_AIR_ZONE26);
                ptSetsBrick27 = new CartesianPointSetsBrick(ptArea_27);
                ptSetsBrick27 .setSectionType(SECTION_AIR_ZONE27);
                ptSetsBrick28 = new CartesianPointSetsBrick(ptArea_28);
                ptSetsBrick28 .setSectionType(SECTION_AIR_ZONE28);

                //設定相接面共用點
                ptSetsBrick11.setContactNodeFromOther(ptArea_25, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);
                ptSetsBrick13.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                ptSetsBrick18.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_DOWN);
                ptSetsBrick16.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_INNER);
                ptSetsBrick19.setContactNodeFromOther(ptArea_22, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_24, this.BRICK_FACE_DOWN);


                //modify by fox 2012.11.02
                ptSetsBrick28.setContactNodeFromOther(ptArea_27, this.BRICK_FACE_DOWN);
                ptSetsBrick27.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_DOWN);

                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);

                ptSetsBrick26.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick27.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick28.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);


                /*
                                 ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                                 ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                                 ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                                 ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                                 ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                                 ptSetsBrick15.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                                 ptSetsBrick16.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                 */

                /**
                 * 10.31. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick24.setTInner(0);
                ptSetsBrick23.setTInner(0);
                ptSetsBrick27.setTInner(0);
                ptSetsBrick23.setIUp(0);
                ptSetsBrick26.setIUp(0);
                ptSetsBrick16.setIUp(0);
                ptSetsBrick19.setIUp(0);
                ptSetsBrick22.setIUp(0);
                /**
                 *  End
                 */

                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);
                this._arrayPtSets.add(ptSetsBrick15);
                this._arrayPtSets.add(ptSetsBrick16);
                this._arrayPtSets.add(ptSetsBrick18);
                this._arrayPtSets.add(ptSetsBrick19);
                this._arrayPtSets.add(ptSetsBrick22);

                this._arrayPtSets.add(ptSetsBrick23);
                this._arrayPtSets.add(ptSetsBrick24);
                this._arrayPtSets.add(ptSetsBrick25);
                this._arrayPtSets.add(ptSetsBrick26);
                this._arrayPtSets.add(ptSetsBrick27);
                this._arrayPtSets.add(ptSetsBrick28);

                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_25, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_24, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_28, 1);

                //volumeAirGapOut += this.calculateRegionVolume(ptArea_27, 1);

                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_15, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_16, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_18, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_19, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_22, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_23, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_26, 1);

                volumeCavity13 = this.calculateRegionVolume(ptArea_13, 1);
                volumeCavity27 = this.calculateRegionVolume(ptArea_27, 1);

                if (_hCircle[8]  > _hCircle[7] )
                {
                    double volumeAirGapOut1;
                    double volumeAirGapOut2;
                    if (_hCircle[8]  < _hCircle[6] )
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[8]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8]  * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[8]  - _hCircle[7] );
                    }
                    else
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[6]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8]  * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[6]  - _hCircle[7] );
                    }
                    volumeCavity27 -= volumeAirGapOut1;
                    volumeCavity13 -= volumeAirGapOut2;

                    volumeAirGapOut += volumeAirGapOut1;
                    volumeAirGapOut += volumeAirGapOut2;
                }

                volumeCavityMagnetYoke += volumeCavity13;
                volumeCavityDiaphSurr += volumeCavity27;


                break;


            case DefineSystemConstant.AIRTYPE_COIL2_YOKE1_MAGNETOUT://type 8
                array1.add(ptArea_23);

                array2.add(ptArea_24);
                array2.add(ptArea_25);

                array3.add(ptArea_15); //////////////////
                array3.add(ptArea_14); //順序不能重複
                array3.add(ptArea_13); //
                array3.add(ptArea_12); //////////////////

                for(int j = 0 ; j < jCount ; j++)
                {
                   ptArea_26[j] = this.calculatePt3(1, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                   ptArea_27[j] = this.calculatePt3(2, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                   ptArea_28[j] = this.calculatePt3(3, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                }

                ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);
                ptSetsBrick26 .setSectionType(SECTION_AIR_ZONE26);
                ptSetsBrick27 = new CartesianPointSetsBrick(ptArea_27);
                ptSetsBrick27 .setSectionType(SECTION_AIR_ZONE27);
                ptSetsBrick28 = new CartesianPointSetsBrick(ptArea_28);
                ptSetsBrick28 .setSectionType(SECTION_AIR_ZONE28);

                //設定相接面共用點
                ptSetsBrick11.setContactNodeFromOther(ptArea_25, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);
                ptSetsBrick13.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_DOWN);
                ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                ptSetsBrick14.setContactNodeFromOther(ptArea_17, this.BRICK_FACE_INNER);
                ptSetsBrick17.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                ptSetsBrick18.setContactNodeFromOther(ptArea_20, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_24, this.BRICK_FACE_DOWN);


                //modify by fox 2012.11.02
                ptSetsBrick28.setContactNodeFromOther(ptArea_27, this.BRICK_FACE_DOWN);
                ptSetsBrick27.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_DOWN);

                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);

                ptSetsBrick26.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick27.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick28.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                /*
                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick14.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick15.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
*/
                /**
                 * 10.31. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick24.setTInner(0);
                ptSetsBrick23.setTInner(0);
                ptSetsBrick27.setTInner(0);
                ptSetsBrick23.setIUp(0);
                ptSetsBrick26.setIUp(0);
                ptSetsBrick15.setIUp(0);
                ptSetsBrick18.setIUp(0);
                ptSetsBrick20.setIUp(0);
                /**
                 *  End
                 */

                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);
                this._arrayPtSets.add(ptSetsBrick14);
                this._arrayPtSets.add(ptSetsBrick15);
                this._arrayPtSets.add(ptSetsBrick17);
                this._arrayPtSets.add(ptSetsBrick18);
                this._arrayPtSets.add(ptSetsBrick20);

                this._arrayPtSets.add(ptSetsBrick23);
                this._arrayPtSets.add(ptSetsBrick24);
                this._arrayPtSets.add(ptSetsBrick25);
                this._arrayPtSets.add(ptSetsBrick26);
                this._arrayPtSets.add(ptSetsBrick27);
                this._arrayPtSets.add(ptSetsBrick28);

                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_25, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_24, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_28, 1);

                //volumeAirGapOut += this.calculateRegionVolume(ptArea_27, 1);

                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_14, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_15, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_17, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_18, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_20, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_23, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_26, 1);

                volumeCavity13 = this.calculateRegionVolume(ptArea_13, 1);
                volumeCavity27 = this.calculateRegionVolume(ptArea_27, 1);

                if (_hCircle[8]  > _hCircle[7] )
                {
                    double volumeAirGapOut1;
                    double volumeAirGapOut2;
                    if (_hCircle[8]  < _hCircle[6] )
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[8]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8]  * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[8]  - _hCircle[7] );
                    }
                    else
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[6]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8]  * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[6]  - _hCircle[7] );
                    }
                    volumeCavity27 -= volumeAirGapOut1;
                    volumeCavity13 -= volumeAirGapOut2;

                    volumeAirGapOut += volumeAirGapOut1;
                    volumeAirGapOut += volumeAirGapOut2;
                }

                volumeCavityMagnetYoke += volumeCavity13;
                volumeCavityDiaphSurr += volumeCavity27;



                break;

            case DefineSystemConstant.AIRTYPE_COIL2_YOKE1://type 9
                array1.add(ptArea_23);

                array2.add(ptArea_24);
                array2.add(ptArea_25);

                array3.add(ptArea_15); //////////////////
                array3.add(ptArea_13); //順序不能重複
                array3.add(ptArea_12); //////////////////

                for(int j = 0 ; j < jCount ; j++)
                {
                   ptArea_26[j] = this.calculatePt3(1, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                   ptArea_27[j] = this.calculatePt3(2, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                   ptArea_28[j] = this.calculatePt3(3, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                }

                ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);
                ptSetsBrick26 .setSectionType(SECTION_AIR_ZONE26);
                ptSetsBrick27 = new CartesianPointSetsBrick(ptArea_27);
                ptSetsBrick27 .setSectionType(SECTION_AIR_ZONE27);
                ptSetsBrick28 = new CartesianPointSetsBrick(ptArea_28);
                ptSetsBrick28 .setSectionType(SECTION_AIR_ZONE28);

                ptSetsBrick11.setContactNodeFromOther(ptArea_25, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);
                ptSetsBrick13.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_24, this.BRICK_FACE_DOWN);


                //modify by fox 2012.11.02
                ptSetsBrick28.setContactNodeFromOther(ptArea_27, this.BRICK_FACE_DOWN);
                ptSetsBrick27.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_DOWN);

                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);

                ptSetsBrick26.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick27.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick28.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                /*
                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick15.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
*/
                /**
                 * 10.31. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick24.setTInner(0);
                ptSetsBrick23.setTInner(0);
                ptSetsBrick27.setTInner(0);
                ptSetsBrick23.setIUp(0);
                ptSetsBrick26.setIUp(0);
                ptSetsBrick15.setIUp(0);
                ptSetsBrick18.setIUp(0);
                /**
                 *  End
                 */

                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);
                this._arrayPtSets.add(ptSetsBrick15);
                this._arrayPtSets.add(ptSetsBrick18);

                this._arrayPtSets.add(ptSetsBrick23);
                this._arrayPtSets.add(ptSetsBrick24);
                this._arrayPtSets.add(ptSetsBrick25);
                this._arrayPtSets.add(ptSetsBrick26);
                this._arrayPtSets.add(ptSetsBrick27);
                this._arrayPtSets.add(ptSetsBrick28);

                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_25, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_24, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_28, 1);

                //volumeAirGapOut += this.calculateRegionVolume(ptArea_27, 1);

                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_15, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_18, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_23, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_26, 1);

                volumeCavity13 = this.calculateRegionVolume(ptArea_13, 1);
                volumeCavity27 = this.calculateRegionVolume(ptArea_27, 1);

                if (_hCircle[8]  > _hCircle[7] )
                {
                    double volumeAirGapOut1;
                    double volumeAirGapOut2;
                    if (_hCircle[8]  < _hCircle[6] )
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[8]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8]  * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[8]  - _hCircle[7] );
                    }
                    else
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[6]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8]  * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[6]  - _hCircle[7] );
                    }
                    volumeCavity27 -= volumeAirGapOut1;
                    volumeCavity13 -= volumeAirGapOut2;

                    volumeAirGapOut += volumeAirGapOut1;
                    volumeAirGapOut += volumeAirGapOut2;
                }

                volumeCavityMagnetYoke += volumeCavity13;
                volumeCavityDiaphSurr += volumeCavity27;


                break;
            case DefineSystemConstant.AIRTYPE_COIL2://type 10

                array1.add(ptArea_23);

                array2.add(ptArea_24);
                array2.add(ptArea_25);

                array3.add(ptArea_13); //順序不能重複
                array3.add(ptArea_12); //////////////////

                for(int j = 0 ; j < jCount ; j++)
                {
                   ptArea_26[j] = this.calculatePt3(1, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                   ptArea_27[j] = this.calculatePt3(2, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                   ptArea_28[j] = this.calculatePt3(3, array1, array2, array3, _hCircle[6] , _hCircle[7] , j);
                }

                ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);
                ptSetsBrick26 .setSectionType(SECTION_AIR_ZONE26);
                ptSetsBrick27 = new CartesianPointSetsBrick(ptArea_27);
                ptSetsBrick27 .setSectionType(SECTION_AIR_ZONE27);
                ptSetsBrick28 = new CartesianPointSetsBrick(ptArea_28);
                ptSetsBrick28 .setSectionType(SECTION_AIR_ZONE28);

                ptSetsBrick11.setContactNodeFromOther(ptArea_25, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);
                ptSetsBrick25.setContactNodeFromOther(ptArea_24, this.BRICK_FACE_DOWN);


                //modify by fox 2012.11.02
                ptSetsBrick28.setContactNodeFromOther(ptArea_27, this.BRICK_FACE_DOWN);
                ptSetsBrick27.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_DOWN);

                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_28, this.BRICK_FACE_INNER);

                ptSetsBrick26.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick26.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick27.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick27.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);

                ptSetsBrick28.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_INNER);
                ptSetsBrick28.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);


                /*
                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
*/
                /**
                 * 10.31. Roger
                 *        Assgin Elements And Sets For Tie
                 */
                ptSetsBrick24.setTInner(0);
                ptSetsBrick23.setTInner(0);
                ptSetsBrick27.setTInner(0);
                ptSetsBrick23.setIUp(0);
                ptSetsBrick26.setIUp(0);
                ptSetsBrick13.setIUp(0);
                /**
                 *  End
                 */

                this._arrayPtSets.add(ptSetsBrick12);
                this._arrayPtSets.add(ptSetsBrick13);
                this._arrayPtSets.add(ptSetsBrick23);
                this._arrayPtSets.add(ptSetsBrick24);
                this._arrayPtSets.add(ptSetsBrick25);
                this._arrayPtSets.add(ptSetsBrick26);
                this._arrayPtSets.add(ptSetsBrick27);
                this._arrayPtSets.add(ptSetsBrick28);

                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_25, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_24, 1);
                volumeCavityMagnetYoke += this.calculateRegionVolume(ptArea_28, 1);

                //volumeAirGapOut += this.calculateRegionVolume(ptArea_27, 1);

                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_23, 1);
                volumeCavityDiaphSurr += this.calculateRegionVolume(ptArea_26, 1);

                volumeCavity13 = this.calculateRegionVolume(ptArea_13, 1);
                volumeCavity27 = this.calculateRegionVolume(ptArea_27, 1);

                if (_hCircle[8]  > _hCircle[7] )
                {
                    double volumeAirGapOut1;
                    double volumeAirGapOut2;
                    if (_hCircle[8]  < _hCircle[6] )
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[8]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8]  * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[8]  - _hCircle[7] );
                    }
                    else
                    {
                        volumeAirGapOut1 = (Math.PI * _vCircle[5] * _vCircle[5] - Math.PI * _vCircle[7]  * _vCircle[7] ) * (_hCircle[6]  - _hCircle[7] );
                        volumeAirGapOut2 = (Math.PI * _vCircle[8]  * _vCircle[8]  - Math.PI * _vCircle[5] * _vCircle[5] ) * (_hCircle[6]  - _hCircle[7] );
                    }
                    volumeCavity27 -= volumeAirGapOut1;
                    volumeCavity13 -= volumeAirGapOut2;

                    volumeAirGapOut += volumeAirGapOut1;
                    volumeAirGapOut += volumeAirGapOut2;
                }

                volumeCavityMagnetYoke += volumeCavity13;
                volumeCavityDiaphSurr += volumeCavity27;


                break;
        }
    }


    /**
     * <p>得到空氣分區的資訊</p>
     *
     * @param vCircle double[]  垂直線分割點
     * @param hCircle double[]  水平線分割點
     * @param lCircle int[]  高度分割數量
     * @param wCircle int[]  寬度分割數量
     * @param lCircleValue double[]   高度分割單位長
     * @param wCircleValue double[]   寬度分割單位長
     * @param factor double             根據V4-V6之間的距離乘上的比例
     * @param factorCoilBottom double   根據V4-V6之間的距離乘上的比例 適用範圍僅 Coil Bottom
     * @return double
     * <p>V&H 分布圖 (有紙筒)</p>
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/AirMeshVHwBobbin.png"  align="center" class="border" width="624" height="436" />
     * <p>V&H 分布圖 (無紙筒)</p>
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/AirMeshVHwoBobbin.png"  align="center" class="border" width="570" height="450" />
     */

    public void getDivisionInfo()
    {


        //Cap
        ElementPoint capStartPt = this._dataCap.getElementPointStart(this.XZView);
        ElementPoint capEndPt = this._dataCap.getElementPointEnd(this.XZView);

        //MagnetTop
        ElementPoint magnetTopStartPt = this._dataMagnetTop.getElementPointStart(this.XZView);
        ElementPoint magnetTopEndPt = this._dataMagnetTop.getElementPointEnd(this.XZView);

        //TopPlate
        ElementPoint topPlateStartPt = this._dataTopPlate.getElementPointStart(this.XZView);
        ElementPoint topPlateEndPt = this._dataTopPlate.getElementPointEnd(this.XZView);

        //Magnet
        ElementPoint magnetStartPt = this._dataMagnet.getElementPointStart(this.XZView);
        ElementPoint magnetEndPt = this._dataMagnet.getElementPointEnd(this.XZView);

        //Coil
        ElementPoint coil_StartPt = this._dataCoil.getElementPointStart(this.XZView);
        ElementPoint coil_OuterStartPt = this._dataCoil.getElementPointOuterStart(this.XZView);
        ElementPoint coil_OuterEndPt = this._dataCoil.getElementPointOuterEnd(this.XZView);

        //Coil Outer
        ElementPoint coil_CoilStartPt = this._dataCoil.getElementPointCoilStart(this.XZView);//new
        ElementPoint coil_CoilOuterEndPt = this._dataCoil.getElementPointCoilOuterEnd(this.XZView);//new

        //YokeBase
        ElementPoint yokeBaseStartPt = this._dataYokeBase.getElementPointStart(this.XZView);
        ElementPoint yokeBaseMidUpPt = this._dataYokeBase.getElementPointMiddleUp(this.XZView);
        ElementPoint yokeBaseEndPt = this._dataYokeBase.getElementPointEnd(this.XZView);

        //YokeStage1
        ElementPoint yokeStage1StartPt = this._dataYokeStage1.getElementPointStart(this.XZView);
        ElementPoint yokeStage1MidUpPt = this._dataYokeStage1.getElementPointMiddleUp(this.XZView);
        ElementPoint yokeStage1EndPt = this._dataYokeStage1.getElementPointEnd(this.XZView);

        //YokeStage2
        ElementPoint yokeStage2StartPt = this._dataYokeStage2.getElementPointStart(this.XZView);
        ElementPoint yokeStage2MidUpPt = this._dataYokeStage2.getElementPointMiddleUp(this.XZView);
        ElementPoint yokeStage2EndPt = this._dataYokeStage2.getElementPointEnd(this.XZView);

        //MagnetOuter
        ElementPoint magnetOuterStartPt = this._dataMagnetOuter.getElementPointStart(this.XZView);
        ElementPoint magnetOuterEndPt = this._dataMagnetOuter.getElementPointEnd(this.XZView);

        _vCircle[0] = 0;
        if(this._dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
            _vCircle[1] = magnetTopEndPt.X();
        else
            _vCircle[1] = topPlateEndPt.X();

        _vCircle[2] = topPlateEndPt.X();
        _vCircle[3] = magnetEndPt.X();
        _vCircle[4] = coil_StartPt.X();
        if(this._dataCoil.getGeometryType() == this.COIL_TYPE1)//new
            _vCircle[5] = coil_OuterEndPt.X();//new
        else
            _vCircle[5] = (yokeBaseMidUpPt.X() +  coil_CoilOuterEndPt.X()) / 2;//new

        _vCircle[6] = coil_OuterEndPt.X();//new
        _vCircle[7] = coil_CoilOuterEndPt.X();//new
        _vCircle[8] = yokeBaseMidUpPt.X();
        if(this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE)
            _vCircle[9] = magnetOuterStartPt.X();
        else
            _vCircle[9] = yokeStage1MidUpPt.X();

        _vCircle[10] = yokeStage1MidUpPt.X();
        _vCircle[11] = yokeStage2MidUpPt.X();


        _unitWidth = _vCircle[6] - _vCircle[4];  //unit width comes from



        _hCircle[0] = capEndPt.Y() - 2 * _unitWidth;

        if(this._dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
            _hCircle[1] = magnetTopStartPt.Y();
        else
            _hCircle[1] = topPlateStartPt.Y();

        _hCircle[2] = topPlateStartPt.Y();
        _hCircle[3] = magnetStartPt.Y();
        _hCircle[4] = yokeBaseStartPt.Y();
        _hCircle[5] = coil_OuterEndPt.Y();
        _hCircle[6] = coil_CoilStartPt.Y();
        _hCircle[7] = coil_CoilOuterEndPt.Y();

        if(this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
            _hCircle[8] = yokeStage1StartPt.Y();
        else
        {
            if (coil_OuterStartPt.Y() >= yokeBaseMidUpPt.Y())
                _hCircle[8] = yokeBaseMidUpPt.Y() - (yokeBaseMidUpPt.Y() - yokeBaseStartPt.Y()) / 3;// - unitWidth; Modifyed by Fox 20121211
            else
                _hCircle[8] = coil_OuterStartPt.Y() - (coil_OuterStartPt.Y() - yokeBaseStartPt.Y()) / 3;// - unitWidth; Modifyed by Fox 20121211
        }

        if(this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE)
            _hCircle[9] = magnetOuterStartPt.Y();
        else
            _hCircle[9] = yokeStage1StartPt.Y();

        if(this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
            _hCircle[10] = yokeStage2StartPt.Y();
        else
        {
            if (coil_OuterStartPt.Y() >= yokeStage1MidUpPt.Y())
                _hCircle[10] = yokeStage1MidUpPt.Y() - (yokeStage1MidUpPt.Y() - yokeStage1StartPt.Y()) / 3;// - unitWidth; Modifyed by Fox 20121211
            else
                _hCircle[10] = coil_OuterStartPt.Y() - (coil_OuterStartPt.Y() - yokeStage1StartPt.Y()) / 3;// - unitWidth; Modifyed by Fox 20121211
        }

        if(coil_OuterStartPt.Y() >= yokeStage2MidUpPt.Y())
            _hCircle[11] = yokeStage2MidUpPt.Y() - (yokeStage2MidUpPt.Y() - yokeStage2StartPt.Y()) / 3;// - unitWidth; Modifyed by Fox 20121211
        else
            _hCircle[11] = coil_OuterStartPt.Y() - (coil_OuterStartPt.Y() - yokeStage2StartPt.Y()) / 3;// - unitWidth; Modifyed by Fox 20121211

        if(this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
            _hCircle[12] = _hCircle[11];
        else
            if(this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
                _hCircle[12] = _hCircle[10];
            else
                _hCircle[12] = _hCircle[8];


        _lCircle[0] = (int) Math.round(Math.abs(_hCircle[0] - _hCircle[1]) / (_factorCircle * _unitWidth));
        if (_lCircle[0] == 0)
            _lCircle[0] = 1;
        //divide_L1_value = Math.abs(h0 - h1) / (double) divide_L1_number;
        _lCircleValue[0] = Math.abs(_hCircle[0] - _hCircle[1]) / (double) _lCircle[0];

         _lCircle[1] = (int) Math.round(Math.abs(_hCircle[1] - _hCircle[2]) / (_factorCircle * _unitWidth));
        if ( _lCircle[1] == 0)
             _lCircle[1] = 1;
        //divide_L2_value = Math.abs(h1 - h2) / (double) divide_L2_number;
        _lCircleValue[1] = Math.abs(_hCircle[1] - _hCircle[2]) / (double)  _lCircle[1];

        _lCircle[2] = (int) Math.round(Math.abs(_hCircle[2] - _hCircle[3]) / (_factorCircleBottom *_unitWidth));
        if (_lCircle[2] == 0)
            _lCircle[2] = 1;
        //divide_L3_value = Math.abs(h2 - h3) / (double) divide_L3_number;
        _lCircleValue[2] = Math.abs(_hCircle[2] - _hCircle[3]) / (double) _lCircle[2];

        _lCircle[3] = (int) Math.round(Math.abs(_hCircle[3] - _hCircle[5]) / (_factorCircleBottom *_unitWidth));
        if (_lCircle[3] == 0)
            _lCircle[3] = 1;
        //divide_L4_value = Math.abs(h3 - h5) / (double) divide_L4_number;
        _lCircleValue[3] = Math.abs(_hCircle[3] - _hCircle[5]) / (double) _lCircle[3];

        _lCircle[4] = (int) Math.round(Math.abs(_hCircle[5] - _hCircle[4]) / (_factorCircleBottom *_unitWidth));
        if (_lCircle[4] == 0)
            _lCircle[4] = 1;
        //divide_L5_value = Math.abs(h5 - h4) / (double) divide_L5_number;
        _lCircleValue[4] = Math.abs(_hCircle[5] - _hCircle[4]) / (double) _lCircle[4];

        _lCircle[5] = (int) Math.round(Math.abs(_hCircle[5] - _hCircle[4]) / (_factorCircleBottom *_unitWidth));
        if (_lCircle[5] == 0)
            _lCircle[5] = 1;
        //divide_L6_value = Math.abs(h6 - h5) / (double) divide_L6_number;
        _lCircleValue[5]  = Math.abs(_hCircle[8] - _hCircle[5]) / (double) _lCircle[5];

        _lCircle[6] = (int) Math.round(Math.abs(_hCircle[9] - _hCircle[8]) / (_factorCircle * _unitWidth));
        if (_lCircle[6] == 0)
            _lCircle[6] = 1;
        _lCircleValue[6]  = Math.abs(_hCircle[9] - _hCircle[8]) / (double) _lCircle[6];

        _lCircle[7] = (int) Math.round(Math.abs(_hCircle[10] - _hCircle[9]) / (_factorCircle * _unitWidth));
        if (_lCircle[7] == 0)
            _lCircle[7] = 1;
        _lCircleValue[7] = Math.abs(_hCircle[10] - _hCircle[9]) / (double) _lCircle[7];

        _lCircle[8] = (int) Math.round(Math.abs(_hCircle[11] - _hCircle[10]) / (_factorCircle * _unitWidth));
        if (_lCircle[8] == 0)
            _lCircle[8] = 1;
        _lCircleValue[8] = Math.abs(_hCircle[11] - _hCircle[10]) / (double) _lCircle[8];

        _lCircle[9] = (int) Math.round(Math.abs(_hCircle[12] - _hCircle[6]) /  (_factorCircle * _unitWidth));
        if (_lCircle[9] == 0)
            _lCircle[9] = 1;
        _lCircleValue[9] = Math.abs(_hCircle[12] - _hCircle[6]) / (double) _lCircle[9];

        _lCircle[10] = (int) Math.round(Math.abs(_hCircle[7] - _hCircle[5]) / (_factorCircleBottom *_unitWidth));
        if (_lCircle[10] == 0)
            _lCircle[10] = 1;
        _lCircleValue[10] = Math.abs(_hCircle[7] - _hCircle[5]) / (double) _lCircle[10];


        _wCircle[0] = (int) Math.round(Math.abs(_vCircle[1] - _vCircle[0]) / (_factorCircle * _unitWidth));
        if (_wCircle[0] == 0)
            _wCircle[0] = 1;
        _wCircleValue[0] = Math.abs(_vCircle[1] - _vCircle[0]) / (double) _wCircle[0];

        _wCircle[1] = (int) Math.round(Math.abs(_vCircle[2] - _vCircle[1]) / (_factorCircle * _unitWidth));
        if (_wCircle[1] == 0)
            _wCircle[1] = 1;
        _wCircleValue[1] = Math.abs(_vCircle[2] - _vCircle[1]) / (double) _wCircle[1];

        _wCircle[2] = (int) Math.round(Math.abs(_vCircle[4] - _vCircle[2]) / (_factorCircleBottom *_unitWidth));
        if (_wCircle[2] == 0)
            _wCircle[2] = 1;
        _wCircleValue[2]  = Math.abs(_vCircle[4] - _vCircle[2]) / (double) _wCircle[2];

        _wCircle[3]= (int) Math.round(Math.abs(_vCircle[2] - _vCircle[3]) / (_factorCircleBottom *_unitWidth));
        if (_wCircle[3] == 0)
            _wCircle[3] = 1;
        _wCircleValue[3]  = Math.abs(_vCircle[2] - _vCircle[3]) / (double) _wCircle[3];

        _wCircle[4] = (int) Math.round(Math.abs(_vCircle[6] - _vCircle[4]) / (_factorCircleBottom *_unitWidth));
        if (_wCircle[4] == 0)
            _wCircle[4] = 1;
        _wCircleValue[4]  = Math.abs(_vCircle[6] - _vCircle[4]) / (double) _wCircle[4];

        _wCircle[5] = (int) Math.round(Math.abs(_vCircle[8] - _vCircle[5]) / (_factorCircleBottom *_unitWidth));
        if (_wCircle[5] == 0)
            _wCircle[5] = 1;
        _wCircleValue[5]  = Math.abs(_vCircle[8] - _vCircle[5]) / (double) _wCircle[5];

        _wCircle[6] = 1;//new
        _wCircleValue[6]  = Math.abs(_vCircle[7] - _vCircle[6]);//new

       // factor =2;

        _wCircle[7] = (int) Math.round(Math.abs(_vCircle[9] - _vCircle[8]) / (_factorCircle * _unitWidth));
        if (_wCircle[7] == 0)
            _wCircle[7] = 1;
        _wCircleValue[7]  = Math.abs(_vCircle[9] - _vCircle[8]) / (double) _wCircle[7];

        _wCircle[8] = (int) Math.round(Math.abs(_vCircle[10] - _vCircle[9]) / (_factorCircle * _unitWidth));
        if (_wCircle[8] == 0)
            _wCircle[8] = 1;
        _wCircleValue[8]  = Math.abs(_vCircle[10] - _vCircle[9]) / (double) _wCircle[8];

        _wCircle[9] = (int) Math.round(Math.abs(_vCircle[11] - _vCircle[10]) / (_factorCircle * _unitWidth));
        if (_wCircle[9] == 0)
            _wCircle[9] = 1;
        _wCircleValue[9]  = Math.abs(_vCircle[11] - _vCircle[10]) / (double)( _wCircle[9] *2);



    }


    private stepb_cartesian_point[][] calculatePt(double theta, int widthPartitionCount, double widthPartitionValue,
                                                  int heightPartitionCount, double heightPartitionValue,
                                                  double verticalStart, double horizontalStart)
    {
        stepb_cartesian_point[][] ptArea = new stepb_cartesian_point[heightPartitionCount + 1][];
        for (int i = 0; i <= heightPartitionCount; i++)
        {
            stepb_cartesian_point[] ptThick = new stepb_cartesian_point[widthPartitionCount + 1];
            for (int t = 0; t <= widthPartitionCount; t++)
            {
                double x = (verticalStart + widthPartitionValue * t) * Math.cos(theta);
                double y = (verticalStart + widthPartitionValue * t) * Math.sin(theta);
                double z = horizontalStart - heightPartitionValue * i;
                stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                ptThick[t] = cPt;
            }
            ptArea[i] = ptThick;
        }

        return ptArea;
    }
    private stepb_cartesian_point[][][] calculateShellUnderPt(DataShell dataShell,double horizontalStart)
    {
        stepb_cartesian_point[][] ptOnCap = dataShell.getShellPts(-1, 1);
        int jCount = ptOnCap.length;
        int iCount = ptOnCap[0].length;
        int tCount = 3;
        stepb_cartesian_point[][][] ptAreaCap = new stepb_cartesian_point[ptOnCap.length][ptOnCap[0].length][tCount];
        for (int j = 0; j <  jCount; j++)
        {
            for(int i = 0 ; i < iCount ; i++)
            {
                for (int t = 0; t < tCount; t++)
                {
                    double x = ptOnCap[j][i].X();
                    double y = ptOnCap[j][i].Y();
                    double z = (ptOnCap[j][i].Z() - horizontalStart) / (tCount - 1) * t + horizontalStart;
                    stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                    ptAreaCap[j][i][t] = cPt;
                }
            }
        }

        return ptAreaCap;
    }

    private stepb_cartesian_point[][][] calculateShellUnderPt_Tran(DataShell dataShell, double horizontalStart, boolean isInner)
    {
        stepb_cartesian_point[][] ptOnTransition = dataShell.getShellPts(-1, 1);
        int jCount = ptOnTransition.length;
        int iCount = ptOnTransition[0].length;
        int tCount = 3;
        int iStart = 0;
        if (isInner)
            iCount = 2;
        else
            iStart = 2;

        stepb_cartesian_point[][][] ptAreaTran = new stepb_cartesian_point[ptOnTransition.length][2][tCount];
        for (int j = 0; j < jCount; j++)
        {
            for (int i = iStart; i < iCount; i++)
            {
                for (int t = 0; t < tCount; t++)
                {
                    double x = ptOnTransition[j][i].X();
                    double y = ptOnTransition[j][i].Y();
                    double z = (ptOnTransition[j][i].Z() - horizontalStart) / (tCount - 1) * t + horizontalStart;
                    stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                    ptAreaTran[j][i - iStart][t] = cPt;
                }
            }
        }

        return ptAreaTran;
    }

    private stepb_cartesian_point[][] calculatePt3(int index, ArrayList array1, ArrayList array2, ArrayList array3, double coilUpHorizontalValue, double coilDownHorizontalValue, int jNumber)
    {
        ArrayList cartesianPtArray1 = new ArrayList();
        for (int n = 0; n < array1.size(); n++)
        {
            stepb_cartesian_point[][][] tempArea = (stepb_cartesian_point[][][]) array1.get(n);
            int jCount = tempArea.length;
            int iCount = tempArea[0].length;
            int tCount = tempArea[0][0].length;

            if (n == 0)
                for (int i = 0; i < iCount; i++)
                {
                    cartesianPtArray1.add(tempArea[jNumber][i][tCount - 1]);
                }
            else
                for (int i = 1; i < iCount; i++)
                {
                    cartesianPtArray1.add(tempArea[jNumber][i][tCount - 1]);
                }
        }

        ArrayList cartesianPtArray2 = new ArrayList();
        for (int n = 0; n < array2.size(); n++)
        {
            stepb_cartesian_point[][][] tempArea = (stepb_cartesian_point[][][]) array2.get(n);
            int jCount = tempArea.length;
            int iCount = tempArea[0].length;
            int tCount = tempArea[0][0].length;

            if (n == 0)
                for (int i = 0; i < iCount; i++)
                {
                    cartesianPtArray2.add(tempArea[jNumber][i][tCount - 1]);
                }
            else
                for (int i = 1; i < iCount; i++)
                {
                    cartesianPtArray2.add(tempArea[jNumber][i][tCount - 1]);
                }

        }

        ArrayList cartesianPtArray3 = new ArrayList();
        for (int n = 0; n < array3.size(); n++)
        {
            stepb_cartesian_point[][][] tempArea = (stepb_cartesian_point[][][]) array3.get(n);
            int jCount = tempArea.length;
            int iCount = tempArea[0].length;
            int tCount = tempArea[0][0].length;

            if (n == 0)
                for (int i = 0; i < iCount; i++)
                {
                    cartesianPtArray3.add(tempArea[jNumber][i][0]);
                }
            else
                for (int i = 1; i < iCount; i++)
                {
                    cartesianPtArray3.add(tempArea[jNumber][i][0]);
                }
        }

        int area_23_I_Count = cartesianPtArray1.size();
        int middleCount = cartesianPtArray3.size() - cartesianPtArray2.size() - cartesianPtArray1.size();
        if(middleCount < 2)
            middleCount = 2;

        int arae_2425_I_Count = cartesianPtArray3.size() - middleCount - cartesianPtArray1.size();
        //int arae_2425_I_Count = cartesianPtArray2.size();

        //int total_I_Count = cartesianPtArray3.size();

        stepb_cartesian_point[][] ptArea1 = new stepb_cartesian_point[area_23_I_Count][2];
        stepb_cartesian_point[][] ptArea2 = new stepb_cartesian_point[middleCount][2];
        stepb_cartesian_point[][] ptArea3 = new stepb_cartesian_point[arae_2425_I_Count][2];

        for (int i = 0; i < area_23_I_Count; i++)
        {
            ptArea1[i][0] = (stepb_cartesian_point) cartesianPtArray1.get(i);
            ptArea1[i][1] = (stepb_cartesian_point) cartesianPtArray3.get(i);
        }

        double periodValue = Math.abs(coilUpHorizontalValue - coilDownHorizontalValue) / (middleCount + 1);
        double x = ((stepb_cartesian_point)cartesianPtArray1.get(0)).X();
        double y = ((stepb_cartesian_point)cartesianPtArray1.get(0)).Y();

        for (int i = 0; i < middleCount; i++)
        {
            double z = coilUpHorizontalValue - periodValue * (i + 1);
            stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);

            ptArea2[i][0] = cPt;
            ptArea2[i][1] = (stepb_cartesian_point) cartesianPtArray3.get(area_23_I_Count + i);
        }

        for (int i = 0; i < arae_2425_I_Count; i++)
        {
            ptArea3[i][0] = (stepb_cartesian_point) cartesianPtArray2.get(i);
            ptArea3[i][1] = (stepb_cartesian_point) cartesianPtArray3.get(area_23_I_Count + middleCount + i);
        }

        if(index == 1)
            return ptArea1;
        else if(index == 2)
            return ptArea2;
        else
            return ptArea3;

    }

    //
    /*
    private stepb_cartesian_point[][] calculatePt2(ArrayList array1,ArrayList array2, ArrayList array3, double coilUpHorizontalValue, double coilDownHorizontalValue,int jNumber)
    {
        ArrayList cartesianPtArray1 = new ArrayList();
        for(int n = 0 ; n < array1.size() ; n++)
        {
            stepb_cartesian_point[][][] tempArea = (stepb_cartesian_point[][][]) array1.get(n);
            int jCount = tempArea.length;
            int iCount = tempArea[0].length;
            int tCount = tempArea[0][0].length;

            if(n == 0)
                for (int i = 0; i < iCount; i++)
                {
                    cartesianPtArray1.add(tempArea[jNumber][i][tCount - 1]);
                }
            else
                for (int i = 1; i < iCount; i++)
                {
                    cartesianPtArray1.add(tempArea[jNumber][i][tCount - 1]);
                }
        }


        ArrayList cartesianPtArray2 = new ArrayList();
        for (int n = 0; n < array2.size(); n++)
        {
            stepb_cartesian_point[][][] tempArea = (stepb_cartesian_point[][][]) array2.get(n);
            int jCount = tempArea.length;
            int iCount = tempArea[0].length;
            int tCount = tempArea[0][0].length;

            if(n == 0)
                for (int i = 0; i < iCount; i++)
                {
                    cartesianPtArray2.add(tempArea[jNumber][i][tCount - 1]);
                }
            else
                for (int i = 1; i < iCount; i++)
                {
                    cartesianPtArray2.add(tempArea[jNumber][i][tCount - 1]);
                }

        }


        ArrayList cartesianPtArray3 = new ArrayList();
        for(int n = 0 ; n < array3.size() ; n++)
        {
            stepb_cartesian_point[][][] tempArea = (stepb_cartesian_point[][][])array3.get(n);
            int jCount = tempArea.length;
            int iCount = tempArea[0].length;
            int tCount = tempArea[0][0].length;

            if(n == 0)
                for(int i = 0 ; i < iCount ; i++)
                {
                    cartesianPtArray3.add(tempArea[jNumber][i][0]);
                }
            else
                for(int i = 1 ; i < iCount ; i++)
                {
                    cartesianPtArray3.add(tempArea[jNumber][i][0]);
                }
        }

        int total_I_Count = cartesianPtArray3.size();

        stepb_cartesian_point[][] ptArea = new stepb_cartesian_point[total_I_Count][2];
        for (int t = 0; t <= 1; t++)
        {
            if(t == 0)
            {
                int area_23_I_Count = cartesianPtArray1.size();
                int arae_2425_I_Count = cartesianPtArray2.size();

                int middleCount = total_I_Count - area_23_I_Count - arae_2425_I_Count;



                double x = ((stepb_cartesian_point)cartesianPtArray1.get(0)).X();
                double y = ((stepb_cartesian_point)cartesianPtArray1.get(0)).Y();

                for(int i = 0 ; i < area_23_I_Count ; i++)
                {
                    ptArea[i][0] = (stepb_cartesian_point)cartesianPtArray1.get(i);
                }


                double periodValue = Math.abs(coilUpHorizontalValue - coilDownHorizontalValue) / (middleCount + 1);

                for(int i = 0 ; i < middleCount ; i++)
                {
                    double z = coilUpHorizontalValue - periodValue * (i + 1);
                    stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);

                    ptArea[area_23_I_Count + i][0] = cPt;
                }

                for(int i = 0 ; i < arae_2425_I_Count ; i++)
                {
                    ptArea[area_23_I_Count + middleCount + i][0] = (stepb_cartesian_point)cartesianPtArray2.get(i);
                }
            }

            if(t == 1)
            {
                for(int i = 0 ; i < total_I_Count ; i++)
                {
                    ptArea[i][1] = (stepb_cartesian_point)cartesianPtArray3.get(i);
                }
            }
        }

        return ptArea;
    }*/


    protected void createDataPointCircumferentialRunway()
    {

        int jCount = this._dataSurround._countGirth;
        double totalVolume = 0;
        double thetaIntervalJ = 2 * Math.PI / jCount;

        //Cap
        ElementPoint capStartPt_XZ = this._dataCap.getElementPointStart(this.XZView);
        ElementPoint capEndPt_XZ = this._dataCap.getElementPointEnd(this.XZView);

        //MagnetTop
        ElementPoint magnetTopStartPt_XZ = this._dataMagnetTop.getElementPointStart(this.XZView);
        ElementPoint magnetTopEndPt_XZ = this._dataMagnetTop.getElementPointEnd(this.XZView);
        ElementPoint magnetTopStartPt_YZ = this._dataMagnetTop.getElementPointStart(this.YZView);
        ElementPoint magnetTopEndPt_YZ = this._dataMagnetTop.getElementPointEnd(this.YZView);

        //TopPlate
        ElementPoint topPlateStartPt_XZ = this._dataTopPlate.getElementPointStart(this.XZView);
        ElementPoint topPlateEndPt_XZ = this._dataTopPlate.getElementPointEnd(this.XZView);
        ElementPoint topPlateStartPt_YZ = this._dataTopPlate.getElementPointStart(this.YZView);
        ElementPoint topPlateEndPt_YZ = this._dataTopPlate.getElementPointEnd(this.YZView);

        //Magnet
        ElementPoint magnetStartPt_XZ = this._dataMagnet.getElementPointStart(this.XZView);
        ElementPoint magnetEndPt_XZ = this._dataMagnet.getElementPointEnd(this.XZView);
        ElementPoint magnetStartPt_YZ = this._dataMagnet.getElementPointStart(this.YZView);
        ElementPoint magnetEndPt_YZ = this._dataMagnet.getElementPointEnd(this.YZView);

        //Coil
        ElementPoint coil_StartPt_XZ = this._dataCoil.getElementPointStart(this.XZView);
        ElementPoint coil_OuterStartPt_XZ = this._dataCoil.getElementPointOuterStart(this.XZView);
        ElementPoint coil_OuterEndPt_XZ = this._dataCoil.getElementPointOuterEnd(this.XZView);
        ElementPoint coil_StartPt_YZ = this._dataCoil.getElementPointStart(this.YZView);
        ElementPoint coil_OuterEndPt_YZ = this._dataCoil.getElementPointOuterEnd(this.YZView);
        ElementPoint coil_OuterStartPt_YZ = this._dataCoil.getElementPointOuterStart(this.YZView);

        //Coil Outer
        ElementPoint coil_CoilStartPt_XZ = this._dataCoil.getElementPointCoilStart(this.XZView); //new
        ElementPoint coil_CoilOuterEndPt_XZ = this._dataCoil.getElementPointCoilOuterEnd(this.XZView); //new
        ElementPoint coil_CoilStartPt_YZ = this._dataCoil.getElementPointCoilStart(this.YZView); //new
        ElementPoint coil_CoilOuterEndPt_YZ = this._dataCoil.getElementPointCoilOuterEnd(this.YZView); //new



        //YokeBase
        ElementPoint yokeBaseStartPt_XZ = this._dataYokeBase.getElementPointStart(this.XZView);
        ElementPoint yokeBaseMidUpPt_XZ = this._dataYokeBase.getElementPointMiddleUp(this.XZView);
        ElementPoint yokeBaseEndPt_XZ = this._dataYokeBase.getElementPointEnd(this.XZView);
        ElementPoint yokeBaseStartPt_YZ = this._dataYokeBase.getElementPointStart(this.YZView);
        ElementPoint yokeBaseMidUpPt_YZ = this._dataYokeBase.getElementPointMiddleUp(this.YZView);
        ElementPoint yokeBaseEndPt_YZ = this._dataYokeBase.getElementPointEnd(this.YZView);

        //YokeStage1
        ElementPoint yokeStage1StartPt_XZ = this._dataYokeStage1.getElementPointStart(this.XZView);
        ElementPoint yokeStage1MidUpPt_XZ = this._dataYokeStage1.getElementPointMiddleUp(this.XZView);
        ElementPoint yokeStage1EndPt_XZ = this._dataYokeStage1.getElementPointEnd(this.XZView);
        ElementPoint yokeStage1StartPt_YZ = this._dataYokeStage1.getElementPointStart(this.YZView);
        ElementPoint yokeStage1MidUpPt_YZ = this._dataYokeStage1.getElementPointMiddleUp(this.YZView);
        ElementPoint yokeStage1EndPt_YZ = this._dataYokeStage1.getElementPointEnd(this.YZView);

        //YokeStage2
        ElementPoint yokeStage2StartPt_XZ = this._dataYokeStage2.getElementPointStart(this.XZView);
        ElementPoint yokeStage2MidUpPt_XZ = this._dataYokeStage2.getElementPointMiddleUp(this.XZView);
        ElementPoint yokeStage2EndPt_XZ = this._dataYokeStage2.getElementPointEnd(this.XZView);
        ElementPoint yokeStage2StartPt_YZ = this._dataYokeStage2.getElementPointStart(this.YZView);
        ElementPoint yokeStage2MidUpPt_YZ = this._dataYokeStage2.getElementPointMiddleUp(this.YZView);
        ElementPoint yokeStage2EndPt_YZ = this._dataYokeStage2.getElementPointEnd(this.YZView);

        //MagnetOuter
        ElementPoint magnetOuterStartPt_XZ = this._dataMagnetOuter.getElementPointStart(this.XZView);
        ElementPoint magnetOuterEndPt_XZ = this._dataMagnetOuter.getElementPointEnd(this.XZView);
        ElementPoint magnetOuterStartPt_YZ = this._dataMagnetOuter.getElementPointStart(this.YZView);
        ElementPoint magnetOuterEndPt_YZ = this._dataMagnetOuter.getElementPointEnd(this.YZView);

        double v0_XZ = 0;
        double v1_XZ;
        if (this._dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
            v1_XZ = magnetTopEndPt_XZ.X();
        else
            v1_XZ = topPlateEndPt_XZ.X();

        double v2_XZ = topPlateEndPt_XZ.X();
        double v3_XZ = magnetEndPt_XZ.X();
        double v4_XZ = coil_StartPt_XZ.X();
        double v5_XZ;
        if(this._dataCoil.getGeometryType() == this.COIL_TYPE1)//new
            v5_XZ = coil_OuterEndPt_XZ.X();//new
        else
            v5_XZ = (yokeBaseMidUpPt_XZ.X() +  coil_CoilOuterEndPt_XZ.X()) / 2;//new

        double v5_1_XZ = coil_OuterEndPt_XZ.X();//new
        double v5_2_XZ = coil_CoilOuterEndPt_XZ.X();//new

        double v6_XZ = yokeBaseMidUpPt_XZ.X();
        double v7_XZ;
        if(this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE)
            v7_XZ = magnetOuterStartPt_XZ.X();
        else
            v7_XZ = yokeStage1MidUpPt_XZ.X();

        double v8_XZ = yokeStage1MidUpPt_XZ.X();
        double v9_XZ = yokeStage2MidUpPt_XZ.X();



        double v0_YZ = 0;
        double v1_YZ;
        if (this._dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
            v1_YZ = magnetTopEndPt_YZ.X();
        else
            v1_YZ = topPlateEndPt_YZ.X();

        double v2_YZ = topPlateEndPt_YZ.X();
        double v3_YZ = magnetEndPt_YZ.X();
        double v4_YZ = coil_StartPt_YZ.X();
        double v5_YZ;
        if(this._dataCoil.getGeometryType() == this.COIL_TYPE1)//new
            v5_YZ = coil_OuterEndPt_YZ.X();//new
        else
            v5_YZ = (yokeBaseMidUpPt_YZ.X() +  coil_CoilOuterEndPt_YZ.X()) / 2;//new

        double v5_1_YZ = coil_OuterEndPt_YZ.X();//new
        double v5_2_YZ = coil_CoilOuterEndPt_YZ.X();//new
        double v6_YZ = yokeBaseMidUpPt_YZ.X();
        double v7_YZ;
        if(this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE)
            v7_YZ = magnetOuterStartPt_YZ.X();
        else
            v7_YZ = yokeStage1MidUpPt_YZ.X();

        double v8_YZ = yokeStage1MidUpPt_YZ.X();
        double v9_YZ = yokeStage2MidUpPt_YZ.X();

        double unitWidth = v5_1_XZ - v4_XZ;
        double factor = 2;


        double h00 = capEndPt_XZ.Y();
        double h0 = capEndPt_XZ.Y() - 2 * unitWidth;

        double h1;
        if (this._dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
            h1 = magnetTopStartPt_XZ.Y();
        else
            h1 = topPlateStartPt_XZ.Y();

        double h2 = topPlateStartPt_XZ.Y();
        double h3 = magnetStartPt_XZ.Y();
        double h4 = yokeBaseStartPt_XZ.Y();
        double h5 = coil_OuterEndPt_XZ.Y();
        double h5_1 = coil_CoilStartPt_XZ.Y();
        double h5_2 = coil_CoilOuterEndPt_XZ.Y();
        double h6;
        if (this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
            h6 = yokeStage1StartPt_XZ.Y();
        else
        {
            if (coil_OuterStartPt_XZ.Y() >= yokeBaseMidUpPt_XZ.Y())
                h6 = yokeBaseMidUpPt_XZ.Y() - unitWidth;
            else
                h6 = coil_OuterStartPt_XZ.Y() - unitWidth;
        }

        double h7;
        if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE)
            h7 = magnetOuterStartPt_XZ.Y();
        else
            h7 = yokeStage1StartPt_XZ.Y();

        double h8;
        if (this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
            h8 = yokeStage2StartPt_XZ.Y();
        else
        {
            if (coil_OuterStartPt_XZ.Y() >= yokeStage1MidUpPt_XZ.Y())
                h8 = yokeStage1MidUpPt_XZ.Y() - unitWidth;
            else
                h8 = coil_OuterStartPt_XZ.Y() - unitWidth;
        }

        double h9;
        if(coil_OuterStartPt_XZ.Y() >= yokeStage2MidUpPt_XZ.Y())
            h9 = yokeStage2MidUpPt_XZ.Y() - unitWidth;
        else
            h9 = coil_OuterStartPt_XZ.Y() - unitWidth;

        double h10;
        if(this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
            h10 = h9;
        else
            if(this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
                h10 = h8;
            else
                h10 = h6;



        int divide_L1_number = (int) Math.round(Math.abs(h0 - h1) / (factor * unitWidth));
        if (divide_L1_number == 0)
            divide_L1_number = 1;
        double divide_L1_value = Math.abs(h0 - h1) / (double) divide_L1_number;

        int divide_L2_number = (int) Math.round(Math.abs(h1 - h2) / (factor * unitWidth));
        if (divide_L2_number == 0)
            divide_L2_number = 1;
        double divide_L2_value = Math.abs(h1 - h2) / (double) divide_L2_number;

        int divide_L3_number = (int) Math.round(Math.abs(h2 - h3) / unitWidth);
        if (divide_L3_number == 0)
            divide_L3_number = 1;
        double divide_L3_value = Math.abs(h2 - h3) / (double) divide_L3_number;

        int divide_L4_number = (int) Math.round(Math.abs(h3 - h5) / unitWidth);
        if (divide_L4_number == 0)
            divide_L4_number = 1;
        double divide_L4_value = Math.abs(h3 - h5) / (double) divide_L4_number;

        int divide_L5_number = (int) Math.round(Math.abs(h5 - h4) / unitWidth);
        if (divide_L5_number == 0)
            divide_L5_number = 1;
        double divide_L5_value = Math.abs(h5 - h4) / (double) divide_L5_number;

        int divide_L6_number = (int) Math.round(Math.abs(h6 - h5) / unitWidth);
        if (divide_L6_number == 0)
            divide_L6_number = 1;
        double divide_L6_value = Math.abs(h6 - h5) / (double) divide_L6_number;

        int divide_L7_number = (int) Math.round(Math.abs(h7 - h6) / (factor * unitWidth));
        if (divide_L7_number == 0)
            divide_L7_number = 1;
        double divide_L7_value = Math.abs(h7 - h6) / (double) divide_L7_number;

        int divide_L8_number = (int) Math.round(Math.abs(h8 - h7) / (factor * unitWidth));
        if (divide_L8_number == 0)
            divide_L8_number = 1;
        double divide_L8_value = Math.abs(h8 - h7) / (double) divide_L8_number;

        int divide_L9_number = (int) Math.round(Math.abs(h9 - h8) / (factor * unitWidth));
        if (divide_L9_number == 0)
            divide_L9_number = 1;
        double divide_L9_value = Math.abs(h9 - h8) / (double) divide_L9_number;

        int divide_L10_number = (int) Math.round(Math.abs(h10 - h5_1) /  (factor * unitWidth));
        if (divide_L10_number == 0)
            divide_L10_number = 1;
        double divide_L10_value = Math.abs(h10 - h5_1) / (double) divide_L10_number;

        int divide_L11_number = (int) Math.round(Math.abs(h5_2 - h5) / unitWidth);
        if (divide_L11_number == 0)
            divide_L11_number = 1;
        double divide_L11_value = Math.abs(h5_2 - h5) / (double) divide_L11_number;


        int divide_W1_number = (int) Math.round(Math.abs(v1_XZ - v0_XZ) / (factor * unitWidth));
        if (divide_W1_number == 0)
            divide_W1_number = 1;


        int divide_W2_number = (int) Math.round(Math.abs(v2_XZ - v1_XZ) / (factor * unitWidth));
        if (divide_W2_number == 0)
            divide_W2_number = 1;


        int divide_W3_number = (int) Math.round(Math.abs(v4_XZ - v2_XZ) / unitWidth);
        if (divide_W3_number == 0)
            divide_W3_number = 1;


        int divide_W4_number = (int) Math.round(Math.abs(v2_XZ - v3_XZ) / unitWidth);
        if (divide_W4_number == 0)
            divide_W4_number = 1;


        int divide_W5_number = (int) Math.round(Math.abs(v5_1_XZ - v4_XZ) / unitWidth);
        if (divide_W5_number == 0)
            divide_W5_number = 1;


        int divide_W6_number = (int) Math.round(Math.abs(v6_XZ - v5_XZ) / unitWidth);
        if (divide_W6_number == 0)
            divide_W6_number = 1;

        int divide_W6_1_number = 1;//new

        int divide_W7_number = (int) Math.round(Math.abs(v7_XZ - v6_XZ) / (factor * unitWidth));
        if (divide_W7_number == 0)
            divide_W7_number = 1;


        int divide_W8_number = (int) Math.round(Math.abs(v8_XZ - v7_XZ) / (factor * unitWidth));
        if (divide_W8_number == 0)
            divide_W8_number = 1;


        int divide_W9_number = (int) Math.round(Math.abs(v9_XZ - v8_XZ) / (factor * unitWidth));
        if (divide_W9_number == 0)
            divide_W9_number = 1;

        double runwayH_1 = v1_YZ;
        double runwayW_1 = v1_XZ - v1_YZ;
        double runwayH_2 = v2_YZ;
        double runwayW_2 = v2_XZ - v2_YZ;
        double runwayH_3 = v3_YZ;
        double runwayW_3 = v3_XZ - v3_YZ;
        double runwayH_4 = v4_YZ;
        double runwayW_4 = v4_XZ - v4_YZ;
        double runwayH_5 = v5_YZ;
        double runwayW_5 = v5_XZ - v5_YZ;
        double runwayH_5_1 = v5_1_YZ;
        double runwayW_5_1 = v5_1_XZ - v5_1_YZ;
        double runwayH_5_2 = v5_2_YZ;
        double runwayW_5_2 = v5_2_XZ - v5_2_YZ;
        double runwayH_6 = v6_YZ;
        double runwayW_6 = v6_XZ - v6_YZ;
        double runwayH_7 = v7_YZ;
        double runwayW_7 = v7_XZ - v7_YZ;
        double runwayH_8 = v8_YZ;
        double runwayW_8 = v8_XZ - v8_YZ;
        double runwayH_9 = v9_YZ;
        double runwayW_9 = v9_XZ - v9_YZ;


        stepb_cartesian_point[][][] ptArea_1 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_2 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_3 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_4 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_5 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_6 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_7 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_8 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_9 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_10 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_11 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_12 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_13 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_14 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_15 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_16 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_17 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_18 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_19 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_20 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_21 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_22 = new stepb_cartesian_point[jCount][][];

        stepb_cartesian_point[][][] ptArea_23 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_24 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_25 = new stepb_cartesian_point[jCount][][];
        stepb_cartesian_point[][][] ptArea_26 = new stepb_cartesian_point[jCount][][];

        stepb_cartesian_point[][][] ptArea_Cap;
        stepb_cartesian_point[][][] ptArea_Tran_Inner;
        stepb_cartesian_point[][][] ptArea_Tran_Outer;
        stepb_cartesian_point[][][] ptArea_Diaphragm;
        stepb_cartesian_point[][][] ptArea_Surround;

        ptArea_Cap        = this.calculateShellUnderPt(_dataCap, h0);
        ptArea_Tran_Inner = this.calculateShellUnderPt_Tran(_dataTransition, h0, true);
        ptArea_Tran_Outer = this.calculateShellUnderPt_Tran(_dataTransition, h10, false);
        ptArea_Diaphragm  = this.calculateShellUnderPt(_dataDiaphragm, h10);
        ptArea_Surround   = this.calculateShellUnderPt(_dataSurround, h10);

        for (int j = 0; j < jCount; j++)
        {
            double theta = j * thetaIntervalJ;

            //跑道型各個截面的v
            double v0   = 0;
            double v1   = this.calculateRunwayNewVerticalValue(theta, runwayH_1, runwayW_1);
            double v2   = this.calculateRunwayNewVerticalValue(theta, runwayH_2, runwayW_2);
            double v3   = this.calculateRunwayNewVerticalValue(theta, runwayH_3, runwayW_3);
            double v4   = this.calculateRunwayNewVerticalValue(theta, runwayH_4, runwayW_4);
            double v5   = this.calculateRunwayNewVerticalValue(theta, runwayH_5, runwayW_5);
            double v5_1 = this.calculateRunwayNewVerticalValue(theta, runwayH_5_1, runwayW_5_1);
            double v5_2 = this.calculateRunwayNewVerticalValue(theta, runwayH_5_2, runwayW_5_1);
            double v6   = this.calculateRunwayNewVerticalValue(theta, runwayH_6, runwayW_6);
            double v7   = this.calculateRunwayNewVerticalValue(theta, runwayH_7, runwayW_7);
            double v8   = this.calculateRunwayNewVerticalValue(theta, runwayH_8, runwayW_8);
            double v9   = this.calculateRunwayNewVerticalValue(theta, runwayH_9, runwayW_9);

            double divide_W1_value   = Math.abs(v1 - v0) / (double) divide_W1_number;
            double divide_W2_value   = Math.abs(v2 - v1) / (double) divide_W2_number;
            double divide_W3_value   = Math.abs(v4 - v2) / (double) divide_W3_number;
            double divide_W4_value   = Math.abs(v2 - v3) / (double) divide_W4_number;
            double divide_W5_value   = Math.abs(v5_1 - v4) / (double) divide_W5_number;
            double divide_W6_value   = Math.abs(v6 - v5) / (double) divide_W6_number;
            double divide_W6_1_value = Math.abs(v5_2 - v5_1); //new
            double divide_W7_value   = Math.abs(v7 - v6) / (double) divide_W7_number;
            double divide_W8_value   = Math.abs(v8 - v7) / (double) divide_W8_number;
            double divide_W9_value   = Math.abs(v9 - v8) / (double) divide_W9_number;



            //area 1  L1 & W1
            ptArea_1[j] = this.calculatePt(theta, divide_W1_number, divide_W1_value, divide_L1_number, divide_L1_value, 0, h0);
            //area 2  L1 & W2
            ptArea_2[j] = this.calculatePt(theta, divide_W2_number, divide_W2_value, divide_L1_number, divide_L1_value, v1, h0);
            //area 3  L1 & W3
            ptArea_3[j] = this.calculatePt(theta, divide_W3_number, divide_W3_value, divide_L1_number, divide_L1_value, v2, h0);
            //area 4  L2 & W2
            ptArea_4[j] = this.calculatePt(theta, divide_W2_number, divide_W2_value, divide_L2_number, divide_L2_value, v1, h1);
            //area 5  L2 & W3
            ptArea_5[j] = this.calculatePt(theta, divide_W3_number, divide_W3_value, divide_L2_number, divide_L2_value, v2, h1);
            //area 6  L3 & W3
            ptArea_6[j] = this.calculatePt(theta, divide_W3_number, divide_W3_value, divide_L3_number, divide_L3_value, v2, h2);
            //area 7  L4 & W4
            ptArea_7[j] = this.calculatePt(theta, divide_W4_number, divide_W4_value, divide_L4_number, divide_L4_value, v3, h3);
            //area 8  L4 & W3
            ptArea_8[j] = this.calculatePt(theta, divide_W3_number, divide_W3_value, divide_L4_number, divide_L4_value, v2, h3);
            //area 9  L5 & W4
            ptArea_9[j] = this.calculatePt(theta, divide_W4_number, divide_W4_value, divide_L5_number, divide_L5_value, v3, h5);
            //area 10  L5 & W3
            ptArea_10[j] = this.calculatePt(theta, divide_W3_number, divide_W3_value, divide_L5_number, divide_L5_value, v2, h5);
            //area 11  L5 & W5
            ptArea_11[j] = this.calculatePt(theta, divide_W5_number, divide_W5_value, divide_L5_number, divide_L5_value, v4, h5);
            //area 12  L5 & W6
            ptArea_12[j] = this.calculatePt(theta, divide_W6_number, divide_W6_value, divide_L5_number, divide_L5_value, v5, h5);
            //area 13  L6 & W6
            ptArea_13[j] = this.calculatePt(theta, divide_W6_number, divide_W6_value, divide_L6_number, divide_L6_value, v5, h6);
            //area 14  L7 & W6
            ptArea_14[j] = this.calculatePt(theta, divide_W6_number, divide_W6_value, divide_L7_number, divide_L7_value, v5, h7);
            //area 15  L8 & W6
            ptArea_15[j] = this.calculatePt(theta, divide_W6_number, divide_W6_value, divide_L8_number, divide_L8_value, v5, h8);
            //area 16  L9 & W6
            ptArea_16[j] = this.calculatePt(theta, divide_W6_number, divide_W6_value, divide_L9_number, divide_L9_value, v5, h9);
            //area 17  L7 & W7
            ptArea_17[j] = this.calculatePt(theta, divide_W7_number, divide_W7_value, divide_L7_number, divide_L7_value, v6, h7);
            //area 18  L8 & W7
            ptArea_18[j] = this.calculatePt(theta, divide_W7_number, divide_W7_value, divide_L8_number, divide_L8_value, v6, h8);
            //area 19  L9 & W7
            ptArea_19[j] = this.calculatePt(theta, divide_W7_number, divide_W7_value, divide_L9_number, divide_L9_value, v6, h9);
            //area 20  L8 & W8
            ptArea_20[j] = this.calculatePt(theta, divide_W8_number, divide_W8_value, divide_L8_number, divide_L8_value, v7, h8);
            //area 21  L9 & W8
            ptArea_21[j] = this.calculatePt(theta, divide_W8_number, divide_W8_value, divide_L9_number, divide_L9_value, v7, h9);
            //area 22  L9 & W9
            ptArea_22[j] = this.calculatePt(theta, divide_W9_number, divide_W9_value, divide_L9_number, divide_L9_value, v8, h9);

            //area 23  L10 & W6_1
            ptArea_23[j] = this.calculatePt(theta, divide_W6_1_number, divide_W6_1_value, divide_L10_number, divide_L10_value, v5_1, h10);
            //area 24  L11 & W6_1
            ptArea_24[j] = this.calculatePt(theta, divide_W6_1_number, divide_W6_1_value, divide_L11_number, divide_L11_value, v5_1, h5_2);
            //area 25  L5 & W6_1
            ptArea_25[j] = this.calculatePt(theta, divide_W6_1_number, divide_W6_1_value, divide_L5_number, divide_L5_value, v5_1, h5);
        }

        CartesianPointSetsBrick ptSetsBrick1  = new CartesianPointSetsBrick(ptArea_1);
        CartesianPointSetsBrick ptSetsBrick2  = new CartesianPointSetsBrick(ptArea_2);
        CartesianPointSetsBrick ptSetsBrick3  = new CartesianPointSetsBrick(ptArea_3);
        CartesianPointSetsBrick ptSetsBrick4  = new CartesianPointSetsBrick(ptArea_4);
        CartesianPointSetsBrick ptSetsBrick5  = new CartesianPointSetsBrick(ptArea_5);
        CartesianPointSetsBrick ptSetsBrick6  = new CartesianPointSetsBrick(ptArea_6);
        CartesianPointSetsBrick ptSetsBrick7  = new CartesianPointSetsBrick(ptArea_7);
        CartesianPointSetsBrick ptSetsBrick8  = new CartesianPointSetsBrick(ptArea_8);
        CartesianPointSetsBrick ptSetsBrick9  = new CartesianPointSetsBrick(ptArea_9);
        CartesianPointSetsBrick ptSetsBrick10 = new CartesianPointSetsBrick(ptArea_10);
        CartesianPointSetsBrick ptSetsBrick11 = new CartesianPointSetsBrick(ptArea_11);
        CartesianPointSetsBrick ptSetsBrick12 = new CartesianPointSetsBrick(ptArea_12);
        CartesianPointSetsBrick ptSetsBrick13 = new CartesianPointSetsBrick(ptArea_13);
        CartesianPointSetsBrick ptSetsBrick14 = new CartesianPointSetsBrick(ptArea_14);
        CartesianPointSetsBrick ptSetsBrick15 = new CartesianPointSetsBrick(ptArea_15);
        CartesianPointSetsBrick ptSetsBrick16 = new CartesianPointSetsBrick(ptArea_16);
        CartesianPointSetsBrick ptSetsBrick17 = new CartesianPointSetsBrick(ptArea_17);
        CartesianPointSetsBrick ptSetsBrick18 = new CartesianPointSetsBrick(ptArea_18);
        CartesianPointSetsBrick ptSetsBrick19 = new CartesianPointSetsBrick(ptArea_19);
        CartesianPointSetsBrick ptSetsBrick20 = new CartesianPointSetsBrick(ptArea_20);
        CartesianPointSetsBrick ptSetsBrick21 = new CartesianPointSetsBrick(ptArea_21);
        CartesianPointSetsBrick ptSetsBrick22 = new CartesianPointSetsBrick(ptArea_22);
        CartesianPointSetsBrick ptSetsBrick23 = new CartesianPointSetsBrick(ptArea_23);
        CartesianPointSetsBrick ptSetsBrick24 = new CartesianPointSetsBrick(ptArea_24);
        CartesianPointSetsBrick ptSetsBrick25 = new CartesianPointSetsBrick(ptArea_25);

        ptSetsBrick1.setSectionType(SECTION_AIR_ZONE01);
        ptSetsBrick2.setSectionType(SECTION_AIR_ZONE02);
        ptSetsBrick3.setSectionType(SECTION_AIR_ZONE03);
        ptSetsBrick4.setSectionType(SECTION_AIR_ZONE04);
        ptSetsBrick5.setSectionType(SECTION_AIR_ZONE05);
        ptSetsBrick6.setSectionType(SECTION_AIR_ZONE06);
        ptSetsBrick7.setSectionType(SECTION_AIR_ZONE07);
        ptSetsBrick8.setSectionType(SECTION_AIR_ZONE08);
        ptSetsBrick9.setSectionType(SECTION_AIR_ZONE09);
        ptSetsBrick10.setSectionType(SECTION_AIR_ZONE10);
        ptSetsBrick11.setSectionType(SECTION_AIR_ZONE11);
        ptSetsBrick12.setSectionType(SECTION_AIR_ZONE12);
        ptSetsBrick13.setSectionType(SECTION_AIR_ZONE13);
        ptSetsBrick14.setSectionType(SECTION_AIR_ZONE14);
        ptSetsBrick15.setSectionType(SECTION_AIR_ZONE15);
        ptSetsBrick16.setSectionType(SECTION_AIR_ZONE16);
        ptSetsBrick17.setSectionType(SECTION_AIR_ZONE17);
        ptSetsBrick18.setSectionType(SECTION_AIR_ZONE18);
        ptSetsBrick19.setSectionType(SECTION_AIR_ZONE19);
        ptSetsBrick20.setSectionType(SECTION_AIR_ZONE20);
        ptSetsBrick21.setSectionType(SECTION_AIR_ZONE21);
        ptSetsBrick22.setSectionType(SECTION_AIR_ZONE22);
        ptSetsBrick23.setSectionType(SECTION_AIR_ZONE23);
        ptSetsBrick24.setSectionType(SECTION_AIR_ZONE24);
        ptSetsBrick25.setSectionType(SECTION_AIR_ZONE25);

        CartesianPointSetsBrick ptSetsBrickCap       = new CartesianPointSetsBrick(ptArea_Cap);
        CartesianPointSetsBrick ptSetsBrickTranInner = new CartesianPointSetsBrick(ptArea_Tran_Inner);
        CartesianPointSetsBrick ptSetsBrickTranOuter = new CartesianPointSetsBrick(ptArea_Tran_Outer);
        CartesianPointSetsBrick ptSetsBrickDiaphragm = new CartesianPointSetsBrick(ptArea_Diaphragm);
        CartesianPointSetsBrick ptSetsBrickSurround  = new CartesianPointSetsBrick(ptArea_Surround);

        ptSetsBrickCap.setSectionType(SECTION_AIR_CAP);
        ptSetsBrickTranInner.setSectionType(SECTION_AIR_TRAN_IN);
        ptSetsBrickTranOuter.setSectionType(SECTION_AIR_TRAN_OUT);
        ptSetsBrickDiaphragm.setSectionType(SECTION_AIR_DIAPHRAGM);
        ptSetsBrickSurround.setSectionType(SECTION_AIR_SURROUND);

        //2012.10.17
        ptSetsBrickTranInner.setContactNodeFromOther(ptArea_Cap, this.BRICK_FACE_INNER);
        ptSetsBrickTranOuter.setContactNodeFromOther(ptArea_Diaphragm, this.BRICK_FACE_OUTER);
        ptSetsBrickDiaphragm.setContactNodeFromOther(ptArea_Surround, this.BRICK_FACE_OUTER);
        //2012.10.17

        this._arrayPtSets.add(ptSetsBrickCap);
        this._arrayPtSets.add(ptSetsBrickTranInner);
        this._arrayPtSets.add(ptSetsBrickTranOuter);
        if (this._dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
            this._arrayPtSets.add(ptSetsBrickDiaphragm);
        this._arrayPtSets.add(ptSetsBrickSurround);

        totalVolume += this.calculateRegionVolume(ptArea_Cap, 0);
        totalVolume += this.calculateRegionVolume(ptArea_Tran_Inner, 0);
        totalVolume += this.calculateRegionVolume(ptArea_Tran_Outer, 0);
        if (this._dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
            totalVolume += this.calculateRegionVolume(ptArea_Diaphragm, 0);
        totalVolume += this.calculateRegionVolume(ptArea_Surround, 0);


        //有上磁鐵
        if (this._dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
        {
            ptSetsBrick1.setContactNodeFromOther(ptArea_2, this.BRICK_FACE_INNER);
            ptSetsBrick2.setContactNodeFromOther(ptArea_3, this.BRICK_FACE_INNER);
            ptSetsBrick2.setContactNodeFromOther(ptArea_4, this.BRICK_FACE_UP);
            ptSetsBrick4.setContactNodeFromOther(ptArea_5, this.BRICK_FACE_INNER);
            ptSetsBrick3.setContactNodeFromOther(ptArea_5, this.BRICK_FACE_UP);
            ptSetsBrick5.setContactNodeFromOther(ptArea_6, this.BRICK_FACE_UP);

            this._arrayPtSets.add(ptSetsBrick1);
            this._arrayPtSets.add(ptSetsBrick2);
            this._arrayPtSets.add(ptSetsBrick3);
            this._arrayPtSets.add(ptSetsBrick4);
            this._arrayPtSets.add(ptSetsBrick5);

            totalVolume += this.calculateRegionVolume(ptArea_1, 1);
            totalVolume += this.calculateRegionVolume(ptArea_2, 1);
            totalVolume += this.calculateRegionVolume(ptArea_3, 1);
            totalVolume += this.calculateRegionVolume(ptArea_4, 1);
            totalVolume += this.calculateRegionVolume(ptArea_5, 1);
        }
        else //無上磁鐵
        {
            ptSetsBrick1.setContactNodeFromOther(ptArea_3, this.BRICK_FACE_INNER);
            ptSetsBrick3.setContactNodeFromOther(ptArea_6, this.BRICK_FACE_UP);

            this._arrayPtSets.add(ptSetsBrick1);
            this._arrayPtSets.add(ptSetsBrick3);

            totalVolume += this.calculateRegionVolume(ptArea_1, 1);
            totalVolume += this.calculateRegionVolume(ptArea_3, 1);
        }

        ptSetsBrick6.setContactNodeFromOther(ptArea_8, this.BRICK_FACE_UP);
        ptSetsBrick7.setContactNodeFromOther(ptArea_8, this.BRICK_FACE_INNER);
        ptSetsBrick7.setContactNodeFromOther(ptArea_9, this.BRICK_FACE_UP);
        ptSetsBrick8.setContactNodeFromOther(ptArea_10, this.BRICK_FACE_UP);
        ptSetsBrick9.setContactNodeFromOther(ptArea_10, this.BRICK_FACE_INNER);
        ptSetsBrick10.setContactNodeFromOther(ptArea_11, this.BRICK_FACE_INNER);
        ptSetsBrick11.setContactNodeFromOther(ptArea_12, this.BRICK_FACE_INNER);
        ptSetsBrick12.setContactNodeFromOther(ptArea_13, this.BRICK_FACE_DOWN);

        this._arrayPtSets.add(ptSetsBrick6);
        this._arrayPtSets.add(ptSetsBrick7);
        this._arrayPtSets.add(ptSetsBrick8);
        this._arrayPtSets.add(ptSetsBrick9);
        this._arrayPtSets.add(ptSetsBrick10);
        this._arrayPtSets.add(ptSetsBrick11);
        this._arrayPtSets.add(ptSetsBrick12);
        this._arrayPtSets.add(ptSetsBrick13);

        totalVolume += this.calculateRegionVolume(ptArea_6, 1);
        totalVolume += this.calculateRegionVolume(ptArea_7, 1);
        totalVolume += this.calculateRegionVolume(ptArea_8, 1);
        totalVolume += this.calculateRegionVolume(ptArea_9, 1);
        totalVolume += this.calculateRegionVolume(ptArea_10, 1);
        totalVolume += this.calculateRegionVolume(ptArea_11, 1);
        totalVolume += this.calculateRegionVolume(ptArea_12, 1);
        totalVolume += this.calculateRegionVolume(ptArea_13, 1);


        if (this._dataCoil.getGeometryType() == this.COIL_TYPE1) // coil type 1
        {
            if (this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE) // 有YokeStage1
            {
                if (this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE) // 有YokeStage2
                {
                    if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE) //coil type 1 ; 有YokeStage2  有YokeStage1 有MagnetOuter
                    {
                        //設定相接面共用點


                        ptSetsBrick13.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_DOWN);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_DOWN);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_17, this.BRICK_FACE_INNER);
                        ptSetsBrick17.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                        ptSetsBrick18.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_DOWN);
                        ptSetsBrick16.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_INNER);
                        ptSetsBrick18.setContactNodeFromOther(ptArea_20, this.BRICK_FACE_INNER);
                        ptSetsBrick20.setContactNodeFromOther(ptArea_21, this.BRICK_FACE_DOWN);
                        ptSetsBrick19.setContactNodeFromOther(ptArea_21, this.BRICK_FACE_INNER);
                        ptSetsBrick22.setContactNodeFromOther(ptArea_22, this.BRICK_FACE_INNER);

                        this._arrayPtSets.add(ptSetsBrick14);
                        this._arrayPtSets.add(ptSetsBrick15);
                        this._arrayPtSets.add(ptSetsBrick16);
                        this._arrayPtSets.add(ptSetsBrick17);
                        this._arrayPtSets.add(ptSetsBrick18);
                        this._arrayPtSets.add(ptSetsBrick19);
                        this._arrayPtSets.add(ptSetsBrick20);
                        this._arrayPtSets.add(ptSetsBrick21);
                        this._arrayPtSets.add(ptSetsBrick22);

                        totalVolume += this.calculateRegionVolume(ptArea_14, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_15, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_16, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_17, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_18, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_19, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_20, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_21, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_22, 1);

                    }
                    else //coil type 1 ; 有YokeStage2  有YokeStage1 無MagnetOuter
                    {
                        //設定相接面共用點
                        ptSetsBrick13.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                        ptSetsBrick18.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_DOWN);
                        ptSetsBrick16.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_INNER);
                        ptSetsBrick19.setContactNodeFromOther(ptArea_22, this.BRICK_FACE_INNER);

                        this._arrayPtSets.add(ptSetsBrick15);
                        this._arrayPtSets.add(ptSetsBrick16);
                        this._arrayPtSets.add(ptSetsBrick18);
                        this._arrayPtSets.add(ptSetsBrick19);
                        this._arrayPtSets.add(ptSetsBrick22);

                        totalVolume += this.calculateRegionVolume(ptArea_15, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_16, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_18, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_19, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_22, 1);

                    }
                }
                else // 無YokeStage2
                {
                    if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE) //coil type 1 ; 無YokeStage2  有YokeStage1 有MagnetOuter
                    {
                        //設定相接面共用點
                        ptSetsBrick13.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_DOWN);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_17, this.BRICK_FACE_INNER);
                        ptSetsBrick17.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                        ptSetsBrick18.setContactNodeFromOther(ptArea_20, this.BRICK_FACE_INNER);

                        this._arrayPtSets.add(ptSetsBrick14);
                        this._arrayPtSets.add(ptSetsBrick15);
                        this._arrayPtSets.add(ptSetsBrick17);
                        this._arrayPtSets.add(ptSetsBrick18);
                        this._arrayPtSets.add(ptSetsBrick20);

                        totalVolume += this.calculateRegionVolume(ptArea_14, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_15, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_17, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_18, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_20, 1);
                    }
                    else //coil type 1 ; 無YokeStage2  有YokeStage1 無MagnetOuter
                    {
                        //設定相接面共用點
                        ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);

                        this._arrayPtSets.add(ptSetsBrick15);
                        this._arrayPtSets.add(ptSetsBrick18);

                        totalVolume += this.calculateRegionVolume(ptArea_15, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_18, 1);
                    }
                }
            }
            else // 無YokeStage1
            {
                //
            }
        }
        else //coil type 2
        {
            CartesianPointSetsBrick ptSetsBrick26;
            ArrayList array1 = new ArrayList();
            ArrayList array2 = new ArrayList();
            ArrayList array3 = new ArrayList();

            if (this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE) // 有YokeStage1
            {
                if (this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE) // 有YokeStage2
                {
                    if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE) //coil type 2 ; 有YokeStage2  有YokeStage1 有MagnetOuter
                    {
                        array1.add(ptArea_23);

                        array2.add(ptArea_24);
                        array2.add(ptArea_25);

                        array3.add(ptArea_16); //////////////////
                        array3.add(ptArea_15); //
                        array3.add(ptArea_14); //順序不能重複
                        array3.add(ptArea_13); //
                        array3.add(ptArea_12); //////////////////

                        for (int j = 0; j < jCount; j++)
                        {
                            //ptArea_26[j] = this.calculatePt2(array1, array2, array3, h5_1, h5_2, j);
                        }

                        ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);

                        //設定相接面共用點
                        ptSetsBrick13.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_DOWN);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_DOWN);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_17, this.BRICK_FACE_INNER);
                        ptSetsBrick17.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                        ptSetsBrick18.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_DOWN);
                        ptSetsBrick16.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_INNER);
                        ptSetsBrick18.setContactNodeFromOther(ptArea_20, this.BRICK_FACE_INNER);
                        ptSetsBrick20.setContactNodeFromOther(ptArea_21, this.BRICK_FACE_DOWN);
                        ptSetsBrick19.setContactNodeFromOther(ptArea_21, this.BRICK_FACE_INNER);
                        ptSetsBrick22.setContactNodeFromOther(ptArea_22, this.BRICK_FACE_INNER);
                        ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick16.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);

                        this._arrayPtSets.add(ptSetsBrick14);
                        this._arrayPtSets.add(ptSetsBrick15);
                        this._arrayPtSets.add(ptSetsBrick16);
                        this._arrayPtSets.add(ptSetsBrick17);
                        this._arrayPtSets.add(ptSetsBrick18);
                        this._arrayPtSets.add(ptSetsBrick19);
                        this._arrayPtSets.add(ptSetsBrick20);
                        this._arrayPtSets.add(ptSetsBrick21);
                        this._arrayPtSets.add(ptSetsBrick22);

                        totalVolume += this.calculateRegionVolume(ptArea_14, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_15, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_16, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_17, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_18, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_19, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_20, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_21, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_22, 1);
                    }
                    else //coil type 2 ; 有YokeStage2  有YokeStage1 無MagnetOuter
                    {
                        array1.add(ptArea_23);

                        array2.add(ptArea_24);
                        array2.add(ptArea_25);

                        array3.add(ptArea_16); //////////////////
                        array3.add(ptArea_15); //順序不能重複
                        array3.add(ptArea_13); //
                        array3.add(ptArea_12); //////////////////

                        for (int j = 0; j < jCount; j++)
                        {
                            //ptArea_26[j] = this.calculatePt2(array1, array2, array3, h5_1, h5_2, j);
                        }

                        ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);

                        //設定相接面共用點
                        ptSetsBrick13.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_16, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                        ptSetsBrick18.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_DOWN);
                        ptSetsBrick16.setContactNodeFromOther(ptArea_19, this.BRICK_FACE_INNER);
                        ptSetsBrick19.setContactNodeFromOther(ptArea_22, this.BRICK_FACE_INNER);
                        ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick16.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);

                        this._arrayPtSets.add(ptSetsBrick15);
                        this._arrayPtSets.add(ptSetsBrick16);
                        this._arrayPtSets.add(ptSetsBrick18);
                        this._arrayPtSets.add(ptSetsBrick19);
                        this._arrayPtSets.add(ptSetsBrick22);

                        totalVolume += this.calculateRegionVolume(ptArea_15, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_16, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_18, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_19, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_22, 1);
                    }
                }
                else // 無YokeStage2
                {
                    if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE) //coil type 2 ; 無YokeStage2  有YokeStage1 有MagnetOuter
                    {
                        array1.add(ptArea_23);

                        array2.add(ptArea_24);
                        array2.add(ptArea_25);

                        array3.add(ptArea_15); //////////////////
                        array3.add(ptArea_14); //順序不能重複
                        array3.add(ptArea_13); //
                        array3.add(ptArea_12); //////////////////

                        for (int j = 0; j < jCount; j++)
                        {
                            //ptArea_26[j] = this.calculatePt2(array1, array2, array3, h5_1, h5_2, j);
                        }

                        ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);

                        //設定相接面共用點
                        ptSetsBrick13.setContactNodeFromOther(ptArea_14, this.BRICK_FACE_DOWN);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_17, this.BRICK_FACE_INNER);
                        ptSetsBrick17.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                        ptSetsBrick18.setContactNodeFromOther(ptArea_20, this.BRICK_FACE_INNER);
                        ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick14.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);

                        this._arrayPtSets.add(ptSetsBrick14);
                        this._arrayPtSets.add(ptSetsBrick15);
                        this._arrayPtSets.add(ptSetsBrick17);
                        this._arrayPtSets.add(ptSetsBrick18);
                        this._arrayPtSets.add(ptSetsBrick20);

                        totalVolume += this.calculateRegionVolume(ptArea_14, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_15, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_17, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_18, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_20, 1);
                    }
                    else //coil type 2 ; 無YokeStage2  有YokeStage1 無MagnetOuter
                    {
                        array1.add(ptArea_23);

                        array2.add(ptArea_24);
                        array2.add(ptArea_25);

                        array3.add(ptArea_15); //////////////////
                        array3.add(ptArea_13); //順序不能重複
                        array3.add(ptArea_12); //////////////////

                        for (int j = 0; j < jCount; j++)
                        {
                            //ptArea_26[j] = this.calculatePt2(array1, array2, array3, h5_1, h5_2, j);
                        }

                        ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);

                        ptSetsBrick14.setContactNodeFromOther(ptArea_15, this.BRICK_FACE_DOWN);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_18, this.BRICK_FACE_INNER);
                        ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                        ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                        ptSetsBrick15.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);

                        this._arrayPtSets.add(ptSetsBrick15);
                        this._arrayPtSets.add(ptSetsBrick18);

                        totalVolume += this.calculateRegionVolume(ptArea_15, 1);
                        totalVolume += this.calculateRegionVolume(ptArea_18, 1);
                    }
                }
            }
            else //coil type 2 ; 無YokeStage1
            {
                array1.add(ptArea_23);

                array2.add(ptArea_24);
                array2.add(ptArea_25);

                array3.add(ptArea_13); //順序不能重複
                array3.add(ptArea_12); //////////////////

                for (int j = 0; j < jCount; j++)
                {
                    //ptArea_26[j] = this.calculatePt2(array1, array2, array3, h5_1, h5_2, j);
                }

                ptSetsBrick26 = new CartesianPointSetsBrick(ptArea_26);

                ptSetsBrick23.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick24.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick25.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_INNER);
                ptSetsBrick12.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);
                ptSetsBrick13.setContactNodeFromOther(ptArea_26, this.BRICK_FACE_OUTER);

            }
            ptSetsBrick26.setSectionType(SECTION_AIR_ZONE26);
            this._arrayPtSets.add(ptSetsBrick23);
            this._arrayPtSets.add(ptSetsBrick24);
            this._arrayPtSets.add(ptSetsBrick25);
            this._arrayPtSets.add(ptSetsBrick26);

            totalVolume += this.calculateRegionVolume(ptArea_23, 1);
            totalVolume += this.calculateRegionVolume(ptArea_24, 1);
            totalVolume += this.calculateRegionVolume(ptArea_25, 1);
            totalVolume += this.calculateRegionVolume(ptArea_26, 1);
        }
    }

    private double calculateRegionVolume(stepb_cartesian_point[][][] region, int dirType)
    {
        int jCount = region.length;
        int iCount = region[0].length;
        int tCount = region[0][0].length;

        if (dirType == 0)//某些region的i與j是顛倒的  在這邊把i與j互換
        {
            stepb_cartesian_point[][][] transposeArr = new stepb_cartesian_point[jCount][tCount][iCount];
            for (int j = 0; j < jCount; j++)
            {
                for (int i = 0; i < iCount; i++)
                {
                    for (int t = 0; t < tCount; t++)
                    {
                        transposeArr[j][t][i] = region[j][i][t];
                    }
                }
            }

            region = transposeArr;
            iCount = region[0].length;
            tCount = region[0][0].length;
        }

        double volume = 0;
        for (int j = 0; j < jCount; j++)
        {

            //   3------4
            //   |      |
            //   |      |
            //   1------2
            //
            if (j != jCount - 1)
            {
                for (int t = 0; t < tCount - 1; t++)
                {
                    stepb_cartesian_point pt1 = region[j][0][t];
                    stepb_cartesian_point pt2 = region[j][0][t + 1];
                    stepb_cartesian_point pt3 = region[j + 1][0][t];
                    stepb_cartesian_point pt4 = region[j + 1][0][t + 1];

                    MathVector2D vector1 = new MathVector2D(pt2.X() - pt1.X(), pt2.Y() - pt1.Y());
                    MathVector2D vector2 = new MathVector2D(pt4.X() - pt1.X(), pt4.Y() - pt1.Y());
                    MathVector2D vector3 = new MathVector2D(pt3.X() - pt1.X(), pt3.Y() - pt1.Y());
                    double area1 = Math.abs(vector1.Cross(vector2)) / 2; //wait for check
                    double area2 = Math.abs(vector2.Cross(vector3)) / 2; //wait for check
                    double region_I_Length = (Math.abs(region[j][0][t].Z() - region[j][(iCount - 1)][t].Z()) +
                                              Math.abs(region[j][0][t + 1].Z() - region[j][(iCount - 1)][t + 1].Z()) +
                                              Math.abs(region[j + 1][0][t].Z() - region[j + 1][(iCount - 1)][t].Z()) +
                                              Math.abs(region[j + 1][0][t + 1].Z() - region[j + 1][(iCount - 1)][t + 1].Z())) / 4;

                    volume = volume + (area1 + area2) * region_I_Length;
                }
            }
            else
            {
                for (int t = 0; t < tCount - 1; t++)
                {
                    stepb_cartesian_point pt1 = region[j][0][t];
                    stepb_cartesian_point pt2 = region[j][0][t + 1];
                    stepb_cartesian_point pt3 = region[0][0][t];
                    stepb_cartesian_point pt4 = region[0][0][t];

                    MathVector2D vector1 = new MathVector2D(pt2.X() - pt1.X(), pt2.Y() - pt1.Y());
                    MathVector2D vector2 = new MathVector2D(pt4.X() - pt1.X(), pt4.Y() - pt1.Y());
                    MathVector2D vector3 = new MathVector2D(pt3.X() - pt1.X(), pt3.Y() - pt1.Y());
                    double area1 = Math.abs(vector1.Cross(vector2)) / 2; //wait for check
                    double area2 = Math.abs(vector2.Cross(vector3)) / 2; //wait for check
                    double region_I_Length = (Math.abs(region[j][0][t].Z() - region[j][(iCount - 1)][t].Z()) +
                                              Math.abs(region[j][0][t + 1].Z() - region[j][(iCount - 1)][t + 1].Z()) +
                                              Math.abs(region[0][0][t].Z() - region[0][(iCount - 1)][t].Z()) +
                                              Math.abs(region[0][0][t + 1].Z() - region[0][(iCount - 1)][t + 1].Z())) / 4;

                    volume = volume + (area1 + area2) * region_I_Length;
                }
            }
        }
        return volume;
    }

    private double calculateRunwayNewVerticalValue(double theta, double runwayH_0, double runwayW_0)
    {
        double criticalAngle_0 = Math.atan(runwayH_0 / runwayW_0);
        double newV = 0;

        if (theta < criticalAngle_0) // 1st area
        {
            double angleA = Math.asin(runwayW_0 / runwayH_0 * Math.sin(theta));
            double angleB = Math.PI - angleA - theta;
            newV = Math.sqrt(runwayH_0 * runwayH_0 + runwayW_0 * runwayW_0 - 2 * runwayH_0 * runwayW_0 * Math.cos(angleB));
        }
        else if (theta >= criticalAngle_0 && theta <= Math.PI - criticalAngle_0) // 2nd area
        {
            newV = runwayH_0 / Math.sin(theta);
        }
        else if (theta > Math.PI - criticalAngle_0 && theta < Math.PI + criticalAngle_0) // 3rd area
        {
            if (theta > Math.PI)
                theta = theta - Math.PI;
            if (theta > Math.PI / 2)
                theta = Math.PI - theta;

            double angleA = Math.asin(runwayW_0 / runwayH_0 * Math.sin(theta));
            double angleB = Math.PI - angleA - theta;

            newV = Math.sqrt(runwayW_0 * runwayW_0 + runwayH_0 * runwayH_0 - 2 * runwayW_0 * runwayH_0 * Math.cos(angleB));
        }
        else if (theta >= Math.PI + criticalAngle_0 && theta <= 2 * Math.PI - criticalAngle_0) // 4th area
        {
            newV = Math.abs(runwayH_0 / Math.sin(theta));
        }
        else if (theta <= 2 * Math.PI) // 5th area
        {
            if (theta > Math.PI)
                theta = theta - Math.PI;
            if (theta > Math.PI / 2)
                theta = Math.PI - theta;

            double angleA = Math.asin(runwayW_0 / runwayH_0 * Math.sin(theta));
            double angleB = Math.PI - angleA - theta;

            newV = Math.sqrt(runwayW_0 * runwayW_0 + runwayH_0 * runwayH_0 - 2 * runwayW_0 * runwayH_0 * Math.cos(angleB));
        }
        return newV;
    }
    public void setAirDenser(boolean bool)
   {
       if (bool)
           _factorCircle += 1.0;
       else
       {
           if (_factorCircle >= 2)
               _factorCircle -= 1.0;
       }
   }
   public void setAirElementRatio(double factorCircle)
   {
       _factorCircle = factorCircle;
   }
   public double getAirElementRatio()
   {
       return this._factorCircle;
   }



}
