package sdt.action.view;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import sdt.define.DefineSystemConstant;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

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
public class ActionViewShading extends ActionMain
{
    public ActionViewShading(SDT_System system)
    {
        super(system, "Shading");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("View --> Shading");

        JCheckBoxMenuItem itemShading = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxShading");
        JCheckBoxMenuItem itemShadingWithWireFrame = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxShadingWithEdges");
        JCheckBoxMenuItem itemWireFrame = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxWireFrame");

        //if(itemShading.isSelected() != true)
        this._system.getModeler().getPanel3D().setShowType(DefineSystemConstant.VIEW_SHADING);

        itemShading.setSelected(true);
        itemShadingWithWireFrame.setSelected(false);
        itemWireFrame.setSelected(false);



    }

}
