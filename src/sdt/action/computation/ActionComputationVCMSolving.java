package sdt.action.computation;

import java.awt.event.*;
import java.io.*;

import sdt.action.*;
import sdt.dialog.*;
import sdt.framework.*;

 public class ActionComputationVCMSolving extends ActionMain
{
    public ActionComputationVCMSolving(SDT_System system)
    {
         super(system, "VCMSolving");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("VCM Solving");
        String workDir = this._system.getConfig().getWorkingDirPath();
        String ansysVault =  this._system.getConfig().getAnsysWorkingFileName();
        if(ansysVault == "" || ansysVault == null)
        {
            ansysVault = "AnsysVault";
            this._system.getConfig().setAnsysWorkingFileName(ansysVault);
            this._system.getConfig().save();
        }

        String fileName = "selfComputation";

        File realFolder = new File(workDir+"\\"+ansysVault);
        if (!realFolder.isDirectory())
        {
            realFolder.mkdirs();
        }


        String filePath = workDir + "\\" + ansysVault + "\\" + fileName + ".mac";// "D:\\temp\\testAbaqus\\selfComputation.inp";
        String fileLogPath = workDir + "\\" + ansysVault + "\\" + fileName + ".log";
        String fileDatPath =  workDir + "\\" + ansysVault + "\\" + fileName + ".dat";
        String fileOdbPath =  workDir + "\\" + ansysVault + "\\" + fileName + ".odb";
        String fileComPath =  workDir + "\\" + ansysVault + "\\" + fileName + ".com";
        String fileSimPath =  workDir + "\\" + ansysVault + "\\" + fileName + ".sim";
        String fileMsgPath =  workDir + "\\" + ansysVault + "\\" + fileName + ".msg";
        String filePrtPath =  workDir + "\\" + ansysVault + "\\" + fileName + ".prt";
        String fileStaPath =  workDir + "\\" + ansysVault + "\\" + fileName + ".sta";

        try
        {
            boolean isExported = this._system.getDataManager().exportMacFile(filePath, fileName);
            if (isExported)
            {
                /*File fileLog = new File(fileLogPath);
                File fileDat = new File(fileDatPath);
                File fileOdb = new File(fileOdbPath);
                File fileCom = new File(fileComPath);
                File fileSim = new File(fileSimPath);
                File fileMsg = new File(fileMsgPath);
                File filePrt = new File(filePrtPath);
                File fileSta = new File(fileStaPath);

                if (fileLog.exists())                     fileLog.delete();
                if (fileDat.exists())                     fileDat.delete();
                if (fileOdb.exists())                     fileOdb.delete();
                if (fileCom.exists())                     fileCom.delete();
                if (fileSim.exists())                     fileSim.delete();
                if (fileMsg.exists())                     fileMsg.delete();
                if (filePrt.exists())                     filePrt.delete();
                if (fileSta.exists())                     fileSta.delete();
*/
                this._system.getDataManager().getFileFolderAndName(filePath);
                DiaExportMacFile dia = new DiaExportMacFile(this._system);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return;
        }

    }
}
