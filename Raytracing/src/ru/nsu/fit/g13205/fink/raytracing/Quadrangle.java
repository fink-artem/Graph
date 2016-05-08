package ru.nsu.fit.g13205.fink.raytracing;

import java.util.ArrayList;
import java.util.List;

public class Quadrangle extends Shape {

    private Coordinate3D point1;
    private Coordinate3D point2;
    private Coordinate3D point3;
    private Coordinate3D point4;

    public Quadrangle(Coordinate3D point1, Coordinate3D point2, Coordinate3D point3, Coordinate3D point4, double kdr, double kdg, double kdb, double ksr, double ksg, double ksb, double power) {
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

}
