package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;

public class SplainPanel extends JPanel {

    private BufferedImage image;
    private final Model model;
    private int coordinateNumber = 0;
    private int modelNumber = 0;
    private boolean taken = false;

    public SplainPanel(Model model) {
        this.model = model;
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (taken) {
                    int x = e.getX();
                    int y = e.getY();
                    if (x > 0 && y > 0 && x < image.getWidth() && y < image.getHeight()) {
                        Coordinate coordinate = Drawer.screenToCoordinate(new Point(x, y));
                        model.data.setCoordinateInModel(modelNumber, coordinateNumber, coordinate);
                        repaint();
                    }
                }
            }
        });
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();
                List<Coordinate> coordinateList = model.data.getCoordinateForModel(modelNumber);
                Point p;
                int size = coordinateList.size();
                for (int i = 0; i < size; i++) {
                    p = Drawer.coordinateToScreen(coordinateList.get(i));
                    if (Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2)) < Drawer.RADIUS) {
                        coordinateNumber = i;
                        taken = true;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                taken = false;
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        Dimension size = getSize();
        image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawLine(size.width / 2, 0, size.width / 2, size.height - 1);
        image.getGraphics().drawLine(0, size.height / 2, size.width - 1, size.height / 2);
        Drawer.drawSplain(image, model.data, 0);
        g.drawImage(image, 0, 0, this);
    }

}
