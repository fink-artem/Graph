package ru.nsu.fit.g13205.fink.raytracing;

import java.util.ArrayList;
import java.util.List;

public class Quadrangle extends Shape {

    private Coordinate3D point1;
    private Coordinate3D point2;
    private Coordinate3D point3;
    private Coordinate3D point4;

    public Quadrangle(Coordinate3D point1, Coordinate3D point2, Coordinate3D point3, Coordinate3D point4, int kdr, int kdg, int kdb, int ksr, int ksg, int ksb, double power) {
        super(kdr, kdg, kdb, ksr, ksg, ksb, power, ShapeType.QUADRANGLE);
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
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

    public Coordinate3D getPoint4() {
        return point4;
    }

    public void setPoint4(Coordinate3D point4) {
        this.point4 = point4;
    }

    @Override
    public double getMin() {
        return Math.min(Math.min(Math.min(Math.min(point1.x, point1.y), point1.z), Math.min(Math.min(point2.x, point2.y), point2.z)), Math.min(Math.min(Math.min(point3.x, point3.y), point3.z), Math.min(Math.min(point4.x, point4.y), point4.z)));
    }

    @Override
    public double getMax() {
        return Math.max(Math.max(Math.max(Math.max(point1.x, point1.y), point1.z), Math.max(Math.max(point2.x, point2.y), point2.z)), Math.max(Math.max(Math.max(point3.x, point3.y), point3.z), Math.max(Math.max(point4.x, point4.y), point4.z)));
    }

    @Override
    public List<Segment> getCoordinate() {
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(new Segment(new Coordinate3D(point1.x, point1.y, point1.z), new Coordinate3D(point2.x, point2.y, point2.z)));
        segmentList.add(new Segment(new Coordinate3D(point2.x, point2.y, point2.z), new Coordinate3D(point3.x, point3.y, point3.z)));
        segmentList.add(new Segment(new Coordinate3D(point3.x, point3.y, point3.z), new Coordinate3D(point4.x, point4.y, point4.z)));
        segmentList.add(new Segment(new Coordinate3D(point4.x, point4.y, point4.z), new Coordinate3D(point1.x, point1.y, point1.z)));
        return segmentList;
    }

    @Override
    public Coordinate3D getIntersectionPoint(Coordinate3D start, Coordinate3D end) {
        double a = (point2.y - point1.y) * (point3.z - point1.z) - (point3.y - point1.y) * (point2.z - point1.z);
        double b = (point3.x - point1.x) * (point2.z - point1.z) - (point2.x - point1.x) * (point3.z - point1.z);
        double c = (point2.x - point1.x) * (point3.y - point1.y) - (point3.x - point1.x) * (point2.y - point1.y);
        double d = -(point1.x * a + point1.y * b + point1.z * c);
        Coordinate3D pn = new Coordinate3D(a, b, c);
        double scalar = end.scalarMultiply(pn);
        if (scalar == 0) {
            return null;
        }
        if (scalar > 0) {
            return null;
        }
        double t = -(pn.scalarMultiply(start) + d) / pn.scalarMultiply(end);
        if (t < 0) {
            return null;
        }
        Coordinate3D intersection = new Coordinate3D(start.x + end.x * t, start.y + end.y * t, start.z + end.z * t);
        double deltaX = Math.max(Math.max(point1.x, point2.x), point3.x) - Math.min(Math.min(point1.x, point2.x), point3.x);
        double deltaY = Math.max(Math.max(point1.y, point2.y), point3.y) - Math.min(Math.min(point1.y, point2.y), point3.y);
        double deltaZ = Math.max(Math.max(point1.z, point2.z), point3.z) - Math.min(Math.min(point1.z, point2.z), point3.z);
        double s, s1, s2, s3;
        if (deltaZ <= deltaX && deltaZ <= deltaY) {
            s = getArea(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y);
            s1 = getArea(point1.x, point1.y, point2.x, point2.y, intersection.x, intersection.y);
            s2 = getArea(point1.x, point1.y, intersection.x, intersection.y, point3.x, point3.y);
            s3 = getArea(intersection.x, intersection.y, point2.x, point2.y, point3.x, point3.y);
        } else if (deltaY <= deltaX && deltaY <= deltaZ) {
            s = getArea(point1.x, point1.z, point2.x, point2.z, point3.x, point3.z);
            s1 = getArea(point1.x, point1.z, point2.x, point2.z, intersection.x, intersection.z);
            s2 = getArea(point1.x, point1.z, intersection.x, intersection.z, point3.x, point3.z);
            s3 = getArea(intersection.x, intersection.z, point2.x, point2.z, point3.x, point3.z);
        } else {
            s = getArea(point1.z, point1.y, point2.z, point2.y, point3.z, point3.y);
            s1 = getArea(point1.z, point1.y, point2.z, point2.y, intersection.z, intersection.y);
            s2 = getArea(point1.z, point1.y, intersection.z, intersection.y, point3.z, point3.y);
            s3 = getArea(intersection.z, intersection.y, point2.z, point2.y, point3.z, point3.y);
        }
        if (Math.abs(s - s1 - s2 - s3) < EXP) {
            return intersection;
        } else {
            deltaX = Math.max(Math.max(point4.x, point1.x), point3.x) - Math.min(Math.min(point4.x, point1.x), point3.x);
            deltaY = Math.max(Math.max(point4.y, point1.y), point3.y) - Math.min(Math.min(point4.y, point1.y), point3.y);
            deltaZ = Math.max(Math.max(point4.z, point1.z), point3.z) - Math.min(Math.min(point4.z, point1.z), point3.z);
            if (deltaZ <= deltaX && deltaZ <= deltaY) {
                s = getArea(point4.x, point4.y, point1.x, point1.y, point3.x, point3.y);
                s1 = getArea(point4.x, point4.y, point1.x, point1.y, intersection.x, intersection.y);
                s2 = getArea(point4.x, point4.y, intersection.x, intersection.y, point3.x, point3.y);
                s3 = getArea(intersection.x, intersection.y, point1.x, point1.y, point3.x, point3.y);
            } else if (deltaY <= deltaX && deltaY <= deltaZ) {
                s = getArea(point4.x, point4.z, point1.x, point1.z, point3.x, point3.z);
                s1 = getArea(point4.x, point4.z, point1.x, point1.z, intersection.x, intersection.z);
                s2 = getArea(point4.x, point4.z, intersection.x, intersection.z, point3.x, point3.z);
                s3 = getArea(intersection.x, intersection.z, point1.x, point1.z, point3.x, point3.z);
            } else {
                s = getArea(point4.z, point4.y, point1.z, point1.y, point3.z, point3.y);
                s1 = getArea(point4.z, point4.y, point1.z, point1.y, intersection.z, intersection.y);
                s2 = getArea(point4.z, point4.y, intersection.z, intersection.y, point3.z, point3.y);
                s3 = getArea(intersection.z, intersection.y, point1.z, point1.y, point3.z, point3.y);
            }
            if (Math.abs(s - s1 - s2 - s3) < EXP) {
                return intersection;
            } else {
                return null;
            }
        }
    }

    @Override
    public Coordinate3D getNormal(Coordinate3D intersectionPoint) {
        return null;
    }

    private double getArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
    }

}
