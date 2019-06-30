package myyuk.exam.exception;

/**
 * If an unexpected error occurs during stream operation, StreamExecutionException occurred.
 */
public class StreamExecutionException extends RuntimeException {

    public StreamExecutionException(String message) {
        super(message);
    }

    public StreamExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
