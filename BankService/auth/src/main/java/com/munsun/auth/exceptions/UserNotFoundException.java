package com.munsun.auth.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
