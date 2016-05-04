package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private static final double SPEED = 20.0;
    private Data data;
    private BufferedImage image;
    private boolean taken = false;
    private int width = 500;
    private int height = 500;
    private Point startPoint;

    public InitView(Data data) {
        this.data = data;
        setBackground(ViewOptions.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(width + 2 * ViewOptions.MARGIN, height + 2 * ViewOptions.MARGIN));
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
                if (e.getX() >= ViewOptions.MARGIN && e.getX() < width + ViewOptions.MARGIN && e.getY() >= ViewOptions.MARGIN && e.getY() < height + ViewOptions.MARGIN) {
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

        addMouseWheelListener((MouseWheelEvent e) -> {
            InitView.this.data.setZn(InitView.this.data.getZn() - e.getPreciseWheelRotation() / 10);
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        height = (int) Math.round(width * data.getSh() / data.getSw());
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width + 2 * ViewOptions.MARGIN, height + 2 * ViewOptions.MARGIN));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, data.getBackgroundColor().getRGB());
            }
        }
        double[][] rotateMatrix = MatrixOperation.multiply(Matrix.getMcamMatrix(Data.eyeMatrix, Data.refMatrix, Data.upMatrix), data.getRotateMatrix());
        double length = 100;
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
                drawLine(g1, coordinate[0][l], coordinate[0][l - 1], false);
            }
            for (int j = 1; j < n; j++) {
                drawLine(g1, coordinate[j][0], coordinate[j - 1][0], false);
                if (j % k == 0) {
                    for (int l = 1; l < m; l++) {
                        drawLine(g1, coordinate[j][l], coordinate[j][l - 1], false);
                        if (l % k == 0) {
                            drawLine(g1, coordinate[j][l], coordinate[j - 1][l], false);
                        }
                    }
                } else {
                    for (int l = 1; l < m; l++) {
                        if (l % k == 0) {
                            drawLine(g1, coordinate[j][l], coordinate[j - 1][l], false);
                        }
                    }
                }
            }
            g1.setColor(Color.red);
            double[][] vector1 = {{length}, {0}, {0}, {1}};
            double[][] vector2 = {{0}, {0}, {0}, {1}};
            double[][] prom = MatrixOperation.multiply(rotateMatrix, MatrixOperation.multiply(data.getScaleMatrix(), data.getModel(i).getMatrixM1()));
            double[][] result1 = MatrixOperation.multiply(prom, vector1);
            double[][] result2 = MatrixOperation.multiply(prom, vector2);
            drawLine(g1, new Coordinate3D(-result2[0][0] / result2[3][0], -result2[1][0] / result2[3][0], -result2[2][0] / result2[3][0]), new Coordinate3D(-result1[0][0] / result1[3][0], -result1[1][0] / result1[3][0], -result1[2][0] / result1[3][0]), true);
            g1.setColor(Color.green);
            vector1[0][0] = 0;
            vector1[1][0] = length;
            result1 = MatrixOperation.multiply(prom, vector1);
            drawLine(g1, new Coordinate3D(-result2[0][0] / result2[3][0], -result2[1][0] / result2[3][0], -result2[2][0] / result2[3][0]), new Coordinate3D(-result1[0][0] / result1[3][0], -result1[1][0] / result1[3][0], -result1[2][0] / result1[3][0]), true);
            g1.setColor(Color.blue);
            vector1[1][0] = 0;
            vector1[2][0] = length;
            result1 = MatrixOperation.multiply(prom, vector1);
            drawLine(g1, new Coordinate3D(-result2[0][0] / result2[3][0], -result2[1][0] / result2[3][0], -result2[2][0] / result2[3][0]), new Coordinate3D(-result1[0][0] / result1[3][0], -result1[1][0] / result1[3][0], -result1[2][0] / result1[3][0]), true);

        }
        length = 1;
        g1.setColor(Color.red);
        double[][] vector = {{length}, {0}, {0}, {1}};
        double[][] result = MatrixOperation.multiply(rotateMatrix, vector);
        drawLine(g1, new Coordinate3D(0, 0, 0), new Coordinate3D(-result[0][0] / result[3][0], -result[1][0] / result[3][0], -result[2][0] / result[3][0]), true);
        g1.setColor(Color.green);
        vector[0][0] = 0;
        vector[1][0] = length;
        result = MatrixOperation.multiply(rotateMatrix, vector);
        drawLine(g1, new Coordinate3D(0, 0, 0), new Coordinate3D(-result[0][0] / result[3][0], -result[1][0] / result[3][0], -result[2][0] / result[3][0]), true);
        g1.setColor(Color.blue);
        vector[1][0] = 0;
        vector[2][0] = length;
        result = MatrixOperation.multiply(rotateMatrix, vector);
        drawLine(g1, new Coordinate3D(0, 0, 0), new Coordinate3D(-result[0][0] / result[3][0], -result[1][0] / result[3][0], -result[2][0] / result[3][0]), true);
        g.drawImage(image, ViewOptions.MARGIN, ViewOptions.MARGIN, this);
    }

    private Point coordinateToScreen(Coordinate3D c) {
        double s = Math.min(data.getSh(),data.getSw());
        return new Point((int) Math.round(c.x * 1000.0 / s) + width / 2, (int) Math.round(height / 2 - c.y * 1000.0 / s));
    }

    void drawLine(Graphics g, Coordinate3D c1, Coordinate3D c2, boolean check) {
        if (check(c1) || check) {
            Point p1 = coordinateToScreen(c1);
            Point p2 = coordinateToScreen(c2);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    boolean check(Coordinate3D c) {
        double sw = data.getSw();
        double sh = data.getSh();
        return c.x >= -sw / 2 && c.x <= sw / 2 && c.y >= -sh / 2 && c.y <= sh / 2 && c.z >= data.getZn() && c.z <= 2 * data.getZn();
    }

    void updateData(Data data) {
        this.data = data;
        repaint();
    }
}
