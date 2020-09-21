package com.socket.multiclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ClientHandler implements Runnable {
	private Socket client = null;;
	private PrintWriter out = null;
	private BufferedReader in = null;

	public ClientHandler(Socket client) throws IOException {
		this.client = client;
		this.out = new PrintWriter(client.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}

	@Override
	public void run() {
		String clientName = Thread.currentThread().getName();
		try {
			while (true) {
				String command = in.readLine();
				System.out.println("[Server][" + clientName + "]: command -> " + command);
				
				if (command.contains("exit")) {
					break;
				} else if (command.equalsIgnoreCase("time")) {
					out.println(new Date().toString());
				} else if (command.equalsIgnoreCase("name")) {
					out.println(Thread.currentThread().getName());
				} else {
					out.println("allowed commands are [time|name|exit]");
				}
			}
		} catch (IOException e) {
			System.err.println("[Server][" + clientName + "]: Error " + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				client.close();
			} catch (IOException e) {
				System.err.println("[Server][" + clientName + "]: Finally Error " + e.getLocalizedMessage());
			}
		}
	}
}
