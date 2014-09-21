package sdt.framework;

import java.awt.*;

import sdt.java3d.*;
import sdt.panel.drawpanel.*;
import sdt.tree.*;

public class SDT_Modeler
{

    private SDT_System                _system;
    private SDT_TreeAssembly          _tree;
    private SDT_DrawPanel3D           _panel3D;
    private SDT_DrawPanelOuter        _panel2D;

    private int                       _diplayControlType;

    public int          getControlType()                 {return this._diplayControlType;}
    public void         setControlType(int controlType)  {this._diplayControlType = controlType;}


  public SDT_Modeler(SDT_System system)
  {
      this._system = system;
      this._tree = null;
  }

  /**
   * 建立3D的繪圖區域
   * @return SDT_DrawPanel3D
   */
  public SDT_DrawPanel3D buildPanel3D()
   {
       _panel3D = new SDT_DrawPanel3D(_system);
        _panel3D.SetSize(new Dimension(840, 680));
      // this.build3DModel();
       return  _panel3D;
   }
   public SDT_DrawPanel3D getPanel3D()
   {
       return  _panel3D;
   }
   /**
    * 建立2D的繪圖區域
    * @return SDT_DrawPanelOuter
    */
   public SDT_DrawPanelOuter buildPanel2D()
   {
       _panel2D = new SDT_DrawPanelOuter(_system);
       return  _panel2D;
   }

   /**
   * 得到建立的2D繪圖區域
   * @return SDT_DrawPanelOuter
   */
   public SDT_DrawPanelOuter getPanel2D()
   {
       return  _panel2D;
   }


   /**
   * 建立特徵樹
   * @return SDT_TreeAssembly
   *
   */
   public SDT_TreeAssembly BuildTree()
   {
       this._tree = new SDT_TreeAssembly(_system);
       return this._tree;
   }

   /**
   * 得到建立的特徵樹
   * @return SDT_TreeAssembly
   *
   */
   public SDT_TreeAssembly GetAssemTree()
   {
       return this._tree;
   }


}
