package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.entity.Point;
import org.jazzteam.eltay.gasimov.service.impl.FindTriangleParametersServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

        Assertions.assertEquals(expected, actual, 0.001);
    }
}