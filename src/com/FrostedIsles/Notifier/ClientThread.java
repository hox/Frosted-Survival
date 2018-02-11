package com.FrostedIsles.Notifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.bukkit.craftbukkit.libs.jline.internal.Log;

import com.FrostedIsles.Comp.Util;

public class ClientThread implements Runnable {
	protected Responder res;
	protected Socket socket;
	protected String AUTH_CODE = "89D1H8RTG1S6R81684GFDG98SD4G";
	public ClientThread(Responder responder, Socket clientSocket) {
		this.res = responder;
		this.socket = clientSocket;
	}
	
	@Override
	synchronized public void run() {
		while (!Thread.interrupted() && res.NotifyAll(socket)) {
			/*try {
				BufferedReader inFromClient =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if(inFromClient.readLine().replace("AUTH_CODE:", "").equals(AUTH_CODE)) {
					Util.msgAll("AUTH Connect");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
	}
}
