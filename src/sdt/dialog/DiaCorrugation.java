package sdt.dialog;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import sdt.data.*;
import sdt.framework.*;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;

public class DiaCorrugation extends DiaMain
{
    JTabbedPane                 _tabbedJAssign ;
    private Box                 _boxTabPeriodical;
    private Box                 _boxTabCircumferential;
    private Box                 _boxJCritical;
    private Box                 _boxJCriticalList;
    private Box                 _boxJCriticalLabelList;
    private Box                 _boxJCriticalButtons;

    private JLabel              _jLabelJCritical;
    private JLabel              _jLabelJCriticalList;
    private JLabel              _jLabelJTotalList;
    private JList               _jListJCriticalList;
    private JList               _jListJTotalList;
    private JButton             _jButtonToTotal;
    private JButton             _jButtonToCritical;
    private JScrollPane         _jScrollCriticalList;
    private JScrollPane         _jScrollTotalList;
    private JCheckBox           _jCheckBoxCorrugationOn;


    private DefaultListModel    _modelTotal;
    private DefaultListModel    _modelCritical;

    private JSpinner            _jSpinBoxJCritical;
    private SpinnerNumberModel  _spinnerModelJCritical;

    private JTabbedPane         _tabbedIAssign ;
    private Box                 _boxTabJStart;
    private Box                 _boxTabJEnd;
    private Box                 _boxColumnTitle;
    private Box                 _boxColumnTitle2;
    private Box                 _boxJStartIStart;
    private Box                 _boxJStartICritical;
    private Box                 _boxJStartIEnd;
    private Box                 _boxJEndIStart;
    private Box                 _boxJEndICritical;
    private Box                 _boxJEndIEnd;
    private Box                 _boxCenterFormingAngle;

    private JLabel              _jLabelJStartIStart;
    private JLabel              _jLabelJStartICritical;
    private JLabel              _jLabelJStartIEnd;
    private JLabel              _jLabelJEndIStart;
    private JLabel              _jLabelJEndICritical;
    private JLabel              _jLabelJEndIEnd;


    private JLabel              _jLabelItem;
    private JLabel              _jLabelPosition;
    private JLabel              _jLabelRatio;
    private JLabel              _jLabelItem2;
    private JLabel              _jLabelPosition2;
    private JLabel              _jLabelRatio2;
    private JCheckBox           _jCheckBoxBias;
    private JCheckBox           _jCheckBoxBias2;
    private JLabel              _jLabelCenterFormingAngle;
    private JTextField          _jTextFieldCenterFormingAngle;


    private JSpinner             _jSpinBoxJStartIStart;
    private JSpinner             _jSpinBoxJStartICritical;
    private JSpinner             _jSpinBoxJStartIEnd;
    private SpinnerNumberModel   _spinnerModelJStartIStart;
    private SpinnerNumberModel   _spinnerModelJStartICritical;
    private SpinnerNumberModel   _spinnerModelJStartIEnd;
    private JSpinner             _jSpinBoxJEndIStart;
    private JSpinner             _jSpinBoxJEndICritical;
    private JSpinner             _jSpinBoxJEndIEnd;
    private SpinnerNumberModel   _spinnerModelJEndIStart;
    private SpinnerNumberModel   _spinnerModelJEndICritical;
    private SpinnerNumberModel   _spinnerModelJEndIEnd;
    private JSlider              _jSliderJStartIStartRatio;
    private JSlider              _jSliderJStartICriticalRatio;
    private JSlider              _jSliderJStartIEndRatio;
    private JSlider              _jSliderJEndIStartRatio;
    private JSlider              _jSliderJEndICriticalRatio;
    private JSlider              _jSliderJEndIEndRatio;

   // private JList

    private int                 _countPerodical;
    private int                 _countCircumferential;
    private int                 _countRadial;

    private int[]                 _JStart;
    private int[]                 _JCritical;
    private int[]                 _JEnd;
    private double              _corrugationAngle;

    private boolean             _isBiasOn;
    private boolean             _isCorrugationOn;
    private boolean             _isPerodical;
    private JButton             _jButtonApply;

    private double              _corrugationJStartIStartRatio;
    private double              _corrugationJStartIEndRatio;
    private double              _corrugationJStartICriticalRatio  ;

    private double              _corrugationJEndIStartRatio  ;
    private double              _corrugationJEndIEndRatio ;
    private double              _corrugationJEndICriticalRatio  ;

    private int                 _corrugationJStartIStart;
    private int                 _corrugationJStartIEnd;
    private int                 _corrugationJStartICritical  ;

    private int                 _corrugationJEndIStart  ;
    private int                 _corrugationJEndIEnd ;
    private int                 _corrugationJEndICritical  ;

    private int[]               _oldIntValue;
    private double[]            _oldDoubleValue;
    private boolean[]           _oldBooleanValue;

    private int _countTemp = 0;

    public DiaCorrugation(SDT_System system)
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

    void jbInit() throws Exception
    {
        String str = this._system.GetInterfaceString("TitleCorrugation");
        this.setTitle(str);
        this.setSize(300, 485);
        this.setLocation();
        this.readDiaData();
        this.addItemListener2();
        this._system.getModeler().getPanel2D().GetDrawPanel().showCurrentDataXY();
        this.setVisible(true);
    }


    protected void readDiaData()
    {
        DataShell data = this._system.getDataManager().getCurrentDataShell();

        boolean isPeroidical = data.getPeriodical();
        this._countPerodical = data.getCountPeriodical();
        this._countCircumferential = data.getCountCircumferential();
        this._countRadial = data.getCountRadial();
        data.setIsXYUpdateNeed(true);
        data.selectCorrugation();


        this._isBiasOn                          = data.getIsBiasOn();
        this._isCorrugationOn                   = data.getIsCorrugationOn();
        this._JStart                            = data.getCorrugationJStart();
        this._JEnd                              = data.getCorrugationJEnd();
        this._corrugationAngle                  = data.getCorrugationAngle();

        if(this._JEnd.length == 0)
        {
            this._JCritical = new int[1];
        }
        else
        {
            this._JCritical = new int[this._JEnd.length];
            for (int i = 0; i < this._JCritical.length; i++)
            {
                _JCritical[i] = this._JEnd[i] - 1;
            }
        }


        this._corrugationJStartIStart           = data.getCorrugationJStartIStart() ;
        this._corrugationJStartIEnd             = data.getCorrugationJStartIEnd() ;
        this._corrugationJStartICritical        = data.getCorrugationJStartICritical()   ;
        this._corrugationJEndIStart             = data.getCorrugationJEndIStart();
        this._corrugationJEndIEnd               = data.getCorrugationJEndIEnd() ;
        this._corrugationJEndICritical          = data.getCorrugationJEndICritical();

        this._corrugationJStartIStartRatio      = data.getCorrugationJStartIStartRatio() ;
        this._corrugationJStartIEndRatio        = data.getCorrugationJStartIEndRatio() ;
        this._corrugationJStartICriticalRatio   = data.getCorrugationJStartICriticalRatio()   ;
        this._corrugationJEndIStartRatio        = data.getCorrugationJEndIStartRatio();
        this._corrugationJEndIEndRatio          = data.getCorrugationJEndIEndRatio() ;
        this._corrugationJEndICriticalRatio     = data.getCorrugationJEndICriticalRatio()   ;

        this.setPerodical(isPeroidical);

        if (isPeroidical)
        {
            //this._spinnerModelJCritical.setMaximum(this._countCircumferential - 2 - 2);
            //this._spinnerModelJCritical.setMinimum(2);
            this._spinnerModelJCritical.setMaximum(this._countCircumferential- 3);
            this._spinnerModelJCritical.setMinimum(0);
            this._spinnerModelJCritical.setValue(_JCritical[0]);
        }
        else
        {
            for (int i = 0; i < this._countCircumferential; i++)
            {
                if(!data.isJCritical(i))
                    _modelTotal.addElement(Integer.toString(i));
                else
                    _modelCritical.addElement(Integer.toString(i));
            }
        }
        this._jCheckBoxBias                 .setSelected( this._isBiasOn) ;
        this._jCheckBoxCorrugationOn        .setSelected( this._isCorrugationOn) ;

        this._jSliderJStartIStartRatio      .setValue(     (int) (_corrugationJStartIStartRatio * 100   ));
        this._jSliderJStartICriticalRatio   .setValue(     (int) (_corrugationJStartICriticalRatio * 100));
        this._jSliderJStartIEndRatio        .setValue(     (int) (_corrugationJStartIEndRatio * 100     ));
        this._jSliderJEndIStartRatio        .setValue(     (int) (_corrugationJEndIStartRatio * 100     ));
        this._jSliderJEndICriticalRatio     .setValue(     (int) (_corrugationJEndICriticalRatio * 100  ));
        this._jSliderJEndIEndRatio          .setValue(     (int) (_corrugationJEndIEndRatio * 100       ));

        this._jSpinBoxJStartIStart          .setValue(     this._corrugationJStartIStart    );
        this._jSpinBoxJStartICritical       .setValue(     this._corrugationJStartICritical );
        this._jSpinBoxJStartIEnd            .setValue(     this._corrugationJStartIEnd      );
        this._jSpinBoxJEndIStart            .setValue(     this._corrugationJEndIStart      );
        this._jSpinBoxJEndICritical         .setValue(     this._corrugationJEndICritical   );
        this._jSpinBoxJEndIEnd              .setValue(     this._corrugationJEndIEnd        );

        ((SpinnerNumberModel)this._jSpinBoxJStartIStart.getModel()).setMaximum(this._countRadial - 1 - 1);
        ((SpinnerNumberModel)this._jSpinBoxJStartIStart.getModel()).setMinimum(0 + 1 );
        ((SpinnerNumberModel)this._jSpinBoxJStartICritical.getModel()).setMaximum(this._countRadial - 1- 1);
        ((SpinnerNumberModel)this._jSpinBoxJStartICritical.getModel()).setMinimum(0 + 1 );
        ((SpinnerNumberModel)this._jSpinBoxJStartIEnd.getModel()).setMaximum(this._countRadial - 1 - 1);
        ((SpinnerNumberModel)this._jSpinBoxJStartIEnd.getModel()).setMinimum(0 + 1);
        ((SpinnerNumberModel)this._jSpinBoxJEndIStart.getModel()).setMaximum(this._countRadial - 1 - 1);
        ((SpinnerNumberModel)this._jSpinBoxJEndIStart.getModel()).setMinimum(0 + 1 );
        ((SpinnerNumberModel)this._jSpinBoxJEndICritical.getModel()).setMaximum(this._countRadial - 1- 1);
        ((SpinnerNumberModel)this._jSpinBoxJEndICritical.getModel()).setMinimum(0 + 1 );
        ((SpinnerNumberModel)this._jSpinBoxJEndIEnd.getModel()).setMaximum(this._countRadial - 1 - 1);
        ((SpinnerNumberModel)this._jSpinBoxJEndIEnd.getModel()).setMinimum(0 + 1);


        this._oldIntValue = new int[9];
        this._oldIntValue[0] = this._corrugationJStartIStart;
        this._oldIntValue[1] = this._corrugationJStartIEnd;
        this._oldIntValue[2] = this._corrugationJStartICritical  ;
        this._oldIntValue[3] = this._corrugationJEndIStart  ;
        this._oldIntValue[4] = this._corrugationJEndIEnd ;
        this._oldIntValue[5] = this._corrugationJEndICritical  ;


        this._oldDoubleValue = new double[6];
        this._oldDoubleValue[0] = this._corrugationJStartIStartRatio;
        this._oldDoubleValue[1] = this._corrugationJStartICriticalRatio;
        this._oldDoubleValue[2] = this._corrugationJStartIEndRatio;
        this._oldDoubleValue[3] = this._corrugationJEndIStartRatio;
        this._oldDoubleValue[4] = this._corrugationJEndICriticalRatio;
        this._oldDoubleValue[5] = this._corrugationJEndIEndRatio;

        this._oldBooleanValue = new boolean[1];
        this._oldBooleanValue[0] = this._isBiasOn;

        this._jTextFieldCenterFormingAngle.setText(""+_corrugationAngle);

    }
    protected void createComponent()
    {
        _tabbedJAssign           = new JTabbedPane();
        _boxTabPeriodical        = Box.createVerticalBox();
        _boxTabCircumferential   = Box.createVerticalBox();

        _boxJCritical            = Box.createHorizontalBox();

        _boxJCriticalList        = Box.createHorizontalBox();
        _boxJCriticalLabelList   = Box.createHorizontalBox();
        _boxJCriticalButtons     = Box.createVerticalBox();

        _jLabelJCritical         = new JLabel("Center Curve : ");
        _jLabelJCriticalList     = new JLabel("Center Curve List");
        _jLabelJTotalList        = new JLabel("Total List");

        this._modelCritical      = new DefaultListModel();
        this._modelTotal         = new DefaultListModel();

        _jListJCriticalList      = new JList(this._modelCritical);
        _jListJTotalList         = new JList(this._modelTotal);
        _jButtonToTotal          = new JButton("-->");
        _jButtonToCritical       = new JButton("<--");
        _jScrollCriticalList     = new JScrollPane(_jListJCriticalList);
        _jScrollTotalList        = new JScrollPane(_jListJTotalList);

        _jCheckBoxCorrugationOn = new JCheckBox("");

        _jSpinBoxJCritical      = new JSpinner();
        this._spinnerModelJCritical = (SpinnerNumberModel)this._jSpinBoxJCritical.getModel();
        this._spinnerModelJCritical.setStepSize(1) ;


        this._jButtonApply = new JButton("Apply");

        this._tabbedIAssign      = new JTabbedPane();
        this._boxTabJStart       = Box.createVerticalBox();
        this._boxTabJEnd         = Box.createVerticalBox();
        this._boxColumnTitle     = Box.createHorizontalBox();
        this._boxColumnTitle2    = Box.createHorizontalBox();
        this._boxJStartIStart    = Box.createHorizontalBox();
        this._boxJStartIEnd      = Box.createHorizontalBox();
        this._boxJStartICritical = Box.createHorizontalBox();
        this._boxJEndIStart      = Box.createHorizontalBox();
        this._boxJEndIEnd        = Box.createHorizontalBox();
        this._boxJEndICritical   = Box.createHorizontalBox();
        this._boxJEndICritical   = Box.createHorizontalBox();

        this._jLabelJStartIStart   = new JLabel("   Start : ");
        this._jLabelJStartICritical= new JLabel("Critical : ");
        this._jLabelJStartIEnd     = new JLabel("     End : ");

        this._jLabelJEndIStart       = new JLabel("   Start : ");
        this._jLabelJEndICritical    = new JLabel("Critical : ");
        this._jLabelJEndIEnd         = new JLabel("     End : ");

        this._jCheckBoxBias          = new JCheckBox("Bias on");
        this._jCheckBoxCorrugationOn = new JCheckBox("Corrugation on");

        this._jLabelItem             = new JLabel("Item");
        this._jLabelPosition         = new JLabel("Position");
        this._jLabelRatio            = new JLabel("Ratio");
        this._jLabelItem2            = new JLabel("Item");
        this._jLabelPosition2        = new JLabel("Position");
        this._jLabelRatio2           = new JLabel("Ratio");

        _jSpinBoxJStartIStart         = new JSpinner();
        this._spinnerModelJStartIStart = (SpinnerNumberModel)this._jSpinBoxJStartIStart.getModel();
        this._spinnerModelJStartIStart.setStepSize(1) ;

        _jSpinBoxJStartICritical      = new JSpinner();
        this._spinnerModelJStartICritical = (SpinnerNumberModel)this._jSpinBoxJStartICritical.getModel();
        this._spinnerModelJStartICritical.setStepSize(1) ;

        _jSpinBoxJStartIEnd             = new JSpinner();
        this._spinnerModelJStartIEnd = (SpinnerNumberModel)this._jSpinBoxJStartIEnd.getModel();
        this._spinnerModelJStartIEnd.setStepSize(1) ;

        _jSpinBoxJEndIStart         = new JSpinner();
        this._spinnerModelJEndIStart = (SpinnerNumberModel)this._jSpinBoxJEndIStart.getModel();
        this._spinnerModelJEndIStart.setStepSize(1) ;

        _jSpinBoxJEndICritical      = new JSpinner();
        this._spinnerModelJEndICritical = (SpinnerNumberModel)this._jSpinBoxJEndICritical.getModel();
        this._spinnerModelJEndICritical.setStepSize(1) ;

        _jSpinBoxJEndIEnd             = new JSpinner();
        this._spinnerModelJEndIEnd = (SpinnerNumberModel)this._jSpinBoxJEndIEnd.getModel();
        this._spinnerModelJEndIEnd.setStepSize(1) ;


        this._jSliderJStartIStartRatio      =   new JSlider(0,100,50);
        this._jSliderJStartICriticalRatio   =   new JSlider(0,100,50);
        this._jSliderJStartIEndRatio        =   new JSlider(0,100,50);
        this._jSliderJEndIStartRatio        =   new JSlider(0,100,50);
        this._jSliderJEndICriticalRatio     =   new JSlider(0,100,50);
        this._jSliderJEndIEndRatio          =   new JSlider(0,100,50);


        this._jTextFieldCenterFormingAngle  = new JTextField("90.0");
        this._jLabelCenterFormingAngle      = new JLabel("Center Curve Forming Angle:") ;
        this._boxCenterFormingAngle         = Box.createHorizontalBox();

    }

    protected void setSizeComponent()
    {
        this._jButtonApply.setMaximumSize(this._dimensionButtonShort);

        this._jLabelJCritical         .setMaximumSize(this._dimensionFieldLong);

        this._jLabelJCriticalList       .setMaximumSize(this._dimensionFieldLong);
        this._jLabelJTotalList          .setMaximumSize(this._dimensionFieldLong);
        this._jLabelJCriticalList       .setMaximumSize(this._dimensionFieldLong);
        this._jLabelJTotalList          .setMaximumSize(this._dimensionFieldLong);
        this._jCheckBoxCorrugationOn    .setMaximumSize(this._dimensionFieldLong);


        Dimension dimRec = new Dimension(50,180);
        Dimension dimLonger = new Dimension(200,35);
        BevelBorder b = new BevelBorder(BevelBorder.LOWERED);
        this._jScrollCriticalList.      setPreferredSize(dimRec);
        this._jScrollTotalList.         setPreferredSize(dimRec);

        this._jListJCriticalList.setBorder(b);
        this._jListJTotalList.setBorder(b);

        this._jCheckBoxCorrugationOn    .setPreferredSize(this._dimensionFieldLong);
        this._jButtonToTotal        .setMaximumSize(this._dimensionButtonShort);
        this._jButtonToCritical           .setMaximumSize(this._dimensionButtonShort);
        this._jButtonToTotal        .setPreferredSize(this._dimensionButtonShort);
        this._jButtonToCritical           .setPreferredSize(this._dimensionButtonShort);

        this._jSpinBoxJCritical       .setMaximumSize(this._dimensionFieldLong);

        this._jLabelJStartIStart            .setMaximumSize(this._dimensionFieldLong);
        this._jLabelJStartICritical         .setMaximumSize(this._dimensionFieldLong);
        this._jLabelJStartIEnd              .setMaximumSize(this._dimensionFieldLong);

        this._jSpinBoxJStartIStart          .setMaximumSize(this._dimensionFieldShort);
        this._jSpinBoxJStartICritical       .setMaximumSize(this._dimensionFieldShort);
        this._jSpinBoxJStartIEnd            .setMaximumSize(this._dimensionFieldShort);

        this._jSliderJStartIStartRatio      .setMaximumSize(this._dimensionFieldLong);
        this._jSliderJStartICriticalRatio   .setMaximumSize(this._dimensionFieldLong);
        this._jSliderJStartIEndRatio        .setMaximumSize(this._dimensionFieldLong);

        this._jLabelJEndIStart              .setMaximumSize(this._dimensionFieldLong);
        this._jLabelJEndICritical           .setMaximumSize(this._dimensionFieldLong);
        this._jLabelJEndIEnd                .setMaximumSize(this._dimensionFieldLong);

        this._jSpinBoxJEndIStart            .setMaximumSize(this._dimensionFieldShort);
        this._jSpinBoxJEndICritical         .setMaximumSize(this._dimensionFieldShort);
        this._jSpinBoxJEndIEnd              .setMaximumSize(this._dimensionFieldShort);

        this._jSliderJEndIStartRatio        .setMaximumSize(this._dimensionFieldLong);
        this._jSliderJEndICriticalRatio     .setMaximumSize(this._dimensionFieldLong);
        this._jSliderJEndIEndRatio          .setMaximumSize(this._dimensionFieldLong);

        this._jLabelItem                    .setMaximumSize(this._dimensionFieldLong);
        this._jLabelPosition                .setMaximumSize(this._dimensionFieldShort);
        this._jLabelRatio                   .setMaximumSize(this._dimensionFieldLong);

        this._jLabelItem2                   .setMaximumSize(this._dimensionFieldLong);
        this._jLabelPosition2               .setMaximumSize(this._dimensionFieldShort);
        this._jLabelRatio2                  .setMaximumSize(this._dimensionFieldLong);

        this._jCheckBoxBias                 .setMaximumSize(this._dimensionFieldLong);

        this._jTextFieldCenterFormingAngle  .setMaximumSize(this._dimensionFieldShort);
        this._jLabelCenterFormingAngle      .setMaximumSize(this._dimensionLong);




        this._jLabelJStartIStart            .setPreferredSize(this._dimensionFieldLong);
        this._jLabelJStartICritical         .setPreferredSize(this._dimensionFieldLong);
        this._jLabelJStartIEnd              .setPreferredSize(this._dimensionFieldLong);

        this._jSpinBoxJStartIStart          .setPreferredSize(this._dimensionFieldShort);
        this._jSpinBoxJStartICritical       .setPreferredSize(this._dimensionFieldShort);
        this._jSpinBoxJStartIEnd            .setPreferredSize(this._dimensionFieldShort);

        this._jSliderJStartIStartRatio      .setPreferredSize(this._dimensionFieldLong);
        this._jSliderJStartICriticalRatio   .setPreferredSize(this._dimensionFieldLong);
        this._jSliderJStartIEndRatio        .setPreferredSize(this._dimensionFieldLong);

        this._jLabelJEndIStart              .setPreferredSize(this._dimensionFieldLong);
        this._jLabelJEndICritical           .setPreferredSize(this._dimensionFieldLong);
        this._jLabelJEndIEnd                .setPreferredSize(this._dimensionFieldLong);

        this._jSpinBoxJEndIStart            .setPreferredSize(this._dimensionFieldShort);
        this._jSpinBoxJEndICritical         .setPreferredSize(this._dimensionFieldShort);
        this._jSpinBoxJEndIEnd              .setPreferredSize(this._dimensionFieldShort);

        this._jSliderJEndIStartRatio        .setPreferredSize(this._dimensionFieldLong);
        this._jSliderJEndICriticalRatio     .setPreferredSize(this._dimensionFieldLong);
        this._jSliderJEndIEndRatio          .setPreferredSize(this._dimensionFieldLong);

        this._jLabelItem                    .setPreferredSize(this._dimensionFieldLong);
        this._jLabelPosition                .setPreferredSize(this._dimensionFieldLong);
        this._jLabelRatio                   .setPreferredSize(this._dimensionFieldLong);

        this._jLabelItem2                   .setPreferredSize(this._dimensionFieldLong);
        this._jLabelPosition2               .setPreferredSize(this._dimensionFieldLong);
        this._jLabelRatio2                  .setPreferredSize(this._dimensionFieldLong);

        this._jCheckBoxBias                 .setPreferredSize(this._dimensionFieldLong);

         this._jTextFieldCenterFormingAngle  .setPreferredSize(this._dimensionFieldShort);
         this._jLabelCenterFormingAngle      .setPreferredSize(this._dimensionLong);





    }



    protected void addComponent()
    {
        //----------------------
        this._boxTabPeriodical.add(Box.createVerticalStrut(10));//




        this._boxJCritical.add(_jLabelJCritical);
        this._boxJCritical.add(_jSpinBoxJCritical);
        this._boxTabPeriodical.add(_boxJCritical);//
        this._boxTabPeriodical.add(Box.createVerticalStrut(10));//
        this._boxTabPeriodical.add(Box.createVerticalStrut(10));
        this._boxTabPeriodical.add(Box.createVerticalGlue());

        this._boxTabCircumferential.add(Box.createVerticalStrut(10));
        this._boxJCriticalLabelList.add(this._jLabelJCriticalList );
        this._boxJCriticalLabelList.add(this._jLabelJTotalList );
        this._boxTabCircumferential.add(this._boxJCriticalLabelList);
        this._boxTabCircumferential.add(Box.createVerticalStrut(10));
        this._boxJCriticalList.add(this._jScrollCriticalList);
        this._boxJCriticalButtons.add(this._jButtonToTotal);
        this._boxJCriticalButtons.add(this._jButtonToCritical);
        this._boxJCriticalList.add(this._boxJCriticalButtons);
        this._boxJCriticalList.add(this._jScrollTotalList);
        this._boxTabCircumferential.add(_boxJCriticalList);
        this._boxTabCircumferential.add(Box.createVerticalStrut(10));
        this._boxTabCircumferential.add(Box.createVerticalGlue());
        this._tabbedJAssign.add(_boxTabPeriodical, "Periodical Mesh");
        this._tabbedJAssign.add(_boxTabCircumferential, "Circumferential Mesh");

        //--------------
        this._boxTabJStart.add(Box.createVerticalStrut(10));//

        this._boxColumnTitle.add(this._jLabelItem);
        this._boxColumnTitle.add(this._jLabelPosition);
        this._boxColumnTitle.add(this._jLabelRatio);
        this._boxTabJStart.add(_boxColumnTitle); //
        this._boxJStartIStart.add(this._jLabelJStartIStart);
        this._boxJStartIStart.add(this._jSpinBoxJStartIStart);
        this._boxJStartIStart.add(this._jSliderJStartIStartRatio);
        this._boxTabJStart.add(_boxJStartIStart);//
        this._boxTabJStart.add(Box.createVerticalStrut(10));//
        this._boxJStartICritical.add(this._jLabelJStartICritical);
        this._boxJStartICritical.add(this._jSpinBoxJStartICritical);
        this._boxJStartICritical.add(this._jSliderJStartICriticalRatio);
        this._boxTabJStart.add(_boxJStartICritical); //
        this._boxTabJStart.add(Box.createVerticalStrut(10));//
        this._boxJStartIEnd.add(this._jLabelJStartIEnd);
        this._boxJStartIEnd.add(this._jSpinBoxJStartIEnd);
        this._boxJStartIEnd.add(this._jSliderJStartIEndRatio);
        this._boxTabJStart.add(_boxJStartIEnd); //
        this._boxTabJStart.add(Box.createVerticalStrut(10));
        this._boxTabJStart.add(Box.createVerticalGlue());
        //----------------------
        this._boxTabJEnd.add(Box.createVerticalStrut(10));//
        this._boxColumnTitle2.add(this._jLabelItem2);
        this._boxColumnTitle2.add(this._jLabelPosition2);
        this._boxColumnTitle2.add(this._jLabelRatio2);
        this._boxTabJEnd.add(_boxColumnTitle2); //
        this._boxJEndIStart.add(this._jLabelJEndIStart);
        this._boxJEndIStart.add(this._jSpinBoxJEndIStart);
        this._boxJEndIStart.add(this._jSliderJEndIStartRatio);
        this._boxTabJEnd.add(_boxJEndIStart);//
        this._boxTabJEnd.add(Box.createVerticalStrut(10));//
        this._boxJEndICritical.add(this._jLabelJEndICritical);
        this._boxJEndICritical.add(this._jSpinBoxJEndICritical);
        this._boxJEndICritical.add(this._jSliderJEndICriticalRatio);
        this._boxTabJEnd.add(_boxJEndICritical); //
        this._boxTabJEnd.add(Box.createVerticalStrut(10));//
        this._boxJEndIEnd.add(this._jLabelJEndIEnd);
        this._boxJEndIEnd.add(this._jSpinBoxJEndIEnd);
        this._boxJEndIEnd.add(this._jSliderJEndIEndRatio);
        this._boxTabJEnd.add(_boxJEndIEnd); //
        this._boxTabJEnd.add(Box.createVerticalStrut(10));
        this._boxTabJEnd.add(Box.createVerticalGlue());
        this._tabbedIAssign.add(_boxTabJStart, "Right Curve");
        this._tabbedIAssign.add(_boxTabJEnd, "Left Curve");

        this._boxCenterFormingAngle.add(this._jLabelCenterFormingAngle);
        this._boxCenterFormingAngle.add(this._jTextFieldCenterFormingAngle);
        this._boxCenterFormingAngle.add(Box.createHorizontalStrut(10));
        this._boxCenterFormingAngle.add(Box.createVerticalGlue());



        this._contentPane.setLayout(null);

         this._jCheckBoxBias.setBounds(20,35,100,25);
         this._jCheckBoxCorrugationOn.setBounds(140,35,120,25);
         this._boxCenterFormingAngle.setBounds(60,445,350,25);

         this.add(_jCheckBoxBias);
         this.add(_jCheckBoxCorrugationOn);
         this.add(_boxCenterFormingAngle);

        this._tabbedJAssign.setBounds(20,70,260,165);
        this.add(_tabbedJAssign);

        this._tabbedIAssign.setBounds(20,255,260,180);
        this.add(_tabbedIAssign);

        //this._jButtonApply.setBounds(200, 185, 70, 25);
        this._jButtonOk.setBounds(110, 450, 70, 25);
        this._jButtonCancel.setBounds(200, 450, 70, 25);

        this._contentPane.add(this._jButtonApply);
        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);
    }
    protected void addListener()
    {
        this._jButtonToTotal.addActionListener(this);
        this._jButtonToCritical.addActionListener(this);

        this._jButtonApply.addActionListener(this);
        this._jSpinBoxJCritical.addKeyListener(this);
        this._jCheckBoxCorrugationOn.addActionListener(this);
        this._jCheckBoxBias.addActionListener(this);

        this._jTextFieldCenterFormingAngle.addKeyListener(this);

    }
    private void addItemListener2()
    {
        this._jSliderJStartIStartRatio.addChangeListener(this);
        this._jSliderJStartICriticalRatio.addChangeListener(this);
        this._jSliderJStartIEndRatio.addChangeListener(this);
        this._jSliderJEndIStartRatio.addChangeListener(this);
        this._jSliderJEndICriticalRatio.addChangeListener(this);
        this._jSliderJEndIEndRatio.addChangeListener(this);

        this._jSpinBoxJStartIStart     .addChangeListener(this);
        this._jSpinBoxJStartICritical  .addChangeListener(this);
        this._jSpinBoxJStartIEnd       .addChangeListener(this);
        this._jSpinBoxJEndIStart       .addChangeListener(this);
        this._jSpinBoxJEndICritical    .addChangeListener(this);
        this._jSpinBoxJEndIEnd         .addChangeListener(this);
        this._jSpinBoxJCritical.addChangeListener(this);

    }

    public void keyPressed(KeyEvent e)
    {
        int keyEventNumber = e.getKeyCode();
        switch (keyEventNumber)
        {
            case 10:
                Apply();
                break;
        }
    }




    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if (e.getSource() == this._jButtonOk)
        {
            Apply();
            DataShell data = this._system.getDataManager().getCurrentDataShell();
            data.deSelectCorrugation();
            this._system.getModeler().getPanel2D().GetDrawPanel().showCurrentDataXY();
            this.dispose();
        }
        else if (e.getSource() == this._jButtonCancel)
        {
            this._corrugationJStartIStart           =   this._oldIntValue[0];
            this._corrugationJStartIEnd             =   this._oldIntValue[1];
            this._corrugationJStartICritical        =   this._oldIntValue[2];
            this._corrugationJEndIStart             =   this._oldIntValue[3];
            this._corrugationJEndIEnd               =   this._oldIntValue[4];
            this._corrugationJEndICritical          =   this._oldIntValue[5];

            this._corrugationJStartIStartRatio      =    this._oldDoubleValue[0] ;
            this._corrugationJStartICriticalRatio   =    this._oldDoubleValue[1] ;
            this._corrugationJStartIEndRatio        =    this._oldDoubleValue[2] ;
            this._corrugationJEndIStartRatio        =    this._oldDoubleValue[3] ;
            this._corrugationJEndICriticalRatio     =    this._oldDoubleValue[4] ;
            this._corrugationJEndIEndRatio          =    this._oldDoubleValue[5] ;

            this._isBiasOn                          =    this._oldBooleanValue[0];
            Apply();
            DataShell data = this._system.getDataManager().getCurrentDataShell();

            data.setIsXYUpdateNeed(true);
            data.deSelectCorrugation();
            this._system.getModeler().getPanel2D().GetDrawPanel().showCurrentDataXY();
            this.dispose();
        }
        else if (e.getSource() == this._jCheckBoxCorrugationOn)
        {
            Apply();
        }
        else if (e.getSource() == this._jCheckBoxBias)
        {
            Apply();
        }




        if(e.getSource() == this._jButtonToCritical)
        {
            int index = this._jListJTotalList.getSelectedIndex();

            int indexNum = Integer.parseInt((String)this._modelTotal.getElementAt(index));

            this._modelTotal.removeElementAt(index);

               //&& j == this._modelCritical.size() - 1)
               if(this._modelCritical.size() == 0)
               {
                   this._modelCritical.insertElementAt("" + indexNum, 0);
               }

                for(int j = 0; j < this._modelCritical.size(); j++)
                {
                    int num = Integer.parseInt((String)this._modelCritical.getElementAt(j));
                    if (num > indexNum )
                    {
                        this._modelCritical.insertElementAt(""+indexNum, j);
                        break;
                    }
                    if (num < indexNum && j == this._modelCritical.size() - 1)
                    {
                        this._modelCritical.insertElementAt(""+indexNum, j +1);
                        break;
                    }
                }
                Apply();
        }
        if(e.getSource() == this._jButtonToTotal)
        {
            int index = this._jListJCriticalList.getSelectedIndex();
            int indexNum = Integer.parseInt((String)this._modelCritical.getElementAt(index));

            this._modelCritical.removeElementAt(index);

            for (int j = 0; j < this._modelTotal.size(); j++)
            {
                int num = Integer.parseInt((String)this._modelTotal.getElementAt(j));
                if (num < indexNum && j == this._modelTotal.size() - 1)
                {
                    this._modelTotal.insertElementAt("" + indexNum, j+1);
                }
                if (num > indexNum)
                {
                    this._modelTotal.insertElementAt("" + indexNum, j);
                    break;
                }
            }
            Apply();
        }



    }

    private void Apply() throws NumberFormatException
    {
        boolean isInputOk = false;
        isInputOk = this.checkTextfield(); //judge if the value is interger
        if (!isInputOk)
            return;

        DataShell data = this._system.getDataManager().getCurrentDataShell();
        _isBiasOn = this._jCheckBoxBias.isSelected();
        _isCorrugationOn = this._jCheckBoxCorrugationOn.isSelected();
        data.setIsBiasOn(this._isBiasOn);
        data.setIsCorrugationOn(this._isCorrugationOn);


         if (_isPerodical)
         {
            this._JCritical[0] = ((Integer) _spinnerModelJCritical.getValue()).intValue();

            this._JStart[0] = this._JCritical[0] - 1;
            this._JEnd[0] = this._JCritical[0] + 1;

            /*if (this._JCritical[0] == 0)
            {
                this._JStart[0] = this._countCircumferential - 2;
            }
            else if (this._JCritical[0] == _countCircumferential - 1)
            {
                this._JEnd[0] = 0;
            }*/

        }
        else
        {
            this._JStart = new int[this._modelCritical.size()];
            this._JEnd = new int[this._modelCritical.size()];
            for (int i = 0; i < this._modelCritical.size(); i++)
            {
                int critical = Integer.parseInt((String)this._modelCritical.getElementAt(i));

                this._JStart[i] = critical - 1;
                this._JEnd[i] = critical + 1;
            }
        }


        data.setCorrugationJStart(this._JStart);
        data.setCorrugationJEnd(this._JEnd);
        data.setCorrugationFormingAngle(this._corrugationAngle);

        data.setIsXYUpdateNeed(true);
        data.selectCorrugation();
        this._system.getModeler().getPanel2D().GetDrawPanel().showCurrentDataXY();
    }

    public void setPerodical(boolean bool)
    {
        this._isPerodical = bool;
        if(bool)
        {
            this._tabbedJAssign.setSelectedIndex(0);
            this._tabbedJAssign.remove(1);

        }
        else
        {
            this._tabbedJAssign.setSelectedIndex(1);
            this._tabbedJAssign.remove(0);
        }
    }

    public void setCountPerodical(int count)
    {
        this._countPerodical = count;
    }



    public void setCountRadial(int count)
    {
        this._countRadial = count;
    }


    protected boolean checkTextfield()
    {
        boolean bool = false;

        if(this._tabbedJAssign.getSelectedIndex() == 1) //circumferntial
        {
            this._countPerodical = 1;
            this._JCritical = new int[this._modelCritical.size()];
            for(int i = 0 ; i <  this._modelCritical.size() ; i ++)
            {
                this._JCritical[i] = Integer.parseInt((String)this._modelCritical.get(i));
            }

            bool = true;

        }
        else
        {
            try
            {
                this._JCritical[0] = ((Integer)this._spinnerModelJCritical.getValue()).intValue();

                bool = true;
            }
            catch(Exception e)
            {
                bool = false;
            }
        }

        try
        {
            _corrugationAngle = Double.parseDouble(this._jTextFieldCenterFormingAngle.getText());
        }
        catch (Exception e)
        {
            _corrugationAngle = 90.0;
            System.err.println(" Corrugation Angle Input Error!!");
            bool = false;
        }




        return bool;
    }

    public void stateChanged(ChangeEvent e)
    {
        System.out.println("test State Changed :" + _countTemp);
        _countTemp++;
        DataShell data = this._system.getDataManager().getCurrentDataShell();

        if(e.getSource() == this._jSpinBoxJCritical)
        {
            this.Apply();
            return;
        }

        if (e.getSource() == this._jSliderJStartIStartRatio)
        {
            this._corrugationJStartIStartRatio = this._jSliderJStartIStartRatio.getValue() / 100.0;
            data.setCorrugationJStartIStartRatio(_corrugationJStartIStartRatio);
        }
        else if (e.getSource() == this._jSliderJStartICriticalRatio)
        {
            this._corrugationJStartICriticalRatio = this._jSliderJStartICriticalRatio.getValue() / 100.0;
            data.setCorrugationJStartICirticalRatio(_corrugationJStartICriticalRatio);
        }
        else if (e.getSource() == this._jSliderJStartIEndRatio)
        {
            this._corrugationJStartIEndRatio = this._jSliderJStartIEndRatio.getValue() / 100.0;
            data.setCorrugationJStartIEndRatio(_corrugationJStartIEndRatio);
        }
        else if (e.getSource() == this._jSliderJEndIStartRatio)
        {
            this._corrugationJEndIStartRatio = this._jSliderJEndIStartRatio.getValue() / 100.0;
            data.setCorrugationJEndIStartRatio(_corrugationJEndIStartRatio);
        }
        else if (e.getSource() == this._jSliderJEndICriticalRatio)
        {
            this._corrugationJEndICriticalRatio = this._jSliderJEndICriticalRatio.getValue() / 100.0;
            data.setCorrugationJEndICirticalRatio(_corrugationJEndICriticalRatio);
        }
        else if (e.getSource() == this._jSliderJEndIEndRatio)
        {
            this._corrugationJEndIEndRatio = this._jSliderJEndIEndRatio.getValue() / 100.0;
            data.setCorrugationJEndIEndRatio(_corrugationJEndIEndRatio);
        }

        else if (e.getSource() ==this._jSpinBoxJStartIStart)
        {
            int iStart = ((Integer)this._jSpinBoxJStartIStart.getValue()).intValue() ;
            int iCritical = ((Integer)this._jSpinBoxJStartICritical.getValue()).intValue() ;
            if(iStart == iCritical)
            {
                iStart--;
                this._jSpinBoxJStartIStart.setValue(iStart);
                return;
            }
            this._corrugationJStartIStart = iStart ;
            data.setCorrugationJStartIStart(this._corrugationJStartIStart);
        }

        else if (e.getSource() ==this._jSpinBoxJStartICritical )
        {
            int iStart = ((Integer)this._jSpinBoxJStartIStart.getValue()).intValue() ;
            int iCritical = ((Integer)this._jSpinBoxJStartICritical.getValue()).intValue() ;
            int iEnd = ((Integer)this._jSpinBoxJStartIEnd.getValue()).intValue() ;
            if(iCritical == iEnd || iCritical == iStart)
            {
                if(iCritical == iEnd)
                    iCritical--;
                if(iCritical == iStart)
                    iCritical++;
                this._jSpinBoxJStartICritical.setValue(iCritical);
                return;
            }
            this._corrugationJStartICritical = iCritical ;
            data.setCorrugationJStartICritical(this._corrugationJStartICritical);
        }

        else if (e.getSource() ==this._jSpinBoxJStartIEnd )
        {
            int iCritical = ((Integer)this._jSpinBoxJStartICritical.getValue()).intValue() ;
            int iEnd = ((Integer)this._jSpinBoxJStartIEnd.getValue()).intValue() ;
            if(iEnd == iCritical)
            {
                iEnd++;
                this._jSpinBoxJStartIEnd.setValue(iEnd);
                return;
            }
            this._corrugationJStartIEnd = iEnd;
            data.setCorrugationJStartIEnd(this._corrugationJStartIEnd);
        }

        else if (e.getSource() ==this._jSpinBoxJEndIStart)
        {
            int iStart = ((Integer)this._jSpinBoxJEndIStart.getValue()).intValue() ;
            int iCritical = ((Integer)this._jSpinBoxJEndICritical.getValue()).intValue() ;
            if(iStart == iCritical)
            {
                iStart--;
                this._jSpinBoxJEndIStart.setValue(iStart);
                return;
            }
            this._corrugationJEndIStart = iStart ;
            data.setCorrugationJEndIStart(this._corrugationJEndIStart);
        }

        else if (e.getSource() ==this._jSpinBoxJEndICritical )
        {
            int iStart = ((Integer)this._jSpinBoxJEndIStart.getValue()).intValue() ;
            int iCritical = ((Integer)this._jSpinBoxJEndICritical.getValue()).intValue() ;
            int iEnd = ((Integer)this._jSpinBoxJEndIEnd.getValue()).intValue() ;
            if(iCritical == iEnd || iCritical == iStart)
            {
                if(iCritical == iEnd)
                    iCritical--;
                if(iCritical == iStart)
                    iCritical++;
                this._jSpinBoxJEndICritical.setValue(iCritical);
                return;
            }
            this._corrugationJEndICritical = iCritical ;
            data.setCorrugationJEndICritical(this._corrugationJEndICritical);
        }

        else if (e.getSource() ==this._jSpinBoxJEndIEnd )
        {
            int iCritical = ((Integer)this._jSpinBoxJEndICritical.getValue()).intValue() ;
            int iEnd = ((Integer)this._jSpinBoxJEndIEnd.getValue()).intValue() ;
            if(iEnd == iCritical)
            {
                iEnd++;
                this._jSpinBoxJEndIEnd.setValue(iEnd);
                return;
            }
            this._corrugationJEndIEnd = iEnd;
            data.setCorrugationJEndIEnd(this._corrugationJEndIEnd);

        }
        data.setIsXYUpdateNeed(true);

        this._system.getModeler().getPanel2D().GetDrawPanel().showCurrentDataXY();
    }


}
