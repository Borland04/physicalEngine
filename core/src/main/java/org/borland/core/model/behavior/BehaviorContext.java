package org.borland.core.model.behavior;

import org.borland.core.model.object.EObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BehaviorContext {

    private final EObject object;
    private final List<EObject> others;

    public BehaviorContext(@NotNull EObject object, @NotNull List<EObject> others) {
        this.object = object;
        this.others = others;
    }

    @NotNull
    public EObject getObject() {
        return object;
    }

    @NotNull
    public List<EObject> getOthers() {
        return others;
    }

}
