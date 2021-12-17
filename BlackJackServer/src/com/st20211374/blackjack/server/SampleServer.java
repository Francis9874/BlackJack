package com.st20211374.blackjack.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * A simple server class, listen to a port and serves clients when they connect..
 */
public class SampleServer {
	private Set<PlayerSocket> playerSocket = new HashSet<>();
	private ArrayList<Player> players = new ArrayList<>();
	private String username;
	
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
    public SampleServer(int port, int nbrPlayersWanted) {

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
                    //create a new player to receive the username and socket id, create a main to give the information for the game
                    
                    
                    PlayerSocket handler = new PlayerSocket(s,this);//declare a new thread t of type ClientHandler
                   
                    Player player = new Player(this.username,handler);
                    this.players.add(player);
                    
                    if (nbrPlayersWanted == this.playerSocket.size()) {
                    	Main.main(this.players,this);
                    }

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
        Scanner myObj = new Scanner(System.in);
        System.out.println("How many players are gonna join the game?");
        int nbrPlayersWanted = Integer.parseInt(myObj.nextLine());
        myObj.close();
        
        int port = 5056;
        SampleServer server = new SampleServer(port,nbrPlayersWanted);
        
        //TODO: try to launch game if there is enough players and if everyone as set his username

        Main main = new Main();
        
        

    } // End Main
    
    void broadcast(String message, PlayerSocket excludeUser) {
        for (PlayerSocket aUser : playerSocket) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }
    
    

    void addUserName(String userName) {
        players.get(players.size()).setUsername(userName);
    }
} // End Server Class



