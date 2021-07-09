package service;

import entity.Point;
import entity.Triangle;
import org.junit.Assert;
import org.junit.Test;

public class TriangleServiceTest {
    private static final Triangle TEST_TRIANGLE = new Triangle(
            new Point(1, 1),
            new Point(1, 8),
            new Point(7, 1)
    );

    @Test
    public void isTriangleTest() {
        boolean actual = TriangleService.isTriangle(TEST_TRIANGLE);

        Assert.assertEquals(actual, true);
    }

    @Test
    public void checkIfPointBelongsToTriangleTest() {
        Point point = new Point(1,4);
        boolean actual = TriangleService.checkIfPointBelongsToTriangle(point, TEST_TRIANGLE);

        Assert.assertEquals(actual, true);
    }
}