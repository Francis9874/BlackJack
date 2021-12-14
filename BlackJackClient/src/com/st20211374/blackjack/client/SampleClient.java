package com.st20211374.blackjack.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SampleClient extends Thread
{	
	// variables to store IP and port number
	String					ipaddress;
	int						portnumber;
	
	private Socket socket;
	
	// Handle IO streams to and from socket
	DataInputStream 		dis;
	DataOutputStream 		dos;
	
	public SampleClient(String ipaddress, int port) {
		this.ipaddress = ipaddress;
		this.portnumber = port;

		try {
			this.socket = new Socket(this.ipaddress, this.portnumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		Scanner scn = new Scanner(System.in); 

		// establish the connection with server and output data. Scan everything typed and send it to the server
		try {
			
			dos = new DataOutputStream(socket.getOutputStream()); 
			
			while (true) {
				String tosend = scn.nextLine(); 
				dos.writeUTF(tosend);
				
				if(tosend.equals("exit")) { 
					break; 
				}
			}
			
			// closing resources 
			System.out.println("Closing this connection : " + socket); 
			System.out.println("Connection closed"); 
			socket.close(); 
			scn.close(); 
			dis.close(); 
			dos.close(); 
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DataInputStream getInputStream() {
		try {
			return new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
