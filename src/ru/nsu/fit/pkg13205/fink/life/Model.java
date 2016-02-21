package ru.nsu.fit.pkg13205.fink.life;

public class Model {

    public enum Cell {

        LIVE, DEAD
    }

    private final int n;
    private final int m;
    private final Cell live[][];
    private final double impact[][];
    private double fstImpact;
    private double sndImpact;

    public Model(int n, int m, double fstImpact, double sndImpact) {
        this.n = n;
        this.m = m;
        this.fstImpact = fstImpact;
        this.sndImpact = sndImpact;
        live = new Cell[n][m];
        impact = new double[n][m];
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    Cell getCellStatus(int x, int y) {
        return live[y][x];
    }

    void setCellStatus(int x, int y, Cell status) {
        if (live[y][x] != status) {
            int sig;
            if (status == Cell.DEAD) {
                sig = -1;
            } else {
                sig = 1;
            }
            live[y][x] = status;
            if (y > 0) {
                impact[y - 1][x] += sig * fstImpact;
            }
            if (y < n - 1) {
                impact[y + 1][x] += sig * fstImpact;
            }
            if (x < m - 1) {
                impact[y][x + 1] += sig * fstImpact;
            }
            if (x > 0) {
                impact[y][x - 1] += sig * fstImpact;
            }
            if (y > 1) {
                impact[y - 2][x] += sig * sndImpact;
            }
            if (y < n - 2) {
                impact[y + 2][x] += sig * sndImpact;
            }
            if (y % 2 == 0) {
                if (x > 0 && y > 0) {
                    impact[y - 1][x - 1] += sig * fstImpact;
                }
                if (x > 0 && y < n - 1) {
                    impact[y + 1][x - 1] += sig * fstImpact;
                }
                if (x < m - 1 && y < n - 1) {
                    impact[y + 1][x + 1] += sig * sndImpact;
                }
                if (x < m - 1 && y > 0) {
                    impact[y - 1][x + 1] += sig * sndImpact;
                }
                if (x > 1 && y > 0) {
                    impact[y - 1][x - 2] += sig * sndImpact;
                }
                if (x > 1 && y < n - 1) {
                    impact[y + 1][x - 2] += sig * sndImpact;
                }
            } else {
                if (x > 0 && y > 0) {
                    impact[y - 1][x - 1] += sig * sndImpact;
                }
                if (x > 0 && y < n - 1) {
                    impact[y + 1][x - 1] += sig * sndImpact;
                }
                if (x < m - 1 && y < n - 1) {
                    impact[y + 1][x + 1] += sig * fstImpact;
                }
                if (x < m - 1 && y > 0) {
                    impact[y - 1][x + 1] += sig * fstImpact;
                }
                if (x < m - 2 && y > 0) {
                    impact[y - 1][x + 2] += sig * sndImpact;
                }
                if (x < m - 2 && y < n - 1) {
                    impact[y + 1][x + 2] += sig * sndImpact;
                }
            }
        }
    }

    double getCellImpact(int x, int y) {
        return impact[y][x];
    }
}
