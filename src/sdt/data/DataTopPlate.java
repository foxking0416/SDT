package sdt.data;

import sdt.stepb.stepb_cartesian_point;
import sdt.geometry.element.ElementMagnet;
import sdt.geometry.element.ElementPoint;
import sdt.geometry.element.ElementTopPlate;

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
public class DataTopPlate extends DataMagnet
{
    public DataTopPlate(SDT_DataManager dataManger)
    {
        super(dataManger);

        _dataType = this.TYPE_TOPPLATE;
        _point3DStartSection1 = new stepb_cartesian_point(0, 0, -1);
        _point3DEndSection1 = new stepb_cartesian_point(1.2, 0, -1.5);
        _point3DStartSection2 = new stepb_cartesian_point(0, 0, -1);
        _point3DEndSection2 = new stepb_cartesian_point(0, 1.2, -1.5);

        this._colorBody      = this._dataManager.getColorTopPlate();
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);
    }



    protected void setDataToElementXZ()
    {
        /*if (this._elementPtXZStart == null)
            this._elementPtXZStart = new ElementPoint(_point3DStartSection1.X(), _point3DStartSection1.Z(), _colorPt);
               if (this._elementPtXZEnd == null)
            this._elementPtXZEnd = new ElementPoint(_point3DEndSection1.X(), _point3DEndSection1.Z(), _colorPt);
               if (this._elementPtXZLeftDown == null)
            this._elementPtXZLeftDown = new ElementPoint(this._elementPtXZStart.X(), this._elementPtXZEnd.Y(), _colorPt);
         if (this._elementPtXZRightUp == null)
             this._elementPtXZRightUp = new ElementPoint(this._elementPtXZEnd.X(), this._elementPtXZStart.Y(), _colorPt);
         */

        super.setDataToElementXZ();
        this._elmementXZ = new ElementTopPlate(this, this.XZView, _colorBody);
        _isXYupdateNeed = true;
    }

    protected void setDataToElementYZ()
    {
        /*
               if (this._elementPtYZStart == null)
            this._elementPtYZStart = new ElementPoint(_point3DStartSection2.Y(), _point3DStartSection2.Z(), _colorPt);
               if (this._elementPtYZEnd == null)
            this._elementPtYZEnd = new ElementPoint(_point3DEndSection2.Y(), _point3DEndSection2.Z(), _colorPt);
               if (this._elementPtYZLeftDown == null)
            this._elementPtYZLeftDown = new ElementPoint(this._elementPtYZStart.X(), this._elementPtYZEnd.Y(), _colorPt);
               if (this._elementPtYZRightUp == null)
            this._elementPtYZRightUp = new ElementPoint(this._elementPtYZEnd.X(), this._elementPtYZStart.Y(), _colorPt);
         */
        super.setDataToElementYZ();
        this._elmementYZ = new ElementTopPlate(this, this.YZView, _colorBody);
        _isXYupdateNeed = true;
    }

}
