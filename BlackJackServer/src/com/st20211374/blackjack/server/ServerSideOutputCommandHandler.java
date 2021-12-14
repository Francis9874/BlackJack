package com.st20211374.blackjack.server;

import java.io.DataOutputStream;
import java.io.IOException;

public class ServerSideOutputCommandHandler {
	private DataOutputStream dos;
	
	public ServerSideOutputCommandHandler(DataInputStream input) {
	try {
		// TODO: Move this to it's own class
		
		
    	// Ask user what he wants 
		dos.writeUTF("Welcome to the server connection. Enter your command"); 
		
		// receive the command from the client and uses it 
		//TODO: read.utf
		received = dis.readUTF();
		
		System.out.println(received);
		
        String[] command = received.split(",");

        switch (command[0]) {
            case "setUsername":
                username = command[1];
                if (username.contains("dick")) {
                    dos.writeUTF("usernameDeclined," + username);
                } else {
                    dos.writeUTF("setUsername," + username);
                    players.add(new Player(username));
                }
                break;
            case "drawCard":
//                        CardDraw(player);
//                         Send back the card
                break;
            case "exit":
                exit = true;
                break;
        }
    } catch (IOException e) { 
		e.printStackTrace(); 
	} 
}
	
	
	
}
