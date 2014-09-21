package sdt.data;


import sdt.define.*;
import sdt.geometry.element.*;
import sdt.stepb.*;
/**
 *
 * <p>Title: </p>
 *
 * <pre>所有Data Shell 的基底型態<br>
 * 1.繼承三個2D繪圖元件,分別是elementXZ, elementYZ 以及elementXY<br>
 * 2.且宣告每個element的起點與終點 ElementPointStart,ElementPointEnd
 *
 * </pre>
 *
 * @author Roger
 * @version 1.0 2012.04.27
 */

public abstract class DataShell extends DataBase implements DefineSystemConstant
{

    protected double                      _skewedAngle =0.0; //degree
    protected boolean                     _skewedOn = false;

    protected boolean                     _isCorrugationOn = true;
    protected boolean                     _isBiasOn = false;

    protected int                         _corrugationJStartIStart = 2;
    protected int                         _corrugationJStartIEnd = 15;
    protected int                         _corrugationJStartICritical = 9;
    protected int                         _corrugationJEndIStart = 2;
    protected int                         _corrugationJEndIEnd = 15;
    protected int                         _corrugationJEndICritical = 9;

    protected int                         _JStart[]   ={2}; //2,8,14,20};
    protected int                         _JEnd[]     ={4}; //{4,10,16,22};

    protected double                      _corrugationRatioJStartIStart = 0.85;
    protected double                      _corrugationRatioJStartIEnd = 0.85;
    protected double                      _corrugationRatioJEndIStart = 0.85;
    protected double                      _corrugationRatioJEndIEnd = 0.85;
    protected double                      _corrugationRatioJStartICritical = 0.85;
    protected double                      _corrugationRatioJEndICritical = 0.85;
    protected double                      _corrugationFormingAngle = 90.0;

    protected stepb_cartesian_point[][]   _ptSetsShell;

    protected boolean[][]                 _highlightTopLineRadial;
    protected boolean[][]                 _highlightTopLineCircumfirential;

    protected double                      _thickness;


    public void setThickness(double t)
    {
        this._thickness = t;
    }

    public void setSkewedAngle(double skewedAngle)
    {
        this._skewedAngle = skewedAngle;
    }

    public void setCorrugationFormingAngle(double corrugationFormingAngle)
    {
        this._corrugationFormingAngle = corrugationFormingAngle;
    }



    public void setSkewedOn(boolean skewedOn)
    {
        this._skewedOn = skewedOn;
    }

    public void setCorrugationJStart(int[] jStart)
    {
        this._JStart = jStart;
    }

    public void setCorrugationJEnd(int[] jEnd)
    {
        this._JEnd = jEnd;
    }

    public void setIsBiasOn(boolean isBiasOn)
    {
        this._isBiasOn = isBiasOn;
    }

    public void setIsCorrugationOn(boolean isCorrugationOn)
    {
        this._isCorrugationOn = isCorrugationOn;
    }

    public void setCorrugationJStartIStart(int iStart)
    {
        this._corrugationJStartIStart = iStart;
    }

    public void setCorrugationJStartIEnd(int iEnd)
    {
        this._corrugationJStartIEnd = iEnd;
    }

    public void setCorrugationJStartICritical(int iCritical)
    {
        this._corrugationJStartICritical = iCritical;
    }

    public void setCorrugationJEndIStart(int iStart)
    {
        this._corrugationJEndIStart = iStart;
    }

    public void setCorrugationJEndIEnd(int iEnd)
    {
        this._corrugationJEndIEnd = iEnd;
    }

    public void setCorrugationJEndICritical(int iCritical)
    {
        this._corrugationJEndICritical = iCritical;
    }

    public void setCorrugationJStartIStartRatio(double ratio)
    {
        this._corrugationRatioJStartIStart = ratio;
    }

    public void setCorrugationJStartIEndRatio(double ratio)
    {
        this._corrugationRatioJStartIEnd = ratio;
    }

    public void setCorrugationJEndIStartRatio(double ratio)
    {
        this._corrugationRatioJEndIStart = ratio;
    }

    public void setCorrugationJEndIEndRatio(double ratio)
    {
        this._corrugationRatioJEndIEnd = ratio;
        ;
    }

    public void setCorrugationJStartICirticalRatio(double ratio)
    {
        this._corrugationRatioJStartICritical = ratio;
    }

    public void setCorrugationJEndICirticalRatio(double ratio)
    {
        this._corrugationRatioJEndICritical = ratio;
    }

    public double getThickness()
    {
        return this._thickness;
    }

    public boolean getSkewedOn()
    {
        return this._skewedOn;
    }

    public double getSkewedAngle()
    {
        return this._skewedAngle;
    }

    public double getCorrugationAngle()
    {
        return this._corrugationFormingAngle;
    }


    public int[] getCorrugationJStart()
    {
        return this._JStart;
    }

    public int[] getCorrugationJEnd()
    {
        return this._JEnd;
    }

    public boolean getIsBiasOn()
    {
        return this._isBiasOn;
    }

    public boolean getIsCorrugationOn()
   {
       return this._isCorrugationOn;
   }


    public int getCorrugationJStartIStart()
    {
        return this._corrugationJStartIStart;
    }

    public int getCorrugationJStartICritical()
    {
        return this._corrugationJStartICritical;
    }

    public int getCorrugationJStartIEnd()
    {
        return this._corrugationJStartIEnd;
    }

    public int getCorrugationJEndIStart()
    {
        return this._corrugationJEndIStart;
    }

    public int getCorrugationJEndICritical()
    {
        return this._corrugationJEndICritical;
    }

    public int getCorrugationJEndIEnd()
    {
        return this._corrugationJEndIEnd;
    }

    public double getCorrugationJStartIStartRatio()
    {
        return this._corrugationRatioJStartIStart;
    }

    public double getCorrugationJStartICriticalRatio()
    {
        return this._corrugationRatioJStartICritical;
    }

    public double getCorrugationJStartIEndRatio()
    {
        return this._corrugationRatioJStartIEnd;
    }

    public double getCorrugationJEndIStartRatio()
    {
        return this._corrugationRatioJEndIStart;
    }

    public double getCorrugationJEndICriticalRatio()
    {
        return this._corrugationRatioJEndICritical;
    }

    public double getCorrugationJEndIEndRatio()
    {
        return this._corrugationRatioJEndIEnd;
    }

    public boolean[][] getHighlightTopLineRadial()
    {
        return this._highlightTopLineRadial;
    }

    public DataShell(SDT_DataManager dataManager)
    {
        super(dataManager);
        _dataFamily = this.TYPE_FAMILY_SHELL;
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


    public boolean isJStart(int j)
    {
        if (!this._isCorrugationOn)
        {
            return false;
        }

        int loopNumber = this._countGirth;
        if (this._isPerodical)
            loopNumber = this._countCircumferential - 1;

        for (int jj = 0; jj < this._JStart.length; jj++)
        {
            if (j == _JStart[jj] ||  j == _JStart[jj] + loopNumber)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isJCritical(int j)
    {
        if (!this._isCorrugationOn)
        {
            return false;
        }

        int loopNumber = this._countGirth;
        if(this._isPerodical)
            loopNumber = this._countCircumferential-1;

        for (int jj = 0; jj < this._JStart.length; jj++)
        {
            if (j == (_JStart[jj] + 1) ||  j == _JStart[jj] + 1 + loopNumber)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isJStartPrevious(int j)
    {
        if (!this._isCorrugationOn)
        {
            return false;
        }
        int loopNumber = this._countGirth;
        if (this._isPerodical)
            loopNumber = this._countCircumferential - 1;



        for (int jj = 0; jj < this._JStart.length; jj++)
        {
            if (j == (_JStart[jj] - 1) || j == _JStart[jj] - 1 + loopNumber)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isJEnd(int j)
    {
        if (!this._isCorrugationOn)
        {
            return false;
        }
        int loopNumber = this._countGirth;
        if (this._isPerodical)
            loopNumber = this._countCircumferential - 1;


        for (int jj = 0; jj < this._JEnd.length; jj++)
        {
            if (j == _JEnd[jj]|| j == _JEnd[jj] - 1 - loopNumber )
            {
                return true;
            }
        }
        return false;
    }

    public boolean isJEndNext(int j)
    {
        if (!this._isCorrugationOn)
        {
            return false;
        }
        int loopNumber = this._countGirth;
        if (this._isPerodical)
            loopNumber = this._countCircumferential - 1;


        for (int jj = 0; jj < this._JEnd.length; jj++)
        {
            if (j == (_JEnd[jj] + 1) || j == _JEnd[jj]+1  - loopNumber)
            {
                return true;
            }
        }
        return false;
    }


    public void selectCorrugation()
    {
          if(this._isPerodical)
          {
              for (int k = 0; k < this._countPeriodical; k++)
              {
                  for (int j = 0; j < this._countCircumferential-1; j++)
                  {
                      for (int i = 0; i < this._countRadial; i++)
                      {
                          this._highlightTopLineRadial[k * (this._countCircumferential - 1) + j][i] = false;
                          if (this.isJStart(j) || this.isJEnd(j))
                          {
                              this._highlightTopLineRadial[k * (this._countCircumferential - 1) + j][i] = true;
                          }
                      }
                  }
              }
          }
          else
          {
              for (int j = 0; j < this._countCircumferential; j++)
              {
                  for (int i = 0; i < this._countRadial; i++)
                  {
                      this._highlightTopLineRadial[j][i] = false;
                      if (this.isJStart(j) || this.isJEnd(j))
                      {
                          this._highlightTopLineRadial[j][i] = true;
                      }
                  }
              }
          }
    }

    public void deSelectCorrugation()
    {
        if (this._isPerodical)
        {
            for (int k = 0; k < this._countPeriodical; k++)
            {
                for (int j = 0; j < this._countCircumferential - 1; j++)
                {
                    for (int i = 0; i < this._countRadial; i++)
                    {
                        this._highlightTopLineRadial[k * (this._countCircumferential - 1) + j][i] = false;
                    }
                }
            }
        }
        else
        {
            for (int j = 0; j < this._countCircumferential; j++)
            {
                for (int i = 0; i < this._countRadial; i++)
                {
                    this._highlightTopLineRadial[j][i] = false;
                }
            }
        }
    }

    /**
     * getPts of this Data
     *
     * @param index if index = -1, than retrun the geometry pts, perodically or
     *   circumfernetially, if index is greater than 0, it will returned the
     *   deformed point
     * @return stepb_cartesian_point[][] points to show
     */
    public stepb_cartesian_point[][] getShellPts(int index, double scale)
    {
        if (this._ptSetsShell == null)
        {
            setDataToElementXY();
        }
        if(index != -1)
        {
            stepb_cartesian_point_array displacementArray = this._dataManager.getDisplacementArray(index);
            displacementArray.rearrangeTable();
/*
            double maxDisplace = 0;
            for(int count = 0; count < displacementArray.size(); count++)
            {
                stepb_cartesian_point ptDisplacement = displacementArray.get(count);
                if(ptDisplacement.GetMathVector3d().Length() > maxDisplace)
                    maxDisplace = ptDisplacement.GetMathVector3d().Length();
            }
            double coneDiameter = this.getDataManager().getDataSurround().getElementPointEnd(this.XZView).X();

            double scale = coneDiameter * 0.2 / maxDisplace;*/

            int iiLength = _ptSetsShell.length;
            int jjLength = _ptSetsShell[0].length;
            stepb_cartesian_point[][] deformedPtsets = new stepb_cartesian_point[iiLength][jjLength];
            for (int ii = 0; ii < iiLength; ii++)
            {
                for (int jj = 0; jj < jjLength; jj++)
                {
                    int IDNumber = _ptSetsShell[ii][jj].GetIDNumber();
                    stepb_cartesian_point ptDisplacement = displacementArray.getByID(IDNumber);
                    math_vector3d v3d = null;
                    if (ptDisplacement != null)
                    {
                        v3d = _ptSetsShell[ii][jj].GetMathVector3d().Add(ptDisplacement.GetMathVector3dScaled(scale));

                        deformedPtsets[ii][jj] = new stepb_cartesian_point(v3d);
                        deformedPtsets[ii][jj].SetIDNumber(_ptSetsShell[ii][jj].GetIDNumber());
                    }
                    else
                    {
                        deformedPtsets[ii][jj] = new stepb_cartesian_point(_ptSetsShell[ii][jj]);
                        deformedPtsets[ii][jj].SetIDNumber(_ptSetsShell[ii][jj].GetIDNumber());
                    }
                }
            }
            return deformedPtsets;
        }
        return this._ptSetsShell;
    }

















    /**
     * Create ElementXy, and if ptSets is null or is needed to update, this
     * function will trigger the regenerateElementXY,
     * In the function regenerateElementXY, ptSets will be calculated
     * by invoking createDataPointPeriodically, createDataPointCircumferential or createDataPointCircumferentialRunway
     *
     */
    public void setDataToElementXY()
    {

        if (this._isRunway && this._isPerodical)
        {
            _isPerodical = false;
            this._countCircumferential = this._countPeriodical * (this._countCircumferential - 1);
            this._countPeriodical = 1;
        }

        if (this._isPerodical)
        {
            if (_ptSetsShell == null ||
            _ptSetsShell.length != _countPeriodical * (_countCircumferential - 1) ||
            _ptSetsShell[0].length != _countRadial)
            {
                _ptSetsShell = new stepb_cartesian_point[_countPeriodical * (_countCircumferential - 1)][_countRadial];
            }
        }
        else
        {
            if (_ptSetsShell == null ||
                _ptSetsShell.length != _countCircumferential ||
                _ptSetsShell[0].length != _countRadial)
            {
                _ptSetsShell = new stepb_cartesian_point[_countCircumferential][_countRadial];
            }
        }
        if (_isXYupdateNeed)
        {
            this.regenerateElementXY();
            _isXYupdateNeed = false;
        }
        this._elmementXY = new ElementTop(this, this.XYView, _colorBody);  //Roger 2012.08.28
    }


    /**
     *
     */
    public void regenerateElementXY()
    {

        System.out.println("regenerateElementXY");

        _point3DEndSection1.GetMathVector3d().SetX(this._elementPtXZEnd.X());
        _point3DEndSection1.GetMathVector3d().SetZ(this._elementPtXZEnd.Y());

        _point3DStartSection1.GetMathVector3d().SetX(this._elementPtXZStart.X());
        _point3DStartSection1.GetMathVector3d().SetZ(this._elementPtXZStart.Y());

        if (this._elementPtXYEnd != null
            && ((this._elementPtXYEnd.X() != _point3DEndSection1.X() || this._elementPtXYEnd.Y() != _point3DEndSection1.Y())
                || (this._elementPtXYStart.X() != _point3DStartSection1.X() || this._elementPtXYStart.Y() != _point3DStartSection1.Y()))
                )
        {
            this._elementPtXYEnd.setCoordinate(_point3DEndSection1.X(), _point3DEndSection1.Y());
            this._elementPtXYStart.setCoordinate(_point3DStartSection1.X(), _point3DStartSection1.Y());
        }
        else
        {
            _elementPtXYEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Y(), _colorPt, this);
            _elementPtXYStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Y(), _colorPt, this);
        }
        /***
         * Create Data Point Periodically or Circumferentially
         */

        if (this._isPerodical)
        {
            if (_elementPtXYCircumferentialRadial == null ||
                _elementPtXYCircumferentialRadial.length != _countPeriodical * (_countCircumferential - 1) ||
                _elementPtXYCircumferentialRadial[0].length != _countRadial)
            {
                _elementPtXYCircumferentialRadial = new ElementPoint[_countPeriodical * (_countCircumferential - 1)][_countRadial];
                this._highlightTopLineCircumfirential = new boolean[_countRadial][_countPeriodical * (_countCircumferential - 1)];
                this._highlightTopLineRadial = new boolean[_countPeriodical * (_countCircumferential - 1)][_countRadial];
            }
        }
        else
        {
            if (_elementPtXYCircumferentialRadial == null ||
                _elementPtXYCircumferentialRadial.length != _countCircumferential ||
                _elementPtXYCircumferentialRadial[0].length != _countRadial)
            {
                _elementPtXYCircumferentialRadial = new ElementPoint[_countCircumferential][_countRadial];
                this._highlightTopLineCircumfirential = new boolean[_countRadial][_countCircumferential];
                this._highlightTopLineRadial = new boolean[_countCircumferential][_countRadial];
            }
        }

        if (this._isPerodical)
        {
            this.createDataPointPeriodically(_ptSetsShell);

            for (int k = 0; k < _countPeriodical; k++)
            {
                for (int j = 0; j < _countCircumferential - 1; j++)
                {
                    for (int i = 0; i < _countRadial; i++)
                    {
                        //double x = ptSetsPeriodical[k][j][i].X(); 1314520
                        //double y = ptSetsPeriodical[k][j][i].Y();

                        double x = _ptSetsShell[k * (_countCircumferential -1) + j][i].X();
                        double y = _ptSetsShell[k * (_countCircumferential -1) + j][i].Y();
                        this._elementPtXYCircumferentialRadial[k * (_countCircumferential - 1) + j][i] = new ElementPoint(x, y, _colorPt, this);
                    }
                }
            }
        }
        else
        {
            if (!this._isRunway)
            {
                this.createDataPointCircumferential(_ptSetsShell);
            }
            else
            {
                if(this.detectedIsRoundRound())
                {
                    this.createDataPointCircumferential(_ptSetsShell);
                }
                else
                {
                    this.createDataPointCircumferentialRunway(_ptSetsShell);
                }
            }

            for (int j = 0; j < _countCircumferential; j++)
            {
                for (int i = 0; i < _countRadial; i++) //TempRoger  2;i++)//
                {
                                      double x = _ptSetsShell[j][i].X();
                    double y = _ptSetsShell[j][i].Y();
                    this._elementPtXYCircumferentialRadial[j][i] = new ElementPoint(x, y, _colorPt,this);
                }
            }
        }

    }

    protected abstract void createDataPointPeriodically(stepb_cartesian_point[][] ptSets);

    protected abstract void createDataPointCircumferential(stepb_cartesian_point[][] ptSets);

    protected abstract void createDataPointCircumferentialRunway(stepb_cartesian_point[][] ptSets);
}
