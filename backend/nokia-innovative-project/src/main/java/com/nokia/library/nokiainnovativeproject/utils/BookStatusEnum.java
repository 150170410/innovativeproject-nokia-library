package com.nokia.library.nokiainnovativeproject.utils;

public enum BookStatusEnum {
	AVAILABLE(1L),
	AWAITING(2L),
	BORROWED(3L),
	RESERVED(4L),
	UNAVAILABLE(5L);

	private Long id;

	BookStatusEnum(Long id) {
		this.id = id;
	}

	public Long getStatusId(){
		return id;
	}
}
