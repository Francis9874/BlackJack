package com.st20211374.blackjack.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class PlayerSocket extends Thread {

    final DataInputStream dis; //Declare dis as DataInputStream
    final DataOutputStream dos; //Declare dos as DataOutputStream

    final Socket s; //Declare s as a Socket
    String username = null;
    int score;

    /**
     * Constructor.
     *
     * @param s
     * @param dis
     * @param dos
     */
    public PlayerSocket(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    /**
     * run method, called when a client handler thread is starting..
     * responds to client requests
     */
    public void run() {
        boolean exit = false;
        String received; 
        Player player = new Player("");
        //Declare string to receive information

        //Infinite loop setup
        while (!exit) {
        	try {
        		// TODO: Move this to it's own class
        		
        		
	        	// Ask user what he wants 
				dos.writeUTF("Welcome to the server connection. Enter your command"); 
				
				// receive the answer from client 
				received = dis.readUTF();
				
				System.out.println(received);
				
	            String[] command = received.split(",");
	
	            switch (command[0]) {
	                case "setUsername":
	                    username = command[1];
	                    if (username.contains("shit")) {
		                    dos.writeUTF("usernameDeclined," + username);
	                    } else {
		                    dos.writeUTF("setUsername," + username);
		                    player.setUsername(username);
		                    System.out.println(player.getUsername());
	                    }
	                    break;
	                case "drawCard":
	                        Main.CardDraw(player);
	                        System.out.println(player.getCardNames());
	                    break;
	                case "exit":
	                    exit = true;
	                    break;
	            }
		    } catch (IOException e) { 
				e.printStackTrace(); 
			} 
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
