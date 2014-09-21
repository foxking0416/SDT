package sdt.dialog;

import sdt.framework.SDT_System;
import sdt.data.DataDiaphragm;
import sdt.geometry.element.ElementPoint;
import java.awt.event.ActionEvent;
import sdt.data.DataSurround;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class DiaDimensionDiaphragmType1 extends DiaDimensionTransition
{

    public DiaDimensionDiaphragmType1(SDT_System system, int sectionNumber)
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
        str = "Diaphragm Type1" + str;

        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 280);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        super.createComponent();
        this._iconDimension = new ImageIcon(this.getClass().getResource("/sdt/icon/Dimension/DimensionDiaphragmType1.png"));
        this._jLabelDimension = new JLabel(_iconDimension);
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

            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }
            boolean isOutOfConstraint = false;

            dataDiaphragm.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataDiaphragm.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);

            isOutOfConstraint = dataSurround.getElement(this._sectionNumber).setBoundary();

            if (isOutOfConstraint == true)
            {
                String diaStr = _system.GetInterfaceString("dia_MessageStrInputOutOfConstraint");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);

                dataDiaphragm.setElementPointStartCoordinate(this._sectionNumber, this._startPtX_Old, this._startPtY_Old);
                dataDiaphragm.setElementPointEndCoordinate(this._sectionNumber, this._endPtX_Old, this._endPtY_Old);
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

            if (this._startPtX < 0 || this._endPtX < 0)
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

        DataDiaphragm dataDiaphragm = (DataDiaphragm)this._system.getDataManager().getCurrentDataShell();
        ElementPoint startPt = dataDiaphragm.getElementPointStart(this._sectionNumber);
        ElementPoint endPt = dataDiaphragm.getElementPointEnd(this._sectionNumber);

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

    }

}
