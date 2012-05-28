package jmdb.spikes.berkleydb.spatial.rtree;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;
import java.util.List;


import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.GREEN;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.lang.System.out;

public class CartesianSketchPad {

    private final int width;
    private final int height;
    private List<Point> points = new ArrayList<Point>();
    private int borderWidth = 1;
    private Color pointColor = GREEN;


    public static CartesianSketchPad createCartesianSketchPad(int limitX, int limitY) {
        return new CartesianSketchPad(limitX, limitY);
    }

    public CartesianSketchPad(int extentX, int height) {
        this.width = extentX;
        this.height = height;
    }

    public CartesianSketchPad addPoint(int x, int y) {
        this.points.add(point(x, y));
        return this;
    }


    public void printTo(File file) {
        file.mkdirs();
        if (file.exists()) {
            file.delete();
        }

        int canvasWidth = width + borderWidth * 2;
        int canvasHeight = height + borderWidth * 2;

        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = initialiseCanvas(image);

        drawRectangle(g2, DARK_GRAY, 1, 0, 0, canvasWidth, canvasHeight);

        for (Point p : points) {
            drawPoint(g2, pointColor, p.x, p.y);
        }

        try {
            ImageIO.write(image, "png", file);
            out.println(format("Just wrote an image to [%s]", file.getAbsolutePath()));
        } catch (IOException e) {
            throw new RuntimeException(format("Failed to write image file [%s] (See cause)", file.getAbsolutePath()), e);
        }

    }

    private void writeText(Graphics2D g2,
                           int topLeft_X, int topLeft_Y,
                           String text) {


        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        g2.drawString(text, topLeft_X, topLeft_Y);
    }

    private void drawPoint(Graphics2D g2,
                           Color color,
                           int x, int y) {

        g2.setBackground(color);
        g2.setStroke(new BasicStroke(0));
        g2.setColor(color);
        g2.fillRect(x + borderWidth, y + borderWidth, 1, 1);
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

    private static Point point(int x , int y) {
        return new Point(x, y);
    }

    private static class Point {
        public final int x;
        public final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}