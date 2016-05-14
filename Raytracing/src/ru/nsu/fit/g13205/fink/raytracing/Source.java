
package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;

public class Source {
    
    Coordinate3D coordinate;
    Color l;

    public Source(double lx, double ly, double lz, Color l) {
        coordinate = new Coordinate3D(lx, ly, lz);
        this.l = l;
    }
    
}
