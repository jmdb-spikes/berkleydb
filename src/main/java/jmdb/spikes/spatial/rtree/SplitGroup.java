package jmdb.spikes.spatial.rtree;

import java.util.ArrayList;
import java.util.List;

import static jmdb.spikes.spatial.rtree.Rectangle.calculateBoundingRectangle;

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

    public Rectangle getBoundingRectangle() {
        return calculateBoundingRectangle(boundingRectangles);
    }

}