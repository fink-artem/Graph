package ru.nsu.fit.g13205.fink.raytracing;

public class Segment {

    Coordinate3D point1;
    Coordinate3D point2;

    public Segment(Coordinate3D point1, Coordinate3D point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public String toString() {
        return point1 + " " + point2;
    }

}
