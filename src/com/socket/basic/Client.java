package com.socket.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException {
		Socket client = new Socket(InetAddress.getLocalHost(), 9090);
		System.out.println("[Client]: Connected to the server -> "+InetAddress.getLocalHost());
		
		var writer = new PrintWriter(client.getOutputStream());
		writer.println("Client Request!!!");
		writer.flush();
		System.out.println("[Client]: Request sent to the server.");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String serverResponse = reader.readLine();
		System.out.println("[Client]: [Response From Server]:" + serverResponse);
		
		reader.close();
		client.close();
	}
}
