package ru.nsu.fit.g13205.fink.raytracing;

import java.util.List;

public abstract class Shape {

    protected static final double EXP = 0.00000001;
    protected int kdr;
    protected int kdg;
    protected int kdb;
    protected int ksr;
    protected int ksg;
    protected int ksb;
    protected double power;
    protected final ShapeType shapeType;
    protected boolean visible = true;

    public Shape(int kdr, int kdg, int kdb, int ksr, int ksg, int ksb, double power, ShapeType shapeType) {
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

    public abstract double getMin();

    public abstract double getMax();

    public abstract List<Segment> getCoordinate();

    public abstract Coordinate3D getIntersectionPoint(Coordinate3D start, Coordinate3D end);
}
