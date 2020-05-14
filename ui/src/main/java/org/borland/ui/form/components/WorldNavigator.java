package org.borland.ui.form.components;

import org.borland.core.model.worldcontext.ObjectWorldContext;
import org.borland.ui.model.SelectionManager;
import org.borland.ui.model.WorldState;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

// TODO: javadoc
// TODO: logging
public class WorldNavigator extends JList<String> {
    private WorldState worldState;

    public WorldNavigator(WorldState worldState) {
        super();
        this.worldState = worldState;

        registerComponentEvents();
        doSubscribe();
    }

    private void registerComponentEvents() {
        this.addListSelectionListener(this::onSelectionChanged);
    }

    private void onSelectionChanged(ListSelectionEvent e) {
        List<String> selectedIds = this.getSelectedValuesList();
        // TODO: check if it changed at all
        updateSelectionState(selectedIds);
    }

    private void updateSelectionState(List<String> selectedIds) {
        worldState.getSelectionManager().setSelectedObjects(selectedIds);
    }


    private void doSubscribe() {
        worldState.getWorld().getWorldContext().getObjectContext().subscribe(this::updateWorldNavigator);
        worldState.getSelectionManager().subscribe(this::updateSelectedItems);
    }

    private void updateWorldNavigator(ObjectWorldContext objectContext) {
        // TODO: atomize it: put all logic into one "SwingUtils.invokeLater"
        String[] ids = objectContext.getObjects().stream()
                .map(object -> object.getId())
                .toArray(String[]::new);

        SwingUtilities.invokeLater(() -> this.setListData(ids));
        // TODO: selection.clear???
    }

    private void updateSelectedItems(SelectionManager selectionManager) {
        // TODO: implement
    }
}
