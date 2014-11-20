package com.daxia.generator.util;

import com.daxia.generator.exception.ValidationException;

public class ValidationUtils {

	public static void assertTrue(boolean value, String msg) {
		if (!value) {
			throw new ValidationException(msg);
		}
		
	}
	
}
