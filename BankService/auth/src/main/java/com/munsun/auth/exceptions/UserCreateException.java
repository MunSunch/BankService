package com.munsun.auth.exceptions;

public class UserCreateException extends RuntimeException {
    public UserCreateException(String userWithUsernameAlreadyExists) {
        super(userWithUsernameAlreadyExists);
    }
}
