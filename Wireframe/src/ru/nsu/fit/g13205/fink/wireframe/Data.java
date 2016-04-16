package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private final List<Model> modelList = new ArrayList<>();
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
    private double ex;
    private double ey;
    private double ez;
    private int br;
    private int bg;
    private int bb;

    int addNewModel(double cx, double cy, double cz, double rx, double ry, double rz, Color color) {
        modelList.add(new Model(n, m, k, cx, cy, cz, rx, ry, rz, color));
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

    Coordinate3D[][] getCoordinate(int modelNumber) {
        return modelList.get(modelNumber).getCoordinate();
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
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setK(int k) {
        this.k = k;
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

    public void setEx(double ex) {
        this.ex = ex * 2 * Math.PI / 360.0;
    }

    public void setEy(double ey) {
        this.ey = ey * 2 * Math.PI / 360.0;
    }

    public void setEz(double ez) {
        this.ez = ez * 2 * Math.PI / 360.0;
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

}
