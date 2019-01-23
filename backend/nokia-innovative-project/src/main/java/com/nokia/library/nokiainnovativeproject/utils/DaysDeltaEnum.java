package com.nokia.library.nokiainnovativeproject.utils;

public enum DaysDeltaEnum {
	PLUSMONTH(30),
	MINUSMONTH(-30),
	RESET(0);

	private Integer days;

	DaysDeltaEnum(Integer days) {
		this.days = days;
	}

	public Integer getDays(){
		return days;
	}
}
