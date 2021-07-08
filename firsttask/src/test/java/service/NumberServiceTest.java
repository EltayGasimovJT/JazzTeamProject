package service;

import exception.NumberCustomException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NumberServiceTest extends TestCase {
    private static final String PATH = "firsttask\\src\\test\\resources\\testdata.txt";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Expected IllegalArgumentException";
    private static final String WRONG_NUM_PATH = "firsttask\\src\\test\\resources\\wrongTestData.txt";

    @Test
    public void testCountSum() throws IOException {
        int actual;
        int expected = 2;

        actual = NumberService.countSum(PATH);

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test()
    public void testCustomException() {
        try {
            NumberService.countSum(WRONG_NUM_PATH);
            Assert.fail(ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IOException | IllegalArgumentException thrown) {
            Assert.assertNotEquals("", thrown.getMessage());
        }
    }
}