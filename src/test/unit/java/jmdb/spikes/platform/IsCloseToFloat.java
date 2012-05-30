package jmdb.spikes.platform;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCloseToFloat extends TypeSafeMatcher<Float> {
    private final float delta;
    private final float value;

    public IsCloseToFloat(float value, float error) {
        this.delta = error;
        this.value = value;
    }

    @Override
    public boolean matchesSafely(Float item) {
        return actualDelta(item) <= 0.0;
    }

    @Override
    public void describeMismatchSafely(Float item, Description mismatchDescription) {
        mismatchDescription.appendValue(item)
                           .appendText(" differed by ")
                           .appendValue(actualDelta(item));
    }

    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
                   .appendValue(delta)
                   .appendText(" of ")
                   .appendValue(value);
    }

    private double actualDelta(Float item) {
        return (Math.abs((item - value)) - delta);
    }


    @Factory
    public static Matcher<Float> closeTo(float operand, float error) {
        return new IsCloseToFloat(operand, error);
    }


}