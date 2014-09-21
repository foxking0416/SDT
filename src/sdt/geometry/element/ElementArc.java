package sdt.geometry.element;

import java.awt.*;
import java.io.*;

import sdt.geometry.*;
import sdt.panel.drawpanel.*;
import sdt.data.DataBase;

public class ElementArc extends ElementBase implements Serializable
{

    public double radius;
    public int angleStart;
    public int angleEnd;
    ObjectPoint endPt;
    ObjectPoint startPt;
    ObjectPoint centerPt;
    ObjectRectangle recBoundary;


    /*public ElementArc(ObjectPoint spt, ObjectPoint ept, Color c,int angle_Start,int angle_End)
    {
        super(c);
        //this.origin = spt;
        this.startPt = spt;
        this.endPt = ept;

        this.angleStart = angle_Start;
        this.angleEnd = angle_End;

        this.centerPt = new ObjectPoint();
        this.recBoundary = new ObjectRectangle();

        //this.radius = this.getRadius(this.startPt, this.endPt);
    }*/

    public ElementArc(ObjectPoint spt, ObjectPoint ept, ObjectPoint cpt, Color c, DataBase data)//,int angle_Start,int angle_End)
    {
        super(c, data);

        this.startPt = spt;
        this.endPt = ept;
        this.centerPt = cpt;

        //this.angleStart = angle_Start;
        //this.angleEnd = angle_End;
        this.recBoundary = new ObjectRectangle();
    }

    public boolean setBoundary()
    {
        ObjectPoint leftTop = new ObjectPoint();

        this.radius = Math.sqrt(Math.pow(this.startPt.x - this.centerPt.x, 2) + Math.pow(this.startPt.y - this.centerPt.y, 2));
        leftTop.x = this.centerPt.x - this.radius;
        leftTop.y = this.centerPt.y + this.radius;

        this.rec.setLocation(leftTop);//這個rec是用來畫圖的
        this.rec.width = 2 * this.radius;
        this.rec.height = 2 * this.radius;


        //計算弧的起始角度
        if(Math.abs(this.startPt.x - this.centerPt.x) < 10E-5 && this.startPt.y > this.centerPt.y)
            this.angleStart = 90;
        else if(Math.abs(this.startPt.x - this.centerPt.x) < 10E-5 && this.startPt.y < this.centerPt.y)
            this.angleStart = 270;
        else if(this.startPt.x > this.centerPt.x)
            this.angleStart = (int)(Math.atan((this.startPt.y - this.centerPt.y) / (this.startPt.x - this.centerPt.x)) / Math.PI * 180);
        else
            this.angleStart = (int)(Math.atan((this.startPt.y - this.centerPt.y) / (this.startPt.x - this.centerPt.x)) / Math.PI * 180) + 180;

        //計算弧的終點角度
        if (Math.abs(this.endPt.x - this.centerPt.x) < 10E-5 && this.endPt.y > this.centerPt.y)
            this.angleEnd = 90;
        else if (Math.abs(this.endPt.x - this.centerPt.x) < 10E-5 && this.endPt.y < this.centerPt.y)
            this.angleEnd = 270;
        else if (this.endPt.x > this.centerPt.x)
            this.angleEnd = (int) (Math.atan((this.endPt.y - this.centerPt.y) / (this.endPt.x - this.centerPt.x)) / Math.PI * 180);
        else
            this.angleEnd = (int) (Math.atan((this.endPt.y - this.centerPt.y) / (this.endPt.x - this.centerPt.x)) / Math.PI * 180) + 180;


         return false;
    }

    public void draw(Graphics g,SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        this.setBoundary();


        g.setColor(this.color);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(_data.getDataManager().getStrokeLine());

        int showRecXY[] = tran.setPointRealToShow(this.rec.x, this.rec.y);
        int showRecWH[] = tran.setLengthRealToShow(this.rec.width, this.rec.height);

        g.drawArc(showRecXY[0], showRecXY[1], showRecWH[0], showRecWH[1], this.angleStart, Math.abs(this.angleEnd - this.angleStart));
    }

    public void move(double dx, double dy)
    {
        this.startPt.translate(dx, dy);
        this.endPt.translate(dx,dy);

        //this.origin.translate(dx, dy);
    }


    public void setStartEndPt(ObjectPoint spt, ObjectPoint ept, ObjectPoint cpt)
    {
        this.startPt = spt;
        this.endPt = ept;
        this.centerPt =(ObjectPoint) cpt;
    }

    public void setStartEndAngle(int angle_Start,int angle_End)
    {
        this.angleStart = angle_Start;
        this.angleEnd = angle_End;
    }

    // check if the pt is in circle or not
    public boolean isPtInBoundary(ObjectPoint pt)
    {
        double distance = Math.sqrt( Math.pow(pt.x - this.centerPt.x, 2) + Math.pow(pt.y - this.centerPt.y, 2));

        int mouseAngle;
        if (Math.abs(pt.x - this.centerPt.x) < 0.001 && pt.y > this.centerPt.y)
            mouseAngle = 90;
        else if (Math.abs(pt.x - this.centerPt.x) < 0.001 && pt.y < this.centerPt.y)
            mouseAngle = 270;
        else if (pt.x > this.centerPt.x)
            mouseAngle = (int) (Math.atan((pt.y - this.centerPt.y) / (pt.x - this.centerPt.x)) / Math.PI * 180);
        else
            mouseAngle = (int) (Math.atan((pt.y - this.centerPt.y) / (pt.x - this.centerPt.x)) / Math.PI * 180) + 180;



        if(Math.abs(distance - this.radius) < this._transfer.setLengthShowToReal(5) && mouseAngle > this.angleStart && mouseAngle < this.angleEnd)
          // && pt.x < this.endPt.x && pt.x > this.startPt.x && pt.y > this.endPt.y && pt.y < this.startPt.y)
            return true;
        else
            return false;
    }

    public void setColor(Color c)
    {
        this.color = c;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException
    {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws
        ClassNotFoundException, IOException
    {
        ois.defaultReadObject();
    }

    public void setConstraint()
    {
    }

}
