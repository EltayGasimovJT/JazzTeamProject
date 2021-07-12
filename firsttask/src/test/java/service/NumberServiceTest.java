package service;

import exception.NumberCustomException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NumberServiceTest extends TestCase {
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Expected IllegalArgumentException";

    public static int[] wrongTestData() {
        return new int[]{21522, 0, 62, -125, -125126};
    }

    public static int[][] correctTestData() {
        return new int[][]{
                {8151, 1},
                {4251, 0},
                {1160, 4}};
    }

    @ParameterizedTest
    @MethodSource("wrongTestData")
    public void testNegativeNumberEntering(int number) {
        try {
            NumberService.countDifferenceBetweenSumOfOddsAndSumOfEvens(Integer.toString(number));
            Assert.fail(ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IllegalArgumentException | NumberCustomException thrown) {
            Assert.assertNotEquals("", thrown.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("correctTestData")
    public void testCorrectCountSum(int[] number) throws NumberCustomException {
        int actual = NumberService
                .countDifferenceBetweenSumOfOddsAndSumOfEvens(Integer.toString(number[0]));
        int expected = number[1];

        Assert.assertEquals(expected, actual, 0.001);
    }

}