package sdt.dialog;

import sdt.framework.SDT_System;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Box;
import sdt.define.DefineSystemConstant;
import java.awt.event.ActionEvent;
import sdt.data.DataCoil;
import sdt.geometry.element.ElementPoint;

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
public class DiaDimensionCoilType2 extends DiaMain
{
    private JTabbedPane _tabbed;

    private Box _boxTabContainer1;
    private Box _boxTabContainer2;
    private Box _boxEndPtY;
    private Box _boxCoilStartY;
    private Box _boxCoilEndY;
    private Box _boxThicknessFormer;
    private Box _boxThickness;


    private JLabel _jLabelEndPtY;
    private JLabel _jLabelCoilStartY;
    private JLabel _jLabelCoilEndY;

    private JLabel _jLabelThicknessFormer;
    private JLabel _jLabelThickness;




    private JTextField _jTextEndPtY;
    private JTextField _jTextCoilStartY;
    private JTextField _jTextCoilEndY;
    private JTextField _jTextThicknessFormer;
    private JTextField _jTextThickness;


    private double _endPtY;
    private double _endPtY_Old;
    private double _coilStartY;
    private double _coilStartY_Old;
    private double _coilEndY;
    private double _coilEndY_Old;

    private double _thicknessFormer ;
    private double _thicknessFormer_Old;
    private double _thickness ;
    private double _thickness_Old;



    private double _startPtY;
    private double _startPtX;

    private int    _sectionNumber = 0;


    public DiaDimensionCoilType2(SDT_System system, int sectionNumber)
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
        this.setSize(300, 200);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        this._tabbed = new JTabbedPane();

        this._boxTabContainer1 = Box.createVerticalBox();
        this._boxTabContainer2 = Box.createVerticalBox();

        this._boxEndPtY = Box.createHorizontalBox();
        this._boxCoilStartY = Box.createHorizontalBox();
        this._boxCoilEndY= Box.createHorizontalBox();
        this._boxThicknessFormer  = Box.createHorizontalBox();
        this._boxThickness = Box.createHorizontalBox();



        this._jLabelEndPtY = new JLabel("Former End Pt Y : ");
        this._jLabelCoilStartY = new JLabel("Coil Start Pt Y : ");
        this._jLabelCoilEndY = new JLabel("Coil End Pt Y : ");
        this._jLabelThicknessFormer = new JLabel("Former Thickness : ");
        this._jLabelThickness = new JLabel("Coil Thickness : ");

        this._jTextEndPtY = new JTextField("");
        this._jTextCoilStartY = new JTextField("");
        this._jTextCoilEndY = new JTextField("");
        this._jTextThicknessFormer = new JTextField("");
        this._jTextThickness = new JTextField("");




    }

    protected void setSizeComponent()
    {
        this._jLabelEndPtY.    setMaximumSize(this._dimensionFieldLong);
        this._jLabelCoilStartY.setMaximumSize(this._dimensionFieldLong);
        this._jLabelCoilEndY.  setMaximumSize(this._dimensionFieldLong);
        this._jLabelThicknessFormer.setMaximumSize(this._dimensionFieldLong);
        this._jLabelThickness.  setMaximumSize(this._dimensionFieldLong);

        this._jTextEndPtY.     setMaximumSize(this._dimensionFieldLong);
        this._jTextCoilStartY. setMaximumSize(this._dimensionFieldLong);
        this._jTextCoilEndY.   setMaximumSize(this._dimensionFieldLong);
        this._jTextThicknessFormer . setMaximumSize(this._dimensionFieldLong);
        this._jTextThickness. setMaximumSize(this._dimensionFieldLong);


    }

    protected void addComponent()
    {
        this._boxTabContainer1.add(Box.createVerticalStrut(10));
        this._boxCoilStartY.add(this._jLabelCoilStartY);
        this._boxCoilStartY.add(this._jTextCoilStartY);
        this._boxTabContainer1.add(this._boxCoilStartY);

        this._boxTabContainer1.add(Box.createVerticalStrut(10));
        this._boxCoilEndY.add(this._jLabelCoilEndY);
        this._boxCoilEndY.add(this._jTextCoilEndY);
        this._boxTabContainer1.add(this._boxCoilEndY);

        this._boxTabContainer1.add(Box.createVerticalStrut(10));
        this._boxEndPtY.add(this._jLabelEndPtY);
        this._boxEndPtY.add(this._jTextEndPtY);
        this._boxTabContainer1.add(this._boxEndPtY);

        this._boxTabContainer2.add(Box.createVerticalStrut(10));
        this._boxThicknessFormer.add(this._jLabelThicknessFormer);
        this._boxThicknessFormer.add(this._jTextThicknessFormer);
        this._boxTabContainer2.add(this._boxThicknessFormer);

        this._boxTabContainer2.add(Box.createVerticalStrut(10));
        this._boxThickness.add(this._jLabelThickness);
        this._boxThickness.add(this._jTextThickness);
        this._boxTabContainer2.add(this._boxThickness);

        this._tabbed.add(this._boxTabContainer1, "Length");
        this._tabbed.add(this._boxTabContainer2, "Thickness");

        this._contentPane.setLayout(null);
        this._tabbed.setBounds(20, 35, 260, 150);
        this.add(_tabbed);
        this._jButtonOk.setBounds(110, 160, 70, 25);
        this._jButtonCancel.setBounds(200, 160, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);

    }

    protected void addListener()
    {
        this._jTextEndPtY.    addKeyListener(this);
        this._jTextCoilStartY.addKeyListener(this);
        this._jTextCoilEndY.  addKeyListener(this);
        this._jTextThicknessFormer .addKeyListener(this);
        this._jTextThickness.  addKeyListener(this);

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
              this._coilStartY_Old = dataCoil.getElementPointCoilStart(DefineSystemConstant.XZView).Y();
              this._coilEndY_Old = dataCoil.getElementPointCoilEnd(DefineSystemConstant.XZView).Y();

              this._thicknessFormer_Old = dataCoil.getFormerThickness();
              this._thickness_Old = dataCoil.getThickness();


              boolean isInputCorrect = this.checkTextfield();
              if (isInputCorrect != true)
              {
                  this.setToOldValue();
                  return;
              }


              dataCoil.setElementPointEndCoordinate(DefineSystemConstant.XZView, this._startPtX, this._endPtY);
              dataCoil.setElementPointCoilStartCoordinate(DefineSystemConstant.XZView, this._startPtX, this._coilStartY);
              dataCoil.setElementPointCoilEndCoordinate(DefineSystemConstant.XZView, this._startPtX, this._coilEndY);
              dataCoil.setThickness(this._thickness);
              dataCoil.setFormerThickness(this._thicknessFormer);


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

        DataCoil dataCoil = (DataCoil)this._system.getDataManager().getDataCoil();
        try
        {
            this._startPtY = dataCoil.getElementPointStart(DefineSystemConstant.XZView).Y();
            this._endPtY = Double.parseDouble(this._jTextEndPtY.getText());
            this._coilStartY = Double.parseDouble(this._jTextCoilStartY.getText());
            this._coilEndY = Double.parseDouble(this._jTextCoilEndY.getText());

            if (this._endPtY > this._coilEndY - 0.1 || this._coilEndY > this._coilStartY - 0.1 || this._coilStartY > this._startPtY - 0.1)
            {
                String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                bool = false;
            }


            this._thickness = Double.parseDouble(this._jTextThickness.getText());
            if (this._thickness < 0)
                bool = false;

            this._thicknessFormer = Double.parseDouble(this._jTextThicknessFormer.getText());
            if (this._thicknessFormer < 0)
                bool = false;


        }
        catch(Exception ex)
        {
            String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
            DiaMessage diaWarning = new DiaMessage(_system, diaStr);
            bool = false;
        }

        return bool;
    }

    private void setCoilStartY(double coilSptY)
    {
        this._jTextCoilStartY.setText("" + coilSptY);
        this.setTextfieldFormat(this._jTextCoilStartY);
    }

    private void setCoilEndY(double coilEptY)
    {
        this._jTextCoilEndY.setText("" + coilEptY);
        this.setTextfieldFormat(this._jTextCoilEndY);
    }

    private void setEndPtY(double ePtY)
    {
        this._jTextEndPtY.setText("" + ePtY);
        this.setTextfieldFormat(this._jTextEndPtY);
    }

    private void setThicknessFormer(double thick)
    {
        this._jTextThicknessFormer.setText("" + thick);
        this.setTextfieldFormat(this._jTextThicknessFormer);
    }

    private void setThickness(double thick)
    {
        this._jTextThickness.setText("" + thick);
        this.setTextfieldFormat(this._jTextThickness);
    }


    private void setToOldValue()
    {
        this.setEndPtY(this._endPtY_Old);
        this.setCoilStartY(this._coilStartY_Old);
        this.setCoilEndY(this._coilEndY_Old);
        this.setThicknessFormer(this._thicknessFormer_Old);
        this.setThickness(this._thickness_Old);
    }

    protected void readDiaData()
    {
        double endPtY = 0;
        double coilStartY = 0;
        double coilEndY = 0;
        double thicknessFormer = 0;
        double thickness = 0;


        DataCoil dataCoil = (DataCoil)this._system.getDataManager().getDataCoil();
        ElementPoint startPt = dataCoil.getElementPointStart(DefineSystemConstant.XZView);
        ElementPoint endPt = dataCoil.getElementPointEnd(DefineSystemConstant.XZView);
        ElementPoint coilStartPt = dataCoil.getElementPointCoilStart(DefineSystemConstant.XZView);
        ElementPoint coilEndPt = dataCoil.getElementPointCoilEnd(DefineSystemConstant.XZView);


        if (endPt != null)
            endPtY = endPt.Y();
        if(coilStartPt != null)
            coilStartY = coilStartPt.Y();
        if(coilEndPt != null)
            coilEndY = coilEndPt.Y();
        thicknessFormer = dataCoil.getFormerThickness();
        thickness = dataCoil.getThickness();


        this.setEndPtY(endPtY);
        this.setCoilStartY(coilStartY);
        this.setCoilEndY(coilEndY);
        this.setThicknessFormer(thicknessFormer);
        this.setThickness(thickness);
    }
}
