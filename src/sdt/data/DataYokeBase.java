package sdt.data;

import sdt.stepb.stepb_cartesian_point;
import sdt.geometry.element.ElementYoke;
import sdt.define.DefineSystemConstant;

public class DataYokeBase extends DataYoke
{
    public DataYokeBase(SDT_DataManager dataManger)
    {
        super(dataManger);

        _dataType = this.TYPE_YOKEBASE;
        _point3DStartSection1 = new stepb_cartesian_point(0, 0, -2.0);
        _point3DMiddleSection1 = new stepb_cartesian_point(1.5, 0, -1.25);
        _point3DEndSection1 = new stepb_cartesian_point(2, 0, -2.5);
        _point3DStartSection2 = new stepb_cartesian_point(0, 0, -2.0);
        _point3DMiddleSection2 = new stepb_cartesian_point(0, 1.5, -1.25);
        _point3DEndSection2 = new stepb_cartesian_point(0, 2, -2.5);

        this._colorBody = this._dataManager.getColorYokeBase();
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);

    }
    public void setDataToElementXZ()
    {
        super.setDataToElementXZ();
        this._elmementXZ = new ElementYoke(this, this.XZView, _colorBody);
        this._elmementXZ.setElementType(DefineSystemConstant.TYPE_YOKEBASE);
    }

    public void setDataToElementYZ()
    {
        super.setDataToElementYZ();
        this._elmementYZ = new ElementYoke(this, this.YZView, _colorBody);
        this._elmementYZ.setElementType(DefineSystemConstant.TYPE_YOKEBASE);
    }





}
