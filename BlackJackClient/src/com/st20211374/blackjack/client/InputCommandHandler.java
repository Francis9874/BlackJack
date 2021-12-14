package com.st20211374.blackjack.client;

import java.io.DataInputStream;
import java.io.IOException;

public class InputCommandHandler {
	//input data 
	private DataInputStream input;
	private Player player;
	
	public InputCommandHandler(DataInputStream input) {
		this.input = input;
		this.player = new Player();
		
		boolean exit = false;

		try {
			while(!exit) {
				String received = input.readUTF();
	            String[] command = received.split(",");
				
				switch (command[0]) {
					case "setUsername":
						this.player.setUsername(command[1]);
						System.out.println("Username '" + this.player.getUsername() + "' accepted");
						break;
					case "usernameDeclined":
						String username = command[1];
						System.out.println("Username '" + username + "' declined");
						break;
				}
			}
		} catch (IOException e) {
			System.out.println("Error occurred");
		} 
	}
}
