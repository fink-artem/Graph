package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InitView extends JPanel {

    public InitView() {
        setBackground(Color.WHITE);
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
        Grid grid = new Grid(g);
        grid.drawGrid(20, 25, 19, 1);
    }
}
