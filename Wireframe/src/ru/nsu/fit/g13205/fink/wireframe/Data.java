package ru.nsu.fit.g13205.fink.wireframe;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private final List<List<Coordinate>> modelList = new ArrayList<>();

    int addNewModel() {
        modelList.add(new ArrayList<>());
        return modelList.size() - 1;
    }

    void addNewCoordinateInModelByPosition(Coordinate coordinate, int modelNumber, int position) {
        modelList.get(modelNumber).add(position, coordinate);
    }

    void addNewCoordinateInModel(Coordinate coordinate, int modelNumber) {
        modelList.get(modelNumber).add(coordinate);
    }

    void deleteModel(int modelNumber) {
        modelList.remove(modelNumber);
    }

    void deleteCoordinateFromModel(int modelNumber, int position) {
        modelList.get(modelNumber).remove(position);
    }

    void setCoordinateInModel(int modelNumber, int position, Coordinate coordinate) {
        modelList.get(modelNumber).set(position, coordinate);
    }

    List<Coordinate> getCoordinateForModel(int modelNumber) {
        return modelList.get(modelNumber);
    }
}
