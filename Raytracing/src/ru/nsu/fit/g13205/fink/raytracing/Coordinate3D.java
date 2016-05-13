package ru.nsu.fit.g13205.fink.raytracing;

public class Coordinate3D {

    double x;
    double y;
    double z;
    double w = 1;

    public Coordinate3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Coordinate3D minus(Coordinate3D c) {
        return new Coordinate3D(x - c.x, y - c.y, z - c.z);
    }

    Coordinate3D plus(Coordinate3D c) {
        return new Coordinate3D(x + c.x, y + c.y, z + c.z);
    }

    Coordinate3D divide(double c) {
        return new Coordinate3D(x / c, y / c, z / c);
    }

    Coordinate3D multiply(double c) {
        return new Coordinate3D(x * c, y * c, z * c);
    }

    double getNorm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    Coordinate3D vectorMultiply(Coordinate3D c) {
        return new Coordinate3D(y * c.z - z * c.y, z * c.x - x * c.z, x * c.y - y * c.x);
    }

    double scalarMultiply(Coordinate3D c) {
        return x * c.x + y * c.y + z * c.z;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }

}
