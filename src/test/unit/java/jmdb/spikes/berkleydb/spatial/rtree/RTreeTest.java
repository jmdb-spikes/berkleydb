package jmdb.spikes.berkleydb.spatial.rtree;

import org.junit.Test;

import java.io.File;

import static jmdb.spikes.berkleydb.spatial.rtree.CartesianSketchPad.createCartesianSketchPad;

public class RTreeTest {

    private static final String OUTPUT_DIR = System.getProperty("user.home") + "/tmp/spatial-tests";

    @Test
    public void build_and_show_some_points() {
        CartesianSketchPad sketch = createCartesianSketchPad(100, 100).percentZoom(1000);

        sketch.addPoint(1, 1)
              .addPoint(20, 20)
              .addPoint(100, 100)
              .addPoint(50, 50)
              .addPoint(1, 100)
              .addPoint(100, 1);

        sketch.addBoundingRectangle(1, 1, 20, 20);


        sketch.printTo(new File(OUTPUT_DIR, "simple_sketch.png"));


    }
}