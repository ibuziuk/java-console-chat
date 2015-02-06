package org.exadel.simple.chat.server;

public final class HttpUtil {
	public static final String SERVER_NAME = "Exadel-Simple-Server";
	
	// HTTP methods
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	
	// HTTP errors
	public static final String BAD_REQUEST = "400";
	
	
	public static final String LOCALHOST = "localhost";
	public static final String CONTENT_TYPE = "Content-type";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String UTF_8 = "UTF-8";
	public static final String CRLF = "\r\n";
	public static final String HEADERS_END = CRLF + CRLF;
	
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
	
	public static String formBadRequestError() {
		String errorText = "<h1>Invalid Response</h1>";
		return "HTTP/1.1 400 Bad Request" + CRLF + 
				"Server: " + SERVER_NAME + CRLF + 
				CONTENT_TYPE + ": text/html" + CRLF +
				CONTENT_LENGTH + ": " + errorText.length() + CRLF + 
				"Connection: close" + HEADERS_END
				+ errorText;
	}

	public static final String formOkResponse() {
		String okText = "<h1>Ok</h1>";
		return "HTTP/1.1 200 OK" + CRLF + 
				"Server: " + SERVER_NAME + CRLF + 
				CONTENT_TYPE + ": text/html" + CRLF + 
				CONTENT_LENGTH + ": " + okText.length() + HEADERS_END 
				+ okText;

	}
	
	

}
