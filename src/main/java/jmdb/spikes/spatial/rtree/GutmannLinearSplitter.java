package jmdb.spikes.spatial.rtree;

import java.util.List;

class GutmannLinearSplitter implements NodeSplitter {

    private GutmannAlgorithm gutmannAlgorithm;


    public GutmannLinearSplitter(GutmannAlgorithm gutmannAlgorithm) {
        this.gutmannAlgorithm = gutmannAlgorithm;
    }

    public Split split(List<IndexEntry> entries) {
        SplitSeeds seeds = gutmannAlgorithm.pickSeeds(entries);

        SplitGroup group1 = new SplitGroup();
        SplitGroup group2 = new SplitGroup();

        group1.addEntry(seeds.seedA);
        group2.addEntry(seeds.seedB);

        while (gutmannAlgorithm.hasNextEntry()) {
            IndexEntry E = gutmannAlgorithm.nextEntry();

            gutmannAlgorithm.selectGroupToAddTo(group1, group2, E)
                            .addEntry(E);
        }


        return new Split(group1, group2);
    }

}