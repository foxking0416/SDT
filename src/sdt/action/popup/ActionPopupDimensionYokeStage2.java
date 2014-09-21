package sdt.action.popup;

import java.awt.event.*;

import sdt.action.*;
import sdt.define.*;
import sdt.dialog.*;
import sdt.framework.*;

public class ActionPopupDimensionYokeStage2 extends ActionMain
{
    public ActionPopupDimensionYokeStage2(SDT_System system)
    {
        super(system, "YokeStage2Dimension");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("YokeStage2Dimension");

        DiaDimensionYokeStage2 dia;
        String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeNameLowestLevel();


        if(nodeName != null && nodeName != "")
        {
            if (nodeName.equals(DefineTreeNode.SKT_Section) || nodeName.equals(DefineTreeNode.SKT_Section1))
            {
                dia = new DiaDimensionYokeStage2(this._system, this.XZView);
            }
            else //(nodeName.equals(DefineTreeNode.SKT_Section2))
            {
                dia = new DiaDimensionYokeStage2(this._system, this.YZView);
            }
        }
        else
        {

        }
    }

}
