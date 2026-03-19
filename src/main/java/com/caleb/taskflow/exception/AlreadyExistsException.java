package com.caleb.taskflow.exception;

public class AlreadyExistsException extends  RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
