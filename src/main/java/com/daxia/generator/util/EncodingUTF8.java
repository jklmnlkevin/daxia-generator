package com.daxia.generator.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class EncodingUTF8 {
	public static String getUtfString(String str) throws UnsupportedEncodingException {
		return URLDecoder.decode(str,"utf-8");
	}
}
