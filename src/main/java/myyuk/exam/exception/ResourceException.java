package myyuk.exam.exception;

/**
 * If an error occurs when creating or removing the resources, ResourceException occurred.
 */
public class ResourceException extends RuntimeException {

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
