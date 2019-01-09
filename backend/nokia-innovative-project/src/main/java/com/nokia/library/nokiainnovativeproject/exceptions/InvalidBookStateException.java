package com.nokia.library.nokiainnovativeproject.exceptions;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBookStateException extends RuntimeException {

    public InvalidBookStateException(MessageTypes messageType) {
        super(Messages.get(messageType));
    }
}