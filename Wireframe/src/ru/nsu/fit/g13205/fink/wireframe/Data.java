package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private final Coordinate3D eyeMatrix = new Coordinate3D(0, 0, -10);
    private final Coordinate3D refMatrix = new Coordinate3D(0, 0, 10);
    private final Coordinate3D upMatrix = new Coordinate3D(0, 1, 0);
    private final List<Model> modelList = new ArrayList<>();
    private double[][] rotateMatrix;
    private int rotatingModelNumber = -1;
    private int n;
    private int m;
    private int k;
    private double a;
    private double b;
    private double c;
    private double d;
    private double zn;
    private double zf;
    private double sw;
    private double sh;
    private int br;
    private int bg;
    private int bb;
    private double max;
    private double min;

    int addNewModel(double cx, double cy, double cz, double[][] rotateMatrix, Color color) {
        modelList.add(new Model(this, cx, cy, cz, rotateMatrix, color));
        return modelList.size() - 1;
    }

    void addNewPivotInModel(int modelNumber, int position, Coordinate2D coordinate) {
        modelList.get(modelNumber).addPivot(position, coordinate);
    }

    void deleteModel(int modelNumber) {
        modelList.remove(modelNumber);
    }

    void deletePivotFromModel(int modelNumber, int position) {
        modelList.get(modelNumber).deletePivot(position);
    }

    void setPivotInModel(int modelNumber, int position, Coordinate2D coordinate) {
        modelList.get(modelNumber).setPivot(position, coordinate);
    }

    List<Coordinate2D> getPivots(int modelNumber) {
        return modelList.get(modelNumber).getPivotsList();
    }

    Color getModelColor(int modelNumber) {
        return modelList.get(modelNumber).getColor();
    }

    Coordinate3D[][] getCoordinate(int modelNumber) {
        Coordinate3D[][] coordinate = modelList.get(modelNumber).getCoordinate();
        int n = coordinate.length;
        int m = coordinate[0].length;
        Coordinate3D[][] newCoordinate = new Coordinate3D[n][m];
        double[][] matrix = MatrixOperation.multiply(rotateMatrix, modelList.get(modelNumber).getMatrixM1());
        double[][] matrixP = new double[4][1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                try {
                    matrixP[0][0] = coordinate[i][j].x;
                    matrixP[1][0] = coordinate[i][j].y;
                    matrixP[2][0] = coordinate[i][j].z;
                    matrixP[3][0] = coordinate[i][j].w;
                } catch (NullPointerException e) {
                }
                matrixP = MatrixOperation.multiply(matrix, matrixP);
                newCoordinate[i][j] = new Coordinate3D(matrixP[0][0] / matrixP[3][0], matrixP[1][0] / matrixP[3][0], matrixP[2][0] / matrixP[3][0]);
                if (newCoordinate[i][j].x > max) {
                    max = newCoordinate[i][j].x;
                }
                if (newCoordinate[i][j].y > max) {
                    max = newCoordinate[i][j].y;
                }
                if (newCoordinate[i][j].z > max) {
                    max = newCoordinate[i][j].z;
                }
                if (newCoordinate[i][j].x < min) {
                    min = newCoordinate[i][j].x;
                }
                if (newCoordinate[i][j].y < min) {
                    min = newCoordinate[i][j].y;
                }
                if (newCoordinate[i][j].z < min) {
                    min = newCoordinate[i][j].z;
                }
            }
        }
        return newCoordinate;
    }

    List<Coordinate3D[][]> getCoordinate() {
        List<Coordinate3D[][]> list = new ArrayList<>();
        int size = modelList.size();
        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            list.add(getCoordinate(i));
        }
        double[][] matrix = MatrixOperation.multiply(Matrix.getProjMatrix(sw, sh, zn, zf), MatrixOperation.multiply(Matrix.getMcamMatrix(eyeMatrix, refMatrix, upMatrix), Matrix.getScaleMatrix(1.0 / Math.max(Math.abs(max), Math.abs(min)))));
        Coordinate3D[][] coordinate;
        double[][] matrixP = new double[4][1];
        int n;
        int m;
        double zoom = 50000.0;
        for (int l = 0; l < size; l++) {
            coordinate = list.get(l);
            n = coordinate.length;
            m = coordinate[0].length;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    try {
                        matrixP[0][0] = coordinate[i][j].x;
                        matrixP[1][0] = coordinate[i][j].y;
                        matrixP[2][0] = coordinate[i][j].z;
                        matrixP[3][0] = coordinate[i][j].w;
                    } catch (NullPointerException e) {
                    }
                    matrixP = MatrixOperation.multiply(matrix, matrixP);
                    coordinate[i][j].x = matrixP[0][0] / matrixP[3][0] * zoom;
                    coordinate[i][j].y = matrixP[1][0] / matrixP[3][0] * zoom;
                    coordinate[i][j].z = matrixP[2][0] / matrixP[3][0] * zoom;
                    coordinate[i][j].w = 1;
                }
            }
        }
        return list;
    }

    void setPivotsListInModel(int modelNumber, List<Coordinate2D> pivotsList) {
        modelList.get(modelNumber).setPivotsList(pivotsList);
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public void setN(int n) {
        this.n = n;
        modelList.stream().forEach(model -> model.updateCoordinate());
    }

    public void setM(int m) {
        this.m = m;
        modelList.stream().forEach(model -> model.updateCoordinate());
    }

    public void setK(int k) {
        this.k = k;
        modelList.stream().forEach(model -> model.updateCoordinate());
    }

    public void setA(double a) {
        this.a = a;
        modelList.stream().forEach(model -> model.updateCoordinate());
    }

    public void setB(double b) {
        this.b = b;
        modelList.stream().forEach(model -> model.updateCoordinate());
    }

    public void setC(double c) {
        this.c = c;
        modelList.stream().forEach(model -> model.updateCoordinate());
    }

    public void setD(double d) {
        this.d = d;
        modelList.stream().forEach(model -> model.updateCoordinate());
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

    public void setBr(int br) {
        this.br = br;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public void setBb(int bb) {
        this.bb = bb;
    }

    public double getA() {
        return a;
    }

    public double getC() {
        return c;
    }

    public Color getBackgroundColor() {
        return new Color(br, bg, bb);
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

    public int getModelNumber() {
        return modelList.size();
    }

    public int getK() {
        return k;
    }

    public double getB() {
        return b;
    }

    public double getD() {
        return d;
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

    public Model getModel(int modelNumber) {
        return modelList.get(modelNumber);
    }

    public int getRotatingModelNumber() {
        return rotatingModelNumber;
    }

    public void setRotatingModelNumber(int rotatingModelNumber) {
        this.rotatingModelNumber = rotatingModelNumber;
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

}
