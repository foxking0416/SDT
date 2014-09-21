package sdt.dialog;

import javax.swing.*;

import sdt.framework.*;
import sdt.geometry.element.ElementPoint;
import sdt.define.DefineSystemConstant;
import java.awt.event.ActionEvent;
import sdt.data.DataCoil;

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
public class DiaDimensionCoilType1 extends DiaMain
{
    private JTabbedPane _tabbed;

    private Box _boxTabContainer1;
    private Box _boxTabContainer2;
    private Box _boxTabContainer3;
    private Box _boxLength;
    private Box _boxEndPtY;
    private Box _boxThickness;

    private JLabel _jLabelLength;
    private JLabel _jLabelEndPtY;
    private JLabel _jLabelThickness;

    private JTextField _jTextLength;
    private JTextField _jTextEndPtY;
    private JTextField _jTextThickness;

    private double _length;
    private double _endPtY;
    private double _thickness;
    private double _length_Old;
    private double _endPtY_Old;
    private double _thickness_Old;
    private double _startPtY;
    private double _startPtX;

    private int    _sectionNumber = 0;


    public DiaDimensionCoilType1(SDT_System system, int sectionNumber)
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
        str = "Coil " + str;



        this.readDiaData();
        this.setTitle(str);
        this.setSize(300, 130);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        this._tabbed = new JTabbedPane();

        this._boxTabContainer1 = Box.createVerticalBox();
        this._boxTabContainer2 = Box.createVerticalBox();
        this._boxTabContainer3 = Box.createVerticalBox();
        this._boxLength = Box.createHorizontalBox();
        this._boxEndPtY = Box.createHorizontalBox();
        this._boxThickness = Box.createHorizontalBox();

        this._jLabelLength = new JLabel("Length : ");
        this._jLabelEndPtY = new JLabel("End Point Y : ");
        this._jLabelThickness = new JLabel("Thickness : ");

        this._jTextLength = new JTextField("");
        this._jTextEndPtY = new JTextField("");
        this._jTextThickness = new JTextField("");

    }

    protected void setSizeComponent()
    {
        this._jLabelLength.setMaximumSize(this._dimensionFieldLong);
        this._jLabelEndPtY.setMaximumSize(this._dimensionFieldLong);
        this._jLabelThickness.setMaximumSize(this._dimensionFieldLong);
        this._jTextLength.setMaximumSize(this._dimensionFieldLong);
        this._jTextEndPtY.setMaximumSize(this._dimensionFieldLong);
        this._jTextThickness.setMaximumSize(this._dimensionFieldLong);


    }

    protected void addComponent()
    {
        this._boxTabContainer1.add(Box.createVerticalStrut(10));
        this._boxLength.add(this._jLabelLength);
        this._boxLength.add(this._jTextLength);
        this._boxTabContainer1.add(this._boxLength);

        this._boxTabContainer2.add(Box.createVerticalStrut(10));
        this._boxEndPtY.add(this._jLabelEndPtY);
        this._boxEndPtY.add(this._jTextEndPtY);
        this._boxTabContainer2.add(this._boxEndPtY);

        this._boxTabContainer3.add(Box.createVerticalStrut(10));
        this._boxThickness.add(this._jLabelThickness);
        this._boxThickness.add(this._jTextThickness);
        this._boxTabContainer3.add(this._boxThickness);

        this._tabbed.add(this._boxTabContainer1, "Length");
        this._tabbed.add(this._boxTabContainer2, "End Point Y");
        this._tabbed.add(this._boxTabContainer3, "Thickness");


        this._contentPane.setLayout(null);
        this._tabbed.setBounds(20, 35, 260, 80);
        this.add(_tabbed);
        this._jButtonOk.setBounds(110, 90, 70, 25);
        this._jButtonCancel.setBounds(200, 90, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);

    }

    protected void addListener()
    {
        this._jTextLength.addKeyListener(this);
        this._jTextEndPtY.addKeyListener(this);
        this._jLabelThickness.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if (e.getSource() == this._jButtonOk)
        {
            DataCoil dataCoil = (DataCoil)this._system.getDataManager().getDataCoil();
            this._startPtX = dataCoil.getElementPointStart(this._sectionNumber).X();
            this._startPtY = dataCoil.getElementPointStart(DefineSystemConstant.XZView).Y();

            this._endPtY_Old = dataCoil.getElementPointEnd(DefineSystemConstant.XZView).Y();
            this._length_Old = Math.abs(this._startPtY - this._endPtY_Old);

            this._thickness_Old = dataCoil.getThickness();




            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }

            if (this._tabbed.getSelectedIndex() == 0 ||
                this._tabbed.getSelectedIndex() == 2)
                this._endPtY = this._startPtY - this._length;
            else if (this._tabbed.getSelectedIndex() == 1)
                this._endPtY = Double.parseDouble(this._jTextEndPtY.getText());

            this._thickness = Double.parseDouble(this._jTextThickness.getText());

            dataCoil.setElementPointEndCoordinate(DefineSystemConstant.XZView, this._startPtX, this._endPtY);
            dataCoil.setThickness(_thickness);

            this._system.getModeler().getPanel2D().GetDrawPanel().upDate();
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
        boolean bool = true;
        try
        {
            if (this._tabbed.getSelectedIndex() == 0)
            {

                this._length = Double.parseDouble(this._jTextLength.getText());

                if (this._length < 0)
                    bool = false;

            }
            else
            {
                DataCoil dataCoil = (DataCoil)this._system.getDataManager().getDataCoil();
                this._startPtY = dataCoil.getElementPointStart(DefineSystemConstant.XZView).Y();
                this._endPtY = Double.parseDouble(this._jTextEndPtY.getText());

                if (this._endPtY > this._startPtY - 0.1)
                    bool = false;

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

    private void setLength(double length)
    {
        this._jTextLength.setText("" + length);
        this.setTextfieldFormat(this._jTextLength);
    }

    private void setEndPtY(double ePtY)
    {
        this._jTextEndPtY.setText("" + ePtY);
        this.setTextfieldFormat(this._jTextEndPtY);
    }

    private void setThickness(double thick)
    {
        this._jTextThickness.setText("" + thick);
        this.setTextfieldFormat(this._jTextThickness);
    }

    private void setToOldValue()
    {
        this.setLength(this._length_Old);
        this.setEndPtY(this._endPtY_Old);
        this.setThickness(this._thickness_Old);
    }


    protected void readDiaData()
    {
        double length = 0;
        double endPtY = 0;
        double thickness = 0;

        DataCoil dataCoil = (DataCoil)this._system.getDataManager().getDataCoil();
        ElementPoint startPt = dataCoil.getElementPointStart(DefineSystemConstant.XZView);
        ElementPoint endPt = dataCoil.getElementPointEnd(DefineSystemConstant.XZView);
        thickness =dataCoil.getThickness();
        if (startPt != null || endPt != null)
        {
            length = Math.abs(startPt.Y() - endPt.Y());

            endPtY = endPt.Y();

        }

        this.setLength(length);
        this.setEndPtY(endPtY);
        this.setThickness(thickness);

    }
}
