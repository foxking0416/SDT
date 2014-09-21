package sdt.geometry.element;

import java.awt.*;
import java.util.*;

import sdt.data.*;
import sdt.define.*;
import sdt.geometry.*;
import sdt.panel.drawpanel.*;

/**
 * <p>Title: Element Base</p>
 *
 * <p>Description: 所有繪圖原件的最上層</p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: </p>
 * @author Roger And Fox
 * @version 1.0
 */
public abstract class ElementBase implements DefineSystemConstant
{
    public     DataBase _data;

    public     Color color;
    public     Color color2;

    public     BasicStroke _stroke;
    public     ObjectPoint origin; // the origin point
    public     ObjectRectangle rec;
    public     SDT_DrawTransfer _transfer;

    protected  boolean   _isShowDetail = false;
    protected  int       _elementType = this.TYPE_NULL;


    /**
     * 設定是否顯示Detail of 目前的繪圖元件 Ex.Radial Center of an Arc
     *
     * @param isShowDetail is True then Show Detail
     */
    public void setShowDetail(boolean isShowDetail)
    {
        this._isShowDetail = isShowDetail;
    }

    public ElementBase(Color c, DataBase data)
    {
        this.color = c;
        this.color2 = c;
        this._data = data;
        this.origin = new ObjectPoint();
        this.rec = new ObjectRectangle();
        this.linkedElementArray  = new ArrayList();
    }

    public ElementBase(Color c, Color c2, DataBase data)
    {
        this.color = c;
        this.color2 = c2;
        this._data = data;
        this.origin = new ObjectPoint();
        this.rec = new ObjectRectangle();
        this.linkedElementArray  = new ArrayList();
    }

    protected ArrayList linkedElementArray;

    public ArrayList getLinkedElementArray()
    {
        return this.linkedElementArray;
    }
    public void addLinkedElement(ElementBase linkedElement)
    {
        this.linkedElementArray.add(linkedElement);
        linkedElement.linkedElementArray.add(this);

    }
    public void removeAllLinkedElement()
    {
        this.linkedElementArray.clear();
    }

    public abstract void draw(Graphics g, SDT_DrawTransfer tran);

    public void move(double dx, double dy)
    {}

    public void moveTerminalPoint(double dx, double dy)
    {}

    public abstract boolean setBoundary();

    public void setOrigin()
    {}

    public abstract void setConstraint();

    public void setColor(Color c)
    {
        this.color = c;
    }

    public Color getColor()
    {
        return this.color;
    }

    public abstract boolean isPtInBoundary(ObjectPoint pt);

    public int getElementType()
    {
        return this._elementType;
    }

    public void setElementType(int elementType)
    {
        this._elementType = elementType;
    }
}

