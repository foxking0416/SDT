package sdt.action.computation;

import java.awt.event.*;

import sdt.action.*;
import sdt.dialog.*;
import sdt.framework.*;

public class ActionComputationStepFrame extends ActionMain
{
    public ActionComputationStepFrame(SDT_System system)
    {
         super(system, "StepFrame");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Step/Frame");

        new DiaResultStepFrame(this._system);
    }
}
