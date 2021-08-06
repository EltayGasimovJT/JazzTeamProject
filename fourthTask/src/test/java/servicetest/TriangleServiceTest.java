package servicetest;

import entity.Point;
import entity.Triangle;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TriangleServiceTest {
    private static Stream<Arguments> isTriangleTestData() {
        return Stream.of(
                Arguments.of(new Triangle(
                        new Point(1, 1),
                        new Point(1, 8),
                        new Point(7, 1))),
                Arguments.of(new Triangle(
                        new Point(0, 0),
                        new Point(1, 6),
                        new Point(-1, -1))),
                Arguments.of(new Triangle(
                        new Point(-5, -1),
                        new Point(-2, 1),
                        new Point(4, -2)))
        );
    }

    private static Stream<Arguments> trianglesToTest() {
        return Stream.of(
                Arguments.of(new Triangle(
                        new Point(1, 1),
                        new Point(1, 8),
                        new Point(7, 1)
                ), new Point(1, 4), true),
                Arguments.of(new Triangle(
                        new Point(0, 0),
                        new Point(0, 0),
                        new Point(0, 0)
                ), new Point(0, 0), false),
                Arguments.of(new Triangle(
                        new Point(-5, -1),
                        new Point(-2, 1),
                        new Point(4, -2)
                ), new Point(0, 0), true)
        );
    }

    @ParameterizedTest(name = "expected = IllegalArgumentException")
    @MethodSource("isTriangleTestData")
    public void isTriangleTest(Triangle triangle) {
        TriangleService.isTriangle(triangle);
    }

    @ParameterizedTest
    @MethodSource("trianglesToTest")
    public void checkIfPointBelongsToTriangleTest(Triangle triangle, Point point) {

        boolean actual = TriangleService.isBelongsToTriangle(point, triangle);

        Assert.assertEquals(actual, true);
    }
}