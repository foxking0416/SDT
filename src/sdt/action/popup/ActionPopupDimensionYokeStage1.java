package sdt.action.popup;


import java.awt.event.*;

import sdt.action.*;
import sdt.define.*;
import sdt.dialog.*;
import sdt.framework.*;
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
public class ActionPopupDimensionYokeStage1 extends ActionMain
{
    public ActionPopupDimensionYokeStage1(SDT_System system)
    {
        super(system, "YokeStage1Dimension");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("YokeStage1Dimension");

        DiaDimensionYokeStage1 dia;
        String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeNameLowestLevel();


        if(nodeName != null && nodeName != "")
        {
            if (nodeName.equals(DefineTreeNode.SKT_Section) || nodeName.equals(DefineTreeNode.SKT_Section1))
            {
                dia = new DiaDimensionYokeStage1(this._system, this.XZView);
            }
            else //(nodeName.equals(DefineTreeNode.SKT_Section2))
            {
                dia = new DiaDimensionYokeStage1(this._system, this.YZView);
            }
        }
        else
        {

        }
    }

}
