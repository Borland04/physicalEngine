package org.borland.core.model.property.impl;

import org.borland.core.model.property.EProperty;
import org.jetbrains.annotations.NotNull;

// TODO: factory method, because value should be checked before set it(negative values should trigger exception throwing)
public class PositiveIntProperty extends IntProperty {

    public PositiveIntProperty(@NotNull String id, @NotNull String label, Integer value) {
        super(id, label, value);
    }

    @Override
    public void setValue(Object value) {

        super.setValue(value);
    }
}
