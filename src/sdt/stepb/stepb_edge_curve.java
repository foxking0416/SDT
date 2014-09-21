package sdt.stepb;


public class stepb_edge_curve extends stepb_object
{
    private stepb_vertex_point theStartPt;
    private stepb_vertex_point theEndPt;
    private stepb_curve        theCurve;

    public stepb_vertex_point GetStartPoint()  {return this.theStartPt;}
    public stepb_vertex_point GetEndPoint()    {return this.theEndPt;}
    public stepb_curve        GetCurve()       {return this.theCurve;}

    public stepb_edge_curve(stepb_vertex_point vpt1,stepb_vertex_point vpt2,stepb_curve crv)
    {
        this.Name= "STEP_EDGE_CURVE";
        theStartPt = vpt1;
        theEndPt   = vpt2;
        theCurve   = crv;

    }


}
