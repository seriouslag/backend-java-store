package com.landongavin.entities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PARTIAL_CONTENT )
public class UserExistsButNotSavedException extends Throwable {
    public UserExistsButNotSavedException(String exception) {
        super();
        System.out.println("UserExistsButNotSavedException: " + exception);
    }
}
