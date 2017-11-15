package sample.classes;

import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import java.awt.*;

public class ImageHistogram {
    private Image image;
    private long red[] = new long[256];
    private long green[] = new long[256];
    private long blue[] = new long[256];
    private long brightness[] = new long[256];
    XYChart.Series seriesBrightness;
    XYChart.Series seriesRed;
    XYChart.Series seriesGreen;
    XYChart.Series seriesBlue;

    private boolean success;

    public ImageHistogram(Image src) {
        image = src;
        success = false;

        //init
        for (int i = 0; i < 256; i++) {
            brightness[i] = 0;
        }

        PixelReader pixelReader = image.getPixelReader();
        if (pixelReader == null) {
            return;
        }

        //count pixels
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int argb = pixelReader.getArgb(x, y);
                int r = (0xff & (argb >> 16));
                int g = (0xff & (argb >> 8));
                int b = (0xff & argb);

                red[r]++;
                green[g]++;
                blue[b]++;

                //Convert RGB to HSB (or HSV)
                float[] hsb = new float[3];
                Color.RGBtoHSB(r, g, b, hsb);
                brightness[(int)(hsb[2]*255)]++;
            }
        }

        seriesRed = new XYChart.Series();
        seriesGreen = new XYChart.Series();
        seriesBlue = new XYChart.Series();
        seriesBrightness = new XYChart.Series();
        seriesRed.setName("red");
        seriesGreen.setName("green");
        seriesBlue.setName("blue");
        seriesBrightness.setName("Brightness");

        //copy red[], green[], blue[]
        //to seriesRed, seriesGreen, seriesBlue
        for (int i = 0; i < 256; i++) {
            seriesRed.getData().add(new XYChart.Data(String.valueOf(i), red[i]));
            seriesGreen.getData().add(new XYChart.Data(String.valueOf(i), green[i]));
            seriesBlue.getData().add(new XYChart.Data(String.valueOf(i), blue[i]));
            seriesBrightness.getData().add(new XYChart.Data(String.valueOf(i), brightness[i]));
        }
        success = true;
    }

    public long[] getBrightness(){ return brightness;}

    public boolean isSuccess() {
        return success;
    }

    public XYChart.Series getSeriesRed() {
        return seriesRed;
    }

    public XYChart.Series getSeriesGreen() {
        return seriesGreen;
    }

    public XYChart.Series getSeriesBlue() {
        return seriesBlue;
    }

    public XYChart.Series getSeriesBrightness() {
        return seriesBrightness;
    }
}
