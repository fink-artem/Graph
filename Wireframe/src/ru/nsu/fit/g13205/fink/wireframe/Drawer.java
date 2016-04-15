package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class Drawer {

    public static final int BIG_RADIUS = 5;
    public static final int SMALL_RADIUS = 3;
    private static double relation = 1;
    private static double centerX = 1;
    private static double centerY = 1;

    static void drawSplain(BufferedImage image, Data data, int modelNumber) {
        Graphics g = image.getGraphics();
        centerX = image.getWidth() / 2;
        centerY = image.getHeight() / 2;
        List<Coordinate2D> coordinate = data.getPivots(0);
        int length = coordinate.size();
        double maxCoordinateX = 0;
        double maxCoordinateY = 0;
        for (Coordinate2D point : coordinate) {
            if (Math.abs(point.x) > maxCoordinateX) {
                maxCoordinateX = Math.abs(point.x);
            }
            if (Math.abs(point.y) > maxCoordinateY) {
                maxCoordinateY = Math.abs(point.y);
            }
        }
        maxCoordinateX += maxCoordinateX * 0.1 + 0.1;
        maxCoordinateY += maxCoordinateY * 0.1 + 0.1;
        if (image.getWidth() / (maxCoordinateX * 2) < image.getHeight() / (maxCoordinateY * 2)) {
            relation = image.getWidth() / (maxCoordinateX * 2);
        } else {
            relation = image.getHeight() / (maxCoordinateY * 2);
        }
        g.setColor(ViewOptions.POINT_COLOR);
        Point c;
        Point c2;
        c = coordinateToScreen(coordinate.get(0));
        g.drawOval(c.x - BIG_RADIUS, c.y - BIG_RADIUS, BIG_RADIUS * 2, BIG_RADIUS * 2);
        for (int i = 1; i < length; i++) {
            c = coordinateToScreen(coordinate.get(i));
            c2 = coordinateToScreen(coordinate.get(i - 1));
            g.drawLine(c.x, c.y, c2.x, c2.y);
            g.drawOval(c.x - BIG_RADIUS, c.y - BIG_RADIUS, BIG_RADIUS * 2, BIG_RADIUS * 2);
            c = coordinateToScreen(new Coordinate2D((coordinate.get(i).x + coordinate.get(i - 1).x) / 2, (coordinate.get(i).y + coordinate.get(i - 1).y) / 2));
            g.drawOval(c.x - SMALL_RADIUS, c.y - SMALL_RADIUS, SMALL_RADIUS * 2, SMALL_RADIUS * 2);
        }
        length -= 2;
        double[][] matrixG = new double[4][1];
        double[][] matrixT = new double[1][4];
        double[][] matrixX;
        double[][] matrixY;
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
            c = coordinateToScreen(new Coordinate2D(matrixX[3][0], matrixY[3][0]));
            for (double t = 0; t < 1; t += ViewOptions.STEP) {
                matrixT[0][0] = Math.pow(t, 3);
                matrixT[0][1] = Math.pow(t, 2);
                matrixT[0][2] = t;
                matrixT[0][3] = 1;
                c2 = c;
                c = coordinateToScreen(new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]));
                g.drawLine(c.x, c.y, c2.x, c2.y);
            }
            matrixT[0][0] = 1;
            matrixT[0][1] = 1;
            matrixT[0][2] = 1;
            matrixT[0][3] = 1;
            c2 = c;
            c = coordinateToScreen(new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]));
            g.drawLine(c.x, c.y, c2.x, c2.y);
        }
    }

    static Point coordinateToScreen(Coordinate2D coordinate) {
        return new Point((int) Math.round(centerX + coordinate.x * relation), (int) Math.round(centerY - coordinate.y * relation));
    }

    static Coordinate2D screenToCoordinate(Point point) {
        return new Coordinate2D((point.x - centerX) / relation, (centerY - point.y) / relation);
    }
    
}
