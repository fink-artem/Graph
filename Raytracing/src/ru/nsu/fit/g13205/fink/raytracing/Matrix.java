package ru.nsu.fit.g13205.fink.raytracing;

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

    public static double[][] getMcamMatrix(Coordinate3D eye, Coordinate3D ref, Coordinate3D up) {
        Coordinate3D k = eye.minus(ref).divide(ref.minus(eye).getNorm());
        Coordinate3D i = up.vectorMultiply(k).divide(up.vectorMultiply(k).getNorm());
        Coordinate3D j = k.vectorMultiply(i);
        double[][] T = {
            {i.x, i.y, i.z, 0},
            {j.x, j.y, j.z, 0},
            {k.x, k.y, k.z, 0},
            {0, 0, 0, 1}};
        double[][] T2 = {
            {1, 0, 0, -eye.x},
            {0, 1, 0, -eye.y},
            {0, 0, 1, -eye.z},
            {0, 0, 0, 1}};
        return MatrixOperation.multiply(T, T2);
    }

    /*public static double[][] getProjMatrix(double sw, double sh, double zn, double zf) {
     double[][] T = {
     {2.0 * zn / sw, 0, 0, 0},
     {0, 2.0 * zn / sh, 0, 0},
     {0, 0, zf / (zf - zn), -zn * zf / (zf - zn)},
     {0, 0, 1, 0}};
     return T;
     }*/
    public static double[][] getProjMatrix(double sw, double sh, double zn, double zf) {
        double[][] T = {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 1.0 / zn, 0}};
        return T;
    }

}
