package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProlongationForbiddenException extends RuntimeException {

	public ProlongationForbiddenException(Long bookId, String date) {
		super(String.format("Cannot prolong book with ID: %s before %s", bookId, date));
	}
}