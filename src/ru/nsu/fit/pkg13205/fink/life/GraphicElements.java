package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Graphics;

public class GraphicElements {

    private static final int ANGLE = 6;

    private static void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        double k = (double) (y1 - y2) / (x1 - x2);
        if (x1 == x2) {
            if (y1 > y2) {
                int swap = y1;
                y1 = y2;
                y2 = swap;
            }
            for (int y = y1; y <= y2; y++) {
                g.fillRect(x1, y, 1, 1);
            }
        } else if (Math.abs(x1 - x2) >= Math.abs(y1 - y2)) {
            if (x1 > x2) {
                int swap = x1;
                x1 = x2;
                x2 = swap;
                y1 = y2;
            }
            for (int x = x1; x <= x2; x++) {
                g.fillRect(x, (int) Math.round(k * (x - x1) + y1), 1, 1);
            }
        } else {
            if (y1 > y2) {
                x1 = x2;
                int swap = y1;
                y1 = y2;
                y2 = swap;
            }
            for (int y = y1; y <= y2; y++) {
                g.fillRect((int) Math.round((y - y1 + k * x1) / k), y, 1, 1);
            }
        }
    }

    static void drawHexagon(Graphics g, int x, int y, int k) {
        int xArray[] = new int[ANGLE];
        int yArray[] = new int[ANGLE];
        xArray[0] = (int) (x - k * Math.sqrt(3) / 2);
        yArray[0] = y - k / 2;
        xArray[1] = x;
        yArray[1] = y - k;
        xArray[2] = (int) (x + k * Math.sqrt(3) / 2);
        yArray[2] = y - k / 2;
        xArray[3] = (int) (x + k * Math.sqrt(3) / 2);
        yArray[3] = y + k / 2;
        xArray[4] = x;
        yArray[4] = y + k;
        xArray[5] = (int) (x - k * Math.sqrt(3) / 2);
        yArray[5] = y + k / 2;
        for (int i = 1; i < ANGLE; i++) {
            drawLine(g, xArray[i - 1], yArray[i - 1], xArray[i], yArray[i]);
        }
        drawLine(g, xArray[0], yArray[0], xArray[ANGLE - 1], yArray[ANGLE - 1]);
    }
}
