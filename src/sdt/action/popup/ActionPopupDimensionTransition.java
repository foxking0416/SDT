package sdt.action.popup;

import java.awt.event.*;

import sdt.action.*;
import sdt.define.*;
import sdt.dialog.*;
import sdt.framework.*;

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
public class ActionPopupDimensionTransition extends ActionMain
{
    public ActionPopupDimensionTransition(SDT_System system)
    {
        super(system, "TransitionDimension");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("TransitionDimension");
        DiaDimensionTransition dia;

        String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeNameLowestLevel();

        if (nodeName.equals(DefineTreeNode.SKT_Section) || nodeName.equals(DefineTreeNode.SKT_Section1))
        {
            dia = new DiaDimensionTransition(this._system, this.XZView);
        }
        else
        {
            dia = new DiaDimensionTransition(this._system, this.YZView);
        }


    }

}
