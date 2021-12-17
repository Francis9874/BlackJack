package com.st20211374.blackjack.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class SampleClient extends Thread
{	
	// variables to store IP and port number
	String					ipaddress;
	int						portnumber;
	
	private Socket socket;
	
	// Handle IO streams to and from socket
	DataInputStream 		dis;
	DataOutputStream 		dos;
	
	private String userName;
	
	
	public SampleClient(String ipaddress, int port) {
		this.ipaddress = ipaddress;
		this.portnumber = port;

		try {
			this.socket = new Socket(this.ipaddress, this.portnumber);
			new ClientToServerHandler(socket,this).start();
			new ServerToClientHandler(this).start();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setUserName(String userName) {
        this.userName = userName;
    }
 
    String getUserName() {
        return this.userName;
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
	
	public static void main(String[] args) throws IOException { 
		String ip = "127.0.0.1";
		int port = 5056;
		
		SampleClient client = new SampleClient(ip, port);
		client.start();
	}
}
