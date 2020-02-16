package org.borland.core.util;

public class Vector3 implements Cloneable {

    protected double x, y, z;

    public Vector3() {
        this(0, 0, 0);
    }
    public Vector3(double x) {
        this(x, 0, 0);
    }
    public Vector3(double x, double y) {
        this(x, y, 0);
    }
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 scale(double multiplier) {
        return new Vector3(x * multiplier, y * multiplier, z * multiplier);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

}
