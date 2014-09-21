package sdt.stepb;

public class math_vector3d
{
    private double x;
    private double y;
    private double z;

    public math_vector3d()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public math_vector3d(double x_value, double y_value, double z_value)
    {
        this.x = x_value;
        this.y = y_value;
        this.z = z_value;
    }

    public math_vector3d(math_vector3d mathVector)
    {
        this.x = mathVector.X();
        this.y = mathVector.Y();
        this.z = mathVector.Z();
    }

    public math_vector3d(double[] floatArry)
    {
        this.x = floatArry[0];
        this.y = floatArry[1];
        this.z = floatArry[2];
    }

    public double X()
    {
        return this.x;
    }

    public double Y()
    {
        return this.y;
    }

    public double Z()
    {
        return this.z;
    }

    public void SetX(double x_value)
    {
        this.x = x_value;
    }

    public void SetY(double y_value)
    {
        this.y = y_value;
    }

    public void SetZ(double z_value)
    {
        this.z = z_value;
    }

    public void Set(double x_value, double y_value, double z_value)
    {
        this.x = x_value;
        this.y = y_value;
        this.z = z_value;
    }

    public void Set(double[] floatArry)
    {
        this.x = floatArry[0];
        this.y = floatArry[1];
        this.z = floatArry[2];
    }

    public void Set(math_vector3d mathVector)
    {
        this.x = mathVector.X();
        this.y = mathVector.Y();
        this.z = mathVector.Z();
    }

    public math_vector3d Add(math_vector3d mathVector)
    {
        return new math_vector3d(this.x + mathVector.X(), this.y + mathVector.Y(),
                               this.z + mathVector.Z());
    }

    public math_vector3d Add(math_vector3d mathVector, double factor)
    {
        return new math_vector3d(this.x + mathVector.X()*factor,
                               this.y + mathVector.Y()*factor,
                               this.z + mathVector.Z()*factor);
    }



    public math_vector3d Subtract(math_vector3d mathVector)
    {
        return new math_vector3d(this.x - mathVector.X(), this.y - mathVector.Y(),
                               this.z - mathVector.Z());
    }
    public math_vector3d Subtract2(math_vector3d mathVector)
    {
       this.x -= mathVector.X();
       this.y -= mathVector.Y();
       this.z -= mathVector.Z();

       return this;
    }


    public math_vector3d Subtract(math_vector3d mathVector, double factor)
    {
        return new math_vector3d(this.x - mathVector.X()*factor,
                               this.y - mathVector.Y()*factor,
                               this.z - mathVector.Z()*factor);
    }



    public math_vector3d Multiply(double double0)
    {
        return new math_vector3d(this.x * double0, this.y * double0,
                               this.z * double0);
    }

    public math_vector3d Divide(double double0)
    {
        return new math_vector3d(this.x / double0, this.y / double0,
                               this.z / double0);
    }

    public math_vector3d Normalize()
    {
        double scale = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) +
                                 Math.pow(this.z, 2));
        return this.Divide( (double) scale);
    }

    public double DotProduct(math_vector3d mathVector)
    {
        return (this.x * mathVector.X() + this.y * mathVector.Y() +
                this.z * mathVector.Z());
    }

    public math_vector3d CrossProduct(math_vector3d mathVector)
    {
        return new math_vector3d(this.y * mathVector.Z() - this.z * mathVector.Y(),
                               this.z * mathVector.X() - this.x * mathVector.Z(),
                               this.x * mathVector.Y() - this.y * mathVector.X());
    }

    public math_vector3d CrossProductThenNormalize(math_vector3d mathVector)
    {
        return this.CrossProduct(mathVector).Normalize();
    }

    public boolean IsEqual(math_vector3d mathVector)
    {
        if ( (double) (this.x - mathVector.X()) == 0 &&
            (double) (this.y - mathVector.Y()) == 0 &&
            (double) (this.z - mathVector.Z()) == 0)
        {
            return true;
        }
        else
            return false;
    }

    public boolean IsNotEqual(math_vector3d mathVector)
    {
        if ( (double) (this.x - mathVector.X()) == 0 &&
            (double) (this.y - mathVector.Y()) == 0 &&
            (double) (this.z - mathVector.Z()) == 0)
        {
            return false;
        }
        else
            return true;
    }

    public double Length()
    {
        return (Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) +
                          Math.pow(this.z, 2)));
    }

    public double Distance(math_vector3d mathVector)
    {
        return this.Subtract(mathVector).Length();
    }
    public void translate(double dx,double dy, double dz)
    {
        this.x += dx;
        this.y += dy;
        this.z += dz;
    }

}
