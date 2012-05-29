package jmdb.spikes.spatial.rtree;

public class LeafNode extends Node {
    public LeafNode(String s) {

    }

    @Override public boolean isLeaf() {
        return true;
    }
}