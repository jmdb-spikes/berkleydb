package jmdb.spikes.spatial.rtree;

import java.util.ArrayList;
import java.util.List;

class GutmannLinearSplitter implements NodeSplitter {

    private EntryPicker entryPicker;

    public GutmannLinearSplitter(EntryPicker entryPicker) {
        this.entryPicker = entryPicker;
    }

    public Split split(List<IndexEntry> entries) {
        SplitSeeds seeds = entryPicker.pickSeeds(entries);

        List<IndexEntry> group1 = new ArrayList<IndexEntry>();
        List<IndexEntry> group2 = new ArrayList<IndexEntry>();

        group1.add(seeds.seedA);
        group2.add(seeds.seedB);

        boolean flipflop = false;
        while (entryPicker.hasNextEntry()) {
            if (flipflop) {
                group1.add(entryPicker.nextEntry());
            } else {
                group2.add(entryPicker.nextEntry());
            }
            flipflop = (flipflop) ? false : true;
        }

        return new Split(group1, group2);
    }
}