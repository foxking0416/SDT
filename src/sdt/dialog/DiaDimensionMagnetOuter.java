package sdt.dialog;

import java.awt.event.*;

import sdt.data.*;
import sdt.framework.*;
import sdt.geometry.element.*;


public class DiaDimensionMagnetOuter extends DiaDimensionMagnet
{
    public DiaDimensionMagnetOuter(SDT_System system, int sectionNumber)
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
        str = "Magnet Outer" + str;
        this.readDiaData();
        this.setTitle(str);
        this.setSize(320, 280);
        this.setLocation();
        this.setVisible(true);
    }


    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this._jButtonOk)
        {
            DataMagnetOuter dataMagnetOuter = (DataMagnetOuter)this._system.getDataManager().getCurrentDataSolid();
            DataYokeStage1 dataYokeStage1 = (DataYokeStage1)this._system.getDataManager().getDataYokeStage1();
            DataYokeStage2 dataYokeStage2 = (DataYokeStage2)this._system.getDataManager().getDataYokeStage2();

            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }


            dataMagnetOuter.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataMagnetOuter.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);
            ElementPoint lfPt = dataMagnetOuter.getElementPointLeftDown(this._sectionNumber);
            double lfXy[] = lfPt.getCoordinate();

            if(lfXy[1] != _endPtY)
                lfPt.setCoordinate(lfXy[1],_endPtY);

            double xy[] = dataYokeStage1.getElementPointStart(this._sectionNumber).getCoordinate();
            dataYokeStage1.setElementPointStartCoordinate(this._sectionNumber, xy[0], this._endPtY);

             double xy2[] = dataYokeStage1.getElementPointMiddleUp(this._sectionNumber).getCoordinate();
             dataYokeStage1.setElementPointMiddleCoordinate(this._sectionNumber, _endPtX, xy2[1]);

             double xy3[] = dataYokeStage2.getElementPointStart(this._sectionNumber).getCoordinate();
             dataYokeStage2.setElementPointStartCoordinate(this._sectionNumber,_endPtX, xy3[1]);



            this.dispose();

        }
        else if (e.getSource() == this._jButtonCancel)
        {
            this.dispose();
            System.out.println("button Cancel");
        }
        else if (e.getSource() == this._jButtonClose)
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

        DataMagnetOuter dataMagnetOuter = (DataMagnetOuter)this._system.getDataManager().getCurrentDataSolid();
        ElementPoint startPt = dataMagnetOuter.getElementPointStart(this._sectionNumber);
        ElementPoint endPt = dataMagnetOuter.getElementPointEnd(this._sectionNumber);

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
        this._endPtX_Old = endPtX;
        this._endPtY_Old = endPtY;

         this._jTextStartPtX.setEditable(true); //lock on yAxis
    }

}
