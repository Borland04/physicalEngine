package org.borland.plugin.chargedparticle.model.impl;

import org.borland.core.util.Vector3;
import org.borland.plugin.chargedparticle.model.Particle;
import org.borland.plugin.chargedparticle.utils.Vector3Utils;

// TODO: javadoc
public class FixedParticle implements Particle {
  private double charge;
  private Vector3 position;

  private static double approximation = 0.00001;

  public FixedParticle(double charge, Vector3 position) {
    this.charge = charge;
    this.position = position;
  }

  @Override
  public Vector3 getForceAtPoint(Vector3 point) {
    double distance = Vector3Utils.distanceTo(this.position, point);

    // If point is same as this particle position
    if(distance < approximation) {
      return new Vector3();
    }

    //Find force value
    double forceValue = charge / Math.pow(distance, 2);

    // Find vector that affects on point
    // It is important that 'diff' = 'point' - 'this position'
    double diffX = point.getX() - position.getX();
    double diffY = point.getY() - position.getY();
    double diffZ = point.getZ() - position.getZ();
    Vector3 vector = new Vector3(diffX, diffY, diffZ);
    vector = vector.normalize();
    vector = vector.mul(forceValue);

    return vector;
  }
}
