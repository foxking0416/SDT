package sdt.action.file;

import java.awt.event.*;
import sdt.framework.SDT_System;
import sdt.action.ActionMain;


public class ActionFileExit extends ActionMain
{
    public ActionFileExit(SDT_System system)
    {
        super(system, "Exit");
    }

    public void actionPerformed(ActionEvent e)
    {
        System.exit(0);
    }
}
