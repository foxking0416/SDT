package sdt.stepb;

import java.util.Hashtable;


public class stepb_cartesian_point_array extends stepb_object_array
{
    public stepb_cartesian_point_array()
    {
        super();
    }
    public stepb_cartesian_point get(int index)
    {
        return (stepb_cartesian_point)super.get(index);
    }

    public stepb_cartesian_point getByID(int index)
    {
        return (stepb_cartesian_point)super.getByID(index);
    }



}
