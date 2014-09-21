package sdt.java3d.shape3d;

import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.*;

public class SDT_ShapePoint extends PointArray
{
    private Color3f color;
    private long     stepId;

    public void      SetStepId(long id)                     {this.stepId = id;}
    public long      GetStepId()                            {return this.stepId;}

    public SDT_ShapePoint(Color c)
    {
        super(1, PointArray.COORDINATES | PointArray.COLOR_3);
        this.setCapability(PointArray.ALLOW_COORDINATE_READ);
        this.setCapability(PointArray.ALLOW_COORDINATE_WRITE);
        this.setCapability(PointArray.ALLOW_COUNT_READ);
        this.setCapability(PointArray.ALLOW_COUNT_WRITE);
        this.setCapability(PointArray.ALLOW_INTERSECT);
        this.setCapability(PointArray.ALLOW_COLOR_READ);
        this.setCapability(PointArray.ALLOW_COLOR_WRITE);
        this.setCapability(PointArray.ALLOW_FORMAT_READ);
        this.color = new Color3f(c);
        this.setColor(0, this.color);
    }

    public SDT_ShapePoint(Point3d p,Color c)
    {
        super(1, PointArray.COORDINATES | PointArray.COLOR_3);
        this.setCapability(PointArray.ALLOW_COORDINATE_READ);
        this.setCapability(PointArray.ALLOW_COORDINATE_WRITE);
        this.setCapability(PointArray.ALLOW_COUNT_READ);
        this.setCapability(PointArray.ALLOW_COUNT_WRITE);
        this.setCapability(PointArray.ALLOW_INTERSECT);
        this.setCapability(PointArray.ALLOW_COLOR_READ);
        this.setCapability(PointArray.ALLOW_COLOR_WRITE);
        this.setCapability(PointArray.ALLOW_FORMAT_READ);
        Point3d point = p;
        this.setCoordinate(0, point);
        this.color = new Color3f(c);
        this.setColor(0, this.color);
    }

    public SDT_ShapePoint(int length)
    {
        super(length, 1);
        this.setCapability(PointArray.ALLOW_COORDINATE_READ);
        this.setCapability(PointArray.ALLOW_COORDINATE_WRITE);
        this.setCapability(PointArray.ALLOW_COUNT_READ);
        this.setCapability(PointArray.ALLOW_COUNT_WRITE);
        this.setCapability(PointArray.ALLOW_INTERSECT);
        this.setCapability(PointArray.ALLOW_COLOR_READ);
        this.setCapability(PointArray.ALLOW_COLOR_WRITE);
        this.setCapability(PointArray.ALLOW_FORMAT_READ);
    }

    public SDT_ShapePoint(double x, double y, double z,Color c)
    {
        super(1, PointArray.COORDINATES | PointArray.COLOR_3);
        this.setCapability(PointArray.ALLOW_COORDINATE_READ);
        this.setCapability(PointArray.ALLOW_COORDINATE_WRITE);
        this.setCapability(PointArray.ALLOW_COUNT_READ);
        this.setCapability(PointArray.ALLOW_COUNT_WRITE);
        this.setCapability(PointArray.ALLOW_INTERSECT);
        this.setCapability(PointArray.ALLOW_COLOR_READ);
        this.setCapability(PointArray.ALLOW_COLOR_WRITE);
        this.setCapability(PointArray.ALLOW_FORMAT_READ);
        Point3d point = new Point3d(x, y, z);
        this.setCoordinate(0, point);
        this.color = new Color3f(c);
        this.setColor(0, this.color);
    }

    public void SetColor(Color c)
    {
        this.color = new Color3f(c);
        this.ResetColor();
    }

    public void ChangeColor(Color c)
    {
        Color3f colorToSet = new Color3f(c);
        for (int i = 0; i < this.getVertexCount(); i++)
        {
            this.setColor(i, colorToSet);
        }
    }

    public void ResetColor()
    {
        for (int i = 0; i < this.getVertexCount(); i++)
        {
            this.setColor(i, this.color);
        }
    }

}
