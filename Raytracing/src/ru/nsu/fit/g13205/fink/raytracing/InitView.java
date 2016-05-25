package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Stack;
import javax.swing.JLabel;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private static final double SPEED = 20.0;
    private final JLabel statusBar;
    private Point startPoint;
    private Data data;
    private BufferedImage image;
    private boolean taken = false;
    private boolean ctrl = false;
    private boolean renderMode = false;
    private int width = 500;
    private int height = 500;

    public InitView(Data data, JLabel statusBar) {
        this.data = data;
        this.statusBar = statusBar;
        setBackground(ViewOptions.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(width + 2 * ViewOptions.MARGIN, height + 2 * ViewOptions.MARGIN));
        draw();
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (taken) {
                    InitView.this.data.rotateZ((e.getX() - startPoint.x) / SPEED);
                    InitView.this.data.rotateY(-(e.getY() - startPoint.y) / SPEED);
                    startPoint = e.getPoint();
                    draw();
                }
            }

        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getX() >= ViewOptions.MARGIN && e.getX() < width + ViewOptions.MARGIN && e.getY() >= ViewOptions.MARGIN && e.getY() < height + ViewOptions.MARGIN && !renderMode) {
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
            if (!ctrl) {
                InitView.this.data.setZn(InitView.this.data.getZn() - e.getPreciseWheelRotation() / 10);
            } else {
                Coordinate3D eye = InitView.this.data.getEyeVector();
                InitView.this.data.setEyeVector(eye.plus(eye.minus(InitView.this.data.getRefVector()).normalize().multiply(e.getPreciseWheelRotation() / 10)));
            }
            draw();
        });

        Toolkit.getDefaultToolkit().addAWTEventListener((AWTEvent event) -> {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                    Coordinate3D eye = InitView.this.data.getEyeVector();
                    Coordinate3D normal = (InitView.this.data.getRefVector()).minus(eye);
                    Coordinate3D right = normal.vectorMultiply(InitView.this.data.getUpVector());
                    Coordinate3D up = right.vectorMultiply(normal).normalize();
                    InitView.this.data.setEyeVector(eye.minus(up));
                    InitView.this.data.setRefVector(InitView.this.data.getRefVector().minus(up));
                    draw();
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                    Coordinate3D eye = InitView.this.data.getEyeVector();
                    Coordinate3D normal = (InitView.this.data.getRefVector()).minus(eye);
                    Coordinate3D right = normal.vectorMultiply(InitView.this.data.getUpVector());
                    Coordinate3D up = right.vectorMultiply(normal).normalize();
                    InitView.this.data.setEyeVector(eye.plus(up));
                    InitView.this.data.setRefVector(InitView.this.data.getRefVector().plus(up));
                    draw();
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                    Coordinate3D eye = InitView.this.data.getEyeVector();
                    Coordinate3D normal = (InitView.this.data.getRefVector()).minus(eye);
                    Coordinate3D right = normal.vectorMultiply(InitView.this.data.getUpVector()).normalize();
                    InitView.this.data.setEyeVector(eye.plus(right));
                    InitView.this.data.setRefVector(InitView.this.data.getRefVector().plus(right));
                    draw();
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                    Coordinate3D eye = InitView.this.data.getEyeVector();
                    Coordinate3D normal = (InitView.this.data.getRefVector()).minus(eye);
                    Coordinate3D right = normal.vectorMultiply(InitView.this.data.getUpVector()).normalize();
                    InitView.this.data.setEyeVector(eye.minus(right));
                    InitView.this.data.setRefVector(InitView.this.data.getRefVector().minus(right));
                    draw();
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrl = true;
                }
            } else {
                if (keyEvent.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrl = false;
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);

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
        repaint();
    }

    public void render() {
        if (!renderMode) {
            return;
        }
        Thread t = new Thread(() -> {
            double quality = data.getQuality().num;
            int widthArea = (int) Math.round(width * quality);
            int heightArea = (int) Math.round(height * quality);
            float backGroundRed = (float) data.getBr();
            float backGroundGreen = (float) data.getBg();
            float backGroundBlue = (float) data.getBb();
            double[][] fullrotateMatrix = MatrixOperation.multiply(Matrix.getTranslateMatrix(data.getRefVector().x, data.getRefVector().y, data.getRefVector().z), MatrixOperation.multiply(MatrixOperation.transporter(data.getRotateMatrix()), Matrix.getTranslateMatrix(-data.getRefVector().x, -data.getRefVector().y, -data.getRefVector().z)));
            Coordinate3D eye = new Coordinate3D(MatrixOperation.multiply(fullrotateMatrix, data.getEyeVector().getMatrix()));
            Coordinate3D normal = (new Coordinate3D(MatrixOperation.multiply(fullrotateMatrix, data.getRefVector().getMatrix()))).minus(eye);
            Coordinate3D znPoint = normal.divide(normal.getNorm()).multiply(data.getZn()).plus(eye);
            Coordinate3D right = normal.vectorMultiply(new Coordinate3D(MatrixOperation.multiply(MatrixOperation.transporter(data.getRotateMatrix()), data.getUpVector().getMatrix())));
            Coordinate3D up = right.vectorMultiply(normal);
            up = up.normalize();
            right = right.normalize();
            Coordinate3D stepX = znPoint.plus(right.multiply(data.getSw() / 2)).minus(znPoint.minus(right.multiply(data.getSw() / 2))).divide(widthArea);
            Coordinate3D stepY = znPoint.plus(up.multiply(data.getSh() / 2)).minus(znPoint.minus(up.multiply(data.getSh() / 2))).divide(heightArea);
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
            float ir, ig, ib;
            double depth = data.getDepth();
            Coordinate3D startCoordinate, vector;
            Coordinate3D peekCoordinate;
            int size;
            float red[][] = new float[heightArea][widthArea];
            float green[][] = new float[heightArea][widthArea];
            float blue[][] = new float[heightArea][widthArea];
            float max = 0;
            for (int i = 0; i < heightArea; i++) {
                for (int j = 0; j < widthArea; j++) {
                    now = start.plus(stepX.multiply(j)).minus(stepY.multiply(i));
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
                            if (!stack.empty()) {
                                peekCoordinate = stack.peek().coordinate;
                            } else {
                                peekCoordinate = eye;
                            }
                            nearestIntersectionPoint = shapeAndCoordinate.coordinate;
                            nearestShape = shapeAndCoordinate.shape;
                            ir *= nearestShape.ksr;
                            ig *= nearestShape.ksg;
                            ib *= nearestShape.ksb;
                            ir += data.getaR() * nearestShape.kdr;
                            ig += data.getaG() * nearestShape.kdg;
                            ib += data.getaB() * nearestShape.kdb;
                            normal = nearestShape.getNormal(nearestIntersectionPoint);
                            for (Source source : sourceList) {
                                intersectionPoint = getNearestShape(source.coordinate, (nearestIntersectionPoint.minus(source.coordinate)).normalize()).coordinate;
                                if (nearestIntersectionPoint.equals(intersectionPoint)) {
                                    length = fatt(nearestIntersectionPoint.length(source.coordinate));
                                    l = source.coordinate.minus(nearestIntersectionPoint).normalize();
                                    nl = normal.scalarMultiply(l);
                                    double n = normal.scalarMultiply(peekCoordinate.minus(shapeAndCoordinate.coordinate).normalize().plus(l).normalize());
                                    nh = Math.pow(normal.scalarMultiply(peekCoordinate.minus(shapeAndCoordinate.coordinate).normalize().plus(l).normalize()), nearestShape.power);
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
                statusBar.setText("Progress: " + (int) (i * 100.0 / widthArea) + "%");
            }
            double gamma = data.getGamma();
            float resultRed;
            float resultGreen;
            float resultBlue;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    resultRed = 0;
                    resultGreen = 0;
                    resultBlue = 0;
                    for (int k = 0; k < quality; k++) {
                        for (int m = 0; m < quality; m++) {
                            resultRed += red[(int) (i * quality) + k][(int) (j * quality) + m];
                            resultGreen += green[(int) (i * quality) + k][(int) (j * quality) + m];
                            resultBlue += blue[(int) (i * quality) + k][(int) (j * quality) + m];
                        }
                    }
                    if (quality > 1) {
                        resultRed /= Math.pow(quality, 2);
                        resultGreen /= Math.pow(quality, 2);
                        resultBlue /= Math.pow(quality, 2);
                    }
                    resultRed /= max;
                    resultGreen /= max;
                    resultBlue /= max;
                    resultRed = (float) Math.pow(resultRed, gamma);
                    resultGreen = (float) Math.pow(resultGreen, gamma);
                    resultBlue = (float) Math.pow(resultBlue, gamma);
                    image.setRGB(j, i, (new Color(resultRed, resultGreen, resultBlue)).getRGB());
                }
            }
            repaint();
            statusBar.setText("Ready");
        });
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
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
        int wh = Math.min(width, height);
        return new Point((int) Math.round(width / 2.0 - c.x / s * wh), (int) Math.round(c.y / s * wh + height / 2.0));
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
