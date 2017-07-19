/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.image_processing_project;

import java.awt.image.BufferedImage;

/**
 *
 * @author yasas
 */
public class ImageTransformer {

    public static BufferedImage flipImage(boolean horizontal) {
        BufferedImage image = ImageHandler.getCurrentImage();
        BufferedImage tempImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        int height = image.getHeight();
        int width = image.getWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (horizontal) {
                    tempImage.setRGB(i, j, image.getRGB(width - 1 - i, j));
                } else {
                    tempImage.setRGB(i, j, image.getRGB(i, height - 1 - j));
                }

            }
        }
        return tempImage;
    }

    public static BufferedImage rotateImage(boolean clockwise) {
        BufferedImage image = ImageHandler.getCurrentImage();

        int height = image.getWidth();
        int width = image.getHeight();

        BufferedImage tempImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (clockwise) {
                    tempImage.setRGB(i, j, image.getRGB(j, width - 1 - i));
                } else {
                    tempImage.setRGB(i, j, image.getRGB(height - 1 - j, i));
                }
            }
        }
        return tempImage;
    }

}
