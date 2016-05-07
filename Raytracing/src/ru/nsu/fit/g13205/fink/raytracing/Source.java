
package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;

public class Source {
    
    double lx;
    double ly;
    double lz;
    Color l;

    public Source(double lx, double ly, double lz, Color l) {
        this.lx = lx;
        this.ly = ly;
        this.lz = lz;
        this.l = l;
    }
    
}
