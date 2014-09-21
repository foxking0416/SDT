package sdt.framework;

import java.io.*;
import java.util.*;

public class SDT_Config
{
    private Properties config;
    private String path;
    private String folder;

    public String GetFolder()
    {
        return this.folder;
    }

    public SDT_Config()
    {
        config = new Properties();
        folder = "C:/temp/SDT";
        path = folder + "/Config";

        File realFolder = new File(folder);
        if (!realFolder.isDirectory())
        {
            realFolder.mkdirs();
        }
        try
        {
            FileInputStream fileInputStream = new FileInputStream(path);
            config.load(fileInputStream);
        }
        catch (Exception e)
        {
            //System.err.println("can't find the KConfig!!");
           reWriteConfig();
        }
    }

    public void save()
    {
        try
        {
            config.store(new FileOutputStream(path), "This is the Config about the program!!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setAbaqusPath(String exePath)
    {
        config.setProperty("abaqus_path", exePath);
    }

    public void setAnsysPath(String exePath)
    {
        config.setProperty("ansys_path", exePath);
    }

    public void setWorkingDirPath(String workingDirPath)
    {
        config.setProperty("working_path",workingDirPath);
    }

    public void setAnsysWorkingFileName(String fileName)
    {
        config.setProperty("ansys_working_filename", fileName);
    }

    public void setAbaqusWorkingFileName(String fileName)
    {
        config.setProperty("abaqus_working_filename", fileName);
    }

    public void setReRunTime(int time)
    {
        config.setProperty("reRunTimes", String.valueOf(time));
    }

    public int getReRunTime()
    {
        String strTime = config.getProperty("reRunTimes");
        int time;
        if(strTime =="" || strTime == null)
            time = 0;
        else
            time = Integer.parseInt(strTime);
        return time;
    }


    public String getAbaqusPath()
    {
        String path = config.getProperty("abaqus_path");
        return path;
    }

    public String getAnsysPath()
    {
        String path = config.getProperty("ansys_path");
        return path;
    }

    public String getWorkingDirPath()
    {
        //String
        String path = config.getProperty("working_path");
        return path;
    }

    public String getAnsysWorkingFileName()
    {
        String name = config.getProperty("ansys_working_filename");
        return name;
    }

    public String getAbaqusWorkingFileName()
    {
        String name = config.getProperty("abaqus_working_filename");
        return name;
    }


    public String getModifyDir()
    {
        String path = config.getProperty("last_modify_dir");
        return path;
    }

    public void setModifyDir(String folderPath)
    {
        config.setProperty("last_modify_dir", folderPath);
    }

    public void setDelayTime(int millsecs)
    {
        config.setProperty("delayTime", millsecs+"");
    }


    public int getDelayTime()
    {
        return Integer.parseInt(config.getProperty("delayTime").trim());
    }



    public void setRecentExportPath(String exportPath)
    {
        config.setProperty("recent_export_path", exportPath);
    }

    public String getRecentExportPath()
    {
        String path;
        try
        {
            path = config.getProperty("recent_export_path");
        }
        catch (Exception ex)
        {
            path = "";
        }
        return path;
    }

    public void setRecentSavePath(String savePath)
    {
        config.setProperty("recent_save_path", savePath);
    }

    public String getRecentSavePath()
    {
        String path;
        try
        {
            path = config.getProperty("recent_save_path");
        }
        catch (Exception ex)
        {
            path = "";
        }

        return path;
    }

    public void setRecentOpenPath(String openPath)
    {
        config.setProperty("recent_open_path", openPath);
    }

    public String getRecentOpenPath()
    {
        String path;

        try
        {
            path = config.getProperty("recent_open_path");
        }
        catch (Exception ex)
        {
            path = "";
        }

        return path;
    }

    public void setRecentFile(String file)
    {
        boolean hadSame = false;
        String files[] = new String[4];
        for (int i = 0; i < 4; i++)
        {
            files[i] = config.getProperty("recent_file" + (i + 1));
        }
        if(files[0] == null)
        {
             reWriteConfig();
             for (int i = 0; i < 4; i++)
             {
                 files[i] = config.getProperty("recent_file" + (i + 1));
             }
        }

        for (int i = 0; i < 4; i++)
        {
            if (files[0].equals(file))
                return;
            if (files[i].equals(file))
            {
                for (int y = i; y > 0; y--)
                {
                    files[y] = files[y - 1];
                }
                files[0] = file;
                hadSame = true;
                break;
            }
        }
        if (hadSame == false)
        {
            for (int i = 3; i > 0; i--)
            {
                files[i] = files[i - 1];
            }
            files[0] = file;
        }
        for (int j = 0; j < 4; j++)
        {
            config.setProperty("recent_file" + (j + 1), files[j]);
        }
    }

   public String getRecentFile(int index)
   {
       String path = config.getProperty("recent_file" + index);
       return path;
   }

    /**
     * reWriteConfig
     */
    public void reWriteConfig()
    {
        try
           {
               File file = new File(path);

               FileOutputStream fileOutputStream = new FileOutputStream(file);
               fileOutputStream.write("#This is the Config about the program!!\n".getBytes());
               fileOutputStream.write("abaqus_path=D:/Roger_Program/Abaqus611/6.11-1/exec/abq6111.exe\n".getBytes());
               fileOutputStream.write("last_modify_dir=\"\"\n".getBytes());
               fileOutputStream.write(("delayTime="+2000+"\n").getBytes());
               fileOutputStream.write(("reRunTimes="+50+"\n").getBytes());
               fileOutputStream.write("recent_file4=\"\"\n".getBytes());
               fileOutputStream.write("recent_file3=\"\"\n".getBytes());
               fileOutputStream.write("recent_file2=\"\"\n".getBytes());
               fileOutputStream.write("recent_file1=\"\"\n".getBytes());

               fileOutputStream.close();
           }
           catch (IOException ex)
           {
           }
           try
           {
               FileInputStream fileInputStream = new FileInputStream(path);
               config.load(fileInputStream);
           }
           catch (Exception e2)
           {
               System.err.println("Default Config has Error!!");
           }

    }


}
