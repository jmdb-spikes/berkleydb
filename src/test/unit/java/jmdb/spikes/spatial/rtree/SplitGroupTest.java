package jmdb.spikes.spatial.rtree;

import jmdb.spikes.spatial.visualisation.CartesianSketchPad;
import org.junit.Test;

import java.awt.*;
import java.io.File;

import static java.lang.System.getProperty;
import static jmdb.spikes.platform.IsCloseToFloat.closeTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SplitGroupTest {

    @Test
    public void can_tell_us_the_covering_rectangle_area() {
        SplitGroup group = new SplitGroup();

        IndexEntry E1 = indexEntry(10, 10, 20, 20);
        IndexEntry E2 = indexEntry(15, 15, 20, 25);
        IndexEntry E3 = indexEntry(25, 5, 30, 20);

        group.addEntry(E1);
        group.addEntry(E2);
        group.addEntry(E3);

        Rectangle r = group.getBoundingRectangle();

        debugToSketchPad(methodName(this), 40, 40, r, E1, E2, E3);

        assertThat(r.x1, closeTo(10, 0.001f));
        assertThat(r.y1, closeTo(5, 0.001f));
        assertThat(r.x2, closeTo(30, 0.001f));
        assertThat(r.y2, closeTo(25, 0.001f));

    }

    private static String methodName(Object source) {
        String className = source.getClass().getSimpleName();

        Exception e = new Exception();
        StackTraceElement[] stackTrace = e.getStackTrace();
        String methodName = stackTrace[1].getMethodName();

        return className + "_" + methodName;
    }

    private static void debugToSketchPad(String name, int width, int height, Rectangle bounds, IndexEntry... indexEntries) {
        CartesianSketchPad sketchPad = new CartesianSketchPad(width, height).percentZoom(1000);

        sketchPad.addBoundingRectangle(bounds.x1, bounds.y1, bounds.x2, bounds.y2, Color.GREEN);

        for (IndexEntry e : indexEntries) {
            Rectangle r = e.rectangle;
            sketchPad.addBoundingRectangle(r.x1, r.y1, r.x2, r.y2);
        }

        sketchPad.printTo(new File(getProperty("user.home") + "/tmp/test-images/" + name + ".png"));
    }

    private static IndexEntry indexEntry(int x1, int y1, int x2, int y2) {
        return new IndexEntry(new Rectangle(x1, y1, x2, y2), "UID-E1");
    }
}