package sdt.dialog;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import sdt.framework.*;
import sdt.data.SDT_DataManager;
import sdt.tree.SDT_ObjectNode;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.TreePath;
import sdt.tree.SDT_IconNode;
import javax.swing.event.ChangeEvent;
import java.io.File;

public class DiaToolOption extends DiaMain
{
    private JSplitPane              _splited;
    private OptionTree              _tree;
    private JTabbedPane             _tabbedColor;

    private JPanel                  _jPanelColorBasic;
    private JPanel                  _jPanelColorDrawingObject;
    private JPanel                  _jPanelAbaqusSetting;

    private JLabel                  _jLabelColorCap;
    private JLabel                  _jLabelColorTransition;
    private JLabel                  _jLabelColorDiaphragm;
    private JLabel                  _jLabelColorSurround;

    private JLabel                  _jLabelColorCoil;
    private JLabel                  _jLabelColorFormer;

    private JLabel                  _jLabelColorMagnet;
    private JLabel                  _jLabelColorTopPlate;
    private JLabel                  _jLabelColorMagnetTop;
    private JLabel                  _jLabelColorMagnetOuter;
    private JLabel                  _jLabelColorYokeBase;
    private JLabel                  _jLabelColorStage1;
    private JLabel                  _jLabelColorStage2;

    private JLabel                  _jLabelAbaqusExecute;
    private JTextField              _jTextAbaqusPath;
    private JButton                 _jButtonAbaqusPathBrowse;

    private JLabel                   _jLabelANSYSExecute;
    private JTextField               _jTextANSYSPath;
    private JButton                  _jButtonANSYSPathBrowse;

    private JLabel                   _jLabelWorkingDirName;
    private JTextField               _jTextWorkingExecute;
    private JButton                  _jButtonWorkingExecute;

    private JLabel                   _jLabelAbaqusWorkFolderName;
    private JTextField               _jTextAbaqusWorkFolderName;

    private JLabel                   _jLabelAnsysWorkFolderName;
    private JTextField               _jTextAnsysWorkFolderName;

    private JLabel                   _jLabelWorkTime;
    private JLabel                   _jLabelWorkTimeUnit;
    private JSpinner                 _jSpinnerWorkTime;
    private SpinnerNumberModel       _jSpinnerModel;





    private JLabel                  _jLabelColorPoint2D;
    private JLabel                  _jLabelColorLine2D;
    private JLabel                  _jLabelColorSolid2D;
    private JLabel                  _jLabelSizePoint2D;
    private JLabel                  _jLabelSizeLine2D;
    private JLabel                  _jLabelColorBackgroudUp;
    private JLabel                  _jLabelColorBackgroudDown;


    private JButton                  _jButtonColorCap;
    private JButton                  _jButtonColorTransition;
    private JButton                  _jButtonColorDiaphragm;
    private JButton                  _jButtonColorSurround;

    private JButton                  _jButtonColorCoil;
    private JButton                  _jButtonColorFormer;

    private JButton                  _jButtonColorMagnet;
    private JButton                  _jButtonColorTopPlate;
    private JButton                  _jButtonColorMagnetTop;
    private JButton                  _jButtonColorMagnetOuter;
    private JButton                  _jButtonColorYokeBase;
    private JButton                  _jButtonColorStage1;
    private JButton                  _jButtonColorStage2;

    private JButton                  _jButtonColorPoint2D;
    private String[]                 _sizePointString =  {"2","4","6","8"};
    private JComboBox                _jComboBoxSizePoint2D;

    private JButton                  _jButtonColorLine2D;
    private String[]                 _sizeLineString =   {"1","1.5","2","3","5"};
    private JComboBox                _jComboBoxSizeLine2D;
    private JButton                  _jButtonColorSolid2D;
    private JButton                  _jButtonColorBackgroundUp;
    private JButton                  _jButtonColorBackgroundDown;
    private JButton                  _jButtonResetFactorySettingBasic;
    private JButton                  _jButtonResetFactorySettingDrawingObject;

    private boolean                  _isChangedColorCap;
    private boolean                  _isChangedColorTransition;
    private boolean                  _isChangedColorDiaphragm;
    private boolean                  _isChangedColorSurround;
    private boolean                  _isChangedColorCoil;
    private boolean                  _isChangedColorFormer;

    private boolean                  _isChangedColorMagnet;
    private boolean                  _isChangedColorTopPlate;
    private boolean                  _isChangedColorMagnetTop;
    private boolean                  _isChangedColorMagnetOuter;
    private boolean                  _isChangedColorYokeBase;
    private boolean                  _isChangedColorStage1;
    private boolean                  _isChangedColorStage2;

    private boolean                  _isChangedColorPoint2D;
    private boolean                  _isChangedColorLine2D;
    private boolean                  _isChangedSizePoint2D;
    private boolean                  _isChangedSizeLine2D;

    private boolean                  _isChangedColorSolid2D;
    private boolean                  _isChangedColorBackgroudUp;
    private boolean                  _isChangedColorBackgroudDown;

    private boolean                  _isChangedAbaqusPath;
    private boolean                  _isChangedAnsysPath;
    private boolean                  _isChangedWorkingPath;

    private JButton                  _jButtonApply;

    private SDT_filefilter           _filterEXE   = new SDT_filefilter("exe", "EXE File");

    public DiaToolOption(SDT_System system)
    {
        super(system, true);
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public DiaToolOption(SDT_System system, int showTreeNode)
    {
        super(system, true);
        try
        {
            String str = "";

            str = this._system.GetInterfaceString("TitleOption");

            this.readDiaData();
            this.setTitle(str);
            this.setSize(550, 480);
            this.setLocation();
            this._splited.removeAll();
            this._splited.add(_tree, JSplitPane.LEFT);
            this._splited.add(this._jPanelAbaqusSetting, JSplitPane.RIGHT);
            this._splited.setDividerLocation(150);

            this.setVisible(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    void jbInit() throws Exception
    {
        String str = "";

        str = this._system.GetInterfaceString("TitleOption");

        this.readDiaData();
        this.setTitle(str);
        this.setSize(550, 480);

        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        this._tree                      = new OptionTree(this._system);

        this._tabbedColor                    = new JTabbedPane();
        this._jPanelColorBasic          = new JPanel();
        this._jPanelColorDrawingObject  = new JPanel();
        this._jPanelAbaqusSetting       = new JPanel();

        this._splited                   = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);//,_tree,this._tabbedColor);

        this._jLabelColorCap            = new JLabel("Cap :");
        this._jLabelColorTransition     = new JLabel("Transition :");
        this._jLabelColorDiaphragm      = new JLabel("Diaphragm :");
        this._jLabelColorSurround       = new JLabel("Surround :");
        this._jLabelColorCoil           = new JLabel("Coil :");
        this._jLabelColorFormer         = new JLabel("Former :");
        this._jLabelColorMagnet         = new JLabel("Magnet :");
        this._jLabelColorTopPlate       = new JLabel("TopPlate :");
        this._jLabelColorMagnetTop      = new JLabel("MagnetTop :");
        this._jLabelColorMagnetOuter    = new JLabel("MagnetOuter :");
        this._jLabelColorYokeBase       = new JLabel("YokeBase :");
        this._jLabelColorStage1         = new JLabel("Stage1");
        this._jLabelColorStage2         = new JLabel("Stage2");

        this._jButtonColorCap           = new JButton();
        this._jButtonColorTransition    = new JButton();
        this._jButtonColorDiaphragm     = new JButton();
        this._jButtonColorSurround      = new JButton();
        this._jButtonColorCoil          = new JButton();
        this._jButtonColorFormer        = new JButton();
        this._jButtonColorMagnet        = new JButton();
        this._jButtonColorTopPlate      = new JButton();
        this._jButtonColorMagnetTop     = new JButton();
        this._jButtonColorMagnetOuter   = new JButton();
        this._jButtonColorYokeBase      = new JButton();
        this._jButtonColorStage1        = new JButton();
        this._jButtonColorStage2        = new JButton();


        String strReset = this._system.GetInterfaceString("OptionButtonReset");
        this._jButtonResetFactorySettingBasic = new JButton(strReset);
        this._jButtonResetFactorySettingDrawingObject = new JButton(strReset);

        _jLabelAbaqusExecute    = new JLabel("Abaqus Execution Path:");
        _jTextAbaqusPath        = new JTextField();
        _jButtonAbaqusPathBrowse = new JButton("...");

        _jLabelANSYSExecute    = new JLabel("ANSYS Execution Path:");
        _jTextANSYSPath        = new JTextField();
        _jButtonANSYSPathBrowse = new JButton("...");

        this._jLabelAbaqusWorkFolderName = new JLabel("ABAQUS Work Folder:");
        this._jTextAbaqusWorkFolderName = new JTextField();

        this._jLabelAnsysWorkFolderName = new JLabel("ANSYS Work Folder:");
        this._jTextAnsysWorkFolderName = new JTextField();

        this._jLabelWorkTime = new JLabel("Work Time:");
        this._jLabelWorkTimeUnit = new JLabel("Minutes");
        this._jSpinnerModel = new SpinnerNumberModel(10,1,60,1);
        this._jSpinnerWorkTime = new JSpinner(this._jSpinnerModel);



        _jLabelWorkingDirName         = new JLabel("Working Dir Path:");
        this._jTextWorkingExecute     = new JTextField();
        this._jButtonWorkingExecute   = new JButton("...");

        BevelBorder border = new BevelBorder(BevelBorder.RAISED);

        this._jButtonColorCap               .setBorder(border);
        this._jButtonColorTransition        .setBorder(border);
        this._jButtonColorDiaphragm         .setBorder(border);
        this._jButtonColorSurround          .setBorder(border);
        this._jButtonColorCoil              .setBorder(border);
        this._jButtonColorFormer            .setBorder(border);
        this._jButtonColorMagnet            .setBorder(border);
        this._jButtonColorTopPlate          .setBorder(border);
        this._jButtonColorMagnetTop         .setBorder(border);
        this._jButtonColorMagnetOuter       .setBorder(border);
        this._jButtonColorYokeBase          .setBorder(border);
        this._jButtonColorStage1            .setBorder(border);
        this._jButtonColorStage2            .setBorder(border);



        this._jButtonColorCap.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorTransition.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorDiaphragm.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorSurround.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorCoil.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorFormer.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorMagnet.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorTopPlate.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorMagnetTop.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorMagnetOuter.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorYokeBase.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorStage1.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorStage2.setUI(new javax.swing.plaf.metal.MetalButtonUI());


        this._jLabelSizePoint2D         = new JLabel("Point Size:");
        this._jLabelSizeLine2D          = new JLabel("Line Size:");
        this._jLabelColorPoint2D        = new JLabel("Point Color:");
        this._jLabelColorLine2D         = new JLabel("Line Color:");
        this._jLabelColorSolid2D        = new JLabel("Solid Color:");
        this._jLabelColorBackgroudUp    = new JLabel("Background Color(Up):");
        this._jLabelColorBackgroudDown  = new JLabel("Background Color(Down):");

        _sizePointString = new String[]
                           {"2", "4", "6", "8"};

        _sizeLineString = new String[]
                          {"1", "1.5", "2", "3", "5"};
        this._jComboBoxSizePoint2D        = new JComboBox(_sizePointString);

        this._jComboBoxSizeLine2D         = new JComboBox(_sizeLineString);
        this._jButtonColorPoint2D       = new JButton();
        this._jButtonColorLine2D        = new JButton();
        this._jButtonColorSolid2D        = new JButton();
        this._jButtonColorBackgroundUp   = new JButton();
        this._jButtonColorBackgroundDown= new JButton();

        String strApply = this._system.GetInterfaceString("dia_ButtonApply");
        this._jButtonApply               = new JButton(strApply);

        //this._jComboBoxSizePoint2D.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        //this._jComboBoxSizeLine2D.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorPoint2D.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorLine2D.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorSolid2D.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorBackgroundUp.setUI(new javax.swing.plaf.metal.MetalButtonUI());
        this._jButtonColorBackgroundDown.setUI(new javax.swing.plaf.metal.MetalButtonUI());

        this._jComboBoxSizePoint2D        .setBorder(border);
        this._jComboBoxSizeLine2D         .setBorder(border);
        this._jButtonColorPoint2D       .setBorder(border);
        this._jButtonColorLine2D        .setBorder(border);
        this._jButtonColorSolid2D        .setBorder(border);
        this._jButtonColorBackgroundUp   .setBorder(border);
        this._jButtonColorBackgroundDown .setBorder(border);

        this._isChangedColorCap         = false;
        this._isChangedColorTransition  = false;
        this._isChangedColorDiaphragm   = false;
        this._isChangedColorSurround    = false;
        this._isChangedColorCoil        = false;
        this._isChangedColorFormer      = false;

        this._isChangedColorMagnet      = false;
        this._isChangedColorTopPlate    = false;
        this._isChangedColorMagnetTop   = false;
        this._isChangedColorMagnetOuter = false;
        this._isChangedColorYokeBase    = false;
        this._isChangedColorStage1      = false;
        this._isChangedColorStage2      = false;

        this._isChangedColorPoint2D     = false;
        this._isChangedColorLine2D      = false;
        this._isChangedSizePoint2D      = false;
        this._isChangedSizeLine2D       = false;


        this._isChangedColorBackgroudUp    = false;
        this._isChangedColorBackgroudDown  = false;

        this._isChangedAbaqusPath          = false;
        this._isChangedAnsysPath          = false;
        this._isChangedWorkingPath          = false;






    }

    public void getTree()
    {
        this._splited.removeAll();
        this._splited.add(_tree, JSplitPane.LEFT);
        this._splited.add(this._jPanelAbaqusSetting, JSplitPane.RIGHT);
        this._splited.setDividerLocation(150);


        //return this._tree;
    }

    protected void setSizeComponent()
    {





    }

    protected void addComponent()
    {
        this._contentPane.setLayout(null);

        this._splited.removeAll();
        this._splited.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this._splited.add(_tree,JSplitPane.LEFT);
        this._splited.add(this._tabbedColor,JSplitPane.RIGHT);

        this._splited.setBounds(10, 10, 530, 435);
        this._splited.setDividerLocation(150);
        this._splited.setDividerSize(10);
        this._splited.setEnabled(true);

        //this._tree.setBounds(10,10,150,435) ;

        this.addComponentColor();
        this.addComponentAbaqusSetting();

        this._contentPane.add(this._splited);

        this._jButtonOk.setBounds(290, 445, 60, 25);
        this._jButtonCancel.setBounds(430, 445, 60, 25);
        this._jButtonApply.setBounds(360, 445, 60, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);
        this._contentPane.add(this._jButtonApply);
    }

    private void addComponentColor()
    {
        //this._tabbedColor.setBounds(10, 10, 370, 435);
        String strBasic = this._system.GetInterfaceString("OptionTabColorBasicObject");
        String strDrawingObject = this._system.GetInterfaceString("OptionTabColorDrawingObject");
        this._tabbedColor.add(_jPanelColorBasic,strBasic);
        this._tabbedColor.add(_jPanelColorDrawingObject,strDrawingObject);
        _jPanelColorBasic.setLayout(null);;
        _jPanelColorDrawingObject.setLayout(null);;

        _jPanelColorBasic.add(this._jLabelSizePoint2D);
        _jPanelColorBasic.add(this._jComboBoxSizePoint2D);
        _jPanelColorBasic.add(this._jLabelColorPoint2D);
        _jPanelColorBasic.add(this._jButtonColorPoint2D);
        _jPanelColorBasic.add(this._jLabelSizeLine2D);
        _jPanelColorBasic.add(this._jComboBoxSizeLine2D);
        _jPanelColorBasic.add(this._jLabelColorLine2D);
        _jPanelColorBasic.add(this._jButtonColorLine2D);
        _jPanelColorBasic.add(this._jLabelColorSolid2D);
        _jPanelColorBasic.add(this._jButtonColorSolid2D);
        _jPanelColorBasic.add(this._jLabelColorBackgroudUp);
        _jPanelColorBasic.add(this._jButtonColorBackgroundUp);
        _jPanelColorBasic.add(this._jLabelColorBackgroudDown);
        _jPanelColorBasic.add(this._jButtonColorBackgroundDown);

        _jPanelColorBasic.add(this._jButtonResetFactorySettingBasic);

        this._jLabelSizePoint2D         .setBounds(20,  20, 130, 25);
        this._jLabelSizeLine2D          .setBounds(20,  55, 130, 25);
        this._jLabelColorPoint2D        .setBounds(20,  90, 130, 25);
        this._jLabelColorLine2D         .setBounds(20, 125, 130, 25);
        this._jLabelColorSolid2D        .setBounds(20, 160, 130, 25);
        this._jLabelColorBackgroudUp    .setBounds(20, 190, 130, 25);
        this._jLabelColorBackgroudDown  .setBounds(20, 230, 130, 25);


        this._jComboBoxSizePoint2D         .setBounds(160, 20, 65, 25);
        this._jComboBoxSizeLine2D          .setBounds(160, 55, 65, 25);
        this._jButtonColorPoint2D        .setBounds(160, 90, 50, 25);
        this._jButtonColorLine2D         .setBounds(160,125, 50, 25);
        this._jButtonColorSolid2D        .setBounds(160,160, 50, 25);
        this._jButtonColorBackgroundUp   .setBounds(160,195, 50, 25);
        this._jButtonColorBackgroundDown .setBounds(160,230, 50, 25);

        this._jButtonResetFactorySettingBasic   .setBounds(250,360, 120, 25);

        _jPanelColorDrawingObject.add(this._jLabelColorCap         );
        _jPanelColorDrawingObject.add(this._jLabelColorTransition  );
        _jPanelColorDrawingObject.add(this._jLabelColorDiaphragm   );
        _jPanelColorDrawingObject.add(this._jLabelColorSurround    );
        _jPanelColorDrawingObject.add(this._jLabelColorCoil        );
        _jPanelColorDrawingObject.add(this._jLabelColorFormer      );
        _jPanelColorDrawingObject.add(this._jLabelColorMagnet      );
        _jPanelColorDrawingObject.add(this._jLabelColorTopPlate    );
        _jPanelColorDrawingObject.add(this._jLabelColorMagnetTop   );
        _jPanelColorDrawingObject.add(this._jLabelColorMagnetOuter );
        _jPanelColorDrawingObject.add(this._jLabelColorYokeBase    );
        _jPanelColorDrawingObject.add(this._jLabelColorStage1      );
        _jPanelColorDrawingObject.add(this._jLabelColorStage2      );


        _jPanelColorDrawingObject.add(this._jButtonColorCap         );
        _jPanelColorDrawingObject.add(this._jButtonColorTransition  );
        _jPanelColorDrawingObject.add(this._jButtonColorDiaphragm   );
        _jPanelColorDrawingObject.add(this._jButtonColorSurround    );
        _jPanelColorDrawingObject.add(this._jButtonColorCoil        );
        _jPanelColorDrawingObject.add(this._jButtonColorFormer      );
        _jPanelColorDrawingObject.add(this._jButtonColorMagnet      );
        _jPanelColorDrawingObject.add(this._jButtonColorTopPlate    );
        _jPanelColorDrawingObject.add(this._jButtonColorMagnetTop   );
        _jPanelColorDrawingObject.add(this._jButtonColorMagnetOuter );
        _jPanelColorDrawingObject.add(this._jButtonColorYokeBase    );
        _jPanelColorDrawingObject.add(this._jButtonColorStage1      );
        _jPanelColorDrawingObject.add(this._jButtonColorStage2      );

        _jPanelColorDrawingObject.add(this._jButtonResetFactorySettingDrawingObject      );

        this._jLabelColorCap                  .setBounds(20,  20, 130, 25);
        this._jLabelColorTransition           .setBounds(20,  50, 130, 25);
        this._jLabelColorDiaphragm            .setBounds(20,  80, 130, 25);
        this._jLabelColorSurround             .setBounds(20, 110, 130, 25);
        this._jLabelColorCoil                 .setBounds(20, 140, 130, 25);
        this._jLabelColorFormer               .setBounds(20, 170, 130, 25);
        this._jLabelColorMagnet               .setBounds(190,  20, 130, 25);
        this._jLabelColorTopPlate             .setBounds(190,  50, 130, 25);
        this._jLabelColorMagnetTop            .setBounds(190,  80, 130, 25);
        this._jLabelColorMagnetOuter          .setBounds(190, 110, 130, 25);
        this._jLabelColorYokeBase             .setBounds(190, 140, 130, 25);
        this._jLabelColorStage1               .setBounds(190, 170, 130, 25);
        this._jLabelColorStage2               .setBounds(190, 200, 130, 25);

        this._jButtonColorCap                 .setBounds(120,  20, 50, 25);
        this._jButtonColorTransition          .setBounds(120,  50, 50, 25);
        this._jButtonColorDiaphragm           .setBounds(120,  80, 50, 25);
        this._jButtonColorSurround            .setBounds(120, 110, 50, 25);
        this._jButtonColorCoil                .setBounds(120, 140, 50, 25);
        this._jButtonColorFormer              .setBounds(120, 170, 50, 25);
        this._jButtonColorMagnet              .setBounds(290,  20, 50, 25);
        this._jButtonColorTopPlate            .setBounds(290,  50, 50, 25);
        this._jButtonColorMagnetTop           .setBounds(290,  80, 50, 25);
        this._jButtonColorMagnetOuter         .setBounds(290, 110, 50, 25);
        this._jButtonColorYokeBase            .setBounds(290, 140, 50, 25);
        this._jButtonColorStage1              .setBounds(290, 170, 50, 25);
        this._jButtonColorStage2              .setBounds(290, 200, 50, 25);

        this._jButtonResetFactorySettingDrawingObject .setBounds(250,360, 120, 25);
    }

    private void addComponentAbaqusSetting()
    {
        this._jPanelAbaqusSetting.setLayout(null);
        this._jPanelAbaqusSetting.setBounds(10, 10, 370, 435);

        this._jLabelAbaqusExecute       .setBounds(30,20,200,25);
        this._jTextAbaqusPath           .setBounds(30,45,300,25);
        this._jButtonAbaqusPathBrowse   .setBounds(340,45,35,25);

        this._jLabelANSYSExecute        .setBounds(30,75,200,25);
        this._jTextANSYSPath            .setBounds(30,100,300,25);
        this._jButtonANSYSPathBrowse    .setBounds(340,100,35,25);

        this._jLabelWorkingDirName       .setBounds(30,130,200,25);
        this._jTextWorkingExecute        .setBounds(30,155,300,25);
        this._jButtonWorkingExecute      .setBounds(340,155,35,25);

        this._jLabelAbaqusWorkFolderName.setBounds(30,185,200,25);
        this._jTextAbaqusWorkFolderName.setBounds(30,210,100,25);

        this._jLabelAnsysWorkFolderName.setBounds(30,240,200,25);
        this._jTextAnsysWorkFolderName.setBounds(30,265,100,25);

        this._jLabelWorkTime.setBounds(30,295,200,25);
        this._jSpinnerWorkTime.setBounds(30,320,50,25);
        this._jLabelWorkTimeUnit.setBounds(85,320,50,25);

        this._jPanelAbaqusSetting.add(this._jLabelAbaqusExecute);
        this._jPanelAbaqusSetting.add(this._jTextAbaqusPath);
        this._jPanelAbaqusSetting.add(this._jButtonAbaqusPathBrowse);

        this._jPanelAbaqusSetting.add(this._jLabelANSYSExecute);
        this._jPanelAbaqusSetting.add(this._jTextANSYSPath);
        this._jPanelAbaqusSetting.add(this._jButtonANSYSPathBrowse);

        this._jPanelAbaqusSetting.add(this._jLabelWorkingDirName);
        this._jPanelAbaqusSetting.add(this._jTextWorkingExecute);
        this._jPanelAbaqusSetting.add(this._jButtonWorkingExecute);

        this._jPanelAbaqusSetting.add(this._jLabelAbaqusWorkFolderName);
        this._jPanelAbaqusSetting.add(this._jTextAbaqusWorkFolderName);

        this._jPanelAbaqusSetting.add(this._jLabelAnsysWorkFolderName);
        this._jPanelAbaqusSetting.add(this._jTextAnsysWorkFolderName);

        this._jPanelAbaqusSetting.add(this._jLabelWorkTime);
        this._jPanelAbaqusSetting.add(this._jSpinnerWorkTime);
        this._jPanelAbaqusSetting.add(this._jLabelWorkTimeUnit);
    }

    public void stateChanged(ChangeEvent e)
    {
       if(e.getSource().equals(this._tabbedColor))
       {
           this._splited.removeAll();
           this._splited.add(this._tree, JSplitPane.LEFT);
           this._splited.add(this._tabbedColor, JSplitPane.RIGHT);
           this._splited.setDividerLocation(150);
       }
    }


    protected void addListener()
    {
        this._jButtonColorCap                   .addActionListener(this);
        this._jButtonColorTransition            .addActionListener(this);
        this._jButtonColorDiaphragm             .addActionListener(this);
        this._jButtonColorSurround              .addActionListener(this);
        this._jButtonColorCoil                  .addActionListener(this);
        this._jButtonColorFormer                .addActionListener(this);
        this._jButtonColorMagnet                .addActionListener(this);
        this._jButtonColorTopPlate              .addActionListener(this);
        this._jButtonColorMagnetTop             .addActionListener(this);
        this._jButtonColorMagnetOuter           .addActionListener(this);
        this._jButtonColorYokeBase              .addActionListener(this);
        this._jButtonColorStage1                .addActionListener(this);
        this._jButtonColorStage2                .addActionListener(this);


        this._jComboBoxSizePoint2D              .addItemListener(this);
        this._jComboBoxSizeLine2D               .addItemListener(this);
        this._jButtonColorPoint2D               .addActionListener(this);
        this._jButtonColorLine2D                .addActionListener(this);
        this._jButtonColorSolid2D               .addActionListener(this);
        this._jButtonColorBackgroundDown        .addActionListener(this);
        this._jButtonColorBackgroundUp          .addActionListener(this);

        this._jButtonResetFactorySettingBasic           .addActionListener(this);
        this._jButtonResetFactorySettingDrawingObject   .addActionListener(this);

        this._jButtonAbaqusPathBrowse           .addActionListener(this);
        this._jButtonANSYSPathBrowse           .addActionListener(this);
        this._jButtonWorkingExecute           .addActionListener(this);

        this._jButtonApply .addActionListener(this);
        this._tree.addMouseListener(this);
        this._tabbedColor.addChangeListener(this);
    }



    protected boolean checkTextfield()
    {
        boolean bool = true;

        try
        {

        }
        catch (Exception e)
        {
            String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
            DiaMessage diaWarning = new DiaMessage(_system, diaStr);
            bool = false;
        }
       return bool;
   }


   protected void readDiaData()
   {
       this._jButtonColorCap                .setBackground(this._system.getDataManager().getColorCap());
       this._jButtonColorTransition         .setBackground(this._system.getDataManager().getColorTransition());
       this._jButtonColorDiaphragm          .setBackground(this._system.getDataManager().getColorDiaphragm());
       this._jButtonColorSurround           .setBackground(this._system.getDataManager().getColorSurround());
       this._jButtonColorCoil               .setBackground(this._system.getDataManager().getColorCoil());
       this._jButtonColorFormer             .setBackground(this._system.getDataManager().getColorFormer());
       this._jButtonColorMagnet             .setBackground(this._system.getDataManager().getColorMagnet());
       this._jButtonColorTopPlate           .setBackground(this._system.getDataManager().getColorTopPlate());
       this._jButtonColorMagnetTop          .setBackground(this._system.getDataManager().getColorMagnetTop());
       this._jButtonColorMagnetOuter        .setBackground(this._system.getDataManager().getColorMagnetOuter());
       this._jButtonColorYokeBase           .setBackground(this._system.getDataManager().getColorYokeBase());
       this._jButtonColorStage1             .setBackground(this._system.getDataManager().getColorYokeStage1());
       this._jButtonColorStage2             .setBackground(this._system.getDataManager().getColorYokeStage2());


       this._jButtonColorPoint2D            .setBackground(this._system.getDataManager().getColorPoint());
       this._jButtonColorLine2D             .setBackground(this._system.getDataManager().getColorLine());
       this._jButtonColorSolid2D            .setBackground(this._system.getDataManager().getColorSolid());
       this._jButtonColorBackgroundUp        .setBackground(this._system.getDataManager().getColorBackgroundUp());
       this._jButtonColorBackgroundDown      .setBackground(this._system.getDataManager().getColorBackgroundDown());


       float lineWidth = this._system.getDataManager().getStrokeLine().getLineWidth();
       int  ptSize = this._system.getDataManager().getStrokePt();
       int indexLine = -1;
       int indexPt = -1;

       if (lineWidth == 1f)
           indexLine = 0;
       else if (lineWidth == 1.5f)
           indexLine = 1;
       else if (lineWidth == 2f)
           indexLine = 2;
       else if (lineWidth == 3f)
           indexLine = 3;
       else if (lineWidth == 5f)
           indexLine = 4;

       if (ptSize == 2)
           indexPt = 0;
       else if (ptSize == 4)
           indexPt = 1;
       else if (ptSize == 6)
           indexPt = 2;
       else if (ptSize == 8)
           indexPt = 3;

       this._jComboBoxSizeLine2D.setSelectedIndex(indexLine);
       this._jComboBoxSizePoint2D.setSelectedIndex(indexPt);

       String strAbaqus = this._system.getConfig().getAbaqusPath();
       this._jTextAbaqusPath.setText(strAbaqus) ;

       String strAnsys = this._system.getConfig().getAnsysPath();
       this._jTextANSYSPath.setText(strAnsys) ;

       String strWorkDir = this._system.getConfig().getWorkingDirPath();
       this._jTextWorkingExecute.setText(strWorkDir) ;

       String strAbaqusWorkFileName = this._system.getConfig().getAbaqusWorkingFileName();
       if(strAbaqusWorkFileName == "" || strAbaqusWorkFileName == null)
           strAbaqusWorkFileName = "AbaqusVault";
       this._jTextAbaqusWorkFolderName.setText(strAbaqusWorkFileName);

       String strAnsysWorkFileName = this._system.getConfig().getAnsysWorkingFileName();
       if(strAnsysWorkFileName == "" || strAnsysWorkFileName == null)
           strAnsysWorkFileName = "AnsysVault";
       this._jTextAnsysWorkFolderName.setText(strAnsysWorkFileName);



       int workTime = this._system.getConfig().getReRunTime();
       if(workTime == 0)
           this._jSpinnerWorkTime.setValue(10);
       else
           this._jSpinnerWorkTime.setValue(workTime);

    }




    public void  actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this._jButtonCancel)
        {
            this.dispose();
            System.out.println("button Cancel");
        }

        else if (e.getSource() == this._jButtonOk)
        {
            this.dispose();
            this.apply();
            System.out.println("button Ok");
        }
        else if(e.getSource()== this._jButtonClose)
        {
           this._jButtonCancel.doClick();
           this.dispose();
        }
        else if (e.getSource() == this._jButtonApply)
        {
            System.out.println("button Apply");
            this.apply();
        }
        if(
            (e.getSource() == this._jButtonColorCap)  ||
            (e.getSource() == this._jButtonColorTransition)  ||
            (e.getSource() == this._jButtonColorDiaphragm)  ||
            (e.getSource() == this._jButtonColorSurround)  ||

            (e.getSource() == this._jButtonColorCoil)  ||
            (e.getSource() == this._jButtonColorCoil)  ||
            (e.getSource() == this._jButtonColorFormer)  ||

            (e.getSource() == this._jButtonColorMagnet)  ||
            (e.getSource() == this._jButtonColorTopPlate)  ||
            (e.getSource() == this._jButtonColorMagnetTop)  ||
            (e.getSource() == this._jButtonColorMagnetOuter)  ||
            (e.getSource() == this._jButtonColorYokeBase)  ||
            (e.getSource() == this._jButtonColorStage1)  ||
            (e.getSource() == this._jButtonColorStage2)  ||

            (e.getSource() == this._jButtonColorLine2D    )  ||
            (e.getSource() == this._jButtonColorPoint2D   )  ||
            (e.getSource() == this._jButtonColorSolid2D   )  ||
            (e.getSource() == this._jButtonColorBackgroundUp)  ||
            (e.getSource() == this._jButtonColorBackgroundDown)
         )
        {
           Color color = JColorChooser.showDialog(this, "Change Color", Color.white);
           if (color != null)
           {
               ((JButton)e.getSource()).setOpaque(true);
               ((JButton)e.getSource()).setBackground(color);
           }
           if (e.getSource() == this._jButtonColorCap)
               this._isChangedColorCap = true;
           else if (e.getSource() == this._jButtonColorTransition)
               this._isChangedColorTransition = true;
           else if (e.getSource() == this._jButtonColorDiaphragm)
               this._isChangedColorDiaphragm = true;
           else if (e.getSource() == this._jButtonColorSurround)
               this._isChangedColorSurround = true;
           else if (e.getSource() == this._jButtonColorCoil)
               this._isChangedColorCoil = true;
           else if (e.getSource() == this._jButtonColorFormer)
               this._isChangedColorFormer = true;
           else if (e.getSource() == this._jButtonColorMagnet)
               this._isChangedColorMagnet = true;
           else if (e.getSource() == this._jButtonColorTopPlate)
               this._isChangedColorTopPlate = true;
           else if (e.getSource() == this._jButtonColorMagnetTop)
               this._isChangedColorMagnetTop = true;
           else if (e.getSource() == this._jButtonColorMagnetOuter)
               this._isChangedColorMagnetOuter = true;
           else if (e.getSource() == this._jButtonColorYokeBase)
               this._isChangedColorYokeBase = true;
           else if (e.getSource() == this._jButtonColorStage1)
               this._isChangedColorStage1 = true;
           else if (e.getSource() == this._jButtonColorStage2)
               this._isChangedColorStage2 = true;
           else if (e.getSource() == this._jButtonColorSolid2D)
               this._isChangedColorSolid2D = true;
           else if (e.getSource() == this._jButtonColorLine2D)
               this._isChangedColorLine2D = true;
           else if (e.getSource() == this._jButtonColorPoint2D)
               this._isChangedColorPoint2D = true;
           else if (e.getSource() == this._jButtonColorBackgroundDown)
               this._isChangedColorBackgroudDown = true;
           else if (e.getSource() == this._jButtonColorBackgroundUp)
               this._isChangedColorBackgroudUp = true;
        }
        if(e.getSource() == this._jButtonResetFactorySettingBasic)
        {
            String str = this._system.GetInterfaceString("dia_MessageStrIfResetFactorySetting");
            DiaAsk dia = new DiaAsk(_system, str);
            if (dia.GetResult() == false)
                return;
            this._system.getDataManager().setColorDefaultBasic();
            this.readDiaData();
            this._isChangedColorSolid2D = true;
            this._isChangedColorLine2D = true;
            this._isChangedColorPoint2D = true;
            this._isChangedColorBackgroudDown = true;
            this._isChangedColorBackgroudUp = true;
        }
        if (e.getSource() == this._jButtonResetFactorySettingDrawingObject)
        {
            String str = this._system.GetInterfaceString("dia_MessageStrIfResetFactorySetting");
            DiaAsk dia = new DiaAsk(_system, str);
            if (dia.GetResult() == false)
                return;
            this._system.getDataManager().setColorDefaultDrawingObject();
            this.readDiaData();
            this._isChangedColorCap = true;
            this._isChangedColorTransition = true;
            this._isChangedColorDiaphragm = true;
            this._isChangedColorSurround = true;
            this._isChangedColorCoil = true;
            this._isChangedColorFormer = true;
            this._isChangedColorMagnet = true;
            this._isChangedColorTopPlate = true;
            this._isChangedColorMagnetOuter = true;
            this._isChangedColorYokeBase = true;
            this._isChangedColorStage1 = true;
        }
        else if (e.getSource() == this._jButtonAbaqusPathBrowse)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(this._filterEXE);
            int result = fileChooser.showOpenDialog(this);
            if (result != -1)
            {
                File newfile = fileChooser.getSelectedFile();
                if (newfile == null)
                    return;
                try
                {
                    this._jTextAbaqusPath.setText(newfile.getPath());
                    this._isChangedAbaqusPath = true;
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        }
        else if (e.getSource() == this._jButtonANSYSPathBrowse)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(this._filterEXE);
            int result = fileChooser.showOpenDialog(this);
            if (result != -1)
            {
                File newfile = fileChooser.getSelectedFile();
                if (newfile == null)
                    return;
                try
                {
                    this._jTextANSYSPath.setText(newfile.getPath());
                    this._isChangedAnsysPath = true;
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        }
        else if (e.getSource() == this._jButtonWorkingExecute)
        {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(this);

            //if (result != 0)
            if (result == JFileChooser.APPROVE_OPTION)
            {

                File newDir = fileChooser.getSelectedFile();

                try
                {
                    this._jTextWorkingExecute.setText(newDir.getPath());
                    this._isChangedWorkingPath = true;
                    //this._system.getConfig().setWorkingDirPath(newDir.getPath());
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        }


    }
    public void apply()
    {
        SDT_DataManager manager = null;
        manager =  this._system.getDataManager();
        if (_isChangedColorCap)
            manager.setColorCap(this._jButtonColorCap.getBackground());

        if (_isChangedColorTransition)
            manager.setColorTransition(this._jButtonColorTransition.getBackground());

        if (_isChangedColorDiaphragm)
            manager.setColorDiaphragm(this._jButtonColorDiaphragm.getBackground());

        if (_isChangedColorSurround)
            manager.setColorSurround(this._jButtonColorSurround.getBackground());

        if (this._isChangedColorCoil)
            manager.setColorCoil(this._jButtonColorCoil.getBackground());

        if (this._isChangedColorFormer)
            manager.setColorFormer(this._jButtonColorFormer.getBackground());

        if (_isChangedColorMagnet)
            manager.setColorMagnet(this._jButtonColorMagnet.getBackground());

        if (_isChangedColorTopPlate)
            manager.setColorTopPlate(this._jButtonColorTopPlate.getBackground());

        if (_isChangedColorMagnetTop)
            manager.setColorMagnetTop(this._jButtonColorMagnetTop.getBackground());

        if (_isChangedColorMagnetOuter)
            manager.setColorMagnetOuter(this._jButtonColorMagnetOuter.getBackground());

        if (_isChangedColorYokeBase)
            manager.setColorYokeBase(this._jButtonColorYokeBase.getBackground());

        if (_isChangedColorStage1)
            manager.setColorStage1(this._jButtonColorStage1.getBackground());

        if (_isChangedColorStage2)
            manager.setColorStage2(this._jButtonColorStage2.getBackground());

        if (_isChangedColorLine2D)
            manager.setColorLine(this._jButtonColorLine2D.getBackground());
        if (_isChangedColorSolid2D)
            manager.setColorSolid(this._jButtonColorSolid2D.getBackground());
        if (_isChangedColorPoint2D)
            manager.setColorPt(this._jButtonColorPoint2D.getBackground());

        if (_isChangedColorBackgroudDown || _isChangedColorBackgroudUp)
        {
            manager.setColorBackgroundDown(this._jButtonColorBackgroundDown.getBackground());
            manager.setColorBackgroundUp(this._jButtonColorBackgroundUp.getBackground());
            this._system.getModeler().getPanel2D().GetDrawPanel().setBackgroundPaint();
             this._system.getModeler().getPanel2D().setBackgroundPaint();
             this._system.getModeler().getPanel2D().GetDrawPanel().updateUI();
             this._system.getModeler().getPanel2D().updateUI();

             if(this._system.getModeler().getPanel3D() != null)
             this._system.getModeler().getPanel3D().resetBackground();
        }
        if(_isChangedSizeLine2D)
        {
            String lineSizeStr = (String)this._jComboBoxSizeLine2D.getSelectedItem();
            int lineSize = Integer.parseInt(lineSizeStr);
            manager.setStrokeLine(new BasicStroke(lineSize));
        }
        if(_isChangedSizePoint2D)
        {
            String ptSizeStr = (String)this._jComboBoxSizePoint2D.getSelectedItem();
            int ptSize = Integer.parseInt(ptSizeStr);
            manager.setStrokePt(ptSize);
        }
        this._system.getModeler().getPanel2D().GetDrawPanel().showElement();

        if(_isChangedAbaqusPath)
        {
            this._system.getConfig().setAbaqusPath(this._jTextAbaqusPath.getText().trim());
            this._system.getConfig().save();
        }
        if(_isChangedAnsysPath)
       {
           this._system.getConfig().setAnsysPath(this._jTextANSYSPath.getText().trim());
           this._system.getConfig().save();
       }
       if(_isChangedWorkingPath)
       {
           this._system.getConfig().setWorkingDirPath(this._jTextWorkingExecute.getText().trim());
           this._system.getConfig().save();
       }


       this._system.getConfig().setAbaqusWorkingFileName(this._jTextAbaqusWorkFolderName.getText());
       this._system.getConfig().save();

       this._system.getConfig().setAnsysWorkingFileName(this._jTextAnsysWorkFolderName.getText());
       this._system.getConfig().save();

       this._system.getConfig().setReRunTime((Integer)this._jSpinnerWorkTime.getValue());
       this._system.getConfig().save();
    }
    public void itemStateChanged(ItemEvent e)
    {
        if(e.getSource().equals(this._jComboBoxSizeLine2D) )
        {
            _isChangedSizeLine2D  = true;
        }
        if(e.getSource().equals(this._jComboBoxSizePoint2D) )
        {
            _isChangedSizePoint2D = true;
        }


    }


    public void mousePressed(MouseEvent e)
    {
        if(e.getSource() == this._tree.theTree)
        {
            OptionTreeNode  node = this._tree.getNode(e);
            if(node  != null)
            {
                int nodeID = node.getNodeID();

                switch (nodeID)
                {
                    case 1:

                        this._splited.removeAll();
                        this._splited.add(this._tree,JSplitPane.LEFT);
                        this._splited.add(this._tabbedColor,JSplitPane.RIGHT);
                        this._splited.setDividerLocation(150);

                        break;
                    case 2:
                        this._splited.removeAll();
                        this._splited.add(_tree,JSplitPane.LEFT);
                        this._splited.add(this._jPanelAbaqusSetting,JSplitPane.RIGHT);
                        this._splited.setDividerLocation(150);

                        break;
                }
            }
        }
        super.mousePressed(e);
    }




    private class OptionTree extends JScrollPane implements MouseListener
    {
        private SDT_System theSystem ;
        JTree theTree ;
        private TreePath  theTreePath;
        private DefaultTreeModel defaultTreeModel;
        private TreeSelectionModel treeSelectionModel;
        OptionTreeNode theNodeRoot;
        OptionTreeNode theNodeColor;
        OptionTreeNode theNodeAbaqusSetting;
        OptionTreeNode theNodeSelected;

        public OptionTree(SDT_System system)
        {
            theSystem = system;
            String strOption = this.theSystem.GetInterfaceString("TitleOption");
            String strOptionColor = this.theSystem.GetInterfaceString("OptionNodeColor");
            String strOptionAbaqusSetting = this.theSystem.GetInterfaceString("OptionNodeFEMSettinig");
            theNodeRoot = new OptionTreeNode(strOption,0);
            theNodeColor = new OptionTreeNode(strOptionColor,1);
            theNodeAbaqusSetting  = new OptionTreeNode(strOptionAbaqusSetting,2);

            theNodeRoot.add(theNodeColor);
            theNodeRoot.add(theNodeAbaqusSetting);

            this.theTree = new JTree(this.theNodeRoot);
            this.theTree.setRowHeight(28);

            defaultTreeModel = (DefaultTreeModel) theTree.getModel();
            treeSelectionModel = theTree.getSelectionModel();
            treeSelectionModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

            this.theTreePath = theTree.getPathForRow(0);
            treeSelectionModel.setSelectionPath(this.theTreePath);
            try
            {
                this.getViewport().add(theTree, null);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }


        }

        public void addMouseListener(MouseListener listener)
        {
            theTree.addMouseListener(listener);
            super.addMouseListener(listener);
        }

        public void mouseClicked(MouseEvent e)
        {
        }

        public void mousePressed(MouseEvent e)
        {

            //(theNodeSelected.getUserObject().equals(""))




        }

        public OptionTreeNode getNode(MouseEvent e)
        {
            int pressNodeRow = -1;
            pressNodeRow = this.theTree.getRowForLocation(e.getX(), e.getY());
            if (pressNodeRow == -1)
            {
                this.treeSelectionModel.clearSelection();
                return  null;
            }

            TreePath path = this.theTree.getPathForRow(pressNodeRow);


            if (path == null)
            {
               // this.treeSelectionModel.setSelectionPath(_selectedPath);
                return null;
            }
            this.treeSelectionModel.setSelectionPath(path);
            OptionTreeNode node = (OptionTreeNode) path.getLastPathComponent();
            return node;
        }

        public void mouseReleased(MouseEvent e)
        {
        }

        public void mouseEntered(MouseEvent e)
        {
        }

        public void mouseExited(MouseEvent e)
        {
        }


    }


    private class OptionTreeNode extends DefaultMutableTreeNode
    {
        protected int theNodeID;

        public int getNodeID()
        {
            return this.theNodeID;
        }


        public OptionTreeNode(Object userObject, int nodeID)
        {
            super(userObject);
            this.theNodeID  = nodeID;
        }

        public void setNodeID(int nodeID)
        {
            this.theNodeID = nodeID;
        }


        public String getIconName() // for icon_node_renderer
        {
            String str = userObject.toString();
            int index = str.lastIndexOf(".");
            if (index != -1)
            {
                return str.substring(++index);
            }
            else
            {
                return null;
            }

        }
    }


}
