package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private Coordinate3D eyeVector = new Coordinate3D(10, 0, 0);
    private Coordinate3D refVector = new Coordinate3D(-10, 0, 0);
    private Coordinate3D upVector = new Coordinate3D(10, 0, 1);
    private List<Source> sourceList = new ArrayList<>();
    private List<Shape> shapeList = new ArrayList<>();
    private double[][] rotateMatrix = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
    private double zn = 8;
    private double zf = 15;
    private double sw = 5;
    private double sh = 5;
    private double gamma = 1;
    private int depth = 3;
    private Quality quality = Quality.NORMAL;
    private double aR;
    private double aG;
    private double aB;
    private Color background = new Color(129, 0, 129);

    List<List<Segment>> getCoordinate() {
        List<List<Segment>> list = new ArrayList<>();
        int size = shapeList.size();
        double max = Integer.MIN_VALUE;
        double min = Integer.MAX_VALUE;
        Shape shape;
        for (int i = 0; i < size; i++) {
            shape = shapeList.get(i);
            max = Math.max(max, shape.getMax());
            min = Math.min(min, shape.getMin());
            list.add(shape.getCoordinate());
        }
        double[][] matrix = MatrixOperation.multiply(Matrix.getProjMatrix(sw, sh, zn, zf), MatrixOperation.multiply(Matrix.getMcamMatrix(eyeVector, refVector, upVector), MatrixOperation.multiply(Matrix.getScaleMatrix(1.0 / Math.max(Math.abs(max), Math.abs(min))), rotateMatrix)));
        double[][] matrixP = new double[4][1];
        List<Segment> segmentList;
        Segment segment;
        int length;
        for (int i = 0; i < size; i++) {
            segmentList = list.get(i);
            length = segmentList.size();
            for (int j = 0; j < length; j++) {
                segment = segmentList.get(j);
                matrixP[0][0] = segment.point1.x;
                matrixP[1][0] = segment.point1.y;
                matrixP[2][0] = segment.point1.z;
                matrixP[3][0] = segment.point1.w;
                matrixP = MatrixOperation.multiply(matrix, matrixP);
                segment.point1.x = matrixP[0][0] / matrixP[3][0];
                segment.point1.y = matrixP[1][0] / matrixP[3][0];
                segment.point1.z = matrixP[2][0] / -1;
                segment.point1.w = 1;
                matrixP[0][0] = segment.point2.x;
                matrixP[1][0] = segment.point2.y;
                matrixP[2][0] = segment.point2.z;
                matrixP[3][0] = segment.point2.w;
                matrixP = MatrixOperation.multiply(matrix, matrixP);
                segment.point2.x = matrixP[0][0] / matrixP[3][0];
                segment.point2.y = matrixP[1][0] / matrixP[3][0];
                segment.point2.z = matrixP[2][0] / -1;
                segment.point2.w = 1;
            }
        }
        return list;
    }

    public void setZn(double zn) {
        if (zn >= 0 && zn <= 100) {
            this.zn = zn;
        }
    }

    public void setZf(double zf) {
        this.zf = zf;
    }

    public void setSw(double sw) {
        this.sw = sw;
    }

    public void setSh(double sh) {
        this.sh = sh;
    }

    public void setRotateMatrix(double[][] rotateMatrix) {
        this.rotateMatrix = rotateMatrix;
    }

    public double[][] getRotateMatrix() {
        return rotateMatrix;
    }

    public void rotateX(double angle) {
        rotateMatrix = MatrixOperation.multiply(Matrix.getRotateXMatrix(angle), rotateMatrix);
    }

    public void rotateY(double angle) {
        rotateMatrix = MatrixOperation.multiply(Matrix.getRotateYMatrix(angle), rotateMatrix);
    }

    public void rotateZ(double angle) {
        rotateMatrix = MatrixOperation.multiply(Matrix.getRotateZMatrix(angle), rotateMatrix);
    }

    public double getZn() {
        return zn;
    }

    public double getZf() {
        return zf;
    }

    public double getSw() {
        return sw;
    }

    public double getSh() {
        return sh;
    }

    public void clearRotate() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j) {
                    rotateMatrix[i][j] = 1;
                } else {
                    rotateMatrix[i][j] = 0;
                }
            }
        }
    }

    public double getaR() {
        return aR;
    }

    public double getaG() {
        return aG;
    }

    public double getaB() {
        return aB;
    }

    public void setaRGB(Color aRGB) {
        aR = aRGB.getRed() / 255.0;
        aG = aRGB.getGreen()/ 255.0;
        aB = aRGB.getBlue()/ 255.0;
    }

    public List<Source> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<Source> sourceList) {
        this.sourceList = sourceList;
    }

    public List<Shape> getShapeList() {
        return shapeList;
    }

    public void setShapeList(List<Shape> shapeList) {
        this.shapeList = shapeList;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Coordinate3D getEyeVector() {
        return eyeVector;
    }

    public void setEyeVector(Coordinate3D eyeMatrix) {
        this.eyeVector = eyeMatrix;
    }

    public Coordinate3D getRefVector() {
        return refVector;
    }

    public void setRefVector(Coordinate3D refVector) {
        this.refVector = refVector;
    }

    public Coordinate3D getUpVector() {
        return upVector;
    }

    public void setUpVector(Coordinate3D upVector) {
        this.upVector = upVector;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

}
