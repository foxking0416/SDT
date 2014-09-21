package sdt.stepb;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class stepb_vertex_point extends stepb_object
{
    private stepb_cartesian_point theCPt;
    public  stepb_cartesian_point GetCartesianPoint() {return this.theCPt;}
    public stepb_vertex_point(stepb_cartesian_point pt)
    {
        theCPt = pt;
    }
}
