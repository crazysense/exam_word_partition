package myyuk.exam.exception;

/**
 * If the option is invalid, InvalidOptionException occurred.
 */
public class InvalidOptionException extends RuntimeException {

    public InvalidOptionException(String message) {
        super(message);
    }

    public InvalidOptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
