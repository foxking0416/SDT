package sdt.data;

import sdt.stepb.stepb_cartesian_point;
import sdt.geometry.element.ElementYoke;
import sdt.define.DefineSystemConstant;

public class DataYokeStage1 extends DataYoke
{
    public DataYokeStage1(SDT_DataManager dataManger)
    {
        super(dataManger);

        _dataType = this.TYPE_YOKESTAGE1;
        _point3DStartSection1 = new stepb_cartesian_point(1.5, 0, -1);
        _point3DMiddleSection1 = new stepb_cartesian_point(2.25, 0, -0.5);
        _point3DEndSection1 = new stepb_cartesian_point(2.5, 0, -1.25);
        _point3DStartSection2 = new stepb_cartesian_point(0, 1.5, -1);
        _point3DMiddleSection2 = new stepb_cartesian_point(0, 2.25, -0.5);
        _point3DEndSection2 = new stepb_cartesian_point(0, 2.5, -1.25);

        this._colorBody = this._dataManager.getColorYokeStage1();
        this._material = this._dataManager.getMaterialManager().getMaterial(this._dataType);

        this._geometryType = this.YOKESTAGE1_TYPE1;
    }
    public void setDataToElementXZ()
   {
       super.setDataToElementXZ();
       this._elmementXZ = new ElementYoke(this, this.XZView, _colorBody);
       this._elmementXZ.setElementType(DefineSystemConstant.TYPE_YOKESTAGE1);
   }

   public void setDataToElementYZ()
   {
       super.setDataToElementYZ();
       this._elmementYZ = new ElementYoke(this, this.YZView, _colorBody);
       this._elmementYZ.setElementType(DefineSystemConstant.TYPE_YOKESTAGE1);
   }


}
