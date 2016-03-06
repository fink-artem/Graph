package ru.nsu.fit.g13205.fink.filter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class Zone extends JPanel {

    protected BufferedImage image = null;
    protected ZoneName zoneName;

    public Zone() {
        setPreferredSize(new Dimension(ViewOptions.ZONE_WIDTH + ViewOptions.BORDER_SIZE, ViewOptions.ZONE_HEIGHT + ViewOptions.BORDER_SIZE));
        setBackground(ViewOptions.BACKGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(ViewOptions.BORDER_COLOR, ViewOptions.BORDER_SIZE, false));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }

    void setImage(BufferedImage bufferedImage) {
        this.image = bufferedImage;
    }
    
    BufferedImage getImage(){
        return image;
    }

    void removeImage() {
        image = null;
    }

}
