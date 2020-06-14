package org.borland.plugin.chargedparticle;

import org.borland.core.model.behavior.BehaviorContext;
import org.borland.core.model.behavior.EBehavior;
import org.borland.core.model.object.EObject;
import org.borland.core.model.property.EProperty;
import org.borland.core.util.Vector3;
import org.borland.plugin.chargedparticle.model.Particle;
import org.borland.plugin.chargedparticle.model.ParticleFactory;
import org.borland.plugin.chargedparticle.model.impl.DynamicParticle;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChargedParticleBehavior implements EBehavior {
  private static String CHARGED_PARTICLE_BEHAVIOR_ID = "CHARGED_PARTICLE_BEHAVIOR";
  private static String CHARGED_PARTICLE_BEHAVIOR_LABEL = "Charged particle behavior";

  @Override
  public @NotNull String getId() {
    return CHARGED_PARTICLE_BEHAVIOR_ID;
  }

  @Override
  public @NotNull String getLabel() {
    return CHARGED_PARTICLE_BEHAVIOR_LABEL;
  }

  @Override
  public void behave(@NotNull BehaviorContext context, double deltaTime) {
    try {
      behaveInternal(context, deltaTime);
    }
    catch(RuntimeException re) {
      // TODO: do not fail silently. Do, for example, a log
      return;
    }
  }

  private void behaveInternal(BehaviorContext context, double deltaTime) {
    EObject currentObject = context.getObject();
    DynamicParticle currentParticle = ParticleFactory.buildDynamicParticle(currentObject);
    List<Particle> otherParticles = context.getOthers().stream()
        .map(o -> eObjectToParticle(o))
        .filter(opt -> opt.isPresent())
        .map(opt -> opt.get())
        .collect(Collectors.toList());

    Vector3 currentParticlePosition = currentParticle.getPosition();
    Vector3 resultForce = new Vector3();
    for(Particle particle: otherParticles) {
      resultForce = resultForce.add(particle.getForceAtPoint(currentParticlePosition));
    }

    resultForce = resultForce.mul(deltaTime);
    resultForce = currentParticle.calculateAffectedForce(resultForce);

    EProperty currentVelocity = currentObject.getProperty("VELOCITY")
        .orElse(new EProperty("VELOCITY", "VELOCITY", new Vector3()));

    currentVelocity.setValue(currentVelocity.getValue(Vector3.class).add(resultForce));
    currentObject.setProperty(currentVelocity);
  }

  /**
   * Method tries to construct Particle from EObject
   * @param obj
   * @return If EObject can be converted to an Particle, Particle instance. Empty Optional otherwise
   */
  private Optional<Particle> eObjectToParticle(EObject obj) {
    try {
      return Optional.of(ParticleFactory.buildParticle(obj));
    }
    catch(RuntimeException re) {
      // TODO: do not fail silently. Do, for example, a log
      return Optional.empty();
    }
  }
}
