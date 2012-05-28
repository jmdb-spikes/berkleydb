package jmdb.spikes.platform;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.copyOfRange;

public class ArrayManipulation {

    // Prevent construction
    private ArrayManipulation(){
    };

    public static float[] select_subset(float[] input, int[] indexesToSelect) {
        float[] selectedValues = new float[indexesToSelect.length];

        for (int i = 0; i < indexesToSelect.length; ++i) {
            selectedValues[i] = input[indexesToSelect[i]];
        }

        return selectedValues;
    }

    public static int[] indexes_in_range(float[] input,
                                         float from, float to,
                                         float precision) {

        int[] indexes = new int[input.length];
        int countOfIndexesInRange = 0;

        for (int i = 0; i < input.length; ++i) {
            if (FloatingPointMaths.is_between(input[i], from, to, precision)) {
                indexes[countOfIndexesInRange] = i;
                countOfIndexesInRange++;
            }
        }

        return copyOfRange(indexes, 0, countOfIndexesInRange);
    }

    public static List<Object> asList(float[] floats) {
        ArrayList<Object> list = new ArrayList<Object>(floats.length);
        for (float f : floats) {
            list.add(f);
        }
        return list;
    }

    public static List<Object> asList(int[] ints) {
        ArrayList<Object> list = new ArrayList<Object>(ints.length);
        for (int i : ints) {
            list.add(i);
        }
        return list;
    }

    public static String printArray(Object[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }


}