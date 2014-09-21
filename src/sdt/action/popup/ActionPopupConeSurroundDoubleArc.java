package sdt.action.popup;

import sdt.action.ActionMain;
import java.awt.event.ActionEvent;
import sdt.framework.SDT_System;
import sdt.define.DefineSystemConstant;
import sdt.data.DataSurround;
import sdt.define.DefineTreeNode;

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
public class ActionPopupConeSurroundDoubleArc extends ActionMain
{
    public ActionPopupConeSurroundDoubleArc(SDT_System system)
    {
        super(system, "ChkBoxSurroundDoubleArc");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxSurroundDoubleArc");


        DataSurround dataSurround = this._system.getDataManager().getDataSurround();

            if (dataSurround.getGeometryType() == DefineSystemConstant.SURROUND_SINGLE_ARC)
            {
                dataSurround.setGeometryType(DefineSystemConstant.SURROUND_DOUBLE_ARC);
                dataSurround.setIfSwitchSurroundType(DefineSystemConstant.XZView, true);
                dataSurround.setIfSwitchSurroundType(DefineSystemConstant.YZView, true);
                //this._system.getModeler().setSurroundStatus(DefineSystemConstant.SURROUND_DOUBLE_ARC);

                this._system.getModeler().getPanel2D().GetDrawPanel().showElement();
            }

    }
}
