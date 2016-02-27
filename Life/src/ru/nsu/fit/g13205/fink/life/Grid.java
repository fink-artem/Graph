package ru.nsu.fit.g13205.fink.life;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Grid {

    public static final int MARGIN_GRID = 5;
    public static final int ERROR = -1;
    private static final int ANGLE = 6;
    private static final double SQRT_THREE = Math.sqrt(3);
    private int startX1;
    private int startX2;
    private int stepX;
    private int startY;
    private int stepY;
    private int w;
    private int k;
    int a = 0;

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

    private void drawCell(BufferedImage bufferedImage, int x, int y, int k, int w, boolean one, boolean two, boolean three, boolean four, boolean five, boolean six) {
        int xArray[] = new int[ANGLE];
        int yArray[] = new int[ANGLE];
        boolean b[] = new boolean[ANGLE + 1];
        b[1] = one;
        b[2] = two;
        b[3] = three;
        b[4] = four;
        b[5] = five;
        b[6] = six;
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
                if (b[i]) {
                    drawLine(bufferedImage, xArray[i - 1], yArray[i - 1], xArray[i], yArray[i]);
                }
            }
            if (b[ANGLE]) {
                drawLine(bufferedImage, xArray[0], yArray[0], xArray[ANGLE - 1], yArray[ANGLE - 1]);
            }
        } else {
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
            g.setStroke(new BasicStroke(w, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setPaint(ViewColor.BORDER_COLOR);
            for (int i = 1; i < ANGLE; i++) {
                if (b[i]) {
                    g.drawLine(xArray[i - 1], yArray[i - 1], xArray[i], yArray[i]);
                }
            }
            if (b[ANGLE]) {
                g.drawLine(xArray[0], yArray[0], xArray[ANGLE - 1], yArray[ANGLE - 1]);
            }
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

    void drawGrid(BufferedImage bufferedImage, int n, int m, int k, int w, Model model) {
        this.w = w;
        this.k = k;
        int m2 = m - 1;
        if (w % 2 != 0) {
            startX1 = (int) Math.round(MARGIN_GRID + k * SQRT_THREE / 2 + w - 1);
            startX2 = (int) Math.round(MARGIN_GRID + k * SQRT_THREE + w - 1 + w / 2);
            stepX = (int) Math.round(k * SQRT_THREE + w - 1);
            startY = (int) Math.round(MARGIN_GRID + k + 2 * w / SQRT_THREE - 1);
        } else {
            startX1 = (int) Math.round(MARGIN_GRID + k * SQRT_THREE / 2 + w);
            startX2 = (int) Math.round(MARGIN_GRID + k * SQRT_THREE + w + w / 2);
            stepX = (int) Math.round(k * SQRT_THREE + w);
            startY = (int) Math.round(MARGIN_GRID + k + 2 * w / SQRT_THREE);
        }
        stepY = (int) Math.round((k * 3 + w * SQRT_THREE) / 2);
        int x;
        int y;
        drawCell(bufferedImage, startX1, startY, k, w, true, true, true, true, true, true);
        if (model.getCellStatus(0, 0) == Model.Cell.DEAD) {
            fill(bufferedImage, startX1, startY, ViewColor.CELL_COLOR_OFF.getRGB());
        } else {
            fill(bufferedImage, startX1, startY, ViewColor.CELL_COLOR_ON.getRGB());
        }
        for (int j = 1; j < m; j++) {
            x = startX1 + j * stepX;
            drawCell(bufferedImage, x, startY, k, w, true, true, true, true, true, false);
            if (model.getCellStatus(j, 0) == Model.Cell.DEAD) {
                fill(bufferedImage, x, startY, ViewColor.CELL_COLOR_OFF.getRGB());
            } else {
                fill(bufferedImage, x, startY, ViewColor.CELL_COLOR_ON.getRGB());
            }
        }

        for (int i = 1; i < n; i++) {
            y = startY + i * stepY;
            drawCell(bufferedImage, startX2, y, k, w, false, false, true, true, true, true);
            if (model.getCellStatus(0, i) == Model.Cell.DEAD) {
                fill(bufferedImage, startX2, y, ViewColor.CELL_COLOR_OFF.getRGB());
            } else {
                fill(bufferedImage, startX2, y, ViewColor.CELL_COLOR_ON.getRGB());
            }
            for (int j = 1; j < m2; j++) {
                x = startX2 + j * stepX;
                drawCell(bufferedImage, x, y, k, w, false, false, true, true, true, false);
                if (model.getCellStatus(j, i) == Model.Cell.DEAD) {
                    fill(bufferedImage, x, y, ViewColor.CELL_COLOR_OFF.getRGB());
                } else {
                    fill(bufferedImage, x, y, ViewColor.CELL_COLOR_ON.getRGB());
                }
            }
            i++;
            if (i >= n) {
                break;
            }
            y = startY + i * stepY;
            drawCell(bufferedImage, startX1, y, k, w, true, false, true, true, true, true);
            if (model.getCellStatus(0, i) == Model.Cell.DEAD) {
                fill(bufferedImage, startX1, y, ViewColor.CELL_COLOR_OFF.getRGB());
            } else {
                fill(bufferedImage, startX1, y, ViewColor.CELL_COLOR_ON.getRGB());
            }
            for (int j = 1; j < m2; j++) {
                x = startX1 + j * stepX;
                drawCell(bufferedImage, x, y, k, w, false, false, true, true, true, false);
                if (model.getCellStatus(j, i) == Model.Cell.DEAD) {
                    fill(bufferedImage, x, y, ViewColor.CELL_COLOR_OFF.getRGB());
                } else {
                    fill(bufferedImage, x, y, ViewColor.CELL_COLOR_ON.getRGB());
                }
            }
            drawCell(bufferedImage, startX1 + m2 * stepX, y, k, w, false, true, true, true, true, false);
            if (model.getCellStatus(0, i) == Model.Cell.DEAD) {
                fill(bufferedImage, startX1 + m2 * stepX, y, ViewColor.CELL_COLOR_OFF.getRGB());
            } else {
                fill(bufferedImage, startX1 + m2 * stepX, y, ViewColor.CELL_COLOR_ON.getRGB());
            }
        }
    }

    //номер ячейки
    Point getAbsoluteCellCoordinate(BufferedImage bufferedImage, int x, int y) {
        int xRight = x;
        int xLeft = x;
        int yUp = y;
        int yEnd = y;
        int width = bufferedImage.getWidth() - 1;
        int height = bufferedImage.getHeight() - 1;
        while (true) {
            xLeft--;
            if (xLeft < MARGIN_GRID) {
                return new Point(ERROR, ERROR);
            }
            if (bufferedImage.getRGB(xLeft, y) == ViewColor.BORDER_COLOR.getRGB()) {
                break;
            }
        }
        while (true) {
            yUp--;
            if (yUp < MARGIN_GRID) {
                return new Point(ERROR, ERROR);
            }
            if (bufferedImage.getRGB(x, yUp) == ViewColor.BORDER_COLOR.getRGB()) {
                break;
            }
        }
        while (true) {
            xRight++;
            if (xRight == width) {
                return new Point(ERROR, ERROR);
            }
            if (bufferedImage.getRGB(xRight, y) == ViewColor.BORDER_COLOR.getRGB()) {
                break;
            }
        }
        while (true) {
            yEnd++;
            if (yEnd == height) {
                return new Point(ERROR, ERROR);
            }
            if (bufferedImage.getRGB(x, yEnd) == ViewColor.BORDER_COLOR.getRGB()) {
                break;
            }
        }
        x = (xRight + xLeft) / 2;
        y = (yUp + yEnd) / 2;
        double y1 = y - MARGIN_GRID - w * SQRT_THREE - k / 2;
        double x1 = x - MARGIN_GRID - w / 2;
        double x2 = x - MARGIN_GRID - w / 2 - k * SQRT_THREE / 2;
        y1 /= stepY;
        x1 /= stepX;
        x2 /= stepX;
        if (((int) y1) % 2 == 0) {
            Point p1 = getCellCordinate((int) x1, (int) y1);
            Point p2 = getCellCordinate((int) x2, (int) y1 + 1);
            if (Math.sqrt((x - p1.x) * (x - p1.x) + (y - p1.y) * (y - p1.y)) > Math.sqrt((x - p2.x) * (x - p2.x) + (y - p2.y) * (y - p2.y))) {
                return new Point((int) x2, (int) y1 + 1);
            } else {
                return new Point((int) x1, (int) y1);
            }
        } else {
            Point p1 = getCellCordinate((int) x2, (int) y1);
            Point p2 = getCellCordinate((int) x1, (int) y1 + 1);
            if (Math.sqrt((x - p1.x) * (x - p1.x) + (y - p1.y) * (y - p1.y)) > Math.sqrt((x - p2.x) * (x - p2.x) + (y - p2.y) * (y - p2.y))) {
                return new Point((int) x1, (int) y1 + 1);
            } else {
                return new Point((int) x2, (int) y1);
            }
        }
    }

    //Координаты центра
    Point getCellCordinate(int x, int y) {
        if (y % 2 == 0) {
            x = startX1 + x * stepX;
        } else {
            x = startX2 + x * stepX;
        }
        y = startY + y * stepY;
        return new Point(x, y);
    }

    void drawImpact(BufferedImage bufferedImage, int n, int m, Model model, int k) {
        int m2 = m - 1;
        int x;
        int y;
        Graphics g = bufferedImage.createGraphics();
        g.setColor(ViewColor.IMPACT_COLOR);
        g.setFont(new Font("X", Font.BOLD, 15));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                x = startX1 + j * stepX;
                y = startY + i * stepY;
                double impact = (double) Math.round(model.getCellImpact(j, i) * 10) / 10;
                if (impact == (int) impact) {
                    g.drawString(String.valueOf((int) impact), x - 10, y + 7);
                } else {
                    g.drawString(String.valueOf(impact), x - 10, y + 7);
                }
            }
            i++;
            if (i >= n) {
                break;
            }
            for (int j = 0; j < m2; j++) {
                x = startX2 + j * stepX;
                y = startY + i * stepY;
                double impact = (double) Math.round(model.getCellImpact(j, i) * 10) / 10;
                if (impact == (int) impact) {
                    g.drawString(String.valueOf((int) impact), x - 10, y + 7);
                } else {
                    g.drawString(String.valueOf(impact), x - 10, y + 7);
                }
            }
        }
    }

    void clearImpact(BufferedImage bufferedImage, int n, int m, Model model, int k) {
        int m2 = m - 1;
        int x;
        int y;
        Graphics g = bufferedImage.createGraphics();
        g.setFont(new Font("X", Font.BOLD, 15));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                x = startX1 + j * stepX;
                y = startY + i * stepY;
                double impact = (double) Math.round(model.getCellImpact(j, i) * 10) / 10;
                if (model.getCellStatus(j, i) == Model.Cell.DEAD) {
                    g.setColor(ViewColor.CELL_COLOR_OFF);
                } else {
                    g.setColor(ViewColor.CELL_COLOR_ON);
                }
                if (impact == (int) impact) {
                    g.drawString(String.valueOf((int) impact), x - 10, y + 7);
                } else {
                    g.drawString(String.valueOf(impact), x - 10, y + 7);
                }
            }
            i++;
            if (i >= n) {
                break;
            }
            for (int j = 0; j < m2; j++) {
                x = startX2 + j * stepX;
                y = startY + i * stepY;
                double impact = (double) Math.round(model.getCellImpact(j, i) * 10) / 10;
                if (model.getCellStatus(j, i) == Model.Cell.DEAD) {
                    g.setColor(ViewColor.CELL_COLOR_OFF);
                } else {
                    g.setColor(ViewColor.CELL_COLOR_ON);
                }
                if (impact == (int) impact) {
                    g.drawString(String.valueOf((int) impact), x - 10, y + 7);
                } else {
                    g.drawString(String.valueOf(impact), x - 10, y + 7);
                }
            }
        }
    }
}
