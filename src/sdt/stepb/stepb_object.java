package sdt.stepb;

public class stepb_object
{
    protected int       IDNumber;
    protected String    Name;

    public int      GetIDNumber()               {return this.IDNumber;}
    public String   GetName()                   {return this.Name;}
    public void     SetIDNumber(int idNumber)   {this.IDNumber = idNumber;}
    public stepb_object()
    {
        this.IDNumber = -1;
        this.Name     = "";
    }
}
