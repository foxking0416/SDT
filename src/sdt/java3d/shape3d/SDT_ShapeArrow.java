package sdt.java3d.shape3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import sdt.java3d.BTGgroup.*;

public class SDT_ShapeArrow extends SDT_BranchGroup
{
    private Vector3d direction;
    private Vector3d centerTranslate;
    private SDT_TransformGroup tg;
    private SDT_TransformGroup parent;
    private SDT_TransformGroup ConeTg,CyliTg,ConeTg2,CyliTg2;

    public void SetDirection(Vector3d v)                                        { this.direction = v;}
    public void SetCenterTranslate(Vector3d t)                                  { this.direction = t;}

    public SDT_ShapeArrow()
    {
        this.direction = new Vector3d();
        this.centerTranslate = new Vector3d();
        this.tg = new SDT_TransformGroup();
        this.addChild(tg);
        Transform3D coneT3d = new Transform3D();
        coneT3d.set( new Vector3d(0.0, 45.0,0.0));
        Transform3D coneT3d2 = new Transform3D();
        Matrix3d m3d = new Matrix3d();
        m3d.rotX(Math.PI);
        coneT3d2.setRotation(m3d);
        coneT3d2.setTranslation(new Vector3d(0.0, -45.0,0.0));
        Transform3D cylinT3d = new Transform3D();
        cylinT3d.set( new Vector3d(0.0, 15.0,0.0));
        Transform3D cylinT3d2 = new Transform3D();
        cylinT3d2.setRotation(m3d);
        cylinT3d2.setTranslation( new Vector3d(0.0, -15.0,0.0));
        Appearance app = new Appearance();
        ColoringAttributes col = new ColoringAttributes(new Color3f(1.0F, 0.5F, 0.0F),ColoringAttributes.SHADE_FLAT);
        app.setColoringAttributes(col);
        this.ConeTg = new SDT_TransformGroup(coneT3d);
        this.CyliTg = new SDT_TransformGroup(cylinT3d);
        this.ConeTg2 = new SDT_TransformGroup(coneT3d2);
        this.CyliTg2 = new SDT_TransformGroup(cylinT3d2);
        Cone cone = new Cone(6.0f, 30.0f,  app);//radius,height
        Cone cone2 = new Cone(6.0f, 30.0f,  app);//radius,height
        Cylinder cylin = new Cylinder(3.0f,30f,app);
        Cylinder cylin2 = new Cylinder(3.0f,30f,app);
        cone.setPickable(false);
        cylin.setPickable(false);
        cone2.setPickable(false);
        cylin2.setPickable(false);
        this.ConeTg.addChild(cone);
        this.CyliTg.addChild(cylin);
        this.ConeTg2.addChild(cone2);
        this.CyliTg2.addChild(cylin2);
        this.Draw();
    }

    public void SetParant(SDT_TransformGroup btg)
    {
        parent = btg;
    }

    public void BackMode()
    {
        double theta;
        Vector3d axis;
        if (Math.abs(1.0 - Math.abs(direction.y)) < 1e-8)
        {
            if (direction.y > 0)
            {
                axis = new Vector3d(1.0, 0.0, 0.0);
                theta = Math.PI;
            }
            else
            {
                axis = new Vector3d(1.0, 0.0, 0.0);
                theta = 0.0;
            }
        }
        else
        {
            axis = new Vector3d(0.0, 1.0, 0.0);
            theta = Math.acos(direction.y)+Math.PI;
            axis.cross(axis,direction);
            axis.normalize();
        }
        AxisAngle4d a = new AxisAngle4d(axis, theta);
        Transform3D t3d = new Transform3D();
        this.tg.getTransform(t3d);
        t3d.setRotation(a);
        t3d.set(this.centerTranslate);
        this.tg.setTransform(t3d);
    }

    public void FrontMode()
    {
        double theta;
        Vector3d axis;
        if (Math.abs(1.0 - Math.abs(direction.y)) < 1e-8)
        {
            if (direction.y > 0)
            {
                axis = new Vector3d(1.0, 0.0, 0.0);
                theta = 0.0;
            }
            else
            {
                axis = new Vector3d(1.0, 0.0, 0.0);
                theta = Math.PI;
            }
        }
        else
        {
            axis = new Vector3d(0.0, 1.0, 0.0);
            theta = Math.acos(direction.y);
            axis.cross(axis,direction);
            axis.normalize();
        }
        AxisAngle4d a = new AxisAngle4d(axis, theta);
        Transform3D t3d = new Transform3D();
        this.tg.getTransform(t3d);
        t3d.setRotation(a);
        t3d.set(this.centerTranslate);
        this.tg.setTransform(t3d);
    }


    public SDT_BranchGroup Draw()
    {
        this.detach();
        this.tg.removeAllChildren();
        this.tg.addChild(ConeTg);
        this.tg.addChild(CyliTg);
        if (parent != null)
        {
            parent.addChild(this);
        }
        return this;
    }

    public SDT_BranchGroup DrawDouble()
    {
        this.detach();
        this.tg.removeAllChildren();
        this.tg.addChild(ConeTg);
        this.tg.addChild(CyliTg);
        this.tg.addChild(ConeTg2);
        this.tg.addChild(CyliTg2);
        if (parent != null)
        {
            parent.addChild(this);
        }
        return this;
    }

}
