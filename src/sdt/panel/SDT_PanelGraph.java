package sdt.panel;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.*;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.Action;
import java.awt.MouseInfo;

import javax.swing.*;

import sdt.define.*;
import sdt.geometry.*;
import sdt.panel.drawpanel.*;

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
public class SDT_PanelGraph extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener, ComponentListener, KeyListener, DefineSystemConstant
{

    private JPopupMenu _popupMenu;
    private Action actionExportData;
    private Action actionImportData;

    private Color pointColor; //點的顏色
    private Color lineColor; //連線的顏色
    private Color bgGridColor; //格線的顏色
    private Color bgColor; //背景的顏色
    private Color oldPointColor;
    private Color oldLineColor;

    private Image imgBuffer;
    private Graphics gBuffer;

    private ObjectPoint ptMouseCurrent_Real;
    private ObjectPoint ptMousePressed_Real;
    private Point ptMousePressed_Show;

    private Point ptMouseCurrent_Show;

    private ArrayList arrayPt;
    private ArrayList arrayClibrationLabel;
    private ArrayList arrayLineIndex;
    private ArrayList arrayLines;
    private ArrayList arrayLineColor;
    private ArrayList arrayLineWidth;
    private ArrayList arrayPointColor;
    private ArrayList arrayPointType;
    private ArrayList arrayPointSize;
    private ArrayList arrayIsDrawData;
    private ArrayList arrayIsDrawLine;
    private ArrayList arrayIsDrawPt;

    private SDT_DrawTransfer transfer;

    private int _mouseDragedState = this.MOUSE_NONE;
    private int _keyDownState = this.KEY_NONE;

    private Cursor cursorMove = new Cursor(Cursor.MOVE_CURSOR);
    private Cursor cursorHand = new Cursor(Cursor.HAND_CURSOR);
    private Cursor cursorScale = new Cursor(Cursor.S_RESIZE_CURSOR);
    private Cursor cursorArrow = new Cursor(Cursor.DEFAULT_CURSOR);

    private boolean isShowGrid;
    private boolean isScaleLog;
    private boolean isDrawData;
    private boolean isDrawLine;
    private boolean isDrawPt;
    private boolean isShowMouseLocation;

    private int pointType;
    private int pointSize;
    private int boundaryWidth;
    private int gridWidth;
    private float lineWidth;
    private int panelWidth;
    private int panelHeight;
    private int panelStartX;
    private int panelStartY;
    private int selectIndex = -1;

    private String unitX;
    private String unitY;


    private double initialWidthScale;
    private double initialHeightScale;

    private double rightMostValue;
    private double leftMostValue;
    private double upMostValue;
    private double downMostValue;





    public SDT_PanelGraph(int startX, int startY, int width, int height)
    {
        this.panelStartX = startX;
        this.panelStartY = startY;
        this.panelWidth  = width;
        this.panelHeight = height;

        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        this.isShowGrid = true;
        this.isScaleLog = true;
        this.isDrawData = true;
        this.isDrawLine = true;
        this.isDrawPt = true;
        this.isShowMouseLocation = true;
        this.bgColor = Color.lightGray; //black
        this.bgGridColor = Color.black; //green
        this.pointColor = Color.gray; //white
        this.pointType = 0;
        this.pointSize = 4;
        this.lineColor = Color.red;
        this.lineWidth = 2;


        this.boundaryWidth = 50;
        //this.gridWidth = 40;
        this.rightMostValue = 0;
        this.leftMostValue = 0;
        this.upMostValue = 0;
        this.downMostValue = 0;
        this.unitX = "Hz";
        this.unitY = "";
        this.ptMouseCurrent_Show = new Point(0, 0);

        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addComponentListener(this);

        this.setBounds(this.panelStartX, this.panelStartY, this.panelWidth, this.panelHeight);
        this.calculateGridWidth();
        this.setTransfer();

        //設定Action
        this.actionImportData = new importKlippelDataAction(this);
        this.actionExportData = new exportKlippelDataAction(this);

        JMenuItem menuImportDataFromKlippel = new JMenuItem("Paste Data From Klippel");
        menuImportDataFromKlippel.addActionListener(this.actionImportData);

        JMenuItem menuExportDataToKlippel = new JMenuItem("Export Data To Klippel");
        menuExportDataToKlippel.addActionListener(actionExportData);

        this._popupMenu = new JPopupMenu();
        this._popupMenu.add(menuImportDataFromKlippel);
        this._popupMenu.add(menuExportDataToKlippel);


    }



    private void calculateGridWidth()
    {
        this.gridWidth = (this.getSize().width - 2 * this.boundaryWidth ) / 12; //x方向切12格
        this.initialWidthScale = (double)this.gridWidth / 50 * 100;
        this.initialHeightScale = (double)this.gridWidth / 50 * 100;
    }

    private void setTransfer()
    {
        if (this.transfer == null)
        {
            ObjectPoint origin = new ObjectPoint((double) (this.getSize().getWidth()) / this.initialWidthScale / 2, (double) (this.panelHeight) / this.initialHeightScale / 2);
            this.transfer = new SDT_DrawTransfer(this.panelHeight, this.panelWidth, origin, this.initialWidthScale, this.initialHeightScale);
            this.transfer.setIfLimitScale(false);//把放大縮小的極限限制關掉
        }
    }

    public void setShowGrid(boolean show){this.isShowGrid = show;}
    public void setShowLine(boolean show){this.isDrawLine = show;}
    public void setShowPt  (boolean show){this.isDrawPt = show;}
    public void setShowMouseValue(boolean show){this.isShowMouseLocation = show;}
    public void setShowLinearScale(){this.switchLinearOrLog(false);}
    public void setShowLogScale   (){this.switchLinearOrLog(true);}
    public void setGridColor (Color c) {this.bgGridColor = c;}
    public void setBGColor   (Color c) {this.bgColor = c;}
    public void setXUnit     (String x) {this.unitX = x;}
    public void setYUnit     (String y) {this.unitY = y;}

    private Color getLineColor (int index)
    {
        if(index < 1)
            return Color.white;
        Color c = (Color)this.arrayLineColor.get(index - 1);
        return c;
    }
    public void setLineColor (Color c, int index)
    {
        if(index < 1 || index > this.arrayLineColor.size())
            return;
        this.arrayLineColor.set(index - 1, c);
    }

    public void setLineWidth (float w, int index)
    {
        if(index < 1 || index > this.arrayLineWidth.size())
            return;
        this.arrayLineWidth.set(index - 1, w);
    }

    private Color getPointColor(int index)
    {
        if (index < 1)
            return Color.white;
        Color c = (Color)this.arrayPointColor.get(index - 1);
        return c;
    }

    public void setPointColor(Color c, int index)
    {
        if(index < 1 || index > this.arrayPointColor.size())
            return;
        this.arrayPointColor.set(index - 1, c);
    }

    public void setPointType (int type, int index)
    {
        if(index < 1 || index > this.arrayPointType.size())
                   return;
        this.arrayPointType.set(index - 1, type);
    }

    public void setPointSize (int size, int index)
    {
        if(index < 1 || index > this.arrayPointSize.size())
                   return;
        this.arrayPointSize.set(index - 1, size);
    }





    /**
     * 輸入資料點
     * @param x double X座標
     * @param y double Y座標
     * @param lineNum int 屬於第N條線的資料點
     */
    public void inputValue(double x, double y, int lineNum)
    {
        ObjectPoint objPt = new ObjectPoint(x, y);
        if(this.arrayLineIndex == null)
            this.arrayLineIndex = new ArrayList();

        if(this.arrayLines == null)
            this.arrayLines = new ArrayList();

        if(this.arrayLineColor == null)
            this.arrayLineColor = new ArrayList();

        if(this.arrayLineWidth == null)
            this.arrayLineWidth = new ArrayList();

        if(this.arrayPointColor == null)
            this.arrayPointColor = new ArrayList();

        if(this.arrayPointSize == null)
            this.arrayPointSize = new ArrayList();

        if(this.arrayPointType == null)
            this.arrayPointType = new ArrayList();

        if(this.arrayIsDrawData == null)
            this.arrayIsDrawData = new ArrayList();

        if(this.arrayIsDrawLine == null)
            this.arrayIsDrawLine = new ArrayList();

        if(this.arrayIsDrawPt == null)
            this.arrayIsDrawPt = new ArrayList();

        int index = -1;
        for(int i = 0; i < this.arrayLineIndex.size(); i++)
        {
            int lineNumInArray = (Integer)this.arrayLineIndex.get(i);
            if(lineNum == lineNumInArray)
            {
                index = i;
                break;
            }
        }

        if(index == -1)//表示這條線是新增加的
        {
            ArrayList newLine = new ArrayList();
            newLine.add(objPt);
            this.arrayLines.add(newLine);
            this.arrayLineIndex.add(lineNum);
            this.arrayLineColor.add(this.lineColor);
            this.arrayLineWidth.add(this.lineWidth);
            this.arrayPointColor.add(this.pointColor);
            this.arrayPointSize.add(this.pointSize);
            this.arrayPointType.add(this.pointType);
        }
        else
        {
            ArrayList arrayPtInLine = (ArrayList)this.arrayLines.get(index);
            arrayPtInLine.add(objPt);

        }


        if(this.arrayLines.size() == 0)
        {
            this.rightMostValue = x;
            this.leftMostValue = x;
            this.upMostValue = y;
            this.downMostValue = y;
        }
        else
        {
            if (x > this.rightMostValue)
                this.rightMostValue = x;
            else if (x < this.leftMostValue)
                this.leftMostValue = x;

            if (y > this.upMostValue)
                this.upMostValue = y;
            else if (y < this.downMostValue)
                this.downMostValue = y;
        }

        //this.arrayPt.add(objPt);
        this.upDate();
    }

    private boolean isPtInBoundary(ObjectPoint objPt, int x, int y)
    {
        int[] showValue = this.transfer.setPointRealToShow(objPt.x, objPt.y);
        if( Math.abs(showValue[0] - x) <= 4 && Math.abs(showValue[1] - y) <= 4)
            return true;
        else
            return false;
    }




    public void setZoomToFit()
    {
        double overAllWidth = this.rightMostValue - this.leftMostValue;
        double overAllHeight = this.upMostValue - this.downMostValue;
        double xDirectionMiddle = (this.rightMostValue + this.leftMostValue) / 2;
        double yDirectionMiddle = (this.upMostValue + this.downMostValue) / 2;

        double widthScale;
        double heightScale;

        if(overAllWidth == 0)
            widthScale = this.initialWidthScale;
        else
        {
            if( this.isScaleLog == false)
                widthScale = (this.getSize().width - 2 * this.boundaryWidth - this.gridWidth) / overAllWidth;
            else
                widthScale =  this.initialWidthScale;
                //widthScale = (this.getSize().width - 2 * this.boundaryWidth - this.gridWidth) / (Math.log10(this.rightMostValue) - Math.log10(this.leftMostValue));
        }
        if(overAllHeight == 0)
            heightScale = this.initialHeightScale;
        else
            heightScale = (this.getSize().height - 2 * this.boundaryWidth - this.gridWidth) / overAllHeight;

        if(this.isScaleLog == false)
        {
            this.transfer.setScale(widthScale, heightScale, this.getSize().width / 2, this.getSize().height / 2);
            widthScale = this.transfer.GetWidthScale();
            heightScale = this.transfer.GetHeightScale();

            this.transfer.setRealOrigin(this.getSize().width  / widthScale / 2 - xDirectionMiddle,
                                        this.getSize().height / heightScale / 2 - yDirectionMiddle);

        }
        else
        {
            this.transfer.setScale(widthScale, heightScale, this.getSize().width / 2, this.getSize().height / 2);
            widthScale = this.transfer.GetWidthScale();
            //this.transfer.setScale(this.initialWidthScale, heightScale, this.getSize().width / 2, this.getSize().height / 2);
            this.transfer.setRealOrigin(this.boundaryWidth / widthScale, this.getSize().height / heightScale / 2 - yDirectionMiddle);

        }

        this.upDate();
    }

    public void keyTyped(KeyEvent e)
    {}

    public void keyPressed(KeyEvent e)
    {
        int keyEventNumber = e.getKeyCode();
        int keyModifier = e.getModifiers();

        switch (keyModifier)
        {
            case KeyEvent.CTRL_MASK:
                _keyDownState = this.KEY_CTRL;
                break;
            case KeyEvent.ALT_MASK:
                _keyDownState = this.KEY_ALT;
                break;
            case KeyEvent.SHIFT_MASK:
                _keyDownState = this.KEY_SHIFT;
                break;
        }

        switch (keyEventNumber)
        {
            case KeyEvent.VK_F1:
                System.out.println("F1");
                break;
            case KeyEvent.VK_F2:
                System.out.println("F2");
                break;
            case KeyEvent.VK_F4:
                this.setZoomToFit();
                break;

            case KeyEvent.VK_F8:
                break;
            case KeyEvent.VK_F12:
                break;
            case KeyEvent.VK_F9:
            case KeyEvent.VK_F10:
                break;

            case 27: //KeyEvent.VK_EXCLAMATION_MARK:

                break;
        }

    }

    public void keyReleased(KeyEvent e)
    {
        this._keyDownState = this.KEY_NONE;
        this._mouseDragedState = this.MOUSE_NONE;
        this.setCursor(this.cursorArrow);
    }

    public void mouseDragged(MouseEvent e)
    {
        this.ptMouseCurrent_Show = new Point(e.getX(), e.getY());

        double[] mousePosition = this.transfer.setPointShowToReal(e.getX(), e.getY());
        this.ptMouseCurrent_Real = new ObjectPoint(mousePosition[0], mousePosition[1]);

        if ((e.getModifiers() & e.BUTTON1_MASK) != 0)
        {}
        else if ((e.getModifiers() & e.BUTTON2_MASK) != 0)
        {
            if (this._mouseDragedState == this.MOUSE_TRANSLATE)
            {
                this.transfer.translateOriginReal((this.ptMouseCurrent_Real.x -this.ptMousePressed_Real.x),
                                                  (this.ptMouseCurrent_Real.y - this.ptMousePressed_Real.y));
            }
        }
        this.upDate();

    }

    public void mouseMoved(MouseEvent e)
    {
        int lineNum = -1;
        int ptNum = -1;
        if(this.arrayLines != null)
        {
            for (int i = 0; i < this.arrayLines.size(); i++)
            {
                ArrayList arrayPtInLine = (ArrayList)this.arrayLines.get(i);
                for (int j = 0; j < arrayPtInLine.size(); j++)
                {
                    ObjectPoint objPt = (ObjectPoint) arrayPtInLine.get(j);
                    if (this.isPtInBoundary(objPt, e.getX(), e.getY()) == true)
                    {
                        lineNum = i;
                        ptNum = j;
                        break;
                    }
                }
            }
        }

        if(this.selectIndex != lineNum)
        {
            if(this.selectIndex != -1)
            {
                this.setPointColor(this.oldPointColor, this.selectIndex + 1);
                this.setLineColor(this.oldLineColor, this.selectIndex + 1);
            }
            this.selectIndex = lineNum;
            if(this.selectIndex != -1)
            {
                this.oldPointColor = this.getPointColor(lineNum + 1);
                this.oldLineColor = this.getLineColor(lineNum + 1);

                this.setPointColor(Color.white, lineNum + 1);
                this.setLineColor(Color.white, lineNum + 1);
            }
        }

        this.ptMouseCurrent_Show.setLocation(e.getX(),e.getY());

        this.upDate();
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        this.ptMousePressed_Show = new Point(e.getX(), e.getY());

        double[] mousePosition = this.transfer.setPointShowToReal(e.getX(), e.getY());
        this.ptMousePressed_Real = new ObjectPoint(mousePosition[0], mousePosition[1]);


        if ((e.getModifiers() & e.BUTTON1_MASK) != 0)
        {}

        if ((e.getModifiers() & e.BUTTON2_MASK) != 0)
        {
            if (_keyDownState == this.KEY_CTRL)
            {
                this.setCursor(this.cursorMove);
                _mouseDragedState = this.MOUSE_TRANSLATE;
            }
            if (_keyDownState == this.KEY_SHIFT)
            {
                this.setCursor(this.cursorScale);
                _mouseDragedState = this.MOUSE_SCALE;
            }
        }

    }

    public void mouseReleased(MouseEvent e)
    {
        this.setCursor(this.cursorArrow);
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0)
            this.popupMenu(e.getComponent(), e.getX(), e.getY());
    }

    public void mouseEntered(MouseEvent e)
    {
        this.requestFocus(); //需加入這一行keylistener才有作用
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseWheelMoved(MouseWheelEvent e)
    {
        int rotate = e.getWheelRotation();
        int amount = e.getScrollAmount();

        if (_keyDownState == this.KEY_CTRL)
            this.transfer.adjustWidthScale(rotate, e.getX(), e.getY());
        else if (_keyDownState == this.KEY_SHIFT)
            this.transfer.adjustHeightScale(rotate, e.getX(), e.getY());
        else
            this.transfer.adjustScale(rotate, e.getX(), e.getY());

        this.upDate();

    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(imgBuffer, 0, 0, this);
    }

    public void componentResized(ComponentEvent e)
    {
        this.getImgBuffer();
    }

    private void getImgBuffer()
    {
        imgBuffer = this.createImage(this.getWidth(), this.getHeight());
        if (imgBuffer != null)
        {
            gBuffer = imgBuffer.getGraphics();
            this.update(this.getGraphics());
        }
    }

    public void componentMoved(ComponentEvent e)
    {
    }

    public void componentShown(ComponentEvent e)
    {

    }

    public void componentHidden(ComponentEvent e)
    {
    }

    public void actionPerformed(ActionEvent e)
    {
    }

    public void update(Graphics g)
    {

        this.gBuffer.setColor(this.bgColor);
        this.gBuffer.fillRect(0, 0, this.getWidth(), this.getHeight());
        Graphics2D g2 = (Graphics2D)this.gBuffer;
        ////////////////
        if(this.arrayLines != null)
        {
            float[] dashline1 = {2f, 2f, 2f};

            for(int i = 0 ; i < this.arrayLines.size(); i++)
            {
                BasicStroke basicStroke1 = new BasicStroke((Float)this.arrayLineWidth.get(i), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dashline1, 2f);
                g2.setStroke(basicStroke1);

                ArrayList arrayLinePt = (ArrayList)this.arrayLines.get(i);
                for (int j = 0; j < arrayLinePt.size(); j++)
                {
                    ObjectPoint objCurrentPt = (ObjectPoint)arrayLinePt.get(j);
                    int[] currentPtShow = this.transfer.setPointRealToShow(objCurrentPt.x, objCurrentPt.y);

                    if (this.isDrawPt == true)
                    {
                        g2.setColor((Color)this.arrayPointColor.get(i));
                        if ((Integer)this.arrayPointType.get(i) == 0)
                            g2.fillOval(currentPtShow[0] - (Integer)this.arrayPointSize.get(i) / 2, currentPtShow[1] - (Integer)this.arrayPointSize.get(i) / 2, (Integer)this.arrayPointSize.get(i), (Integer)this.arrayPointSize.get(i));
                        else if ((Integer)this.arrayPointType.get(i) == 1)
                            g2.drawOval(currentPtShow[0] - (Integer)this.arrayPointSize.get(i) / 2, currentPtShow[1] - (Integer)this.arrayPointSize.get(i) / 2, (Integer)this.arrayPointSize.get(i), (Integer)this.arrayPointSize.get(i));
                    }

                    if (this.isDrawLine == true)
                    {
                        g2.setColor((Color)this.arrayLineColor.get(i));
                        if (j >= 1)
                        {
                            ObjectPoint objPreviousPt = (ObjectPoint)arrayLinePt.get(j - 1);
                            int[] previousPtShow = this.transfer.setPointRealToShow(objPreviousPt.x, objPreviousPt.y);
                            g2.drawLine(previousPtShow[0], previousPtShow[1], currentPtShow[0], currentPtShow[1]);
                        }
                    }

                }
            }


        }

        ////////////////


        g2.setColor(this.bgColor);

        g2.fillRect(0, 0, this.boundaryWidth, this.panelHeight);
        g2.fillRect(this.panelWidth - this.boundaryWidth, 0, this.boundaryWidth, this.panelHeight);
        g2.fillRect(0, 0, this.panelWidth, this.boundaryWidth);
        g2.fillRect(0, this.panelHeight - this.boundaryWidth, this.panelWidth, this.boundaryWidth);

        if(this.isScaleLog == true)
            this.drawBackGroundLog(g2);
        else
            this.drawBackGroundLinear(g2);

        if (this.isShowMouseLocation == true &&
            this.ptMouseCurrent_Show.x > this.boundaryWidth &&
            this.ptMouseCurrent_Show.x < this.getSize().width - this.boundaryWidth &&
            this.ptMouseCurrent_Show.y > this.boundaryWidth &&
            this.ptMouseCurrent_Show.y < this.getSize().height - this.boundaryWidth   )
        {
            double[] ptCurrentValue = this.transfer.setPointShowToReal(this.ptMouseCurrent_Show.x, this.ptMouseCurrent_Show.y);

            java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
            String xValue = myformat.format(Math.pow(10, (ptCurrentValue[0] / 1.25)));
            String yValue = myformat.format(ptCurrentValue[1]);

            String drawUnitX = "(" + xValue + ", " + yValue + ")";

            g2.drawString(drawUnitX, this.ptMouseCurrent_Show.x + 20, this.ptMouseCurrent_Show.y + 20);
        }

        if (g != null)
           this.paint(g);
    }

    private void upDate()
    {
        if (gBuffer != null)
        {
            this.update(this.getGraphics());
        }
    }

    private void popupMenu(Component parent, int positionX, int positionY )
    {
        this._popupMenu.show(parent, positionX, positionY);
    }





    private void drawBackGroundLinear(Graphics2D g2d)
    {
        if (isShowGrid != true)
            return;

        //設定繪製格線的畫筆
        float[] dashline1 = {2f, 0f, 2f};
        BasicStroke basicStroke1 = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dashline1, 2f);

        g2d.setStroke(basicStroke1);
        g2d.setColor(this.bgGridColor);

        this.calculateGridWidth();

        Point origin = this.transfer.GetShowOrign();

        String drawUnitX = "( " + this.unitX + " )";
        String drawUnitY = "( " + this.unitY + " )";
        g2d.drawString(drawUnitX, this.getSize().width - 50, this.getSize().height - 20);
        g2d.drawString(drawUnitY, 20, 30);


        int startIndex;
        int endIndex  ;

        //畫垂直線
        startIndex = (int) Math.floor((this.boundaryWidth - origin.x) / this.gridWidth);
        endIndex = (int) Math.ceil((this.getSize().width - origin.x - this.boundaryWidth) / this.gridWidth);
        for (int i = startIndex; i <= endIndex; i++)
        {
            if (origin.x + i * this.gridWidth >= this.boundaryWidth && origin.x + i * this.gridWidth <= this.getSize().width - this.boundaryWidth)
            {
                if (this.isShowGrid == true)
                    g2d.drawLine(origin.x + i * this.gridWidth, this.boundaryWidth, origin.x + i * this.gridWidth, this.getSize().height - this.boundaryWidth);

                //畫刻度
                if (i % 2 == 0)
                {
                    double value = (double)(i * this.gridWidth) / this.transfer.GetWidthScale();
                    double absValue = Math.abs(value);

                    String labelx;

                    if (absValue == 0)
                        labelx = "0";
                    else if (absValue < 0.001)
                    {

                        int eValue = 0;
                        double eValue2 = 1;
                        double tempValue = absValue * eValue2;
                        while (absValue * eValue2 < 1)
                        {
                            eValue++;
                            eValue2 *= 10;
                            tempValue = absValue * eValue2;
                        }
                        if (value >= 0)
                            labelx = String.format("%.2f", tempValue) + "E-" + eValue;
                        else
                            labelx = "-" + String.format("%.2f", tempValue) + "E-" + eValue;
                    }
                    else if (absValue >= 1000000)
                    {
                        int eValue = 0;
                        double eValue2 = 1;
                        double tempValue = absValue * eValue2;
                        while (absValue * eValue2 >= 10)
                        {
                            eValue++;
                            eValue2 *= 0.1;
                            tempValue = absValue * eValue2;
                        }
                        if (value >= 0)
                            labelx = String.format("%.2f", tempValue) + "E+" + eValue;
                        else
                            labelx = "-" + String.format("%.2f", tempValue) + "E+" + eValue;
                    }
                    else
                    {
                        int eValue = 0;
                        double eValue2 = 1;
                        while (absValue * eValue2 >= 10)
                        {
                            eValue++;
                            eValue2 *= 0.1;
                        }

                        BigDecimal bd = new BigDecimal(absValue);
                        BigDecimal bd2 = bd.setScale(5 - eValue, BigDecimal.ROUND_HALF_UP);
                        if (value >= 0)
                            labelx = bd2.toString();
                        else
                            labelx = "-" + bd2.toString();
                    }
                    g2d.drawString(labelx, origin.x + i * this.gridWidth - this.gridWidth / 8, this.getSize().height - 20);
                }
            }
        }

        //畫水平線
        startIndex = (int) Math.floor((this.boundaryWidth - origin.y) / this.gridWidth);
        endIndex = (int) Math.ceil((this.getSize().width - origin.y - this.boundaryWidth) / this.gridWidth);
        for (int i = startIndex; i <= endIndex; i++)
        {
            if (origin.y + i * this.gridWidth >= this.boundaryWidth && origin.y + i * this.gridWidth <= this.getSize().width - this.boundaryWidth)
            {
                if (this.isShowGrid == true)
                    g2d.drawLine(this.boundaryWidth, origin.y + i * this.gridWidth, this.getSize().width - this.boundaryWidth, origin.y + i * this.gridWidth);

                //畫刻度
                if (i % 2 == 0)
                {
                    double value = 0 - i * this.gridWidth / this.transfer.GetHeightScale();
                    double absValue = Math.abs(value);

                   String labely;

                   if(absValue == 0)
                       labely = "0";
                   else if(absValue < 0.001)
                   {

                       int eValue = 0;
                       double eValue2 = 1;
                       double tempValue = absValue * eValue2;
                       while(absValue * eValue2 < 1)
                       {
                           eValue++;
                           eValue2 *= 10;
                           tempValue = absValue * eValue2;
                       }
                       if(value >= 0)
                           labely = String.format("%.2f", tempValue) + "E-" + eValue;
                       else
                           labely = "-" + String.format("%.2f", tempValue) + "E-" + eValue;
                   }
                   else if(absValue >= 1000000)
                   {
                       int eValue = 0;
                       double eValue2 = 1;
                       double tempValue = absValue * eValue2;
                       while (absValue * eValue2 >= 10)
                       {
                           eValue++;
                           eValue2 *= 0.1;
                           tempValue = absValue * eValue2;
                       }
                       if(value >= 0)
                           labely = String.format("%.2f", tempValue) + "E+" + eValue;
                       else
                           labely = "-" + String.format("%.2f", tempValue) + "E+" + eValue;
                   }
                   else
                   {
                       int eValue = 0;
                       double eValue2 = 1;
                       while (absValue * eValue2 >= 10)
                       {
                           eValue++;
                           eValue2 *= 0.1;
                       }

                       BigDecimal bd = new BigDecimal(absValue);
                       BigDecimal bd2 = bd.setScale( 5 - eValue, BigDecimal.ROUND_HALF_UP);
                       if(value >= 0)
                           labely = bd2.toString();
                       else
                           labely = "-" + bd2.toString();
                   }

                    g2d.drawString(labely, 4, origin.y + i * this.gridWidth + this.gridWidth / 8);

                }
            }
        }



    }

    private void drawBackGroundLog(Graphics2D g2d)
    {
        //設定繪製格線的畫筆
        float[] dashline1 = {2f, 0f, 2f};
        BasicStroke basicStroke1 = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dashline1, 2f);
        g2d.setStroke(basicStroke1);
        g2d.setColor(this.bgGridColor);

        //this.calculateGridWidth();

        Point origin = this.transfer.GetShowOrign();

        //int logGridWidth = (int) ((this.gridWidth * 2.5) * this.transfer.GetWidthScale() / 100);
        int logGridWidth = (int) ((this.gridWidth * 2.5) * this.transfer.GetWidthScale() / this.initialWidthScale);

        int gridCountLeft = (origin.x - this.boundaryWidth) / logGridWidth + 1;
        int gridCountRight = (this.getSize().width - origin.x - this.boundaryWidth) / logGridWidth;
        int gridCountDown = (this.getSize().height - origin.y - this.boundaryWidth) / this.gridWidth;
        int gridCountUp = (origin.y - this.boundaryWidth) / this.gridWidth;

        String drawUnitX = "( " + this.unitX + " )";
        String drawUnitY = "( " + this.unitY + " )";
        g2d.drawString(drawUnitX, this.getSize().width - 50, this.getSize().height - 20);
        g2d.drawString(drawUnitY, 20, 30);

        if (this.arrayClibrationLabel == null)
            this.arrayClibrationLabel = new ArrayList();

        for (int i = 0; i < this.arrayClibrationLabel.size(); i++)
        {
            JLabel label = (JLabel)this.arrayClibrationLabel.get(i);

            this.remove(label);
        }


        for (int i = 0; i <= gridCountLeft; i++)
        {
            if (origin.x - i * logGridWidth >= this.boundaryWidth && origin.x - i * logGridWidth <= this.getSize().width - this.boundaryWidth)
            {
                //畫左半部垂直線
                if (this.isShowGrid == true)
                    g2d.drawLine(origin.x - i * logGridWidth, this.boundaryWidth, origin.x - i * logGridWidth, this.getSize().height - this.boundaryWidth);

                //畫刻度
                double value = Math.pow(10, i * -1);
                String labelx;
                if (value < 0.0001)
                    labelx = "1.0E-" + Integer.toString(i);
                else
                    labelx = Double.toString(value);

                g2d.drawString(labelx, origin.x - i * logGridWidth - logGridWidth / 8, this.getSize().height - 20);

            }
            for (int L = 2; L <= 9; L++)
            {
                if (this.isShowGrid == true)
                {
                    if (origin.x - i * logGridWidth + (int) (logGridWidth * Math.log10(L)) >= this.boundaryWidth &&
                        origin.x - i * logGridWidth + (int) (logGridWidth * Math.log10(L)) <= this.getSize().width - this.boundaryWidth)
                        g2d.drawLine(origin.x - i * logGridWidth + (int) (logGridWidth * Math.log10(L)), this.boundaryWidth,
                                     origin.x - i * logGridWidth + (int) (logGridWidth * Math.log10(L)), this.getSize().height - this.boundaryWidth);
                }
            }

        }

        for (int i = 1; i <= gridCountRight; i++)
        {
            if (origin.x + i * logGridWidth >= this.boundaryWidth && origin.x + i * logGridWidth <= this.getSize().width - this.boundaryWidth)
            {
                //畫右半部垂直線
                if (this.isShowGrid == true)
                    g2d.drawLine(origin.x + i * logGridWidth, this.boundaryWidth, origin.x + i * logGridWidth, this.getSize().height - this.boundaryWidth);

                //畫刻度
                double value = Math.pow(10, i); //sampling rate 250ks/s 表示每個點的時間相差0.004ms 希望每40個pixel也就是一大格線就是0.004ms的時間差
                String labelx;
                if (value > 10000)
                    labelx = "1.0E+" + Integer.toString(i);
                else
                    labelx = Double.toString(value);

                g2d.drawString(labelx, origin.x + i * logGridWidth - logGridWidth / 8, this.getSize().height - 20);
            }
            for (int L = 2; L <= 9; L++)
            {
                if (this.isShowGrid == true)
                {
                    if (origin.x + i * logGridWidth + (int) (logGridWidth * Math.log10(L)) >= this.boundaryWidth &&
                        origin.x + i * logGridWidth + (int) (logGridWidth * Math.log10(L)) <= this.getSize().width - this.boundaryWidth)
                        g2d.drawLine(origin.x + i * logGridWidth + (int) (logGridWidth * Math.log10(L)), this.boundaryWidth,
                                     origin.x + i * logGridWidth + (int) (logGridWidth * Math.log10(L)), this.getSize().height - this.boundaryWidth);
                }
            }

        }

        for (int i = 0; i <= gridCountDown; i++)
        {
            if (origin.y + i * this.gridWidth >= this.boundaryWidth && origin.y + i * this.gridWidth <= this.getSize().width - this.boundaryWidth)
            {
                //畫下半部水平線
                if (this.isShowGrid == true)
                    g2d.drawLine(this.boundaryWidth, origin.y + i * this.gridWidth, this.getSize().width - this.boundaryWidth, origin.y + i * this.gridWidth);

                //畫刻度
                if (i % 2 == 0)
                {
                    double value = 0 - i * this.gridWidth / this.transfer.GetHeightScale();
                    double absValue = Math.abs(value);

                    String labely;

                    if(absValue == 0)
                        labely = "0";
                    else if(absValue < 0.001)
                    {

                        int eValue = 0;
                        double eValue2 = 1;
                        double tempValue = absValue * eValue2;
                        while(absValue * eValue2 < 1)
                        {
                            eValue++;
                            eValue2 *= 10;
                            tempValue = absValue * eValue2;
                        }
                        if(value >= 0)
                            labely = String.format("%.2f", tempValue) + "E-" + eValue;
                        else
                            labely = "-" + String.format("%.2f", tempValue) + "E-" + eValue;
                    }
                    else if(absValue >= 1000000)
                    {
                        int eValue = 0;
                        double eValue2 = 1;
                        double tempValue = absValue * eValue2;
                        while (absValue * eValue2 >= 10)
                        {
                            eValue++;
                            eValue2 *= 0.1;
                            tempValue = absValue * eValue2;
                        }
                        if(value >= 0)
                            labely = String.format("%.2f", tempValue) + "E+" + eValue;
                        else
                            labely = "-" + String.format("%.2f", tempValue) + "E+" + eValue;
                    }
                    else
                    {
                        int eValue = 0;
                        double eValue2 = 1;
                        while (absValue * eValue2 >= 10)
                        {
                            eValue++;
                            eValue2 *= 0.1;
                        }

                        BigDecimal bd = new BigDecimal(absValue);
                        BigDecimal bd2 = bd.setScale( 5 - eValue, BigDecimal.ROUND_HALF_UP);
                        if(value >= 0)
                            labely = bd2.toString();
                        else
                            labely = "-" + bd2.toString();
                    }


                g2d.drawString(labely, 4, origin.y + i * this.gridWidth  + this.gridWidth / 8 );
                }
            }
        }

        for (int i = 1; i <= gridCountUp; i++)
        {
            if (origin.y - i * this.gridWidth >= this.boundaryWidth && origin.y - i * this.gridWidth <= this.getSize().width - this.boundaryWidth)
            {
                //畫上半部水平線
                if (this.isShowGrid == true)
                    g2d.drawLine(this.boundaryWidth, origin.y - i * this.gridWidth, this.getSize().width - this.boundaryWidth, origin.y - i * this.gridWidth);

                //畫刻度
                if (i % 2 == 0)
                {
                    double value = i * this.gridWidth / this.transfer.GetHeightScale();
                    double absValue = Math.abs(value);

                    String labely;

                    if (absValue == 0)
                        labely = "0";
                    else if (absValue < 0.001)
                    {

                        int eValue = 0;
                        double eValue2 = 1;
                        double tempValue = absValue * eValue2;
                        while (absValue * eValue2 < 1)
                        {
                            eValue++;
                            eValue2 *= 10;
                            tempValue = absValue * eValue2;
                        }
                        if (value >= 0)
                            labely = String.format("%.2f", tempValue) + "E-" + eValue;
                        else
                            labely = "-" + String.format("%.2f", tempValue) + "E-" + eValue;
                    }
                    else if (absValue >= 1000000)
                    {
                        int eValue = 0;
                        double eValue2 = 1;
                        double tempValue = absValue * eValue2;
                        while (absValue * eValue2 >= 10)
                        {
                            eValue++;
                            eValue2 *= 0.1;
                            tempValue = absValue * eValue2;
                        }
                        if (value >= 0)
                            labely = String.format("%.2f", tempValue) + "E+" + eValue;
                        else
                            labely = "-" + String.format("%.2f", tempValue) + "E+" + eValue;
                    }
                    else
                    {
                        int eValue = 0;
                        double eValue2 = 1;
                        while (absValue * eValue2 >= 10)
                        {
                            eValue++;
                            eValue2 *= 0.1;
                        }

                        BigDecimal bd = new BigDecimal(absValue);
                        BigDecimal bd2 = bd.setScale(5 - eValue, BigDecimal.ROUND_HALF_UP);
                        if (value >= 0)
                            labely = bd2.toString();
                        else
                            labely = "-" + bd2.toString();
                    }

                    g2d.drawString(labely, 4, origin.y - i * this.gridWidth + this.gridWidth / 8 );
                }
            }
        }

    }

    private void switchLinearOrLog(boolean isLog)
    {
        this.isScaleLog = isLog;
        if(this.transfer == null)
            return;

        ObjectPoint currentRealOrigin = this.transfer.GetRealOrigin();

        if(isLog == true)
        {
            this.transfer.setScale(this.initialWidthScale, this.initialHeightScale, this.getSize().width, this.getSize().height);
            this.transfer.setRealOrigin((this.boundaryWidth) / this.initialWidthScale, currentRealOrigin.y);
        }

        this.transfer.setIfLog(isLog);

        this.upDate();
    }

    public void resetPanel()
    {
    }

    /**
     * 將Klippel的數據貼上
     */
    public void importDataFromKlippel()
    {
        ArrayList arrayKlippelData = new ArrayList();

        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        //System.out.println("Object Name: " + clip.getName());
        Transferable clipData = clip.getContents(clip);
        if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor))
        {

            try
            {
                String KlippelData = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
                //System.out.println("String: " + KlippelData);

                int startIndex = 0;
                int endIndex = 0;
                startIndex = KlippelData.indexOf("Curve=[");
                endIndex = KlippelData.indexOf("];");

                int valueStartIndex = 0;
                int valueEndIndex = 0;
                boolean boolStart = false;

                for (int i = startIndex + 7; i < endIndex; i++)
                {
                    if (KlippelData.charAt(i) != 10 && KlippelData.charAt(i) != 9 && KlippelData.charAt(i) != 32 && boolStart == false)
                    {
                        boolStart = true;
                        valueStartIndex = i;
                        continue;
                    }

                    if ((KlippelData.charAt(i) == 10 || KlippelData.charAt(i) == 9 || KlippelData.charAt(i) == 32) && boolStart == true)
                    {
                        boolStart = false;
                        valueEndIndex = i;
                        String s = KlippelData.substring(valueStartIndex, valueEndIndex);
                        //System.out.println("value: " + s);
                        double value = Double.valueOf(s);
                        //System.out.println("value double: " + value);
                        arrayKlippelData.add(value);

                        continue;
                    }
                }
                int maxIndex = 0;
                if (this.arrayLineIndex != null)
                {
                    for (int count = 0; count < this.arrayLineIndex.size(); count++)
                    {
                        int existLineIndex = (Integer)this.arrayLineIndex.get(count);
                        if (existLineIndex > maxIndex)
                            maxIndex = existLineIndex;
                    }
                }

                for (int num = 0; num < arrayKlippelData.size(); num += 2)
                {
                    double xValue = (Double) arrayKlippelData.get(num);
                    double yValue = (Double) arrayKlippelData.get(num + 1);
                    this.inputValue(xValue, yValue, maxIndex + 1);

                }
            }
            catch (IOException ex)
            {
            }
            catch (UnsupportedFlavorException ex)
            {
            }
        }
    }

    public void exportDataToKlippel()
    {
        int lineNum = 0;

        if (this.arrayLines == null)
            return;
        if (this.arrayLines.size() == 0)
            return;

        if (this.selectIndex != -1)
            lineNum = this.selectIndex;

        ArrayList lineData = (ArrayList)this.arrayLines.get(lineNum);
        String data_SDT = "";
        data_SDT += "Curve=[";

        for (int i = 0; i < lineData.size(); i++)
        {
            ObjectPoint objPt = (ObjectPoint) lineData.get(i);
            double xValue = objPt.x;
            double yValue = objPt.y;

            data_SDT += "\n" + "\t" + Double.toString(xValue) + "\t" + Double.toString(yValue);
        }
        data_SDT += "\n";
        data_SDT += "];";

        StringSelection stringSelection = new StringSelection(data_SDT);
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(stringSelection, null);
    }


}


class importKlippelDataAction extends AbstractAction
{
    protected SDT_PanelGraph _panel;

    public importKlippelDataAction(SDT_PanelGraph panel)
    {
        super("importKlippelData");
        this._panel = panel;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        this._panel.importDataFromKlippel();
    }
}


class exportKlippelDataAction extends AbstractAction
{
    protected SDT_PanelGraph _panel;

    public exportKlippelDataAction(SDT_PanelGraph panel)
    {
        super("exportKlippelData");
        this._panel = panel;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        this._panel.exportDataToKlippel();
    }
}
