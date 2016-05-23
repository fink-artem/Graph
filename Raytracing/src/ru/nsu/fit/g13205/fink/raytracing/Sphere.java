package ru.nsu.fit.g13205.fink.raytracing;

import java.util.ArrayList;
import java.util.List;

public class Sphere extends Shape {

    private final int n = 10;
    private final int m = 10;
    private final int k = 2;
    private Coordinate3D center;
    private double radius;

    public Sphere(Coordinate3D center, double radius, double kdr, double kdg, double kdb, double ksr, double ksg, double ksb, double power) {
        super(kdr, kdg, kdb, ksr, ksg, ksb, power, ShapeType.SPHERE);
        this.center = center;
        this.radius = radius;
    }

    public Coordinate3D getCenter() {
        return center;
    }

    public void setCenter(Coordinate3D center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getMinX() {
        return center.x - radius;
    }

    @Override
    public double getMaxX() {
        return center.x + radius;
    }

    @Override
    public double getMinY() {
        return center.y - radius;
    }

    @Override
    public double getMaxY() {
        return center.y + radius;
    }

    @Override
    public double getMinZ() {
        return center.z - radius;
    }

    @Override
    public double getMaxZ() {
        return center.z + radius;
    }

    @Override
    public List<Segment> getCoordinate() {
        List<Segment> segmentList = new ArrayList<>();
        double stepNK = Math.PI / n / k;
        double stepN = Math.PI / n;
        double stepMK = 2 * Math.PI / m / k;
        double stepM = 2 * Math.PI / m;
        Coordinate3D last;
        Coordinate3D c;
        for (int i = 0; i < n; i++) {
            last = new Coordinate3D(center.x, center.y + Math.sin(i * stepN) * radius, center.z + Math.cos(i * stepN) * radius);
            for (int j = 0; j <= k; j++) {
                c = new Coordinate3D(center.x, center.y + Math.sin(stepNK * j + i * stepN) * radius, center.z + Math.cos(stepNK * j + i * stepN) * radius);
                segmentList.add(new Segment(c, last));
                last = new Coordinate3D(c.x, c.y, c.z);
            }
            double z = center.z + Math.cos((i + 1) * stepN) * radius;
            double rad = Math.sin((i + 1) * stepN) * radius;
            for (int j = 0; j < m; j++) {
                for (int l = 0; l <= k; l++) {
                    c = new Coordinate3D(center.x + Math.sin(stepMK * l + j * stepM) * rad, center.y + Math.cos(stepMK * l + j * stepM) * rad, z);
                    segmentList.add(new Segment(c, last));
                    last = new Coordinate3D(c.x, c.y, c.z);
                }
            }
        }
        for (int i = 1; i < m; i++) {
            last = new Coordinate3D(center.x, center.y, center.z + radius);
            for (int j = 0; j < n; j++) {
                for (int l = 0; l <= k; l++) {
                    c = new Coordinate3D(center.x + Math.sin(i * stepM) * Math.sin(stepNK * l + j * stepN) * radius, center.y + Math.sin(stepNK * l + j * stepN) * Math.cos(i * stepM) * radius, center.z + Math.cos(stepNK * l + j * stepN) * radius);
                    segmentList.add(new Segment(c, last));
                    last = new Coordinate3D(c.x, c.y, c.z);
                }
            }
        }
        return segmentList;
    }

    @Override
    public Coordinate3D getIntersectionPoint(Coordinate3D start, Coordinate3D end) {
        Coordinate3D os = center.minus(start);
        if (os.getNorm() < radius) {
            return null;
        }
        double tca = os.scalarMultiply(end);
        if (tca < 0) {
            return null;
        }
        double thc = radius * radius - Math.pow(os.getNorm(), 2) + tca * tca;
        if (thc < 0) {
            return null;
        }
        double t = tca - Math.sqrt(thc);
        return new Coordinate3D(start.x + end.x * t, start.y + end.y * t, start.z + end.z * t);
    }

    @Override
    public Coordinate3D getNormal(Coordinate3D intersectionPoint) {
        return intersectionPoint.minus(center).normalize();
    }

}
