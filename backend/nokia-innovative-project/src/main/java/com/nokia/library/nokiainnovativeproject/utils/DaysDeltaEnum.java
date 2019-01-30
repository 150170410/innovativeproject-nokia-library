package com.nokia.library.nokiainnovativeproject.utils;

public enum DaysDeltaEnum {
	PLUSMONTH(31),
	MINUSMONTH(-31),
	RESET(0);

	private Integer days;

	DaysDeltaEnum(Integer days) {
		this.days = days;
	}

	public Integer getDays(){
		return days;
	}
}
