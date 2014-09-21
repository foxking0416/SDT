package sdt.dialog;

import java.awt.event.*;

import javax.swing.*;

import sdt.data.*;
import sdt.define.*;
import sdt.framework.*;
import sdt.geometry.element.*;

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
public class DiaDimensionSurroundDoubleArc extends DiaMain
{
    protected JTabbedPane _tabbed;
    protected Icon   _iconDimension;

    protected JLabel _jLabelDimension;
    protected JPanel _jPanelCoordinate;

    protected JLabel _jLabelX;
    protected JLabel _jLabelY;
    protected JLabel _jLabelStartPt;
    protected JLabel _jLabelEndPt;
    protected JLabel _jLabelSurroundHeight;
    protected JLabel _jLabelSurroundInnerRadius;
    protected JLabel _jLabelSurroundOuterRadius;


    protected JTextField _jTextStartPtX;
    protected JTextField _jTextStartPtY;
    protected JTextField _jTextEndPtX;
    protected JTextField _jTextEndPtY;
    protected JTextField _jTextSurroundHeight;
    protected JTextField _jTextSurroundInnerRadius;
    protected JTextField _jTextSurroundOuterRadius;


    protected double _surroundWidth = 0;
    protected double _surroundInnerRadius = 0;
    protected double _surroundOuterRadius = 0;
    protected double _surroundHeight = 0;
    protected double _transitionHeight = 0;
    protected double _startPtX;
    protected double _startPtY;
    protected double _endPtX;
    protected double _endPtY;

    protected double _surroundWidth_Old;
    protected double _surroundInnerRadius_Old;
    protected double _surroundOuterRadius_Old;
    protected double _surroundHeight_Old;
    protected double _transitionHeight_Old;
    protected double _startPtX_Old;
    protected double _startPtY_Old;
    protected double _endPtX_Old;
    protected double _endPtY_Old;
    protected int _sectionNumber;


    public DiaDimensionSurroundDoubleArc(SDT_System system, int sectionNumber)
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
        str = "Surround " + str;

        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 410);
        this.setLocation();
        this.setVisible(true);
    }


    protected void createComponent()
    {
        this._tabbed = new JTabbedPane();
        this._jPanelCoordinate = new JPanel(null);

        this._iconDimension = new ImageIcon(this.getClass().getResource("/sdt/icon/Dimension/DimensionSurroundDoubleArc.png"));
        this._jLabelDimension = new JLabel(_iconDimension);

        this._jLabelX       = new JLabel("X");
        this._jLabelY       = new JLabel("Y");
        this._jLabelStartPt             = new JLabel("Start Point : ");
        this._jLabelEndPt               = new JLabel("End Point : ");
        this._jLabelSurroundHeight      = new JLabel("Height : ");
        this._jLabelSurroundInnerRadius = new JLabel("Inner Radius : ");
        this._jLabelSurroundOuterRadius = new JLabel("Outer Radius : ");


        this._jTextSurroundHeight = new JTextField("");
        this._jTextSurroundInnerRadius = new JTextField("");
        this._jTextSurroundOuterRadius = new JTextField("");
        this._jTextStartPtX = new JTextField("");
        this._jTextStartPtY = new JTextField("");
        this._jTextEndPtX = new JTextField("");
        this._jTextEndPtY = new JTextField("");
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

        this._jLabelSurroundHeight.setBounds(10,210, 80, 25);
        this._jTextSurroundHeight.setBounds(90,210, 80, 25);

        this._jLabelSurroundInnerRadius.setBounds(10,250, 80, 25);
        this._jTextSurroundInnerRadius.setBounds(90,250, 80, 25);

        this._jLabelSurroundOuterRadius.setBounds(10,290, 80, 25);
        this._jTextSurroundOuterRadius.setBounds(90,290, 80, 25);

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

        this._jPanelCoordinate.add(_jLabelSurroundHeight);
        this._jPanelCoordinate.add(_jTextSurroundHeight);

        this._jPanelCoordinate.add(_jLabelSurroundInnerRadius);
        this._jPanelCoordinate.add(_jTextSurroundInnerRadius);

        this._jPanelCoordinate.add(_jLabelSurroundOuterRadius);
        this._jPanelCoordinate.add(_jTextSurroundOuterRadius);


        this._tabbed.add(this._jPanelCoordinate, "Dimension");

        this._contentPane.setLayout(null);
        this._tabbed.setBounds(20, 35, 280, 360);
        this.add(_tabbed);
        this._jButtonOk.setBounds(150, 375, 70, 25);
        this._jButtonCancel.setBounds(230, 375, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);

    }

    protected void addListener()
    {
        this._jTextStartPtX.addKeyListener(this);
        this._jTextStartPtY.addKeyListener(this);
        this._jTextEndPtX.addKeyListener(this);
        this._jTextEndPtY.addKeyListener(this);
        this._jTextSurroundInnerRadius.addKeyListener(this);
        this._jTextSurroundOuterRadius.addKeyListener(this);
        this._jTextSurroundHeight.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if (e.getSource() == this._jButtonOk)
        {
            boolean isInputCorrect = false;
            boolean isOutOfConstraint = false;
            boolean isOutOfOtherConstraint = false;

            DataSurround dataSurround = (DataSurround)this._system.getDataManager().getCurrentDataShell();
            this._surroundHeight_Old = dataSurround.getSurroundHeight(this._sectionNumber);
            this._surroundInnerRadius_Old = dataSurround.getSurroundDoubleArcInnerRadius(this._sectionNumber);
            this._surroundOuterRadius_Old = dataSurround.getSurroundDoubleArcOuterRadius(this._sectionNumber);
            this._startPtX_Old = dataSurround.getElementPointStart(this._sectionNumber).X();
            this._startPtY_Old = dataSurround.getElementPointStart(this._sectionNumber).Y();
            this._endPtX_Old = dataSurround.getElementPointEnd(this._sectionNumber).X();
            this._endPtY_Old = dataSurround.getElementPointEnd(this._sectionNumber).Y();

            isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }
            dataSurround.setIfSetFromDialog(DefineSystemConstant.XZView, true);
            dataSurround.setIfSetFromDialog(DefineSystemConstant.YZView, true);
            //dataSurround.setIfSetFromDialog(this._sectionNumber, true);
            dataSurround.setSurroundHeightInOutRadius(this._sectionNumber, this._surroundHeight, this._surroundInnerRadius, this._surroundOuterRadius);
            dataSurround.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataSurround.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);

            if (!dataSurround.getIsRunway())
            {
                if (this._sectionNumber == DefineSystemConstant.XZView)
                    dataSurround.setSurroundHeightInOutRadius(DefineSystemConstant.YZView, this._surroundHeight, this._surroundInnerRadius, this._surroundOuterRadius);
                else if (this._sectionNumber == DefineSystemConstant.YZView)
                    dataSurround.setSurroundHeightInOutRadius(DefineSystemConstant.XZView, this._surroundHeight, this._surroundInnerRadius, this._surroundOuterRadius);
            }


            isOutOfConstraint = dataSurround.getElement(this._sectionNumber).setBoundary();
            isOutOfOtherConstraint = !(dataSurround.setElementToData(this._sectionNumber));


            this._system.getModeler().getPanel2D().GetDrawPanel().upDate();
            if (isOutOfConstraint == true || isOutOfOtherConstraint == true)
            {
                if(isOutOfConstraint == true)
                    System.out.println("out of self constraint");
                if(isOutOfOtherConstraint == true)
                    System.out.println("out of other constraint");
                String diaStr = _system.GetInterfaceString("dia_MessageStrInputOutOfConstraint");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);

                dataSurround.setSurroundHeightInOutRadius(this._sectionNumber, this._surroundHeight_Old, this._surroundInnerRadius_Old, this._surroundOuterRadius_Old);
                dataSurround.setElementPointStartCoordinate(this._sectionNumber, this._startPtX_Old, this._startPtY_Old);
                dataSurround.setElementPointEndCoordinate(this._sectionNumber, this._endPtX_Old, this._endPtY_Old);

                if(!dataSurround.getIsRunway())
                {
                    if(this._sectionNumber == DefineSystemConstant.XZView)
                    {
                        dataSurround.setSurroundHeightInOutRadius(DefineSystemConstant.YZView, this._surroundHeight_Old, this._surroundInnerRadius_Old, this._surroundOuterRadius_Old);
                        dataSurround.setElementPointStartCoordinate(DefineSystemConstant.YZView, this._startPtX_Old, this._startPtY_Old);
                        dataSurround.setElementPointEndCoordinate(DefineSystemConstant.YZView, this._endPtX_Old, this._endPtY_Old);
                    }
                    else if(this._sectionNumber == DefineSystemConstant.YZView)
                    {
                        dataSurround.setSurroundHeightInOutRadius(DefineSystemConstant.XZView, this._surroundHeight_Old, this._surroundInnerRadius_Old, this._surroundOuterRadius_Old);
                        dataSurround.setElementPointStartCoordinate(DefineSystemConstant.XZView, this._startPtX_Old, this._startPtY_Old);
                        dataSurround.setElementPointEndCoordinate(DefineSystemConstant.XZView, this._endPtX_Old, this._endPtY_Old);
                    }
                }


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


    public void setSurroundHeight(double h)
    {
        this._jTextSurroundHeight.setText("" + h);
        this.setTextfieldFormat(this._jTextSurroundHeight);
    }

    public void setSurroundInnerRadius(double innerR)
    {
        this._jTextSurroundInnerRadius.setText("" + innerR);
        this.setTextfieldFormat(this._jTextSurroundInnerRadius);
    }

    public void setSurroundOuterRadius(double outerR)
    {
        this._jTextSurroundOuterRadius.setText("" + outerR);
        this.setTextfieldFormat(this._jTextSurroundOuterRadius);
    }


    public void setStartPtX(double sPtX)
    {
        this._jTextStartPtX.setText("" + sPtX);
        this.setTextfieldFormat(this._jTextStartPtX);
    }

    public void setStartPtY(double sPtY)
    {
        this._jTextStartPtY.setText("" + sPtY);
        this.setTextfieldFormat(this._jTextStartPtY);
    }

    public void setEndPtX(double ePtX)
    {
        this._jTextEndPtX.setText("" + ePtX);
        this.setTextfieldFormat(this._jTextEndPtX);
    }

    public void setEndPtY(double ePtY)
    {
        this._jTextEndPtY.setText("" + ePtY);
        this.setTextfieldFormat(this._jTextEndPtY);
    }

    protected boolean checkTextfield()
    {
        boolean bool = false;
        double tempSurroundInnerR;
        double tempSurroundOuterR;
        double tempSurroundHeight;

        try
        {
            this._startPtX = Double.parseDouble(this._jTextStartPtX.getText());
            this._startPtY = Double.parseDouble(this._jTextStartPtY.getText());
            this._endPtX = Double.parseDouble(this._jTextEndPtX.getText());
            this._endPtY = Double.parseDouble(this._jTextEndPtY.getText());


            tempSurroundInnerR = Double.parseDouble(this._jTextSurroundInnerRadius.getText());
            tempSurroundOuterR = Double.parseDouble(this._jTextSurroundOuterRadius.getText());
            tempSurroundHeight = Double.parseDouble(this._jTextSurroundHeight.getText());

            if (tempSurroundInnerR < 0 || tempSurroundOuterR < 0 || tempSurroundHeight < 0)
            {
                String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                bool = false;
            }
            else
            {
                this._surroundInnerRadius = tempSurroundInnerR;
                this._surroundOuterRadius = tempSurroundOuterR;
                this._surroundHeight = tempSurroundHeight;

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

    public void setToOldValue()
    {
        this.setSurroundHeight(this._surroundHeight_Old);
        this.setSurroundInnerRadius(this._surroundInnerRadius_Old);
        this.setSurroundOuterRadius(this._surroundOuterRadius_Old);
        this.setStartPtX(this._startPtX_Old);
        this.setStartPtY(this._startPtY_Old);
        this.setEndPtX(this._endPtX_Old);
        this.setEndPtY(this._endPtY_Old);
    }

    protected void readDiaData()
    {

        double surroundHeight = 0;
        double surroundInnerRadius = 0;
        double surroundOuterRadius = 0;
        double startPtX = 0;
        double startPtY = 0;
        double endPtX = 0;
        double endPtY = 0;

        DataSurround dataSurround = (DataSurround)this._system.getDataManager().getCurrentDataShell();

        surroundHeight = dataSurround.getSurroundHeight(this._sectionNumber);
        surroundInnerRadius = dataSurround.getSurroundDoubleArcInnerRadius(this._sectionNumber);
        surroundOuterRadius = dataSurround.getSurroundDoubleArcOuterRadius(this._sectionNumber);

        ElementPoint startPt = dataSurround.getElementPointStart(this._sectionNumber);
        ElementPoint endPt = dataSurround.getElementPointEnd(this._sectionNumber);
        if (startPt != null && endPt != null)
        {
            startPtX = startPt.X();
            startPtY = startPt.Y();
            endPtX = endPt.X();
            endPtY = endPt.Y();
        }

        this.setSurroundHeight(surroundHeight);
        this.setSurroundInnerRadius(surroundInnerRadius);
        this.setSurroundOuterRadius(surroundOuterRadius);
        this.setStartPtX(startPtX);
        this.setStartPtY(startPtY);
        this.setEndPtX(endPtX);
        this.setEndPtY(endPtY);

    }



}
