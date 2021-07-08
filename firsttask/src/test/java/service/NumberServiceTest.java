package service;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NumberServiceTest extends TestCase {
    private static final String PATH = ".\\src\\main\\resources\\data\\data.txt";
    private static final int WRONG_NUMBER = 1245125;
    private static final int CORRECT_NUMBER = 1245;
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Expected IllegalArgumentException";

    @Test()
    public void testCustomException() {
        try {
            NumberService.countSum(WRONG_NUMBER);
            Assert.fail(ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IOException | IllegalArgumentException thrown) {
            Assert.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    public void testCorrectCountSum() throws IOException {

        int actual = NumberService.countSum(CORRECT_NUMBER);
        int expected = 0;

        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void testFileService() throws IOException {
        Path path = Paths.get(PATH);
        int numberFromFileAsString = FileService.getNumberFromFileAsString(path);
        int actual = NumberService.countSum(numberFromFileAsString);

        int expected = 2;

        Assert.assertEquals(expected, actual, 0.001);
    }
}