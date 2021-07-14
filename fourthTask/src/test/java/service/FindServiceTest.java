package service;

import entity.Point;
import org.junit.Assert;
import org.junit.Test;
import service.impl.FindTriangleParametersServiceImpl;

public class FindServiceTest {
    private static final Point[] TEST_POINTS = new Point[]{
            new Point(1, 1),
            new Point(1, 8)
    };

    @Test
    public void findSide() {
        double expected = 7;
        FindTriangleParametersService findService = new FindTriangleParametersServiceImpl();
        double actual = findService.findSide(TEST_POINTS[0], TEST_POINTS[1]);

        Assert.assertEquals(expected, actual, 0.001);
    }
}