package sdt.action.tool;

import java.awt.event.*;

import sdt.action.*;
import sdt.define.*;
import sdt.framework.*;
import javax.swing.JCheckBoxMenuItem;


public class ActionToolLanguageChinese extends ActionMain
{
    public ActionToolLanguageChinese(SDT_System system)
    {
        super(system, "ChkBoxLanguageChinese");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Language --> Chinese");

        this._system.setLanguage(DefineSystemConstant.LANG_CHINESE_BIG5);
        JCheckBoxMenuItem item2 = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxLanguageEnglish");
        JCheckBoxMenuItem item = this._system.getMenu().getMenuItemCheckBoxFromName("ChkBoxLanguageChinese");
        item.setSelected(true);
        item2.setSelected(false);

    }


}
