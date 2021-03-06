package sdt.dialog;

import java.awt.event.*;

import sdt.data.*;
import sdt.framework.*;
import sdt.geometry.element.*;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class DiaDimensionYokeStage1 extends DiaDimensionYokeBase
{
    public DiaDimensionYokeStage1(SDT_System system, int sectionNumber)
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
        str = "Yoke Stage1 " + str;
        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 320);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        super.createComponent();
        this._jTextStartPtX.setEditable(true);
    }



    public void actionPerformed(ActionEvent e)
    {
        //super.actionPerformed(e);
        if (e.getSource() == this._jButtonOk)
        {
            DataYokeStage1 dataYokeStage1 = (DataYokeStage1)this._system.getDataManager().getCurrentDataSolid();
            DataYokeStage2  dataYokeStage2 = (DataYokeStage2)this._system.getDataManager().getDataYokeStage2();
            DataYokeBase  dataYokeBase = (DataYokeBase)this._system.getDataManager().getDataYokeBase();

            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }

            dataYokeStage1.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataYokeStage1.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);
            double startXY[] = dataYokeStage2.getElementPointStart(this._sectionNumber).getCoordinate();
            double endXY[] = dataYokeStage2.getElementPointEnd(this._sectionNumber).getCoordinate();
            dataYokeStage2.setElementPointStartCoordinate(this._sectionNumber, this._middlePtX, startXY[1]);
            dataYokeStage2.setElementPointEndCoordinate(this._sectionNumber, endXY[0], this._middlePtY);
            dataYokeStage1.setElementPointMiddleCoordinate(this._sectionNumber, this._middlePtX, this._middlePtY);
            dataYokeStage1.setElementPointMiddleCoordinate(this._sectionNumber, this._middlePtX, this._middlePtY);

            dataYokeBase.setElementPointMiddleCoordinate(this._sectionNumber, this._startPtX, this._endPtY);

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
           double middlePtX = 0;
           double middlePtY = 0;


           DataYokeStage1 dataYokeStage1 = (DataYokeStage1)this._system.getDataManager().getCurrentDataSolid();
           ElementPoint startPt = dataYokeStage1.getElementPointStart(this._sectionNumber);
           ElementPoint endPt = dataYokeStage1.getElementPointEnd(this._sectionNumber);
           ElementPoint middlePt = dataYokeStage1.getElementPointMiddleUp(this._sectionNumber);


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

}
