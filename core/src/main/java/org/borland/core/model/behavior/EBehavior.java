package org.borland.core.model.behavior;

import org.jetbrains.annotations.NotNull;

public interface EBehavior {
    @NotNull String getId();
    @NotNull String getLabel();
    void behave(@NotNull BehaviorContext context, long deltaTime);
}
