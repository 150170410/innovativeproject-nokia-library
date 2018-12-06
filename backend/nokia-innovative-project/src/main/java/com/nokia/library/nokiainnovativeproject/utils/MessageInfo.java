package com.nokia.library.nokiainnovativeproject.utils;

import lombok.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MessageInfo {

	private Boolean success;
	private Object object;
	private List<String> message;

	public static MessageInfo success(Object object, List<String> message){
		return new MessageInfo(true, object, message);
	}

	public static MessageInfo failure(List<String> message) {
		return new MessageInfo(false, null, message);
	}

	public static MessageInfo getErrors(BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			List<String> errorsList = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
			return MessageInfo.failure(errorsList);
		}
		return null;
	}

	public static MessageInfo getErrors(ConstraintViolationException exc){
		List<String> errors = new ArrayList<>();
		Set<ConstraintViolation<?>> violationSet = exc.getConstraintViolations();
		for(ConstraintViolation constraintViolation : violationSet){
			errors.add(constraintViolation.getMessageTemplate());
		}
		return new MessageInfo(false, null, errors);
	}
}
