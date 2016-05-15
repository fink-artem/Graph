package ru.nsu.fit.g13205.fink.raytracing;

import java.util.List;

public abstract class Shape {

    protected static final double EXP = 0.00000001;
    protected double kdr;
    protected double kdg;
    protected double kdb;
    protected double ksr;
    protected double ksg;
    protected double ksb;
    protected double power;
    protected final ShapeType shapeType;

    public Shape(int kdr, int kdg, int kdb, int ksr, int ksg, int ksb, double power, ShapeType shapeType) {
        this.kdr = kdr / 255.0;
        this.kdg = kdg / 255.0;
        this.kdb = kdb / 255.0;
        this.ksr = ksr / 255.0;
        this.ksg = ksg / 255.0;
        this.ksb = ksb / 255.0;
        this.power = power;
        this.shapeType = shapeType;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public abstract double getMin();

    public abstract double getMax();

    public abstract List<Segment> getCoordinate();

    public abstract Coordinate3D getIntersectionPoint(Coordinate3D start, Coordinate3D end);

    public abstract Coordinate3D getNormal(Coordinate3D intersectionPoint);
}
