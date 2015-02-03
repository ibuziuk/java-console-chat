package org.exadel.simple.chat.server;

public final class HttpUtil {
	public static final String LOCALHOST = "localhost";

	public final static String GET = "GET";
	public final static String POST = "POST";
	public final static String PUT = "PUT";
	public final static String DELETE = "DELETE";
	
	public static final String CONTENT_TYPE = "Content-type";
	public static final String CONTENT_LENGTH = "Content-Length";

	public static final String UTF_8 = "UTF-8";
	
	public static final String CRLF = "\r\n";
	public static final String HEADERS_END = CRLF + CRLF;
	
//	public static final String BLANK_LINE = "";

	public static String formGet() {
		// Basic GET request
		return "GET / HTTP/1.1" + CRLF +
			   "Host: " + LOCALHOST + CRLF +
			   "Connection: close" + HEADERS_END;
	}

	public static String formPost(String path, String data) {
		return "POST " + path + " HTTP/1.1" + CRLF +
			   "Host: " + LOCALHOST + CRLF +
			   "Content-Length: " + data.length() + CRLF +
			   "Content-type: application/json" + HEADERS_END +
			   data;
	}

	public static String formPut(String message) {
		// TODO
		return null;
	}

	public static String formDelete(String message) {
		// TODO
		return null;
	}
	
	public static int getContentLength(String header) {
		 return Integer.parseInt(header.replaceAll("\\D+",""));
	}

}
