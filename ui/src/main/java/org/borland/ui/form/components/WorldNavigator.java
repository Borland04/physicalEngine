package org.borland.ui.form.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.borland.core.model.object.EObject;
import org.borland.core.model.worldcontext.ObjectWorldContext;
import org.borland.ui.model.SelectionManager;
import org.borland.ui.model.WorldState;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// TODO: javadoc
public class WorldNavigator extends JList<String> {
    private static Logger logger = LogManager.getLogger(WorldNavigator.class);

    private WorldState worldState;

    public WorldNavigator(WorldState worldState) {
        super();
        this.worldState = worldState;

        registerComponentEvents();
        doSubscribe();
    }

    private void registerComponentEvents() {
        logger.trace("Register event 'WorldNavigator.onSelectionChanged'");
        this.addListSelectionListener(this::onSelectionChanged);
    }

    private void onSelectionChanged(ListSelectionEvent e) {
        // We don't care about dead loop(onSelectionChanged -> selectionManager.changed -> onSelectionChanged -> ...)
        // Because 'selectionManager' has a blocker for this kind of situation
        logger.trace("'WorldNavigator.onSelectionChanged' event was fired. Update 'selection state'");
        updateSelectionState();
    }

    private void updateSelectionState() {
        List<String> selectedIds = this.getSelectedValuesList();
        worldState.getSelectionManager().setSelectedObjects(selectedIds);
    }


    private void doSubscribe() {
        worldState.getWorld().getWorldContext().getObjectContext().subscribe(this::updateWorldNavigator);
        logger.trace("Subscribe on 'object context'");
        // TODO: causes NPE???
        worldState.getSelectionManager().subscribe(selectionManager -> this.updateSelectedItems(selectionManager));
        logger.trace("Subscribe on 'selection manager'");
    }

    private void updateWorldNavigator(ObjectWorldContext objectContext) {
        SwingUtilities.invokeLater(() -> {
            updateWorldNavigatorInternal(objectContext);
        });
    }

    private void updateWorldNavigatorInternal(ObjectWorldContext objectContext) {
        String[] ids = objectContext.getObjects().stream()
                .map(EObject::getId)
                .toArray(String[]::new);

        logger.debug("Update world navigator values. " +
                "Old: {}, new: {}",getValues(), objectContext.getObjects());
        this.setListData(ids);
        // TODO: selection.clear???
    }

    private void updateSelectedItems(SelectionManager selectionManager) {
        List<String> allNavigatorValues = getValues();
        logger.debug("All navigator's values(ordered): {}", allNavigatorValues);
        List<String> valuesToSelect = selectionManager.getSelectedObjects();
        logger.debug("Values to select: {}", valuesToSelect);

        List<Boolean> shouldSelectMap = getSelectionMap(allNavigatorValues, valuesToSelect);
        updateSelectedItems(shouldSelectMap);
    }

    private List<String> getValues() {
        ListModel<String> model = getModel();
        int modelSize = model.getSize();
        ArrayList<String> result = new ArrayList<>(modelSize);
        for(int i = 0; i < modelSize; i++) {
            result.add(model.getElementAt(i));
        }

        logger.trace("All WorldNavigator's values: {}", result);
        return result;
    }

    private List<Boolean> getSelectionMap(List<String> orderedValues, List<String> valuesToSelect) {
        return orderedValues.stream()
                .map(value -> valuesToSelect.stream().anyMatch(value::equals))
                .collect(Collectors.toList());
    }

    private void updateSelectedItems(List<Boolean> selectionMap) {
        int[] indicesToSelect = findIndicesToSelect(selectionMap);
        logger.debug("Indices of items, that should be selected: {}", Arrays.asList(indicesToSelect));
        this.setSelectedIndices(indicesToSelect);
    }

    private int[] findIndicesToSelect(List<Boolean> selectionMap) {
        int selectedItemsCount = (int) selectionMap.stream()
                .filter(v -> v)
                .count();

        int[] result = new int[selectedItemsCount];
        int resultIndex = 0;
        for(int i = 0; i < selectionMap.size(); i++) {
            if(selectionMap.get(i)) {
                result[resultIndex] = i;
                resultIndex++;
            }
        }

        return result;
    }
}
