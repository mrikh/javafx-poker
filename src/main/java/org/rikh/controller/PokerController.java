package org.rikh.controller;

import org.rikh.model.Card;
import org.rikh.model.Hand;

public class PokerController {

    private Hand playerHand;
    private Hand opponentHand;

    public PokerController(){
        playerHand = new Hand(new Card[0]);
        opponentHand = new Hand(playerHand.getCards());
    }

    public String[] playerCards(){
        return cardsString(playerHand);
    }

    //private
    private String[] cardsString(Hand hand){
        Card[] playerCards = hand.getCards();
        String[] temp = new String[playerCards.length];
        for (int i = 0; i < playerCards.length; i++){
            Card card = playerCards[i];
            temp[i] = card.toString();
        }
        return temp;
    }
}
