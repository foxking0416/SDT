package sdt.stepb;

public class stepb_edge_curve_array extends stepb_object_array
{
    public stepb_edge_curve_array()
    {
        super();

    }

    public stepb_edge_curve get(int index)
     {
         return (stepb_edge_curve)super.get(index);

     }

     public void add(stepb_edge_curve ec)
     {
         this.list.add(ec);
     }

}
