package com.productservice.exception;

public class AlreadyExistException extends Exception {
    public AlreadyExistException(String message) {
        super(message);
    }
}
