package sdt.data;

import java.awt.*;
import java.io.*;
import java.util.*;

import sdt.define.*;
import sdt.dialog.*;
import sdt.framework.*;
import sdt.geometry.element.*;
import sdt.java3d.*;
import sdt.material.*;
import sdt.stepb.*;
import sdt.FEM.*;


public class SDT_DataManager
{
    private SDT_System                  _system;
    private SDT_MaterialManager         _materialMgr;


    private Color                       _colorCap;
    private Color                       _colorTransition;
    private Color                       _colorDiaphragm;
    private Color                       _colorSurround;
    private Color                       _colorMagnet;
    private Color                       _colorTopPlate;
    private Color                       _colorMagnetTop;
    private Color                       _colorMagnetOuter;
    private Color                       _colorCoil;
    private Color                       _colorFormer;
    private Color                       _colorYokeBase;
    private Color                       _colorStage1;
    private Color                       _colorStage2;
    private Color                       _colorAir;

    private Color                       _colorPt;
    private Color                       _colorLine;
    private Color                       _colorSolid;
    private Color                       _colorBGUp           = new Color(0,27,55);
    private Color                       _colorBGDown         = new Color(0,74,105);
    private BasicStroke                 _strokeLine          ;
    private int                         _strokePt            ;


    private DataCap                     _dataCap;
    private DataTransition              _dataTran;
    private DataDiaphragm               _dataDiaphragm;
    private DataSurround                _dataSurround;
    private DataMagnet                  _dataMagnet;
    private DataTopPlate                _dataTopPlate;
    private DataMagnetTop               _dataMagnetTop;
    private DataMagnetOuter             _dataMagnetOuter;
    private DataCoil                    _dataCoil;
    private DataYokeBase                _dataYokeBase;
    private DataYokeStage1              _dataYokeStage1;
    private DataYokeStage2              _dataYokeStage2;
    private DataAir                     _dataAir;

    private stepb_manifold_solid_brep   _solidModel;
    private boolean                     _isStartFromAxis;
    private int                         _countFan;
    private int                         _countTri;
    private int                         _countGirth;

    private String                      _workingFolder = "";
    private String                      _fileFolderAbaqus= "";
    private String                      _fileFolderAnsys = "";
    private String                      _fileNameAbaqus = "";
    private String                      _fileNameAnsys = "";

    private int                         _currentDataType;
    private ArrayList                   _eigenValueArray;
    private ArrayList                   _frequencyArray;
    private stepb_cartesian_point_array[] _displacementArray;
    private stepb_cartesian_point       _minPt;
    private stepb_cartesian_point       _maxPt;

    public SDT_MaterialManager          getMaterialManager()                {return _materialMgr;}
    public stepb_cartesian_point_array  getDisplacementArray(int index )    {return this._displacementArray[index];}
    public ArrayList                    getEigenValueArray()                {return this._eigenValueArray ;}
    public ArrayList                    getFrequencyArray()                 {return this._frequencyArray ;}




    public Color getColorCap()                              {return this._colorCap;}
    public Color getColorTransition()                       {return this._colorTransition;}
    public Color getColorDiaphragm()                        {return this._colorDiaphragm;}
    public Color getColorSurround()                         {return this._colorSurround;}
    public Color getColorMagnet()                           {return this._colorMagnet;}
    public Color getColorTopPlate()                         {return this._colorTopPlate;}
    public Color getColorMagnetTop()                        {return this._colorMagnetTop;}
    public Color getColorMagnetOuter()                      {return this._colorMagnetOuter;}
    public Color getColorCoil()                             {return this._colorCoil;}
    public Color getColorFormer()                           {return this._colorFormer;}
    public Color getColorYokeBase()                         {return this._colorYokeBase;}
    public Color getColorYokeStage1()                       {return this._colorStage1;}
    public Color getColorYokeStage2()                       {return this._colorStage2;}
    public Color getColorAir()                              {return this._colorAir;}

    public Color getColorPoint()                            {return this._colorPt;}
    public Color getColorLine()                             {return this._colorLine;}
    public Color getColorSolid()                            {return this._colorSolid;}

    public int   getStrokePt()                              {return this._strokePt;}
    public BasicStroke   getStrokeLine()                    {return this._strokeLine;}


    public Color getColorBackgroundUp()                     {return this._colorBGUp;}
    public Color getColorBackgroundDown()                   {return this._colorBGDown;}


    public void setColorCap       (Color colorCap)          {this._colorCap = colorCap;
                                                             this._dataCap.setColor(colorCap); }
    public void setColorTransition(Color colorTransition)   {this._colorTransition = colorTransition;
                                                             this._dataTran.setColor(colorTransition); }
    public void setColorDiaphragm (Color colorDiaphragm)    {this._colorDiaphragm = colorDiaphragm;
                                                             this._dataDiaphragm.setColor(colorDiaphragm); }
    public void setColorSurround  (Color colorSurround)     {this._colorSurround = colorSurround;
                                                             this._dataSurround.setColor(colorSurround); }
    public void setColorMagnet    (Color colorMagnet)       {this._colorMagnet = colorMagnet;
                                                             this._dataMagnet.setColor(colorMagnet); }
    public void setColorTopPlate  (Color colorTopPlate)     {this._colorTopPlate = colorTopPlate;
                                                            this._dataTopPlate.setColor(colorTopPlate); }
    public void setColorMagnetTop (Color colorMagnetTop)    {this._colorMagnetTop = colorMagnetTop;
                                                            this._dataMagnetTop.setColor(colorMagnetTop); }
    public void setColorMagnetOuter(Color colorMagnetOuter) {this._colorMagnetOuter = colorMagnetOuter;
                                                            this._dataMagnetOuter.setColor(colorMagnetOuter); }
    public void setColorCoil      (Color colorCoil)         {this._colorCoil = colorCoil;
                                                            this._dataCoil.setColor(colorCoil); }
    public void setColorFormer    (Color colorFormer)       {this._colorFormer = colorFormer;
                                                            this._dataCoil.setColorFormer(colorFormer); }
    public void setColorYokeBase  (Color colorYokeBase)     {this._colorYokeBase = colorYokeBase;
                                                            this._dataYokeBase.setColor(colorYokeBase); }
    public void setColorStage1    (Color colorStage1)       {this._colorStage1 = colorStage1;
                                                            this._dataYokeStage1.setColor(colorStage1); }
    public void setColorStage2    (Color colorStage2)       {this._colorStage2 = colorStage2;
                                                            this._dataYokeStage2.setColor(colorStage2); }
    public void setColorAir       (Color colorAir)          {this._colorAir = colorAir;
                                                            this._dataAir.setColor(colorAir); }



    public void setColorPt              (Color colorPt)                {this._colorPt = colorPt; }
    public void setColorLine            (Color colorLine)              {this._colorLine = colorLine; }
    public void setColorSolid           (Color colorSolid)             {this._colorSolid = colorSolid; }

    public void setStrokePt             (int stokePtSize)              {this._strokePt   = stokePtSize;}
    public void setStrokeLine           (BasicStroke stroke)           {this._strokeLine = stroke;}

    public void setColorBackgroundUp    (Color colorBackgroundUp)      {this._colorBGUp = colorBackgroundUp;}
    public void setColorBackgroundDown  (Color colorBackgroundDown)    {this._colorBGDown = colorBackgroundDown;}


    //public Color getColorSurround()     {return this._colorSurround;}
    public SDT_DataManager(SDT_System system)
    {
        _system = system;
        _materialMgr = new SDT_MaterialManager();
        this.setColorDefaultBasic();
        this.setColorDefaultDrawingObject();
        this.setData();
        this.setSolidModel();
    }

    public void setColorDefaultDrawingObject()
    {
        this._colorCap          = Color.BLUE;
        this._colorTransition   = Color.YELLOW;
        this._colorDiaphragm    = Color.RED;
        this._colorSurround     = Color.GREEN;
        this._colorMagnet       = Color.CYAN;
        this._colorTopPlate     = Color.BLACK;
        this._colorMagnetTop    = Color.MAGENTA;
        this._colorMagnetOuter  = Color.ORANGE;
        this._colorCoil         = new Color(40, 75, 185);
        this._colorFormer       = new Color(150, 215, 85);

        this._colorYokeBase     = Color.getHSBColor((float) 0.65, (float) 0.5, (float) 0.7);
        this._colorStage1       = Color.getHSBColor((float) 0.7, (float) 0.5, (float) 0.8);
        this._colorStage2   = Color.getHSBColor((float) 0.8, (float) 0.5, (float) 0.9);

        this._colorAir = Color.white;
    }


    public void setColorDefaultBasic()
    {
        this._colorPt               = Color.ORANGE;
        this._colorLine             = Color.BLACK;
        this._colorSolid            = Color.GRAY;

        this._strokeLine            = new BasicStroke(2.0f);
        this._strokePt              = 4;

        this._colorBGUp             = new Color(0,27,55);
        this._colorBGDown           = new Color(0,74,105);
    }

    public void setData()
    {
        _countGirth = 80;
        _isStartFromAxis = true;
        _dataCap = new DataCap(this);
        _dataTran = new DataTransition(this);
        _dataDiaphragm = new DataDiaphragm(this);
        _dataSurround = new DataSurround(this);
        _dataMagnet = new DataMagnet(this);
        _dataTopPlate = new DataTopPlate(this);
        _dataMagnetTop = new DataMagnetTop(this);
        _dataMagnetOuter = new DataMagnetOuter(this);
        _dataCoil = new DataCoil(this);
        _dataYokeBase = new DataYokeBase(this);
        _dataYokeStage1 = new DataYokeStage1(this);
        _dataYokeStage2 = new DataYokeStage2(this);
        _dataAir = new DataAir(this);
    }

    public void setSolidModel()
    {
        _solidModel = new stepb_manifold_solid_brep();
    }
    public stepb_manifold_solid_brep getSolidModel()
    {
        return this._solidModel;
    }


    /**
     * Get Mesh from Data using SetConeMesh(), and get the meshArrays from
     * solidmodel.
     *
     * @return SDT_Array3DMesh[] the sequence is Cap, Tran, suuround, Diaphragm, Coil
     */
    public SDT_Array3DMesh[] getArrayMesh()
    {
        SDT_Array3DMesh[] meshEleArraySets = null;
        this._solidModel.setConeMesh(this._dataCap, this._dataTran, this._dataSurround, this._dataDiaphragm, this._dataCoil, -1);
        meshEleArraySets = this._solidModel.getSDTArrayMeshes( );
        return meshEleArraySets;
    }

    public SDT_Array3DMesh[] getAirArrayMesh()
    {
        SDT_Array3DMesh[] meshEleArraySets = null;
        this._solidModel.setAirMesh(this._dataAir, -1);
        meshEleArraySets = this._solidModel.getSDTAirArrayMeshes();//getSDTAirArrayMeshes 是把剛剛setAirMesh算完的array拿出來
        return meshEleArraySets;
    }


    public SDT_Array3DMesh[] getArrayMeshDeformed(int index)
    {
        SDT_Array3DMesh[] meshEleArraySets = null;
        this._solidModel.setConeMesh(this._dataCap, this._dataTran, this._dataSurround, this._dataDiaphragm, this._dataCoil, index);
        meshEleArraySets = this._solidModel.getSDTArrayMeshes();
        return meshEleArraySets;
    }

    public SDT_Array3DEdge getArrayEdge()
    {
        SDT_Array3DEdge eeArray = new SDT_Array3DEdge(_solidModel);
        return eeArray;
    }

    public DataCap            getDataCap()          {return _dataCap;}
    public DataTransition     getDataTran()         {return _dataTran;}
    public DataDiaphragm      getDataDiaphragm()    {return _dataDiaphragm;}
    public DataSurround       getDataSurround()     {return _dataSurround;}
    public DataCoil           getDataCoil()         {return _dataCoil;}

    public DataYokeBase       getDataYokeBase()     {return _dataYokeBase;}
    public DataYokeStage1     getDataYokeStage1()   {return _dataYokeStage1;}
    public DataYokeStage2     getDataYokeStage2()   {return _dataYokeStage2;}

    public DataMagnet         getDataMagnet()       {return _dataMagnet;}
    public DataTopPlate       getDataTopPlate()     {return _dataTopPlate;}
    public DataMagnetTop      getDataMagnetTop()    {return _dataMagnetTop;}
    public DataMagnetOuter    getDataMagnetOuter()  {return _dataMagnetOuter;}
    public DataAir            getDataAir()          {return _dataAir;}


    public DataBase getCurrentData()
    {
        DataBase dataToReturn = null;
        dataToReturn = this.getCurrentDataShell();
        if (dataToReturn == null)
            dataToReturn = this.getCurrentDataSolid();
        return dataToReturn;
    }


    public DataShell getCurrentDataShell()
    {
        DataShell data = null;
        switch(this._currentDataType)
        {

           case DefineSystemConstant.TYPE_CAP:
               data = this._dataCap;
               break;
           case DefineSystemConstant.TYPE_TRANSITION:
               data = this._dataTran;
               break;
           case DefineSystemConstant.TYPE_DIAPHRAGM:
               data = this._dataDiaphragm;
               break;
           case DefineSystemConstant.TYPE_SURROUND:
               data = this._dataSurround;
               break;



           default:
               data = null;
               break;

        }
        return data;
    }

    public DataSolid getCurrentDataSolid()
    {
        DataSolid data = null;
        switch (this._currentDataType)
        {

            case DefineSystemConstant.TYPE_MAGNET:
                data = this._dataMagnet;
                break;
            case DefineSystemConstant.TYPE_MAGNETTOP:
                data = this._dataMagnetTop;
                break;
            case DefineSystemConstant.TYPE_MAGNETOUTER:
                data = this._dataMagnetOuter;
                break;
            case DefineSystemConstant.TYPE_TOPPLATE:
                data = this._dataTopPlate;
                break;
            case DefineSystemConstant.TYPE_YOKEBASE:
                data = this._dataYokeBase;
                break;
            case DefineSystemConstant.TYPE_YOKESTAGE1:
                data = this._dataYokeStage1;
                break;
            case DefineSystemConstant.TYPE_YOKESTAGE2:
                data = this._dataYokeStage2;
                break;
            case DefineSystemConstant.TYPE_COIL:
                data = this._dataCoil;
                break;
            case DefineSystemConstant.TYPE_AIR:
                data = this._dataAir;
                break;

            default:
                data = null;
                break;
        }
        return data;
    }




    public void setCurrentDataType(int type)
    {
        _currentDataType = type;
    }
    public void setSurroundDiaphragmRunway(boolean isSurroundRunway)
    {
        this._dataSurround.setIsRunway(isSurroundRunway);
        this._dataDiaphragm.setIsRunway(isSurroundRunway);
    }
    public void setCapTransitionRunway(boolean isCapRunway)
    {
        this._dataCap.setIsRunway(isCapRunway);
        if(!isCapRunway)
        {
            this._dataCap.setGeometryType(DefineSystemConstant.CAP_TYPE_REGULAR);
            this._system.getModeler().getPanel2D().GetDrawPanel().showElement();
        }

        this._dataTran.setIsRunway(isCapRunway);
        this._dataCoil.setIsRunway(isCapRunway);
    }
    public void setVCMRunway(boolean isVCMRunway)
    {
        this._dataMagnet.setIsRunway(isVCMRunway);
        this._dataTopPlate.setIsRunway(isVCMRunway);
        this._dataMagnetTop.setIsRunway(isVCMRunway);
        this._dataMagnetOuter.setIsRunway(isVCMRunway);
        this._dataYokeBase.setIsRunway(isVCMRunway);
        this._dataYokeStage1.setIsRunway(isVCMRunway);
        this._dataYokeStage2.setIsRunway(isVCMRunway);


   }




    public boolean getSurroundDiaphragmRunway()
    {
        return this._dataSurround.getIsRunway();
    }

    public boolean getCapTransitionRunway()
    {
        return this._dataCap.getIsRunway();
    }

     public boolean getVCMRunway()
     {
         return this._dataMagnet.getIsRunway();
     }

     public void setCountGirth(int countC)
     {
         this._countGirth = countC;
         this._dataSurround.setGirth(countC);
         this._dataCap.setGirth(countC);
         this._dataCap.setIsXYUpdateNeed(true);
         this._dataTran.setGirth(countC);
         this._dataTran.setIsXYUpdateNeed(true);
         this._dataDiaphragm.setGirth(countC);
         this._dataDiaphragm.setIsXYUpdateNeed(true);
         this._dataCoil.setGirth(countC);
         this._dataCoil.setIsXYUpdateNeed(true);
     }
     public void setCountCircumferentialRunway(double height, double width)
     {
         double r = height; //Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
         double perimeter = 4 * width + 2 * Math.PI * height;
         double perimeterInterval = perimeter / _countGirth;

         this._countFan = (int) (Math.PI * r / perimeterInterval);
         this._countTri = _countGirth / 2 - _countFan;

         if(this._countFan % 2 !=0)
             this._isStartFromAxis = false;
         else
             this._isStartFromAxis = true;

     }

     public int getCountGirth() {return this._countGirth;}
     public int getCountFan(boolean isDriven)
     {
         if(isDriven)   //only not
         {
             if (this._dataSurround.getIsXYUpdateNeed())
             {
                 this._dataSurround.setDataToElementXY();
             }
         }
         return this._countFan;
     }
     public int getCountTri(boolean isDriven)
     {
         if (isDriven) //only not
         {
             if (this._dataSurround.getIsXYUpdateNeed())
             {
                 this._dataSurround.setDataToElementXY();
             }
         }
         return this._countTri;
     }
     public boolean getIsStartFromAxis()
     {
         return this._isStartFromAxis;
     }

     public void Open(String Path)
     {
         try
         {
             FileReader fr = new FileReader(Path);
             BufferedReader br = new BufferedReader(fr);



             //shape
             boolean isSurroundDiaphragmRunway  = Boolean.parseBoolean(this._dataCap.readLastString(br.readLine().trim()));
             boolean isCapTransitionRunway      = Boolean.parseBoolean(this._dataCap.readLastString(br.readLine().trim()));
             boolean isMagnetTransitionRunway   = Boolean.parseBoolean(this._dataCap.readLastString(br.readLine().trim()));

             this.setSurroundDiaphragmRunway(isSurroundDiaphragmRunway);
             this.setCapTransitionRunway(isCapTransitionRunway);
             this.setVCMRunway(isMagnetTransitionRunway);

             this._system.getModeler().GetAssemTree().setShapeSurroundDiaphgram();
             this._system.getModeler().GetAssemTree().setShapeCapTransition();
             this._system.getModeler().GetAssemTree().setShapeVCM();

             int countGirth                     = Integer.parseInt(this._dataCap.readLastString(br.readLine().trim()));
             this._countFan                     = Integer.parseInt(this._dataCap.readLastString(br.readLine().trim()));
             this._countTri                     = Integer.parseInt(this._dataCap.readLastString(br.readLine().trim()));
             this.setCountGirth(countGirth);

             this._dataCap.open(br) ;
             this._dataTran.open(br);
             this._dataDiaphragm.open(br);
             this._dataSurround.open(br);
             this._dataCoil.open(br);
             this._dataMagnet.open(br);
             this._dataMagnetTop.open(br);
             this._dataMagnetOuter.open(br);
             this._dataTopPlate.open(br);
             this._dataYokeBase.open(br);
             this._dataYokeStage1.open(br);
             this._dataYokeStage2.open(br);

             this._system.getModeler().getPanel2D().GetDrawPanel().showElement();
             //this._system.getModeler().GetAssemTree().updateUI();
             //this._system.getModeler().getPanel2D().GetDrawPanel().showElement();

             this._system.ChangeToStandardDesign();
             this._system.getFrame().setTitle(Path);
         }
         catch (IOException ex)
         {
             ex.printStackTrace();
         }

     }





     public void Save(String Path) //save as a model file
     {
         try
         {
             FileWriter fw = new FileWriter(Path);

             fw.write("Surround IsRunway ? : , " + this._dataSurround.getIsRunway() + "\n");
             fw.write("     Cap IsRunway ? : , " + this._dataCap.getIsRunway() + "\n");
             fw.write("     VCM IsRunway ? : , " + this._dataMagnet.getIsRunway() + "\n");
             fw.write("        count Girth : , " + this._countGirth + "\n");
             fw.write("          count Fan : , " + this._countFan + "\n");
             fw.write("          count Tri : , " + this._countTri + "\n");

             this._dataCap.save(fw) ;
             this._dataTran.save(fw);
             this._dataDiaphragm.save(fw);
             this._dataSurround.save(fw);
             this._dataCoil.save(fw);
             this._dataMagnet.save(fw);
             this._dataMagnetTop.save(fw);
             this._dataMagnetOuter.save(fw);
             this._dataTopPlate.save(fw);
             this._dataYokeBase.save(fw);
             this._dataYokeStage1.save(fw);
             this._dataYokeStage2.save(fw);

             fw.close();

         }
         catch (IOException IOE)
         {

         }

     }


     public boolean exportInpFile(String path, boolean isExportAir, boolean isPortSealed, int portLength)
     {
         String[] defaultConeMaterial = new String[]{"PU-1", "1.2e-10", "360.52", "0.4"};
         String[] defaultCoilMaterial = new String[]{"CUWIRE", "4.12e-10", "5000", "0.3"};

         String[] materialSurround = this._dataSurround.getMaterial().getMaterialRawData();
         String[] materialCap = this._dataCap.getMaterial().getMaterialRawData();
         String[] materialTransition = this._dataTran.getMaterial().getMaterialRawData();
         String[] materialDiaphragm = this._dataDiaphragm.getMaterial().getMaterialRawData();
         String[] materialCoil = this._dataCoil.getMaterial().getMaterialRawData();

         if(materialSurround[0].trim().equals(""))
             materialSurround = defaultConeMaterial;
         if(materialCap[0].trim() .equals(""))
             materialCap = defaultConeMaterial;
         if(materialTransition[0].trim() .equals(""))
             materialTransition = defaultConeMaterial;
         if(materialDiaphragm[0].trim() .equals(""))
             materialDiaphragm = defaultConeMaterial;
         if(materialCoil[0].trim() .equals(""))
             materialCoil = defaultCoilMaterial;


         boolean result = this._solidModel.writeInpFile(path,
                 "surround", this._dataSurround.getThickness(), materialSurround,
                 "Cap", this._dataCap.getThickness(), materialCap,
                 "Transition", this._dataTran.getThickness(), materialTransition,
                 "Diaphragm", this._dataDiaphragm.getThickness(), materialDiaphragm,
                 "Coil", 0.005, materialCoil,
                 isExportAir, isPortSealed, portLength);

         if(!result)
         {
             this._solidModel.setConeMesh(this._dataCap, this._dataTran, this._dataSurround, this._dataDiaphragm, this._dataCoil, -1);
             this._solidModel.setAirMesh(this._dataAir,-1);

             result = this._solidModel.writeInpFile(path,
                                                    "surround", this._dataSurround.getThickness(), materialSurround,
                                                    "Cap", this._dataCap.getThickness(), materialCap,
                                                    "Transition", this._dataTran.getThickness(), materialTransition,
                                                    "Diaphragm", this._dataDiaphragm.getThickness(), materialDiaphragm,
                                                    "Coil", 0.005, materialCoil,
                                                    isExportAir, isPortSealed, portLength);
         }
         return result;
     }

     public boolean exportMacFile(String path, String fileName)
     {
         boolean result = true;

         this._workingFolder = this._system.getConfig().getWorkingDirPath();
         _fileFolderAnsys = this._workingFolder + "\\AnsysVault";


         String projectName = fileName;
         String projectSheet = "";
         String macFileName = fileName + ".mac";


         int YOKE_STAGES;
         if (this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
             YOKE_STAGES = 2;
         else if (this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
             YOKE_STAGES = 1;
         else
             YOKE_STAGES = 0;

         double YOKE_BASE_INNER_DIAMETER   = 2 * this._dataYokeBase.getElementPointMiddleUp(DefineSystemConstant.XZView).X();
         double YOKE_BASE_OUTER_DIAMETER   = 2 * this._dataYokeBase.getElementPointEnd(DefineSystemConstant.XZView).X();
         double YOKE_BASE_LOWER_HEIGHT     = 0;
         double YOKE_BASE_UPPER_HEIGHT     = this._dataYokeBase.getElementPointStart(DefineSystemConstant.XZView).Y()
                                             - this._dataYokeBase.getElementPointEnd(DefineSystemConstant.XZView).Y();

         double YOKE_STAGE1_INNER_DIAMETER = 2 * this._dataYokeStage1.getElementPointMiddleUp(DefineSystemConstant.XZView).X();
         double YOKE_STAGE1_OUTER_DIAMETER = 2 * this._dataYokeStage1.getElementPointEnd(DefineSystemConstant.XZView).X();
         double YOKE_STAGE1_LOWER_HEIGHT   = this._dataYokeStage1.getElementPointEnd(DefineSystemConstant.XZView).Y()
                                             - this._dataYokeBase.getElementPointEnd(DefineSystemConstant.XZView).Y();
         double YOKE_STAGE1_UPPER_HEIGHT   = this._dataYokeStage1.getElementPointStart(DefineSystemConstant.XZView).Y()
                                             - this._dataYokeBase.getElementPointEnd(DefineSystemConstant.XZView).Y();

         double YOKE_STAGE2_INNER_DIAMETER = 2 * this._dataYokeStage2.getElementPointMiddleUp(DefineSystemConstant.XZView).X();
         double YOKE_STAGE2_OUTER_DIAMETER = 2 * this._dataYokeStage2.getElementPointEnd(DefineSystemConstant.XZView).X();
         double YOKE_STAGE2_LOWER_HEIGHT   = this._dataYokeStage2.getElementPointEnd(DefineSystemConstant.XZView).Y()
                                             - this._dataYokeBase.getElementPointEnd(DefineSystemConstant.XZView).Y();
         double YOKE_STAGE2_UPPER_HEIGHT   = this._dataYokeStage2.getElementPointStart(DefineSystemConstant.XZView).Y()
                                             - this._dataYokeBase.getElementPointEnd(DefineSystemConstant.XZView).Y();

         double YOKE_STAGE3_INNER_DIAMETER = 0;
         double YOKE_STAGE3_OUTER_DIAMETER = 0;
         double YOKE_STAGE3_LOWER_HEIGHT   = 0;
         double YOKE_STAGE3_UPPER_HEIGHT   = 0;

         double YOKE_TOTAL_HEIGHT;
         if (this._dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
             YOKE_TOTAL_HEIGHT = this._dataYokeStage2.getElementPointMiddleUp(DefineSystemConstant.XZView).Y()
                                 - this._dataYokeBase.getElementPointEnd(DefineSystemConstant.XZView).Y();
         else if (this._dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
             YOKE_TOTAL_HEIGHT = this._dataYokeStage1.getElementPointMiddleUp(DefineSystemConstant.XZView).Y()
                                 - this._dataYokeBase.getElementPointEnd(DefineSystemConstant.XZView).Y();
         else
             YOKE_TOTAL_HEIGHT = this._dataYokeBase.getElementPointMiddleUp(DefineSystemConstant.XZView).Y()
                                 - this._dataYokeBase.getElementPointEnd(DefineSystemConstant.XZView).Y();




         double MAGNET1_INNER_DIAMETER     = 0;
         double MAGNET1_OUTER_DIAMETER     = 2 * this._dataMagnet.getElementPointEnd(DefineSystemConstant.XZView).X();
         double MAGNET1_THICKNESS          = this._dataMagnet.getElementPointStart(DefineSystemConstant.XZView).Y()
                                             - this._dataMagnet.getElementPointEnd(DefineSystemConstant.XZView).Y();

         double MAGNET2_INNER_DIAMETER     = 0;
         double MAGNET2_OUTER_DIAMETER     = 0;
         double MAGNET2_THICKNESS          = 0;
         if (this._dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE)
         {
             MAGNET2_INNER_DIAMETER     = 2 * this._dataMagnetTop.getElementPointStart(DefineSystemConstant.XZView).X();
             MAGNET2_OUTER_DIAMETER     = 2 * this._dataMagnetTop.getElementPointEnd(DefineSystemConstant.XZView).X();
             MAGNET2_THICKNESS          = this._dataMagnetTop.getElementPointStart(DefineSystemConstant.XZView).Y()
                                          - this._dataMagnetTop.getElementPointEnd(DefineSystemConstant.XZView).Y();
         }


         double PLATE1_INNER_DIAMETER      = 0;
         double PLATE1_OUTER_DIAMETER      =  2 *this._dataTopPlate.getElementPointEnd(DefineSystemConstant.XZView).X();
         double PLATE1_THICKNESS           = this._dataTopPlate.getElementPointStart(DefineSystemConstant.XZView).Y()
                                            -this._dataTopPlate.getElementPointEnd(DefineSystemConstant.XZView).Y();

        double PLATE2_INNER_DIAMETER       = 0;
        double PLATE2_OUTER_DIAMETER       = 0;
        double PLATE2_THICKNESS            = 0;



        double MAGNET_TOP_INNER_DIAMETER = 0;
        double MAGNET_TOP_OUTER_DIAMETER =  2 * this._dataMagnetTop.getElementPointEnd(DefineSystemConstant.XZView).X();
        double MAGNET_TOP_THICKNESS      = this._dataMagnetTop.getElementPointStart(DefineSystemConstant.XZView).Y()
                                           - this._dataMagnetTop.getElementPointEnd(DefineSystemConstant.XZView).Y();

        double COIL_INNER_GAP;
        double COIL_OUTER_GAP;
        double COIL_FORMER_THICKNESS;
        double COIL_OFFSET_FROM_PLATE_CENTER;
        double COIL_OFFSET_FROM_FORMER;
        double COIL_HEIGHT;
        double COIL_WINDINGS = 50;///////////////
        double COIL_BL_SPAN;
        double CURRENT;


        if(this._dataCoil.getGeometryType() == DefineSystemConstant.COIL_TYPE1)
        {
            COIL_INNER_GAP          = this._dataCoil.getElementPointStart(DefineSystemConstant.XZView).X()
                                      - this._dataTopPlate.getElementPointEnd(DefineSystemConstant.XZView).X();
            COIL_OUTER_GAP          = this._dataYokeBase.getElementPointMiddleUp(DefineSystemConstant.XZView).X()
                                      - this._dataCoil.getElementPointOuterEnd(DefineSystemConstant.XZView).X();
            COIL_FORMER_THICKNESS   = 0;
            COIL_OFFSET_FROM_PLATE_CENTER  = ((this._dataCoil.getElementPointStart(DefineSystemConstant.XZView).Y()
                                             +this._dataCoil.getElementPointEnd(DefineSystemConstant.XZView).Y()) /2.0)
                                             -
                                              ((this._dataTopPlate.getElementPointStart(DefineSystemConstant.XZView).Y()
                                             +this._dataTopPlate.getElementPointEnd(DefineSystemConstant.XZView).Y()) /2.0);
            COIL_OFFSET_FROM_FORMER = 0;
            COIL_HEIGHT             = this._dataCoil.getElementPointStart(DefineSystemConstant.XZView).Y()
                                      - this._dataCoil.getElementPointEnd(DefineSystemConstant.XZView).Y();
            //COIL_WINDINGS;

            COIL_BL_SPAN = 0.5;
            double buttomGap = this._dataCoil.getElementPointEnd(DefineSystemConstant.XZView).Y()
                               - this._dataYokeBase.getElementPointStart(DefineSystemConstant.XZView).Y();
            while(COIL_BL_SPAN > buttomGap)
            {
                COIL_BL_SPAN -= 0.05;
            }

            CURRENT = -0.001;
        }
        else
        {
            COIL_INNER_GAP          = this._dataCoil.getElementPointStart(DefineSystemConstant.XZView).X()
                                      - this._dataTopPlate.getElementPointEnd(DefineSystemConstant.XZView).X();
            COIL_OUTER_GAP          = this._dataYokeBase.getElementPointMiddleUp(DefineSystemConstant.XZView).X()
                                      - this._dataCoil.getElementPointCoilOuterEnd(DefineSystemConstant.XZView).X();
            COIL_FORMER_THICKNESS   = this._dataCoil.getElementPointOuterEnd(DefineSystemConstant.XZView).X()
                                      - this._dataCoil.getElementPointEnd(DefineSystemConstant.XZView).X();
            COIL_OFFSET_FROM_PLATE_CENTER  = ((this._dataCoil.getElementPointStart(DefineSystemConstant.XZView).Y()
                                           -this._dataCoil.getElementPointEnd(DefineSystemConstant.XZView).Y()) /2.0)
                                           -
                                            ((this._dataTopPlate.getElementPointStart(DefineSystemConstant.XZView).Y()
                                           -this._dataTopPlate.getElementPointEnd(DefineSystemConstant.XZView).Y()) /2.0);

            COIL_OFFSET_FROM_FORMER = 0;
            COIL_HEIGHT             = this._dataCoil.getElementPointCoilStart(DefineSystemConstant.XZView).Y()
                                      - this._dataCoil.getElementPointCoilEnd(DefineSystemConstant.XZView).Y();
            //COIL_WINDINGS;

            COIL_BL_SPAN = 0.5;
            double buttomGap        = this._dataCoil.getElementPointEnd(DefineSystemConstant.XZView).Y()
                                      - this._dataYokeBase.getElementPointStart(DefineSystemConstant.XZView).Y();
            while (COIL_BL_SPAN > buttomGap)
            {
                COIL_BL_SPAN -= 0.05;
            }

            CURRENT = 0.001;
        }


        int extraItems = 4;
        int items = 39;
        int argumentItems = 4;
        String[] tags = new String[items + extraItems + argumentItems];
        String[] values = new String[items + extraItems+ argumentItems];

        tags  [0] = "YOKE_STAGES";
        values[0] = Integer.toString(YOKE_STAGES);
        tags  [1] = "YOKE_BASE_INNER_DIAMETER";
        values[1] = Double.toString(YOKE_BASE_INNER_DIAMETER);
        tags  [2] = "YOKE_BASE_OUTER_DIAMETER";
        values[2] = Double.toString(YOKE_BASE_OUTER_DIAMETER);
        tags  [3] = "YOKE_BASE_LOWER_HEIGHT";
        values[3] = Double.toString(YOKE_BASE_LOWER_HEIGHT);
        tags  [4] = "YOKE_BASE_UPPER_HEIGHT";
        values[4] = Double.toString(YOKE_BASE_UPPER_HEIGHT);
        tags  [5] = "YOKE_STAGE1_INNER_DIAMETER";
        values[5] = Double.toString(YOKE_STAGE1_INNER_DIAMETER);
        tags  [6] = "YOKE_STAGE1_OUTER_DIAMETER";
        values[6] = Double.toString(YOKE_STAGE1_OUTER_DIAMETER);
        tags  [7] = "YOKE_STAGE1_LOWER_HEIGHT";
        values[7] = Double.toString(YOKE_STAGE1_LOWER_HEIGHT);
        tags  [8] = "YOKE_STAGE1_UPPER_HEIGHT";
        values[8] = Double.toString(YOKE_STAGE1_UPPER_HEIGHT);
        tags  [9] = "YOKE_STAGE2_INNER_DIAMETER";
        values[9] = Double.toString(YOKE_STAGE2_INNER_DIAMETER);
        tags  [10]= "YOKE_STAGE2_OUTER_DIAMETER";
        values[10]= Double.toString(YOKE_STAGE2_OUTER_DIAMETER);
        tags  [11] = "YOKE_STAGE2_LOWER_HEIGHT";
        values[11] = Double.toString(YOKE_STAGE2_LOWER_HEIGHT);
        tags  [12] = "YOKE_STAGE2_UPPER_HEIGHT";
        values[12] = Double.toString(YOKE_STAGE2_UPPER_HEIGHT);
        tags  [13]= "YOKE_STAGE3_INNER_DIAMETER";
        values[13]= Double.toString(YOKE_STAGE3_INNER_DIAMETER);
        tags  [14]= "YOKE_STAGE3_OUTER_DIAMETER";
        values[14]= Double.toString(YOKE_STAGE3_OUTER_DIAMETER);
        tags  [15] = "YOKE_STAGE3_LOWER_HEIGHT";
        values[15] = Double.toString(YOKE_STAGE3_LOWER_HEIGHT);
        tags  [16] = "YOKE_STAGE3_UPPER_HEIGHT";
        values[16] = Double.toString(YOKE_STAGE3_UPPER_HEIGHT);
        tags  [17] = "YOKE_TOTAL_HEIGHT";
        values[17] = Double.toString(YOKE_TOTAL_HEIGHT);


        tags  [18] = "MAGNET1_INNER_DIAMETER";
        values[18] = Double.toString(MAGNET1_INNER_DIAMETER);
        tags  [19] = "MAGNET1_OUTER_DIAMETER";
        values[19] = Double.toString(MAGNET1_OUTER_DIAMETER);
        tags  [20] = "MAGNET1_THICKNESS";
        values[20] = Double.toString(MAGNET1_THICKNESS);

        tags  [21] = "MAGNET2_INNER_DIAMETER";
        values[21] = Double.toString(MAGNET2_INNER_DIAMETER);
        tags  [22] = "MAGNET2_OUTER_DIAMETER";
        values[22] = Double.toString(MAGNET2_OUTER_DIAMETER);
        tags  [23] = "MAGNET2_THICKNESS";
        values[23] = Double.toString(MAGNET2_THICKNESS);


        tags  [24] = "PLATE1_INNER_DIAMETER";
        values[24] = Double.toString(PLATE1_INNER_DIAMETER);
        tags  [25] = "PLATE1_OUTER_DIAMETER";
        values[25] = Double.toString(PLATE1_OUTER_DIAMETER);
        tags  [26] = "PLATE1_THICKNESS";
        values[26] = Double.toString(PLATE1_THICKNESS);

        tags  [27] = "PLATE2_INNER_DIAMETER";
        values[27] = Double.toString(PLATE2_INNER_DIAMETER);
        tags  [28] = "PLATE2_OUTER_DIAMETER";
        values[28] = Double.toString(PLATE2_OUTER_DIAMETER);
        tags  [29] = "PLATE2_THICKNESS";
        values[29] = Double.toString(PLATE2_THICKNESS);


        tags  [30] = "COIL_INNER_GAP";
        values[30] = Double.toString(COIL_INNER_GAP);
        tags  [31] = "COIL_OUTER_GAP";
        values[31] = Double.toString(COIL_OUTER_GAP);
        tags  [32] = "COIL_FORMER_THICKNESS";
        values[32] = Double.toString(COIL_FORMER_THICKNESS);
        tags  [33] = "COIL_OFFSET_FROM_PLATE_CENTER";
        values[33] = Double.toString(COIL_OFFSET_FROM_PLATE_CENTER);
        tags  [34] = "COIL_OFFSET_FROM_FORMER";
        values[34] = Double.toString(COIL_OFFSET_FROM_FORMER);
        tags  [35] = "COIL_HEIGHT";
        values[35] = Double.toString(COIL_HEIGHT);
        tags  [36] = "COIL_WINDINGS";
        values[36] = Double.toString(COIL_WINDINGS);
        tags  [37] = "COIL_BL_SPAN";
        values[37] = Double.toString(COIL_BL_SPAN);
        tags  [38] = "CURRENT";
        values[38] = Double.toString(CURRENT);



        tags  [items + 0] = "MACGEN";
        tags  [items + 1] = "FILENAME";
        tags  [items + 2] = "FILENAME"; // we have 2 FILENAME tags
        tags  [items + 3] = "TIME_STAMP";
        values[items + 0] = "Sdt2mac (v" + Version.VERSION_STRING + ')';
        values[items + 1] = projectName;
        values[items + 2] = projectName;


        // determine date and time stamp
        Calendar rightNow = Calendar.getInstance();
        int hh = rightNow.get(Calendar.HOUR);
        int mm = rightNow.get(Calendar.MINUTE);
        int ss = rightNow.get(Calendar.SECOND);
        int ap = rightNow.get(Calendar.AM_PM);
        String HH = (hh == 0 && ap == 1) ? "12" : Integer.toString(hh);
        if (new Integer(HH)<10)
            HH = '0' + HH;
        String MM = (mm < 10) ? '0' + Integer.toString(mm) : Integer.toString(mm);
        String SS = (ss < 10) ? '0' + Integer.toString(ss) : Integer.toString(ss);
        String timeStamp = HH + ':' + MM + ':' + SS + (ap == 1 ? "pm" : "am");

        mm = rightNow.get(Calendar.MONTH) + 1;
        int dd = rightNow.get(Calendar.DATE);
        int yy = rightNow.get(Calendar.YEAR);
        MM = (mm<10) ? '0'+Integer.toString(mm) : Integer.toString(mm);
        String DD = (dd<10) ? '0'+Integer.toString(dd) : Integer.toString(dd);
        String YYYY = Integer.toString(yy);
        values[items + 3] = timeStamp + ", " + MM + '/' + DD + '/' + YYYY;

        tags  [items + extraItems + 0] = "ARG1";
        values[items + extraItems + 0] = "'axis'";
        tags  [items + extraItems + 1] = "ARG2";
        values[items + extraItems + 1] = "1";
        tags  [items + extraItems + 2] = "ARG3";
        values[items + extraItems + 2] = "0";
        tags  [items + extraItems + 3] = "ARG4";
        values[items + extraItems + 3] = "0";

        SdtVcmMacR1xb vcm1 = new SdtVcmMacR1xb(tags, values);
        String tmpPath = "resource\\macTemplate";

        vcm1.setTmpPath(tmpPath);
        vcm1.output(path, macFileName);

        return result;
    }


    public void getFileFolderAndName(String inpPath)
    {
         int lastSlashIndex = inpPath.lastIndexOf("\\");
         _fileFolderAbaqus = inpPath.substring(0, lastSlashIndex);
         _fileNameAbaqus = inpPath.substring(lastSlashIndex+1);
         _fileNameAbaqus = _fileNameAbaqus.substring(0,_fileNameAbaqus.length() - 4 ) ;

     }

     public void createAnsysBatAndRun()
     {
         boolean isBatGenerated = false;

         String batFilePathName = _fileFolderAnsys + "\\" + "SDT_ANSYS.bat";
         String diskSetting = _fileFolderAbaqus.substring(0, 2);
         FileWriter fw = null;

         try
         {

             fw = new FileWriter(batFilePathName);
             //fileName = fileName.Substring(0, fileName.Length - 4);
             fw.append("@echo off" + "\n");
             fw.append(diskSetting + "\n");
             fw.append("cd " + _fileFolderAbaqus + "\\" + "\n");
             fw.append(this._system.getConfig().getAnsysPath() + " -b -i " + _fileNameAbaqus + "\n");
             //  fw.append(this._system.getConfig().getAbaqusPath() + " job=" + _fileName + "_Static\n");
             fw.append("exit");
             fw.close();
             System.out.println("Make a file");
             isBatGenerated = true;
         }
         catch (IOException ex)
         {
             System.err.println(ex.getMessage());
         }
         if (isBatGenerated)
         {
             Runtime runtime = Runtime.getRuntime();
             try
             {
                 runtime.exec("cmd.exe /c start " + batFilePathName);
             }
             catch (IOException ex)
             {
                 ex.printStackTrace();

             }
         }

     }



     public void createAbaqusBatAndRun()
     {
         boolean isBatGenerated = false;


         String batFilePathName = _fileFolderAbaqus + "\\" + "SDT_ABAQUS.bat";
         String diskSetting = _fileFolderAbaqus.substring(0, 2);
         FileWriter fw = null;

         try
         {

             fw = new FileWriter(batFilePathName);
             //fileName = fileName.Substring(0, fileName.Length - 4);
             fw.append("@echo off" + "\n");
             fw.append(diskSetting + "\n");
             fw.append("cd " + _fileFolderAbaqus + "\\" + "\n");
             fw.append(this._system.getConfig().getAbaqusPath() + " job=" + _fileNameAbaqus + "\n");
           //  fw.append(this._system.getConfig().getAbaqusPath() + " job=" + _fileName + "_Static\n");
             fw.append("exit");
             fw.close();
             System.out.println("Make a file");
             isBatGenerated = true;
         }
         catch (IOException ex)
         {
             System.err.println(ex.getMessage());
         }
         if(isBatGenerated)
         {
             Runtime runtime = Runtime.getRuntime();
             try
             {
                 runtime.exec("cmd.exe /c start " + batFilePathName);
             }
             catch (IOException ex)
             {
                 ex.printStackTrace();

             }
         }



     }
     public boolean getDatContentTest()
     {
         this._workingFolder = this._system.getConfig().getWorkingDirPath();
         this._fileFolderAbaqus = this._workingFolder + "\\AbaqusVault";

         this._fileNameAbaqus = "selfComputation";
         return this.getDatContent();
     }


     public boolean getDatContent()
     {
         boolean isDataAvailable = false;

         FileReader fr;
         BufferedReader br;
         String str;
         int numEigenValue = 0;
         int lineMark = 100;
         int indexEigenValue = 0;


         String key = "";

         numEigenValue = 0;


        // ArrayList eigenValueArray = new ArrayList();
       //  ArrayList frequencyArray = new ArrayList();

       _eigenValueArray = new ArrayList();
       _frequencyArray = new ArrayList();
       double eigenValue[] = null;
       double frequency[] = null;

       _displacementArray = null;
       stepb_cartesian_point ptU;


       System.out.println("Start Reading DAT!!!!..............");
       String dataPath = _fileFolderAbaqus + "\\" + _fileNameAbaqus + ".dat";
       try
         {
             fr = new FileReader(dataPath);
             br = new BufferedReader(fr);

             str = br.readLine();
             do
             {

                 if (str != null)
                 {
                     str = str.trim();
                     /*if (str.startsWith("NUMBER OF EIGENVALUES"))
                                        {
                         str = str.replace("NUMBER OF EIGENVALUES", "");
                         numEigenValue = Integer.parseInt(str.trim());
                         eigenValue = new double[numEigenValue];
                         por = new double[numEigenValue];
                         u = new double[numEigenValue][4];
                                        }*/
                     if (str.startsWith("E I G E N V A L U E    O U T P U T"))
                     {
                         lineMark = 0;
                         key = "EIGENVALUE";
                     }

                     if (key.equals("EIGENVALUE") && lineMark > 5)
                     {
                         if (!str.trim().equals(""))
                         {

                             StringTokenizer token = new StringTokenizer(str);
                             numEigenValue = Integer.parseInt(token.nextToken());
                             String eigenValueStr = token.nextToken();
                             token.nextToken();
                             String freqencyStr = token.nextToken();

                             _eigenValueArray.add(eigenValueStr);
                             _frequencyArray.add(freqencyStr);


                         }
                         else
                         {
                             key = "";
                             numEigenValue = _eigenValueArray.size();
                             eigenValue = new double[numEigenValue];
                             frequency = new double[numEigenValue];
                             _displacementArray    = new stepb_cartesian_point_array[numEigenValue];


                             for (int i = 0; i < numEigenValue; i++)
                             {
                                 String eigenValueStr1 = (String) _eigenValueArray.get(i);
                                 String frequencyStr1 = (String) _frequencyArray.get(i);
                                 eigenValue[i] = Double.parseDouble(eigenValueStr1);
                                 frequency[i] = Double.parseDouble(frequencyStr1);
                             }
                         }

                     }
                     if (str.startsWith("E I G E N V A L U E    N U M B E R"))
                     {
                         str = str.replace("E I G E N V A L U E    N U M B E R", "").trim();
                         indexEigenValue = Integer.parseInt(str.trim() );
                         System.out.println("indexEigenValue: " + indexEigenValue);
                     }
                     if (str.startsWith("NODE FOOT-   U1"))
                     {
                         lineMark = 0;
                         key = "Un";
                     }
                     if (lineMark >= 3 && key.equals("Un"))
                     {
                         if (str.trim().length() != 0) //
                         {

                           //  System.out.println("indexEigenValue");
                             StringTokenizer token = new StringTokenizer(str);

                             String tempStr = token.nextToken();
                          //   System.out.println(tempStr);
                             int id = Integer.parseInt(tempStr);

                             tempStr = token.nextToken();
                           //  System.out.println(tempStr);
                             double u1 = Double.parseDouble(tempStr);

                             tempStr = token.nextToken();
                         //    System.out.println(tempStr);
                             double u2 = Double.parseDouble(tempStr);

                             tempStr = token.nextToken();
                           //  System.out.println(tempStr);
                             tempStr.trim();
                             double u3 = Double.parseDouble(tempStr);

                             ptU = new stepb_cartesian_point(u1, u2, u3);
                             ptU.SetIDNumber(id);

                             if (lineMark == 3)
                             {
                                 _displacementArray[indexEigenValue - 1] = new stepb_cartesian_point_array();
                             }
                             _displacementArray[indexEigenValue - 1].add(ptU);

                         }
                         else
                         {
                             key = "else";
                         }
                     }
                 }
                 str = br.readLine();
                 lineMark++;
             //    System.out.println(str);
             }
             while (str != null);


            /* for (int i = 0; i < numEigenValue; i++)
             {
                 System.out.println("   " + eigenValue[i]);
                 System.out.println("   " + frequency[i]);

             }
             for (int i = 0; i < numEigenValue; i++)
             {
                 System.out.println("*********Eigen Number******");
                 System.out.println(i + 1);
                 System.out.println("***************************\n");

                 System.out.println("*********Eigen Value******");
                 System.out.println(eigenValue[i]);
                 System.out.println("***************************\n");

                 System.out.println("*******Eigen Frequency******");
                 System.out.println(frequency[i]);
                 System.out.println("***************************\n");


                   System.out.println("***ID  ,     U1  ,    U2  ,     U3********\n");

                 for (int j = 0; j < _displacementArray[0].size(); j++)
                 {
                     System.out.print("   " + _displacementArray[i].get(j).GetIDNumber());
                     System.out.print("   " + _displacementArray[i].get(j).X());
                     System.out.print("   " + _displacementArray[i].get(j).Y());
                     System.out.println("   " + _displacementArray[i].get(j).Z());

                 }
             }*/


             isDataAvailable = true;
         }
         catch (FileNotFoundException ex)
         {
             DiaMessage diaMsg = new DiaMessage(this._system, "Can't find your FEM result file!");
         }
         catch (IOException ex)
         {
             DiaMessage diaMsg = new DiaMessage(this._system, "The format of DAT is wrong! Please check the DAT file.");
             /** @todo Handle this exception */
         }

         return isDataAvailable;
     }

     public double[][] getDatContentStatic()
     {
            this._workingFolder = this._system.getConfig().getWorkingDirPath();
          String abaqusValut = "AbaqusVault";
          this._fileFolderAbaqus = _workingFolder + "\\"+ abaqusValut;
          this._fileNameAbaqus = "selfComputation";

         double[][] displaceDeflection = null;
         System.out.println("Start Reading DAT!!!!..............");
         String dataPath = _fileFolderAbaqus + "\\" + _fileNameAbaqus + "_Static.dat";

         FileReader fr = null;
         BufferedReader br = null;

         boolean isData = false;
         String str = "";
         double forceDeflection = 0.0;
         double displacement = 0.0;
         double time = 0;
         int number = 0;
         int nodeCount = 0;
         ArrayList arrayTimeDeflection = new ArrayList();
         try
         {
             fr = new FileReader(dataPath);
             br = new BufferedReader(fr);
             str = br.readLine();

             while (str != null)
             {
                 isData = false;
                 try
                 {
                     str = str.trim();
                     if (str.startsWith("INCREMENT"))
                     {
                         number = Integer.parseInt(str.substring(9, 14).trim());
                         for (int i = 0; i < 4; i++)
                         {
                             str = br.readLine();
                         }
                         StringTokenizer token = new StringTokenizer(str);

                         String lastStr = "";
                         while (token.hasMoreTokens())
                         {
                             lastStr = token.nextToken();
                         }
                         time = Double.parseDouble(lastStr.trim());
                     }
                     if (str.startsWith("AT NODE"))
                     {
                         nodeCount++;
                         if (nodeCount == 1)
                         {

                             for (int i = 0; i < 5; i++)
                             {
                                 str = br.readLine();

                             }
                             nodeCount++;
                             if (str != null)
                             {
                                 String content = str.substring(6);
                                 forceDeflection = Double.parseDouble(content.trim());
                                 System.out.println(content.trim());
                             }

                         }
                         else if (nodeCount == 3)
                         {
                             for (int i = 0; i < 2; i++)
                             {
                                 str = br.readLine();
                             }
                             if (str != null)
                             {
                                 String content = str.substring(8);
                                 displacement = Double.parseDouble(content.trim());

                                 System.out.println(content.trim());
                             }
                         }
                         else if (nodeCount == 4)
                         {
                             nodeCount = 0;
                             double[] nTDD = new double[]
                                             {number, time, displacement, forceDeflection}; //number + time +Displacement+ Defelection
                             arrayTimeDeflection.add(nTDD);

                         }


                     }
                 }
                 catch (NumberFormatException ex1)
                 {

                 }
                 str = br.readLine();

             }
         }
         catch (FileNotFoundException ex)
         {
         }
         catch (IOException ex)
         {
             /** @todo Handle this exception */
         }
         displaceDeflection= new double[arrayTimeDeflection.size()][2];
         for(int i = 0 ; i < arrayTimeDeflection.size() ; i++)
         {
             double nTDD[] = (double[])arrayTimeDeflection.get(i);
             displaceDeflection[i][0] = nTDD[2];
             displaceDeflection[i][1] = -nTDD[3];
         }
         return displaceDeflection;
     }
     public double[][] getDatContentBL()
   {
          this._workingFolder = this._system.getConfig().getWorkingDirPath();
        String ansysVault = "AnsysVault";
        this._fileFolderAnsys = _workingFolder + "\\"+ ansysVault;
        this._fileNameAnsys = "mag_data.dat";

       double[][] displaceDeflection = null;
       System.out.println("Start Reading DAT!!!!..............");
       String dataPath = _fileFolderAnsys + "\\" + _fileNameAnsys ;

       boolean isData = false;
       FileReader fr = null;
       BufferedReader br = null;
       String str = "";
       double forceDeflection = 0.0;
       double displacement = 0.0;
       double blValue = 0.0;
       double time = 0;
       int number = 0;
       int nodeCount = 0;
       int lineCount = 0;

       displaceDeflection= new double[21][2];

       try
       {
           fr = new FileReader(dataPath);
           br = new BufferedReader(fr);
           while (str != null)
           {
               if(lineCount >= 4)
               {
                   str = str.trim();

                   StringTokenizer token = new StringTokenizer(str);
                   displacement = Double.parseDouble(token.nextToken().trim());
                   blValue = Double.parseDouble(token.nextToken().trim());
                   displaceDeflection[lineCount-4][0] = displacement;
                   displaceDeflection[lineCount-4][1] = blValue;
               }

               str = br.readLine();
               lineCount++;
           }
       }
       catch (FileNotFoundException ex)
       {
       }
       catch (IOException ex)
       {
           /** @todo Handle this exception */
       }

       return displaceDeflection;
   }

   /**
    *
    * @return int 0:Finished  1:Error  2:Unfinished
    */
   public  int CheckIsAbaqusLogCompeleted()
     {
         boolean isJobCompleted = true;
         String pathLog = this._fileFolderAbaqus +"\\"+ this._fileNameAbaqus + ".log";

         FileReader fr = null;
         String lastStr = "";
         try
         {
             fr = new FileReader(pathLog);

             BufferedReader br = new BufferedReader(fr);
             String str = "";

             while (str != null)
             {
                 lastStr = str;
                 str = br.readLine();
             }
         }
         catch (FileNotFoundException ex)
         {
             ex.printStackTrace();
         }
         catch (IOException ex)
         {
             ex.printStackTrace();
             /** @todo Handle this exception */
         }

         if(lastStr.endsWith("COMPLETED"))
             return 0;
         else if(lastStr.endsWith("errors"))
             return 1;
         else
             return 2;

         //isJobCompleted = lastStr.endsWith("COMPLETED");

         //return isJobCompleted;
     }

     public boolean checkIsAbacusLogError()
     {
         boolean isJobCompleted = true;
         String pathLog = this._fileFolderAbaqus + "\\" + this._fileNameAbaqus + ".log";

         FileReader fr = null;
         String lastStr = "";
         try
         {
             fr = new FileReader(pathLog);

             BufferedReader br = new BufferedReader(fr);
             String str = "";

             while (str != null)
             {
                 lastStr = str;
                 str = br.readLine();
             }
         }
         catch (FileNotFoundException ex)
         {
             ex.printStackTrace();
         }
         catch (IOException ex)
         {
             ex.printStackTrace();
             /** @todo Handle this exception */
         }

         isJobCompleted = lastStr.endsWith("COMPLETED");

         return isJobCompleted;

     }

     public Color getColorByType(int type)
     {
         Color colorToReturn;
         switch (type)
         {
             case DefineSystemConstant.TYPE_CAP:
                 colorToReturn = this._colorCap;
                 break;
             case DefineSystemConstant.TYPE_TRANSITION:
                 colorToReturn = this._colorTransition;
                 break;
             case DefineSystemConstant.TYPE_DIAPHRAGM:
                 colorToReturn = this._colorDiaphragm;
                 break;
             case DefineSystemConstant.TYPE_SURROUND:
                 colorToReturn = this._colorSurround;
                 break;
             case DefineSystemConstant.TYPE_MAGNET:
                 colorToReturn = this._colorMagnet;
                 break;
             case DefineSystemConstant.TYPE_MAGNETTOP:
                 colorToReturn = this._colorMagnetTop;
                 break;
             case DefineSystemConstant.TYPE_MAGNETOUTER:
                 colorToReturn = this._colorMagnetOuter;
                 break;
             case DefineSystemConstant.TYPE_TOPPLATE:
                 colorToReturn = this._colorTopPlate;
                 break;
             case DefineSystemConstant.TYPE_YOKEBASE:
                 colorToReturn = this._colorYokeBase;
                 break;
             case DefineSystemConstant.TYPE_YOKESTAGE1:
                 colorToReturn = this._colorStage1;
                 break;
             case DefineSystemConstant.TYPE_YOKESTAGE2:
                 colorToReturn = this._colorStage2;
                 break;
             case DefineSystemConstant.TYPE_COIL:
                 colorToReturn = this._colorCoil;
                 break;
             default:
                 colorToReturn = Color.LIGHT_GRAY;
                 break;
         }
         return colorToReturn;
     }
   /*  private class ThreadLogRead implements Runnable
     {
         private volatile Thread t;
         private boolean bool2 = false;
         private int DelaySetting[] =
                 {0, 0, 0};
         private int delayTime;
         private int reRunTimesReadLog;

         public ThreadLogRead()
         {
             delayTime = _system.getConfig().getDelayTime();
             reRunTimesReadLog = _system.getConfig().getReRunTime();
         }

         public void run()
         {
             int reRunTimes = 1;
             Thread thisThread = Thread.currentThread();
             while (thisThread == this.t)
             {
                 try
                 {
                     this.t.sleep(delayTime);
                 }
                 catch (InterruptedException ex1)
                 {
                 }

                 bool2 = CheckIsLogCompeleted();

                 if ( bool2 == true)
                 {
                     this.stop();
                 }
                 else
                 {
                     reRunTimes++;
                     if (reRunTimes > reRunTimesReadLog ) //2010/07/28
                     {
                         new DiaMessage(_system, "<html>Job is not completed, successfully. </html>");
                         this.stop();
                     }
                 }
             }
         }

         public boolean isLive()
         {
             if (this.t != null)
             {
                 return this.t.isAlive();
             }
             else
             {
                 return false;
             }
         }

         private void RunSimulation()
         {
             this.stop();
             this.t = new Thread(this);
             this.t.start();
         }

         public void stop()
         {
             this.t = null;
         }
     }*/
   /**
    * Return All the Volume of the
    * @return double[][]
    */
   public double[][] getModalShellVolume(double[][] volumeCap,double[][] volumeTransition, double[][] volumeDiaphragm , double[][] volumeSurround)
   {
       this._solidModel.setConeMesh(this._dataCap, this._dataTran, this._dataSurround, this._dataDiaphragm, this._dataCoil, -1);
       SDT_Array3DMesh[] meshEleArraySets = this._solidModel.getSDTArrayMeshes( );

       int modalSize = _frequencyArray.size();
      double volume[][] = new double[modalSize][2];
      for(int j = 0 ; j < modalSize ; j++)
      {

          for (int i = 0; i < meshEleArraySets.length; i++)
          {
              SDT_Array3DMesh array3DMesh = meshEleArraySets[i];

              switch (array3DMesh.getTypeID())
              {
                  case DefineSystemConstant.TYPE_CAP:
                      volumeCap[j] = this.caculateMeshVolume(j, array3DMesh);
                      break;
                  case DefineSystemConstant.TYPE_TRANSITION:
                      volumeTransition[j] = this.caculateMeshVolume(j, array3DMesh);
                      break;
                  case DefineSystemConstant.TYPE_DIAPHRAGM:
                      volumeDiaphragm[j] = this.caculateMeshVolume(j, array3DMesh);
                      break;
                  case DefineSystemConstant.TYPE_SURROUND:
                      volumeSurround[j] = this.caculateMeshVolume(j, array3DMesh);
                      break;

                  case DefineSystemConstant.TYPE_FORMER:
                      break;
                  case DefineSystemConstant.TYPE_COIL:
                      break;
              }

          }
          double[] volumeTotal = {0,0};


          volumeTotal[0] = volumeCap[j][0] + volumeTransition[j][0] + volumeDiaphragm[j][0] + volumeSurround[j][0];
          volumeTotal[1] = volumeCap[j][1] + volumeTransition[j][1] + volumeDiaphragm[j][1] + volumeSurround[j][1];
          volume[j] = volumeTotal;
      }
       return volume;
   }

   public double[] getModalSolidDisplacement()
   {
       SDT_Array3DMesh[] meshEleArraySets = this._solidModel.getSDTArrayMeshes();
       double displacementCoil = 0;
       double displacementFormer = 0;
       int ptSizeCoil = 0;
       int ptSizeFormer = 0;

       int modalSize = _frequencyArray.size();
       double alpha[] = new double[modalSize];
       for(int j = 0 ; j < modalSize ; j++)
       {
           for (int i = 0; i < meshEleArraySets.length; i++)
           {
               SDT_Array3DMesh array3DMesh = meshEleArraySets[i];

               switch (array3DMesh.getTypeID())
               {
                   case DefineSystemConstant.TYPE_CAP:
                       break;
                   case DefineSystemConstant.TYPE_TRANSITION:
                       break;
                   case DefineSystemConstant.TYPE_DIAPHRAGM:
                       break;
                   case DefineSystemConstant.TYPE_SURROUND:
                       break;
                   case DefineSystemConstant.TYPE_FORMER:
                       displacementFormer = this.caculateMeshDisplacement(j , array3DMesh);
                       ptSizeFormer = array3DMesh.Size();
                       break;
                   case DefineSystemConstant.TYPE_COIL:
                       displacementCoil = this.caculateMeshDisplacement(j , array3DMesh);
                       ptSizeCoil = array3DMesh.Size();
                       break;
               }

           }

           double displacementAverage = (displacementFormer * ptSizeFormer + displacementCoil * ptSizeCoil) / (ptSizeFormer + ptSizeCoil);
           alpha[j] = displacementAverage;
       }
       return alpha;
   }

   /**
    *  With normal value and absoulte value in a 2 dimension array
    */
   private double[] caculateMeshVolume(int modeIndex, SDT_Array3DMesh array3DMesh)

   {
       int sizeOfMesh = array3DMesh.Size();
       double[] volumeTotal = {0,0};
       for (int j = 0; j < sizeOfMesh; j++)
       {
           SDT_3DMesh mesh = array3DMesh.get(j);
           double ptCU3 = 0;
           int idPt1 = mesh.GetPoint1().GetIDNumber();
           int idPt2 = mesh.GetPoint2().GetIDNumber();
           int idPt3 = mesh.GetPoint3().GetIDNumber();
           int idPt4 = 0;

            double pt1U3 =0;
            double pt2U3 =0;
            double pt3U3 =0;
            double pt4U3 =0;

           stepb_cartesian_point cpt1 = this.getPtDisplacement(modeIndex, idPt1);
           stepb_cartesian_point cpt2 = this.getPtDisplacement(modeIndex, idPt2);
           stepb_cartesian_point cpt3 = this.getPtDisplacement(modeIndex, idPt3);
           if (cpt1 != null)
               pt1U3 = cpt1.Z();
           else
               System.out.println("Point id 1:" + idPt1+ "<-----can't find displacement!!!" );

           if (cpt2 != null)
               pt2U3 = cpt2.Z();
           else
               System.out.println("Point id 2:" + idPt2+ "<-----can't find displacement!!!" );

           if (cpt3 != null)
               pt3U3 = cpt3.Z();
           else
               System.out.println("Point id 3:" + idPt3+ "<-----can't find displacement!!!" );


           pt4U3 = 0;
           if (mesh.GetPoint4() != null)
           {
               idPt4 = mesh.GetPoint4().GetIDNumber();

               stepb_cartesian_point cpt4 = this.getPtDisplacement(modeIndex, idPt4);
               if (cpt4 != null)
                   pt4U3 = cpt4.Z();
               else
                   System.out.println("Point id 4:" + idPt4 + "<-----can't find displacement!!!");

               ptCU3 = pt1U3 + pt2U3 + pt3U3 + pt4U3 / 4.0;
           }
           else
           {
               ptCU3 = pt1U3 + pt2U3 + pt3U3 / 3.0;
           }

           double sectionZArea = mesh.getSectionZArea();
           volumeTotal[0] += sectionZArea * ptCU3;
           volumeTotal[1] += Math.abs( sectionZArea * ptCU3 );

       }
       return volumeTotal;
   }

   private double caculateMeshDisplacement(int modeIndex, SDT_Array3DMesh array3DMesh)
   {
       int sizeOfMesh = array3DMesh.Size();
       double ptCU3Total = 0;   //中心點位移總合
       for (int j = 0; j < sizeOfMesh; j++)
       {
           SDT_3DMesh mesh = array3DMesh.get(j);
           double ptCU3 = 0;   //中心點位移
           int idPt1 = mesh.GetPoint1().GetIDNumber();
           int idPt2 = mesh.GetPoint2().GetIDNumber();
           int idPt3 = mesh.GetPoint3().GetIDNumber();
           int idPt4 = 0;
           double pt1U3 = this.getPtDisplacement(modeIndex, idPt1).Z();
           double pt2U3 = this.getPtDisplacement(modeIndex, idPt2).Z();
           double pt3U3 = this.getPtDisplacement(modeIndex, idPt3).Z();
           double pt4U3 = 0;
           if (mesh.GetPoint4() != null)
           {
               idPt4 = mesh.GetPoint4().GetIDNumber();
               pt4U3 = this.getPtDisplacement(modeIndex, idPt4).Z();
               ptCU3 = pt1U3 + pt2U3 + pt3U3 + pt4U3 / 4.0;
           }
           else
           {
               ptCU3 = pt1U3 + pt2U3 + pt3U3 / 3.0;
           }

           ptCU3Total += ptCU3;
       }
       double ptCUAverage = ptCU3Total / sizeOfMesh;   //中心點平均位移
       return ptCUAverage;
   }


   private stepb_cartesian_point getPtDisplacement(int modalIndex, int IDNumber)
   {

       stepb_cartesian_point_array displacementArray = this._displacementArray[modalIndex];
       displacementArray.rearrangeTable();
       stepb_cartesian_point ptDisplacement = displacementArray.getByID(IDNumber);

       return ptDisplacement;
   }

   public double[][] caluculateSPL(double valueMaganetForce,
                             double valueCircuitResistance,
                             double valueCircuitInductance,
                             double valueInductanceMultiplier,
                             double valueDrivingVoltage,
                             double zetaArray[])
   {

       double freqStart = 20.0;
       double freqEnd = 20000.0;



/**
* Start of Debug
*/

       //original code



       double[] alpha = this.getModalSolidDisplacement(); //Coil Displacement
       int modalSize = alpha.length;

       double[][] volumeCap = new double[modalSize][2];
        double[][] volumeTransition =  new double[modalSize][2];
        double[][] volumeDiaphragm =  new double[modalSize][2];
        double[][] volumeSurround =  new double[modalSize][2];



       double[][] volume = this.getModalShellVolume(volumeCap,volumeTransition,volumeDiaphragm,volumeSurround);


      for(int i = 0 ; i < volume.length ; i++)
       {
           volume[i][0] =  volume[i][0] / (1E9);
           volume[i][1] =  volume[i][1] / (1E9);

           volumeCap[i][0] =  volumeCap[i][0] / (1E9);
           volumeCap[i][1] =  volumeCap[i][1] / (1E9);
           volumeTransition[i][0] =  volumeTransition[i][0] / (1E9);
           volumeTransition[i][1] =  volumeTransition[i][1] / (1E9);
           volumeDiaphragm[i][0] =  volumeDiaphragm[i][0] / (1E9);
           volumeDiaphragm[i][1] =  volumeDiaphragm[i][1] / (1E9);
           volumeSurround[i][0] =  volumeSurround[i][0] / (1E9);
           volumeSurround[i][1] =  volumeSurround[i][1] / (1E9);




           alpha[i] =  alpha[i] /1E3 ;
       }

       double[] volumeAbs = new double[volume.length];
       double[] volumeNormal = new double[volume.length];
       for (int i = 0; i < volume.length; i++)
       {
           volumeNormal[i] = volume[i][0];
           volumeAbs[i] = volume[i][1];
       }


      //test code

      String workingDir = this._system.getConfig().getWorkingDirPath();
      String abaqusVault = "AbaqusVault";
      this._fileFolderAbaqus = workingDir + "\\" + abaqusVault;

      this._fileNameAbaqus = "selfComputation";

     this.getDatContent();

    /*double[] volume = {-0.008003428966714826 ,1.043184684845766E-14 , 8.750512523025834E-15 ,
                      8.224876468787557E-17, -7.270499564459437E-17, 1.7272115127899E-17, 2.254470427356936E-17,
                      -6.256080098410166E-18, -1.4725027863704554E-18, 1.6312385528895767E-5, -3.477502565594593E-5,
                      -0.01285482479888757, 4.95411384962216E-18, -1.6904522456319404E-18, -2.8774770041783504E-18,
                      -1.121919560292256E-21, -2.9460292018810747E-17, 3.476569416599679E-18, -8.116236543953548E-18,
                      -5.880135406366449E-19, -3.833270426435392E-5, -3.995446391037484E-5, 2.6794112678585335E-17,
                      -1.9297049069777472E-17, 7.0315420560782E-18, 3.964907755414637E-18, 0.022048418760287656,
                      0.0012535573334852305, 1.613002089020878E-4, -4.580940667813296E-18, 1.4489657750547945E-17,
                      1.950758212670105E-17, -1.4540090393649225E-17, -4.563546949997036E-18, 4.2397069314369575E-18};

  double[] alpha ={-83.8695,  -6.823231362634235E-15,  -7.0711528813397446E-15,
                  0.0, -3.8675420068169906E-15, 9.485692889602094E-16, 6.898685737892433E-16,
                  3.5355764406698723E-15, -1.3797371475784867E-15, 2.8932222222183854E-4, 0.0020421851851849353,
                  4.883959259259259, -2.802591081018801E-16, -2.694799116364232E-16, 8.252822293865461E-18,
                  0.0, 3.068702493759769E-16, 2.243420264373223E-16, -1.6421432115344537E-16,
                  -1.013244467752951E-15, -7.338922222235252E-5, -6.788115370354751E-5, -4.392522559673698E-16,
                  0.0, 1.8189894035458566E-17, 1.375189674069622E-16, -3.0589199629629507,
                  0.004638827777777721, -2.5855312251906343E-4, 1.5875103700737882E-16, 6.736997790910579E-19,
                  1.2867665780639208E-16, 8.185452315956353E-17, -4.965377903082063E-17, -1.0947621410229692E-17};
     */


    System.out.println("****************************** ");
    System.out.println("index,      Wn       ,   Volume      ,   Volume(ABS) ,   ratio      , VolumeCap     ,VolumeSurround ");
    for (int i = 0; i < alpha.length; i++)
    {
        String freq = ((String) _frequencyArray.get(i)).trim();
        double Wn = Double.parseDouble(freq) * Math.PI * 2;

        System.out.print(i + " , ");
        System.out.print(Wn + " , ");
        System.out.print(volume[i][0] + " , ");
        System.out.print(volume[i][1] + " , ");
        System.out.print((volume[i][0]/volume[i][1])  + " , ");
        System.out.print((volumeCap[i][0] + volumeTransition[i][0]/2.0)+ " , ");
        System.out.print((volumeSurround[i][0] + volumeTransition[i][0]/2.0)+ " , ");

        System.out.print(alpha[i]+ "\n");
        //alpha[i] *= 1E3;
    }
System.out.println("****************************** ");


    /**
    * End of Debug
      */
       double ratio = 0;

       int freqencySample = 250;
       double intervalLog = (Math.log10(freqEnd) - Math.log10(freqStart)) / (double)freqencySample;

       double[][] dataXY = new double[freqencySample][2];
       for(int i = 0 ; i < freqencySample ; i++)
       {
           double freq =  Math.pow(10,Math.log10(freqStart) + intervalLog*i) ;

           double w = 2 * Math.PI * freq;
           dataXY[i][0] = freq;

           //dataXY[i][1] = getSPLByFrequecny(w,volume,alpha, zetaArray, 0.01, 1.21, valueMaganetForce, valueCircuitInductance, valueCircuitResistance,valueDrivingVoltage);
           //debug mode

           dataXY[i][1] = getSPLByFrequecny(w,volumeNormal,alpha, zetaArray, 0.01, 1.21, valueMaganetForce, valueCircuitInductance, valueCircuitResistance,valueDrivingVoltage);
           // normal mode

           //dataXY[i][1] = getSPLByFrequecny(w,volumeAbs,alpha, zetaArray, 0.01, 1.21, valueMaganetForce, valueCircuitInductance, valueCircuitResistance,valueDrivingVoltage);
           //emphasize rocking mode
       }

       return dataXY;

   }
   private double getSPLByFrequecny(double w,double volumeArray[],double alphaArray[],double zetaArray[],double r,double airDensity,double BL,double L,double R,double V)
   {
      //int modalSize = _frequencyArray.size();
      int modalSize = volumeArray.length;

      double wi = 0;

      double[] A = new double[modalSize];
      double[] B = new double[modalSize];
      double[] C = new double[modalSize];
      double[] D = new double[modalSize];
      double ASum = 0;
      double BSum = 0;
      double CSum = 0;
      double DSum = 0;



       for(int i = 0 ; i < modalSize ; i++)
       {
          // if(i != 0 &&i != 10  && i != 11 && i != 26 && i != 27)
          //     continue;

           double rfreq = Double.parseDouble(((String)_frequencyArray.get(i)).trim());
           wi = rfreq *Math.PI * 2;
           double a = wi*wi - w*w;
           double b = 2 * zetaArray[i]* wi *w;

           double volume = volumeArray[i];
           double alpha = alphaArray[i] ;
           double beta = airDensity * volume/ (2 * Math.PI * r );

           A[i] = - alpha* 9.8  * beta* w*w * a /(a*a+ b*b);
           B[i] =  alpha* 9.8  * beta* w*w * b /(a*a+ b*b);
           C[i] = alpha * alpha * 9.8  * a /(a*a+ b*b);
           D[i] = - alpha * alpha * 9.8  * b /(a*a+ b*b);

           ASum += A[i];
           BSum += B[i];
           CSum += C[i];
           DSum += D[i];
       }



       double h = R - BL * BL * DSum * w;
       double k = (L + BL * BL * CSum) * w;
       double H = h / (h * h + k * k);
       double K = -k / (h * h + k * k);

//****************

         double Pre = (ASum * H - BSum * K ) * BL*V;
       double Pim = (ASum * K + BSum * H ) * BL*V;
       double PAbs = Math.sqrt( Pre* Pre + Pim* Pim ) ;
       double SPL = 20 * Math.log10(PAbs / (2E-5));
        return SPL;


//****************

    /*  double GcAbs = Math.sqrt( CSum* CSum + DSum* DSum ) ;
       double GcValue = Math.log10(GcAbs );
       return GcValue;
//return GcAbs;*/
 //****************
      /*
       double GpAbs = Math.sqrt( ASum* ASum + BSum* BSum ) ;
       double GpValue = Math.log10(GpAbs );
       return Greturn GpValue;
       */
//****************


     /*    double Pre = (CSum * H - DSum * K) * BL * V;
        double Pim = (CSum * K + DSum * H) * BL * V;
        double U = Math.sqrt(Pre * Pre + Pim * Pim);
        return U;
*/
//****************
        /*
         double GcAbs = Math.sqrt( CSum* CSum + DSum* DSum ) ;
        double mass = 1 /-( Math.pow( w,2)* GcAbs);
        return mass;
*/

   }

    public double[] getBoundingBox()
    {
        double minX = 10000, minY = 10000, minZ = 10000, maxX = -10000, maxY = -10000, maxZ = -10000;
        double[][] minMax = new double[12][];

        minMax[0]    = _dataCap            .getMaxMin();
        minMax[1]    = _dataTran           .getMaxMin();
        minMax[2]    = _dataDiaphragm      .getMaxMin();
        minMax[3]    = _dataSurround       .getMaxMin();
        minMax[4]    = _dataMagnet         .getMaxMin();
        minMax[5]    = _dataTopPlate       .getMaxMin();
        minMax[6]    = _dataMagnetTop      .getMaxMin();
        minMax[7]    = _dataMagnetOuter    .getMaxMin();
        minMax[8]    = _dataCoil           .getMaxMin();
        minMax[9]    = _dataYokeBase       .getMaxMin();
        minMax[10]   = _dataYokeStage1     .getMaxMin();
        minMax[11]   = _dataYokeStage2     .getMaxMin();

        for(int i = 0 ; i <12 ; i++ )
        {
            if (minX > minMax[i][0])
                minX = minMax[i][0];
            if (minY > minMax[i][1])
                minY = minMax[i][1];
            if (minZ > minMax[i][2])
                minZ = minMax[i][2];

            if (maxX < minMax[i][3])
                maxX = minMax[i][3];
            if (maxY < minMax[i][4])
                maxY = minMax[i][4];
            if (maxZ < minMax[i][5])
                maxZ = minMax[i][5];
        }
        return new double[]{minX,minY,minZ,maxX,maxY,maxZ};

   }

   public double[] getPortSize()
   {
       this._dataAir.getDivisionInfo();
       double wValue[] = this._dataAir.getWCircleValue();
       double v[] = this._dataAir.getVCircle();
       double portWidth = wValue[9];
       double portPosition = v[11]- portWidth/2;

       if (portWidth < 10E-3)
       {
           portWidth = wValue[7];
           portPosition = v[9]- portWidth/2;
       }

       double portLength = 2*Math.PI* portPosition / this._countGirth;

       return new double[]{portWidth, portLength};


   }



}
