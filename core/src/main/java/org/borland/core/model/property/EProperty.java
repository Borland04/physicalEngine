package org.borland.core.model.property;

// TODO: make this a plain class -- No, we need inheritation and type separation(Like StringProperty and etc)
// TODO: Use Abstract Factory with 'get' and 'getDefault' methods???
// TODO: implement StringProperty, IntProperty and etc

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.borland.core.model.object.EObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * You could inherit this class to add some rules on getter/setter
 * Examples: @StringProperty, @IntProperty
 */
public class EProperty implements org.borland.core.util.Observable<EProperty> {

    private static final Logger logger = LogManager.getLogger(EProperty.class);

    private final Subject<EProperty> subject;

    private String id;
    private String label;
    private Object value;

    public EProperty(@NotNull String id, @NotNull String label, Object value) {
        this.id = id;
        this.label = label;
        this.value = value;

        subject = PublishSubject.create();

        logger.trace("Create Property with id: '{}', label: '{}', value: '{}'", id, label, value);
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        logger.debug("Change property's id: '{}' -> '{}'", this.id, id);
        this.id = id;
        onChange();
    }

    @NotNull
    public String getLabel() {
        return label;
    }

    public void setLabel(@NotNull String label) {
        logger.debug("Change property's label: '{}' -> '{}'", this.label, label);
        this.label = label;
        onChange();
    }

    public <T> T getValue(Class<T> clazz) {
        return (T) value;
    }

    public void setValue(Object value) {
        logger.debug("Change property's value: '{}' -> '{}'", this.value, value);
        this.value = value;
        onChange();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EProperty eProperty = (EProperty) o;
        return id.equals(eProperty.id) &&
                label.equals(eProperty.label) &&
                Objects.equals(value, eProperty.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, value);
    }

    @Override
    public String toString() {
        return "EProperty{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public void subscribe(Consumer<EProperty> consumer) {
        subject.subscribe(consumer);
    }

    private void onChange() {
        subject.onNext(this);
    }
}
