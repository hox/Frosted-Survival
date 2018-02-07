package com.FrostedIsles.Notifier;

import java.io.IOException;
import java.net.Socket;

import org.bukkit.craftbukkit.libs.jline.internal.Log;

public class ClientThread implements Runnable {
	protected Responder res;
	protected Socket socket;
	
	public ClientThread(Responder responder, Socket clientSocket) {
		this.res = responder;
		this.socket = clientSocket;
	}
	
	@Override
	synchronized public void run() {
		while (!Thread.interrupted() && res.NotifyAll(socket)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
	}
}
