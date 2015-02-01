package org.exadel.simple.chat.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private static final String QUIT = "quit";
	private Socket socket;
	BufferedReader reader;
	DataOutputStream streamOut;

	public Client(String host, int port) {
		try {
			System.out.println("Connecting to the server");
			socket = new Socket(host, port);
			reader = new BufferedReader(new InputStreamReader(System.in));
			streamOut = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println("Host is unknown: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Unexpected exception: " + e.getMessage());
		}
		String line = "";
		while (!line.equals(QUIT)) {
			try {
				line = reader.readLine();
				streamOut.writeUTF(line);
				streamOut.flush();
			} catch (IOException e) {
				System.out.println("Sending error: " + e.getMessage());
			}
		}
		try {
			if (reader != null) {
				reader.close();
			}
			if (streamOut != null) {
				streamOut.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Error while closing...");
		}
	}

	public static void main(String[] args) {
		if (args == null || args.length != 2) {
			System.out.println("You must specify host and port: Usage: java Client 'host' 'port'");
		} else {
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			Client client = new Client(host, port);
		}
	}

}
