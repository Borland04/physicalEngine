package org.borland.ui.form.components.propertiesview.cellviews;

import org.borland.core.util.Vector3;
import org.borland.ui.form.components.propertiesview.cellviews.impl.DefaultCellView;
import org.borland.ui.form.components.propertiesview.cellviews.impl.DoubleCellView;
import org.borland.ui.form.components.propertiesview.cellviews.impl.EnumCellView;
import org.borland.ui.form.components.propertiesview.cellviews.impl.Vector3CellView;

public class CellViewFactory {

    public static PropertyView getCellView(Object value) {
//        Class<?> clazz = value.getClass();
//
//        if(clazz.in)
        if(value instanceof Double) {
            return new DoubleCellView((Double) value);
        }
        if(value instanceof Vector3) {
            return new Vector3CellView((Vector3) value);
        }
        if(value instanceof Enum) {
            return new EnumCellView(value);
        }

        return new DefaultCellView();
    }
}
