package sdt.action.popup;

import java.awt.event.*;

import sdt.action.*;
import sdt.data.*;
import sdt.define.*;
import sdt.framework.*;

public class ActionPopupConeCapCapsule extends ActionMain
{
    public ActionPopupConeCapCapsule(SDT_System system)
    {
        super(system, "ChkBoxCapCapsule");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxCapCapsule");

        DataCap dataCap = this._system.getDataManager().getDataCap();
        dataCap.setGeometryType(DefineSystemConstant.CAP_TYPE_CAPSULE);
         this._system.getModeler().getPanel2D().GetDrawPanel().showElement();
       // this._system.getModeler().setCapStatus(DefineSystemConstant.CAP_TYPE_CAPSULE);
        if (!dataCap.getIsRunway())
        {
            this._system.getDataManager().setSurroundDiaphragmRunway(true);
            this._system.getModeler().GetAssemTree().setShapeSurroundDiaphgram();

            this._system.getDataManager().setCapTransitionRunway(true);
            this._system.getModeler().GetAssemTree().setShapeCapTransition();

            this._system.getDataManager().setVCMRunway(true);
            this._system.getModeler().GetAssemTree().setShapeVCM();
        }

    }
}
