package org.jazzteam.eltay.gasimov.validator;

public class NumberValidator {
    private NumberValidator() {
    }

    public static void validateNumber(int numberToValidate) throws IllegalArgumentException {
        int numberToCheck = Math.abs(numberToValidate);
        if (numberToCheck < 1000 || numberToCheck > 9999) {
            throw new IllegalArgumentException("Wrong input number " + numberToValidate + " number must be more than 999 or less then 9999!!!");
        }
    }
}
