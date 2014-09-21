package sdt.FEM;
// SdtVcmMacT1xb.java

//-----------------------------------------------------------------------------
//
// @file      SdtVcmMacT1xb.java
//
// @brief     Class to generate ANSYS script files to perform magnetic circuit
//            analysis of VCM consisting of a magnet, a top plate (washer), and
//            a yoke.  The three components are all in track-field shape.
//
// @version   $Id:$
//
// Copyright (c) 2012 Hon-Hai Technology Group
// See LICENSE file for conditions of use.
//
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.*;

public class SdtVcmMacT1xb extends SdtVcmMacGen
{

   //
   // Default constructor with two arguments.
   //
   // @param tag array to contain tags
   // @param value array to contain corresponding values
   //
   public SdtVcmMacT1xb(String[] tag, String[] value)
   {
      super(tag, value);
   }

   //
   // Implementation of abstract method to write out ANSYS script file by
   // combining template script "t1xb_vcm.mac" and values provided in the
   // default constructor.
   //
   // @param fname file name of the ANSYS script file
   //
   public void output(String path, String fname)
   {
   }
}
