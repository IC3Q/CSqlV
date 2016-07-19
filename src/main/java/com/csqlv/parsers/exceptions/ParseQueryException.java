package com.csqlv.parsers.exceptions;

public class ParseQueryException extends Exception {

    public ParseQueryException() {
        this("The problem with parsing query occurred.");
    }

    public ParseQueryException(String message) {
        super(message);
    }
}
