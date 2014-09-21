package sdt.uicomponent;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class IDTUI_button_transparent extends JButton
{
    private float theTransparency = 1;

    public IDTUI_button_transparent(float transparency)
   {
       super();
       this.setOpaque(false);
       this.setBorder(null) ;
       theTransparency = transparency;
   }
   public IDTUI_button_transparent()
   {
       super();
       this.setOpaque(false);
   }
   public void setEnabled(boolean bool)
   {
       super.setEnabled(bool) ;

       if(bool)
       {
           theTransparency = 0f;
       }
       else
       {
           theTransparency = 0.8f;
       }

   }



    public IDTUI_button_transparent(String text)
    {
        super(text);
        this.setOpaque(false);
    }
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, theTransparency));
        super.paint(g2);
        g2.dispose();
    }

}
