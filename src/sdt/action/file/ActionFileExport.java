package sdt.action.file;

import java.awt.event.*;

import sdt.action.*;
import sdt.framework.*;


public class ActionFileExport extends ActionMain
{
    public ActionFileExport(SDT_System system)
    {
         super(system, "Export");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Export File");

        this._system.exportInpFile();


    }

}
