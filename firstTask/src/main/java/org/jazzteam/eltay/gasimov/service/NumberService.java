package org.jazzteam.eltay.gasimov.service;

import exception.NumberCustomException;
import org.jazzteam.eltay.gasimov.validator.NumberValidator;

public class NumberService {
    private NumberService() {
    }

    public static int countDifferenceBetweenSumOfOddsAndSumOfEvens(String numberStringFromFile) throws IllegalArgumentException, NumberCustomException {
        validateString(numberStringFromFile);
        int numberFromTextFile = Integer.parseInt(numberStringFromFile);
        NumberValidator.validateNumber(numberFromTextFile);
        int result = 0;
        result += getDifferenceBetweenOddsAndEventsInNumber(Math.abs(numberFromTextFile));

        return result;
    }

    private static int getDifferenceBetweenOddsAndEventsInNumber(int number) {
        int result = 0;
        while (number != 0) {
            int currentNumber;
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

    private static void validateString(String stringToValidate) throws NumberCustomException {
        if (stringToValidate == null) {
            throw new IllegalArgumentException("entered String cannot be null!!! " + stringToValidate);
        }

        if (stringToValidate.isEmpty()) {
            throw new IllegalArgumentException("entered String cannot be empty!!! " + stringToValidate);
        }

        try {
            Integer.parseInt(stringToValidate);
        } catch (NumberFormatException e) {
            throw new NumberCustomException("You entered not number value!!! " + stringToValidate);
        }
    }
}