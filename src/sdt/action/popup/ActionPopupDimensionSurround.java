package sdt.action.popup;

import sdt.framework.SDT_System;
import sdt.action.ActionMain;
import java.awt.event.ActionEvent;
import sdt.data.DataSurround;
import sdt.dialog.DiaDimensionSurroundDoubleArc;
import sdt.dialog.DiaDimensionSurroundSingleArc;
import sdt.define.DefineTreeNode;
import sdt.define.DefineSystemConstant;
import sdt.data.DataDiaphragm;


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
public class ActionPopupDimensionSurround extends ActionMain
{
    public ActionPopupDimensionSurround(SDT_System system)
    {
        super(system, "SurroundDimension");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("SurroundDimension");

        String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeNameLowestLevel();
        DataSurround dataSurround = this._system.getDataManager().getDataSurround();



        if(nodeName.equals(DefineTreeNode.SKT_Section) || nodeName.equals(DefineTreeNode.SKT_Section1))
        {
            if(dataSurround.getGeometryType() == DefineSystemConstant.SURROUND_DOUBLE_ARC)
            {
                DiaDimensionSurroundDoubleArc dia = new DiaDimensionSurroundDoubleArc(this._system, this.XZView);
            }
            else
            {
                DiaDimensionSurroundSingleArc dia = new DiaDimensionSurroundSingleArc(this._system, this.XZView);
            }
        }
        else
        {
            if(dataSurround.getGeometryType() == DefineSystemConstant.SURROUND_DOUBLE_ARC)
            {
                DiaDimensionSurroundDoubleArc dia = new DiaDimensionSurroundDoubleArc(this._system, this.YZView);
            }
            else
            {
                DiaDimensionSurroundSingleArc dia = new DiaDimensionSurroundSingleArc(this._system, this.YZView);
            }
        }

    }

}
