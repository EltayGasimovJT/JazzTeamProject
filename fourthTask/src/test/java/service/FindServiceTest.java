package service;

import entity.Point;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.FindTriangleParametersServiceImpl;

import java.util.stream.Stream;

public class FindServiceTest {
    private static final Point[] TEST_POINTS = new Point[]{
            new Point(1, 1),
            new Point(1, 8)
    };

    @ParameterizedTest
    @MethodSource("pointsToTest")
    public void findSide() {
        double expected = 7;
        FindTriangleParametersService findService = new FindTriangleParametersServiceImpl();
        double actual = findService.findSide(TEST_POINTS[0], TEST_POINTS[1]);

        Assert.assertEquals(expected, actual, 0.001);
    }
}