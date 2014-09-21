package sdt.material;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class MaterialMagnet extends MaterialBase
{
    public MaterialMagnet()
    {
        this._path = "materials\\magnet.csv";
        this._tableHeader = new String[]{"No.", "Material Name", "Relative Permeability", "Hc (KA/m)"};
        this._rawData = new String[]{" "," "," "};
    }



}
