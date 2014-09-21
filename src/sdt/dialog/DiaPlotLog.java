package sdt.dialog;

import sdt.framework.SDT_System;
import java.awt.event.ActionEvent;
//import sdt.panel.SDT_PanelPlot;
import java.awt.BorderLayout;
import sdt.panel.SDT_PanelGraph;

public class DiaPlotLog extends DiaMain
{
    private double[][]  _dataXY;
   // private SDT_PanelPlot  _panelPlot;
    private SDT_PanelGraph _panelGraph;

    public DiaPlotLog(SDT_System system, double[][] dataXY)
    {
        super(system, true);
        _dataXY = dataXY;

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
        String str = this._system.GetInterfaceString("TitlePlot");
        this.setTitle(str);


        this.setDialogBody();

        this.setSize(580, 610);
        this.setLocation();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        super.actionPerformed(e);
        if (e.getSource() == this._jButtonOk)
        {
            System.out.println(" ok is pressed");
            this.dispose();
        }
    }



    protected void createComponent()
    {
        /*if(_dataXY != null)
            _panelPlot = new SDT_PanelPlot("SPL","frequency", "Hz","SPL","dB","SPL", this._dataXY);
        else
            _panelPlot = null;
*/
        if (_dataXY != null)
        {
            _panelGraph = new SDT_PanelGraph(0, 0, 580, 580);
            _panelGraph.setShowLogScale();
            for (int i = 0; i < _dataXY.length; i++)
            {
                _panelGraph.inputValue(_dataXY[i][0], _dataXY[i][1],0);
                //  System.out.println(_dataXY[i][0] +" , "+ _dataXY[i][1]);
            }

            this._panelGraph.setZoomToFit();
      }
        else
        {
           /* _panelGraph = new SDT_PanelGraph(0, 0, 580, 580);
            _panelGraph.setShowLogScale();*/
        }
    }

    protected void setSizeComponent()
    {
    }

    protected void addComponent()
    {
        this._contentPane.setLayout(new BorderLayout());
         if(_panelGraph != null)
            this._contentPane.add(_panelGraph,BorderLayout.CENTER);
        //if(_panelPlot != null)
         //  this._contentPane.add(_panelPlot,BorderLayout.CENTER);

        this._contentPane.add(this._jButtonOk,BorderLayout.SOUTH);
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
