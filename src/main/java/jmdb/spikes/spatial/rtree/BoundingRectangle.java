package jmdb.spikes.spatial.rtree;

class BoundingRectangle {
    public final float x1;
    public final float y1;

    public final float x2;
    public final float y2;

    BoundingRectangle(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}