package org.borland.plugin.chargedparticle;

import org.borland.core.model.plugin.BehaviorManager;
import org.borland.core.model.plugin.EPlugin;

public class ChargedParticlePlugin implements EPlugin {

  @Override
  public void registerBehavior(BehaviorManager manager) {
    manager.addBehavior(new ChargedParticleBehavior());
  }

}
