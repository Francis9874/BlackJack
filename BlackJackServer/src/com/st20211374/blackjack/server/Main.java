package com.st20211374.blackjack.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 */

/**
 * @author Maxim Perolle
 *
 */
public class Main {
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

    public static Player runGame(Player player,SampleServer server) throws IOException {
        String decision = "d";
        int bust = 21;
        String msg = ("Your cards are: " + player.getCardNames() + "(" + player.getScore() + ")");
        server.dos.writeUTF(msg);

        while (decision.equals("d")) {
            msg = ("Do you want to hold or to draw a new card ? (type h or d)");
            server.dos.writeUTF(msg);
            
            decision = server.dis.readUTF();
            server.broadcast(decision, player.getPlayerSocket());

            switch (decision) {
                case "h":
                    msg = ("\nOk then: ");
                    server.dos.writeUTF(msg);
                    break;
                case "d":
                    CardDraw(player);
                    
                    msg = ("Your cards are: " + player.getCardNames() + "(" + player.getScore() + ")");
                    server.dos.writeUTF(msg);
      
                    if (player.getScore() > bust) {
                        msg = ("Your score is over 21, you get busted");
                        server.dos.writeUTF(msg);
                        player.setBusted(true);
                        decision = "h";
                    }
                    break;
                default:
                    msg = ("Bad entry");
                    server.dos.writeUTF(msg);
                    decision = "d";
                    break;
            }
        }
        return (player);
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

    public static void main(ArrayList<Player> players,SampleServer server) throws IOException {
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
				runGame(player,server);
        }

        String result = findWinner(players, dealer);

        
        String msg = ("Dealer has a score of " + dealer.getCardNames());
        server.dos.writeUTF(msg);
        server.dos.writeUTF(result);

    }
}




