package sdt.action.file;

import java.awt.event.*;

import sdt.action.*;
import sdt.framework.*;

public class ActionFileModulePreliminaryDesign extends ActionMain
{
    public ActionFileModulePreliminaryDesign(SDT_System system)
    {
        super(system, "PreliminaryModule");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("PreliminaryModule");
        this._system.ChangeToPreliminaryDesign();
    }

}
