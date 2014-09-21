package sdt.java3d;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SDT_Array3DMesh
{
    protected ArrayList array;
    protected int  _typeID;

    public SDT_Array3DMesh()
    {
        array = new ArrayList();
    }

    public void add(SDT_3DMesh mesh)
    {
        array.add(mesh);
    }

    public SDT_3DMesh get(int index)
    {
        return (SDT_3DMesh) array.get(index);
    }
    public int Size()
    {
        return this.array.size();
    }
    public void addArray(SDT_Array3DMesh array)
    {
        for(int i = 0 ; i < array.Size();i++)
        {
            this.array.add(array.array.get(i));
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
    public int getLastID()
    {
        return ((SDT_3DMesh) array.get(this.array.size()-1)).getElementID();

    }


}
