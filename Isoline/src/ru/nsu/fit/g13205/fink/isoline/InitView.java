package ru.nsu.fit.g13205.fink.isoline;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private int n;
    private int[] colors;
    private int isolineColor;
    private BufferedImage image;
    private BufferedImage legend;
    private boolean drawingMode = false;
    private boolean gridMode = false;
    private boolean isolineMode = false;
    private final Options options;
    private final JLabel statusBar;

    public InitView(Options options, JLabel statusBar) {
        this.options = options;
        this.statusBar = statusBar;
        setBackground(ViewOptions.BACKGROUND_COLOR);
        addMouseMotionListener(new MouseAdapter() {

            double round(double x) {
                return Math.round(x * 1000) / 1000.0;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (drawingMode && image != null) {
                    int width = image.getWidth();
                    int height = image.getHeight();
                    if (e.getX() >= ViewOptions.MARGIN && e.getY() >= ViewOptions.MARGIN && e.getX() <= ViewOptions.MARGIN + width && e.getY() <= ViewOptions.MARGIN + height) {
                        double stepX = Math.abs(options.getA() - options.getC()) / width;
                        double stepY = Math.abs(options.getB() - options.getD()) / height;
                        double x = (e.getX() - ViewOptions.MARGIN) * stepX + options.getA();
                        double y = (e.getY() - ViewOptions.MARGIN) * stepY + options.getB();
                        statusBar.setText("X=" + round(x) + " Y=" + round(y) + " F=" + round(Logic.f(y, x)));
                    } else {
                        statusBar.setText("Ready");
                    }
                }
            }

        });
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                statusBar.setText("Ready");
            }
        });
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
            Drawer.drawLegend(legend, colors, n);
            if (gridMode) {
                Drawer.drawGrid(image, options.getK(), options.getM());
            }
            if (isolineMode) {
                Drawer.drawIsoline(image, isolineColor, options.getK(), options.getM(), n, options.getA(), options.getB(), options.getC(), options.getD());
            }
            g.drawImage(image, ViewOptions.MARGIN, ViewOptions.MARGIN, this);
            g.drawImage(legend, width - ViewOptions.MARGIN - ViewOptions.LEGEND_WIDTH, ViewOptions.MARGIN, this);
            Drawer.drawLabel(n, g,width - ViewOptions.MARGIN - ViewOptions.LEGEND_WIDTH,ViewOptions.MARGIN,legend.getHeight());
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

    void setIsolineMode(boolean isolineMode) {
        this.isolineMode = isolineMode;
        repaint();
    }

}
