package sdt.tree;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import sdt.framework.SDT_System;
import sdt.icon.SDT_ManagerIcon;
import java.util.Enumeration;
import sdt.define.DefineTreeNode;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *最高層級的Tree元件, 實作一些凡是Tree皆應該有的功能
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SDT_TreeMain extends JScrollPane implements MouseListener, MouseMotionListener, KeyListener, DefineTreeNode
{
    protected DefaultTreeModel      defaultTreeModel;
    protected TreeSelectionModel    treeSelectionModel;
    protected JTree                 _tree;
    protected SDT_System            _system;
    protected SDT_ManagerIcon   _managerIcon;
    protected SDT_ObjectNode    _selectedNode;
    protected TreePath          _selectedPath;

    public SDT_TreeMain(SDT_System system)
    {
        this._system = system;
        this._managerIcon = _system.getManagerIcon();
        try
        {
            this.Initial();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void Initial()
    {

    }

    public void ExpandTreeAll()
    {
        this.ExpandAll(this._tree, new TreePath(this._tree.getModel().getRoot()));
    }

    public void ExpandTreeFirst()
    {
        this._tree.expandRow(0);
    }

    public void CollapseTree()
    {
        this.CollapseAll(this._tree, new TreePath(this._tree.getModel().getRoot()));
        this._tree.expandRow(0);
    }

    private void ExpandAll(JTree tree, TreePath path) //把所有的樹節點展開
    {
        tree.expandPath(path);
        TreeNode node = (TreeNode) path.getLastPathComponent();
        for (Enumeration i = node.children(); i.hasMoreElements(); )
        {
            ExpandAll(tree, path.pathByAddingChild(i.nextElement()));
        }
    }

    private void CollapseAll(JTree tree, TreePath path) //把所有的樹節點關上
    {
        TreeNode node = (TreeNode) path.getLastPathComponent();
        for (Enumeration i = node.children(); i.hasMoreElements(); )
        {
            CollapseAll(tree, path.pathByAddingChild(i.nextElement()));
            tree.collapsePath(path);
        }
    }

    public TreePath getTreePath(MouseEvent e)
    {
        int pressNodeRow = -1;
        pressNodeRow = this._tree.getRowForLocation(e.getX(), e.getY());
        if (pressNodeRow == -1)
        {
            this.treeSelectionModel.clearSelection();
            return null;
        }

        TreePath path = this._tree.getPathForRow(pressNodeRow);

        return path;
    }

    protected void highLightNode(SDT_ObjectNode pickTreeNode)
    {
        if (pickTreeNode == null)
            return;
        this.CollapseTree();

        TreePath path = new TreePath(pickTreeNode.getPath());
        this.treeSelectionModel.setSelectionPath(path);
        this._tree.updateUI();
        // pickTreeNode.SetAuto(true);
    }



    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
       // System.out.println("Pressed");
        TreePath path = this.getTreePath(e);
        if (path == null)
            return;

        this.treeSelectionModel.setSelectionPath(path);
        _selectedNode = (SDT_ObjectNode) path.getLastPathComponent();

    }

    public void mouseReleased(MouseEvent e)
    {

    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void mouseDragged(MouseEvent e)
    {
    }

    public void mouseMoved(MouseEvent e)
    {
    }


}
