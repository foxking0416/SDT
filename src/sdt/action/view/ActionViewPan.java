package sdt.action.view;

import sdt.action.ActionMain;
import sdt.define.DefineSystemConstant;
import java.awt.event.ActionEvent;
import sdt.framework.SDT_System;
import javax.swing.JCheckBoxMenuItem;

public class ActionViewPan extends ActionMain
{
    public ActionViewPan(SDT_System system)
    {
        super(system, "Pan");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("View --> Pan");

        JCheckBoxMenuItem itemPan = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxPan");
        JCheckBoxMenuItem itemRotate = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxRotate");

        itemRotate.setSelected(false);


        if(itemPan.isSelected())
        {
            this._system.getModeler().setControlType(DefineSystemConstant.MOUSE_TRANSLATE);
        }
        else
        {
            this._system.getModeler().setControlType(DefineSystemConstant.MOUSE_NONE);
        }

    }




}
