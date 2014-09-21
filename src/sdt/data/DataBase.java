package sdt.data;

import java.awt.*;
import java.io.*;
import java.util.*;

import sdt.define.*;
import sdt.geometry.element.*;
import sdt.material.*;
import sdt.stepb.*;
/**
 *
 * <p>Title: </p>
 *
 * <pre>所有Data的基底型態<br>
 * 1.宣告三個2D繪圖元件,分別是elementXZ, elementYZ 以及elementXY<br>
 * 2.且宣告每個element的起點與終點 ElementPointStart,ElementPointEnd
 *
 * </pre>
 *
 * @author Roger, Fox
 * @version 1.0 2012.04.27
 */

public abstract class DataBase implements DefineSystemConstant
{
    /**DataManager 資料總管*/
    protected SDT_DataManager _dataManager = null;
    /**elementXZ = XZ 截面 2D繪圖原件*/
    protected ElementCommon _elmementXZ = null; //Section1
    /**elementYZ = YZ 截面 2D繪圖原件*/
    protected ElementCommon _elmementYZ = null; //Section2
    /**elementXY = XY 截面 2D繪圖原件 TopView*/
    protected ElementCommon _elmementXY = null;

    protected int _dataFamily = this.TYPE_NULL;
    protected int _dataType  = this.TYPE_NULL;
    protected int _geometryType = this.TYPE_NULL;
    /** point 3D for show圖 */
    /**
     * Basic points ,contains only start and End , can cover only parts of dataShell
     *
     */
    protected stepb_cartesian_point _point3DStartSection1 = null;
    protected stepb_cartesian_point _point3DEndSection1 = null;

    protected stepb_cartesian_point _point3DStartSection2 = null;
    protected stepb_cartesian_point _point3DEndSection2 = null;

    protected ElementPoint _elementPtXZStart = null;
    protected ElementPoint _elementPtXZEnd = null;

    protected ElementPoint _elementPtYZStart = null;
    protected ElementPoint _elementPtYZEnd = null;


    protected ElementPoint _elementPtXYStart = null;
    protected ElementPoint _elementPtXYEnd = null;
    protected ElementPoint _elementPtXYCircumferentialRadial[][] = null;
    protected ElementPoint _elementPtXYCircumferentialRadialThickness[][][] = null;

    protected boolean _isPerodical = true; //or only Circumferential
    protected int _countRadial;
    protected int _countCircumferential;
    protected int _countPeriodical;
    protected int _countGirth;
    protected int _countThickness;


    protected boolean _isRunway;

    protected Color _colorPt;
    protected Color _colorBody;




    protected MaterialBase _material;

    public Color            getColorBody()        { return this._colorBody;         }

    public MaterialBase     getMaterial()         { return _material;               }
    public int              getDataType()         { return this._dataType;          }
    public int              getDataFamily()       { return this._dataFamily;        }

    public void setPeriodical(boolean bool)
    {
        this._isPerodical = bool;
    }

    public void setCountPeriodical(int count)
    {
        this._countPeriodical = count;
    }

    public void setCountCircumferential(int count)
    {
        this._countCircumferential = count;
    }

    public void setCountRadial(int count)
    {
        this._countRadial = count;
    }
    public void setCountThickness(int count)
    {
        this._countThickness = count;
    }


    public void setIsRunway(boolean isRunway)
    {
        this._isRunway = isRunway;
    }

    public boolean getPeriodical()
    {
        return this._isPerodical;
    }

    public int getCountPeriodical()
    {
        return this._countPeriodical;
    }

    public int getCountCircumferential()
    {
        return this._countCircumferential;
    }

    public int getCountRadial()
    {
        return this._countRadial;
    }

    public int getCountThickness()
    {
        return this._countThickness;
    }


    public boolean getIsRunway()
    {
        return this._isRunway;
    }


    public SDT_DataManager getDataManager()
    {
        return this._dataManager;
    }

    public DataBase(SDT_DataManager dataManager)
    {
        this._dataManager = dataManager;
        //this._colorCap = _dataManager.getColorCap();
        this._colorPt = _dataManager.getColorPoint();
        /*this._colorTransition = _dataManager.getColorTransition();
        this._colorSurround = _dataManager.getColorSurround();
        this._colorDiaphragm = _dataManager.getColorDiaphragm();
        this._colorMagnet = _dataManager.getColorMagnet();
        this._colorMagnetTop = _dataManager.getColorMagnetTop();
        this._colorTopPlate = _dataManager.getColorTopPlate();
        this._colorCoil = _dataManager.getColorCoil();
        this._colorBobbin = _dataManager.getColorBobbin();
        this._colorYoke = _dataManager.getColorBobbin();*/


        this._isRunway = false;
    }


    public ElementPoint getElementPointStart(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZStart;

            case DefineSystemConstant.YZView:
                return this._elementPtYZStart;

            case DefineSystemConstant.XYView:
                return this._elementPtXYStart;

            default:
                return null;
        }
    }

    /**
     * 輸入定義截面就可以得到該元件之末端點  在截面上之投影點
     * @param sectionNumber int  參考DefineSystemConstant.XZView, YZView,.XYView
     * @return ElementPoint show 圖2D元件
     */

    public ElementPoint getElementPointEnd(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZEnd;

            case DefineSystemConstant.YZView:
                return this._elementPtYZEnd;

            case DefineSystemConstant.XYView:
                return this._elementPtXYEnd;

            default:
                return null;
        }
    }


    public ElementPoint[][] getElementPointtXYCircumferentialRadial()
    {
        return this._elementPtXYCircumferentialRadial;
    }
    public ElementPoint[][][] getElementPointtXYCircumferentialRadialThickness()
    {
        return this._elementPtXYCircumferentialRadialThickness;
    }
    public stepb_cartesian_point getPoint3DStartSection1()
    {
        return this._point3DStartSection1;
    }

    public stepb_cartesian_point getPoint3DEndSection1()
    {
        return this._point3DEndSection1;
    }

    public stepb_cartesian_point getPoint3DStartSection2()
    {
        return this._point3DStartSection2;
    }

    public stepb_cartesian_point getPoint3DEndSection2()
    {
        return this._point3DEndSection2;
    }

    public ElementCommon getElement(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this.setDataToElementXZ();
                return this._elmementXZ;

            case DefineSystemConstant.YZView:
                this.setDataToElementYZ();
                return this._elmementYZ;

            case DefineSystemConstant.XYView:
                this.setDataToElementXY();
                return this._elmementXY;

            default:
                return null;
        }

    }
/*
    public void setElement(int sectionNumber, ElementCommon _element)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._elmementXZ = _element;
                this.setElementXZToData();
                break;

            case DefineSystemConstant.YZView:
                this._elmementYZ = _element;
                this.setElementYZToData();
                break;

            case DefineSystemConstant.XYView:
                this._elmementXY = _element;
                this.setElementXYToData();
                break;
        }
    }*/

    public void setElementPointStart(ElementPoint startPt, int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZStart = startPt;
                this._point3DStartSection1.GetMathVector3d().Set(_elementPtXZStart.X(), 0, _elementPtXZStart.Y());
                this._point3DStartSection2.GetMathVector3d().SetZ(_elementPtXZStart.Y());
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZStart = startPt;
                this._point3DStartSection2.GetMathVector3d().Set(0, _elementPtYZStart.X(), _elementPtYZStart.Y());
                this._point3DStartSection1.GetMathVector3d().SetZ(_elementPtYZStart.Y());
                break;
            case DefineSystemConstant.XYView:
                break;
        }
    }

    public void setElementPointEnd(ElementPoint startPt, int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZEnd = startPt;
                this._point3DEndSection1.GetMathVector3d().Set(_elementPtXZEnd.X(), 0, _elementPtXZEnd.Y());
                this._point3DEndSection2.GetMathVector3d().SetZ(_elementPtXZEnd.Y());
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZEnd = startPt;
                this._point3DEndSection2.GetMathVector3d().Set(0, _elementPtYZEnd.X(), _elementPtYZEnd.Y());
                this._point3DEndSection1.GetMathVector3d().SetZ(_elementPtYZEnd.Y());
                break;
            case DefineSystemConstant.XYView:
                break;
        }
    }


    /* public void setElementPointXZStart(ElementPoint startPt)
     {
         this._elementPtXZStart = startPt;
         this._point3DStartSection1.GetMathVector3d().Set(_elementPtXZStart.X(),0,_elementPtXZStart.Y());
     }

     public void setElementPointXZEnd(ElementPoint endPt)
     {
         this._elementPtXZEnd = endPt;
         this._point3DEndSection1.GetMathVector3d().Set(_elementPtXZEnd.X(),0,_elementPtXZEnd.Y());
     }

     public void setElementPointYZStart(ElementPoint startPt)
     {
          this._point3DStartSection1.GetMathVector3d().Set(0,_elementPtYZStart.X(),_elementPtYZStart.Y());
     }

     public void setElementPointYZEnd(ElementPoint endPt)
     {
          this._point3DStartSection1.GetMathVector3d().Set(0,_elementPtYZStart.X(),_elementPtYZStart.Y());
     }*/




    public void setElementPointStartCoordinate(int sectionNumber, double x, double y)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                if (this._elementPtXZStart != null)
                {
                    this._elementPtXZStart.setCoordinate(x, y);
                }
                break;

            case DefineSystemConstant.YZView:
                if (this._elementPtYZStart != null)
                {
                    this._elementPtYZStart.setCoordinate(x, y);
                }
                break;

            case DefineSystemConstant.XYView:
                if (this._elementPtXYStart != null)
                {
                    this._elementPtXYStart.setCoordinate(x, y);
                }
                break;
        }
    }

    public void setElementPointEndCoordinate(int sectionNumber, double x, double y)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                if (this._elementPtXZEnd != null)
                {
                    this._elementPtXZEnd.setCoordinate(x, y);
                }
                break;

            case DefineSystemConstant.YZView:
                if (this._elementPtYZEnd != null)
                {
                    this._elementPtYZEnd.setCoordinate(x, y);
                }
                break;

            case DefineSystemConstant.XYView:
                if (this._elementPtXYEnd != null)
                {
                    this._elementPtXYEnd.setCoordinate(x, y);
                }
                break;
        }
    }

    /**
     * 根據不同的Section將現在的Element的Start Point及End Point 座標參數寫進dataBase裡面
     * @param sectionNumber int
     * @return boolean
     */
    public boolean setElementToData(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this.setElementXZToData();
            case DefineSystemConstant.YZView:
                return this.setElementYZToData();
            default:
                return false;
        }
    }


    /**
     * 將XZ_View的Start Point及End Point參數寫進DataBase
     * 包含XZ_View的X, Z座標值以及連帶影響YZ_View的Z座標值
     * @return boolean true: 成功寫入  false: 超過尺寸限制，寫入失敗
     */
    protected boolean setElementXZToData()
    {
        boolean isDrawAble = true;

        double newX1 = 0;
        double newX2 = 0;
        double newY1 = 0;
        double newY2 = 0;
        double oldX1 = 0;
        double oldX2 = 0;
        double oldY1 = 0;
        double oldY2 = 0;

        newX1 = this._elementPtXZStart.X();
        newX2 = this._elementPtXZEnd.X();
        newY1 = this._elementPtXZStart.Y();
        newY2 = this._elementPtXZEnd.Y();

        this._point3DStartSection1.GetMathVector3d().SetX(newX1);
        this._point3DEndSection1.GetMathVector3d().SetX(newX2);

        this._point3DStartSection1.GetMathVector3d().SetZ(newY1);
        this._point3DEndSection1.GetMathVector3d().SetZ(newY2);

        this._point3DStartSection2.GetMathVector3d().SetZ(newY1);
        this._point3DEndSection2.GetMathVector3d().SetZ(newY2);

        if (!_isRunway)//如果是圓形的話，XZ_View的X值須等於YZ_View的Y值
        {
            this._point3DStartSection2.GetMathVector3d().SetY(newX1);
            this._point3DEndSection2.GetMathVector3d().SetY(newX2);
        }

        if (this._elmementYZ == null)
        {
            this.setDataToElementYZ();
        }

        oldX1 = this._elementPtYZStart.X();
        oldX2 = this._elementPtYZEnd.X();
        oldY1 = this._elementPtYZStart.Y();
        oldY2 = this._elementPtYZEnd.Y();

        if (_isRunway)
        {
            this._elementPtYZStart.setCoordinate(oldX1, newY1);
            this._elementPtYZEnd.setCoordinate(oldX2, newY2);
            //System.out.println("this._elementPtYZEnd: ( " +oldX2 + ", "+  newY2 + ")");

        }
        else
        {
            this._elementPtYZStart.setCoordinate(newX1, newY1);
            this._elementPtYZEnd.setCoordinate(newX2, newY2);
        }

       if (this._elmementYZ.setBoundary())
        {
            //System.out.println("elementYZ Can't draw");
            this._elementPtYZStart.setCoordinate(oldX1, oldY1);
            this._elementPtYZEnd.setCoordinate(oldX2, oldY2);

            this._elementPtXZStart.setCoordinate(newX1, oldY1);
            this._elementPtXZEnd.setCoordinate(newX2, oldY2);

            this._point3DStartSection1.GetMathVector3d().SetZ(oldY1);
            this._point3DEndSection1.GetMathVector3d().SetZ(oldY2);

            this._point3DStartSection2.GetMathVector3d().SetZ(oldY1);
            this._point3DEndSection2.GetMathVector3d().SetZ(oldY2);
            isDrawAble = false;
        }
        return isDrawAble;
    }

    protected boolean setElementYZToData()
    {
        boolean isDrawAble = true;

        double newX1 = 0;
        double newX2 = 0;
        double newY1 = 0;
        double newY2 = 0;
        double oldX1 = 0;
        double oldX2 = 0;
        double oldY1 = 0;
        double oldY2 = 0;

        newX1 = this._elementPtYZStart.X();
        newX2 = this._elementPtYZEnd.X();
        newY1 = this._elementPtYZStart.Y();
        newY2 = this._elementPtYZEnd.Y();

        this._point3DStartSection2.GetMathVector3d().SetY(newX1);
        this._point3DEndSection2.GetMathVector3d().SetY(newX2);

        this._point3DStartSection2.GetMathVector3d().SetZ(newY1);
        this._point3DEndSection2.GetMathVector3d().SetZ(newY2);

        this._point3DStartSection1.GetMathVector3d().SetZ(newY1);
        this._point3DEndSection1.GetMathVector3d().SetZ(newY2);
        if (!_isRunway || this._geometryType == this.CAP_TYPE_ROUNDCUT) //如果是圓形的話，YZ_View的Y值須等於XZ_View的X值
        {
            this._point3DStartSection1.GetMathVector3d().SetX(newX1);
            this._point3DEndSection1.GetMathVector3d().SetX(newX2);
        }

        if (this._elmementXZ == null)
        {
            this.setDataToElementXZ();
        }
        oldX1 = this._elementPtXZStart.X();
        oldX2 = this._elementPtXZEnd.X();
        oldY1 = this._elementPtXZStart.Y();
        oldY2 = this._elementPtXZEnd.Y();
        if (_isRunway &&  this._geometryType != this.CAP_TYPE_ROUNDCUT)
        {
            this._elementPtXZStart.setCoordinate(oldX1, newY1);
            this._elementPtXZEnd.setCoordinate(oldX2, newY2);
        }
        else
        {
            this._elementPtXZStart.setCoordinate(newX1, newY1);
            this._elementPtXZEnd.setCoordinate(newX2, newY2);
        }

        if (this._elmementXZ.setBoundary())
        {
            this._elementPtXZStart.setCoordinate(oldX1, oldY1);
            this._elementPtXZEnd.setCoordinate(oldX2, oldY2);

            this._elementPtYZStart.setCoordinate(newX1, oldY1);
            this._elementPtYZEnd.setCoordinate(newX2, oldY2);

            this._point3DStartSection2.GetMathVector3d().SetZ(oldY1);
            this._point3DEndSection2.GetMathVector3d().SetZ(oldY2);

            this._point3DStartSection1.GetMathVector3d().SetZ(oldY1);
            this._point3DEndSection1.GetMathVector3d().SetZ(oldY2);
            isDrawAble = false;
        }
        return isDrawAble;
    }


    protected abstract void setElementXYToData();

    protected abstract void setDataToElementXZ();

    protected abstract void setDataToElementYZ();




    /**
     * Create ElementXy, and if ptSets is null or is needed to update, this
     * function will trigger the regenerateElementXY,
     * In the function regenerateElementXY, ptSets will be calculated
     * by invoking createDataPointPeriodically, createDataPointCircumferential or createDataPointCircumferentialRunway
     *
     */
    public abstract void setDataToElementXY();

    protected double _heightOuter;
    protected double _widthOuter;
    protected double _heightInner;
    protected double _widthInner;

    protected double _thetaFan;
    //protected double _thetaTri;
    protected int _numberFan;
    protected int _numberTri;

    protected double _thetaFanInner;
    protected double _thetaTriInner;
    //protected int _numberFanInner;
    //protected int _numberTriInner;

    protected boolean _isRoundRunway;
    protected ElementPoint[] _elementArrayInner;
    protected ElementPoint[] _elementArrayOuter;
    protected boolean _isXYupdateNeed = true;

    public void setIsXYUpdateNeed(boolean bool)
    {
        _isXYupdateNeed = bool;
    }
    public boolean getIsXYUpdateNeed()
    {
        return this._isXYupdateNeed;
    }
    protected boolean detectedIsRoundRound()
    {
        if ((Math.abs(this._point3DStartSection2.Y() - this._point3DStartSection1.X()) / this._point3DStartSection1.X() < 0.03)
            && (Math.abs(this._point3DEndSection2.Y() - this._point3DEndSection1.X()) / this._point3DEndSection1.X() < 0.03))
            return true;
        return false;
    }

    protected void detectedIsRoundRunway()
    {
        if (Math.abs(this._point3DStartSection2.Y() - this._point3DStartSection1.X()) / this._point3DStartSection1.X() < 0.03)
        {
            _isRoundRunway = true;
        }
        else
        {
            _isRoundRunway = false;
        }
    }

    /***
     * <pre> make sure all the </pre>
     *
     *
     */

    public void setCircumferentialNumber(boolean isDriven, boolean isOuter)
    {
        this._heightOuter = this._point3DEndSection2.Y();
        this._widthOuter = Math.abs(this._point3DEndSection1.X() - _heightOuter);
        this._heightInner = this._point3DStartSection2.Y();
        this._widthInner = Math.abs(this._point3DStartSection1.X() - _heightInner);

        double r = _heightOuter; //Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
        double thetaFan_2 = Math.atan(r / _widthOuter);

        this._thetaFan = thetaFan_2 * 2;
        if(!isDriven)
        {
            if (isOuter)
            {
                this._dataManager.setCountCircumferentialRunway(_heightOuter, _widthOuter);
            }
            else
            {
                this._dataManager.setCountCircumferentialRunway(_heightInner, _widthInner);
            }

        }
        this._numberFan = this._dataManager.getCountFan(isDriven);
        this._numberTri = this._dataManager.getCountTri(isDriven);

    }






    private int[] setRunwayCircumferentialNumber(double height, double width, int count)
    {
        double r = height; //Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
        double perimeter = 4 * width + 2 * Math.PI * height;
        double perimeterInterval = perimeter / count;

        int     numberFan = 0;
        int     numberTri = 0;

        numberFan = (int) (Math.PI * r / perimeterInterval);
        numberTri = count / 2  - numberFan;
        int[] result = {numberFan,numberTri};
        return result;
    }


    protected void setRunwayInner()
    {
        this._elementArrayInner = this.setRunway(_heightInner,
                                                 _widthInner,
                                                 _numberFan,
                                                 _numberTri,
                                                 0
                                                 );
        /*  System.out.println("=============================");
          System.out.println("_heightInner: " + _heightInner);
          System.out.println("_widthInner: " + _widthInner);
          System.out.println("_thetaFanInner: " + _thetaFanInner);
          System.out.println("_thetaIntervalFanInner: " + _thetaIntervalFanInner);
          System.out.println("_thetaIntervalTriInner: " + _thetaIntervalTriInner);
          System.out.println("_numberFanInner: " + _numberFanInner);
          System.out.println("_numberTriInner: " + _numberTriInner);

         */

    }

    protected void setRunwayOuter()
    {
        this._elementArrayOuter = this.setRunway(_heightOuter,
                                                 _widthOuter,
                                                 _numberFan,
                                                 _numberTri,
                                                 0
                                                 );
        /*
          System.out.println("=============================");
          System.out.println("_heightOuter: " + _heightOuter);
          System.out.println("_widthOuter: " + _widthOuter);
          System.out.println("_thetaFanOuter: " + _thetaFanOuter);
          System.out.println("_thetaIntervalFanOuter: " + _thetaIntervalFanOuter);
          System.out.println("_thetaIntervalTriOuter: " + _thetaIntervalTriOuter);
          System.out.println("_numberFanOuter: " + _numberFanOuter);
          System.out.println("_numberTriOuter: " + _numberTriOuter);
         */
    }

    protected void setRoundCutOuter()
    {
        this._elementArrayOuter = new ElementPoint[this._countGirth];

        double roundCutRadius = this._point3DEndSection1.X();
        double roundCutHeight = this._point3DEndSection2.Y();

        double thetaInteval = 2 * Math.PI / _countGirth;
        double thetaInitial = 0;

        if (!this._dataManager.getIsStartFromAxis())
            thetaInitial = thetaInteval / 2.0;

        for (int i = 0; i < _countGirth; i++)
        {
            double thetaI = thetaInteval * i + thetaInitial;

            double x = roundCutRadius * Math.cos(thetaI);
            double y = roundCutRadius * Math.sin(thetaI);

            if (y > roundCutHeight)
            {
                y = roundCutHeight;

            }
            else if (y < -1 * roundCutHeight)
            {
                y = -1 * roundCutHeight;
            }

            ElementPoint point = new ElementPoint(x, y, this._colorPt ,this);
            this._elementArrayOuter[i] = point;

        }

    }

    protected void setRoundInner()
    {
        this._elementArrayInner = this.setRound(_heightInner);//,this._thetaFan,this._numberFan ,this._numberTri);
    }

    protected void setRoundCutInner()
    {
        this._elementArrayInner = new ElementPoint[this._countGirth];

        double roundCutRadius = this._point3DStartSection1.X();
        double roundCutHeight = this._point3DStartSection2.Y();

        double thetaInteval = 2 * Math.PI / _countGirth;
        double thetaInitial = 0;


        if (!this._dataManager.getIsStartFromAxis())
            thetaInitial = thetaInteval / 2.0;

        for (int i = 0; i < _countGirth; i++)
        {
            double thetaI = thetaInteval * i + thetaInitial;

            double x = roundCutRadius * Math.cos(thetaI);
            double y = roundCutRadius * Math.sin(thetaI);

            if (y > roundCutHeight)
            {
                y = roundCutHeight;

            }
            else if (y < -1 * roundCutHeight)
            {
                y = -1 * roundCutHeight;
            }

            ElementPoint point = new ElementPoint(x, y, this._colorPt ,this);
            this._elementArrayInner[i] = point;
        }


    }

    private ElementPoint[] setRunway(double height,
                                     double width,
                                     int numberFan,
                                     int numberTri,
                                     double angle
                                     )
    {
        double skewAngle = angle * Math.PI / 180.0;

        ElementPoint elementPtArray[] = new ElementPoint[_countCircumferential];
        ArrayList pointArray = new ArrayList();
        double radius = height / 6.0;
        double r = height;
        double w = width;
        double h = height;
        double thetaFan = 2 *Math.atan(h/w);
        double thetaIntervalFan =thetaFan / numberFan;

        double phi = 0;

        for (int i = 0; i < numberFan / 2 + 1; i++) //1
        {
            double thetaI = thetaIntervalFan * i;

            if (numberFan % 2 != 0)
            {
                thetaI += thetaIntervalFan / 2.0;

                if (i == (numberFan / 2) + 1)
                {
                    break;
                }
            }
            double l;
            if (Math.abs(Math.sin(thetaI - skewAngle)) > 10E-5)
            {
                if (thetaI + skewAngle != 0)
                {
                    l = Math.sin(skewAngle) / Math.sin(thetaI - skewAngle) * radius;
                }
                else
                {
                    l = 0;
                }
                phi = thetaI - skewAngle + Math.asin((w + l) / r * Math.sin(thetaI - skewAngle));
            }
            else
            {
                phi = Math.asin(radius * Math.sin(thetaI) / h);
            }
            double x;
            double y;

            if (Math.cos(phi) > 0)
            {
                x = w + r * Math.cos(phi);
                y = r * Math.sin(phi);
            }
            else
            {
                x = (r - radius * Math.sin(thetaI)) / Math.tan(thetaI - skewAngle) + radius * Math.cos(thetaI);
                y = r;
            }

            //pointSets[i] = new ObjectPoint(x, y);
            ElementPoint point = new ElementPoint(x, y, _colorPt ,this );
            pointArray.add(point);

        }

        //Block 2 Tri

        double xStart = w;
        double yTri = r;

        double xInterval = w * 2 / (numberTri);
        for (int i = 1; i < numberTri + 1; i++) //2
        {
            double x = xStart - i * xInterval;
            ElementPoint point = new ElementPoint(x, yTri, _colorPt ,this);
            pointArray.add(point);

        }

        for (int i = 1; i < numberFan + 1; i++) //3
        {
            //double thetaI = Math.PI - thetaFan / 2.0 + thetaIntervalFan * i;

            /* r = height;
             double x =  - w  +  r * Math.cos(thetaI);
             double y = r * Math.sin(thetaI);*/
            double thetaI = thetaFan / 2.0 - thetaIntervalFan * i; //Math.PI -  thetaFan + thetaIntervalFan * i;

            double l = 0;
            if (Math.abs(Math.sin(thetaI + skewAngle)) > 10E-5)
            {
                if (thetaI - skewAngle != 0)
                {
                    l = Math.sin(skewAngle) / Math.sin(thetaI + skewAngle) * radius;
                }
                phi = thetaI + skewAngle + Math.asin((w - l) / r * Math.sin(thetaI + skewAngle));
            }
            else
            {
                phi = Math.asin(radius * Math.sin(thetaI) / h);
            }

            //double l = -1 * Math.sin(skewAngle) / Math.sin(thetaI + skewAngle) * radius;
            //phi = thetaI + skewAngle + Math.asin((w + l) / r * Math.sin(thetaI + skewAngle));


            double x;
            double y;

            if (Math.cos(phi) > 0)
            {
                x = -w - r * Math.cos(phi);
                y = r * Math.sin(phi);
            }
            else if (phi > 90 * Math.PI / 180)
            {
                x = -1 * ((r - radius * Math.sin(thetaI)) / Math.tan(thetaI + skewAngle) + radius * Math.cos(thetaI));
                y = r;
            }
            else //
            {
                x = -1 * ((r - radius * Math.sin( -thetaI)) / Math.tan( -thetaI - skewAngle) + radius * Math.cos( -thetaI));
                y = -r;
            }

            //pointSets[i] = new ElementPoint(x, y,_colorPt);
            ElementPoint point = new ElementPoint(x, y, _colorPt ,this);
            pointArray.add(point);

        }

        xStart = -w;
        yTri = -r;

        for (int i = 1; i < numberTri + 1; i++) //4
        {
            double x = xStart + i * xInterval;
            ElementPoint point = new ElementPoint(x, yTri, _colorPt ,this);
            pointArray.add(point);

        }

        for (int i = 1; i < numberFan / 2 + 1; i++) //5
        {
            double thetaI = thetaFan / 2.0 - thetaIntervalFan * i;

            double l = 0;
            if (Math.abs(Math.sin(thetaI + skewAngle)) > 10E-5)
            {
                if (thetaI + skewAngle != 0)
                {
                    l = -1 * Math.sin(skewAngle) / Math.sin(thetaI + skewAngle) * radius;
                }
                phi = thetaI + skewAngle + Math.asin((w + l) / r * Math.sin(thetaI + skewAngle));
            }
            else
            {
                phi = Math.asin(radius * Math.sin(thetaI) / h);
            }

            double x;
            double y;

            if (phi > 90 * Math.PI / 180)
            {
                x = (r - radius * Math.sin(thetaI)) / Math.tan(thetaI + skewAngle) + radius * Math.cos(thetaI);
                y = -r;
            }
            else
            {
                x = w + r * Math.cos(phi);
                y = -r * Math.sin(phi);
            }

            ElementPoint point = new ElementPoint(x, y, _colorPt,this);
            pointArray.add(point);

        }

        for (int i = 0; i < _countCircumferential; i++)
        {
            elementPtArray[i] = (ElementPoint) pointArray.get(i);
        }

        return elementPtArray;
    }
    private ElementPoint[] setRound(double height,
                                  double thetaFan,
                                  int numberFan,
                                  int numberTri)

  {
      ElementPoint elementPtArray[] = new ElementPoint[_countGirth];
      ArrayList pointArray = new ArrayList();
      double radius = height;
      double thetaTri = Math.PI - thetaFan;
      double thetaIntervalFan = thetaFan / numberFan;
      double thetaIntervalTri = thetaTri / numberTri;

      for (int i = 0; i < numberFan / 2 + 1; i++) //1
      {
          double thetaI = thetaIntervalFan * i;

          if (numberFan % 2 != 0)
          {
              thetaI += thetaIntervalFan / 2.0;

              if (i == (numberFan / 2) + 1)
                  break;
          }

          double x = radius * Math.cos(thetaI);
          double y = radius * Math.sin(thetaI);

          //pointSets[i] = new ElementPoint(x, y,_colorPt);
          ElementPoint point = new ElementPoint(x, y, this._colorPt,this);
          pointArray.add(point);
      }

      for (int i = 1; i < numberTri + 1; i++) //2
      {
          double thetaI = thetaFan / 2.0 + thetaIntervalTri * i;
          double x = radius * Math.cos(thetaI);
          double y = radius * Math.sin(thetaI);
          //pointSets[i] = new ElementPoint(x, y,_colorPt);
          ElementPoint point = new ElementPoint(x, y, this._colorPt ,this);
          pointArray.add(point);

      }
      for (int i = 1; i < numberFan + 1; i++) //3
      {
          double thetaI = Math.PI - thetaFan / 2.0 + thetaIntervalFan * i; //Math.PI -  thetaFan + thetaIntervalFan * i;
          double x = radius * Math.cos(thetaI);
          double y = radius * Math.sin(thetaI);
          //pointSets[i] = new ElementPoint(x, y,_colorPt);
          ElementPoint point = new ElementPoint(x, y, this._colorPt ,this);
          pointArray.add(point);

      }
      for (int i = 1; i < numberTri + 1; i++) //4
      {
          double thetaI = Math.PI + thetaFan / 2.0 + thetaIntervalTri * i;
          double x = radius * Math.cos(thetaI);
          double y = radius * Math.sin(thetaI);
          ElementPoint point = new ElementPoint(x, y, this._colorPt,this);
          pointArray.add(point);

      }
      for (int i = 1; i < numberFan / 2 + 1; i++) //5
      {
          double thetaI = -thetaFan / 2.0 + thetaIntervalFan * i;
          double x = radius * Math.cos(thetaI);
          double y = radius * Math.sin(thetaI);

          //pointSets[i] = new ElementPoint(x, y,_colorPt);
          ElementPoint point = new ElementPoint(x, y, this._colorPt,this);
          pointArray.add(point);

      }

      for (int i = 0; i < _countGirth; i++)
      {
          elementPtArray[i] = (ElementPoint) pointArray.get(i);
      }
      return elementPtArray;
  }

    private ElementPoint[] setRound(double height)



    {
        ElementPoint elementPtArray[] = new ElementPoint[this._countGirth];
        ArrayList pointArray = new ArrayList();
        double radius = height;
        double thetaInteval = 2 * Math.PI / _countGirth;
        double thetaInitial = 0;

        if (!this._dataManager.getIsStartFromAxis())
            thetaInitial = thetaInteval / 2.0;

        for (int i = 0; i < _countGirth; i++) //1
        {
            double thetaI = thetaInteval * i + thetaInitial;

            double x = radius * Math.cos(thetaI);
            double y = radius * Math.sin(thetaI);

            //pointSets[i] = new ElementPoint(x, y,_colorPt);
            ElementPoint point = new ElementPoint(x, y, this._colorPt,this);
            pointArray.add(point);
        }

        for (int i = 0; i < _countGirth; i++)
        {
            elementPtArray[i] = (ElementPoint) pointArray.get(i);
        }
        return elementPtArray;
    }

    /***
     * <pre>
     *
     *
     * <br>
     * 計算線段方向
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/calculateTheta.png"  align="center" class="border" width="150" height="150" />
     * </pre>
     * @param dx
     * @param dy
     * @return Theta from 0 ~ 2PI, angle start from 3 O'Clock as 0 degree
     */
    public double calculateTheta(double dx, double dy)
    {
        double theta = 0;

        if (dx > 0 && dy >= 0) //第1象限
        {
            theta = Math.atan(dy / dx);
        }
        else if (dx == 0 && dy > 0)
        {
            theta = Math.PI / 2.0; //90度
        }
        else if (dx < 0 && dy >= 0) //第2象限
        {
            theta = Math.PI - Math.atan(Math.abs(dy / dx));
        }
        else if (dx < 0 && dy < 0) //第3象限
        {
            theta = Math.PI + Math.atan(Math.abs(dy / dx));
        }
        else if (dx == 0 && dy < 0) //270度
        {
            theta = Math.PI * 3.0 / 2.0;
        }
        else if (dx > 0 && dy < 0) //第4象限
        {
            theta = 2 * Math.PI - Math.atan(Math.abs(dy / dx));
        }
        return theta;
    }

    /**
     * 將超過0~2Pi的角度修正回0~2Pi
     * @param originalAng 原始角度
     * @return 修正後0~2Pi之間的角度
     */
    public double modifyAngle(double originalAng)
    {
        while (originalAng < 0)
        {
            originalAng += 2 * Math.PI;
        }
        while (originalAng > 2 * Math.PI)
        {
            originalAng -= 2 * Math.PI;
        }

        return originalAng;
    }


    public void setGirth(int countGirth)
    {
        this._countGirth = countGirth;
        boolean isQuarter = true;

        if(this._countGirth % 4 != 0)
            isQuarter = false;

        if(!_isPerodical || !isQuarter)
        {
            this._countCircumferential = _countGirth;
            this._countPeriodical = 1;
            _isPerodical = false;
        }
        else
        {
            int multiple = 1;
            int countPeriodical = 4;
            int countCircumferential = countGirth / countPeriodical + 1;

            while (countCircumferential > this._countCircumferential && countGirth % (4 * multiple) == 0)
            {
                countPeriodical = 4 * multiple;
                countCircumferential = countGirth / countPeriodical + 1;
                multiple++;
            }
            this._countCircumferential = countCircumferential;
            this._countPeriodical = countPeriodical;
        }

    }
    /**
     * Save All Data into the FileWirter, don't need to concern about the file
     * address
     *
     * @param fw FileWriter FilePath will be assigned in this argument
     * @throws IOException
     */
    public abstract void save(FileWriter fw) throws IOException;

    /**
     * Open Data into the BufferedReader, don't need to concern about the file
     * address
     *
     * @param fw BufferedReader FilePath will be assigned in this argument
     * @throws IOException
     */
    public abstract void open(BufferedReader br) throws IOException;

    public String readLastString(String bufLine)
    {
        String resultStr = "";
        StringTokenizer token = new StringTokenizer(bufLine, ",");
        while (token.hasMoreTokens())
            resultStr = token.nextToken();

        return resultStr.trim();

    }
    /**
        * Set Geometry type (diaphragmType CapType)
        * <br>
        * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/diaphragmType1.png"  align="center" class="border" width="250" height="204" />
        * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/diaphragmType2.png"  align="center" class="border" width="250" height="204" />
        * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/diaphragmNone.png"  align="center" class="border" width="250" height="204" />
        * </pre>
        * <br>
        * @param type 參考DefineSystemConstant.NONE, .TYPE1,.TYPE2
        */
       public void setGeometryType(int type)
       {
           this._geometryType = type;
       }

       /**
        * 得到Geometry型式 (diaphragmType CapType)
        * <br>
        * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/diaphragmType1.png"  align="center" class="border" width="250" height="204" />
        * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/diaphragmType2.png"  align="center" class="border" width="250" height="204" />
        * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/diaphragmNone.png"  align="center" class="border" width="250" height="204" />
        * </pre>
        * <br>
        * @return 0: Type1  1: Type2  2: None
        */
       public int getGeometryType()
       {
           return  this._geometryType;
   }
   public void setColor(Color newColor)
   {
       this._colorBody = newColor;
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
       ElementPoint section2EndPt = new ElementPoint(0, endPt.Y(), Color.WHITE,this);
       ElementPoint section2StartPt = new ElementPoint(0, startPt.Y(), Color.WHITE,this);

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
           double theta, int iiCount)
   {
       stepb_cartesian_point ptsArray[] = new stepb_cartesian_point[iiCount];

       double rIncrement = (rE - rS) / (double) (iiCount - 1);
       double hIncrement = (hE - hS) / (double) (iiCount - 1);

       for (int ii = 0; ii < iiCount; ii++)
       {
           double x = (rS + rIncrement * ii) * Math.cos(theta);
           double y = (rS + rIncrement * ii) * Math.sin(theta);
           double z =  hS + hIncrement * ii;

           stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);

           ptsArray[ii] = cPt;
       }
       return ptsArray;
   }
   public double[] getMaxMin()
   {
       double maxX = this._point3DStartSection1.X();
       double maxY = this._point3DStartSection1.Y();
       double maxZ = this._point3DStartSection1.Z();
       double minX = this._point3DEndSection1.X();
       double minY = this._point3DEndSection1.Y();
       double minZ = this._point3DEndSection1.Z();
       double temp = 0;

       if(maxX < minX)
       {
           temp = maxX;
           maxX = minX;
           minX = temp;
       }
       if (maxY < minY)
       {
           temp = maxY;
           maxY = minY;
           minY = temp;
       }
       if (maxZ < minZ)
       {
           temp = maxZ;
           maxZ = minZ;
           minZ = temp;
       }

       return new double[]{minX,minY,minZ,maxX,maxY,maxZ};
   }

}
