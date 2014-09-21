package sdt.FEM;
// SdtVcmMacR2xb.java

//-----------------------------------------------------------------------------
//
// @file      SdtVcmMacR2xb.java
//
// @brief     Class to generate ANSYS script files to perform magnetic circuit
//            analysis of VCM consisting of two piled round magnets, a round
//            top plate (washer), and a yoke.
//
// @version   $Id:$
//
// Copyright (c) 2012 Hon-Hai Technology Group
// See LICENSE file for conditions of use.
//
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.*;

public class SdtVcmMacR2xb extends SdtVcmMacGen
{

   //
   // Default constructor with two arguments.
   //
   // @param tag array to contain tags
   // @param value array to contain corresponding values
   //
   public SdtVcmMacR2xb(String[] tag, String[] value)
   {
      super(tag, value);
   }

   //
   // Implementation of abstract method to write out ANSYS script file by
   // combining template script "r2xb_vcm.mac" and values provided in the
   // default constructor.
   //
   // @param fname file name of the ANSYS script file
   //
   public void output(String path, String fname)
   {
   }
}
