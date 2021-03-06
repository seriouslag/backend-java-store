package com.landongavin.entities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAuthorized extends Throwable {
    public NotAuthorized(String exception) {
        super();
        System.out.println("Unathorized attempt made " + exception);
    }
}
