package jmdb.spikes.spatial.rtree;

import java.util.ArrayList;
import java.util.List;

public class LeafNode extends Node {

    private final List<IndexEntry> entries = new ArrayList<IndexEntry>();

    private final int maxNumEntries;

    public LeafNode(int maxNumEntries) {
        this.maxNumEntries = maxNumEntries;
    }

    @Override public boolean isLeaf() {
        return true;
    }

    public void installEntry(IndexEntry e) {
        entries.add(e);
    }

    public boolean hasFreeCapacity() {
        return entries.size() < maxNumEntries;
    }
}