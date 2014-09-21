package sdt.action.popup;

import java.awt.event.*;
import sdt.framework.SDT_System;
import sdt.action.ActionMain;
import sdt.framework.SDT_Modeler;
import javax.swing.JCheckBoxMenuItem;
import sdt.tree.SDT_TreeAssembly;
import sdt.define.DefineTreeNode;


public class ActionPopupSectionRound extends ActionMain
{
    public ActionPopupSectionRound(SDT_System system)
    {
        super(system, "ChkBoxSectionRound");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxSectionRound");

        JCheckBoxMenuItem itemRound = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxSectionRound");
        JCheckBoxMenuItem itemRunway = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxSectionRunway");


        SDT_Modeler modeler = this._system.getModeler();
        SDT_TreeAssembly tree = modeler.GetAssemTree();
        /***
         * if Surround is round,  then the CAP and VCM swithed to round at the same time
         */
        String nodeName = (String)tree.getSelectedNode().getNodeName();
        if (nodeName.equals(DefineTreeNode.PRT_Surround) || nodeName.equals(DefineTreeNode.PRT_Diaphragm))
        {
           this._system.getDataManager().setSurroundDiaphragmRunway(false);
           tree.setShapeSurroundDiaphgram();

           this._system.getDataManager().setCapTransitionRunway(false);
           tree.setShapeCapTransition();

           this._system.getDataManager().setVCMRunway(false);
           tree.setShapeVCM();


        }
        else if (nodeName.equals(DefineTreeNode.PRT_Cap)       ||nodeName.equals(DefineTreeNode.PRT_Transition) ||
                 nodeName.equals(DefineTreeNode.PRT_Magnet)    || nodeName.equals(DefineTreeNode.PRT_TopPlate) ||
                 nodeName.equals(DefineTreeNode.PRT_MagnetTop) || nodeName.equals(DefineTreeNode.PRT_MagnetOuter) ||
                 nodeName.equals(DefineTreeNode.PRT_YokeStage1)    || nodeName.equals(DefineTreeNode.PRT_YokeStage2) ||
                 nodeName.equals(DefineTreeNode.PRT_Coil)
                )
        {
            this._system.getDataManager().setCapTransitionRunway(false);
            tree.setShapeCapTransition();

            this._system.getDataManager().setVCMRunway(false);
            tree.setShapeVCM();
        }


        itemRound.setSelected(true);
        if (itemRunway.isSelected())
            itemRunway.setSelected(false);
    }
}
