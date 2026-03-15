package com.cristiancid.taskmanager.exception;

public class UserNotFoundException  extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
