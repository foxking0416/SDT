package sdt.dialog;

import sdt.framework.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class DiaMessage  extends DiaMain
{
    protected JLabel    _jLabelMessage;
    protected String    _theMsg;
    protected boolean   _longerOrShorter = false; //default shorter

    public DiaMessage(SDT_System system,String msg)
    {
        this(system,msg,true,true);
    }
    public DiaMessage(SDT_System system,String msg, boolean longerOrShorter)
    {
        this(system,msg,longerOrShorter,true);
    }

    public DiaMessage(SDT_System system, String msg, boolean longerOrShorter,boolean modal)
    {

        super(system, modal);
        _theMsg = msg;
        _longerOrShorter = longerOrShorter;

        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    void jbInit() throws Exception
    {
        String str = this._system.GetInterfaceString("dia_MessageTitle");
        this.setTitle(str);
        if (_longerOrShorter)
        {
            this.setSize(360, 130);
        }
        else
            this.setSize(240, 130);
        this.setDialogBody();

        this.setLocation();
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if(e.getSource() == this._jButtonOk)
        {
            this.dispose();
        }
    }

    protected void createComponent()
    {
        _jLabelMessage = new JLabel(_theMsg);
    }

    protected void addComponent()
    {
        _contentPane.add(this._jLabelMessage);
        _contentPane.add(this._jButtonOk);
    }

    protected void addListener()
    {
    }

    protected void setSizeComponent()
    {
        this._contentPane.setLayout(null);
        this._jLabelMessage.setBounds(40, 10, 250, 40);
        this._jButtonOk.setBounds(110, 60, 70, 25);
    }

    protected boolean checkTextfield()
    {
        return false;
    }

    protected void readDiaData()
    {
    }


}
