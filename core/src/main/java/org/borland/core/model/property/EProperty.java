package org.borland.core.model.property;

// TODO: make this a plain class -- No, we need inheritation and type separation(Like StringProperty and etc)
// TODO: Use Abstract Factory with 'get' and 'getDefault' methods???
// TODO: implement StringProperty, IntProperty and etc

/**
 * You could inherit this class to add some rules on getter/setter
 * Examples: @StringProperty, @IntProperty
 */
public class EProperty {
    private String id;
    private String label;
    private Object value;

    public EProperty(String id, String label, Object value) {
        this.id = id;
        this.label = label;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public <T> T getValue(Class<T> clazz) {
        return (T) value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
