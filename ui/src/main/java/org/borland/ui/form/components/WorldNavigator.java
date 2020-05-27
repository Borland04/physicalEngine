package org.borland.ui.form.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.borland.core.model.object.EObject;
import org.borland.core.model.worldcontext.ObjectWorldContext;
import org.borland.ui.model.SelectionManager;
import org.borland.ui.model.WorldState;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: javadoc
public class WorldNavigator extends JList<EObject> {
    private static Logger logger = LogManager.getLogger(WorldNavigator.class);

    private WorldState worldState;

    private WorldNavigatorModel dataModel;
    private WorldNavigatorSelectionModel selectionModel;

    public WorldNavigator(WorldState worldState) {
        super();
        this.worldState = worldState;
        initModels(this.worldState);

        worldState.getWorld().getWorldContext().getObjectContext().subscribe(objCtx -> this.repaint());
    }

    private void initModels(WorldState worldState) {
        ObjectWorldContext objectWorldContext = worldState.getWorld().getWorldContext().getObjectContext();
        dataModel = new WorldNavigatorModel(objectWorldContext);
        this.setModel(dataModel);

        selectionModel = new WorldNavigatorSelectionModel(worldState.getSelectionManager(), dataModel);
        this.setSelectionModel(selectionModel);
    }
}

class WorldNavigatorSelectionModel implements ListSelectionModel {

    private SelectionManager selectionManager;
    private ListModel<EObject> dataModel;
    private List<ListSelectionListener> listeners = new LinkedList<>();

    private int anchor = -1;
    private int lead = -1;
    private boolean isAdjusting = false;
    private int selectionMode = 0;

    public WorldNavigatorSelectionModel(SelectionManager selectionManager, ListModel<EObject> dataModel) {
        this.selectionManager = selectionManager;
        this.dataModel = dataModel;
    }

    @Override
    public void setSelectionInterval(int index0, int index1) {
        List<String> selectedIds = getValuesAtInterval(index0, index1).stream()
                .map(obj -> obj.getId())
                .collect(Collectors.toList());

        selectionManager.setSelectedObjects(selectedIds);
        setAnchorSelectionIndex(index0);
        setLeadSelectionIndex(index1);
        fireSelectionChanged();
    }

    private List<EObject> getValuesAtInterval(int index0, int index1) {
        int minIndex = Math.min(index0, index1);
        int maxIndex = Math.max(index0, index1);

        List<EObject> selectedObjects = new LinkedList<>();
        for(int i = minIndex; i <= maxIndex; i++) {
            EObject obj = dataModel.getElementAt(i);
            selectedObjects.add(obj);
        }

        return selectedObjects;
    }

    @Override
    public void addSelectionInterval(int index0, int index1) {
        getValuesAtInterval(index0, index1).stream()
                .map(obj -> obj.getId())
                .forEach(id -> selectionManager.addSelection(id));

        setAnchorSelectionIndex(index0);
        setLeadSelectionIndex(index1);
        fireSelectionChanged();
    }

    @Override
    public void removeSelectionInterval(int index0, int index1) {
        getValuesAtInterval(index0, index1).stream()
                .map(obj -> obj.getId())
                .forEach(id -> selectionManager.removeSelection(id));

        setAnchorSelectionIndex(index0);
        setLeadSelectionIndex(index1);
        fireSelectionChanged();
    }

    @Override
    public int getMinSelectionIndex() {
        return dataModel.getSize() > 0 ? 0 : -1;
    }

    @Override
    public int getMaxSelectionIndex() {
        int modelSize = dataModel.getSize();
        return modelSize - 1;
    }

    @Override
    public boolean isSelectedIndex(int index) {
        String idToCheck = dataModel.getElementAt(index).getId();
        return selectionManager.isSelected(idToCheck);
    }

    @Override
    public int getAnchorSelectionIndex() {
        return anchor;
    }

    @Override
    public void setAnchorSelectionIndex(int index) {
        anchor = index;
    }

    @Override
    public int getLeadSelectionIndex() {
        return lead;
    }

    @Override
    public void setLeadSelectionIndex(int index) {
        lead = index;
    }

    @Override
    public void clearSelection() {
        selectionManager.clearSelection();
    }

    @Override
    public boolean isSelectionEmpty() {
        return selectionManager.getSelectedObjects().size() > 0;
    }

    @Override
    public void insertIndexInterval(int index, int length, boolean before) {
        // TODO: ???
    }

    @Override
    public void removeIndexInterval(int index0, int index1) {
        // TODO: ???
    }

    @Override
    public void setValueIsAdjusting(boolean valueIsAdjusting) {
        isAdjusting = valueIsAdjusting;
    }

    @Override
    public boolean getValueIsAdjusting() {
        return isAdjusting;
    }

    @Override
    public void setSelectionMode(int selectionMode) {
    this.selectionMode = selectionMode;
    }

    @Override
    public int getSelectionMode() {
        return selectionMode;
    }

    @Override
    public void addListSelectionListener(ListSelectionListener x) {
        listeners.add(x);
    }

    @Override
    public void removeListSelectionListener(ListSelectionListener x) {
        listeners.remove(x);
    }

    private void fireSelectionChanged() {
        listeners.forEach(l -> l.valueChanged(new ListSelectionEvent(this, 0, getMaxSelectionIndex(), getValueIsAdjusting())));
    }
}

class WorldNavigatorModel extends AbstractListModel<EObject> {

    private final ObjectWorldContext objectWorldContext;

    public WorldNavigatorModel(ObjectWorldContext objectWorldContext) {
        this.objectWorldContext = objectWorldContext;
        objectWorldContext.subscribe(objCtx -> onDataChanged());
    }

    private void onDataChanged() {
        int itemsSize = this.objectWorldContext.getObjects().size();
        EventQueue.invokeLater(() -> this.fireContentsChanged(this, 0, itemsSize - 1));
    }

    @Override
    public int getSize() {
        return objectWorldContext.getObjects().size();
    }

    @Override
    public EObject getElementAt(int index) {
        return objectWorldContext.getObjects().get(index);
    }

}
