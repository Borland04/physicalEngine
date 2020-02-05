package org.borland.core.model.property;

// TODO: make this a plain class -- No, we need inheritation and type separation(Like StringProperty and etc)
// TODO: Use Abstract Factory with 'get' and 'getDefault' methods???
// TODO: implement StringProperty, IntProperty and etc

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * You could inherit this class to add some rules on getter/setter
 * Examples: @StringProperty, @IntProperty
 */
public class EProperty {

    private static final Logger logger = LogManager.getLogger(EProperty.class);

    private String id;
    private String label;
    private Object value;

    public EProperty(String id, String label, Object value) {
        this.id = id;
        this.label = label;
        this.value = value;

        logger.trace("Create Property with id: '{}', label: '{}', value: '{}'", id, label, value);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        logger.debug("Change property's id: '{}' -> '{}'", this.id, id);
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        logger.debug("Change property's label: '{}' -> '{}'", this.label, label);
        this.label = label;
    }

    public <T> T getValue(Class<T> clazz) {
        return (T) value;
    }

    public void setValue(Object value) {
        logger.debug("Change property's value: '{}' -> '{}'", this.value, value);
        this.value = value;
    }

}
