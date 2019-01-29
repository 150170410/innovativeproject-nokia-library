package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TypeNotSupportedException extends RuntimeException {

    public TypeNotSupportedException( List<String> supportedTypes) {
        super(String.format(INVALID_TYPE + "%s",String.join(", ",supportedTypes)));
    }
}