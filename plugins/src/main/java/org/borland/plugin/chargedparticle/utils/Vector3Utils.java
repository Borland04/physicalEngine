package org.borland.plugin.chargedparticle.utils;

import org.borland.core.util.Vector3;

public class Vector3Utils {

  // TODO: javadoc: always positive value
  public static double distanceTo(Vector3 start, Vector3 end) {
    return Math.sqrt(
        Math.pow(start.getX() - end.getX(), 2) +
        Math.pow(start.getY() - end.getY(), 2) +
        Math.pow(start.getZ() - end.getZ(), 2)
    );
  }

}
