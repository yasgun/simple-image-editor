/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.image_processing_project;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author yasas
 */
public class YCbCrImage {

    private final int height;
    private final int width;
    private int[][] y;
    private int[][] cb;
    private int[][] cr;

    YCbCrImage(BufferedImage image) {
        height = image.getHeight();
        width = image.getWidth();

        y = new int[width][height];
        cb = new int[width][height];
        cr = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = new Color(image.getRGB(i, j));
                y[i][j] = (int) (0 + 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
                cb[i][j] = (int) (128 - 0.168736 * color.getRed() - 0.331264 * color.getGreen() + 0.5 * color.getBlue());
                cr[i][j] = (int) (128 + 0.5 * color.getRed() - 0.418688 * color.getGreen() - 0.081312 * color.getBlue());

                if (y[i][j] > 255) {
                    y[i][j] = 255;
                }
                if (cb[i][j] > 255) {
                    cb[i][j] = 255;
                }
                if (cr[i][j] > 255) {
                    cr[i][j] = 255;
                }
                if (y[i][j] < 0) {
                    y[i][j] = 0;
                }
                if (cb[i][j] < 0) {
                    cb[i][j] = 0;
                }
                if (cr[i][j] < 0) {
                    cr[i][j] = 0;
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getY(int i, int j) {
        return y[i][j];
    }

    public void setY(int i, int j, int value) {
        y[i][j] = value;
    }

    public int getCb(int i, int j) {
        return cb[i][j];
    }

    public int getCr(int i, int j) {
        return cr[i][j];
    }

    public BufferedImage getRGBImage() {
        BufferedImage tempImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int r = (int) (y[i][j] + 1.402 * (cr[i][j] - 128));
                int g = (int) (y[i][j] - 0.344136 * (cb[i][j] - 128) - 0.714136 * (cr[i][j] - 128));
                int b = (int) (y[i][j] + 1.772 * (cb[i][j] - 128));

                if (r > 255) {
                    r = 255;
                }
                if (g > 255) {
                    g = 255;
                }
                if (b > 255) {
                    b = 255;
                }
                if (r < 0) {
                    r = 0;
                }
                if (g < 0) {
                    g = 0;
                }
                if (b < 0) {
                    b = 0;
                }

                Color color = new Color(r, g, b);
                tempImage.setRGB(i, j, color.getRGB());
            }
        }
        return tempImage;
    }
}
