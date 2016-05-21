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

    double length(Coordinate3D c) {
        return Math.sqrt(Math.pow(c.x - x, 2) + Math.pow(c.y - y, 2) + Math.pow(c.z - z, 2));
    }

    Coordinate3D normalize() {
        return divide(getNorm());
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate3D other = (Coordinate3D) obj;
        if (Math.abs(x - other.x) > Shape.EXP) {
            return false;
        }
        if (Math.abs(y - other.y) > Shape.EXP) {
            return false;
        }
        if (Math.abs(z - other.z) > Shape.EXP) {
            return false;
        }
        return true;
    }

}
