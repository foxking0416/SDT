package sdt.dialog;

import sdt.framework.SDT_System;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.event.ActionEvent;
import sdt.data.DataTransition;
import sdt.data.DataCap;
import sdt.geometry.element.ElementPoint;
import sdt.define.DefineSystemConstant;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

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
public class DiaDimensionTransition extends DiaMain
{
    protected JTabbedPane _tabbed;
    protected Icon   _iconDimension;

    protected JLabel _jLabelDimension;
    protected JPanel _jPanelCoordinate;

    protected JLabel _jLabelX;
    protected JLabel _jLabelY;
    protected JLabel _jLabelStartPt;
    protected JLabel _jLabelEndPt;
    protected JLabel _jLabelMiddleInnerPt;
    protected JLabel _jLabelMiddleOuterPt;


    protected JTextField _jTextStartPtX;
    protected JTextField _jTextStartPtY;
    protected JTextField _jTextEndPtX;
    protected JTextField _jTextEndPtY;
    private JTextField _jTextMiddleInnerPtX;
    private JTextField _jTextMiddleInnerPtY;
    private JTextField _jTextMiddleOuterPtX;
    private JTextField _jTextMiddleOuterPtY;



    protected double _startPtX;
    protected double _startPtY;
    protected double _endPtX;
    protected double _endPtY;
    protected double _startPtX_Old;
    protected double _startPtY_Old;
    protected double _endPtX_Old;
    protected double _endPtY_Old;

    private double _middleInnerPtX;
    private double _middleInnerPtY;
    private double _middleOuterPtX;
    private double _middleOuterPtY;
    private double _middleInnerPtX_Old;
    private double _middleInnerPtY_Old;
    private double _middleOuterPtX_Old;
    private double _middleOuterPtY_Old;


    protected int _sectionNumber;


    public DiaDimensionTransition(SDT_System system, int sectionNumber)
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
        str = "Transition " + str;


        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 360);
        this.setLocation();
        this.setVisible(true);
    }


    protected void createComponent()
    {
        this._tabbed = new JTabbedPane();
        this._jPanelCoordinate = new JPanel(null);

        this._iconDimension = new ImageIcon(this.getClass().getResource("/sdt/icon/Dimension/DimensionTransition.png"));
        this._jLabelDimension = new JLabel(_iconDimension);

        this._jLabelX           = new JLabel("X");
        this._jLabelY           = new JLabel("Y");
        this._jLabelStartPt     = new JLabel("Start Point : ");
        this._jLabelEndPt       = new JLabel("End Point : ");
        this._jLabelMiddleInnerPt  = new JLabel("Middle In:");
        this._jLabelMiddleOuterPt = new JLabel("Middle Out:");


        this._jTextStartPtX = new JTextField("");
        this._jTextStartPtY = new JTextField("");
        this._jTextEndPtX = new JTextField("");
        this._jTextEndPtY = new JTextField("");
        this._jTextMiddleInnerPtX = new JTextField("");
        this._jTextMiddleInnerPtY = new JTextField("");
        this._jTextMiddleOuterPtX = new JTextField("");
        this._jTextMiddleOuterPtY = new JTextField("");

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

        this._jLabelMiddleInnerPt.setBounds(10, 200 ,80, 25);
        this._jTextMiddleInnerPtX.setBounds(90, 200 ,80, 25);
        this._jTextMiddleInnerPtY.setBounds(180, 200 ,80, 25);

        this._jLabelMiddleOuterPt.setBounds(10, 240 ,80, 25);
        this._jTextMiddleOuterPtX.setBounds(90, 240 ,80, 25);
        this._jTextMiddleOuterPtY.setBounds(180, 240 ,80, 25);

        this._jTextMiddleInnerPtY.setEditable(false); //lock on yAxis
        this._jTextMiddleOuterPtY.setEditable(false); //lock on yAxis
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

        this._jPanelCoordinate.add(_jLabelMiddleInnerPt);
        this._jPanelCoordinate.add(_jTextMiddleInnerPtX);
        this._jPanelCoordinate.add(_jTextMiddleInnerPtY);

        this._jPanelCoordinate.add(_jLabelMiddleOuterPt);
        this._jPanelCoordinate.add(_jTextMiddleOuterPtX);
        this._jPanelCoordinate.add(_jTextMiddleOuterPtY);


        this._tabbed.add(this._jPanelCoordinate, "Point");


        this._contentPane.setLayout(null);
        this._tabbed.setBounds(20, 35, 280, 310);
        this.add(_tabbed);
        this._jButtonOk.setBounds(150, 325, 70, 25);
        this._jButtonCancel.setBounds(230, 325, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);

    }

    protected void addListener()
    {
        this._jTextStartPtX.addKeyListener(this);
        this._jTextStartPtY.addKeyListener(this);
        this._jTextEndPtX.addKeyListener(this);
        this._jTextEndPtY.addKeyListener(this);
        this._jTextMiddleInnerPtX.addKeyListener(this);
        this._jTextMiddleOuterPtX.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if (e.getSource() == this._jButtonOk)
        {
            DataTransition dataTransition = (DataTransition)this._system.getDataManager().getCurrentDataShell();
            DataCap dataCap = (DataCap)this._system.getDataManager().getDataCap();
            this._startPtX_Old = dataTransition.getElementPointStart(this._sectionNumber).X();
            this._startPtY_Old = dataTransition.getElementPointStart(this._sectionNumber).Y();
            this._endPtX_Old = dataTransition.getElementPointEnd(this._sectionNumber).X();
            this._endPtY_Old = dataTransition.getElementPointEnd(this._sectionNumber).Y();
            this._middleInnerPtX_Old = dataTransition.getElementPointMiddleInner(this._sectionNumber).X();
            this._middleInnerPtY_Old = dataTransition.getElementPointMiddleInner(this._sectionNumber).Y();
            this._middleOuterPtX_Old = dataTransition.getElementPointMiddleOuter(this._sectionNumber).X();
            this._middleOuterPtY_Old = dataTransition.getElementPointMiddleOuter(this._sectionNumber).Y();

            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }
            boolean isOutOfConstraint = false;

            dataTransition.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataTransition.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);
            dataTransition.setElementPointMiddle(this._sectionNumber, this._middleInnerPtX, this._middleOuterPtX);

            isOutOfConstraint = dataCap.getElement(this._sectionNumber).setBoundary();

            if (isOutOfConstraint == true)
            {
                String diaStr = _system.GetInterfaceString("dia_MessageStrInputOutOfConstraint");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);

                dataTransition.setElementPointStartCoordinate(this._sectionNumber, this._startPtX_Old, this._startPtY_Old);
                dataTransition.setElementPointEndCoordinate(this._sectionNumber, this._endPtX_Old, this._endPtY_Old);
                dataTransition.setElementPointMiddle(this._sectionNumber, this._middleInnerPtX_Old, this._middleOuterPtX_Old);
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

        try
        {
            this._startPtX = Double.parseDouble(this._jTextStartPtX.getText());
            this._startPtY = Double.parseDouble(this._jTextStartPtY.getText());
            this._endPtX = Double.parseDouble(this._jTextEndPtX.getText());
            this._endPtY = Double.parseDouble(this._jTextEndPtY.getText());
            this._middleInnerPtX = Double.parseDouble(this._jTextMiddleInnerPtX.getText());
            this._middleInnerPtY = Double.parseDouble(this._jTextMiddleInnerPtY.getText());
            this._middleOuterPtX = Double.parseDouble(this._jTextMiddleOuterPtX.getText());
            this._middleOuterPtY = Double.parseDouble(this._jTextMiddleOuterPtY.getText());

            if (this._startPtX < 0 || this._endPtX < 0 || this._middleInnerPtX < 0 || this._middleOuterPtX < 0)
                bool = false;



            bool = true;
        }
        catch (Exception e)
        {
            String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
            DiaMessage diaWarning = new DiaMessage(_system, diaStr);
            bool = false;
        }
        return bool;
    }

    protected void setStartPtX(double sPtX)
    {
        this._jTextStartPtX.setText("" + sPtX);
        this.setTextfieldFormat(this._jTextStartPtX);
    }

    protected void setStartPtY(double sPtY)
    {
        this._jTextStartPtY.setText("" + sPtY);
        this.setTextfieldFormat(this._jTextStartPtY);
    }

    protected void setEndPtX(double ePtX)
    {
        this._jTextEndPtX.setText("" + ePtX);
        this.setTextfieldFormat(this._jTextEndPtX);
    }

    protected void setEndPtY(double ePtY)
    {
        this._jTextEndPtY.setText("" + ePtY);
        this.setTextfieldFormat(this._jTextEndPtY);
    }

    private void setMiddleInnerPtX(double mInPtX)
    {
        this._jTextMiddleInnerPtX.setText("" + mInPtX);
        this.setTextfieldFormat(this._jTextMiddleInnerPtX);
    }

    private void setMiddleInnerPtY(double mInPtY)
    {
        this._jTextMiddleInnerPtY.setText("" + mInPtY);
        this.setTextfieldFormat(this._jTextMiddleInnerPtY);
    }

    private void setMiddleOuterPtX(double mOutPtX)
    {
        this._jTextMiddleOuterPtX.setText("" + mOutPtX);
        this.setTextfieldFormat(this._jTextMiddleOuterPtX);
    }

    private void setMiddleOuterPtY(double mOutPtY)
    {
        this._jTextMiddleOuterPtY.setText("" + mOutPtY);
        this.setTextfieldFormat(this._jTextMiddleOuterPtY);
    }

    protected void setToOldValue()
    {
        this.setStartPtX(this._startPtX_Old);
        this.setStartPtY(this._startPtY_Old);
        this.setEndPtX(this._endPtX_Old);
        this.setEndPtY(this._endPtY_Old);
        this.setMiddleInnerPtX(this._middleInnerPtX_Old);
        this.setMiddleOuterPtX(this._middleOuterPtX_Old);
    }

    protected void readDiaData()
    {
        double startPtX = 0;
        double startPtY = 0;
        double endPtX = 0;
        double endPtY = 0;
        double middleInnerPtX = 0;
        double middleInnerPtY = 0;
        double middleOuterPtX = 0;
        double middleOuterPtY = 0;

        DataTransition dataTransition = (DataTransition)this._system.getDataManager().getCurrentDataShell();
        ElementPoint startPt = dataTransition.getElementPointStart(this._sectionNumber);
        ElementPoint endPt = dataTransition.getElementPointEnd(this._sectionNumber);
        ElementPoint middleInnerPt = dataTransition.getElementPointMiddleInner(this._sectionNumber);
        ElementPoint middleOuterPt = dataTransition.getElementPointMiddleOuter(this._sectionNumber);

        if (startPt != null || endPt != null)
        {
            startPtX = startPt.X();
            startPtY = startPt.Y();
            endPtX = endPt.X();
            endPtY = endPt.Y();

            middleInnerPtX = middleInnerPt.X();
            middleInnerPtY = middleInnerPt.Y();
            middleOuterPtX = middleOuterPt.X();
            middleOuterPtY = middleOuterPt.Y();

        }

        this.setStartPtX(startPtX);
        this.setStartPtY(startPtY);
        this.setEndPtX(endPtX);
        this.setEndPtY(endPtY);
        this.setMiddleInnerPtX(middleInnerPtX);
        this.setMiddleInnerPtY(middleInnerPtY);
        this.setMiddleOuterPtX(middleOuterPtX);
        this.setMiddleOuterPtY(middleOuterPtY);

    }
}
