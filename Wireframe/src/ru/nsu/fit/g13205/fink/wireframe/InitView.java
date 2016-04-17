package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private Data data;
    private BufferedImage image;
    private boolean taken = false;
    private int width = 500;
    private Point startPoint;

    public InitView(Data data) {
        this.data = data;
        setBackground(ViewOptions.BACKGROUND_COLOR);
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (taken) {
                    super.mouseDragged(e);
                    data.rotateZ((e.getX() - startPoint.x)/15.0);
                    data.rotateX((e.getY() - startPoint.y)/15.0);
                    startPoint = e.getPoint();
                    repaint();
                }
            }

        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getX() >= 5 && e.getX() < width + 5 && e.getY() >= 5 && e.getY() < width + 5) {
                    taken = true;
                    startPoint = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                taken = false;
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                image.setRGB(i, j, data.getBackgroundColor().getRGB());
            }
        }
        Graphics g1 = image.getGraphics();
        g1.setColor(Color.WHITE);
        Coordinate3D[][] coordinate = data.getCoordinate(0);
        int n = coordinate.length;
        int m = coordinate[0].length;
        g1.drawLine((int) Math.round(coordinate[0][0].x) + width / 2, (int) Math.round(width / 2 - coordinate[0][0].z), (int) Math.round(coordinate[0][m - 1].x) + width / 2, (int) Math.round(width / 2 - coordinate[0][m - 1].z));
        for (int i = 1; i < m; i++) {
            g1.drawLine((int) Math.round(coordinate[0][i].x) + width / 2, (int) Math.round(width / 2 - coordinate[0][i].z), (int) Math.round(coordinate[0][i - 1].x) + width / 2, (int) Math.round(width / 2 - coordinate[0][i - 1].z));
        }
        for (int i = 1; i < n; i++) {
            g1.drawLine((int) Math.round(coordinate[i][0].x) + width / 2, (int) Math.round(width / 2 - coordinate[i][0].z), (int) Math.round(coordinate[i][m - 1].x) + width / 2, (int) Math.round(width / 2 - coordinate[i][m - 1].z));
            g1.drawLine((int) Math.round(coordinate[i][0].x) + width / 2, (int) Math.round(width / 2 - coordinate[i][0].z), (int) Math.round(coordinate[i - 1][0].x) + width / 2, (int) Math.round(width / 2 - coordinate[i - 1][0].z));
            for (int j = 1; j < m; j++) {
                g1.drawLine((int) Math.round(coordinate[i][j].x) + width / 2, (int) Math.round(width / 2 - coordinate[i][j].z), (int) Math.round(coordinate[i][j - 1].x) + width / 2, (int) Math.round(width / 2 - coordinate[i][j - 1].z));
                g1.drawLine((int) Math.round(coordinate[i][j].x) + width / 2, (int) Math.round(width / 2 - coordinate[i][j].z), (int) Math.round(coordinate[i - 1][j].x) + width / 2, (int) Math.round(width / 2 - coordinate[i - 1][j].z));
            }
        }
        g.drawImage(image, 5, 5, this);
    }

    void updateData(Data data) {
        this.data = data;
        repaint();
    }
}
