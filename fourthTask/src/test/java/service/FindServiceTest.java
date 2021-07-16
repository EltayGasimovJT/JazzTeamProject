package service;

import entity.Point;
import entity.Triangle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.FindTriangleParametersServiceImpl;

import java.util.stream.Stream;

public class FindServiceTest {
    private static final FindTriangleParametersService findService = new FindTriangleParametersServiceImpl();
    private static Stream<Arguments> pointsToTest() {
        return Stream.of(
                Arguments.of(new Point(1, 1), new Point(1, 8), 7),
                Arguments.of(new Point(6, 1), new Point(6, 4), 3),
                Arguments.of(new Point(0, 0), new Point(0, 0), 0),
                Arguments.of(new Point(6, -5), new Point(6, -1), 4)

        );
    }

    @ParameterizedTest
    @MethodSource("pointsToTest")
    public void findSide(Point firstPoint, Point secondPoint, double expected) {
        double actual = findService.findSide(firstPoint, secondPoint);

        Assert.assertEquals(expected, actual, 0.001);
    }
}