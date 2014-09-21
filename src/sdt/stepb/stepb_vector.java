package sdt.stepb;

public class stepb_vector extends stepb_object
{
    private double          length;
    private stepb_direction direction;
    public stepb_vector(stepb_direction dir, double len)
    {
        direction = dir;
        length = len;
    }
}
