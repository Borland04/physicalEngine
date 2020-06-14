package org.borland.plugin.chargedparticle.model.impl;

import org.borland.core.util.Vector3;
import org.borland.plugin.chargedparticle.model.Particle;
import org.borland.plugin.chargedparticle.utils.Constants;

public class DynamicParticle implements Particle {

  private double mass;
  private double charge;
  private Vector3 position;

  public DynamicParticle(double mass, double charge, Vector3 position) {
    this.mass = mass;
    this.charge = charge;
    this.position = position;
  }

  @Override
  public Vector3 getForceAtPoint(Vector3 point) {
    return new FixedParticle(charge, position).getForceAtPoint(point);
  }

  public Vector3 calculateAffectedForce(Vector3 force) {
    if(charge == 0) {
      return new Vector3();
    }

    Vector3 resultMovement = force;
    resultMovement = resultMovement.mul(charge * Constants.E);
    resultMovement = resultMovement.mul(1/(mass * Constants.M));

    return resultMovement;
  }

  public double getMass() {
    return mass;
  }

  public double getCharge() {
    return charge;
  }

  public Vector3 getPosition() {
    return position;
  }
}
