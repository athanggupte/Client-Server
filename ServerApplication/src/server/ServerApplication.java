/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tecomp
 */
public class ServerApplication {

	ServerSocket server;
	Socket socket;
	static Vector<ClientThread> clients;
	DataInputStream inputStream;

	public ServerApplication(int port) {
		this.server = null;
		this.socket = null;
		this.clients = new Vector<>();
		this.inputStream = null;
		startServer(port);
	}
	
	public void startServer(int port) {
		
		try {
			server = new ServerSocket(12345);
			System.out.println("Server started");
			System.out.println("Listening at Port : " + 12345);
		} catch (IOException ex) {
			Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	public synchronized void acceptClient() throws IOException {
		while(true) {
			socket = server.accept();
			
			clients.add(new ClientThread(
					new DataOutputStream(socket.getOutputStream()), 
					new DataInputStream(socket.getInputStream()),
					clients.size()+1));
			
			new Thread(clients.lastElement()).start();
			
			System.out.println("Client-" + clients.size() + " connected : " + socket.getInetAddress());
		}
	}
	
	public static void main(String[] args) {
		
		ServerApplication serverApp = new ServerApplication(12345);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serverApp.acceptClient();
				} catch (IOException ex) {
					Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}).start();
		
		
	}
	
}
