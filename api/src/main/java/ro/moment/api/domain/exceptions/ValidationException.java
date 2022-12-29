package ro.moment.api.domain.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String mess) {
        super(mess);
    }
}
