package sdt.stepb;

public class stepb_circle extends stepb_curve
{
    private stepb_axis   theAxis;
    private double       theRadius;
    public  stepb_axis   GetAxis()   {return this.theAxis;}
    public  double       GetRadius() {return this.theRadius;}
    public stepb_circle(stepb_axis axis,double radius)
    {
        super();
        this.Name= "STEP_CIRCLE";
        theAxis = axis;
        theRadius = radius;
    }
}
