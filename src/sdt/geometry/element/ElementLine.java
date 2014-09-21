package sdt.geometry.element;

import java.awt.*;

import sdt.geometry.*;
import sdt.panel.drawpanel.*;
import sdt.data.DataBase;

public class ElementLine extends ElementBase
{

    public ObjectPoint startPt;
    public ObjectPoint endPt;



    public ElementLine(ObjectPoint spt, ObjectPoint ept, Color c,DataBase data)
    {
        super(c, data);
        this.startPt = spt;
        this.endPt = ept;
        this.setOrigin();

    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        this.setBoundary();


        Graphics2D g2d = (Graphics2D)g;

        g2d.setStroke(_data.getDataManager().getStrokeLine());
        g2d.setColor(this.color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int showStartXY[]=tran.setPointRealToShow(this.startPt.x, this.startPt.y);
        int showEndXY[]=tran.setPointRealToShow(this.endPt.x, this.endPt.y);

        g2d.drawLine(showStartXY[0], showStartXY[1], showEndXY[0], showEndXY[1]);
    }

    public void drawDashLine(Graphics g, SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        this.setBoundary();

        Graphics2D g2d = (Graphics2D) g;

        int dashRed = this.color.getRed()+30;
        int dashGreen = this.color.getGreen()+30;
        int dashBlue = this.color.getBlue()+30;
        if(dashRed > 255)
            dashRed = 255;
        if(dashGreen > 255)
            dashGreen = 255;
        if(dashBlue > 255)
            dashBlue = 255;

        Color c = new Color(dashRed,dashGreen,dashBlue);

        g2d.setColor(c);
        float[] dashline1= {2f,0f,2f};
        float width = _data.getDataManager().getStrokeLine().getLineWidth();

        BasicStroke basicStroke1 = new BasicStroke((float)(width / 2.0), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, (float)(width / 2.0), dashline1, width);

        g2d.setStroke(basicStroke1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int showStartXY[] = tran.setPointRealToShow(this.startPt.x, this.startPt.y);
        int showEndXY[] = tran.setPointRealToShow(this.endPt.x, this.endPt.y);

        g2d.drawLine(showStartXY[0], showStartXY[1], showEndXY[0], showEndXY[1]);
    }






    public void move(double dx, double dy)
    {
        this.startPt.translate(dx, dy);
        this.endPt.translate(dx, dy);
        this.origin.translate(dx, dy);
        this.rec.translate(dx, dy);
    }

    public void setOrigin()
    {
        this.origin.x = (int) ( (this.startPt.x + this.endPt.x) / 2);
        this.origin.y = (int) ( (this.startPt.y + this.endPt.y) / 2);
    }

    public boolean setBoundary()
    {
        ObjectPoint leftTop = new ObjectPoint();

        if ( (this.endPt.x >= this.startPt.x) && (this.endPt.y <= this.startPt.y))//¥ª¤W¨ì¥k¤U
        {
            this.rec.setLocation(this.startPt);
            this.rec.width = Math.abs(this.endPt.x - this.startPt.x);
            this.rec.height = Math.abs(this.startPt.y - this.endPt.y);
        }

        if ( (this.endPt.x <= this.startPt.x) && (this.endPt.y <= this.startPt.y))//¥k¤W¨ì¥ª¤U
        {
            leftTop.x = this.endPt.x;
            leftTop.y = this.startPt.y;
            this.rec.setLocation(leftTop);
            this.rec.width = Math.abs(this.endPt.x - this.startPt.x);
            this.rec.height = Math.abs(this.startPt.y - this.endPt.y);
        }

        if ( (this.endPt.x <= this.startPt.x) && (this.endPt.y >= this.startPt.y))//¥k¤U¨ì¥ª¤W
        {
            this.rec.setLocation(this.endPt);
            this.rec.width = Math.abs(this.endPt.x - this.startPt.x);
            this.rec.height = Math.abs(this.startPt.y - this.endPt.y);
        }

        if ( (this.endPt.x >= this.startPt.x) && (this.endPt.y >= this.startPt.y))//¥ª¤U¨ì¥k¤W
        {
            leftTop.x = this.startPt.x;
            leftTop.y = this.endPt.y;
            this.rec.setLocation(leftTop);
            this.rec.width = Math.abs(this.endPt.x - this.startPt.x);
            this.rec.height = Math.abs(this.startPt.y - this.endPt.y);
        }
        return false;
    }
    public void setStartEndPt(ObjectPoint spt, ObjectPoint ept)
    {
        this.startPt = spt;
        this.endPt = ept;
    }



    // check if the pt is on line or not
    public boolean isPtInBoundary(ObjectPoint pt)
    {
        double a, b;
        double ptToLine; // ÂI¨ì½uªº««ª½¶ZÂ÷
        if (rec.contains(pt)) // ¤£¬O««ª½½u©Î¤ô¥­½u
        {
            a = (double) (this.startPt.y - this.endPt.y) / (double) (this.startPt.x - this.endPt.x);
            b = (double) (this.startPt.x * this.endPt.y - this.endPt.x * this.startPt.y) /
                (double) (this.startPt.x - this.endPt.x);

            ptToLine =  (Math.abs(a * pt.x - pt.y + b) /
                              Math.sqrt(Math.pow(a, 2.0) + 1));

            if (ptToLine <= this._transfer.setLengthShowToReal(5))
                return true;
            else
                return false;
        }
        if (this.rec.height <= 0.0001) // ¤ô¥­½u
        {
            if (Math.abs(pt.y - this.startPt.y) <= this._transfer.setLengthShowToReal(5))
            {
                if ( (this.startPt.x < this.endPt.x) && (pt.x > this.startPt.x) && (pt.x < this.endPt.x))
                    return true;
                if ( (this.startPt.x > this.endPt.x) && (pt.x < this.startPt.x) && (pt.x > this.endPt.x))
                    return true;
            }
            else
                return false;
        }
        if (this.rec.width <= 0.0001) // ««ª½½u
        {
            if (Math.abs(pt.x - this.startPt.x) <= this._transfer.setLengthShowToReal(5))
            {
                if ( (this.startPt.y < this.endPt.y) && (pt.y > this.startPt.y) && (pt.y < this.endPt.y))
                    return true;
                if ( (this.startPt.y > this.endPt.y) && (pt.y < this.startPt.y) && (pt.y > this.endPt.y))
                    return true;
            }
            else
                return false;
        }
        return false;
    }

    public void setConstraint()
    {
    }


}
