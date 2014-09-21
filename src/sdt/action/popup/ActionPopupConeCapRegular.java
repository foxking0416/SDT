package sdt.action.popup;

import java.awt.event.*;

import sdt.action.*;
import sdt.data.*;
import sdt.define.*;
import sdt.framework.*;

public class ActionPopupConeCapRegular extends ActionMain
{
    public ActionPopupConeCapRegular(SDT_System system)
    {
        super(system, "ChkBoxCapRegular");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxCapRegular");

        DataCap dataCap = this._system.getDataManager().getDataCap();
        dataCap.setGeometryType(DefineSystemConstant.CAP_TYPE_REGULAR);
         this._system.getModeler().getPanel2D().GetDrawPanel().showElement();

       // this._system.getModeler().setCapStatus(DefineSystemConstant.CAP_TYPE_REGULAR);
    }
}
