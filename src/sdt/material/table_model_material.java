package sdt.material;



import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;
import sdt.framework.SDT_System;
import sdt.material.TableButton.Type;


public class table_model_material extends AbstractTableModel
{
    private SDT_System _system;
    private String[] _columnNames =
                                   {};
    private Vector _rowDatas;
    private int column;
    private int rowCount;
    private boolean _isEditable = true;

    private int _buttonColumn = -1;

    public table_model_material(SDT_System system)
    {
        this._system = system;
    }

    public void setResultSet(ResultSet results)
    {
        try
        {
            ResultSetMetaData metaData = results.getMetaData();
            int column = metaData.getColumnCount();
            _columnNames = new String[column];
            //取得所有欄位名稱
            for (int i = 0; i < column; i++)
            {
                _columnNames[i] = metaData.getColumnLabel(i + 1);
            }
            //取得每個欄位的所有資料
            _rowDatas = new Vector();
            String[] rowData;
            while (results.next())
            {
                rowData = new String[column];
                for (int i = 0; i < column; i++)
                {
                    rowData[i] = results.getString(i + 1);
                }
                _rowDatas.addElement(rowData);
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
            this._columnNames = names;
            this.column = columnData[0].length ;
            this.rowCount = columnData.length;

            //取得每個欄位的所有資料
            _rowDatas = new Vector();
            Object[] rowData;

            for (int i = 0; i < rowCount; i++)
            {
                rowData = new Object[column];
                for (int j = 0; j < column ; j++)
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
                _rowDatas.addElement(rowData);
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
        return _columnNames.length;
    }

    public int getRowCount()
    {
        if (_rowDatas == null)
        {
            return 0;
        }
        else
        {
            return _rowDatas.size();
        }
    }

    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }
    public void setEditable(boolean bool)
    {
        _isEditable = bool;
    }

    public void setButtonColumn(int column)
    {
        this._buttonColumn = column;
    }


    public boolean isCellEditable(int row, int col)
    {

        if(col == this._buttonColumn)
        {
            return true;
        }
        return _isEditable;
    }


    public void setValueAt(Object value, int row, int col)
    {

        ((Object[]) (_rowDatas.elementAt(row)))[col] = value;
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
        return ((Object[]) (_rowDatas.elementAt(row)))[column];
    }

    public Object[] getRowData(int row)
    {
        return (Object[]) (_rowDatas.elementAt(row));
    }

    public String[][] getAllData()
    {
        int arraySize = _rowDatas.size();
        int dataSize = ((Object[])_rowDatas.get(0)).length;

        String[][] result = new String[arraySize][dataSize];

        for (int i = 0; i < arraySize; i++)
        {
            for(int j = 0 ; j < dataSize ; j++)
            {
                result[i][j] = ((Object[]) _rowDatas.get(i))[j].toString();
            }
        }

        return result;
    }



    public String getColumnName(int column)
    {
        return _columnNames[column] == null ? "No Name " : _columnNames[column];
    }
}

