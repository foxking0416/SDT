package sdt.material;

import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;

public abstract  class MaterialBase
{
    protected String   _path;
    protected String[] _tableHeader;
    protected String[] _rawData;

    public String getPath()
    {
        return this._path;
    }

    public String[] getTableHeader()
    {
        return this._tableHeader;
    }

    public void setSeletedRowData(String[] rawData)
    {
        _rawData = rawData;
    }

    public String[] getRawData()
    {
        return _rawData;
    }

    public void save(FileWriter fw) throws IOException
    {
        //fw.write("*****Material*****\n");
        String rowData = "";

        for (int i = 0; i < this._rawData.length - 1; i++)
            rowData += (this._rawData[i] + ",");

        rowData += this._rawData[this._rawData.length - 1] ;

        fw.write("         Material Data: ," + rowData + "\n");
    }

    public void open(BufferedReader br) throws IOException
    {
        String bufLine = br.readLine();
        _rawData = this.readStringToArray(bufLine);
    }

    public String[] getMaterialRawData()
    {
        return _rawData;
    }

    public String[] readStringToArray(String bufLine)
    {
        String[] resultStrs = null;
        String resultStr = "";

        ArrayList stringArray = new ArrayList();
        StringTokenizer token = new StringTokenizer(bufLine, ",");
        while (token.hasMoreTokens())
        {
            resultStr = token.nextToken();
            stringArray.add(resultStr);
        }
        resultStrs = new String[stringArray.size() -1];

        for(int i = 1 ; i < stringArray.size() ; i++)  //first String is Material:
        {
            resultStrs[i-1] = (String)stringArray.get(i);
        }
        return resultStrs;

    }


}
