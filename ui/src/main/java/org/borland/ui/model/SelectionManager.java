package org.borland.ui.model;

import com.sun.istack.internal.NotNull;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.borland.ui.util.ArrayCompareUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// TODO: javadoc
public class SelectionManager extends Observable<SelectionManager> {
    private static Logger logger = LogManager.getLogger(SelectionManager.class);

    private final List<Observer<? super SelectionManager>> observers = new LinkedList<>();
    private final List<String> selectedObjects = new LinkedList<>();

    public void clearSelection() {
        logger.debug("Clear selection");
        selectedObjects.clear();
        onChanged();
    }

    // TODO: javadoc -- if new list is the same, event won't be triggered
    public void setSelectedObjects(List<String> selectedObjects) {
        logger.debug("Old selection: {}", this.selectedObjects);
        logger.debug("New selection: {}", selectedObjects);

        boolean doesNewSelectionTheSame =
                ArrayCompareUtils.areUnorderedListsEqual(selectedObjects, this.selectedObjects);
        if(doesNewSelectionTheSame) {
            // There's nothing we should do
            logger.debug("New selection is the same as before. Skip any work and do not notify observers");
            return;
        }

        setSelectedObjectsInternal(selectedObjects);
    }

    // TODO: javadoc
    public void setSelectedObjectsInternal(List<String> selectedObjects) {
        this.clearSelection();
        this.selectedObjects.addAll(selectedObjects);
        logger.debug("Selection was changed. New selection: {}", this.selectedObjects);

        onChanged();
    }

    public void addSelection(@NotNull String selection) {
        logger.debug("Select item '{}'", selection);
        if(isSelected(selection)) {
            // There's nothing we should do
            logger.debug("Item '{}' was selected already. " +
                    "Skip any work and do not notify observers", selection);
            return;
        }

        selectedObjects.add(selection);
        logger.debug("Item '{}' was added to a selection list", selection);
        onChanged();
    }

    public void removeSelection(@NotNull String selection) {
        logger.debug("Unselect item '{}'", selection);
        if(!isSelected(selection)) {
            // There's nothing we should do
            logger.debug("Item '{}' wasn't selected. " +
                    "Skip any work and do not notify observers", selection);
            return;
        }

        selectedObjects.removeIf(id -> selection.equals(id));
        logger.debug("Item '{}' was removed from a selection list", selection);
        onChanged();
    }

    public void toggleSelection(@NotNull String selection) {
        logger.debug("Toggle selection of item '{}'", selection);
        logger.debug("Is item selected: {}", isSelected(selection));
        if(isSelected(selection)) {
            removeSelection(selection);
        }
        else {
            addSelection(selection);
        }
    }

    public boolean isSelected(@NotNull String id) {
        boolean isSelected = selectedObjects.stream()
                .anyMatch(selectedId -> id.equals(selectedId));
        logger.trace("Is item '{}' selected: {}", id, isSelected);
        return isSelected;
    }

    public List<String> getSelectedObjects() {
        LinkedList<String> copy = new LinkedList<>();
        Collections.copy(copy, selectedObjects);
        logger.debug("Selected objects: {}", copy);
        return copy;
    }

    @Override
    protected void subscribeActual(@NonNull Observer<? super SelectionManager> observer) {
        logger.debug("Subscribe on SelectionManager");
        observers.add(observer);
    }

    private void onChanged() {
        logger.debug("SelectionManager was changed. Notify all observers");
        observers.stream()
                .forEach(observer -> observer.onNext(this));
    }
}
