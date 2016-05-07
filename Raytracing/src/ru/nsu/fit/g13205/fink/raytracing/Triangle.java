package ru.nsu.fit.g13205.fink.raytracing;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends Shape {

    private Coordinate3D point1;
    private Coordinate3D point2;
    private Coordinate3D point3;

    public Triangle(Coordinate3D point1, Coordinate3D point2, Coordinate3D point3, double kdr, double kdg, double kdb, double ksr, double ksg, double ksb, double power) {
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
        segmentList.add(new Segment(point1, point2));
        segmentList.add(new Segment(point2, point3));
        segmentList.add(new Segment(point3, point1));
        return segmentList;
    }
}
