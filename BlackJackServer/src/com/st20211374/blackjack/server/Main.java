package com.st20211374.blackjack.server;

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

	private static List<Card> deck = new ArrayList<>();
	
    public static void CardDraw(Player player) {
        Card card = deck.get(0);
        deck.remove(card);

        player.addCard(card);
    }

    public static Player runGame(Player player) {
        Scanner myObj = new Scanner(System.in);
        String decision = "d";
        int bust = 21;
        System.out.println("Your cards are: " + player.getCardNames() + "(" + player.getScore() + ")");

        while (decision.equals("d")) {
            System.out.println("Do you want to hold or to draw a new card ? (type h or d)");
            decision = myObj.nextLine();

            switch (decision) {
                case "h":
                    System.out.println("\nOk then: ");
                    break;
                case "d":
                    CardDraw(player);
                    System.out.println("Your cards are: " + player.getCardNames() + "(" + player.getScore() + ")");

                    if (player.getScore() > bust) {
                        System.out.println("Your score is over 21, you get busted");
                        player.setBusted(true);
                        decision = "h";
                    }
                    break;
                default:
                    System.out.println("Bad entry");
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

    public static void main(String[] args) {
    	
    	
        deck = createDeck();

        //TODO Nbr of player expected
        //TODO for each player, take the username
        //TODO add a loop for a new game
        //TODO create a github

        //username can't be dr@@w
//        int nbrPlayer = 3;
//        Player[] myplayer = new Player[nbrPlayer];
//        myplayer[0] = new Player("Franck");
//        myplayer[1] = new Player("Jhons");
//        myplayer[2] = new Player("Taume");

        List<Player> players = new ArrayList<>();
        players.add(new Player("Franck"));
        players.add(new Player("Jhons"));
        players.add(new Player("Taume"));
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
            runGame(player);
        }

        String result = findWinner(players, dealer);

        System.out.println("Dealer has a score of " + dealer.getCardNames());
        System.out.println(result);
        //initiation of the game
//        for (int i = 0; i < nbrPlayer; i++) {
//            for (int x = 0; x < 2; x++) {
//                //Draw two cards for each player
//                Card newCard = CardDraw(allCardDrawn);
//                String cardName = newCard.getCard();
//                allCardDrawn.add(cardName);
//                myplayer[i].addCard(cardName);
//                myplayer[i].addScore(newCard.getScore());
//            }
//
//            System.out.println(myplayer[i].getUsername() + " your cards are: " + myplayer[i].getCardList());
//        }
//
//        //dealer draw, false, draw until score>15, as to have a separate function. If dealerScore>21 every player win
//        while (dealer.getScore() < 15) {
//            Card newCard = CardDraw(allCardDrawn);
//            String cardName = newCard.getCard();
//            allCardDrawn.add(cardName);
//            dealer.addCard(cardName);
//            dealer.addScore(newCard.getScore());
//        }
//        System.out.println("The dealer's cards are: " + dealer.getCardList());


//        if (dealer.getScore() <= 21) {
//            //player draw
//            for (int i = 0; i < nbrPlayer; i++) {
//                int nbrOfCards = myplayer[i].getCardList().size();
//                System.out.println("\n" + myplayer[i].getUsername() + " your turn !");
//                runGame(myplayer[i], allCardDrawn);
//                //add drawn cards to the list
//                for (int x = nbrOfCards; x < myplayer[i].getCardList().size(); x++) {
//                    allCardDrawn.add(myplayer[i].getCard(x));
//                }
//
//            }
//
//            //Determination of the winner
//            String winner = findWinner(myplayer, dealer, nbrPlayer);
//            switch (winner) {
//
//                case "":
//                    System.out.println("Nobody got a better score than the dealer, everybody lose");
//                    break;
//                case "dr@@w":
//                    System.out.println("More than one person got a better score than the dealer, it's a draw !");
//                    break;
//                default:
//                    System.out.println("Well done " + winner + " you win !");
//                    break;
//            }
//        } else {
//            System.out.println("The dealer made " + dealer.getScore() + ", everybody wins !");
//        }

    }
}




