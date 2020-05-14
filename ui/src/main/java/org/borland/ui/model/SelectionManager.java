package org.borland.ui.model;

import com.sun.istack.internal.NotNull;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// TODO: logging
// TODO: javadoc
public class SelectionManager extends Observable<SelectionManager> {
    private final List<Observer<? super SelectionManager>> observers = new LinkedList<>();
    private final List<String> selectedObjects = new LinkedList<>();

    public void clearSelection() {
        selectedObjects.clear();
        onChanged();
    }

    public void setSelectedObjects(List<String> selectedObjects) {
        // TODO: check if it was changed at all
        this.clearSelection();
        this.selectedObjects.addAll(selectedObjects);
        onChanged();
    }

    public void addSelection(@NotNull String selection) {
        if(isSelected(selection)) {
            // There's nothing we should do
            // TODO: logging
            return;
        }

        selectedObjects.add(selection);
        onChanged();
    }

    public void removeSelection(@NotNull String selection) {
        if(!isSelected(selection)) {
            // There's nothing we should do
            // TODO: logging
            return;
        }

        selectedObjects.removeIf(id -> selection.equals(id));
        onChanged();
    }

    public void toggleSelection(@NotNull String selection) {
        if(isSelected(selection)) {
            removeSelection(selection);
        }
        else {
            addSelection(selection);
        }
    }

    public boolean isSelected(@NotNull String id) {
        return selectedObjects.stream()
            .anyMatch(selectedId -> id.equals(selectedId));
    }

    public List<String> getSelectedObjects() {
        LinkedList<String> copy = new LinkedList<>();
        Collections.copy(copy, selectedObjects);
        return copy;
    }

    @Override
    protected void subscribeActual(@NonNull Observer<? super SelectionManager> observer) {
        observers.add(observer);
    }

    private void onChanged() {
        observers.stream()
                .forEach(observer -> observer.onNext(this));
    }
}
