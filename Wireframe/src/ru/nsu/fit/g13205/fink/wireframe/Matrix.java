package ru.nsu.fit.g13205.fink.wireframe;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Matrix {

    public static final double[][] MS = {
        {-1 / 6.0, 3 / 6.0, -3 / 6.0, 1 / 6.0},
        {3 / 6.0, -6 / 6.0, 3 / 6.0, 0 / 6.0},
        {-3 / 6.0, 0 / 6.0, 3 / 6.0, 0 / 6.0},
        {1 / 6.0, 4 / 6.0, 1 / 6.0, 0 / 6.0}};

    public static double[][] getRotateXMatrix(double t) {
        double[][] Rx = {
            {1, 0, 0, 0},
            {0, cos(t), -sin(t), 0},
            {0, sin(t), cos(t), 0},
            {0, 0, 0, 1}};
        return Rx;
    }

    public static double[][] getRotateYMatrix(double t) {
        double[][] Ry = {
            {cos(t), 0, sin(t), 0},
            {0, 1, 0, 0},
            {-sin(t), 0, cos(t), 0},
            {0, 0, 0, 1}};
        return Ry;
    }

    public static double[][] getRotateZMatrix(double t) {
        double[][] Rz = {
            {cos(t), -sin(t), 0, 0},
            {sin(t), cos(t), 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}};
        return Rz;
    }

    public static double[][] getTranslateMatrix(double tx, double ty, double tz) {
        double[][] T = {
            {1, 0, 0, tx},
            {0, 1, 0, ty},
            {0, 0, 1, tz},
            {0, 0, 0, 1}};
        return T;
    }

    public static double[][] getScaleMatrix(double s) {
        double[][] T = {
            {s, 0, 0, 0},
            {0, s, 0, 0},
            {0, 0, s, 0},
            {0, 0, 0, 1}};
        return T;
    }

}
