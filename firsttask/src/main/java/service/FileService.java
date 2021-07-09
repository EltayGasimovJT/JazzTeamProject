package service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class FileService {
    private FileService() {
    }

    public static int getNumberFromFileAsString(Path pathName) throws IOException {
        StringBuilder result = new StringBuilder();
        Scanner scanner = new Scanner(pathName);
        scanner.useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            result.append(scanner.next());
        }

        scanner.close();
        return Integer.parseInt(result.toString());
    }
}
