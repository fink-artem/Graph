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

    public Shape(double kdr, double kdg, double kdb, double ksr, double ksg, double ksb, double power, ShapeType shapeType) {
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.ksr = ksr;
        this.ksg = ksg;
        this.ksb = ksb;
        this.power = power;
        this.shapeType = shapeType;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public abstract double getMinX();

    public abstract double getMaxX();

    public abstract double getMinY();

    public abstract double getMaxY();

    public abstract double getMinZ();

    public abstract double getMaxZ();

    public abstract List<Segment> getCoordinate();

    public abstract Coordinate3D getIntersectionPoint(Coordinate3D start, Coordinate3D end);

    public abstract Coordinate3D getNormal(Coordinate3D intersectionPoint);
}
