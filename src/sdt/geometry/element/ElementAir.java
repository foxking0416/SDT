package sdt.geometry.element;

import java.awt.Graphics;
import sdt.panel.drawpanel.SDT_DrawTransfer;
import sdt.geometry.ObjectPoint;
import sdt.data.DataCap;
import java.awt.Color;
import sdt.data.DataAir;
import java.util.ArrayList;
import sdt.stepb.stepb_cartesian_point;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ElementAir extends ElementCommon
{

    DataAir _dataAir;
    ArrayList _arrContourPt;
    ArrayList _arrContourLine;
    ArrayList _arrGridLine;
    ArrayList _arrGridPt;

    public ElementAir(DataAir data, int viewType, Color c)
    {
        super(c, data, viewType);
        this._dataAir = data;



        this._elementType = this.TYPE_AIR;
        //this._capType = data.getGeometryType();

    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this.setBoundary();

        this._transfer = tran;

        //this.drawLine(g);
        //this.drawContourPoint(g);
        //this.drawGridLine(g);
        //this.drawGridPoint(g);
    }

    public void drawContourLine(Graphics g)
    {
        for(int i = 0 ; i < this._arrContourLine.size();i++)
        {
            ElementLine tempLine = (ElementLine)this._arrContourLine.get(i);
            tempLine.draw(g, this._transfer);
        }
    }

    public void drawContourPoint(Graphics g)
    {
        for(int i = 0 ; i < this._arrContourPt.size();i++)
        {
            ElementPoint tempPt = (ElementPoint)this._arrContourPt.get(i);
            tempPt.draw(g, this._transfer);
        }
    }

    public void drawGridLine(Graphics g)
    {
        for(int i = 0 ; i < this._arrGridLine.size() ; i++)
        {
            ElementLine tempLine = (ElementLine)this._arrGridLine.get(i);
            tempLine.draw(g, this._transfer);
        }
    }

    public void drawGridPoint(Graphics g)
    {
        for(int i = 0 ; i < this._arrGridPt.size() ; i++)
        {
            ElementPoint tempPt = (ElementPoint)this._arrGridPt.get(i);
            tempPt.draw(g, this._transfer);
        }

    }

    public boolean setBoundary()
    {
        this._arrContourPt = new ArrayList();
        this._arrContourLine = new ArrayList();
        this._arrGridLine = new ArrayList();
        this._arrGridPt = new ArrayList();
        /*
        ArrayList contourPt = this._dataAir.getElementAirContourPtXZ();

        for(int i = 0 ; i < contourPt.size() - 1  ; i++)
        {
            stepb_cartesian_point sPt = (stepb_cartesian_point)contourPt.get(i);
            stepb_cartesian_point ePt = (stepb_cartesian_point)contourPt.get(i+1);

            ObjectPoint objSpt = new ObjectPoint(sPt.X(), sPt.Z());
            ObjectPoint objEpt = new ObjectPoint(ePt.X(), ePt.Z());

            ElementPoint elemSPt = new ElementPoint(objSpt, this.color, this._dataAir);
            ElementPoint elemEPt = new ElementPoint(objEpt, this.color, this._dataAir);
            ElementLine  elemLine = new ElementLine(objSpt, objEpt, this.color, this._dataAir);

            if(i == 0)
                this._arrContourPt.add(elemSPt);

            this._arrContourPt.add(elemEPt);
            this._arrContourLine.add(elemLine);
        }

       this._arrGridLine = this._dataAir.getElementAirGrid();*/
       this._arrGridPt = this._dataAir.getElementAirGrid2();


        return false;
    }

    public void setConstraint()
    {
    }

    public boolean isPtInBoundary(ObjectPoint pt)
    {
        return false;
    }
}
