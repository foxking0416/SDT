package sdt.panel.drawpanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import javax.swing.*;

import sdt.define.*;
import sdt.framework.*;
import sdt.geometry.*;
import sdt.panel.*;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SDT_DrawPanelOuter extends SDT_PanelResult implements ActionListener,DefineSystemConstant, MouseListener, ComponentListener
{
    private SDT_System         _system;
    private SDT_DrawTransfer  theTransfer;
    private int               theDrawerHeight;
    private int               theDrawerWidth;
    private Color             colorCalibration;
    private Color             colorBackground;
    private Font              fontCali = this.getFont().deriveFont(Font.PLAIN, 10f);//313

    private SDT_DrawPanel2D    theDrawPanel;
    private int              hOuterDistance = 100;
    private int              vOuterDistance = 100;

    private int              theContactType;
    private SDT_DrawTransfer theSecondTransfer;


    protected   Image               imgBuffer;
    protected   Graphics            gBuffer;


    private ImageIcon         imageClearAll;
    private ImageIcon         imageClearAllDown;
    private ImageIcon         imageDraw;
    private ImageIcon         imageModify;
    private GradientPaint     paintBG;
    private ArrayList         arrayClibrationLabel;
 //   public IDT
    public SDT_DrawPanel2D GetDrawPanel()  {return this.theDrawPanel;}



    /**
     * IDT_drawpanel_outer
     */
    public SDT_DrawPanelOuter()
    {
    }


    public SDT_DrawPanelOuter(SDT_System system)
    {
        this._system = system;



        Dimension dimFrame = _system.getFrame().getSplitRightDimension();

        this.setPreferredSize(dimFrame) ;
        this.colorCalibration = Color.WHITE;

        theDrawPanel = new SDT_DrawPanel2D(system);
        this.setBackgroundPaint();
        this.setLayout(null);

        colorBackground = new Color(85, 85, 85);
        this.setBackground(new Color(85, 85, 85));
        theDrawPanel.setBounds(50, 50, theDrawerWidth, theDrawerHeight);
        this.add(theDrawPanel);

        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    private void jbInit() throws Exception
    {



        this.AddActionListener();
        this.addComponentListener(this) ;
    }







    protected void SetCalibration()
    {
        if(arrayClibrationLabel == null)
         arrayClibrationLabel = new ArrayList();

        for(int i = 0 ; i <arrayClibrationLabel.size();i++)
        {
            JLabel label = (JLabel)arrayClibrationLabel.get(i);
            this.remove(label);
        }
        int countY = 0,countX = 0;
        int gird =(int)this.theTransfer.GetWidthScale()/2;
        double time = 2;
        countX = theDrawerWidth / gird;
        countY = theDrawerHeight / gird;


        int leftGirdCount = theTransfer.GetShowOrign().x / gird;
        int DownGirdCount = (theDrawerHeight - theTransfer.GetShowOrign().y) / gird;

        if (leftGirdCount > countX)
        {
            leftGirdCount = countX;
            //----------------
            //  負很多 ex-8~-3
            //----------------
        }
        else if (leftGirdCount < 0)
        {
            leftGirdCount = 0;
            //----------------
            //  正很多 ex8~12
            //----------------
        }
        if (DownGirdCount > countY)
        {
            DownGirdCount = countY;
        }
        else if (DownGirdCount < 0)
        {
            DownGirdCount = 0;
        }
        JLabel  xLabel;
        int     showOriX    = theTransfer.GetShowOrign().x;
        int     diffX       = 0;
        double  splitPtX    = 0;
        int     showOriY    = theTransfer.GetShowOrign().y;
        int     diffY       = 0;
        double  splitPtY    = 0;

        if(leftGirdCount > 0)
        {
            if (showOriX > this.theDrawerWidth)
            {
                diffX = this.theDrawerWidth - showOriX;
                diffX = ((diffX / gird) - 1) * gird;
                splitPtX = ((double) diffX) / theTransfer.GetWidthScale();
            }
            for (int k = 0; k <= leftGirdCount; k++)
            {

                if (showOriX > this.theDrawerWidth)
                {
                    if (k ==  leftGirdCount)
                        break;
                }

                double temp = splitPtX - k / time;
                String labelx = Double.toString(temp);
                xLabel = new JLabel(labelx);
                //xLabel.setBounds(95+ theTransfer.GetShowOrign().x - (int)(k* gird), 560, 30, 20);
                xLabel.setBounds(45 + theTransfer.GetShowOrign().x + diffX - (int) (k * gird),  this.theDrawerHeight + this.vOuterDistance* 4/6, 30, 20);
                xLabel.setFont(fontCali); //313
                xLabel.setForeground(this.colorCalibration);
                this.add(xLabel);
                arrayClibrationLabel.add(xLabel);
            }
        }
        if (showOriX < 0)
        {
            diffX = -showOriX;
            diffX = (diffX / gird) * gird;
            splitPtX = ((double) diffX) / theTransfer.GetWidthScale();
        }
        for(int k = 1; k<= countX - leftGirdCount  +1; k++ )
        {
            if (showOriX >= 0 && k == countX - leftGirdCount + 1)
            {
                break;
            }

            double temp = splitPtX + k/  time;
            String labelx = Double.toString(temp);
            xLabel = new JLabel(labelx);
            //xLabel.setBounds(95+theTransfer.GetShowOrign().x + (int)(k* gird), 560 , 30, 20);
            xLabel.setBounds(45+theTransfer.GetShowOrign().x +diffX+ (int)(k* gird), this.theDrawerHeight + this.vOuterDistance* 4 /6 , 30, 20);
            xLabel.setFont(fontCali);//313
            xLabel.setForeground(this.colorCalibration);
            this.add(xLabel);
            arrayClibrationLabel.add(xLabel);
        }

        JLabel yLabel;

        if(DownGirdCount > 0)
        {
            if (showOriY < 0)
            {
                diffY = showOriY;
                diffY = (diffY / gird) * gird;
                splitPtY = ((double) diffY) / theTransfer.GetWidthScale();

            }

            for (int k = DownGirdCount; k >=0 ; k--)
            {
                if(showOriY < 0 && k == 0)
                {
                    break;
                }

                double temp = splitPtY - k / time;

                String labely = Double.toString(temp);
                yLabel = new JLabel(labely);
                //yLabel.setBounds(65,  95+(theTransfer.GetShowOrign().y + (int)(k* gird)), 30, 20);
                yLabel.setBounds(this.hOuterDistance/4, 45 + (theTransfer.GetShowOrign().y - diffY + (int) (k * gird)), 30, 20);
                yLabel.setFont(fontCali); //313
                yLabel.setForeground(this.colorCalibration);
                this.add(yLabel);
                 arrayClibrationLabel.add(yLabel);
            }
        }
        if (showOriY > this.theDrawerHeight)
        {
            diffY = showOriY - this.theDrawerHeight ;
            diffY = (diffY / gird)  * gird;
            splitPtY = ((double) diffY) / theTransfer.GetWidthScale();
        }


        for (int k = 1; k <= countY - DownGirdCount + 1; k++)
        {
            if(showOriY < this.theDrawerHeight)
            {
                if(k == countY - DownGirdCount +1)
                    break;
            }

            double temp = splitPtY + k / time;




            String labely = Double.toString(temp);
            yLabel = new JLabel(labely);
            //yLabel.setBounds(65, 95+(theTransfer.GetShowOrign().y - (int) (k * gird)), 30, 20);
            yLabel.setBounds(this.hOuterDistance/4, 45+(theTransfer.GetShowOrign().y - diffY- (int) (k * gird)), 30, 20);
            yLabel.setFont(fontCali);//313
            yLabel.setForeground(this.colorCalibration);
            this.add(yLabel);
            arrayClibrationLabel.add(yLabel);
        }
    }




    public void paintComponent(Graphics g)
    {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(paintBG);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.paintChildren(g);


    }


    private void AddActionListener()
    {

    }

    public void GetImgBuffer()
    {
        imgBuffer = this.createImage(this.getWidth(), this.getHeight());
        if (imgBuffer != null)
        {
            gBuffer = imgBuffer.getGraphics();
            this.update(this.getGraphics());
        }
        //this.update(this.getGraphics());
    }

    public void actionPerformed(ActionEvent e)
    {





    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {

    }

    public void mouseReleased(MouseEvent e)
    {


    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }
    public void SetColorForPrint(boolean isForPrint)
    {
        if(isForPrint)
        {
            this.colorCalibration= Color.BLACK;
            this.setBackground(Color.WHITE) ;
        }
        else
        {
            this.colorCalibration= Color.WHITE;
            this.setBackground(this.colorBackground) ;
        }
        this.validate();
    }

    public void componentResized(ComponentEvent e)
    {
        Dimension dim  = this._system.getFrame().getSplitRightDimension();

        this.theDrawerHeight = dim.height - this.hOuterDistance;
        this.theDrawerWidth = dim.width - this.vOuterDistance;
        this.refreshCalibration();

    }

    public void refreshCalibration()
    {
        if(theDrawerHeight > 0)
        {
            if(theTransfer == null)
            {
                int scale = 100;
                ObjectPoint origin = new ObjectPoint( theDrawerWidth/scale/2,theDrawerHeight/scale/2);
                theTransfer = new SDT_DrawTransfer(theDrawerHeight, theDrawerWidth, origin, scale, scale);
                this.theDrawPanel.setSize(theDrawerWidth, theDrawerHeight);
                System.out.println("drawer size null");
            }
            else
            {
                this.theDrawPanel.setSize(theDrawerWidth, theDrawerHeight);
                this.theTransfer.setPanelHeight(theDrawerHeight);
                this.theTransfer.setPanelWidth(theDrawerWidth);

                int scale = (int)theTransfer.GetWidthScale();
                theTransfer.setRealOrigin( theDrawerWidth/scale/2, theDrawerHeight/scale/2);
                this.theDrawPanel.setSize(theDrawerWidth, theDrawerHeight);
                this.theTransfer.setPanelHeight(theDrawerHeight);
                this.theTransfer.setPanelWidth(theDrawerWidth);
            }

            theDrawPanel.setTransfer(theTransfer);
            this.SetCalibration();
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
    public void setBackgroundPaint()
    {
        GradientPaint   paintDrawer = this.theDrawPanel.getBackgroundPaint();

        Color colorBGUp           = paintDrawer.getColor1();
        Color colorBGDown         = paintDrawer.getColor2();
        Point2D ptUp   = paintDrawer.getPoint1();
        Point2D ptDown = paintDrawer.getPoint2();


        paintBG             = new GradientPaint(0.1f,(float)ptUp.getY() + vOuterDistance / 2,colorBGUp,
                                              0.1f,(float)ptDown.getY() + vOuterDistance / 2,colorBGDown);
    }


}

