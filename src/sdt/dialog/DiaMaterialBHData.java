package sdt.dialog;

import javax.swing.*;

import sdt.material.*;
import sdt.framework.SDT_System;
import javax.swing.table.JTableHeader;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.Vector;
import java.io.File;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.awt.event.ActionEvent;

public class DiaMaterialBHData  extends DiaMain
{

    protected table_model_material    tableModelC;
    protected JTable                  materialTableC;
    protected JScrollPane             scrollPanelC;
    protected JPanel                  panelC;
    protected String                  pathC ;
    protected int                     rowInterval;
    protected String[]                tableHeader;

    protected                         String[][] materialInfo =
                                    {
                                    };



    public DiaMaterialBHData(SDT_System system, String path)
    {
        super(system, true);

        pathC = "materials\\" + path.trim() + ".csv";
        tableHeader = new String[]
                      {"H (KA/m)", "B (T)"};

        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public DiaMaterialBHData(SDT_System system)
   {
       super(system, true);

   }



    public DiaMaterialBHData(SDT_System system, String[][] result)
    {
        super(system, true);
        tableHeader = new String[]
                      {"H (KA/m)", "B (T)"};

        try
        {
            materialInfo = result;
            jbInit2();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



    void jbInit2() throws Exception
    {
        String str = "";
        str = this._system.GetInterfaceString("TitleMaterial");


        tableModelC.SetTableModel(tableHeader, materialInfo);
        panelC.add(this.scrollPanelC, BorderLayout.CENTER);
        this.tableModelC.setEditable(true);
        this.setTitle(str);
        this.setSize(280, 320);
        this.setLocation();
        this.setVisible(true);
    }

    void jbInit() throws Exception
    {
        String str = "";
        str = this._system.GetInterfaceString("TitleMaterial");

        this.readDiaData();
        this.tableModelC.setEditable(false);
        this.setTitle(str);
        this.setSize(280, 320);
        this.setLocation();
        this.setVisible(true);
    }


    protected void createComponent()
    {
        tableModelC = new table_model_material(this._system);
        materialTableC = new JTable(tableModelC);
        this.scrollPanelC = new JScrollPane(materialTableC);
        panelC = new JPanel();

    }

    protected void setSizeComponent()
     {

         Dimension dim = new Dimension(280, 55);
         JTableHeader tableHeader;

         tableHeader = this.materialTableC.getTableHeader();
         tableHeader.setReorderingAllowed(true);
         tableHeader.setSize(dim);
         tableHeader.setPreferredSize(dim);

         rowInterval = 25;
         this.materialTableC.setRowHeight(this.rowInterval);
     }

     protected void addComponent()
     {
         panelC.setLayout(new BorderLayout());
         panelC.add(this.scrollPanelC, BorderLayout.CENTER);


         this._contentPane.setLayout(null);
         this.panelC.setBounds(10, 40, 260, 250);
         this.add(panelC);
         this._jButtonOk.setBounds(110, 280, 70, 25);
         this._jButtonCancel.setBounds(200, 280, 70, 25);

         this._contentPane.add(this._jButtonOk);
         this._contentPane.add(this._jButtonCancel);
     }

     protected void addListener()
     {
         this.materialTableC.addMouseListener(this);
     }

     protected boolean checkTextfield()
     {
         boolean bool = true;

         try
         {

         }
         catch (Exception e)
         {
             String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
             DiaMessage diaWarning = new DiaMessage(_system, diaStr);
             bool = false;
         }
         return bool;
     }

     protected void readDiaData()
     {
         Vector stringC = this.LoadData(this.pathC);
         this.AddToPanelC(stringC);
     }

     public Vector LoadData(String path)
     {
         File mFile = new File(path);
         BufferedReader breader;
         Vector stringVect = new Vector();
         String str = "";
         StringTokenizer token;
         int strSize = 0;
         boolean isNumberGet = false; // need to know how many records
         String strArray[];
         try
         {
             breader = new BufferedReader(new FileReader(mFile));
             do
             {
                 str = breader.readLine();
                 if (str == null)
                     break;
                 str.trim();
                 token = new StringTokenizer(str, ",");
                 strArray = new String[strSize];
                 int strIndex = 0;
                 while (token.hasMoreElements())
                 {
                     if (!isNumberGet)
                     {
                         strSize++;
                         stringVect.add(token.nextToken());
                     }
                     else
                     {
                         strArray[strIndex] = token.nextToken().trim();
                         strIndex++;
                     }
                 }
                 if (!isNumberGet)
                 {
                     isNumberGet = true;

                     strArray = new String[strSize];
                     for (int i = 0; i < stringVect.size(); i++)
                     {
                         strArray[i] = (String) stringVect.get(i);
                     }
                     stringVect.removeAllElements();
                 }
                 stringVect.add(strArray);

             }
             while (str != null);
             breader.close();
         }

         catch (Exception e)
         {
             e.printStackTrace();
         }

         return stringVect;
     }

     private void AddToPanelC(Vector stringVect)
     {

         int arraySize = stringVect.size();
         String[] rowData1 = (String[]) stringVect.get(0);
         int dataLength = rowData1.length;

         String result[][] = new String[arraySize][dataLength];

         for (int i = 0; i < arraySize; i++)
         {
             for (int j = 0; j < dataLength; j++)
             {
                 result[i][j] = ((String[]) stringVect.get(i))[j];
             }
         }
         materialInfo = result;
         tableModelC.SetTableModel(tableHeader, materialInfo);

         panelC.add(this.scrollPanelC, BorderLayout.CENTER);
     }

     public void actionPerformed(ActionEvent e)
     {
         if (e.getSource() == this._jButtonCancel)
         {
             this.dispose();
             System.out.println("button Cancel");
         }

         if (e.getSource() == this._jButtonOk)
         {
             this.dispose();
             System.out.println("button Ok");
             ((MaterialSteel)this._system.getDataManager().getCurrentData().getMaterial()).getHBData().setData(this.tableModelC.getAllData());
         }
         if (e.getSource() == this._jButtonClose)
         {
             this._jButtonCancel.doClick();
             this.dispose();
         }

     }



}
