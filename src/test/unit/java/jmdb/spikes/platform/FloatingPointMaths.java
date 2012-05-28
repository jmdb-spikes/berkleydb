package jmdb.spikes.platform;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class FloatingPointMaths {
    public static final float TO_1_DP = 0.01f;

    static boolean float_eq(float a, float b, float precision) {
        float diff_a_b = a - b;
        if (diff_a_b < precision) {
            return true;
        }
        return false;
    }

    static boolean greater_or_equal(float a,
                                    float b, float precision) {
        float diff = b - a;

        if (diff >= 0 && diff < precision) {  // equal
            return true;
        }

        if (a >= b) {
            return true;
        }

        return false;
    }

    static boolean lessthan_or_equal(float a,
                                     float b, float precision) {
        float diff = a - b;

        if (abs(diff) <= precision) {  // equal
            return true;
        }

        if (a <= b) {
            return true;
        }

        return false;
    }

    public static boolean is_between(float subject,
                              float from, float to,
                              float precision) {


        if (greater_or_equal(subject, from, precision)
                && lessthan_or_equal(subject, to, precision)) {
            return true;
        }
        return false;
    }

    public static Float[] range_from(float[] input,
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
}