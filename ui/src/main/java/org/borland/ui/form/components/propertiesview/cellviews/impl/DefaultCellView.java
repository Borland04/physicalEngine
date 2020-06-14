package org.borland.ui.form.components.propertiesview.cellviews.impl;

import org.borland.ui.form.components.propertiesview.cellviews.PropertyView;

import javax.swing.*;
import java.awt.*;

public class DefaultCellView extends PropertyView {
  JLabel label = new JLabel("???");

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    return label;
  }

  @Override
  public Object getCellEditorValue() {
    return null;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    return label;
  }

}
