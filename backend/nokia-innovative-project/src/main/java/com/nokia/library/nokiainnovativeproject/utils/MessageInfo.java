package com.nokia.library.nokiainnovativeproject.utils;

import lombok.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.List;
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

}
