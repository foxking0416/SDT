package sdt.dialog;

import java.awt.event.*;

import javax.swing.*;

import sdt.framework.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class DiaPortSetting extends DiaMain
{
    private Icon   _iconDimension;

    private JLabel _jLabelPortNumber;
    private JLabel _jLabelPortLength;
    private JLabel _jLabelPortWidth;
    private JLabel _jLabelPortArea;

    private JTextField _jTextPortNumber;
    private JTextField _jTextPortLength;
    private JTextField _jTextPortWidth;
    private JTextField _jTextPortArea;
    private JCheckBox _jCheckBoxIsPortSealed;

    private JButton  _jButtonLengthPlus;
    private JButton  _jButtonLengthMinus;
    private JButton  _jButtonWidthPlus;
    private JButton  _jButtonWidthMinus;

    private JSpinner    _jSpinBoxJPortNumber;
    private SpinnerNumberModel   _spinnerModelJPortNumber;
    private int            lengthIndex = 1;
    private boolean        isPortSealed;
    private double ratioInitial;
    private boolean        isCancel = false;

    public int     getLengthIndex()     {return this.lengthIndex;}
    public boolean getIsPortSealed()    {return this.isPortSealed;}
    public boolean getIsCancel()        {return this.isCancel;}

    public DiaPortSetting(SDT_System system)
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
        String str = "Dia Port Setting";

            //str = this._system.GetInterfaceString("TitleDimensionYZ");
        //str = "Cap " + str;

        this.readDiaData();
        this.setTitle(str);
        this.setSize(280, 200);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
       // this._iconDimension = new ImageIcon(this.getClass().getResource("/sdt/icon/Dimension/DimensionCap.png"));

       this._jCheckBoxIsPortSealed  = new JCheckBox("Is Port Sealed?");

       this._jLabelPortNumber       = new JLabel("Port Number:");
        this._jLabelPortLength       = new JLabel("Port Length:");
        this._jLabelPortWidth        = new JLabel("Port Width:");
        this._jLabelPortArea         = new JLabel("Port Area: ");

        this._jTextPortNumber = new JTextField("4");
        this._jTextPortLength = new JTextField("");
        this._jTextPortWidth  = new JTextField("");
        this._jTextPortArea   = new JTextField("");

        this._jTextPortArea.setEditable(false);
        this._jTextPortLength.setEditable(false);
        this._jTextPortWidth.setEditable(false);

        _jSpinBoxJPortNumber         = new JSpinner();
        this._spinnerModelJPortNumber = (SpinnerNumberModel)this._jSpinBoxJPortNumber.getModel();
        this._spinnerModelJPortNumber.setStepSize(1);
        this._spinnerModelJPortNumber.setMaximum(12);
        this._spinnerModelJPortNumber.setMinimum(1);
        this._spinnerModelJPortNumber.setValue(4);

        this._jButtonLengthPlus = new JButton("+");
        this._jButtonLengthMinus= new JButton("-");
        this._jButtonWidthPlus  = new JButton("+");
        this._jButtonWidthMinus = new JButton("-");

         ratioInitial = this._system.getDataManager().getDataAir().getAirElementRatio();
        //_jSpinBoxJPortLength         = new JSpinner();
        //this._spinnerModelJPortLength = (SpinnerNumberModel)this._jSpinBoxJPortLength.getModel();
        //this._spinnerModelJPortLength.setStepSize(0.85);
       // this._spinnerModelJPortLength.setMaximum(6);
       // this._spinnerModelJPortLength.setMinimum(1);
       // this._spinnerModelJPortLength.setValue(1);


    }

    protected void setSizeComponent()
    {
        this._jCheckBoxIsPortSealed.setBounds(20, 15, 220, 25);

        this._jLabelPortNumber.setBounds(20, 45, 80, 25);
        this._jLabelPortLength.setBounds(20, 75, 80, 25);
        this._jLabelPortWidth.setBounds(20, 105, 80, 25);
        this._jLabelPortArea.setBounds(20, 135, 80, 25);

        this._jSpinBoxJPortNumber.setBounds(100, 45, 140, 25);

        this._jTextPortLength.setBounds(140, 75, 60, 25);
        this._jButtonLengthPlus.setBounds(200, 75, 40, 25);
        this._jButtonLengthMinus.setBounds(100, 75, 40, 25);

        this._jTextPortWidth.setBounds(140, 105, 60, 25);
        this._jButtonWidthPlus.setBounds(200, 105, 40, 25);
        this._jButtonWidthMinus.setBounds(100, 105, 40, 25);



        this._jTextPortArea.setBounds(100, 135, 140, 25);

    }

    protected void addComponent()
    {
        this._contentPane.setLayout(null);

        this._contentPane.add(_jCheckBoxIsPortSealed);

        this._contentPane.add(_jLabelPortNumber);
        this._contentPane.add(_jLabelPortLength);
        this._contentPane.add(_jLabelPortWidth);
        this._contentPane.add(_jLabelPortArea);

        this._contentPane.add(_jSpinBoxJPortNumber);

        this._contentPane.add(_jTextPortLength);
        this._contentPane.add(_jButtonLengthPlus);
        this._contentPane.add(_jButtonLengthMinus);

        this._contentPane.add(_jTextPortWidth);
        this._contentPane.add(_jButtonWidthPlus);
        this._contentPane.add(_jButtonWidthMinus);

        this._contentPane.add(_jTextPortArea);

        this._jButtonOk.setBounds(100, 165, 70, 25);
        this._jButtonCancel.setBounds(170, 165, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);

    }

    protected void addListener()
    {
        this._jTextPortNumber .addKeyListener(this);
        this._jTextPortLength .addKeyListener(this);
        this._jTextPortArea   .addKeyListener(this);

        this._jButtonLengthPlus.addActionListener(this);
        this._jButtonLengthMinus.addActionListener(this);
        this._jButtonWidthPlus.addActionListener(this);
        this._jButtonWidthMinus.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {

        super.actionPerformed(e);
        if (e.getSource() == this._jButtonOk)
        {
            if (this._jCheckBoxIsPortSealed.isSelected())
                this.isPortSealed = true;
            else
                this.isPortSealed = false;
            this.dispose();

        }
        else if (e.getSource() == this._jButtonCancel)
        {
            lengthIndex = 1;
            this._system.getDataManager().getDataAir().setAirElementRatio(ratioInitial);
           // this.readDiaData();
           // this.validate();
            isCancel = true;
            this.dispose();
            System.out.println("button Cancel");
        }
        NumberFormat formatter = new DecimalFormat("#.###");
        if(e.getSource() == this._jButtonLengthPlus)
        {
            lengthIndex ++;
            this.readDiaData();
            this.validate();



        }
        if(e.getSource() == this._jButtonLengthMinus)
        {
            if(lengthIndex >= 2)
                lengthIndex --;

            this.readDiaData();
            this.validate();
        }


        if(e.getSource() == this._jButtonWidthPlus)
        {
            System.out.println("Width Plus");
            String str = this._jTextPortWidth.getText();

            double ratio = this._system.getDataManager().getDataAir().getAirElementRatio();
            if(ratio >=2)
            {
                ratio -= 1;
                this._system.getDataManager().getDataAir().setAirElementRatio(ratio);

                this.readDiaData();
                this.validate();
                String str2 = this._jTextPortWidth.getText();
                if (str.equals(str2))
                {
                    ratio += 1;
                    this._system.getDataManager().getDataAir().setAirElementRatio(ratio);
                }
            }

        }
        else if (e.getSource() == this._jButtonWidthMinus)
        {
            System.out.println("Width Minus");
            String str = this._jTextPortWidth.getText();
            double ratio = this._system.getDataManager().getDataAir().getAirElementRatio();
            ratio += 1;
            this._system.getDataManager().getDataAir().setAirElementRatio(ratio);

            this.readDiaData();
            this.validate();
            String str2 = this._jTextPortWidth.getText();
            if (str.equals(str2))
            {
                ratio -= 1;
                this._system.getDataManager().getDataAir().setAirElementRatio(ratio);
            }
        }

    }

    protected boolean checkTextfield()
    {
        boolean bool = false;
        double tempCapStartX;
        double tempCapStartY;
        double tempCapEndX;
        double tempCapEndY;

        try
        {
            tempCapStartX = Double.parseDouble(this._jTextPortNumber.getText());
            tempCapStartY = Double.parseDouble(this._jTextPortLength.getText());
            tempCapEndY = Double.parseDouble(this._jTextPortArea.getText());


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
       double[] portSize = this._system.getDataManager().getPortSize();

       NumberFormat formatter = new DecimalFormat("#.###");

       double length = lengthIndex * portSize[1];

       this._jTextPortLength.setText( formatter.format(length));
       this._jTextPortWidth.setText( formatter.format(portSize[0]));
       this._jTextPortArea.setText( formatter.format(length * portSize[0]));
    }

}
