package jmdb.spikes.spatial.rtree;

import java.util.List;

class Split {
    public final SplitGroup group1;
    public final SplitGroup group2;

    public Split(SplitGroup group1, SplitGroup group2) {
        this.group1 = group1;
        this.group2 = group2;
    }
}