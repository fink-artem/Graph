package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private final double STEP = 0.01;
    private final Color color;
    private Coordinate3D[][] coordinate;
    private List<Coordinate2D> pivotsList = new ArrayList<>();
    private double[][] matrixM1 = new double[4][4];
    private int n;
    private int m;
    private int k;
    private double cx;
    private double cy;
    private double cz;
    private int r;
    private int g;
    private int b;

    public Model(int n, int m, int k, double cx, double cy, double cz, double[][] rotateMatrix, Color color) {
        this.n = n + 1;
        this.m = m;
        this.k = k;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
        this.color = color;
        matrixM1 = MatrixOperation.multiply(Matrix.getTranslateMatrix(cx, cy, cz), rotateMatrix);
        coordinate = new Coordinate3D[this.n][m];
    }

    void addPivot(int position, Coordinate2D pivot) {
        pivotsList.add(position, pivot);
        updateCoordinate();
    }

    void deletePivot(int position) {
        if (pivotsList.size() > 4) {
            pivotsList.remove(position);
            updateCoordinate();
        }
    }

    void setPivot(int position, Coordinate2D pivot) {
        pivotsList.set(position, pivot);
        updateCoordinate();
    }

    List<Coordinate2D> getPivotsList() {
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
        double length = 0;
        int size = pivotsList.size() - 2;
        Coordinate2D c;
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
            c = new Coordinate2D(matrixX[3][0], matrixY[3][0]);
            for (double t = 0; t < 1; t += STEP) {
                matrixT[0][0] = Math.pow(t, 3);
                matrixT[0][1] = Math.pow(t, 2);
                matrixT[0][2] = t;
                matrixT[0][3] = 1;
                c2 = c;
                c = new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]);
                length += Operation.distance(c, c2);
            }
            matrixT[0][0] = 1;
            matrixT[0][1] = 1;
            matrixT[0][2] = 1;
            matrixT[0][3] = 1;
            c2 = c;
            c = new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]);
            length += Operation.distance(c, c2);
        }
        double edgeLength = length / (n - 1);
        int number = 1;
        length = 0;

        matrixG[0][0] = pivotsList.get(0).x;
        matrixG[1][0] = pivotsList.get(1).x;
        matrixG[2][0] = pivotsList.get(2).x;
        matrixG[3][0] = pivotsList.get(3).x;
        matrixX = MatrixOperation.multiply(Matrix.MS, matrixG);
        matrixG[0][0] = pivotsList.get(0).y;
        matrixG[1][0] = pivotsList.get(1).y;
        matrixG[2][0] = pivotsList.get(2).y;
        matrixG[3][0] = pivotsList.get(3).y;
        matrixY = MatrixOperation.multiply(Matrix.MS, matrixG);
        c = new Coordinate2D(matrixX[3][0], matrixY[3][0]);
        matrixT[0][0] = 0;
        matrixT[0][1] = 0;
        matrixT[0][2] = 0;
        matrixT[0][3] = 1;
        coordinate[0][0] = convert2Dto3D(new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]));
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
            c = new Coordinate2D(matrixX[3][0], matrixY[3][0]);
            for (double t = 0; t < 1; t += STEP) {
                matrixT[0][0] = Math.pow(t, 3);
                matrixT[0][1] = Math.pow(t, 2);
                matrixT[0][2] = t;
                matrixT[0][3] = 1;
                c2 = c;
                c = new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]);
                length += Operation.distance(c, c2);
                if (length > edgeLength) {
                    length %= edgeLength;
                    coordinate[number][0] = convert2Dto3D(c);
                    number++;
                }
            }
            matrixT[0][0] = 1;
            matrixT[0][1] = 1;
            matrixT[0][2] = 1;
            matrixT[0][3] = 1;
            c2 = c;
            c = new Coordinate2D(MatrixOperation.multiply(matrixT, matrixX)[0][0], MatrixOperation.multiply(matrixT, matrixY)[0][0]);
            length += Operation.distance(c, c2);
        }
        if (number != n) {
            coordinate[number][0] = convert2Dto3D(c);
        }
        edgeLength = 2 * Math.PI / m;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < m; j++) {
                try {
                    coordinate[i][j] = new Coordinate3D(coordinate[i][0].x * Math.cos(j * edgeLength), coordinate[i][0].x * Math.sin(j * edgeLength), coordinate[i][0].z);
                } catch (NullPointerException e) {
                }
            }
        }
        double[][] matrixP = new double[4][1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrixP[0][0] = coordinate[i][j].x;
                matrixP[1][0] = coordinate[i][j].y;
                matrixP[2][0] = coordinate[i][j].z;
                matrixP[3][0] = coordinate[i][j].w;
                matrixP = MatrixOperation.multiply(matrixM1, matrixP);
                coordinate[i][j].x = matrixP[0][0] / matrixP[3][0];
                coordinate[i][j].y = matrixP[1][0] / matrixP[3][0];
                coordinate[i][j].z = matrixP[2][0] / matrixP[3][0];
                coordinate[i][j].w = 1;
            }
        }
    }

    Coordinate3D convert2Dto3D(Coordinate2D c) {
        return new Coordinate3D(c.y, 0, c.x);
    }

    public void setN(int n) {
        this.n = n + 1;
        coordinate = new Coordinate3D[this.n][m];
        updateCoordinate();
    }

    public void setM(int m) {
        this.m = m;
        coordinate = new Coordinate3D[n][m];
        updateCoordinate();
    }

    public void setK(int k) {
        this.k = k;
        updateCoordinate();
    }

}
