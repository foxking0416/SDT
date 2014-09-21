package sdt.action.file;

import java.awt.event.*;

import sdt.action.*;
import sdt.framework.*;


public class ActionFileExportInp extends ActionMain
{
    public ActionFileExportInp(SDT_System system)
    {
         super(system, "ExportInp");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Export File For ABAQUS");

        this._system.exportInpFile();


    }

}
