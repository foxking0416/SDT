package sdt.define;

public interface DefineSystemConstant
{
    public static final double ErrorValue            = 10E-6;

    public static final int MOUSE_NONE               = 0;
    public static final int MOUSE_TRANSLATE          = 1;
    public static final int MOUSE_SCALE              = 3;
    public static final int MOUSE_ROTATE             = 4;
    public static final int MOUSE_ROTATEZ            = 5;

    public static final int KEY_NONE               = 0;
    public static final int KEY_CTRL               = 1;
    public static final int KEY_ALT                = 2;
    public static final int KEY_SHIFT              = 3;


    public static final int LANG_ENGLINSH            = 0;
    public static final int LANG_CHINESE_BIG5        = 1;

    public static final int MODULE_STANDARD = 0;
    public static final int MODULE_PRELIMINARY = 1;
    public static final int MODULE_CONE = 2;
    public static final int MODULE_VCM = 3;
    public static final int MODULE_DRIVER = 4;
    public static final int MODULE_ENCLOSURE = 5;

    public static final int XYView = 0;
    public static final int YZView = 1;
    public static final int XZView = 2;

    public static final String INTERFACE_ENG = "sdt.property.InterfaceEnglish";
    public static final String INTERFACE_CHN = "sdt.property.InterfaceChineseBig5";

    public static final int TYPE_NULL                   = 0;
    public static final int TYPE_POINT                  = 1;
    public static final int TYPE_FAMILY_SOLID           = 10;
    public static final int TYPE_FAMILY_SHELL           = 11;
    public static final int TYPE_TOP                    = 99;
    public static final int TYPE_CONE                   = 100;
    public static final int TYPE_CAP                    = 101;
    public static final int TYPE_TRANSITION             = 102;
    public static final int TYPE_DIAPHRAGM              = 103;
    public static final int TYPE_SURROUND               = 104;
    public static final int TYPE_VCM                    = 200;
    public static final int TYPE_MAGNET                 = 201;
    public static final int TYPE_TOPPLATE               = 202;
    public static final int TYPE_MAGNETTOP              = 203;
    public static final int TYPE_MAGNETOUTER            = 204;
    public static final int TYPE_FORMER                 = 205;
    public static final int TYPE_COIL                   = 206;
    public static final int TYPE_TUBE                   = 207;
    public static final int TYPE_YOKE                   = 210;
    public static final int TYPE_YOKEBASE               = 211;
    public static final int TYPE_YOKESTAGE1             = 212;
    public static final int TYPE_YOKESTAGE2             = 213;

    public static final int TYPE_FRAME                  = 300;
    public static final int TYPE_ENCLOSURE              = 400;
    public static final int TYPE_AIR                    = 500;


    public static final int DIAPHRAGM_TYPE1             = 0;
    public static final int DIAPHRAGM_TYPE2             = 1;
    public static final int DIAPHRAGM_NONE              = 2;
    public static final int COIL_TYPE1                  = 0;
    public static final int COIL_TYPE2                  = 1;
    public static final int SURROUND_SINGLE_ARC         = 0;
    public static final int SURROUND_DOUBLE_ARC         = 1;
    public static final int CAP_TYPE_REGULAR            = 0;
    public static final int CAP_TYPE_CAPSULE            = 1;
    public static final int CAP_TYPE_ROUNDCUT           = 2;

    public static final int MAGNETTOP_TYPE1             = 0;
    public static final int MAGNETTOP_NONE              = 1;
    public static final int MAGNETOUTER_TYPE1           = 2;
    public static final int MAGNETOUTER_NONE            = 3;
    public static final int YOKESTAGE1_TYPE1            = 4;
    public static final int YOKESTAGE1_NONE             = 5;
    public static final int YOKESTAGE2_TYPE1            = 6;
    public static final int YOKESTAGE2_NONE             = 7;

    public static final int VIEW_SHADING                = 0;
    public static final int VIEW_SHADINGWITHEDGE        = 1;
    public static final int VIEW_WIREFRAME              = 2;

    public static final int BRICK_FACE_INNER            = 0;
    public static final int BRICK_FACE_OUTER            = 1;
    public static final int BRICK_FACE_UP               = 2;
    public static final int BRICK_FACE_DOWN             = 3;
    public static final int BRICK_FACE_CENTER           = 4;

    public static final int BRICKARRAY_TIE_INNER        = 2;
    public static final int BRICKARRAY_TIE_OUTER        = 3;
    public static final int BRICKARRAY_TIE_UP           = 5;
    public static final int BRICKARRAY_TIE_DOWN         = 7;


    public static final int TIEFACE_CONE2AIRFINE_INNER      = 0;
    public static final int TIEFACE_CONE2AIRFINE_OUTER      = 1;

    public static final int TIEFACE_AIRFINE2CONE_INNER      = 2;
    public static final int TIEFACE_AIRFINE2CONE_OUTER      = 3;

    public static final int TIEFACE_AIRFINE2COARSE_INNER    = 4;
    public static final int TIEFACE_AIRFINE2COARSE_OUTER    = 5;
    public static final int TIEFACE_AIRCOARSE2FINE_INNER    = 6;
    public static final int TIEFACE_AIRCOARSE2FINE_OUTER    = 7;

    public static final int TIEFACE_AIRFINE2COIL_INNER      = 8;
    public static final int TIEFACE_AIRFINE2COIL_OUTER      = 9;
    public static final int TIEFACE_AIRCOARSE2COIL_INNER    = 10;
    public static final int TIEFACE_AIRCOARSE2COIL_OUTER    = 11;




    public static final int SECTION_AIR_CAP              = 100;
    public static final int SECTION_AIR_TRAN_IN          = 101;
    public static final int SECTION_AIR_TRAN_OUT         = 102;
    public static final int SECTION_AIR_DIAPHRAGM        = 103;
    public static final int SECTION_AIR_SURROUND         = 104;

    public static final int SECTION_AIR_ZONE01           = 1;
    public static final int SECTION_AIR_ZONE02           = 2;
    public static final int SECTION_AIR_ZONE03           = 3;
    public static final int SECTION_AIR_ZONE04           = 4;
    public static final int SECTION_AIR_ZONE05           = 5;
    public static final int SECTION_AIR_ZONE06           = 6;
    public static final int SECTION_AIR_ZONE07           = 7;
    public static final int SECTION_AIR_ZONE08           = 8;
    public static final int SECTION_AIR_ZONE09           = 9;
    public static final int SECTION_AIR_ZONE10           = 10;
    public static final int SECTION_AIR_ZONE11           = 11;
    public static final int SECTION_AIR_ZONE12           = 12;
    public static final int SECTION_AIR_ZONE13           = 13;
    public static final int SECTION_AIR_ZONE14           = 14;
    public static final int SECTION_AIR_ZONE15           = 15;
    public static final int SECTION_AIR_ZONE16           = 16;
    public static final int SECTION_AIR_ZONE17           = 17;
    public static final int SECTION_AIR_ZONE18           = 18;
    public static final int SECTION_AIR_ZONE19           = 19;
    public static final int SECTION_AIR_ZONE20           = 20;
    public static final int SECTION_AIR_ZONE21           = 21;
    public static final int SECTION_AIR_ZONE22           = 22;
    public static final int SECTION_AIR_ZONE23           = 23;
    public static final int SECTION_AIR_ZONE24           = 24;
    public static final int SECTION_AIR_ZONE25           = 25;
    public static final int SECTION_AIR_ZONE26           = 26;
    public static final int SECTION_AIR_ZONE27           = 27;
    public static final int SECTION_AIR_ZONE28           = 28;

    public static final int AIRTYPE_COIL1_YOKE1_YOKE2_MAGNETOUT = 1;
    public static final int AIRTYPE_COIL1_YOKE1_YOKE2           = 2;
    public static final int AIRTYPE_COIL1_YOKE1_MAGNETOUT       = 3;
    public static final int AIRTYPE_COIL1_YOKE1                 = 4;
    public static final int AIRTYPE_COIL1                       = 5;
    public static final int AIRTYPE_COIL2_YOKE1_YOKE2_MAGNETOUT = 6;
    public static final int AIRTYPE_COIL2_YOKE1_YOKE2           = 7;
    public static final int AIRTYPE_COIL2_YOKE1_MAGNETOUT       = 8;
    public static final int AIRTYPE_COIL2_YOKE1                 = 9;
    public static final int AIRTYPE_COIL2                       = 10;

}
