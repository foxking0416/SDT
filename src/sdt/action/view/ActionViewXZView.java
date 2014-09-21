package sdt.action.view;

import java.awt.event.*;

import sdt.action.*;
import sdt.framework.*;

public class ActionViewXZView extends ActionMain
{
    public ActionViewXZView(SDT_System system)
    {
        super(system, "XZView");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("View --> XZiew");
        this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(this.TYPE_CONE,this.XZView);


    }

}
