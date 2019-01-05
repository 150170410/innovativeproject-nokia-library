package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyHandedOverException extends RuntimeException {

	public AlreadyHandedOverException(Long bookId) {
		super(String.format("Book with id: %s has already been handed over", bookId));
	}
}