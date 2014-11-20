package com.daxia.generator.util;

import java.io.UnsupportedEncodingException;

public class WebParamUtils {
	
	public static String getUTF8Param(String value) throws UnsupportedEncodingException {
		if (value == null) {
			return null;
		}
		return new String(value.getBytes("ISO8859-1"), "UTF-8");
	}
}
