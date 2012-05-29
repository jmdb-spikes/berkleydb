package jmdb.spikes.spatial.rtree;

import java.util.List;

interface NodeSplitter {
    Split split(List<IndexEntry> entries);
}