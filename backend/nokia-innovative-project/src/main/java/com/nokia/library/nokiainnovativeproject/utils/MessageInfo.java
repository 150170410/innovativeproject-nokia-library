package com.nokia.library.nokiainnovativeproject.utils;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import lombok.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MessageInfo {

	private Boolean success;
	private Object object;
	private List<String> message;

	public static ResponseEntity success(Object object, List<String> message){
		return ResponseEntity.ok().body(new MessageInfo(true, object, message));
	}

	public static ResponseEntity failure(List<String> message) {
		return ResponseEntity.badRequest().body(new MessageInfo(false, null, message));
	}

	public static ResponseEntity getErrors(BindingResult bindingResult) {
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
