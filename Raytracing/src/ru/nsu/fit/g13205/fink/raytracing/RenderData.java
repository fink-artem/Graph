package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;

public class RenderData {

    double br = 0.5;
    double bg = 0;
    double bb = 0.5;
    double gamma = 1;
    int depth = 3;
    Quality quality = Quality.NORMAL;
    Coordinate3D eye = new Coordinate3D(10, 0, 0);
    Coordinate3D ref = new Coordinate3D(-10, 0, 0);
    Coordinate3D upVector = new Coordinate3D(10, 0, 1);
    double zn = 8;
    double zf = 15;
    double sw = 5;
    double sh = 5;

    public void setBackGround(Color color) {
        br = color.getRed() / 255.0;
        bg = color.getGreen() / 255.0;
        bb = color.getBlue() / 255.0;
    }
}
