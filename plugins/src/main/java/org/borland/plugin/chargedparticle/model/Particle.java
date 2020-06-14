package org.borland.plugin.chargedparticle.model;

import org.borland.core.util.Vector3;

public interface Particle {
  // TODO: javadoc: force at the unit positive particle
  Vector3 getForceAtPoint(Vector3 point);
}
