package servicetest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    private FileService() {
    }

    public static String getStringFromFile(String pathName) throws IOException {
        Path path = Paths.get(pathName);

        return Files.readAllLines(path).get(0);
    }
}
