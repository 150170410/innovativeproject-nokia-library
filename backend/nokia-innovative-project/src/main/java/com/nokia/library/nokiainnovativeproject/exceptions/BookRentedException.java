package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookRentedException extends RuntimeException {

	public BookRentedException(Long bookId) {
		super(String.format("Book with id: %s is rented by another user.", bookId));
	}
}