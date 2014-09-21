package sdt.framework;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import sdt.java3d.*;

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
public class SDT_Frame extends JFrame
{
    private Container   contentPane;            // include paneCenter  paneStatus
    private JPanel      paneCenter;             // include toolbarMain  paneMain
    private JPanel      paneMain;               // include labelBack  paneSplit
    private JPanel      paneStatus;             // include lableStatus
    private JPanel      paneRight;              // include canvas3d toolbarModel
    private JPanel      paneLeft;               // include tree
    private JSplitPane  paneSplit;              // include panelRight   panelLeft
    private ImageIcon   backImage;
    private JLabel      labelBack;
    private JLabel      labelStatus;

    private JPanel      toolbarPanelUpper;
    private JPanel      toolbarPanelRight;

    private int         splitLocation;

    public JPanel       GetContentPane()                {return (JPanel)this.contentPane;}
    public JLabel       GetBackLabel()                  {return this.labelBack;}


    public SDT_Frame()
    {
        try
        {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.contentPane = this.getContentPane();
            this.paneCenter = new JPanel();
            this.paneMain = new JPanel();
            this.paneStatus = new JPanel();
            this.toolbarPanelUpper = new JPanel();
            this.toolbarPanelRight = new JPanel();

            this.labelStatus = new JLabel("  SDT Start");

            jbInit();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    public Dimension getSplitRightDimension()
    {
        int OuterX = this.paneSplit.getRightComponent().getWidth();//this.paneSplit.getWidth() - this.splitLocation;
        int OuterY =   this.paneSplit.getRightComponent().getHeight();//.paneSplit.getHeight();
        Dimension dimFrame = new Dimension(OuterX, OuterY);
        return dimFrame;
    }
    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception
    {


        this.setTitle("");

        this.contentPane.setLayout(new BorderLayout());
        this.paneCenter.setLayout(new BorderLayout());
        this.paneMain.setLayout(new BorderLayout());
        this.paneStatus.setLayout(new BorderLayout());
        this.toolbarPanelUpper.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.paneMain.setBorder(BorderFactory.createLoweredBevelBorder());
        this.paneStatus.setBorder(BorderFactory.createEtchedBorder());

        //this.paneMain.add(null, BorderLayout.CENTER);
        this.paneCenter.add(this.paneMain, BorderLayout.CENTER);
        this.paneStatus.add(this.labelStatus, BorderLayout.WEST);
        this.contentPane.add(this.paneCenter, BorderLayout.CENTER);
        this.contentPane.add(this.paneStatus, BorderLayout.SOUTH);

        this.buildSplitPane();
        this.SetSplitPane();
    }
    public void buildSplitPane()
    {
        this.paneSplit = new JSplitPane();
        this.paneRight = new JPanel();
        this.paneLeft = new JPanel();

        this.paneRight.setLayout(new BorderLayout());
        this.paneLeft.setLayout(new BorderLayout());
        this.splitLocation = 250;

        this.paneSplit.setDividerLocation(splitLocation);
        this.paneSplit.setDividerSize(10);
        this.paneSplit.setOneTouchExpandable(true);

    }

    public void SetSplitPane()
    {
        this.paneMain.add(this.paneSplit, BorderLayout.CENTER);
        this.paneSplit.add(this.paneLeft, JSplitPane.LEFT);
        this.paneSplit.add(this.paneRight, JSplitPane.RIGHT);
    }


    /**
     * 在JAVA的Layout中將2D或3D繪圖區域加進來
     * @param panel3d SDT_DrawPanel3D
     */
    public void SetPanel3D(SDT_DrawPanel3D panel3d)
    {
        this.paneRight.removeAll();
        this.paneRight.add(panel3d, BorderLayout.CENTER);
        this.paneRight.updateUI();
    }

    public void SetPanel2D(JPanel panel2d)
    {
       // this.SetSplitPane();
        this.paneRight.removeAll();
        this.paneRight.add(panel2d, BorderLayout.CENTER);
    }


    public void SetTree(JScrollPane scrollpaneTree)
    {
        //this.SetSplitPane();
        this.paneLeft.add(scrollpaneTree, BorderLayout.CENTER);//scrollpaneTree);
    }



    public void SetStatus(String str)
    {
        this.labelStatus.setText("  " + str);
    }

    public void setJMenuBar(JMenuBar menuBar)
    {
        this.contentPane.add(menuBar, BorderLayout.NORTH);
        this.contentPane.validate();
    }
    public void setTitle(String title)
    {
        if(title.length() != 0)
            super.setTitle("Speaker Design Tool (NWInG, FEA, 2013.04.23)-->" + title);
        else
            super.setTitle("Speaker Design Tool (NWInG, FEA, 2013.04.23)");
    }


    public void paneMain_componentResized(ComponentEvent e)
    {
        //   this.toolbarPanelRight.removeAll();
    }

    public void validate()
    {
        System.out.println("validate");
        super.validate();
        //this.paneRight.updateUI();
        //this.paneLeft.updateUI();
        //this.paneMain.updateUI();
    }



}


