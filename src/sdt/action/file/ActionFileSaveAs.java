package sdt.action.file;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ActionFileSaveAs  extends ActionMain
{
    public ActionFileSaveAs(SDT_System system)
    {
             super(system, "SaveAs");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Save File");

        this._system.SaveAsFile();
    }
}
