package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    private FileService() {
    }

    public static String getNumberFromFileAsString(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        return Files.readAllLines(path).get(0);
    }
}
