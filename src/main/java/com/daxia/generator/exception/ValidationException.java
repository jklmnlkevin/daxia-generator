package com.daxia.generator.exception;

public class ValidationException extends KException {
    private static final long serialVersionUID = 1L;

	public ValidationException() {
    }
	
	public ValidationException(String message) {
        super(message);
    }
	
}
