package org.borland.ui.form.components.propertiesview.cellviews.impl;

import org.borland.core.util.Vector3;
import org.borland.ui.form.components.propertiesview.cellviews.PropertyView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class DoubleCellView extends PropertyView {
  JTextField field = new JTextField();
  Double value;

  {
    field.addActionListener((ActionEvent e) -> {
      String serializedDouble = field.getText();
      try {
        this.value = Double.parseDouble(serializedDouble);
        this.fireEditingStopped();
      }
      catch(Exception ex) {
        // TODO: message box
        this.fireEditingCanceled();
      }
    });
  }

  public DoubleCellView(Double initialValue) {
    this.value = initialValue;
  }

  @Override
  public JTextField getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    this.value = (Double) value;
    String serializedDouble = Double.toString(this.value);
    field.setText(serializedDouble);
    return field;
  }

  @Override
  public Object getCellEditorValue() {
    return value;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    JTextField result = getTableCellEditorComponent(table, value, isSelected, row, column);
    if(hasFocus) {
      result.grabFocus();
    }

    return result;
  }
}
