package com.socket.basic.multiclientserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Server {

	public static void main(String[] args) throws IOException {
		ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
			int clientCount = 0;

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				String threadName = "ClientConnection - " + ++clientCount;
				System.out.println("ThreadName:" + threadName);
				thread.setName(threadName);
				return thread;
			}
		});

		ServerSocket serverSocket = new ServerSocket(9090);
		try {
			System.out.println("[Server]: Waiting for the first client...");
			while (true) {
				Socket client = serverSocket.accept();
				try {
					pool.execute(new ClientHandler(client));
				} catch (Exception e) {
					System.err.println("[Server]: Error On Processing Client Request: " + e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			System.err.println("[Server]: Error On Accepting Client: " + e.getLocalizedMessage());
		} finally {
			serverSocket.close();
		}

	}
}
