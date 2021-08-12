package service;

import exception.NumberCustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class NumberServiceTest {
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Expected IllegalArgumentException";

    public static int[] wrongTestData() {
        return new int[]{
                21522,
                0,
                62,
                -125,
                -125126,
                999,
                10000
        };
    }

    private static Stream<Arguments> numbersToTest() {
        return Stream.of(
                Arguments.of(8151, 1),
                Arguments.of(4251, 0),
                Arguments.of(1160, 4),
                Arguments.of(-1060, 5),
                Arguments.of(1000, -1),
                Arguments.of(9999, -36)
        );
    }

    @ParameterizedTest
    @MethodSource("wrongTestData")
    public void testNegativeNumberEntering(int number) {
        try {
            NumberService.countDifferenceBetweenSumOfOddsAndSumOfEvens(Integer.toString(number));
            Assertions.fail(ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IllegalArgumentException | NumberCustomException thrown) {
            Assertions.assertNotEquals("", thrown.getMessage());
        }

    }

    @ParameterizedTest
    @MethodSource("numbersToTest")
    public void testCountDifferenceBetweenSumOfOddsAndSumOfEvens(int number, int expected) throws NumberCustomException {
        int actual = NumberService
                .countDifferenceBetweenSumOfOddsAndSumOfEvens(Integer.toString(number));

        Assertions.assertEquals(expected, actual, 0.001);
    }
}