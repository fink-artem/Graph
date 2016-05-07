package ru.nsu.fit.g13205.fink.raytracing;

import java.util.List;

public abstract class Shape {

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

    public double getKdr() {
        return kdr;
    }

    public void setKdr(double kdr) {
        this.kdr = kdr;
    }

    public double getKdg() {
        return kdg;
    }

    public void setKdg(double kdg) {
        this.kdg = kdg;
    }

    public double getKdb() {
        return kdb;
    }

    public void setKdb(double kdb) {
        this.kdb = kdb;
    }

    public double getKsr() {
        return ksr;
    }

    public void setKsr(double ksr) {
        this.ksr = ksr;
    }

    public double getKsg() {
        return ksg;
    }

    public void setKsg(double ksg) {
        this.ksg = ksg;
    }

    public double getKsb() {
        return ksb;
    }

    public void setKsb(double ksb) {
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
}
