package jmdb.spikes.spatial.rtree;

import java.util.List;

class Split {
    public final List<IndexEntry> group1;
    public final List<IndexEntry> group2;

    public Split(List<IndexEntry> group1, List<IndexEntry> group2) {
        this.group1 = group1;
        this.group2 = group2;
    }
}