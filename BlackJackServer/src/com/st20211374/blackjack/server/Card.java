package com.st20211374.blackjack.server;

class Card {
    //A class used to transmit information from cardDraw to Player
    private String cardNbr;
    CardType cardType;
    int score;
    String cardName;

    Card(CardType type, String number) {
        this.cardType = type;
        this.cardNbr = number;
        this.score = this.scoreCalculation(number);
    }

    //Getters and Setters
    public String getCardNbr() { //useless
        return cardNbr;
    }

    public CardType getCardType() {//useless
        return cardType;
    }

    public void setCardType(CardType cardType) {//useless
        this.cardType = cardType;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCard() {
        return this.cardName;
    }

    private int scoreCalculation(String rdmCardNbr) {
        int score;

        if (rdmCardNbr == "Ace") {
            score = 1;
            return score;
        } else if (rdmCardNbr.length() <= 2) {
            score = Integer.parseInt(rdmCardNbr);
            return score;
        } else {
            score = 10;
            return score;
        }
    }
}

