package ru.nsu.fit.g13205.fink.raytracing;

import java.util.ArrayList;
import java.util.List;

public class Box extends Shape {

    private Coordinate3D minPoint;
    private Coordinate3D maxPoint;

    public Box(Coordinate3D minPoint, Coordinate3D maxPoint, double kdr, double kdg, double kdb, double ksr, double ksg, double ksb, double power) {
        super(kdr, kdg, kdb, ksr, ksg, ksb, power, ShapeType.BOX);
        this.maxPoint = maxPoint;
        this.minPoint = minPoint;
    }

    public Coordinate3D getMinPoint() {
        return minPoint;
    }

    public void setMin(Coordinate3D min) {
        this.minPoint = min;
    }

    public Coordinate3D getMaxPoint() {
        return maxPoint;
    }

    public void setMax(Coordinate3D max) {
        this.maxPoint = max;
    }

    @Override
    public double getMin() {
        return Math.min(Math.min(minPoint.x, minPoint.y), minPoint.z);
    }

    @Override
    public double getMax() {
        return Math.max(Math.max(maxPoint.x, maxPoint.y), maxPoint.z);
    }

    @Override
    public List<Segment> getCoordinate() {
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(new Segment(new Coordinate3D(minPoint.x, minPoint.y, minPoint.z), new Coordinate3D(minPoint.x, minPoint.y, maxPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(minPoint.x, minPoint.y, minPoint.z), new Coordinate3D(minPoint.x, maxPoint.y, minPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(minPoint.x, minPoint.y, minPoint.z), new Coordinate3D(maxPoint.x, minPoint.y, minPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(minPoint.x, minPoint.y, maxPoint.z), new Coordinate3D(maxPoint.x, minPoint.y, maxPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(minPoint.x, minPoint.y, maxPoint.z), new Coordinate3D(minPoint.x, maxPoint.y, maxPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(minPoint.x, maxPoint.y, minPoint.z), new Coordinate3D(minPoint.x, maxPoint.y, maxPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(minPoint.x, maxPoint.y, minPoint.z), new Coordinate3D(maxPoint.x, maxPoint.y, minPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(maxPoint.x, minPoint.y, minPoint.z), new Coordinate3D(maxPoint.x, maxPoint.y, minPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(maxPoint.x, minPoint.y, minPoint.z), new Coordinate3D(maxPoint.x, minPoint.y, maxPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(maxPoint.x, maxPoint.y, maxPoint.z), new Coordinate3D(minPoint.x, maxPoint.y, maxPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(maxPoint.x, maxPoint.y, maxPoint.z), new Coordinate3D(maxPoint.x, maxPoint.y, minPoint.z)));
        segmentList.add(new Segment(new Coordinate3D(maxPoint.x, maxPoint.y, maxPoint.z), new Coordinate3D(maxPoint.x, minPoint.y, maxPoint.z)));
        return segmentList;
    }

    @Override
    public Coordinate3D getIntersectionPoint(Coordinate3D start, Coordinate3D end) {
        double tNear = Double.NEGATIVE_INFINITY;
        double tFar = Double.POSITIVE_INFINITY;
        if (end.x == 0) {
            if (start.x < minPoint.x || start.x > maxPoint.x) {
                return null;
            }
        } else {
            double t1 = (minPoint.x - start.x) / end.x;
            double t2 = (maxPoint.x - start.x) / end.x;
            if (t2 < t1) {
                double prom = t1;
                t1 = t2;
                t2 = prom;
            }
            if (t1 > tNear) {
                tNear = t1;
            }
            if (t2 < tFar) {
                tFar = t2;
            }
            if (tNear > tFar) {
                return null;
            }
            if (tFar < 0) {
                return null;
            }
            if (tNear < 0) {
                return null;
            }
        }
        if (end.y == 0) {
            if (start.y < minPoint.y || start.y > maxPoint.y) {
                return null;
            }
        } else {
            double t1 = (minPoint.y - start.y) / end.y;
            double t2 = (maxPoint.y - start.y) / end.y;
            if (t2 < t1) {
                double prom = t1;
                t1 = t2;
                t2 = prom;
            }
            if (t1 > tNear) {
                tNear = t1;
            }
            if (t2 < tFar) {
                tFar = t2;
            }
            if (tNear > tFar) {
                return null;
            }
            if (tFar < 0) {
                return null;
            }
            if (tNear < 0) {
                return null;
            }
        }
        if (end.z == 0) {
            if (start.z < minPoint.z || start.z > maxPoint.z) {
                return null;
            }
        } else {
            double t1 = (minPoint.z - start.z) / end.z;
            double t2 = (maxPoint.z - start.z) / end.z;
            if (t2 < t1) {
                double prom = t1;
                t1 = t2;
                t2 = prom;
            }
            if (t1 > tNear) {
                tNear = t1;
            }
            if (t2 < tFar) {
                tFar = t2;
            }
            if (tNear > tFar) {
                return null;
            }
            if (tFar < 0) {
                return null;
            }
            if (tNear < 0) {
                return null;
            }
        }
        return new Coordinate3D(EXP, EXP, EXP);
    }

    @Override
    public Coordinate3D getNormal(Coordinate3D intersectionPoint) {
        return null;
    }
}
