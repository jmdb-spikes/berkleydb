package jmdb.spikes.spatial.rtree;

public class LeafNode extends Node {
    public LeafNode(String s) {

    }

    public LeafNode(int minNumEntries, int maxNumEntries) {

    }

    @Override public boolean isLeaf() {
        return true;
    }

    public void installEntry(IndexEntry e) {

    }

    public boolean hasFreeCapacity() {
        return false;
    }
}