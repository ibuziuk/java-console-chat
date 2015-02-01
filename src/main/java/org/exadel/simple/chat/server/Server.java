package org.exadel.simple.chat.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static final String QUIT = "quit";
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private DataInputStream inputStream;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
			inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			boolean done = false;
			while (!done) {
				String line = inputStream.readUTF();
				System.out.println(line);
				done = line.equals(QUIT);
			}
			stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stop() {
		try {
			if (clientSocket != null) {
				clientSocket.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (serverSocket != null) {
				serverSocket.close();
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
