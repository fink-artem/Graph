package ru.nsu.fit.g13205.fink.wireframe;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private final List<Model> modelList = new ArrayList<>();
    private int n = 10;
    private int m = 10;
    int k;
    double a;
    double b;
    double c;
    double d;
    double zn;
    double zf;
    double sw;
    double sh;
    double ex;
    double ey;
    double ez;
    int br;
    int bg;
    int bb;

    int addNewModel() {
        modelList.add(new Model(n, m, k));
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

}
