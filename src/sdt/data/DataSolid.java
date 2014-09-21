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

public abstract class DataSolid extends DataBase implements DefineSystemConstant
{
    protected ArrayCartesianPtSetsBrick      _arrayPtSets;

    public DataSolid(SDT_DataManager dataManager)
    {
        super(dataManager);
        _dataFamily = this.TYPE_FAMILY_SOLID;
    }

    protected abstract void setElementXYToData();

    protected abstract void setDataToElementXZ();

    protected abstract void setDataToElementYZ();



    /**
     * getPts of this Data
     *
     * @param index if index = -1, than retrun the geometry pts, perodically or
     *   circumfernetially, if index is greater than 0, it will returned the
     *   deformed point
     * @return stepb_cartesian_point[][] points to show
     */
    public ArrayCartesianPtSetsBrick getSolidPts(int index, double scale)
    {
        if (this._arrayPtSets == null)
        {
            setDataToElementXY();
        }
        if(index != -1)
        {
            /*stepb_cartesian_point_array displacementArray = this._dataManager.getDisplacementArray(index);
            displacementArray.rearrangeTable();

            int iiLength = _ptSetsSolid.length;
            int jjLength = _ptSetsSolid[0].length;
            int kkLength = _ptSetsSolid[0][0].length;
            stepb_cartesian_point[][][] deformedPtsets = new stepb_cartesian_point[iiLength][jjLength][kkLength];
            for (int ii = 0; ii < iiLength; ii++)
            {
                for (int jj = 0; jj < jjLength; jj++)
                {
                    for (int kk = 0; kk < kkLength; kk++)
                    {
                        int IDNumber = _ptSetsSolid[ii][jj][kk].GetIDNumber();
                        stepb_cartesian_point ptDisplacement = displacementArray.getByID(IDNumber);
                        math_vector3d v3d = null;
                        if (ptDisplacement != null)
                        {
                            v3d = _ptSetsSolid[ii][jj][kk].GetMathVector3d().Add(ptDisplacement.GetMathVector3d());
                            deformedPtsets[ii][jj][kk] = new stepb_cartesian_point(v3d);
                            deformedPtsets[ii][jj][kk].SetIDNumber(_ptSetsSolid[ii][jj][kk].GetIDNumber());
                        }
                        else
                        {
                            deformedPtsets[ii][jj][kk] = new stepb_cartesian_point(_ptSetsSolid[ii][jj][kk]);
                            deformedPtsets[ii][jj][kk].SetIDNumber(_ptSetsSolid[ii][jj][kk].GetIDNumber());
                        }
                    }
                }
            }*/

            ArrayCartesianPtSetsBrick arrayPtSetsDeformed = new ArrayCartesianPtSetsBrick();
            stepb_cartesian_point_array displacementArray = this._dataManager.getDisplacementArray(index);

            for (int i = 0; i < _arrayPtSets.Size(); i++)
            {
               CartesianPointSetsBrick ptSetsBrick = _arrayPtSets.get(i);
               CartesianPointSetsBrick ptSetsBrickDeformed = ptSetsBrick.setDisplacementArray(displacementArray, scale);
               arrayPtSetsDeformed.add(ptSetsBrickDeformed);
            }

            return arrayPtSetsDeformed;
        }
        return this._arrayPtSets;
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

        DataSurround dataSurround = (DataSurround)this._dataManager.getDataSurround();
        if(this._dataType == this.TYPE_AIR)
        {
            this._isPerodical = dataSurround._isPerodical;
            this._isRunway = dataSurround._isRunway;
        }

        if (this._isRunway && this._isPerodical)
        {
            _isPerodical = false;
            this._countCircumferential = this._countPeriodical * (this._countCircumferential - 1);
            this._countPeriodical = 1;
        }


        if (_isXYupdateNeed)
        {
            if (_arrayPtSets == null)
                _arrayPtSets = new ArrayCartesianPtSetsBrick();
            else
                _arrayPtSets.removeAll();


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

        if(this._dataType == this.TYPE_AIR)
        {
            if (this._isPerodical)
            {
                this.createDataPointPeriodically();
            }
            else
            {
                if (!this._isRunway)
                    this.createDataPointCircumferential();
                else
                {
                    if (this.detectedIsRoundRound())
                        this.createDataPointCircumferential();
                    else
                        this.createDataPointCircumferentialRunway();
                }
            }
            return;
        }


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
            if (_elementPtXYCircumferentialRadialThickness == null ||
                _elementPtXYCircumferentialRadialThickness.length != _countPeriodical * (_countCircumferential - 1) ||
                _elementPtXYCircumferentialRadialThickness[0].length != _countRadial  ||
                _elementPtXYCircumferentialRadialThickness[0][0].length != _countThickness)
            {
                _elementPtXYCircumferentialRadialThickness = new ElementPoint[_countPeriodical * (_countCircumferential - 1)][_countRadial][_countThickness];
            }
        }
        else
        {
            if (_elementPtXYCircumferentialRadialThickness == null ||
                _elementPtXYCircumferentialRadialThickness.length != _countCircumferential ||
                _elementPtXYCircumferentialRadialThickness[0].length != _countRadial||
                _elementPtXYCircumferentialRadialThickness[0][0].length != _countThickness)
            {
                _elementPtXYCircumferentialRadialThickness = new ElementPoint[_countCircumferential][_countRadial][_countThickness];
            }
        }

        if (this._isPerodical)
        {
            this.createDataPointPeriodically();//_ptSetsSolid);
            CartesianPointSetsBrick ptSetsBrick = this._arrayPtSets.get(0) ;
            stepb_cartesian_point[][][] ptSets = ptSetsBrick.getPtSets();
            for (int k = 0; k < _countPeriodical; k++)
            {
                for (int j = 0; j < _countCircumferential - 1; j++)
                {
                    for (int i = 0; i < _countRadial; i++)
                    {
                        for(int t = 0; t < _countThickness; t++)
                        {
                            double x = ptSets[k * (_countCircumferential - 1) + j][i][t].X();
                            double y = ptSets[k * (_countCircumferential - 1) + j][i][t].Y();
                            //if(t ==0)
                            this._elementPtXYCircumferentialRadialThickness[k * (_countCircumferential - 1) + j][i][t] = new ElementPoint(x, y, _colorPt, this);
                        }
                    }
                }
            }
        }
        else
        {
            if (!this._isRunway)
            {
                this.createDataPointCircumferential();//_ptSetsSolid);
            }
            else
            {
                if(this.detectedIsRoundRound())
                {
                    this.createDataPointCircumferential();//_ptSetsSolid);
                }
                else
                {
                    this.createDataPointCircumferentialRunway();//_ptSetsSolid);
                }
            }
            CartesianPointSetsBrick ptSetsBrick = this._arrayPtSets.get(0) ;
            stepb_cartesian_point[][][] ptSets = ptSetsBrick.getPtSets();
            for (int j = 0; j < _countCircumferential; j++)
            {
                for (int i = 0; i < _countRadial; i++)
                {
                    for (int t = 0; t < _countThickness; t++)
                    {
                        double x = ptSets[j][i][t].X();
                        double y = ptSets[j][i][t].Y();
                        //if(t ==0)
                            this._elementPtXYCircumferentialRadialThickness[j][i][t] = new ElementPoint(x, y, _colorPt, this);
                    }
                }
            }
        }

    }

    protected abstract void createDataPointPeriodically(); // stepb_cartesian_point[][][] ptSets);

    protected abstract void createDataPointCircumferential();//stepb_cartesian_point[][][] ptSets);

    protected abstract void createDataPointCircumferentialRunway();//stepb_cartesian_point[][][] ptSets);



}
