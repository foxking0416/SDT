package sdt.dialog;

import java.awt.event.*;

import javax.swing.*;

import sdt.data.*;
import sdt.define.*;
import sdt.framework.*;
import sdt.geometry.element.*;
import java.io.File;
import sdt.data.SDT_DataManager.*;






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
public class DiaExportInpFile extends DiaMain
{

    private JLabel          jLabel1;
    private JProgressBar    jProgressBarWait;
    private JList           jList;
    private JScrollPane     jScrollpanel;
    private DefaultListModel listModel;
    private JButton         jButtonStop;
    private String          path;
    private progress_thread _progressThread;
    private ThreadLogRead _logDataReader ;

    public DiaExportInpFile(SDT_System system)
    {
        super(system, true);
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    void jbInit() throws Exception
    {
        String str = this._system.GetInterfaceString("TitleSolvingProgress");
        this.setTitle(str);
        this.setSize(375, 220);
        this.setLocation();


        _progressThread = new progress_thread();
        _logDataReader = new ThreadLogRead();

        _progressThread.RunSimulation();
        this.setVisible(true);

    }

    protected void createComponent()
    {
        int progressMax = 5;
        this.jProgressBarWait = new JProgressBar(0, progressMax);

        this.jList = new JList();
        this.listModel = new DefaultListModel();
        jScrollpanel = new JScrollPane();
        jButtonStop = new JButton("Stop");

    }

    protected void setSizeComponent()
    {
        _contentPane.setLayout(null);
        jProgressBarWait.setBounds(25, 15, 325, 30);
        jScrollpanel.setBounds(25, 55, 325, 105);
        jButtonStop.setBounds(270, 175, 70, 25);
        jButtonStop.addActionListener(this);
    }

    protected void addComponent()
    {
        jList.setModel(listModel);
        jScrollpanel.getViewport().add(jList);
        _contentPane.add(jProgressBarWait);
        _contentPane.add(jScrollpanel);
        _contentPane.add(jButtonStop);

    }

    protected void addListener()
    {
        this.jButtonStop.addActionListener(this);
        this._jButtonClose.addActionListener(this);
    }

    protected boolean checkTextfield()
    {
        return false;
    }




    protected void readDiaData()
    {

    }

    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if(e.getSource() == this.jButtonStop ||e.getSource()  == this._jButtonClose)
        {
            if (_progressThread.isLive())
                _progressThread.stop();
            if (_logDataReader.isLive())
                _logDataReader.stop();
            this.dispose();
        }
    }
    public void progress1()
    {

        String str = this._system.GetInterfaceString("SolvingProgressInputFileExported");
        int size = this.listModel.getSize();
        this.listModel.add(size, str);
        this.jList.setSelectedIndex(size);
        this.setProgressBarValue(1);
    }

    public void progress2()
    {
        String str = this._system.GetInterfaceString("SolvingProgressActivatingAbaqus");
        int size = this.listModel.getSize();
        this.listModel.add(size, str);
        this.jList.setSelectedIndex(size);
        this.setProgressBarValue(2);
        this._system.getDataManager().createAbaqusBatAndRun();

    }

    public void progress3()
    {

        String str = this._system.GetInterfaceString("SolvingProgressListenLogFile");
        int size = this.listModel.getSize();
        this.listModel.add(size, str);
        this.jList.setSelectedIndex(size);
        this.setProgressBarValue(3);
        _logDataReader.RunSimulation();
    }

    public void progress4()
    {
         String str = this._system.GetInterfaceString("SolvingProgressRetrivingData");

        int size = this.listModel.getSize();
        this.listModel.add(size, str);
        this.jList.setSelectedIndex(size);
        this.setProgressBarValue(4);



        this._system.getDataManager().getDatContent();
        try
        {
            Thread.currentThread().sleep(500);
        }
        catch (InterruptedException ex)
        {
        }
        str = this._system.GetInterfaceString("SolvingProgressGetDataAndShow");
        size = this.listModel.getSize();
        this.listModel.add(size, str);
        this.jList.setSelectedIndex(size);
        this.setProgressBarValue(5);
    }



    public class progress_thread implements Runnable
    {

        private volatile Thread t;

        public void run()
        {
            Thread thisThread = Thread.currentThread();
            while (thisThread == this.t)
            {

                try
                {
                    progress1();
                    this.t.sleep(1000); //10001
                    progress2();
                    this.t.sleep(10000);
                    progress3();
                    //this.t.sleep(1000);
                   // progress4();

                }
                catch (InterruptedException ex1)
                {
                }
                break ;
            }
        }

      public boolean isLive()
      {
          if (this.t != null)
          {
              return this.t.isAlive();
          }
          else
          {
              return false;
          }
      }

      private void RunSimulation()
      {
          this.stop();
          this.t = new Thread(this);
          this.t.start();
      }

      public void stop()
      {
          this.t = null;
      }
  }

  private void setProgressBarValue(int value)
   {
       int max = this.jScrollpanel.getVerticalScrollBar().getMaximum();
       try
       {
           this.jScrollpanel.getVerticalScrollBar().setValue(max);
       }
       catch (Exception ex)
       {
           //Thread error
           try
           {
               Thread.sleep(1000);
           }
           catch (InterruptedException ex1)
           {

           }
           this.jScrollpanel.getVerticalScrollBar().setValue(max);
       }
       this.jProgressBarWait.setValue(value);
   }



   private class ThreadLogRead implements Runnable
    {
        private volatile Thread t;
        private int status = 2;//0: Finished, 1: Error, 2:Unfinished
        private int DelaySetting[] =
                {0, 0, 0};
        private int delayTime;
        private int reRunTimesReadLog;

        public ThreadLogRead()
        {
            delayTime = _system.getConfig().getDelayTime();
            reRunTimesReadLog = _system.getConfig().getReRunTime();
            if(reRunTimesReadLog == 0)
            {
                reRunTimesReadLog = 10;
                _system.getConfig().setReRunTime(reRunTimesReadLog);
                _system.getConfig().save();
            }

        }

        public void run()
        {
            int reRunTimes = 1;
            Thread thisThread = Thread.currentThread();
            while (thisThread == this.t)
            {
                try
                {
                    this.t.sleep(delayTime);
                }
                catch (InterruptedException ex1)
                {
                }

                status = _system.getDataManager().CheckIsAbaqusLogCompeleted();

                if( status == 0)
                {

                    progress4();
                    try
                    {
                        this.t.sleep(1000);
                    }
                    catch (InterruptedException ex1)
                    {
                    }

                    this.stop();
                    dispose();
                   // _system.changeTo3DDeformed(1);
                    _system.getMenu().doMenuItem("StepFrame");

                }
                else if(status == 1)
                {
                    new DiaMessage(_system, "<html>Error occurs in Job! <br>Analysis progress is terminated. </html>");
                    this.stop();
                }
                else
                {
                    reRunTimes++;
                    if (reRunTimes * 10 > reRunTimesReadLog * 60) //2010/07/28
                    {
                        new DiaMessage(_system, "<html>Job is not completed! <br>Analysis progress is terminated. </html>");
                        this.stop();
                    }
                }
            }
        }

        public boolean isLive()
        {
            if (this.t != null)
            {
                return this.t.isAlive();
            }
            else
            {
                return false;
            }
        }

        private void RunSimulation()
        {
            this.stop();
            this.t = new Thread(this);
            this.t.start();
        }

        public void stop()
        {
            this.t = null;
        }
    }



}
