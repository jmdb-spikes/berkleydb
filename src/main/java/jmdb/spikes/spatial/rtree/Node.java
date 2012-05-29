package jmdb.spikes.spatial.rtree;

import java.util.ArrayList;
import java.util.List;

abstract class Node {

    private final List<Node> entries = new ArrayList<Node>();

    public abstract boolean isLeaf();
}