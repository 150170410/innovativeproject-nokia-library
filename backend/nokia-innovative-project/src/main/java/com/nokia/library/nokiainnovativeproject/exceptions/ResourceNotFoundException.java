package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String resourceName) {
		super(CANT_FIND + resourceName);
	}
}