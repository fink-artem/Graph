package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private final List<Model> modelList = new ArrayList<>();
    private double[][] rotateMatrix;
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

    int addNewModel(double cx, double cy, double cz, double[][] rotateMatrix, Color color) {
        modelList.add(new Model(n, m, k, cx, cy, cz, rotateMatrix, color));
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
    
    Color getModelColor(int modelNumber){
        return modelList.get(modelNumber).getColor();
    }

    Coordinate3D[][] getCoordinate(int modelNumber) {
        Coordinate3D[][] coordinate = modelList.get(modelNumber).getCoordinate();
        int n = this.n + 1;
        Coordinate3D[][] newCoordinate = new Coordinate3D[n][m];
        double[][] matrixP = new double[4][1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrixP[0][0] = coordinate[i][j].x;
                matrixP[1][0] = coordinate[i][j].y;
                matrixP[2][0] = coordinate[i][j].z;
                matrixP[3][0] = coordinate[i][j].w;
                matrixP = MatrixOperation.multiply(rotateMatrix, matrixP);
                newCoordinate[i][j] = new Coordinate3D(matrixP[0][0] / matrixP[3][0], matrixP[1][0] / matrixP[3][0], matrixP[2][0] / matrixP[3][0]);
            }
        }
        return newCoordinate;
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
        for (Model model : modelList) {
            model.setN(n);
        }
    }

    public void setM(int m) {
        this.m = m;
        for (Model model : modelList) {
            model.setM(m);
        }
    }

    public void setK(int k) {
        this.k = k;
        for (Model model : modelList) {
            model.setK(k);
        }
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setZn(double zn) {
        this.zn = zn;
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

    public int getModelNumber(){
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
    
    public Model getModel(int modelNumber){
        return modelList.get(modelNumber);
    }
}
