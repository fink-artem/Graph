package ru.nsu.fit.pkg13205.fink.life;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Grid {

    public static final int MARGIN_GRID = 5;
    private static final int ANGLE = 6;
    private static final double SQRT_THREE = Math.sqrt(3);

    private void drawLine(BufferedImage bufferedImage, int x1, int y1, int x2, int y2) {
        int deltaX = Math.abs(x1 - x2);
        int deltaY = Math.abs(y1 - y2);
        if (Math.abs(x1 - x2) >= Math.abs(y1 - y2)) {
            if (x1 > x2) {
                int swap = x1;
                x1 = x2;
                x2 = swap;
                swap = y1;
                y1 = y2;
                y2 = swap;
            }
            int y = y1;
            int changeY = (int) Math.signum(y2 - y1);
            double error = 0;
            for (int x = x1; x <= x2; x++) {
                bufferedImage.setRGB(x, y, ViewColor.BORDER_COLOR.getRGB());
                error += deltaY;
                if (2 * error >= deltaX) {
                    y += changeY;
                    error -= deltaX;
                }
            }
        } else {
            if (y1 > y2) {
                int swap = x1;
                x1 = x2;
                x2 = swap;
                swap = y1;
                y1 = y2;
                y2 = swap;
            }
            int x = x1;
            int changeX = (int) Math.signum(x2 - x1);
            double error = 0;
            for (int y = y1; y <= y2; y++) {
                bufferedImage.setRGB(x, y, ViewColor.BORDER_COLOR.getRGB());
                error += deltaX;
                if (2 * error >= deltaY) {
                    x += changeX;
                    error -= deltaY;
                }
            }
        }
    }

    private void drawCell(BufferedImage bufferedImage, int x, int y, int k, int w) {
        int xArray[] = new int[ANGLE];
        int yArray[] = new int[ANGLE];
        xArray[0] = (int) (x - (k * SQRT_THREE + w) / 2);
        yArray[0] = (int) (y - k / 2 - w / 2 / SQRT_THREE);
        xArray[1] = x;
        yArray[1] = (int) (y - k - w / SQRT_THREE);
        xArray[2] = (int) (x + (k * SQRT_THREE + w) / 2);
        yArray[2] = (int) (y - k / 2 - w / 2 / SQRT_THREE);
        xArray[3] = (int) (x + (k * SQRT_THREE + w) / 2);
        yArray[3] = (int) (y + k / 2 + w / 2 / SQRT_THREE);
        xArray[4] = x;
        yArray[4] = (int) (y + k + w / SQRT_THREE);
        xArray[5] = (int) (x - (k * SQRT_THREE + w) / 2);
        yArray[5] = (int) (y + k / 2 + w / 2 / SQRT_THREE);
        if (w == 1) {
            for (int i = 1; i < ANGLE; i++) {
                drawLine(bufferedImage, xArray[i - 1], yArray[i - 1], xArray[i], yArray[i]);
            }
            drawLine(bufferedImage, xArray[0], yArray[0], xArray[ANGLE - 1], yArray[ANGLE - 1]);
        } else {
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
            g.setStroke(new BasicStroke(w, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setPaint(Color.BLACK);
            for (int i = 1; i < ANGLE; i++) {
                g.drawLine(xArray[i - 1], yArray[i - 1], xArray[i], yArray[i]);
            }
            g.drawLine(xArray[0], yArray[0], xArray[ANGLE - 1], yArray[ANGLE - 1]);
        }
    }

    void fill(BufferedImage bufferedImage, int x, int y, int color) {
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(x, y));
        int startColor = bufferedImage.getRGB(x, y);
        if (startColor == color) {
            return;
        }
        int width = bufferedImage.getWidth() - 1;
        int height = bufferedImage.getHeight() - 1;
        Point point;
        while (!stack.isEmpty()) {
            point = stack.pop();
            if (point.x > 0 && bufferedImage.getRGB(point.x - 1, point.y) == startColor) {
                stack.push(new Point(point.x - 1, point.y));
                bufferedImage.setRGB(point.x - 1, point.y, color);
            }
            if (point.y > 0 && bufferedImage.getRGB(point.x, point.y - 1) == startColor) {
                stack.push(new Point(point.x, point.y - 1));
                bufferedImage.setRGB(point.x, point.y - 1, color);
            }
            if (point.x < width && bufferedImage.getRGB(point.x + 1, point.y) == startColor) {
                stack.push(new Point(point.x + 1, point.y));
                bufferedImage.setRGB(point.x + 1, point.y, color);
            }
            if (point.y < height && bufferedImage.getRGB(point.x, point.y + 1) == startColor) {
                stack.push(new Point(point.x, point.y + 1));
                bufferedImage.setRGB(point.x, point.y + 1, color);
            }
        }
    }

    void drawGrid(BufferedImage bufferedImage, int n, int m, int k, int w) {
        int m2 = m - 1;
        int startX1;
        int startX2;
        int stepX;
        int startY;
        int stepY;
        if (w % 2 != 0) {
            startX1 = (int) Math.round(MARGIN_GRID + k * SQRT_THREE / 2 + w - 1);
            startX2 = (int) Math.round(MARGIN_GRID + k * SQRT_THREE + w - 1 + w / 2);
            stepX = (int) Math.round(k * SQRT_THREE + w - 1);
            startY = (int) Math.round(MARGIN_GRID + k + 2 * w / SQRT_THREE - 1);
            stepY = (int) Math.round(k * 3 / 2 + 2 * w / SQRT_THREE - 1);
        } else {
            startX1 = (int) Math.round(MARGIN_GRID + k * SQRT_THREE / 2 + w);
            startX2 = (int) Math.round(MARGIN_GRID + k * SQRT_THREE + w + w / 2);
            stepX = (int) Math.round(k * SQRT_THREE + w);
            startY = (int) Math.round(MARGIN_GRID + k + 2 * w / SQRT_THREE);
            stepY = (int) Math.round(k * 3 / 2 + 2 * w / SQRT_THREE);
        }
        int x;
        int y;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                x = startX1 + j * stepX;
                y = startY + i * stepY;
                drawCell(bufferedImage, x, y, k, w);
                fill(bufferedImage, x, y, ViewColor.CELL_COLOR_OFF.getRGB());
            }
            i++;
            if (i >= n) {
                break;
            }
            for (int j = 0; j < m2; j++) {
                x = startX2 + j * stepX;
                y = startY + i * stepY;
                drawCell(bufferedImage, x, y, k, w);
                fill(bufferedImage, x, y, ViewColor.CELL_COLOR_OFF.getRGB());
            }
        }
    }
}
