package sdt.FEM;
// SdtVcmMacR1xb.java

//-----------------------------------------------------------------------------
//
// @file      SdtVcmMacR1xb.java
//
// @brief     Class to generate ANSYS script files to perform magnetic circuit
//            analysis of VCM consisting of a round magnet, a round top plate
//            (washer), and a yoke.
//
// @version   $Id:$
//
// Copyright (c) 2012-2012 Hon-Hai Technology Group
// See LICENSE file for conditions of use.
//
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.*;

public class SdtVcmMacR1xb extends SdtVcmMacGen
{

   //
   // Default constructor with two arguments.
   //
   // @param tag array to contain tags
   // @param value array to contain corresponding values
   //
   public SdtVcmMacR1xb(String[] tag, String[] value) {
      super(tag, value);
   }

   //
   // Implementation of abstract method to write out ANSYS script file by
   // combining template script file "r1xb_vcm.mac" and values provided
   // in the constructor.
   //
   // @param fname file name of the ANSYS script file
   //
   public void output(String path, String fname)
   {
      BufferedReader tmplReader = null;
      try
      {
         tmplReader = new BufferedReader(new FileReader(templatePath + "\\r1xb_vcm.mac"));
      }
      catch (FileNotFoundException e)
      {
         System.err.println("Template file open error: " + e.getMessage());
      }

      // create mac writer
      BufferedWriter macWriter = null;
      try {
         macWriter = new BufferedWriter(new FileWriter(path + fname));
      }
      catch (IOException e) {
         System.err.println("Mac file open error: " + e.getMessage());
      }

      String akey = "";  // key
      String rline = ""; // line for reading
      String wline = ""; // line for writing
      int idx1, idx2;
      try {
         while(tmplReader.ready()) {
            //rline = tmplReader.readLine().trim();
            rline = tmplReader.readLine();

            idx1 = idx2 = -1;
            if (rline.length() == 0) { // blank line
               macWriter.newLine();
               continue;
            }
            else if (rline.charAt(0)=='!') {
               if (rline.length()>1) {
                  if (rline.charAt(1)=='*') // skip this line
                     continue;              // because it is a log comment
                  else {
                     if ((idx1=rline.indexOf('$'))==-1) { // regular mac comment
                        macWriter.write(rline);
                        macWriter.newLine();
                        continue;
                     }
                     else
                        idx2 = rline.lastIndexOf('$');
                  }
               }
               else {
                  macWriter.write(rline);
                  macWriter.newLine();
                  continue;
               }
            }
            else {
               idx1 = rline.indexOf('$');
               idx2 = rline.lastIndexOf('$');
               if (idx1 == idx2) {
                  macWriter.write(rline);
                  macWriter.newLine();
                  continue;
               }
            }

            // any rline reaches here should have a tag
            wline = rline.substring(0, idx1);
            akey = rline.substring(idx1+1, idx2).toUpperCase();
            for (int i=0; i<items; ++i) {
               if (isSearched[i]==false &&
                   akey.equals(keys[i])) {
                  if (akey.equals("MACGEN") || akey.equals("MACGEN_VERSION") ||
                      akey.equals("TIME_STAMP") || akey.equals("FILENAME") ||
                      akey.equals("YOKE_STAGES") ||akey.equals("COIL_WINDINGS") ||
                      akey.equals("CURRENT")||akey.equals("ARG1") ||
                      akey.equals("ARG2")||akey.equals("ARG3") ||
                      akey.equals("ARG4"))
                     wline += values[i];
                  else
                     wline += values[i] + "E-3";
                  isSearched[i] = true;
                  wline += rline.substring(idx2+1);
                  macWriter.write(wline);
                  macWriter.newLine();
                  break;
               }
            }
         }
      }
      catch (IOException e) {
         System.err.println("File I/O error: " + e.getMessage());
      }

      try {
         macWriter.flush();
         tmplReader.close();
         macWriter.close();
      }
      catch (IOException e) {
         System.err.println("Close file error: " + e.getMessage());
      }

      check();
   }
}
