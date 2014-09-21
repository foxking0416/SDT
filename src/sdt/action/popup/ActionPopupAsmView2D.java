package sdt.action.popup;

import sdt.tree.SDT_TreeAssembly;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import sdt.framework.SDT_Modeler;
import sdt.action.ActionMain;

public class ActionPopupAsmView2D extends ActionMain
{
    public ActionPopupAsmView2D(SDT_System system)
    {
        super(system, "ChkBox2D");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBox2D");

        JCheckBoxMenuItem item = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBox2D");
        JCheckBoxMenuItem item2 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBox3D");

        SDT_Modeler modeler = this._system.getModeler();
        SDT_TreeAssembly tree = modeler.GetAssemTree();

        tree.setAsmView2D(true);

        item.setSelected(true);
        if (item2.isSelected())
        {
            item2.setSelected(false);
        }
        boolean isPanel2D  = this._system.getIsPanelShow2Dor3D();
        this._system.changeTo2D();

        this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(this.TYPE_CONE, this.XZView);
        this._system.getModeler().getPanel2D().GetDrawPanel().setScaleFit();

    }
}
