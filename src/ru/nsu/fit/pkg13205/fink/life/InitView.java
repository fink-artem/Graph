package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private final BufferedImage bufferedImage;
    private final Grid grid;

    public InitView() {
        setBackground(ViewColor.BACKGROUND_COLOR);
        bufferedImage = new BufferedImage(1200, 700, BufferedImage.TYPE_INT_RGB);
        grid = new Grid(bufferedImage);
        grid.fill(0, 0, ViewColor.BACKGROUND_COLOR.getRGB());
        initGrid();
        addMouseListener(new MouseAdapter() {

            int width = bufferedImage.getWidth() - 1;
            int height = bufferedImage.getHeight() - 1;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getX() > 0 && e.getY() > 0 && e.getX() < width && e.getY() < height && bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.HEXAGON_COLOR_OFF.getRGB()) {
                    grid.fill(e.getX(), e.getY(), ViewColor.HEXAGON_COLOR_ON.getRGB());
                }
                repaint();
            }

        });

        addMouseMotionListener(new MouseAdapter() {

            int width = bufferedImage.getWidth() - 1;
            int height = bufferedImage.getHeight() - 1;

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (e.getX() > 0 && e.getY() > 0 && e.getX() < width && e.getY() < height && bufferedImage.getRGB(e.getX(), e.getY()) == ViewColor.HEXAGON_COLOR_OFF.getRGB()) {
                    grid.fill(e.getX(), e.getY(), ViewColor.HEXAGON_COLOR_ON.getRGB());
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
    
    public final void initGrid(){
        grid.drawGrid(20, 25, 20, 1);
        repaint();
    }
}
