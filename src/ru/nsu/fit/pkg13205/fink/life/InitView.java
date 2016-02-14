package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InitView extends JPanel {

    public InitView() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showMessageDialog(InitView.this,
                        "Clicked on " + e.getX() + "," + e.getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bufferedImage = new BufferedImage(1200,600,BufferedImage.TYPE_INT_RGB);
        Grid grid = new Grid(bufferedImage);
        grid.fill(0, 0, Color.WHITE.getRGB());
        grid.drawGrid(20, 25, 19, 1);
        g.drawImage(bufferedImage, 0, 0, this);
    }
}
