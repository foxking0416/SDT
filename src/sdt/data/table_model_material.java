package sdt.data;



import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;
import sdt.framework.SDT_System;


public class table_model_material extends AbstractTableModel
{
    private SDT_System _system;
    private String[] columnNames =
                                   {};
    private Vector rowDatas;
    private int column;
    private int rowCount;

    public table_model_material(SDT_System system)
    {
        this._system = system;
    }

    public void set_result_set(ResultSet results)
    {
        try
        {
            ResultSetMetaData metaData = results.getMetaData();
            int column = metaData.getColumnCount();
            columnNames = new String[column];
            //取得所有欄位名稱
            for (int i = 0; i < column; i++)
            {
                columnNames[i] = metaData.getColumnLabel(i + 1);
            }
            //取得每個欄位的所有資料
            rowDatas = new Vector();
            String[] rowData;
            while (results.next())
            {
                rowData = new String[column];
                for (int i = 0; i < column; i++)
                {
                    rowData[i] = results.getString(i + 1);
                }
                rowDatas.addElement(rowData);
            }
            this.fireTableChanged(null);
        }
        catch (SQLException sqle)
        {
            System.err.println(sqle + "欄位儲入表格錯誤");
        }
    }

    public void SetTableModel(String[] names, String[][] columnData)
    {
        try
        {
            //取得所有欄位名稱
            this.columnNames = names;
            this.column = names.length;
            this.rowCount = columnData.length;

            //取得每個欄位的所有資料
            rowDatas = new Vector();
            Object[] rowData;

            for (int i = 0; i < rowCount; i++)
            {
                rowData = new Object[column];
                for (int j = 0; j < column; j++)
                {
                   /* if (j == column - 3)
                        rowData[j] = new Boolean(columnData[i][j]);
                    else if (j == column - 2)
                    {
                        JButton jb = new JButton("GO");
                        jb.setToolTipText(columnData[i][0]);

                        if (columnData[i][j - 1].equals("false"))
                            jb.setEnabled(true);
                        else if (columnData[i][j - 1].equals("true"))
                            jb.setEnabled(false);

                        //jb.setEnabled(!Boolean.parseBoolean(columnData[i][j-1]));
                        rowData[j] = jb; //new table_celleditor_revision();

                        // ( (JButton) rowData[j,]).setEnabled( ( (Boolean) rowData[j - 1]).booleanValue());
                    }
                    else if (j == column - 1)
                    {
                        rowData[j] = (String) columnData[i][j - 1];
                    }
                    else*/

                        rowData[j] = (String) columnData[i][j];
                }
                rowDatas.addElement(rowData);
            }

            this.fireTableChanged(null);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }


    public int getColumnCount()
    {
        return columnNames.length;
    }

    public int getRowCount()
    {
        if (rowDatas == null)
        {
            return 0;
        }
        else
        {
            return rowDatas.size();
        }
    }

    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col)
    {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col == 6)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setValueAt(Object value, int row, int col)
    {

        ((Object[]) (rowDatas.elementAt(row)))[col] = value;
        fireTableCellUpdated(row, col);
        /* if(col == 6)
         {

             String revID = (String)((Object[])(rowDatas.elementAt(row)))[0];

             theSystem.CreateMessage(define_message.MSG_INFO,"You are Picking \n" +
                                                         "             RevID                = "+ revID + " \n"+
                                                         "             Is Model Valid =" + value + " \n"+
                                                         "                       , code is typed in table_model_reversion line132");

         }*/

    }


    public Object getValueAt(int row, int column)
    {
        return ((Object[]) (rowDatas.elementAt(row)))[column];
    }

    public String getColumnName(int column)
    {
        return columnNames[column] == null ? "No Name " : columnNames[column];
    }
}

