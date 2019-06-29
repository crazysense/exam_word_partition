package myyuk.exam.exception;

public class StreamExecutionException extends RuntimeException {

    public StreamExecutionException(String message) {
        super(message);
    }

    public StreamExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
