package sdt.action.popup;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import sdt.dialog.*;

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
public class ActionPopupCorrugation extends ActionMain
{
    public ActionPopupCorrugation(SDT_System system)
    {
        super(system, "Corrugation");
    }
    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Corrugation");

        if(this._system.getDataManager().getCurrentData().getDataType() == this.TYPE_SURROUND)
        {
            DiaCorrugation dia = new DiaCorrugation(this._system);
        }
        else
        {

        }

    }


}
