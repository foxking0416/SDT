package sdt.panel;

import javax.swing.*;
import java.awt.Image;
import java.awt.image.BufferedImage;

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
public class SDT_PanelResult extends JPanel
{
    public SDT_PanelResult()
    {

    }

    public Image GetImages()
    {
        Image imageContent = this.createImage(this.getWidth(), this.getHeight());
        //java.awt.Graphics   bkground = buffer.getGraphics();
        java.awt.Graphics bkground = imageContent.getGraphics();
        this.printAll(bkground);

        return imageContent;
    }



}
