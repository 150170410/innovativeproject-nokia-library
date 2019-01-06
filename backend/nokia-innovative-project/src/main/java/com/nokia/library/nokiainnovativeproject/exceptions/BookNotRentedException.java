package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookNotRentedException extends RuntimeException {

	public BookNotRentedException(Long bookId) {
		super(String.format("Book with id: %s is not rented by anyone. Renting is available", bookId));
	}
}