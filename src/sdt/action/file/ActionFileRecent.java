package sdt.action.file;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import sdt.framework.SDT_Config;


public class ActionFileRecent  extends ActionMain
{
    public  ActionFileRecent(SDT_System system)
    {
        super(system, "recent_files");
    }


    public void actionPerformed(ActionEvent e)
    {
        String filePathName;
        filePathName = ((JMenuItem) e.getSource()).getText();
        _system.openFile(filePathName);


        SDT_Config config = this._system.getConfig();
        config.setRecentFile(filePathName);
        config.save();

    }
}
