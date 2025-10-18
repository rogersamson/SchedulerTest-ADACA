package com.rogersamson.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = { ResourceNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { BadRequestException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleResourceBadRequestException(BadRequestException ex) {
		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST , ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleAllExceptions(Exception ex) {
		return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message) {
		super(message);
	}
}

@Data
class ApiError {
	private final HttpStatus responseCode;
	private final String message;

	public ApiError(HttpStatus status, String message) {
		this.responseCode = status;
		this.message = message;
	}
}
