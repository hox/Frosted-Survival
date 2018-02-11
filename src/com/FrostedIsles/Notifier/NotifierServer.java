package com.FrostedIsles.Notifier;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Log;

import com.FrostedIsles.Comp.Main;

public class NotifierServer {
	private ServerSocket server;
	private List<Thread> clients;
	
	private final short PORT = 1338;
	
	public Responder message;	
	
	public NotifierServer() {
		try {
			server = new ServerSocket(PORT);
			Log.info("Notifier is now listening for clients...");
		} catch (IOException e) {
			Log.error("Notifier failed to bind to port " + PORT + ".");
			return;
		}
		
		message = new Responder();
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plugin, new Runnable() {
			@Override
			public void run() {
				while (!server.isClosed()) {
					Socket client;
					try {
						client = server.accept();
					} catch (IOException e) {
						Log.warn("Client connection failed. Reason: " + e.getMessage());
						continue;
					}

					Log.info("Connection established from " + client.getInetAddress().getHostAddress());

					new ClientThread(message, client);
					message.SetNotifyMessage("New connection",
						"IP: " + client.getInetAddress().getHostAddress());
				}
			}
		}, 10000);
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
