package sdt.dialog;

import java.awt.event.*;

import javax.swing.*;

import sdt.data.*;
import sdt.framework.*;
import javax.swing.event.ChangeEvent;

public class DiaMeshDensity extends DiaMain
{
    /**
     *  UI Component
     */
    private JTabbedPane _tabbed ;
    private Box         _boxTabPeriodical;
    private Box         _boxTabGirth;

    private Box         _boxPeriodical;
    private Box         _boxCircumferential;
    private Box         _boxRadial;
    private Box         _boxThickness;
    private Box         _boxGirth;
    private Box         _boxRadial2;
    private Box         _boxThickness2;

    private Box         _boxSkewedAngle;

    private JLabel      _jLabelPeriodical;
    private JLabel      _jLabelCircumferential;
    private JLabel      _jLabelRadial;
    private JLabel      _jLabelThickness;

    private JLabel      _jLabelGirth;
    private JLabel      _jLabelRadial2;
    private JLabel      _jLabelThickness2;
    private JTextField  _jTextPeriodical;
    private JTextField  _jTextCircumferential;
    private JTextField  _jTextRadial;
    private JTextField  _jTextThickness;
    private JTextField  _jTextGirth;
    private JTextField  _jTextRadial2;
    private JTextField  _jTextThickness2;
    private JSlider     _jSliderSkewedAngle;
    private JCheckBox   _jCheckBoxSkewedOn;
    private JTextField  _jTextSkewedAngle;

    private SDT_DataManager _dataManager;
    private DataBase        _data;
    /**
     *  General Attribute
     */
    private int         _countGirth;
    private int         _countPeriodical;
    private int         _countCircumferential;
    private int         _countRadial;
    private int         _countThickness;
    /**
     *  Attribute of Shell
     */
    private double      _skewedAngle;
    private boolean     _skewedOn;
    /**
     *  Attribute of Solid
     */


    public DiaMeshDensity(SDT_System system)
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
        String str = this._system.GetInterfaceString("TitleDiaMeshDenstiy");
        this.setTitle(str);
        this.setSize(300, 255);
        this.setLocation();
        this.readDiaData();
        this.addItemListener2();
        this.setVisible(true);
    }
    protected void readDiaData()
    {

        this._dataManager           = this._system.getDataManager();
        this._data                  = this._system.getDataManager().getCurrentData();

        this._countGirth            = this._dataManager.getCountGirth();
        boolean isPeroidical        = this._data.getPeriodical();
        this._countPeriodical       = this._data.getCountPeriodical();
        this._countCircumferential  = this._data.getCountCircumferential();
        this._countRadial           = this._data.getCountRadial();
        this._countThickness        = this._data.getCountThickness();

        if(this._data.getDataFamily() == this.TYPE_FAMILY_SHELL)
        {
            this._skewedAngle = this._system.getDataManager().getCurrentDataShell().getSkewedAngle();
            this._skewedOn = this._system.getDataManager().getCurrentDataShell().getSkewedOn();

            _jCheckBoxSkewedOn.setSelected(this._skewedOn);
            _jSliderSkewedAngle.setValue((int)this._skewedAngle);
            _jTextSkewedAngle.setText("" + this._skewedAngle);

            _jSliderSkewedAngle.setEnabled(_skewedOn);
            _jTextSkewedAngle.setEnabled(_skewedOn);

            _jLabelThickness.setEnabled(false);
            _jTextThickness.setEnabled(false);
            _jLabelThickness2.setEnabled(false);
            _jTextThickness2.setEnabled(false);

        }
        else
        {

            _jTextThickness.setText("" + this._countThickness);
            _jTextThickness2.setText("" + this._countThickness);

            _jCheckBoxSkewedOn.setEnabled(false);
            _jSliderSkewedAngle.setEnabled(false);
            _jTextSkewedAngle.setEnabled(false);
         }


       this.setPerodical(isPeroidical);
       if (isPeroidical)
       {
           this.setCountPerodical(_countPeriodical);
           this.setCountCircumferential(_countCircumferential);
           this.setCountRadial(_countRadial);

           this.setCountGirth(_countGirth);
           this.setCountRadial2(_countRadial);

       }
       else
       {
           _countPeriodical = 1;
           this.setCountPerodical(_countPeriodical);
           this.setCountCircumferential(_countGirth);
           this.setCountRadial(_countRadial);

           this.setCountGirth(_countGirth);
           this.setCountRadial2(_countRadial);
       }
       if( this._data.getDataType() != this.TYPE_SURROUND)
       {
           this._jTextCircumferential.setEditable(false);
           this._jTextGirth.setEditable(false);
           this._jSliderSkewedAngle.setEnabled(false);
           this._jTextSkewedAngle.setEnabled(false);
           this._jCheckBoxSkewedOn.setEnabled(false);
       }


    }



    protected void createComponent()
    {
        _tabbed                  = new JTabbedPane();
        _boxTabPeriodical        = Box.createVerticalBox();
        _boxTabGirth             = Box.createVerticalBox();
        _boxPeriodical           = Box.createHorizontalBox();
        _boxCircumferential      = Box.createHorizontalBox();
        _boxRadial               = Box.createHorizontalBox();
        _boxThickness            = Box.createHorizontalBox();
        _boxGirth                = Box.createHorizontalBox();
        _boxRadial2              = Box.createHorizontalBox();
        _boxThickness2           = Box.createHorizontalBox();
        _boxSkewedAngle          = Box.createHorizontalBox();

        _jLabelPeriodical        = new JLabel("Periodical : ");
        _jLabelCircumferential   = new JLabel("Circumferential : ");
        _jLabelRadial            = new JLabel("Radial : ");
        _jLabelThickness         = new JLabel("Thickness : ");
        _jLabelGirth             = new JLabel("Girth : ");
        _jLabelRadial2           = new JLabel("Radial : ");
        _jLabelThickness2        = new JLabel("Thickness : ");
        _jTextPeriodical         = new JTextField("16");
        _jTextCircumferential    = new JTextField("7");
        _jTextRadial             = new JTextField("7");
        _jTextThickness          = new JTextField("1");
        _jTextGirth              = new JTextField("96");
        _jTextRadial2            = new JTextField("7");
        _jTextThickness2         = new JTextField("1");

        _jSliderSkewedAngle     = new JSlider(-90,90,0);
        _jCheckBoxSkewedOn       = new JCheckBox("Skewed");
        _jTextSkewedAngle        = new JTextField("0.0");

        _jSliderSkewedAngle.setEnabled(false);
        _jTextSkewedAngle.setEnabled(false);

    }
    protected void setSizeComponent()
    {
        _jLabelPeriodical        .setMaximumSize(this._dimensionFieldLong);
        _jLabelCircumferential   .setMaximumSize(this._dimensionFieldLong);
        _jLabelRadial            .setMaximumSize(this._dimensionFieldLong);
        _jLabelThickness         .setMaximumSize(this._dimensionFieldLong);
        _jLabelGirth             .setMaximumSize(this._dimensionFieldLong);
        _jLabelRadial2           .setMaximumSize(this._dimensionFieldLong);
        _jLabelThickness2        .setMaximumSize(this._dimensionFieldLong);
        _jTextPeriodical         .setMaximumSize(this._dimensionFieldLong);
        _jTextCircumferential    .setMaximumSize(this._dimensionFieldLong);
        _jTextRadial             .setMaximumSize(this._dimensionFieldLong);
        _jTextThickness          .setMaximumSize(this._dimensionFieldLong);
        _jTextGirth              .setMaximumSize(this._dimensionFieldLong);
        _jTextRadial2            .setMaximumSize(this._dimensionFieldLong);
        _jTextThickness2         .setMaximumSize(this._dimensionFieldLong);

         _jCheckBoxSkewedOn      .setMaximumSize(this._dimensionButtonMiddle);
         _jSliderSkewedAngle     .setMaximumSize(this._dimensionBoxMiddle);
         _jSliderSkewedAngle     .setPreferredSize(this._dimensionBoxMiddle);
         _jTextSkewedAngle       .setMaximumSize(this._dimensionButtonShort);


    }



    protected void addComponent()
    {
        _boxTabPeriodical.add(Box.createVerticalStrut(10));//
        _boxPeriodical.add(_jLabelPeriodical);
        _boxPeriodical.add(_jTextPeriodical);
        _boxTabPeriodical.add(_boxPeriodical);//
        _boxTabPeriodical.add(Box.createVerticalStrut(10));//
        _boxCircumferential.add(_jLabelCircumferential);
        _boxCircumferential.add(_jTextCircumferential);
        _boxTabPeriodical.add(_boxCircumferential);//
        _boxTabPeriodical.add(Box.createVerticalStrut(10));//
        _boxRadial.add(_jLabelRadial);
        _boxRadial.add(_jTextRadial);
        _boxTabPeriodical.add(_boxRadial);
        _boxTabPeriodical.add(Box.createVerticalStrut(10));
        _boxThickness.add(_jLabelThickness);
        _boxThickness.add(_jTextThickness);
        _boxTabPeriodical.add(_boxThickness);
        _boxTabPeriodical.add(Box.createVerticalStrut(10));
        _boxTabPeriodical.add(Box.createVerticalGlue());

        _boxTabGirth.add(Box.createVerticalStrut(10));
        _boxGirth.add(_jLabelGirth);
        _boxGirth.add(_jTextGirth);
        _boxTabGirth.add(_boxGirth);
        _boxTabGirth.add(Box.createVerticalStrut(10));
        _boxRadial2.add(_jLabelRadial2);
        _boxRadial2.add(_jTextRadial2);
        _boxTabGirth.add(_boxRadial2);
        _boxTabGirth.add(Box.createVerticalStrut(10));
        _boxThickness2.add(_jLabelThickness2);
        _boxThickness2.add(_jTextThickness2);
        _boxTabGirth.add(_boxThickness2);
        _boxTabGirth.add(Box.createVerticalStrut(10));
        _boxTabGirth.add(Box.createVerticalGlue());

        _boxSkewedAngle.add(_jCheckBoxSkewedOn);
        _boxSkewedAngle.add(_jSliderSkewedAngle);
        _boxSkewedAngle.add(_jTextSkewedAngle);

        _tabbed.add(_boxTabPeriodical, "Periodical Mesh");
        _tabbed.add(_boxTabGirth, "Girth Mesh");

        this._contentPane.setLayout(null);
        this._tabbed.setBounds(20,35,260,160);
        this._boxSkewedAngle.setBounds(20,210,260,25);
        this.add(_tabbed);
        this.add(_boxSkewedAngle);
        this._jButtonOk.setBounds(125, 220, 70, 25);
        this._jButtonCancel.setBounds(215, 220, 70, 25);
        //this.add(this.jButtonOk);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);
    }
    protected void addListener()
    {
        this._jTextCircumferential.addKeyListener(this);
        this._jTextGirth.addKeyListener(this);
        this._jTextPeriodical.addKeyListener(this);
        this._jTextThickness.addKeyListener(this);
        this._jTextRadial.addKeyListener(this);
        this._jTextRadial2.addKeyListener(this);
        this._jTextThickness2.addKeyListener(this);
        this._jCheckBoxSkewedOn.addActionListener(this);
        this._jTextSkewedAngle.addKeyListener(this);
    }
    private void addItemListener2()
    {
        this._jTextPeriodical.addFocusListener(this) ;
        this._jTextCircumferential.addFocusListener(this) ;
        this._jTextGirth.addFocusListener(this) ;
        this._jSliderSkewedAngle.addChangeListener(this);

    }

    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource() == this._jSliderSkewedAngle)
        {
            this._skewedAngle = this._jSliderSkewedAngle.getValue();
            this._jTextSkewedAngle.setText( this._skewedAngle + "");
            this.apply();
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if(e.getSource() == this._jButtonOk)
        {
            boolean applyable = this.apply();
            if(applyable)
                this.dispose();
        }
        else if(e.getSource() == this._jButtonCancel)
        {
            this.dispose();
            System.out.println("button Cancel");
        }
        else if(e.getSource() == this._jCheckBoxSkewedOn)
        {
            boolean bool = this._jCheckBoxSkewedOn.isSelected();
            this._jSliderSkewedAngle.setEnabled(bool) ;
            this._jTextSkewedAngle.setEnabled(bool) ;
            this.apply();
        }

    }
    public boolean apply()
    {
        this._countPeriodical       = Integer.parseInt(this._jTextPeriodical.getText());
        this._countCircumferential  = Integer.parseInt(this._jTextCircumferential.getText());
        this._countGirth            = Integer.parseInt(this._jTextGirth.getText());
        this._countThickness        = Integer.parseInt(this._jTextThickness.getText());

        this._skewedAngle           = Double.parseDouble(this._jTextSkewedAngle.getText());
        this._skewedOn              = this._jCheckBoxSkewedOn.isSelected();


        DataBase data = this._system.getDataManager().getCurrentData();

        if (this._tabbed.getSelectedIndex() == 0)
        {
            this._countRadial  = Integer.parseInt(this._jTextRadial.getText());
            if(this._countRadial < 4)
            {
                DiaMessage dia = new DiaMessage(this._system, "Radial Count can't be less than 4");
                return false;
            }

            this._countGirth = _countPeriodical * (_countCircumferential-1);
            this._jTextGirth.setText(this._countGirth +"");
            data.setPeriodical(true);
            data.setCountPeriodical(_countPeriodical);
            data.setCountCircumferential(_countCircumferential);
            data.setCountRadial(_countRadial);
            data.setCountThickness(_countThickness) ;
        }
        else
        {
            this._countRadial       = Integer.parseInt(this._jTextRadial2.getText());
            this._countThickness    = Integer.parseInt(this._jTextThickness2.getText());
            if (this._countRadial < 4)
            {
                DiaMessage dia = new DiaMessage(this._system, "Radial Count can't be less than 4");
                return false;
            }

            data.setPeriodical(false);
            //_countPeriodical = 1;
            _countCircumferential = _countGirth;
            //data.setCountPeriodical(_countPeriodical);
            data.setCountCircumferential(_countCircumferential);
            data.setCountRadial(_countRadial);
            data.setCountThickness(_countThickness) ;
        }
        if (this._system.getDataManager().getCurrentData().getDataType()  == this.TYPE_SURROUND)
        {
            this._system.getDataManager().setCountGirth(_countGirth);
        }

        if(data.getDataFamily() == this.TYPE_FAMILY_SHELL)
        {
            ((DataShell)data).setSkewedAngle(_skewedAngle);
            ((DataShell)data).setSkewedOn(_skewedOn);
        }
        data.setIsXYUpdateNeed(true);
        this._system.getModeler().getPanel2D().GetDrawPanel().showCurrentDataXY();
        return true;

    }

    public void keyPressed(KeyEvent e)
    {
        int keyEventNumber = e.getKeyCode();
        switch (keyEventNumber)
        {
            case 10:
                apply();
                break;
        }
    }

    public void setPerodical(boolean bool)
    {
        if(bool)
            this._tabbed.setSelectedIndex(0);
        else
            this._tabbed.setSelectedIndex(1);
    }

    public void setCountPerodical(int count)
    {
       this._jTextPeriodical.setText(""+count);
    }

    public void setCountCircumferential(int count)
    {
        this._jTextCircumferential.setText("" + count);
    }

    public void setCountRadial(int count)
    {
        this._jTextRadial.setText("" + count);
    }

    public void setCountGirth(int count)
    {
        this._jTextGirth.setText("" + count);
    }

    public void setCountRadial2(int count)
    {
        this._jTextRadial2.setText("" + count);
    }
    protected boolean checkTextfield()
    {
        boolean bool = false;

        if(this._tabbed.getSelectedIndex() == 1)
        {
            this._countPeriodical = 1;
            try
            {
                this._countGirth   = Integer.parseInt(this._jTextGirth.getText());
                this._countRadial  = Integer.parseInt(this._jTextRadial2.getText());

                if (this._countGirth  % 4 != 0)
                {
                    String diaStr = _system.GetInterfaceString("dia_MessageStrCountCantBeOddNumber");
                    DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                    bool = false;
                }
                else
                {
                    bool = true;
                }
            }
            catch(Exception e)
            {
                bool = false;
            }
        }
        else
        {
            try
            {
                this._countPeriodical = Integer.parseInt(this._jTextPeriodical.getText());
                this._countCircumferential = Integer.parseInt(this._jTextCircumferential.getText());
                this._countRadial  = Integer.parseInt(this._jTextRadial.getText());
                this._countGirth   = Integer.parseInt(this._jTextGirth.getText());

                if (this._countPeriodical * (_countCircumferential - 1 ) % 2 != 0)
                {
                    String diaStr = _system.GetInterfaceString("dia_MessageStrCountCantBeOddNumber");
                    DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                    bool = false;
                }
                else
                {
                    bool = true;
                }
            }
            catch(Exception e)
            {
                bool = false;
            }
        }

        if (this._countPeriodical < 1 ||
            this._countCircumferential < 1 ||
            this._countRadial < 1)
        {
            String diaStr = _system.GetInterfaceString("dia_MessageStrIntegerIsNotAllowedLowerThan1");
            DiaMessage diaWarning = new DiaMessage(_system, diaStr);
            bool = false;
        }

        return bool;
    }
    public void focusLost(FocusEvent e)
    {
        System.out.println("Focus Lost");
        if(this._data.getDataType() != this.TYPE_SURROUND)
            return;
        if(e.getSource() == this._jTextPeriodical)
        {
            System.out.println("Focus Lost from periodical");
            try
            {
                int countGirth = Integer.parseInt(this._jTextGirth.getText());
                int countPeriodical = Integer.parseInt(this._jTextPeriodical.getText());
                int countCircumferential = Integer.parseInt(this._jTextCircumferential.getText());
                if (countGirth % countPeriodical != 0)
                {
                    String diaStr = "Periodical number should be the factor of Girth!!"; //_system.GetInterfaceString("dia_MessageStrIntegerIsNotAllowedLowerThan1");
                    DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                    this._jTextPeriodical.setText(this._countPeriodical + "");
                    return;
                }
                else
                {

                    countCircumferential = countGirth / countPeriodical + 1 ;

                }
                this._jTextPeriodical.setText(countPeriodical + "");
                this._jTextCircumferential.setText(countCircumferential + "");
            }
            catch(Exception ex)
            {
                String diaStr = "Periodical number should be Integer"; //_system.GetInterfaceString("dia_MessageStrIntegerIsNotAllowedLowerThan1");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                this._jTextPeriodical.setText( this._countPeriodical + "");
            }
        }
        else if(e.getSource() == this._jTextCircumferential) // can be used only from dataSurround
        {
            System.out.println("Focus Lost from Circumferential");
            try
            {
                int countCircumferential = Integer.parseInt(this._jTextCircumferential.getText());
                int countPeriodical = Integer.parseInt(this._jTextPeriodical.getText());
                int countGirth = (countCircumferential -1)* countPeriodical;
                if(countGirth % 4 !=0)
                {
                    String diaStr = "The girth number ("+countGirth +") is not a multiple of 4"; //_system.GetInterfaceString("dia_MessageStrIntegerIsNotAllowedLowerThan1");
                    DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                    this._jTextCircumferential.setText(this._countCircumferential + "");
                }
                else
                {
                    this._jTextGirth.setText(countGirth + "");
                }
            }
            catch (Exception ex)
            {
                String diaStr = "Circumferential number should be Integer"; //_system.GetInterfaceString("dia_MessageStrIntegerIsNotAllowedLowerThan1");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                this._jTextCircumferential.setText( this._countCircumferential + "");
            }
        }
        else if (e.getSource() == this._jTextGirth) // can be used only from dataSurround
        {
            System.out.println("Focus Lost from Girth");
            try
            {
                int countGirth = Integer.parseInt(this._jTextGirth.getText());
                int countCircumferential = Integer.parseInt(this._jTextCircumferential.getText());
                int countPeriodical = Integer.parseInt(this._jTextPeriodical.getText());
                if (countGirth % 4 != 0)
                {
                    String diaStr = "The girth number (" + countGirth + ") is not a multiple of 4"; //_system.GetInterfaceString("dia_MessageStrIntegerIsNotAllowedLowerThan1");
                    DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                    this._jTextGirth.setText(this._countGirth + "");
                    return;
                }
                if(countGirth % (countCircumferential-1) == 0)  // First , consider making the countCircumferential Same
                {
                    countPeriodical = countGirth  / (countCircumferential-1);

                    this._jTextGirth.setText(countGirth + "");
                    this._jTextPeriodical.setText(countPeriodical + "");
                    return;
                }
                else if (countGirth % countPeriodical == 0) // second , if countPerodical  is the factor of countGirth
                                                                 // make sure that countCircumferential is becoming larger
                {
                    countCircumferential = countGirth  / countPeriodical + 1 ;
                    if( countCircumferential > this._countCircumferential)     // if the new countCircumferential is larger, acceptable
                    {
                        this._jTextCircumferential.setText(countCircumferential + "");
                        return;
                    }
                    else                                 // if the new countCircumferential is smaller, need to change the _countPeriodical to make the
                                                         // new countCircumferential become larger
                    {
                        int multiple = 1;
                        multiple = _countCircumferential / countCircumferential;
                        if(_countCircumferential % countCircumferential != 0)
                            multiple += 1;
                        if (this._countPeriodical % multiple == 0)                 // if the periodical can be divided directly
                        {
                            countPeriodical = countPeriodical/ multiple;
                            countCircumferential = countGirth  / countPeriodical + 1 ;

                            this._jTextPeriodical.setText(countPeriodical + "");
                            this._jTextCircumferential.setText(countCircumferential + "");
                            return;
                        }
                        else   // the periocial can't be divided to make the new countCircumferential
                               // being equal with or larger than the original countCircumferential
                        {
                            this.findNewPeriodicalAndCircuferential(countGirth);
                        }
                    }

                }
                else
                {
                    this.findNewPeriodicalAndCircuferential(countGirth);
                }


            }
            catch (Exception ex)
            {
                String diaStr = "Girth number should be Integer"; //_system.GetInterfaceString("dia_MessageStrIntegerIsNotAllowedLowerThan1");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                this._jTextGirth.setText( this._countGirth + "");
            }
        }

        System.out.println("Focus Lost End");
    }
    public void findNewPeriodicalAndCircuferential(int countGirth)
    {

        int multiple = 1;
        int countPeriodical = 4;
        int countCircumferential =  countGirth  / countPeriodical + 1;

        if(countCircumferential < this._countCircumferential )
        {
            String diaStr = "the Girth number is too small"; //_system.GetInterfaceString("dia_MessageStrIntegerIsNotAllowedLowerThan1");
            DiaMessage diaWarning = new DiaMessage(_system, diaStr);
            this._jTextGirth.setText( this._countGirth + "");
            return;
        }


        while (countCircumferential > this._countCircumferential && countGirth % (4* multiple) == 0 )
        {
            countPeriodical = 4* multiple;
            countCircumferential = countGirth  / countPeriodical + 1;
            multiple++;
        }

        this._jTextGirth.setText( countGirth + "");
        this._jTextCircumferential.setText( countCircumferential + "");
        this._jTextPeriodical.setText( countPeriodical + "");

    }



}

