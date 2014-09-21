package sdt.panel.drawpanel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import sdt.define.*;
import sdt.framework.*;
import sdt.geometry.*;
import sdt.geometry.element.*;
import sdt.data.*;



/**
* <p>Title: </p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2004</p>
* <p>Company: </p>
* @author not attributable
* @version 1.0
*/

public class SDT_DrawPanel2D extends JPanel implements MouseListener, MouseMotionListener,MouseWheelListener,ActionListener, ComponentListener,KeyListener,DefineSystemConstant
{
    protected   SDT_System           _system;

    protected   int                 showMode                    = 0 ; // 1 for print
    private     JPopupMenu          _popupMenuSktSectionCap;
    private     JPopupMenu          _popupMenuSktSectionSurround;
    private     JPopupMenu          _popupMenuSktSectionTransition;
    private     JPopupMenu          _popupMenuSktSectionDiaphragm;
    private     JPopupMenu          _popupMenuSktTop;
    private     JPopupMenu          _popupMenuSktSectionCoil;
    private     JPopupMenu          _popupMenuSktSectionMagnet;
    private     JPopupMenu          _popupMenuSktSectionMagnetTop;
    private     JPopupMenu          _popupMenuSktSectionMagnetOuter;
    private     JPopupMenu          _popupMenuSktSectionTopPlate;
    private     JPopupMenu          _popupMenuSktSectionYokeBase;
    private     JPopupMenu          _popupMenuSktSectionYokeStage1;
    private     JPopupMenu          _popupMenuSktSectionYokeStage2;

    private     Cursor              cursorMove          = new Cursor(Cursor.MOVE_CURSOR);
    private     Cursor              cursorHand          = new Cursor(Cursor.HAND_CURSOR);
    private     Cursor              cursorScale         = new Cursor(Cursor.S_RESIZE_CURSOR);
    protected   Color               colorBG             = new Color(0.1f,0.1f,0.1f);//Color.BLACK;//Color.DARK_GRAY;


    //protected   Color               colorBGUp           = new Color(0,27,55);
    //protected   Color               colorBGDown         = new Color(0,74,105);
    protected   GradientPaint       paintBG ;           // = new GradientPaint(0.1f,0.0f,colorBGUp,
                                                        //                    0.1f,500f,colorBGDown);

    protected   Color               colorBGGrid         = new Color(0.55f,0.55f,0.55f);
    protected   Color               colorBGCoordinate   = new Color(0.85f,0.65f,0.5f);//Color.white;///Color.BLACK;

    private     ObjectPoint         ptMousePressed;
    private     ObjectPoint         ptMouseCurrent;

    protected   Image               imgBuffer;
    protected   Graphics            gBuffer;

    private     boolean             _isShowGird         = true;

    private     int                 _mouseDragedState   = this.MOUSE_NONE;
    private     int                 _keyDownState       = this.KEY_NONE;
    private     int                 _elementType        = this.TYPE_CONE;
    private     int                 _sectionNumber      = this.XZView;
    protected   double              nodeThickness;


    private     ArrayList          arrayElementShow  = new ArrayList();
    private     ElementBase    selectedPoint;
    private     ElementBase    _selectedElement;
    private     Color oldColor;




    protected   SDT_DrawTransfer    _transfer;

    public      SDT_DrawTransfer    getTransfer()                                { return this._transfer;}
    public      void                setTransfer(SDT_DrawTransfer t)              { this._transfer = t;}
    public      void                setIsShowGrid(boolean bool)                  { this._isShowGird = bool;}
    public      GradientPaint       getBackgroundPaint()                         { return this.paintBG;}
    public      void                setMenuPopupCapSection       (JPopupMenu pop){ this._popupMenuSktSectionCap = pop;}

    public      void                setMenuPopupTransitionSection(JPopupMenu pop){ this._popupMenuSktSectionTransition = pop;}
    public      void                setMenuPopupDiaphragmSection (JPopupMenu pop){ this._popupMenuSktSectionDiaphragm = pop;}
    public      void                setMenuPopupSurroundSection  (JPopupMenu pop){ this._popupMenuSktSectionSurround = pop;}
    public      void                setMenuPopupSktTop           (JPopupMenu pop){ this._popupMenuSktTop = pop;}
    public      void                setMenuPopupCoilSection      (JPopupMenu pop){ this._popupMenuSktSectionCoil = pop;}

    public      void                setMenuPopupMagnetSection       (JPopupMenu pop){ this._popupMenuSktSectionMagnet = pop;}
    public      void                setMenuPopupMagnetTopSection    (JPopupMenu pop){ this._popupMenuSktSectionMagnetTop = pop;}
    public      void                setMenuPopupMagnetOuterSection    (JPopupMenu pop){ this._popupMenuSktSectionMagnetOuter = pop;}
    public      void                setMenuPopupTopPlateSection     (JPopupMenu pop){ this._popupMenuSktSectionTopPlate = pop;}
    public      void                setMenuPopupYokeBaseSection       (JPopupMenu pop){ this._popupMenuSktSectionYokeBase = pop;}
    public      void                setMenuPopupYokeStage1Section    (JPopupMenu pop){ this._popupMenuSktSectionYokeStage1 = pop;}
    public      void                setMenuPopupYokeStage2Section     (JPopupMenu pop){ this._popupMenuSktSectionYokeStage2 = pop;}

    public SDT_DrawPanel2D()
    {

    }


    public SDT_DrawPanel2D(SDT_System system) //index main or second
    {
        this._system = system;
        this.setBackgroundPaint();
        this.showElementBySection(this._elementType,this._sectionNumber);
        try
        {

            this.nodeThickness = 0.2;
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    void jbInit() throws Exception
    {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addComponentListener(this);

   }



   public void mouseClicked(MouseEvent e)
   {
       if (e.getClickCount() == 1)
       {
           mouseClickedOnce(e);
       }
   }


  private void mouseClickedOnce(MouseEvent e)
  {
      int clickType = e.getModifiers();
      System.out.println("Click Once");

      if ((e.getModifiers() & e.BUTTON1_MASK) != 0)
      {
          double[] mousePosition = this._transfer.setPointShowToReal(e.getX(), e.getY());
          ObjectPoint ptMouseCurrent = new ObjectPoint();
          ptMouseCurrent.x = mousePosition[0];
          ptMouseCurrent.y = mousePosition[1];

          _selectedElement = this.selectElement(ptMouseCurrent);

          if (_selectedElement == null)
          {
              System.out.println("Element Selected is null");
              return;
          }
          System.out.println("Element Selected Not null");

          if (_sectionNumber != this.XYView)
          {
              this._elementType = _selectedElement.getElementType();
              this._system.getModeler().GetAssemTree().setSelectedNode(this._elementType, this._sectionNumber);

              this._system.getDataManager().setCurrentDataType(this._elementType);
              this.showElement();
              this._system.getModeler().GetAssemTree().highLightSelectedNode();
          }
      }
  }



   public void mousePressed(MouseEvent e)
   {
       if (ptMousePressed == null)
           ptMousePressed = new ObjectPoint();

       double[] mousePosition = this._transfer.setPointShowToReal(e.getX(), e.getY());
       this.ptMousePressed.x = mousePosition[0];
       this.ptMousePressed.y = mousePosition[1];


       if ((e.getModifiers() & e.BUTTON1_MASK) != 0)
       {
           if(this.selectedPoint != null)
           {}


       }

       if ((e.getModifiers() & e.BUTTON2_MASK) != 0 )
       {
           if(_keyDownState == this.KEY_CTRL)
           {
               this.setCursor(this.cursorMove);
               _mouseDragedState = this.MOUSE_TRANSLATE;
           }
           if(_keyDownState == this.KEY_SHIFT)
           {
               this.setCursor(this.cursorScale);
               _mouseDragedState = this.MOUSE_SCALE;
           }
       }


    }


    public void mouseReleased(MouseEvent e)
    {
        double[] mousePosition = this._transfer.setPointShowToReal(e.getX(), e.getY());
        ObjectPoint ptMouseCurrent = new ObjectPoint(mousePosition[0], mousePosition[1]);

        if ((e.getModifiers() & e.BUTTON1_MASK) != 0)
        {
            this.setCursor(null);
        }
        if ((e.getModifiers() & e.BUTTON2_MASK) != 0)
        {
            this.setCursor(null);
            this._mouseDragedState = this.MOUSE_NONE;
        }

        if ((e.getModifiers() & e.BUTTON3_MASK) != 0)
        {
            _selectedElement = this.selectElement(ptMouseCurrent);
            if (_selectedElement != null)
                this.popupMenu(_selectedElement, e.getComponent(), e.getX(), e.getY());
        }
   }



   public void mouseEntered(MouseEvent e)
   {
       this.requestFocus();
   }

   public void mouseExited(MouseEvent e)
   {
   }

   public void mouseDragged(MouseEvent e)
   {

       double[] mousePosition = this._transfer.setPointShowToReal(e.getX(), e.getY());
       ptMouseCurrent = new ObjectPoint(mousePosition[0],mousePosition[1]);

       if ((e.getModifiers() & e.BUTTON1_MASK) != 0 )
       {
           if(this._system.getModeler().getControlType() == this.MOUSE_NONE)
           {
               if ((this.selectedPoint != null || this._selectedElement != null))
               {
                   System.out.println("Element Move");
                   this.setCursor(this.cursorMove);

                   if (this.selectedPoint != null && this._selectedElement == this.selectedPoint)
                       this.selectedPoint.move((ptMouseCurrent.x - this.ptMousePressed.x),
                                               (ptMouseCurrent.y - this.ptMousePressed.y));
                   else if (this._selectedElement != null)
                       this._selectedElement.move((ptMouseCurrent.x - this.ptMousePressed.x),
                                                  (ptMouseCurrent.y - this.ptMousePressed.y));

                   this.ptMousePressed = ptMouseCurrent; // reset the first point
               }
           }
           else if(this._system.getModeler().getControlType() == this.MOUSE_TRANSLATE)
           {
               this.setCursor(this.cursorMove);
               this._transfer.translateOriginReal((ptMouseCurrent.x - this.ptMousePressed.x),
                                                  (ptMouseCurrent.y - this.ptMousePressed.y));
           }
           else if(this._system.getModeler().getControlType() == this.MOUSE_ROTATE)
           {

           }

       }
       else if((e.getModifiers() & e.BUTTON2_MASK) != 0 )
       {

           if (_mouseDragedState == this.MOUSE_TRANSLATE)
           {
               this._transfer.translateOriginReal((ptMouseCurrent.x - this.ptMousePressed.x),
                                                  (ptMouseCurrent.y - this.ptMousePressed.y));
           }
           else if(_mouseDragedState == this.MOUSE_SCALE)
           {
               int dy =  this._transfer.setLengthRealToShow(ptMouseCurrent.y - this.ptMousePressed.y);
               this._transfer.adjustScale(dy / 20);

           }
       }
       this.upDate();


   }


   public void mouseMoved(MouseEvent e)
   {
       double[] mousePosition = this._transfer.setPointShowToReal(e.getX(), e.getY());
       ObjectPoint ptMouseCurrent = new ObjectPoint();
       ptMouseCurrent.x = mousePosition[0];
       ptMouseCurrent.y = mousePosition[1];

       ObjectPoint currentObjPt = new ObjectPoint(mousePosition[0], mousePosition[1]);
       ElementBase selectedElement = (ElementBase)this.selectElement(currentObjPt);
       if(selectedElement != null && selectedElement.getElementType() == this.TYPE_POINT)
       {
           this.selectedPoint = selectedElement;
       }
       else
           this.selectedPoint = null;

       if(selectedElement != this._selectedElement)
       {
           if (this._selectedElement != null) // back to the element's color
           {
               this.setCursor(null);
               this._selectedElement.setColor(this.oldColor);
               this.upDate();
           }
           this._selectedElement = selectedElement;
           if (this._selectedElement != null) // high light the selected element
           {
               this.setCursor(this.cursorHand);
               this.oldColor = this._selectedElement.getColor();
               this._selectedElement.setColor(Color.white);
               this.upDate();
           }
       }
   }

   public void mouseWheelMoved(MouseWheelEvent e)
   {
       int rotate = e.getWheelRotation();
       int amount = e.getScrollAmount();
       this._transfer.adjustScale(rotate * amount,e.getX(),e.getY());
       this.upDate();
   }

   public void actionPerformed(ActionEvent e)
   {


   }


   public void update(Graphics g)
   {
       this.gBuffer.clearRect(0, 0, this.getWidth(), this.getHeight());
       Graphics2D g2 = (Graphics2D)this.gBuffer;
       g2.setPaint(paintBG);
       g2.fillRect(0, 0, this.getWidth(), this.getHeight());
       this.drawBackGround(g2);

       //Stroke line = new BasicStroke(2.2f);
       //g2.setStroke(line);

       boolean drawSelectedElementFirst = false;
       ArrayList selectedElementArray = null;

       if(this._selectedElement == null)
       {
           drawSelectedElementFirst  = false;
       }
       else if(this.selectedPoint != this._selectedElement && this.selectedPoint != null)
       {
           drawSelectedElementFirst  = true;
           this._selectedElement.draw(this.gBuffer, this._transfer);
       }
       else if(this.selectedPoint == this._selectedElement )
       {
           drawSelectedElementFirst  = true;

           //this._selectedElement.draw(this.gBuffer, this._transfer);
           selectedElementArray = selectedPoint.getLinkedElementArray();
           ((ElementBase)selectedElementArray.get(0)).draw(this.gBuffer, this._transfer);
       }


      // System.out.println(" drawSelectedElementFirst :" + drawSelectedElementFirst);
       for (int i = 0; i < this.arrayElementShow.size(); i++)
       {
           ElementBase component = (ElementBase)this.arrayElementShow.get(i);
           if (drawSelectedElementFirst)
           {
               if (selectedElementArray != null)
               {
                   if ( component!= selectedElementArray.get(0)) // component != this._selectedElement &&)
                   {
                       component.draw(this.gBuffer, this._transfer);
                   }
               }
               else
               {
                   if (component != this._selectedElement)
                       component.draw(this.gBuffer, this._transfer);
               }

           }
           else
               component.draw(this.gBuffer, this._transfer);
       }

       if(this._selectedElement != null)
           this._selectedElement.draw(this.gBuffer, this._transfer);


       //if (g != null)
           //this.paint(g);
   }
   public void upDate()
   {
       if (gBuffer != null)
       {
           this.update(this.getGraphics());
       }
       if(this._system.getModeler().getPanel2D()!= null)
       {
           this._system.getModeler().getPanel2D().SetCalibration();
           this._system.getModeler().getPanel2D().updateUI();
       }
   }

   public void drawBackGround(Graphics2D g2d)
   {

       int grid = (int)this._transfer.GetWidthScale()/2;
       int fineWidthCount  = this.getSize().width/grid;
       int fineHeightCount = this.getSize().height/grid;

       float[] dashlineCoordinate= {5f,0f,2f};
       BasicStroke basicStrokeCoordinate = new BasicStroke(2.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND,1.0f, dashlineCoordinate, 2f);
       g2d.setStroke(basicStrokeCoordinate);
       g2d.setColor(colorBGCoordinate);
       Point origin = this._transfer.GetShowOrign();
       int originalRadius = 3; //grid/12
       g2d.fillOval(origin.x- originalRadius,origin.y - originalRadius,2*originalRadius,2*originalRadius);
       g2d.drawLine(origin.x, origin.y, origin.x - grid * 3, origin.y);
       g2d.drawLine(origin.x, origin.y, origin.x + grid * 3, origin.y);
       g2d.drawLine(origin.x, origin.y, origin.x, origin.y - grid * 3);
       g2d.drawLine(origin.x, origin.y, origin.x, origin.y + grid * 3);

       if(!_isShowGird)
           return;

       float[] dashline1= {2f,0f,2f};
       BasicStroke basicStroke1 = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dashline1, 2f);

       g2d.setStroke(basicStroke1);
       g2d.setColor(colorBGGrid);

       int gridCountLeft = origin.x / grid;
       int gridCountDown = (this.getSize().height - origin.y) / grid;

       //System.out.println("gridCountLeft = " +gridCountLeft);

       for (int i = 0; i <= gridCountLeft; i++)
       {
           g2d.drawLine(origin.x - i * grid, 0, origin.x - i * grid, this.getSize().height); //y方向
       }
       for (int i = 1; i <= fineWidthCount - gridCountLeft+1; i++)
       {
           g2d.drawLine(origin.x + i * grid, 0, origin.x + i * grid, this.getSize().height); //y方向
       }
       for (int i = 0; i <= gridCountDown; i++)
       {
           g2d.drawLine(0, origin.y + i * grid, this.getSize().width, origin.y + i * grid);
       }
       for (int i = 1; i <= fineHeightCount - gridCountDown+1; i++)
       {
           g2d.drawLine(0, origin.y - i * grid, this.getSize().width, origin.y - i * grid);
       }
   }

   public void paintComponent(Graphics g)
   {
       g.drawImage(imgBuffer, 0, 0, this);
   }

   public void componentResized(ComponentEvent e)
   {
       this.getImgBuffer();

   }

   public void getImgBuffer()
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
     //  /**@todo Implement this java.awt.event.ComponentListener method*/
       //throw new java.lang.UnsupportedOperationException("Method componentMoved() not yet implemented.");
   }
   public void componentShown(ComponentEvent e)
   {
      // /**@todo Implement this java.awt.event.ComponentListener method*/
      // throw new java.lang.UnsupportedOperationException("Method componentShown() not yet implemented.");
   }
   public void componentHidden(ComponentEvent e)
   {
      // /**@todo Implement this java.awt.event.ComponentListener method*/
      // throw new java.lang.UnsupportedOperationException("Method componentHidden() not yet implemented.");
   }





    public void keyTyped(KeyEvent e)
    {
        System.out.print("keyTyped-->");
    }

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
               setScaleFit();
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
    }



    public void setScaleFit()
    {

        double realMaxMin[] = this._system.getDataManager().getBoundingBox();

        double realMax[] = {0,0},
                realMin[] = {0,0};
         switch(this._sectionNumber)
        {
            case XYView:
                realMax[0] = realMaxMin[3]; //maxX
                realMax[1] = realMaxMin[3]; //maxY   //after revolution origin Y is replaced by
                realMin[0] = -realMaxMin[3];//realMaxMin[0]; //minX
                realMin[1] = -realMaxMin[3];//realMaxMin[1]; //minY
                break;
            case XZView:
                realMax[0] = realMaxMin[3]; //maxX
                realMax[1] = realMaxMin[5]; //maxZ
                realMin[0] = realMaxMin[0]; //minX
                realMin[1] = realMaxMin[2]; //minZ
                break;
            case YZView:
                realMax[0] = realMaxMin[4]; //maxY
                realMax[1] = realMaxMin[5]; //maxZ
                realMin[0] = realMaxMin[1]; //minY
                realMin[1] = realMaxMin[2]; //minZ
                break;

        }



        this._transfer.setScaleFitFixedRatio(realMax, realMin, 0.85F,false);
        this._transfer.setScaleFitFixedRatio(realMax, realMin, 0.85F,false);
        this._system.getModeler().getPanel2D().GetDrawPanel().setTransfer(this._transfer);
        this.upDate();
    }



    public void SetColorForPrint()
    {
        this.colorBG = Color.white;
        this.colorBGGrid = Color.DARK_GRAY;
        this.showMode = 1;
    }

    public void SetColorForDraw()
    {
        this.colorBG = Color.DARK_GRAY;
        this.colorBGGrid = new Color(0.55f, 0.55f, 0.55f);
        this.showMode = 0;
    }




    /**
     * Show Element By  Stored ElementType  and SectionNumber
     */
    public void showElement()
    {
        this.showElementBySection(this._elementType,_sectionNumber) ;
    }

    /**
     * show Element By ElementType and SectionNumber
     *
     * @param elementType int
     * @param sectionNumber int
     */
    public void showElementBySection(int elementType, int sectionNumber)
    {
        this._sectionNumber = sectionNumber;
        this._elementType = elementType;
        if(this._sectionNumber == this.XYView)
        {
            this.showCurrentDataXY();
            return;
        }

        if(this._system.getModeler().GetAssemTree()!= null)
            this._system.getModeler().GetAssemTree().reloadSpeakerTree();


        this.setSelectElementUnselected();
        this.arrayElementShow.clear();

        ElementCap           elementCap;
        ElementTransition    elementTransition;
        ElementDiaphragm     elementDiaphragm;
        ElementSurround      elementSurround;
        ElementMagnet        elementMagnet;
        ElementMagnet        elementTopPlate;
        ElementMagnet        elementMagnetTop;
        ElementMagnet        elementMagnetOuter;
        ElementCoil          elementCoil;
        ElementYoke          elementYokeBase;
        ElementYoke          elementYokeStage1;
        ElementYoke          elementYokeStage2;
        ElementAir           elementAir;

        ElementPoint         ptStartCap;
        ElementPoint         ptEndCap;
        ElementPoint         ptEndTran;
        ElementPoint         ptEndDiaphragm;
        ElementPoint         ptStartSurround;
        ElementPoint         ptEndSurround;

        ElementPoint         ptStartMagnetTop;
        ElementPoint         ptRightUpMagnetTop;
        ElementPoint         ptEndMagnetTop;

        ElementPoint         ptStartTopPlate;
        ElementPoint         ptRightUpTopPlate;
        ElementPoint         ptEndTopPlate;

        ElementPoint         ptStartMagnet;
        ElementPoint         ptRightUpMagnet;
        ElementPoint         ptEndMagnet;
        //ElementPoint         ptLeftDownMagnet;

        ElementPoint         ptStartYokeBase;
        ElementPoint         ptMiddleDownYokeBase;
        ElementPoint         ptMiddleUpYokeBase;
        ElementPoint         ptRightUpYokeBase;
        ElementPoint         ptEndYokeBase;
        ElementPoint         ptLeftDownYokeBase;

        ElementPoint         ptStartYokeStage1;
        ElementPoint         ptMiddleDownYokeStage1;
        ElementPoint         ptMiddleUpYokeStage1;
        ElementPoint         ptRightUpYokeStage1;
        ElementPoint         ptEndYokeStage1;
      //  ElementPoint         ptLeftDownYokeStage1;

        ElementPoint         ptStartYokeStage2;
        ElementPoint         ptMiddleDownYokeStage2;
        ElementPoint         ptMiddleUpYokeStage2;
        ElementPoint         ptRightUpYokeStage2;
        ElementPoint         ptEndYokeStage2;
     //   ElementPoint         ptLeftDownYokeStage2;

        ElementPoint         ptStartMagnetOuter;
        ElementPoint         ptRightUpMagnetOuter;
      //  ElementPoint         ptEndMagnetOuter;
        ElementPoint         ptLeftDownMagnetOuter;


        ElementPoint         ptRidgeDiaphragm;
        ElementPoint         ptEndCoil;
        ElementPoint         ptMiddleInnerTransition;
        ElementPoint         ptMiddleOuterTransition;
        ElementPoint         ptCoilMiddle1;
        ElementPoint         ptCoilMiddle2;
        ElementPoint         ptCoilMiddle1Outer;
        ElementPoint         ptCoilMiddle2Outer;
        ElementPoint         ptCoilOuterEnd;


        Color       colorLine                =this._system.getDataManager().getColorLine();
        Color       colorSolid               =this._system.getDataManager().getColorSolid();

        Color       colorPt                  =this._system.getDataManager().getColorPoint();
        Color       colorCap                 =colorLine;
        Color       colorTransition          =colorLine;
        Color       colorDiaphragm           =colorLine;
        Color       colorSurround            =colorLine;

        Color       colorCoil                =colorSolid;
        Color       colorFormer              =colorSolid;

        Color       colorMagnet              =colorSolid;
        Color       colorTopPlate            =colorSolid;
        Color       colorMagnetTop           =colorSolid;
        Color       colorMagnetOuter         =colorSolid;

        Color       colorYokeBase            =colorSolid;
        Color       colorYokeStage1          =colorSolid;
        Color       colorYokeStage2          =colorSolid;


        boolean     isShowDetailCap             =false;
        boolean     isShowDetailTransition      =false;
        boolean     isShowDetailDiaphragm       =false;
        boolean     isShowDetailSurround        =false;

        boolean     isShowDetailCoil            =false;

        boolean     isShowDetailMagnet          =false;
        boolean     isShowDetailTopPlate        =false;
        boolean     isShowDetailMagnetTop       =false;
        boolean     isShowDetailMagnetOuter     =false;

        boolean     isShowDetailYokeBase        =false;
        boolean     isShowDetailYokeStage1      =false;
        boolean     isShowDetailYokeStage2      =false;


        switch(elementType)
        {
            case DefineSystemConstant.TYPE_CONE:
                colorCap                 =this._system.getDataManager().getColorCap();
                colorTransition          =this._system.getDataManager().getColorTransition();
                colorDiaphragm           =this._system.getDataManager().getColorDiaphragm();
                colorSurround            =this._system.getDataManager().getColorSurround();

                break;
            case DefineSystemConstant.TYPE_CAP:
                 colorCap                 =this._system.getDataManager().getColorCap();
                 isShowDetailCap          =true;
                break;
            case DefineSystemConstant.TYPE_TRANSITION:
                colorTransition           =this._system.getDataManager().getColorTransition();
                isShowDetailTransition   =true;
                break;
            case DefineSystemConstant.TYPE_DIAPHRAGM:
                colorDiaphragm           =this._system.getDataManager().getColorDiaphragm();
                isShowDetailDiaphragm    =true;
                break;
            case DefineSystemConstant.TYPE_SURROUND:
                colorSurround            =this._system.getDataManager().getColorSurround();
                isShowDetailSurround     = true;
                break;
            case DefineSystemConstant.TYPE_COIL:
                colorCoil                =this._system.getDataManager().getColorCoil();
                colorFormer              =this._system.getDataManager().getColorFormer();
                isShowDetailCoil         = true;
                break;

            case DefineSystemConstant.TYPE_VCM:
                colorMagnet              =this._system.getDataManager().getColorMagnet();
                colorTopPlate            =this._system.getDataManager().getColorTopPlate();
                colorMagnetTop           =this._system.getDataManager().getColorMagnetTop();
                colorMagnetOuter         =this._system.getDataManager().getColorMagnetOuter();
                colorCoil                =this._system.getDataManager().getColorCoil();
                colorFormer              =this._system.getDataManager().getColorFormer();
                colorYokeBase           = this._system.getDataManager().getColorYokeBase();
                colorYokeStage1         = this._system.getDataManager().getColorYokeStage1();
                colorYokeStage2         = this._system.getDataManager().getColorYokeStage2();
                break;
            case DefineSystemConstant.TYPE_MAGNET:
                colorMagnet              =this._system.getDataManager().getColorMagnet();
                isShowDetailMagnet        = true;
                break;
            case DefineSystemConstant.TYPE_TOPPLATE:
                colorTopPlate            =this._system.getDataManager().getColorTopPlate();
                isShowDetailTopPlate     = true;
                break;
            case DefineSystemConstant.TYPE_MAGNETTOP:
                colorMagnetTop           =this._system.getDataManager().getColorMagnetTop();
                isShowDetailMagnetTop    = true;
                break;
            case DefineSystemConstant.TYPE_MAGNETOUTER:
                colorMagnetOuter = this._system.getDataManager().getColorMagnetOuter();
                isShowDetailMagnetOuter = true;
                break;
            case DefineSystemConstant.TYPE_YOKE:
                colorYokeBase = this._system.getDataManager().getColorYokeBase();
                colorYokeStage1 = this._system.getDataManager().getColorYokeStage1();
                colorYokeStage2 = this._system.getDataManager().getColorYokeStage2();
                break;
            case DefineSystemConstant.TYPE_YOKEBASE:
                colorYokeBase = this._system.getDataManager().getColorYokeBase();
                isShowDetailYokeBase = true;
                break;
            case DefineSystemConstant.TYPE_YOKESTAGE1:
                colorYokeStage1 = this._system.getDataManager().getColorYokeStage1();
                isShowDetailYokeStage1 = true;
                break;
            case DefineSystemConstant.TYPE_YOKESTAGE2:
                colorYokeStage2 = this._system.getDataManager().getColorYokeStage2();
                isShowDetailYokeStage2 = true;
                break;
        }

       DataCap         dataCap         = _system.getDataManager().getDataCap();
       elementCap = (ElementCap)dataCap.getElement(this._sectionNumber);
       elementCap.setShowDetail(isShowDetailCap);
       elementCap.setColor(colorCap);
       ptStartCap = dataCap.getElementPointStart(this._sectionNumber);
       ptEndCap = dataCap.getElementPointEnd(this._sectionNumber);


       //Transition
       DataTransition  dataTran        = _system.getDataManager().getDataTran();

       ElementPoint ptTransitionStart = null;
       if(dataCap.getGeometryType() == DefineSystemConstant.CAP_TYPE_ROUNDCUT && this._sectionNumber == this.YZView)
      {
          ptTransitionStart = dataCap.getCapPtYZRoundCutL();
      }
      else
          ptTransitionStart = ptEndCap;


       dataTran.setElementPointStart(ptTransitionStart,this._sectionNumber);
       elementTransition = (ElementTransition)dataTran.getElement(this._sectionNumber);
       elementTransition.setShowDetail(isShowDetailTransition);
       elementTransition.setColor(colorTransition);
       ptEndTran = dataTran.getElementPointEnd(this._sectionNumber);
       ptMiddleInnerTransition = dataTran.getElementPointMiddleInner(this._sectionNumber);
       ptMiddleOuterTransition = dataTran.getElementPointMiddleOuter(this._sectionNumber);

       //Diaphragm
       DataDiaphragm   dataDiaphragm   = _system.getDataManager().getDataDiaphragm();
       dataDiaphragm.setElementPointStart(ptEndTran,this._sectionNumber);
       elementDiaphragm = (ElementDiaphragm) dataDiaphragm.getElement(this._sectionNumber);
       elementDiaphragm.setShowDetail(isShowDetailDiaphragm);
       elementDiaphragm.setColor(colorDiaphragm);
       ptEndDiaphragm = dataDiaphragm.getElementPointEnd(this._sectionNumber);
       ptRidgeDiaphragm = dataDiaphragm.getElementPointRidge(this._sectionNumber);

       if(dataDiaphragm.getGeometryType() == DefineSystemConstant.DIAPHRAGM_TYPE2 && this._sectionNumber == DefineSystemConstant.XZView)
           this.arrayElementShow.add(ptRidgeDiaphragm);

       this.arrayElementShow.add(elementCap);
       this.arrayElementShow.add(elementTransition);


       //Surround
       DataSurround dataSurround = _system.getDataManager().getDataSurround();

       //if (this._isShowDiaphragm)
       if(dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
       {
           elementSurround = (ElementSurround) dataSurround.getElement(this._sectionNumber);
           elementSurround.moveEnd(0, ptEndDiaphragm);
           dataSurround.setElementPointStart(ptEndDiaphragm,this._sectionNumber);
           elementSurround = (ElementSurround) dataSurround.getElement(this._sectionNumber);
           this.arrayElementShow.add(elementDiaphragm);
       }
       else
       {
           elementSurround = (ElementSurround) dataSurround.getElement(this._sectionNumber);
           elementSurround.moveEnd(0, ptEndTran);
           dataSurround.setElementPointStart(ptEndTran,this._sectionNumber);
           elementSurround = (ElementSurround) dataSurround.getElement(this._sectionNumber);
       }

       ptStartSurround = dataSurround.getElementPointStart(this._sectionNumber);
       elementSurround.setShowDetail(isShowDetailSurround);
       elementSurround.setColor(colorSurround);
       ptEndSurround = dataSurround.getElementPointEnd(this._sectionNumber);
       this.arrayElementShow.add(elementSurround);

       //Coil
       DataCoil  dataCoil        = _system.getDataManager().getDataCoil();
       dataCoil.setElementPointStart(ptMiddleInnerTransition, this._sectionNumber);
       dataCoil.setElementPointOuterStart(ptMiddleOuterTransition, this._sectionNumber);
       elementCoil = (ElementCoil)dataCoil.getElement(this._sectionNumber);
       elementCoil.setShowDetail(isShowDetailCoil);
       elementCoil.setColor(colorCoil,colorFormer);

       ptEndCoil =     dataCoil.getElementPointEnd(this._sectionNumber);
       ptCoilMiddle1 = dataCoil.getElementPointCoilStart(this._sectionNumber);
       ptCoilMiddle2 = dataCoil.getElementPointCoilEnd(this._sectionNumber);
       ptCoilMiddle1Outer = dataCoil.getElementPointCoilOuterStart(this._sectionNumber);
       ptCoilMiddle2Outer = dataCoil.getElementPointCoilOuterEnd(this._sectionNumber);
       ptCoilOuterEnd = dataCoil.getElementPointOuterEnd(this._sectionNumber);


       this.arrayElementShow.add(elementCoil);

       //Yoke
       DataYokeBase dataYokeBase = _system.getDataManager().getDataYokeBase();
       elementYokeBase = (ElementYoke) dataYokeBase.getElement(this._sectionNumber);
       ptStartYokeBase = dataYokeBase.getElementPointStart(this._sectionNumber);
       ptMiddleDownYokeBase = dataYokeBase.getElementPointMiddleDown(this._sectionNumber);
       ptMiddleUpYokeBase = dataYokeBase.getElementPointMiddleUp(this._sectionNumber); //
       ptRightUpYokeBase = dataYokeBase.getElementPointRightUp(this._sectionNumber);
       ptEndYokeBase = dataYokeBase.getElementPointEnd(this._sectionNumber);
       ptLeftDownYokeBase = dataYokeBase.getElementPointLeftDown(this._sectionNumber);
       elementYokeBase.setColor(colorYokeBase);

       DataYokeStage1 dataYokeStage1 = _system.getDataManager().getDataYokeStage1();
       dataYokeStage1.setElementPointLeftDown(ptMiddleUpYokeBase, this._sectionNumber);
       elementYokeStage1 = (ElementYoke) dataYokeStage1.getElement(this._sectionNumber);
       ptStartYokeStage1 = dataYokeStage1.getElementPointStart(this._sectionNumber);
       ptMiddleDownYokeStage1 = dataYokeStage1.getElementPointMiddleDown(this._sectionNumber);
       ptMiddleUpYokeStage1 = dataYokeStage1.getElementPointMiddleUp(this._sectionNumber);
       ptRightUpYokeStage1 = dataYokeStage1.getElementPointRightUp(this._sectionNumber);
       ptEndYokeStage1 = dataYokeStage1.getElementPointEnd(this._sectionNumber);
       elementYokeStage1.setColor(colorYokeStage1);

       DataYokeStage2 dataYokeStage2 = _system.getDataManager().getDataYokeStage2();
       dataYokeStage2.setElementPointLeftDown(ptMiddleUpYokeStage1, this._sectionNumber);
       elementYokeStage2 = (ElementYoke) dataYokeStage2.getElement(this._sectionNumber);
       ptStartYokeStage2 = dataYokeStage2.getElementPointStart(this._sectionNumber);
       ptMiddleDownYokeStage2 = dataYokeStage2.getElementPointMiddleDown(this._sectionNumber);
       ptMiddleUpYokeStage2 = dataYokeStage2.getElementPointMiddleUp(this._sectionNumber);
       ptRightUpYokeStage2 = dataYokeStage2.getElementPointRightUp(this._sectionNumber);
       ptEndYokeStage2 = dataYokeStage2.getElementPointEnd(this._sectionNumber);
       elementYokeStage2.setColor(colorYokeStage2);

       //Magnet
       DataMagnet dataMagnet = _system.getDataManager().getDataMagnet();

       dataMagnet.setElementPointLeftDown(ptStartYokeBase,this._sectionNumber);
       elementMagnet = (ElementMagnet) dataMagnet.getElement(this._sectionNumber);
       ptStartMagnet = dataMagnet.getElementPointStart(this._sectionNumber);
       ptEndMagnet = dataMagnet.getElementPointEnd(this._sectionNumber);
       ptRightUpMagnet = dataMagnet.getElementPointRightUp(this._sectionNumber);

       elementMagnet.setColor(colorMagnet);
       elementMagnet.setShowDetail(isShowDetailMagnet);

       //Top Plate
       DataTopPlate dataTopPlate = _system.getDataManager().getDataTopPlate();


       dataTopPlate.setElementPointLeftDown(ptStartMagnet,this._sectionNumber);
       elementTopPlate = (ElementTopPlate) dataTopPlate.getElement(this._sectionNumber);
       ptStartTopPlate = dataTopPlate.getElementPointStart(this._sectionNumber);
       ptEndTopPlate = dataTopPlate.getElementPointEnd(this._sectionNumber);
       ptRightUpTopPlate  = dataTopPlate.getElementPointRightUp(this._sectionNumber);
       ptStartMagnet = dataTopPlate.getElementPointLeftDown(this._sectionNumber);
       dataTopPlate.setElementPointLeftDown(ptStartMagnet,this._sectionNumber);

       elementTopPlate.setColor(colorTopPlate);
       elementTopPlate.setShowDetail(isShowDetailTopPlate);


        //Magnet Top
        DataMagnetTop dataMagnetTop = _system.getDataManager().getDataMagnetTop();


        dataMagnetTop.setElementPointLeftDown(ptStartTopPlate,this._sectionNumber);
        elementMagnetTop = (ElementMagnet) dataMagnetTop.getElement(this._sectionNumber);
        ptStartMagnetTop = dataMagnetTop.getElementPointStart(this._sectionNumber);
        ptEndMagnetTop = dataMagnetTop.getElementPointEnd(this._sectionNumber);
        ptRightUpMagnetTop  = dataMagnetTop.getElementPointRightUp(this._sectionNumber);


        elementMagnetTop.setColor(colorMagnetTop);
        elementMagnetTop.setShowDetail(isShowDetailMagnetTop);

        //Magnet Outer
        DataMagnetOuter dataMagnetOuter = _system.getDataManager().getDataMagnetOuter();

        dataMagnetOuter.setElementPointEnd(ptMiddleDownYokeStage1, this._sectionNumber);
        elementMagnetOuter = (ElementMagnet) dataMagnetOuter.getElement(this._sectionNumber);

        ptStartMagnetOuter = dataMagnetOuter.getElementPointStart(this._sectionNumber);
        ptLeftDownMagnetOuter = dataMagnetOuter.getElementPointLeftDown(this._sectionNumber);
        ptRightUpMagnetOuter = dataMagnetOuter.getElementPointRightUp(this._sectionNumber);

        elementMagnetOuter.setColor(colorMagnetOuter);
        elementMagnetOuter.setShowDetail(isShowDetailMagnetOuter);





        this.arrayElementShow.add(elementYokeBase);

        if (dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
           this.arrayElementShow.add(elementYokeStage1);
        if (dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
           this.arrayElementShow.add(elementYokeStage2);

        this.arrayElementShow.add(elementMagnet);
        this.arrayElementShow.add(elementTopPlate);

        if (dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
           this.arrayElementShow.add(elementMagnetTop);
        if (dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE)
           this.arrayElementShow.add(elementMagnetOuter);

       //Air
       DataAir dataAir = _system.getDataManager().getDataAir();
       elementAir = (ElementAir) dataAir.getElement(this._sectionNumber);
       this.arrayElementShow.add(elementAir);



        this.arrayElementShow.add( ptStartCap           );
        this.arrayElementShow.add( ptEndCap             );
        this.arrayElementShow.add( ptEndTran            );
        this.arrayElementShow.add( ptStartSurround      );
        this.arrayElementShow.add( ptEndSurround        );

        this.arrayElementShow.add( ptStartYokeBase      );
        this.arrayElementShow.add( ptMiddleDownYokeBase );
        this.arrayElementShow.add( ptMiddleUpYokeBase   );
        this.arrayElementShow.add( ptRightUpYokeBase    );
        this.arrayElementShow.add( ptEndYokeBase        );
        this.arrayElementShow.add( ptLeftDownYokeBase   );

        if (dataYokeStage1.getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE)
        {
           this.arrayElementShow.add(ptStartYokeStage1);
           this.arrayElementShow.add(ptMiddleDownYokeStage1);
           this.arrayElementShow.add(ptMiddleUpYokeStage1);
           this.arrayElementShow.add(ptRightUpYokeStage1);
           this.arrayElementShow.add(ptEndYokeStage1);
        }
        if (dataYokeStage2.getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE)
        {
           this.arrayElementShow.add(ptStartYokeStage2);
           this.arrayElementShow.add(ptMiddleDownYokeStage2);
           this.arrayElementShow.add(ptMiddleUpYokeStage2);
           this.arrayElementShow.add(ptRightUpYokeStage2);
           this.arrayElementShow.add(ptEndYokeStage2);
        }
        this.arrayElementShow.add(ptStartMagnet);
        this.arrayElementShow.add(ptEndMagnet);
        this.arrayElementShow.add(ptRightUpMagnet);
        //this.arrayElementShow.add(ptLeftDownMagnet);

        this.arrayElementShow.add(ptStartTopPlate);
        this.arrayElementShow.add(ptEndTopPlate);
        this.arrayElementShow.add(ptRightUpTopPlate);

        if(dataMagnetTop.getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE)
        {
           this.arrayElementShow.add(ptStartMagnetTop);
           this.arrayElementShow.add(ptEndMagnetTop);
           this.arrayElementShow.add(ptRightUpMagnetTop);
        }

        if (dataMagnetOuter.getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE)
        {
           this.arrayElementShow.add(ptStartMagnetOuter);
           this.arrayElementShow.add(ptLeftDownMagnetOuter);
           this.arrayElementShow.add(ptRightUpMagnetOuter);
        }

        if (dataCoil.getGeometryType() == DefineSystemConstant.COIL_TYPE2)
        {
           this.arrayElementShow.add(ptCoilMiddle1);
           this.arrayElementShow.add(ptCoilMiddle2);
           this.arrayElementShow.add(ptCoilMiddle1Outer);
           this.arrayElementShow.add(ptCoilMiddle2Outer);
        }
        this.arrayElementShow.add(ptCoilOuterEnd);
        this.arrayElementShow.add(ptEndCoil);
        this.arrayElementShow.add(ptMiddleInnerTransition);
        this.arrayElementShow.add(ptMiddleOuterTransition);
        this.arrayElementShow.add(ptCoilOuterEnd);


        ptStartCap.setColor(colorPt);
        ptEndCap.setColor(colorPt);
        ptEndTran.setColor(colorPt);
        ptEndDiaphragm.setColor(colorPt);
        ptStartSurround.setColor(colorPt);
        ptEndSurround.setColor(colorPt);

        ptStartMagnetTop.setColor(colorPt);
        ptRightUpMagnetTop.setColor(colorPt);
        ptEndMagnetTop.setColor(colorPt);

        ptStartTopPlate.setColor(colorPt);
        ptRightUpTopPlate.setColor(colorPt);
        ptEndTopPlate.setColor(colorPt);

        ptStartMagnet.setColor(colorPt);
        ptRightUpMagnet.setColor(colorPt);
        ptEndMagnet.setColor(colorPt);

        ptStartYokeBase.setColor(colorPt);
        ptMiddleDownYokeBase.setColor(colorPt);
        ptMiddleUpYokeBase.setColor(colorPt);
        ptRightUpYokeBase.setColor(colorPt);
        ptEndYokeBase.setColor(colorPt);
        ptLeftDownYokeBase.setColor(colorPt);

        ptStartYokeStage1.setColor(colorPt);
        ptMiddleDownYokeStage1.setColor(colorPt);
        ptMiddleUpYokeStage1.setColor(colorPt);
        ptRightUpYokeStage1.setColor(colorPt);
        ptEndYokeStage1.setColor(colorPt);

        ptStartYokeStage2.setColor(colorPt);
        ptMiddleDownYokeStage2.setColor(colorPt);
        ptMiddleUpYokeStage2.setColor(colorPt);
        ptRightUpYokeStage2.setColor(colorPt);
        ptEndYokeStage2.setColor(colorPt);

        ptStartMagnetOuter.setColor(colorPt);
        ptRightUpMagnetOuter.setColor(colorPt);
        ptLeftDownMagnetOuter.setColor(colorPt);


        ptRidgeDiaphragm.setColor(colorPt);
        ptEndCoil.setColor(colorPt);
        ptMiddleInnerTransition.setColor(colorPt);
        ptMiddleOuterTransition.setColor(colorPt);
        ptCoilMiddle1.setColor(colorPt);
        ptCoilMiddle2.setColor(colorPt);
        ptCoilMiddle1Outer.setColor(colorPt);
        ptCoilMiddle2Outer.setColor(colorPt);
        ptCoilOuterEnd.setColor(colorPt);

        /***
        *  linkAllElement
        */
        elementCap              .removeAllLinkedElement();
        elementTransition       .removeAllLinkedElement();
        elementDiaphragm        .removeAllLinkedElement();
        elementSurround         .removeAllLinkedElement();

        elementMagnet           .removeAllLinkedElement();
        elementTopPlate         .removeAllLinkedElement();
        elementMagnetTop        .removeAllLinkedElement();
        elementMagnetOuter      .removeAllLinkedElement();
        elementCoil             .removeAllLinkedElement();
        elementYokeBase         .removeAllLinkedElement();
        elementYokeStage1       .removeAllLinkedElement();
        elementYokeStage2       .removeAllLinkedElement();

        ptStartCap              .removeAllLinkedElement();
        ptEndCap                .removeAllLinkedElement();

        ptEndTran               .removeAllLinkedElement();
        ptMiddleInnerTransition .removeAllLinkedElement();
        ptMiddleOuterTransition .removeAllLinkedElement();

        ptEndDiaphragm          .removeAllLinkedElement();
        ptRidgeDiaphragm        .removeAllLinkedElement();

        ptStartSurround         .removeAllLinkedElement();
        ptEndSurround           .removeAllLinkedElement();

        ptStartMagnetTop        .removeAllLinkedElement();
        ptRightUpMagnetTop      .removeAllLinkedElement();
        ptEndMagnetTop          .removeAllLinkedElement();

        ptStartTopPlate         .removeAllLinkedElement();
        ptRightUpTopPlate       .removeAllLinkedElement();
        ptEndTopPlate           .removeAllLinkedElement();

        ptStartMagnet           .removeAllLinkedElement();
        ptRightUpMagnet         .removeAllLinkedElement();
        ptEndMagnet             .removeAllLinkedElement();

        ptStartYokeBase         .removeAllLinkedElement();
        ptMiddleDownYokeBase    .removeAllLinkedElement();
        ptMiddleUpYokeBase      .removeAllLinkedElement();
        ptRightUpYokeBase       .removeAllLinkedElement();
        ptEndYokeBase           .removeAllLinkedElement();
        ptLeftDownYokeBase      .removeAllLinkedElement();

        ptStartYokeStage1       .removeAllLinkedElement();
        ptMiddleDownYokeStage1  .removeAllLinkedElement();
        ptMiddleUpYokeStage1    .removeAllLinkedElement();
        ptRightUpYokeStage1     .removeAllLinkedElement();
        ptEndYokeStage1         .removeAllLinkedElement();

        ptStartYokeStage2       .removeAllLinkedElement();
        ptMiddleDownYokeStage2  .removeAllLinkedElement();
        ptMiddleUpYokeStage2    .removeAllLinkedElement();
        ptRightUpYokeStage2     .removeAllLinkedElement();
        ptEndYokeStage2         .removeAllLinkedElement();

        ptStartMagnetOuter      .removeAllLinkedElement();
        ptRightUpMagnetOuter    .removeAllLinkedElement();
        ptLeftDownMagnetOuter   .removeAllLinkedElement();

        ptEndCoil               .removeAllLinkedElement();
        ptCoilMiddle1           .removeAllLinkedElement();
        ptCoilMiddle2           .removeAllLinkedElement();
        ptCoilMiddle1Outer      .removeAllLinkedElement();
        ptCoilMiddle2Outer      .removeAllLinkedElement();
        ptCoilOuterEnd          .removeAllLinkedElement();

        elementCap              .addLinkedElement(elementTransition);

        elementTransition       .addLinkedElement(elementDiaphragm);
        elementTransition       .addLinkedElement(elementCoil);

        elementDiaphragm        .addLinkedElement(elementSurround);

        elementMagnetTop        .addLinkedElement(elementTopPlate);

        elementTopPlate         .addLinkedElement(elementMagnet);

        elementMagnet           .addLinkedElement(elementYokeBase);

        elementYokeBase         .addLinkedElement(elementYokeStage1);

        elementYokeStage1       .addLinkedElement(elementYokeStage2);


        ptStartCap              .addLinkedElement(elementCap);
        ptEndCap                .addLinkedElement(elementCap);

        ptEndTran               .addLinkedElement(elementTransition);
        ptMiddleInnerTransition .addLinkedElement(elementTransition);
        ptMiddleOuterTransition .addLinkedElement(elementTransition);

        ptEndDiaphragm          .addLinkedElement(elementDiaphragm);
        ptRidgeDiaphragm        .addLinkedElement(elementDiaphragm);

        ptStartSurround         .addLinkedElement(elementSurround);
        ptEndSurround           .addLinkedElement(elementSurround);

        ptStartMagnetTop        .addLinkedElement(elementMagnetTop);
        ptRightUpMagnetTop      .addLinkedElement(elementMagnetTop);
        ptEndMagnetTop          .addLinkedElement(elementMagnetTop);

        ptStartTopPlate         .addLinkedElement(elementTopPlate);
        ptRightUpTopPlate       .addLinkedElement(elementTopPlate);
        ptEndTopPlate           .addLinkedElement(elementTopPlate);

        ptStartMagnet           .addLinkedElement(elementMagnet);
        ptRightUpMagnet         .addLinkedElement(elementMagnet);
        ptEndMagnet             .addLinkedElement(elementMagnet);

        ptStartYokeBase         .addLinkedElement(elementYokeBase);
        ptMiddleDownYokeBase    .addLinkedElement(elementYokeBase);
        ptMiddleUpYokeBase      .addLinkedElement(elementYokeBase);
        ptRightUpYokeBase       .addLinkedElement(elementYokeBase);
        ptEndYokeBase           .addLinkedElement(elementYokeBase);
        ptLeftDownYokeBase      .addLinkedElement(elementYokeBase);

        ptStartYokeStage1       .addLinkedElement(elementYokeStage1);
        ptMiddleDownYokeStage1  .addLinkedElement(elementYokeStage1);
        ptMiddleUpYokeStage1    .addLinkedElement(elementYokeStage1);
        ptRightUpYokeStage1     .addLinkedElement(elementYokeStage1);
        ptEndYokeStage1         .addLinkedElement(elementYokeStage1);

        ptStartYokeStage2       .addLinkedElement(elementYokeStage2);
        ptMiddleDownYokeStage2  .addLinkedElement(elementYokeStage2);
        ptMiddleUpYokeStage2    .addLinkedElement(elementYokeStage2);
        ptRightUpYokeStage2     .addLinkedElement(elementYokeStage2);
        ptEndYokeStage2         .addLinkedElement(elementYokeStage2);


        ptStartMagnetOuter      .addLinkedElement(elementMagnetOuter);
        ptRightUpMagnetOuter    .addLinkedElement(elementMagnetOuter);
        ptLeftDownMagnetOuter   .addLinkedElement(elementMagnetOuter);

        ptEndCoil               .addLinkedElement(elementCoil);
        ptCoilMiddle1           .addLinkedElement(elementCoil);
        ptCoilMiddle2           .addLinkedElement(elementCoil);
        ptCoilMiddle1Outer      .addLinkedElement(elementCoil);
        ptCoilMiddle2Outer      .addLinkedElement(elementCoil);
        ptCoilOuterEnd          .addLinkedElement(elementCoil);



       this.upDate();

    }

    public void showAllDataXY()
    {
        this._sectionNumber = this.XYView;
        this.arrayElementShow.clear();
        this.arrayElementShow.add(this._system.getDataManager().getDataCap().getElement(this.XYView));
        this.arrayElementShow.add(this._system.getDataManager().getDataTran().getElement(this.XYView));
        //if (this._isShowDiaphragm)
        if(this._system.getDataManager().getDataDiaphragm().getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
        {
            this.arrayElementShow.add(this._system.getDataManager().getDataDiaphragm().getElement(this.XYView));
        }
        this.arrayElementShow.add(this._system.getDataManager().getDataSurround().getElement(this.XYView));
        this.arrayElementShow.add(this._system.getDataManager().getDataCoil().getElement(this.XYView));

        this.upDate();
    }



    public void showCurrentDataXY()
    {
        this.arrayElementShow.clear();
        this.arrayElementShow.add(this._system.getDataManager().getCurrentData().getElement(this.XYView));
        this.upDate();
    }



    private void popupMenu(ElementBase element, Component parent, int positionX, int positionY )
    {
        this._system.getModeler().GetAssemTree().setSelectedNode(this._elementType, this._sectionNumber);
        this._system.getDataManager().setCurrentDataType(this._elementType);
        if (this._sectionNumber == XYView)
        {
            if (this._elementType != sdt.define.DefineSystemConstant.TYPE_SURROUND &&
                this._elementType != sdt.define.DefineSystemConstant.TYPE_CAP &&
                this._elementType != sdt.define.DefineSystemConstant.TYPE_DIAPHRAGM &&
                this._elementType != sdt.define.DefineSystemConstant.TYPE_TRANSITION &&
                this._elementType != sdt.define.DefineSystemConstant.TYPE_COIL
                    )
                return;

            Component[] menuItems = (Component[])this._popupMenuSktTop.getComponents();

            if (this._elementType != sdt.define.DefineSystemConstant.TYPE_SURROUND)
                menuItems[1].setEnabled(false);
            else
                menuItems[1].setEnabled(true);
            this._popupMenuSktTop.show(parent, positionX, positionY);
            return;
        }

        int elementType = element.getElementType();
        if (elementType != this._elementType)
            return;
        JMenu menuType = this._system.getMenuPopup().getMenuFromName("TypeSelection");
        switch (elementType)
        {
            case sdt.define.DefineSystemConstant.TYPE_CAP:
                JCheckBoxMenuItem chbMenuItemCapRegular = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCapRegular");
                JCheckBoxMenuItem chbMenuItemCapCapsule = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCapCapsule");
                JCheckBoxMenuItem chbMenuItemCapRoundCut = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCapRoundCut");
                menuType.removeAll();
                menuType.add(chbMenuItemCapRegular);
                menuType.add(chbMenuItemCapCapsule);
                menuType.add(chbMenuItemCapRoundCut);
                this._popupMenuSktSectionCap.add(menuType);
                //switch (this._system.getModeler().getCapStatus())
                switch (this._system.getDataManager().getDataCap().getGeometryType())
                {
                    case DefineSystemConstant.CAP_TYPE_REGULAR:
                        chbMenuItemCapRegular.setSelected(true);
                        chbMenuItemCapCapsule.setSelected(false);
                        chbMenuItemCapRoundCut.setSelected(false);
                        break;
                    case DefineSystemConstant.CAP_TYPE_CAPSULE:
                        chbMenuItemCapRegular.setSelected(false);
                        chbMenuItemCapCapsule.setSelected(true);
                        chbMenuItemCapRoundCut.setSelected(false);
                        break;
                    case DefineSystemConstant.CAP_TYPE_ROUNDCUT:
                        chbMenuItemCapRegular.setSelected(false);
                        chbMenuItemCapCapsule.setSelected(false);
                        chbMenuItemCapRoundCut.setSelected(true);
                        break;
                }
                this._popupMenuSktSectionCap.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_TRANSITION:
                this._popupMenuSktSectionTransition.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_DIAPHRAGM:


                JCheckBoxMenuItem chbMenuItemD1 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxDiaphragmType1");
                JCheckBoxMenuItem chbMenuItemD2 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxDiaphragmType2");
                JCheckBoxMenuItem chbMenuItemD3 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxDiaphragmNone");

                menuType.removeAll();
                menuType.add(chbMenuItemD1);
                menuType.add(chbMenuItemD2);
                menuType.add(chbMenuItemD3);
                this._popupMenuSktSectionDiaphragm.add(menuType);

                //switch (this._system.getModeler().getDiaphragmStatus())
                switch (this._system.getDataManager().getDataDiaphragm().getGeometryType())
                {
                    case DefineSystemConstant.DIAPHRAGM_TYPE1:
                        chbMenuItemD1.setSelected( true);
                        chbMenuItemD2.setSelected( false);
                        chbMenuItemD3.setSelected( false);
                        break;
                    case DefineSystemConstant.DIAPHRAGM_TYPE2:
                        chbMenuItemD1.setSelected( false);
                        chbMenuItemD2.setSelected( true);
                        chbMenuItemD3.setSelected( false);
                        break;
                    case DefineSystemConstant.DIAPHRAGM_NONE:
                        chbMenuItemD1.setSelected(false);
                        chbMenuItemD2.setSelected( false);
                        chbMenuItemD3.setSelected( true);
                        break;
                }
                this._popupMenuSktSectionDiaphragm.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_SURROUND:

                JCheckBoxMenuItem chbMenuItem1 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxSurroundSingleArc");
                JCheckBoxMenuItem chbMenuItem2 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxSurroundDoubleArc");

                menuType.removeAll();
                menuType.add(chbMenuItem1);
                menuType.add(chbMenuItem2);
                this._popupMenuSktSectionSurround.add(menuType);

                //switch (this._system.getModeler().getSurroundStatus())
                switch(this._system.getDataManager().getDataSurround().getGeometryType())
                {
                    case DefineSystemConstant.SURROUND_DOUBLE_ARC:
                        chbMenuItem1.setSelected(false);
                        chbMenuItem2.setSelected(true);
                        break;
                    case DefineSystemConstant.SURROUND_SINGLE_ARC:
                        chbMenuItem1.setSelected(true);
                        chbMenuItem2.setSelected(false);

                        break;
                }
                this._popupMenuSktSectionSurround.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_COIL:

                JCheckBoxMenuItem chbMenuItemC1 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCoilType1");
                JCheckBoxMenuItem chbMenuItemC2 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCoilType2");

                menuType.removeAll();
                menuType.add(chbMenuItemC1);
                menuType.add(chbMenuItemC2);
                this._popupMenuSktSectionCoil.add(menuType);
                //switch (this._system.getModeler().getCoilStatus())

                switch(this._system.getDataManager().getDataCoil().getGeometryType())
                {
                    case DefineSystemConstant.COIL_TYPE1:
                        chbMenuItemC1.setSelected(true);
                        chbMenuItemC2.setSelected(false);
                        break;
                    case DefineSystemConstant.COIL_TYPE2:
                        chbMenuItemC1.setSelected(false);
                        chbMenuItemC2.setSelected( true);
                        break;
                }
                this._popupMenuSktSectionCoil.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_YOKEBASE:
                this._popupMenuSktSectionYokeBase.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_YOKESTAGE1:

                JCheckBoxMenuItem chbMenuItemY1 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxStage1");
                JCheckBoxMenuItem chbMenuItemY2 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxStage2");

                menuType.removeAll();
                menuType.add(chbMenuItemY1);
                menuType.add(chbMenuItemY2);
                this._popupMenuSktSectionYokeStage1.add(menuType);


                //switch (this._system.getModeler().getYokeStage1Status())
                switch(this._system.getDataManager().getDataYokeStage1().getGeometryType())
                {
                    case DefineSystemConstant.YOKESTAGE1_TYPE1:
                        chbMenuItemY1.setSelected(true);
                        break;
                    case DefineSystemConstant.YOKESTAGE1_NONE:
                        chbMenuItemY1.setSelected(false);
                        break;
                }
                //switch (this._system.getModeler().getYokeStage2Status())
                switch(this._system.getDataManager().getDataYokeStage2().getGeometryType())
                {
                    case DefineSystemConstant.YOKESTAGE2_TYPE1:
                        chbMenuItemY2.setSelected(true);
                        break;
                    case DefineSystemConstant.YOKESTAGE2_NONE:
                        chbMenuItemY2.setSelected(false);
                        break;
                }

                this._popupMenuSktSectionYokeStage1.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_YOKESTAGE2:
                chbMenuItemY1 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxStage1");
                chbMenuItemY2 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxStage2");

                menuType.removeAll();
                menuType.add(chbMenuItemY1);
                menuType.add(chbMenuItemY2);
                this._popupMenuSktSectionYokeStage2.add(menuType);

                //switch (this._system.getModeler().getYokeStage1Status())
                switch(this._system.getDataManager().getDataYokeStage1().getGeometryType())
                {
                    case DefineSystemConstant.YOKESTAGE1_TYPE1:
                        chbMenuItemY1.setSelected(true);
                        break;
                    case DefineSystemConstant.YOKESTAGE1_NONE:
                        chbMenuItemY1.setSelected(false);
                        break;
                }
                //switch (this._system.getModeler().getYokeStage2Status())
                switch(this._system.getDataManager().getDataYokeStage2().getGeometryType())
                {
                    case DefineSystemConstant.YOKESTAGE2_TYPE1:
                        chbMenuItemY2.setSelected(true);
                        break;
                    case DefineSystemConstant.YOKESTAGE2_NONE:
                        chbMenuItemY2.setSelected(false);
                        break;
                }


                this._popupMenuSktSectionYokeStage2.show(parent, positionX, positionY);
                break;

            case sdt.define.DefineSystemConstant.TYPE_MAGNET:
                this._popupMenuSktSectionMagnet.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_MAGNETTOP:
                JCheckBoxMenuItem chbMenuItemMagnetTop = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxMagnetTop");
                menuType.removeAll();
                menuType.add(chbMenuItemMagnetTop );
                this._popupMenuSktSectionMagnetTop.add(menuType);

                //switch (this._system.getModeler().getMagnetTopStatus())
                switch (this._system.getDataManager().getDataMagnet().getGeometryType())
                {
                    case DefineSystemConstant.MAGNETTOP_TYPE1:
                        chbMenuItemMagnetTop.setSelected(true);
                        break;
                    case DefineSystemConstant.MAGNETTOP_NONE:
                        chbMenuItemMagnetTop.setSelected(false);
                        break;
                }

                this._popupMenuSktSectionMagnetTop.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_MAGNETOUTER:
                JCheckBoxMenuItem chbMenuItemMagnetOuter = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxMagnetOuter");
                menuType.removeAll();
                menuType.add(chbMenuItemMagnetOuter);
                this._popupMenuSktSectionMagnetOuter.add(menuType);

                //switch (this._system.getModeler().getMagnetOutStatus())
                switch (this._system.getDataManager().getDataMagnetOuter().getGeometryType())
                {
                    case DefineSystemConstant.MAGNETOUTER_TYPE1:
                        chbMenuItemMagnetOuter.setSelected(true);
                        break;
                    case DefineSystemConstant.MAGNETOUTER_NONE:
                        chbMenuItemMagnetOuter.setSelected(false);
                        break;
                }


                this._popupMenuSktSectionMagnetOuter.show(parent, positionX, positionY);
                break;
            case sdt.define.DefineSystemConstant.TYPE_TOPPLATE:
                this._popupMenuSktSectionTopPlate.show(parent, positionX, positionY);
                break;
        }
    }


    public ElementBase selectElement(ObjectPoint point)
    {
        if(point == null)
            return null;

        for (int i = 0; i < this.arrayElementShow.size(); i++)
        {
            ElementBase element = (ElementBase)this.arrayElementShow.get(i);
            if (element.isPtInBoundary(point) == true)
            {
                return element;
            }
        }
        return null;
    }
    public void setBackgroundPaint()
    {
        Color colorBGUp = this._system.getDataManager().getColorBackgroundUp();
        Color colorBGDown = this._system.getDataManager().getColorBackgroundDown();
        paintBG  = new GradientPaint(0.1f,0.0f,colorBGUp, 0.1f,500f,colorBGDown);
    }

    public void setSelectElementUnselected()
    {
        if (this._selectedElement != null) // back to the element's color
        {
            this.setCursor(null);
            this._selectedElement.setColor(this.oldColor);
            this.upDate();
            this._selectedElement =null;
        }
    }


}


