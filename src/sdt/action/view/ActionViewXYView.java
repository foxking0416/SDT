package sdt.action.view;

import java.awt.event.*;

import sdt.action.*;
import sdt.framework.*;

public class ActionViewXYView extends ActionMain
{
    public ActionViewXYView(SDT_System system)
    {
        super(system, "XYView");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("View --> XYView");
        this._system.getModeler().getPanel2D().GetDrawPanel().showAllDataXY();


    }

}
