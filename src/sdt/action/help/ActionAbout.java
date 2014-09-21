package sdt.action.help;

import java.awt.event.*;

import sdt.action.*;
import sdt.dialog.*;
import sdt.framework.*;

public class ActionAbout extends ActionMain
{
    public ActionAbout(SDT_System system)
    {
        super(system, "AboutSDT");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("AboutSDT");
        DiaAbout dia = new DiaAbout(this._system);

    }
}
