package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Data {

    public static final Coordinate3D eyeMatrix = new Coordinate3D(10, 0, 0);
    public static final Coordinate3D refMatrix = new Coordinate3D(-10, 0, 0);
    public static final Coordinate3D upMatrix = new Coordinate3D(10, 0, 1);
    private List<Source> sourceList = new ArrayList<>();
    private List<Shape> shapeList = new ArrayList<>();
    private double[][] rotateMatrix = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
    private double zn = 8;
    private double zf = 15;
    private double sw = 1;
    private double sh = 1;
    private double max;
    private double min;
    private Color aRGB;

    List<List<Segment>> getCoordinate() {
        List<List<Segment>> list = new ArrayList<>();
        int size = shapeList.size();
        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;
        Shape shape;
        for (int i = 0; i < size; i++) {
            shape = shapeList.get(i);
            max = Math.max(max, shape.getMax());
            min = Math.min(min, shape.getMin());
            list.add(shape.getCoordinate());
        }
        double[][] matrix = MatrixOperation.multiply(Matrix.getProjMatrix(sw, sh, zn, zf), MatrixOperation.multiply(Matrix.getMcamMatrix(eyeMatrix, refMatrix, upMatrix), MatrixOperation.multiply(Matrix.getScaleMatrix(1.0 / Math.max(Math.abs(max), Math.abs(min))), rotateMatrix)));
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

    public double[][] getScaleMatrix() {
        return Matrix.getScaleMatrix(1.0 / Math.max(Math.abs(max), Math.abs(min)));
    }

    public Color getaRGB() {
        return aRGB;
    }

    public void setaRGB(Color aRGB) {
        this.aRGB = aRGB;
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

}
