package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TypeNotSupportedException extends RuntimeException {

    public TypeNotSupportedException( List<String> supportedTypes) {
        super(String.format(Messages.get(INVALID_TYPE) + "%s",String.join(", ",supportedTypes)));
    }
}