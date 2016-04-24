package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private static final double STEP = 0.001;
    private Color color;
    private Coordinate3D[][] coordinate;
    private List<Coordinate2D> pivotsList = new ArrayList<>();
    private double[][] matrixM1 = new double[4][4];
    private double[][] rotateMatrix;
    private final Data data;
    private double cx;
    private double cy;
    private double cz;

    public Model(Data data, double cx, double cy, double cz, double[][] rotateMatrix, Color color) {
        this.data = data;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
        this.color = color;
        this.rotateMatrix = rotateMatrix;
        matrixM1 = MatrixOperation.multiply(Matrix.getTranslateMatrix(cx, cy, cz), rotateMatrix);
    }

    public void addPivot(int position, Coordinate2D pivot) {
        pivotsList.add(position, pivot);
        updateCoordinate();
    }

    public void deletePivot(int position) {
        if (pivotsList.size() > 4) {
            pivotsList.remove(position);
            updateCoordinate();
        }
    }

    public void setPivot(int position, Coordinate2D pivot) {
        pivotsList.set(position, pivot);
        updateCoordinate();
    }

    public List<Coordinate2D> getPivotsList() {
        return pivotsList;
    }

    public void setPivotsList(List<Coordinate2D> pivotsList) {
        this.pivotsList = pivotsList;
        updateCoordinate();
    }

    public Coordinate3D[][] getCoordinate() {
        return coordinate;
    }

    void updateCoordinate() {
        int n = data.getN();
        int m = data.getM();
        int k = data.getK();
        coordinate = new Coordinate3D[n * k + 1][m * k + 1];
        double a = data.getA();
        double b = data.getB();
        double c = data.getC();
        double d = data.getD();
        double length = 0;
        int size = pivotsList.size() - 2;
        Coordinate2D c1 = null;
        Coordinate2D c2;
        double[][] matrixG = new double[4][1];
        double[][] matrixT = new double[1][4];
        double[][] matrixX;
        double[][] matrixY;
        for (int i = 1; i < size; i++) {
            matrixG[0][0] = pivotsList.get(i - 1).x;
            matrixG[1][0] = pivotsList.get(i).x;
            matrixG[2][0] = pivotsList.get(i + 1).x;
            matrixG[3][0] = pivotsList.get(i + 2).x;
            matrixX = MatrixOperation.multiply(Matrix.MS, matrixG);
            matrixG[0][0] = pivotsList.get(i - 1).y;
            matrixG[1][0] = pivotsList.get(i).y;
            matrixG[2][0] = pivotsList.get(i + 1).y;
            matrixG[3][0] = pivotsList.get(i + 2).y;
            matrixY = MatrixOperation.multiply(Matrix.MS, matrixG);
            c1 = new Coordinate2D(matrixX[3][0], matrixY[3][0]);
            for (double t = 0; t < 1; t += STEP) {
                matrixT[0][0] = Math.pow(t, 3);
                matrixT[0][1] = Math.pow(t, 2);
                matrixT[0][2] = t;
                matrixT[0][3] = 1;
                c2 = c1;
                c1 = new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]);
                length += Operation.distance(c1, c2);
            }
            matrixT[0][0] = 1;
            matrixT[0][1] = 1;
            matrixT[0][2] = 1;
            matrixT[0][3] = 1;
            c2 = c1;
            c1 = new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]);
            length += Operation.distance(c1, c2);
        }
        double edgeLength = length * (b - a) / (n * k);
        double start = length * a;
        int number = 0;
        length = 0;

        boolean bool = false;
        one:
        for (int i = 1; i < size; i++) {
            matrixG[0][0] = pivotsList.get(i - 1).x;
            matrixG[1][0] = pivotsList.get(i).x;
            matrixG[2][0] = pivotsList.get(i + 1).x;
            matrixG[3][0] = pivotsList.get(i + 2).x;
            matrixX = MatrixOperation.multiply(Matrix.MS, matrixG);
            matrixG[0][0] = pivotsList.get(i - 1).y;
            matrixG[1][0] = pivotsList.get(i).y;
            matrixG[2][0] = pivotsList.get(i + 1).y;
            matrixG[3][0] = pivotsList.get(i + 2).y;
            matrixY = MatrixOperation.multiply(Matrix.MS, matrixG);
            c1 = new Coordinate2D(matrixX[3][0], matrixY[3][0]);
            for (double t = 0; t < 1; t += STEP) {
                matrixT[0][0] = Math.pow(t, 3);
                matrixT[0][1] = Math.pow(t, 2);
                matrixT[0][2] = t;
                matrixT[0][3] = 1;
                c2 = c1;
                c1 = new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]);
                length += Operation.distance(c1, c2);
                if (length >= start || bool) {
                    if (!bool) {
                        coordinate[number][0] = convert2Dto3D(new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]));
                        number++;
                    }
                    bool = true;
                    if (length >= edgeLength) {
                        length %= edgeLength;
                        coordinate[number][0] = convert2Dto3D(c1);
                        number++;
                        if (number == coordinate.length) {
                            break one;
                        }
                    }
                }
            }
            matrixT[0][0] = 1;
            matrixT[0][1] = 1;
            matrixT[0][2] = 1;
            matrixT[0][3] = 1;
            c2 = c1;
            c1 = new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]);
            length += Operation.distance(c1, c2);
            if (length >= start || bool) {
                if (!bool) {
                    coordinate[number][0] = convert2Dto3D(new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]));
                    number++;
                }
                bool = true;
                if (length >= edgeLength) {
                    length %= edgeLength;
                    coordinate[number][0] = convert2Dto3D(c1);
                    number++;
                }
            }
        }
        if (number != coordinate.length) {
            coordinate[number][0] = convert2Dto3D(c1);
        }

        edgeLength = 2 * Math.PI * (d - c) / 6.28 / m / k;
        start = 2 * Math.PI * c / 6.28;
        int n2 = n * k + 1;
        int m2 = m * k + 1;
        Coordinate3D copy;
        for (int i = 0; i < n2; i++) {
            try {
                copy = new Coordinate3D(coordinate[i][0].x, coordinate[i][0].y, coordinate[i][0].z);
                for (int j = 0; j < m2; j++) {
                    coordinate[i][j] = new Coordinate3D(copy.x * Math.cos(start + j * edgeLength), copy.x * Math.sin(start + j * edgeLength), copy.z);
                }
            } catch (NullPointerException e) {
            }
        }
    }

    Coordinate3D convert2Dto3D(Coordinate2D c) {
        return new Coordinate3D(c.y, 0, c.x);
    }

    public Color getColor() {
        return color;
    }

    public double[][] getRotateMatrix() {
        return rotateMatrix;
    }

    public double getCx() {
        return cx;
    }

    public double getCy() {
        return cy;
    }

    public double getCz() {
        return cz;
    }

    public void setCx(double cx) {
        this.cx = cx;
        matrixM1 = MatrixOperation.multiply(Matrix.getTranslateMatrix(cx, cy, cz), rotateMatrix);
    }

    public void setCy(double cy) {
        this.cy = cy;
        matrixM1 = MatrixOperation.multiply(Matrix.getTranslateMatrix(cx, cy, cz), rotateMatrix);
    }

    public void setCz(double cz) {
        this.cz = cz;
        matrixM1 = MatrixOperation.multiply(Matrix.getTranslateMatrix(cx, cy, cz), rotateMatrix);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void rotateX(double angle) {
        rotateMatrix = MatrixOperation.multiply(Matrix.getRotateXMatrix(angle), rotateMatrix);
        matrixM1 = MatrixOperation.multiply(Matrix.getTranslateMatrix(cx, cy, cz), rotateMatrix);
    }

    public void rotateY(double angle) {
        rotateMatrix = MatrixOperation.multiply(Matrix.getRotateYMatrix(angle), rotateMatrix);
        matrixM1 = MatrixOperation.multiply(Matrix.getTranslateMatrix(cx, cy, cz), rotateMatrix);
    }

    public void rotateZ(double angle) {
        rotateMatrix = MatrixOperation.multiply(Matrix.getRotateZMatrix(angle), rotateMatrix);
        matrixM1 = MatrixOperation.multiply(Matrix.getTranslateMatrix(cx, cy, cz), rotateMatrix);
    }

    public double[][] getMatrixM1() {
        return matrixM1;
    }

}
