package sdt.action.view;

import java.awt.event.*;

import javax.swing.*;

import sdt.action.*;
import sdt.define.*;
import sdt.framework.*;

public class ActionViewZoomIn extends ActionMain
{
    public ActionViewZoomIn(SDT_System system)
   {
       super(system, "ZoomIn");
   }

   public void actionPerformed(ActionEvent e)
   {
       this._system.getFrame().SetStatus("View --> ZoomIn");
       boolean isPanel2D = this._system.getIsPanelShow2Dor3D();
       if (isPanel2D)
       {
           this._system.getModeler().getPanel2D().GetDrawPanel().getTransfer().adjustScale(1);
           this._system.getModeler().getPanel2D().GetDrawPanel().upDate();
       }
       else
       {
           this._system.getModeler().getPanel3D().SetZoom(1.3);
       }


   }

}
