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
}
