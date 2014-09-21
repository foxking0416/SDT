package sdt.java3d.BTGgroup;

import javax.media.j3d.*;
import javax.vecmath.*;

public class SDT_TransformGroup extends TransformGroup
{

    public SDT_TransformGroup()
    {
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        this.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        this.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

        this.setCapability(TransformGroup.ALLOW_AUTO_COMPUTE_BOUNDS_READ);
        this.setCapability(TransformGroup.ALLOW_AUTO_COMPUTE_BOUNDS_WRITE);
        this.setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        this.setCapability(TransformGroup.ALLOW_BOUNDS_WRITE);
    }

    public SDT_TransformGroup(Transform3D transform3d)
    {
        super(transform3d);
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        this.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        this.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
    }

    public void Translate(Vector3d dt)
    {
        Transform3D t3d = new Transform3D();
        Vector3d translte = new Vector3d();
        this.getTransform(t3d);
        t3d.get(translte);
        translte.add(dt);
        t3d.setTranslation(translte);
        this.setTransform(t3d);
    }

    public void RotateX(double theta)
    {
        Transform3D t3d = new Transform3D();
        Matrix3d rotate1 = new Matrix3d();
        Matrix3d rotate2 = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotate1);
        rotate2.rotX(theta);
        rotate2.mul(rotate1);
        t3d.setRotation(rotate2);
        this.setTransform(t3d);
    }

    public void RotateY(double theta)
    {
        Transform3D t3d = new Transform3D();
        Matrix3d rotate1 = new Matrix3d();
        Matrix3d rotate2 = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotate1);
        rotate2.rotY(theta);
        rotate2.mul(rotate1);
        t3d.setRotation(rotate2);
        this.setTransform(t3d);
    }

    public void RotateZ(double theta)
    {
        Transform3D t3d = new Transform3D();
        Matrix3d rotate1 = new Matrix3d();
        Matrix3d rotate2 = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotate1);
        rotate2.rotZ(theta);
        rotate2.mul(rotate1);
        t3d.setRotation(rotate2);
        this.setTransform(t3d);
    }

    public void RotateAboutAxis(AxisAngle4d axisAngle) //AxisAngle4d(axis_vec,angle)
    {
        Transform3D t3d = new Transform3D();
        Matrix3d rotate1 = new Matrix3d();
        Matrix3d rotate2 = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotate1);
        rotate2.set(axisAngle);
        rotate2.mul(rotate1);
        t3d.setRotation(rotate2);
        this.setTransform(t3d);
    }

    public void ScaleTo(double scale)
    {
        Transform3D t3d = new Transform3D();
        this.getTransform(t3d);
        t3d.setScale(scale);
        this.setTransform(t3d);
    }

    public void ScaleWith(double scale)
    {
        Transform3D t3d = new Transform3D();
        this.getTransform(t3d);
        t3d.setScale(t3d.getScale() * scale);
        this.setTransform(t3d);
    }

    public void RotateWithAxis(Vector3d p0, Vector3d p1, double theta)
    {
        Vector3d dt = new Vector3d( -p0.x, -p0.y, -p0.z);
        Vector3d axis = new Vector3d();
        axis.sub(p1, p0);
        axis.normalize();
        AxisAngle4d axisAngle = new AxisAngle4d(axis, theta);
        this.Translate(dt);
        this.RotateAboutAxis(axisAngle);
        this.Translate(p0);
    }

    public void Mirror(Vector3d normal, Vector3d point)
    {
        Transform3D t = new Transform3D();
        Matrix3d m = new Matrix3d();
        Transform3D t3d = new Transform3D();
        Vector3d p, p0;
        p0 = new Vector3d(0, 0, 0);
        p0 = GetMirrorPoint(p0, normal, point);
        p = new Vector3d(1, 0, 0);
        p = GetMirrorPoint(p, normal, point);
        p.sub(p0);
        m.setColumn(0, p);

        p = new Vector3d(0, 1, 0);
        p = GetMirrorPoint(p, normal, point);
        p.sub(p0);
        m.setColumn(1, p);

        p = new Vector3d(0, 0, 1);
        p = GetMirrorPoint(p, normal, point);
        p.sub(p0);
        m.setColumn(2, p);

        t.setRotation(m);
        t.setTranslation(p0);
        this.getTransform(t3d);
        t.mul(t3d);
        this.setTransform(t);
    }

    private Vector3d GetMirrorPoint(Vector3d p0, Vector3d normal,
                                    Vector3d point)
    {
        Vector3d v = new Vector3d();
        v.sub(p0, point);
        double dist = v.dot(normal);
        return new Vector3d(p0.x - 2.0 * normal.x * dist,
                            p0.y - 2.0 * normal.y * dist,
                            p0.z - 2.0 * normal.z * dist);
    }

    public void LocalTranslateX(double dx)
    {
        Transform3D t3d = new Transform3D();
        Vector3d vec = new Vector3d();
        Vector3d translte = new Vector3d();
        Matrix3d rotation = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotation, translte);
        rotation.getColumn(0, vec);
        vec.scale(dx);
        translte.add(vec);
        t3d.setTranslation(translte);
        this.setTransform(t3d);
    }

    public void LocalTranslateY(double dy)
    {
        Transform3D t3d = new Transform3D();
        Vector3d vec = new Vector3d();
        Vector3d translte = new Vector3d();
        Matrix3d rotation = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotation, translte);
        rotation.getColumn(1, vec);
        vec.scale(dy);
        translte.add(vec);
        t3d.setTranslation(translte);
        this.setTransform(t3d);
    }

    public void LocalTranslateZ(double dz)
    {
        Transform3D t3d = new Transform3D();
        Vector3d vec = new Vector3d();
        Vector3d translte = new Vector3d();
        Matrix3d rotation = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotation, translte);
        rotation.getColumn(2, vec);
        vec.scale(dz);
        translte.add(vec);
        t3d.setTranslation(translte);
        this.setTransform(t3d);
    }

    public void LocalRotateX(double theta)
    {
        Transform3D t3d = new Transform3D();
        Transform3D t3d2 = new Transform3D();
        Vector3d vec = new Vector3d();
        Matrix3d rotate1 = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotate1);
        rotate1.getColumn(0, vec);
        t3d2.set(new AxisAngle4d(vec, theta));
        t3d2.mul(t3d);
        this.setTransform(t3d2);
    }

    public void LocalRotateY(double theta)
    {
        Transform3D t3d = new Transform3D();
        Transform3D t3d2 = new Transform3D();
        Vector3d vec = new Vector3d();
        Matrix3d rotate1 = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotate1);
        rotate1.getColumn(1, vec);
        t3d2.set(new AxisAngle4d(vec, theta));
        t3d2.mul(t3d);
        this.setTransform(t3d2);

    }

    public void LocalRotateZ(double theta)
    {
        Transform3D t3d = new Transform3D();
        Transform3D t3d2 = new Transform3D();
        Vector3d vec = new Vector3d();
        Matrix3d rotate1 = new Matrix3d();
        this.getTransform(t3d);
        t3d.get(rotate1);
        rotate1.getColumn(2, vec);
        t3d2.set(new AxisAngle4d(vec, theta));
        t3d2.mul(t3d);
        this.setTransform(t3d2);
    }

    public void SetViewDirection(double[] matrix)
    {
        Transform3D t3d = new Transform3D();
        this.getTransform(t3d);
        double scale = t3d.getScale();
        t3d.set(matrix);
        t3d.setScale(scale);
        this.setTransform(t3d);
    }

    public void SetAxisViewDirection(double[] matrix)
    {
        Transform3D t3d = new Transform3D();
        Vector3d v3d = new Vector3d();
        this.getTransform(t3d);
        t3d.get(v3d);
        double scale = t3d.getScale();
        t3d.set(matrix);
        t3d.setScale(scale);
        t3d.setTranslation(v3d);
        this.setTransform(t3d);
    }

}

