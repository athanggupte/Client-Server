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

        Socket socket;

    public ClientApplicaton(Socket socket) {
        this.socket = socket;
    }
        
    public void startService() throws IOException {
	DataInputStream in = null;
	DataOutputStream out = null;
        
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
                       
        new Thread(new ReceiverThread(in)).start();
        new Thread(new SenderThread(out)).start();
    }
    
	/**
	 * @param args the command line arguments
	 */
    public static void main(String[] args) {
	
	try {
            ClientApplicaton client = new ClientApplicaton(new Socket("10.10.12.194", 12345));
            System.out.println("Connected");
			
            client.startService();
            
	} catch (UnknownHostException ue) {
            Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ue);
	}catch (IOException ioe) {
            Logger.getLogger(ClientApplicaton.class.getName()).log(Level.SEVERE, null, ioe);
	}		
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        socket.close();
    }
	
    
}
