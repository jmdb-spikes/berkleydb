package jmdb.spikes.spatial.rtree;

import static java.lang.Math.abs;

class Rectangle {
    public final float x1;
    public final float y1;

    public final float x2;
    public final float y2;
    private float area;

    Rectangle(int x1, int y1, int x2, int y2) {
        this((float)x1, (float)y1, (float)x2, (float)y2);
    }

    Rectangle(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.area = calculateArea(x1, y1, x2, y2);
    }

    public static Rectangle calculateBoundingRectangle(Rectangle ... rectangles) {
        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;

        float minY = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;

        for (Rectangle r : rectangles) {
            minX = (r.x1 < minX) ? r.x1 : minX;
            maxX = (r.x2 > maxX) ? r.x2 : maxX;

            minY = (r.y1 < minY) ? r.y1 : minY;
            maxY = (r.y2 > maxY) ? r.y2 : maxY;
        }
        return new Rectangle(minX, minY, maxX, maxY);
    }

    public float area() {
        return area;
    }

    private static float calculateArea(float x1, float y1, float x2, float y2) {
        float width = abs(x2 - x1);
        float height = abs(y2 - y1);

        return width * height;
    }


}