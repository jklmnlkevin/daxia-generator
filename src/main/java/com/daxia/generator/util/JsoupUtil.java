package com.daxia.generator.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 工具类
 * */
public class JsoupUtil {
	
	// get请求
	public static String getOldDWByGet(String objUrl) {
		Document doc = null;
		String dataStr = null;
		try {
			doc = Jsoup.connect(objUrl).timeout(20000).get();
			dataStr = doc.text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataStr;
	}
	
	// post请求
	public static String getOldDWByPost(String objUrl) {
		Document doc = null;
		String dataStr = null;
		try {
			doc = Jsoup.connect(objUrl).timeout(10000).post();
			dataStr = doc.text();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dataStr;
	}
	
}
