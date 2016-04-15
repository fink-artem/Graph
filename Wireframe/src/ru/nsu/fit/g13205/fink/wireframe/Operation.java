package ru.nsu.fit.g13205.fink.wireframe;

public class Operation {

    static double distance(Coordinate2D c1, Coordinate2D c2) {
        return Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
    }
}
