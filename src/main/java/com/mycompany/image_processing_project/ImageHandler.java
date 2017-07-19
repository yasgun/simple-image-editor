/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.image_processing_project;

import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author yasas
 */
public class ImageHandler {

    private static BufferedImage currentImage = null;
    private static YCbCrImage currentYCbCrImage = null;
    private static String currentImageDetails = "";
    private static HashMap history = new HashMap<Integer, BufferedImage>();
    private static HashMap historyDetails = new HashMap<Integer, String>();
    private static Integer count = 0;

    public static void resetImage() {
        int size = history.size();
        if (size > 0) {
            setCurrentImage((BufferedImage) history.get(0), "Image Reset");
        }
    }

    public static void undoImage() {
        int size = history.size();
        if (size > 0) {
            setCurrentImage((BufferedImage) history.get(size - 1), "Image Undo");
        }
    }

    public static void goToHistoryImage(int index) {
        int size = history.size();
        if (size > 0) {
            setCurrentImage((BufferedImage) history.get(size - 1 - index), "Image Restored");
        }
    }

    public static void setCurrentImage(BufferedImage image, String details) {
        if (currentImage != null) {
            history.put(count, currentImage);
            historyDetails.put(count, currentImageDetails);
            count++;
        }
        currentImage = image;
        currentYCbCrImage = new YCbCrImage(image);
        currentImageDetails = details;
    }

    public static BufferedImage getCurrentImage() {
        return currentImage;
    }

    public static YCbCrImage getCurrentYCbCrImage() {
        return currentYCbCrImage;
    }

    public static HashMap<Integer, String> getHistoryDetails() {
        return historyDetails;
    }

    public static void saveImage(JFrame context) {
        if (getCurrentImage() != null) {
            JFileChooser locationChooser = new JFileChooser();
            if (locationChooser.showSaveDialog(context) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = new File(locationChooser.getSelectedFile().getAbsolutePath() + ".png");
                    ImageIO.write(getCurrentImage(), "png", file);
                    JOptionPane.showMessageDialog(locationChooser, "FILE SAVED");
                } catch (HeadlessException | IOException ex) {
                    JOptionPane.showMessageDialog(locationChooser, "FILE NOT SAVED");
                }
            }
        }
    }
}
