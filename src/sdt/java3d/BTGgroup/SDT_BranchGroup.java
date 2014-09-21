package sdt.java3d.BTGgroup;

import javax.media.j3d.*;

public class SDT_BranchGroup extends BranchGroup
{
    public SDT_BranchGroup()
    {
        super();
        this.setCapability(BranchGroup.ALLOW_DETACH);
        this.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        this.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        this.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
    }
}
