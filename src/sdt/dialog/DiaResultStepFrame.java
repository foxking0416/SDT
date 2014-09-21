package sdt.dialog;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import sdt.data.*;
import sdt.framework.*;

public class DiaResultStepFrame extends DiaMain
{

    private JTabbedPane             _tabbed;
    private table_model_material    tableModelC;

    private JTable                  materialTableC;
    private JScrollPane             scrollPanelC;
    private JPanel                  panelContent;
    private int                     rowInterval;
    private String[]                tableHeader;

    public DiaResultStepFrame(SDT_System system)
    {
        super(system, false);
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

        str = this._system.GetInterfaceString("TitleResultStepFrame");

        this.readDiaData();
        this.setTitle(str);
        this.setSize(380, 340);
        this.setLocation();


        if (tableModelC.getRowCount()  != 0)
        {
            this.setVisible(true);
        }
        else
        {
            String strMsg = this._system.GetInterfaceString("dia_MessageStrNoFEMResult");
            DiaMessage dia = new DiaMessage(this._system, strMsg);
            this.dispose();
        }



    }



    protected void createComponent()
    {
        this._tabbed = new JTabbedPane();

        this.tableModelC             = new table_model_material(this._system);
        this.materialTableC          = new JTable(tableModelC);

        this.scrollPanelC            = new JScrollPane(materialTableC);

        panelContent = new JPanel();


    }

    protected void setSizeComponent()
    {
        Dimension dim = new Dimension(350, 55);
        JTableHeader tableHeader;

        tableHeader = this.materialTableC.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.setSize(dim);
        tableHeader.setPreferredSize(dim);

        rowInterval = 25;
        this.materialTableC.setRowHeight(this.rowInterval);

    }

    protected void addComponent()
    {
        panelContent.setLayout(new BorderLayout());

        this._contentPane.setLayout(null);
        this._tabbed.setBounds(10, 40, 360, 250);
        this.add(_tabbed);


        this._jButtonOk.setBounds(190, 305, 70, 25);
        this._jButtonCancel.setBounds(280, 305, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);
    }

    protected void addListener()
    {
         this.materialTableC.addMouseListener(this);
    }


    private void AddToPanelC(ArrayList eigenArray,ArrayList frequencyArray)
    {

        int arraySize = eigenArray.size();

        String result[][] = new String[arraySize][3];

        for (int i = 0; i < arraySize; i++)
        {
            result[i][0] =  (i + 1) +  "";
            result[i][1] = (String) eigenArray.get(i);
            result[i][2] = (String) frequencyArray.get(i);
        }

        tableModelC.SetTableModel(tableHeader, result);
        panelContent.add(this.scrollPanelC, BorderLayout.CENTER);
    }

    protected boolean checkTextfield()
    {
        return false;
    }

    protected void readDiaData()
    {
        ArrayList eigneValueArray = this._system.getDataManager().getEigenValueArray();
        ArrayList frequencyArray  = this._system.getDataManager().getFrequencyArray();

        if(eigneValueArray == null)
            return;

        tableHeader = new String[]{"index","EigenValue","Frequency"};

        this.AddToPanelC(eigneValueArray,frequencyArray);


        this._tabbed.removeAll();
        this._tabbed.add(this.panelContent, "Step/Frame");

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

         }
         if (e.getSource() == this._jButtonClose)
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
             this._system.changeTo3DDeformed(index);
         }
     }




}
