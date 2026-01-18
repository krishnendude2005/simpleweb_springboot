package com.krishnendu.SimpleWebApp.exception;

public class CustomUncheckedException extends RuntimeException {
    public CustomUncheckedException(String message){
        super(message);
    }
}
