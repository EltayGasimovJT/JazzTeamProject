package service;

import entity.Point;
import entity.Triangle;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TriangleServiceTest {
    private static Stream<Arguments> isTriangleTestData() {
        Triangle firstTriangle = new Triangle(
                new Point(1, 1),
                new Point(1, 8),
                new Point(7, 1)
        );

        Triangle secondTriangle = new Triangle(
                new Point(0, 0),
                new Point(0, 0),
                new Point(0, 0)
        );

        Triangle thirdTriangle = new Triangle(
                new Point(-5, -1),
                new Point(-2, 1),
                new Point(4, -2)
        );

        return Stream.of(
                Arguments.of(firstTriangle, true),
                Arguments.of(secondTriangle, false),
                Arguments.of(thirdTriangle, true)
        );
    }

    private static Stream<Arguments> trianglesToTest() {
        Triangle firstTriangle = new Triangle(
                new Point(1, 1),
                new Point(1, 8),
                new Point(7, 1)
        );

        Triangle secondTriangle = new Triangle(
                new Point(0, 0),
                new Point(0, 0),
                new Point(0, 0)
        );

        Triangle thirdTriangle = new Triangle(
                new Point(-5, -1),
                new Point(-2, 1),
                new Point(4, -2)
        );

        return Stream.of(
                Arguments.of(firstTriangle, new Point(1, 4), true),
                Arguments.of(secondTriangle, new Point(0, 0), false),
                Arguments.of(thirdTriangle, new Point(0, 0), true)
        );
    }

    @ParameterizedTest
    @MethodSource("isTriangleTestData")
    public void isTriangleTest(Triangle triangle, boolean expected) {
        boolean actual = TriangleService.isTriangle(triangle);
        Assert.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("trianglesToTest")
    public void checkIfPointBelongsToTriangleTest(Triangle triangle, Point point) {

        boolean actual = TriangleService.isBelongsToTriangle(point, triangle);

        Assert.assertEquals(actual, true);
    }
}