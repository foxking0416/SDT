package sdt.action.computation;

import java.awt.event.*;

import sdt.action.*;
import sdt.dialog.*;
import sdt.framework.*;

public class ActionComputationEMMA extends ActionMain
{
    public ActionComputationEMMA(SDT_System system)
    {
         super(system, "EMMA");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("EMMA");

        //new DiaPlotLog(this._system,null);
        new DiaGraph(this._system);
        //new DiaEMMA(this._system);
    }
}
