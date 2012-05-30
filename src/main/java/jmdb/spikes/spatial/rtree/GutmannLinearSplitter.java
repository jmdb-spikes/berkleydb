package jmdb.spikes.spatial.rtree;

import javax.swing.*;
import java.util.List;

import static java.lang.Math.abs;
import static jmdb.spikes.platform.FloatingPointMaths.float_eq;

class GutmannLinearSplitter implements NodeSplitter {

    private EntryPicker entryPicker;

    public GutmannLinearSplitter(EntryPicker entryPicker) {
        this.entryPicker = entryPicker;
    }

    public Split split(List<IndexEntry> entries) {
        SplitSeeds seeds = entryPicker.pickSeeds(entries);

        SplitGroup group1 = new SplitGroup();
        SplitGroup group2 = new SplitGroup();

        group1.addEntry(seeds.seedA);
        group2.addEntry(seeds.seedB);

        while (entryPicker.hasNextEntry()) {
            IndexEntry E = entryPicker.nextEntry();

            float bounds1 = group1.calculateBoundingRectangle().area;
            float bounds2 = group2.calculateBoundingRectangle().area;

            float newBounds1 = group1.calculateBoundingRectangleIfAdd(E).area;
            float newBounds2 = group2.calculateBoundingRectangleIfAdd(E).area;

            float diff1 = abs(bounds1 - newBounds1);
            float diff2 = abs(bounds2 - newBounds2);

            SplitGroup groupToAddTo = null;
            if (float_eq(diff1, diff2, 0.0001f)) {
                groupToAddTo = (bounds1 < bounds2) ? group1 : group2;
                // then one with fewer entries
                // then either
            } else if (diff1 > diff2) {
                groupToAddTo = group2;
            } else {
                groupToAddTo = group1;
            }

            groupToAddTo.addEntry(E);
        }


        return new Split(group1, group2);
    }
}