package sdt.dialog;

import java.awt.*;
import java.awt.event.*;

import sdt.framework.*;
import sdt.panel.*;

public class DiaPlotLinear extends DiaMain
{
    private double[][]  _dataXY;
   // private SDT_PanelPlot  _panelPlot;
    private SDT_PanelGraph _panelGraph;
    private String         _title;

    public DiaPlotLinear(SDT_System system, double[][] dataXY, String title)
    {
        super(system, false);
        _dataXY = dataXY;
        _title = title;
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
        if(!_title.equals(""))
        {
            str += (" - " + _title);
        }
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

      if(_dataXY != null)
      {
          _panelGraph = new SDT_PanelGraph(0, 0, 580, 580);
            _panelGraph.setShowLinearScale();
            for (int i = 0; i < _dataXY.length; i++)
            {
                _panelGraph.inputValue(_dataXY[i][0], _dataXY[i][1], 1);
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
        this._contentPane.setLayout(null);
        if (_panelGraph != null)
        {
            _panelGraph.setBounds(0, 0, 580, 580);
            this._contentPane.add(_panelGraph);
        }
        //   if(_panelPlot != null)
        //      this._contentPane.add(_panelPlot,BorderLayout.CENTER);


        _jButtonOk.setBounds(0, 580, 580, 30);
        this._contentPane.add(_jButtonOk);
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
