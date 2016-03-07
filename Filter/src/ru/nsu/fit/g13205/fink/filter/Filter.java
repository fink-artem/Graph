package ru.nsu.fit.g13205.fink.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    static BufferedImage floydDithering(BufferedImage originalImage, int redN, int greenN, int blueN) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        double a[][][] = new double[height][width][3];
        int step[] = new int[3];
        step[0] = 255 / (redN - 1);
        step[1] = 255 / (greenN - 1);
        step[2] = 255 / (blueN - 1);
        Color color;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color = new Color(originalImage.getRGB(j, i));
                a[i][j][0] = color.getRed();
                a[i][j][1] = color.getGreen();
                a[i][j][2] = color.getBlue();
            }
        }
        height--;
        width--;
        double error;
        int red;
        int green;
        int blue;
        for (int i = 0; i < height; i++) {
            resultImage.setRGB(0, i, (new Color(findClosestPaletteColor(a[i][0][0], step[0]), findClosestPaletteColor(a[i][0][1], step[1]), findClosestPaletteColor(a[i][0][2], step[2]))).getRGB());
            resultImage.setRGB(width, i, (new Color(findClosestPaletteColor(a[i][width][0], step[0]), findClosestPaletteColor(a[i][width][1], step[1]), findClosestPaletteColor(a[i][width][2], step[2]))).getRGB());
        }
        for (int j = 0; j <= width; j++) {
            resultImage.setRGB(j, height, (new Color(findClosestPaletteColor(a[j][height][0], step[0]), findClosestPaletteColor(a[j][height][1], step[1]), findClosestPaletteColor(a[j][height][2], step[2]))).getRGB());
        }
        for (int i = 0; i < height; i++) {
            for (int j = 1; j < width; j++) {
                color = new Color(originalImage.getRGB(j, i));
                int oldColor[] = new int[3];
                oldColor[0] = color.getRed();
                oldColor[1] = color.getGreen();
                oldColor[2] = color.getBlue();
                for (int k = 0; k < 3; k++) {
                    error = oldColor[k] - findClosestPaletteColor(a[j][i][k], step[k]);
                    a[i][j + 1][k] += 7.0 * error / 16.0;
                    a[i + 1][j - 1][k] += 3.0 * error / 16.0;
                    a[i + 1][j][k] += 5.0 * error / 16.0;
                    a[i + 1][j + 1][k] += error / 16.0;
                }
                red = Math.max(Math.min(findClosestPaletteColor(a[i][j][0], step[0]), 255), 0);
                green = Math.max(Math.min(findClosestPaletteColor(a[i][j][1], step[1]), 255), 0);
                blue = Math.max(Math.min(findClosestPaletteColor(a[i][j][2], step[2]), 255), 0);
                resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
            }
        }
        return resultImage;
    }

    static int findClosestPaletteColor(double n, int step) {
        return (int) (Math.round(n / step) * step + 0.5);
    }

    static BufferedImage zoom(BufferedImage originalImage) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color color1, color2, color3;
        int red;
        int green;
        int blue;
        int startX = width / 4;
        int startY = height / 4;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        resultImage.setRGB(j, i, originalImage.getRGB(startX + j / 2, startY + i / 2));
                    } else {
                        color1 = new Color(originalImage.getRGB(startX + j / 2, startY + i / 2));
                        color2 = new Color(originalImage.getRGB(startX + j / 2 + 1, startY + i / 2));
                        red = (int) ((double) (color1.getRed() + color2.getRed()) / 2 + 0.5);
                        green = (int) ((double) (color1.getGreen() + color2.getGreen()) / 2 + 0.5);
                        blue = (int) ((double) (color1.getBlue() + color2.getBlue()) / 2 + 0.5);
                        resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
                    }
                } else {
                    if (j % 2 == 0) {
                        color1 = new Color(originalImage.getRGB(startX + j / 2, startY + i / 2));
                        color2 = new Color(originalImage.getRGB(startX + j / 2, startY + i / 2 + 1));
                        red = (int) ((double) (color1.getRed() + color2.getRed()) / 2 + 0.5);
                        green = (int) ((double) (color1.getGreen() + color2.getGreen()) / 2 + 0.5);
                        blue = (int) ((double) (color1.getBlue() + color2.getBlue()) / 2 + 0.5);
                        resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
                    } else {
                        color1 = new Color(originalImage.getRGB(startX + j / 2, startY + i / 2));
                        color2 = new Color(originalImage.getRGB(startX + j / 2 + 1, startY + i / 2));
                        red = (int) ((double) (color1.getRed() + color2.getRed()) / 2 + 0.5);
                        green = (int) ((double) (color1.getGreen() + color2.getGreen()) / 2 + 0.5);
                        blue = (int) ((double) (color1.getBlue() + color2.getBlue()) / 2 + 0.5);
                        color1 = new Color(red, green, blue);
                        color3 = new Color(originalImage.getRGB(startX + j / 2, startY + i / 2 + 1));
                        color2 = new Color(originalImage.getRGB(startX + j / 2 + 1, startY + i / 2 + 1));
                        red = (int) ((double) (color3.getRed() + color2.getRed()) / 2 + 0.5);
                        green = (int) ((double) (color3.getGreen() + color2.getGreen()) / 2 + 0.5);
                        blue = (int) ((double) (color3.getBlue() + color2.getBlue()) / 2 + 0.5);
                        color2 = new Color(red, green, blue);
                        red = (int) ((double) (color1.getRed() + color2.getRed()) / 2 + 0.5);
                        green = (int) ((double) (color1.getGreen() + color2.getGreen()) / 2 + 0.5);
                        blue = (int) ((double) (color1.getBlue() + color2.getBlue()) / 2 + 0.5);
                        resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
                    }
                }
            }
        }
        return resultImage;
    }

    static BufferedImage sobel(BufferedImage originalImage) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color color;
        int redX, redY;
        int greenX, greenY;
        int blueX, blueY;
        int red;
        int green;
        int blue;
        height--;
        width--;
        int Gy[][] = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        int Gx[][] = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                redX = 0;
                greenX = 0;
                blueX = 0;
                redY = 0;
                greenY = 0;
                blueY = 0;
                for (int k = 0; k <= 2; k++) {
                    for (int l = 0; l <= 2; l++) {
                        color = new Color(originalImage.getRGB(j + l - 1, i + k - 1));
                        redX += Gx[k][l] * color.getRed();
                        redY += Gy[k][l] * color.getRed();
                        greenX += Gx[k][l] * color.getGreen();
                        greenY += Gy[k][l] * color.getGreen();
                        blueX += Gx[k][l] * color.getBlue();
                        blueY += Gy[k][l] * color.getBlue();
                    }
                }
                red = Math.min((int) (Math.sqrt(redX * redX + redY * redY) + 0.5), 255);
                green = Math.min((int) (Math.sqrt(greenX * greenX + greenY * greenY) + 0.5), 255);
                blue = Math.min((int) (Math.sqrt(blueX * blueX + blueY * blueY) + 0.5), 255);
                resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
            }
        }
        return resultImage;
    }

    static BufferedImage robert(BufferedImage originalImage) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color color1, color2, color3, color4;
        int red;
        int green;
        int blue;
        height--;
        width--;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color1 = new Color(originalImage.getRGB(j, i));
                color2 = new Color(originalImage.getRGB(j + 1, i));
                color3 = new Color(originalImage.getRGB(j, i + 1));
                color4 = new Color(originalImage.getRGB(j + 1, i + 1));
                red = Math.min((int) Math.sqrt(Math.pow(color1.getRed() - color4.getRed(), 2) + Math.pow(color2.getRed() - color3.getRed(), 2)), 255);
                green = Math.min((int) Math.sqrt(Math.pow(color1.getGreen() - color4.getGreen(), 2) + Math.pow(color2.getGreen() - color3.getGreen(), 2)), 255);
                blue = Math.min((int) Math.sqrt(Math.pow(color1.getBlue() - color4.getBlue(), 2) + Math.pow(color2.getBlue() - color3.getBlue(), 2)), 255);
                resultImage.setRGB(j, i, (new Color(red, green, blue)).getRGB());
            }
        }
        return resultImage;
    }

    static BufferedImage smoothing(BufferedImage originalImage) {
        if (originalImage == null) {
            return null;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Color color;
        int median;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                List<Integer> redList = new ArrayList<>();
                List<Integer> greenList = new ArrayList<>();
                List<Integer> blueList = new ArrayList<>();
                for (int k = i - 2; k <= i + 2; k++) {
                    for (int l = j - 2; l <= j + 2; l++) {
                        if (k >= 0 && l >= 0 && k < height && l < width) {
                            color = new Color(originalImage.getRGB(l, k));
                            redList.add(color.getRed());
                            greenList.add(color.getGreen());
                            blueList.add(color.getBlue());
                        }
                    }
                }
                Collections.sort(redList);
                Collections.sort(greenList);
                Collections.sort(blueList);
                median = redList.size() / 2;
                resultImage.setRGB(j, i, (new Color(redList.get(median), greenList.get(median), blueList.get(median))).getRGB());
            }
        }
        return resultImage;
    }

    static BufferedImage sharpness(BufferedImage image) {
        int matrix[][] = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
        return matrixTreatment(image, matrix, 0);
    }

    static BufferedImage stamping(BufferedImage image) {
        int matrix[][] = {{0, 1, 0}, {1, 0, -1}, {0, -1, 0}};
        return matrixTreatment(image, matrix, 128);
    }

    static BufferedImage watercolorCorrection(BufferedImage originalImage) {
        return sharpness(smoothing(originalImage));
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

    static BufferedImage matrixTreatment(BufferedImage originalImage, int[][] matrix, int plus) {
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
                red += plus;
                green += plus;
                blue += plus;
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
