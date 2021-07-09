package service;

import exception.NumberCustomException;
import validator.NumberValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileService {
    private FileService() {
    }

    public static String getNumberFromFileAsString(String pathName) throws IOException, NumberCustomException {
        Path path = Paths.get(pathName);
        String strNum = Files.readAllLines(path).get(0);
        NumberValidator.validateString(strNum);
        return strNum;
    }
}
