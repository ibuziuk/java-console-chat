package org.exadel.simple.chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpRequestHandler implements Runnable {
	private Socket client;
	private InputStream in;
	private PrintWriter out;
	private BufferedReader reader;

	public HttpRequestHandler(Socket client) throws IOException {
		this.client = client;
		this.in = client.getInputStream();
		this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));
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
		String initialRequestLine = reader.readLine();
		System.out.println(initialRequestLine);
		// TODO Need to parse request URI and HTTP version
		if (initialRequestLine.contains(HttpUtil.GET)) {
			doGet();
		} else if (initialRequestLine.contains(HttpUtil.POST)) {
			doPost();
		} else if (initialRequestLine.contains(HttpUtil.PUT)) {
			doPut();
		} else if (initialRequestLine.contains(HttpUtil.DELETE)) {
			doDelete();
		}  else {
			// send 400 Bad Request
			out.write(HttpUtil.formBadRequestError());
			close();
		}

	}
	
	private void doGet() throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (line.isEmpty()) {
				break;
			}
		}
		
		out.write(HttpUtil.formOkResponse());
		close();
	}
	
	private void doPost() throws IOException {
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
		
		out.write(HttpUtil.formOkResponse());
		close();
	}
	
	private void doPut() throws IOException {
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
		
		out.write(HttpUtil.formOkResponse());
		close();
	}
	
	private void doDelete() throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (line.isEmpty()) {
				break;
			}
		}
		
		out.write(HttpUtil.formOkResponse());
		close();
	}
	
	private void close() throws IOException {
		if (in != null) {
			in.close();
		}
		if (out != null) {
			out.close();
		}
		if (reader != null) {
			reader.close();
		}
		if (client != null) {
			client.close();
		}
	}
}
