package ru.nsu.fit.g13205.fink.filter;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class InitView extends JPanel {

    private final ZoneA zoneA = new ZoneA(this);
    private final ZoneB zoneB = new ZoneB();
    private final ZoneC zoneC = new ZoneC();

    public InitView() {
        setBackground(ViewOptions.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(ViewOptions.ZONE_WIDTH * 3 + ViewOptions.MARGIN * 4 + ViewOptions.BORDER_SIZE * 6, ViewOptions.ZONE_HEIGHT + ViewOptions.MARGIN * 2 + ViewOptions.BORDER_SIZE * 2));
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, ViewOptions.MARGIN, ViewOptions.MARGIN));
        mainPanel.setBackground(ViewOptions.BACKGROUND_COLOR);
        mainPanel.add(zoneA);
        mainPanel.add(zoneB);
        mainPanel.add(zoneC);
        add(mainPanel);
    }

    public void setImageInZone(BufferedImage bufferedImage, ZoneName zoneName) {
        switch (zoneName) {
            case ZONE_A:
                zoneB.removeImage();
                zoneB.repaint();
                zoneC.removeImage();
                zoneC.repaint();
                zoneA.setImage(bufferedImage);
                zoneA.repaint();
                break;
            case ZONE_B:
                zoneB.setImage(bufferedImage);
                zoneB.repaint();
                break;
            case ZONE_C:
                zoneC.setImage(bufferedImage);
                zoneC.repaint();
                break;
        }
    }

    void clear() {
        zoneA.removeImage();
        zoneA.repaint();
        zoneB.removeImage();
        zoneB.repaint();
        zoneC.removeImage();
        zoneC.repaint();
    }

    void setAllocationMode(boolean mode) {
        zoneA.setAllocationMode(mode);
    }

    void blackAndWhiteTransformation() {
        zoneC.setImage(Filter.blackAndWhiteTransformation(zoneB.getImage()));
        zoneC.repaint();
    }

    void negativeTransformation() {
        zoneC.setImage(Filter.negativeTransformation(zoneB.getImage()));
        zoneC.repaint();
    }

    void sharpness() {
        zoneC.setImage(Filter.sharpness(zoneB.getImage()));
        zoneC.repaint();
    }

    void stamping() {
        zoneC.setImage(Filter.stamping(zoneB.getImage()));
        zoneC.repaint();
    }

    void gammaCorrection(double gamma) {
        zoneC.setImage(Filter.gammaCorrection(zoneB.getImage(), gamma));
        zoneC.repaint();
    }

}
