package ru.nsu.fit.g13205.fink.isoline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Drawer {

    private static double maxZ = 0;
    private static double minZ = 0;

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
        height--;
        for (int i = 0; i <= height; i++) {
            for (int j = 0; j < width; j++) {
                z = (Logic.f(i * stepY + b, j * stepX + a) - minZ) / stepZ;
                if (z == n) {
                    image.setRGB(j, i, colors[n - 1]);
                    continue;
                }
                try {
                    image.setRGB(j, height - i, colors[(int) z]);
                } catch (Exception e) {
                }
            }
        }
    }

    static void drawLegend(BufferedImage image, int[] colors, int n, int isolineColor) {
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
            z = Math.round((maxZ - stepZ * i) * 1000) / 1000.0;
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
        double stepX = Math.abs(a - c) / width;
        double stepY = Math.abs(b - d) / height;
        double stepM = stepX * width / m;
        double stepK = stepY * height / k;
        height--;
        double f[] = new double[5];
        double x[] = new double[5];
        double y[] = new double[5];
        boolean bool[] = new boolean[5];
        Coordinate point[] = new Coordinate[5];
        int counter;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < m; j++) {
                x[1] = j * stepM + a;
                y[1] = i * stepK + b;
                f[1] = Logic.f(y[1], x[1]);
                x[2] = (j + 1) * stepM + a;
                y[2] = y[1];
                f[2] = Logic.f(y[2], x[2]);
                x[3] = x[1];
                y[3] = (i + 1) * stepK + b;
                f[3] = Logic.f(y[3], x[3]);
                x[4] = x[2];
                y[4] = y[3];
                f[4] = Logic.f(y[4], x[4]);
                counter = 0;
                for (int l = 1; l <= 4; l++) {
                    bool[l] = f[l] > z;
                    if (bool[l]) {
                        counter++;
                    }
                }
                if (bool[1] != bool[2]) {
                    if (f[1] < f[2]) {
                        point[1] = new Coordinate(x[1] + stepM * (z - f[1]) / (f[2] - f[1]), y[1]);
                    } else {
                        point[1] = new Coordinate(x[2] - stepM * (z - f[2]) / (f[1] - f[2]), y[2]);
                    }
                }
                if (bool[2] != bool[4]) {
                    if (f[2] < f[4]) {
                        point[2] = new Coordinate(x[2], y[2] + stepK * (z - f[2]) / (f[4] - f[2]));
                    } else {
                        point[2] = new Coordinate(x[4], y[4] - stepK * (z - f[4]) / (f[2] - f[4]));
                    }
                }
                if (bool[3] != bool[4]) {
                    if (f[3] < f[4]) {
                        point[3] = new Coordinate(x[3] + stepM * (z - f[3]) / (f[4] - f[3]), y[3]);
                    } else {
                        point[3] = new Coordinate(x[4] - stepM * (z - f[4]) / (f[3] - f[4]), y[4]);
                    }
                }
                if (bool[1] != bool[3]) {
                    if (f[1] < f[3]) {
                        point[4] = new Coordinate(x[1], y[1] + stepK * (z - f[1]) / (f[3] - f[1]));
                    } else {
                        point[4] = new Coordinate(x[3], y[3] - stepK * (z - f[3]) / (f[1] - f[3]));
                    }
                }
                if (counter == 1) {
                    if (bool[1]) {
                        g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b));
                    } else if (bool[2]) {
                        g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b));
                    } else if (bool[3]) {
                        g.drawLine(pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b), pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b));
                    } else {
                        g.drawLine(pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b));
                    }
                } else if (counter == 2) {
                    if (bool[1]) {
                        if (bool[2]) {
                            g.drawLine(pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b), pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b));
                        } else if (bool[3]) {
                            g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b));
                        } else {
                            if (Logic.f((y[3] - y[1]) / 2, (x[2] - x[1]) / 2) > z) {
                                g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b));
                                g.drawLine(pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b), pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b));
                            } else {
                                g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b));
                                g.drawLine(pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b), pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b));
                            }
                        }
                    } else if (bool[2]) {
                        if (bool[3]) {
                            if (Logic.f((y[3] - y[1]) / 2, (x[2] - x[1]) / 2) > z) {
                                g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b));
                                g.drawLine(pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b), pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b));
                            } else {
                                g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b));
                                g.drawLine(pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b), pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b));
                            }
                        } else {
                            g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b));
                        }
                    } else {
                        g.drawLine(pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b), pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b));
                    }
                } else if (counter == 3) {
                    if (!bool[1]) {
                        g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b));
                    } else if (!bool[2]) {
                        g.drawLine(pointToCoordinate(point[1].x, stepX, a), height - pointToCoordinate(point[1].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b));
                    } else if (!bool[3]) {
                        g.drawLine(pointToCoordinate(point[4].x, stepX, a), height - pointToCoordinate(point[4].y, stepY, b), pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b));
                    } else {
                        g.drawLine(pointToCoordinate(point[3].x, stepX, a), height - pointToCoordinate(point[3].y, stepY, b), pointToCoordinate(point[2].x, stepX, a), height - pointToCoordinate(point[2].y, stepY, b));
                    }
                }
            }
        }
    }

    static int pointToCoordinate(double point, double step, double start) {
        return (int) Math.round((point - start) / step);
    }

    static void drawInterpolationLegend(BufferedImage image, int[] colors, int n, int isolineColor) {
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
        height--;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                z = Logic.f(i * stepY + b, j * stepX + a);
                image.setRGB(j, height - i, legend.getRGB(0, (int) Math.round((legendHeight - 1) * (maxZ - z) / (maxZ - minZ))));
            }
        }
    }

    static void updateDomain(int height, int width, double a, double b, double c, double d) {
        double z;
        double stepX = Math.abs(a - c) / width;
        double stepY = Math.abs(b - d) / height;
        minZ = Logic.f(b, a);
        maxZ = Logic.f(b, a);
        for (double i = 0; i <= height; i += 0.5) {
            for (double j = 0; j <= width; j += 0.5) {
                z = Logic.f(i * stepY + b, j * stepX + a);
                if (z < minZ) {
                    minZ = z;
                }
                if (z > maxZ) {
                    maxZ = z;
                }
            }
        }
    }

    static void fill(BufferedImage image, int color) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, color);
            }
        }
    }

    static void drawIsolineLegend(BufferedImage legend, int isolineColor, int n) {
        Graphics g = legend.getGraphics();
        g.setColor(new Color(isolineColor));
        int width = legend.getWidth();
        int height = legend.getHeight();
        double step = (double) height / n;
        int z;
        n--;
        int saveZ = n;
        for (int i = 0; i < height; i++) {
            z = n - (int) (i / step);
            if (z != saveZ) {
                saveZ = z;
                for (int j = 0; j < width; j++) {
                    legend.setRGB(j, i, isolineColor);
                }
            }
        }
    }

    static void drawLastIsoline(BufferedImage legend, double z,int isolineColor) {
        Graphics g = legend.getGraphics();
        g.setColor(new Color(isolineColor));
        g.drawLine(0, (int) Math.round((legend.getHeight() - 1) * (maxZ - z) / (maxZ - minZ)), legend.getWidth() - 1, (int) Math.round((legend.getHeight() - 1) * (maxZ - z) / (maxZ - minZ)));
    }
}
