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
        Color color;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color = new Color(originalImage.getRGB(j, i));
                gray = (int) (color.getRed() * 0.3 + color.getGreen() * 0.59 + color.getBlue() * 0.11 + 0.5);
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
        Color color;
        int red;
        int green;
        int blue;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color = new Color(originalImage.getRGB(j, i));
                red = 255 - color.getRed();
                green = 255 - color.getGreen();
                blue = 255 - color.getBlue();
                resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
            }
        }
        return resultImage;
    }

    static BufferedImage sharpness(BufferedImage image) {
        int matrix[][] = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
        return matrixTreatment(image, matrix);
    }

    static BufferedImage stamping(BufferedImage image) {
        int matrix[][] = {{0, 1, 0}, {1, 0, -1}, {0, -1, 0}};
        return matrixTreatment(image, matrix);
    }

    static BufferedImage gammaCorrection(BufferedImage originalImage, double gamma) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color color;
        int red;
        int green;
        int blue;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color = new Color(originalImage.getRGB(j, i));
                red = (int) (Math.pow((double) color.getRed() / 255, gamma) * 255 + 0.5);
                green = (int) (Math.pow((double) color.getGreen() / 255, gamma) * 255 + 0.5);
                blue = (int) (Math.pow((double) color.getBlue() / 255, gamma) * 255 + 0.5);
                resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
            }
        }
        return resultImage;
    }

    static BufferedImage matrixTreatment(BufferedImage originalImage, int[][] matrix) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color color;
        int red;
        int green;
        int blue;
        width--;
        height--;
        for (int i = 0; i <= height; i++) {
            for (int j = 0; j <= width; j++) {
                color = new Color(originalImage.getRGB(j, i));
                red = color.getRed() * matrix[1][1];
                green = color.getGreen() * matrix[1][1];
                blue = color.getBlue() * matrix[1][1];
                if (i > 0) {
                    color = new Color(originalImage.getRGB(j, i - 1));
                    red += color.getRed() * matrix[0][1];
                    green += color.getGreen() * matrix[0][1];
                    blue += color.getBlue() * matrix[0][1];
                }
                if (j > 0) {
                    color = new Color(originalImage.getRGB(j - 1, i));
                    red += color.getRed() * matrix[1][0];
                    green += color.getGreen() * matrix[1][0];
                    blue += color.getBlue() * matrix[1][0];
                }
                if (i < height) {
                    color = new Color(originalImage.getRGB(j, i + 1));
                    red += color.getRed() * matrix[2][1];
                    green += color.getGreen() * matrix[2][1];
                    blue += color.getBlue() * matrix[2][1];
                }
                if (j < width) {
                    color = new Color(originalImage.getRGB(j + 1, i));
                    red += color.getRed() * matrix[1][2];
                    green += color.getGreen() * matrix[1][2];
                    blue += color.getBlue() * matrix[1][2];
                }
                if (red < 0) {
                    red = 0;
                }
                if (green < 0) {
                    green = 0;
                }
                if (blue < 0) {
                    blue = 0;
                }
                if (red > 255) {
                    red = 255;
                }
                if (green > 255) {
                    green = 255;
                }
                if (blue > 255) {
                    blue = 255;
                }
                resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
            }
        }
        return resultImage;
    }
}
