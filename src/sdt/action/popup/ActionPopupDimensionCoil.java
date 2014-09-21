package sdt.action.popup;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import sdt.define.DefineTreeNode;
import java.awt.event.ActionEvent;
import sdt.data.DataCoil;
import sdt.define.DefineSystemConstant;
import sdt.dialog.DiaDimensionCoilType1;
import sdt.dialog.DiaDimensionCoilType2;

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
public class ActionPopupDimensionCoil extends ActionMain
{
    public ActionPopupDimensionCoil(SDT_System system)
    {
        super(system, "CoilDimension");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("CoilDimension");
        String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeNameLowestLevel();

        DataCoil dataCoil = this._system.getDataManager().getDataCoil();

        if(nodeName != null && nodeName != "")
        {
            if (nodeName.equals(DefineTreeNode.SKT_Section) || nodeName.equals(DefineTreeNode.SKT_Section1))
            {
                if(dataCoil.getGeometryType() == DefineSystemConstant.COIL_TYPE1)
                {
                   DiaDimensionCoilType1 dia = new DiaDimensionCoilType1(this._system, this.XZView);
                }
                else
                {
                    DiaDimensionCoilType2 dia = new DiaDimensionCoilType2(this._system, this.XZView);
                }
            }
            else
            {
                if(dataCoil.getGeometryType() == DefineSystemConstant.COIL_TYPE1)
                {
                    DiaDimensionCoilType1 dia = new DiaDimensionCoilType1(this._system, this.YZView);
                }
                else
                {
                    DiaDimensionCoilType2 dia = new DiaDimensionCoilType2(this._system, this.XZView);
                }
            }
        }


    }
}
