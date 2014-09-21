package sdt.stepb;

public class stepb_axis  extends stepb_object
{
    stepb_cartesian_point theOrigin;
    stepb_direction       theDirZ;
    stepb_direction       theDirX;
    public stepb_cartesian_point GetOrigin()             {return theOrigin;}
    public stepb_direction       GetZAxis()              {return theDirZ;}

    public stepb_axis(stepb_cartesian_point ori,stepb_direction dirZ,stepb_direction dirX)
    {
        this.theOrigin = ori;
        this.theDirZ =dirZ;
        this.theDirX =dirX;
    }
}
