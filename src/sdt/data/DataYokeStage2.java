package sdt.data;

import sdt.stepb.stepb_cartesian_point;
import sdt.geometry.element.ElementYoke;
import sdt.define.DefineSystemConstant;

public class DataYokeStage2 extends DataYoke
{
    public DataYokeStage2(SDT_DataManager dataManger)
    {
        super(dataManger);

        _dataType = this.TYPE_YOKESTAGE2;
        _point3DStartSection1 = new stepb_cartesian_point(2.25, 0, -0.25);
        _point3DMiddleSection1 = new stepb_cartesian_point(2.5, 0, 0);
        _point3DEndSection1 = new stepb_cartesian_point(3.0, 0, -0.5);
        _point3DStartSection2 = new stepb_cartesian_point(0, 2.25, -0.25);
        _point3DMiddleSection2 = new stepb_cartesian_point(0, 2.5, 0);
        _point3DEndSection2 = new stepb_cartesian_point(0, 3.0, -0.5);

        this._colorBody = this._dataManager.getColorYokeStage2();
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);

        this._geometryType = this.YOKESTAGE2_TYPE1;
    }

    public void setDataToElementXZ()
    {
        super.setDataToElementXZ();
        this._elmementXZ = new ElementYoke(this, this.XZView, _colorBody);
        this._elmementXZ.setElementType(DefineSystemConstant.TYPE_YOKESTAGE2);
    }

    public void setDataToElementYZ()
    {
        super.setDataToElementYZ();
        this._elmementYZ = new ElementYoke(this, this.YZView, _colorBody);
        this._elmementYZ.setElementType(DefineSystemConstant.TYPE_YOKESTAGE2);
    }

}
