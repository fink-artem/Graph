package ru.nsu.fit.g13205.fink.raytracing;

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
    private boolean renderMode = false;

    public InitView(Data data) {
        this.data = data;
        setBackground(ViewOptions.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(width + 2 * ViewOptions.MARGIN, height + 2 * ViewOptions.MARGIN));
        draw();
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (taken) {
                    InitView.this.data.rotateZ((e.getX() - startPoint.x) / SPEED);
                    InitView.this.data.rotateY((e.getY() - startPoint.y) / SPEED);
                    startPoint = e.getPoint();
                    draw();
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
            draw();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, ViewOptions.MARGIN, ViewOptions.MARGIN, this);
    }

    public final void draw() {
        if (renderMode) {
            return;
        }
        height = (int) Math.round(width * data.getSh() / data.getSw());
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width + 2 * ViewOptions.MARGIN, height + 2 * ViewOptions.MARGIN));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, Color.BLACK.getRGB());
            }
        }
        double[][] rotateMatrix = MatrixOperation.multiply(Matrix.getMcamMatrix(data.getEyeVector(), data.getRefVector(), data.getUpVector()), data.getRotateMatrix());
        Graphics g1 = image.getGraphics();
        List<List<Segment>> list = data.getCoordinate();
        List<Shape> shapeList = data.getShapeList();
        int size = list.size();
        int length;
        for (int i = 0; i < size; i++) {
            List<Segment> segmentList = list.get(i);
            g1.setColor(new Color(shapeList.get(i).kdr, shapeList.get(i).kdg, shapeList.get(i).kdb));
            length = segmentList.size();
            for (int l = 0; l < length; l++) {
                drawLine(g1, segmentList.get(l).point1, segmentList.get(l).point2, true);
            }
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
        repaint();
    }

    public void render() {
        if (!renderMode) {
            return;
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

            }
        }
    }

    private Point coordinateToScreen(Coordinate3D c) {
        double s = Math.min(data.getSh(), data.getSw());
        return new Point((int) Math.round(c.x * 1000.0 / s + width / 2.0), (int) Math.round(height / 2.0 - c.y * 1000.0 / s));
    }

    private Coordinate3D screenToCoordinate(Point p) {
        double s = Math.min(data.getSh(), data.getSw());
        return new Coordinate3D((int) Math.round((p.x - width / 2.0) * s / 1000.0), (int) Math.round(-(p.y - height / 2.0) * s / 1000.0), data.getEyeVector().z);
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
        draw();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setRenderMode(boolean renderMode) {
        this.renderMode = renderMode;
    }

}
