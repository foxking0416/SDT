package sdt.action.popup;


import java.awt.event.*;

import sdt.action.*;
import sdt.define.*;
import sdt.dialog.*;
import sdt.framework.*;

public class ActionPopupDimensionMagnetTop extends ActionMain
{
    public ActionPopupDimensionMagnetTop(SDT_System system)
  {
      super(system, "MagnetTopDimension");
  }

  public void actionPerformed(ActionEvent e)
  {
      this._system.getFrame().SetStatus("MagnetTopDimension");

      DiaDimensionMagnetTop dia;
      String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeNameLowestLevel();


      if(nodeName != null && nodeName != "")
      {
          if (nodeName.equals(DefineTreeNode.SKT_Section) || nodeName.equals(DefineTreeNode.SKT_Section1))
          {
              dia = new DiaDimensionMagnetTop(this._system, this.XZView);
          }
          else //(nodeName.equals(DefineTreeNode.SKT_Section2))
          {
              dia = new DiaDimensionMagnetTop(this._system, this.YZView);
          }
      }
      else
      {

      }
  }

}
