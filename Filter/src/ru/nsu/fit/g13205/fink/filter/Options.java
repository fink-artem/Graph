package ru.nsu.fit.g13205.fink.filter;

public class Options {

    public enum PaintMode {

        XOR, REPLACE
    }

    public static final int MAX_ROW_NUMBER = 41;
    public static final int MIN_ROW_NUMBER = 1;
    public static final int MAX_COLUMN_NUMBER = 41;
    public static final int MIN_COLUMN_NUMBER = 1;
    public static final int MAX_CELL_SIZE = 65;
    public static final int MIN_CELL_SIZE = 15;
    public static final int MAX_GRID_WIDTH = 21;
    public static final int MIN_GRID_WIDTH = 1;
    public static final int ERROR = 1;
    public static final int SUCCESS = 0;
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

    public int setRowNumber(int rowNumber) {
        if (rowNumber > MAX_ROW_NUMBER || rowNumber < MIN_ROW_NUMBER) {

        }
        this.rowNumber = rowNumber;
        return SUCCESS;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int setColumnNumber(int columnNumber) {
        if (columnNumber > MAX_COLUMN_NUMBER || columnNumber < MIN_COLUMN_NUMBER) {
            return ERROR;
        }
        this.columnNumber = columnNumber;
        return SUCCESS;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int setCellSize(int cellSize) {
        if (cellSize > MAX_CELL_SIZE || cellSize < MIN_CELL_SIZE) {
            return ERROR;
        }
        this.cellSize = cellSize;
        return SUCCESS;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int setGridWidth(int gridWidth) {
        if (gridWidth > MAX_GRID_WIDTH || gridWidth < MIN_GRID_WIDTH) {
            return ERROR;
        }
        this.gridWidth = gridWidth;
        return SUCCESS;
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
