package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private BufferedImage bufferedImage;
    private final Grid grid;
    private final Options options;
    private int width;
    private int height;
    private final Point coordinateNow = new Point(-1, -1);
    private Model model;
    private boolean impactMode = false;
    private final double exp = Math.pow(0.1, 6);

    public InitView(final Options options) {
        this.options = options;
        setBackground(ViewColor.BACKGROUND_COLOR);
        grid = new Grid();
        model = new Model(options.getRowNumber(), options.getColumnNumber(), options.getFstImpact(), options.getSndImpact());
        initGrid();
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getX() > 0 && e.getY() > 0 && e.getX() < width && e.getY() < height) {
                    Point p = grid.getAbsoluteCellCoordinate(e.getX(), e.getY());
                    if (bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.CELL_COLOR_OFF.getRGB()) {
                        grid.fill(bufferedImage, e.getX(), e.getY(), ViewColor.CELL_COLOR_ON.getRGB());
                        model.setCellStatus(p.x, p.y, Model.Cell.LIVE);
                        coordinateNow.x = p.x;
                        coordinateNow.y = p.y;
                    } else if (options.getPaintMode() == Options.PaintMode.XOR && bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.CELL_COLOR_ON.getRGB()) {
                        grid.fill(bufferedImage, e.getX(), e.getY(), ViewColor.CELL_COLOR_OFF.getRGB());
                        model.setCellStatus(p.x, p.y, Model.Cell.DEAD);
                        coordinateNow.x = p.x;
                        coordinateNow.y = p.y;
                    }
                }
                repaint();
            }

        });

        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (e.getX() > 0 && e.getY() > 0 && e.getX() < width && e.getY() < height) {
                    Point p = grid.getAbsoluteCellCoordinate(e.getX(), e.getY());
                    if (p.x != coordinateNow.x || p.y != coordinateNow.y) {
                        if (bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.CELL_COLOR_OFF.getRGB()) {
                            grid.fill(bufferedImage, e.getX(), e.getY(), ViewColor.CELL_COLOR_ON.getRGB());
                            model.setCellStatus(p.x, p.y, Model.Cell.LIVE);
                            coordinateNow.x = p.x;
                            coordinateNow.y = p.y;
                        } else if (options.getPaintMode() == Options.PaintMode.XOR && bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.CELL_COLOR_ON.getRGB()) {
                            grid.fill(bufferedImage, e.getX(), e.getY(), ViewColor.CELL_COLOR_OFF.getRGB());
                            model.setCellStatus(p.x, p.y, Model.Cell.DEAD);
                            coordinateNow.x = p.x;
                            coordinateNow.y = p.y;
                        }
                    }
                }
                repaint();
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, this);
    }

    public final void initGrid() {
        coordinateNow.x = -1;
        coordinateNow.y = -1;
        width = 2 * Grid.MARGIN_GRID + options.getColumnNumber() * (options.getGridWidth() + (int) Math.round(options.getCellSize() * Math.sqrt(3))) + options.getGridWidth() + 1;
        height = 2 * Grid.MARGIN_GRID + options.getRowNumber() * ((int) Math.round(2 * options.getGridWidth() / Math.sqrt(3)) + 3 * options.getCellSize() / 2) + (int) Math.round(2 * options.getGridWidth() / Math.sqrt(3)) + options.getCellSize() / 2 + 1;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width, height));
        model = new Model(options.getRowNumber(), options.getColumnNumber(), options.getFstImpact(), options.getSndImpact());
        grid.drawGrid(bufferedImage, options.getRowNumber(), options.getColumnNumber(), options.getCellSize(), options.getGridWidth(), model);
        grid.fill(bufferedImage, 0, 0, ViewColor.BACKGROUND_COLOR.getRGB());
        repaint();
    }

    void next() {
        if (impactMode) {
            coordinateNow.x = -1;
            coordinateNow.y = -1;
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    bufferedImage.setRGB(j, i, ViewColor.BACKGROUND_COLOR.getRGB());
                }
            }
            grid.drawGrid(bufferedImage, options.getRowNumber(), options.getColumnNumber(), options.getCellSize(), options.getGridWidth(), model);
        }
        int n = options.getRowNumber();
        int m = options.getColumnNumber();
        double impact[][] = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                impact[i][j] = model.getCellImpact(j, i);
            }
        }
        Point p;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                p = grid.getCellCordinate(j, i);
                if (model.getCellStatus(j, i) == Model.Cell.LIVE) {
                    if (impact[i][j] - options.getLiveEnd() > exp || impact[i][j] - options.getLiveBegin() < -exp) {
                        model.setCellStatus(j, i, Model.Cell.DEAD);
                        grid.fill(bufferedImage, p.x, p.y, ViewColor.CELL_COLOR_OFF.getRGB());
                    } else {
                        grid.fill(bufferedImage, p.x, p.y, ViewColor.CELL_COLOR_ON.getRGB());
                    }
                } else {
                    if (impact[i][j] - options.getBirthBegin() >= -exp && impact[i][j] - options.getBirthEnd() <= exp) {
                        model.setCellStatus(j, i, Model.Cell.LIVE);
                        grid.fill(bufferedImage, p.x, p.y, ViewColor.CELL_COLOR_ON.getRGB());
                    } else {
                        grid.fill(bufferedImage, p.x, p.y, ViewColor.CELL_COLOR_OFF.getRGB());
                    }
                }
            }
        }
        if (impactMode) {
            grid.drawImpact(bufferedImage, options.getRowNumber(), options.getColumnNumber(), model);
        }
        repaint();
    }

    void ImpactOn() {
        impactMode = true;
        grid.drawImpact(bufferedImage, options.getRowNumber(), options.getColumnNumber(), model);
        repaint();
    }

    void ImpactOff() {
        impactMode = false;
        grid.clearImpact(bufferedImage, options.getRowNumber(), options.getColumnNumber(), model);
        repaint();
    }

    boolean isImpactMode() {
        return impactMode;
    }

    void clear() {
        coordinateNow.x = -1;
        coordinateNow.y = -1;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        model = new Model(options.getRowNumber(), options.getColumnNumber(), options.getFstImpact(), options.getSndImpact());
        grid.drawGrid(bufferedImage, options.getRowNumber(), options.getColumnNumber(), options.getCellSize(), options.getGridWidth(), model);
        grid.fill(bufferedImage, 0, 0, ViewColor.BACKGROUND_COLOR.getRGB());
        if (impactMode) {
            grid.drawImpact(bufferedImage, options.getRowNumber(), options.getColumnNumber(), model);
        }
        repaint();
    }

}
