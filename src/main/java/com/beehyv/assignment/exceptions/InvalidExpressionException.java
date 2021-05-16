package com.beehyv.assignment.exceptions;

public class InvalidExpressionException extends Exception{
    public InvalidExpressionException(String message) {
        super(message);
    }

    public InvalidExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
