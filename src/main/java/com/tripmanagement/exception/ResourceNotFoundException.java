package com.tripmanagement.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	String resourceName;

	String fieldName;

	String fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, String tripId) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, tripId));

		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = tripId;
	}

}
