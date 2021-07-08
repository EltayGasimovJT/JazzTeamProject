package service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NumberService {


    public static int countSum(String path) throws IOException, IllegalArgumentException {
        Path pathName = Paths.get(path);
        int number = FileService.getNumberFromFileAsString(pathName);
        if (number < 999 || number > 9999) {
            throw new IllegalArgumentException("Wrong input number " + number + " number must be more than 999 or less then 9999!!!");
        }

        int result = 0;
        result = getDifferenceBetweenOddsAndEventsInNumber(number, result);

        return result;
    }

    private static int getDifferenceBetweenOddsAndEventsInNumber(int number, int result) {
        int currentNumber;
        while (number != 0) {
            currentNumber = number % 10;
            if (currentNumber % 2 == 0) {
                result += currentNumber;
            } else {
                result -= currentNumber;
            }
            number /= 10;
        }
        return result;
    }
}