package service;

import exception.NumberCustomException;

import java.io.IOException;

public class NumberService {


    public static int countSum(String path) throws NumberCustomException, IOException {
        String getString = FileService.getNumberFromFileAsString(path);
        int number = Integer.parseInt(getString);
        if (number < 999 || number > 9999) {
            throw new NumberCustomException("Wrong input number " + number + " number must be more than 999 or less then 9999!!!");
        }

        int currentNumber;
        int result = 0;
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