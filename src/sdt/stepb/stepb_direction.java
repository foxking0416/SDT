package sdt.stepb;

public class stepb_direction extends stepb_object
{
    double x;
    double y;
    double z;

    public double X() {return x;}
    public double Y() {return y;}
    public double Z() {return z;}

    public stepb_direction(double sx,double sy, double sz)
    {
        x = sx;
        y = sy;
        z = sz;
    }
}
