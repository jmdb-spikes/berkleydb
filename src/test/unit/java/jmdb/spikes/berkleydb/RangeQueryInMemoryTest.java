package jmdb.spikes.berkleydb;

import jmdb.spikes.platform.ArrayManipulation;
import jmdb.spikes.platform.FloatingPointMaths;
import org.junit.Test;

import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.lang.System.out;
import static java.util.Arrays.copyOfRange;
import static jmdb.spikes.berkleydb.RangeQueryInMemoryTest.Box.box;
import static jmdb.spikes.berkleydb.RangeQueryInMemoryTest.Point.point;
import static jmdb.spikes.platform.FloatingPointMaths.greater_or_equal;
import static jmdb.spikes.platform.FloatingPointMaths.lessthan_or_equal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * A simple in memory test of the theory behind finding points within a radius which we can then translate into
 * berkeleydb code.
 */
public class RangeQueryInMemoryTest {

    @Test
    public void find_a_range() {

        float[] values = new float[]{0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.712f, 0.8f, 0.9f, 1.0f};

        Float[] range = FloatingPointMaths.range_from(values, 0.2f, 0.7f, 0.1f);

        out.println("Range is (precision 0.1) : " + ArrayManipulation.printArray(range));

        assertThat(range.length, is(6));

        range = FloatingPointMaths.range_from(values, 0.2f, 0.7f, 0.01f);
        out.println("Range is (precision 0.01) : " + ArrayManipulation.printArray(range));

        assertThat(range.length, is(5));

    }

    @Test
    public void specific_test() {
        assertThat(FloatingPointMaths.is_between(0.712f, 0.2f, 0.7f, 0.1f), is(true));
    }

    @Test
    public void specific_test_b() {
        assertThat(FloatingPointMaths.is_between(0.7f, 0.4f, 0.6f, 0.01f), is(false));
    }


    @Test
    public void greater_than_or_equal_test() {

        float a = 0.1f;
        float b = 0.123f;
        float c = 0.6f;
        float d = 0.01f;
        float e = 0.2f;
        float f = 0.11f;
        float g = 0.112f;

        assertThat(greater_or_equal(a, b, 0.1f), is(true));
        assertThat(greater_or_equal(a, c, 0.1f), is(false));
        assertThat(greater_or_equal(c, a, 0.1f), is(true));
        assertThat(greater_or_equal(a, d, 0.1f), is(true));
        assertThat(greater_or_equal(a, b, 0.01f), is(false));
        assertThat(greater_or_equal(a, e, 0.1f), is(false));
        assertThat(greater_or_equal(e, a, 0.1f), is(true));
        assertThat(greater_or_equal(a, f, 0.1f), is(true));
        assertThat(greater_or_equal(a, g, 0.1f), is(true));
    }

    @Test
    public void lessthan_or_equal_test() {

        float a = 0.1f;
        float b = 0.12f;
        float c = 0.6f;
        float d = 0.09f;
        float e = 0.2f;


        assertThat(lessthan_or_equal(a, b, 0.1f), is(true));
        assertThat(lessthan_or_equal(b, a, 0.1f), is(true));
        assertThat(lessthan_or_equal(a, b, 0.01f), is(true));

        assertThat(lessthan_or_equal(a, c, 0.1f), is(true));
        assertThat(lessthan_or_equal(c, a, 0.1f), is(false));
        assertThat(lessthan_or_equal(a, d, 0.1f), is(true));
        assertThat(lessthan_or_equal(a, e, 0.1f), is(true));
        assertThat(lessthan_or_equal(e, a, 0.1f), is(true));

    }

    @Test
    public void demonstrate_precision() {

        float a = 0.123456f;
        float b = 0.1234f;

        out.println(format("a=%f, b=%f", a, b));

        float diff_a_b = a - b;

        out.println(format("dif=%f", diff_a_b));

        if (diff_a_b < 0.0001) {
            out.println("numbers are equal to within 0.0001 d.p.");
        }

    }

    @Test
    public void equals_to_precision() {
        float a = 0.123456f;
        float b = 0.1234f;

        assertThat(FloatingPointMaths.float_eq(a, b, 0.0001f), is(true));
        assertThat(FloatingPointMaths.float_eq(a, b, 0.001f), is(true));
        assertThat(FloatingPointMaths.float_eq(a, b, 0.00001f), is(false));

    }

    static class Box {
        public final Point bottomLeft;
        public final Point bottomRight;
        public final Point topLeft;
        public final Point topRight;

        private Box(Point bottomLeft, Point bottomRight, Point topLeft, Point topRight) {
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
            this.topLeft = topLeft;
            this.topRight = topRight;
        }

        public static final Box box(Point bottomLeft, Point bottomRight, Point topLeft, Point topRight) {
            return new Box(bottomLeft, bottomRight, topLeft, topRight);
        }
    }

    static class Point {
        public final float x;
        public final float y;

        private Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public static Point point(float x, float y) {
            return new Point(x, y);
        }

        public boolean isInBox(Box box, float precision) {
            return FloatingPointMaths.is_between(x, box.bottomLeft.x, box.topRight.x, precision)
                    && FloatingPointMaths.is_between(y, box.bottomLeft.y, box.topRight.y, precision);
        }
    }

    @Test
    public void find_points_in_a_box() {

        Point p1 = point(0.5f, 0.5f);
        Point p2 = point(0.7f, 0.7f);

        Box b = box(point(0.4f, 0.4f), point(0.6f, 0.4f),
                    point(0.4f, 0.6f), point(0.6f, 0.6f));

        assertThat(p1.isInBox(b, 0.01f), is(true));
        assertThat(p2.isInBox(b, 0.01f), is(false));

    }

}