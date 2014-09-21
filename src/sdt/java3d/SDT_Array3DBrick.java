package sdt.java3d;

import java.util.ArrayList;

public class SDT_Array3DBrick
{
    protected   ArrayList   arrayList;
    protected   int         _typeID;
    private     int         _brickType;

    public int    getBrickType()                                {return this._brickType;}
    public void   setBrickType(int brickType)                   {this._brickType=brickType; }

    public SDT_Array3DBrick()
    {
        arrayList = new ArrayList();
        _brickType = -1;
    }

    public void add(SDT_3DBrick brick)
    {
        arrayList.add(brick);
    }

    public SDT_3DBrick get(int index)
    {
        return (SDT_3DBrick) arrayList.get(index);
    }

    public int Size()
    {
        return this.arrayList.size();
    }

    public void addArray(SDT_Array3DBrick array)
    {
        for (int i = 0; i < array.Size(); i++)
        {
            this.arrayList.add(array.arrayList.get(i));
        }
    }

    public int getTypeID()
    {
        return this._typeID;
    }
    public void setTypeID(int typeID)
    {
        this._typeID = typeID;
    }

}
