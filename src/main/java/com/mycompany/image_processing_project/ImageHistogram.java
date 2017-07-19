/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.image_processing_project;

import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author yasas
 */
public class ImageHistogram {

    private static double[] histogram;
    private static double[] contrast_lut;

    public static BufferedImage autoContrastImage() {
        YCbCrImage image = ImageHandler.getCurrentYCbCrImage();
        createContrastLUT(image);

        int height = image.getHeight();
        int width = image.getWidth();

        int min = 0;
        int max = 255;

        for (int i = 0; i < 256; i++) {
            if (contrast_lut[i] <= 0.05) {
                min = i;
            }
        }

        for (int i = 255; i >= 0; i--) {
            if (contrast_lut[i] >= 1 - 0.05) {
                max = i;
            }
        }

        System.out.println("MIN:" + min + " MAX:" + max);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setY(i, j, (int) ((double) (image.getY(i, j) - min) / (double) (max - min) * 255));
            }
        }

        return image.getRGBImage();
    }

    public static BufferedImage adjustContrast(int min, int max) {
        YCbCrImage image = ImageHandler.getCurrentYCbCrImage();
        createContrastLUT(image);

        int height = image.getHeight();
        int width = image.getWidth();

        System.out.println("MIN:" + min + " MAX:" + max);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setY(i, j, (int) ((double) (image.getY(i, j) - min) / (double) (max - min) * 255));
            }
        }

        return image.getRGBImage();
    }

    public static BufferedImage adjustBrightness(int value) {
        YCbCrImage image = ImageHandler.getCurrentYCbCrImage();
        createContrastLUT(image);

        int height = image.getHeight();
        int width = image.getWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (0 > image.getY(i, j) + value) {
                    image.setY(i, j, 0);
                } else if (255 < image.getY(i, j) + value) {
                    image.setY(i, j, 255);
                } else {
                    image.setY(i, j, image.getY(i, j) + value);
                }
            }
        }

        return image.getRGBImage();
    }

    private static void createContrastLUT(YCbCrImage image) {
        createHistogram(image);
        contrast_lut = new double[256];

        double cumulative_prob = 0;
        for (int i = 0; i < 256; i++) {
            cumulative_prob += histogram[i];
            contrast_lut[i] = cumulative_prob;
        }
    }

    private static void createHistogram(YCbCrImage image) {
        histogram = new double[256];
        int height = image.getHeight();
        int width = image.getWidth();

        int sum = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int grayVal = (int) image.getY(i, j);
                histogram[grayVal]++;
                sum++;
            }
        }

        for (int i = 0; i < 256; i++) {
            histogram[i] = histogram[i] / sum;
        }

    }

    public static JFreeChart plotHistogram() {
        YCbCrImage image = ImageHandler.getCurrentYCbCrImage();
        createHistogram(image);

        final XYSeries series = new XYSeries("Y Channel");

        for (int i = 0; i < histogram.length; i++) {
            series.add(i, histogram[i]);
        }

        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                null,
                "intensity",
                "PDF",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        return chart;
    }
}
