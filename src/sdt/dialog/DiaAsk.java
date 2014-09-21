package sdt.dialog;

import sdt.framework.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class DiaAsk  extends DiaMain
{
    protected JLabel _jLabelMessage;
    protected String _theMsg;
    protected boolean _result;
    public boolean GetResult()    {return _result;}

    public DiaAsk(SDT_System system, String msg)
    {
        super(system, true);
        _theMsg = msg;
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
        String str = this._system.GetInterfaceString("dia_AskTitle");
        this.setTitle(str);

        this.setSize(220, 100);
        this.setDialogBody();

        this.setLocation();

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if(e.getSource() == this._jButtonOk)
        {
            _result = true;
            this.dispose();
        }

        if (e.getSource() == this._jButtonCancel)
        {
            _result = false;
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
        _contentPane.add(this._jButtonCancel);
    }

    protected void addListener()
    {
    }

    protected void setSizeComponent()
    {
        _contentPane.setLayout(null);
        this._jLabelMessage.setBounds(40, 10, 180, 50);
        this._jButtonOk.setBounds(35, 55, 70, 25);
        this._jButtonCancel.setBounds(115, 55, 70, 25);
    }

    protected boolean checkTextfield()
    {
        return false;
    }

    protected void readDiaData()
    {
    }


}
