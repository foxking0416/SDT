package sdt.dialog;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import sdt.data.*;
import sdt.define.*;
import sdt.framework.*;
import sdt.material.*;
import sdt.material.table_model_material;
import sdt.uicomponent.*;
import sdt.uicomponent.TableButton;

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
public class DiaMaterial extends DiaMain
{
    private JTabbedPane _tabbed;

    private table_model_material    tableModelC;
    private table_model_material    tableModelSelected;
    private JTable                  materialTableC;
    private JTable                  materialTableSelected;
    private JScrollPane             scrollPanelC;
    private JScrollPane             scrollPanelSelected;
    private JPanel                  panelContent;
    private JPanel                  panelSelected;
    private JLabel                  jLabelSelectedMaterial;
    private String                  pathC ;
    private int                     rowInterval;
    private String[]                tableHeader;
    private String[]                tableHeaderSelected;

    private MaterialBase            _materialBase;

    private                         String[][] materialInfo =
                                    {
                                    };
    private boolean                 _isNestedDataContained = false;

    public DiaMaterial(SDT_System system)
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
        String str = "";

        str = this._system.GetInterfaceString("TitleMaterial");

        this.readDiaData();
        this.setTitle(str);
        this.setSize(580, 340);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        this._tabbed = new JTabbedPane();

       this.tableModelC             = new table_model_material(this._system);
       this.tableModelSelected      = new table_model_material(this._system);
       this.materialTableC          = new JTable(tableModelC);
       this.materialTableSelected   = new JTable(tableModelSelected);

       this.scrollPanelC            = new JScrollPane(materialTableC);
       this.scrollPanelSelected     = new JScrollPane(materialTableSelected);

       String str = "";
       str = this._system.GetInterfaceString("LabelSelectedMaterial");
       this.jLabelSelectedMaterial  = new JLabel(str);

       panelContent = new JPanel();
       panelSelected = new JPanel();
  //     pathC = "materials\\CopperAlloy.csv";
  //     tableHeader = new String[]{"No.", "Material Name", "<html>Density<br> (kgf/mm^2)</html>","<html>Young's Modulus<br> (kgf/mm^2)</html>","<html>Poisson's ratio</html>","Button"};

    }

    protected void setSizeComponent()
    {

        Dimension dim = new Dimension(620, 55);
        JTableHeader tableHeader;

        tableHeader = this.materialTableC.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.setSize(dim);
        tableHeader.setPreferredSize(dim);

        rowInterval = 25;
        this.materialTableC.setRowHeight(this.rowInterval);
        this.materialTableSelected.setRowHeight(this.rowInterval);

    }

    protected void addComponent()
    {
        panelContent.setLayout(new BorderLayout());
        panelSelected.setLayout(new BorderLayout());

        this._contentPane.setLayout(null);
        this._tabbed.setBounds(10, 40, 560, 250);
        this.add(_tabbed);


        jLabelSelectedMaterial.setBounds(10, 300, 80, 25);
        this.add(jLabelSelectedMaterial);

        this.materialTableSelected.setBorder( new BevelBorder(BevelBorder.LOWERED,Color.darkGray,Color.lightGray));
        this.materialTableSelected.setBounds(100, 300, 560, 25);
        this.add(materialTableSelected);


        this._jButtonOk.setBounds(210, 305, 70, 25);
        this._jButtonCancel.setBounds(300, 305, 70, 25);

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

   private Vector LoadData(String path)
   {
       File mFile = new File(path);
       BufferedReader breader;
       Vector stringVect= new Vector();
       String str = "";
       StringTokenizer token;
       int strSize = 0;
       boolean isNumberGet= false;
       String strArray[];
       try
       {
           breader = new BufferedReader(new FileReader(mFile));
           do
           {
               str = breader.readLine();
               if(str ==null )
                   break;
               str.trim();
               token = new StringTokenizer(str,",");
               strArray = new String[strSize];
               int strIndex = 0;
               while(token.hasMoreElements())
               {
                   if(!isNumberGet)
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
               if(!isNumberGet)
               {
                   isNumberGet = true;

                   strArray = new String[strSize];
                   for(int i = 0 ; i < stringVect.size(); i++)
                   {
                       strArray[i] = (String)stringVect.get(i);
                   }
                   stringVect.removeAllElements();
               }
               stringVect.add(strArray);

           }
           while (str != null);
           breader.close();
       }

       catch(Exception e)
       {
       e.printStackTrace();
       }

       return stringVect;
   }

   private void AddToPanelC(Vector stringVect)
   {


       int arraySize = stringVect.size();
       String[] rowData1 = (String[])stringVect.get(0);
       int dataLength = rowData1.length;

       String result[][] = new String[arraySize][dataLength];
       //String resultSelected[][] = new String[1][dataLength-1];

       for (int i = 0; i < arraySize; i++)
       {
           for (int j = 0; j < dataLength; j++)
           {
               result[i][j] = ((String[]) stringVect.get(i))[j];
              /* if(i == 0 && j!= dataLength -1)
               {
                   resultSelected[0][j] = "";
               }*/
           }

       }

       materialInfo = result;
       tableModelC.SetTableModel(tableHeader, materialInfo);
       tableModelC.setEditable(false);


       panelContent.add(this.scrollPanelC,BorderLayout.CENTER);
   }

   private void AddToPanelSelected(String[] stringArray)
   {

       int dataLength = stringArray.length;

       String resultSelected[][] = new String[1][dataLength];

       for (int j = 0; j < dataLength; j++)
       {
           resultSelected[0][j] = stringArray[j];
       }

       tableHeaderSelected = new String[tableHeader.length - 1];
       for (int i = 1; i < tableHeader.length; i++)
       {
           tableHeaderSelected[i - 1] = tableHeader[i];
       }

       int newLengthDiff = 560  / tableHeader.length;
       this.materialTableSelected.setBounds(10 + newLengthDiff, 300, 560 - newLengthDiff, 25);

       tableModelSelected.SetTableModel(tableHeaderSelected, resultSelected);
       panelSelected.add(this.scrollPanelSelected, BorderLayout.CENTER);



   }






   protected void readDiaData()
   {
       DataBase data = this._system.getDataManager().getCurrentData();
       _materialBase = data.getMaterial();
       pathC = _materialBase.getPath();
       tableHeader = _materialBase.getTableHeader();

       Vector stringC = this.LoadData(this.pathC);
       this.AddToPanelC(stringC);



       String[] rowData = this._system.getDataManager().getCurrentData().getMaterial().getRawData();
       this.AddToPanelSelected(rowData);


       int dataType = this._system.getDataManager().getCurrentData().getDataType();
       //String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeName();

        switch(dataType)
        {
            case DefineSystemConstant.TYPE_CAP:
            case DefineSystemConstant.TYPE_TRANSITION:
            case DefineSystemConstant.TYPE_DIAPHRAGM:
            case DefineSystemConstant.TYPE_SURROUND:
                this._tabbed.removeAll();
                this._tabbed.add(this.panelContent, "Material For Diaphragm");
                break;

            case DefineSystemConstant.TYPE_COIL:
                this._tabbed.removeAll();
                this._tabbed.add(this.panelContent, "Material For Coil");
                break;

            case DefineSystemConstant.TYPE_MAGNET:
            case DefineSystemConstant.TYPE_MAGNETTOP:
                this._tabbed.removeAll();
                this._tabbed.add(this.panelContent, "Material For Magnet");
                break;

            case DefineSystemConstant.TYPE_TOPPLATE:
            case DefineSystemConstant.TYPE_YOKEBASE:
            case DefineSystemConstant.TYPE_YOKESTAGE1:
            case DefineSystemConstant.TYPE_YOKESTAGE2:
                this.materialTableC.getColumn("<html>H-B Data or <br>Relative Permeability</html>").setCellRenderer(new TableButton());
                this.materialTableC.getColumn("<html>H-B Data or <br>Relative Permeability</html>").setCellEditor(new ButtonEditor(new JCheckBox(), this._system));

                this._tabbed.removeAll();
                this.tableModelC.setButtonColumn(2);
                this._tabbed.add(this.panelContent, "Material For Steel");

                _isNestedDataContained = true;

                this.materialTableSelected.getColumn("<html>H-B Data or <br>Relative Permeability</html>").setCellRenderer(new TableButton());
                this.materialTableSelected.getColumn("<html>H-B Data or <br>Relative Permeability</html>").setCellEditor(new ButtonEditor(new JCheckBox(),this._system));
                break;
       }

    }

    public void  actionPerformed(ActionEvent e)
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

            Object[] dataObjs = this.tableModelSelected.getRowData(0);
             String[] datas = new String[dataObjs.length] ;
            for(int i = 0 ;i < dataObjs.length ; i++)
            {
                datas[i] = (String)dataObjs[i];
            }


            this._materialBase.setSeletedRowData(datas);

        }
        if(e.getSource()== this._jButtonClose)
       {
           this._jButtonCancel.doClick();
           this.dispose();
       }

    }
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("test mouse click");
        if ((e.getModifiers() & e.BUTTON1_MASK) != 0)
        {
            int index = e.getY() / this.rowInterval;
            Object[] selectedRow = this.tableModelC.getRowData(index);

            String lastSelectedColumn = "";
            if(_isNestedDataContained)
            {
                lastSelectedColumn = (String) selectedRow[selectedRow.length - 1];
                if (lastSelectedColumn.endsWith("Curve"))
                {
                    lastSelectedColumn = "Curve Data";
                }
            }

            for (int i = 1; i < selectedRow.length; i++)
            {
                this.tableModelSelected.setValueAt(selectedRow[i], 0, i-1);
                if (_isNestedDataContained && i == selectedRow.length - 1)
                    if (lastSelectedColumn.equals("Curve Data"))
                    {
                        this.tableModelSelected.setValueAt(lastSelectedColumn, 0, i-1);
                        DiaMaterialBHData diaBHDataReader = new DiaMaterialBHData(_system);
                        Vector stringVect = diaBHDataReader.LoadData("materials\\" +((String)selectedRow[selectedRow.length - 1]).trim() + ".csv");

                        int arraySize = stringVect.size();
                        String[] rowData1 = (String[]) stringVect.get(0);
                        int dataLength = rowData1.length;

                        String result[][] = new String[arraySize][dataLength];

                        for (int ii = 0; ii < arraySize; ii++)
                        {
                            for (int j = 0; j < dataLength; j++)
                            {
                                result[ii][j] = ((String[]) stringVect.get(ii))[j];
                            }
                        }
                        ((MaterialSteel) _materialBase).getHBData().setData(result);
                    }

            }
        }

    }


}
