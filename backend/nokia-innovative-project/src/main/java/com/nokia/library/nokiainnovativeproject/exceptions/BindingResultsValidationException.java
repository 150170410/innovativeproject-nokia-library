package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BindingResultsValidationException extends RuntimeException {

    public BindingResultsValidationException(String errors) {
        super(errors);
    }
}
