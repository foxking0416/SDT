package sdt.action.popup;

import sdt.tree.SDT_TreeAssembly;
import java.awt.event.ActionEvent;
import sdt.framework.SDT_Modeler;
import javax.swing.JCheckBoxMenuItem;
import sdt.action.ActionMain;
import sdt.framework.SDT_System;

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
public class ActionPopupAirView2D extends ActionMain
{
    public ActionPopupAirView2D(SDT_System system)
    {
        super(system, "ChkBoxAir2D");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxAir2D");

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
        boolean isPanel2D = this._system.getIsPanelShow2Dor3D();
        this._system.changeTo2D();
        /*if (!isPanel2D)
        {
            this._system.getModeler().getPanel2D().GetDrawPanel().showAllDataXY();
        }
        else
        {*/
            this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(this.TYPE_CONE, this.XZView);
            this._system.getModeler().getPanel2D().GetDrawPanel().setScaleFit();
        //}

    }
}
