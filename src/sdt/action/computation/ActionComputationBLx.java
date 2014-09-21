package sdt.action.computation;

import java.awt.event.*;

import sdt.action.*;
import sdt.dialog.*;
import sdt.framework.*;

public class ActionComputationBLx extends ActionMain
{
    public ActionComputationBLx(SDT_System system)
    {
         super(system, "BLxCurve");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("BLx Curve");


        double[][] dataXY = this._system.getDataManager().getDatContentBL();
        if (dataXY != null)
            new DiaPlotLinear(this._system, dataXY, "BL-X Curve");
    }
}
