package org.borland.ui.form.components.propertiesview.cellviews.impl;

import org.borland.core.util.Vector3;
import org.borland.ui.form.components.propertiesview.cellviews.PropertyView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class Vector3CellView extends PropertyView {
    JTextField field = new JTextField();
    Vector3 value;

    public Vector3CellView(Vector3 initialValue) {
        this.value = initialValue;
    }

    public Vector3CellView() {
        field.addActionListener((ActionEvent e) -> {
            String serializedVector = field.getText();
            Double[] values = Arrays.stream(serializedVector.split(";"))
                    .map(String::trim)
                    .map(Double::parseDouble)
                    .toArray(Double[]::new);

            if(values.length != 3) {
                // TODO: throw error?
            }

            value = new Vector3(values[0], values[1], values[2]);
        });
    }

    @Override
    public Object getCellEditorValue() {
        return value;
    }


    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.value = (Vector3) value;
        String serializedVector = String.format("%f; %f; %f", this.value.getX(), this.value.getY(), this.value.getZ());
        field.setText(serializedVector);
        return field;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
