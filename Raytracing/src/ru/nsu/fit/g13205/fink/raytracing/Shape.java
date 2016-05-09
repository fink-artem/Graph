package ru.nsu.fit.g13205.fink.raytracing;

import java.util.List;

public abstract class Shape {

    protected int kdr;
    protected int kdg;
    protected int kdb;
    protected int ksr;
    protected int ksg;
    protected int ksb;
    protected double power;
    protected final ShapeType shapeType;

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

    public int getKdr() {
        return kdr;
    }

    public void setKdr(int kdr) {
        this.kdr = kdr;
    }

    public int getKdg() {
        return kdg;
    }

    public void setKdg(int kdg) {
        this.kdg = kdg;
    }

    public int getKdb() {
        return kdb;
    }

    public void setKdb(int kdb) {
        this.kdb = kdb;
    }

    public int getKsr() {
        return ksr;
    }

    public void setKsr(int ksr) {
        this.ksr = ksr;
    }

    public int getKsg() {
        return ksg;
    }

    public void setKsg(int ksg) {
        this.ksg = ksg;
    }

    public int getKsb() {
        return ksb;
    }

    public void setKsb(int ksb) {
        this.ksb = ksb;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public abstract double getMin();

    public abstract double getMax();

    public abstract List<Segment> getCoordinate();

    public abstract Coordinate3D getIntersectionPoint(Coordinate3D start, Coordinate3D end);
}
