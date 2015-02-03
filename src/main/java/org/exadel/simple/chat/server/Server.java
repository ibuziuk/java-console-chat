package org.exadel.simple.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket serverSocket;

	public Server(int port) {
		try {
			System.out.println("Starting server, please wait ...");
			serverSocket = new ServerSocket(port);
			System.out.println("Server started: " + serverSocket);
			start();
		} catch (IOException e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
	}

	private void start() throws IOException {
		while (true) {
			Socket client = serverSocket.accept();
			System.out.println("Client accepted: " + client);
			try {
				HttpRequestHandler request = new HttpRequestHandler(client);
				Thread thread = new Thread(request);
				thread.start();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.err.println("No port parameter: usage - 'java Server port'");
		} else {
			int port = Integer.parseInt(args[0]);
			if (port < 0 && port > 65535) {
				System.err.println("Invalid port value: usege - port value must be beetwen '0' and '65535'");
			} else {
				Server server = new Server(port);
			}
		}
	}
}
