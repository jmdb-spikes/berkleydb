package jmdb.spikes.spatial.rtree;

public class IndexEntry {

    public final BoundingRectangle boundingRectangle;
    public final String key;

    public IndexEntry(BoundingRectangle boundingRectangle, String key) {

        this.boundingRectangle = boundingRectangle;
        this.key = key;
    }
}