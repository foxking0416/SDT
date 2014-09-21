package sdt.dialog;

import sdt.framework.SDT_System;
import javax.swing.JTabbedPane;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import sdt.data.DataSurround;
import sdt.geometry.element.ElementPoint;
import sdt.define.DefineSystemConstant;
import java.awt.event.ActionEvent;
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
public class DiaDimensionSurroundSingleArc extends DiaDimensionSurroundDoubleArc
{




    public DiaDimensionSurroundSingleArc(SDT_System system, int sectionNumber)
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
        str = "Surround " + str;

        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 330);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        super.createComponent();
        this._iconDimension = new ImageIcon(this.getClass().getResource("/sdt/icon/Dimension/DimensionSurroundSingleArc.png"));
        this._jLabelDimension = new JLabel(_iconDimension);
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

        this._jLabelSurroundInnerRadius.setBounds(10,210, 80, 25);
        this._jTextSurroundInnerRadius.setBounds(90,210, 80, 25);
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


        this._jPanelCoordinate.add(_jLabelSurroundInnerRadius);
        this._jPanelCoordinate.add(_jTextSurroundInnerRadius);

        this._tabbed.add(this._jPanelCoordinate, "Dimension");

        this._contentPane.setLayout(null);
        this._tabbed.setBounds(20, 35, 280, 280);
        this.add(_tabbed);
        this._jButtonOk.setBounds(150, 295, 70, 25);
        this._jButtonCancel.setBounds(230, 295, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);

    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this._jButtonOk)
        {
            boolean isInputCorrect = false;
            boolean isOutOfConstraint = false;
            boolean isOutOfOtherConstraint = false;

            DataSurround dataSurround = (DataSurround)this._system.getDataManager().getCurrentDataShell();
            this._surroundInnerRadius_Old = dataSurround.getSurroundSingleArcRadius(this._sectionNumber);
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

            dataSurround.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataSurround.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);
            dataSurround.setSurroundSingleArcRadius(this._sectionNumber, this._surroundInnerRadius);

            if (!dataSurround.getIsRunway())
            {
                if(this._sectionNumber == DefineSystemConstant.XZView)
                    dataSurround.setSurroundSingleArcRadius(DefineSystemConstant.YZView, this._surroundInnerRadius);
                else if(this._sectionNumber == DefineSystemConstant.YZView)
                    dataSurround.setSurroundSingleArcRadius(DefineSystemConstant.XZView, this._surroundInnerRadius);
            }


            isOutOfConstraint = dataSurround.getElement(this._sectionNumber).setBoundary();
            isOutOfOtherConstraint = !(dataSurround.setElementToData(this._sectionNumber));

            this._system.getModeler().getPanel2D().GetDrawPanel().upDate();
            if (isOutOfConstraint == true || isOutOfOtherConstraint == true)
            {
                if (isOutOfConstraint == true)
                    System.out.println("out of self constraint");
                if (isOutOfOtherConstraint == true)
                    System.out.println("out of other constraint");
                String diaStr = _system.GetInterfaceString("dia_MessageStrInputOutOfConstraint");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);


                dataSurround.setElementPointStartCoordinate(this._sectionNumber, this._startPtX_Old, this._startPtY_Old);
                dataSurround.setElementPointEndCoordinate(this._sectionNumber, this._endPtX_Old, this._endPtY_Old);
                dataSurround.setSurroundSingleArcRadius(this._sectionNumber, this._surroundInnerRadius_Old);

                if(!dataSurround.getIsRunway())
                 {
                     if (this._sectionNumber == DefineSystemConstant.XZView)
                         dataSurround.setSurroundSingleArcRadius(DefineSystemConstant.YZView, this._surroundInnerRadius_Old);
                     else if (this._sectionNumber == DefineSystemConstant.YZView)
                         dataSurround.setSurroundSingleArcRadius(DefineSystemConstant.XZView, this._surroundInnerRadius_Old);
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
        else if(e.getSource()== this._jButtonClose)
        {
            this._jButtonCancel.doClick();
            this.dispose();
        }


    }




    protected boolean checkTextfield()
    {
        boolean bool = false;
        double tempSurroundRadius;

        try
        {
            tempSurroundRadius = Double.parseDouble(this._jTextSurroundInnerRadius.getText());
            if (tempSurroundRadius < 0)
            {
                String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
                DiaMessage diaWarning = new DiaMessage(_system, diaStr);
                bool = false;
            }
            else
            {
                this._surroundInnerRadius = tempSurroundRadius;
                this._startPtX = Double.parseDouble(this._jTextStartPtX.getText());
                this._startPtY = Double.parseDouble(this._jTextStartPtY.getText());
                this._endPtX = Double.parseDouble(this._jTextEndPtX.getText());
                this._endPtY = Double.parseDouble(this._jTextEndPtY.getText());

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


    protected void readDiaData()
    {
        double startPtX = 0;
        double startPtY = 0;
        double endPtX = 0;
        double endPtY = 0;
        double surroundRadius = 0;


        DataSurround dataSurround = (DataSurround)this._system.getDataManager().getCurrentDataShell();
        surroundRadius = dataSurround.getSurroundSingleArcRadius(this._sectionNumber);

        ElementPoint startPt = dataSurround.getElementPointStart(this._sectionNumber);
        ElementPoint endPt = dataSurround.getElementPointEnd(this._sectionNumber);
        if (startPt != null && endPt != null)
        {
            startPtX = startPt.X();
            startPtY = startPt.Y();
            endPtX = endPt.X();
            endPtY = endPt.Y();
        }

        this.setSurroundInnerRadius(surroundRadius);
        this.setStartPtX(startPtX);
        this.setStartPtY(startPtY);
        this.setEndPtX(endPtX);
        this.setEndPtY(endPtY);

    }
}
