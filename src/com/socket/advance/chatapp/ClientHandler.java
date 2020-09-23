package com.socket.advance.chatapp;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ClientHandler implements Runnable {
	private Socket client = null;

	public ClientHandler(Socket client) throws IOException {
		this.client = client;
	}

	@Override
	public void run() {
		String deviceName = Thread.currentThread().getName();
		ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r, deviceName);
				return thread;
			}
		});

		var responseListener = new ResponseListener(client);
		pool.execute(responseListener);
		pool.execute(new RequestListener(client, responseListener));
		// It will wait until it will shutdown
		while (!pool.isShutdown()) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
