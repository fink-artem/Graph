package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class Drawer {

    public static final int RADIUS = 5;
    private static final double STEP = 0.05;
    private static double relation = 1;
    private static double centerX = 1;
    private static double centerY = 1;

    static void drawSplain(BufferedImage image, Data data, int modelNumber) {
        Graphics g = image.getGraphics();
        centerX = image.getWidth() / 2;
        centerY = image.getHeight() / 2;
        int width = Math.min(image.getWidth(), image.getHeight());
        List<Coordinate> coordinate = data.getCoordinateForModel(0);
        int length = coordinate.size();
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
        g.setColor(ViewOptions.POINT_COLOR);
        relation = width / (maxCoordinate * 2);
        Point c;
        for (int i = 0; i < length; i++) {
            c = coordinateToScreen(coordinate.get(i));
            g.drawOval(c.x - RADIUS, c.y - RADIUS, RADIUS * 2, RADIUS * 2);
        }
        length -= 2;
        double[][] matrixG = new double[4][1];
        double[][] matrixT = new double[1][4];
        double[][] matrixX;
        double[][] matrixY;
        Point c2;
        g.setColor(ViewOptions.SPLAIN_COLOR);
        for (int i = 1; i < length; i++) {
            matrixG[0][0] = coordinate.get(i - 1).x;
            matrixG[1][0] = coordinate.get(i).x;
            matrixG[2][0] = coordinate.get(i + 1).x;
            matrixG[3][0] = coordinate.get(i + 2).x;
            matrixX = MatrixOperation.multiply(Matrix.MS, matrixG);
            matrixG[0][0] = coordinate.get(i - 1).y;
            matrixG[1][0] = coordinate.get(i).y;
            matrixG[2][0] = coordinate.get(i + 1).y;
            matrixG[3][0] = coordinate.get(i + 2).y;
            matrixY = MatrixOperation.multiply(Matrix.MS, matrixG);
            c = coordinateToScreen(new Coordinate(matrixX[3][0], matrixY[3][0]));
            for (double t = 0; t < 1; t += STEP) {
                matrixT[0][0] = Math.pow(t, 3);
                matrixT[0][1] = Math.pow(t, 2);
                matrixT[0][2] = t;
                matrixT[0][3] = 1;
                c2 = c;
                c = coordinateToScreen(new Coordinate(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]));
                g.drawLine(c.x, c.y, c2.x, c2.y);
            }
            matrixT[0][0] = 1;
            matrixT[0][1] = 1;
            matrixT[0][2] = 1;
            matrixT[0][3] = 1;
            c2 = c;
            c = coordinateToScreen(new Coordinate(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]));
            g.drawLine(c.x, c.y, c2.x, c2.y);

        }
    }

    static Point coordinateToScreen(Coordinate coordinate) {
        return new Point((int) Math.round(centerX + coordinate.x * relation), (int) Math.round(centerY - coordinate.y * relation));
    }

    static Coordinate screenToCoordinate(Point point) {
        return new Coordinate((point.x - centerX) / relation, (centerY - point.y) / relation);
    }
}
