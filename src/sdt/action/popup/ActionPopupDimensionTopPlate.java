
package sdt.action.popup;

import java.awt.event.*;

import sdt.action.*;
import sdt.define.*;
import sdt.dialog.*;
import sdt.framework.*;

public class ActionPopupDimensionTopPlate extends ActionMain
{
    public ActionPopupDimensionTopPlate(SDT_System system)
  {
      super(system, "TopPlateDimension");
  }

  public void actionPerformed(ActionEvent e)
  {
      this._system.getFrame().SetStatus("TopPlateDimension");

      DiaDimensionTopPlate dia;
      String nodeName = this._system.getModeler().GetAssemTree().getSelectedNodeNameLowestLevel();


      if(nodeName != null && nodeName != "")
      {
          if (nodeName.equals(DefineTreeNode.SKT_Section) || nodeName.equals(DefineTreeNode.SKT_Section1))
          {
              dia = new DiaDimensionTopPlate(this._system, this.XZView);
          }
          else //(nodeName.equals(DefineTreeNode.SKT_Section2))
          {
              dia = new DiaDimensionTopPlate(this._system, this.YZView);
          }
      }
      else
      {

      }
  }

}
