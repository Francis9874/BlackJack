package com.st20211374.blackjack.server;

import java.util.ArrayList;
import java.util.List;


public class Player {

    private String username;
    private ArrayList<Card> cards;
    private boolean busted;
    private boolean isUsernameSet;
    private PlayerSocket playerSocket; 

    //basic public constructor
    public Player(String username, PlayerSocket playerSocket) {
        this.cards = new ArrayList<>();
        this.username = username;
        this.busted = false;
        this.isUsernameSet = false;
        this.playerSocket = playerSocket;
    }

    public Player(ArrayList<Card> cards) {
        this.cards = cards;
        this.busted = false;
    }

    //object methods
    public int getScore() {
        int score = 0;

        for (Card card : this.cards) {
            score += card.getScore();
        }

        return score;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    //Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Card> getCardList() {
        return cards;
    }

    public List<String> getCardNames() {
        List<String> cardNames = new ArrayList<>();

        for (Card card : cards) {
            String name = card.getCardNbr() + " of " + card.getCardType().name().toLowerCase();
            cardNames.add(name);
        }

        return cardNames;
    }

    public void setBusted(boolean busted) {
        this.busted = busted;
    }

    public boolean isBusted() {
        return busted;
    }
    
    public PlayerSocket getPlayerSocket() {
        return playerSocket;
    }
    
    public void setPlayerSocket(PlayerSocket playerSocket) {
        this.playerSocket = playerSocket;
    }
    
}