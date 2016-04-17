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
    private final Data data;
    private int coordinateNumber = 0;
    private int modelNumber = 0;
    private boolean taken = false;

    public SplainPanel(Data data, InitView initView) {
        this.data = data;
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (taken) {
                    Coordinate2D coordinate = Drawer.screenToCoordinate(e.getPoint());
                    data.setPivotInModel(modelNumber, coordinateNumber, coordinate);
                    repaint();
                    initView.repaint();
                }
            }
        });
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();
                List<Coordinate2D> coordinateList = data.getPivots(modelNumber);
                Point p;
                Coordinate2D c;
                int size = coordinateList.size();
                if (e.getButton() == MouseEvent.BUTTON1) {
                    for (int i = 0; i < size; i++) {
                        p = Drawer.coordinateToScreen(coordinateList.get(i));
                        if (Operation.distance(new Coordinate2D(x, y), new Coordinate2D(p.x, p.y)) < Drawer.BIG_RADIUS) {
                            coordinateNumber = i;
                            taken = true;
                            return;
                        }
                    }
                    for (int i = 1; i < size; i++) {
                        c = new Coordinate2D((coordinateList.get(i).x + coordinateList.get(i - 1).x) / 2, (coordinateList.get(i).y + coordinateList.get(i - 1).y) / 2);
                        p = Drawer.coordinateToScreen(c);
                        if (Operation.distance(new Coordinate2D(x, y), new Coordinate2D(p.x, p.y)) < Drawer.SMALL_RADIUS) {
                            coordinateNumber = i;
                            data.addNewPivotInModel(modelNumber, coordinateNumber, c);
                            taken = true;
                            repaint();
                            initView.repaint();
                            return;
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    for (int i = 0; i < size; i++) {
                        p = Drawer.coordinateToScreen(coordinateList.get(i));
                        if (Operation.distance(new Coordinate2D(x, y), new Coordinate2D(p.x, p.y)) < Drawer.BIG_RADIUS) {
                            data.deletePivotFromModel(modelNumber, i);
                            repaint();
                            initView.repaint();
                            return;
                        }
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
        Drawer.drawSplain(image, data, 0);
        g.drawImage(image, 0, 0, this);
    }

}
