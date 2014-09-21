package sdt.tree;

import java.util.*;
import javax.swing.*;

public class SDT_ObjectNode extends SDT_IconNode
{
    private     String          _nodeName = "";
    protected   int             _nodeID;

    public      void            setNodeName(String name)            { this._nodeName = name;  }
    public      void            setNodeID(int ID)                   { this._nodeID = ID;      }

    public      String          getNodeName()                       { return (String)this._nodeName;    }
    public      int             getNodeID()                         { return this._nodeID;   }
    public      SDT_ObjectNode  getParentNode()                     { return (SDT_ObjectNode)this.getParent(); }


    public SDT_ObjectNode()
    {
        super();
    }
    public SDT_ObjectNode(String nodeName, String showName, int nodeID, int iconID, Icon nodeIcon)
    {
        super(showName, true, null);
        this._nodeName = nodeName;
        this._nodeID = nodeID;
        this._iconID = iconID;
        this._icon   = nodeIcon;
    }
}
