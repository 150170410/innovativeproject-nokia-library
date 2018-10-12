package com.nokia.library.nokiainnovativeproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Greeting {
	
	private @Getter final long id;
    private @Getter final String contentString;
}