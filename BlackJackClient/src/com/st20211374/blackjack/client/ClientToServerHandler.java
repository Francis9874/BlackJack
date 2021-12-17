package com.st20211374.blackjack.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientToServerHandler extends Thread {
    private Socket socket;
    private SampleClient client;
 
    public ClientToServerHandler(Socket socket, SampleClient client) {
        this.socket = socket;
        this.client = client;
    }
	
	public void run() {
		Scanner scn = new Scanner(System.in); 

		// sent everything wrote by the client console
		try {
			client.dos = new DataOutputStream(socket.getOutputStream()); 
			
			System.out.println("Hello new Player ! Please enter your username: ");
			client.setUserName(scn.nextLine());
			
			while (true) {
				String tosend = scn.nextLine(); 
				client.dos.writeUTF(tosend);
				
				if(tosend.equals("exit")) { 
					break; 
				}
			}
			
			// closing resources 
			System.out.println("Closing this connection : " + socket); 
			System.out.println("Connection closed"); 
			socket.close(); 
			scn.close(); 
			client.dis.close(); 
			client.dos.close(); 
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
