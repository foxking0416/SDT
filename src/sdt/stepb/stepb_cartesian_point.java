package sdt.stepb;


public class stepb_cartesian_point extends stepb_object
{


    private math_vector3d      math_vector;
    private boolean           _isConnectionPt;

    public double           X()                 {return math_vector.X();}
    public double           Y()                 {return math_vector.Y();}
    public double           Z()                 {return math_vector.Z();}
    public math_vector3d    GetMathVector3d()   {return this.math_vector;   }
    public void             setIsConnectionPt(boolean isConnection) {_isConnectionPt = isConnection;}


    public boolean getIsConnectionPt()
    {
        return this._isConnectionPt;
    }

    public stepb_cartesian_point()
    {
        super();
        math_vector = new math_vector3d();

    }

    public stepb_cartesian_point(double ix, double iy, double iz)
    {
        super();
        math_vector = new math_vector3d(ix,iy,iz);
    }

    public math_vector3d GetMathVector3dScaled(double scale)
    {
        return this.math_vector.Multiply(scale);
    }

    public stepb_cartesian_point(math_vector3d mathvec)
    {
        super();
        this.math_vector  = mathvec;
    }
    public stepb_cartesian_point(stepb_cartesian_point cartesianPt)
    {
        super();
        this.math_vector  = new math_vector3d(cartesianPt.X(),cartesianPt.Y(),cartesianPt.Z());
    }

    public boolean isEqualValue(stepb_cartesian_point cartesianPt)
    {
        //System.out.println("CompareTwoPoints!!!!!!");
        //System.out.println("( " + this.math_vector.X() + " ," + this.math_vector.Y() + " ," + this.math_vector.Z() + " )");
        //System.out.println("( " + cartesianPt.X() + " ," + cartesianPt.Y() + " ," + cartesianPt.Z() + " )");

        if( Math.abs( this.math_vector.X() - cartesianPt.X())< 10E-3 &&
            Math.abs( this.math_vector.Y() - cartesianPt.Y())< 10E-3 &&
            Math.abs( this.math_vector.Z() - cartesianPt.Z())< 10E-3
            )
        {
            return true;
        }
        return false;
    }


    public void Print()
    {
        System.out.println(this.PrintStr());
    }
    public String PrintStr()
       {
           return (this.IDNumber + " , " + math_vector.X() + " , " + math_vector.Y() + " , " + math_vector.Z());
    }


}
