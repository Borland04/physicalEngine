package org.borland.ui.form.components.propertiesview;

import org.borland.core.model.object.EObject;
import org.borland.core.model.property.EProperty;
import org.borland.core.model.worldcontext.ObjectWorldContext;
import org.borland.ui.form.components.propertiesview.cellviews.CellViewFactory;
import org.borland.ui.model.WorldState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.util.List;
import java.util.Optional;

public class PropertiesView extends JTable {
    private final WorldState worldState;
    private final PropertiesViewModel model = new PropertiesViewModel();

    public PropertiesView(WorldState worldState) {
        super();
        this.worldState = worldState;
        initTableModel();
        initSelectionListener();
    }

    private void initTableModel() {
        this.setModel(model);
    }

    private void initSelectionListener() {
        this.worldState.getSelectionManager().subscribe(selectionManager -> {
           List<String> selectedObjects = selectionManager.getSelectedObjects();
           if(selectedObjects.size() != 1) {
               model.setEObject(null);
               return;
           }

           String selectedObjectId = selectedObjects.get(0);
           ObjectWorldContext objectWorldContext = worldState.getWorld().getWorldContext().getObjectContext();
           @NotNull Optional<EObject> maybeSelectedObject = objectWorldContext.getObject(selectedObjectId);
           EObject selectedObject = maybeSelectedObject.orElse(null);
           model.setEObject(selectedObject);
        });
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        if(column == 0) {
            return super.getCellEditor(row, column);
        }

        Object value = getValueAt(row, column);
        return CellViewFactory.getCellEditor(value);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        if(column == 0) {
            return super.getCellRenderer(row, column);
        }

        Object value = getValueAt(row, column);
        return CellViewFactory.getCellEditor(value);
    }
}

class PropertiesViewModel extends AbstractTableModel {
    private String[] columnNames = new String[] { "Property", "Value" };
    private @Nullable EObject eobject;

    @Override
    public int getRowCount() {
        return eobject == null
                ? 0
                : eobject.getProperties().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(eobject == null) {
            return null;
        }

        List<EProperty> properties = eobject.getProperties();
        EProperty property = properties.get(rowIndex);
        return columnIndex == 0
                ? property.getId()
                : property.getValue(Object.class);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // TODO: implement property change
        this.fireTableDataChanged();
    }

    public void setEObject(@Nullable EObject eobject) {
        this.eobject = eobject;
        if(this.eobject != null) {
            this.eobject.subscribe(object -> this.fireTableDataChanged());
        }

        this.fireTableDataChanged();
    }
}
