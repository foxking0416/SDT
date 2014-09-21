package sdt.property;

import java.util.*;
import sdt.framework.SDT_System;

public class SDT_Language
{
    private SDT_System _system;
    private static ResourceBundle resources;

    public SDT_Language(SDT_System system, String property)
    {
        this._system = system;

        try
        {
            Initial(property);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void Initial(String property)
    {

        try
        {
            this.resources = ResourceBundle.getBundle(property, Locale.getDefault());
        }
        catch (MissingResourceException e)
        {
            System.err.println("Properties not found");
            System.exit(1);
            e.printStackTrace();
        }

    }

    public String GetResourceString(String key)
    {
        String str = null;
        try
        {
            str = resources.getString(key);
        }
        catch (MissingResourceException e)
        {}
        return str;
    }




}
