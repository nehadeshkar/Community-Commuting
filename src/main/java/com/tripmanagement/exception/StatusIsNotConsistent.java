package com.tripmanagement.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusIsNotConsistent extends RuntimeException {

	String fieldName;
	String fieldValue;

	public StatusIsNotConsistent(String fieldName, String fieldValue) {
		super(String.format("Resource is Not Consistent %s : %s", fieldName, fieldValue));
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;

	}
}
