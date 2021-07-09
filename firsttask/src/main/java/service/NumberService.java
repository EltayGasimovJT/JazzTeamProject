package service;

import validator.NumberValidator;

public class NumberService {
    private NumberService() {
    }

    public static int countSum(int number) throws IllegalArgumentException {
        NumberValidator.validateNumber(number);
        int result = 0;
        result += getDifferenceBetweenOddsAndEventsInNumber(number);

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
}