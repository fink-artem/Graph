package ru.nsu.fit.g13205.fink.raytracing;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends Shape {

    private Coordinate3D point1;
    private Coordinate3D point2;
    private Coordinate3D point3;

    public Triangle(Coordinate3D point1, Coordinate3D point2, Coordinate3D point3, int kdr, int kdg, int kdb, int ksr, int ksg, int ksb, double power) {
        super(kdr, kdg, kdb, ksr, ksg, ksb, power, ShapeType.TRIANGLE);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public Coordinate3D getPoint1() {
        return point1;
    }

    public void setPoint1(Coordinate3D point1) {
        this.point1 = point1;
    }

    public Coordinate3D getPoint2() {
        return point2;
    }

    public void setPoint2(Coordinate3D point2) {
        this.point2 = point2;
    }

    public Coordinate3D getPoint3() {
        return point3;
    }

    public void setPoint3(Coordinate3D point3) {
        this.point3 = point3;
    }

    @Override
    public double getMin() {
        return Math.min(Math.min(Math.min(Math.min(point1.x, point1.y), point1.z), Math.min(Math.min(point2.x, point2.y), point2.z)), Math.min(Math.min(point3.x, point3.y), point3.z));
    }

    @Override
    public double getMax() {
        return Math.max(Math.max(Math.max(Math.max(point1.x, point1.y), point1.z), Math.max(Math.max(point2.x, point2.y), point2.z)), Math.max(Math.max(point3.x, point3.y), point3.z));
    }

    @Override
    public List<Segment> getCoordinate() {
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(new Segment(new Coordinate3D(point1.x, point1.y, point1.z), new Coordinate3D(point2.x, point2.y, point2.z)));
        segmentList.add(new Segment(new Coordinate3D(point2.x, point2.y, point2.z), new Coordinate3D(point3.x, point3.y, point3.z)));
        segmentList.add(new Segment(new Coordinate3D(point3.x, point3.y, point3.z), new Coordinate3D(point1.x, point1.y, point1.z)));
        return segmentList;
    }

    @Override
    public Coordinate3D getIntersectionPoint(Coordinate3D start, Coordinate3D end) {
        end = end.divide(end.getNorm());
        double a = (point2.y - point1.y) * (point3.z - point1.z) - (point3.y - point1.y) * (point2.z - point1.z);
        double b = (point3.x - point1.x) * (point2.z - point1.z) - (point2.x - point1.x) * (point3.z - point1.z);
        double c = (point2.x - point1.x) * (point3.y - point1.y) - (point3.x - point1.x) * (point2.y - point1.y);
        double d = -(point1.x * a + point1.y * b + point1.z * c);
        Coordinate3D pn = new Coordinate3D(a, b, c);
        double scalar = end.scalarMultiply(pn);
        if (scalar == 0) {
            return null;
        }
        double t = -(pn.scalarMultiply(start) + d) / pn.scalarMultiply(end);
        if (t < 0) {
            return null;
        }
        Coordinate3D intersection = new Coordinate3D(start.x + end.x * t, start.y + end.y * t, start.z + end.z * t);
        return null;
    }

}
