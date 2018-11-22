package com.nokia.library.nokiainnovativeproject.utils;

import lombok.*;

import java.util.List;

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
