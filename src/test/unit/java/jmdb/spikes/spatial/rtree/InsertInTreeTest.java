package jmdb.spikes.spatial.rtree;

import jmdb.spikes.platform.MessageRuntimeException;
import org.junit.Test;

import static jmdb.spikes.spatial.rtree.NodeFactory.asLeafNode;

public class InsertInTreeTest {

    @Test
    public void insert_into_empty_leaf() {
        NodeFactory nodeFactory = new NodeFactory()
                .minEntries(2)
                .maxEntries(3);

        Node root = nodeFactory.createRootNode();

        IndexEntry E = new IndexEntry(new BoundingRectangle(20, 20, 20, 20), "UID-A");


        LeafNode L = chooseLeafNode(root);

        if (L.hasFreeCapacity()) {
            L.installEntry(E);
        }

    }

    private static LeafNode chooseLeafNode(Node root) {
        if (root.isLeaf()) {
            return asLeafNode(root);
        }
        throw new MessageRuntimeException("Could not find a leaf node");
    }

}