package org.exadel.simple.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	private ServerSocket serverSocket;
	private ClientThread clientThread;
	private Thread thread;

	public Server(int port) {
		try {
			System.out.println("Starting server, please wait ...");
			serverSocket = new ServerSocket(port);
			System.out.println("Server started: " + serverSocket);
			start();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to start a server");
		}
	}

	@Override
	public void run() {
		while (thread != null) {
			System.out.println("Waiting for a client");
			try {
				addThread(serverSocket.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void addThread(Socket clientSocket) {
		System.out.println("Client accepted: " + clientSocket);
		clientThread = new ClientThread(clientSocket);
		try {
			clientThread.open();
			clientThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	private void stop() {
		if (thread != null) {
			thread.interrupt();
			thread = null;
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
