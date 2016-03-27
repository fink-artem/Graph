package ru.nsu.fit.g13205.fink.isoline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Drawer {

    private static double maxZ = 100;
    private static double minZ = -100;

    static class Coordinate {

        double x;
        double y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static void drawFunction(BufferedImage image, int[] colors, int n, double a, double b, double c, double d) {
        int width = image.getWidth();
        int height = image.getHeight();
        double z;
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

    static void drawLabel(int n, Graphics g, int startX, int startY, int height) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("X", Font.BOLD, 15));
        double stepZ = Math.abs(maxZ - minZ) / n;
        double step = (double) height / n;
        double z;
        for (int i = 1; i < n; i++) {
            z = Math.round((stepZ * i + minZ) * 1000) / 1000.0;
            g.drawString(String.valueOf(z), startX - 50, (int) Math.round(startY + step * i + 5));
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

    static void drawIsolineMap(Graphics g, int isolineColor, int k, int m, int n, double a, double b, double c, double d, int height, int width) {
        double stepZ = Math.abs(maxZ - minZ) / n;
        for (int l = 1; l < n; l++) {
            drawIsoline(g, isolineColor, k, m, a, b, c, d, height, width, stepZ * l + minZ);
        }
    }

    static void drawIsoline(Graphics g, int isolineColor, int k, int m, double a, double b, double c, double d, int height, int width, double z) {
        g.setColor(new Color(isolineColor));
        double f1, f2, f3, f4;
        double stepX = Math.abs(a - c) / width;
        double stepY = Math.abs(b - d) / height;
        double stepM = stepX * ((double) width / m);
        double stepK = stepY * ((double) height / k);
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < m; j++) {
                f1 = Logic.f(i * stepK + b, j * stepM + a);
                f2 = Logic.f(i * stepK + b, (j + 1) * stepM + a);
                f3 = Logic.f((i + 1) * stepK + b, j * stepM + a);
                f4 = Logic.f((i + 1) * stepK + b, (j + 1) * stepM + a);
                boolean bool[] = new boolean[4];
                Coordinate point[] = new Coordinate[4];
                int counter = 0;
                if ((f1 >= z && f2 <= z || f1 <= z && f2 >= z) && f1 != f2) {
                    bool[0] = true;
                    if (f1 < f2) {
                        point[counter] = new Coordinate(j * stepM + a + stepM * (z - f1) / (f2 - f1), i * stepK + b);
                    } else {
                        point[counter] = new Coordinate((j + 1) * stepM + a - stepM * (z - f2) / (f1 - f2), i * stepK + b);
                    }
                    counter++;
                }
                if ((f2 >= z && f4 <= z || f2 <= z && f4 >= z) && f4 != f2) {
                    bool[1] = true;
                    if (f2 < f4) {
                        point[counter] = new Coordinate((j + 1) * stepM + a, i * stepK + b + stepK * (z - f2) / (f4 - f2));
                    } else {
                        point[counter] = new Coordinate((j + 1) * stepM + a, (i + 1) * stepK + b - stepK * (z - f4) / (f2 - f4));
                    }
                    counter++;
                }
                if ((f3 >= z && f4 <= z || f3 <= z && f4 >= z) && f3 != f4) {
                    bool[2] = true;
                    if (f3 < f4) {
                        point[counter] = new Coordinate(j * stepM + a + stepM * (z - f3) / (f4 - f3), (i + 1) * stepK + b);
                    } else {
                        point[counter] = new Coordinate((j + 1) * stepM + a - stepM * (z - f4) / (f3 - f4), (i + 1) * stepK + b);
                    }
                    counter++;
                }
                if ((f1 >= z && f3 <= z || f1 <= z && f3 >= z) && f1 != f3) {
                    bool[3] = true;
                    if (f1 < f3) {
                        point[counter] = new Coordinate(j * stepM + a, i * stepK + b + stepK * (z - f1) / (f3 - f1));
                    } else {
                        point[counter] = new Coordinate(j * stepM + a, (i + 1) * stepK + b - stepK * (z - f3) / (f1 - f3));
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
                        g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b));
                        g.drawLine(pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b));
                    } else if (!bool[2]) {
                        g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepY, b), pointToCoordinate(point[1].x, stepX, a), pointToCoordinate(point[1].y, stepY, b));
                        g.drawLine(pointToCoordinate(point[0].x, stepX, a), pointToCoordinate(point[0].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), pointToCoordinate(point[2].y, stepY, b));
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

    static int pointToCoordinate(double point, double step, double start) {
        return (int) Math.round((point - start) / step);
    }

    static void drawInterpolationLegend(BufferedImage image, int[] colors, int n) {
        int width = image.getWidth();
        int height = image.getHeight();
        double step = (double) height / n;
        int z;
        n--;
        double start = (step / 2);
        double end = height - start;
        int startInteger = (int) Math.round(start);
        int endInteger = (int) Math.round(end);
        double nearestColorUp;
        double nearestColorDown;
        double proc;
        Color color1, color2, color3;
        for (int i = 0; i < start; i++) {
            z = n - (int) (i / step);
            for (int j = 0; j < width; j++) {
                image.setRGB(j, i, colors[z]);
            }
        }
        for (int i = startInteger; i < endInteger; i++) {
            nearestColorUp = ((int) ((i + start) / step)) * step - start;
            nearestColorDown = nearestColorUp + step;
            proc = (i - nearestColorUp) / step;
            color1 = new Color(colors[n - (int) (nearestColorUp / step)]);
            color2 = new Color(colors[n - (int) (nearestColorDown / step)]);
            color3 = new Color((int) Math.round(color1.getRed() * (1 - proc) + color2.getRed() * proc), (int) Math.round(color1.getGreen() * (1 - proc) + color2.getGreen() * proc), (int) Math.round(color1.getBlue() * (1 - proc) + color2.getBlue() * proc));
            for (int j = 0; j < width; j++) {
                image.setRGB(j, i, color3.getRGB());
            }
        }
        for (int i = endInteger; i < height; i++) {
            z = n - (int) (i / step);
            for (int j = 0; j < width; j++) {
                image.setRGB(j, i, colors[z]);
            }
        }
    }

    static void drawInterpolationFunction(BufferedImage image, BufferedImage legend, int n, double a, double b, double c, double d) {
        int width = image.getWidth();
        int height = image.getHeight();
        int legendHeight = legend.getHeight();
        double z;
        double stepX = Math.abs(a - c) / width;
        double stepY = Math.abs(b - d) / height;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                z = Logic.f(i * stepY + b, j * stepX + a);
                image.setRGB(j, i, legend.getRGB(0, (int)Math.round((legendHeight-1)*(z-minZ)/(maxZ-minZ))));
            }
        }
    }
}
