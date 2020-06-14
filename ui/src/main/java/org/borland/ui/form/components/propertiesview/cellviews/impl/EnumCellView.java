package org.borland.ui.form.components.propertiesview.cellviews.impl;

import org.borland.ui.form.components.propertiesview.cellviews.PropertyView;

import javax.swing.*;
import java.awt.*;

public class EnumCellView extends PropertyView {
  JComboBox comboBox = new JComboBox();
  Object value;

  {
    comboBox.addActionListener(listener -> {
      Object selectedItem = comboBox.getSelectedItem();
      this.value = selectedItem;
      this.fireEditingStopped();
    });
  }

  public EnumCellView(Object initialValue) {
    this.value = initialValue;

    Object[] possibleValues = value.getClass().getEnumConstants();
    comboBox.setModel(new DefaultComboBoxModel(possibleValues));

    comboBox.setSelectedItem(this.value);
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    this.value = value;
    comboBox.setSelectedItem(this.value);
    return comboBox;
  }

  @Override
  public Object getCellEditorValue() {
    return value;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    return getTableCellEditorComponent(table, value, isSelected, row, column);
  }
}
