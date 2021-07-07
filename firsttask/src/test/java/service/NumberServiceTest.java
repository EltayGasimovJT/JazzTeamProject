package service;

import exception.NumberCustomException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class NumberServiceTest extends TestCase {
    private static final String PATH = "D:\\Уроки Java JD2\\PrivateProj\\firstTask\\src\\test\\resources\\testdata.txt";
    private static final String WRONG_NUM_PATH = "D:\\Уроки Java JD2\\PrivateProj\\firstTask\\src\\test\\resources\\wrongTestData.txt";

    @Test
    public void testCountSum() throws IOException, NumberCustomException {
        int actual;
        int expected = 2;

        actual = NumberService.countSum(PATH);

        Assert.assertEquals(expected, actual, 0.001);
    }



   @Test()
    public void testCustomException() {
       try {
           NumberService.countSum(WRONG_NUM_PATH);
           Assert.fail("Expected IOException");
       } catch (IOException | NumberCustomException thrown) {
           Assert.assertNotEquals("", thrown.getMessage());
       }
    }
}