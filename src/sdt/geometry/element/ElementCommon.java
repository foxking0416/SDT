package sdt.geometry.element;

import java.awt.*;

import sdt.data.*;
import sdt.geometry.*;
import sdt.panel.drawpanel.*;
import sdt.define.DefineSystemConstant;

/**
 * <p>Title: </p>
 * <p>Description: 所有可移動的單元元件 皆繼承此元件 <br>Line 、 Point 、Arc 及Rectangle 算是基礎元件, 繼承於ElementBase</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: </p>
 * @author Roger
 * @version 1.0
 */

public abstract class ElementCommon extends ElementBase
{
    //protected DataBase       _data;
    protected int            _viewType;

    public ElementCommon(Color c, DataBase data, int viewType)
    {
        super(c, data);
        //this._data = data;
        this._viewType = viewType;
    }

    public ElementCommon(Color c, Color c2,  DataBase data, int viewType)
    {
        super(c, c2,data);
        //this._data = data;
        this._viewType = viewType;
    }



    public boolean setDataRecover()
    {
        boolean setElementToDataSuccess = true;

        setElementToDataSuccess = !(this._data.setElementToData(this._viewType));

        return setElementToDataSuccess;
    }


    public void moveEnd(int refPointStartOrEnd,ElementPoint destinationPt) // 0 Start 1 End
    {
        ElementPoint startPt = null;
       ElementPoint endPt = null;


       startPt = this._data.getElementPointStart(this._viewType);
       endPt = this._data.getElementPointEnd(this._viewType);

       double dx;
       double dy;
       if(refPointStartOrEnd == 0)
       {
           dx = destinationPt.X() - startPt.X();
           dy = destinationPt.Y() - startPt.Y();
       }
       else //End
       {
           dx = destinationPt.X() - endPt.X();
           dy = destinationPt.Y() - endPt.Y();
       }

       if(Math.abs(dx) < 10E-5 && Math.abs(dy) < 10E-5)     //same point
           return;

       //startPt.move(dx,dy);
       endPt.move(dx,dy);

    }




}
