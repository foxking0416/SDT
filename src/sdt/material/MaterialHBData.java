package sdt.material;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MaterialHBData extends MaterialBase
{
    private String[][] _data;
    private boolean _isCurve;
    private String _permeability;

    public MaterialHBData()
    {
        _data = new String[][]
                {
                {" ", " "},
                {" ", " "}
        };
        _permeability = " ";
        _isCurve = true;
    }
    public void setIsCurve(boolean bool)
    {
        _isCurve = bool;
    }
    public boolean getIsCurve()
    {
        return _isCurve;
    }
    public void setData(String[][] data)
    {
        _data = data;

    }
    public String[][] getData( )
    {
        return _data;
    }

    public void save(FileWriter fw)  throws IOException
    {
        String rowData = " data size: ," + _data.length + "\n {";
        for(int i = 0 ; i < _data.length; i++)
        {
            for( int j = 0 ; j < _data[0].length ; j++)
            {
                if( j != _data[0].length- 1)
                    rowData +=( _data[i][j] + ",");
                else if(i != _data.length -1)
                    rowData += (_data[i][j] + "} \n {");
                else
                    rowData += (_data[i][j] + "} \n");
            }
        }
        fw.write(rowData);
    }

    public void open(BufferedReader br)  throws IOException
    {
        //String[] resultStrs = null;
        ArrayList arrayStr = new ArrayList();
        String readLine = br.readLine();
        StringTokenizer token = new StringTokenizer(readLine, ",");
        token.nextToken();
        int dataSize = Integer.parseInt(token.nextToken());


        int i = 1;
        String resultStr = "";
        while(i <= dataSize)
        {
            readLine = br.readLine();
            String[] dataPair = new String[2];
            token = new StringTokenizer(readLine, ",");
            int j = 0;
            while (token.hasMoreTokens())
            {

                resultStr = token.nextToken();
                resultStr = resultStr.replace(" { ", " ");
                resultStr = resultStr.replace(" } ", " ");
                dataPair[j] = resultStr;
                j++;
            }
            //this._data[i] = dataPair;
            arrayStr.add(dataPair);
            i++;
        }

        this._data = new String[arrayStr.size()][2];
        for(int a = 0 ; a < arrayStr.size(); a++)
        {
            this._data[a] = (String[])arrayStr.get(a);
        }

    }


}
