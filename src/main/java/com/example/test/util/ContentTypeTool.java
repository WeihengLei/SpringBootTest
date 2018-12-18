package com.example.test.util;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeTool {
	private static Map<String, String> contentTypeMap = new HashMap<String, String>();
	private static String defaultContentType = "multipart/form-data";
	static {
		contentTypeMap.put("jpe", "image/jpeg");
		contentTypeMap.put("jpeg", "image/jpeg");
		contentTypeMap.put("jpg", "image/jpeg");
		contentTypeMap.put("png", "image/png");
		contentTypeMap.put("gif", "image/gif");
		contentTypeMap.put("bmp", "image/bmp");
		contentTypeMap.put("pdf", "application/pdf");
		contentTypeMap.put("zip", "application/zip");
		contentTypeMap.put("csv", "application/excel");
		contentTypeMap.put("avi", "video/x-msvideo");
		contentTypeMap.put("mp3", "audio/mpeg");
		contentTypeMap.put("mp2", "audio/mpeg");
		contentTypeMap.put("movie", "video/x-sgi-movie");
	}

	public static String getContentType(String ext) {
		String contentType = contentTypeMap.get(ext.toLowerCase());
		return contentType == null ? defaultContentType : contentType;
	}

}
