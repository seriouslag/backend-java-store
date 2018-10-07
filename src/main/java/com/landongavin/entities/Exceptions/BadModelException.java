package com.landongavin.entities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadModelException extends RuntimeException {
    public BadModelException(String exception) {
        super(exception);
    }
}
