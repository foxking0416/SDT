package sdt.action.view;

import sdt.define.DefineSystemConstant;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import sdt.action.ActionMain;

public class ActionViewRotate extends ActionMain
{
    public ActionViewRotate(SDT_System system)
    {
        super(system, "Rotate");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("View --> Rotate");

        JCheckBoxMenuItem itemPan = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxPan");
        JCheckBoxMenuItem itemRotate = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxRotate");

        itemPan.setSelected(false);

        if (itemRotate.isSelected())
        {
            this._system.getModeler().setControlType(DefineSystemConstant.MOUSE_ROTATE);
        }
        else
        {
            this._system.getModeler().setControlType(DefineSystemConstant.MOUSE_NONE);
        }


    }
}
