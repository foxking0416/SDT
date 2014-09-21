package sdt.geometry.element;

import java.awt.*;

import sdt.data.*;
import sdt.geometry.*;
import sdt.panel.drawpanel.*;

public class ElementTop extends ElementCommon
{
    protected double            _radius;
    protected ElementPoint      _pointEnd;
    protected ElementPoint      _pointStart;

    protected ElementPoint[][]  _ptCircumferentialRadial;          //SHELL
    protected ElementPoint[][][]_ptCircumferentialRadialThickness; //SOLID

    protected ElementLine[][]   _lineShellRadial;
    protected ElementLine[][]   _lineShellCircumfirential;
    protected boolean[][]       _highlightLineRadial;

    protected ElementLine[][][]  _lineSolidRadial;
    protected ElementLine[][][]  _lineSolidCircumfirential;
    protected ElementLine[][][]  _lineSolidThickness;



    public ElementTop(DataBase data, int viewType, Color c)
    {
        super(c, data, viewType);
        if(_viewType == this.XYView)
        {
            this._pointEnd                  = data.getElementPointEnd(_viewType);
            this._pointStart                = data.getElementPointStart(_viewType);
            if(data.getDataFamily() == this.TYPE_FAMILY_SHELL)
            {
                this._highlightLineRadial = ((DataShell)data).getHighlightTopLineRadial();
                this._ptCircumferentialRadial   = data.getElementPointtXYCircumferentialRadial();
            }
            else if (data.getDataFamily() == this.TYPE_FAMILY_SOLID)
            {
                //this._highlightLineRadial = ((DataSolid) data).getHighlightTopLineRadial();
                _ptCircumferentialRadialThickness = data.getElementPointtXYCircumferentialRadialThickness();
            }


            this._elementType = this.TYPE_TOP;
        }

    }

    public void draw(Graphics g, SDT_DrawTransfer tran)
    {
        this._transfer = tran;
        this.setBoundary();

        // ObjectPoint objPtCenter = new ObjectPoint(0, 0);
        ObjectPoint                 objRadialEnd;
        ObjectPoint                 objRadialStart;
        ObjectPoint                 objCircumfrential;
        ObjectPoint                 objCircumfrentialNext;
        ObjectPoint                 objThicknessStart;
        ObjectPoint                 objThicknessNext;




        int                         countCircumferential;
        int                         countRadial         ;
        int                         countThickness      ;


        Color                       colorToDraw2;




        //Circumferential line
        if(this._data.getDataFamily() == this.TYPE_FAMILY_SHELL)
        {
            countCircumferential    = _ptCircumferentialRadial.length;
            countRadial             = _ptCircumferentialRadial[0].length;
            _lineShellRadial             = new ElementLine[countCircumferential][countRadial];
            _lineShellCircumfirential    = new ElementLine[countRadial][countCircumferential];


            for (int i = 0; i < countRadial; i++)
            {
                for (int j = 0; j < countCircumferential; j++)
                {
                    if (j != countCircumferential - 1)
                    {
                        objCircumfrential = new ObjectPoint(_ptCircumferentialRadial[j][i].X(), _ptCircumferentialRadial[j][i].Y());
                        objCircumfrentialNext = new ObjectPoint(_ptCircumferentialRadial[j + 1][i].X(), _ptCircumferentialRadial[j + 1][i].Y());
                    }
                    else
                    {
                        objCircumfrential = new ObjectPoint(_ptCircumferentialRadial[j][i].X(), _ptCircumferentialRadial[j][i].Y());
                        objCircumfrentialNext = new ObjectPoint(_ptCircumferentialRadial[0][i].X(), _ptCircumferentialRadial[0][i].Y());
                    }
                    Color colorToDraw = this.color;

                    (new ElementLine(objCircumfrential, objCircumfrentialNext, colorToDraw,_data)).draw(g, tran);
                    // (new ElementPoint(objCircumfrential, Color.RED)).drawBig(g, tran);

                    _lineShellCircumfirential[i][j] = (new ElementLine(objCircumfrential, objCircumfrentialNext, colorToDraw,_data));                    _lineShellCircumfirential[i][j].draw(g, tran);

                }
            }
             //Radial line

            for (int i = 0; i < countCircumferential; i++)
            {
                if (_highlightLineRadial[i][0])
                {
                    colorToDraw2 = Color.WHITE;
                }
                else
                {
                    colorToDraw2 = this.color;
                }
                for (int j = 0; j < countRadial - 1; j++)
                {
                    objRadialStart = new ObjectPoint(_ptCircumferentialRadial[i][j].X(), _ptCircumferentialRadial[i][j].Y());
                    objRadialEnd = new ObjectPoint(_ptCircumferentialRadial[i][j + 1].X(), _ptCircumferentialRadial[i][j + 1].Y());
                    _lineShellRadial[i][j] = (new ElementLine(objRadialStart, objRadialEnd, colorToDraw2,_data));
                    _lineShellRadial[i][j].draw(g, tran);
                }
            }
        }
        else // this._data.getDataFamily() == this.TYPE_FAMILY_SOLID)
        {
            countCircumferential    = _ptCircumferentialRadialThickness.length;
            countRadial             = _ptCircumferentialRadialThickness[0].length;
            countThickness          = _ptCircumferentialRadialThickness[0][0].length;
            _lineSolidRadial             = new ElementLine[countThickness][countCircumferential][countRadial];
            _lineSolidCircumfirential    = new ElementLine[countThickness][countRadial][countCircumferential];
            _lineSolidThickness          = new ElementLine[countCircumferential][countRadial][countThickness];


            for(int t = 0 ; t < countThickness ; t++)
            {
                for (int i = 0; i < countRadial; i++)
                {
                    for (int j = 0; j < countCircumferential; j++)
                    {
                        if (j != countCircumferential - 1)
                        {
                            objCircumfrential = new ObjectPoint(_ptCircumferentialRadialThickness[j][i][t].X(), _ptCircumferentialRadialThickness[j][i][t].Y());
                            objCircumfrentialNext = new ObjectPoint(_ptCircumferentialRadialThickness[j + 1][i][t].X(), _ptCircumferentialRadialThickness[j + 1][i][t].Y());
                        }
                        else
                        {
                            objCircumfrential = new ObjectPoint(_ptCircumferentialRadialThickness[j][i][t].X(), _ptCircumferentialRadialThickness[j][i][t].Y());
                            objCircumfrentialNext = new ObjectPoint(_ptCircumferentialRadialThickness[0][i][t].X(), _ptCircumferentialRadialThickness[0][i][t].Y());
                        }
                        Color colorToDraw = this.color;

                        (new ElementLine(objCircumfrential, objCircumfrentialNext, colorToDraw,_data)).draw(g, tran);

                        _lineSolidCircumfirential[t][i][j] = (new ElementLine(objCircumfrential, objCircumfrentialNext, colorToDraw,_data));
                        _lineSolidCircumfirential[t][i][j].draw(g, tran);

                    }
                }
                //Radial line

                for (int i = 0; i < countCircumferential; i++)
                {
                    for (int j = 0; j < countRadial - 1; j++)
                    {
                        objRadialStart = new ObjectPoint(_ptCircumferentialRadialThickness[i][j][t].X(), _ptCircumferentialRadialThickness[i][j][t].Y());
                        objRadialEnd = new ObjectPoint(_ptCircumferentialRadialThickness[i][j + 1][t].X(), _ptCircumferentialRadialThickness[i][j + 1][t].Y());
                        _lineSolidRadial[t][i][j] = (new ElementLine(objRadialStart, objRadialEnd, this.color,_data));
                        _lineSolidRadial[t][i][j].draw(g, tran);
                    }
                }
            }
            // for thicknessLine
            for (int i = 0; i < countCircumferential; i++)
            {
                for (int j = 0; j < countRadial; j++)
                {
                    for (int t = 0; t < countThickness - 1; t++)
                    {
                        objThicknessStart = new ObjectPoint(_ptCircumferentialRadialThickness[i][j][t].X(), _ptCircumferentialRadialThickness[i][j][t].Y());
                        objThicknessNext = new ObjectPoint(_ptCircumferentialRadialThickness[i][j][t + 1].X(), _ptCircumferentialRadialThickness[i][j][t + 1].Y());
                        _lineSolidThickness[i][j][t] = (new ElementLine(objThicknessStart, objThicknessNext, this.color,_data));
                        _lineSolidThickness[i][j][t].draw(g, tran);
                    }
                }
            }

        }

    }

    public boolean setBoundary()
    {
        this._radius = Math.abs(_pointEnd.X());

        this.rec.setLocation( -_radius, _radius);
        this.rec.width = _radius * 2;
        this.rec.height = _radius * 2;
        return false;
    }


    public void setConstraint()
    {
    }

    public boolean isPtInBoundary(ObjectPoint pt)
    {
        int countCircumferential = -1;
        int countRadial          = -1;
        int countThickness       = -1;

        if(this._data.getDataFamily() == this.TYPE_FAMILY_SHELL)
        {
            if (this._lineShellRadial == null || this._lineShellRadial[0] == null || this._lineShellRadial[0][0] == null)
            {
                //System.out.println("return false-->this._lineRadial == null");
                return false;
            }
            countCircumferential = _ptCircumferentialRadial.length;
            countRadial = _ptCircumferentialRadial[0].length;
            for (int i = 0; i < countCircumferential; i++) //Radial line
            {
                for (int j = 0; j < countRadial - 1; j++)
                {

                    if (this._lineShellRadial[i][j].isPtInBoundary(pt) == true)
                    {
                        //System.out.println("return true");
                        return true;
                    }
                }
            }
            for (int i = 0; i < countRadial; i++) //Circumferential line
            {
                for (int j = 0; j < countCircumferential; j++)
                {
                    if (this._lineShellCircumfirential[i][j].isPtInBoundary(pt) == true)
                    {
                        //System.out.println("return true");
                        return true;
                    }
                }
            }
        }
        else //if(this._data.getDataFamily() == this.TYPE_FAMILY_SHELL)
        {
            if (this._lineSolidRadial == null || this._lineSolidRadial[0] == null || this._lineSolidRadial[0][0] == null|| this._lineSolidRadial[0][0][0] == null)
            {
                //System.out.println("return false-->this._lineRadial == null");
                return false;
            }
            countCircumferential= _ptCircumferentialRadialThickness.length;
            countRadial         = _ptCircumferentialRadialThickness[0].length;
            countThickness      = _ptCircumferentialRadialThickness[0][0].length;


            for (int t = 0; t < countThickness; t++)
            {
                for (int i = 0; i < countCircumferential; i++) //Radial line
                {
                    for (int j = 0; j < countRadial - 1; j++)
                    {

                        if (this._lineSolidRadial[t][i][j].isPtInBoundary(pt) == true)
                        {
                            //System.out.println("return true");
                            return true;
                        }
                    }
                }
                for (int i = 0; i < countRadial; i++) //Circumferential line
                {
                    for (int j = 0; j < countCircumferential; j++)
                    {
                        if (this._lineSolidCircumfirential[t][i][j].isPtInBoundary(pt) == true)
                        {
                            //System.out.println("return true");
                            return true;
                        }
                    }
                }
            }
            for (int i = 0; i < countCircumferential; i++) //Radial line
            {
                for (int j = 0; j < countRadial - 1; j++)
                {
                    for (int t = 0; t < countThickness - 1; t++)
                    {
                        if (this._lineSolidThickness[i][j][t].isPtInBoundary(pt) == true)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        //System.out.println("return false");
        return false;
    }
}
