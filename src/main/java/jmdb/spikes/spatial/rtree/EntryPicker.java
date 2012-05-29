package jmdb.spikes.spatial.rtree;

import java.util.List;

interface EntryPicker {

    SplitSeeds pickSeeds(List<IndexEntry> entries);


    boolean hasNextEntry();

    IndexEntry nextEntry();
}