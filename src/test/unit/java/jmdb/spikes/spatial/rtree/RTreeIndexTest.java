package jmdb.spikes.spatial.rtree;

import jmdb.spikes.spatial.visualisation.CartesianSketchPad;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jmdb.spikes.spatial.visualisation.CartesianSketchPad.createCartesianSketchPad;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class RTreeIndexTest {

    private static final String OUTPUT_DIR = System.getProperty("user.home") + "/tmp/spatial-tests";

    private RTreeIndex rTreeIndex;
    private Map<String, Place> mapOfPlaces;


    @Ignore("WIP - JMDB 29-05-2012")
    @Test
    public void build_an_rtree() {
        mapOfPlaces = createMapOfPlaces(
                place("A", "Point A", 2, 2),
                place("B", "Point B", 10, 10),
                place("C", "Point C", 20, 20),
                place("D", "Point D", 15, 12),
                place("E", "Point E", 20, 20));


        rTreeIndex = buildRTreeIndex(mapOfPlaces);

        List<String> keys = rTreeIndex.findObjectsInRectangle(10, 10, 20, 20);

        assertThat(keys.size(), is(3));
        assertThat(keys, contains("B"));
        assertThat(keys, contains("C"));
        assertThat(keys, contains("D"));

    }

    private static RTreeIndex buildRTreeIndex(Map<String, Place> mapOfPlaces) {
        RTreeIndex index = new RTreeIndex();

        for (Place p : mapOfPlaces.values()) {
            index.insertObjectWithKey(p.key).atPoint(p.lattitude, p.longitude);
        }

        return index;
    }

    private static Map<String, Place> createMapOfPlaces(Place... places) {
        Map<String, Place> mapOfPlaces = new HashMap<String, Place>();
        for (Place p : places) {
            mapOfPlaces.put(p.key, p);
        }
        return mapOfPlaces;
    }

    private static Place place(String key, String name, int x, int y) {
        return new Place(key, name, x, y);
    }

    private static class Place {

        public final String key;
        public final String name;
        public final int longitude;
        public final int lattitude;

        public Place(String key, String name, int longitude, int lattitude) {
            this.key = key;
            this.name = name;
            this.longitude = longitude;
            this.lattitude = lattitude;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("{\n")
              .append("  \"is\" : \"place\",\n")
              .append("  \"key\" : \"").append(key).append("\",\n")
              .append("  \"name\" : \"").append(name).append("\",\n")
              .append("  \"lattitude\" : \"").append(longitude).append("\",\n")
              .append("  \"longitude\"  : \"").append(lattitude).append("\",\n")
              .append("}\n");

            return sb.toString();
        }


    }

    @Test
    public void build_and_show_some_points() {
        CartesianSketchPad sketch = createCartesianSketchPad(100, 100).percentZoom(1000);

        sketch.addPoint(0, 0)
              .addPoint(20, 20)
              .addPoint(100, 100)
              .addPoint(50, 50)
              .addPoint(0, 100)
              .addPoint(100, 0);

        sketch.addBoundingRectangle(0, 0, 20, 20);
        sketch.addBoundingRectangle(20, 20, 50, 50);
        sketch.addBoundingRectangle(50, 0, 100, 50);

        sketch.printTo(new File(OUTPUT_DIR, "simple_sketch.png"));
    }
}