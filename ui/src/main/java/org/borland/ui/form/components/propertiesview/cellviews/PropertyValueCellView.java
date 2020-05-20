package org.borland.ui.form.components.propertiesview.cellviews;

import org.borland.core.model.property.EProperty;

import javax.swing.*;
import java.awt.*;

public class PropertyValueCellView extends PropertyView {

    private PropertyView internalCellEditor;
    private EProperty property;

    // TODO: javaDoc - could throw an exception
    public static PropertyValueCellView create(EProperty property) {
        PropertyView editor = CellViewFactory.getCellEditor(property);
        return new PropertyValueCellView(property, editor);
    }

    private PropertyValueCellView(EProperty property, PropertyView editor) {
        this.property = property;
        internalCellEditor = editor;
    }

    @Override
    public Object getCellEditorValue() {
        return internalCellEditor.getCellEditorValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return internalCellEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return internalCellEditor.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
