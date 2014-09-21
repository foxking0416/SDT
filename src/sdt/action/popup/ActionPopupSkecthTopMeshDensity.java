package sdt.action.popup;

import java.awt.event.*;

import sdt.action.*;
import sdt.dialog.*;
import sdt.framework.*;
import sdt.tree.*;
import sdt.define.DefineTreeNode;
public class ActionPopupSkecthTopMeshDensity extends ActionMain implements DefineTreeNode
{
    public ActionPopupSkecthTopMeshDensity(SDT_System system)
    {
        super(system, "MeshDensity");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("MeshDensity");
        DiaMeshDensity dia = new DiaMeshDensity(this._system);
    }



}
