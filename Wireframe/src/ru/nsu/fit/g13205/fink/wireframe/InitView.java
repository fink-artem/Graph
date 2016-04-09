package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Graphics;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private final Model model;

    public InitView(Model model) {
        this.model = model;
        setBackground(ViewOptions.BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    }

    void clear() {
    }

}
