package sdt.action.tool;

import java.awt.event.*;

import sdt.action.*;
import sdt.define.*;
import sdt.framework.*;
import javax.swing.JCheckBoxMenuItem;
import sdt.dialog.DiaToolOption;

public class ActionToolOption  extends ActionMain
{
    public ActionToolOption(SDT_System system)
    {
        super(system, "Options");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Options");
        DiaToolOption dia = new DiaToolOption(this._system);

    }


}
