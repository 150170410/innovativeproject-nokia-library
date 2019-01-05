package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookReservedException extends RuntimeException {

	public BookReservedException(Long bookId) {
		super(String.format("Book with id: %s is reserved by another user.", bookId));
	}
}