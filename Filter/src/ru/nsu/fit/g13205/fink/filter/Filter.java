package ru.nsu.fit.g13205.fink.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Filter {

    static BufferedImage blackAndWhiteTransformation(BufferedImage originalImage) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int gray;
        Color originalColor;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                originalColor = new Color(originalImage.getRGB(j, i));
                gray = (int) (originalColor.getRed() * 0.3 + originalColor.getGreen() * 0.59 + originalColor.getBlue() * 0.11 + 0.5);
                resultImage.setRGB(j, i, (new Color(gray, gray, gray)).getRGB());
            }
        }
        return resultImage;
    }

    static BufferedImage negativeTransformation(BufferedImage originalImage) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color originalColor;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                originalColor = new Color(originalImage.getRGB(j, i));
                int red = 255 - originalColor.getRed();
                int green = 255 - originalColor.getGreen();
                int blue = 255 - originalColor.getBlue();
                resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
            }
        }
        return resultImage;
    }

    static BufferedImage gammaCorrection(BufferedImage originalImage, double gamma) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color originalColor;
        int red;
        int green;
        int blue;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                originalColor = new Color(originalImage.getRGB(j, i));
                red = (int) (Math.pow((double) originalColor.getRed() / 255, gamma) * 255 + 0.5);
                green = (int) (Math.pow((double) originalColor.getGreen() / 255, gamma) * 255 + 0.5);
                blue = (int) (Math.pow((double) originalColor.getBlue() / 255, gamma) * 255 + 0.5);
                resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
            }
        }
        return resultImage;
    }

}
