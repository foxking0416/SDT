package sdt.action;

import java.awt.event.*;
import javax.swing.*;

import sdt.framework.*;
import sdt.define.DefineIcon;
import sdt.define.DefineSystemConstant;

public class ActionMain extends AbstractAction implements DefineIcon, DefineSystemConstant
{
    protected SDT_System     _system;
    public ActionMain(SDT_System inputSystem, String str)

    {
        super(str);
        this._system = inputSystem;
    }

    public void actionPerformed(ActionEvent e)
    {

    }



}
