package service;

import entity.Point;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.FindTriangleParametersServiceImpl;

import java.util.stream.Stream;

public class FindServiceTest {
    private static Stream<Arguments> pointsToTest() {
        return Stream.of(
                Arguments.of(new Point(1, 1), new Point(1, 8)));
    }

    @ParameterizedTest
    @MethodSource("pointsToTest")
    public void findSide(Point point1, Point point2) {
        double expected = 7;
        FindTriangleParametersService findService = new FindTriangleParametersServiceImpl();
        double actual = findService.findSide(point1, point2);

        Assert.assertEquals(expected, actual, 0.001);
    }
}