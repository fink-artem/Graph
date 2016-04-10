package ru.nsu.fit.g13205.fink.wireframe;

public class MatrixOperation {

    static double[][] multiply(double[][] a, double[][] b) {
        int rowNumber = a.length;
        int columnNumber = b[0].length;
        double[][] result = new double[rowNumber][columnNumber];
        int length = b.length;
        double sum;
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                sum = 0;
                for (int k = 0; k < length; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }
}
