package jmdb.spikes.spatial.rtree;

class IndexNode extends Node {


    public final long id;
    public final BoundingRectangle boundingRectangle;


    IndexNode(long id, BoundingRectangle boundingRectangle) {
        this.id = id;
        this.boundingRectangle = boundingRectangle;
    }


    @Override public boolean isLeaf() {
        return false;
    }
}