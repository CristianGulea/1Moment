package ro.moment.api.security.exceptions;

import javax.naming.AuthenticationException;

public class RefreshTokenExpiredException extends AuthenticationException {

    public RefreshTokenExpiredException(String t) {
        super(t);
    }
}
