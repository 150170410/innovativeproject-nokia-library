package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyReservedException extends RuntimeException  {
    public AlreadyReservedException(Long bookId, Long userId) {
        super(String.format("Book with id: %s has already been reserved by user with id: %s", bookId, userId));
    }
}
