package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private static final double SPEED = 20.0;
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
                super.mouseDragged(e);
                if (taken) {
                    int number = data.getRotatingModelNumber();
                    if (number == -1) {
                        InitView.this.data.rotateZ((e.getX() - startPoint.x) / SPEED);
                        InitView.this.data.rotateY((e.getY() - startPoint.y) / SPEED);
                        startPoint = e.getPoint();
                        repaint();
                    } else {
                        InitView.this.data.getModel(number).rotateZ((e.getX() - startPoint.x) / SPEED);
                        InitView.this.data.getModel(number).rotateY((e.getY() - startPoint.y) / SPEED);
                        startPoint = e.getPoint();
                        repaint();
                    }
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
        int k = data.getK();
        List<Coordinate3D[][]> list = data.getCoordinate();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            g1.setColor(data.getModelColor(i));
            Coordinate3D[][] coordinate = list.get(i);
            int n = coordinate.length;
            int m = coordinate[0].length;
            for (int l = 1; l < m; l++) {
                g1.drawLine((int) Math.round(coordinate[0][l].y) + width / 2, (int) Math.round(width / 2 - coordinate[0][l].z), (int) Math.round(coordinate[0][l - 1].y) + width / 2, (int) Math.round(width / 2 - coordinate[0][l - 1].z));
            }
            for (int j = 1; j < n; j++) {
                g1.drawLine((int) Math.round(coordinate[j][0].y) + width / 2, (int) Math.round(width / 2 - coordinate[j][0].z), (int) Math.round(coordinate[j - 1][0].y) + width / 2, (int) Math.round(width / 2 - coordinate[j - 1][0].z));
                if (j % k == 0) {
                    for (int l = 1; l < m; l++) {
                        g1.drawLine((int) Math.round(coordinate[j][l].y) + width / 2, (int) Math.round(width / 2 - coordinate[j][l].z), (int) Math.round(coordinate[j][l - 1].y) + width / 2, (int) Math.round(width / 2 - coordinate[j][l - 1].z));
                        if (l % k == 0) {
                            g1.drawLine((int) Math.round(coordinate[j][l].y) + width / 2, (int) Math.round(width / 2 - coordinate[j][l].z), (int) Math.round(coordinate[j - 1][l].y) + width / 2, (int) Math.round(width / 2 - coordinate[j - 1][l].z));
                        }
                    }
                } else {
                    for (int l = 1; l < m; l++) {
                        if (l % k == 0) {
                            g1.drawLine((int) Math.round(coordinate[j][l].y) + width / 2, (int) Math.round(width / 2 - coordinate[j][l].z), (int) Math.round(coordinate[j - 1][l].y) + width / 2, (int) Math.round(width / 2 - coordinate[j - 1][l].z));
                        }
                    }
                }
            }
        }
        g.drawImage(image, 5, 5, this);
    }

    void updateData(Data data) {
        this.data = data;
        repaint();
    }
}
