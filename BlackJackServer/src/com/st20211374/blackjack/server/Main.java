package com.st20211374.blackjack.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 * @author Maxim Perolle
 *
 */
public class Main extends Thread{
	private ArrayList<Player> player = new ArrayList<>();
	private static List<Card> deck = new ArrayList<>();
	
	//create the array of player each time a new client connect
    public void addPlayer(Player player) {
        this.player.add(player);
    }
	
    public static void CardDraw(Player player) {
        Card card = deck.get(0);
        deck.remove(card);

        player.addCard(card);
    }

    public static void run(ArrayList<Player> player,ArrayList<SampleServer> servers) throws IOException {
        String decision = "d";
        int bust = 21;
        
        for (int i = 0; i< player.size(); i++) {
        	String msg = (player.get(i).getUsername() + " your cards are: " + player.get(i).getCardNames() + "(" + player.get(i).getScore() + ")");
            servers.get(1).broadcastToAll(msg);
        }
        
        for (int i = 0; i<= player.size(); i++) {
	        while (decision.equals("d")) {
	            String msg = (player.get(i).getUsername() + " your turn ! Do you want to hold or to draw a new card ? (type h or d)");
	            servers.get(i).broadcastToAll(msg);	        
	                       
	            decision = servers.get(i).getPlayerSocket().get(1).receiveMessage();//this is awful
	            System.out.println(decision);
	            servers.get(i).broadcast(decision, player.get(i).getPlayerSocket());
	            
	            switch (decision) {
	                case "h":
	                    msg = ("\n You hold, go to the next player: ");
	                    servers.get(i).dos.writeUTF(msg);
	                    break;
	                case "d":
	                    CardDraw(player.get(i));
	                    
	                    msg = ("Your cards are: " + player.get(i).getCardNames() + "(" + player.get(i).getScore() + ")");
	                    servers.get(i).dos.writeUTF(msg);
	      
	                    if (player.get(i).getScore() > bust) {
	                        msg = ("Your score is over 21, you get busted");
	                        servers.get(i).dos.writeUTF(msg);
	                        player.get(i).setBusted(true);
	                        decision = "h";
	                    }
	                    break;
	                default:
	                    msg = ("Bad entry");
	                    servers.get(i).dos.writeUTF(msg);
	                    decision = "d";
	                    break;
	            }
	        }
        }
    }

    public static String findWinner(List<Player> players, Dealer dealer) {
        //TODO could display name for a draw
        String winner = null;
        boolean draw = false;

        for (Player player : players) {
            if (player.getScore() > dealer.getScore() && !player.isBusted()) {
                if (winner == null) {
                    winner = player.getUsername();
                } else {
                    draw = true;
                }

            }
        }

        if (draw) {
            return "dr@@w";
        } else if (winner == null) {
            return "Dealer";
        } else {
            return winner;
        }
    }

    private static List<Card> createDeck() {
        List<Card> cards = new ArrayList<>();
        String[] cardNbr = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        for (CardType type : CardType.values()) {
            for (String number : cardNbr) {
                Card card = new Card(type, number);
                cards.add(card);
            }
        }

        Collections.shuffle(cards);

        return cards;
    }

    public static void serveurToGame(ArrayList<Player> players, ArrayList<SampleServer> servers) throws IOException {
       deck = createDeck();
       
       
        //TODO add a loop for a new game


        Dealer dealer = new Dealer();

//        Deal all players 2 cards
        for (Player player : players) {
            for (int i = 0; i < 2; i++) {
                CardDraw(player);
            }
        }

//        Draw cards for the dealer until it reaches a score of 16
        while (dealer.getScore() < 15) {
            CardDraw(dealer);
        }

//        Take turns on players
        for (Player player : players) {
            System.out.println("\n" + player.getUsername() + " your turn !");
				run(players,servers);
        }

        String result = findWinner(players, dealer);

        
        String msg = ("Dealer has a score of " + dealer.getCardNames());
        servers.get(1).broadcastToAll(msg);
        servers.get(1).broadcastToAll(result);

    }
    
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("How many players are gonna join the game?");
        int nbrPlayersWanted = Integer.parseInt(myObj.nextLine());
        myObj.close();
        
        int port = 5056;
        SampleServer server = new SampleServer(port,nbrPlayersWanted);
    }
}




