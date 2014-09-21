package sdt.action.popup;

import java.awt.event.*;

import sdt.action.*;
import sdt.data.*;
import sdt.define.*;
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
public class ActionPopupConeDiaphragmType1 extends ActionMain
{
    public ActionPopupConeDiaphragmType1(SDT_System system)
    {
        super(system, "ChkBoxDiaphragmType1");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxDiaphragmType1");

        DataDiaphragm dataDiaphragm = this._system.getDataManager().getDataDiaphragm();
        dataDiaphragm.setGeometryType(DefineSystemConstant.DIAPHRAGM_TYPE1);

        //this._system.getModeler().setDiaphragmStatus(DefineSystemConstant.DIAPHRAGM_TYPE1);
        this._system.getModeler().getPanel2D().GetDrawPanel().showElement();
    }

}
