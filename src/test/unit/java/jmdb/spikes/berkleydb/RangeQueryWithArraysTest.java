package jmdb.spikes.berkleydb;

import jmdb.spikes.platform.ArrayManipulation;
import jmdb.spikes.platform.FloatingPointMaths;
import jmdb.spikes.platform.ListManipulation;
import org.junit.Test;

import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.lang.System.out;
import static java.util.Arrays.copyOfRange;
import static jmdb.spikes.berkleydb.RangeQueryInMemoryTest.Point.point;
import static jmdb.spikes.platform.ArrayManipulation.select_subset;
import static jmdb.spikes.platform.ListManipulation.printList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RangeQueryWithArraysTest {

    @Test
    public void find_a_range_in_two_independant_arrays() {
        float[] x_arr = {0.2f, 0.3f, 0.3f, 0.4f, 0.4f, 0.5f};
        float[] y_arr = {0.2f, 0.3f, 0.6f, 0.4f, 0.7f, 0.5f};

        RangeQueryInMemoryTest.Point bottomLeft = point(0.25f, 0.25f);
        RangeQueryInMemoryTest.Point topRight = point(0.45f, 0.45f);

        int[] xIndexesInRange = ArrayManipulation.indexes_in_range(x_arr, bottomLeft.x, topRight.x, FloatingPointMaths.TO_1_DP);
        float[] ySubset = select_subset(y_arr, xIndexesInRange);
        int[] yIndexesInRangeAndSubset = ArrayManipulation.indexes_in_range(ySubset, bottomLeft.x, topRight.x, FloatingPointMaths.TO_1_DP);

        float[] xInRange = select_subset(x_arr, yIndexesInRangeAndSubset);
        float[] yInRange = select_subset(ySubset, yIndexesInRangeAndSubset);

        out.println("Results In Box:");
        out.println(format("x: %s", printList(ArrayManipulation.asList(xInRange))));
        out.println(format("y: %s", printList(ArrayManipulation.asList(yInRange))));

        assertThat(xInRange.length, is(yInRange.length));
        assertThat(xInRange.length, is(2));

    }

}