package sdt.action.file;

import java.awt.event.*;

import sdt.action.*;
import sdt.framework.*;


public class ActionFileExportMac extends ActionMain
{
    public ActionFileExportMac(SDT_System system)
    {
         super(system, "ExportMac");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Export File For ANSYS");

        this._system.exportMacFile();


    }

}
