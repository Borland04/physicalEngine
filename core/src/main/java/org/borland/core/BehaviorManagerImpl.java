package org.borland.core;

import org.borland.core.model.behavior.EBehavior;
import org.borland.core.model.plugin.BehaviorManager;
import org.borland.core.model.worldcontext.WorldContext;

public class BehaviorManagerImpl implements BehaviorManager {

    private WorldContext worldContext;

    public BehaviorManagerImpl(WorldContext worldContext) {
        this.worldContext = worldContext;
    }

    // TODO: javaDoc
    @Override
    public void addBehavior(EBehavior behavior) {
        worldContext.getBehaviorContext().addBehavior(behavior);
    }
}
