package com.yocale.billmanagement.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {
    public JwtTokenMissingException(String message) {
        super(message);
    }
}
