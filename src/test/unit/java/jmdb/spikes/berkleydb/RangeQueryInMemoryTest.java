package jmdb.spikes.berkleydb;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.System.out;
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

        Float[] range = range_from(values, 0.2f, 0.7f, 0.1f);

        out.println("range is : " + printArray(range));

    }

    private static String printArray(Object[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<array.length;++i) {
            sb.append(array[i]);
            if (i < array.length-1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private static Float[] range_from(float[] input,
                                      float from, float to,
                                      float precision) {

        List<Float> result = new ArrayList<Float>();

        for (int i = 0; i < input.length; ++i) {
            if (is_between(input[i], from, to, precision)) {
                result.add(input[i]);
            }
        }

        return result.toArray(new Float[]{});
    }

    private static boolean is_between(float subject,
                                      float from, float to,
                                      float precision) {

        if (subject >= from
                && subject <= to) {
            return true;
        }
        return false;
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

        assertThat(float_eq(a, b, 0.0001f), is(true));
        assertThat(float_eq(a, b, 0.001f), is(true));
        assertThat(float_eq(a, b, 0.00001f), is(false));

    }

    private static boolean float_eq(float a, float b, float precision) {
        float diff_a_b = a - b;
        if (diff_a_b < precision) {
            return true;
        }
        return false;
    }


}