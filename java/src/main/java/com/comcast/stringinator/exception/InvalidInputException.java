package com.comcast.stringinator.exception;

import org.springframework.lang.NonNull;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(@NonNull final String message) {
        super(message);
    }
}
