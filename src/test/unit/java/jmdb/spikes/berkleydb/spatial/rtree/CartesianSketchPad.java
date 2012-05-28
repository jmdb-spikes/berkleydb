package jmdb.spikes.berkleydb.spatial.rtree;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Color.BLUE;
import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.GREEN;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.String.format;
import static java.lang.System.out;

public class CartesianSketchPad {

    private final int extentX;
    private final int extentY;


    public static CartesianSketchPad createCartesianSketchPad(int limitX, int limitY) {
        return new CartesianSketchPad(limitX, limitY);
    }

    public CartesianSketchPad(int extentX, int extentY) {
        this.extentX = extentX;
        this.extentY = extentY;
    }


    public void printTo(File file) {
        file.mkdirs();
        if (file.exists()) {
            file.delete();
        }

        int borderWidth = 1;
        int canvasExtentX = extentX + borderWidth * 2;
        int canvasExtentY = extentY + borderWidth * 2;

        BufferedImage image = new BufferedImage(canvasExtentX, canvasExtentY, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = initialiseCanvas(image);

        drawRectangle(g2, DARK_GRAY, 1, 0, 0, canvasExtentX, canvasExtentY);

        Color cornerColor = GREEN;
        drawPoint(g2, cornerColor, 1, 1);
        drawPoint(g2, cornerColor, extentX, extentY);
        drawPoint(g2, cornerColor, 1, extentY);
        drawPoint(g2, cornerColor, extentX, 1);

        drawPoint(g2, cornerColor, 51, 51);

        try {
            ImageIO.write(image, "png", file);
            out.println(format("Just wrote an image to [%s]", file.getAbsolutePath()));
        } catch (IOException e) {
            throw new RuntimeException(format("Failed to write image file [%s] (See cause)", file.getAbsolutePath()), e);
        }

    }

    private void drawPoint(Graphics2D g2,
                           Color color,
                           int x, int y) {

        g2.setBackground(color);
        g2.setStroke(new BasicStroke(0));
        g2.setColor(color);
        g2.fillRect(x, y, 1, 1);
    }

    private Graphics2D initialiseCanvas(BufferedImage image) {
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g2.setBackground(new Color(255, 255, 255));
        g2.fillRect(0, 0, 1000, 1000);
        return g2;
    }

    private void drawRectangle(Graphics2D g2, Color color,
                               int strokeWidth,
                               int bottom_x, int bottom_y, int top_x, int top_y) {
        g2.setStroke(new BasicStroke(strokeWidth));

        int adj = max(1, strokeWidth - 1);

        g2.setColor(color);
        g2.drawRect(bottom_x, bottom_y, top_x - adj, top_y - adj);
    }
}