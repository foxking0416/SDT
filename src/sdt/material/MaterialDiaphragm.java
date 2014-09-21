package sdt.material;

public class MaterialDiaphragm extends MaterialBase
{
    public MaterialDiaphragm()
    {
        this._path = "materials\\diaphragm.csv";
        this._tableHeader = new String[]
                            {"No.", "Material Name", "<html>Density<br> (kgf/mm^2)</html>", "<html>Young's Modulus<br> (kgf/mm^2)</html>", "<html>Poisson's ratio</html>"};
        ;

    }



}
