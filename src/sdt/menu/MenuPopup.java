package sdt.menu;

import java.net.*;
import java.util.*;

import javax.swing.*;
import sdt.define.DefineIcon;
import sdt.framework.SDT_System;
import sdt.define.DefineMenu;


public class MenuPopup implements DefineMenu
{
    private SDT_System            _system;
    private static ResourceBundle _resources;
    private Hashtable             _commandsTable;
    private Hashtable             _menuItemsTable;
    private Hashtable             _menuItemCheckboxesTable;
    private Hashtable             _menusTable;

    private JPopupMenu            _menuPopupPartShell;
    private JPopupMenu            _menuPopupPartSolid;
    private JPopupMenu            _menuPopupCone;
    private JPopupMenu            _menuPopupSketchTop;
    private JPopupMenu            _menuPopupVCM;
    private JPopupMenu            _menuPopupAir;
    private JPopupMenu            _menuPopupYoke;
    private JPopupMenu            _menuPopupCapSection;
    private JPopupMenu            _menuPopupSurroundSection;
    private JPopupMenu            _menuPopupTransitionSection;
    private JPopupMenu            _menuPopupDiaphragmSection;
    private JPopupMenu            _menuPopupCoilSection;
    private JPopupMenu            _menuPopupMagnetSection;
    private JPopupMenu            _menuPopupTopPlateSection;
    private JPopupMenu            _menuPopupMagnetTopSection;
    private JPopupMenu            _menuPopupMagnetOuterSection;
    private JPopupMenu            _menuPopupYokeBaseSection;
    private JPopupMenu            _menuPopupYokeStage1Section;
    private JPopupMenu            _menuPopupYokeStage2Section;


    public MenuPopup(SDT_System inputSystem, Hashtable inputHash)
    {
        this._system = inputSystem;
        this._commandsTable = inputHash;
        this._menuItemsTable = new Hashtable();
        this._menusTable = new Hashtable();
        this._menuItemCheckboxesTable  = new Hashtable();


    }

    public void reloadPopupMenu()
    {

        //this.CreateStandard();

        //return this.menubar;
    }


    public JPopupMenu createMenuPopupPartShell() // create second, so should  borrow the roundway menuItem from menuPopupPartSolid( when menuPopup)
    {
        if (this._menuPopupPartShell != null)
        {
            this._menuPopupPartShell.removeAll();
        }
        _menuPopupPartShell = this.addPopupMenu(this.MENUPOPUP_PARTSHELL);

        return this._menuPopupPartShell;
    }

    public JPopupMenu createMenuPopupPartSolid() // create first
    {
        if (this._menuPopupPartSolid != null)
        {
            this._menuPopupPartSolid.removeAll();
        }
        _menuPopupPartSolid = this.addPopupMenu(this.MENUPOPUP_PARTSOLID);

        return this._menuPopupPartSolid;
    }

    public JPopupMenu createMenuPopupCone()
    {
        if (this._menuPopupCone != null)
        {
            this._menuPopupCone.removeAll();
        }
        _menuPopupCone = this.addPopupMenu(this.MENUPOPUP_CONE);

        return this._menuPopupCone;
    }

    public JPopupMenu createMenuPopupSketchTop()
    {
        if (this._menuPopupSketchTop != null)
        {
            this._menuPopupSketchTop.removeAll();
        }
        _menuPopupSketchTop = this.addPopupMenu(this.MENUPOPUP_SKETCHTOP);

        return this._menuPopupSketchTop;
    }

    public JPopupMenu createMenuPopupSketchSectionCap()
    {
        if (this._menuPopupCapSection != null)
        {
            this._menuPopupCapSection.removeAll();
        }
        this._menuPopupCapSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("CapDimension");
        this._menuPopupCapSection.removeAll();
        this._menuPopupCapSection.add(menuItem);

        return this._menuPopupCapSection;
    }

    public JPopupMenu createMenuPopupSketchSectionSurround()
    {
        if (this._menuPopupSurroundSection != null)
        {
            this._menuPopupSurroundSection.removeAll();
        }
        this._menuPopupSurroundSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("SurroundDimension");
       // JCheckBoxMenuItem chbMenuItem1 = this.getMenuItemCheckBoxFromName("ChkBoxSurroundSingleArc");
        //JCheckBoxMenuItem chbMenuItem2 = this.getMenuItemCheckBoxFromName("ChkBoxSurroundDoubleArc");

        this._menuPopupSurroundSection.removeAll();
        this._menuPopupSurroundSection.add(menuItem);
       // this.menuPopupSurroundSection.add(chbMenuItem1);
       // this.menuPopupSurroundSection.add(chbMenuItem2);

        return this._menuPopupSurroundSection;
    }

    public JPopupMenu createMenuPopupSketchSectionTransition()
    {
        if (this._menuPopupTransitionSection != null)
        {
            this._menuPopupTransitionSection.removeAll();
        }
        this._menuPopupTransitionSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("TransitionDimension");
        this._menuPopupTransitionSection.removeAll();
        this._menuPopupTransitionSection.add(menuItem);

        return this._menuPopupTransitionSection;
    }

    public JPopupMenu createMenuPopupSketchSectionDiaphragm()
    {
        if (this._menuPopupDiaphragmSection != null)
        {
            this._menuPopupDiaphragmSection.removeAll();
        }
        this._menuPopupDiaphragmSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("DiaphragmDimension");

        this._menuPopupDiaphragmSection.removeAll();
        this._menuPopupDiaphragmSection.add(menuItem);
        return this._menuPopupDiaphragmSection;
    }

    public JPopupMenu createMenuPopupSketchSectionCoil()
    {
        if (this._menuPopupCoilSection != null)
        {
            this._menuPopupCoilSection.removeAll();
        }
        this._menuPopupCoilSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("CoilDimension");

        this._menuPopupCoilSection.removeAll();
        this._menuPopupCoilSection.add(menuItem);


        return this._menuPopupCoilSection;
    }

    public JPopupMenu createMenuPopupVCM()
    {
        if(this._menuPopupVCM != null)
        {
            this._menuPopupVCM.removeAll();
        }
        _menuPopupVCM = this.addPopupMenu(this.MENUPOPUP_VCM);

        return this._menuPopupVCM;
    }

    public JPopupMenu createMenuPopupAir()
    {
        if(this._menuPopupAir != null)
        {
            this._menuPopupAir.removeAll();
        }
        _menuPopupAir = this.addPopupMenu(this.MENUPOPUP_AIR);

        return this._menuPopupAir;
    }



    public JPopupMenu createMenuPopupSketchSectionMagnet()
    {
        if (this._menuPopupMagnetSection != null)
        {
            this._menuPopupMagnetSection.removeAll();
        }
        this._menuPopupMagnetSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("MagnetDimension");

        this._menuPopupMagnetSection.removeAll();
        this._menuPopupMagnetSection.add(menuItem);

        return this._menuPopupMagnetSection;
    }

    public JPopupMenu createMenuPopupSketchSectionMagnetTop()
    {
        if (this._menuPopupMagnetTopSection != null)
        {
            this._menuPopupMagnetTopSection.removeAll();
        }
        this._menuPopupMagnetTopSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("MagnetTopDimension");

        this._menuPopupMagnetTopSection.removeAll();
        this._menuPopupMagnetTopSection.add(menuItem);

        return this._menuPopupMagnetTopSection;
    }
    public JPopupMenu createMenuPopupSketchSectionMagnetOuter()
    {
        if (this._menuPopupMagnetOuterSection != null)
        {
            this._menuPopupMagnetOuterSection.removeAll();
        }
        this._menuPopupMagnetOuterSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("MagnetOuterDimension");

        this._menuPopupMagnetOuterSection.removeAll();
        this._menuPopupMagnetOuterSection.add(menuItem);

        return this._menuPopupMagnetOuterSection;
    }




    public JPopupMenu createMenuPopupSketchSectionTopPlate()
    {
        if (this._menuPopupTopPlateSection != null)
        {
            this._menuPopupTopPlateSection.removeAll();
        }
        this._menuPopupTopPlateSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("TopPlateDimension");

        this._menuPopupTopPlateSection.removeAll();
        this._menuPopupTopPlateSection.add(menuItem);
        return this._menuPopupTopPlateSection;
    }

    public JPopupMenu createMenuPopupYoke()
    {
        if(this._menuPopupYoke != null)
        {
            this._menuPopupYoke.removeAll();
        }
        this._menuPopupYoke = this.addPopupMenu(this.MENUPOPUP_YOKE);

        return this._menuPopupYoke;
    }

    public JPopupMenu createMenuPopupSketchSectionYokeBase()
    {
        if (this._menuPopupYokeBaseSection != null)
        {
            this._menuPopupYokeBaseSection.removeAll();
        }
        this._menuPopupYokeBaseSection = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("YokeBaseDimension");

        this._menuPopupYokeBaseSection.removeAll();
        this._menuPopupYokeBaseSection.add(menuItem);

        return this._menuPopupYokeBaseSection;
    }

    public JPopupMenu createMenuPopupSketchSectionYokeStage1()
    {
        if (this._menuPopupYokeStage1Section != null)
        {
            this._menuPopupYokeStage1Section.removeAll();
        }
        this._menuPopupYokeStage1Section = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("YokeStage1Dimension");

        this._menuPopupYokeStage1Section.removeAll();
        this._menuPopupYokeStage1Section.add(menuItem);

        return this._menuPopupYokeStage1Section;
    }

    public JPopupMenu createMenuPopupSketchSectionYokeStage2()
    {
        if (this._menuPopupYokeStage2Section != null)
        {
            this._menuPopupYokeStage2Section.removeAll();
        }
        this._menuPopupYokeStage2Section = this.addPopupMenu(this.MENUPOPUP_SECTION);
        JMenuItem menuItem = this.getMenuItemFromName("YokeStage2Dimension");

        this._menuPopupYokeStage2Section.removeAll();
        this._menuPopupYokeStage2Section.add(menuItem);

        return this._menuPopupYokeStage2Section;
    }

    public void setMenuEnable(String name, boolean bool)
    {
        JMenu menu = this.getMenuFromName(name);
        menu.setEnabled(bool);
    }

    public void setMenuItemEnable(String name)
    {
        JMenuItem menuItem = this.getMenuItemFromName(name);
        menuItem.setEnabled(true);
        //System.out.println(name);
    }

    public void setMenuItemCheckBoxEnable(String name)
   {
       JCheckBoxMenuItem menuItem = this.getMenuItemCheckBoxFromName(name);
       menuItem.setEnabled(true);
       //System.out.println(name);
   }

   public void setMenuItemCheckBoxSelected(String name, boolean bool)
   {
       JCheckBoxMenuItem menuItem = this.getMenuItemCheckBoxFromName(name);
       menuItem.setSelected(bool);
   }


    public void setMenuItemDisable(String name)
    {
        JMenuItem menuItem = this.getMenuItemFromName(name);
        menuItem.setEnabled(false);
    }

    public JMenu getMenuFromName(String name)
    {
        JMenu menu = (JMenu) this._menusTable.get(name);
        return menu;
    }

    private JMenuItem getMenuItemFromName(String name)
    {
        JMenuItem menuItem = (JMenuItem) this._menuItemsTable.get(name);
        return menuItem;
    }

    public JCheckBoxMenuItem getMenuItemCheckBoxFromName(String name)
    {
        JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) this._menuItemCheckboxesTable.get(name);
        return menuItem;
    }

    private JPopupMenu addPopupMenu(String property)
    {
        JPopupMenu menuPopup = null;
        try
        {
            this._resources = ResourceBundle.getBundle(property, Locale.getDefault());
        }
        catch (MissingResourceException e)
        {
            e.printStackTrace();
        }
        String keyResource = this.getResourceString("Popup");
        StringTokenizer token = new StringTokenizer(keyResource);
        while (token.hasMoreTokens())
        {
            String str = token.nextToken();
            JMenu menu = this.createMenu(str);
            //System.out.println(str);
            menuPopup =  menu.getPopupMenu();
        }
        return menuPopup;
    }


    private JMenu createMenu(String key)
    {
        JMenu menu = null;
        String lstr = this.getResourceString(key.concat(this.LABEL));
        URL url = this.getResource(key.concat(this.IMAGE));
        if (lstr != null)
        {
            String lstr2 = this._system.GetInterfaceString(lstr);
            if (lstr2 != null)
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
            else
            {
                if (strResource != null)
                {
                    JMenu innerMenu = this.createMenu(str);
                    System.out.println("Menu:  "+ str);
                    if (innerMenu != null)
                    {
                        menu.add(innerMenu);
                    }
                }
                else
                {
                    if(!str.startsWith("ChkBox"))
                    {
                        JMenuItem menuItem = this.createMenuItem(str);
                        //System.out.println("MenuItem:  "+ str);
                        this.setHotKey(str, menuItem);
                        menu.add(menuItem);
                    }
                    else
                    {
                        JCheckBoxMenuItem menuItemCheck = this.createMenuItemCheckBox(str);
                        //System.out.println("MenuItemCheckBox:  "+ str);
                        menu.add(menuItemCheck);
                    }
                }
            }
        }
        this._menusTable.put(key, menu);
        //menu.getPopupMenu().setLightWeightPopupEnabled(false);
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
                //menuItem.setEnabled(false);   //2005.01.20
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
       JCheckBoxMenuItem  menuItem = null;
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

          // menuItem = new JCheckBoxMenuItem(lstr);
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



   /* private JMenuItem CreateMenuItemForRecentFiles(String key)
    {
        int index = Integer.parseInt(key.substring(6, 7));
        String astr = "recent_files";
        String name = this.system.GetManagerConfig().GetRecentFile(index);
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setActionCommand(astr);
        Action action = (Action) this.commandsTable.get(astr);
        menuItem.addActionListener(action);
        this.menuItemsTable.put(key, menuItem);

        if (isFirstRecent)
        {
            menuItem.setAccelerator(KeyStroke.getKeyStroke('1',
                    java.awt.Event.CTRL_MASK, false));
        }
        this.isFirstRecent = false;

        return menuItem;
    }*/

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


    public void setInitialEnable()
    {
        //File
        this.setMenuItemEnable("Material");
        this.setMenuItemCheckBoxEnable("ChkBoxSectionRound");
        this.setMenuItemCheckBoxEnable("ChkBoxSectionRunway");
        //this.SetMenuItemCheckBoxEnable("ChkBoxMagnet");
        this.setMenuItemEnable("EditSketch");
        this.getMenuItemCheckBoxFromName("ChkBoxSectionRound").setSelected(true);
       // this.getMenuItemCheckBoxFromName("ChkBoxMagnet").setEnabled(true);
    }
    public void setTSModuleEnable()
    {


    }



}
