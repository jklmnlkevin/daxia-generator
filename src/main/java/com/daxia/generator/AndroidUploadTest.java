package com.daxia.generator;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;


public class AndroidUploadTest {
    
	private static final int TIME_OUT = 10 * 10000000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	public static final String SUCCESS = "1";
	public static final String FAILURE = "0";

	public static String uploadFile(File file) {
	    String res = null;
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		// String RequestURL = "http://localhost:8080/wy/m/image/upload";
		String RequestURL = "http://210.209.117.121/wy/m/image/upload";
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("user.id", "1");
			conn.setRequestProperty("title", "又是中文标题");
			conn.setRequestProperty("remark", "又是中文内容");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String[] args) {
        File file = new File("d:/me.jpg");
        String result = uploadFile(file);
        System.out.println(result);
    }
}