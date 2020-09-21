package com.socket.multiclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException {
		Socket client = new Socket(InetAddress.getLocalHost(), 9090);
		System.out.println("[Client]: Connected to the server -> " + InetAddress.getLocalHost());

		while (true) {

			var commandIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("[name|date|exit]>");
			String command = commandIn.readLine();

			var out = new PrintWriter(client.getOutputStream(), true);
			out.println(command);
			System.out.println("[Client]: Request sent to the server.");

			if (command.equalsIgnoreCase("exit"))
				break;

			var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String serverResponse = in.readLine();
			System.out.println("[Client]: [Response From Server]:" + serverResponse);
		}

		client.close();
	}
}
