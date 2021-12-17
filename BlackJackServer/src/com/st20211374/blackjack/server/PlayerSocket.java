package com.st20211374.blackjack.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class PlayerSocket extends Thread {

    final private DataInputStream dis; //Declare dis as DataInputStream
    final private DataOutputStream dos; //Declare dos as DataOutputStream
    final private Socket s; //Declare s as a Socket
    private SampleServer server;
    
    String username = null;
    int score;
    Player player;

    /**
     * Constructor.
     *
     * @param s
     * @param dis
     * @param dos
     */
    public PlayerSocket(Socket s, SampleServer server) {
        this.s = s;
        this.server = server;
        this.dis = server.dis;
        this.dos = server.dos;
    }

    @Override
    /**
     * run method, called when a client handler thread is starting..
     * responds to client requests
     */
    public void run() {
        String received = ""
        		; 
        //Declare string to receive information

        //Infinite loop setup
        while (!received.equals("exit")) {
        	try {       		
                String userName = dis.readUTF();      
                server.addUserName(userName);
                
                //TODO: make a "enter you username with user=" so that it's possible to use if contains("use") o be sure that it's really the username that had been inputed
                String serverMessage = "New user connected: " + userName;
                server.broadcast(serverMessage, this);
     
                String clientMessage;
     
                do {
                    clientMessage = dis.readUTF();
                    serverMessage = "[" + userName + "]: " + clientMessage;
                    server.broadcast(serverMessage, this);
                    
                } while (!clientMessage.equals("exit"));
                
	        	
		    } catch (IOException e) { 
				e.printStackTrace(); 
			} 
        	try {
     	        // closing resources
     	        this.dis.close();
     	        this.dos.close();
     	
     	    } catch (IOException e) {
     	        e.printStackTrace();
     	    }
        	System.out.println("Client disconnected");
	    }

	   
    }
    
    void sendMessage(String message) {
        try {
			dos.writeUTF(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
