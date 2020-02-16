package org.borland.plugin.velocity;

import org.borland.core.model.plugin.BehaviorManager;
import org.borland.core.model.plugin.EPlugin;

public class VelocityPlugin implements EPlugin {

    @Override
    public void registerBehavior(BehaviorManager manager) {
        manager.addBehavior(new VelocityBehavior());
    }
}
