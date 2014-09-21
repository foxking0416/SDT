package sdt.action.popup;

import java.awt.event.ActionEvent;
import sdt.framework.SDT_System;
import sdt.dialog.DiaMaterial;
import sdt.action.ActionMain;

public class ActionPopupMaterial extends ActionMain
{
    public ActionPopupMaterial(SDT_System system)
    {
        super(system, "Material");
    }
    public void actionPerformed(ActionEvent e)
     {
         this._system.getFrame().SetStatus("Material");
         DiaMaterial dia = new DiaMaterial(this._system);

     }
}
