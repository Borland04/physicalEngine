package org.borland.core.model.property.impl;

import org.borland.core.model.property.EProperty;
import org.jetbrains.annotations.NotNull;

public class StringProperty extends EProperty {

    public StringProperty(@NotNull String id, @NotNull String label, String value) {
        super(id, label, value);
    }

    @Override
    // TODO: javaDoc
    public void setValue(Object value) {
        if(!(value instanceof String)) {
            throw new IllegalArgumentException("Value must be a string");
        }

        super.setValue(value);
    }

}
