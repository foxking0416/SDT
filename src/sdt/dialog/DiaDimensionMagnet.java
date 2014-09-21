package sdt.dialog;

import java.awt.event.*;

import javax.swing.*;

import sdt.data.*;
import sdt.framework.*;
import sdt.geometry.element.*;


public class DiaDimensionMagnet extends DiaDimensionTransition
{
    protected JLabel _jLabelX;
    protected JLabel _jLabelY;
    protected JLabel _jLabelStartPt;
    protected JLabel _jLabelEndPt;
    protected JPanel _jPanelCoordinate;



    public DiaDimensionMagnet(SDT_System system, int sectionNumber)
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
        str = "Magnet " + str;
        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 280);
        this.setLocation();
        this.setVisible(true);
    }

    protected void addListener()
    {
        this._jTextStartPtX.addKeyListener(this);
        this._jTextStartPtY.addKeyListener(this);
        this._jTextEndPtX.addKeyListener(this);
        this._jTextEndPtY.addKeyListener(this);

    }


    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == this._jButtonOk)
        {
            DataMagnet dataMagnet = (DataMagnet)this._system.getDataManager().getCurrentDataSolid();
            DataYokeBase dataYokeBase = (DataYokeBase)this._system.getDataManager().getDataYokeBase();

            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }

            dataMagnet.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataMagnet.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);
            double xy[] = dataYokeBase.getElementPointStart(this._sectionNumber).getCoordinate();
            dataYokeBase.setElementPointStartCoordinate(this._sectionNumber, xy[0], this._endPtY);


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


    protected void readDiaData()
    {
        double startPtX = 0;
        double startPtY = 0;
        double endPtX = 0;
        double endPtY = 0;

        DataMagnet dataMagnet = (DataMagnet)this._system.getDataManager().getCurrentDataSolid();
        ElementPoint startPt = dataMagnet.getElementPointStart(this._sectionNumber);
        ElementPoint endPt = dataMagnet.getElementPointEnd(this._sectionNumber);

        if (startPt != null || endPt != null)
        {
            startPtX = startPt.X();
            startPtY = startPt.Y();
            endPtX = endPt.X();
            endPtY = endPt.Y();
        }

        this.setStartPtX(startPtX);
        this.setStartPtY(startPtY);
        this.setEndPtX(endPtX);
        this.setEndPtY(endPtY);

        this._startPtX_Old = startPtX;
        this._startPtY_Old = startPtY;
        this._endPtX_Old   = endPtX;
        this._endPtY_Old   = endPtY;

    }

    protected void createComponent()
    {
        this._tabbed = new JTabbedPane();

        this._jPanelCoordinate = new JPanel(null);

        this._iconDimension = new ImageIcon(this.getClass().getResource("/sdt/icon/Dimension/DimensionRectangle.png"));
        this._jLabelDimension = new JLabel(this._iconDimension);

        this._jLabelX       = new JLabel("X");
        this._jLabelY       = new JLabel("Y");
        this._jLabelStartPt = new JLabel("Start Point : ");
        this._jLabelEndPt   = new JLabel("End Point : ");


        this._jTextStartPtX = new JTextField("");
        this._jTextStartPtY = new JTextField("");
        this._jTextEndPtX = new JTextField("");
        this._jTextEndPtY = new JTextField("");

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
    protected void setToOldValue()
    {
        this.setStartPtX(this._startPtX_Old);
        this.setStartPtY(this._startPtY_Old);
        this.setEndPtX(this._endPtX_Old);
        this.setEndPtY(this._endPtY_Old);
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
            if (this._startPtX < 0 || this._endPtX < 0 )
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




}

