/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.image_processing_project;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author yasas
 */
public class ImageFilter {

    public static int alpha = 0;
    public static int type = 0;

    public static BufferedImage meanFilterImage() {
        YCbCrImage image = ImageHandler.getCurrentYCbCrImage();

        int height = image.getHeight();
        int width = image.getWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int sum = 0;
                int count = 9;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (k >= 0 && k < width && l >= 0 && l < height) {
                            sum += image.getY(k, l);
                        } else {
                            count--;
                        }
                    }
                }
                image.setY(i, j, sum / count);
            }
        }

        return image.getRGBImage();
    }

    public static BufferedImage medianFilterImage() {
        YCbCrImage image = ImageHandler.getCurrentYCbCrImage();

        int height = image.getHeight();
        int width = image.getWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int count = 9;
                ArrayList<Integer> pixels = new ArrayList<>();
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (k >= 0 && k < width && l >= 0 && l < height) {
                            pixels.add(image.getY(k, l));
                        } else {
                            count--;
                        }

                    }
                }

                Collections.sort(pixels);

                if (count % 2 != 0) {
                    System.out.println("count: " + (((count - 1) / 2) + 1));
                    image.setY(i, j, pixels.get(((count - 1) / 2) + 1));
                } else {
                    image.setY(i, j, pixels.get(count / 2));
                }

            }
        }

        return image.getRGBImage();
    }

    public static BufferedImage alphaTrimmedMeanFilterImage() {
        YCbCrImage image = ImageHandler.getCurrentYCbCrImage();

        int height = image.getHeight();
        int width = image.getWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int count = 9;
                ArrayList<Integer> pixels = new ArrayList<>();
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (k >= 0 && k < width && l >= 0 && l < height) {
                            pixels.add(image.getY(k, l));
                        } else {
                            count--;
                        }

                    }
                }

                Collections.sort(pixels);
                int final_value = 0;
                int final_count = 0;
                int final_alpha = alpha;

                if (count != 9) {
                    final_alpha = alpha - (((9 - count) / 2) + 1);
                    if (final_alpha < 0) {
                        final_alpha = 0;
                    }
                }

                for (int m = final_alpha; m < pixels.size() - final_alpha; m++) {
                    final_value += pixels.get(m);
                    final_count++;
                }
                image.setY(i, j, final_value / final_count);
            }
        }

        return image.getRGBImage();
    }
}
