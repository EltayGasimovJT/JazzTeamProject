package exception;

public class NumberCustomException extends Exception {
    public NumberCustomException() {
        super();
    }

    public NumberCustomException(String message) {
        super(message);
    }

    public NumberCustomException(String message, Throwable cause) {
        super(message, cause);
    }
    public NumberCustomException(Throwable cause) {
        super(cause);
    }
}
