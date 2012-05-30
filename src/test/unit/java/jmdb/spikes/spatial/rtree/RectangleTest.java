package jmdb.spikes.spatial.rtree;

import org.junit.Test;

import static jmdb.spikes.platform.IsCloseToFloat.closeTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RectangleTest {

    @Test
    public void can_calculate_area() {
        Rectangle r = new Rectangle(10, 10, 20, 20);

        float area = r.area;

        assertThat(area,  closeTo(100, 0.001f));
    }
}