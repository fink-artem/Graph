package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class Grid {

    private static final int ANGLE = 6;
    private static final double SQRT_THREE = Math.sqrt(3);
    private final BufferedImage bufferedImage;

    public Grid(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        double k = (double) (y1 - y2) / (x1 - x2);
        if (x1 == x2) {
            if (y1 > y2) {
                int swap = y1;
                y1 = y2;
                y2 = swap;
            }
            for (int y = y1; y <= y2; y++) {
                bufferedImage.setRGB(x1, y, Color.BLACK.getRGB());
            }
        } else if (Math.abs(x1 - x2) >= Math.abs(y1 - y2)) {
            if (x1 > x2) {
                int swap = x1;
                x1 = x2;
                x2 = swap;
                y1 = y2;
            }
            for (int x = x1; x <= x2; x++) {
                bufferedImage.setRGB(x, (int) Math.round(k * (x - x1) + y1), Color.BLACK.getRGB());
            }
        } else {
            if (y1 > y2) {
                x1 = x2;
                int swap = y1;
                y1 = y2;
                y2 = swap;
            }
            for (int y = y1; y <= y2; y++) {
                bufferedImage.setRGB((int) Math.round((y - y1 + k * x1) / k), y, Color.BLACK.getRGB());
            }
        }
    }

    private void drawHexagon(int x, int y, int k) {
        int xArray[] = new int[ANGLE];
        int yArray[] = new int[ANGLE];
        xArray[0] = (int) (x - k * SQRT_THREE / 2);
        yArray[0] = y - k / 2;
        xArray[1] = x;
        yArray[1] = y - k;
        xArray[2] = (int) (x + k * SQRT_THREE / 2);
        yArray[2] = y - k / 2;
        xArray[3] = (int) (x + k * SQRT_THREE / 2);
        yArray[3] = y + k / 2;
        xArray[4] = x;
        yArray[4] = y + k;
        xArray[5] = (int) (x - k * SQRT_THREE / 2);
        yArray[5] = y + k / 2;
        for (int i = 1; i < ANGLE; i++) {
            drawLine(xArray[i - 1], yArray[i - 1], xArray[i], yArray[i]);
        }
        drawLine(xArray[0], yArray[0], xArray[ANGLE - 1], yArray[ANGLE - 1]);
    }

    void fill(int x, int y, int color) {
        int startColor = bufferedImage.getRGB(x, y);
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(x, y));
        bufferedImage.setRGB(x, y, color);
        int width = bufferedImage.getWidth() - 1;
        int height = bufferedImage.getHeight() - 1;
        Point point;
        while (true) {
            point = queue.poll();
            if (point == null) {
                break;
            }
            if (point.x > 0 && bufferedImage.getRGB(point.x - 1, point.y) == startColor) {
                queue.offer(new Point(point.x - 1, point.y));
                bufferedImage.setRGB(point.x - 1, point.y, color);
            }
            if (point.y > 0 && bufferedImage.getRGB(point.x, point.y - 1) == startColor) {
                queue.offer(new Point(point.x, point.y - 1));
                bufferedImage.setRGB(point.x, point.y - 1, color);
            }
            if (point.x < width && bufferedImage.getRGB(point.x + 1, point.y) == startColor) {
                queue.offer(new Point(point.x + 1, point.y));
                bufferedImage.setRGB(point.x + 1, point.y, color);
            }
            if (point.y < height && bufferedImage.getRGB(point.x, point.y + 1) == startColor) {
                queue.offer(new Point(point.x, point.y + 1));
                bufferedImage.setRGB(point.x, point.y + 1, color);
            }
        }
    }

    void drawGrid(int n, int m, int k, int w) {
        int m2 = m - 1;
        int marginGrid = 5;
        int startX1 = (int) Math.round(marginGrid + k * SQRT_THREE / 2);
        int startX2 = (int) Math.round(marginGrid + k * SQRT_THREE);
        int stepX = (int) Math.round(k * SQRT_THREE);
        int startY = marginGrid + k;
        int x;
        int y;
        int stepY = k * 3 / 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                x = startX1 + j * stepX;
                y = startY + i * stepY;
                drawHexagon(x, y, k);
                fill(x, y, Color.GREEN.getRGB());
            }
            i++;
            if (i >= n) {
                break;
            }
            for (int j = 0; j < m2; j++) {
                x = startX2 + j * stepX;
                y = startY + i * stepY;
                drawHexagon(x, y, k);
                fill(x, y, Color.GREEN.getRGB());
            }
        }
    }
}
