package com.socket.advance.chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ResponseListener implements Runnable {

	private boolean canRun = true;
	private Socket socket;

	public ResponseListener(Socket socket) {
		this.socket = socket;
	}

	public void stop() {
		this.canRun = false;
	}

	@Override
	public void run() {
		var threadName = Thread.currentThread().getName()+"[RES]";
		try {
			Thread.currentThread().setName(threadName);
			var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (canRun) {
				String message = in.readLine();
				if (message != null && message.trim().length() != 0) {
					System.out.println(String.format("\n[%s] << %s", threadName, message));
					if ("exit".equalsIgnoreCase(message)) {
						break;
					}
				}
			}
		} catch (IOException e) {
			System.out
					.println(String.format("[%s]: [ResponseListener Error]: %s", threadName, e.getLocalizedMessage()));
		}
	}
}
