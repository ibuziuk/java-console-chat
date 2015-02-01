package org.exadel.simple.chat.server;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	public static final String QUIT = "quit";
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private DataInputStream inputStream;
	private Thread thread;

	public Server(int port) {
		try {
			System.out.println("Starting server, please wait ...");
			serverSocket = new ServerSocket(port);
			System.out.println("Server started!" + serverSocket);
			start();
		} catch (IOException e) {
			System.out.println("Failed to start a server ((");
		}
	}

	@Override
	public void run() {
		while (thread != null) {
			System.out.println("Waiting for a client");
			try {
				clientSocket = serverSocket.accept();
				System.out.println("New client accepted: " + clientSocket);
				inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
				boolean done = false;
				while (!done) {
					try {
					String line = inputStream.readUTF();
					System.out.println(line);
					done = line.equals(QUIT);
					} catch (IOException e) {
						System.out.println("Something went wrong ...");
						done = true;
					}
				}
				close();
//				stop();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	private void close() {
		try {
			if (clientSocket != null) {
				clientSocket.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
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
