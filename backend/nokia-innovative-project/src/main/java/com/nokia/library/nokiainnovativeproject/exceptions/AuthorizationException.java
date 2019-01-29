package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super(ACCESS_DENIED.toString());
    }
}