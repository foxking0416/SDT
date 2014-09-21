package sdt.action.popup;

import java.awt.event.*;

import javax.swing.*;

import sdt.action.*;
import sdt.data.*;
import sdt.define.*;
import sdt.framework.*;
import sdt.tree.*;


public class ActionPopupVCMMagnetTop extends ActionMain
{
    public ActionPopupVCMMagnetTop(SDT_System system)
    {
        super(system, "ChkBoxMagnetTop");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxMagnetTop");//�bbottom�����A�C��ܳo�Ӧr��

        JCheckBoxMenuItem item3 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxMagnetTop");


        int magnetTopStatus = DefineSystemConstant.MAGNETTOP_TYPE1;
        if(!item3.isSelected())
        {
            magnetTopStatus = DefineSystemConstant.MAGNETTOP_NONE;
        }

        DataMagnetTop dataMagnetTop = this._system.getDataManager().getDataMagnetTop();
        dataMagnetTop.setGeometryType(magnetTopStatus);
        this._system.getModeler().getPanel2D().GetDrawPanel().showElement();


    }
}
