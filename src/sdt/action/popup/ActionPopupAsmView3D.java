package sdt.action.popup;

import sdt.tree.SDT_TreeAssembly;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import sdt.framework.SDT_Modeler;
import sdt.action.ActionMain;

public class ActionPopupAsmView3D extends ActionMain
{
    public ActionPopupAsmView3D(SDT_System system)
    {
        super(system, "ChkBox3D");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBox3D");

        JCheckBoxMenuItem item = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBox2D");
        JCheckBoxMenuItem item2 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBox3D");

        SDT_Modeler modeler = this._system.getModeler();
        SDT_TreeAssembly tree = modeler.GetAssemTree();

        tree.setAsmView2D(false);

        item2.setSelected(true);
        if (item.isSelected())
            item.setSelected(false);

        this._system.changeTo3D();

       // For Deformed Test
        //this._system.getDataManager().getDatContentTest();
        //this._system.changeTo3DDeformed(1);
    }
}
