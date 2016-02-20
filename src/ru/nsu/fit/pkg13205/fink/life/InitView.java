package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Dimension;
import java.awt.Graphics;
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

    public InitView(final Options options) {
        this.options = options;
        setBackground(ViewColor.BACKGROUND_COLOR);
        grid = new Grid();
        initGrid();
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getX() > 0 && e.getY() > 0 && e.getX() < width && e.getY() < height) {
                    if (bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.CELL_COLOR_OFF.getRGB()) {
                        grid.fill(bufferedImage, e.getX(), e.getY(), ViewColor.CELL_COLOR_ON.getRGB());
                    } else if (options.getPaintMode() == Options.PaintMode.XOR && bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.CELL_COLOR_ON.getRGB()) {
                        grid.fill(bufferedImage, e.getX(), e.getY(), ViewColor.CELL_COLOR_OFF.getRGB());
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
                    if (bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.CELL_COLOR_OFF.getRGB()) {
                        grid.fill(bufferedImage, e.getX(), e.getY(), ViewColor.CELL_COLOR_ON.getRGB());
                    } else if (options.getPaintMode() == Options.PaintMode.XOR && bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.CELL_COLOR_ON.getRGB()) {
                        grid.fill(bufferedImage, e.getX(), e.getY(), ViewColor.CELL_COLOR_OFF.getRGB());
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
        width = 2 * Grid.MARGIN_GRID + options.getColumnNumber() * (options.getGridWidth() + (int) Math.round(options.getCellSize() * Math.sqrt(3))) + options.getGridWidth() + 1;
        height = 2 * Grid.MARGIN_GRID + options.getRowNumber() * ((int) Math.round(2 * options.getGridWidth() / Math.sqrt(3)) + 3 * options.getCellSize() / 2) + (int) Math.round(2 * options.getGridWidth() / Math.sqrt(3)) + options.getCellSize() / 2 + 1;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width, height));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                bufferedImage.setRGB(j, i, ViewColor.BACKGROUND_COLOR.getRGB());
            }
        }
        grid.drawGrid(bufferedImage, options.getRowNumber(), options.getColumnNumber(), options.getCellSize(), options.getGridWidth());
        repaint();
    }

}
