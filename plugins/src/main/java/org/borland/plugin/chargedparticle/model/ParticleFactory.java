package org.borland.plugin.chargedparticle.model;

import org.borland.core.model.object.EObject;
import org.borland.core.model.property.EProperty;
import org.borland.core.util.Vector3;
import org.borland.plugin.chargedparticle.ChargedParticleIds;
import org.borland.plugin.chargedparticle.model.impl.DynamicParticle;
import org.borland.plugin.chargedparticle.model.impl.FixedParticle;

import java.util.Optional;

public class ParticleFactory {

  /**
   * Method builds Particle from EObject
   * @param obj
   * @return Particle instance
   * @throws IllegalArgumentException if one of the required properties does not exists
   * @throws ClassCastException if one of the properties has wrong type
   */
  public static Particle buildParticle(EObject obj) {
    ParticleType particleType = getParticleType(obj);
    switch(particleType) {
      case Fixed:
        return buildFixedParticle(obj);
      case Dynamic:
        return buildDynamicParticle(obj);
    }

    throw new IllegalArgumentException("Unknown particle type: " + particleType.toString());
  }

  /**
   * Method finds the particleType property of EObject 'obj'
   * @param obj
   * @return Particle type
   * @throws IllegalArgumentException if property 'particle type' does not exists in 'obj
   * @throws ClassCastException if property 'particle type' has wrong type
   */
  private static ParticleType getParticleType(EObject obj) {
    Optional<EProperty> particleTypeMaybe = obj.getProperty(ChargedParticleIds.PARTICLE_TYPE_PROPERTY);
    if(!particleTypeMaybe.isPresent()) {
      throw new IllegalArgumentException(
          "Object doesn't have required field: " + ChargedParticleIds.PARTICLE_TYPE_PROPERTY);
    }
     return particleTypeMaybe.get().getValue(ParticleType.class);
  }

  /**
   * Method builds Fixed Particle from EObject
   * @param obj
   * @return Fixed particle instance
   * @throws IllegalArgumentException if one of the required properties does not exists
   * @throws IllegalArgumentException if particle type is not 'Fixed'
   * @throws ClassCastException if one of the properties has wrong type
   */
  public static FixedParticle buildFixedParticle(EObject obj) {
    ParticleType particleType = getParticleType(obj);
    if(particleType != ParticleType.Fixed) {
      throw new IllegalArgumentException(
          "Expected 'particle type' to be 'Fixed', but was: " + particleType.toString());
    }

    Optional<EProperty> chargeValueMaybe = obj.getProperty(ChargedParticleIds.CHARGE_VALUE_PROPERTY);
    Optional<EProperty> positionMaybe = obj.getProperty("POSITION");

    if(!chargeValueMaybe.isPresent()) {
      throw new IllegalArgumentException(
          "Object doesn't have required field: " + ChargedParticleIds.CHARGE_VALUE_PROPERTY);
    }
    if(!positionMaybe.isPresent()) {
      throw new IllegalArgumentException(
          "Object doesn't have required field: " + "POSITION");
    }

    return new FixedParticle(chargeValueMaybe.get().getValue(Double.class),
        positionMaybe.get().getValue(Vector3.class));
  }

  /**
   * Method builds Dynamic Particle from EObject
   * @param obj
   * @return Dynamic particle instance
   * @throws IllegalArgumentException if one of the required properties does not exists
   * @throws IllegalArgumentException if particle type is not 'Dynamic'
   * @throws ClassCastException if one of the properties has wrong type
   */
  public static DynamicParticle buildDynamicParticle(EObject obj) {
    ParticleType particleType = getParticleType(obj);
    if(particleType != ParticleType.Dynamic) {
      throw new IllegalArgumentException(
          "Expected 'particle type' to be 'Dynamic', but was: " + particleType.toString());
    }

    Optional<EProperty> chargeValueMaybe = obj.getProperty(ChargedParticleIds.CHARGE_VALUE_PROPERTY);
    Optional<EProperty> positionMaybe = obj.getProperty("POSITION");
    Optional<EProperty> massMaybe = obj.getProperty("MASS");

    if(!chargeValueMaybe.isPresent()) {
      throw new IllegalArgumentException(
          "Object doesn't have required field: " + ChargedParticleIds.CHARGE_VALUE_PROPERTY);
    }
    if(!positionMaybe.isPresent()) {
      throw new IllegalArgumentException(
          "Object doesn't have required field: " + "POSITION");
    }
    if(!massMaybe.isPresent()) {
      throw new IllegalArgumentException(
          "Object doesn't have required field: " + "MASS");
    }

    return new DynamicParticle(massMaybe.get().getValue(Double.class),
        chargeValueMaybe.get().getValue(Double.class),
        positionMaybe.get().getValue(Vector3.class));
  }
}
