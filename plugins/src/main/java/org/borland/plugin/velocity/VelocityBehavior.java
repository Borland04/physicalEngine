package org.borland.plugin.velocity;

import org.borland.core.model.behavior.BehaviorContext;
import org.borland.core.model.behavior.EBehavior;
import org.borland.core.model.object.EObject;
import org.borland.core.util.Vector3;
import org.jetbrains.annotations.NotNull;

public class VelocityBehavior implements EBehavior {

    public static final String ID = "VELOCITY";
    public static final String LABEL = "Velocity";

    @Override
    public @NotNull String getId() {
        return ID;
    }

    @Override
    public @NotNull String getLabel() {
        return LABEL;
    }

    @Override
    public void behave(@NotNull BehaviorContext context, double deltaTime) {
        EObject obj = context.getObject();
        obj.getProperty("VELOCITY").ifPresent(velocity -> {
            Vector3 v = velocity.getValue(Vector3.class);

            obj.getProperty("POSITION")
                    .map(pos -> {
                        Vector3 p = pos.getValue(Vector3.class);

                        Vector3 scaledVelocity = v.scale(deltaTime);
                        Vector3 updatedPosition = new Vector3(p.getX() + scaledVelocity.getX(),
                                p.getY() + scaledVelocity.getY(), p.getZ() + scaledVelocity.getZ());

                        pos.setValue(updatedPosition);
                        return pos;
                    })
                    .ifPresent(pos -> obj.setProperty(pos));
        });
    }

}
