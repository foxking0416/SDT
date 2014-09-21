package sdt.dialog;

import java.awt.event.*;

import javax.swing.*;

import sdt.data.*;
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
public class DiaDimensionYokeBase extends DiaDimensionMagnet
{

    protected JLabel        _jLabelMiddlePt;
    protected JTextField    _jTextMiddlePtX;
    protected JTextField    _jTextMiddlePtY;
    protected double _middlePtX_Old;
    protected double _middlePtY_Old;
    protected double _middlePtX;
    protected double _middlePtY;



    public DiaDimensionYokeBase(SDT_System system, int sectionNumber)
    {
        super(system, sectionNumber);
    }

    void jbInit() throws Exception
    {
        String str = "";
        if (this._sectionNumber == this.XZView)
            str = this._system.GetInterfaceString("TitleDimensionXZ");
        else if (this._sectionNumber == this.YZView)
            str = this._system.GetInterfaceString("TitleDimensionYZ");
        str = "YokeBase " + str;
        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 320);
        this.setLocation();
        this.setVisible(true);
    }

    protected void addListener()
    {
        super.addListener();

        this._jTextMiddlePtX.addKeyListener(this);
        this._jTextMiddlePtY.addKeyListener(this);

    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this._jButtonOk)
        {
            DataYokeBase dataYokeBase = (DataYokeBase)this._system.getDataManager().getCurrentDataSolid();
            DataYokeStage1  dataYokeStage1 = (DataYokeStage1)this._system.getDataManager().getDataYokeStage1();

            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }

            dataYokeBase.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataYokeBase.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);
            double startXY[] = dataYokeStage1.getElementPointStart(this._sectionNumber).getCoordinate();
            double endXY[] = dataYokeStage1.getElementPointEnd(this._sectionNumber).getCoordinate();
            dataYokeStage1.setElementPointStartCoordinate(this._sectionNumber, this._middlePtX, startXY[1]);
            dataYokeStage1.setElementPointEndCoordinate(this._sectionNumber, endXY[0], this._middlePtY);
            dataYokeBase.setElementPointMiddleCoordinate(this._sectionNumber, this._middlePtX, this._middlePtY);
            this.dispose();

        }
        else if (e.getSource() == this._jButtonCancel)
        {
            this.dispose();
            System.out.println("button Cancel");
        }
        else if(e.getSource()== this._jButtonClose)
        {
            this._jButtonCancel.doClick();
            this.dispose();
        }

    }

    protected void setToOldValue()
    {
        super.setToOldValue();
        this.setMiddlePtX(this._middlePtX_Old);
        this.setMiddlePtY(this._middlePtY_Old);
    }


   protected void readDiaData()
   {
       double startPtX = 0;
       double startPtY = 0;
       double endPtX = 0;
       double endPtY = 0;
       double middlePtX = 0;
       double middlePtY = 0;


       DataYokeBase dataYokeBase = (DataYokeBase)this._system.getDataManager().getCurrentDataSolid();
       ElementPoint startPt = dataYokeBase.getElementPointStart(this._sectionNumber);
       ElementPoint endPt = dataYokeBase.getElementPointEnd(this._sectionNumber);
       ElementPoint middlePt = dataYokeBase.getElementPointMiddleUp(this._sectionNumber);


       if (startPt != null && endPt != null && middlePt != null)
       {
           startPtX = startPt.X();
           startPtY = startPt.Y();
           endPtX = endPt.X();
           endPtY = endPt.Y();
           middlePtX = middlePt.X();
           middlePtY = middlePt.Y();

       }

       this.setStartPtX(startPtX);
       this.setStartPtY(startPtY);
       this.setEndPtX(endPtX);
       this.setEndPtY(endPtY);
       this.setMiddlePtX(middlePtX);
       this.setMiddlePtY(middlePtY);


       this._startPtX_Old = startPtX;
       this._startPtY_Old = startPtY;
       this._endPtX_Old   = endPtX;
       this._endPtY_Old   = endPtY;
       this._middlePtX_Old   = middlePtX;
       this._middlePtX_Old   = middlePtY;

   }
   protected void createComponent()
   {
       super.createComponent();

       this._iconDimension = new ImageIcon(this.getClass().getResource("/sdt/icon/Dimension/DimensionLType.png"));
       this._jLabelDimension = new JLabel(this._iconDimension);

       this._jLabelMiddlePt = new JLabel("Middle Point : ");
       this._jTextMiddlePtX = new JTextField("");
       this._jTextMiddlePtY = new JTextField("");
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

       this._jLabelMiddlePt.setBounds(10, 200, 80, 25);
       this._jTextMiddlePtX.setBounds(90, 200, 80, 25);
       this._jTextMiddlePtY.setBounds(180, 200, 80, 25);

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
       this._jPanelCoordinate.add(_jLabelMiddlePt);
       this._jPanelCoordinate.add(_jTextMiddlePtX);
       this._jPanelCoordinate.add(_jTextMiddlePtY);

       this._tabbed.add(this._jPanelCoordinate, "Point");

       this._contentPane.setLayout(null);
       this._tabbed.setBounds(20, 35, 280, 270);
       this.add(_tabbed);
       this._jButtonOk.setBounds(150, 285, 70, 25);
       this._jButtonCancel.setBounds(230, 285, 70, 25);

       this._contentPane.add(this._jButtonOk);
       this._contentPane.add(this._jButtonCancel);

   }
    protected void setMiddlePtX(double ePtX)
    {
        this._jTextMiddlePtX.setText("" + ePtX);
        this.setTextfieldFormat(this._jTextEndPtX);
    }

    protected void setMiddlePtY(double ePtY)
    {
        this._jTextMiddlePtY.setText("" + ePtY);
        this.setTextfieldFormat(this._jTextEndPtY);
    }
    protected boolean checkTextfield()
   {
       boolean bool = false;
       if (this._tabbed.getSelectedIndex() == 0)
       {
           try
           {
               this._startPtX = Double.parseDouble(this._jTextStartPtX.getText());
               this._startPtY = Double.parseDouble(this._jTextStartPtY.getText());
               this._endPtX = Double.parseDouble(this._jTextEndPtX.getText());
               this._endPtY = Double.parseDouble(this._jTextEndPtY.getText());
               this._middlePtX = Double.parseDouble(this._jTextMiddlePtX.getText());
               this._middlePtY = Double.parseDouble(this._jTextMiddlePtY.getText());

               if(this._startPtX < 0 || this._endPtX < 0|| this._middlePtX < 0)
                   bool = false;

               bool = true;
           }
           catch (Exception e)
           {
               String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
               DiaMessage diaWarning = new DiaMessage(_system, diaStr);
               bool = false;
           }
       }
       else
       {

       }
       return bool;
   }




}
