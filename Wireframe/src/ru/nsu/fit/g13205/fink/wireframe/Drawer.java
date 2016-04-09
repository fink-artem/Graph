package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class Drawer {

    private static double relation = 1;
    private static double centerX = 1;
    private static double centerY = 1;

    static void drawSplain(BufferedImage image, Data data, int modelNumber) {
        Graphics g = image.getGraphics();
        centerX = image.getHeight() / 2;
        centerY = image.getWidth() / 2;
        int width = Math.max(image.getWidth(), image.getHeight());
        List<Coordinate> coordinate = data.getCoordinateForModel(0);
        int length = coordinate.size() - 2;
        double maxCoordinate = 0;
        for (Coordinate point : coordinate) {
            if (Math.abs(point.x) > maxCoordinate) {
                maxCoordinate = Math.abs(point.x);
            }
            if (Math.abs(point.y) > maxCoordinate) {
                maxCoordinate = Math.abs(point.y);
            }
        }
        maxCoordinate++;
        relation = width / (maxCoordinate * 2);
        for (int i = 1; i < length; i++) {
            
            g.drawOval(i, i, width, length);
        }
    }

    static Coordinate coordinateToScreen(Coordinate coordinate) {
        return new Coordinate(centerX + coordinate.x * relation, centerY - coordinate.y * relation);
    }
}
