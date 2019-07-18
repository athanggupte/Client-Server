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
import java.net.SocketException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tecomp
 */
public class ClientThread implements Runnable {

	private DataOutputStream out;
	private DataInputStream in;
	private int clientID;
	boolean isOnline;

	public ClientThread(DataOutputStream out, DataInputStream in, int clientID) {
		this.out = out;
		this.in = in;
		this.clientID = clientID;
		this.isOnline = true;
	}
	
	@Override
	public void run() {
		while(true) {
                    boolean isFtp = false;
                    int bytesRead = 0;
                    try {
                    	String line = in.readUTF();
                    	System.out.println("Client-" + clientID + ": " + line);
                    	if(line.equalsIgnoreCase("EXIT")) {
                            this.isOnline = false;
                            break;
			}
			if (line.equals("ftp")) {
                            String name = in.readUTF();
                            isFtp = true;
                            byte[] byteArray = new byte[256];
                            
                            long totBytesRead = 0;
                            long fileLength = in.readLong();
                            for (ClientThread ct : ServerApplication.clients) {
				if(ct.isOnline && ct.clientID != this.clientID) {
                                    ct.out.writeUTF("ftp");
                                    ct.out.writeUTF(name);
                                    ct.out.writeLong(fileLength);
                                    System.out.println("Sending file to Client-" + ct.clientID);
				}
                            }
                            while(totBytesRead < fileLength && (bytesRead = in.read(byteArray, 0, byteArray.length)) != -1){
                                totBytesRead += bytesRead;
                                for (ClientThread ct : ServerApplication.clients) {
                                    if(ct.isOnline && ct.clientID != this.clientID) {
                                        ct.out.write(byteArray, 0, bytesRead);
                                        System.out.println("Sent " + bytesRead + " bytes to Client-" + ct.clientID);
                                        ct.out.flush();
                                    }
                                }
                            }
                        } else {
                            for (ClientThread ct : ServerApplication.clients) {
				if(ct.isOnline && ct.clientID != this.clientID) {
                                    ct.out.writeUTF("Client-" + clientID + ": " + line);
                                    System.out.println("Sent message to Client-" + ct.clientID);
				}
                            }
                        }
				
                    } catch (SocketException se) {
                        System.out.println("Client-" + clientID + " disconnected");
                    } catch (EOFException eofe) {
			System.out.println("Client-" + clientID + " disconnected");
			break;
                    } catch (IOException ex) {
			Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
			break;
                    }
			
		}
		
		try {
			in.close();
			out.close();
			
		} catch (IOException ex) {
			Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
}
