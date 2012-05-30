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

    public float area() {
        return area;
    }

    private static float calculateArea(float x1, float y1, float x2, float y2) {
        float width = abs(x2 - x1);
        float height = abs(y2 - y1);

        return width * height;
    }


}