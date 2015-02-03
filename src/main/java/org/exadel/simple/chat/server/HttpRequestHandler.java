package org.exadel.simple.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HttpRequestHandler implements Runnable {
	private Socket client;
	private InputStream in;
	private OutputStream out;
	private BufferedReader reader;

	public HttpRequestHandler(Socket client) throws IOException {
		this.client = client;
		this.in = client.getInputStream();
		this.out = client.getOutputStream();
		this.reader = new BufferedReader(new InputStreamReader(this.in));
	}

	@Override
	public void run() {
		try {
			processRequest();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processRequest() throws IOException {
		String line;
		int contentLength = 0;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (line.startsWith(HttpUtil.CONTENT_LENGTH)) {
				contentLength = HttpUtil.getContentLength(line);
			}
			if (line.isEmpty()) {
				break;
			}
		}
		
		StringBuilder body = new StringBuilder();
		int c = 0;
		for (int i = 0; i < contentLength; i++) {
			c = reader.read();
			body.append((char) c);
		}
		System.out.println(body.toString());
	}
}
