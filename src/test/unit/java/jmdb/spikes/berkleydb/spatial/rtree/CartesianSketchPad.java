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
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.lang.Math.max;
import static java.lang.String.format;
import static java.lang.System.out;

public class CartesianSketchPad {

    private final int width;
    private final int height;

    private int borderWidth = 1;
    private Color pointColor = GREEN;
    private int zoomFactor = 1;
    private boolean antialiasing = false;

    private List<Rectangle> boundingRectangles = new ArrayList<Rectangle>();
    private List<Point> points = new ArrayList<Point>();

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

    public CartesianSketchPad addBoundingRectangle(int bottomLeft_x, int bottomLeft_y,
                                                   int topRight_y, int topRight_Y) {

        this.boundingRectangles.add(rectangle(bottomLeft_x, bottomLeft_y,
                                              topRight_y, topRight_Y));
        return this;
    }


    public void printTo(File file) {
        file.mkdirs();
        if (file.exists()) {
            file.delete();
        }


        int canvasWidth = (width + 1 + (borderWidth * 2)) * zoomFactor;
        int canvasHeight = (height + 1 + (borderWidth * 2)) * zoomFactor;

        int strokeWidth = 1 * zoomFactor;


        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = initialiseCanvas(image, canvasWidth, canvasHeight);


        int adj_top = max(borderWidth * zoomFactor, strokeWidth - zoomFactor);
        drawCanvasRectangle(g2, DARK_GRAY, strokeWidth,
                            0, 0,
                            canvasWidth - adj_top, canvasHeight - adj_top);

        for (Rectangle r : boundingRectangles) {
            drawRectangle(g2, LIGHT_GRAY, strokeWidth,
                          r.bottomLeftX, r.bottomLeftY, r.topRightX, r.topRightY);
        }

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

    private void drawPoint(Graphics2D g2,
                           Color color,
                           int x, int y) {

        g2.setBackground(color);
        g2.setStroke(new BasicStroke(0));
        g2.setColor(color);
        g2.fillRect(x * zoomFactor + (borderWidth * zoomFactor),
                    y * zoomFactor + (borderWidth * zoomFactor),
                    zoomFactor, zoomFactor);
    }

    private void drawRectangle(Graphics2D g2,
                               Color color,
                               int strokeWidth,
                               int bottomLeftX, int bottomLeftY,
                               int topRightX, int topRightY) {


        int width = topRightX - bottomLeftX;
        int height = topRightY - bottomLeftY;

        drawCanvasRectangle(g2,
                            color,
                            strokeWidth,
                            (bottomLeftX + borderWidth) * zoomFactor,
                            (bottomLeftY + borderWidth) * zoomFactor,
                            width * zoomFactor, height * zoomFactor);
    }

    private void drawCanvasRectangle(Graphics2D g2, Color color,
                                     int strokeWidth,
                                     int bottom_x, int bottom_y,
                                     int width, int height) {
        int adj_bottom = strokeWidth / 2;
        g2.setStroke(new BasicStroke(strokeWidth));



        g2.setColor(color);

        g2.drawRect(bottom_x + adj_bottom, bottom_y + adj_bottom,
                    width, height);
    }

    private void writeText(Graphics2D g2,
                           int topLeft_X, int topLeft_Y,
                           String text) {


        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        g2.drawString(text, topLeft_X, topLeft_Y);
    }

    private Graphics2D initialiseCanvas(BufferedImage image,
                                        int canvasWidth, int canvasHeight) {
        Graphics2D g2 = image.createGraphics();

        if (antialiasing) {
            turnOnAntialiasing(g2);
        } else {
            turnOffAntialiasing(g2);
        }

        g2.setBackground(new Color(255, 255, 255));
        g2.fillRect(0, 0, canvasWidth, canvasHeight);
        return g2;
    }

    private void turnOnAntialiasing(Graphics2D g2) {
        g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
    }

    private void turnOffAntialiasing(Graphics2D g2) {
        g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
    }




    private static Point point(int x , int y) {
        return new Point(x, y);
    }

    public CartesianSketchPad percentZoom(int zoomPercentage) {
        this.zoomFactor = zoomPercentage / 100;
        return this;
    }



    private static Rectangle rectangle(int bottomLeftX, int bottomLeftY, int topRightX, int topRightY) {
        return new Rectangle(bottomLeftX, bottomLeftY, topRightX, topRightY);
    }

    private static class Point {
        public final int x;
        public final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Rectangle {
        public final int bottomLeftX;
        public final int bottomLeftY;
        public final int topRightX;
        public final int topRightY;

        private Rectangle(int bottomLeftX, int bottomLeftY, int topRightX, int topRightY) {
            this.bottomLeftX = bottomLeftX;
            this.bottomLeftY = bottomLeftY;
            this.topRightX = topRightX;
            this.topRightY = topRightY;
        }
    }
}