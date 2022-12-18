package ro.moment.api.domain.exceptions;

public class ValidationException extends RuntimeException {
    ValidationException(String mess) {
        super(mess);
    }
}
