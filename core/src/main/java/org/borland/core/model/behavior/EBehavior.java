package org.borland.core.model.behavior;

import org.jetbrains.annotations.NotNull;

public interface EBehavior {
    @NotNull String getId();
    @NotNull String getLabel();

    /**
     * Apply behavior on specified EObject
     * @param context Container with information about CURRENT applying(such as on what object this behavior applies)
     * @param deltaTime Time, passed between current and previous iteration for this object in seconds
     */
    void behave(@NotNull BehaviorContext context, double deltaTime);
}
