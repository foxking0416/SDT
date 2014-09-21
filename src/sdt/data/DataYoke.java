package sdt.data;

import java.io.*;

import sdt.define.*;
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
public class DataYoke extends DataMagnet
{

    //int _yokeType;

    protected stepb_cartesian_point _point3DMiddleSection1 = null;
    protected stepb_cartesian_point _point3DMiddleSection2 = null;

    protected ElementPoint _elementPtXZMiddleUp;
    protected ElementPoint _elementPtXZMiddleDown;
    protected ElementPoint _elementPtYZMiddleUp;
    protected ElementPoint _elementPtYZMiddleDown;


    public DataYoke(SDT_DataManager dataManger)
    {
        super(dataManger);

        _dataType = this.TYPE_YOKE;
        /*_point3DStartSection1 = new stepb_cartesian_point(0, 0, -2.0);
        _point3DMiddleSection1 = new stepb_cartesian_point(1.5, 0, -1);
        _point3DEndSection1 = new stepb_cartesian_point(2, 0, -2.5);
        _point3DStartSection2 = new stepb_cartesian_point(0, 0, -2.0);
        _point3DMiddleSection2 = new stepb_cartesian_point( 0,1.5, -1);
        _point3DEndSection2 = new stepb_cartesian_point( 0, 2, -2.5);*/

        //this._colorBody        = this._dataManager.getColorYokeBase();
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);
    }




    protected void setDataToElementXZ()
    {
        if (this._elementPtXZStart == null)
            this._elementPtXZStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Z(), _colorPt, this);

        if (this._elementPtXZEnd == null)
            this._elementPtXZEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Z(), _colorPt, this);

        if (this._elementPtXZLeftDown == null)
            this._elementPtXZLeftDown = new ElementPoint(_point3DStartSection1.X(), _point3DEndSection1.Z(), _colorPt, this);

        if (this._elementPtXZRightUp == null)
            this._elementPtXZRightUp = new ElementPoint(_point3DEndSection1.X(), this._point3DMiddleSection1.Z(), _colorPt, this);

        if (this._elementPtXZMiddleUp == null)
            this._elementPtXZMiddleUp = new ElementPoint(this._point3DMiddleSection1.X(), this._point3DMiddleSection1.Z(), _colorPt, this);

        if (this._elementPtXZMiddleDown == null)
            this._elementPtXZMiddleDown = new ElementPoint(this._point3DMiddleSection1.X(), _point3DStartSection1.Z(), _colorPt, this);

        this._elmementXZ = new ElementYoke(this, this.XZView, _colorBody);
        _isXYupdateNeed = true;
    }

    protected void setDataToElementYZ()
    {
        if (this._elementPtYZStart == null)
            this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt, this);

        if (this._elementPtYZEnd == null)
            this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt, this);

        if (this._elementPtYZLeftDown == null)
            this._elementPtYZLeftDown = new ElementPoint(_point3DStartSection2.Y(),  _point3DEndSection2.Z(), _colorPt, this);

        if (this._elementPtYZRightUp == null)
            this._elementPtYZRightUp = new ElementPoint(_point3DEndSection2.Y(), this._point3DMiddleSection2.Z(), _colorPt, this);

        if (this._elementPtYZMiddleUp == null)
            this._elementPtYZMiddleUp = new ElementPoint(this._point3DMiddleSection2.Y(), this._point3DMiddleSection2.Z(), _colorPt, this);

        if (this._elementPtYZMiddleDown == null)
            this._elementPtYZMiddleDown = new ElementPoint(this._point3DMiddleSection2.Y(),  _point3DStartSection2.Z(), _colorPt, this);

        this._elmementYZ = new ElementYoke(this, this.YZView, _colorBody);
        _isXYupdateNeed = true;
    }

    /**
     * 將XZ_View的Start Point及End Point參數寫進DataBase
     * 包含XZ_View的X, Z座標值以及連帶影響YZ_View的Z座標值
     * @return boolean true: 成功寫入  false: 超過尺寸限制，寫入失敗
     */
    protected boolean setElementXZToData()
    {
       /* boolean isDrawAble = true;

        double newX1 = 0;
        double newX2 = 0;
        double newY1 = 0;
        double newY2 = 0;
        double oldX1 = 0;
        double oldX2 = 0;
        double oldY1 = 0;
        double oldY2 = 0;
        double newX3 = 0;
        double newY3 = 0;
        double oldX3 = 0;
        double oldY3 = 0;


        newX1 = this._elementPtXZStart.X();
        newX2 = this._elementPtXZEnd.X();
        newY1 = this._elementPtXZStart.Y();
        newY2 = this._elementPtXZEnd.Y();
        newX3 = this._elementPtXZMiddleUp.X();
        newY3 = this._elementPtXZMiddleUp.Y();

System.out.println("new elementPtXZEnd Y:" + newY2);
        this._point3DStartSection1.GetMathVector3d().SetX(newX1);
        this._point3DEndSection1.GetMathVector3d().SetX(newX2);
        this._point3DMiddleSection1.GetMathVector3d().SetX(newX3);

        this._point3DStartSection1.GetMathVector3d().SetZ(newY1);
        this._point3DEndSection1.GetMathVector3d().SetZ(newY2);
        this._point3DMiddleSection1.GetMathVector3d().SetZ(newY3);

        this._point3DStartSection2.GetMathVector3d().SetZ(newY1);
        this._point3DEndSection2.GetMathVector3d().SetZ(newY2);
        this._point3DMiddleSection2.GetMathVector3d().SetZ(newY3);

        System.out.println("_isRunway: " + _isRunway);
        if (!_isRunway)//如果是圓形的話，XZ_View的X值須等於YZ_View的Y值
        {
            this._point3DStartSection2.GetMathVector3d().SetY(newX1);
            this._point3DEndSection2.GetMathVector3d().SetY(newX2);
            this._point3DMiddleSection2.GetMathVector3d().SetY(newX3);
        }

        if (this._elmementYZ == null)
        {
            this.setDataToElementYZ();
        }

        oldX1 = this._elementPtYZStart.X();
        oldX2 = this._elementPtYZEnd.X();
        oldY1 = this._elementPtYZStart.Y();
        oldY2 = this._elementPtYZEnd.Y();
        oldX3 = this._elementPtYZMiddleUp.X();
        oldY3 = this._elementPtYZMiddleUp.Y();
        System.out.println("this._elementPtYZEnd: ( " +oldX2 + ", "+  oldY2 + ")");
        System.out.println("this._elementPtYZEnd: ( " +oldX2 + ", "+  oldY2 + ")");
        if (_isRunway)
        {
            this._elementPtYZStart.setCoordinate(oldX1, newY1);
            this._elementPtYZEnd.setCoordinate(oldX2, newY2);
            this._elementPtYZMiddleUp.setCoordinate(oldX3, newY3);
            System.out.println("this._elementPtYZEnd(New): ( " +oldX2 + ", "+  newY2 + ")");

        }
        else
        {
            this._elementPtYZStart.setCoordinate(newX1, newY1);
            this._elementPtYZEnd.setCoordinate(newX2, newY2);
            this._elementPtYZMiddleUp.setCoordinate(newX3, newY3);
            System.out.println("this._elementPtYZEnd(New): ( " +newX2 + ", "+  newY2 + ")");
        }

       if (this._elmementYZ.setBoundary())
        {
            System.out.println("elementYZ Can't draw");
            this._elementPtYZStart.setCoordinate(oldX1, oldY1);
            this._elementPtYZEnd.setCoordinate(oldX2, oldY2);
            this._elementPtYZMiddleUp.setCoordinate(oldX3, oldY3);

            this._elementPtXZStart.setCoordinate(newX1, oldY1);
            this._elementPtXZEnd.setCoordinate(newX2, oldY2);
            this._elementPtXZMiddleUp.setCoordinate(newX3, oldY3);

            this._point3DStartSection1.GetMathVector3d().SetZ(oldY1);
            this._point3DEndSection1.GetMathVector3d().SetZ(oldY2);
            this._point3DMiddleSection1.GetMathVector3d().SetZ(oldY3);

            this._point3DStartSection2.GetMathVector3d().SetZ(oldY1);
            this._point3DEndSection2.GetMathVector3d().SetZ(oldY2);
            this._point3DMiddleSection2.GetMathVector3d().SetZ(oldY3);

            isDrawAble = false;
        }
        return isDrawAble;
*/



        boolean isDrawAble = true;

        isDrawAble = super.setElementXZToData();
        if(!isDrawAble)
            return isDrawAble;


        double newX3 = 0;
        double newY3 = 0;
        double oldX3 = 0;
        double oldY3 = 0;

        newX3 = this._elementPtXZMiddleUp.X();
        newY3 = this._elementPtXZMiddleUp.Y();


        this._point3DMiddleSection1.GetMathVector3d().SetX(newX3);
        this._point3DMiddleSection1.GetMathVector3d().SetZ(newY3);

        this._point3DMiddleSection2.GetMathVector3d().SetZ(newY3);

        if (!_isRunway) //如果是圓形的話，XZ_View的X值須等於YZ_View的Y值
        {
            this._point3DMiddleSection2.GetMathVector3d().SetY(newX3);
        }

        if (this._elmementYZ == null)
        {
            this.setDataToElementYZ();
        }

        oldX3 = this._elementPtYZMiddleUp.X();
        oldY3 = this._elementPtYZMiddleUp.Y();

        if (_isRunway)
        {
            this._elementPtYZMiddleUp.setCoordinate(oldX3, newY3);

        }
        else
        {
            this._elementPtYZMiddleUp.setCoordinate(newX3, newY3);
        }

        if (this._elmementYZ.setBoundary())
        {
            System.out.println("elementYZ Can't draw");
            this._elementPtYZMiddleUp.setCoordinate(oldX3, oldY3);

            this._elementPtXZMiddleUp.setCoordinate(newX3, oldY3);

            this._point3DMiddleSection1.GetMathVector3d().SetZ(oldY3);

            this._point3DMiddleSection2.GetMathVector3d().SetZ(oldY3);
            isDrawAble = false;
        }
        return isDrawAble;

    }



    protected boolean setElementYZToData()
    {
        boolean isDrawAble = true;
        isDrawAble = super.setElementYZToData();
        if(!isDrawAble)
            return isDrawAble;
        double newX3 = 0;
        double newY3 = 0;
        double oldX3 = 0;
        double oldY3 = 0;

        newX3 = this._elementPtYZMiddleUp.X();
        newY3 = this._elementPtYZMiddleUp.Y();

        this._point3DMiddleSection2.GetMathVector3d().SetY(newX3);

        this._point3DMiddleSection2.GetMathVector3d().SetZ(newY3);

        this._point3DMiddleSection1.GetMathVector3d().SetZ(newY3);
        if (!_isRunway)
        {
            this._point3DMiddleSection1.GetMathVector3d().SetX(newX3);
        }

        if (this._elmementXZ == null)
        {
            this.setDataToElementXZ();
        }
        oldX3 = this._elementPtXZMiddleUp.X();
        oldY3 = this._elementPtXZMiddleUp.Y();
        if (_isRunway)
        {
            this._elementPtXZMiddleUp.setCoordinate(oldX3, newY3);
        }
        else
        {
            this._elementPtXZMiddleUp.setCoordinate(newX3, newY3);
        }

        if (this._elmementXZ.setBoundary())
        {
            this._elementPtXZMiddleUp.setCoordinate(oldX3, oldY3);

            this._elementPtXZMiddleUp.setCoordinate(newX3, oldY3);

            this._point3DMiddleSection2.GetMathVector3d().SetZ(oldY3);

            this._point3DMiddleSection1.GetMathVector3d().SetZ(oldY3);
            isDrawAble = false;
        }
        return isDrawAble;
    }





    public ElementPoint getElementPointMiddleUp(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZMiddleUp;

            case DefineSystemConstant.YZView:
                return this._elementPtYZMiddleUp;

            case DefineSystemConstant.XYView:
                return null;

            default:
                return null;
        }
    }

    public ElementPoint getElementPointMiddleDown(int sectionNumber)
    {
        switch (sectionNumber)
        {
            case DefineSystemConstant.XZView:
                return this._elementPtXZMiddleDown;

            case DefineSystemConstant.YZView:
                return this._elementPtYZMiddleDown;

            case DefineSystemConstant.XYView:
                return null;

            default:
                return null;
        }
    }
    public void setElementPointMiddleCoordinate(int sectionNumber, double x, double y)
   {
       switch (sectionNumber)
       {
           case DefineSystemConstant.XZView:
               if (this._elementPtXZMiddleUp != null)
               {
                   this._elementPtXZMiddleUp.setCoordinate(x, y);
               }
               break;

           case DefineSystemConstant.YZView:
               if (this._elementPtYZMiddleUp != null)
               {
                   this._elementPtYZMiddleUp.setCoordinate(x, y);
               }
               break;

           case DefineSystemConstant.XYView:

               break;
       }
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

       double startPtX_XZ = _point3DStartSection1.X();
       double startPtY_XZ = _point3DStartSection1.Y();
       double startPtZ_XZ = _point3DStartSection1.Z();
       double endPtX_XZ = _point3DEndSection1.X();
       double endPtY_XZ = _point3DEndSection1.Y();
       double endPtZ_XZ = _point3DEndSection1.Z();
       double middlePtX_XZ = _point3DMiddleSection1.X();
       double middlePtY_XZ = _point3DMiddleSection1.Y();
       double middlePtZ_XZ = _point3DMiddleSection1.Z();

       double startPtX_YZ = _point3DStartSection2.X();
       double startPtY_YZ = _point3DStartSection2.Y();
       double startPtZ_YZ = _point3DStartSection2.Z();
       double endPtX_YZ = _point3DEndSection2.X();
       double endPtY_YZ = _point3DEndSection2.Y();
       double endPtZ_YZ = _point3DEndSection2.Z();
       double middlePtX_YZ = _point3DMiddleSection2.X();
       double middlePtY_YZ = _point3DMiddleSection2.Y();
       double middlePtZ_YZ = _point3DMiddleSection2.Z();

       fw.write("     Status: , " + this._geometryType+ "\n");
       //StartPt XZ Section
       fw.write("startPtX_XZ: , " + startPtX_XZ + "\n");
       fw.write("startPtY_XZ: , " + startPtY_XZ + "\n");
       fw.write("startPtZ_XZ: , " + startPtZ_XZ + "\n");

       //EndPt XZ Section
       fw.write("endPtX_XZ: , " + endPtX_XZ + "\n");
       fw.write("endPtY_XZ: , " + endPtY_XZ + "\n");
       fw.write("endPtZ_XZ: , " + endPtZ_XZ + "\n");

       //MiddlePt XZ Section
       fw.write("middlePtX_XZ: , " + middlePtX_XZ  + "\n");
       fw.write("middlePtY_XZ: , " + middlePtY_XZ  + "\n");
       fw.write("middlePtZ_XZ: , " + middlePtZ_XZ  + "\n");

       //StartPt YZ Section
       fw.write("startPtX_YZ: , " + startPtX_YZ + "\n");
       fw.write("startPtY_YZ: , " + startPtY_YZ + "\n");
       fw.write("startPtZ_YZ: , " + startPtZ_YZ + "\n");

       //EndPt YZ Section
       fw.write("endPtX_YZ: , " + endPtX_YZ + "\n");
       fw.write("endPtY_YZ: , " + endPtY_YZ + "\n");
       fw.write("endPtZ_YZ: , " + endPtZ_YZ + "\n");

       //MiddlePt YZ Section
       fw.write("middlePtX_YZ: , " + middlePtX_YZ  + "\n");
       fw.write("middlePtY_YZ: , " + middlePtY_YZ  + "\n");
       fw.write("middlePtZ_YZ: , " + middlePtZ_YZ  + "\n");


       this._material.save(fw);
   }

   /**
       * Open Data into the BufferedReader, don't need to concern about the file
       * address
       *
       * @param fw BufferedReader FilePath will be assigned in this argument
       * @throws IOException
    */
   public void open(BufferedReader br) throws IOException
   {
       //Magnet parameter
       br.readLine().trim();

       this._geometryType    = Integer.parseInt(this.readLastString(br.readLine().trim()));
       double startPtX_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double startPtY_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double startPtZ_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double endPtX_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double endPtY_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double endPtZ_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double middlePtX_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double middlePtY_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double middlePtZ_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));

       double startPtX_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double startPtY_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double startPtZ_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double endPtX_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double endPtY_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double endPtZ_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double middlePtX_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double middlePtY_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
       double middlePtZ_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));


       this._point3DStartSection1.GetMathVector3d().Set(startPtX_XZ, startPtY_XZ, startPtZ_XZ);
       this._point3DEndSection1.GetMathVector3d().Set(endPtX_XZ, endPtY_XZ, endPtZ_XZ);
       this._point3DMiddleSection1.GetMathVector3d().Set(middlePtX_XZ, middlePtY_XZ, middlePtZ_XZ);
       this._point3DStartSection2.GetMathVector3d().Set(startPtX_YZ, startPtY_YZ, startPtZ_YZ);
       this._point3DEndSection2.GetMathVector3d().Set(endPtX_YZ, endPtY_YZ, endPtZ_YZ);
       this._point3DMiddleSection2.GetMathVector3d().Set(middlePtX_YZ, middlePtY_YZ, middlePtZ_YZ);


       this._elementPtXZStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Z(), _colorPt, this);
       this._elementPtXZEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Z(), _colorPt, this);
       this._elementPtXZLeftDown = new ElementPoint(this._elementPtXZStart.X(), this._elementPtXZEnd.Y(), _colorPt, this);
       this._elementPtXZRightUp = new ElementPoint(this._elementPtXZEnd.X(), this._elementPtXZStart.Y(), _colorPt, this);
       this._elementPtXZMiddleUp = new ElementPoint(this._point3DMiddleSection1.X(), this._point3DMiddleSection1.Z(), _colorPt, this);
       this._elementPtXZMiddleDown = new ElementPoint(this._point3DMiddleSection1.X(), this._elementPtXZStart.Y(), _colorPt, this);


       this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt, this);
       this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt, this);
       this._elementPtYZLeftDown = new ElementPoint(this._elementPtYZStart.X(), this._elementPtYZEnd.Y(), _colorPt, this);
       this._elementPtYZRightUp = new ElementPoint(this._elementPtYZEnd.X(), this._elementPtYZStart.Y(), _colorPt, this);
       this._elementPtYZMiddleUp = new ElementPoint(this._point3DMiddleSection2.Y(), this._point3DMiddleSection2.Z(), _colorPt, this);
       this._elementPtYZMiddleDown = new ElementPoint(this._point3DMiddleSection2.Y(), this._elementPtYZStart.Y(), _colorPt, this);


       this._material.open(br);
   }



}

