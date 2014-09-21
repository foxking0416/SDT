package sdt.tree;

import javax.swing.*;
import javax.swing.tree.*;

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

public class SDT_IconNode extends DefaultMutableTreeNode
{
    private     String _iconName;
    protected   Icon   _icon;
    protected   int    _iconID;

    public      int  getIconID()                        {  return this._iconID;   }

    protected SDT_IconNode()
    {
        this(null);
    }

    protected SDT_IconNode(Object userObject)
    {
        this(userObject, true, null);
    }

    protected SDT_IconNode(Object userObject, boolean allowsChildren, Icon icon)
    {
        super(userObject, allowsChildren);
        this._icon = icon;
    }

    public void setIconID(int iconID, Icon nodeIcon)
    {
        this._iconID = iconID;
        this._icon = nodeIcon;
    }


    public Icon getIcon() // for icon_node_renderer
    {
        return _icon;
    }

    public String getIconName() // for icon_node_renderer
    {
        if (_iconName != null)
        {
            return _iconName;
        }
        else
        {
            String str = userObject.toString();
            int index = str.lastIndexOf(".");
            if (index != -1)
            {
                return str.substring(++index);
            }
            else
            {
                return null;
            }
        }
    }
}
