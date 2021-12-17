package com.st20211374.blackjack.client;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerToClientHandler extends Thread {
	private SampleClient client;
	private DataInputStream input;
	
	public ServerToClientHandler(SampleClient client) {
		this.client = client;
		this.input = client.getInputStream();
	}
	
	public void run() {
		//TODO: make this thing work properly 
		String received = "";
		
		try {
			while(!received.equals("exit")) {
				received = input.readUTF();
	            
	            if (client.getUserName() != null) {
                    System.out.print(/*"[" + client.getUserName() + "]: " +*/ received + "\n");                 
                } 
			}
			
				
		}catch (IOException e) {
			System.out.println("Error occurred");
		} 
	}
}
