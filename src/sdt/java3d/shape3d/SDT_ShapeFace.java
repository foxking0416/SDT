package sdt.java3d.shape3d;

import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.*;

public class SDT_ShapeFace extends TriangleArray
{
    private Color3f     color;
    private long        stepId;
    private float[]    centroid;

    public void      SetStepId(long id)                     {this.stepId = id;}
    public long      GetStepId()                            {return this.stepId;}
    public float[]  GetCentroid()                          {return this.centroid;}

    public SDT_ShapeFace(int vertexCount, Color c)
    {
        super(vertexCount,
              TriangleArray.COORDINATES | TriangleArray.COLOR_3 |
              TriangleArray.NORMALS);

        this.setCapability(TriangleArray.ALLOW_COORDINATE_READ);
        this.setCapability(TriangleArray.ALLOW_COORDINATE_WRITE);
        this.setCapability(TriangleArray.ALLOW_COUNT_READ);
        this.setCapability(TriangleArray.ALLOW_COUNT_WRITE);
        this.setCapability(TriangleArray.ALLOW_INTERSECT);
        this.setCapability(TriangleArray.ALLOW_COLOR_READ);
        this.setCapability(TriangleArray.ALLOW_COLOR_WRITE);
        this.setCapability(TriangleArray.ALLOW_FORMAT_READ);
        this.color = new Color3f(c);
        for (int i = 0; i < vertexCount; i++)
        {
            this.setColor(i, this.color);
        }
        centroid = new float[3];
    }

    public void SetColor(Color c)
    {
        this.color = new Color3f(c);
        this.ResetColor();
    }

    public void SetCentroid(float[] cen)
    {
        this.centroid[0] = cen[0];
        this.centroid[1] = cen[1];
        this.centroid[2] = cen[2];
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
