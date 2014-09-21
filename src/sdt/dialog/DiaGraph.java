package sdt.dialog;

import sdt.framework.SDT_System;
import sdt.panel.SDT_PanelGraph;
import sdt.panel.drawpanel.SDT_DrawPanel2D;
import javax.swing.JPanel;
import java.awt.Color;

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
public class DiaGraph extends DiaMain
{
    private SDT_PanelGraph _graphPanel;
    private JPanel panel;
    private JPanel panel2;

    public DiaGraph(SDT_System system)
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
        String str = "Graph";


        this.readDiaData();
        this.setTitle(str);
        this.setSize(800, 800);
        this.setLocation();
        this.setVisible(true);
    }

    protected void createComponent()
    {
        this._graphPanel = new SDT_PanelGraph(0, 30, 680, 680);
        this._graphPanel.setShowLogScale();

        //this._graphPanel.pasteKlippelData();


        //this._graphPanel.inputValue(0 , 0);
        //this._graphPanel.inputValue(0.012 , 0.01);
        //this._graphPanel.inputValue(0.013 , 0.02);
        //this._graphPanel.inputValue(0.015 , 0.03);

        this._graphPanel.inputValue(40,-2,0);
        this._graphPanel.inputValue(100,-1,0);
        this._graphPanel.inputValue(400,-2,0);
        this._graphPanel.inputValue(1000,-1,0);
        this._graphPanel.inputValue(40,1,1);
        this._graphPanel.inputValue(100,2,1);
        this._graphPanel.inputValue(400,1,1);
        this._graphPanel.inputValue(1000,2,1);

        this._graphPanel.setLineColor(Color.green, 1);
        //this._graphPanel.setLineWidth(3, 1);
        //this._graphPanel.setPointSize(8, 1);
        //this._graphPanel.setPointType(1, 1);


        //this._graphPanel.copyDataToKlippel(0);
    }

    protected void setSizeComponent()
    {
    }

    protected void addComponent()
    {
        this.add(this._graphPanel);
    }

    protected void addListener()
    {
    }

    protected boolean checkTextfield()
    {
        return false;
    }

    protected void readDiaData()
    {
    }
}
