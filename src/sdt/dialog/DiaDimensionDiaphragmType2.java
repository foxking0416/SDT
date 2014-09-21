package sdt.dialog;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Box;
import sdt.framework.SDT_System;
import sdt.data.DataDiaphragm;
import sdt.geometry.element.ElementPoint;
import java.awt.event.ActionEvent;
import sdt.data.DataTransition;
import sdt.data.DataCap;
import sdt.data.DataSurround;
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
public class DiaDimensionDiaphragmType2 extends DiaDimensionTransition
{

    protected JLabel _jLabelRidgePt;
    protected JLabel _jLabelRadius;

    protected JTextField _jTextRidgePtX;
    protected JTextField _jTextRidgePtY;
    protected JTextField _jTextRadius;


    protected double _ridgePtX;
    protected double _ridgePtY;
    protected double _radius;
    protected double _ridgePtX_Old;
    protected double _ridgePtY_Old;
    protected double _radius_Old;


    public DiaDimensionDiaphragmType2(SDT_System system, int sectionNumber)
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
        str = "Diaphragm " + str;


        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 370);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        super.createComponent();
        this._iconDimension = new ImageIcon(this.getClass().getResource("/sdt/icon/Dimension/DimensionDiaphragmType2.png"));
        this._jLabelDimension = new JLabel(_iconDimension);

        this._jLabelRidgePt           = new JLabel("Ridge Point:");
        this._jLabelRadius           = new JLabel("Radius:");


        this._jTextRidgePtX = new JTextField("");
        this._jTextRidgePtY = new JTextField("");
        this._jTextRadius = new JTextField("");
    }

    protected void setSizeComponent()
    {
        super.setSizeComponent();

        this._jLabelRidgePt.setBounds(10, 200 ,80, 25);
        this._jTextRidgePtX.setBounds(90, 200 ,80, 25);
        this._jTextRidgePtY.setBounds(180, 200 ,80, 25);

        this._jLabelRadius.setBounds(10, 250 ,80, 25);
        this._jTextRadius.setBounds(90, 250 ,80, 25);
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

        this._jPanelCoordinate.add(_jLabelRidgePt);
        this._jPanelCoordinate.add(_jTextRidgePtX);
        this._jPanelCoordinate.add(_jTextRidgePtY);

        this._jPanelCoordinate.add(_jLabelRadius);
        this._jPanelCoordinate.add(_jTextRadius);


        this._tabbed.add(this._jPanelCoordinate, "Point");


        this._contentPane.setLayout(null);
        this._tabbed.setBounds(20, 35, 280, 320);
        this.add(_tabbed);
        this._jButtonOk.setBounds(150, 335, 70, 25);
        this._jButtonCancel.setBounds(230, 335, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);

    }

    protected void addListener()
    {
        super.addListener();
        this._jTextRidgePtX.addKeyListener(this);
        this._jTextRidgePtY.addKeyListener(this);
        this._jTextRadius.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this._jButtonOk)
        {
            DataDiaphragm dataDiaphragm = (DataDiaphragm)this._system.getDataManager().getCurrentDataShell();
            DataSurround dataSurround = (DataSurround)this._system.getDataManager().getDataSurround();
            this._startPtX_Old = dataDiaphragm.getElementPointStart(this._sectionNumber).X();
            this._startPtY_Old = dataDiaphragm.getElementPointStart(this._sectionNumber).Y();
            this._endPtX_Old = dataDiaphragm.getElementPointEnd(this._sectionNumber).X();
            this._endPtY_Old = dataDiaphragm.getElementPointEnd(this._sectionNumber).Y();
            this._ridgePtX_Old = dataDiaphragm.getDiaphragmRidgePtX(this._sectionNumber);
            this._ridgePtY_Old = dataDiaphragm.getDiaphragmRidgePtY(this._sectionNumber);
            this._radius_Old = dataDiaphragm.getDiaphragmRadius(this._sectionNumber);

            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }
            boolean isOutOfConstraint = false;

            dataDiaphragm.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataDiaphragm.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);
            dataDiaphragm.setRidgePtAndRadius(this._sectionNumber, this._ridgePtX, this._ridgePtY, this._radius);

            isOutOfConstraint = dataSurround.getElement(this._sectionNumber).setBoundary();
            this._system.getModeler().getPanel2D().GetDrawPanel().upDate();
            if (isOutOfConstraint == true)
            {
                String diaStr = _system.GetInterfaceString("dia_MessageStrInputOutOfConstraint");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);

                dataDiaphragm.setElementPointStartCoordinate(this._sectionNumber, this._startPtX_Old, this._startPtY_Old);
                dataDiaphragm.setElementPointEndCoordinate(this._sectionNumber, this._endPtX_Old, this._endPtY_Old);
                dataDiaphragm.setRidgePtAndRadius(this._sectionNumber, this._ridgePtX_Old, this._ridgePtY_Old, this._radius_Old);

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
            this._ridgePtX = Double.parseDouble(this._jTextRidgePtX.getText());
            this._ridgePtY = Double.parseDouble(this._jTextRidgePtY.getText());
            this._radius = Double.parseDouble(this._jTextRadius.getText());

            if (this._startPtX < 0 || this._endPtX < 0 || this._radius < 0)
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

    protected void readDiaData()
    {
        double startPtX = 0;
        double startPtY = 0;
        double endPtX = 0;
        double endPtY = 0;
        double middlePtX = 0;
        double middlePtY = 0;
        double radius = 0;

        DataDiaphragm dataDiaphragm = (DataDiaphragm)this._system.getDataManager().getCurrentDataShell();
        ElementPoint startPt = dataDiaphragm.getElementPointStart(this._sectionNumber);
        ElementPoint endPt = dataDiaphragm.getElementPointEnd(this._sectionNumber);

        if (startPt != null || endPt != null)
        {
            startPtX = startPt.X();
            startPtY = startPt.Y();
            endPtX = endPt.X();
            endPtY = endPt.Y();
            middlePtX = dataDiaphragm.getDiaphragmRidgePtX(this._sectionNumber);
            middlePtY = dataDiaphragm.getDiaphragmRidgePtY(this._sectionNumber);
            radius = dataDiaphragm.getDiaphragmRadius(this._sectionNumber);
        }

        this.setStartPtX(startPtX);
        this.setStartPtY(startPtY);
        this.setEndPtX(endPtX);
        this.setEndPtY(endPtY);
        this.setRidgePtX(middlePtX);
        this.setRidgePtY(middlePtY);
        this.setRadius(radius);

    }

    protected void setToOldValue()
    {
        this.setStartPtX(this._startPtX_Old);
        this.setStartPtY(this._startPtY_Old);
        this.setEndPtX(this._endPtX_Old);
        this.setEndPtY(this._endPtY_Old);
        this.setRidgePtX(this._ridgePtX_Old);
        this.setRidgePtY(this._ridgePtY_Old);
        this.setRadius(this._radius_Old);
    }




    private void setRidgePtX(double mPtX)
    {
        this._jTextRidgePtX.setText("" + mPtX);
        this.setTextfieldFormat(this._jTextRidgePtX);
    }

    private void setRidgePtY(double mPtY)
    {
        this._jTextRidgePtY.setText("" + mPtY);
        this.setTextfieldFormat(this._jTextRidgePtY);
    }

    private void setRadius(double radius)
    {
        this._jTextRadius.setText("" + radius);
        this.setTextfieldFormat(this._jTextRadius);
    }
}
