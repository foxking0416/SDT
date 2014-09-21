package sdt.action.popup;

import sdt.action.ActionMain;
import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
import sdt.framework.SDT_Modeler;
import sdt.tree.SDT_TreeAssembly;
import sdt.panel.drawpanel.SDT_DrawPanel2D;
import javax.swing.JCheckBoxMenuItem;
import sdt.define.DefineSystemConstant;
import sdt.data.DataDiaphragm;

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
public class ActionPopupConeDiaphragmType2 extends ActionMain
{
    public ActionPopupConeDiaphragmType2(SDT_System system)
     {
         super(system, "ChkBoxDiaphragmType2");
     }

     public void actionPerformed(ActionEvent e)
     {
         this._system.getFrame().SetStatus("ChkBoxDiaphragmType2");

         DataDiaphragm dataDiaphragm = this._system.getDataManager().getDataDiaphragm();
         dataDiaphragm.setGeometryType(DefineSystemConstant.DIAPHRAGM_TYPE2);

         this._system.getModeler().getPanel2D().GetDrawPanel().showElement();

     }
}
