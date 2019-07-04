/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
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
    }
    
    @Override
    public void run() {
        
        String line = "";
		
	while(!line.equalsIgnoreCase("EXIT")){
		try {
                        System.out.println(in.readUTF());
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
