package ru.nsu.fit.g13205.fink.life;

public class Options {

    public enum PaintMode {

        XOR, REPLACE
    }

    private PaintMode paintMode = PaintMode.REPLACE;
    private int rowNumber = 20;
    private int columnNumber = 25;
    private int cellSize = 20;
    private int gridWidth = 2;
    private int timer = 1000;
    private double liveBegin = 2;
    private double liveEnd = 3.3;
    private double birthBegin = 2.3;
    private double birthEnd = 2.9;
    private double fstImpact = 1;
    private double sndImpact = 0.3;

    public PaintMode getPaintMode() {
        return paintMode;
    }

    public void setPaintMode(PaintMode paintMode) {
        this.paintMode = paintMode;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public double getLiveBegin() {
        return liveBegin;
    }

    public void setLiveBegin(double liveBegin) {
        this.liveBegin = liveBegin;
    }

    public double getLiveEnd() {
        return liveEnd;
    }

    public void setLiveEnd(double liveEnd) {
        this.liveEnd = liveEnd;
    }

    public double getBirthBegin() {
        return birthBegin;
    }

    public void setBirthBegin(double birthBegin) {
        this.birthBegin = birthBegin;
    }

    public double getBirthEnd() {
        return birthEnd;
    }

    public void setBirthEnd(double birthEnd) {
        this.birthEnd = birthEnd;
    }

    public double getFstImpact() {
        return fstImpact;
    }

    public void setFstImpact(double fstImpact) {
        this.fstImpact = fstImpact;
    }

    public double getSndImpact() {
        return sndImpact;
    }

    public void setSndImpact(double sndImpact) {
        this.sndImpact = sndImpact;
    }

}
