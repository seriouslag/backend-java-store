package com.landongavin.entities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PARTIAL_CONTENT )
public class UserExistsButIsNotAFirebaseUserException extends Throwable {
    public UserExistsButIsNotAFirebaseUserException(String exception) {
        super();
        System.out.println("UserExistsButIsNotAFirebaseUserException: " + exception);
    }
}
