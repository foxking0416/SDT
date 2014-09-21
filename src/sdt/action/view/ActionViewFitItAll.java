package sdt.action.view;

import java.awt.event.*;

import javax.swing.*;

import sdt.action.*;
import sdt.define.*;
import sdt.framework.*;

public class ActionViewFitItAll extends ActionMain
{
    public ActionViewFitItAll(SDT_System system)
   {
       super(system, "FitItAll");
   }

   public void actionPerformed(ActionEvent e)
   {
       this._system.getFrame().SetStatus("View --> FitItAll");

       boolean isPanel2D = this._system.getIsPanelShow2Dor3D();

       if (isPanel2D)
       {
           this._system.getModeler().getPanel2D().GetDrawPanel().setScaleFit();
       }
       else //3D
       {
           this._system.getModeler().getPanel3D().SetFititAll();
       }

   }

}
