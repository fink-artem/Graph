package ru.nsu.fit.g13205.fink.isoline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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
    
    static void drawIsoline(BufferedImage image,int isolineColor){
        int width = image.getWidth();
        int height = image.getHeight();
    }
}
