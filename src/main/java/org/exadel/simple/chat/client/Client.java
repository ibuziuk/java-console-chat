package org.exadel.simple.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.exadel.simple.chat.server.HttpUtil;

public class Client {
	private Socket clientSocket;
	BufferedReader reader;
	DataOutputStream streamOut;

	public Client(String host, int port) {
		try {
			System.out.println("Connecting to the server");
			this.clientSocket = new Socket(host, port);
			System.out.println("Connected " + this.clientSocket);
			// reader = new BufferedReader(new InputStreamReader(System.in));
			streamOut = new DataOutputStream(this.clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Something went wrong ...");
			e.printStackTrace();
		}
	}

	public void processRequest() {
		BufferedReader rd = null;
		BufferedWriter wr = null;

		try {
			// sending request
			String request = HttpUtil.formPost("/", "some stuff");
			wr = new BufferedWriter(new OutputStreamWriter(streamOut, HttpUtil.UTF_8));
			wr.write(request);
			wr.flush();

			// reading response
			rd = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (wr != null) {
					wr.close();
				}
				if (rd != null) {
					rd.close();
				}
			} catch (IOException e) {
				System.err.println("Error occured. Can't close resource");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		if (args == null || args.length != 2) {
			System.out.println("You must specify host and port: Usage: java Client 'host' 'port'");
		} else {
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			Client client = new Client(host, port);
			client.processRequest();
		}
	}
}
