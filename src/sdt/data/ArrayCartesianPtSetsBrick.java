package sdt.data;

import java.util.*;

public class ArrayCartesianPtSetsBrick
{
    protected ArrayList arrayList;

    public ArrayCartesianPtSetsBrick()
    {
        arrayList = new ArrayList();
    }

    public void add(CartesianPointSetsBrick brick)
    {
        arrayList.add(brick);
    }

    public CartesianPointSetsBrick get(int index)
    {
        return (CartesianPointSetsBrick) arrayList.get(index);
    }

    public int Size()
    {
        return this.arrayList.size();
    }

    public void addArray(ArrayCartesianPtSetsBrick array)
    {
        for (int i = 0; i < array.Size(); i++)
        {
            this.arrayList.add(array.arrayList.get(i));
        }
    }
    public void removeAll()
    {
        this.arrayList.clear();
    }


}


