package sdt.material;

import sdt.define.DefineSystemConstant;

public class SDT_MaterialManager
{
    MaterialCoil _VCMCoil;
    MaterialCone _ConeCap;
    MaterialCone _ConeTransition;
    MaterialCone _ConeDiaphragm;
    MaterialCone _ConeSurround;
    MaterialMagnet _VCMMagnet;
    MaterialMagnet _VCMMagnetTop;
    MaterialMagnet _VCMMagnetOuter;
    MaterialSteel  _VCMTopPlate;
    MaterialSteel  _VCMTopYokeBase;
    MaterialSteel  _VCMTopYokeStage1;
    MaterialSteel  _VCMTopYokeStage2;


    public  SDT_MaterialManager()
    {
        _VCMCoil            = new MaterialCoil();
        _ConeCap            = new MaterialCone();
        _ConeTransition     = new MaterialCone();
        _ConeDiaphragm      = new MaterialCone();
        _ConeSurround       = new MaterialCone();
        _VCMMagnet          = new MaterialMagnet();
        _VCMMagnetTop       = new MaterialMagnet();
        _VCMMagnetOuter     = new MaterialMagnet();
        _VCMTopPlate        = new MaterialSteel();
        _VCMTopYokeBase     = new MaterialSteel();
        _VCMTopYokeStage1   = new MaterialSteel();
        _VCMTopYokeStage2   = new MaterialSteel();
    }

    public MaterialBase getMaterial(int Type)
    {
        MaterialBase material = null;
        switch(Type)
        {

            case DefineSystemConstant.TYPE_CAP:
                material =_ConeCap;
                break;

            case DefineSystemConstant.TYPE_TRANSITION:
                material =_ConeTransition;
                break;

            case DefineSystemConstant.TYPE_DIAPHRAGM:
                  material =_ConeDiaphragm;
                break;

            case DefineSystemConstant.TYPE_SURROUND:
                 material =_ConeSurround;
                break;

            case DefineSystemConstant.TYPE_COIL:
                 material =_VCMCoil;
                break;

            case DefineSystemConstant.TYPE_MAGNET:
                material =_VCMMagnet;
                break;

            case DefineSystemConstant.TYPE_TOPPLATE:
                material =_VCMTopPlate;
                break;

            case DefineSystemConstant.TYPE_MAGNETTOP:
                material =_VCMMagnetTop;
                break;
            case DefineSystemConstant.TYPE_MAGNETOUTER:
                material = _VCMMagnetOuter;
                break;

            case DefineSystemConstant.TYPE_YOKEBASE:
                material =_VCMTopYokeBase;
                break;

            case DefineSystemConstant.TYPE_YOKESTAGE1:
                material =_VCMTopYokeStage1;
                break;

            case DefineSystemConstant.TYPE_YOKESTAGE2:
                material =_VCMTopYokeStage2;
                break;


        }
        return material;

    }
    /*public MaterialCoil getMaterialCoil()
    {
        return this._VCMCoil;
    }

    public MaterialCone getMaterialConeCap()
    {
        return this._ConeCap;
    }
     public MaterialCone getMaterialConeTransition()
     {
         return this._ConeTransition;
     }

     public MaterialCone getMaterialConeDiaphragm()
     {
         return this._ConeDiaphragm;
     }

     public MaterialCone getMaterialConeSurround()
     {
         return this._ConeSurround;
     }




    public MaterialMagnet getMaterialMagnet()
    {
        return this._VCMMagnet;
    }

    public MaterialSteel getMaterialSteel()
    {
        return this._VCMTopPlate;
    }*/





}
