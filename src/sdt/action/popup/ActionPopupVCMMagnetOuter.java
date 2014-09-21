package sdt.action.popup;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import sdt.framework.SDT_Modeler;
import sdt.define.DefineSystemConstant;
import sdt.data.DataMagnetOuter;

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
public class ActionPopupVCMMagnetOuter extends ActionMain
{
    public ActionPopupVCMMagnetOuter(SDT_System system)
    {
            super(system, "ChkBoxMagnetOuter");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxMagnetOuter"); //在bottom的狀態列顯示這個字串

        JCheckBoxMenuItem chkBoxMenuItemMagnetOut = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxMagnetOuter");
        SDT_Modeler modeler = this._system.getModeler();

        int magnetOuterStatus = DefineSystemConstant.MAGNETOUTER_TYPE1;
        if (!chkBoxMenuItemMagnetOut.isSelected())
        {
            magnetOuterStatus = DefineSystemConstant.MAGNETOUTER_NONE;
        }

        DataMagnetOuter dataMagnetOuter = this._system.getDataManager().getDataMagnetOuter();
        dataMagnetOuter.setGeometryType(magnetOuterStatus);
        this._system.getModeler().getPanel2D().GetDrawPanel().showElement();


    }

}
