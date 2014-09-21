package sdt.dialog;

import sdt.framework.SDT_System;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.event.ActionEvent;
import sdt.define.DefineTreeNode;
import sdt.data.DataCoil;
import sdt.define.DefineSystemConstant;
import sdt.data.DataShell;

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
public class DiaThickness extends DiaMain
{
    private JTabbedPane _tabbed;

    private Box _boxTabContainer1;
    private Box _boxThickness;

    private JLabel _jLabelThickness;

    private JTextField _jTextThickness;

    private double _thickness;
    private double _thickness_Old;



    public DiaThickness(SDT_System system)
    {
        super(system, true);
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
        String str = "";

        str = this._system.GetInterfaceString("TitleThickness");

        this.setTitle(str);
        this.setSize(300, 130);
        this.setLocation();
        this.readDiaData();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        this._tabbed = new JTabbedPane();

        this._boxTabContainer1 = Box.createVerticalBox();
        this._boxThickness = Box.createHorizontalBox();
        this._jLabelThickness = new JLabel("Thickness : ");
        this._jTextThickness = new JTextField("");
    }

    protected void setSizeComponent()
    {
        this._jLabelThickness.setMaximumSize(this._dimensionFieldLong);
        this._jTextThickness.setMaximumSize(this._dimensionFieldLong);
    }

    protected void addComponent()
    {
        this._boxTabContainer1.add(Box.createVerticalStrut(10));
        this._boxThickness.add(this._jLabelThickness);
        this._boxThickness.add(this._jTextThickness);
        this._boxTabContainer1.add(this._boxThickness);



        this._tabbed.add(this._boxTabContainer1, "Thickness");


        this._contentPane.setLayout(null);
        this._tabbed.setBounds(20, 35, 260, 80);
        this.add(_tabbed);
        this._jButtonOk.setBounds(110, 90, 70, 25);
        this._jButtonCancel.setBounds(200, 90, 70, 25);

        this._contentPane.add(this._jButtonOk);
        this._contentPane.add(this._jButtonCancel);
    }

    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if (e.getSource() == this._jButtonOk)
        {
            String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeName();

            DataShell dataShell = this._system.getDataManager().getCurrentDataShell();
            DataCoil dataCoil = this._system.getDataManager().getDataCoil();

            if(dataShell != null)
                this._thickness_Old =dataShell.getThickness();
            else
                this._thickness_Old = dataCoil.getThickness();



            boolean isInputCorrect = this.checkTextfield();
            if (isInputCorrect != true)
            {
                this.setToOldValue();
                return;
            }

            if(dataShell != null)
                dataShell.setThickness(_thickness);
            else
                dataCoil.setThickness(_thickness);


            this._system.getModeler().getPanel2D().GetDrawPanel().upDate();
            this.dispose();

        }
        else if (e.getSource() == this._jButtonCancel)
        {
            this.dispose();
            System.out.println("button Cancel");
        }

    }

    protected void addListener()
    {
        this._jTextThickness.addKeyListener(this);
    }

    protected boolean checkTextfield()
    {
        boolean bool = true;

        try
        {
            this._thickness = Double.parseDouble(this._jTextThickness.getText());
            if (this._thickness < 0)
                bool = false;


        }
        catch (Exception e)
        {
            String diaStr = _system.GetInterfaceString("dia_MessageStrInvalidInputValue");
            DiaMessage diaWarning = new DiaMessage(_system, diaStr);
            bool = false;
        }
       return bool;
   }

   private void setThickness(double thick)
   {
       this._jTextThickness.setText("" + thick);
       this.setTextfieldFormat(this._jTextThickness);
   }




   private void setToOldValue()
   {
       this.setThickness(this._thickness_Old);
   }

   protected void readDiaData()
   {
       String str = "";
       String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeName();

       DataShell dataShell = this._system.getDataManager().getCurrentDataShell();
       DataCoil dataCoil = this._system.getDataManager().getDataCoil();


       if (dataShell == null && !nodeName.equals(DefineTreeNode.PRT_Coil))
           return;

       double thick = 0;
       if(dataShell != null)
       {
           thick = this._system.getDataManager().getCurrentDataShell().getThickness();
       }
       else
       {
           thick =dataCoil.getThickness();
       }

       if (nodeName.equals(DefineTreeNode.PRT_Cap))
           str = this._system.GetInterfaceString("TitleCapThickness");
       else if (nodeName.equals(DefineTreeNode.PRT_Transition))
           str = this._system.GetInterfaceString("TitleTransitionThickness");
       else if (nodeName.equals(DefineTreeNode.PRT_Diaphragm))
           str = this._system.GetInterfaceString("TitleDiaphragmThickness");
       else if (nodeName.equals(DefineTreeNode.PRT_Surround))
           str = this._system.GetInterfaceString("TitleSurroundThickness");


       this.setTitle(str);
       this.setThickness(thick);

   }
}
