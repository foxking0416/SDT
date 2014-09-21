package sdt.dialog;

import java.awt.event.*;

import sdt.data.*;
import sdt.framework.*;
import sdt.geometry.element.*;


public class DiaDimensionTopPlate extends DiaDimensionMagnet
{
    public DiaDimensionTopPlate(SDT_System system, int sectionNumber)
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
        str = "TopPlate " + str;
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
            DataTopPlate dataTopPlate = (DataTopPlate)this._system.getDataManager().getCurrentDataSolid();
            DataMagnet  dataMagnet = (DataMagnet)this._system.getDataManager().getDataMagnet();

            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }

            dataTopPlate.setElementPointStartCoordinate(this._sectionNumber, this._startPtX, this._startPtY);
            dataTopPlate.setElementPointEndCoordinate(this._sectionNumber, this._endPtX, this._endPtY);
            double xy[] = dataTopPlate.getElementPointStart(this._sectionNumber).getCoordinate();
            dataMagnet.setElementPointStartCoordinate(this._sectionNumber, xy[0], this._endPtY);

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

       DataTopPlate dataTopPlate = (DataTopPlate)this._system.getDataManager().getCurrentDataSolid();
       ElementPoint startPt = dataTopPlate.getElementPointStart(this._sectionNumber);
       ElementPoint endPt = dataTopPlate.getElementPointEnd(this._sectionNumber);

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

}
