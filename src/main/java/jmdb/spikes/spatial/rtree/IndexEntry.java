package jmdb.spikes.spatial.rtree;

public class IndexEntry {

    public final Rectangle rectangle;
    public final String key;

    public IndexEntry(Rectangle rectangle, String key) {

        this.rectangle = rectangle;
        this.key = key;
    }
}