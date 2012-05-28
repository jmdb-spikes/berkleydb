package jmdb.spikes.berkleydb.spatial.rtree;

import org.junit.Test;

import java.io.File;

import static jmdb.spikes.berkleydb.spatial.rtree.CartesianSketchPad.createCartesianSketchPad;

public class RTreeTest {

    private static final String OUTPUT_DIR = System.getProperty("user.home") + "/tmp/spatial-tests";

    @Test
    public void build_and_show_some_points() {
        CartesianSketchPad sketch = createCartesianSketchPad(100, 100).percentZoom(900);

        sketch.addPoint(0, 0)
              .addPoint(99, 99)
              .addPoint(50, 50)
              .addPoint(0, 99)
              .addPoint(99, 0);

        sketch.addBoundingRectangle(0, 0, 20, 20);


        sketch.printTo(new File(OUTPUT_DIR, "simple_sketch.png"));


    }
}