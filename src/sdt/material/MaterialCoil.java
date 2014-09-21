package sdt.material;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class MaterialCoil extends MaterialBase
{
    public MaterialCoil()
    {
        this._path = "materials\\coil.csv";
        this._tableHeader = new String[]{"No.", "Material Name", "<html>Density<br> (kgf/mm^2)</html>","<html>Young's Modulus<br> (kgf/mm^2)</html>","<html>Poisson's ratio</html>", "<html> Relative Permeability</html>"};

        //this._rowData = new String[7];
        _rawData = new String[]{" "," "," "," "," "};
    }






}
