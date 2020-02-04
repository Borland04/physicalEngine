package org.borland.core.model.object;

import org.borland.core.model.property.EProperty;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class EObject {
    private String id = "";
    private List<EProperty> properties = new LinkedList<>();

    public EObject(@NotNull String id) {
        this.id = id;
    }

    public abstract String getTypeLabel();

    public Optional<EProperty> getProperty(@NotNull String key) {
        return properties.stream()
                .filter(property -> key.equals(property.getId()))
                .findAny();
    }

    public void setProperty(@NotNull EProperty property) {
        if(getProperty(property.getId()).isPresent()) {
            throw new IllegalArgumentException("Property with the same id already exists");
        }

        properties.add(property);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
