package com.st20211374.blackjack.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thanuja
 * <p>
 * A simple server class, listen to a port and serves clients when they connect..
 */
public class SampleServer {
    public static List<PlayerSocket> clients = new ArrayList<>();
    int portnumber;
    ServerSocket server_socket;

    // reference variable to store IO streams
    DataInputStream dis;
    DataOutputStream dos;

    /**
     * Constructor to run the server..
     *
     * @param port
     */
    public SampleServer(int port) {

        this.portnumber = port;

        try {
            this.server_socket = new ServerSocket(port);

            System.out.println("## server is listening to port: " + port + " waiting for client connections..");

            while (true) //infinite while loop
            {
                Socket s = null; //Declare a variable s of type socket and set it to null

                try {
                    // socket object to receive incoming client requests
                    s = this.server_socket.accept();

                    System.out.println("A new client is connected : " + s);

                    // obtaining input and out streams
                    dis = new DataInputStream(s.getInputStream());
                    dos = new DataOutputStream(s.getOutputStream());

                    // create a new thread to handle the connected client
                    PlayerSocket handler = new PlayerSocket(s, dis, dos);//declare a new thread t of type ClientHandler
                    Thread t = handler;

                    clients.add(handler);

                    // Invoking the start() method
                    t.start(); //Start the client handler

                } // End try part
                catch (Exception e) {
                	
                    s.close();
                    e.printStackTrace();
                } // End catch
            } // End while

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /**
     * Main program...
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // Get port from command line argument
        // int port = Integer.parseInt(args[0]);

        // server is listening on port 5056
    	// TODO: Move this to Main in Main.java
        int port = 5056;
        SampleServer server = new SampleServer(port);

    } // End Main
} // End Server Class


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
	                    if (username.contains("dick")) {
		                    dos.writeUTF("usernameDeclined," + username);
	                    } else {
		                    dos.writeUTF("setUsername," + username);
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
