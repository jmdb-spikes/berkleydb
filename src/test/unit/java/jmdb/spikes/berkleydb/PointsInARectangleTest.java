package jmdb.spikes.berkleydb;

import org.junit.Test;

import static jmdb.spikes.berkleydb.PointsInARectangleTest.Rectangle.rectangle;
import static jmdb.spikes.berkleydb.PointsInARectangleTest.Point.point;
import static jmdb.spikes.platform.FloatingPointMaths.is_between;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PointsInARectangleTest {
    @Test
    public void find_points_in_a_rectangle() {

        Point p1 = point(0.5f, 0.5f);
        Point p2 = point(0.7f, 0.7f);

        Rectangle b = rectangle(point(0.4f, 0.4f), point(0.6f, 0.4f),
                          point(0.4f, 0.6f), point(0.6f, 0.6f));

        assertThat(p1.isInRectangle(b, 0.01f), is(true));
        assertThat(p2.isInRectangle(b, 0.01f), is(false));

    }

    static class Rectangle {
        public final Point bottomLeft;
        public final Point bottomRight;
        public final Point topLeft;
        public final Point topRight;

        private Rectangle(Point bottomLeft, Point bottomRight, Point topLeft, Point topRight) {
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
            this.topLeft = topLeft;
            this.topRight = topRight;
        }

        public static final Rectangle rectangle(Point bottomLeft, Point bottomRight, Point topLeft, Point topRight) {
            return new Rectangle(bottomLeft, bottomRight, topLeft, topRight);
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

        public boolean isInRectangle(Rectangle rectangle, float precision) {
            return is_between(x, rectangle.bottomLeft.x, rectangle.topRight.x, precision)
                    && is_between(y, rectangle.bottomLeft.y, rectangle.topRight.y, precision);
        }
    }
}