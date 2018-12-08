package com.nokia.library.nokiainnovativeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectBindingException extends RuntimeException {

	public BindingResult bindingResult;

	public ObjectBindingException(String objectName, BindingResult bindingResult) {
		super(String.format("Binding of %s resulted in errors.", objectName));
		this.bindingResult = bindingResult;
	}
}