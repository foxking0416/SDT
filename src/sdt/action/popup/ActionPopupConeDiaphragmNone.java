package sdt.action.popup;

import java.awt.event.*;

import sdt.action.*;
import sdt.data.*;
import sdt.define.*;
import sdt.framework.*;


public class ActionPopupConeDiaphragmNone extends ActionMain
{
    public ActionPopupConeDiaphragmNone(SDT_System system)
    {
        super(system, "ChkBoxDiaphragmNone");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxDiaphragmNone");

        DataDiaphragm dataDiaphragm = this._system.getDataManager().getDataDiaphragm();
        dataDiaphragm.setGeometryType(DefineSystemConstant.DIAPHRAGM_NONE);
        this._system.getModeler().getPanel2D().GetDrawPanel().showElement();
        //this._system.getModeler().setDiaphragmStatus(DefineSystemConstant.DIAPHRAGM_NONE);



    }
}
