package com.landongavin.entities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PARTIAL_CONTENT )
public class UserExistsButIsNotAFirebaseUser extends Throwable {
    public UserExistsButIsNotAFirebaseUser(String exception) {
        super();
        System.out.println("UserExistsButIsNotAFirebaseUser: " + exception);
    }
}
