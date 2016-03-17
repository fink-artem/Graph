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
    private final double EPS = 0.00000001;
    private boolean allocation = false;
    private boolean allocationMode = false;
    private BufferedImage originalImage;
    private double step;
    private double rectWidth;
    private double rectHeight;
    private int width;
    private int height;
    private double rectangleX1;
    private double rectangleY1;

    public ZoneA(final InitView initView) {
        super();
        zoneName = ZoneName.ZONE_A;
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (image != null && allocationMode) {
                    allocation = true;
                    rectangleX1 = e.getX() - rectWidth / 2;
                    rectangleY1 = e.getY() - rectWidth / 2;
                    if (rectangleX1 - ViewOptions.BORDER_SIZE + rectWidth > width) {
                        rectangleX1 = width - rectWidth + ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleY1 - ViewOptions.BORDER_SIZE + rectHeight > height) {
                        rectangleY1 = height - rectHeight + ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleX1 < ViewOptions.BORDER_SIZE) {
                        rectangleX1 = ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleY1 < ViewOptions.BORDER_SIZE) {
                        rectangleY1 = ViewOptions.BORDER_SIZE;
                    }
                    try {
                        initView.setImageInZone(originalImage.getSubimage((int) ((rectangleX1 - ViewOptions.BORDER_SIZE) * step + 0.5), (int) ((rectangleY1 - ViewOptions.BORDER_SIZE) * step + 0.5), (int) (rectWidth * step + 0.5), (int) (rectHeight * step + 0.5)), ZoneName.ZONE_B);
                    } catch (Exception ex) {
                    }
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
                    rectangleX1 = e.getX() - rectWidth / 2;
                    rectangleY1 = e.getY() - rectHeight / 2;
                    if (rectangleX1 - ViewOptions.BORDER_SIZE + rectWidth > width) {
                        rectangleX1 = width - rectWidth + ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleY1 - ViewOptions.BORDER_SIZE + rectHeight > height) {
                        rectangleY1 = height - rectHeight + ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleX1 < ViewOptions.BORDER_SIZE) {
                        rectangleX1 = ViewOptions.BORDER_SIZE;
                    }
                    if (rectangleY1 < ViewOptions.BORDER_SIZE) {
                        rectangleY1 = ViewOptions.BORDER_SIZE;
                    }
                    try {
                        initView.setImageInZone(originalImage.getSubimage((int) ((rectangleX1 - ViewOptions.BORDER_SIZE) * step + 0.5), (int) ((rectangleY1 - ViewOptions.BORDER_SIZE) * step + 0.5), (int) (rectWidth * step + 0.5), (int) (rectHeight * step + 0.5)), ZoneName.ZONE_B);
                    } catch (Exception ex) {
                    }
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
            g2.draw(new Rectangle2D.Double(rectangleX1, rectangleY1, rectWidth - 1, rectHeight - 1));
        }
    }

    @Override
    public void setImage(BufferedImage newImage) {
        originalImage = newImage;
        if (image == null) {
            image = new BufferedImage(ViewOptions.ZONE_WIDTH, ViewOptions.ZONE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        }
        width = newImage.getWidth();
        height = newImage.getHeight();
        if (width <= ViewOptions.ZONE_WIDTH && height <= ViewOptions.ZONE_HEIGHT) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    image.setRGB(i, j, newImage.getRGB(i, j));
                }
                for (int j = height; j < ViewOptions.ZONE_HEIGHT; j++) {
                    image.setRGB(i, j, ViewOptions.BACKGROUND_COLOR.getRGB());
                }
            }
            for (int i = width; i < ViewOptions.ZONE_WIDTH; i++) {
                for (int j = 0; j < ViewOptions.ZONE_HEIGHT; j++) {
                    image.setRGB(i, j, ViewOptions.BACKGROUND_COLOR.getRGB());
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
            int startX, endX;
            int startY, endY;
            int red, green, blue;
            Color color;
            int n;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    startX = (int) (i * step);
                    startY = (int) (j * step);
                    endX = (int) ((i + 1) * step);
                    if (Math.abs((double) endX - (i + 1) * step) > EPS) {
                        endX++;
                    }
                    endY = (int) ((j + 1) * step);
                    if (Math.abs((double) endY - (j + 1) * step) > EPS) {
                        endY++;
                    }
                    red = 0;
                    green = 0;
                    blue = 0;
                    for (int k = startY; k < endY; k++) {
                        for (int l = startX; l < endX; l++) {
                            color = new Color(newImage.getRGB(l, k));
                            red += color.getRed();
                            green += color.getGreen();
                            blue += color.getBlue();
                        }
                    }
                    n = (endY - startY) * (endX - startX);
                    image.setRGB(i, j, (new Color(red / n, green / n, blue / n)).getRGB());
                }
                for (int j = height; j < ViewOptions.ZONE_HEIGHT; j++) {
                    image.setRGB(i, j, ViewOptions.BACKGROUND_COLOR.getRGB());
                }
            }
            for (int i = width; i < ViewOptions.ZONE_WIDTH; i++) {
                for (int j = 0; j < ViewOptions.ZONE_HEIGHT; j++) {
                    image.setRGB(i, j, ViewOptions.BACKGROUND_COLOR.getRGB());
                }
            }
            if (newImage.getWidth() < ViewOptions.ZONE_WIDTH) {
                rectWidth = (double) newImage.getWidth() / step;
            } else {
                rectWidth = (double) ViewOptions.ZONE_WIDTH / step;
            }
            if (newImage.getHeight() < ViewOptions.ZONE_HEIGHT) {
                rectHeight = (double) newImage.getHeight() / step;
            } else {
                rectHeight = (double) ViewOptions.ZONE_HEIGHT / step;
            }
        }
        repaint();
    }

    public void setAllocationMode(boolean allocationMode) {
        this.allocationMode = allocationMode;
    }

}
