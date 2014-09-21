package sdt.action.popup;


import java.awt.event.*;
import sdt.framework.SDT_System;
import sdt.action.ActionMain;
import sdt.framework.SDT_Modeler;
import javax.swing.JCheckBoxMenuItem;
import sdt.tree.SDT_TreeAssembly;
import sdt.define.DefineTreeNode;


public class ActionPopupSectionRunway extends ActionMain
{
    public ActionPopupSectionRunway(SDT_System system)
    {
        super(system, "ChkBoxSectionRunway");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxSectionRunway");

        JCheckBoxMenuItem itemRunway = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxSectionRunway");
        JCheckBoxMenuItem itemRound = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxSectionRound");
        SDT_Modeler modeler = this._system.getModeler();

        SDT_TreeAssembly tree = modeler.GetAssemTree();

        String nodeName = (String)tree.getSelectedNode().getNodeName();
        if (nodeName.equals(DefineTreeNode.PRT_Surround) || nodeName.equals(DefineTreeNode.PRT_Diaphragm))
        {
           this._system.getDataManager().setSurroundDiaphragmRunway(true);
           tree.setShapeSurroundDiaphgram();
        }

        /***
         * if the CAP and VCM  are runway,  then the Surround swithed to runway at the same time
         */

        else if (nodeName.equals(DefineTreeNode.PRT_Cap)       ||nodeName.equals(DefineTreeNode.PRT_Transition) ||
                 nodeName.equals(DefineTreeNode.PRT_Magnet)    || nodeName.equals(DefineTreeNode.PRT_TopPlate) ||
                 nodeName.equals(DefineTreeNode.PRT_MagnetTop) || nodeName.equals(DefineTreeNode.PRT_MagnetOuter) ||
                 nodeName.equals(DefineTreeNode.PRT_YokeStage1)    || nodeName.equals(DefineTreeNode.PRT_YokeStage2) ||
                 nodeName.equals(DefineTreeNode.PRT_Coil)
                )
        {
            this._system.getDataManager().setSurroundDiaphragmRunway(true);
            tree.setShapeSurroundDiaphgram();

            this._system.getDataManager().setCapTransitionRunway(true);
            tree.setShapeCapTransition();

            this._system.getDataManager().setVCMRunway(true);
            tree.setShapeVCM();
        }

        itemRunway.setSelected(true);
        if (itemRound.isSelected())
            itemRound.setSelected(false);


    }
}
