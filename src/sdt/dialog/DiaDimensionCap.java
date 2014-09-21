package sdt.dialog;

import java.awt.event.*;

import javax.swing.*;

import sdt.data.*;
import sdt.define.*;
import sdt.framework.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DiaDimensionCap extends DiaMain
{
    private JTabbedPane _tabbed;


    private Icon   _iconDimension;

    private JLabel _jLabelDimension;
    private JPanel _jPanelCoordinate;

    private JLabel _jLabelX;
    private JLabel _jLabelY;
    private JLabel _jLabelStartPt;
    private JLabel _jLabelEndPt;

    private JTextField _jTextStartPtX;
    private JTextField _jTextStartPtY;
    private JTextField _jTextEndPtX;
    private JTextField _jTextEndPtY;


    private double _startPtX = 0;
    private double _startPtY = 0;
    private double _endPtX = 0;
    private double _endPtY = 0;

    private double _oldStartPtX = 0;
    private double _oldStartPtY = 0;
    private double _oldEndPtX = 0;
    private double _oldEndPtY = 0;



    private int           _sectionNumber = 0;


    public DiaDimensionCap(SDT_System system, int sectionNumber)
    {
        super(system, true);
        this._sectionNumber = sectionNumber;
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
        if (this._sectionNumber == this.XZView)
            str = this._system.GetInterfaceString("TitleDimensionXZ");
        else if (this._sectionNumber == this.YZView)
            str = this._system.GetInterfaceString("TitleDimensionYZ");
        str = "Cap " + str;

        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 280);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        this._tabbed = new JTabbedPane();

        this._jPanelCoordinate = new JPanel(null);

        this._iconDimension = new ImageIcon(this.getClass().getResource("/sdt/icon/Dimension/DimensionCap.png"));
        this._jLabelDimension = new JLabel(this._iconDimension);

        this._jLabelX       = new JLabel("X");
        this._jLabelY       = new JLabel("Y");
        this._jLabelStartPt = new JLabel("Start Point : ");
        this._jLabelEndPt   = new JLabel("End Point : ");

        this._jTextStartPtX = new JTextField("");
        this._jTextStartPtY = new JTextField("");
        this._jTextEndPtX   = new JTextField("");
        this._jTextEndPtY   = new JTextField("");

        this._jTextStartPtX.setEditable(false); //lock on yAxis
    }

    protected void setSizeComponent()
    {
        this._jLabelDimension.setBounds(0, 10, 280, 75);
        this._jLabelX.setBounds(90, 90, 80, 25);
        this._jLabelY.setBounds(180, 90, 80, 25);
        this._jLabelStartPt.setBounds(10, 120, 80, 25);
        this._jTextStartPtX.setBounds(90, 120, 80, 25);
        this._jTextStartPtY.setBounds(180, 120, 80, 25);
        this._jLabelEndPt.setBounds(10, 160, 80, 25);
        this._jTextEndPtX.setBounds(90, 160, 80, 25);
        this._jTextEndPtY.setBounds(180, 160, 80, 25);

    }

    protected void addComponent()
    {
        this._jPanelCoordinate.add(_jLabelDimension);

        this._jPanelCoordinate.add(_jLabelX);
        this._jPanelCoordinate.add(_jLabelY);

        this._jPanelCoordinate.add(_jLabelStartPt);
        this._jPanelCoordinate.add(_jTextStartPtX);
        this._jPanelCoordinate.add(_jTextStartPtY);

        this._jPanelCoordinate.add(_jLabelEndPt);
        this._jPanelCoordinate.add(_jTextEndPtX);
        this._jPanelCoordinate.add(_jTextEndPtY);


        this._tabbed.add(this._jPanelCoordinate, "Point");

        this._contentPane.setLayout(null);
        this._tabbed.setBounds(20, 35, 280, 230);
        this.add(_tabbed);
        this._jButtonOk.setBounds(150, 245, 70, 25);
        this._jButtonCancel.setBounds(230, 245, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);

    }

    protected void addListener()
    {
        this._jTextStartPtX .addKeyListener(this);
        this._jTextStartPtY .addKeyListener(this);
        this._jTextEndPtX   .addKeyListener(this);
        this._jTextEndPtY   .addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {

        super.actionPerformed(e);
        if (e.getSource() == this._jButtonOk)
        {

            boolean isInputCorrect = false;
            boolean isOutOfConstraint = false;
            boolean isOutOfOtherConstraint = false;

            DataCap dataCap = (DataCap)this._system.getDataManager().getCurrentDataShell();

            this._oldStartPtX = dataCap.getCapStartPt(_sectionNumber)[0];
            this._oldStartPtY = dataCap.getCapStartPt(_sectionNumber)[1];
            this._oldEndPtX = this._oldStartPtX + dataCap.getCapWidth(_sectionNumber);
            this._oldEndPtY = this._oldStartPtY - dataCap.getCapHeight(_sectionNumber);



            isInputCorrect = this.checkTextfield();
            if(isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }

            dataCap.setCapStartPoint(this._startPtX, this._startPtY, this._sectionNumber);
            dataCap.setCapEndPoint(this._endPtX, this._endPtY, this._sectionNumber);


            isOutOfConstraint = dataCap.getElement(this._sectionNumber).setBoundary();
            isOutOfOtherConstraint = !(dataCap.setElementToData(this._sectionNumber));


            this._system.getModeler().getPanel2D().GetDrawPanel().upDate();
            if (isOutOfConstraint == true || isOutOfOtherConstraint == true)
            {
                String diaStr = _system.GetInterfaceString("dia_MessageStrInputOutOfConstraint");

                DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                dataCap.setCapStartPoint(this._oldStartPtX, this._oldStartPtY, this._sectionNumber);
                dataCap.setCapEndPoint(this._oldEndPtX, this._oldEndPtY, this._sectionNumber);
                this.setToOldValue();
                return;
            }
            this.dispose();

        }
        else if (e.getSource() == this._jButtonCancel)
        {
            this.dispose();
            System.out.println("button Cancel");
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
            tempCapStartX = Double.parseDouble(this._jTextStartPtX.getText());
            tempCapStartY = Double.parseDouble(this._jTextStartPtY.getText());
            tempCapEndX = Double.parseDouble(this._jTextEndPtX.getText());
            tempCapEndY = Double.parseDouble(this._jTextEndPtY.getText());

            if (tempCapEndX < 0 || tempCapStartY < tempCapEndY)
            {
                String diaStr = _system.GetInterfaceString("dia_MessageStrIntegerIsNotAllowedLowerThan0");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                bool = false;
            }
            else
            {
                this._startPtX = tempCapStartX;
                this._startPtY = tempCapStartY;
                this._endPtX = tempCapEndX;
                this._endPtY = tempCapEndY;

                bool = true;
            }

        }
        catch (Exception e)
        {
            String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
            DiaMessage diaWarning = new DiaMessage(_system, diaStr);
            bool = false;

        }

        return bool;
    }

    private void setCapStartPt(double ptX,double ptY)
    {
        this._jTextStartPtX.setText("" + ptX);
        this.setTextfieldFormat(this._jTextStartPtX);

         this._jTextStartPtY.setText("" + ptY);
         this.setTextfieldFormat(this._jTextStartPtY);
    }

    private void setCapEndPt(double ptX,double ptY)
    {
        this._jTextEndPtX.setText("" + ptX);
        this.setTextfieldFormat(this._jTextEndPtX);

         this._jTextEndPtY.setText("" + ptY);
         this.setTextfieldFormat(this._jTextEndPtY);
    }



    private void setToOldValue()
    {
        this.setCapStartPt(this._oldStartPtX,this._oldStartPtY);
        this.setCapEndPt(this._oldEndPtX, this._oldEndPtY);
    }

    protected void readDiaData()
    {
        double capStartPtX= 0;
        double capStartPtY= 0;
        double capEndPtX= 0;
        double capEndPtY= 0;
        double capWidth = 0;
        double capHeight = 0;


        DataCap dataCap = (DataCap)this._system.getDataManager().getCurrentDataShell();
        capHeight = dataCap.getCapHeight(this._sectionNumber);
        capWidth = dataCap.getCapWidth(this._sectionNumber);

        capStartPtX= dataCap.getCapStartPt(this._sectionNumber)[0];
        capStartPtY= dataCap.getCapStartPt(this._sectionNumber)[1];

        capEndPtX = capStartPtX + capWidth;
        capEndPtY = capStartPtY - capHeight;

        this.setCapStartPt( capStartPtX, capStartPtY);
        this.setCapEndPt(capEndPtX, capEndPtY);

    }

}
