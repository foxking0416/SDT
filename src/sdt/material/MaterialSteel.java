package sdt.material;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class MaterialSteel extends MaterialBase
{
    MaterialHBData _hbData = null;
    public MaterialSteel()
    {
        this._path = "materials\\steel.csv";
        this._tableHeader = new String[]
                                 {"No.", "Material Name", "<html>H-B Data or <br>Relative Permeability</html>"};

        this._rawData = new String[]{" "," "};
        _hbData = new MaterialHBData();
    }



    public MaterialHBData getHBData()
    {
        return this._hbData;
    }


    public void save(FileWriter fw) throws IOException
    {
        fw.write("*****Material Steel*****\n");

        String rowData = "";
        for (int i = 0; i < this._rawData.length - 1; i++)
            rowData += (this._rawData[i] + ",");
        rowData += this._rawData[this._rawData.length - 1];

        fw.write("Data: ," + rowData + "\n");

        this._hbData.save(fw);

    }

    public void open(BufferedReader br) throws IOException
    {
        br.readLine().trim();

        //Surround Parameter
        this._rawData = this.readStringToArray(br.readLine());
        this._hbData.open(br);

    }
}









