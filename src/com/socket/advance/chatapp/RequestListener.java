package com.socket.advance.chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestListener implements Runnable {

	private Socket client;
	private ResponseListener responseListener;

	public RequestListener(Socket client, ResponseListener responseListener) {
		this.client = client;
		this.responseListener = responseListener;
	}

	@Override
	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			var threadName = Thread.currentThread().getName() + "[REQ]";
			Thread.currentThread().setName(threadName);

			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(System.in));

			while (true) {

				System.out.print(String.format("[%s] >> ", threadName));
				String command = in.readLine();

				out.println(command);

				if ("exit".equalsIgnoreCase(command)) {
					responseListener.stop();
					break;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
