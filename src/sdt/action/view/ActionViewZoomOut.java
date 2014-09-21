package sdt.action.view;

import java.awt.event.*;

import javax.swing.*;

import sdt.action.*;
import sdt.define.*;
import sdt.framework.*;

public class ActionViewZoomOut extends ActionMain
{
    public ActionViewZoomOut(SDT_System system)
   {
       super(system, "ZoomOut");
   }

   public void actionPerformed(ActionEvent e)
   {
       this._system.getFrame().SetStatus("View --> ZoomOut");
       boolean isPanel2D = this._system.getIsPanelShow2Dor3D();
       if (isPanel2D)
       {
           this._system.getModeler().getPanel2D().GetDrawPanel().getTransfer().adjustScale( -1);
           this._system.getModeler().getPanel2D().GetDrawPanel().upDate();
       }
       else
       {
           this._system.getModeler().getPanel3D().SetZoom(0.77);
       }

   }

}
