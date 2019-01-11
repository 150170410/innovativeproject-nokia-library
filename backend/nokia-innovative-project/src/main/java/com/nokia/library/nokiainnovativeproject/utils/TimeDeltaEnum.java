package com.nokia.library.nokiainnovativeproject.utils;

public enum TimeDeltaEnum {
	PLUSMONTH(30),
	MINUSMONTH(-30);

	private Integer days;

	TimeDeltaEnum(Integer days) {
		this.days = days;
	}

	public Integer getDays(){
		return days;
	}
}
