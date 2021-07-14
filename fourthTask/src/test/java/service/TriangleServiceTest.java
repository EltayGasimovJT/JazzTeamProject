package service;

import entity.Point;
import entity.Triangle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TriangleServiceTest {
    private static final Triangle TEST_TRIANGLE = new Triangle(
            new Point(1, 1),
            new Point(1, 8),
            new Point(7, 1)
    );

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)
        );
    }

    @Test
    public void isTriangleTest() {
        boolean actual = TriangleService.isTriangle(TEST_TRIANGLE);
        Assert.assertEquals(actual, true);
    }

    @Test
    public void checkIfPointBelongsToTriangleTest() {
        Point point = new Point(1,4);
        boolean actual = TriangleService.isBelongsToTriangle(point, TEST_TRIANGLE);

        Assert.assertEquals(actual, true);
    }
}