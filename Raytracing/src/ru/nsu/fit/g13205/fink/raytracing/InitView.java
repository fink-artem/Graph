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
import java.util.Stack;

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
            g1.setColor(new Color((float) shapeList.get(i).kdr, (float) shapeList.get(i).kdg, (float) shapeList.get(i).kdb));
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
        double backGroundRed = data.getBackground().getRed() / 255.0;
        double backGroundGreen = data.getBackground().getGreen() / 255.0;
        double backGroundBlue = data.getBackground().getBlue() / 255.0;
        Coordinate3D eye = data.getEyeVector();
        Coordinate3D normal = data.getRefVector().minus(eye);
        Coordinate3D znPoint = normal.divide(normal.getNorm()).multiply(data.getZn()).plus(eye);
        Coordinate3D right = normal.vectorMultiply(data.getUpVector());
        Coordinate3D up = right.vectorMultiply(normal);
        up = up.normalize();
        right = right.normalize();
        Coordinate3D stepX = znPoint.plus(right.multiply(data.getSw() / 2)).minus(znPoint.minus(right.multiply(data.getSw() / 2))).divide(width);
        Coordinate3D stepY = znPoint.plus(up.multiply(data.getSh() / 2)).minus(znPoint.minus(up.multiply(data.getSh() / 2))).divide(height);
        List<Source> sourceList = data.getSourceList();
        Coordinate3D start = znPoint.plus(up.multiply(data.getSh() / 2)).minus(right.multiply(data.getSw() / 2));
        Coordinate3D now;
        Coordinate3D l;
        double nl, nh;
        Coordinate3D nearestIntersectionPoint;
        Coordinate3D intersectionPoint;
        ShapeAndCoordinate shapeAndCoordinate;
        Shape nearestShape;
        double length;
        double ir, ig, ib;
        double depth = data.getDepth();
        Coordinate3D startCoordinate, vector;
        int size;
        double red[][] = new double[height][width];
        double green[][] = new double[height][width];
        double blue[][] = new double[height][width];
        double max = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                now = start.plus(stepX.multiply(j)).minus(stepY.multiply(i));
                if (now.z == 0 && now.y > 0) {
                    System.out.println("");
                }
                Stack<ShapeAndCoordinate> stack = new Stack();
                startCoordinate = eye;
                vector = now.minus(startCoordinate).normalize();
                for (int k = 0; k < depth; k++) {
                    shapeAndCoordinate = getNearestShape(startCoordinate, vector);
                    if (shapeAndCoordinate.shape == null) {
                        break;
                    }
                    stack.push(shapeAndCoordinate);
                    startCoordinate = shapeAndCoordinate.coordinate;
                    normal = shapeAndCoordinate.shape.getNormal(startCoordinate);
                    vector = vector.minus(normal.multiply(2 * normal.scalarMultiply(vector)));
                }
                if (!stack.empty()) {
                    size = stack.size();
                    ir = 0;
                    ig = 0;
                    ib = 0;
                    for (int q = 0; q < size; q++) {
                        shapeAndCoordinate = stack.pop();
                        nearestIntersectionPoint = shapeAndCoordinate.coordinate;
                        nearestShape = shapeAndCoordinate.shape;
                        ir *= nearestShape.ksr;
                        ig *= nearestShape.ksg;
                        ib *= nearestShape.ksb;
                        ir += data.getaR() * nearestShape.kdr;
                        ig += data.getaG() * nearestShape.kdg;
                        ib += data.getaB() * nearestShape.kdb;
                        for (Source source : sourceList) {
                            intersectionPoint = getNearestShape(source.coordinate, (nearestIntersectionPoint.minus(source.coordinate)).normalize()).coordinate;
                            if (nearestIntersectionPoint.equals(intersectionPoint)) {
                                length = fatt(nearestIntersectionPoint.length(source.coordinate));
                                normal = nearestShape.getNormal(nearestIntersectionPoint);
                                l = source.coordinate.minus(nearestIntersectionPoint).normalize();
                                nl = normal.scalarMultiply(l);
                                nh = Math.pow(normal.scalarMultiply(now.minus(eye).multiply(-1).plus(l).normalize()), nearestShape.power);
                                ir += length * source.lr * (nearestShape.kdr * nl + nearestShape.ksr * nh);
                                ig += length * source.lg * (nearestShape.kdg * nl + nearestShape.ksg * nh);
                                ib += length * source.lb * (nearestShape.kdb * nl + nearestShape.ksb * nh);
                            }
                        }
                    }
                    red[i][j] = ir;
                    green[i][j] = ig;
                    blue[i][j] = ib;
                } else {
                    red[i][j] = backGroundRed;
                    green[i][j] = backGroundGreen;
                    blue[i][j] = backGroundBlue;
                }
                max = Math.max(max, Math.max(Math.max(red[i][j], green[i][j]), blue[i][j]));
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                red[i][j] /= max;
                green[i][j] /= max;
                blue[i][j] /= max;
                red[i][j] = Math.pow(red[i][j], data.getGamma());
                green[i][j] = Math.pow(green[i][j], data.getGamma());
                blue[i][j] = Math.pow(blue[i][j], data.getGamma());
                image.setRGB(j, i, (new Color((float) red[i][j], (float) green[i][j], (float) blue[i][j])).getRGB());
            }
        }
        repaint();
    }

    private ShapeAndCoordinate getNearestShape(Coordinate3D start, Coordinate3D end) {
        List<Shape> shapeList = data.getShapeList();
        Coordinate3D intersectionPoint;
        Coordinate3D nearestIntersectionPoint = null;
        double minLength = Double.POSITIVE_INFINITY;
        double length;
        Shape nearestShape = null;
        for (Shape shape : shapeList) {
            intersectionPoint = shape.getIntersectionPoint(start, end);
            if (intersectionPoint != null) {
                length = start.length(intersectionPoint);
                if (length < minLength) {
                    minLength = length;
                    nearestShape = shape;
                    nearestIntersectionPoint = intersectionPoint;
                }
            }
        }
        return new ShapeAndCoordinate(nearestShape, nearestIntersectionPoint);
    }

    private Point coordinateToScreen(Coordinate3D c) {
        double s = Math.min(data.getSh(), data.getSw());
        return new Point((int) Math.round(c.x * 1000.0 / s + width / 2.0), (int) Math.round(height / 2.0 - c.y * 1000.0 / s));
    }

    private void drawLine(Graphics g, Coordinate3D c1, Coordinate3D c2, boolean check) {
        if (check(c1) || check) {
            Point p1 = coordinateToScreen(c1);
            Point p2 = coordinateToScreen(c2);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    private double fatt(double d) {
        return 1.0 / (1.0 + d);
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
