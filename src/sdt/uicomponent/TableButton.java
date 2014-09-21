package sdt.uicomponent;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

public class TableButton extends JButton implements TableCellRenderer {

    public TableButton()
    {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
          String label = (value == null) ? "" : value.toString();
          boolean isCurve = label.endsWith("Curve") || label.startsWith("Curve Data");

          if(isCurve)
          {
              if (isSelected)
              {
                  this.setForeground(table.getSelectionForeground());
                  this.setBackground(table.getSelectionBackground());
              }
              else
              {
                  this.setForeground(table.getForeground());
                  this.setBackground(table.getBackground());
              }
              this.setText(label);
              return this;
          }
          else
          {
              JLabel onlyLabel = new JLabel(label);
              onlyLabel.setHorizontalAlignment(JLabel.CENTER);
              return onlyLabel;
          }
      }

}
