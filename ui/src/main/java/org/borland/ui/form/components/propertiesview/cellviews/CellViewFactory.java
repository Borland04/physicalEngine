package org.borland.ui.form.components.propertiesview.cellviews;

import org.borland.core.util.Vector3;
import org.borland.ui.form.components.propertiesview.cellviews.impl.Vector3CellView;

public class CellViewFactory {

    public static PropertyView getCellEditor(Object value) {
//        Class<?> clazz = value.getClass();
//
//        if(clazz.in)
        if(value instanceof Vector3) {
            return new Vector3CellView((Vector3) value);
        }

        throw new RuntimeException("Not implemented yet");
    }
}
