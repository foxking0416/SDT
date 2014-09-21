package sdt.action.popup;

import sdt.tree.SDT_TreeAssembly;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import sdt.framework.SDT_Modeler;
import sdt.action.ActionMain;

public class ActionPopupAirView3D extends ActionMain
{
    public ActionPopupAirView3D(SDT_System system)
    {
        super(system, "ChkBoxAir3D");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxAir3D");

        JCheckBoxMenuItem item = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxAir2D");
        JCheckBoxMenuItem item2 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxAir3D");

        SDT_Modeler modeler = this._system.getModeler();
        SDT_TreeAssembly tree = modeler.GetAssemTree();

        //tree.setAsmView2D(true);

        item.setSelected(true);
        if (item2.isSelected())
        {
            item2.setSelected(false);
        }
        boolean isPanel2D  = this._system.getIsPanelShow2Dor3D();

        tree.setAsmView2D(false);

        item2.setSelected(true);
        if (item.isSelected())
            item.setSelected(false);

        this._system.changeToAir3D();

    }
}
