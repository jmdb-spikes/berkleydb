package jmdb.spikes.spatial.rtree;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LeafNodeTest {

    @Test
    public void knows_its_a_leaf() {
        assertThat(new LeafNode(0, 0).isLeaf(), is(true));
    }

    @Test
    public void has_a_capacity_for_entries() {

        LeafNode L = new LeafNode(2, 3);



    }
}