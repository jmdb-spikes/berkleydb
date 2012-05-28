package jmdb.spikes.berkleydb.spatial.rtree;

import org.junit.Test;

import java.io.File;

import static jmdb.spikes.berkleydb.spatial.rtree.CartesianSketchPad.createCartesianSketchPad;

public class RTreeTest {

    private static final String OUTPUT_DIR = System.getProperty("user.home") + "/tmp/spatial-tests";

    @Test
    public void build_and_show_some_points() {
        CartesianSketchPad sketch = createCartesianSketchPad(100, 100);

        sketch.printTo(new File(OUTPUT_DIR, "simple_sketch.png"));


    }
}