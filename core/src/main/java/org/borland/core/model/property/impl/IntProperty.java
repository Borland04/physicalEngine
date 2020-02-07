package org.borland.core.model.property.impl;

import org.borland.core.model.property.EProperty;
import org.jetbrains.annotations.NotNull;

public class IntProperty extends EProperty {

    public IntProperty(@NotNull String id, @NotNull String label, Integer value) {
        super(id, label, value);
    }

    @Override
    // TODO: javaDoc
    public void setValue(Object value) {
        if(!(value instanceof Integer)) {
            throw new IllegalArgumentException("Value must be an integer");
        }

        super.setValue(value);
    }

}
