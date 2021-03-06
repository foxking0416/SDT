package sdt.action.view;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import sdt.define.DefineSystemConstant;
import javax.swing.JCheckBoxMenuItem;

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
public class ActionViewShadingWithEdges extends ActionMain
{
    public ActionViewShadingWithEdges(SDT_System system)
    {
        super(system, "ShadingWithEdges");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("View --> ShadingWithEdges");

        JCheckBoxMenuItem itemShading = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxShading");
        JCheckBoxMenuItem itemShadingWithWireFrame = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxShadingWithEdges");
        JCheckBoxMenuItem itemWireFrame = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxWireFrame");

        //if(itemShadingWithWireFrame.isSelected() != true)
       this._system.getModeler().getPanel3D().setShowType(DefineSystemConstant.VIEW_SHADINGWITHEDGE);

        itemShading.setSelected(false);
        itemShadingWithWireFrame.setSelected(true);
        itemWireFrame.setSelected(false);



    }

}
