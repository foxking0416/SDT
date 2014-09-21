package sdt.java3d.BTGgroup;


import javax.media.j3d.*;
import javax.vecmath.*;

public class SDT_BranchTransformGroup extends SDT_BranchGroup
{
    protected SDT_TransformGroup SpringTG;
    protected SDT_BranchTransformGroup parent;
    protected int showType;
    /*
         0 show
         1 hide
     */
    protected int classType;
    /*
         0 spring_branch_transform_group
         1 spring_component_branch_transform_group
         2 spring_part_branch_transform_group
         3 spring_datumPlane_branch_transform_group
         4 spring_sketch_branch_transform_group
         5 spring_body_branch_transform_group
     */
    public int GetClassType()
    {
        return classType;
    }

    public int GetShowType()
    {
        return this.showType;
    }

    public void SetShowType(int showType)
    {
        this.showType = showType;
    }

    public void GetTransformGroup(Transform3D t3d)
    {
        this.SpringTG.getTransform(t3d);
    }

    public void SetTransformGroup(Transform3D t3d)
    {
        this.SpringTG.setTransform(t3d);
    }

    public void SetTransform(double[] tm)
    {
        Transform3D t3d = new Transform3D(tm);
        this.SpringTG.setTransform(t3d);
    }

    public double[] GetTransform()
    {
        Transform3D t3d = new Transform3D();
        this.SpringTG.getTransform(t3d);
        double[] tm = new double[16];
        t3d.get(tm);
        return tm;
    }

    public SDT_BranchTransformGroup GetParent()
    {
        return this.parent;
    }

    public SDT_TransformGroup GetSpringTG()
    {
        return this.SpringTG;
    }

    public SDT_BranchTransformGroup GetChild(int i)
    {
        return (SDT_BranchTransformGroup)SpringTG.getChild(i);
    }

    public SDT_BranchTransformGroup()
    {
        this.SpringTG = new SDT_TransformGroup();
        this.addChild(SpringTG);
        this.classType = 0;
        this.showType = 0;
    }

    public void Detach()
    {
        this.detach();
    }

    public void Translate(Vector3d dt)
    {
        this.SpringTG.Translate(dt);
    }

    public void RotateX(double theta)
    {
        this.SpringTG.RotateX(theta);
    }

    public void RotateY(double theta)
    {
        this.SpringTG.RotateY(theta);
    }

    public void RotateZ(double theta)
    {
        this.SpringTG.RotateZ(theta);
    }

    public void RotateAboutAxis(AxisAngle4d axisAngle)
    {
        this.SpringTG.RotateAboutAxis(axisAngle);
    }

    public void RotateWithAxis(Vector3d p0, Vector3d p1, double theta)
    {
        this.SpringTG.RotateWithAxis(p0, p1, theta);
    }

    public void LocalTranslateX(double dx)
    {
        this.SpringTG.LocalTranslateX(dx);
    }

    public void LocalTranslateY(double dy)
    {
        this.SpringTG.LocalTranslateY(dy);
    }

    public void LocalTranslateZ(double dz)
    {
        this.SpringTG.LocalTranslateZ(dz);
    }

    public void LocalRotateX(double theta)
    {
        this.SpringTG.LocalRotateX(theta);
    }

    public void LocalRotateY(double theta)
    {
        this.SpringTG.LocalRotateY(theta);
    }

    public void LocalRotateZ(double theta)
    {
        this.SpringTG.LocalRotateZ(theta);
    }
    public void SetAllViewType(int allType)
    {

    }
}
