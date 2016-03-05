package ru.nsu.fit.g13205.fink.filter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ZoneA extends Zone {

    private final float dash[] = {1, 1};
    private final InitView initView;
    private boolean allocation = false;
    private boolean allocationMode = false;
    private BufferedImage originalImage;
    private double step;
    private int rectWidth;
    private int rectHeight;
    private int width;
    private int height;
    private int rectangleX1;
    private int rectangleY1;

    public ZoneA(final InitView initView) {
        super();
        this.initView = initView;
        zoneName = ZoneName.ZONE_A;
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (bufferedImage != null && allocationMode) {
                    allocation = true;
                    rectangleX1 = e.getX() - (int) (rectWidth / 2 + 0.5);
                    rectangleY1 = e.getY() - (int) (rectWidth / 2 + 0.5);
                    if (rectangleX1 < ViewOptions.BORDER_SIZE) {
                        rectangleX1 = ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleY1 < ViewOptions.BORDER_SIZE) {
                        rectangleY1 = ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleX1 - ViewOptions.BORDER_SIZE + rectWidth > width) {
                        rectangleX1 = width - rectWidth + ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleY1 - ViewOptions.BORDER_SIZE + rectHeight > height) {
                        rectangleY1 = height - rectHeight + ViewOptions.BORDER_SIZE;
                    }
                    initView.setImageInZone(originalImage.getSubimage((int) ((rectangleX1 - ViewOptions.BORDER_SIZE) * step + 0.5), (int) ((rectangleY1 - ViewOptions.BORDER_SIZE) * step + 0.5), (int) (rectWidth * step + 0.5), (int) (rectHeight * step + 0.5)), ZoneName.ZONE_B);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                super.mouseReleased(me);
                if (allocation) {
                    allocation = false;
                    repaint();
                }
            }

        });
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (allocation) {
                    rectangleX1 = e.getX() - (int) (rectWidth / 2 + 0.5);
                    rectangleY1 = e.getY() - (int) (rectHeight / 2 + 0.5);
                    if (rectangleX1 < ViewOptions.BORDER_SIZE) {
                        rectangleX1 = ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleY1 < ViewOptions.BORDER_SIZE) {
                        rectangleY1 = ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleX1 - ViewOptions.BORDER_SIZE + rectWidth > width) {
                        rectangleX1 = width - rectWidth + ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleY1 - ViewOptions.BORDER_SIZE + rectHeight > height) {
                        rectangleY1 = height - rectHeight + ViewOptions.BORDER_SIZE;
                    }
                    initView.setImageInZone(originalImage.getSubimage((int) ((rectangleX1 - ViewOptions.BORDER_SIZE) * step + 0.5), (int) ((rectangleY1 - ViewOptions.BORDER_SIZE) * step + 0.5), (int) (rectWidth * step + 0.5), (int) (rectHeight * step + 0.5)), ZoneName.ZONE_B);
                    repaint();
                }
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (allocation) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dash, 0));
            g2.draw(new Rectangle2D.Double(rectangleX1, rectangleY1, rectWidth - ViewOptions.BORDER_SIZE - 1, rectHeight - ViewOptions.BORDER_SIZE - 1));
        }
    }

    @Override
    public void setImage(BufferedImage image) {
        originalImage = image;
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(ViewOptions.ZONE_WIDTH, ViewOptions.ZONE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        }
        width = image.getWidth();
        height = image.getHeight();
        if (width <= ViewOptions.ZONE_WIDTH && height <= ViewOptions.ZONE_HEIGHT) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bufferedImage.setRGB(i, j, image.getRGB(i, j));
                }
                for (int j = height; j < ViewOptions.ZONE_HEIGHT; j++) {
                    bufferedImage.setRGB(i, j, ViewOptions.BACKGROUND_COLOR.getRGB());
                }
            }
            for (int i = width; i < ViewOptions.ZONE_WIDTH; i++) {
                for (int j = 0; j < ViewOptions.ZONE_HEIGHT; j++) {
                    bufferedImage.setRGB(i, j, ViewOptions.BACKGROUND_COLOR.getRGB());
                }
            }
            rectHeight = height;
            rectWidth = width;
            step = 1;
        } else {
            if (width * ViewOptions.ZONE_HEIGHT > height * ViewOptions.ZONE_WIDTH) {
                step = (double) width / ViewOptions.ZONE_WIDTH;
                width = ViewOptions.ZONE_WIDTH;
                height /= step;
            } else {
                step = (double) height / ViewOptions.ZONE_HEIGHT;
                width /= step;
                height = ViewOptions.ZONE_HEIGHT;
            }
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bufferedImage.setRGB(i, j, image.getRGB((int) (i * step + 0.5), (int) (j * step + 0.5)));
                }
                for (int j = height; j < ViewOptions.ZONE_HEIGHT; j++) {
                    bufferedImage.setRGB(i, j, ViewOptions.BACKGROUND_COLOR.getRGB());
                }
            }
            for (int i = width; i < ViewOptions.ZONE_WIDTH; i++) {
                for (int j = 0; j < ViewOptions.ZONE_HEIGHT; j++) {
                    bufferedImage.setRGB(i, j, ViewOptions.BACKGROUND_COLOR.getRGB());
                }
            }
            rectWidth = (int) (ViewOptions.ZONE_WIDTH / step + 0.5);
            rectHeight = rectWidth;
        }
        repaint();
    }

    public void setAllocationMode(boolean allocationMode) {
        this.allocationMode = allocationMode;
    }

}
