package jmdb.spikes.spatial.rtree;

class IndexNode extends Node {


    public final long id;
    public final Rectangle rectangle;


    IndexNode(long id, Rectangle rectangle) {
        this.id = id;
        this.rectangle = rectangle;
    }


    @Override public boolean isLeaf() {
        return false;
    }
}