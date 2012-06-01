package jmdb.spikes.spatial.rtree;

import java.util.List;

interface GutmannAlgorithm {

    SplitSeeds pickSeeds(List<IndexEntry> entries);


    boolean hasNextEntry();

    IndexEntry nextEntry();

    SplitGroup selectGroupToAddTo(SplitGroup group1, SplitGroup group2, IndexEntry nextEntry);
}