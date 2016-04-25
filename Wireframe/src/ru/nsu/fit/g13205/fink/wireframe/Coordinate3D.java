package ru.nsu.fit.g13205.fink.wireframe;

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

    Coordinate3D divide(double c) {
        return new Coordinate3D(x / c, y / c, z / c);
    }

    double getNorm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    Coordinate3D multiply(Coordinate3D c) {
        return new Coordinate3D(y * c.z - z * c.y, z * c.x - x * c.z, x * c.y - y * c.x);
    }
}
