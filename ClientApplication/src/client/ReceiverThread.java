/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tecomp
 */
public class ReceiverThread implements Runnable {

    DataInputStream in;
    boolean mRun;

    public ReceiverThread(DataInputStream in) {
        this.in = in;
        this.mRun = true;
    }
    
    @Override
    public void run() {
        
        if(mRun){
        String line = "";
		
	while(!line.equalsIgnoreCase("EXIT")){
            try {
                line = in.readUTF();
                if(line.equals("ftp")){
                    String fileName = in.readUTF();
                    File file = new File(fileName);
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    int bytesRead = 0;
                    byte[] byteArray = new byte[256];
                    
                    long totBytesRead = 0;
                    long fileLength = in.readLong();
                    
                    while(totBytesRead < fileLength && (bytesRead = in.read(byteArray, 0, byteArray.length)) != -1){
                        totBytesRead += bytesRead;
                        bos.write(byteArray, 0, bytesRead);
                        System.out.println("Received " + bytesRead + " bytes");
                    }
                    bos.flush();
                    System.out.println("File transfer complete.");
                    System.out.println("\t--> " + file.length());
                } else {
                    System.out.println(line);
                }
              
            } catch (EOFException eofe){
                System.out.println("Server Error");  
                break;
            } catch (IOException ioe) {
		Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ioe);
            }
	}
	
	try {
		in.close();
	} catch (IOException ioe) {
		Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ioe);
	}
        }
    }
    
    public void close() {
        mRun = false;
    }
    
}
