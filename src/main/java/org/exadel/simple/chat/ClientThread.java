package org.exadel.simple.chat;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
	private int id;
	private Socket client;
	private DataInputStream inputStream;

	public ClientThread(Socket client) {
		this.client = client;
		id = client.getPort();
	}

	@Override
	public void run() {
		System.out.println("new Client Thread " + id + " is running");
		boolean done = false;
		while (!done) {
			try {
				if (inputStream != null) {
					String line = inputStream.readUTF();
					System.out.println(line);
				}
			} catch (IOException e) {
				try {
					System.out.println("Client " + id + " was disconnected");
					done = true;
					close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public void open() throws IOException {
		inputStream = new DataInputStream(new BufferedInputStream(client.getInputStream()));
	}

	public void close() throws IOException {
		if (client != null) {
			client.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}
	}

}
