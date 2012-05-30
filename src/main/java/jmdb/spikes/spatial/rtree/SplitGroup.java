package jmdb.spikes.spatial.rtree;

import java.util.ArrayList;
import java.util.List;


public class SplitGroup {

    private List<IndexEntry> entries = new ArrayList<IndexEntry>();
    private Rectangle[] boundingRectangles = new Rectangle[0];

    public void addEntry(IndexEntry indexEntry) {
        entries.add(indexEntry);
        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        for (IndexEntry e : entries) {
            rectangles.add(e.rectangle);
        }
        boundingRectangles = rectangles.toArray(new Rectangle[rectangles.size()]);
    }

    public Rectangle calculateBoundingRectangle() {
        return calculateBoundingRectangle(entries.toArray(new IndexEntry[entries.size()]));
    }

    public static Rectangle calculateBoundingRectangle(IndexEntry... indexEntries) {
        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;

        float minY = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;

        for (IndexEntry e : indexEntries) {
            Rectangle r = e.rectangle;
            minX = (r.x1 < minX) ? r.x1 : minX;
            maxX = (r.x2 > maxX) ? r.x2 : maxX;

            minY = (r.y1 < minY) ? r.y1 : minY;
            maxY = (r.y2 > maxY) ? r.y2 : maxY;
        }
        return new Rectangle(minX, minY, maxX, maxY);
    }

    public Rectangle calculateBoundingRectangleIfAdd(IndexEntry newIndexEntry) {
        List<IndexEntry> expandedEntries = new ArrayList<IndexEntry>(entries);
        expandedEntries.add(newIndexEntry);
        return calculateBoundingRectangle(expandedEntries.toArray(new IndexEntry[entries.size() + 1]));
    }

    public int size() {
        return entries.size();
    }

    public IndexEntry get(int i) {
        return entries.get(i);
    }
}