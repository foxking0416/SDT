package sdt.menu;

import java.net.*;
import java.util.*;

import javax.swing.*;
import sdt.define.DefineIcon;
import sdt.framework.SDT_System;
import sdt.define.DefineMenu;
import sdt.define.DefineSystemConstant;


public class Menus implements DefineMenu
{
    private SDT_System            _system;
    private static ResourceBundle _resources;
    private Hashtable             _commandsTable;
    private Hashtable             _menuItemsTable;
    private Hashtable             _menuItemCheckboxesTable;
    private Hashtable             _menusTable;
    private JMenuBar              _menubar;
    private int                   _status;

    public Menus(SDT_System inputSystem, Hashtable inputHash)
    {
        this._system = inputSystem;
        this._commandsTable = inputHash;
        this._menuItemsTable = new Hashtable();
        this._menuItemCheckboxesTable  = new Hashtable();
        this._menusTable = new Hashtable();
        this._menubar = new JMenuBar();
        this._status = DefineSystemConstant.MODULE_STANDARD;
    }

    public JMenuBar createStandard()
    {
        _status = DefineSystemConstant.MODULE_STANDARD;
        if (this._menubar.getComponentCount() != 0)
        {
            this._menubar.removeAll();
        }

        this.addMenu(this.MENU_FILE);
        this.addMenu(this.MENU_VIEW);
        this.addMenu(this.MENU_COMPUTATION);
        this.addMenu(this.MENU_TOOL);
        this.addMenu(this.MENU_HELP);

        return this._menubar;
    }

    public JMenuBar createPreliminaryModuleDesign()
    {
        _status = DefineSystemConstant.MODULE_PRELIMINARY;
        if (this._menubar.getComponentCount() != 0)
        {
            this._menubar.removeAll();
        }


        this.addMenu(this.MENU_FILE);
        this.addMenu(this.MENU_PRELIMINARY);
        this.addMenu(this.MENU_VIEW);
        this.addMenu(this.MENU_TOOL);
        this.addMenu(this.MENU_HELP);

        return this._menubar;
    }

    public JMenuBar reloadMenubar()
    {
        switch (this._status)
        {
            case DefineSystemConstant.MODULE_PRELIMINARY:
                this.createPreliminaryModuleDesign();
                break;
            case DefineSystemConstant.MODULE_STANDARD:
                this.createStandard();
                break;
        }

        // Add by fox 2012.11.02  solve reload file menu problem
        switch (this._system.getLanguageType())
        {
            case DefineSystemConstant.LANG_ENGLINSH:
                this.getMenuItemCheckBoxFromName("ChkBoxLanguageEnglish").setSelected(true);
                this.getMenuItemCheckBoxFromName("ChkBoxLanguageChinese").setSelected(false);
                break;
            case DefineSystemConstant.LANG_CHINESE_BIG5:
                this.getMenuItemCheckBoxFromName("ChkBoxLanguageChinese").setSelected(true);
                this.getMenuItemCheckBoxFromName("ChkBoxLanguageEnglish").setSelected(false);
                break;
        }

        if(this._system.getIsPanelShow2Dor3D())
        {
            this.getMenuFromName("ShadingMode").setSelected(false);
        }
        else
        {
            this.getMenuItemCheckBoxFromName("ChkBoxShading").setSelected(false);
            this.getMenuItemCheckBoxFromName("ChkBoxShadingWithEdges").setSelected(false);
            this.getMenuItemCheckBoxFromName("ChkBoxWireFrame").setSelected(false);

            switch (this._system.getModeler().getPanel3D().getShowType())
            {
                case DefineSystemConstant.VIEW_SHADING:
                    this.getMenuItemCheckBoxFromName("ChkBoxShading").setSelected(true);
                    break;
                case DefineSystemConstant.VIEW_SHADINGWITHEDGE:
                    this.getMenuItemCheckBoxFromName("ChkBoxShadingWithEdges").setSelected(true);
                    break;
                case DefineSystemConstant.VIEW_WIREFRAME:
                    this.getMenuItemCheckBoxFromName("ChkBoxWireFrame").setSelected(true);
                    break;
            }
        }


        switch (this._system.getModeler().getControlType())
        {
            case DefineSystemConstant.MOUSE_ROTATE:
                this.getMenuItemCheckBoxFromName("ChkBoxRotate").setSelected(true);
                this.getMenuItemCheckBoxFromName("ChkBoxPan").setSelected(false);
                break;
            case DefineSystemConstant.MOUSE_TRANSLATE:
                this.getMenuItemCheckBoxFromName("ChkBoxRotate").setSelected(false);
                this.getMenuItemCheckBoxFromName("ChkBoxPan").setSelected(true);
                break;
            case DefineSystemConstant.MOUSE_NONE:
                this.getMenuItemCheckBoxFromName("ChkBoxRotate").setSelected(false);
                this.getMenuItemCheckBoxFromName("ChkBoxPan").setSelected(false);
                break;

        }





        return this._menubar;
    }


    public void setMenuEnable(String name, boolean bool)
    {
        JMenu menu = this.getMenuFromName(name);
        menu.setEnabled(bool);
    }

    public void setMenuItemEnable(String name, boolean bool)
    {
        JMenuItem menuItem = this.getMenuItemFromName(name);
        menuItem.setEnabled(bool);
        //System.out.println(name);
    }

    public void doMenuItem(String name)
    {
       JMenuItem menuItem = (JMenuItem) this._menuItemsTable.get(name);
       menuItem.doClick();
   }






    public JMenu getMenuFromName(String name)
    {
        JMenu menu = (JMenu) this._menusTable.get(name);
        return menu;
    }

    public JMenuItem getMenuItemFromName(String name)
    {
        JMenuItem menuItem = (JMenuItem) this._menuItemsTable.get(name);
        return menuItem;
    }

    public JCheckBoxMenuItem getMenuItemCheckBoxFromName(String name)
    {
        JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) this._menuItemCheckboxesTable.get(name);
        return menuItem;
    }



    private void addMenu(String property)
    {
        try
        {
            this._resources = ResourceBundle.getBundle(property, Locale.getDefault());
        }
        catch (MissingResourceException e)
        {
            e.printStackTrace();
        }
        String keyResource = this.getResourceString("MenuBar");
        StringTokenizer token = new StringTokenizer(keyResource);
        while (token.hasMoreTokens())
        {
            String str = token.nextToken();
            //System.out.println(str);
            JMenu menu = this.createMenu(str);
            if (menu != null)
            {
                menu.setMnemonic(str.charAt(0));
                this._menubar.add(menu);
            }
        }
    }


    private JMenu createMenu(String key)
    {
        JMenu menu = null;
        String lstr = this.getResourceString(key.concat(this.LABEL));
        URL url = this.getResource(key.concat(this.IMAGE));
        if (lstr != null)
        {
            String lstr2 = this._system.GetInterfaceString(lstr);
            if(lstr2 != null)
                menu = new JMenu(lstr2);
            else
                menu = new JMenu(lstr);
        }
        if (url != null)
        {
            menu.setHorizontalTextPosition(4);
            menu.setIcon(new ImageIcon(url));
        }

        String keyResource = this.getResourceString(key);
        StringTokenizer token = new StringTokenizer(keyResource);
        while (token.hasMoreTokens())
        {
            String str = token.nextToken();
            String strResource = this.getResourceString(str);
            if (str.equals("-"))
            {
                menu.addSeparator();
            }
            else if (str.startsWith("r_file"))
            {
              //  System.out.println(str);
                JMenuItem menuItem = this.createMenuItemForRecentFiles(str);
                menu.add(menuItem);
            }
            else
            {
                if (strResource != null)
                {
                    JMenu innerMenu = this.createMenu(str);
                   // System.out.println("Menu:  "+ str);
                    if (innerMenu != null)
                    {
                        menu.add(innerMenu);
                    }
                }
                else
                {
                    if (!str.startsWith("ChkBox"))
                    {
                        JMenuItem menuItem = this.createMenuItem(str);
                        //System.out.println("MenuItem:  "+ str);
                        this.setHotKey(str, menuItem);
                        menu.add(menuItem);
                    }

                    else
                    {
                        JCheckBoxMenuItem menuItemCheck = this.createMenuItemCheckBox(str);
                  //      System.out.println("MenuItemCheckBox:  "+ str);
                        menu.add(menuItemCheck);
                    }
                }
            }
        }
        this._menusTable.put(key, menu);
        menu.getPopupMenu().setLightWeightPopupEnabled(false);//blow
        return menu;
    }

    private JMenuItem createMenuItem(String key)
    {

        JMenuItem menuItem = null;
        String lstr = this.getResourceString(key.concat(this.LABEL));
        URL url = this.getResource(key.concat(this.IMAGE));
        String astr = this.getResourceString(key.concat(this.ACTION));
        if (lstr != null)
        {
            String lstr2 = this._system.GetInterfaceString(lstr);
            if (lstr2 != null)
                menuItem = new JMenuItem(lstr2);
            else
                menuItem = new JMenuItem(lstr);
        }
        if (url != null)
        {
            menuItem.setHorizontalTextPosition(4);
            menuItem.setIcon(new ImageIcon(url));
        }
        if (astr != null)
        {
            menuItem.setActionCommand(astr);
            Action action = (Action) this._commandsTable.get(astr);
            if (action != null)
            {
                menuItem.addActionListener(action);
                menuItem.setEnabled(true);
            }
            else
            {
                menuItem.setEnabled(false);
            }
        }
        this._menuItemsTable.put(key, menuItem);
        return menuItem;
    }

    private JCheckBoxMenuItem createMenuItemCheckBox(String key)
    {
        JCheckBoxMenuItem menuItem = null;
        String lstr = this.getResourceString(key.concat(this.LABEL));
        URL url = this.getResource(key.concat(this.IMAGE));
        String astr = this.getResourceString(key.concat(this.ACTION));
        if (lstr != null)
        {
            String lstr2 = this._system.GetInterfaceString(lstr);
            if (lstr2 != null)
                menuItem = new JCheckBoxMenuItem(lstr2);
            else
                menuItem = new JCheckBoxMenuItem(lstr);
        }
        if (url != null)
        {
            menuItem.setHorizontalTextPosition(4);
            menuItem.setIcon(new ImageIcon(url));
        }
        if (astr != null)
        {
            menuItem.setActionCommand(astr);
            Action action = (Action)this._commandsTable.get(astr);
            if (action != null)
            {
                menuItem.addActionListener(action);
                menuItem.setEnabled(true);
                //menuItem.setEnabled(false);   //2005.01.20
            }
            else
            {
                menuItem.setEnabled(false);
            }
        }
        this._menuItemCheckboxesTable.put(key, menuItem);
        return menuItem;
    }


    private JMenuItem createMenuItemForRecentFiles(String key)
    {
        int index = Integer.parseInt(key.substring(6, 7));
        String astr = "recent_files";
        String name = this._system.getConfig().getRecentFile(index);
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setActionCommand(astr);
        Action action = (Action) this._commandsTable.get(astr);
        menuItem.addActionListener(action);
        this._commandsTable.put(key, menuItem);


        return menuItem;
    }

    private void setHotKey(String name, JMenuItem menuItem)
    {
        if (name.equals("Open"))
        {
            menuItem.setAccelerator(KeyStroke.getKeyStroke('O',
                java.awt.Event.CTRL_MASK, false));
        }
        if (name.equals("Save"))
        {
            menuItem.setAccelerator(KeyStroke.getKeyStroke('S',
                java.awt.Event.CTRL_MASK, false));
        }
        if (name.equals("Undo"))
        {
            menuItem.setAccelerator(KeyStroke.getKeyStroke('Z',
                java.awt.Event.CTRL_MASK, false));
        }
        if (name.equals("Redo"))
        {
            menuItem.setAccelerator(KeyStroke.getKeyStroke('Y',
                java.awt.Event.CTRL_MASK, false));
        }
        if (name.equals("Cut"))
        {
            menuItem.setAccelerator(KeyStroke.getKeyStroke('X',
                java.awt.Event.CTRL_MASK, false));
        }
        if (name.equals("Copy"))
        {
            menuItem.setAccelerator(KeyStroke.getKeyStroke('C',
                java.awt.Event.CTRL_MASK, false));
        }
        if (name.equals("Paste"))
        {
            menuItem.setAccelerator(KeyStroke.getKeyStroke('V',
                java.awt.Event.CTRL_MASK, false));
        }
        if (name.equals("Search"))
        {
            menuItem.setAccelerator(KeyStroke.getKeyStroke('F',
                java.awt.Event.CTRL_MASK, false));
        }
    }

    private String getResourceString(String key)
    {
        String str = null;
        try
        {
            str = _resources.getString(key);
        }
        catch (MissingResourceException e)
        {}
        return str;
    }

    private URL getResource(String key)
    {
        URL url = null;
        String str = this.getResourceString(key);
        if (str != null)
        {
            url = getClass().getResource(str);
        }
        return url;
    }

}
