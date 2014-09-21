package sdt.material;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import sdt.framework.SDT_System;
import javax.swing.JTable;


public class TableButton extends JButton
{
    int row;
    Type myType;
    SDT_System _system;
        public enum Type {
                DELETE, ADD
        }

        public TableButton(Type t,SDT_System system)
        {
            super();
            _system = system;
            setForeground(Color.gray);
            setMargin(new Insets(1,1,1,1));
            myType = t;

            if (myType == Type.DELETE) {
                    setFont(new Font("sans-serif",Font.BOLD,12));
                    setAction(new DeleteAction());
                    setText("x");
            } else if (myType == Type.ADD) {
                    setFont(new Font("sans-serif",Font.BOLD,16));
                    setAction(new AddAction());
                    setText("+");
            }
            //CamDataTableCellButtonMouseAdapter myMouse = new CamDataTableCellButtonMouseAdapter();
           // this.addMouseListener(myMouse);
            setFocusable(false);
        }

        private class DeleteAction extends AbstractAction
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "test Delete");
            }
        }


        private class AddAction extends AbstractAction
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "test Add");
            }
        }


        public Component getComponent(int r, int c) {
                setSelected(false);
               return getComponent(r,c,null);
        }

        public Component getComponent(int r, int c, Object e)
        {
            //    myEditor = e;
                row = r;
                if (row==0 && myType==Type.DELETE) {
                        setEnabled(false);
                }
                setSelected(false);
                return this;
        }

        /*private class CamDataTableCellButtonMouseAdapter extends MouseAdapter {
                public void mouseEntered(MouseEvent e) {
                        //myTable.highlight(row);
                }

                public void mouseExited(MouseEvent e) {
                        if (myEditor != null &&
                                        (e.getModifiersEx()&InputEvent.BUTTON1_DOWN_MASK)==0 &&
                                        (e.getModifiersEx()&InputEvent.BUTTON2_DOWN_MASK)==0 &&
                                        (e.getModifiersEx()&InputEvent.BUTTON3_DOWN_MASK)==0) {
                                myEditor.cancelCellEditing();
                        }
                }
                public void mouseReleased(MouseEvent e) {
                        if (!contains(e.getPoint()))
                                myEditor.cancelCellEditing();
                }
        }*/
}
