package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TypeNotSupportedException extends RuntimeException {

    public TypeNotSupportedException( List<String> supportedTypes) {
        super(String.format("Invalid file type. Supported file types: %s",String.join(", ",supportedTypes)));
    }

}