package sdt.action.popup;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import sdt.define.DefineSystemConstant;
import sdt.data.DataCoil;

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
public class ActionPopupVCMCoilTyp1 extends ActionMain
{
    public ActionPopupVCMCoilTyp1(SDT_System system)
    {
        super(system, "ChkBoxCoilType1");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxCoilType1");



        DataCoil dataCoil = this._system.getDataManager().getDataCoil();
        dataCoil.setGeometryType(DefineSystemConstant.COIL_TYPE1);
        this._system.getModeler().getPanel2D().GetDrawPanel().showElement();
        //tree



    }

}
