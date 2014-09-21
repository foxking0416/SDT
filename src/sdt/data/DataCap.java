package sdt.data;

import java.io.*;

import sdt.define.*;
import sdt.geometry.element.*;
import sdt.stepb.*;

public class DataCap extends DataShell
{
    private double _radius;
    private double _height;
    private double _width;
    private double _theta;

    double _CapsuleX_XZ;
    double _RoundCutX_YZ;
    double _RoundCutY_YZ;
    double _RoundCutRatio;

    ElementPoint _elementPtXZCapsule;
    ElementPoint _elementPtYZRoundCut;
    ElementPoint _elementPtYZRoundCutL;


    public DataCap(SDT_DataManager dataManger)
    {
        super(dataManger);
        _dataType = this.TYPE_CAP;
        _point3DStartSection1 = new stepb_cartesian_point(0, 0, 0.6);
        _point3DEndSection1 = new stepb_cartesian_point(1.2, 0, 0);
        _point3DStartSection2 = new stepb_cartesian_point(0, 0, 0.6);
        _point3DEndSection2 = new stepb_cartesian_point(0, 1.2, 0);

        this._countGirth = dataManger.getCountGirth();
        this._countRadial = 16;
        this._countPeriodical = 8;
        this._countCircumferential = _countGirth / _countPeriodical +1; //11
        this._thickness = 0.05;


        //this._colorCap = this._dataManager.getColorCap();
        this._colorBody = this._dataManager.getColorCap();
        this._material  = this._dataManager.getMaterialManager().getMaterial(this._dataType);
        this._geometryType   = DefineSystemConstant.CAP_TYPE_REGULAR;

        _CapsuleX_XZ = 0.0;
        _RoundCutX_YZ = 0.0;
        _RoundCutY_YZ = 0.0;
        _RoundCutRatio = 0.5;


        _elementPtXZCapsule  = new ElementPoint(this._CapsuleX_XZ             , _point3DStartSection1.Y() , _colorPt ,  this);
        _elementPtYZRoundCut = new ElementPoint(this._RoundCutX_YZ            , _RoundCutY_YZ             , _colorPt ,  this);
        _elementPtYZRoundCutL= new ElementPoint(this._point3DEndSection1.X()  , _RoundCutX_YZ             , _colorPt ,  this);



        this.calculateRadiusXZ();
        this.calculateRadiusYZ();
        this.calculateRoundCutPt(0.6);

    }

    public ElementPoint getCapPtXZCapsule()
    {
        return this._elementPtXZCapsule;
    }

    public ElementPoint getCapPtYZRoundCut()
    {
        return this._elementPtYZRoundCut;
    }

    public ElementPoint getCapPtYZRoundCutL()
    {
        return this._elementPtYZRoundCutL;
    }




    protected void calculateRadius(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this.calculateRadiusXZ();
                break;
            case DefineSystemConstant.YZView:
                this.calculateRadiusYZ();
                break;
        }
    }


    protected void calculateRadiusXZ()
    {
        this._height = Math.abs(_point3DStartSection1.Z() - _point3DEndSection1.Z());
        this._width = Math.abs(_point3DStartSection1.X() - _point3DEndSection1.X());

        double phi = Math.atan(this._width / this._height);
        this._theta = Math.PI - 2 * phi;
        this._radius = this._height + this._width / Math.tan(this._theta);
    }

    protected void calculateRadiusYZ()
    {
        this._height = Math.abs(_point3DStartSection2.Z() - _point3DEndSection2.Z());
        this._width = Math.abs(_point3DStartSection2.Y() - _point3DEndSection2.Y());

        double phi = Math.atan(this._width / this._height);
        this._theta = Math.PI - 2 * phi;
        this._radius = this._height + this._width / Math.tan(this._theta);
    }

    public void calculateRoundCutPt(double xDivision)
    {
        this.calculateRadiusXZ();
        if( xDivision > _point3DEndSection1.X())
        {
            xDivision = _point3DEndSection1.X();
        }
        else if (xDivision < _point3DStartSection1.X())
        {
            xDivision = _point3DStartSection1.X();
        }
        double width = Math.abs(_point3DStartSection1.X() - xDivision);
        double theta = Math.asin( width / this._radius) ;
        double phi = (Math.PI - theta) /2.0;
        double height = width/Math.tan(phi) ;
        _elementPtYZRoundCut.setCoordinate(xDivision, _point3DStartSection1.Z()  -  height);
        _elementPtYZRoundCutL.setCoordinate(xDivision, _point3DEndSection1.Z());

    }





    protected void setDataToElementXZ()
    {
        if (this._elementPtXZStart == null)
        {
            this._elementPtXZStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Z(), _colorPt ,  this);
        }
        if (this._elementPtXZEnd == null)
        {
            this._elementPtXZEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Z(), _colorPt ,  this);
        }
        this._elmementXZ = new ElementCap(this, this.XZView, _colorBody);
        _isXYupdateNeed = true;
    }

    protected void setDataToElementYZ()
    {
        if (this._elementPtYZStart == null)
        {
            this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt ,  this);
        }
        if (this._elementPtYZEnd == null)
        {
            this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt ,  this);
        }
        this._elmementYZ = new ElementCap(this, this.YZView, _colorBody);
        _isXYupdateNeed = true;
    }
    public double[] getCapStartPt(int sectionNumber)
    {
        double[] result = new double[2];
        switch(sectionNumber)
        {
            case DefineSystemConstant.XZView:
                result[0] = this._point3DStartSection1.X();
                result[1] = this._point3DStartSection1.Z();
                break;
            case DefineSystemConstant.YZView:
                result[0] = this._point3DStartSection1.Y();
                result[1] = this._point3DStartSection1.Z();
                break;

        }
        return result;
    }


    public double getCapHeight(int sectionNumber)
    {
        calculateRadius(sectionNumber);
        return this._height;
    }

    public double getCapRadius(int sectionNumber)
    {
        calculateRadius(sectionNumber);
        return this._radius;
    }

    public double getCapTheta(int sectionNumber)
    {
        calculateRadius(sectionNumber);
        return this._theta;
    }

    public double getCapWidth(int sectionNumber)
    {
        calculateRadius(sectionNumber);
        return this._width;
    }

    private void setCapWidthHeightXZ(double w, double h)
    {
        double endPtX = this._elementPtXZStart.X() + w;
        double endPtY = this._elementPtXZStart.Y() - h;

        this._elementPtXZEnd.setCoordinate(endPtX, endPtY);
    }

    private void setCapRadiusThetaXZ(double r, double t)
    {
        double centerPtX = this._elementPtXZStart.X();
        double centerPtY = this._elementPtXZStart.Y() - r;
        double angle = t * Math.PI / 180;

        double endPtX = centerPtX + r * Math.sin(angle);
        double endPtY = centerPtY + r * Math.cos(angle);

        this._elementPtXZEnd.setCoordinate(endPtX, endPtY);
    }

    private void setCapStartPointXZ(double sPtX, double sPtY)
    {

    }

    private void setCapEndPointXZ(double ePtX, double ePtY)
    {}

    private void setCapWidthHeightYZ(double w, double h)
    {
        double endPtX = this._elementPtYZStart.X() + w;
        double endPtY = this._elementPtYZStart.Y() - h;

        this._elementPtYZEnd.setCoordinate(endPtX, endPtY);
    }

    private void setCapRadiusThetaYZ(double r, double t)
    {
        double centerPtX = this._elementPtYZStart.X();
        double centerPtY = this._elementPtYZStart.Y() - r;
        double angle = t * Math.PI / 180;

        double endPtX = centerPtX + r * Math.sin(angle);
        double endPtY = centerPtY + r * Math.cos(angle);

        this._elementPtYZEnd.setCoordinate(endPtX, endPtY);
    }

    public void setCapWidthHeight(double w, double h, int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                this.setCapWidthHeightXZ(w, h);
                break;
            case DefineSystemConstant.YZView:
                this.setCapWidthHeightYZ(w, h);
                break;
        }
    }

    public void setCapRadiusTheta(double r, double t, int viewStatus)
    {
        switch (viewStatus)
        {
            case DefineSystemConstant.XZView:
                this.setCapRadiusThetaXZ(r, t);
                break;
            case DefineSystemConstant.YZView:
                this.setCapRadiusThetaYZ(r, t);
                break;
        }
    }

    public void setCapStartPoint(double sPtX, double sPtY, int viewStatus)
    {
        switch (viewStatus)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZStart.setCoordinate(sPtX, sPtY);
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZStart.setCoordinate(sPtX, sPtY);
                break;
        }
    }

    public void setCapEndPoint(double ePtX, double ePtY, int viewStatus)
    {
        switch (viewStatus)
        {
            case DefineSystemConstant.XZView:
                this._elementPtXZEnd.setCoordinate(ePtX, ePtY);
                break;
            case DefineSystemConstant.YZView:
                this._elementPtYZEnd.setCoordinate(ePtX, ePtY);
                break;

        }
    }

    protected void setElementXYToData()
    {
    }

    protected void createDataPointPeriodically(stepb_cartesian_point[][] ptSets)
    {
        double rCap = _point3DEndSection1.X();
        double hCenter = _point3DStartSection1.Z();
        double hTransition = _point3DEndSection1.Z();

        int iiCount = this._countRadial;
        int jCount = this._countCircumferential;
        int kCount = this._countPeriodical;

        double r = 0;
        double theta = 0;
        double thetaIntervalK = 2 * Math.PI / kCount;
        double thetaIntervalJ = 2 * Math.PI / kCount / (jCount - 1);

        int loopIncrement = 1000;
        int startNodeNumber = 31001;

        if (jCount * kCount > 1000)
            loopIncrement = 10000;

        for (int k = 0; k < kCount; k++)
        {
            for (int j = 0; j < jCount - 1; j++)
            {
                theta = k * thetaIntervalK + j * thetaIntervalJ;
                if (!this._dataManager.getIsStartFromAxis())
                    theta += thetaIntervalJ / 2.0;
                for (int ii = 0; ii < iiCount; ii++)
                {
                    double phi = Math.atan(rCap / (hCenter - hTransition));
                    double thetaZAll = Math.PI - 2 * phi;
                    double thetaZdelta = thetaZAll / (iiCount - 1);
                    double thetaZ = ii * thetaZdelta;

                    double rCircle = hCenter - hTransition + rCap / Math.tan(thetaZAll);

                    r = rCircle * Math.sin(thetaZ);
                    double x = r * Math.cos(theta);
                    double y = r * Math.sin(theta);
                    double z = hCenter - rCircle * Math.sin(thetaZ) / Math.tan((Math.PI - thetaZ) / 2.0);
                    //int nodeNumber = startNodeNumber + loopIncrement * ii + j + (jCount - 1) * k;

                    if (ptSets[k * (_countCircumferential-1) +j][ii] == null)
                    {
                        stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                        ptSets[k * (_countCircumferential-1) +j][ii] = cPt;
                    }
                    else
                    {
                        //stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                        ptSets[k * (_countCircumferential-1) +j][ii].GetMathVector3d().Set(x, y, z);
                    }
                }
            }
        }


    }

    protected void createDataPointCircumferential(stepb_cartesian_point[][] ptSetsCircumferential)
    {
        double rCap = _point3DEndSection1.X();
        double hCenter = _point3DStartSection1.Z();
        double hTransition = _point3DEndSection1.Z();

        int iiCount = ptSetsCircumferential[0].length;
        int jCount = ptSetsCircumferential.length;

        double r = 0;
        double theta = 0;
        double thetaIntervalJ = 2 * Math.PI / jCount;

        int loopIncrement = 1000;
        int startNodeNumber = 31001;

        if (jCount > 1000)
            loopIncrement = 10000;

        for (int j = 0; j < jCount; j++)
        {
            theta = j * thetaIntervalJ;
            if(!this._dataManager.getIsStartFromAxis())
               theta +=  thetaIntervalJ/2.0;
            ptSetsCircumferential[j] = this.setLinePoint3D_Round(hTransition, rCap, hCenter, theta, iiCount);

        }
    }

    protected void createDataPointCircumferentialRunway(stepb_cartesian_point[][] ptSetsCircumferential)
    {
        if(this._geometryType == this.CAP_TYPE_ROUNDCUT)
        {
            this.createDataPointCircumferential(ptSetsCircumferential);
            return;
        }

        this.detectedIsRoundRunway();

        this.setCircumferentialNumber(true, true);
        this.setRunwayOuter();

        if(this._geometryType == this.CAP_TYPE_CAPSULE )
        {
            this.createDataPointCapsule(ptSetsCircumferential);
            return;
        }


        double hCenter = this._point3DStartSection1.Z();
        double hTransition = this._point3DEndSection1.Z();

        int iiCount = ptSetsCircumferential[0].length;
        int jCount = ptSetsCircumferential.length;

        double capWidthXZ = this._point3DEndSection1.X();
        double capWidthYZ = this._point3DEndSection2.Y();


        for (int j = 0; j < jCount; j++)
        {
            ptSetsCircumferential[j] = this.setLinePoint3D_RunwayFan2(this._elementArrayOuter[j], hTransition, hCenter, capWidthXZ, capWidthYZ, iiCount);
        }

    }


    private void createDataPointCapsule(stepb_cartesian_point[][] ptSetsCapsule)
    {
        int iiCount = ptSetsCapsule[0].length;
        int jCount = ptSetsCapsule.length;
        double hCenter = this._point3DStartSection1.Z();
        double hTransition = this._point3DEndSection1.Z();
        double hemiSphereWidth =this._point3DEndSection2.Y();
        double hemiSphereRadius = this.cauculateRadius(hTransition, hCenter, hemiSphereWidth);
        double thetaZAll = Math.atan(hemiSphereWidth / (hemiSphereRadius - (hCenter - hTransition)));
        double thetaZdelta = thetaZAll / (iiCount - 1);


        //////////1st area
        for (int j = 0; j < this._numberFan / 2; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];
            double deltaX = this._elementArrayOuter[j].X() - this._point3DStartSection1.X();
            double deltaY = this._elementArrayOuter[j].Y();
            double theta = this.calculateTheta(deltaX, deltaY);
            //System.out.println("this._elementPtXZCapsule = " + this._point3DStartSection1.X());
            //System.out.println("J = " + j + "   deltaX = " + deltaX + "   deltaY = " + deltaY);

            for (int ii = 0; ii < iiCount; ii++)
            {
                double thetaZ = ii * thetaZdelta;
                double r = hemiSphereRadius * Math.sin(thetaZ);

                double x = this._point3DStartSection1.X()+ r * Math.cos(theta);
                double y = r * Math.sin(theta);
                double z = hCenter - hemiSphereRadius + hemiSphereRadius * Math.cos(thetaZ);

                stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                ptArray[ii] = cPt;
            }

            ptSetsCapsule[j] = ptArray;
        }
        //////////2nd area
        for (int j = this._numberFan / 2; j < this._numberFan / 2 + this._numberTri + 1; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];

            for (int ii = 0; ii < iiCount; ii++)
            {
                double thetaZ = ii * thetaZdelta;
                double r = hemiSphereRadius * Math.sin(thetaZ);

                double x = this._elementArrayOuter[j].X();
                double y = r ;
                double z = hCenter - hemiSphereRadius + hemiSphereRadius * Math.cos(thetaZ);

                stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                ptArray[ii] = cPt;
            }

            ptSetsCapsule[j] = ptArray;

        }
        //////////3rd area
        for (int j = this._numberFan / 2 + this._numberTri + 1;
                     j < this._numberFan / 2 + this._numberTri + this._numberFan; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];

            double deltaX = this._elementArrayOuter[j].X() + this._point3DStartSection1.X();
            double deltaY = this._elementArrayOuter[j].Y();
            double theta = this.calculateTheta(deltaX, deltaY);

            for (int ii = 0; ii < iiCount; ii++)
            {
                double thetaZ = ii * thetaZdelta;
                double r = hemiSphereRadius * Math.sin(thetaZ);

                double x = -1 * this._point3DStartSection1.X() + r * Math.cos(theta);
                double y = r * Math.sin(theta);
                double z = hCenter - hemiSphereRadius + hemiSphereRadius * Math.cos(thetaZ);

                stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                ptArray[ii] = cPt;
            }

            ptSetsCapsule[j] = ptArray;

        }
        //////////4th area
        for (int j = this._numberFan / 2 + this._numberTri + this._numberFan;
                     j < this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];

            for (int ii = 0; ii < iiCount; ii++)
            {
                double thetaZ = ii * thetaZdelta;
                double r = hemiSphereRadius * Math.sin(thetaZ);

                double x = this._elementArrayOuter[j].X();
                double y = -1 * r;
                double z = hCenter - hemiSphereRadius + hemiSphereRadius * Math.cos(thetaZ);

                stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                ptArray[ii] = cPt;
            }

            ptSetsCapsule[j] = ptArray;

        }
        //////////5th area
        for (int j = this._numberFan / 2 + 2 * this._numberTri + this._numberFan + 1; j < jCount; j++)
        {
            stepb_cartesian_point ptArray[] = new stepb_cartesian_point[iiCount];

            double deltaX = this._elementArrayOuter[j].X() - this._point3DStartSection1.X();
            double deltaY = this._elementArrayOuter[j].Y();
            double theta = this.calculateTheta(deltaX, deltaY);

            for (int ii = 0; ii < iiCount; ii++)
            {
                double thetaZ = ii * thetaZdelta;
                double r = hemiSphereRadius * Math.sin(thetaZ);

                double x = this._point3DStartSection1.X() + r * Math.cos(theta);
                double y = r * Math.sin(theta);
                double z = hCenter - hemiSphereRadius + hemiSphereRadius * Math.cos(thetaZ);

                stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
                ptArray[ii] = cPt;
            }

            ptSetsCapsule[j] = ptArray;

        }
    }

    private stepb_cartesian_point[] setLinePoint3D_RunwayFan2(ElementPoint endPt, double hTransition,
            double hCenter, double capWidthXZ, double capWidthYZ, int iiCount)
    {
        stepb_cartesian_point ptsArray[] = new stepb_cartesian_point[iiCount];


        double capRadiusXZ = this.cauculateRadius(hTransition, hCenter, capWidthXZ);
        double capRadiusYZ = this.cauculateRadius(hTransition, hCenter, capWidthYZ);

        double xCoord = endPt.X();
        double yCoord = endPt.Y();
        double theta = this.calculateTheta(xCoord, yCoord);
        double capWidthVaried = Math.sqrt(xCoord * xCoord + yCoord * yCoord);

        double capRadiusVaried;

        double h = hCenter - hTransition;
        if(capWidthXZ - capWidthYZ != 0)
            capRadiusVaried = (Math.pow(h,2) + Math.pow(capWidthVaried,2)) / 2 / h;
        else
            capRadiusVaried = capRadiusXZ;


        double thetaZAll = Math.atan(capWidthVaried / (capRadiusVaried - (hCenter - hTransition)));
        double thetaZdelta = thetaZAll / (iiCount - 1);

        for (int ii = 0; ii < iiCount; ii++)
        {
            double thetaZ = ii * thetaZdelta;
            double r = capRadiusVaried * Math.sin(thetaZ);

            double x = r * Math.cos(theta);
            double y = r * Math.sin(theta);
                        double z = hCenter - capRadiusVaried + capRadiusVaried * Math.cos(thetaZ);

            stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
            ptsArray[ii] = cPt;
        }

        return ptsArray;
    }

    private stepb_cartesian_point[] setLinePoint3D_Round(double hTransition, double rCap,
            double hCenter, double theta, int iiCount)
    {
        stepb_cartesian_point ptsArray[] = new stepb_cartesian_point[iiCount];

        double r = 0;

        for (int ii = 0; ii < iiCount; ii++)
        {
            double phi = Math.atan(rCap / (hCenter - hTransition));
            double thetaZAll = Math.PI - 2 * phi;
            double thetaZdelta = thetaZAll / (iiCount - 1);
            double thetaZ = ii * thetaZdelta;

            double rCircle = hCenter - hTransition + rCap / Math.tan(thetaZAll);

            r = rCircle * Math.sin(thetaZ);
            double x = r * Math.cos(theta);
            double y = r * Math.sin(theta);
            double z = hCenter - rCircle * Math.sin(thetaZ) / Math.tan((Math.PI - thetaZ) / 2.0);
            if (this._geometryType == this.CAP_TYPE_ROUNDCUT)
            {
                double yLimit = this._elementPtYZRoundCut.X();

                if (Math.abs(y) > yLimit)
                {
                    if (y > 0)
                        y = yLimit;
                    if (y < 0)
                        y = -yLimit;
                }
            }


            stepb_cartesian_point cPt = new stepb_cartesian_point(x, y, z);
            ptsArray[ii] = cPt;
        }
        return ptsArray;
    }

    private double cauculateRadius(double hTransition, double hCenter, double rWidth)
    {
        double phi = Math.atan(rWidth / (hCenter - hTransition));
        double thetaZAll = Math.PI - 2 * phi;
        double rCircle = hCenter - hTransition + rWidth / Math.tan(thetaZAll);
        return rCircle;
    }

    /**
     * Save All Data into the FileWirter, don't need to concern about the file
     * address
     *
     * @param fw FileWriter FilePath will be assigned in this argument
     * @throws IOException
     */
    public void save(FileWriter fw) throws IOException
    {
        String className = this.getClass().getCanonicalName();
        fw.write("**********************"+className+"**********************\n");


        double capStartPtX = this._point3DStartSection1.X();
        double capStartPtY = this._point3DStartSection1.Y();
        double capStartPtZ = this._point3DStartSection1.Z();
        double capHeightXZ = this.getCapHeight(sdt.define.DefineSystemConstant.XZView);
        double capWidthXZ = this.getCapWidth(sdt.define.DefineSystemConstant.XZView);
        double capRadiusXZ = this.getCapRadius(sdt.define.DefineSystemConstant.XZView);
        double capThetaXZ = this.getCapTheta(sdt.define.DefineSystemConstant.XZView) * 180 / Math.PI;
        double capHeightYZ = this.getCapHeight(sdt.define.DefineSystemConstant.YZView);
        double capWidthYZ = this.getCapWidth(sdt.define.DefineSystemConstant.YZView);
        double capRadiusYZ = this.getCapRadius(sdt.define.DefineSystemConstant.YZView);
        double capThetaYZ = this.getCapTheta(sdt.define.DefineSystemConstant.YZView) * 180 / Math.PI;


        fw.write("capStartPtX : , " + capStartPtX  + "\n");
        fw.write("capStartPtY : , " + capStartPtY  + "\n");
        fw.write("capStartPtZ : , " + capStartPtZ  + "\n");
        //Cap XZ Section
        fw.write("HeightXZ: , " + capHeightXZ + "\n");
        fw.write("WidthXZ: , " + capWidthXZ + "\n");
        fw.write("RadiusXZ: , " + capRadiusXZ + "\n");
        fw.write("ThetaXZ: , " + capThetaXZ + "\n");

        //Cap YZ Section
        fw.write("HeightYZ: , " + capHeightYZ + "\n");
        fw.write("WidthYZ: , " + capWidthYZ + "\n");
        fw.write("RadiusYZ: , " + capRadiusYZ + "\n");
        fw.write("ThetaYZ: , " + capThetaYZ + "\n");

        //Cap Mesh Count
        fw.write("          Is Perodical: , " + this._isPerodical           + "\n");
        fw.write("          Count Radial: , " + this._countRadial           + "\n");
        fw.write(" Count Circumferential: , " + this._countCircumferential  + "\n");
        fw.write("      Count Periodical: , " + this._countPeriodical       + "\n");

        fw.write("             Thickness: , " + this._thickness       + "\n");
        this._material.save(fw);
    }

    public void open(BufferedReader br) throws IOException
    {
        //Cap parameter
        br.readLine().trim();


        double capStartPtX =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capStartPtY =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capStartPtZ =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capHeightXZ =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capWidthXZ  =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capRadiusXZ =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capThetaXZ  =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capHeightYZ =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capWidthYZ  =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capRadiusYZ =              Double.parseDouble(this.readLastString(br.readLine().trim()));
        double capThetaYZ  =              Double.parseDouble(this.readLastString(br.readLine().trim()));


        this._isPerodical            =       Boolean.parseBoolean(this.readLastString(br.readLine().trim()));//2_9
        this._countRadial            =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countCircumferential   =       Integer.parseInt(this.readLastString(br.readLine().trim()));
        this._countPeriodical        =       Integer.parseInt(this.readLastString(br.readLine().trim()));

        this._thickness              =        Double.parseDouble(this.readLastString(br.readLine().trim()));

        this._point3DStartSection1.GetMathVector3d().Set( capStartPtX,
                                                          capStartPtY,
                                                          capStartPtZ );
        if(_elementPtXZStart != null)
            this._elementPtXZStart.setCoordinate(capStartPtX,capStartPtZ);
        if(_elementPtYZStart != null)
            this._elementPtYZStart.setCoordinate(capStartPtY,capStartPtZ);

        this.setCapWidthHeight(capWidthXZ, capHeightXZ, sdt.define.DefineSystemConstant.XZView);
        this.setCapRadiusTheta(capRadiusXZ, capThetaXZ, sdt.define.DefineSystemConstant.XZView);

        this.setCapWidthHeight(capWidthYZ, capHeightYZ, sdt.define.DefineSystemConstant.YZView);
        this.setCapRadiusTheta(capRadiusYZ, capThetaYZ, sdt.define.DefineSystemConstant.XZView);
        this._material.open(br);
    }
    public int getNumberFan()
    {
        return this._numberFan;
    }
    public int getNumberTri()
    {
        return this._numberTri;
    }



}
