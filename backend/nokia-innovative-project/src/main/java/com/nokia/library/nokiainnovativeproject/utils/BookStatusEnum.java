package com.nokia.library.nokiainnovativeproject.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookStatusEnum {
	AVAILABLE(1L),
	AWAITING(2L),
	BORROWED(3L),
	RESERVED(4L),
	UNAVAILABLE(5L);

	private Long id;

}
