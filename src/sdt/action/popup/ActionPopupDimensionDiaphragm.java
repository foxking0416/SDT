package sdt.action.popup;

import java.awt.event.*;

import sdt.action.*;
import sdt.data.*;
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
public class ActionPopupDimensionDiaphragm extends ActionMain
{
    public ActionPopupDimensionDiaphragm(SDT_System system)
    {
        super(system, "DiaphragmDimension");
    }


    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("DiaphragmDimension");


        String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeNameLowestLevel();

        DataDiaphragm dataDiaphragm = this._system.getDataManager().getDataDiaphragm();


        if (nodeName.equals(DefineTreeNode.SKT_Section) || nodeName.equals(DefineTreeNode.SKT_Section1))
        {
            if(dataDiaphragm.getGeometryType() == DefineSystemConstant.DIAPHRAGM_TYPE1)
            {
                DiaDimensionDiaphragmType1 dia = new DiaDimensionDiaphragmType1(this._system, this.XZView);
            }
            else
            {
                DiaDimensionDiaphragmType2 dia = new DiaDimensionDiaphragmType2(this._system, this.XZView);
            }
        }
        else
        {
                DiaDimensionDiaphragmType1 dia = new DiaDimensionDiaphragmType1(this._system, this.YZView);
        }

    }

}
