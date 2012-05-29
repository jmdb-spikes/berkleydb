package jmdb.spikes.spatial.rtree;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LeafNodeTest {

    @Test
    public void knows_its_a_leaf() {
        assertThat(new LeafNode(0).isLeaf(), is(true));
    }

    @Test
    public void has_a_capacity_limit_for_entries() {
        LeafNode L = new LeafNode(3);

        assertThat(L.hasFreeCapacity(), is(true));

        L.installEntry(newEntry());
        L.installEntry(newEntry());

        assertThat(L.hasFreeCapacity(), is(true));

        L.installEntry(newEntry());

        assertThat(L.hasFreeCapacity(), is(false));

    }






    private static IndexEntry newEntry() {
        return new IndexEntry(null, null);
    }
}