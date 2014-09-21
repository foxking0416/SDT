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
public class ActionPopupVCMCoilTyp2 extends ActionMain
{
    public ActionPopupVCMCoilTyp2(SDT_System system)
    {
        super(system, "ChkBoxCoilType2");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxCoilType2");


        DataCoil dataCoil = this._system.getDataManager().getDataCoil();
        dataCoil.setGeometryType(DefineSystemConstant.COIL_TYPE2);
        this._system.getModeler().getPanel2D().GetDrawPanel().showElement();

    }

}
