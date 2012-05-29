package jmdb.spikes.spatial.rtree;

import jmdb.spikes.platform.MessageRuntimeException;

import static java.lang.String.format;

class NodeFactory {
    private int minNumberOfEntries = 2;
    private int maxNumberOfEntries = 3;

    public Node createRootNode() {
        return new LeafNode(maxNumberOfEntries);
    }



    public NodeFactory minEntries(int minNumberOfEntries) {
        this.minNumberOfEntries = minNumberOfEntries;
        return this;

    }

    public NodeFactory maxEntries(int maxNumberOfEntries) {
        this.maxNumberOfEntries = maxNumberOfEntries;
        return this;
    }

    public static LeafNode asLeafNode(Node node) {
        if (!node.isLeaf()) {
            throw new MessageRuntimeException("Tried to turn a non-leaf node into a leaf! [%s]", node);
        }
        return (LeafNode)node;
    }
}