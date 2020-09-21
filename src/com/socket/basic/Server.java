package com.socket.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(9090);
		System.out.println("[Server]: Waiting for client...");
		Socket client = socket.accept();
		System.out.println("[Server]: Client connected!");

		var reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		System.out.println("[Server]: [Request From Client]:" + reader.readLine());

		var writer = new PrintWriter(client.getOutputStream());
		writer.println("ServerTime:" + new Date().toString());
		writer.flush();

		client.close();
		socket.close();
	}
}
