package ru.nsu.fit.g13205.fink.isoline;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private int n;
    private int[] colors;
    private int isolineColor;
    private BufferedImage image;
    private BufferedImage legend;
    private boolean drawingMode = false;
    private boolean gridMode = false;
    private final Options options;

    public InitView(Options options) {
        this.options = options;
        setBackground(ViewOptions.BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (drawingMode) {
            int height = getSize().height;
            int width = getSize().width;
            image = new BufferedImage(width - ViewOptions.MARGIN * 2 - ViewOptions.LEGEND_WIDTH - ViewOptions.LEGEND_MARGIN, height - ViewOptions.MARGIN * 2, BufferedImage.TYPE_INT_RGB);
            legend = new BufferedImage(ViewOptions.LEGEND_WIDTH, height - ViewOptions.MARGIN * 2, BufferedImage.TYPE_INT_RGB);
            Drawer.drawFunction(image, colors, n, options.getA(), options.getB(), options.getC(), options.getD());
            //Drawer.drawFunction(legend, colors, isolineColor, n);
            if (gridMode) {
                Drawer.drawGrid(image, options.getK(), options.getM());
            }
            g.drawImage(image, ViewOptions.MARGIN, ViewOptions.MARGIN, this);
            g.drawImage(legend, width - ViewOptions.MARGIN - ViewOptions.LEGEND_WIDTH, ViewOptions.MARGIN, this);
        }
    }

    void clear() {
        drawingMode = false;
        repaint();
    }

    void update(int n, int[] colors, int isolineColor) {
        this.n = n;
        this.colors = colors;
        this.isolineColor = isolineColor;
        drawingMode = true;
        repaint();
    }

    public void setGridMode(boolean gridMode) {
        this.gridMode = gridMode;
        repaint();
    }

}
