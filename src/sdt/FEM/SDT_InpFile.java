package sdt.FEM;

import sdt.stepb.stepb_cartesian_point;
import java.io.FileWriter;
import java.io.BufferedWriter;
import sdt.java3d.*;
import java.io.IOException;
import sdt.data.ArrayCartesianPtSetsBrick;
import java.util.ArrayList;
import sdt.stepb.stepb_cartesian_point_array;
import sdt.data.CartesianPointSetsBrick;
import sdt.define.DefineSystemConstant;

public class SDT_InpFile
{
    private SDT_Array3DMesh _arrayMeshCap = null;
    private SDT_Array3DMesh _arrayMeshTransition = null;
    private SDT_Array3DMesh _arrayMeshDiaphragm = null;
    private SDT_Array3DMesh _arrayMeshSurround = null;
    private SDT_Array3DBrick _arrayBrickCoil = null;
    private stepb_cartesian_point[][] _ptSetsShellAll = null;
    private stepb_cartesian_point[] _ptSetsShellCenter = null;
    private ArrayCartesianPtSetsBrick _ptSetsSolidCone = null;
    private ArrayList _arrayBrickAir;
    private ArrayCartesianPtSetsBrick _ptSetsSolidAir = null;
    private int _outestI = -1;

    private boolean isMaterialWrittenCUWIRE = false;
    private boolean isMaterialWrittenPU = false;




    public SDT_InpFile(stepb_cartesian_point[] ptSetsShellCenter,
                       stepb_cartesian_point[][] ptSetsShellAll,
                       ArrayCartesianPtSetsBrick  ptSetsSolidCone,
                       SDT_Array3DMesh arrayMeshCap,
                       SDT_Array3DMesh arrayMeshTransition,
                       SDT_Array3DMesh arrayMeshDiaphragm,
                       SDT_Array3DMesh arrayMeshSurround,
                       SDT_Array3DBrick arrayBrickCoil,

                       ArrayCartesianPtSetsBrick  ptSetsSolidAir,
                       ArrayList arrayBrickAir)
    {
        _ptSetsShellCenter      = ptSetsShellCenter;
        _ptSetsShellAll         = ptSetsShellAll;
        _ptSetsSolidCone        = ptSetsSolidCone;
        _arrayMeshCap           = arrayMeshCap;
        _arrayMeshTransition    = arrayMeshTransition;
        _arrayMeshDiaphragm     = arrayMeshDiaphragm;
        _arrayMeshSurround      = arrayMeshSurround;
        _arrayBrickCoil         = arrayBrickCoil;


        _ptSetsSolidAir         = ptSetsSolidAir;
        _arrayBrickAir          = arrayBrickAir;

    }

    public boolean writeInpFileCone(String path,
                               String elsetNameSurround, double thickSurround, String[] materialSurround,
                               String elsetNameCap, double thickCap, String[] materialCap,
                               String elsetNameTransition, double thickTransition, String[] materialTransition,
                               String elsetNameDiaphragm, double thickDiaphragm, String[] materialDiaphragm,
                               String elsetNameCoil, double thickCoil, String[] materialCoil)
   {
       if (this._ptSetsShellAll == null || this._ptSetsShellCenter == null)
       {
           return false;
       }


       long timeMillis = System.currentTimeMillis();


       try
       {
           FileWriter fw = new FileWriter(path);

           BufferedWriter bw = new BufferedWriter(fw);

           String strToWrite = "";

           strToWrite += "*HEADING \n";
           strToWrite += "\n";
           strToWrite += "*NODE, NSET=NALL \n";

           bw.write(strToWrite, 0, strToWrite.length());
           strToWrite = "";

           System.out.println("          Writing Cartesian Points.....");

           int iCount = _ptSetsShellAll[0].length;
           int kjCount = _ptSetsShellAll.length;
           _outestI = iCount -1;

           writePoints(bw,  this._ptSetsShellCenter);
           writePoints(bw,  this._ptSetsShellAll);
           for(int i = 0 ; i < this._ptSetsSolidCone.Size() ; i++)
           {
               this._ptSetsSolidCone.get(i).writePoints(bw);
           }

          writeElementShell(_arrayMeshSurround, elsetNameSurround, bw);
           writeElementShell(_arrayMeshCap, elsetNameCap, bw);
           writeElementShell(_arrayMeshTransition, elsetNameTransition, bw);
           if (_arrayMeshDiaphragm != null)
           {
               writeElementShell(_arrayMeshDiaphragm, elsetNameDiaphragm, bw);
           }

           writeElementBrick(_arrayBrickCoil, elsetNameCoil, bw, false);

           writeSectionShell(elsetNameSurround, thickSurround, materialSurround[0], bw);
           writeSectionShell(elsetNameCap, thickCap, materialCap[0], bw);
           writeSectionShell(elsetNameTransition, thickTransition, materialTransition[0], bw);
           if (_arrayMeshDiaphragm != null)
           {
               writeSectionShell(elsetNameDiaphragm, thickDiaphragm, materialDiaphragm[0], bw);
           }

           writeSectionSolid(elsetNameCoil, thickCoil, materialCoil[0], bw);
           writeMaterial(0, materialSurround, bw);

           if(!materialSurround[0].trim().equals(materialCap[0].trim()))
               writeMaterial(0, materialCap, bw);

           if(!materialSurround[0].trim().equals(materialTransition[0].trim()) &&
              !materialCap[0].trim().equals(materialTransition[0].trim()))
               writeMaterial(0, materialTransition, bw);

           if (_arrayMeshDiaphragm != null)
           {

               if (!materialSurround[0].trim().equals(materialDiaphragm[0].trim()) &&
                   !materialCap[0].trim().equals(materialDiaphragm[0].trim()) &&
                   !materialTransition[0].trim().equals(materialDiaphragm[0].trim()))
               writeMaterial(0, materialDiaphragm, bw);
           }
           if (!materialSurround[0].trim().equals(materialCoil[0].trim()) &&
               !materialCap[0].trim().equals(materialCoil[0].trim()) &&
               !materialDiaphragm[0].trim().equals(materialCoil[0].trim()) &&
               !materialTransition[0].trim().equals(materialCoil[0].trim()))
           writeMaterial(0, materialCoil, bw);

           strToWrite += "**" + "\n";
           strToWrite += "**" + "\n";
           strToWrite += "*NSET, NSET=FIXED-BC, GENERATE" + "\n";
           strToWrite += _ptSetsShellAll[0][_outestI].GetIDNumber() + ", " + _ptSetsShellAll[kjCount - 1][_outestI].GetIDNumber() + "," + iCount + "\n";
           strToWrite += "*BOUNDARY" + "\n";
           strToWrite += " FIXED-BC, 1, 6, 0" + "\n";

           strToWrite += "**" + "\n";
           strToWrite += "**" + "\n";
           strToWrite += "*Step, name=Step-1, perturbation" + "\n";
           //strToWrite += "*Frequency, eigensolver=Lanczos, normalization=displacement" + "\n";
           strToWrite += "*Frequency, eigensolver=Lanczos, normalization=mass" + "\n";
           strToWrite += ", 20, 20000, , , " + "\n";
           strToWrite += "** " + "\n";
           strToWrite += "** OUTPUT REQUESTS" + "\n";
           strToWrite += "** " + "\n";
           strToWrite += "*Restart, write, frequency=0" + "\n";
           strToWrite += "** " + "\n";
           strToWrite += "** FIELD OUTPUT: F-Output-1" + "\n";
           strToWrite += "** " + "\n";
           strToWrite += "*node print, nset=NALL" + "\n";
           strToWrite += "U1,U2,U3" + "\n";
           strToWrite += "*Output, field, variable=PRESELECT" + "\n";
           strToWrite += "*End Step" + "\n";

           bw.write(strToWrite, 0, strToWrite.length());
           bw.close();

           System.out.println("End Input file Output output");
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

       long timeWriteInpFile = System.currentTimeMillis() - timeMillis;
       java.sql.Timestamp dateTime = new java.sql.Timestamp(timeWriteInpFile);
       String timeStrWriteInpFile = dateTime.toString().substring(14) + "";
       System.out.println("End Write Mesh Inp file....." + timeStrWriteInpFile);
       System.out.println("=============================");
       return true;
   }
   public boolean writeInpFileConeStatic(String path,
                             String elsetNameSurround, double thickSurround, String[] materialSurround,
                             String elsetNameCap, double thickCap, String[] materialCap,
                             String elsetNameTransition, double thickTransition, String[] materialTransition,
                             String elsetNameDiaphragm, double thickDiaphragm, String[] materialDiaphragm,
                             String elsetNameCoil, double thickCoil, String[] materialCoil)
 {
     if (this._ptSetsShellAll == null || this._ptSetsShellCenter == null)
     {
         return false;
     }
     long timeMillis = System.currentTimeMillis();
     try
     {
         String path2 = path.substring(0,path.length() -4);
         path2 = path2 +"_Static.inp";

         FileWriter fw = new FileWriter(path2);

         BufferedWriter bw = new BufferedWriter(fw);

         String strToWrite = "";

         strToWrite += "*HEADING \n";
         strToWrite += "\n";
         strToWrite += "*NODE, NSET=NALL \n";

         bw.write(strToWrite, 0, strToWrite.length());
         strToWrite = "";

         System.out.println("          Writing Cartesian Points.....");

         int iCount = _ptSetsShellAll[0].length;
         int kjCount = _ptSetsShellAll.length;
         _outestI = iCount -1;

         writePoints(bw,  this._ptSetsShellCenter);
         writePoints(bw,  this._ptSetsShellAll);
         for(int i = 0 ; i < this._ptSetsSolidCone.Size() ; i++)
         {
             this._ptSetsSolidCone.get(i).writePoints(bw);
         }

        writeElementShell(_arrayMeshSurround, elsetNameSurround, bw);
         writeElementShell(_arrayMeshCap, elsetNameCap, bw);
         writeElementShell(_arrayMeshTransition, elsetNameTransition, bw);
         if (_arrayMeshDiaphragm != null)
         {
             writeElementShell(_arrayMeshDiaphragm, elsetNameDiaphragm, bw);
         }

         writeElementBrick(_arrayBrickCoil, elsetNameCoil, bw, false);

         writeSectionShell(elsetNameSurround, thickSurround, materialSurround[0], bw);
         writeSectionShell(elsetNameCap, thickCap, materialCap[0], bw);
         writeSectionShell(elsetNameTransition, thickTransition, materialTransition[0], bw);
         if (_arrayMeshDiaphragm != null)
         {
             writeSectionShell(elsetNameDiaphragm, thickDiaphragm, materialDiaphragm[0], bw);
         }

         writeSectionSolid(elsetNameCoil, thickCoil, materialCoil[0], bw);

         strToWrite += "*Amplitude, name=Amp-1" + "\n";
         strToWrite += " 0., 0., 0.25, 1., 0.5, 0., 0.75, -1." + "\n";
         strToWrite += " 1., 0. " + "\n";
         bw.write(strToWrite, 0, strToWrite.length());
         bw.flush();
         strToWrite = "";



         writeMaterial(0, materialSurround, bw);

         if(!materialSurround[0].trim().equals(materialCap[0].trim()))
             writeMaterial(0, materialCap, bw);

         if(!materialSurround[0].trim().equals(materialTransition[0].trim()) &&
            !materialCap[0].trim().equals(materialTransition[0].trim()))
             writeMaterial(0, materialTransition, bw);

         if (_arrayMeshDiaphragm != null)
         {

             if (!materialSurround[0].trim().equals(materialDiaphragm[0].trim()) &&
                 !materialCap[0].trim().equals(materialDiaphragm[0].trim()) &&
                 !materialTransition[0].trim().equals(materialDiaphragm[0].trim()))
             writeMaterial(0, materialDiaphragm, bw);
         }
         if (!materialSurround[0].trim().equals(materialCoil[0].trim()) &&
             !materialCap[0].trim().equals(materialCoil[0].trim()) &&
             !materialDiaphragm[0].trim().equals(materialCoil[0].trim()) &&
             !materialTransition[0].trim().equals(materialCoil[0].trim()))
         writeMaterial(0, materialCoil, bw);

         strToWrite += "**" + "\n";
         strToWrite += "**" + "\n";
         strToWrite += "*NSET, NSET=FIXED-BC, GENERATE" + "\n";
         strToWrite += _ptSetsShellAll[0][_outestI].GetIDNumber() + ", " + _ptSetsShellAll[kjCount - 1][_outestI].GetIDNumber() + "," + iCount + "\n";
         strToWrite += "*BOUNDARY" + "\n";
         strToWrite += " FIXED-BC, 1, 6, 0" + "\n";

         strToWrite += "**" + "\n";
         strToWrite += "**" + "\n";
         strToWrite += "*Step, name=Step-1, nlgeom=YES" + "\n";
         strToWrite += "*Static" + "\n";;
         strToWrite += "0.025, 1., 1e-05, 0.025" + "\n";
         stepb_cartesian_point[][][] ptArray = _ptSetsSolidCone.get(0).getPtSets();
         int jCount = ptArray.length;

         strToWrite += "*NSET, NSET=CoilBottom" + "\n";
         strToWrite += ptArray[0][0][0].GetIDNumber() + ", " + ptArray[jCount / 4][0][0].GetIDNumber() + "," + ptArray[jCount / 2][0][0].GetIDNumber() + "," +
                 ptArray[jCount * 3 / 4][0][0].GetIDNumber() + "\n";

         strToWrite += "*BOUNDARY, amplitude=Amp-1" + "\n";
         strToWrite += "CoilBottom, 3, 3, 0.5" + "\n";

         strToWrite += "** " + "\n";
         strToWrite += "** OUTPUT REQUESTS" + "\n";
         strToWrite += "** " + "\n";
         strToWrite += "*Restart, write, frequency=0" + "\n";
         strToWrite += "** " + "\n";
         strToWrite += "** FIELD OUTPUT: F-Output-1" + "\n";
         strToWrite += "** " + "\n";
         strToWrite += "*node print, nset=FIXED-BC , TOTAL=YES" + "\n";
         strToWrite += "RF3" + "\n";
         strToWrite += "*node print, nset=CoilBottom" + "\n";
         strToWrite += "U3" + "\n";
         strToWrite += "*Output, field, variable=PRESELECT" + "\n";
         strToWrite += "*End Step" + "\n";

         bw.write(strToWrite, 0, strToWrite.length());
         bw.close();

         System.out.println("End Input file Output output");
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }

     long timeWriteInpFile = System.currentTimeMillis() - timeMillis;
     java.sql.Timestamp dateTime = new java.sql.Timestamp(timeWriteInpFile);
     String timeStrWriteInpFile = dateTime.toString().substring(14) + "";
     System.out.println("End Write Mesh Inp file....." + timeStrWriteInpFile);
     System.out.println("=============================");
     return true;
 }
 // For Debug
   public boolean writeInpFileAir(String path, String elsetNameAir,  String materialAir)
   {
       System.out.println("=============================");
       System.out.println("Start Write Data Inp file.....");
       long timeMillis = System.currentTimeMillis();

       if (this._ptSetsShellAll == null || this._ptSetsShellCenter == null)
       {
           return false;
       }
       try
       {
           path = path.replaceAll(".inp","_air.inp");

           FileWriter fw = new FileWriter(path);

           BufferedWriter bw = new BufferedWriter(fw);

           String strToWrite = "";

           strToWrite += "*HEADING \n";
           strToWrite += "\n";
           strToWrite += "*NODE, NSET=NALL \n";

           bw.write(strToWrite, 0, strToWrite.length());
           strToWrite = "";




           for(int i = 0 ; i < this._ptSetsSolidAir.Size() ; i++)
           {
               this._ptSetsSolidAir.get(i).writePoints(bw);
           }

           for(int i = 0 ; i < this._arrayBrickAir.size() ; i++)
           {

                SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)_arrayBrickAir.get(i);
                 writeElementBrick(arrayBrickAirSingle, elsetNameAir+"--> Zone" + arrayBrickAirSingle.getTypeID(), bw, true);
           }





           writeSectionSolid(elsetNameAir, 0, materialAir, bw);

           writeMaterialByName(materialAir, bw);


           bw.close();

           System.out.println("End Input file Output output");
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

       long timeWriteInpFile = System.currentTimeMillis() - timeMillis;
       java.sql.Timestamp dateTime = new java.sql.Timestamp(timeWriteInpFile);
       String timeStrWriteInpFile = dateTime.toString().substring(14) + "";
       System.out.println("End Write Mesh Inp file....." + timeStrWriteInpFile);
       System.out.println("=============================");

       return true;
   }
   // For Debug   New means "with Part Info"
   public boolean writeInpFileAirNew(String path, String elsetNameAir, String materialAir)
   {
       System.out.println("=============================");
       System.out.println("Start Write Data Inp file.....");
       long timeMillis = System.currentTimeMillis();

       if (this._ptSetsShellAll == null || this._ptSetsShellCenter == null)
       {
           return false;
       }
       try
       {
           path = path.replaceAll(".inp", "_AirNew.inp");

           FileWriter fw = new FileWriter(path);

           BufferedWriter bw = new BufferedWriter(fw);

           String strToWrite = "";







           strToWrite += "*Heading \n";
           strToWrite += "SDT_Model\n";
           strToWrite += "** Job name: MyModel Model name: MyFirstModel \n";
           strToWrite += "** Generated by: SDT 20120927 \n";
           strToWrite += "*Preprint, echo=NO, model=NO, history=NO, contact=NO \n";
           strToWrite += "** \n";
           strToWrite += "** PARTS \n";
           strToWrite += "** \n";
           strToWrite += "*Part, name=" + elsetNameAir + "\n";
           strToWrite += "*End Part \n";
           bw.write(strToWrite, 0, strToWrite.length());
           strToWrite = "";


strToWrite += "** \n";
strToWrite += "** \n";
strToWrite += "** ASSEMBLY \n";
strToWrite += "** \n";
strToWrite += "*Assembly, name=Assembly \n";
strToWrite += "** \n";
strToWrite += "** \n";
strToWrite += "*Instance, name="+elsetNameAir + "-1"+", part=" + elsetNameAir+ "\n";
strToWrite += "*NODE, NSET=NALL \n";
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

           for (int i = 0; i < this._ptSetsSolidAir.Size(); i++)
           {
               this._ptSetsSolidAir.get(i).writePoints(bw);
           }

           for (int i = 0; i < this._arrayBrickAir.size(); i++)
           {

               SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick) _arrayBrickAir.get(i);

               writeElementBrick(arrayBrickAirSingle, elsetNameAir + "-->Zone" + arrayBrickAirSingle.getTypeID(), bw, true);
           }

           writeSectionSolid(elsetNameAir, 0, materialAir, bw);

           strToWrite += "*End Instance \n";
           strToWrite += "*End Assembly \n";
           bw.write(strToWrite, 0, strToWrite.length());
           strToWrite = "";
           writeMaterialByName(materialAir, bw);

           /*strToWrite += "**" + "\n";
                     strToWrite += "**" + "\n";
                     strToWrite += "*NSET, NSET=FIXED-BC, GENERATE" + "\n";
                     strToWrite += _ptSetsShellAll[0][_outestI].GetIDNumber() + ", " + _ptSetsShellAll[kjCount - 1][_outestI].GetIDNumber() + "," + iCount + "\n";
                     strToWrite += "*BOUNDARY" + "\n";
                     strToWrite += " FIXED-BC, 1, 6, 0" + "\n";

                     strToWrite += "**" + "\n";
                     strToWrite += "**" + "\n";
                     strToWrite += "*Step, name=Step-1, perturbation" + "\n";
                     //strToWrite += "*Frequency, eigensolver=Lanczos, normalization=displacement" + "\n";
                     strToWrite += "*Frequency, eigensolver=Lanczos, normalization=mass" + "\n";
                     strToWrite += ", 20, 20000, , , " + "\n";
                     strToWrite += "** " + "\n";
                     strToWrite += "** OUTPUT REQUESTS" + "\n";
                     strToWrite += "** " + "\n";
                     strToWrite += "*Restart, write, frequency=0" + "\n";
                     strToWrite += "** " + "\n";
                     strToWrite += "** FIELD OUTPUT: F-Output-1" + "\n";
                     strToWrite += "** " + "\n";
                     strToWrite += "*node print, nset=NALL" + "\n";
                     strToWrite += "U1,U2,U3" + "\n";
                     strToWrite += "*Output, field, variable=PRESELECT" + "\n";
                     strToWrite += "*End Step" + "\n";
                     bw.write(strToWrite, 0, strToWrite.length());
            */
           bw.close();

           System.out.println("End Input file Output output");
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

       long timeWriteInpFile = System.currentTimeMillis() - timeMillis;
       java.sql.Timestamp dateTime = new java.sql.Timestamp(timeWriteInpFile);
       String timeStrWriteInpFile = dateTime.toString().substring(14) + "";
       System.out.println("End Write Mesh Inp file....." + timeStrWriteInpFile);
       System.out.println("=============================");

       return true;
   }






   public boolean writeInpFileConeAir(String path,
                             String elsetNameSurround, double thickSurround, String[] materialSurround,
                             String elsetNameCap, double thickCap, String[] materialCap,
                             String elsetNameTransition, double thickTransition, String[] materialTransition,
                             String elsetNameDiaphragm, double thickDiaphragm, String[] materialDiaphragm,
                             String elsetNameCoil, double thickCoil, String[] materialCoil,
                             String elsetNameAir, String materialAir, boolean isPortSealed, int portLength)
   {
     System.out.println("=============================");
     System.out.println("Start Write Data Inp file.....");
     long timeMillis = System.currentTimeMillis();

     if (this._ptSetsShellAll == null || this._ptSetsShellCenter == null)
     {
         return false;
     }
     try
     {
         FileWriter fw = new FileWriter(path);

         BufferedWriter bw = new BufferedWriter(fw);

         String strToWrite = "";

         strToWrite += "*HEADING \n";
         strToWrite += "\n";
         strToWrite += "*NODE, NSET=NALL \n";

         bw.write(strToWrite, 0, strToWrite.length());
         strToWrite = "";

         System.out.println("          Writing Cartesian Points.....");

         int iCount = _ptSetsShellAll[0].length;
         int kjCount = _ptSetsShellAll.length;
         _outestI = iCount -1;

         writePoints(bw,  this._ptSetsShellCenter);
         writePoints(bw,  this._ptSetsShellAll);
         for(int i = 0 ; i < this._ptSetsSolidCone.Size() ; i++)
         {
             this._ptSetsSolidCone.get(i).writePoints(bw);
         }
         for(int i = 0 ; i < this._ptSetsSolidAir.Size() ; i++)
         {
             this._ptSetsSolidAir.get(i).writePoints(bw);
         }

         writeElementShell(_arrayMeshSurround, elsetNameSurround, bw);
         writeElementShell(_arrayMeshCap, elsetNameCap, bw);
         writeElementShell(_arrayMeshTransition, elsetNameTransition, bw);
         if (_arrayMeshDiaphragm != null)
         {
             writeElementShell(_arrayMeshDiaphragm, elsetNameDiaphragm, bw);
         }

         writeElementBrick(_arrayBrickCoil, elsetNameCoil, bw, false);
         for (int i = 0; i < this._arrayBrickAir.size(); i++)
         {

             SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick) _arrayBrickAir.get(i);
             writeElementBrick(arrayBrickAirSingle, elsetNameAir + "-->Zone" + arrayBrickAirSingle.getTypeID(), bw, true);
         }

strToWrite += "** \n";
strToWrite += "*Elset, elset=CONE2AIRFINE_INNER  \n";
           for(int i = 0 ; i < this._arrayMeshCap.Size() ; i++)
           {
               strToWrite += (this._arrayMeshCap.get(i).getElementID() + " , ");
               if (i % 8 == 7)
                   strToWrite += "\n";
           }
           strToWrite += "\n";

          for (int i = 0; i < this._arrayMeshTransition.Size(); i+=3)
           {
                   strToWrite += (this._arrayMeshTransition.get(i).getElementID() + " , ");
                   if (i % 24 == 21)
                       strToWrite += "\n";
           }

           if (strToWrite.endsWith(", "))
           {
               strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
               strToWrite += "\n";
           }
           else if (strToWrite.endsWith(", \n"))
           {
               strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
               strToWrite += "\n";
           }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=CONE2AIRFINE_OUTER \n";

         for (int i = 2; i < this._arrayMeshTransition.Size(); i += 3)
         {
             strToWrite += (this._arrayMeshTransition.get(i).getElementID() + ", ");
             if (i % 24 == 23)
                 strToWrite += "\n";
         }
         strToWrite += "\n";

         if (_arrayMeshDiaphragm != null)
         {
             for (int i = 0; i < this._arrayMeshDiaphragm.Size(); i++)
             {

                 strToWrite += (this._arrayMeshDiaphragm.get(i).getElementID() + ", ");
                 if (i % 8 == 7)
                     strToWrite += "\n";
             }
             strToWrite += "\n";
         }

         for (int i = 0; i < this._arrayMeshSurround.Size(); i++)
         {
             strToWrite += (this._arrayMeshSurround.get(i).getElementID() + ", ");
             if (i % 8 == 7)
                 strToWrite += "\n";
         }
         strToWrite.trim();

         if (strToWrite.endsWith(", "))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
             strToWrite += "\n";
         }
         else if (strToWrite.endsWith(", \n"))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
             strToWrite += "\n";
         }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

         strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRFINE2CONE_INNER \n";
         for (int i = 0; i < this._arrayBrickAir.size(); i++)
         {
             SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
             if (arrayBrickAirSingle.getBrickType() % DefineSystemConstant.BRICKARRAY_TIE_UP == 0)
             {
                 strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle,DefineSystemConstant.TIEFACE_AIRFINE2CONE_INNER);
                 if(!strToWrite.endsWith("\n"))
                     strToWrite += "\n";
             }
         }
         if (strToWrite.endsWith(", "))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
             strToWrite += "\n";
         }
         else if (strToWrite.endsWith(", \n"))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
             strToWrite += "\n";
         }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRFINE2COARSE_INNER \n";
         for (int i = 0; i < this._arrayBrickAir.size(); i++)
         {
             SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
             if (arrayBrickAirSingle.getBrickType() % DefineSystemConstant.BRICKARRAY_TIE_DOWN == 0)
             {
                 strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle,DefineSystemConstant.TIEFACE_AIRFINE2COARSE_INNER);
                 if(!strToWrite.endsWith("\n"))
                     strToWrite += "\n";
             }
         }
         if (strToWrite.endsWith(", "))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
             strToWrite += "\n";
         }
         else if (strToWrite.endsWith(", \n"))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
             strToWrite += "\n";
         }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRFINE2CONE_OUTER \n";
         for (int i = 0; i < this._arrayBrickAir.size(); i++)
         {
             SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
             if (arrayBrickAirSingle.getBrickType() % DefineSystemConstant.BRICKARRAY_TIE_UP == 0)
             {
                 strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.TIEFACE_AIRFINE2CONE_OUTER);
                 if (!strToWrite.endsWith("\n"))
                     strToWrite += "\n";
             }
         }
         if (strToWrite.endsWith(", "))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
             strToWrite += "\n";
         }
         else if (strToWrite.endsWith(", \n"))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
             strToWrite += "\n";
         }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRFINE2COARSE_OUTER \n";
         for (int i = 0; i < this._arrayBrickAir.size(); i++)
         {
             SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
             if (arrayBrickAirSingle.getBrickType() % DefineSystemConstant.BRICKARRAY_TIE_DOWN == 0)
             {
                 strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.TIEFACE_AIRFINE2COARSE_OUTER);
                 if (!strToWrite.endsWith("\n"))
                     strToWrite += "\n";
             }
         }
         if (strToWrite.endsWith(", "))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
             strToWrite += "\n";
         }
         else if (strToWrite.endsWith(", \n"))
         {
             strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
             strToWrite += "\n";
         }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";


strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRCOARSE2FINE_OUTER \n";
         for (int i = 0; i < this._arrayBrickAir.size(); i++)
         {
             SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
             if (arrayBrickAirSingle.getBrickType() % DefineSystemConstant.BRICKARRAY_TIE_UP == 0)
             {
                 strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.TIEFACE_AIRCOARSE2FINE_OUTER);
                 if (!strToWrite.endsWith("\n"))
                     strToWrite += "\n";
             }
        }
        if (strToWrite.endsWith(", "))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
            strToWrite += "\n";
        }
        else if (strToWrite.endsWith(", \n"))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
            strToWrite += "\n";
        }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";


strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRCOARSE2FINE_INNER \n";
         for (int i = 0; i < this._arrayBrickAir.size(); i++)
         {
             SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
             if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.BRICKARRAY_TIE_UP )
             {
                 strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.TIEFACE_AIRCOARSE2FINE_INNER);
                 if (!strToWrite.endsWith("\n"))
                     strToWrite += "\n";
             }
       }
       if (strToWrite.endsWith(", "))
       {
           strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
           strToWrite += "\n";
       }
       else if (strToWrite.endsWith(", \n"))
       {
           strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
           strToWrite += "\n";
       }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRFINE2COIL_INNER \n";
        for (int i = 0; i < this._arrayBrickAir.size(); i++)
        {
           SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
           if (arrayBrickAirSingle.getBrickType() % DefineSystemConstant.BRICKARRAY_TIE_OUTER == 0)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.TIEFACE_AIRFINE2COIL_INNER);
               if (!strToWrite.endsWith("\n"))
                   strToWrite += "\n";
           }
        }
        if (strToWrite.endsWith(", "))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
            strToWrite += "\n";
        }
        else if (strToWrite.endsWith(", \n"))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
            strToWrite += "\n";
        }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRCOARSE2COIL_INNER \n";
        for (int i = 0; i < this._arrayBrickAir.size(); i++)
        {
            SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
            if (arrayBrickAirSingle.getBrickType() % DefineSystemConstant.BRICKARRAY_TIE_OUTER == 0)
            {
                strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.TIEFACE_AIRCOARSE2COIL_INNER);
                if (!strToWrite.endsWith("\n"))
                    strToWrite += "\n";
            }
        }
        if (strToWrite.endsWith(", "))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
            strToWrite += "\n";
        }
        else if (strToWrite.endsWith(", \n"))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
            strToWrite += "\n";
        }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRFINE2COIL_OUTER \n";
        for (int i = 0; i < this._arrayBrickAir.size(); i++)
        {
            SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
            if (arrayBrickAirSingle.getBrickType() % DefineSystemConstant.BRICKARRAY_TIE_INNER == 0)
            {
                strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.TIEFACE_AIRFINE2COIL_OUTER);
                if (!strToWrite.endsWith("\n"))
                    strToWrite += "\n";
            }
        }
        if (strToWrite.endsWith(", "))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
            strToWrite += "\n";
        }
        else if (strToWrite.endsWith(", \n"))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
            strToWrite += "\n";
        }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";



strToWrite += "** \n";
strToWrite += "*Elset, elset=AIRCOARSE2COIL_OUTER \n";
        for (int i = 0; i < this._arrayBrickAir.size(); i++)
        {
            SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);
            if (arrayBrickAirSingle.getBrickType() % DefineSystemConstant.BRICKARRAY_TIE_INNER == 0)
            {
                strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.TIEFACE_AIRCOARSE2COIL_OUTER);
                if (!strToWrite.endsWith("\n"))
                    strToWrite += "\n";
            }
        }
        if (strToWrite.endsWith(", "))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
            strToWrite += "\n";
        }
        else if (strToWrite.endsWith(", \n"))
        {
            strToWrite = strToWrite.substring(0, strToWrite.length() - 3);
            strToWrite += "\n";
        }

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";



strToWrite += "*Surface, type=ELEMENT, name=CONE2AIRFINE_SURFACEINNER \n";
strToWrite += "CONE2AIRFINE_INNER, SPOS \n";
strToWrite += "*Surface, type=ELEMENT, name=CONE2AIRFINE_SURFACEOUTER \n";
strToWrite += "CONE2AIRFINE_OUTER, SPOS \n";

strToWrite += "*Surface, type=ELEMENT, name=AIRFINE2CONE_SURFACEINNER \n";
strToWrite += "AIRFINE2CONE_INNER, S4 \n";
strToWrite += "*Surface, type=ELEMENT, name=AIRFINE2CONE_SURFACEOUTER \n";
strToWrite += "AIRFINE2CONE_OUTER, S4 \n";

strToWrite += "*Surface, type=ELEMENT, name=AIRFINE2COARSE_SURFACEINNER \n";
strToWrite += "AIRFINE2COARSE_INNER, S6 \n";
strToWrite += "*Surface, type=ELEMENT, name=AIRFINE2COARSE_SURFACEOUTER \n";
strToWrite += "AIRFINE2COARSE_OUTER, S6 \n";

strToWrite += "*Surface, type=ELEMENT, name=AIRCOARSE2FINE_SURFACEINNER \n";
strToWrite += "AIRCOARSE2FINE_INNER, S3 \n";
strToWrite += "*Surface, type=ELEMENT, name=AIRCOARSE2FINE_SURFACEOUTER \n";
strToWrite += "AIRCOARSE2FINE_OUTER, S3 \n";

strToWrite += "*Surface, type=ELEMENT, name=AIRFINE2COIL_SURFACEINNER \n";
strToWrite += "AIRFINE2COIL_INNER, S5 \n";
strToWrite += "*Surface, type=ELEMENT, name=AIRFINE2COIL_SURFACEOUTER \n";
strToWrite += "AIRFINE2COIL_OUTER, S3 \n";
strToWrite += "*Surface, type=ELEMENT, name=AIRCOARSE2COIL_SURFACEINNER \n";
strToWrite += "AIRCOARSE2COIL_INNER, S4 \n";
strToWrite += "*Surface, type=ELEMENT, name=AIRCOARSE2COIL_SURFACEOUTER \n";
strToWrite += "AIRCOARSE2COIL_OUTER, S6 \n";


strToWrite += "*Surface, type=ELEMENT, name=COIL2AIR_SURFACEINNER \n";
strToWrite += elsetNameCoil + ", S6 \n";
strToWrite += "*Surface, type=ELEMENT, name=COIL2AIR_SURFACEOUTER \n";
strToWrite += elsetNameCoil + ", S4 \n";



strToWrite += "*Tie, name=Constraint-ConeToAirDense-Inner, adjust=no \n";
strToWrite += "AIRFINE2CONE_SURFACEINNER, CONE2AIRFINE_SURFACEINNER \n";
strToWrite += "*Tie, name=Constraint-ConeToAirDense-Outer, adjust=no \n";
strToWrite += "AIRFINE2CONE_SURFACEOUTER, CONE2AIRFINE_SURFACEOUTER \n";

strToWrite += "*Tie, name=Constraint-AirDenseToSprase-Inner, adjust=no \n";
strToWrite += "AIRCOARSE2FINE_SURFACEINNER, AIRFINE2COARSE_SURFACEINNER \n";
strToWrite += "*Tie, name=Constraint-AirDenseToSprase-Outer, adjust=no \n";
strToWrite += "AIRCOARSE2FINE_SURFACEOUTER, AIRFINE2COARSE_SURFACEOUTER \n";

strToWrite += "*Tie, name=Constraint-AirToCoilUp-Inner, adjust=no \n";
strToWrite += "AIRFINE2COIL_SURFACEINNER ,COIL2AIR_SURFACEINNER \n";
strToWrite += "*Tie, name=Constraint-AirToCoilDown-Inner, adjust=no \n";
strToWrite += "AIRCOARSE2COIL_SURFACEINNER, COIL2AIR_SURFACEINNER  \n";


strToWrite += "*Tie, name=Constraint-AirToCoilUp-Outer, adjust=no \n";
strToWrite += "AIRFINE2COIL_SURFACEOUTER, COIL2AIR_SURFACEOUTER \n";
strToWrite += "*Tie, name=Constraint-AirToCoilDown-Outer, adjust=no \n";
strToWrite += "AIRCOARSE2COIL_SURFACEOUTER ,COIL2AIR_SURFACEOUTER \n";

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

         writeSectionShell(elsetNameSurround, thickSurround, materialSurround[0], bw);
         writeSectionShell(elsetNameCap, thickCap, materialCap[0], bw);
         writeSectionShell(elsetNameTransition, thickTransition, materialTransition[0], bw);
         if (_arrayMeshDiaphragm != null)
         {
             writeSectionShell(elsetNameDiaphragm, thickDiaphragm, materialDiaphragm[0], bw);
         }

         writeSectionSolid(elsetNameCoil, thickCoil, materialCoil[0], bw);

         for (int i = 0; i < this._arrayBrickAir.size(); i++)
         {
             SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);

              writeSectionSolid(elsetNameAir + "-->Zone" + arrayBrickAirSingle.getTypeID(),0, materialAir, bw);
         }



         writeMaterial(0, materialSurround, bw);
         if (!materialSurround[0].trim().equals(materialCap[0].trim()))
             writeMaterial(0, materialCap, bw);

         if (!materialSurround[0].trim().equals(materialTransition[0].trim()) &&
             !materialCap[0].trim().equals(materialTransition[0].trim()))
             writeMaterial(0, materialTransition, bw);


         if (_arrayMeshDiaphragm != null)
         {
             if (!materialSurround[0].trim().equals(materialDiaphragm[0].trim()) &&
                 !materialCap[0].trim().equals(materialDiaphragm[0].trim()) &&
                 !materialTransition[0].trim().equals(materialDiaphragm[0].trim()))
                 writeMaterial(0, materialDiaphragm, bw);
         }

         if (!materialSurround[0].trim().equals(materialCoil[0].trim()) &&
             !materialCap[0].trim().equals(materialCoil[0].trim()) &&
             !materialDiaphragm[0].trim().equals(materialCoil[0].trim()) &&
             !materialTransition[0].trim().equals(materialCoil[0].trim()))
             writeMaterial(0, materialCoil, bw);

         writeMaterial(1, null, bw);//air



/*
         writeMaterialByName(materialSurround, bw);
         writeMaterialByName(materialCap, bw);
         writeMaterialByName(materialTransition, bw);
         writeMaterialByName(materialAir, bw);
         if (_arrayMeshDiaphragm != null)
         {
             writeMaterialByName(materialDiaphragm, bw);
         }
         writeMaterialByName(materialCoil, bw);*/

         strToWrite += "**" + "\n";
         strToWrite += "**" + "\n";
         strToWrite += "*NSET, NSET=FIXED-BC, GENERATE" + "\n";
         strToWrite += _ptSetsShellAll[0][_outestI].GetIDNumber() + ", " + _ptSetsShellAll[kjCount - 1][_outestI].GetIDNumber() + "," + iCount + "\n";
         strToWrite += "** Name: BC-Fixed Type: Displacement/Rotation \n";
         strToWrite += "*BOUNDARY" + "\n";
         strToWrite += " FIXED-BC, 1, 6, 0" + "\n";



         CartesianPointSetsBrick brickSetsOutest= null;
         CartesianPointSetsBrick brickSetsZone22 = null;
         CartesianPointSetsBrick brickSetsZone20 = null;
         CartesianPointSetsBrick brickSetsZone18= null;
         for (int i = 0; i < _ptSetsSolidAir.Size(); i++)
         {
             CartesianPointSetsBrick brickSets = _ptSetsSolidAir.get(i);
             if (brickSets.getSectionType() == DefineSystemConstant.SECTION_AIR_ZONE18)
             {
                 brickSetsZone18 = brickSets;
             }
             else if (brickSets.getSectionType() == DefineSystemConstant.SECTION_AIR_ZONE20)
             {
                 brickSetsZone20 = brickSets;
             }
             else if (brickSets.getSectionType() == DefineSystemConstant.SECTION_AIR_ZONE22)
             {
                 brickSetsZone22 = brickSets;
                 break;
             }
         }

         if (brickSetsZone22 != null)
             brickSetsOutest = brickSetsZone22;
         else if (brickSetsZone20 != null)
             brickSetsOutest = brickSetsZone20;
         else if (brickSetsZone18 != null)
             brickSetsOutest = brickSetsZone18;


         /*air port setting*/
         if (!isPortSealed)
         {
             stepb_cartesian_point[][][] pts = brickSetsOutest.getPtSets();

             int poreNumber = 6;
             int poreDistance = pts.length / poreNumber;
             int iLast = pts[0].length - 1;                 //last layer
             int tLastThird = pts[0][0].length - 1;         //outest circle
             int poreLength = 4;


             strToWrite += "*NSET, NSET=PORE-SET" + "\n";
             for (int i = 0; i < poreNumber; i++)
             {
                 if(poreLength>=2)
                 {
                     for (int j = 0; j < poreLength; j++)
                     {
                         strToWrite += pts[poreDistance * i + j][iLast][tLastThird].GetIDNumber() + " , ";
                     }
                     for (int j = poreLength - 1; j >= 0; j--)
                     {
                         if(j != 0)
                             strToWrite += pts[poreDistance * i + j][iLast][tLastThird - 1].GetIDNumber() + " , ";
                         else
                             strToWrite += pts[poreDistance * i + j][iLast][tLastThird - 1].GetIDNumber() + " \n ";
                     }
                 }
                 else
                     strToWrite += pts[poreDistance * i][iLast][tLastThird].GetIDNumber() + " \n ";
             }
             strToWrite += "** Name: BC-AirPore Type: Acoustic pressure \n";
             strToWrite += "*BOUNDARY" + "\n";
             strToWrite += " PORE-SET, 8, 8" + "\n";
         }
         //End Air Port Setting//

         strToWrite += "**" + "\n";
         strToWrite += "**" + "\n";
         strToWrite += "*Step, name=Step-1, perturbation" + "\n";
         //strToWrite += "*Frequency, eigensolver=Lanczos, normalization=displacement" + "\n";
         strToWrite += "*Frequency, eigensolver=Lanczos, normalization=mass" + "\n";
         strToWrite += ", 20, 20000, , , " + "\n";
         strToWrite += "** " + "\n";
         strToWrite += "** OUTPUT REQUESTS" + "\n";
         strToWrite += "** " + "\n";
         strToWrite += "*Restart, write, frequency=0" + "\n";
         strToWrite += "** " + "\n";
         strToWrite += "** FIELD OUTPUT: F-Output-1" + "\n";
         strToWrite += "** " + "\n";
         strToWrite += "*node print, nset=NALL" + "\n";
         strToWrite += "U1,U2,U3" + "\n";
         strToWrite += "*Output, field, variable=PRESELECT" + "\n";
         strToWrite += "*End Step" + "\n";

         bw.write(strToWrite, 0, strToWrite.length());
         bw.close();

         System.out.println("End Input file Output output");
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }

     long timeWriteInpFile = System.currentTimeMillis() - timeMillis;
     java.sql.Timestamp dateTime = new java.sql.Timestamp(timeWriteInpFile);
     String timeStrWriteInpFile = dateTime.toString().substring(14) + "";
     System.out.println("End Write Mesh Inp file....." + timeStrWriteInpFile);
     System.out.println("=============================");
     return true;
 }

private String printBrickToString(String strToWrite, SDT_Array3DBrick arrayBrickAirSingle,int positionType)
{
    int brickNumber = 0;
    for (int j = 0; j < arrayBrickAirSingle.Size(); j++)
    {
        SDT_3DBrick brick = arrayBrickAirSingle.get(j);
        if (brick.isPositionMatch(positionType))
        {
            strToWrite += (brick.getElementID() + ", ");
            brickNumber++;

            if (brickNumber % 8 == 0 && brickNumber != 0)
            strToWrite += "\n";
        }
    }
    return strToWrite;
}

//New means "with Part Info"
 public boolean writeInpFileConeAirNew(String path,
                              String elsetNameSurround, double thickSurround, String materialSurround,
                              String elsetNameCap, double thickCap, String materialCap,
                              String elsetNameTransition, double thickTransition, String materialTransition,
                              String elsetNameDiaphragm, double thickDiaphragm, String materialDiaphragm,
                              String elsetNameCoil, double thickCoil, String materialCoil,
                              String elsetNameAir,  String materialAir)
  {
      System.out.println("=============================");
      System.out.println("Start Write Data Inp file.....");
      long timeMillis = System.currentTimeMillis();

      if (this._ptSetsShellAll == null || this._ptSetsShellCenter == null)
      {
          return false;
      }
      try
      {
          FileWriter fw = new FileWriter(path);

          BufferedWriter bw = new BufferedWriter(fw);
 String partNameCone = "CONE";
          String strToWrite = "";

          strToWrite += "*Heading \n";
          strToWrite += "** \n";
          strToWrite += "** PARTS \n";
          strToWrite += "** \n";
          strToWrite += "*Part, name=" + partNameCone + "\n";
          strToWrite += "*End Part \n";
          strToWrite += "*Part, name=" + elsetNameAir + "\n";
          strToWrite += "*End Part \n";
          bw.write(strToWrite, 0, strToWrite.length());
          strToWrite = "";


strToWrite += "** \n";
strToWrite += "** \n";
strToWrite += "** ASSEMBLY \n";
strToWrite += "** \n";
strToWrite += "*Assembly, name=Assembly \n";
strToWrite += "** \n";
strToWrite += "** \n";
strToWrite += "*Instance, name="+partNameCone + "-1"+", part=" + partNameCone+ "\n";
strToWrite += "*NODE, NSET="+partNameCone+" \n";
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";


          System.out.println("          Writing Cartesian Points.....");

          int iCount = _ptSetsShellAll[0].length;
          int kjCount = _ptSetsShellAll.length;
          _outestI = iCount -1;

          writePoints(bw,  this._ptSetsShellCenter);
          writePoints(bw,  this._ptSetsShellAll);
          for(int i = 0 ; i < this._ptSetsSolidCone.Size() ; i++)
          {
              this._ptSetsSolidCone.get(i).writePoints(bw);
          }
          writeElementShell(_arrayMeshSurround, elsetNameSurround, bw);
          writeElementShell(_arrayMeshCap, elsetNameCap, bw);
          writeElementShell(_arrayMeshTransition, elsetNameTransition, bw);
          if (_arrayMeshDiaphragm != null)
          {
              writeElementShell(_arrayMeshDiaphragm, elsetNameDiaphragm, bw);
          }
          writeElementBrick(_arrayBrickCoil, elsetNameCoil, bw, false);

          writeSectionShell(elsetNameSurround, thickSurround, materialSurround, bw);
          writeSectionShell(elsetNameCap, thickCap, materialCap, bw);
          writeSectionShell(elsetNameTransition, thickTransition, materialTransition, bw);
          if (_arrayMeshDiaphragm != null)
          {
              writeSectionShell(elsetNameDiaphragm, thickDiaphragm, materialDiaphragm, bw);
          }
           writeSectionSolid(elsetNameCoil, thickCoil, materialCoil, bw);



strToWrite += "*End Instance \n";
strToWrite += "*Instance, name="+elsetNameAir + "-1"+", part=" + elsetNameAir+ "\n";
strToWrite += "*NODE, NSET="+elsetNameAir+" \n";
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

          for(int i = 0 ; i < this._ptSetsSolidAir.Size() ; i++)
          {
              this._ptSetsSolidAir.get(i).writePoints(bw);
          }

          for(int i = 0 ; i < this._arrayBrickAir.size() ; i++)
          {

              SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick) _arrayBrickAir.get(i);
              writeElementBrick(arrayBrickAirSingle, elsetNameAir + "-->Zone" + arrayBrickAirSingle.getTypeID(), bw, true);
          }


          writeSectionSolid(elsetNameAir, 0, materialAir, bw);

strToWrite += "*End Instance \n";
strToWrite += "** \n";
bw.write(strToWrite, 0, strToWrite.length());
strToWrite ="";



strToWrite += "** \n";
strToWrite += "*Elset, elset=ConeDown_Inner  \n";
         for(int i = 0 ; i < this._arrayMeshCap.Size() ; i++)
         {
             strToWrite += (this._arrayMeshCap.get(i).getElementID() + ", ");
             if (i % 8 == 0 && i != 0)
                 strToWrite += "\n";
         }
         strToWrite += "\n";

         for (int i = 0; i < this._arrayMeshTransition.Size(); i+=3)
         {
                 strToWrite += (this._arrayMeshTransition.get(i).getElementID() + ", ");
                 if (i % 24 == 0)
                     strToWrite += "\n";
         }
         strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
         strToWrite += "\n";

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=ConeDown_Outer \n";

       for (int i = 2; i < this._arrayMeshTransition.Size(); i += 3)
       {
           strToWrite += (this._arrayMeshTransition.get(i).getElementID() + ", ");
           if (i % 24 == 2)
               strToWrite += "\n";
       }
       strToWrite += "\n";

       if (_arrayMeshDiaphragm != null)
       {
           for (int i = 0; i < this._arrayMeshDiaphragm.Size(); i++)
           {

               strToWrite += (this._arrayMeshDiaphragm.get(i).getElementID() + ", ");
               if (i % 8 == 0)
                   strToWrite += "\n";
           }
           strToWrite += "\n";
       }

       for (int i = 0; i < this._arrayMeshSurround.Size(); i++)
       {

           strToWrite += (this._arrayMeshSurround.get(i).getElementID() + ", ");
           if (i % 8 == 0)
               strToWrite += "\n";
       }
       strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
       strToWrite += "\n";

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=AirDenseUp_Inner \n";
       for (int i = 0; i < this._arrayBrickAir.size(); i++)
       {
           SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);

           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_CAP)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle,DefineSystemConstant.BRICK_FACE_UP);
               strToWrite += "\n";
           }
           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_TRAN_IN)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle,DefineSystemConstant.BRICK_FACE_UP);
               strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
               strToWrite += "\n";
           }
       }
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=AirDenseDown_Inner \n";
       for (int i = 0; i < this._arrayBrickAir.size(); i++)
       {
           SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);

           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_CAP)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.BRICK_FACE_DOWN);
               strToWrite += "\n";
           }
           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_TRAN_IN)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.BRICK_FACE_DOWN);
               strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
               strToWrite += "\n";
           }

       }
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";


strToWrite += "** \n";
strToWrite += "*Elset, elset=AirDenseUp_Outer \n";
       for (int i = 0; i < this._arrayBrickAir.size(); i++)
       {
           SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);

           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_TRAN_OUT ||
               arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_DIAPHRAGM)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.BRICK_FACE_UP);
               strToWrite += "\n";
           }
           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_SURROUND)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.BRICK_FACE_UP);
               strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
               strToWrite += "\n";
           }

       }
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "** \n";
strToWrite += "*Elset, elset=AirDenseDown_Outer \n";
       for (int i = 0; i < this._arrayBrickAir.size(); i++)
       {
           SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);

           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_TRAN_OUT ||
               arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_DIAPHRAGM)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.BRICK_FACE_DOWN);
               strToWrite += "\n";
           }
           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_SURROUND)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle, DefineSystemConstant.BRICK_FACE_DOWN);
               strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
               strToWrite += "\n";
           }

       }
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";


strToWrite += "** \n";
strToWrite += "*Elset, elset=AirSpraseUp_Outer \n";
       for (int i = 0; i < this._arrayBrickAir.size(); i++)
       {
           SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);

           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_ZONE16 ||
              arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_ZONE19 ||
              arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_ZONE21)
          {
              strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle,DefineSystemConstant.BRICK_FACE_UP);
              strToWrite += "\n";
          }
          if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_ZONE22)
          {
              strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle,DefineSystemConstant.BRICK_FACE_UP);
              strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
              strToWrite += "\n";
          }

       }
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";


strToWrite += "** \n";
strToWrite += "*Elset, elset=AirSpraseUp_Inner \n";
       for (int i = 0; i < this._arrayBrickAir.size(); i++)
       {
           SDT_Array3DBrick arrayBrickAirSingle = (SDT_Array3DBrick)this._arrayBrickAir.get(i);

           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_ZONE01 ||
               arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_ZONE02 )
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle,DefineSystemConstant.BRICK_FACE_UP);
               strToWrite += "\n";
           }
           if (arrayBrickAirSingle.getBrickType() == DefineSystemConstant.SECTION_AIR_ZONE03)
           {
               strToWrite = printBrickToString(strToWrite, arrayBrickAirSingle,DefineSystemConstant.BRICK_FACE_UP);
               strToWrite = strToWrite.substring(0, strToWrite.length() - 2);
               strToWrite += "\n";
           }
       }
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "*Surface, type=ELEMENT, name=ConeDown_SurfaceInner \n";
strToWrite += "ConeDown_Inner, SPOS \n";
strToWrite += "*Surface, type=ELEMENT, name=ConeDown_SurfaceOuter \n";
strToWrite += "ConeDown_Outer, SPOS \n";

strToWrite += "*Surface, type=ELEMENT, name=AirDenseUp_SurfaceInner \n";
strToWrite += "AirDenseUp_Inner, S4 \n";
strToWrite += "*Surface, type=ELEMENT, name=AirDenseUp_SurfaceOuter \n";
strToWrite += "AirDenseUp_Outer, S4 \n";

strToWrite += "*Surface, type=ELEMENT, name=AirDenseDown_SurfaceInner \n";
strToWrite += "AirDenseDown_Inner, S6 \n";
strToWrite += "*Surface, type=ELEMENT, name=AirDenseDown_SurfaceOuter \n";
strToWrite += "AirDenseDown_Outer, S6 \n";

strToWrite += "*Surface, type=ELEMENT, name=AirSpraseUp_SurfaceInner \n";
strToWrite += "AirSpraseUp_Inner, S3 \n";
strToWrite += "*Surface, type=ELEMENT, name=AirSpraseUp_SurfaceOuter \n";
strToWrite += "AirSpraseUp_Outer, S3 \n";


strToWrite += "*Tie, name=Constraint-ConeToAirDense-Inner, adjust=no \n";
strToWrite += "ConeDown_SurfaceInner, AirDenseUp_SurfaceInner \n";
strToWrite += "*Tie, name=Constraint-ConeToAirDense-Outer, adjust=no \n";
strToWrite += "ConeDown_SurfaceOuter, AirDenseUp_SurfaceOuter \n";

strToWrite += "*Tie, name=Constraint-AirDenseToSprase-Inner, adjust=no \n";
strToWrite += "AirDenseDown_SurfaceInner, AirSpraseUp_SurfaceInner \n";
strToWrite += "*Tie, name=Constraint-AirDenseToSprase-Outer, adjust=no \n";
strToWrite += "AirDenseDown_SurfaceOuter, AirSpraseUp_SurfaceOuter \n";

bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

strToWrite += "*End Assembly \n";
strToWrite += "** \n";
strToWrite += "** MATERIALS \n";
strToWrite += "** \n";
bw.write(strToWrite, 0, strToWrite.length());
strToWrite = "";

          writeMaterialByName(materialSurround, bw);
          writeMaterialByName(materialCap, bw);
          writeMaterialByName(materialTransition, bw);
          writeMaterialByName(materialAir, bw);
          if (_arrayMeshDiaphragm != null)
          {
              writeMaterialByName(materialDiaphragm, bw);
          }
          writeMaterialByName(materialCoil, bw);

          strToWrite += "**" + "\n";
          strToWrite += "**" + "\n";
          strToWrite += "*NSET, NSET=FIXED-BC, GENERATE" + "\n";
          strToWrite += _ptSetsShellAll[0][_outestI].GetIDNumber() + ", " + _ptSetsShellAll[kjCount - 1][_outestI].GetIDNumber() + "," + iCount + "\n";
          strToWrite += "*BOUNDARY" + "\n";
          strToWrite += " FIXED-BC, 1, 6, 0" + "\n";

          strToWrite += "**" + "\n";
          strToWrite += "**" + "\n";
          strToWrite += "*Step, name=Step-1, perturbation" + "\n";
          //strToWrite += "*Frequency, eigensolver=Lanczos, normalization=displacement" + "\n";
          strToWrite += "*Frequency, eigensolver=Lanczos, normalization=mass" + "\n";
          strToWrite += ", 20, 20000, , , " + "\n";
          strToWrite += "** " + "\n";
          strToWrite += "** OUTPUT REQUESTS" + "\n";
          strToWrite += "** " + "\n";
          strToWrite += "*Restart, write, frequency=0" + "\n";
          strToWrite += "** " + "\n";
          strToWrite += "** FIELD OUTPUT: F-Output-1" + "\n";
          strToWrite += "** " + "\n";
          strToWrite += "*node print, nset=NALL" + "\n";
          strToWrite += "U1,U2,U3" + "\n";
          strToWrite += "*Output, field, variable=PRESELECT" + "\n";
          strToWrite += "*End Step" + "\n";

          bw.write(strToWrite, 0, strToWrite.length());
          bw.close();

          System.out.println("End Input file Output output");
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }

      long timeWriteInpFile = System.currentTimeMillis() - timeMillis;
      java.sql.Timestamp dateTime = new java.sql.Timestamp(timeWriteInpFile);
      String timeStrWriteInpFile = dateTime.toString().substring(14) + "";
      System.out.println("End Write Mesh Inp file....." + timeStrWriteInpFile);
      System.out.println("=============================");
      return true;
 }




   private void writePoints(BufferedWriter bw, stepb_cartesian_point[][] ptSets2Dim) throws IOException
   {
       String strToWrite = "";
       int jCount = ptSets2Dim.length;
       int iCount = ptSets2Dim[0].length;
       int totalSize = jCount * iCount;

       for (int j = 0; j < jCount; j++)
       {
           for (int i = 0; i < iCount; i++)
           {
               stepb_cartesian_point cpt = ptSets2Dim[j][i];
               String str = cpt.PrintStr();

               strToWrite += str + "\n";
               int ii = j * iCount + i;

               if ((ii - totalSize / 4) < 1 ||
                   (ii - totalSize / 2) < 1 ||
                   (ii - 3 * totalSize / 4) < 1 ||
                   ii == totalSize - 1)
               {
                   bw.write(strToWrite, 0, strToWrite.length());
                   strToWrite = "";
               }
           }
       }
       bw.write(strToWrite, 0, strToWrite.length());
   }

   private void writePoints(BufferedWriter bw, String strToWrite, stepb_cartesian_point[][][] ptSets3Dim) throws IOException
   {
       int kCount = ptSets3Dim.length;
       int jCount = ptSets3Dim[0].length;
       int iCount = ptSets3Dim[0][0].length;
       int totalSize = kCount * jCount * iCount;

       for (int k = 0; k < kCount; k++)
       {
           for (int j = 0; j < jCount; j++)
           {
               for (int i = 0; i < iCount; i++)
               {
                   stepb_cartesian_point cpt = ptSets3Dim[k][j][i];
                   String str = cpt.PrintStr();

                   strToWrite += str + "\n";
                   int ii = k * jCount * iCount + j * iCount + i;

                   if ((ii - totalSize / 4) < 1 ||
                       (ii - totalSize / 2) < 1 ||
                       (ii - 3 * totalSize / 4) < 1 ||
                       ii == totalSize - 1)
                   {
                       bw.write(strToWrite, 0, strToWrite.length());
                       strToWrite = "";
                   }
               }
           }
       }
       bw.write(strToWrite, 0, strToWrite.length());
   }

    private void writePoints(BufferedWriter bw,  stepb_cartesian_point[] ptSets1Dim) throws IOException
    {
        String strToWrite = "";
        int jCount = ptSets1Dim.length;
        int totalSize = jCount;
        for (int j = 0; j < jCount; j++)
        {
            stepb_cartesian_point cpt = ptSets1Dim[j];
            String str ;
            if (ptSets1Dim[j].GetIDNumber() != -1)  // if the centerpoint of CAP is only a point but not a line, only the first point has IDnumber
            {
                str = cpt.PrintStr();
                strToWrite += str + "\n";
            }



            int ii = j;

            if ((ii - totalSize / 4) < 1 ||
                (ii - totalSize / 2) < 1 ||
                (ii - 3 * totalSize / 4) < 1 ||
                ii == totalSize - 1)
            {
                bw.write(strToWrite, 0, strToWrite.length());
                strToWrite = "";
            }
        }
        bw.write(strToWrite, 0, strToWrite.length());
    }
























   private void writeElementShell(SDT_Array3DMesh arrayMeshSurround, String elsetName, BufferedWriter bw) throws IOException
    {
        elsetName = elsetName.trim();
        String strToWrite = "";
        strToWrite += "**\n";
        strToWrite += "**\n";
        strToWrite += "*ELEMENT, TYPE=S4R, ELSET=" + elsetName + " \n";

        boolean isTriangleExist = false;

        System.out.println("          Writing Mesh Array ......" + elsetName + ":4 Node");
        for (int i = 0; i < arrayMeshSurround.Size(); i++)
        {
            SDT_3DMesh mesh = arrayMeshSurround.get(i);
            if (mesh.GetPoint4() == null)
            {
                isTriangleExist = true;
                continue;
            }
            String str = mesh.printStrCW();
            strToWrite += str + "\n";
            if ((i - arrayMeshSurround.Size() / 4) < 1 ||
                (i - arrayMeshSurround.Size() / 2) < 1 ||
                (i - 3 * arrayMeshSurround.Size() / 4) < 1 ||
                i == arrayMeshSurround.Size() - 1)
            {
                bw.write(strToWrite, 0, strToWrite.length());
                strToWrite = "";
            }
        }

        if (isTriangleExist)
        {
            strToWrite += "*ELEMENT, TYPE=S3R, ELSET=" + elsetName + " \n";
            System.out.println("          Writing Mesh Array ....." + elsetName + ":3 Node");
            for (int i = 0; i < arrayMeshSurround.Size(); i++)
            {
                SDT_3DMesh mesh = arrayMeshSurround.get(i);
                if (mesh.GetPoint4() != null)
                {
                    continue;
                }

                String str = mesh.printStrCW();
                strToWrite += str + "\n";
                if ((i - arrayMeshSurround.Size() / 4) < 1 ||
                    (i - arrayMeshSurround.Size() / 2) < 1 ||
                    (i - 3 * arrayMeshSurround.Size() / 4) < 1 ||
                    i == arrayMeshSurround.Size() - 1)
                {
                    bw.write(strToWrite, 0, strToWrite.length());
                    strToWrite = "";
                }
            }
        }
        bw.flush();
    }

    private void writeElementBrick(SDT_Array3DBrick arrayMeshBrick, String elsetName, BufferedWriter bw, boolean isAir) throws IOException
    {
        elsetName = elsetName.trim();
        String strToWrite = "";
        strToWrite += "**\n";
        strToWrite += "**\n";
        if(!isAir)
            strToWrite += "*ELEMENT, TYPE=C3D8R, ELSET=" + elsetName + " \n";
        else
            strToWrite += "*ELEMENT, TYPE=AC3D8, ELSET=" + elsetName + " \n";
        boolean isWedgeExist = false;

        System.out.println("          Writing Brick Array ......" + elsetName + ":8 Node");
        for (int i = 0; i < arrayMeshBrick.Size(); i++)
        {
            SDT_3DBrick brick = arrayMeshBrick.get(i);
            if (brick.GetPoint4() == brick.GetPoint5())
            {
                isWedgeExist = true;
                continue;
            }

            String str = brick.PrintStrCW();
            strToWrite += str + "\n";
            if ((i - arrayMeshBrick.Size() / 4) < 1 ||
                (i - arrayMeshBrick.Size() / 2) < 1 ||
                (i - 3 * arrayMeshBrick.Size() / 4) < 1 ||
                i == arrayMeshBrick.Size() - 1)
            {
                bw.write(strToWrite, 0, strToWrite.length());
                strToWrite = "";
            }
        }
        if (isWedgeExist)
        {
            if (!isAir)
                strToWrite += "*ELEMENT, TYPE=C3D6, ELSET=" + elsetName + " \n";
            else
                strToWrite += "*ELEMENT, TYPE=AC3D6, ELSET=" + elsetName + " \n";

            System.out.println("          Writing Brick Array ......" + elsetName + ":6 Node");
            for (int i = 0; i < arrayMeshBrick.Size(); i++)
            {
                SDT_3DBrick brick = arrayMeshBrick.get(i);
                if (brick.GetPoint4() == brick.GetPoint5())
                {
                    isWedgeExist = true;
                    continue;
                }

                String str = brick.PrintStrCW();
                strToWrite += str + "\n";
                if ((i - arrayMeshBrick.Size() / 4) < 1 ||
                    (i - arrayMeshBrick.Size() / 2) < 1 ||
                    (i - 3 * arrayMeshBrick.Size() / 4) < 1 ||
                    i == arrayMeshBrick.Size() - 1)
                {
                    bw.write(strToWrite, 0, strToWrite.length());
                    strToWrite = "";
                }
            }

        }
        bw.flush();
    }



    private void writeSectionShell(String elsetName, double thickness, String materialName, BufferedWriter bw) throws IOException
   {
       String strToWrite = "";

       strToWrite += "**" + "\n";
       strToWrite += "**" + "\n";
       strToWrite += "*Shell Section, elset=" + elsetName + ", material=" + materialName + ", offset=" + 0.5 + "\n";
       strToWrite += thickness + "\n";
       bw.write(strToWrite, 0, strToWrite.length());
       bw.flush();
   }

   private void writeSectionSolid(String elsetName, double thickness, String materialName, BufferedWriter bw) throws IOException
   {
       String strToWrite = "";

       strToWrite += "**" + "\n";
       strToWrite += "**" + "\n";
       strToWrite += "*Solid Section, elset=" + elsetName + ", material=" + materialName + "\n";
       strToWrite += 1 + "\n";
       bw.write(strToWrite, 0, strToWrite.length());
       bw.flush();
   }

   private void writeMaterial(int type, String[] material, BufferedWriter bw) throws IOException
   {
       String strToWrite = "";

       switch(type)
       {
           case 0:
               strToWrite += "**" + "\n";
               strToWrite += "**" + "\n";
               strToWrite += "*Material, name = " + material[0] + "\n";
               strToWrite += "*Density" + "\n";
               strToWrite += material[1] + "," + "\n";
               strToWrite += "*Elastic" + "\n";
               strToWrite += " " + material[2] + ", " + material[3] + "\n";
               break;
           case 1:
               strToWrite += "**" + "\n";
               strToWrite += "**" + "\n";
               strToWrite += "*Material, name = AIR" + "\n";
               strToWrite += "*Density" + "\n";
               strToWrite += "1.21e-13," + "\n";
               strToWrite += "*Acoustic Medium" + "\n";
               strToWrite += "0.0142" + "\n";
               break;
       }
       bw.write(strToWrite, 0, strToWrite.length());
       bw.flush();
   }

   private void writeMaterialByName(String materialName, BufferedWriter bw) throws IOException
   {
       String strToWrite = "";
       if (materialName.equals("CUWIRE") && !isMaterialWrittenCUWIRE)
       {
           strToWrite += "**" + "\n";
           strToWrite += "**" + "\n";
           strToWrite += "*Material, name=CUWIRE" + "\n";
           strToWrite += "*Density" + "\n";
           strToWrite += "4.12e-10," + "\n";
           strToWrite += "*Elastic" + "\n";
           strToWrite += " 5000, 0.3" + "\n";
           isMaterialWrittenCUWIRE = true;
       }
       else if (materialName.equals("PU") && !isMaterialWrittenPU)
       {
           strToWrite += "**" + "\n";
           strToWrite += "**" + "\n";
           strToWrite += "*Material, name=PU" + "\n";
           strToWrite += "*Density" + "\n";
           strToWrite += "1.12e-13," + "\n";
           strToWrite += "*Elastic" + "\n";
           strToWrite += " 360.52, 0.4" + "\n";
           isMaterialWrittenPU = true;
       }
       else if (materialName.equals("AIR"))
      {
          strToWrite += "**" + "\n";
          strToWrite += "**" + "\n";
          strToWrite += "*Material, name=AIR" + "\n";
          strToWrite += "*Density" + "\n";
          strToWrite += "1.21e-10," + "\n";
          strToWrite += "*Acoustic Medium" + "\n";
          strToWrite += "0.0142" + "\n";
      }


       bw.write(strToWrite, 0, strToWrite.length());
       bw.flush();
   }

   private void offsetCoilPts(double dx,double dy ,double dz)
   {
       stepb_cartesian_point_array array = new stepb_cartesian_point_array();

       System.out.println("Brick Element Count of Coil:" + _arrayBrickCoil.Size());

       System.out.println("Pt Count of Coil: " + _ptSetsSolidCone.Size());
       for (int i = 0; i < this._ptSetsSolidCone.Size(); i++)
       {
           CartesianPointSetsBrick ptSetsBrick = _ptSetsSolidCone.get(i);
           ptSetsBrick.offsetPoints(dx,dy,dz);
       }



   }


}
