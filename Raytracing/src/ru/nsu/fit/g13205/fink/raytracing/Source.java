package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;

public class Source {

    Coordinate3D coordinate;
    double lr;
    double lg;
    double lb;

    public Source(double lx, double ly, double lz, Color l) {
        coordinate = new Coordinate3D(lx, ly, lz);
        lr = l.getRed() / 255.0;
        lg = l.getGreen() / 255.0;
        lb = l.getBlue() / 255.0;
    }

}
