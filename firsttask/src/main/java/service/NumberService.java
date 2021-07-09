package service;

public class NumberService {
    private NumberService() {
    }

    public static int countSum(int number) throws IllegalArgumentException {
        if (number < 999 || number > 9999) {
            throw new IllegalArgumentException("Wrong input number " + number + " number must be more than 999 or less then 9999!!!");
        }

        int result = 0;
        result += getDifferenceBetweenOddsAndEventsInNumber(number);

        return result;
    }

    private static int getDifferenceBetweenOddsAndEventsInNumber(int number) {
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