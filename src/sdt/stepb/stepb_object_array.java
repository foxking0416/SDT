package sdt.stepb;

import java.util.LinkedList;
import java.util.Hashtable;

public class stepb_object_array
{
    protected LinkedList list;
    protected Hashtable idTable;
    boolean isRerrangeNeed ;

    public stepb_object_array()
    {
        list = new LinkedList();
        idTable = new Hashtable();
        isRerrangeNeed = true;

    }

    public void add(stepb_object enb)
    {
        list.add(enb);
    }

    public stepb_object get(int index)
    {
        return (stepb_object) list.get(index);
    }

    public void RemoveAll()
    {
        list.clear();
    }

    public int size()
    {
        return list.size();
    }

    public void removeFirst()
    {
        list.removeFirst();
    }

    public void removeLast()
    {
        list.removeLast();
    }

    public int indexOf(stepb_object enb)
    {
        return list.indexOf(enb);

    }

    public void remove(int i)
    {
        list.remove(i);
    }

    public void addArray(stepb_object_array array)
    {
        for (int i = 0; i < array.size(); i++)
        {
            this.list.add(array.list.get(i));
        }

    }


    public stepb_object getLast()
    {
        return (stepb_object) list.getLast();
    }

    public void rearrangeTable()
    {
        if(isRerrangeNeed)
        {
            for (int i = 0; i < this.size(); i++)
            {
                idTable.put(this.get(i).IDNumber, i);
            }
            isRerrangeNeed = false;
        }
    }

    public stepb_object getByID(int IDNumber)
    {
        Integer index = (Integer)this.idTable.get(IDNumber);
        if(index == null)
            return null;
        //System.out.println("IDNumber: "  + IDNumber + " --->  " + index.intValue());
        return (stepb_object) list.get(index.intValue());
    }





}
