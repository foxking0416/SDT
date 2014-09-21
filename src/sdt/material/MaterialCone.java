package sdt.material;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class MaterialCone extends MaterialBase
{
    public MaterialCone()
    {
        this._path = "materials\\diaphragm.csv";
        this._tableHeader = new String[]
                            {"No.", "Material Name", "<html>Density<br> (kgf/mm^2)</html>", "<html>Young's Modulus<br> (kgf/mm^2)</html>", "<html>Poisson's ratio</html>"};
        ;
        this._rawData = new String[]{" "," "," "," "};
    }



}
