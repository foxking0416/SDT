package sdt.action.popup;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import sdt.dialog.DiaThickness;

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
public class ActionPopupThickness extends ActionMain
{
    public ActionPopupThickness(SDT_System system)
    {
        super(system, "Thickness");
    }
    public void actionPerformed(ActionEvent e)
     {
         this._system.getFrame().SetStatus("Thickness");
         DiaThickness dia = new DiaThickness(this._system);

     }
}
