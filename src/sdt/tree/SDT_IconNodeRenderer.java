package sdt.tree;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class SDT_IconNodeRenderer extends DefaultTreeCellRenderer
{
   public SDT_IconNodeRenderer()
   {
       super();
   }

   public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                 boolean sel, boolean expanded,
                                                 boolean leaf, int row, boolean hasFocus)
   {
       super.getTreeCellRendererComponent(tree, value,
                                          sel, expanded, leaf, row, hasFocus);

       Icon icon = ((SDT_IconNode) value).getIcon();

       if (icon == null)
       {
           Hashtable icons = (Hashtable) tree.getClientProperty("JTree.icons");
           String name = ((SDT_IconNode) value).getIconName();
           if ((icons != null) && (name != null))
           {
               icon = (Icon) icons.get(name);
               if (icon != null)
               {
                   setIcon(icon);
               }
           }
       }
       else
       {
           setIcon(icon);
       }

       return this;
   }
}
