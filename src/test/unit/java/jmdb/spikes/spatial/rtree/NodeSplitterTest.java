package jmdb.spikes.spatial.rtree;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Taken from Guttman http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.131.7887
 * Section 3.5.3
 */
public class NodeSplitterTest {

    /**
     * Divide a group of M+1 entries into two groups
     * M is the maximum number of entries that a leaf node can contain
     */
    @Test
    public void split_with_just_two_entries_M_2() {


        IndexEntry E1 = pointIndexEntry(12, 12);
        IndexEntry E2 = pointIndexEntry(20, 20);
        IndexEntry E3 = pointIndexEntry(11, 12);


        List<IndexEntry> entries = asList(E1, E2, E3);

        StubEntryPicker entryPicker = stubEntryPicker()
                .seeds(E1, E3)
                .nextEntries(E2);

        NodeSplitter splitter = new GutmannLinearSplitter(entryPicker);

        Split split = splitter.split(entries);

        assertThat(split.group1.size(), is(2));
        assertThat(split.group1.get(0), is(E1));
        assertThat(split.group1.get(1), is(E2));

        assertThat(split.group2.size(), is(1));
        assertThat(split.group2.get(0), is(E3));

    }

    private static StubEntryPicker stubEntryPicker() {
        return new StubEntryPicker();
    }

    private static class StubEntryPicker implements EntryPicker {

        private IndexEntry seedA;
        private IndexEntry seedB;
        private Iterator<IndexEntry> nextEntries;

        public SplitSeeds pickSeeds(List<IndexEntry> entries) {
            return new SplitSeeds(seedA, seedB);
        }

        public boolean hasNextEntry() {
            return nextEntries.hasNext();
        }

        public IndexEntry nextEntry() {
            return nextEntries.next();
        }


        public StubEntryPicker seeds(IndexEntry seedA, IndexEntry seedB) {
            this.seedA = seedA;
            this.seedB = seedB;
            return this;
        }

        public StubEntryPicker nextEntries(IndexEntry... nextEntries) {
            this.nextEntries = asList(nextEntries).iterator();
            return this;
        }
    }

    private static IndexEntry pointIndexEntry(int x, int y) {
        return new IndexEntry(new Rectangle(x, y, x, y), "UID-E1");
    }

}