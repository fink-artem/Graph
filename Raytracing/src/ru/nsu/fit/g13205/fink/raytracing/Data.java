package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private List<Source> sourceList = new ArrayList<>();
    private List<Shape> shapeList = new ArrayList<>();
    private RenderData renderData = new RenderData();
    private double[][] rotateMatrix = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
    private double aR;
    private double aG;
    private double aB;

    List<List<Segment>> getCoordinate() {
        List<List<Segment>> list = new ArrayList<>();
        int size = shapeList.size();
        Shape shape;
        for (int i = 0; i < size; i++) {
            shape = shapeList.get(i);
            list.add(shape.getCoordinate());
        }
        double[][] fullrotateMatrix = MatrixOperation.multiply(Matrix.getTranslateMatrix(renderData.ref.x, renderData.ref.y, renderData.ref.z), MatrixOperation.multiply(rotateMatrix, Matrix.getTranslateMatrix(-renderData.ref.x, -renderData.ref.y, -renderData.ref.z)));
        double[][] matrix = MatrixOperation.multiply(Matrix.getProjMatrix(renderData.sw, renderData.sh, renderData.zn, renderData.zf), MatrixOperation.multiply(Matrix.getMcamMatrix(renderData.eye, renderData.ref, renderData.upVector), rotateMatrix));
        double[][] matrixP = new double[4][1];
        List<Segment> segmentList;
        Segment segment;
        int length;
        for (int i = 0; i < size; i++) {
            segmentList = list.get(i);
            length = segmentList.size();
            for (int j = 0; j < length; j++) {
                segment = segmentList.get(j);
                matrixP[0][0] = segment.point1.x;
                matrixP[1][0] = segment.point1.y;
                matrixP[2][0] = segment.point1.z;
                matrixP[3][0] = segment.point1.w;
                matrixP = MatrixOperation.multiply(matrix, matrixP);
                segment.point1.x = matrixP[0][0] / matrixP[3][0];
                segment.point1.y = matrixP[1][0] / matrixP[3][0];
                segment.point1.z = matrixP[2][0] / -1;
                segment.point1.w = 1;
                matrixP[0][0] = segment.point2.x;
                matrixP[1][0] = segment.point2.y;
                matrixP[2][0] = segment.point2.z;
                matrixP[3][0] = segment.point2.w;
                matrixP = MatrixOperation.multiply(matrix, matrixP);
                segment.point2.x = matrixP[0][0] / matrixP[3][0];
                segment.point2.y = matrixP[1][0] / matrixP[3][0];
                segment.point2.z = matrixP[2][0] / -1;
                segment.point2.w = 1;
            }
        }
        return list;
    }

    public void clear() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j) {
                    rotateMatrix[i][j] = 1;
                } else {
                    rotateMatrix[i][j] = 0;
                }
            }
        }
        renderData.sw = 5;
        renderData.sh = 5;
        double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;
        for (Shape shape : shapeList) {
            minX = Math.min(minX, shape.getMinX());
            maxX = Math.max(maxX, shape.getMaxX());
            minY = Math.min(minY, shape.getMinY());
            maxY = Math.max(maxY, shape.getMaxY());
            minZ = Math.min(minZ, shape.getMinZ());
            maxZ = Math.max(maxZ, shape.getMaxZ());
        }
        double centerX = (maxX + minX) / 2;
        double centerY = (maxY + minY) / 2;
        double centerZ = (maxZ + minZ) / 2;
        renderData.ref = new Coordinate3D(centerX, centerY, centerZ);
        renderData.upVector = new Coordinate3D(0, 0, 1);
        renderData.eye = new Coordinate3D(-maxZ / Math.tan(Math.PI / 6) + minX, centerY, centerZ);
        renderData.zn = (minX - renderData.eye.x) / 2;
        renderData.zf = maxX - renderData.eye.x + (maxX - minX) / 2;
    }

    public void setZn(double zn) {
        if (zn >= 0 && zn <= 100) {
            renderData.zn = zn;
        }
    }

    public void setZf(double zf) {
        renderData.zf = zf;
    }

    public void setSw(double sw) {
        renderData.sw = sw;
    }

    public void setSh(double sh) {
        renderData.sh = sh;
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

    public double getZn() {
        return renderData.zn;
    }

    public double getZf() {
        return renderData.zf;
    }

    public double getSw() {
        return renderData.sw;
    }

    public double getSh() {
        return renderData.sh;
    }

    public double getaR() {
        return aR;
    }

    public double getaG() {
        return aG;
    }

    public double getaB() {
        return aB;
    }

    public void setaRGB(Color aRGB) {
        aR = aRGB.getRed() / 255.0;
        aG = aRGB.getGreen() / 255.0;
        aB = aRGB.getBlue() / 255.0;
    }

    public List<Source> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<Source> sourceList) {
        this.sourceList = sourceList;
    }

    public List<Shape> getShapeList() {
        return shapeList;
    }

    public void setShapeList(List<Shape> shapeList) {
        this.shapeList = shapeList;
    }

    public double getGamma() {
        return renderData.gamma;
    }

    public void setGamma(double gamma) {
        renderData.gamma = gamma;
    }

    public int getDepth() {
        return renderData.depth;
    }

    public void setDepth(int depth) {
        renderData.depth = depth;
    }

    public Quality getQuality() {
        return renderData.quality;
    }

    public void setQuality(Quality quality) {
        renderData.quality = quality;
    }

    public Coordinate3D getEyeVector() {
        return renderData.eye;
    }

    public void setEyeVector(Coordinate3D eyeMatrix) {
        renderData.eye = eyeMatrix;
    }

    public Coordinate3D getRefVector() {
        return renderData.ref;
    }

    public void setRefVector(Coordinate3D refVector) {
        renderData.ref = refVector;
    }

    public Coordinate3D getUpVector() {
        return renderData.upVector;
    }

    public void setUpVector(Coordinate3D upVector) {
        renderData.upVector = upVector;
    }

    public double getBr() {
        return renderData.br;
    }

    public double getBg() {
        return renderData.bg;
    }

    public double getBb() {
        return renderData.bb;
    }

    public void setBackground(Color background) {
        renderData.setBackGround(background);
    }

    public void setRenderData(RenderData renderData) {
        this.renderData = renderData;
    }

    public Color getBackground() {
        return new Color((float) renderData.br, (float) renderData.bg, (float) renderData.bb);
    }

}
