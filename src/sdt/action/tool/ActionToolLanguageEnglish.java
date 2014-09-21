package sdt.action.tool;

import java.awt.event.*;

import sdt.action.*;
import sdt.define.*;
import sdt.framework.*;
import javax.swing.JCheckBoxMenuItem;

public class ActionToolLanguageEnglish extends ActionMain
{
    public ActionToolLanguageEnglish(SDT_System system)
    {
        super(system, "ChkBoxLanguageEnglish");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Language --> English");

        this._system.setLanguage(DefineSystemConstant.LANG_ENGLINSH);
        JCheckBoxMenuItem item = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxLanguageEnglish");
        JCheckBoxMenuItem item2 = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxLanguageChinese");
        item.setSelected(true);
        item2.setSelected(false);
    }


}
