package jmdb.spikes.spatial.rtree;

import java.util.List;

import static java.lang.Math.abs;
import static jmdb.spikes.platform.FloatingPointMaths.float_eq;

public class LinearCostAlgorithm implements GutmannAlgorithm {
    public SplitSeeds pickSeeds(List<IndexEntry> entries) {
        return null;
    }

    public boolean hasNextEntry() {
        return false;
    }

    public IndexEntry nextEntry() {
        return null;
    }

    public SplitGroup selectGroupToAddTo(SplitGroup group1, SplitGroup group2, IndexEntry e) {
        SplitGroup groupToAddTo; float bounds1 = group1.calculateBoundingRectangle().area;
        float bounds2 = group2.calculateBoundingRectangle().area;

        float newBounds1 = group1.calculateBoundingRectangleIfAdd(e).area;
        float newBounds2 = group2.calculateBoundingRectangleIfAdd(e).area;

        float diff1 = abs(bounds1 - newBounds1);
        float diff2 = abs(bounds2 - newBounds2);


        if (float_eq(diff1, diff2, 0.0001f)) {
            groupToAddTo = (bounds1 < bounds2) ? group1 : group2;
            // then one with fewer entries
            // then either
        } else if (diff1 > diff2) {
            groupToAddTo = group2;
        } else {
            groupToAddTo = group1;
        } return groupToAddTo;
    }
}