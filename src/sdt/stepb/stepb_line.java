package sdt.stepb;

public class stepb_line extends stepb_curve
{
    stepb_cartesian_point theCpt;
    stepb_vector          theVec;

    public stepb_line(stepb_cartesian_point cpt,stepb_vector vec)
    {
        super();
        this.Name = "STEP_LINE";
        theCpt = cpt;
        theVec = vec;
    }
}
