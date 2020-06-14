package org.borland.core.util;

import org.jetbrains.annotations.NotNull;

// TODO: javadoc: immutable
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


    public Vector3 add(@NotNull Vector3 other) {
        double resultX = x + other.getX();
        double resultY = y + other.getY();
        double resultZ = z + other.getZ();
        return new Vector3(resultX, resultY, resultZ);
    }

    public Vector3 sub(@NotNull Vector3 other) {
        double resultX = x - other.getX();
        double resultY = y - other.getY();
        double resultZ = z - other.getZ();
        return new Vector3(resultX, resultY, resultZ);
    }

    /**
     * Multiply all coords at 'value'
     * <br />
     * @param value
     */
    public Vector3 mul(double value) {
        double resultX = x * value;
        double resultY = y * value;
        double resultZ = z * value;
        return new Vector3(resultX, resultY, resultZ);
    }

    public Vector3 reverse() {
        double resultX = -x;
        double resultY = -y;
        double resultZ = -z;
        return new Vector3(resultX, resultY, resultZ);
    }

    /**
     * Make an 'unit' vector from this
     * @throws ArithmeticException if vector has length == 0
     */
    public Vector3 normalize() {
        // If vector has length=0
        if(x == 0 && y == 0 && z == 0) {
            String exceptionMessage = String.format("Vector has length == 0 with x=%.4f, y=%.4f, z=%.4f.", x, y ,z);
            throw new ArithmeticException(exceptionMessage);
        }

        double length = getLength();

        double resultX = x / length;
        double resultY = y / length;
        double resultZ = z / length;
        return new Vector3(resultX, resultY, resultZ);
    }

    public double getLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

}
