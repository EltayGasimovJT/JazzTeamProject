package validator;

public class NumberValidator {
    private NumberValidator(){}

    public static void validateNumber(int numberToValidate) throws IllegalArgumentException {
        if (numberToValidate < 999 || numberToValidate > 9999) {
            throw new IllegalArgumentException("Wrong input number " + numberToValidate + " number must be more than 999 or less then 9999!!!");
        }
    }


}
