package sdt.uicomponent;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import sdt.framework.SDT_System;

public class ButtonEditor extends DefaultCellEditor
{
    protected JButton button;
    private String label;
    private boolean isPushed;
    private SDT_System _system;

    public ButtonEditor(JCheckBox checkBox,SDT_System theSystem)
    {
        super(checkBox);
        this._system = theSystem;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column)
    {
        label = (value == null) ? "" : value.toString();
        label = label.trim();
        boolean isCurve = label.endsWith("Curve") || label.startsWith("Curve Data");

        if(isCurve)
        {
            if (isSelected)
            {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            }
            else
            {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            button.setText(label);
            isPushed = true;
            return button;
        }
        else
        {
            isPushed = false;
            JLabel onlyLabel = new JLabel(label);
            onlyLabel.setHorizontalAlignment(JLabel.CENTER);
            /*if (isSelected)
            {
                //onlyLabel.setForeground(table.getSelectionForeground());
                //onlyLabel.setBackground(table.getSelectionBackground());
                System.out.println("tttt");
                 onlyLabel.setForeground(table.getSelectionForeground());
                 onlyLabel.setBackground(table.getSelectionBackground());

            }
            else
            {
                //onlyLabel.setForeground(table.getForeground());
                //onlyLabel.setBackground(table.getBackground());
                System.out.println("000");
                onlyLabel.setForeground(Color.BLACK);
                 onlyLabel.setBackground(Color.BLUE);

            }*/
            return onlyLabel;
        }
    }

    public Object getCellEditorValue()
    {
        if (isPushed)
        {
            this._system.pupupMaterialAttribute(label);
        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing()
    {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped()
    {
        super.fireEditingStopped();
    }
}
