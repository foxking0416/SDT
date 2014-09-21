package sdt.FEM;
// SdtVcmMacGen.java

//-----------------------------------------------------------------------------
//
// @file      SdtVcmMacGen.java
//
// @brief     Abstract class for different VCM types in SDT.
//
// @version   $Id:$
//
// Copyright (c) 2012-2012 Hon-Hai Technology Group
// See LICENSE file for conditions of use.
//
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.*;

public abstract class SdtVcmMacGen {

    //
    // Default constructor with two arguments.
    //
    // @param k array to contain tags
    // @param v array to contain corresponding values
    //
    public SdtVcmMacGen(String[] k, String[] v)
    {
        keys = k;
        values = v;
        items = keys.length;
        isSearched = new boolean[items];
        for (int j = 0; j < items; ++j)
            isSearched[j] = false;
    }

    //
    //
    protected void check()
    {
        int ecode = 0;
        System.out.println("Check key-value assignment...");
        for (int j = 0; j < items; ++j)
        {
            if (!isSearched[j])
            {
                System.err.println("   key-value [" + keys[j] + ',' + values[j] +
                                   "] not assigned");
                ecode = -1;
            }
        }

        switch (ecode)
        {
            case -1:
                System.err.println("Check code: error(-1)");
                break;
            case -2:
                System.err.println("Check code: error(-2)");
                break;
            default:
                System.err.println("Check code: pass");
                break;
        }
    }

   //
   // Abstract method to write out ANSYS script.
   //
   // @param s name of file to write script to
   //
   abstract void output(String p, String s);

   // number of items in key and value arrays
   protected int items;

   // array to store tags
   protected String[] keys;

   // array to store values of corresponding tags
   protected String[] values;

   // array to record whether a tag has been searched
   protected boolean[] isSearched;
   protected String templatePath;
   public void setTmpPath(String Path)
   {
       templatePath = Path;
   }
}
