package service;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NumberServiceTest extends TestCase {
    private static final int WRONG_NUMBER = 1245125;
    private static final int CORRECT_NUMBER = 1245;
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Expected IllegalArgumentException";

    @Test()
    public void testCustomException() {
        try {
            NumberService.countSum(WRONG_NUMBER);
            Assert.fail(ILLEGAL_ARGUMENT_EXCEPTION);
        } catch (IllegalArgumentException thrown) {
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
        String strNum = Files.readAllLines(Paths.get("src\\test\\resources\\dataToTest.txt")).get(0);
        //String pathName = new File("src\\test\\resources\\dataToTest.txt").getCanonicalPath();
        //Path path = Paths.get(pathName);
        int numberFromFileAsString = Integer.parseInt(strNum);//FileService.getNumberFromFileAsString(path);
        int actual = NumberService.countSum(numberFromFileAsString);

        int expected = 11;

        Assert.assertEquals(expected, actual, 0.001);
    }
}