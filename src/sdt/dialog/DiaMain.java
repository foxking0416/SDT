package sdt.dialog;


import java.awt.*;
import java.awt.event.*;
import java.text.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import sdt.define.*;
import sdt.framework.*;


public abstract class DiaMain extends JDialog implements KeyListener,
                                                 ActionListener,
                                                 ItemListener,
                                                 FocusListener,
                                                 ChangeListener,
                                                 WindowListener,
                                                 MouseListener,
                                                 MouseMotionListener,
                                                 ComponentListener,
                                                 DefineSystemConstant
{
    public     int               _focus         = 0;
    public     boolean           _isButtonOK    = false;
    protected  JPanel            _fullPane      = (JPanel)this.getContentPane();
    protected  JPanel            _contentPane   = new JPanel();
    protected  JPanel            _jPanelTitle;
    protected  JLabel            _jLabelTitle;
    protected  JPanel            _buttonPanel;

    protected  int               _diffX;
    protected  int               _diffY;
    protected  JButton           _jButtonOk   ;
    protected  JButton           _jButtonCancel;
    protected  JButton           _jButtonClose;

    protected  SDT_System        _system;
    protected  Dimension         _dimensionButtonShort   = new Dimension(60, 25);
    protected  Dimension         _dimensionButtonMiddle  = new Dimension(80, 25);
    protected  Dimension         _dimensionButtonLong    = new Dimension(100, 25);
    protected  Dimension         _dimensionFieldMiddle   = new Dimension(80, 25);
    protected  Dimension         _dimensionFieldLong     = new Dimension(100, 25);
    protected  Dimension         _dimensionFieldShort    = new Dimension(50, 25);
    protected  Dimension         _dimensionLabelMiddle   = new Dimension(80, 25);
    protected  Dimension         _dimensionLabelLong     = new Dimension(120, 25);
    protected  Dimension         _dimensionBoxMiddle     = new Dimension(120, 25);
    protected  Dimension         _dimensionLong          = new Dimension(160, 25);
    protected  Dimension         _dimensionSuperLong     = new Dimension(220, 25);

    protected ImageIcon          _imageCloseButton;
    protected ImageIcon          _imageCloseButtonDown;



    //protected Color               showColor   ;


    public DiaMain(SDT_System system,  boolean model)
    {
        super(system.getFrame(),  model);
        this._system = system;
        this.setUndecorated(true);
        this.setTitlePanel();
        this.setButtonProperties();
        this.setResizable(false);
        this.addWindowListener(this);
        this.setDialogBody();
    }

    /**
     * DiaMain
     */
    public DiaMain()
    {
    }

    /*public DiaMain()
    {
        super();
    }*/

    public void setLocation()
    {
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension outerSize = null;
        Point leftPoint   = null;
        if(_system.getFrame().isVisible() )
        {
            outerSize = _system.getFrame().getSize();
            leftPoint = _system.getFrame().getLocation();
        }
        else
        {
            outerSize = Toolkit.getDefaultToolkit().getScreenSize();
            leftPoint = new Point(0,0) ;
        }

        Dimension dialogSize = this.getSize();

        if (dialogSize.height > outerSize.height)
        {
            dialogSize.height = outerSize.height;
        }
        this.setLocation((int) leftPoint.getX() + (outerSize.width - dialogSize.width) / 2, (int) leftPoint.getY() + (outerSize.height - dialogSize.height) / 2);
    }
    protected void setDialogBody()
    {
        this.createComponent();
        this.setSizeComponent();
        this.addComponent();
        this.addListener();
    }
    protected abstract void createComponent();
    protected abstract void setSizeComponent();
    protected abstract void addComponent();
    protected abstract void addListener();
    protected abstract boolean checkTextfield();
    /**
     * Read Dialog data before dialog is shown.
     * It is invoke in the constructor of DiaMain
     *  @todo
     **/
    protected abstract void readDiaData();


    protected void setButtonPanel()
    {
        _buttonPanel   = new JPanel();
        _buttonPanel.add(_jButtonOk);
        _buttonPanel.add(_jButtonCancel);
    }

    private void setButtonProperties()
    {
        String str1 = this._system.GetInterfaceString("dia_ButtonOk");
        String str2 = this._system.GetInterfaceString("dia_ButtonCancel");

        _jButtonOk = new JButton(str1);
        _jButtonCancel = new JButton(str2);

        _jButtonOk.addActionListener(this);
        _jButtonCancel.addActionListener(this);
        _jButtonClose.addActionListener(this);

        _jButtonOk.setPreferredSize(_dimensionButtonMiddle); //set Size
        _jButtonOk.setMaximumSize(_dimensionButtonMiddle);
        _jButtonCancel.setPreferredSize(_dimensionButtonMiddle); //set Size
        _jButtonCancel.setMaximumSize(_dimensionButtonMiddle);
    }

    public void itemStateChanged(ItemEvent e)
    {

    }
    public void keyTyped(KeyEvent e)
    {
        /**@todo Implement this java.awt.event.KeyListener method*/
    }
    public void keyPressed(KeyEvent e)
    {
        int keyEventNumber = e.getKeyCode();
        switch (keyEventNumber)
        {
            case 10:
                _jButtonOk.doClick();
                break;

            case 27:
                _jButtonCancel.doClick();
                break;
        }
    }
    public void keyReleased(KeyEvent e)
    {

    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()== this._jButtonClose)
        {
            this._jButtonCancel.doClick();
            this.dispose();
        }
    }
    public void focusGained(FocusEvent e)
    {

    }
    public void focusLost(FocusEvent e)
    {

    }

    public void DisposeWithoutSetCurrentDiaNull()
    {
        super.dispose();
    }

    /**
     * Invoked the first time a window is made visible.
     *
     * @param e WindowEvent
     * @todo Implement this java.awt.event.WindowListener method
     */
    public void windowOpened(WindowEvent e)
    {
    }

    /**
     * Invoked when the user attempts to close the window from the window's system menu.
     *
     * @param e WindowEvent
     * @todo Implement this java.awt.event.WindowListener method
     */
    public void windowClosing(WindowEvent e)
    {

    }

    /**
     * Invoked when a window has been closed as the result of calling dispose on the window.
     *
     * @param e WindowEvent
     * @todo Implement this java.awt.event.WindowListener method
     */
    public void windowClosed(WindowEvent e)
    {
    }

    /**
     * Invoked when a window is changed from a normal to a minimized state.
     *
     * @param e WindowEvent
     * @todo Implement this java.awt.event.WindowListener method
     */
    public void windowIconified(WindowEvent e)
    {
    }

    /**
     * Invoked when a window is changed from a minimized to a normal state.
     *
     * @param e WindowEvent
     * @todo Implement this java.awt.event.WindowListener method
     */
    public void windowDeiconified(WindowEvent e)
    {
    }

    /**
     * Invoked when the Window is set to be the active Window.
     *
     * @param e WindowEvent
     * @todo Implement this java.awt.event.WindowListener method
     */
    public void windowActivated(WindowEvent e)
    {
    }

    /**
     * Invoked when a Window is no longer the active Window.
     *
     * @param e WindowEvent
     * @todo Implement this java.awt.event.WindowListener method
     */
    public void windowDeactivated(WindowEvent e)
    {
    }

    public void stateChanged(ChangeEvent e)
    {

    }
    public void setSize(int x, int y)
    {
        super.setSize(x,y+30) ;

        _jButtonClose.setBounds(x - 25 - 10, 5, 25, 21);
        this._jLabelTitle.setBounds(10, 5, x - 25 - 20, 21);
        this._jPanelTitle.setBounds(0,0,x,30)  ;

        this._fullPane.setLayout(null) ;
        this._fullPane.add(_jPanelTitle);

        this._contentPane.setBounds(0,30,x,y);
        this._fullPane.add(this._contentPane);

        Border borderContent  = LineBorder.createGrayLineBorder();////BorderFactory.createEtchedBorder(EtchedBorder.RAISED,Color.WHITE,Color.darkGray);//EtchedBorder.RAISED, Color.white, Color.black);
        Border borderTitle  = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,Color.DARK_GRAY,Color.lightGray);//EtchedBorder.RAISED, Color.white, Color.black);
        this._contentPane.setBorder(borderContent);
        this._jPanelTitle.setBorder(borderTitle);

    }


    public void setTitlePanel()
    {
        this._jPanelTitle = new JPanel();
        this._jLabelTitle = new JLabel();
        _imageCloseButton = new ImageIcon(this.getClass().getResource("/sdt/icon/Button/close.jpg"));
        _imageCloseButtonDown = new ImageIcon(this.getClass().getResource("/sdt/icon/Button/closeDown.jpg"));
        _jButtonClose = new JButton(_imageCloseButton);
        _jButtonClose.setBorder(null) ;

        this._jPanelTitle.setLayout(null) ;
        this._jPanelTitle.add(_jButtonClose);
        this._jPanelTitle.add(this._jLabelTitle);


        Color colorTitle = new Color(125,125,125);
        this._jPanelTitle.setBackground(colorTitle);
        this._jLabelTitle.setBackground(colorTitle);
        this._jButtonClose.setBackground(colorTitle);

        this._jPanelTitle.addMouseListener(this);
        this._jPanelTitle.addMouseMotionListener(this);
        this._jButtonClose.addActionListener(this);
        this._jButtonClose.addMouseListener(this);
    }
    protected void setTextfieldFormat(JTextField jTextField)
    {
        String  strText = jTextField.getText();
        String  formatText = this.StringNumFormat(strText);
        jTextField.setText(formatText);
    }

    private String StringNumFormat(String num)
    {
        DecimalFormat theFormatedNum = new DecimalFormat("###.000"); //000 表示要三位

        Double dNum = new Double(num); //String ->Double
        String theResult = theFormatedNum.format(dNum); //double -> String

        return theResult;
    }


    public void setTitle(String str)
    {
        this._jLabelTitle.setText(str);
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        if(e.getSource()== this._jButtonClose  )
        {
            this._jButtonClose.setIcon(this._imageCloseButtonDown);
        }
        else
        {
            _diffX = e.getX();
            _diffY = e.getY();
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        if(e.getSource()== this._jButtonClose  )
        {
            this._jButtonClose.setIcon(this._imageCloseButton);
        }

    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseDragged(MouseEvent e)
    {
        int x = this.getLocation().x;
        int y = this.getLocation().y;
        this.setLocation(x + e.getX() - _diffX, y +  e.getY()- _diffY);
    }

    public void mouseMoved(MouseEvent e)
    {
    }

    public void componentResized(ComponentEvent e)
    {
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

}
