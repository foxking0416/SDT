package sdt.java3d;

import java.util.Vector;

public  class SDT_ArrayCrvpt
{

    protected Vector vector;
    public SDT_ArrayCrvpt()
    {
        vector = new Vector();
    }

    public void Add(Object obj)
    {
        this.vector.add(obj);
    }
    public void InsertAfter(Object objExist,Object obj)
    {
        int index = this.vector.indexOf(objExist);
        this.vector.add(index + 1,obj);
    }
    public Object Get(int index )
    {
        return this.vector.get(index);
    }
    public int Size()
    {
        return this.vector.size();
    }

  /*
    public abstract Object get();
    public abstract void removeAll();
    public abstract void remove();

    public abstract void AddAll(vector_array v);
    public abstract void InsertAfter(Object o);
    public abstract void InsertBefore(Object o);
   */
}
