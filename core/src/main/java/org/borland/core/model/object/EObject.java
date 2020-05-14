package org.borland.core.model.object;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.borland.core.model.property.EProperty;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EObject extends Observable<EObject> {

    private static final Logger logger = LogManager.getLogger(EObject.class);
    private final List<Observer<? super EObject>> observers = new LinkedList<>();

    private String id = "";
    private List<EProperty> properties = new LinkedList<>();

    public EObject(@NotNull String id) {
        this.id = id;
        logger.trace("Created EObject with id {}", id);
    }

    public Optional<EProperty> getProperty(@NotNull String key) {
        logger.trace("Trying to get property with id '{}' from object '{}'", key, id);
        return properties.stream()
                .filter(property -> key.equals(property.getId()))
                .findAny();
    }

    public void setProperty(@NotNull EProperty property) {
        String propertyId = property.getId();
        logger.trace("Trying to set property with id '{}' to object '{}'", propertyId, id);

        if(getProperty(propertyId).isPresent()) {
            logger.warn("Property of object '{}' with id '{}' already exists." +
                    "Replace it with new value", propertyId, id);

            removeProperty(propertyId);
        }

        properties.add(property);
        logger.debug("Property with id '{}' successfully set for object '{}'", propertyId, id);

        onChanged();
    }

    public boolean hasProperty(@NotNull String id) {
        return properties.stream().anyMatch(prop -> id.equals(prop.getId()));
    }

    public void removeProperty(@NotNull String id) {
        boolean hasRequiredProp = properties.stream()
                .anyMatch(prop -> id.equals(prop.getId()));

        if(hasRequiredProp) {
            properties.removeIf(prop -> id.equals(prop.getId()));
            onChanged();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        logger.debug("Change id: '{}' -> '{}'", this.id, id);
        this.id = id;
        onChanged();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EObject eObject = (EObject) o;
        return id.equals(eObject.id) &&
                properties.equals(eObject.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, properties);
    }

    @Override
    public String toString() {
        return "EObject{" +
                "id='" + id + '\'' +
                ", properties=" + properties +
                '}';
    }

    @Override
    protected void subscribeActual(@NonNull Observer<? super EObject> observer) {
        observers.add(observer);
    }

    private void onChanged() {
        logger.debug("Object '{}' was changed. Emit 'onNext' for all observers", id);
        observers.stream()
                .forEach(observer -> observer.onNext(this));
    }
}
