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
public class DataMagnetOuter extends DataMagnet
{

    public DataMagnetOuter(SDT_DataManager dataManger)
    {
        super(dataManger);

        _dataType = this.TYPE_MAGNETOUTER;
        _point3DStartSection1 = new stepb_cartesian_point(1.8, 0, -0.75);
        _point3DEndSection1 = new stepb_cartesian_point(2.25, 0, -1);
        _point3DStartSection2 = new stepb_cartesian_point(0, 1.8, -0.75);
        _point3DEndSection2 = new stepb_cartesian_point(0, 2.25, -1);

        this._colorBody      = this._dataManager.getColorMagnetOuter();
        this._geometryType  = DefineSystemConstant.MAGNETOUTER_TYPE1;
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);

    }



    protected void setDataToElementXZ()
    {
        super.setDataToElementXZ();

        this._elmementXZ = new ElementMagnet(this, this.XZView, _colorBody);
        this._elmementXZ.setElementType(DefineSystemConstant.TYPE_MAGNETOUTER);
        _isXYupdateNeed = true;

    }

    protected void setDataToElementYZ()
    {
       super.setDataToElementYZ();

       this._elmementYZ = new ElementMagnet(this, this.YZView, _colorBody);
       this._elmementYZ.setElementType(DefineSystemConstant.TYPE_MAGNETOUTER);
       _isXYupdateNeed = true;

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

       double startPtX_YZ = _point3DStartSection2.X();
       double startPtY_YZ = _point3DStartSection2.Y();
       double startPtZ_YZ = _point3DStartSection2.Z();
       double endPtX_YZ = _point3DEndSection2.X();
       double endPtY_YZ = _point3DEndSection2.Y();
       double endPtZ_YZ = _point3DEndSection2.Z();


       fw.write("     Status: , " + this._geometryType+ "\n");
       //StartPt XZ Section
       fw.write("startPtX_XZ: , " + startPtX_XZ + "\n");
       fw.write("startPtY_XZ: , " + startPtY_XZ + "\n");
       fw.write("startPtZ_XZ: , " + startPtZ_XZ + "\n");

       //EndPt XZ Section
       fw.write("endPtX_XZ: , " + endPtX_XZ + "\n");
       fw.write("endPtY_XZ: , " + endPtY_XZ + "\n");
       fw.write("endPtZ_XZ: , " + endPtZ_XZ + "\n");

       //StartPt YZ Section
       fw.write("startPtX_YZ: , " + startPtX_YZ + "\n");
       fw.write("startPtY_YZ: , " + startPtY_YZ + "\n");
       fw.write("startPtZ_YZ: , " + startPtZ_YZ + "\n");

       //EndPt YZ Section
       fw.write("endPtX_YZ: , " + endPtX_YZ + "\n");
       fw.write("endPtY_YZ: , " + endPtY_YZ + "\n");
       fw.write("endPtZ_YZ: , " + endPtZ_YZ + "\n");

       this._material.save(fw);

    }
    public void open(BufferedReader br) throws IOException
    {
        //Magnet parameter
        br.readLine().trim();


        this._geometryType = Integer.parseInt(this.readLastString(br.readLine().trim()));
        double startPtX_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double startPtY_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double startPtZ_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double endPtX_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double endPtY_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double endPtZ_XZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double startPtX_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double startPtY_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double startPtZ_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double endPtX_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double endPtY_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));
        double endPtZ_YZ = Double.parseDouble(this.readLastString(br.readLine().trim()));

        this._point3DStartSection1.GetMathVector3d().Set(startPtX_XZ, startPtY_XZ, startPtZ_XZ);
        this._point3DEndSection1.GetMathVector3d().Set(endPtX_XZ, endPtY_XZ, endPtZ_XZ);
        this._point3DStartSection2.GetMathVector3d().Set(startPtX_YZ, startPtY_YZ, startPtZ_YZ);
        this._point3DEndSection2.GetMathVector3d().Set(endPtX_YZ, endPtY_YZ, endPtZ_YZ);

        this._elementPtXZStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Z(), _colorPt, this);
        this._elementPtXZEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Z(), _colorPt, this);
        this._elementPtXZLeftDown = new ElementPoint(this._elementPtXZStart.X(), this._elementPtXZEnd.Y(), _colorPt, this);
        this._elementPtXZRightUp = new ElementPoint(this._elementPtXZEnd.X(), this._elementPtXZStart.Y(), _colorPt, this);

        this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt, this);
        this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt, this);
        this._elementPtYZLeftDown = new ElementPoint(this._elementPtYZStart.X(), this._elementPtYZEnd.Y(), _colorPt, this);
        this._elementPtYZRightUp = new ElementPoint(this._elementPtYZEnd.X(), this._elementPtYZStart.Y(), _colorPt, this);

        this._material.open(br);
    }

}
