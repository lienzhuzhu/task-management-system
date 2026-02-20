package uiowa.ais.interview.exception;

/**
 * Custom exception thrown when a requested resource is not found.
 * For example, when trying to get a user or task that doesn't exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
