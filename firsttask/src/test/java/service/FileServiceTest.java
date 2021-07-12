package service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileServiceTest {
    private static final String PATH = "src/test/resources/dataToTest.txt";

    @Test
    public void testFileService() throws IOException {
        String actual = FileService.getStringFromFile(PATH);

        String expected = "1246";

        Assert.assertEquals(expected,  actual);
    }
}
