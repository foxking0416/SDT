package sdt.action.computation;

import java.awt.event.*;
import java.io.*;

import sdt.action.*;
import sdt.dialog.*;
import sdt.framework.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ActionComputationConeAirSolving extends ActionMain
{
    public ActionComputationConeAirSolving(SDT_System system)
    {
        super(system, "ConeAirSolving");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("Cone with Air Solving");
        this._system.getFrame().SetStatus("Cone Solving");


        String workDir = this._system.getConfig().getWorkingDirPath();
        if(workDir == null)
            workDir = "";
        File fileCheck = new File(workDir);
        if(fileCheck.exists() != true)
        {
            DiaToolOption diaToolOption = new DiaToolOption(this._system, 1);
        }

        workDir = this._system.getConfig().getWorkingDirPath();
        if(workDir == null)
            workDir = "";
        fileCheck = new File(workDir);
        if(fileCheck.exists() != true)
        {
            String diaStr = _system.GetInterfaceString("dis_MessageStrNoWorkFile");
            DiaMessage diaWarning = new DiaMessage(_system, diaStr);
            return;
        }



        String abaqusVault =  this._system.getConfig().getAbaqusWorkingFileName();
        if(abaqusVault == "" || abaqusVault == null)
        {
            abaqusVault = "AbaqusVault";
            this._system.getConfig().setAbaqusWorkingFileName(abaqusVault);
            this._system.getConfig().save();
        }

        //取得分割資訊

        //double[] portSize = this._system.getDataManager().getPortSize();



        DiaPortSetting diaPort = new DiaPortSetting(this._system);
        if(diaPort.getIsCancel())
        {
           return;
        }
        int portLength = diaPort.getLengthIndex();
        boolean isPortSealed = diaPort.getIsPortSealed();



        String fileName = "selfComputation";

        File realFolder = new File(workDir+"\\"+abaqusVault);
        if (!realFolder.isDirectory())
        {
            realFolder.mkdirs();
        }


        String filePath    = workDir + "\\" + abaqusVault + "\\" + fileName + ".inp";// "D:\\temp\\testAbaqus\\selfComputation.inp";
        String fileLogPath = workDir + "\\" + abaqusVault + "\\" + fileName + ".log";
        String fileDatPath = workDir + "\\" + abaqusVault + "\\" + fileName + ".dat";
        String fileOdbPath = workDir + "\\" + abaqusVault + "\\" + fileName + ".odb";
        String fileComPath = workDir + "\\" + abaqusVault + "\\" + fileName + ".com";
        String fileSimPath = workDir + "\\" + abaqusVault + "\\" + fileName + ".sim";
        String fileMsgPath = workDir + "\\" + abaqusVault + "\\" + fileName + ".msg";
        String filePrtPath = workDir + "\\" + abaqusVault + "\\" + fileName + ".prt";
        String fileStaPath = workDir + "\\" + abaqusVault + "\\" + fileName + ".sta";

       try
       {
        //   boolean isExported = this._system.getDataManager().exportMacFile(fileMac, "FirstMacFile");

           boolean isExported = this._system.getDataManager().exportInpFile(filePath, true, isPortSealed, portLength);
           if (isExported)
           {
               File fileLog = new File(fileLogPath);
               File fileDat = new File(fileDatPath);
               File fileOdb = new File(fileOdbPath);
               File fileCom = new File(fileComPath);
               File fileSim = new File(fileSimPath);
               File fileMsg = new File(fileMsgPath);
               File filePrt = new File(filePrtPath);
               File fileSta = new File(fileStaPath);

               if (fileLog.exists())      fileLog.delete();
               if (fileDat.exists())      fileDat.delete();
               if (fileOdb.exists())      fileOdb.delete();
               if (fileCom.exists())      fileCom.delete();
               if (fileSim.exists())      fileSim.delete();
               if (fileMsg.exists())      fileMsg.delete();
               if (filePrt.exists())      filePrt.delete();
               if (fileSta.exists())      fileSta.delete();

               this._system.getDataManager().getFileFolderAndName(filePath);
               DiaExportInpFile dia = new DiaExportInpFile(this._system);
           }

       }
       catch (Exception ex)
       {
           ex.printStackTrace();
           return;
       }

       this._system.getMenu().setMenuItemEnable("StepFrame",true) ;
       this._system.getMenu().setMenuItemEnable("EMMA",true) ;

    }


}
