/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	} catch (IOException ioe) {
		Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ioe);
	}
        
    }
    
}
