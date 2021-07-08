package service;

import exception.NumberCustomException;

import java.io.IOException;

public class NumberService {


    public static int countSum(String path) throws IOException, IllegalArgumentException {
        int number = FileService.getNumberFromFileAsString(path);
        if (number < 999 || number > 9999) {
            throw new IllegalArgumentException("Wrong input number " + number + " number must be more than 999 or less then 9999!!!");
        }

        int result = 0;
        result = getResult(number, result);

        return result;
    }

    private static int getResult(int number, int result) {
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