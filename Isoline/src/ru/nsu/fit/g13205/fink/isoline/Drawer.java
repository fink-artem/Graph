package ru.nsu.fit.g13205.fink.isoline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Drawer {

    static void drawFunction(BufferedImage image, int[] colors, int n, double a, double b, double c, double d) {
        int width = image.getWidth();
        int height = image.getHeight();
        double z;
        double maxZ = 100;
        double minZ = -100;
        double stepX = Math.abs(a - c) / width;
        double stepY = Math.abs(b - d) / height;
        double stepZ = Math.abs(maxZ - minZ) / n;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                z = (Logic.f(i * stepY + b, j * stepX + a) + maxZ) / stepZ;
                if (z == n) {
                    image.setRGB(j, i, colors[n - 1]);
                    continue;
                }
                if (z == 0) {
                    image.setRGB(j, i, colors[0]);
                    continue;
                }
                image.setRGB(j, i, colors[(int) z]);
            }
        }
    }

    static void drawLegend(BufferedImage image, int[] colors, int n) {
        int width = image.getWidth();
        int height = image.getHeight();
        double step = (double) height / n;
        int z;
        n--;
        for (int i = 0; i < height; i++) {
            z = n - (int) (i / step);
            for (int j = 0; j < width; j++) {
                image.setRGB(j, i, colors[z]);
            }
        }
    }

    static void drawGrid(BufferedImage image, int k, int m) {
        int width = image.getWidth();
        int height = image.getHeight();
        double stepK = (double) height / k;
        double stepM = (double) width / m;
        Graphics2D g = (Graphics2D) image.getGraphics();
        float dash[] = {2, 2};
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dash, 0));
        g.setPaint(Color.BLACK);
        height--;
        width--;
        for (int i = 1; i < k; i++) {
            g.drawLine(0, (int) (stepK * i + 0.5), width, (int) (stepK * i + 0.5));
        }
        for (int i = 1; i < m; i++) {
            g.drawLine((int) (stepM * i + 0.5), 0, (int) (stepM * i + 0.5), height);
        }
    }

    static void drawIsoline(BufferedImage image, int isolineColor, int k, int m, int n, double a, double b, double c, double d) {
        int width = image.getWidth();
        int height = image.getHeight();
        double f1, f2, f3, f4;
        double maxZ = 100;
        double minZ = -100;
        double stepX = Math.abs(a - c) / width * ((double) width / m);
        double stepY = Math.abs(b - d) / height * ((double) height / k);
        double stepZ = Math.abs(maxZ - minZ) / n;
        double z;
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setPaint(new Color(isolineColor));
        for (int l = 1; l < n; l++) {
            z = stepZ * l + minZ;
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < m; j++) {
                    f1 = Logic.f(i * stepY + b, j * stepX + a);
                    f2 = Logic.f(i * stepY + b, (j + 1) * stepX + a);
                    f3 = Logic.f((i + 1) * stepY + b, j * stepX + a);
                    f4 = Logic.f((i + 1) * stepY + b, (j + 1) * stepX + a);
                    boolean bool[] = new boolean[4];
                    Point point[] = new Point[4];
                    int counter = 0;
                    if (f1 >= z && f2 <= z || f1 <= z && f2 >= z) {
                        bool[0] = true;
                        if (f1 < f2) {
                            point[counter] = new Point((int) Math.round(stepX * (z - f1) / (f2 - f1)), (int) Math.round(i * stepY + b));
                        } else {
                            point[counter] = new Point((int) Math.round(stepX * (z - f2) / (f1 - f2)), (int) Math.round(i * stepY + b));
                        }
                        counter++;
                    }
                    if (f2 >= z && f4 <= z || f2 <= z && f4 >= z) {
                        bool[1] = true;
                        if (f2 < f4) {
                            point[counter] = new Point((int) Math.round((j + 1) * stepX + a), (int) Math.round(stepY * (z - f2) / (f4 - f2)));
                        } else {
                            point[counter] = new Point((int) Math.round((j + 1) * stepX + a), (int) Math.round(stepY * (z - f4) / (f2 - f4)));
                        }
                        counter++;
                    }
                    if (f3 >= z && f4 <= z || f3 <= z && f4 >= z) {
                        bool[2] = true;
                        if (f3 < f4) {
                            point[counter] = new Point((int) Math.round(stepX * (z - f3) / (f4 - f3)), (int) Math.round(i * stepY + b));
                        } else {
                            point[counter] = new Point((int) Math.round(stepX * (z - f4) / (f3 - f4)), (int) Math.round(i * stepY + b));
                        }
                        counter++;
                    }
                    if (f1 >= z && f3 <= z || f1 <= z && f3 >= z) {
                        bool[3] = true;
                        if (f1 < f3) {
                            point[counter] = new Point((int) Math.round(j * stepX + a), (int) Math.round(stepY * (z - f1) / (f3 - f1)));
                        } else {
                            point[counter] = new Point((int) Math.round(j * stepX + a), (int) Math.round(stepY * (z - f3) / (f1 - f3)));
                        }
                        counter++;
                    }
                    if (counter == 2) {
                        g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepY, b), pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b));
                    } else if (counter == 3) {
                        if (!bool[0]) {
                            g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepY, b), pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b));
                            g.drawLine(pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b));
                        } else if (!bool[1]) {
                            g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepX, b), pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b));
                            g.drawLine(pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b));
                        } else if (!bool[2]) {
                            g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepY, b), pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b));
                            g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepX, b), pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b));
                        } else if (!bool[3]) {
                            g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepY, b), pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b));
                            g.drawLine(pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b));
                        }
                    } else if (counter == 4) {
                        g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepY, b), pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b));
                        g.drawLine(pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b));
                        g.drawLine(pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b), pointToCoordinate(point[3].x, stepX, a), pointToCoordinate(point[3].y, stepY, b));
                        g.drawLine(pointToCoordinate(point[3].x, stepX, a), pointToCoordinate(point[3].y, stepY, b), pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepY, b));
                    }
                }
            }
        }
    }

    static int pointToCoordinate(int point, double step, double start) {
        return (int) Math.round((point - start) / step);
    }
}
