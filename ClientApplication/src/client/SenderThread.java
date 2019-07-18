/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tecomp
 */
public class SenderThread implements Runnable {

    DataOutputStream out;

    public SenderThread(DataOutputStream out) {
        this.out = out;
    }
    
    @Override
    public void run() {
        
        String line = "";
	DataInputStream input = new DataInputStream(System.in);
	
        while(!line.equalsIgnoreCase("EXIT")){
            try {
			
		//System.out.println("You: ");
		line = input.readLine();
                StringTokenizer st = new StringTokenizer(line);
                if(st.nextToken().equals("ftp")){
                    String name = st.nextToken(); //Name of file to send
                    
                    File file = new File(st.nextToken());
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                    
                    out.writeUTF("ftp");
                    out.writeUTF(name);
                    
                    byte[] byteArray = new byte[1024];
                    int trfBytes = 0;
                    
                    long totalBytes = 0;
                    out.writeLong(file.length());
                    
                    while(totalBytes < file.length() && (trfBytes = bis.read(byteArray, 0, byteArray.length)) != -1){
                        out.write(byteArray, 0, trfBytes);
                        totalBytes += trfBytes;
                        System.out.println("Transfered : " + totalBytes + "b / " + file.length() + "b");
                    }
                    
                    
                } else {
                    out.writeUTF(line);
                }
			
            } catch (SocketException eofe){
                System.out.println("Socket closed");
                break;
            } catch (IOException ioe) {
		Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ioe);
            }
	}
	
	try {
		input.close();
		out.close();
	} catch (IOException ioe) {
		Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ioe);
	}
        
    }
    
}
