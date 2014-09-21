package sdt.dialog;

import java.awt.*;
import java.awt.event.*;
import java.text.*;

import javax.swing.*;

import sdt.framework.*;
import java.util.ArrayList;
public class DiaEMMA extends DiaMain
{

    private JPanel              panelBoundaryDescription;
    private JPanel              panelExecutionDescription;

    private JLabel              jLabelMagnetForce;
    private JLabel              jLabelCircuitResistance;
    private JLabel              jLabelCircuitInductance;
    private JLabel              jLabelInductanceMultipler;
    private JLabel              jLabelDrivingVoltage;
    private JLabel              jLabelUnitMagnetForce;
    private JLabel              jLabelUnitCircuitResistance;
    private JLabel              jLabelUnitCircuitInductance;
    private JLabel              jLabelUnitDrivingVoltage;

    private JTextField          jTextMagnetForce;
    private JTextField          jTextCircuitResistance;
    private JTextField          jTextCircuitInductance;
    private JTextField          jTextInductanceMultiplier;
    private JTextField          jTextDrivingVoltage;

    private JButton             jbuttonLCaculator;

    private JLabel              jLabelZetaDefault;
    private JTextField          jTextZetaDefault;
    private JLabel[]            jLabelSeq;
    private JLabel              jLabelFreqTitle;
    private JLabel              jLabelZetaTitle;
    private JLabel[]            jLabelFreq;
    private JTextField[]        jTextZeta;
    private JScrollPane         jScrollPaneZeta;

    private double valueMaganetForce = 0.0;
    private double valueCircuitResistance = 0.0;
    private double valueCircuitInductance = 0.0;
    private double valueInductanceMultiplier = 0.0;
    private double valueDrivingVoltage = 0.0;
    private double valueZetaDefault = 0.0;

    private int    state = 0;

    private double omega[];
    private double zeta[];


    public DiaEMMA(SDT_System system)
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
       String str = "";

       str = this._system.GetInterfaceString("TitleEMMA");

       this.readDiaData();
       this.setTitle(str);
       this.setSize(308, 300);
       this.setLocation();
       if(omega != null)
           this.setVisible(true);
       else
       {
           String strMsg = this._system.GetInterfaceString("dia_MessageStrNoFEMResult");
           DiaMessage dia = new DiaMessage(this._system, strMsg);
           this.dispose();
       }
   }



    protected void createComponent()
    {
        this.jLabelMagnetForce                  = new JLabel("Magnet force (BL):");
        this.jLabelCircuitResistance            = new JLabel("Circuit resistance (R):");
        this.jLabelCircuitInductance            = new JLabel("Circuit inductance (L):");
        this.jLabelInductanceMultipler          = new JLabel("Inductance multiplier:");
        this.jLabelDrivingVoltage               = new JLabel("Driving voltage (Vo):");
        this.jLabelUnitMagnetForce              = new JLabel("(N/A)");
        this.jLabelUnitCircuitResistance        = new JLabel("(Ohm)");
        this.jLabelUnitCircuitInductance        = new JLabel("(H)");
        this.jLabelUnitDrivingVoltage           = new JLabel("(volt)");

        this.jTextMagnetForce                    = new JTextField("0.52");
        this.jTextCircuitResistance              = new JTextField("40");
        this.jTextCircuitInductance              = new JTextField("61E-06");
        this.jTextInductanceMultiplier           = new JTextField("1");
        this.jTextDrivingVoltage                 = new JTextField("0.179");

        this.jbuttonLCaculator   = new JButton("Calculator");

        this.jLabelZetaDefault   = new JLabel("Default \u03B6 for All Modes:");
        this.jLabelFreqTitle     = new JLabel("Frequency");
        this.jLabelZetaTitle     = new JLabel("\u03B6");
        this.jTextZetaDefault    = new JTextField("0.02");

        this.panelBoundaryDescription = new JPanel();
        this.panelExecutionDescription = new JPanel();

        this.panelBoundaryDescription.setLayout(null) ;
        this.panelExecutionDescription.setLayout(null) ;
        this.jScrollPaneZeta = new JScrollPane();


        /***
         * original code
         */
        ArrayList array = this._system.getDataManager().getFrequencyArray();

        /***
         * Start of Debug
         */


       /* ArrayList array  = new ArrayList();
        for(int i = 0 ; i < 35 ; i++)
        {
            array.add(i,""+i);
        }*/

        /***
         * End of Debug
         */
        if(array!= null)
        {
            omega = new double[array.size()];
            for (int i = 0; i < array.size(); i++)
            {
                String str = ((String) array.get(i)).trim();
                omega[i] = Double.parseDouble(str);
            }
        }
        this._jButtonCancel.setText("cancel");
       this._jButtonOk.setText("next");
    }

    protected void setSizeComponent()
    {
        this.panelBoundaryDescription.setBounds(5, 5, 303, 257);
        this.jLabelMagnetForce.setBounds(10, 10, 130, 30);
        this.jLabelCircuitResistance.setBounds(10, 50, 130, 30);
        this.jLabelCircuitInductance.setBounds(10, 90, 130, 30);
        this.jLabelInductanceMultipler .setBounds(25, 180, 130, 30);
        this.jLabelDrivingVoltage     .setBounds(10, 220, 130, 30);
        this.jLabelUnitMagnetForce     .setBounds(255, 10, 40, 30);
        this.jLabelUnitCircuitResistance .setBounds(255, 50, 40, 30);
        this.jLabelUnitCircuitInductance .setBounds(255, 90, 40, 30);
        this.jLabelUnitDrivingVoltage    .setBounds(255, 220, 40, 30);


        this.jTextMagnetForce.setBounds(165, 10, 85, 30);
        this.jTextCircuitResistance.setBounds(165, 50, 85, 30);
        this.jTextCircuitInductance.setBounds(165, 90, 85, 30);
        this.jTextInductanceMultiplier.setBounds(155, 180, 45, 30);
        this.jTextDrivingVoltage.setBounds(165, 220, 85, 30);

        this.jbuttonLCaculator.setBounds(195, 130, 100, 30);
        this.jScrollPaneZeta.setSize(303, 257);

        jLabelZetaDefault .   setBounds(10, 10, 200, 30);
        jTextZetaDefault  .   setBounds(155, 10, 40, 30);
        jLabelFreqTitle   .   setBounds(70, 45, 60, 30);
        jLabelZetaTitle   .   setBounds(165, 45, 30, 30);

        this._contentPane.setBackground(Color.lightGray);
        this.panelBoundaryDescription.setBackground(Color.lightGray);
        this.panelExecutionDescription.setBackground(Color.lightGray);

        this._jButtonOk.setBounds(210, 265, 80, 25);
        this._jButtonCancel.setBounds(120, 265, 80, 25);
        //this._jButtonClose.setBounds(frameWidth - 25 - 10, 5, 25, 21);

    }

    protected void addComponent()
    {
        this._contentPane.setLayout(null);
        this._contentPane.add(this.panelBoundaryDescription);

        this.panelBoundaryDescription.setLayout(null) ;
        this.panelBoundaryDescription.add(jLabelMagnetForce);
       this.panelBoundaryDescription.add(jLabelCircuitResistance);
       this.panelBoundaryDescription.add(jLabelCircuitInductance);

       this.panelBoundaryDescription.add(jLabelInductanceMultipler);
       this.panelBoundaryDescription.add(jLabelDrivingVoltage);
       this.panelBoundaryDescription.add(jLabelUnitMagnetForce);
       this.panelBoundaryDescription.add(jLabelUnitCircuitResistance);
       this.panelBoundaryDescription.add(jLabelUnitCircuitInductance);
       this.panelBoundaryDescription.add(jLabelUnitDrivingVoltage);

       this.panelBoundaryDescription.add(jTextMagnetForce);
       this.panelBoundaryDescription.add(jTextCircuitResistance);
       this.panelBoundaryDescription.add(jTextCircuitInductance);
       this.panelBoundaryDescription.add(jTextInductanceMultiplier);
       this.panelBoundaryDescription.add(jTextDrivingVoltage);

       this.panelBoundaryDescription.add(jbuttonLCaculator);
       if(omega != null)
           this.setZetaTable(this.omega);

       this._contentPane.add(this._jButtonOk);
       this._contentPane.add(this._jButtonCancel);



    }

    protected void addListener()
    {
        this.jbuttonLCaculator.addActionListener(this);
    }

    protected boolean checkTextfield()
    {
        return false;
    }

    protected void readDiaData()
    {

    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this._jButtonOk)
        {
            if (this.state == 0)
            {
                valueMaganetForce = this.checkIsTextFieldNumeric(this.jTextMagnetForce);
                valueCircuitResistance = this.checkIsTextFieldNumeric(this.jTextCircuitResistance);
                valueCircuitInductance = this.checkIsTextFieldNumeric(this.jTextCircuitInductance);
                valueInductanceMultiplier = this.checkIsTextFieldNumeric(this.jTextInductanceMultiplier);
                valueDrivingVoltage = this.checkIsTextFieldNumeric(this.jTextDrivingVoltage);

                System.out.println("valueMaganetForce : " + valueMaganetForce);
                System.out.println("valueCircuitResistance : " + valueCircuitResistance);
                System.out.println("valueCircuitInductance : " + valueCircuitInductance);
                System.out.println("valueInductanceMultiplier : " + valueInductanceMultiplier);
                System.out.println("valueDrivingVoltage : " + valueDrivingVoltage);

                if (valueMaganetForce != -1 && valueCircuitResistance != -1 &&
                    valueCircuitInductance != -1 && valueInductanceMultiplier != -1 &&
                    valueDrivingVoltage != -1)
                {
                    this.state += 1;
                    this.showByState(true);
                }
                else
                {

                    DiaMessage diaMsg = new DiaMessage(this._system, "Please input Number format in Textfield!");
                }

            }
            else if (state ==1)
            {
                zeta = new double[this.jTextZeta.length];
                valueZetaDefault = this.checkIsTextFieldNumeric(jTextZetaDefault) ;
                if (valueZetaDefault == -1)
                {
                    DiaMessage diaMsg = new DiaMessage(this._system, "<html>Please input Number format <br> in Zeta Default Value  !</html>");
                    return;
                }

                for(int i = 0 ; i < this.jTextZeta.length; i++)
                {
                    zeta[i] = this.checkIsTextFieldNumeric(jTextZeta[i]) ;
                    if ( zeta[i] == -1)
                    {
                        //System.out.println("jTextZeta Error : " + i + " -->"+ jTextZeta[i].getText());
                        //DiaMessage diaMsg = new DiaMessage(this._system, "<html>Please input Number format <br> in Zeta Setting At" + i  + " !</html>");
                        //return;
                        zeta[i] = valueZetaDefault;
                    }
                    //System.out.println("jTextZeta Error : " + i + " -->"+ jTextZeta[i].getText());
                }


                double[][] dataXY = this._system.getDataManager().caluculateSPL(this.valueMaganetForce, this.valueCircuitResistance, this.valueCircuitInductance, this.valueInductanceMultiplier,this.valueDrivingVoltage,this.zeta);

                new DiaPlotLog(this._system, dataXY);
          for(int i = 0 ; i < dataXY.length ; i++)
                {
              //      System.out.println(dataXY[i][0] + "----> " + dataXY[i][1]);
                }
                this.dispose();
            }
        }
        else if (e.getSource() == this._jButtonCancel)
        {
            if (this.state == 0)
            {
                this.dispose();
            }
            else if (this.state == 1)
            {
                this.state -= 1;
                this.showByState(false);
            }

        }
        else if (e.getSource() == this._jButtonClose)
        {
            this.dispose();
        }
    }
    private void showByState(boolean forward)
    {
        this._contentPane.removeAll();
        this._contentPane.add(this._jButtonCancel);
        this._contentPane.add(this._jButtonOk);

        switch (state)
        {
            case 0:
                this._contentPane.add(this.panelBoundaryDescription);
                if(forward)
                {
                    this.setZetaTable(this.omega);
               }
                this.panelBoundaryDescription.updateUI();
                this._jButtonCancel.setText("Previous");
                this._jButtonOk.setText("Next");
                this._jButtonCancel.setEnabled(true);
                this._jButtonOk.setEnabled(true);

                break;

            case 1:
                this._contentPane.add(this.jScrollPaneZeta);
                if(forward)
                {
                    this.setZetaTable(this.omega);
                }

                this.panelExecutionDescription.updateUI();
                this._jButtonOk.setText("See Result");
                this._jButtonOk.setEnabled(true);
                break;
        }
        this._contentPane.updateUI();
    }

    public double checkIsTextFieldNumeric(JTextField text)
    {
        //boolean isNumber = false;
        double value = -1;
        String str = text.getText();
        try
        {
            value = Double.parseDouble(str);

        }
        catch (NumberFormatException ex)
        {
            value = -1;
        }

        return value;
    }

    public void setZetaTable(double[] omega)
    {
        int modeNumber = omega.length;
        //int modeNumber = 40;
        this.jLabelSeq = new JLabel[modeNumber];
        this.jLabelFreq = new JLabel[modeNumber];
        this.jTextZeta = new JTextField[modeNumber];
        NumberFormat formater = new DecimalFormat("####0.0###");

        panelExecutionDescription.add(jLabelZetaDefault);
        panelExecutionDescription.add(jTextZetaDefault);
        panelExecutionDescription.add(jLabelFreqTitle);
        panelExecutionDescription.add(jLabelZetaTitle);

        for (int i = 0; i < modeNumber; i++)
        {
            jLabelSeq[i] = new JLabel("" + (i + 1));
            jLabelFreq[i] = new JLabel(formater.format(omega[i] ));/// Math.PI / 2.0));
            //jLabelFreq[i] = new JLabel("testFreq");
            jTextZeta[i] = new JTextField();
            jLabelSeq[i].setBounds(40, 70 + i * 30, 30, 30);
            jLabelFreq[i].setBounds(70, 70 + i * 30, 70, 30);
            jTextZeta[i].setBounds(150, 70 + i * 30, 50, 30);

            panelExecutionDescription.add(jLabelSeq[i]);
            panelExecutionDescription.add(jLabelFreq[i]);
            panelExecutionDescription.add(jTextZeta[i]);
        }
        Dimension dim = new Dimension(283, 70 + modeNumber * 30 + 30);
        this.panelExecutionDescription.setSize(dim);
        this.panelExecutionDescription.setPreferredSize(dim);

        jScrollPaneZeta.getViewport().add(panelExecutionDescription, null);
        this.jScrollPaneZeta.setBounds(5, 5, 303, 257);
        this.jScrollPaneZeta.setSize(303, 257);
        this.jScrollPaneZeta.setAutoscrolls(true);
        //this.jScrollPaneZeta.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    }


}
