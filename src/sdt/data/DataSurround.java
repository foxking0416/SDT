package sdt.data;

import java.awt.*;
import java.io.*;

import sdt.define.*;
import sdt.geometry.element.*;
import sdt.stepb.*;

/***
 *
 * ttt
 * <pre>
 * 123
 *
 * <img src="http://10.35.7.80/Software/IDT/home/IDTDesignModel1.png"  align="center" class="border" width="220" height="146" />
 * 2334
 *  </pre>
 */
public class DataSurround extends DataShell
{

    double _radiusInXZ;
    double _radiusOutXZ;
    double _radiusSingleXZ;
    double _hTotalXZ;

    double _radiusInYZ;
    double _radiusOutYZ;
    double _radiusSingleYZ;
    double _hTotalYZ;
    boolean _ifSwitchSurroundTypeXZ;
    boolean _ifSwitchSurroundTypeYZ;
    boolean _ifSetFromDialogXZ;
    boolean _ifSetFromDialogYZ;


    ElementPoint _surroundTangentPt;
    ElementPoint _surroundInnerArcCenterPt;
    ElementPoint _surroundOuterArcCenterPt;



    public DataSurround(SDT_DataManager dataManager)
    {
        super(dataManager);

        _dataType = this.TYPE_SURROUND;
        _point3DStartSection1 = new stepb_cartesian_point(2.5, 0, 0.4);
        _point3DEndSection1 = new stepb_cartesian_point(3.75, 0, 0.0);
        _point3DStartSection2 = new stepb_cartesian_point(0, 2.5, 0.4);
        _point3DEndSection2 = new stepb_cartesian_point(0, 3.75, 0.0);

        _countGirth = dataManager.getCountGirth();
        _countRadial = 17;
        _countPeriodical = 16;
        _countCircumferential = _countGirth / _countPeriodical + 1; //9
        _thickness = 0.05;

        this._colorBody   = this._dataManager.getColorSurround();
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);

        this._radiusInXZ = 0.5;
        this._radiusOutXZ = 1.0;
        this._hTotalXZ = 0.7;

        this._radiusInYZ = 0.5;
        this._radiusOutYZ = 1.0;
        this._hTotalYZ = 0.7;

        this._radiusSingleXZ = 0.75;
        this._radiusSingleYZ = 0.75;

        this._geometryType = DefineSystemConstant.SURROUND_DOUBLE_ARC;


        this._ifSwitchSurroundTypeXZ = false;

        this._ifSetFromDialogXZ = false;
        this._ifSetFromDialogYZ = false;
        //this.getSurroundContour();

    }

    /**
     * 計算以兩個弧構成的surround的profile
     * @param surroundWidth surround的寬度
     * @param hTransition   surround_In 相對於 surround_Out的高度差; surround_In 高於 surround_Out為正
     * @param hTotal        surround最高點相對於 surround_Out的高度差, 必定為正
     * @param innerRadius   surround 內弧的半徑
     * @param outerRadius   surround 外弧的半徑
     * @return [0]: 切點相對於surround_In的X值
     * @return [1]: 切點相對於surround_Out的Y值
     * @return [2]: 內弧中心點相對於surround_In的X值
     * @return [3]: 內弧中心點相對於surround_Out的Y值
     * @return [4]: 外弧中心點相對於surround_In的X值
     * @return [5]: 外弧中心點相對於surround_Out的Y值
     */
    private double[] calculateSurround_DoubleArc(double surroundWidth, double hTransition, double hTotal, double innerRadius, double outerRadius)
    {
        double[] returnValue = new double[6];
        double defaultInnerCenterPtY;
        double defaultInnerCenterPtX;
        double a;
        double b;
        double tempJudgeEquation;
        double defaultOuterCenterPtY;
        double defaultOuterCenterPtX;
        double defaultTangentPtX;
        double defaultTangentPtY;
        double dx;
        double dy;
        double theta;
        double thetaOutCenterToSurroundOut;
        double thetaInCenterToSurroundIn;

        boolean judgeRule = true;

        if(hTransition < 0)//modify @ 2012_06_07 important
            hTotal = hTotal + hTransition;

        defaultInnerCenterPtY = hTotal - innerRadius;
        defaultInnerCenterPtX = Math.sqrt(Math.pow(innerRadius, 2) - Math.pow(hTransition, 2) - Math.pow(defaultInnerCenterPtY, 2) + 2 * hTransition * defaultInnerCenterPtY);

        a = (Math.pow(innerRadius, 2) - 2 * innerRadius * outerRadius - Math.pow(defaultInnerCenterPtX, 2) - Math.pow(defaultInnerCenterPtY, 2) + Math.pow(surroundWidth, 2)) /
            (2 * surroundWidth - 2 * defaultInnerCenterPtX);
        b = 2 * defaultInnerCenterPtY / (2 * surroundWidth - 2 * defaultInnerCenterPtX);
        tempJudgeEquation = Math.pow((2 * a * b - 2 * surroundWidth * b), 2) - 4 * (Math.pow(b, 2) + 1) * (Math.pow(surroundWidth - a, 2) - Math.pow(outerRadius, 2));

        if (innerRadius <= outerRadius)
        {
            defaultOuterCenterPtY = (2 * surroundWidth * b - 2 * a * b - Math.sqrt(tempJudgeEquation)) / (2 * (Math.pow(b, 2) + 1));
            defaultOuterCenterPtX = a + b * defaultOuterCenterPtY;
            dx = defaultInnerCenterPtX - defaultOuterCenterPtX;
            dy = defaultInnerCenterPtY - defaultOuterCenterPtY;

        }
        else
        {
            defaultOuterCenterPtY = (2 * surroundWidth * b - 2 * a * b + Math.sqrt(tempJudgeEquation)) / (2 * (Math.pow(b, 2) + 1));
            defaultOuterCenterPtX = a + b * defaultOuterCenterPtY;
            dx = defaultOuterCenterPtX - defaultInnerCenterPtX;
            dy = defaultOuterCenterPtY - defaultInnerCenterPtY;
        }

        theta = this.calculateTheta(dx, dy);

        thetaOutCenterToSurroundOut = this.calculateTheta((surroundWidth - defaultOuterCenterPtX), (0 - defaultOuterCenterPtY));
        thetaInCenterToSurroundIn = this.calculateTheta((0 - defaultInnerCenterPtX), (hTransition - defaultInnerCenterPtY));

        if (Math.pow(innerRadius, 2) - Math.pow(hTransition, 2) - Math.pow(defaultInnerCenterPtY, 2) + 2 * hTransition * defaultInnerCenterPtY < 0
            || tempJudgeEquation < 0 || theta > (Math.PI / 2.0)
                //|| thetaOutCenterToSurroundOut > (Math.PI / 2.0)
                //|| thetaInCenterToSurroundIn > Math.PI
                )
        {
            judgeRule = false;
        }

        if (judgeRule == false)
        {
            judgeRule = true;
            defaultOuterCenterPtY = hTotal - outerRadius;
            defaultOuterCenterPtX = surroundWidth - Math.sqrt(Math.pow(outerRadius, 2) - Math.pow(defaultOuterCenterPtY, 2));
            a = (Math.pow(defaultOuterCenterPtX, 2) + Math.pow(defaultOuterCenterPtY, 2) + 2 * innerRadius * outerRadius - Math.pow(outerRadius, 2) - Math.pow(hTransition, 2)) /
                (2 * defaultOuterCenterPtX);
            b = (hTransition - defaultOuterCenterPtY) / defaultOuterCenterPtX;
            tempJudgeEquation = Math.pow((a * b - hTransition), 2) - (b * b + 1) * (Math.pow(a, 2) + Math.pow(hTransition, 2) - Math.pow(innerRadius, 2));

            if (innerRadius <= outerRadius)
            {
                defaultInnerCenterPtY = ((hTransition - a * b) + Math.sqrt(tempJudgeEquation)) / (b * b + 1);
                defaultInnerCenterPtX = a + b * defaultInnerCenterPtY;
                dx = defaultInnerCenterPtX - defaultOuterCenterPtX;
                dy = defaultInnerCenterPtY - defaultOuterCenterPtY;
            }
            else
            {
                defaultInnerCenterPtY = ((hTransition - a * b) - Math.sqrt(tempJudgeEquation)) / (b * b + 1);
                defaultInnerCenterPtX = a + b * defaultInnerCenterPtY;
                dx = defaultOuterCenterPtX - defaultInnerCenterPtX;
                dy = defaultOuterCenterPtY - defaultInnerCenterPtY;
            }

            theta = this.calculateTheta(dx, dy);

            thetaOutCenterToSurroundOut = this.calculateTheta((surroundWidth - defaultOuterCenterPtX), (0 - defaultOuterCenterPtY));
            thetaInCenterToSurroundIn = this.calculateTheta((0 - defaultInnerCenterPtX), (hTransition - defaultInnerCenterPtY));

            //System.out.println("tangent outter");
            if (Math.pow(outerRadius, 2) - Math.pow(defaultOuterCenterPtY, 2) < 0
                || tempJudgeEquation < 0 || theta < (Math.PI / 2.0) || theta > Math.PI
                    //|| thetaOutCenterToSurroundOut > (Math.PI / 2.0)
                    //|| thetaInCenterToSurroundIn > Math.PI
                    )
            {
                judgeRule = false;
            }
        }

        defaultTangentPtX = defaultInnerCenterPtX + innerRadius * Math.cos(theta);
        defaultTangentPtY = defaultInnerCenterPtY + innerRadius * Math.sin(theta);

        returnValue[0] = defaultTangentPtX;
        returnValue[1] = defaultTangentPtY;
        returnValue[2] = defaultInnerCenterPtX;
        returnValue[3] = defaultInnerCenterPtY;
        returnValue[4] = defaultOuterCenterPtX;
        returnValue[5] = defaultOuterCenterPtY;

        return returnValue;
    }

    private double[] calculateSurround_SingleArc(double surroundWidth, double hTransition, double radius)
    {
        double[] returnValue = new double[6];

        double lengthIn2Out = Math.sqrt(Math.pow(surroundWidth,2) + Math.pow(hTransition,2));
        double theta = this.calculateAngle(radius, lengthIn2Out, radius);
        double angleIn2Out = this.calculateTheta(surroundWidth, -hTransition);
        double angleIn2Center = angleIn2Out - theta;
        double centerPtX = radius * Math.cos(angleIn2Center);
        double centerPtY = hTransition + radius * Math.sin(angleIn2Center);
        double fakeTangentPtX = surroundWidth;
        double fakeTangentPtY = 0;
        double fakeOuterCenterPtX = centerPtX;
        double fakeOuterCenterPtY = centerPtY;

        returnValue[0] = fakeTangentPtX;
        returnValue[1] = fakeTangentPtY;
        returnValue[2] = centerPtX;
        returnValue[3] = centerPtY;
        returnValue[4] = fakeOuterCenterPtX;
        returnValue[5] = fakeOuterCenterPtY;

        return returnValue;

    }

    protected void setElementXYToData()
    {}

    protected void setDataToElementXZ()
    {
        if (this._elementPtXZStart == null)
        {
            this._elementPtXZStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Z(), _colorPt, this);
        }
        if (this._elementPtXZEnd == null)
        {
            this._elementPtXZEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Z(), _colorPt, this);
        }
        this._elmementXZ = new ElementSurround(this, this.XZView, _colorBody);
        this._isXYupdateNeed = true;
    }

    protected void setDataToElementYZ()
    {
        if (this._elementPtYZStart == null)
        {
            this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt, this);
        }
        if (this._elementPtYZEnd == null)
        {
            this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt, this);
        }
        this._elmementYZ = new ElementSurround(this, this.YZView, _colorBody);
        this._isXYupdateNeed = true;
    }


    /**
     * 根據不同截面獲得Double_Arc的內圓弧半徑
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @return Double_Arc的內圓弧半徑
     */
    public double getSurroundDoubleArcInnerRadius(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._radiusInXZ;

            case DefineSystemConstant.YZView:
                return this._radiusInYZ;
            default:
                return -1;
        }
    }

    /**
     * 根據不同截面獲得Double_Arc的外圓弧半徑
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @return Double_Arc的外圓弧半徑
     */
    public double getSurroundDoubleArcOuterRadius(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._radiusOutXZ;

            case DefineSystemConstant.YZView:
                return this._radiusOutYZ;
            default:
                return -1;
        }
    }

    /**
     * 根據不同截面獲得Single_Arc的外圓弧半徑
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @return Single_Arc的圓弧半徑
     */
    public double getSurroundSingleArcRadius(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._radiusSingleXZ;

            case DefineSystemConstant.YZView:
                return this._radiusSingleYZ;
            default:
                return -1;
        }
    }

    public double getSurroundHeight(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._hTotalXZ;

            case DefineSystemConstant.YZView:
                return this._hTotalYZ;
            default:
                return -1;
        }
    }

    /**
     * 根據不同截面設定Double_Arc Surround的高, 內圓弧半徑, 外圓弧半徑
     * @param sectionNumber  參考DefineSystemConstant.XZView, YZView,.XYView
     * @param height surround高
     * @param innerR Double_Arc surround內圓弧的半徑
     * @param outerR Double_Arc surround外圓弧的半徑
     */
    public void setSurroundHeightInOutRadius(int sectionNumber, double height, double innerR, double outerR)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._radiusInXZ = innerR;
                this._radiusOutXZ = outerR;
                break;
            case DefineSystemConstant.YZView:
                this._radiusInYZ = innerR;
                this._radiusOutYZ = outerR;
                break;
        }
        this._hTotalXZ = height;
        this._hTotalYZ = height;

    }


    /**
     * 根據不同截面設定Single_Arc Surround的圓弧半徑
     * @param sectionNumber 參考DefineSystemConstant.XZView, YZView,.XYView
     * @param radius Surround的圓弧半徑
     */
    public void setSurroundSingleArcRadius(int sectionNumber, double radius)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._radiusSingleXZ = radius;
                break;
            case DefineSystemConstant.YZView:
                this._radiusSingleYZ = radius;
                break;
        }
    }

    public void setSurroundHeight(double height)
    {
        this._hTotalXZ = height;
        this._hTotalYZ = height;
    }

    /***
     * <pre>
     * 用來產生Periodical Mesh, 其中 corrugation 也只需要指定其中1個Unit
     *
     * <br>
     * Skewed Angle的計算方式
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/SkewedAngleComputation.png"  align="center" class="border" width="487" height="276" />
     * </pre>
     * @param ptSets 利用Call by address 的方式將 ptSets的計算結果傳出
     *
     */
    protected void createDataPointPeriodically(stepb_cartesian_point[][] ptSets)
    {
        double hTransition = this._elementPtXZStart.Y() - this._elementPtXZEnd.Y();
        double surroundWidth = this._elementPtXZEnd.X() - this._elementPtXZStart.X();

        double[] contourParrameter;// = this.calculateSurround(surroundWidth, hTransition, this._hTotalXZ, this._radiusInXZ, this._radiusOutXZ);

        if(this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
            contourParrameter = this.calculateSurround_SingleArc(surroundWidth, hTransition, this._radiusSingleXZ);
        else
            contourParrameter = this.calculateSurround_DoubleArc(surroundWidth, hTransition, this._hTotalXZ, this._radiusInXZ, this._radiusOutXZ);

        double tangentPtX = contourParrameter[0] + this._elementPtXZStart.X();
        double tangentPtZ = contourParrameter[1] + this._elementPtXZEnd.Y();
        double innerCenterX = contourParrameter[2] + this._elementPtXZStart.X();
        double innerCenterZ = contourParrameter[3] + this._elementPtXZEnd.Y();
        double outerCenterX = contourParrameter[4] + this._elementPtXZStart.X();
        double outerCenterZ = contourParrameter[5] + this._elementPtXZEnd.Y();

        int iiCount = this._countRadial; //ptSets[0][0].length;
        int jCount = this._countCircumferential; //ptSets[0].length;
        int kCount = this._countPeriodical; //ptSets.length;

        double lengthA = Math.sqrt(Math.pow(this._point3DStartSection1.X() - tangentPtX, 2) + Math.pow(this._point3DStartSection1.Z() - tangentPtZ, 2));
        double lengthB = Math.sqrt(Math.pow(this._point3DEndSection1.X() - tangentPtX, 2) + Math.pow(this._point3DEndSection1.Z() - tangentPtZ, 2));
        double thetaAllIn = this.calculateAngle(lengthA, this._radiusInXZ, this._radiusInXZ);
        double thetaAllOut = this.calculateAngle(lengthB, this._radiusOutXZ, this._radiusOutXZ);

        double arcLengthIn = thetaAllIn * this._radiusInXZ;
        double arcLengthOut = thetaAllOut * this._radiusOutXZ;

        if (this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
        {
            thetaAllIn = this.calculateAngle(lengthA, this._radiusSingleXZ, this._radiusSingleXZ);
            thetaAllOut = 0;
            arcLengthIn = thetaAllIn * this._radiusSingleXZ;
            arcLengthOut = thetaAllOut * this._radiusSingleXZ;
        }


        double arcLengthDelta = (arcLengthIn + arcLengthOut) / (iiCount - 1);
        double arcLengthIncrement;




        double thetaStart = this.calculateTheta(this._elementPtXZStart.X() - innerCenterX, this._elementPtXZStart.Y() - innerCenterZ);
        double thetaIncrement;
        double thetaZ;

        double r = 0;
        double z = 0;
        double theta = 0;
        double[] thetaJStart = new double[iiCount];
        double thetaIntervalK = 2 * Math.PI / kCount;
        double thetaIntervalJ = 2 * Math.PI / kCount / (jCount - 1);

        for (int k = 0; k < kCount; k++)
        {

            for (int j = 0; j < jCount - 1; j++)
            {

                double thetaIStartJStart = 0;
                double thetaIStartJEnd = 0;
                double thetaCriticalJStart = 0;
                double thetaCriticalJEnd = 0;
                double thetaIEndJStart = 0;
                double thetaIEndJEnd = 0;
                double thetaSToC = 0;
                double thetaCToE = 0;
                double thetaIncreSToC = 0;
                double thetaIncreCToE = 0;

                double oldTheta = 0;

                theta = k * thetaIntervalK + j * thetaIntervalJ;
                double thetaIntervalJTemp = thetaIntervalJ;

                if (_isBiasOn)
                    thetaIntervalJTemp *= 1.5;


                if(this.isJStartPrevious(j))
                {
                    if (_isBiasOn)
                    {
                        theta -= thetaIntervalJ / 2.0;
                    }
                }
                //else if (j == jBiasEnd)

                if(this.isJEndNext(j))
                {
                    if (_isBiasOn)
                    {
                        theta += thetaIntervalJ / 2.0;
                    }
                }

                for (int ii = 0; ii < iiCount; ii++)
                {
                    arcLengthIncrement = arcLengthDelta * ii;

                    if (this._geometryType == DefineSystemConstant.SURROUND_DOUBLE_ARC)
                    {
                        if (arcLengthIncrement <= arcLengthIn)
                        {
                            thetaIncrement = arcLengthIncrement / this._radiusInXZ;

                            thetaZ = thetaStart - thetaIncrement;

                            r = innerCenterX + this._radiusInXZ * Math.cos(thetaZ);
                            z = innerCenterZ + this._radiusInXZ * Math.sin(thetaZ);
                        }
                        else
                        {
                            thetaIncrement = thetaAllIn + (arcLengthIncrement - arcLengthIn) / this._radiusOutXZ;

                            thetaZ = thetaStart - thetaIncrement;

                            r = outerCenterX + this._radiusOutXZ * Math.cos(thetaZ);
                            z = outerCenterZ + this._radiusOutXZ * Math.sin(thetaZ);
                        }
                    }
                    else if (this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
                    {
                        thetaIncrement = arcLengthIncrement / this._radiusSingleXZ;

                        thetaZ = thetaStart - thetaIncrement;

                        r = innerCenterX + this._radiusSingleXZ * Math.cos(thetaZ);
                        z = innerCenterZ + this._radiusSingleXZ * Math.sin(thetaZ);

                    }

                    if (_skewedOn)
                    {

                        double skewedTheta = Math.PI * _skewedAngle / 180.0; //12.0* 5.0;  // 75 deg
                        double diffTheta = 0;

                        diffTheta = skewedTheta - Math.asin((this._elementPtXZStart.X() / r) * Math.sin(Math.PI - skewedTheta));

                        if (ii == 0)
                        {
                            oldTheta = diffTheta;
                        }

                        theta += (diffTheta - oldTheta); //(3 * Math.PI / 180.0);
                        oldTheta = diffTheta;
                        if (theta < 0)
                        {
                            theta += 2 * Math.PI;
                        }
                        else if (theta > Math.PI * 2)
                        {
                            theta -= 2 * Math.PI;
                        }




                    }

                    if (this.isJStart(j))
                    {
                        thetaIStartJStart = theta + thetaIntervalJ * this._corrugationRatioJStartIStart;
                        thetaCriticalJStart = theta - thetaIntervalJTemp * this._corrugationRatioJStartICritical;
                        thetaIEndJStart = theta + thetaIntervalJ * this._corrugationRatioJStartIEnd;
                        thetaIncreSToC = (thetaCriticalJStart - thetaIStartJStart) / (_corrugationJStartICritical - _corrugationJStartIStart);
                        thetaIncreCToE = (thetaIEndJStart - thetaCriticalJStart) / (_corrugationJStartIEnd - _corrugationJStartICritical);
                        thetaSToC = thetaCriticalJStart - thetaIStartJStart;
                        thetaCToE = thetaIEndJStart - thetaCriticalJStart;

                        if (ii == _corrugationJStartIStart)
                        {
                            theta = thetaIStartJStart;
                        }
                        else if (ii > _corrugationJStartIStart && ii <= _corrugationJStartICritical)
                        {

                            double a = Math.PI / 2.0 * (ii - _corrugationJStartIStart) / (_corrugationJStartICritical - _corrugationJStartIStart);
                            double aPre = Math.PI / 2.0 * (ii - _corrugationJStartIStart - 1) / (_corrugationJStartICritical - _corrugationJStartIStart);
                            //System.out.println("a ====== " + a);
                            //System.out.println("aPre === " + aPre);
                            theta += (thetaSToC * (Math.sin(a) - Math.sin(aPre)));
                            System.out.println("thetaIStart ========" + thetaIStartJStart);
                            System.out.println("thetaICritical =====" + thetaCriticalJStart);
                            System.out.println("thetaIEnd ==========" + thetaIEndJStart);
                            System.out.println("thetaSToC runway ===" + thetaSToC);
                            System.out.println("thetaCToE runway ===" + thetaCToE);
                            System.out.println("");
                            //System.out.println("Math.sin(a) - Math.sin(aPre)" + (Math.sin(a) - Math.sin(aPre)));
                            if (theta < 0)
                            {
                                theta += 2 * Math.PI;
                            }
                        }
                        else if (ii > _corrugationJStartICritical && ii <= _corrugationJStartIEnd)
                        {
                            double a = Math.PI / 2.0 + Math.PI / 2.0 * (ii - _corrugationJStartICritical) / (_corrugationJStartIEnd - _corrugationJStartICritical);
                            double aPre = Math.PI / 2.0 + Math.PI / 2.0 * (ii - _corrugationJStartICritical - 1) / (_corrugationJStartIEnd - _corrugationJStartICritical);

                            theta -= thetaCToE * (Math.sin(a) - Math.sin(aPre));
                            if (theta > 2 * Math.PI)
                            {
                                theta -= 2 * Math.PI;
                            }
                        }
                        else if (ii == _corrugationJStartIEnd)
                        {
                            theta = thetaIEndJStart;
                        }

                        thetaJStart[ii] = theta;
                        //System.out.println("theta: (" + ii + ") " + theta);
                    }

                    if (this.isJEnd(j))
                    {
                        thetaIStartJEnd = theta - thetaIntervalJ * this._corrugationRatioJEndIStart;
                        thetaCriticalJEnd = theta + thetaIntervalJTemp * this._corrugationRatioJEndICritical;
                        thetaIEndJEnd = theta - thetaIntervalJ * this._corrugationRatioJEndIEnd;
                        thetaIncreSToC = (thetaCriticalJEnd - thetaIStartJEnd) / (_corrugationJStartICritical - _corrugationJStartIEnd);
                        thetaIncreCToE = (thetaIEndJEnd - thetaCriticalJEnd) / (_corrugationJStartIEnd - _corrugationJStartICritical);
                        thetaSToC = thetaCriticalJEnd - thetaIStartJEnd;
                        thetaCToE = thetaIEndJEnd - thetaCriticalJEnd;


                        if (ii == _corrugationJEndIStart)
                        {
                            theta = thetaIStartJEnd;

                        }
                        else if (ii > _corrugationJEndIStart && ii <= _corrugationJEndICritical)
                        {

                            double a = Math.PI / 2.0 * (ii - _corrugationJEndIStart) / (_corrugationJEndICritical - _corrugationJEndIStart);
                            double aPre = Math.PI / 2.0 * (ii - _corrugationJEndIStart - 1) / (_corrugationJEndICritical - _corrugationJEndIStart);
                            theta += (thetaSToC * (Math.sin(a) - Math.sin(aPre)));

                            if (theta < 0)
                            {
                                theta += 2 * Math.PI;
                            }
                        }

                        else if (ii > _corrugationJEndICritical && ii <= _corrugationJEndIEnd)
                        {
                            double a = Math.PI / 2.0 + Math.PI / 2.0 * (ii - _corrugationJEndICritical) / (_corrugationJEndIEnd - _corrugationJEndICritical);
                            double aPre = Math.PI / 2.0 + Math.PI / 2.0 * (ii - _corrugationJEndICritical - 1) / (_corrugationJEndIEnd - _corrugationJEndICritical);

                            theta -= thetaCToE * (Math.sin(a) - Math.sin(aPre));
                            if (theta > 2 * Math.PI)
                            {
                                theta -= 2 * Math.PI;
                            }
                        }
                        else if (ii == _corrugationJEndIEnd)
                        {
                            theta = thetaIEndJEnd;
                        }
                    }

                    double x = r * Math.cos(theta);
                    double y = r * Math.sin(theta);

                    double xPrevious = r * Math.cos(thetaJStart[ii]);
                    double yPrevious = r * Math.sin(thetaJStart[ii]);

                    //change the deep of center of corrugation
                    if (this.isJCritical(j))
                    {
                        if (ii >= _corrugationJStartIStart && ii <= _corrugationJStartIEnd)
                        {
                            double distance = Math.sqrt(Math.pow(xPrevious - x, 2) + Math.pow(yPrevious - y, 2));
                            distance =  distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI );
                            System.out.println("_corrugationFormingAngle: " + _corrugationFormingAngle);

                            z -= distance;
                        }
                    }

                    //只針對在j=0的位置有corrugation的特別計算

                    if (j == 1 && this.isJCritical(0) && k==0)
                    {
                        stepb_cartesian_point ptJZeroArray[] = ptSets[k * (_countCircumferential - 1)+ 0];

                        if (ii >= this._corrugationJStartIStart && ii <= this._corrugationJStartIEnd)
                        {
                            stepb_cartesian_point jZeroCartesianPoint = ptJZeroArray[ii];

                            double distance = Math.pow(ptJZeroArray[ii].X() - x, 2) + Math.pow(ptJZeroArray[ii].Y() - y, 2);
                            distance = Math.sqrt(distance);
                            distance =  distance / Math.tan(this._corrugationFormingAngle /360.0 * Math.PI );
                            double newZ = jZeroCartesianPoint.Z() - distance;
                            stepb_cartesian_point newCartesianPt = new stepb_cartesian_point(jZeroCartesianPoint.X(), jZeroCartesianPoint.Y(), newZ);
                            ptJZeroArray[ii] = newCartesianPt;
                            //ptJZeroArray[ii].GetMathVector3d().SetZ(newZ);
                        }
                    }

                    if (ptSets[k * (_countCircumferential - 1) + j][ii] == null)
                    {
                        stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                        ptSets[k * (_countCircumferential - 1) + j][ii] = cPt;
                    }
                    else
                    {
                        ptSets[k * (_countCircumferential - 1) + j][ii].GetMathVector3d().Set(x, y, z);
                    }
                }
            }
        }

    }

    protected void createDataPointCircumferential(stepb_cartesian_point[][] ptSets)
    {
        double hTransition = this._elementPtXZStart.Y() - this._elementPtXZEnd.Y();
        double surroundWidth = this._elementPtXZEnd.X() - this._elementPtXZStart.X();


        double[] contourParrameter;
        if(this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
            contourParrameter = this.calculateSurround_SingleArc(surroundWidth, hTransition, this._radiusSingleXZ);
        else
            contourParrameter = this.calculateSurround_DoubleArc(surroundWidth, hTransition, this._hTotalXZ, this._radiusInXZ, this._radiusOutXZ);




        double tangentPtX = contourParrameter[0] + this._elementPtXZStart.X();
        double tangentPtZ = contourParrameter[1] + this._elementPtXZEnd.Y();
        double innerCenterX = contourParrameter[2] + this._elementPtXZStart.X();
        double innerCenterZ = contourParrameter[3] + this._elementPtXZEnd.Y();
        double outerCenterX = contourParrameter[4] + this._elementPtXZStart.X();
        double outerCenterZ = contourParrameter[5] + this._elementPtXZEnd.Y();

        int iiCount = this._countRadial; //ptSets[0].length;
        int jCount = this._countCircumferential; //ptSets.length;

        double lengthA = Math.sqrt(Math.pow(this._point3DStartSection1.X() - tangentPtX, 2) + Math.pow(this._point3DStartSection1.Z() - tangentPtZ, 2));
        double lengthB = Math.sqrt(Math.pow(this._point3DEndSection1.X() - tangentPtX, 2) + Math.pow(this._point3DEndSection1.Z() - tangentPtZ, 2));
        double thetaAllIn = this.calculateAngle(lengthA, this._radiusInXZ, this._radiusInXZ);
        double thetaAllOut = this.calculateAngle(lengthB, this._radiusOutXZ, this._radiusOutXZ);

        double arcLengthIn = thetaAllIn * this._radiusInXZ;
        double arcLengthOut = thetaAllOut * this._radiusOutXZ;

        if (this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
        {
            thetaAllIn = this.calculateAngle(lengthA, this._radiusSingleXZ, this._radiusSingleXZ);
            thetaAllOut = 0;
            arcLengthIn = thetaAllIn * this._radiusSingleXZ;
            arcLengthOut = thetaAllOut * this._radiusSingleXZ;
        }


        double arcLengthDelta = (arcLengthIn + arcLengthOut) / (iiCount - 1);
        double arcLengthIncrement;

        double thetaStart = this.calculateTheta(this._elementPtXZStart.X() - innerCenterX, this._elementPtXZStart.Y() - innerCenterZ);
        double thetaIncrement;
        double thetaZ;

        double r = 0;
        double z = 0;
        double theta = 0;
        double[] thetaJStart = new double[iiCount]; //record the corrugation theta variation for the deep computation
        double thetaIntervalJ = 2 * Math.PI / jCount;

        for (int j = 0; j < jCount; j++)
        {

            double thetaIStartJStart = 0;
            double thetaIStartJEnd = 0;
            double thetaCriticalJStart = 0;
            double thetaCriticalJEnd = 0;
            double thetaIEndJStart = 0;
            double thetaIEndJEnd = 0;
            double thetaSToC = 0;
            double thetaCToE = 0;
            double thetaIncreSToC = 0;
            double thetaIncreCToE = 0;

            double oldTheta = 0;

            theta = j * thetaIntervalJ;
            double thetaIntervalJTemp = thetaIntervalJ;
            if (_isBiasOn)
                thetaIntervalJTemp *= 1.5;

            if (this.isJStartPrevious(j))
            {
                if (_isBiasOn)
                {
                    theta -= thetaIntervalJ / 2.0;
                }
            }
            else if (this.isJEndNext(j))
            {
                if (_isBiasOn)
                {
                    theta += thetaIntervalJ / 2.0;
                }
            }

            for (int ii = 0; ii < iiCount; ii++)
            {
                arcLengthIncrement = arcLengthDelta * ii;

                if (this._geometryType == DefineSystemConstant.SURROUND_DOUBLE_ARC)
                {
                    if (arcLengthIncrement <= arcLengthIn)
                    {
                        thetaIncrement = arcLengthIncrement / this._radiusInXZ;

                        thetaZ = thetaStart - thetaIncrement;

                        r = innerCenterX + this._radiusInXZ * Math.cos(thetaZ);
                        z = innerCenterZ + this._radiusInXZ * Math.sin(thetaZ);
                    }
                    else
                    {
                        thetaIncrement = thetaAllIn + (arcLengthIncrement - arcLengthIn) / this._radiusOutXZ;

                        thetaZ = thetaStart - thetaIncrement;

                        r = outerCenterX + this._radiusOutXZ * Math.cos(thetaZ);
                        z = outerCenterZ + this._radiusOutXZ * Math.sin(thetaZ);
                    }
                }
                else if (this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
                {
                    thetaIncrement = arcLengthIncrement / this._radiusSingleXZ;

                    thetaZ = thetaStart - thetaIncrement;

                    r = innerCenterX + this._radiusSingleXZ * Math.cos(thetaZ);
                    z = innerCenterZ + this._radiusSingleXZ * Math.sin(thetaZ);
                }
                if (_skewedOn)
                {

                    double skewedTheta = Math.PI * _skewedAngle / 180.0; //12.0* 5.0;  // 75 deg
                    double diffTheta = 0;
                    diffTheta = skewedTheta - Math.asin((this._elementPtXZStart.X() / r) * Math.sin(Math.PI - skewedTheta));

                    if (ii == 0)
                    {
                        oldTheta = diffTheta;
                    }

                    theta += (diffTheta - oldTheta); //(3 * Math.PI / 180.0);
                    oldTheta = diffTheta;
                    if (theta < 0)
                    {
                        theta += 2 * Math.PI;
                    }
                    else if (theta > Math.PI * 2)
                    {
                        theta -= 2 * Math.PI;
                    }
                }
                if (this.isJStart(j))
                {
                    thetaIStartJStart = theta + thetaIntervalJ * this._corrugationRatioJStartIStart;
                    thetaCriticalJStart = theta - thetaIntervalJTemp * this._corrugationRatioJStartICritical;
                    thetaIEndJStart = theta + thetaIntervalJ * this._corrugationRatioJStartIEnd;
                    thetaSToC = thetaCriticalJStart - thetaIStartJStart;
                    thetaCToE = thetaIEndJStart - thetaCriticalJStart;

                    if (ii == _corrugationJStartIStart)
                    {
                        theta = thetaIStartJStart;
                    }
                    else if (ii > _corrugationJStartIStart && ii <= _corrugationJStartICritical)
                    {
                        double a = Math.PI / 2.0 * (ii - _corrugationJStartIStart) / (_corrugationJStartICritical - _corrugationJStartIStart);
                        double aPre = Math.PI / 2.0 * (ii - _corrugationJStartIStart - 1) / (_corrugationJStartICritical - _corrugationJStartIStart);
                        theta += (thetaSToC * (Math.sin(a) - Math.sin(aPre)));

                        if (theta < 0)
                        {
                            theta += 2 * Math.PI;
                        }
                    }


                    else if (ii > _corrugationJStartICritical && ii <=_corrugationJStartIEnd)
                    {
                        double a = Math.PI / 2.0 + Math.PI / 2.0 * (ii - _corrugationJStartICritical) / (_corrugationJStartIEnd - _corrugationJStartICritical);
                        double aPre = Math.PI / 2.0 + Math.PI / 2.0 * (ii - _corrugationJStartICritical - 1) / (_corrugationJStartIEnd - _corrugationJStartICritical);

                        theta -= thetaCToE * (Math.sin(a) - Math.sin(aPre));
                        if (theta > 2 * Math.PI)
                        {
                            theta -= 2 * Math.PI;
                        }
                    }

                    thetaJStart[ii] = theta;
                }

                if (this.isJEnd(j))
                {
                    thetaIStartJEnd = theta - thetaIntervalJ * this._corrugationRatioJEndIStart;
                    thetaCriticalJEnd = theta + thetaIntervalJTemp * this._corrugationRatioJEndICritical;
                    thetaIEndJEnd = theta - thetaIntervalJ * this._corrugationRatioJEndIEnd;
                    thetaSToC = thetaCriticalJEnd - thetaIStartJEnd;
                    thetaCToE = thetaIEndJEnd - thetaCriticalJEnd;

                    if (ii == _corrugationJEndIStart)
                    {
                        theta = thetaIStartJEnd;

                    }

                    else if (ii > _corrugationJEndIStart && ii <= _corrugationJEndICritical)
                    {

                        double a = Math.PI / 2.0 * (ii - _corrugationJEndIStart) / (_corrugationJEndICritical - _corrugationJEndIStart);
                        double aPre = Math.PI / 2.0 * (ii - _corrugationJEndIStart - 1) / (_corrugationJEndICritical - _corrugationJEndIStart);
                        theta += (thetaSToC * (Math.sin(a) - Math.sin(aPre)));

                        if (theta < 0)
                        {
                            theta += 2 * Math.PI;
                        }
                    }


                    else if (ii > _corrugationJEndICritical && ii <= _corrugationJEndIEnd)
                    {
                        double a = Math.PI / 2.0 + Math.PI / 2.0 * (ii - _corrugationJEndICritical) / (_corrugationJEndIEnd - _corrugationJEndICritical);
                        double aPre = Math.PI / 2.0 + Math.PI / 2.0 * (ii - _corrugationJEndICritical - 1) / (_corrugationJEndIEnd - _corrugationJEndICritical);

                        theta -= thetaCToE * (Math.sin(a) - Math.sin(aPre));
                        if (theta > 2 * Math.PI)
                        {
                            theta -= 2 * Math.PI;
                        }
                    }


                }
                double x = r * Math.cos(theta);
                double y = r * Math.sin(theta);
                double xPrevious = r * Math.cos(thetaJStart[ii]);
                double yPrevious = r * Math.sin(thetaJStart[ii]);

                //int nodeNumber = startNodeNumber + loopIncrement * ii + j + (jCount - 1) * k;
                if (j != 0 && this.isJCritical(j))//把在j=0的位置有corrugation排除掉
                {
                    if (ii >= _corrugationJStartIStart && ii <= _corrugationJStartIEnd)
                    {
                        double distance = Math.sqrt(Math.pow(xPrevious - x, 2) + Math.pow(yPrevious - y, 2));
                        distance =  distance / Math.tan(this._corrugationFormingAngle /360.0 * Math.PI );
                        z -= distance;
                    }
                }

                //只針對在j=0的位置有corrugation的特別計算
                if (j == 1 && this.isJCritical(0))
                {
                    stepb_cartesian_point ptJZeroArray[] = ptSets[0];

                    if (ii >= this._corrugationJStartIStart && ii <= this._corrugationJStartIEnd)
                    {
                        stepb_cartesian_point jZeroCartesianPoint = ptJZeroArray[ii];

                        double distance = Math.pow(ptJZeroArray[ii].X() - x, 2) + Math.pow(ptJZeroArray[ii].Y() - y, 2);
                        distance = Math.sqrt(distance);
                        distance =  distance / Math.tan(this._corrugationFormingAngle /360.0 * Math.PI );
                        double newZ = jZeroCartesianPoint.Z() - distance;
                        stepb_cartesian_point newCartesianPt = new stepb_cartesian_point(jZeroCartesianPoint.X(), jZeroCartesianPoint.Y(), newZ);
                        ptJZeroArray[ii] = newCartesianPt;
                    }
                }

                if (ptSets[j][ii] == null)
                {
                    stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                    ptSets[j][ii] = cPt;
                }
                else
                {
                    //stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                    ptSets[j][ii].GetMathVector3d().Set(x, y, z);

                }
            }
        }

    }

    protected void createDataPointCircumferentialRunway(stepb_cartesian_point[][] ptSetsCircumferential)
    {

        int iCount = ptSetsCircumferential[0].length;
        int jCount = ptSetsCircumferential.length;

        double criticalAngInner;
        double criticalAngOuter;

        if (this._point3DStartSection1.X() - this._point3DStartSection2.Y() != 0)
            criticalAngInner = Math.atan(this._point3DStartSection2.Y() / Math.abs(this._point3DStartSection1.X() - this._point3DStartSection2.Y()));
        else
            criticalAngInner = Math.PI / 2.0;

        if (this._point3DEndSection1.X() - this._point3DEndSection2.Y() != 0)
            criticalAngOuter = Math.atan(this._point3DEndSection2.Y() / Math.abs(this._point3DEndSection1.X() - this._point3DEndSection2.Y()));
        else
            criticalAngOuter = Math.PI / 2.0;

        this.detectedIsRoundRunway();

        if (this._isRoundRunway) //Inner is round
        {
            this.setCircumferentialNumber(false, true);

            this.setRoundInner(); //elementArrayInner is done
            System.out.println("Inner is round.");
        }
        else //Inner is runway
        {
            this.setCircumferentialNumber(false, (criticalAngInner > criticalAngOuter));

            this.setRunwayInner();
            System.out.println("Inner is runway.");
        }

        this.setRunwayOuter();

        double hTransition = this._point3DStartSection1.Z() - this._point3DEndSection1.Z();

        //////////////////////////////////////////////////////////////////*

         ElementPoint sectionYZStartPt = new ElementPoint(0, this._point3DStartSection2.Y(), Color.WHITE, this);
        ElementPoint sectionYZEndPt = new ElementPoint(0, this._point3DEndSection2.Y(), Color.WHITE, this);

        stepb_cartesian_point sectionYZPtsArray[] = this.setLinePoint3D_RunwayFan(sectionYZStartPt, sectionYZEndPt,
                hTransition, this._hTotalXZ,
                this._radiusInYZ, this._radiusOutYZ, this._radiusSingleYZ,
                iCount, 0, this._point3DStartSection1.Z(), null);

        ElementPoint sectionXZStartPt = new ElementPoint(this._point3DStartSection1.X(), 0, Color.WHITE, this);
        ElementPoint sectionXZEndPt = new ElementPoint(this._point3DEndSection1.X(), 0, Color.WHITE, this);

        stepb_cartesian_point sectionXZPtsArray[] = this.setLinePoint3D_RunwayFan(sectionXZStartPt, sectionXZEndPt,
                hTransition, this._hTotalXZ,
                this._radiusInXZ, this._radiusOutXZ, this._radiusSingleXZ,
                iCount, 0, this._point3DStartSection1.Z(), null);

        ///////////////
        double[] YZPositionScale = new double[sectionYZPtsArray.length];
        for(int i = 0; i < sectionYZPtsArray.length; i++)
        {
            YZPositionScale[i] = (sectionYZPtsArray[i].Y() - sectionYZPtsArray[0].Y()) / (sectionYZPtsArray[sectionYZPtsArray.length - 1].Y() - sectionYZPtsArray[0].Y());
        }

        double[] newXZPtHeight = this.sectionXZNewHeight(sectionXZStartPt, sectionXZEndPt, hTransition, this._hTotalXZ,
                               this._radiusInXZ, this._radiusOutXZ, this._radiusSingleXZ, this._point3DStartSection1.Z(), YZPositionScale);

        ///////////////


        if (this._isRoundRunway) //內圓外跑道
        {
            this.innerRoundSweep(jCount, iCount, sectionXZPtsArray, sectionYZPtsArray, newXZPtHeight);
        }
        else //內外皆跑道
        {
            this.innerRunwaySweep(jCount, iCount, sectionXZPtsArray, sectionYZPtsArray, newXZPtHeight);
        }
    }

    /***
     * Used to sweep the surround profile whose inner is round type.
     * @param iCount Total node numbers of surround on radial
     * @param jCount Total node numbers of surround on circumferential
     * @param sectionXZPtsArray Cartesian points array of surround profile on XZ_section
     * @param sectionYZPtsArray Cartesian points array of surround profile on YZ_section
     * @param newXZPtHeight 根據YZ方向的Y分布，重新計算XZ方向的Z高
     */
    private void innerRoundSweep(int jCount, int iCount,
                                 stepb_cartesian_point[] sectionXZPtsArray, stepb_cartesian_point[] sectionYZPtsArray,
                                 double[] newXZPtHeight)

    {
        double outerCriticalWidth = this._point3DEndSection1.X() - this._point3DEndSection2.Y();

        //////////1st area
        for (int j = 0; j < this._numberFan / 2; j++)
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];

            for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);


                //stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                //double sectionXZ_X = cartesianPtXZ.X();
                double sectionXZ_Y = newXZPtHeight[i];
                //double sectionXZ_Y = cartesianPtXZ.Z();
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];
                double h = cartesianPtYZ.Y();
                double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());



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

                double thetaModify = this.modifyAngle(theta);

                if (thetaModify > Math.PI / 2 && thetaModify <= Math.PI)
                    thetaModify = Math.PI - thetaModify;
                else if (thetaModify > Math.PI && thetaModify <= Math.PI * 3 / 2.0)
                    thetaModify = thetaModify - Math.PI;
                else if (thetaModify > Math.PI * 3 / 2.0 && thetaModify <= Math.PI * 2)
                    thetaModify = 2 * Math.PI - thetaModify;


                double heightDelta = sectionXZ_Y - sectionYZ_Y;
                double z = sectionXZ_Y - heightDelta * Math.abs(thetaModify) / (Math.PI / 2.0);
/*
                if (j == 0)
                {
                    System.out.println("x ============ " + x);
                    //System.out.println("y ============ " + y);
                    System.out.println("z ============ " + z);
                    //System.out.println("sectionXZ_Y == " + sectionXZ_Y);
                    //System.out.println("z_New ========" + newXZPtHeight[i]);
                }*/

                stepb_cartesian_point fanCartesianPoint = new stepb_cartesian_point(x, y, z);
                ptArray[i] = fanCartesianPoint;

            }
            _ptSetsShell[j] = ptArray;
        }
        //////////2nd area
        for (int j = this._numberFan / 2; j < this._numberFan / 2 + this._numberTri + 1; j++)
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];

            for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);


                //stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                //double sectionXZ_X = cartesianPtXZ.X();
                double sectionXZ_Y = newXZPtHeight[i];
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                double y = cartesianPtYZ.Y();
                double z = cartesianPtYZ.Z();
                double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;
                stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z);//三角形區域中的點

                stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];
                double h = cartesianPtYZ.Y();
                double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

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

                double thetaModify = this.modifyAngle(theta);

                if (thetaModify > Math.PI / 2 && thetaModify <= Math.PI)
                    thetaModify = Math.PI - thetaModify;
                else if (thetaModify > Math.PI && thetaModify <= Math.PI * 3 / 2.0)
                    thetaModify = thetaModify - Math.PI;
                else if (thetaModify > Math.PI * 3 / 2.0 && thetaModify <= Math.PI * 2)
                    thetaModify = 2 * Math.PI - thetaModify;

                double heightDelta = sectionXZ_Y - sectionYZ_Y;
                newZ = sectionXZ_Y - heightDelta*Math.abs(thetaModify) / (Math.PI / 2.0);


                stepb_cartesian_point fanCartesianPoint = new stepb_cartesian_point(newX, newY, newZ);//扇型區域的點

                if (x > w || x < -w)
                    ptArray[i] = fanCartesianPoint;
                else
                    ptArray[i] = triCartesianPoint;

/*
                if (j == 20)
                {
                    //System.out.println("x ============ " + ptArray[i].X());
                    System.out.println("y ============ " + ptArray[i].Y());
                    //System.out.println("z_Old ======== " + z);
                    //System.out.println("sectionXZ_Y == " + sectionXZ_Y);
                    System.out.println("z ============" + ptArray[i].Z());
                }*/

            }
            _ptSetsShell[j] = ptArray;
         }

        //////////3rd area
        for (int j = this._numberFan / 2 + this._numberTri + 1;
                     j < this._numberFan / 2 + this._numberTri + this._numberFan; j++)
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];

            for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                //stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                //double sectionXZ_X = cartesianPtXZ.X();
                double sectionXZ_Y = newXZPtHeight[i];
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];
                double h = cartesianPtYZ.Y();
                double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

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


                double thetaModify = this.modifyAngle(theta);

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

            _ptSetsShell[j] = ptArray;
        }

        //////////4th area
        for (int j = this._numberFan / 2 + this._numberTri + this._numberFan;
                     j < this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j++)
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];

            for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                //stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                //double sectionXZ_X = cartesianPtXZ.X();
                double sectionXZ_Y = newXZPtHeight[i];
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                double y = -1 * cartesianPtYZ.Y();
                double z = cartesianPtYZ.Z();
                double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;
                stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z);//三角形區域的點

                stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];
                double h = cartesianPtYZ.Y();
                double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

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

                stepb_cartesian_point fanCartesianPoint = new stepb_cartesian_point(newX, newY, newZ);//扇型區域的點

                if ( -x > w || -x < -w)
                    ptArray[i] = fanCartesianPoint;
                else
                    ptArray[i] = triCartesianPoint;

            }
            _ptSetsShell[j] = ptArray;
        }

        //////////5th area
        for (int j = this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j < jCount; j++)
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];

             for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                //stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                //double sectionXZ_X = cartesianPtXZ.X();
                double sectionXZ_Y = newXZPtHeight[i];
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];

                double h = cartesianPtYZ.Y();
                double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());
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

               double thetaModify = this.modifyAngle(theta);

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
            _ptSetsShell[j] = ptArray;
        }
        this.createInnerRoundCorrugation(jCount, iCount, sectionXZPtsArray, sectionYZPtsArray, newXZPtHeight);
    }

    private void createInnerRoundCorrugation(int jCount, int iCount, stepb_cartesian_point[] sectionXZPtsArray, stepb_cartesian_point[] sectionYZPtsArray, double[] newXZPtHeight)
    {
        double outerCriticalWidth = this._point3DEndSection1.X() - this._point3DEndSection2.Y();

        /////////////////1st area/////////////////
        for (int j = 0; j < this._numberFan / 2; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double lineAngleTheta = this.calculateTheta(this._elementArrayOuter[j].X() - this._elementArrayInner[j].X(), this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y());
            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];
            for (int l = 0; l < _ptSetsShell[j].length; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                           Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }

            double[] value = null;

            if (this.isJStart(j))
            {
                if (j == 0)
                    value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[jCount - 1]);
                else
                    value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            }
            if (this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);

            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double sectionXZ_X = cartesianPtXZ.X();
                    double sectionXZ_Y = newXZPtHeight[i];
                    double sectionYZ_X = cartesianPtYZ.Y();
                    double sectionYZ_Y = cartesianPtYZ.Z();

                    stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                    stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];
                    double h = cartesianPtYZ.Y();
                    double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

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

                    double thetaModify = thetaModify = this.modifyAngle(theta);

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
                _ptSetsShell[j] = ptArray;
            }
            //把在j=0的位置有corrugation排除掉
            if (j != 0 && this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }
            //只針對在j=0的位置有corrugation的特別計算
            if (j == 1 && this.isJCritical(0))
            {
                stepb_cartesian_point ptJZeroArray[] = _ptSetsShell[0];
                stepb_cartesian_point ptJOneArray[] = _ptSetsShell[1];
                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptJOneArray[i].X() - ptJZeroArray[i].X(), 2) + Math.pow(ptJOneArray[i].Y() - ptJZeroArray[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptJZeroArray[i].Z() - distance;
                        (ptJZeroArray[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }
        }

        /////////////////2nd area/////////////////
        for (int j = this._numberFan / 2; j < this._numberFan / 2 + this._numberTri + 1; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double lineAngleTheta = this.calculateTheta(this._elementArrayOuter[j].X() - this._elementArrayInner[j].X(), this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y());
            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];
            for (int l = 0; l < _ptSetsShell[j].length; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                           Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }

            double[] value = null;

            if (this.isJStart(j))
                value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            if (this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double sectionXZ_X = cartesianPtXZ.X();
                    double sectionXZ_Y = newXZPtHeight[i];
                    double sectionYZ_X = cartesianPtYZ.Y();
                    double sectionYZ_Y = cartesianPtYZ.Z();

                    double y = cartesianPtYZ.Y();
                    double z = cartesianPtYZ.Z();
                    double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;
                    stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z); //三角形區域中的點

                    stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                    stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];
                    double h = cartesianPtYZ.Y();
                    double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

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

                    double thetaModify = this.modifyAngle(theta);

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
                        ptArray[i] = fanCartesianPoint;
                    else
                        ptArray[i] = triCartesianPoint;
                }
                _ptSetsShell[j] = ptArray;
            }
            if (this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                        /*double distance = Math.pow(jStartPtArray[i].X() - x, 2) + Math.pow(jStartPtArray[i].Y() - y, 2);
                        distance = Math.sqrt(distance);

                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        newZ -= distance; //將扇型區域的點的Z往下壓

                        double triArezPtNewZ = triCartesianPoint.Z() - distance; //將三角形區域的點往下壓
                        triCartesianPoint.GetMathVector3d().Set(triCartesianPoint.X(), triCartesianPoint.Y(), triArezPtNewZ);
                        */
                    }
                }
            }

        }
        /////////////////3rd area/////////////////
        for (int j = this._numberFan / 2 + this._numberTri + 1;
                     j < this._numberFan / 2 + this._numberTri + this._numberFan; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double lineAngleTheta = this.calculateTheta(this._elementArrayOuter[j].X() - this._elementArrayInner[j].X(), this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y());
            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];
            for (int l = 0; l < _ptSetsShell[j].length; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                           Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }

            double[] value = null;

            if (this.isJStart(j))
                value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            if (this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double sectionXZ_X = cartesianPtXZ.X();
                    double sectionXZ_Y = newXZPtHeight[i];
                    double sectionYZ_X = cartesianPtYZ.Y();
                    double sectionYZ_Y = cartesianPtYZ.Z();

                    stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                    stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];
                    double h = cartesianPtYZ.Y();
                    double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

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
                _ptSetsShell[j] = ptArray;
            }
            if (this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }
        }

        //////////4th area
        for (int j = this._numberFan / 2 + this._numberTri + this._numberFan;
                     j < this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double lineAngleTheta = this.calculateTheta(this._elementArrayOuter[j].X() - this._elementArrayInner[j].X(), this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y());
            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];
            for (int l = 0; l < _ptSetsShell[j].length; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                           Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }

            double[] value = null;

            if (this.isJStart(j))
                value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            if (this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);

            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }
                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double sectionXZ_X = cartesianPtXZ.X();
                    double sectionXZ_Y = newXZPtHeight[i];
                    double sectionYZ_X = cartesianPtYZ.Y();
                    double sectionYZ_Y = cartesianPtYZ.Z();

                    double y = -1 * cartesianPtYZ.Y();
                    double z = cartesianPtYZ.Z();
                    double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;

                    stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z);

                    stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                    stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];
                    double h = cartesianPtYZ.Y();
                    double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());

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
                        ptArray[i] = fanCartesianPoint;
                    else
                        ptArray[i] = triCartesianPoint;
                }
                _ptSetsShell[j] = ptArray;
            }
            if (this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }

        }
        //////////5th area
        for (int j = this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j < jCount; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double lineAngleTheta = this.calculateTheta(this._elementArrayOuter[j].X() - this._elementArrayInner[j].X(), this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y());
            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];
            for (int l = 0; l < _ptSetsShell[j].length; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                           Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }

            double[] value = null;

            if (this.isJStart(j))
            {
                if(j == jCount - 1)
                    value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[0], _ptSetsShell[j], _ptSetsShell[j - 1]);
                else
                    value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            }
            if (this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);

            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }
                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double sectionXZ_X = cartesianPtXZ.X();
                    double sectionXZ_Y = newXZPtHeight[i];
                    double sectionYZ_X = cartesianPtYZ.Y();
                    double sectionYZ_Y = cartesianPtYZ.Z();

                    stepb_cartesian_point ptYZStart = sectionYZPtsArray[0];
                    stepb_cartesian_point ptYZEnd = sectionYZPtsArray[iCount - 1];

                    double h = cartesianPtYZ.Y();
                    double w = outerCriticalWidth * (cartesianPtYZ.Y() - ptYZStart.Y()) / (ptYZEnd.Y() - ptYZStart.Y());
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
                _ptSetsShell[j] = ptArray;
            }

            if (this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }
        }
    }



    /***
     * Used to sweep the surround profile whose inner is runway type.
     * @param iCount Total node numbers of surround on radial
     * @param jCount Total node numbers of surround on circumferential
     * @param sectionXZPtsArray Cartesian points array of surround profile on XZ_section
     * @param sectionYZPtsArray Cartesian points array of surround profile on YZ_section
     * @param newXZPtHeight 根據YZ方向的Y分布，重新計算XZ方向的Z高
     */
    private void innerRunwaySweep(int jCount, int iCount,
                                  stepb_cartesian_point[] sectionXZPtsArray, stepb_cartesian_point[] sectionYZPtsArray,
                                  double[] newXZPtHeight)
    {
        //////////1st area
        for (int j = 0; j < this._numberFan / 2; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);
            lineAngleTheta = this.modifyAngle(lineAngleTheta);

            for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                double sectionXZ_X = cartesianPtXZ.X();
                //double sectionXZ_Y = newXZPtHeight[i];
                double sectionXZ_Y = cartesianPtXZ.Z();
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                double h = sectionYZ_X;
                double w = sectionXZ_X - sectionYZ_X;
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
            _ptSetsShell[j] = ptArray;
        }

        //////////2nd area
        for (int j = this._numberFan / 2; j < this._numberFan / 2 + this._numberTri + 1; j++)
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];

            for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];


                double y = cartesianPtYZ.Y();
                double z = cartesianPtYZ.Z();
                double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;
                stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z); //三角形區域中的點
                ptArray[i] = triCartesianPoint;
            }
            _ptSetsShell[j] = ptArray;
        }
        //////////3rd area
        for (int j = this._numberFan / 2 + this._numberTri + 1;
                     j < this._numberFan / 2 + this._numberTri + this._numberFan; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                double sectionXZ_X = cartesianPtXZ.X();
                //double sectionXZ_Y = newXZPtHeight[i];
                double sectionXZ_Y = cartesianPtXZ.Z();
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                double h = sectionYZ_X;
                double w = sectionXZ_X - sectionYZ_X;

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
            _ptSetsShell[j] = ptArray;
        }

        //////////4th area
        for (int j = this._numberFan / 2 + this._numberTri + this._numberFan;
                     j < this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j++)
        {
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];

            for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                double y = -1 * cartesianPtYZ.Y();
                double z = cartesianPtYZ.Z();
                double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;
                stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z); //三角形區域的點
                ptArray[i] = triCartesianPoint;
            }
            _ptSetsShell[j] = ptArray;
        }

        //////////5th area
        for (int j = this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j < jCount; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            for (int i = 0; i < iCount; i++)
            {
                double lineAngle = Math.tan(lineAngleTheta);

                stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                double sectionXZ_X = cartesianPtXZ.X();
                //double sectionXZ_Y = newXZPtHeight[i];
                double sectionXZ_Y = cartesianPtXZ.Z();
                double sectionYZ_X = cartesianPtYZ.Y();
                double sectionYZ_Y = cartesianPtYZ.Z();

                double h = sectionYZ_X;
                double w = sectionXZ_X - sectionYZ_X;

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

            _ptSetsShell[j] = ptArray;
        }
        this.createInnerRunwayCorrugation(jCount, iCount, sectionXZPtsArray, sectionYZPtsArray, newXZPtHeight);
    }

    private double[] start2CriticalLengthScale(stepb_cartesian_point[] ptRadialSets, int IStart, int ICritical)
    {
        double totalLength = Math.sqrt(Math.pow(ptRadialSets[IStart].X() - ptRadialSets[ICritical].X(), 2) +
                                       Math.pow(ptRadialSets[IStart].Y() - ptRadialSets[ICritical].Y(), 2));

        double[] returnValue = new double[ICritical - IStart];

        for(int i = 0 ; i < ICritical - IStart ; i++)
        {
            double length = Math.sqrt(Math.pow(ptRadialSets[IStart + 1 + i].X() - ptRadialSets[IStart].X(), 2) +
                                      Math.pow(ptRadialSets[IStart + 1 + i].Y() - ptRadialSets[IStart].Y(), 2));

            returnValue[i] = length / totalLength;
        }

        return returnValue;
    }

    private double[] critical2EndLengthScale(stepb_cartesian_point[] ptRadialSets, int ICritical, int IEnd)
    {
        double totalLength = Math.sqrt(Math.pow(ptRadialSets[ICritical].X() - ptRadialSets[IEnd].X(), 2) +
                                       Math.pow(ptRadialSets[ICritical].Y() - ptRadialSets[IEnd].Y(), 2));

        double[] returnValue = new double[IEnd - ICritical];

        for (int i = 0; i < IEnd - ICritical; i++)
        {
            double length = Math.sqrt(Math.pow(ptRadialSets[ICritical + 1 + i].X() - ptRadialSets[ICritical].X(), 2) +
                                      Math.pow(ptRadialSets[ICritical + 1 + i].Y() - ptRadialSets[ICritical].Y(), 2));

            returnValue[i] = length / totalLength;
        }

        return returnValue;
    }



    private void createInnerRunwayCorrugation(int jCount, int iCount, stepb_cartesian_point[] sectionXZPtsArray, stepb_cartesian_point[] sectionYZPtsArray, double[] newXZPtHeight)
    {

        //////////1st area
        for (int j = 0; j < this._numberFan / 2; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double lineAngleTheta = this.calculateTheta(this._elementArrayOuter[j].X() - this._elementArrayInner[j].X(), this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y());
            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];

            for(int l = 0; l < _ptSetsShell[j].length ; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                          Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }


            double[] value = null;

            if (this.isJStart(j))
            {
                if (j == 0)
                    value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[jCount - 1]);
                else
                    value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            }
            if(this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);

            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                         currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);
                    }

                    lineAngleTheta = this.modifyAngle(lineAngleTheta);
                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double sectionXZ_X = cartesianPtXZ.X();
                    double sectionXZ_Y = newXZPtHeight[i];
                    double sectionYZ_X = cartesianPtYZ.Y();
                    double sectionYZ_Y = cartesianPtYZ.Z();

                    double h = sectionYZ_X;
                    double w = sectionXZ_X - sectionYZ_X;
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
                _ptSetsShell[j] = ptArray;
            }

            //把在j=0的位置有corrugation排除掉
            if (j != 0 && this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }

            //只針對在j=0的位置有corrugation的特別計算
            if (j == 1 && this.isJCritical(0))
            {
                stepb_cartesian_point ptJZeroArray[] = _ptSetsShell[0];
                stepb_cartesian_point ptJOneArray[] = _ptSetsShell[1];
                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptJOneArray[i].X() - ptJZeroArray[i].X(), 2) + Math.pow(ptJOneArray[i].Y() - ptJZeroArray[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptJZeroArray[i].Z() - distance;
                        (ptJZeroArray[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }

        }

        //////////2nd area
        for (int j = this._numberFan / 2; j < this._numberFan / 2 + this._numberTri + 1; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];
            for (int l = 0; l < _ptSetsShell[j].length; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                           Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }


            double[] value = null;

            if (this.isJStart(j))
                value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            if (this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);

            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);


                    }
                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);

                    }



                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double y = cartesianPtYZ.Y();
                    double z = cartesianPtYZ.Z();
                    double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;
                    stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z); //三角形區域中的點
                    ptArray[i] = triCartesianPoint;
                }
                _ptSetsShell[j] = ptArray;
            }

            if (this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }
        }

        //////////3rd area
        for (int j = this._numberFan / 2 + this._numberTri + 1;
                     j < this._numberFan / 2 + this._numberTri + this._numberFan; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double lineAngleTheta = this.calculateTheta(this._elementArrayOuter[j].X() - this._elementArrayInner[j].X(), this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y());

            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];
            for (int l = 0; l < _ptSetsShell[j].length; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                           Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }


            double[] value = null;

            if(this.isJStart(j))
                value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            if(this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);

            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);

                    }
                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);

                    }

                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double sectionXZ_X = cartesianPtXZ.X();
                    double sectionXZ_Y = newXZPtHeight[i];
                    double sectionYZ_X = cartesianPtYZ.Y();
                    double sectionYZ_Y = cartesianPtYZ.Z();

                    double h = sectionYZ_X;
                    double w = sectionXZ_X - sectionYZ_X;

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
                _ptSetsShell[j] = ptArray;
            }

            if (this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }
        }

        //////////4th area
        for (int j = this._numberFan / 2 + this._numberTri + this._numberFan;
                     j < this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double dx = this._elementArrayOuter[j].X() - this._elementArrayInner[j].X();
            double dy = this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y();
            double lineAngleTheta = this.calculateTheta(dx, dy);

            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];
            for (int l = 0; l < _ptSetsShell[j].length; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                           Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }

            double[] value = null;

            if (this.isJStart(j))
                value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            if (this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);

            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);

                     }
                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);

                    }

                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double y = -1 * cartesianPtYZ.Y();
                    double z = cartesianPtYZ.Z();
                    double x = this._elementArrayInner[j].X() + (y - this._elementArrayInner[j].Y()) / lineAngle;
                    stepb_cartesian_point triCartesianPoint = new stepb_cartesian_point(x, y, z); //三角形區域的點
                    ptArray[i] = triCartesianPoint;
                }
                _ptSetsShell[j] = ptArray;
            }

            if (this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }
        }

        //////////5th area
        for (int j = this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j < jCount; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iCount];
            double lineAngleTheta = this.calculateTheta(this._elementArrayOuter[j].X() - this._elementArrayInner[j].X(), this._elementArrayOuter[j].Y() - this._elementArrayInner[j].Y());

            double currentLineAngleTheta = lineAngleTheta;
            double[] lengthArray = new double[_ptSetsShell.length];
            for (int l = 0; l < _ptSetsShell[j].length; l++)
            {
                lengthArray[l] = Math.sqrt(Math.pow(_ptSetsShell[j][l].X() - _ptSetsShell[j][0].X(), 2) +
                                           Math.pow(_ptSetsShell[j][l].Y() - _ptSetsShell[j][0].Y(), 2));
            }

            double[] value = null;

            if (this.isJStart(j))
            {
                if(j == jCount - 1)
                    value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[0], _ptSetsShell[j], _ptSetsShell[j - 1]);
                else
                    value = this.calculateIStartICriticalIEndAngle(0, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);
            }
            if(this.isJEnd(j))
                value = this.calculateIStartICriticalIEndAngle(1, _ptSetsShell[j + 1], _ptSetsShell[j], _ptSetsShell[j - 1]);

            if (this.isJStart(j) || this.isJEnd(j))
            {
                for (int i = 0; i < iCount; i++)
                {
                    if (this.isJStart(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJStartIStart, this._corrugationJStartICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJStartICritical, this._corrugationJStartIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJStartIStart, this._corrugationJStartICritical, this._corrugationJStartIEnd,
                                currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);

                    }
                    else if (this.isJEnd(j))
                    {
                        double[] start2CriticalScale = start2CriticalLengthScale(_ptSetsShell[j], this._corrugationJEndIStart, this._corrugationJEndICritical);
                        double[] critical2EndScale = critical2EndLengthScale(_ptSetsShell[j], this._corrugationJEndICritical, this._corrugationJEndIEnd);

                        lineAngleTheta = this.calculateJCorrugation2(i, this._corrugationJEndIStart, this._corrugationJEndICritical, this._corrugationJEndIEnd,
                                currentLineAngleTheta, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], start2CriticalScale, critical2EndScale, lengthArray);

                    }
                    double lineAngle = Math.tan(lineAngleTheta);

                    stepb_cartesian_point cartesianPtXZ = sectionXZPtsArray[i];
                    stepb_cartesian_point cartesianPtYZ = sectionYZPtsArray[i];

                    double sectionXZ_X = cartesianPtXZ.X();
                    double sectionXZ_Y = newXZPtHeight[i];
                    double sectionYZ_X = cartesianPtYZ.Y();
                    double sectionYZ_Y = cartesianPtYZ.Z();

                    double h = sectionYZ_X;
                    double w = sectionXZ_X - sectionYZ_X;

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
                _ptSetsShell[j] = ptArray;
            }

            if (this.isJCritical(j))
            {
                stepb_cartesian_point ptArrayCurrent[] = _ptSetsShell[j];
                stepb_cartesian_point ptArrayPrevious[] = _ptSetsShell[j - 1];

                for (int i = 0; i < iCount; i++)
                {
                    if (i >= this._corrugationJStartIStart && i <= this._corrugationJStartIEnd)
                    {
                        double distance = Math.pow(ptArrayCurrent[i].X() - ptArrayPrevious[i].X(), 2) + Math.pow(ptArrayCurrent[i].Y() - ptArrayPrevious[i].Y(), 2);
                        distance = Math.sqrt(distance);
                        distance = distance / Math.tan(this._corrugationFormingAngle / 360.0 * Math.PI);
                        double newZ = ptArrayCurrent[i].Z() - distance;
                        (ptArrayCurrent[i].GetMathVector3d()).SetZ(newZ);
                    }
                }
            }
        }
    }

    private double[] calculateIStartICriticalIEndAngle(int jStartOrjEnd,
            stepb_cartesian_point[] ptArrayNext, stepb_cartesian_point[] ptArrayCurrent, stepb_cartesian_point[] ptArrayPrevious)
    {
        double thetaIStart = 0;
        double thetaICritical = 0;
        double thetaIEnd = 0;
        double thetaSToC = 0;
        double thetaCToE = 0;

        double tempAStart;
        double tempBStart;
        double tempCStart;
        double tempACritical;
        double tempBCritical;
        double tempCCritical;
        double tempAEnd;
        double tempBEnd;
        double tempCEnd;

        double lengthS2C;
        double lengthC2E;

        double length1;
        double length2;
        double length3;
        double length4;
        double length5;

        double anglePre;
        double angleNext;

        if (jStartOrjEnd == 0)
        {
            if (this._corrugationJStartIStart < ptArrayCurrent.length)
            {
                tempAStart = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJStartIStart].X() - ptArrayCurrent[0].X(), 2) +
                                       Math.pow(ptArrayNext[this._corrugationJStartIStart].Y() - ptArrayCurrent[0].Y(), 2));

                tempBStart = Math.sqrt(Math.pow(ptArrayCurrent[this._corrugationJStartIStart].X() - ptArrayCurrent[0].X(), 2) +
                                       Math.pow(ptArrayCurrent[this._corrugationJStartIStart].Y() - ptArrayCurrent[0].Y(), 2));

                tempCStart = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJStartIStart].X() - ptArrayCurrent[this._corrugationJStartIStart].X(), 2) +
                                       Math.pow(ptArrayNext[this._corrugationJStartIStart].Y() - ptArrayCurrent[this._corrugationJStartIStart].Y(), 2));
            }
            else
            {
                tempAStart = Math.sqrt(Math.pow(ptArrayNext[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                       Math.pow(ptArrayNext[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempBStart = Math.sqrt(Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                       Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempCStart = Math.sqrt(Math.pow(ptArrayNext[ptArrayCurrent.length - 1].X() - ptArrayCurrent[ptArrayCurrent.length - 1].X(), 2) +
                                       Math.pow(ptArrayNext[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[ptArrayCurrent.length - 1].Y(), 2));

            }

            if (this._corrugationJStartICritical < ptArrayCurrent.length)
            {
                tempACritical = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJStartICritical].X() - ptArrayCurrent[0].X(), 2) +
                                          Math.pow(ptArrayPrevious[this._corrugationJStartICritical].Y() - ptArrayCurrent[0].Y(), 2));

                tempBCritical = Math.sqrt(Math.pow(ptArrayCurrent[this._corrugationJStartICritical].X() - ptArrayCurrent[0].X(), 2) +
                                          Math.pow(ptArrayCurrent[this._corrugationJStartICritical].Y() - ptArrayCurrent[0].Y(), 2));

                tempCCritical = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJStartICritical].X() - ptArrayCurrent[this._corrugationJStartICritical].X(), 2) +
                                          Math.pow(ptArrayPrevious[this._corrugationJStartICritical].Y() - ptArrayCurrent[this._corrugationJStartICritical].Y(), 2));
            }
            else
            {
                tempACritical = Math.sqrt(Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                          Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempBCritical = Math.sqrt(Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                          Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempCCritical = Math.sqrt(Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].X() - ptArrayCurrent[ptArrayCurrent.length - 1].X(), 2) +
                                          Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[ptArrayCurrent.length - 1].Y(), 2));

            }

            if (this._corrugationJStartIEnd < ptArrayCurrent.length)
            {
                tempAEnd = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJStartIEnd].X() - ptArrayCurrent[0].X(), 2) +
                                     Math.pow(ptArrayNext[this._corrugationJStartIEnd].Y() - ptArrayCurrent[0].Y(), 2));

                tempBEnd = Math.sqrt(Math.pow(ptArrayCurrent[this._corrugationJStartIEnd].X() - ptArrayCurrent[0].X(), 2) +
                                     Math.pow(ptArrayCurrent[this._corrugationJStartIEnd].Y() - ptArrayCurrent[0].Y(), 2));

                tempCEnd = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJStartIEnd].X() - ptArrayCurrent[this._corrugationJStartIEnd].X(), 2) +
                                     Math.pow(ptArrayNext[this._corrugationJStartIEnd].Y() - ptArrayCurrent[this._corrugationJStartIEnd].Y(), 2));
            }
            else
            {
                tempAEnd = Math.sqrt(Math.pow(ptArrayNext[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                     Math.pow(ptArrayNext[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempBEnd = Math.sqrt(Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                     Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempCEnd = Math.sqrt(Math.pow(ptArrayNext[ptArrayCurrent.length - 1].X() - ptArrayCurrent[ptArrayCurrent.length - 1].X(), 2) +
                                     Math.pow(ptArrayNext[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[ptArrayCurrent.length - 1].Y(), 2));
            }

            lengthS2C = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJStartIStart].X() - ptArrayPrevious[this._corrugationJStartICritical].X(), 2) +
                                  Math.pow(ptArrayNext[this._corrugationJStartIStart].Y() - ptArrayPrevious[this._corrugationJStartICritical].Y(), 2));

            lengthC2E = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJStartIEnd].X() - ptArrayPrevious[this._corrugationJStartICritical].X(), 2) +
                                  Math.pow(ptArrayNext[this._corrugationJStartIEnd].Y() - ptArrayPrevious[this._corrugationJStartICritical].Y(), 2));


            length1 = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJStartICritical].X() - ptArrayCurrent[0].X(), 2) +
                                Math.pow(ptArrayNext[this._corrugationJStartICritical].Y() - ptArrayCurrent[0].Y(), 2));

            length2 = Math.sqrt(Math.pow(ptArrayCurrent[this._corrugationJStartICritical].X() - ptArrayCurrent[0].X(), 2) +
                                Math.pow(ptArrayCurrent[this._corrugationJStartICritical].Y() - ptArrayCurrent[0].Y(), 2));

            length3 = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJStartICritical].X() - ptArrayCurrent[0].X(), 2) +
                                Math.pow(ptArrayPrevious[this._corrugationJStartICritical].Y() - ptArrayCurrent[0].Y(), 2));

            length4 = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJStartICritical].X() - ptArrayCurrent[this._corrugationJStartICritical].X(), 2) +
                                Math.pow(ptArrayNext[this._corrugationJStartICritical].Y() - ptArrayCurrent[this._corrugationJStartICritical].Y(), 2));

            length5 = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJStartICritical].X() - ptArrayCurrent[this._corrugationJStartICritical].X(), 2) +
                                Math.pow(ptArrayPrevious[this._corrugationJStartICritical].Y() - ptArrayCurrent[this._corrugationJStartICritical].Y(), 2));

            anglePre = this.calculateAngle(length3, length2, length5);
            angleNext = this.calculateAngle(length1, length2, length4);


        }
        else
        {
            if (this._corrugationJEndIStart < ptArrayCurrent.length)
            {
                tempAStart = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJEndIStart].X() - ptArrayCurrent[0].X(), 2) +
                                       Math.pow(ptArrayPrevious[this._corrugationJEndIStart].Y() - ptArrayCurrent[0].Y(), 2));

                tempBStart = Math.sqrt(Math.pow(ptArrayCurrent[this._corrugationJEndIStart].X() - ptArrayCurrent[0].X(), 2) +
                                       Math.pow(ptArrayCurrent[this._corrugationJEndIStart].Y() - ptArrayCurrent[0].Y(), 2));

                tempCStart = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJEndIStart].X() - ptArrayCurrent[this._corrugationJEndIStart].X(), 2) +
                                       Math.pow(ptArrayPrevious[this._corrugationJEndIStart].Y() - ptArrayCurrent[this._corrugationJEndIStart].Y(), 2));
            }
            else
            {
                tempAStart = Math.sqrt(Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                       Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempBStart = Math.sqrt(Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                       Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempCStart = Math.sqrt(Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].X() - ptArrayCurrent[ptArrayCurrent.length - 1].X(), 2) +
                                       Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[ptArrayCurrent.length - 1].Y(), 2));
            }

            if (this._corrugationJEndICritical < ptArrayCurrent.length)
            {
                tempACritical = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJEndICritical].X() - ptArrayCurrent[0].X(), 2) +
                                          Math.pow(ptArrayNext[this._corrugationJEndICritical].Y() - ptArrayCurrent[0].Y(), 2));

                tempBCritical = Math.sqrt(Math.pow(ptArrayCurrent[this._corrugationJEndICritical].X() - ptArrayCurrent[0].X(), 2) +
                                          Math.pow(ptArrayCurrent[this._corrugationJEndICritical].Y() - ptArrayCurrent[0].Y(), 2));

                tempCCritical = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJEndICritical].X() - ptArrayCurrent[this._corrugationJEndICritical].X(), 2) +
                                          Math.pow(ptArrayNext[this._corrugationJEndICritical].Y() - ptArrayCurrent[this._corrugationJEndICritical].Y(), 2));
            }
            else
            {
                tempACritical = Math.sqrt(Math.pow(ptArrayNext[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                          Math.pow(ptArrayNext[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempBCritical = Math.sqrt(Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                          Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempCCritical = Math.sqrt(Math.pow(ptArrayNext[ptArrayCurrent.length - 1].X() - ptArrayCurrent[ptArrayCurrent.length - 1].X(), 2) +
                                          Math.pow(ptArrayNext[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[ptArrayCurrent.length - 1].Y(), 2));
            }

            if (this._corrugationJEndIEnd < ptArrayCurrent.length)
            {
                tempAEnd = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJEndIEnd].X() - ptArrayCurrent[0].X(), 2) +
                                     Math.pow(ptArrayPrevious[this._corrugationJEndIEnd].Y() - ptArrayCurrent[0].Y(), 2));

                tempBEnd = Math.sqrt(Math.pow(ptArrayCurrent[this._corrugationJEndIEnd].X() - ptArrayCurrent[0].X(), 2) +
                                     Math.pow(ptArrayCurrent[this._corrugationJEndIEnd].Y() - ptArrayCurrent[0].Y(), 2));

                tempCEnd = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJEndIEnd].X() - ptArrayCurrent[this._corrugationJEndIEnd].X(), 2) +
                                     Math.pow(ptArrayPrevious[this._corrugationJEndIEnd].Y() - ptArrayCurrent[this._corrugationJEndIEnd].Y(), 2));
            }
            else
            {
                tempAEnd = Math.sqrt(Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                     Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempBEnd = Math.sqrt(Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), 2) +
                                     Math.pow(ptArrayCurrent[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y(), 2));

                tempCEnd = Math.sqrt(Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].X() - ptArrayCurrent[ptArrayCurrent.length - 1].X(), 2) +
                                     Math.pow(ptArrayPrevious[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[ptArrayCurrent.length - 1].Y(), 2));
            }

            lengthS2C = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJEndICritical].X() - ptArrayPrevious[this._corrugationJEndIStart].X(), 2) +
                                  Math.pow(ptArrayNext[this._corrugationJEndICritical].Y() - ptArrayPrevious[this._corrugationJEndIStart].Y(), 2));

            lengthC2E = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJEndICritical].X() - ptArrayPrevious[this._corrugationJEndIEnd].X(), 2) +
                                  Math.pow(ptArrayNext[this._corrugationJEndICritical].Y() - ptArrayPrevious[this._corrugationJEndIEnd].Y(), 2));


            length1 = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJEndICritical].X() - ptArrayCurrent[0].X(), 2) +
                                Math.pow(ptArrayNext[this._corrugationJEndICritical].Y() - ptArrayCurrent[0].Y(), 2));

            length2 = Math.sqrt(Math.pow(ptArrayCurrent[this._corrugationJEndICritical].X() - ptArrayCurrent[0].X(), 2) +
                                Math.pow(ptArrayCurrent[this._corrugationJEndICritical].Y() - ptArrayCurrent[0].Y(), 2));

            length3 = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJEndICritical].X() - ptArrayCurrent[0].X(), 2) +
                                Math.pow(ptArrayPrevious[this._corrugationJEndICritical].Y() - ptArrayCurrent[0].Y(), 2));

            length4 = Math.sqrt(Math.pow(ptArrayNext[this._corrugationJEndICritical].X() - ptArrayCurrent[this._corrugationJEndICritical].X(), 2) +
                                Math.pow(ptArrayNext[this._corrugationJEndICritical].Y() - ptArrayCurrent[this._corrugationJEndICritical].Y(), 2));

            length5 = Math.sqrt(Math.pow(ptArrayPrevious[this._corrugationJEndICritical].X() - ptArrayCurrent[this._corrugationJEndICritical].X(), 2) +
                                Math.pow(ptArrayPrevious[this._corrugationJEndICritical].Y() - ptArrayCurrent[this._corrugationJEndICritical].Y(), 2));

            anglePre = this.calculateAngle(length3, length2, length5);
            angleNext = this.calculateAngle(length1, length2, length4);
/*
            System.out.println("length1 === " + length1);
            System.out.println("length2 === " + length2);
            System.out.println("length3 === " + length3);
            System.out.println("length4 === " + length4);
            System.out.println("length5 === " + length5);
*/
        }
        //System.out.println("****anglePre === " + anglePre * 180 / Math.PI);
        //System.out.println("****angleNext === " + angleNext * 180 / Math.PI);

        double angleOSC = this.calculateAngle(tempACritical, tempAStart, lengthS2C);
        double angleOCE = this.calculateAngle(tempAEnd, tempACritical, lengthC2E);

        double currentLineAngle = this.calculateTheta(ptArrayCurrent[ptArrayCurrent.length - 1].X() - ptArrayCurrent[0].X(), ptArrayCurrent[ptArrayCurrent.length - 1].Y() - ptArrayCurrent[0].Y());


        double angleIStart = this.calculateAngle(tempCStart, tempAStart, tempBStart);
        double angleICritical = this.calculateAngle(tempCCritical, tempACritical, tempBCritical);
        double angleIEnd = this.calculateAngle(tempCEnd, tempAEnd, tempBEnd);
        double[] returnValue = new double[8];
        double lengthStart = 0;
        double lengthCritical = 0;
        double lengthEnd = 0;

        if (jStartOrjEnd == 0)//means JStart
        {
            thetaIStart = currentLineAngle + angleIStart * this._corrugationRatioJStartIStart;

            thetaICritical = currentLineAngle - angleICritical * this._corrugationRatioJStartICritical;
            thetaIEnd = currentLineAngle + angleIEnd * this._corrugationRatioJStartIEnd;
            thetaSToC = thetaICritical - thetaIStart;
            thetaCToE = thetaIEnd - thetaICritical;

            lengthStart = tempCStart * this._corrugationRatioJStartIStart;
            lengthCritical = -1 * tempCCritical * this._corrugationRatioJStartICritical;
            lengthEnd = tempCEnd * this._corrugationRatioJStartIEnd;
        }
        else
        {
            thetaIStart = currentLineAngle - angleIStart * this._corrugationRatioJEndIStart;
            thetaICritical = currentLineAngle + angleICritical * this._corrugationRatioJEndICritical;
            thetaIEnd = currentLineAngle - angleIEnd * this._corrugationRatioJEndIEnd;
            thetaSToC = thetaICritical - thetaIStart;
            thetaCToE = thetaIEnd - thetaICritical;

            lengthStart = -1 * tempCStart * this._corrugationRatioJEndIStart;
            lengthCritical = tempCCritical * this._corrugationRatioJEndICritical;
            lengthEnd = -1 * tempCEnd * this._corrugationRatioJEndIEnd;
        }

        returnValue[0] = thetaIStart;
        returnValue[1] = thetaICritical;
        returnValue[2] = thetaIEnd;

        returnValue[3] = lengthStart;
        returnValue[4] = lengthCritical;
        returnValue[5] = lengthEnd;

        returnValue[6] = anglePre;
        returnValue[7] = angleNext;


        return returnValue;
    }


    private double calculateJCorrugation2(int i, int corrugationIStart, int corrugationICritical, int corrugationIEnd,
                                          double thetaCurrent,
                                          double thetaIStart, double thetaICritical, double thetaIEnd,
                                          double lengthStart, double lengthCritical, double lengthEnd,
                                          double anglePre, double angleNext,
                                          double[] lengthScaleS2C, double[] lengthScaleC2E, double[] jSetLength
                                          )
    {


        double lineAngleTheta = thetaCurrent;

        if (i == corrugationIStart)
        {
            lineAngleTheta = thetaIStart;
        }
        else if (i > corrugationIStart && i <= corrugationICritical)
        {
            double distance = lengthStart - (lengthStart - lengthCritical) * Math.sin(lengthScaleS2C[i - corrugationIStart - 1] * Math.PI / 2.0);

            if (distance >= 0)
            {
                double tempLength = Math.sqrt(Math.pow(distance, 2) + Math.pow(jSetLength[i], 2) - 2 * distance * jSetLength[i] * Math.cos(angleNext));
                double theta = Math.asin(Math.abs(distance) / tempLength * Math.sin(angleNext));

                lineAngleTheta = thetaCurrent + theta;
                //lineAngleTheta = thetaCurrent + Math.atan(distance / jSetLength[i]);
            }
            else
            {
                double tempLength = Math.sqrt(Math.pow(distance, 2) + Math.pow(jSetLength[i], 2) - 2 * distance * jSetLength[i] * Math.cos(anglePre));
                double theta = Math.asin(Math.abs(distance) / tempLength * Math.sin(anglePre));

                lineAngleTheta = thetaCurrent - theta;
                lineAngleTheta = thetaCurrent + Math.atan(distance / jSetLength[i]);
            }
        }
        /*else if (i == corrugationICritical)
        {
            lineAngleTheta = thetaICritical;
        }*/
        else if (i > corrugationICritical && i < corrugationIEnd)
        {
            double distance = lengthCritical + ( -1 * lengthCritical + lengthEnd) * (1 - Math.sin(Math.PI / 2 - lengthScaleC2E[i - corrugationICritical - 1] * Math.PI / 2.0));

            if (distance >= 0)
            {
                double tempLength = Math.sqrt(Math.pow(distance, 2) + Math.pow(jSetLength[i], 2) - 2 * distance * jSetLength[i] * Math.cos(angleNext));
                double theta = Math.asin(Math.abs(distance) / tempLength * Math.sin(angleNext));

                lineAngleTheta = thetaCurrent + theta;
                //lineAngleTheta = thetaCurrent + Math.atan(distance / jSetLength[i]);
            }
            else
            {
                double tempLength = Math.sqrt(Math.pow(distance, 2) + Math.pow(jSetLength[i], 2) - 2 * distance * jSetLength[i] * Math.cos(anglePre));
                double theta = Math.asin(Math.abs(distance) / tempLength * Math.sin(anglePre));

                lineAngleTheta = thetaCurrent - theta;
                //lineAngleTheta = thetaCurrent + Math.atan(distance / jSetLength[i]);
            }
        }
        else if (i == corrugationIEnd)
        {
            lineAngleTheta = thetaIEnd;
        }
        lineAngleTheta = this.modifyAngle(lineAngleTheta);
        return lineAngleTheta;
    }


    private double[] sectionXZNewHeight(ElementPoint startPt, ElementPoint endPt, double hTransition, double hTotal,
                                        double radiusIn, double radiusOut, double radiusSingle, double absoluteY, double[] YZScale)
    {
        double[] returnValue = new double[YZScale.length];

        double dx = endPt.X() - startPt.X();
        double dy = endPt.Y() - startPt.Y();
        double theta = this.calculateTheta(dx, dy);
        double surroundWidth = Math.sqrt(dx * dx + dy * dy);
        double[] contourParrameter;

        if (this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
            contourParrameter = this.calculateSurround_SingleArc(surroundWidth, hTransition, radiusSingle);
        else
            contourParrameter = this.calculateSurround_DoubleArc(surroundWidth, hTransition, hTotal, radiusIn, radiusOut);

        double tangentPtX = contourParrameter[0];
        double tangentPtZ = contourParrameter[1] - hTransition;
        double innerCenterX = contourParrameter[2];
        double innerCenterZ = contourParrameter[3] - hTransition;
        double outerCenterX = contourParrameter[4];
        double outerCenterZ = contourParrameter[5] - hTransition;

        if (this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
        {
            for (int i = 0; i < YZScale.length; i++)
            {
                double X = YZScale[i] * surroundWidth;

                double X2SingleCenter = Math.abs(X - innerCenterX);
                double height = Math.sqrt(Math.pow(radiusSingle, 2) - Math.pow(X2SingleCenter, 2));
                returnValue[i] = height + innerCenterZ + absoluteY;
            }
        }
        else
        {
            for (int i = 0; i < YZScale.length; i++)
            {
                double X = YZScale[i] * surroundWidth;
                if(X < tangentPtX)
                {
                    double X2InnerCenter = Math.abs(X - innerCenterX);
                    double height = Math.sqrt(Math.pow(radiusIn, 2) - Math.pow(X2InnerCenter, 2));
                    returnValue[i] = height + innerCenterZ + absoluteY;
                }
                else
                {
                    double X2InnerCenter = Math.abs(X - outerCenterX);
                    double height = Math.sqrt(Math.pow(radiusOut, 2) - Math.pow(X2InnerCenter, 2));
                    returnValue[i] = height + outerCenterZ + absoluteY;
                }
            }
        }

        return returnValue;
    }



    /**
     * Uset to create mesh node on Surround profile in Fan area
     * @param startPt XY_View的Surround_In的ElementPoint
     * @param endPt XY_View的Surround_Out的ElementPoint
     * @param hTransition surround_In 相對於 surround_Out的高度差; surround_In 高於 surround_Out為正
     * @param hTotal surround最高點相對於 surround_Out的高度差, 必定為正
     * @param radiusIn surround內圓弧的半徑(視情況使用 使用於Double_Arc的Surround)
     * @param radiusOut surround外圓弧的半徑(視情況使用 使用於Double_Arc的Surround)
     * @param radiusSingle surround圓弧的半徑(視情況使用 使用於Single_Arc的Surround)
     * @param iiCount Total node numbers of surround on radial
     * @param j int 沒有使用
     * @param absoluteY surround_Out的Y值座標
     * @param jStartPts 沒有使用
     * @return stepb_cartesian_point[]
     */
    public stepb_cartesian_point[] setLinePoint3D_RunwayFan(ElementPoint startPt, ElementPoint endPt,
            double hTransition, double hTotal,
            double radiusIn, double radiusOut, double radiusSingle,
            int iiCount, int j, double absoluteY, stepb_cartesian_point[] jStartPts)
    {
        double dx = endPt.X() - startPt.X();
        double dy = endPt.Y() - startPt.Y();
        double theta = this.calculateTheta(dx, dy);
        double surroundWidth = Math.sqrt(dx * dx + dy * dy);
        double[] contourParrameter;


        if(this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
            contourParrameter = this.calculateSurround_SingleArc(surroundWidth, hTransition, radiusSingle);
        else
            contourParrameter = this.calculateSurround_DoubleArc(surroundWidth, hTransition,  hTotal, radiusIn, radiusOut);


        double tangentPtX = contourParrameter[0];
        double tangentPtZ = contourParrameter[1] - hTransition;
        double innerCenterX = contourParrameter[2];
        double innerCenterZ = contourParrameter[3] - hTransition;
        double outerCenterX = contourParrameter[4];
        double outerCenterZ = contourParrameter[5] - hTransition;

        stepb_cartesian_point ptsArray[] = new stepb_cartesian_point[iiCount];

        double r = 0;
        double z = 0;

        double lengthA = Math.sqrt(Math.pow(tangentPtX, 2) + Math.pow(tangentPtZ, 2));
        double lengthB = Math.sqrt(Math.pow(surroundWidth - tangentPtX, 2) + Math.pow(tangentPtZ + hTransition, 2));
        double thetaAllIn = this.calculateAngle(lengthA, radiusIn, radiusIn);
        double thetaAllOut = this.calculateAngle(lengthB, radiusOut, radiusOut);

        double arcLengthIn = thetaAllIn * radiusIn;
        double arcLengthOut = thetaAllOut * radiusOut;

        if (this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
        {
            thetaAllIn = this.calculateAngle(lengthA, radiusSingle, radiusSingle);
            thetaAllOut = 0;
            arcLengthIn = thetaAllIn * radiusSingle;
            arcLengthOut = thetaAllOut * radiusSingle;
        }

        double arcLengthDelta = (arcLengthIn + arcLengthOut) / (iiCount - 1);
        double arcLengthIncrement = 0;


        double thetaStart = this.calculateTheta(0 - innerCenterX, 0 - innerCenterZ);
        double thetaIncrement;
        double thetaZ;

        for (int ii = 0; ii < iiCount; ii++)
        {
            arcLengthIncrement = arcLengthDelta * ii;
            if (this._geometryType == DefineSystemConstant.SURROUND_DOUBLE_ARC)
            {
                if (arcLengthIncrement <= arcLengthIn)
                {
                    thetaIncrement = arcLengthIncrement / radiusIn;

                    thetaZ = thetaStart - thetaIncrement;

                    r = innerCenterX + radiusIn * Math.cos(thetaZ);
                    z = innerCenterZ + radiusIn * Math.sin(thetaZ);

                }
                else
                {
                    thetaIncrement = thetaAllIn + (arcLengthIncrement - arcLengthIn) / radiusOut;

                    thetaZ = thetaStart - thetaIncrement;

                    r = outerCenterX + radiusOut * Math.cos(thetaZ);
                    z = outerCenterZ + radiusOut * Math.sin(thetaZ);

                }
            }
            else if (this._geometryType == DefineSystemConstant.SURROUND_SINGLE_ARC)
            {
                thetaIncrement = arcLengthIncrement / radiusSingle;

                thetaZ = thetaStart - thetaIncrement;

                r = innerCenterX + radiusSingle * Math.cos(thetaZ);
                z = innerCenterZ + radiusSingle * Math.sin(thetaZ);
            }

            double x = startPt.X() + r * Math.cos(theta);
            double y = startPt.Y() + r * Math.sin(theta);

            z = z + absoluteY;

            stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
            ptsArray[ii] = cPt;

        }
        return ptsArray;
    }


    /**
     * <pre>
     *
     *
     * <br>
     * 利用三點座標計算弧的半徑
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/getRadiusFrom3Points.png"  align="center" class="border" width="200" height="113" />
     * </pre>
     * @param x1 double
     * @param y1 double
     * @param x2 double
     * @param y2 double
     * @param x3 double
     * @param y3 double
     * @return double
     */
    public double getRadiusFrom3Points(double x1, double y1, double x2, double y2, double x3, double y3)
    {
        double radius = 0;
        double xMiddle_12 = (x1 + x2) / 2.0;
        double yMiddle_12 = (y1 + y2) / 2.0;
        double xMiddle_23 = (x2 + x3) / 2.0;
        double yMiddle_23 = (y2 + y3) / 2.0;

        double k12 = -1 * (x2 - x1) / (y2 - y1);
        double k23 = -1 * (x3 - x2) / (y3 - y2);

        double xCenter = (yMiddle_12 - yMiddle_23 + xMiddle_23 * k23 - xMiddle_12 * k12) / (k23 - k12);
        double yCenter = (yMiddle_23 - k23 * xMiddle_23) + k23 * xCenter;

        double lengthA = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        double lengthB = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));
        double lengthC = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        double s = (lengthA + lengthB + lengthC) / 2.0;
        double area = Math.sqrt(s * (s - lengthA) * (s - lengthB) * (s - lengthC));

        radius = lengthA * lengthB * lengthC / 4.0 / area;

        return radius;

    }

    /**
     * 利用海龍公式來計算夾角
     * <br>
     * <img src="http://10.35.7.80:6001/Data/SDT/CodingNotePics/calculateAngle.png"  align="center" class="border" width="250" height="340" />
     * </pre>
     * <br>
     * @param a 所要求的夾角的對邊
     * @param b 所要求的夾角的鄰邊1
     * @param c 所要求的夾角的鄰邊2
     * @return 夾角角度，單位為弧度
     */
    public double calculateAngle(double a, double b, double c)
    {
        double s = (a + b + c) / 2.0;
        double triangleArea = Math.sqrt(s * (s - a) * (s - b) * (s - c));
        double theta;
        if(Math.abs(a * a - b * b - c * c) <  0.000000001)
            theta = Math.PI / 2.0;
        else if (a * a - b * b - c * c < 0)
            theta = Math.asin(triangleArea * 2 / c / b);
        else
            theta = Math.PI - Math.asin(triangleArea * 2 / c / b);
        return theta;
    }




    public void setIfSwitchSurroundType(int sectionNumber, boolean ifSet)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._ifSwitchSurroundTypeXZ = ifSet;
                break;
            case DefineSystemConstant.YZView:
                this._ifSwitchSurroundTypeYZ = ifSet;
                break;
        }
    }

    public boolean ifSwitchSurroundType(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._ifSwitchSurroundTypeXZ;

            case DefineSystemConstant.YZView:
                return this._ifSwitchSurroundTypeYZ;
            default:
                return false;
        }

    }


    public void setIfSetFromDialog(int sectionNumber, boolean ifSet)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this._ifSetFromDialogXZ = ifSet;
                break;
            case DefineSystemConstant.YZView:
                this._ifSetFromDialogYZ = ifSet;
                break;
        }

    }

    public boolean ifSetFromDialog(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._ifSetFromDialogXZ;

            case DefineSystemConstant.YZView:
                return this._ifSetFromDialogYZ;
            default:
                return false;
        }
    }


    /**
     * Over write Database的 setGirth , DataSurround 的 周圍數量輸入  應該沒有限制
     * @param countGirth int
     */
    public void setGirth(int countGirth)
    {
        this._countGirth = countGirth;
    }


    public void save(FileWriter fw) throws IOException
    {
        String className = this.getClass().getCanonicalName();
        fw.write("**********************"+className+"**********************\n");
        //Surround Type
        fw.write("Surround Type : , " + this._geometryType + "\n");

        //Surround XZ Section
        fw.write("      XZ Start(X): ," + _elementPtXZStart.X()     + "\n");
        fw.write("      XZ Start(Z): ," + _elementPtXZStart.Y()     + "\n");
        fw.write("        XZ End(X): ," + _elementPtXZEnd.X()       + "\n");
        fw.write("        XZ End(Z): ," + _elementPtXZEnd.Y()       + "\n");
        fw.write("  Radius Inner XZ: ," + _radiusInXZ               + "\n");
        fw.write(" Radius Outter XZ: ," + _radiusOutXZ              + "\n");
        fw.write("       H Total XZ: ," + _hTotalXZ                 + "\n");
        fw.write(" Radius Single XZ: ," + _radiusSingleXZ           + "\n");


        //Surround YZ Section
        fw.write("      YZ Start(Y): ," + _elementPtYZStart.X()     + "\n");
        fw.write("      YZ Start(Z): ," + _elementPtYZStart.Y()     + "\n");
        fw.write("        YZ End(Y): ," + _elementPtYZEnd.X()       + "\n");
        fw.write("        YZ End(Z): ," + _elementPtYZEnd.Y()       + "\n");
        fw.write("  Radius Inner YZ: ," + _radiusInYZ               + "\n");
        fw.write(" Radius Outter YZ: ," + _radiusOutYZ              + "\n");
        fw.write("       H Total YZ: ," + _hTotalYZ                 + "\n");
        fw.write(" Radius Single YZ: ," + _radiusSingleYZ           + "\n");

        //Surround Mesh Count
        fw.write("          Is Perodical: , " + this._isPerodical           + "\n");
        fw.write("          Count Radial: , " + this._countRadial           + "\n");
        fw.write(" Count Circumferential: , " + this._countCircumferential  + "\n");
        fw.write("      Count Periodical: , " + this._countPeriodical       + "\n");

        fw.write("             Thickness: , " + this._thickness       + "\n");
        fw.write("             Skewed On: , " + this._skewedOn        + "\n");
        fw.write("          Skewed Angle: , " + this._skewedAngle     + "\n");
        //Corrugation Parameter

        fw.write("          Bias On : , " +_isBiasOn + "\n");
        fw.write("   Corrugation On : , " +_isCorrugationOn + "\n");
        fw.write("    Forming Angle : , " +_corrugationFormingAngle + "\n");

        fw.write("   IStart Index@JStart : , " +_corrugationJStartIStart + "\n");
        fw.write("ICritical Index@JStart : , " +_corrugationJStartICritical + "\n");
        fw.write("     IEnd Index@JStart : , " +_corrugationJStartIEnd + "\n");
        fw.write("     IStart Index@JEnd : , " +_corrugationJEndIStart + "\n");
        fw.write("  ICritical Index@JEnd : , " +_corrugationJEndICritical + "\n");
        fw.write("       IEnd Index@JEnd : , " +_corrugationJEndIEnd + "\n");

        fw.write("   IStart Ratio@JStart : , " +_corrugationRatioJStartIStart + "\n");
        fw.write("ICritical Ratio@JStart : , " +_corrugationRatioJStartICritical + "\n");
        fw.write("     IEnd Ratio@JStart : , " +_corrugationRatioJStartIEnd + "\n");
        fw.write("     IStart Ratio@JEnd : , " +_corrugationRatioJEndIStart + "\n");
        fw.write("  ICritical Ratio@JEnd : , " +_corrugationRatioJEndICritical + "\n");
        fw.write("       IEnd Ratio@JEnd : , " +_corrugationRatioJEndIEnd + "\n");
        fw.write("     Corrugation Count : , " + _JStart.length           + "\n");

        for (int i = 0; i < this._JStart.length; i++)
        {
            fw.write("JStart: ["+ i +"] :, "+ this._JStart[i] + "\n");
        }
        for (int i = 0; i < _JEnd.length; i++)
        {
            fw.write("JEnd: ["+ i +"] :, "+this._JEnd[i] + "\n");
        }

        this._material.save(fw);
    }

    public void open(BufferedReader br) throws IOException
    {

        br.readLine().trim();

        //Surround Parameter
        this._geometryType          =       Integer.parseInt(this.readLastString(br.readLine().trim()));

        double surroundStartPtXZ_X  =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double surroundStartPtXZ_Y  =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double surroundEndPtXZ_X    =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double surroundEndPtXZ_Y    =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double innerRadiusXZ        =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double outerRadiusXZ        =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double hTotalXZ             =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double singleRadiusXZ       =       Double.parseDouble(this.readLastString(br.readLine().trim()));

        double surroundStartPtYZ_X  =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double surroundStartPtYZ_Y  =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double surroundEndPtYZ_X    =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double surroundEndPtYZ_Y    =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double innerRadiusYZ        =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double outerRadiusYZ        =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double hTotalYZ             =       Double.parseDouble(this.readLastString(br.readLine().trim()));
        double singleRadiusYZ       =       Double.parseDouble(this.readLastString(br.readLine().trim()));

        this._isPerodical            =       Boolean.parseBoolean(this.readLastString(br.readLine().trim()));
        this._countRadial            =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countCircumferential   =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countPeriodical        =       Integer.parseInt(this.readLastString(br.readLine().trim()));

        this._thickness              =        Double.parseDouble(this.readLastString(br.readLine().trim()));
        this._skewedOn               =        Boolean.parseBoolean(this.readLastString(br.readLine().trim()));
        this._skewedAngle            =        Double.parseDouble(this.readLastString(br.readLine().trim()));

        this.setSurroundHeightInOutRadius  (sdt.define.DefineSystemConstant.XZView, hTotalXZ, innerRadiusXZ, outerRadiusXZ);
        this.setElementPointStartCoordinate(sdt.define.DefineSystemConstant.XZView, surroundStartPtXZ_X, surroundStartPtXZ_Y);
        this.setElementPointEndCoordinate  (sdt.define.DefineSystemConstant.XZView, surroundEndPtXZ_X, surroundEndPtXZ_Y);
        this.setSurroundSingleArcRadius    (sdt.define.DefineSystemConstant.XZView, singleRadiusXZ);

        this.setSurroundHeightInOutRadius  (sdt.define.DefineSystemConstant.YZView, hTotalYZ, innerRadiusYZ, outerRadiusYZ);
        this.setElementPointStartCoordinate(sdt.define.DefineSystemConstant.YZView, surroundStartPtYZ_X, surroundStartPtYZ_Y);
        this.setElementPointEndCoordinate  (sdt.define.DefineSystemConstant.YZView, surroundEndPtYZ_X, surroundEndPtYZ_Y);
        this.setSurroundSingleArcRadius    (sdt.define.DefineSystemConstant.YZView, singleRadiusYZ);

        //Corrugation
        this._isBiasOn                          = Boolean.parseBoolean(this.readLastString(br.readLine().trim()));
        this._isCorrugationOn                   = Boolean.parseBoolean(this.readLastString(br.readLine().trim()));
        this._corrugationFormingAngle           = Double.parseDouble(this.readLastString(br.readLine().trim()));

        this._corrugationJStartIStart           = Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._corrugationJStartICritical        = Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._corrugationJStartIEnd             = Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._corrugationJEndIStart             = Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._corrugationJEndICritical          = Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._corrugationJEndIEnd               = Integer.parseInt(this.readLastString(br.readLine().trim()));

        this._corrugationRatioJStartIStart      = Double.parseDouble(this.readLastString(br.readLine().trim()));
        this._corrugationRatioJStartICritical   = Double.parseDouble(this.readLastString(br.readLine().trim()));
        this._corrugationRatioJStartIEnd        = Double.parseDouble(this.readLastString(br.readLine().trim()));
        this._corrugationRatioJEndIStart        = Double.parseDouble(this.readLastString(br.readLine().trim()));
        this._corrugationRatioJEndICritical     = Double.parseDouble(this.readLastString(br.readLine().trim()));
        this._corrugationRatioJEndIEnd          = Double.parseDouble(this.readLastString(br.readLine().trim()));

        int  corrugationCount                   = Integer.parseInt(this.readLastString(br.readLine().trim()));


        int[] _JStart = new int[corrugationCount];
        for (int i =0 ; i < corrugationCount ; i++)
        {
           _JStart[i] = Integer.parseInt(this.readLastString(br.readLine().trim()));
        }

        int[] _JEnd = new int[corrugationCount];
        for (int i =0 ; i < corrugationCount ; i++)
        {
           _JEnd[i] = Integer.parseInt(this.readLastString(br.readLine().trim()));
        }
        this.setCorrugationFormingAngle(_corrugationFormingAngle);
        this._material.open(br);
    }


}
