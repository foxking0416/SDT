package sdt.action.computation;

import java.awt.event.*;

import sdt.action.*;
import sdt.dialog.*;
import sdt.framework.*;
import java.util.ArrayList;

public class ActionComputationKx extends ActionMain
{
    public ActionComputationKx(SDT_System system)
    {
         super(system, "KxCurve");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Kx Curve");


        String strMsg = this._system.GetInterfaceString("dia_MessageStrNoFEMResult");

        ArrayList array = this._system.getDataManager().getFrequencyArray();

        double[][] dataXY = this._system.getDataManager().getDatContentStatic();
        if (dataXY != null && dataXY.length!= 0 &&array!= null &&  array.size() != 0 )
        {
         // new DiaPlotLinear(this._system, dataXY, "Deflection-X Curve");
            double[][] dataXY2 = new double[dataXY.length - 1][2];
            for (int i = 0; i < dataXY2.length; i++)
            {
                dataXY2[i][0] = (dataXY[i][0] + dataXY[i + 1][0]) / 2.0;
                dataXY2[i][1] = (dataXY[i + 1][1] - dataXY[i][1]) / (dataXY[i + 1][0] - dataXY[i][0]) * 9.8;
            }
            new DiaPlotLinear(this._system, dataXY2, "K - X Curve");
        }
        else
        {
            DiaMessage dia = new DiaMessage(this._system, strMsg);
        }
    }
}
