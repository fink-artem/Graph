package ru.nsu.fit.g13205.fink.isoline;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
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
    private boolean interpolationMode = false;
    private boolean colorMapMode = true;
    private final Options options;
    private final JLabel statusBar;
    private List<Double> isolineLevelList;

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
                        double y = options.getD() - (e.getY() - ViewOptions.MARGIN) * stepY;
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

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (drawingMode && image != null && isolineMode) {
                    int width = image.getWidth();
                    int height = image.getHeight();
                    if (e.getX() >= ViewOptions.MARGIN && e.getY() >= ViewOptions.MARGIN && e.getX() <= ViewOptions.MARGIN + width && e.getY() <= ViewOptions.MARGIN + height) {
                        if (isolineLevelList == null) {
                            isolineLevelList = new ArrayList<>();
                        }
                        double stepX = Math.abs(options.getA() - options.getC()) / width;
                        double stepY = Math.abs(options.getB() - options.getD()) / height;
                        double x = (e.getX() - ViewOptions.MARGIN) * stepX + options.getA();
                        double y = options.getD() - (e.getY() - ViewOptions.MARGIN) * stepY;
                        isolineLevelList.add(Logic.f(y, x));
                        repaint();
                    }
                }
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
            Drawer.updateDomain(image.getHeight(), image.getWidth(), options.getA(), options.getB(), options.getC(), options.getD());
            if (interpolationMode) {
                Drawer.drawInterpolationLegend(legend, colors, n, isolineColor);
                if (colorMapMode) {
                    Drawer.drawInterpolationFunction(image, legend, n, options.getA(), options.getB(), options.getC(), options.getD());
                } else {
                    Drawer.fill(image, Color.WHITE.getRGB());
                }
            } else {
                if (colorMapMode) {
                    Drawer.drawFunction(image, colors, n, options.getA(), options.getB(), options.getC(), options.getD());
                } else {
                    Drawer.fill(image, Color.WHITE.getRGB());
                }
                Drawer.drawLegend(legend, colors, n, isolineColor);
            }
            Drawer.drawIsolineLegend(legend, isolineColor, n);
            if (gridMode) {
                Drawer.drawGrid(image, options.getK(), options.getM());
            }
            if (isolineMode) {
                Drawer.drawIsolineMap(image.getGraphics(), isolineColor, options.getK(), options.getM(), n, options.getA(), options.getB(), options.getC(), options.getD(), image.getHeight(), image.getWidth());
                if (isolineLevelList != null) {
                    isolineLevelList.forEach(z -> Drawer.drawIsoline(image.getGraphics(), isolineColor, options.getK(), options.getM(), options.getA(), options.getB(), options.getC(), options.getD(), image.getHeight(), image.getWidth(), z));
                    Drawer.drawLastIsoline(legend,isolineLevelList.get(isolineLevelList.size()-1),isolineColor);
                }
            }
            g.drawImage(image, ViewOptions.MARGIN, ViewOptions.MARGIN, this);
            g.drawImage(legend, width - ViewOptions.MARGIN - ViewOptions.LEGEND_WIDTH, ViewOptions.MARGIN, this);
            Drawer.drawLabel(n, g, width - ViewOptions.MARGIN - ViewOptions.LEGEND_WIDTH, ViewOptions.MARGIN, legend.getHeight());
        }
    }

    void clear() {
        drawingMode = false;
        isolineLevelList = null;
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

    void setInterpolationMode(boolean interpolationMode) {
        this.interpolationMode = interpolationMode;
        repaint();
    }

    void setColorMapMode(boolean colorMapMode) {
        this.colorMapMode = colorMapMode;
        repaint();
    }

}
