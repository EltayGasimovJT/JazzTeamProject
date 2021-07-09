package validator;

import exception.NumberCustomException;

public class NumberValidator {

    public static void validateNumber(int numberToValidate) throws IllegalArgumentException {
        if (numberToValidate < 999 || numberToValidate > 9999) {
            throw new IllegalArgumentException("Wrong input number " + numberToValidate + " number must be more than 999 or less then 9999!!!");
        }
    }

    public static void validateString(String stringToValidate) throws NumberCustomException {
        if (stringToValidate.isEmpty()) {
            throw new IllegalArgumentException("entered String cannot be empty!!! " + stringToValidate);
        }

        try {
            Integer.parseInt(stringToValidate);
        } catch (NumberFormatException e){
            throw new NumberCustomException("You entered not number value!!! " + stringToValidate);
        }
    }
}
