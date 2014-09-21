package sdt.framework;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import sdt.action.Actions;
import sdt.data.*;
import sdt.define.*;
import sdt.dialog.*;
import sdt.icon.*;
import sdt.java3d.*;
import sdt.material.*;
import sdt.menu.*;
import sdt.property.*;
import sdt.tree.*;

public class SDT_System implements DefineSystemConstant
{
    private SDT_Frame               _frame;
    private Dimension               _screenSize;
    private boolean                 is3DBuild;
    private boolean                 is2DBuild;
    private boolean                 _isPanelShow2Dor3D;
    private boolean                 isTreeBuild;
    private SDT_ManagerIcon           _managerIcon;
    private SDT_Modeler             _modeler;
    private SDT_Language            _language;
    private SDT_DataManager         _dataManager;
    private SDT_Config              _config;

    private Actions                 _actions;
    private Menus                    _menu;
    private MenuPopup               _popMenu;

    private String                  _saveFilePath;
    private String                  _AbaqusExePath;
    //private int                     _showType = DefineSystemConstant.VIEW_SHADINGWITHEDGE;

    private int                     _languageType = DefineSystemConstant.LANG_CHINESE_BIG5;

//    private Cursor cr = Toolkit.getDefaultToolkit().createCustomCursor( img1 , new Point(0,0) ,"MyCursor" );



    public int                       getLanguageType(){return this._languageType;}

    public SDT_Modeler               getModeler()   {return this._modeler;}

    public SDT_DataManager           getDataManager() {return this._dataManager;}

    public SDT_ManagerIcon           getManagerIcon() { return this._managerIcon;}

    public MenuPopup                 getMenuPopup()   {return this._popMenu;}

    public Menus                     getMenu()   {return this._menu;}

    public SDT_Config                getConfig()   {return this._config;}


    public boolean                   getIsPanelShow2Dor3D()     { return this._isPanelShow2Dor3D;}

    private SDT_filefilter           _filterSDT   = new SDT_filefilter("sdt", "SDT File");
    private SDT_filefilter           _filterINP   = new SDT_filefilter("inp", "INPUT File");
    private SDT_filefilter           _filterMAC   = new SDT_filefilter("mac", "MACRO File");


     public SDT_System()
     {
         this._frame          = new SDT_Frame();
         this._screenSize     = Toolkit.getDefaultToolkit().getScreenSize(); //new Dimension(1024,768);
         this.setDefaultLanguage();
         this._modeler        = new SDT_Modeler(this);
         this._dataManager    = new SDT_DataManager(this);
         this._config         = new SDT_Config();

         this._actions        = new Actions(this);
         this._menu           = new Menus(this, this._actions.GetCommands());
         this._popMenu        = new MenuPopup(this, this._actions.GetCommands());
         this._managerIcon = new SDT_ManagerIcon();
         this._saveFilePath = "";
         try
         {
             this.Initial();
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

     }


     private void Initial()
     {

         this.buildFrame();
         this.buildMenu();
         this._frame.setVisible(true);
         this.ChangeToStandardDesign();
     }

     private void buildFrame()
     {
         this._frame.setSize(this._screenSize.width, this._screenSize.height-50);
     }

     private void buildMenu()
     {
         JMenuBar menuBar = this._menu.createStandard();
         this._frame.setJMenuBar(menuBar);

         switch(this._languageType)
         {
             case DefineSystemConstant.LANG_ENGLINSH:
                 this._menu.getMenuItemCheckBoxFromName("ChkBoxLanguageEnglish").setSelected(true) ;
                 break;
             case DefineSystemConstant.LANG_CHINESE_BIG5:
                 this._menu.getMenuItemCheckBoxFromName("ChkBoxLanguageChinese").setSelected(true) ;
                 break;
         }

         this._menu.getMenuItemCheckBoxFromName("ChkBoxShading").setSelected(false);
         this._menu.getMenuItemCheckBoxFromName("ChkBoxShadingWithEdges").setSelected(true);
         this._menu.getMenuItemCheckBoxFromName("ChkBoxWireFrame").setSelected(false);

     }

     private void buildPopupMenu()
     {
         JPopupMenu menuPopupPartSolid = this._popMenu.createMenuPopupPartSolid();
         this._modeler.GetAssemTree().setMenuPopupSolidPrt(menuPopupPartSolid);

         JPopupMenu menuPopupPartShell = this._popMenu.createMenuPopupPartShell();
         this._modeler.GetAssemTree().setMenuPopupShellPrt(menuPopupPartShell);

         JPopupMenu menuPopupCone = this._popMenu.createMenuPopupCone();
         this._modeler.GetAssemTree().setMenuPopupAsmCone(menuPopupCone);

         JPopupMenu menuPopupSketchTop = this._popMenu.createMenuPopupSketchTop();
         this._modeler.GetAssemTree().setMenuPopupSktTop(menuPopupSketchTop);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupSktTop(menuPopupSketchTop);

         JPopupMenu menuPopupVCM = this._popMenu.createMenuPopupVCM();
         this._modeler.GetAssemTree().setMenuPopupAsmVCM(menuPopupVCM);

         JPopupMenu menuPopupYoke = this._popMenu.createMenuPopupYoke();
         this._modeler.GetAssemTree().setMenuPopupAsmYoke(menuPopupYoke);

         JPopupMenu menuPopupAir = this._popMenu.createMenuPopupAir();
         this._modeler.GetAssemTree().setMenuPopupAsmAir(menuPopupAir);

         JPopupMenu menuPopupCapSection = this._popMenu.createMenuPopupSketchSectionCap();
         this._modeler.GetAssemTree().setMenuPopupSktSectionCap(menuPopupCapSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupCapSection(menuPopupCapSection);

         JPopupMenu menuPopupSurroundSection = this._popMenu.createMenuPopupSketchSectionSurround();
         this._modeler.GetAssemTree().setMenuPopupSktSectionSurround(menuPopupSurroundSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupSurroundSection(menuPopupSurroundSection);

         JPopupMenu menuPopupTransitionSection = this._popMenu.createMenuPopupSketchSectionTransition();
         this._modeler.GetAssemTree().setMenuPopupSktSectionTransition(menuPopupTransitionSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupTransitionSection(menuPopupTransitionSection);

         JPopupMenu menuPopupDiaphragmSection = this._popMenu.createMenuPopupSketchSectionDiaphragm();
         this._modeler.GetAssemTree().setMenuPopupSktSectionDiaphragm(menuPopupDiaphragmSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupDiaphragmSection(menuPopupDiaphragmSection);

         JPopupMenu menuPopupCoilSection = this._popMenu.createMenuPopupSketchSectionCoil();
         this._modeler.GetAssemTree().setMenuPopupSktSectionCoil(menuPopupCoilSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupCoilSection(menuPopupCoilSection);

         JPopupMenu menuPopupMagnetSection = this._popMenu.createMenuPopupSketchSectionMagnet();
         this._modeler.GetAssemTree().setMenuPopupSktSectionMagnet(menuPopupMagnetSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupMagnetSection(menuPopupMagnetSection);

         JPopupMenu menuPopupMagnetTopSection = this._popMenu.createMenuPopupSketchSectionMagnetTop();
         this._modeler.GetAssemTree().setMenuPopupSktSectionMagnetTop(menuPopupMagnetTopSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupMagnetTopSection(menuPopupMagnetTopSection);

         JPopupMenu menuPopupMagnetOuterSection = this._popMenu.createMenuPopupSketchSectionMagnetOuter();
         this._modeler.GetAssemTree().setMenuPopupSktSectionMagnetOuter(menuPopupMagnetOuterSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupMagnetOuterSection(menuPopupMagnetOuterSection);


         JPopupMenu menuPopupTopPlateSection = this._popMenu.createMenuPopupSketchSectionTopPlate();
         this._modeler.GetAssemTree().setMenuPopupSktSectionTopPlate(menuPopupTopPlateSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupTopPlateSection(menuPopupTopPlateSection);

         JPopupMenu menuPopupYokeBaseSection = this._popMenu.createMenuPopupSketchSectionYokeBase();
         this._modeler.GetAssemTree().setMenuPopupSktSectionYokeBase(menuPopupYokeBaseSection);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupYokeBaseSection(menuPopupYokeBaseSection);

         JPopupMenu menuPopupYokeStage1Section = this._popMenu.createMenuPopupSketchSectionYokeStage1();
         this._modeler.GetAssemTree().setMenuPopupSktSectionYokeStage1(menuPopupYokeStage1Section);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupYokeStage1Section(menuPopupYokeStage1Section);

         JPopupMenu menuPopupYokeStage2Section = this._popMenu.createMenuPopupSketchSectionYokeStage2();
         this._modeler.GetAssemTree().setMenuPopupSktSectionYokeStage2(menuPopupYokeStage2Section);
         this.getModeler().getPanel2D().GetDrawPanel().setMenuPopupYokeStage2Section(menuPopupYokeStage2Section);

     }



     public void setDefaultLanguage()
     {
         Calendar cal = Calendar.getInstance();
         TimeZone tz = cal.getTimeZone();
         if (tz.getID().equals("Asia/Shanghai") || tz.getID().equals("Asia/Taipei"))
         {
             this.setLanguage(DefineSystemConstant.LANG_CHINESE_BIG5);
         }
         else
         {
             this.setLanguage(DefineSystemConstant.LANG_ENGLINSH);
         }
     }

     public void setLanguage(int language)
     {
         switch (language)
         {
             case DefineSystemConstant.LANG_ENGLINSH:
                 this._languageType = DefineSystemConstant.LANG_ENGLINSH;
                 _language = new SDT_Language(this, DefineSystemConstant.INTERFACE_ENG);
                 break;

             case DefineSystemConstant.LANG_CHINESE_BIG5:
                 this._languageType = DefineSystemConstant.LANG_CHINESE_BIG5;
                 _language = new SDT_Language(this, DefineSystemConstant.INTERFACE_CHN);
                 break;
         }
         if (_menu != null)
             this._menu.reloadMenubar();
         if(_modeler!=null && _modeler.GetAssemTree() != null)
             this._modeler.GetAssemTree().reloadSpeakerTree();
         if(this._popMenu != null)
             this.buildPopupMenu();
     }

     public String GetInterfaceString(String str)
     {
         String result = "";
         result = this._language.GetResourceString(str);
         return result;
     }


     public void ChangeToStandardDesign()
     {
         this._managerIcon.CreateIconModel();
         this._managerIcon.CreateIconTreeAsm();
         this._managerIcon.CreateIconTreePart();
         this._managerIcon.CreateIconTreeSurf();
         this._managerIcon.CreateIconTreeSk();

         this.changeTo2D();
         this.buildTree();
     }

     public void ChangeToPreliminaryDesign()
     {
         /*JMenuBar menuBar = this.menu.CreatePartDesign();
                  this.frame.setJMenuBar(menuBar);*/
         this._managerIcon.CreateIconModel();
         this._managerIcon.CreateIconTreeAsm();
         this._managerIcon.CreateIconTreePart();
         this._managerIcon.CreateIconTreeSurf();
         this._managerIcon.CreateIconTreeSk();

          JMenuBar menuBar = this._menu.createPreliminaryModuleDesign();
          this._frame.setJMenuBar(menuBar);

         this.changeTo2D();
         this.buildTree();
     }

     private void build3D()
     {

         if (this.is3DBuild == false)
         {
             this._modeler.buildPanel3D();

             this.is3DBuild = true;
             _isPanelShow2Dor3D = false;
         }
         else
         {
             _isPanelShow2Dor3D = false;
         }
     }

     private void build2D()
     {
         JPanel panel2d;
         if (this.is2DBuild == false)
         {
             panel2d = this._modeler.buildPanel2D();
             this._frame.SetPanel2D(panel2d);

             this.is2DBuild = true;
             this._isPanelShow2Dor3D = true;
         }
         else
         {
             panel2d = this._modeler.getPanel2D();
             this._frame.SetPanel2D(panel2d);
             this._isPanelShow2Dor3D = true;

         }
     }

     public void changeTo2D()
     {
         if(this._isPanelShow2Dor3D)
             return;


         JCheckBoxMenuItem itemRotate = this._menu.getMenuItemCheckBoxFromName("ChkBoxRotate");
         itemRotate.setEnabled(false);
         itemRotate.setSelected(false);

         JMenu itemMenu = this._menu.getMenuFromName("ShadingMode");
         itemMenu.setEnabled(false);



         this.build2D();
         if(this.isTreeBuild)
             this._modeler.getPanel2D().GetDrawPanel().showElement();


     }
     public void changeTo3D()
     {
         if(!this._isPanelShow2Dor3D)
             return;

         JCheckBoxMenuItem itemRotate = this._menu.getMenuItemCheckBoxFromName("ChkBoxRotate");
         itemRotate.setEnabled(true);
         JMenu itemMenu = this._menu.getMenuFromName("ShadingMode");
         itemMenu.setEnabled(true);


         this.build3D();

         SDT_Array3DMesh meshArray[] = this._dataManager.getArrayMesh();

         this._modeler.getPanel3D().DetachRoot();
         this._modeler.getPanel3D().SetMeshArray(meshArray[0], this._dataManager.getColorByType(meshArray[0].getTypeID()) );

         for(int i = 1 ; i < meshArray.length; i++)
         {
              this._modeler.getPanel3D().AddMeshArray(meshArray[i], this._dataManager.getColorByType(meshArray[i].getTypeID()) );
         }


         SDT_Array3DEdge edgeArray = this._dataManager.getArrayEdge();
         this._modeler.getPanel3D().SetEEArray(edgeArray);

         this._modeler.getPanel3D().AttachRoot();

         this._modeler.getPanel3D().ShowType();
         this._modeler.getPanel3D().SetFititAll();
         this._frame.SetPanel3D(this._modeler.getPanel3D());
     }

     public void changeToAir3D()
     {
         if(!this._isPanelShow2Dor3D)
             return;
         JCheckBoxMenuItem itemRotate = this._menu.getMenuItemCheckBoxFromName("ChkBoxRotate");
         itemRotate.setEnabled(true);
         JMenu itemMenu = this._menu.getMenuFromName("ShadingMode");
         itemMenu.setEnabled(true);
         this.build3D();

         SDT_Array3DMesh meshArray[] = this._dataManager.getAirArrayMesh();//每一個area就是一個SDT_Array3DMesh

         this._modeler.getPanel3D().DetachRoot();
         this._modeler.getPanel3D().SetMeshArray(meshArray[0], this._dataManager.getColorByType(meshArray[0].getTypeID()) );

         for(int i = 1 ; i < meshArray.length; i++)
         {
              this._modeler.getPanel3D().AddMeshArray(meshArray[i], this._dataManager.getColorByType(meshArray[i].getTypeID()) );
         }


         SDT_Array3DEdge edgeArray = this._dataManager.getArrayEdge();
         this._modeler.getPanel3D().SetEEArray(edgeArray);

         this._modeler.getPanel3D().AttachRoot();

         this._modeler.getPanel3D().ShowType();
         this._modeler.getPanel3D().SetFititAll();
         this._frame.SetPanel3D(this._modeler.getPanel3D());
     }

     public void changeTo3DDeformed(int index)
     {
        // if (!this._isPanelShow2Dor3D)
        //     return;
        JCheckBoxMenuItem itemRotate = this._menu.getMenuItemCheckBoxFromName("ChkBoxRotate");
        itemRotate.setEnabled(true);
        JMenu itemMenu = this._menu.getMenuFromName("ShadingMode");
        itemMenu.setEnabled(true);


         this.build3D();

         SDT_Array3DMesh meshArray[] = this._dataManager.getArrayMeshDeformed( index);

         this._modeler.getPanel3D().DetachRoot();
         this._modeler.getPanel3D().SetMeshArray(meshArray[0], this._dataManager.getColorByType(meshArray[0].getTypeID()));

         for (int i = 1; i < meshArray.length; i++)
         {
             this._modeler.getPanel3D().AddMeshArray(meshArray[i], this._dataManager.getColorByType(meshArray[i].getTypeID()));
         }


         SDT_Array3DEdge edgeArray = this._dataManager.getArrayEdge();
         this._modeler.getPanel3D().SetEEArray(edgeArray);

         this._modeler.getPanel3D().AttachRoot();

         this._modeler.getPanel3D().ShowType();
         this._modeler.getPanel3D().SetFititAll();
         this._frame.SetPanel3D(this._modeler.getPanel3D());
     }









     private void buildTree()
     {
         SDT_TreeMain asmTree;
         if (this.isTreeBuild == false)
         {
             asmTree = this._modeler.BuildTree();

             this._frame.SetTree(asmTree);
             this.isTreeBuild = true;
             asmTree.ExpandTreeFirst();

             this.buildPopupMenu();
         }
     }

     public SDT_Frame getFrame()
     {
         return this._frame;
     }

     public void openFile(String filePath)
     {
         this._dataManager.Open(filePath);
         this._saveFilePath = filePath;

         this._config.setRecentFile(_saveFilePath);
         this._config.save();
         if (_menu != null)
         {
             JMenuBar mBar = this._menu.reloadMenubar();
             this._frame.setJMenuBar(mBar);
         }

     }

     public void openFile() throws HeadlessException
     {

         String str = this.GetInterfaceString("dia_MessageStrIfReallyWantToOpenFile");
         DiaAsk dia = new DiaAsk(this, str);
         if (dia.GetResult() == false)
             return;

         String lastOpenPath = this._config.getRecentOpenPath();
         JFileChooser fileChooser = new JFileChooser(lastOpenPath);

         //JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileFilter(this._filterSDT);
         int result = fileChooser.showOpenDialog(this._frame);
         if (result != -1)
         {
             File newfile = fileChooser.getSelectedFile();
             if (newfile == null)
                 return;
             try
             {
                 //System.out.println("openPath === " + newfile.getPath());
                 this._dataManager.Open(newfile.getPath());
                 this._saveFilePath = newfile.getPath();
             }
             catch (Exception ex)
             {
                 ex.printStackTrace();
             }

             this._config.setRecentFile(_saveFilePath);
             this._config.setRecentOpenPath(newfile.getParent());
             this._config.save();
             if (_menu != null)
             {
                 JMenuBar mBar = this._menu.reloadMenubar();
                 this._frame.setJMenuBar(mBar);
             }


         }
    }

     public void SaveFile() throws HeadlessException
     {
         if(this._saveFilePath != "")
         {
             try
             {
                 this._dataManager.Save(this._saveFilePath);
             }
             catch (Exception ex)
             {
                 ex.printStackTrace();
             }
         }
         else
         {
             this.SaveAsFile();
         }
     }

     public void SaveAsFile() throws HeadlessException
     {
         String lastSavePath = this._config.getRecentSavePath();

         JFileChooser fileChooser = new JFileChooser(lastSavePath);
         fileChooser.setFileFilter(this._filterSDT);
         if (this._frame.GetContentPane() == null)
             return;

         int result = fileChooser.showSaveDialog(this._frame);

         if (result != 1)
         {
             File newfile = fileChooser.getSelectedFile();

             String fileName = newfile.getName();
             String parentPath = newfile.getParent();

             File parentFile = new File(parentPath);
             String[] allFile = parentFile.list();

             if(!fileName.endsWith(".sdt"))
                 fileName += ".sdt";

             for (int i = 0; i < allFile.length; i++)
             {
                 if (allFile[i].endsWith(".sdt"))
                 {
                     if (fileName.equalsIgnoreCase(allFile[i]))
                     {
                         String str = this.GetInterfaceString("dia_MessageStrIfReallyWantToOverWriteFile");
                         DiaAsk dia = new DiaAsk(this, str);
                         if (dia.GetResult() == false)
                             return;
                         else
                             break;
                     }
                 }
             }

             String filePath = parentPath + "\\" + fileName;
             //System.out.println("finalPath === " + filePath);
             try
             {
                 this._saveFilePath = filePath;
                 this._dataManager.Save(filePath);
             }
             catch (Exception ex)
             {
                 ex.printStackTrace();
             }
             this._config.setRecentSavePath(parentPath);
             this._config.setRecentFile(filePath);
             this._config.save();
             if (_menu != null)
             {
                 JMenuBar mBar = this._menu.reloadMenubar();
                 this._frame.setJMenuBar(mBar);
             }
         }
     }



     public boolean OpenAbaqusExe()
     {
         boolean isFileSelected = false;

         JFileChooser jFileChooserAbqus = new JFileChooser();

         int result = jFileChooserAbqus.showOpenDialog(this._frame);
         if (result != -1)
         {
             File newfile = jFileChooserAbqus.getSelectedFile();
             if (newfile == null)
                 return false;
             try
             {
                 //this.jTextBrowsAbq.setText(newfile.getPath());
                 _AbaqusExePath = newfile.getPath();
                 isFileSelected = true;

                 this._config.setAbaqusPath(_AbaqusExePath);
                 this._config.save();

             }
             catch (Exception ex)
             {
                 ex.printStackTrace();
                 isFileSelected = false;
                 //this.jTextBrowsAbq.setText("");
             }
         }
         return isFileSelected;
     }




     public void exportInpFile() throws HeadlessException
     {
         String lastExportPath = this._config.getRecentExportPath();
         JFileChooser fileChooser = new JFileChooser(lastExportPath);


         fileChooser.setFileFilter(this._filterINP);

         if (this._frame.GetContentPane() == null)
             return;

         int result = fileChooser.showSaveDialog(this._frame);

         if (result != 1)
         {
             File newfile = fileChooser.getSelectedFile();

             String fileName = newfile.getName();
             String parentPath = newfile.getParent();

             File parentFile = new File(parentPath);
             String[] allFile = parentFile.list();

             if(!fileName.endsWith(".inp"))
                 fileName += ".inp";

             for (int i = 0; i < allFile.length; i++)
             {
                 if (allFile[i].endsWith(".inp"))
                 {
                     if (fileName.equalsIgnoreCase(allFile[i]))
                     {
                         String str = this.GetInterfaceString("dia_MessageStrIfReallyWantToOverWriteFile");
                         DiaAsk dia = new DiaAsk(this, str);
                         if (dia.GetResult() == false)
                             return;
                         else
                             break;
                     }
                 }
             }

             String filePath = parentPath + "\\" + fileName;

             try
             {
                 boolean isExported = this._dataManager.exportInpFile(filePath, true, false, 0);
             }
             catch (Exception ex)
             {
                 ex.printStackTrace();
             }
             this._config.setRecentExportPath(parentPath);
             this._config.save();
        }
     }

     public void exportMacFile() throws HeadlessException
     {
         String lastExportPath = this._config.getRecentExportPath();
         JFileChooser fileChooser = new JFileChooser(lastExportPath);

         fileChooser.setFileFilter(this._filterMAC);

         if (this._frame.GetContentPane() == null)
             return;

         int result = fileChooser.showSaveDialog(this._frame);

         if (result != 1)
         {
             File newfile = fileChooser.getSelectedFile();

             String fileName = newfile.getName();
             String parentPath = newfile.getParent();

             File parentFile = new File(parentPath);
             String[] allFile = parentFile.list();

             if (fileName.endsWith(".mac"))
                 fileName = fileName.substring(0,fileName.length()-4);

             for (int i = 0; i < allFile.length; i++)
             {
                 if (allFile[i].endsWith(".mac"))
                 {
                     if (fileName.equalsIgnoreCase(allFile[i]))
                     {
                         String str = this.GetInterfaceString("dia_MessageStrIfReallyWantToOverWriteFile");
                         DiaAsk dia = new DiaAsk(this, str);
                         if (dia.GetResult() == false)
                             return;
                         else
                             break;
                     }
                 }
             }
             parentPath += "\\";
             String filePath = parentPath + fileName;

             try
             {
                 boolean isExported = this._dataManager.exportMacFile(parentPath, fileName);
             }
             catch (Exception ex)
             {
                 ex.printStackTrace();
             }
             this._config.setRecentExportPath(parentPath);
             this._config.save();
         }
     }

     public void pupupMaterialAttribute(String str)
     {
         DiaMaterialBHData dia = null;
         if (str.endsWith("Curve"))
              dia = new DiaMaterialBHData(this, str);
         else
              dia = new DiaMaterialBHData(this,((MaterialSteel)this._dataManager.getCurrentData().getMaterial()).getHBData().getData());
         System.out.println("string  : " + str);
     }





}
