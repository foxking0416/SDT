package sdt.panel.drawpanel;

import java.awt.*;
import sdt.geometry.ObjectPoint;
import sdt.geometry.MathVector2D;

public class SDT_DrawTransfer
{
    private Point        _theShowOrigin;
    private ObjectPoint  _theRealOrigin;
    private double _theWidthScale = 100;
    private double _theHeightScale = 100;
    private double _initialWidthScale;
    private double _initialHeightScale;
    private int    _widthScaleTime;
    private int    _heightScaleTime;

    private int    _thePanelHeight;
    private int    _thePanelWidth;

    private int     _scaleMax  = 2000;
    private int     _scaleMin  = 25;
    private boolean _isLog;
    private boolean _isScaleLimit;
    private double[] zoom = new double[]{1, 1.25, 2, 5, 8};

    //public int     GetScale()         {return this._theScale;}
    public double GetWidthScale()    {return this._theWidthScale;}
    public double GetHeightScale()   {return this._theHeightScale;}
    public int    GetPanelHeight()   {return this._thePanelHeight;}
    public int    GetPanelWidth()    {return this._thePanelWidth;}
    public Point  GetShowOrign()     {return this._theShowOrigin;}
    public ObjectPoint   GetRealOrigin()    {return this._theRealOrigin;}


    public void     setPanelHeight(int height)       {this._thePanelHeight = height;}
    public void     setPanelWidth(int width)         {this._thePanelWidth = width;}
    public void     setIfLog(boolean isLog)          {this._isLog = isLog;}
    public void     setIfLimitScale(boolean isLimit) {this._isScaleLimit = isLimit;}

    public SDT_DrawTransfer(int panelH, int panelW, ObjectPoint origin, double wScale, double hScale)
    {
        this._heightScaleTime = 0;
        this._widthScaleTime = 0;

        this._thePanelHeight = panelH;
        this._thePanelWidth  = panelW;
        this._theRealOrigin  = origin;
        this._theWidthScale  = wScale;
        this._theHeightScale = hScale;
        this._initialWidthScale = wScale;
        this._initialHeightScale = hScale;
        this._isLog = false;
        this._isScaleLimit = true;
        this.setShowOrigin();
    }



    private void setShowOrigin()
    {
        int showShiftX = (int)(this._theRealOrigin.x * _theWidthScale);
        int showShiftY = (int)(this._theRealOrigin.y * _theHeightScale);



        this._theShowOrigin= new Point(showShiftX, _thePanelHeight - showShiftY);
    }

    public void setRealOrigin(double realX, double realY)
    {
        this._theRealOrigin.x = realX;
        this._theRealOrigin.y = realY;
        this.setShowOrigin();
    }


    public double[] setPointShowToReal(int x ,int y)
    {
        double realPt[] = new double[2];
        //if (this._isLog == false)
        //{
            realPt[0] = (double) (x - _theShowOrigin.x)/ (double)_theWidthScale;
            realPt[1] = (double) (_theShowOrigin.y - y)/ (double)_theHeightScale;
       /* }
        else
        {
            try
            {
                realPt[0] = (double) Math.pow(10, (x - _theShowOrigin.x)/ (double)_theWidthScale / 1.25 );
                realPt[1] = (double) (_theShowOrigin.y - y)/ (double)_theHeightScale;
            }
            catch (Exception ex)
            {
                realPt[0] = 0;
                realPt[1] = 0;
            }

        }*/

        return realPt;
    }

    public int[] setPointRealToShow(double x,double y)
    {
        int showPt[] = new int[2];

        if (this._isLog == false)
        {
            showPt[0] = (int) (x * _theWidthScale) + this._theShowOrigin.x;
            showPt[1] = (int) (-y * _theHeightScale) + this._theShowOrigin.y;
        }
        else
        {
            try
            {
                showPt[0] = (int) (Math.log10(x) * _theWidthScale * 1.25) + this._theShowOrigin.x;
                showPt[1] = (int) ( -y * _theHeightScale) + this._theShowOrigin.y;
            }
            catch(Exception ex)
            {
                showPt[0] = 0;
                showPt[1] = 0;
            }


        }
        return showPt;
    }

    public int[] setLengthRealToShow(double x, double y)
    {
        int showLength[] = new int[2];
        showLength[0] = (int) (x * _theWidthScale);
        showLength[1] = (int) (y * _theHeightScale);

        return showLength;
    }

    public int setLengthRealToShow(double x)
    {
        int showLength = (int) (x * _theWidthScale);

        return showLength;
    }

    public double[] setLengthShowToReal(int x, int y)
    {
        double[] realLength = new double[2];
        realLength[0] = x / (double) _theWidthScale;
        realLength[1] = y / (double) _theHeightScale;
        return realLength;
    }

    public double setLengthShowToReal(int l)
    {
        double realLength = l / (double) _theWidthScale;
        return realLength;
    }

    public void translateOriginShow(int showX, int showY)
    {
        double x = (double) showX / this._theWidthScale;
        double y = (double) - showY / this._theHeightScale;

        this._theRealOrigin.x += x;
        this._theRealOrigin.y += y;
        this.setShowOrigin();
    }

    public void translateOriginReal(double x, double y)
    {
        this._theRealOrigin.x += x;
        this._theRealOrigin.y += y;
        this.setShowOrigin();
    }


    public boolean adjustScale(int scaleIncrement)
    {
        this._widthScaleTime += scaleIncrement;
        this._heightScaleTime += scaleIncrement;

        int widthTimes;
        if (this._widthScaleTime < 0)
            widthTimes = (this._widthScaleTime + 1) / zoom.length - 1;
        else
            widthTimes = this._widthScaleTime / zoom.length;

        int widthResidual = this._widthScaleTime % zoom.length;
        if (this._widthScaleTime < 0 && widthResidual != 0)
            widthResidual += zoom.length;

        int heightTimes;
        if (this._heightScaleTime < 0)
            heightTimes = (this._heightScaleTime + 1) / zoom.length - 1;
        else
            heightTimes = this._heightScaleTime / zoom.length;

        int heightResidual = this._heightScaleTime % zoom.length;
        if (this._heightScaleTime < 0 && heightResidual != 0)
            heightResidual += zoom.length;

        double wScale = this._initialWidthScale * Math.pow(10, widthTimes) * zoom[widthResidual];
        double hScale = this._initialHeightScale * Math.pow(10, heightTimes) * zoom[heightResidual];



        //double wScale = this._initialWidthScale * Math.pow(10, (double)this._widthScaleTime / 10);
        //double hScale = this._initialHeightScale * Math.pow(10, (double)this._heightScaleTime / 10);

        if(this._isScaleLimit == true)
        {
            if (wScale > this._scaleMax || wScale < this._scaleMin ||
                hScale > this._scaleMax || hScale < this._scaleMin)
            {
                this._widthScaleTime -= scaleIncrement;
                this._heightScaleTime -= scaleIncrement;
                return false;
            }

        }
        this._theWidthScale = wScale;
        this._theHeightScale = hScale;
        //this.SetShowOrigin();
        this._theRealOrigin.x = (double)this._theShowOrigin.x / (double)this._theWidthScale;
        this._theRealOrigin.y = (double) (_thePanelHeight - this._theShowOrigin.y) / (double)this._theHeightScale;

        return true;
    }

    public boolean adjustScale(int scaleIncrement, int centerX, int centerY)
    {
        this._widthScaleTime += scaleIncrement;
        this._heightScaleTime += scaleIncrement;



        int widthTimes;
        if(this._widthScaleTime < 0)
            widthTimes = (this._widthScaleTime + 1) / zoom.length -1;
        else
            widthTimes = this._widthScaleTime / zoom.length;

        int widthResidual = this._widthScaleTime % zoom.length;
        if(this._widthScaleTime < 0 && widthResidual != 0)
            widthResidual += zoom.length;

        int heightTimes;
        if(this._heightScaleTime < 0)
            heightTimes = (this._heightScaleTime + 1) / zoom.length -1;
        else
            heightTimes = this._heightScaleTime / zoom.length;

        int heightResidual = this._heightScaleTime % zoom.length;
        if(this._heightScaleTime < 0 && heightResidual != 0)
            heightResidual += zoom.length;


        double wScale = this._initialWidthScale * Math.pow(10, widthTimes) * zoom[widthResidual];
        double hScale = this._initialHeightScale * Math.pow(10, heightTimes) * zoom[heightResidual];

        //double wScale = this._initialWidthScale * Math.pow(10, (double)this._widthScaleTime / 10);
        //double hScale = this._initialHeightScale * Math.pow(10, (double)this._heightScaleTime / 10);

        if(this._isScaleLimit == true)
        {
            if (wScale > this._scaleMax || wScale < this._scaleMin ||
                hScale > this._scaleMax || hScale < this._scaleMin)
            {
                this._widthScaleTime -= scaleIncrement;
                this._heightScaleTime -= scaleIncrement;
                return false;
            }
        }
        double[] realScalePt = this.setPointShowToReal(centerX, centerY);
        this._theWidthScale = wScale;
        this._theHeightScale = hScale;

        this._theRealOrigin.x = (double)this._theShowOrigin.x / (double)this._theWidthScale;
        this._theRealOrigin.y = (double) (_thePanelHeight - this._theShowOrigin.y) / (double)this._theHeightScale;

        double[] realScalePt2 = this.setPointShowToReal(centerX, centerY);

        this.translateOriginReal( -realScalePt[0] + realScalePt2[0], -realScalePt[1] + realScalePt2[1]);

        return true;
    }

    public boolean adjustWidthScale(int scaleIncrement, int centerX, int centerY)
    {
        this._widthScaleTime += scaleIncrement;

        int widthTimes;
        if (this._widthScaleTime < 0)
            widthTimes = (this._widthScaleTime + 1) / zoom.length - 1;
        else
            widthTimes = this._widthScaleTime / zoom.length;

        int widthResidual = this._widthScaleTime % zoom.length;
        if (this._widthScaleTime < 0 && widthResidual != 0)
            widthResidual += zoom.length;


        double scale = this._initialWidthScale * Math.pow(10, widthTimes) * zoom[widthResidual];



        //double scale = this._initialWidthScale * Math.pow(10, (double)this._widthScaleTime / 10);

        if(this._isScaleLimit == true)
        {
            if (scale > this._scaleMax || scale < this._scaleMin)
            {
                this._widthScaleTime -= scaleIncrement;
                return false;
            }
        }

        double[] realScalePt = this.setPointShowToReal(centerX, centerY);
        this._theWidthScale = scale;

        this._theRealOrigin.x = (double)this._theShowOrigin.x / (double)this._theWidthScale;
        //this._theRealOrigin.y  would not change

        double[] realScalePt2 = this.setPointShowToReal(centerX, centerY);

        this.translateOriginReal( -realScalePt[0] + realScalePt2[0], -realScalePt[1] + realScalePt2[1]);

        return true;
    }

    public boolean adjustHeightScale(int scaleIncrement, int centerX, int centerY)
    {
        this._heightScaleTime += scaleIncrement;

        int heightTimes;
        if (this._heightScaleTime < 0)
            heightTimes = (this._heightScaleTime + 1) / zoom.length - 1;
        else
            heightTimes = this._heightScaleTime / zoom.length;

        int heightResidual = this._heightScaleTime % zoom.length;
        if (this._heightScaleTime < 0 && heightResidual != 0)
            heightResidual += zoom.length;


        double scale = this._initialHeightScale * Math.pow(10, heightTimes) * zoom[heightResidual];


        //double scale = this._initialHeightScale * Math.pow(10, (double)this._heightScaleTime / 10);

        if(this._isScaleLimit == true)
        {
            if (scale > this._scaleMax || scale < this._scaleMin)
            {
                this._heightScaleTime -= scaleIncrement;
                return false;
            }
        }

        double[] realScalePt = this.setPointShowToReal(centerX, centerY);
        this._theHeightScale = scale;

        //this._theRealOrigin.x would not change
        this._theRealOrigin.y = (double) (_thePanelHeight - this._theShowOrigin.y) / (double)this._theHeightScale;

        double[] realScalePt2 = this.setPointShowToReal(centerX, centerY);

        this.translateOriginReal( -realScalePt[0] + realScalePt2[0], -realScalePt[1] + realScalePt2[1]);

        return true;
    }

    public int getScaleTime(double setScale, double initialScale)
    {
        int scaleTime = 0;

        double scale = initialScale * Math.pow(10, 0) * zoom[0];
        if(setScale >= scale)
        {
            while(scale <= setScale)
            {
                scaleTime++;
                int quotient = scaleTime / zoom.length;
                int residual = scaleTime % zoom.length;

                scale = initialScale * Math.pow(10, quotient) * zoom[residual];
            }

            scaleTime--;
/*
            double subtractScale = Math.abs(scale - setScale);
            double subtractScale2 = Math.abs(scale - setScale);

            do
            {
                subtractScale = subtractScale2;
                scaleTime++;

                int quotient2 = scaleTime / zoom.length;
                int residual2 = scaleTime % zoom.length;

                double scale2 = this._initialHeightScale * Math.pow(10, quotient2) * zoom[residual2];

                subtractScale2 = Math.abs(scale2 - setScale);

            }
            while (subtractScale2 < subtractScale);
            scaleTime--;*/
        }
        else
        {
            while(scale > setScale)
            {
                scaleTime--;
                int quotient = (scaleTime + 1) / zoom.length - 1;
                int residual = scaleTime % zoom.length;
                if (residual != 0)
                    residual += zoom.length;

                scale = initialScale * Math.pow(10, quotient) * zoom[residual];

            }

            /*
            int quotient = (scaleTime + 1) / zoom.length - 1;
            int residual = scaleTime % zoom.length;
            if(residual != 0)
                residual += zoom.length;

            double scale = this._initialHeightScale * Math.pow(10, quotient) * zoom[residual];
            double subtractScale = Math.abs(scale - setScale);
            double subtractScale2 = Math.abs(scale - setScale);
            do
            {
                subtractScale = subtractScale2;
                scaleTime--;
                int quotient2 = scaleTime / zoom.length;
                int residual2 = scaleTime % zoom.length;
                if(residual2 != 0)
                    residual2 += zoom.length;
                double scale2 = this._initialHeightScale * Math.pow(10, quotient2) * zoom[residual2];

                subtractScale2 = Math.abs(scale2 - setScale);
            }
            while(subtractScale2 < subtractScale);
*/
        }

        return scaleTime;
    }

    public boolean setScale(double widthScale, double heightScale, int mousePtX, int mousePtY)
    {
        if(widthScale <= 0 || heightScale <= 0)
            return false;

        int widthScaleTime = this.getScaleTime(widthScale, this._initialWidthScale);
        int heightScaleTime = this.getScaleTime(heightScale, this._initialHeightScale);



        int widthQuotient;
        if (widthScaleTime < 0)
            widthQuotient = (widthScaleTime + 1) / zoom.length - 1;
        else
            widthQuotient = widthScaleTime / zoom.length;

        int widthResidual = widthScaleTime % zoom.length;
        if (widthScaleTime < 0 && widthResidual != 0)
            widthResidual += zoom.length;



        int heightQuotient;
        if (heightScaleTime < 0)
            heightQuotient = (heightScaleTime + 1) / zoom.length - 1;
        else
            heightQuotient = heightScaleTime / zoom.length;

        int heightResidual = heightScaleTime % zoom.length;
        if (heightScaleTime < 0 && heightResidual != 0)
            heightResidual += zoom.length;


        double wScale = this._initialWidthScale * Math.pow(10, widthQuotient) * zoom[widthResidual];
        double hScale = this._initialHeightScale * Math.pow(10, heightQuotient) * zoom[heightResidual];


        if(this._isScaleLimit == true)
        {
            if (wScale > this._scaleMax || wScale < this._scaleMin ||
                hScale > this._scaleMax || hScale < this._scaleMin)
            {
                return false;
            }
        }

        this._widthScaleTime = widthScaleTime;
        this._heightScaleTime = heightScaleTime;
        this._theWidthScale = wScale;
        this._theHeightScale =hScale;


        /*
        int times = 0;
        if (widthScale >= 1)
        {
            while(widthScale >= 10)
            {
                times++;
                widthScale = widthScale / 10.0;
            }
        }
        else
        {
            times--;
            while (widthScale < 1)
            {
                times--;
                widthScale = widthScale * 10.0;
            }
        }*/



        //this._widthScaleTime =(int)( 10 * Math.log10(widthScale/ this._initialWidthScale));
        //this._heightScaleTime =(int)( 10 * Math.log10(heightScale/ this._initialHeightScale));
        //this._theWidthScale = this._initialWidthScale * Math.pow(10, (double)this._widthScaleTime / 10);
        //this._theHeightScale = this._initialHeightScale * Math.pow(10, (double)this._heightScaleTime / 10);
/*
        while(this._theWidthScale > widthScale)
        {
            this._theWidthScale--;
        }
        while(this._theHeightScale > heightScale)
        {
            this._theHeightScale--;
        }*/


        double realOriX = this._theShowOrigin.x / this._theWidthScale;
        double realOriY = (double)(this._thePanelHeight - this._theShowOrigin.y) / (double)(this._theHeightScale);

        this._theRealOrigin.setLocation(realOriX, realOriY);

        return true;

    }

    public boolean setScaleFit(double max[], double min[], float ratio, boolean isShow)
    {

        if (max[0] == min[0] && max[1] == min[1])
            return false;
        double halfX = (max[0] - min[0]) / 2.0;
        double halfY = (max[1] - min[1]) / 2.0;

        double rateX = (this._thePanelWidth / (2.0 * this._theWidthScale)) / halfX;
        double rateY = (this._thePanelHeight / (2.0 * this._theHeightScale)) / halfY;

        this._theHeightScale = (int) (rateY * this._theHeightScale * ratio);
        this._theWidthScale = (int) (Math.log(rateX) * this._theWidthScale * ratio);
        // System.out.println("rateX:( " + rateX + " )");
        // System.out.println("rateY:( " + rateY + " )");

        this._theRealOrigin.x = (double)this._theShowOrigin.x / (double)this._theWidthScale;
        this._theRealOrigin.y = (double) (this._thePanelHeight - this._theShowOrigin.y) / (double)this._theHeightScale;

        ObjectPoint centerObj = new ObjectPoint((max[0] + min[0]) / 2.0, (max[1] + min[1]) / 2.0);

        int objCenter[] = setPointRealToShow(centerObj.x, centerObj.y);

        int toCenterX = this._thePanelWidth / 2 - objCenter[0];
        int toCenterY = this._thePanelHeight / 2 - objCenter[1];
        this.translateOriginShow(toCenterX, toCenterY);

        return true;
    }

    public boolean setScaleFitFixedRatio(double max[], double min[], float ratio, boolean isShow)
    {

        if (max[0] == min[0] && max[1] == min[1])
            return false;
        double halfX = (max[0] - min[0]) / 2.0;
        double halfY = (max[1] - min[1]) / 2.0;

        double rateX = (this._thePanelWidth / (2.0 * this._theWidthScale)) / halfX;
        double rateY = (this._thePanelHeight / (2.0 * this._theHeightScale)) / halfY;

        // System.out.println("rateX:( " + rateX + " )");
        // System.out.println("rateY:( " + rateY + " )");

        this._theWidthScale = (int) (rateX * this._theWidthScale * ratio);//wait for check
        this._theHeightScale = (int) (rateY * this._theHeightScale * ratio);//wait for check

        if(this._theWidthScale > this._theHeightScale)
        {
            this._theWidthScale = this._theHeightScale;
        }
        else
        {
            this._theHeightScale = this._theWidthScale;
        }

        int widthScaleTime = this.getScaleTime(this._theWidthScale, this._initialWidthScale);
        int heightScaleTime = this.getScaleTime(this._theHeightScale, this._initialHeightScale);

        int widthQuotient;
        if (widthScaleTime < 0)
            widthQuotient = (widthScaleTime + 1) / zoom.length - 1;
        else
            widthQuotient = widthScaleTime / zoom.length;

        int widthResidual = widthScaleTime % zoom.length;
        if (widthScaleTime < 0 && widthResidual != 0)
            widthResidual += zoom.length;

        int heightQuotient;
        if (heightScaleTime < 0)
            heightQuotient = (heightScaleTime + 1) / zoom.length - 1;
        else
            heightQuotient = heightScaleTime / zoom.length;

        int heightResidual = heightScaleTime % zoom.length;
        if (heightScaleTime < 0 && heightResidual != 0)
            heightResidual += zoom.length;

        double wScale = this._initialWidthScale * Math.pow(10, widthQuotient) * zoom[widthResidual];
        double hScale = this._initialHeightScale * Math.pow(10, heightQuotient) * zoom[heightResidual];

        this._widthScaleTime = widthScaleTime;
        this._heightScaleTime = heightScaleTime;
        this._theWidthScale = wScale;
        this._theHeightScale = hScale;





        //this._widthScaleTime =(int)( 10 * Math.log10(this._theWidthScale/ this._initialWidthScale));
        //this._heightScaleTime =(int)( 10 * Math.log10(this._theHeightScale/ this._initialHeightScale));
        //this._theWidthScale = this._initialWidthScale * Math.pow(10, (double)this._widthScaleTime / 10);
        //this._theHeightScale = this._initialHeightScale * Math.pow(10, (double)this._heightScaleTime / 10);


        this._theRealOrigin.x = (double)this._theShowOrigin.x / (double)this._theWidthScale;
        this._theRealOrigin.y = (double) (this._thePanelHeight - this._theShowOrigin.y) / (double)this._theWidthScale;

        ObjectPoint centerObj = new ObjectPoint((max[0] + min[0]) / 2.0, (max[1] + min[1]) / 2.0);

        int objCenter[] = setPointRealToShow(centerObj.x, centerObj.y);

        int toCenterX = this._thePanelWidth / 2 - objCenter[0];
        int toCenterY = this._thePanelHeight / 2 - objCenter[1];
        this.translateOriginShow(toCenterX, toCenterY);




        return true;
    }



}
