package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBookStateException extends RuntimeException {

    public InvalidBookStateException(Long bookId , String additionalMessage) {
        super(String.format("Book with id: %s is eligible for this request. %s", bookId, Objects.toString(additionalMessage, "")));
    }
}