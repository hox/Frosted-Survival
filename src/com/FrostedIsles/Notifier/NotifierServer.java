package com.FrostedIsles.Notifier;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.bukkit.craftbukkit.libs.jline.internal.Log;

public class NotifierServer {
	private ServerSocket server;
	private Responder res;
	private List<Thread> clients;
	
	private final short PORT = 1338;
	
	public NotifierServer() {
		try {
			server = new ServerSocket(PORT);
			Log.info("Notifier is now listening for clients...");
		} catch (IOException e) {
			Log.error("Notifier failed to bind to port " + PORT + ".");
			return;
		}
		
		res = new Responder();
		while (!server.isClosed()) {
			Socket client;
			try {
				client = server.accept();
			} catch (IOException e) {
				Log.warn("Client connection failed. Reason: " + e.getMessage());
				continue;
			}
			
			Log.info("Connection established from " + client.getInetAddress());
			
			new Thread(new ClientThread(res, client)).start();
		}
	}
	
	@Override
	public void finalize() {
		for (Thread t : clients) {
			if (t.isAlive()) { //Client is still connected
				t.interrupt();
			}
		}
	}
}
