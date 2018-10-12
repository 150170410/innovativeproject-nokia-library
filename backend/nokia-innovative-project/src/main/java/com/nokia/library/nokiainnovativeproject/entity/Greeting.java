package com.nokia.library.nokiainnovativeproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Greeting {
	
	private @Getter final long id;
    private @Getter final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}