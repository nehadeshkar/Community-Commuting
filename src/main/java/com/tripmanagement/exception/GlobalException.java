package com.tripmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException e) {

		String message = e.getMessage();

		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = DateNotMatched.class)
	public ResponseEntity<String> dateNotMatched(DateNotMatched e) {

		String message = e.getMessage();

		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = StatusIsNotConsistent.class)
	public ResponseEntity<String> statusIsNotConsistent(StatusIsNotConsistent e) {

		String message = e.getMessage();

		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e){
		ResponseEntity<String> errorResponse = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		return errorResponse;
	}

}
