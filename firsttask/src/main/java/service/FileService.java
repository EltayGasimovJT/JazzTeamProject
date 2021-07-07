package service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileService {
    public static String getNumberFromFileAsString(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        StringBuilder result = new StringBuilder();
        Scanner scanner = new Scanner(path);
        scanner.useDelimiter(System.getProperty("line.separator"));
        while(scanner.hasNext()){
            result.append(scanner.next());
        }
        scanner.close();
        return result.toString();
    }
}
