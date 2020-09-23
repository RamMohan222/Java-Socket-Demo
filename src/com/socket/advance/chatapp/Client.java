package com.socket.advance.chatapp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException, InterruptedException {
		Socket socket = new Socket(InetAddress.getLocalHost(), 9090);
		String deviceName = "Client";
		System.out.println(String.format("[%s]: Connected to the host -> %s ", deviceName, InetAddress.getLocalHost()));
		Thread client = new Thread(new ClientHandler(socket), deviceName);
		client.start();
		client.join();
		socket.close();
	}
}
