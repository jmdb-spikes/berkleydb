package jmdb.spikes.spatial.rtree;

import java.util.ArrayList;
import java.util.List;

public class SplitGroup {

    List<IndexEntry> entries = new ArrayList<IndexEntry>();

    public void addEntry(IndexEntry indexEntry) {
        entries.add(indexEntry);
    }

    public Rectangle getBoundingRectangle() {
        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;

        float minY = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;

        for (IndexEntry e : entries) {
            Rectangle r = e.rectangle;
            minX = (r.x1 < minX) ? r.x1 : minX;
            maxX = (r.x2 > maxX) ? r.x2 : maxX;

            minY = (r.y1 < minY) ? r.y1 : minY;
            maxY = (r.y2 > maxY) ? r.y2 : maxY;
        }
        return new Rectangle(minX, minY, maxX, maxY);
    }
}