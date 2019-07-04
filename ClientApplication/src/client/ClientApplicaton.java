/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tecomp
 */
public class ClientApplicaton {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Socket socket = null;
		DataInputStream input = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		
		try {
			socket = new Socket("127.0.0.1", 12345);
			System.out.println("Connected");
			
			input = new DataInputStream(System.in);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
		} catch (UnknownHostException ue) {
			Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ue);
		}catch (IOException ioe) {
			Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ioe);
		}
		
		String line = "";
		
		while(!line.equalsIgnoreCase("EXIT")){
			try {
				
				System.out.println(in.readUTF());
				
				System.out.println("You: ");
				line = input.readLine();
				out.writeUTF(line);
				
			} catch (IOException ioe) {
				Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ioe);
			}
		}
		
		try {
			input.close();
			out.close();
			socket.close();
		} catch (IOException ioe) {
				Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ioe);
		}
		
	}
	
}
