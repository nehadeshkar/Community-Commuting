package com.tripmanagement.exception;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DateNotMatched extends RuntimeException {

	String fieldName;
	LocalDate fieldValue;

	public DateNotMatched(String fieldName, LocalDate fieldValue) {
		super(String.format("Past Date cannot be processed %s:%s", fieldName, fieldValue));
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
