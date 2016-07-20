package com.csqlv.resolvers.exceptions;

public class IllegalCharacterWhileQueryingException extends RuntimeException {
    public IllegalCharacterWhileQueryingException() {
        this("Incorrect data type in csv file. Hint: if you are using <, >, <= or >= operators column elements have to be numbers.");
    }

    public IllegalCharacterWhileQueryingException(String message) {
        super(message);
    }
}
