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
public class ImageScaler {

    public static BufferedImage scaleUpImage() {
        BufferedImage image = ImageHandler.getCurrentImage();

        int height = image.getHeight() * 2;
        int width = image.getWidth() * 2;

        BufferedImage tempImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tempImage.setRGB(i, j, image.getRGB(i / 2, j / 2));
            }
        }
        return tempImage;
    }

    public static BufferedImage scaleDownImage() {
        BufferedImage image = ImageHandler.getCurrentImage();

        int height = image.getHeight() / 2;
        int width = image.getWidth() / 2;

        BufferedImage tempImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tempImage.setRGB(i, j, image.getRGB(i * 2, j * 2));
            }
        }
        return tempImage;
    }

}
