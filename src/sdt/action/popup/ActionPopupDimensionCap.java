package sdt.action.popup;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import sdt.data.DataCap;
import sdt.dialog.DiaDimensionCap;
import sdt.define.DefineTreeNode;
//import sdt.define.DefineSystemConstant;

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
public class ActionPopupDimensionCap extends ActionMain
{
    public ActionPopupDimensionCap(SDT_System system)
    {
        super(system, "CapDimension");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("CapDimension");

        DiaDimensionCap dia;
        String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeNameLowestLevel();


        if(nodeName != null && nodeName != "")
        {
            if (nodeName.equals(DefineTreeNode.SKT_Section) || nodeName.equals(DefineTreeNode.SKT_Section1))
            {
                dia = new DiaDimensionCap(this._system, this.XZView);
            }
            else //(nodeName.equals(DefineTreeNode.SKT_Section2))
            {
                dia = new DiaDimensionCap(this._system, this.YZView);
            }
        }
        else
        {

        }
    }

}
