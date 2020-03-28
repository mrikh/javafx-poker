package org.rikh.model;

import java.util.Random;

/**
 * Class to hold all card information
 */
public class Card  {

    /**
     * Enum to keep track of the suit
     */
    enum Suit{
        SPADES, CLUBS, DIAMONDS, HEARTS
    }

    private Suit suit;
    private int value;

    /**
     * Constructor to create a card.
     */
    public Card(){

        Random random = new Random();
        value = random.nextInt(14 - 2) + 2;

        int suit = random.nextInt(4 - 1) + 1;
        this.suit = createSuit(suit);
    }

    /**
     * Constructor to create card with specified value
     * @param suit Suit to assign
     * @param value Value to assign
     */
    public Card(int suit, int value){
        this.value = value;
        this.suit = createSuit(suit);
    }

    /**
     * Method to get value of the current card
     * @return Returns an integer containing card number
     */
    public int getValue(){
        return value;
    }

    /**
     * Method to get value of current suit
     * @return Returns the current suit
     */
    public Suit getSuit(){ return suit;}

    /**
     * Method to check if one card is greater than the card passed as the argument based on their numeric value
     * @param card Param to compare against.
     * @return Boolean, true if 'this' is greater than card passed as argument
     */
    public boolean greaterThan(Card card){
        return value > card.value;
    }


    @Override
    /**
     * Return a pretty format for the card class values
     */
    public String toString() {

        String temp = "";

        switch (value){
            case 14:
                temp = "Ace";
                break;
            case 11:
                temp = "Jack";
                break;
            case 12:
                temp = "Queen";
                break;
            case 13:
                temp = "King";
                break;
            default:
                temp += value;
        }

        temp += " of ";

        switch (suit){
            case CLUBS:
                temp += "Clubs";
                break;
            case DIAMONDS:
                temp += "Diamonds";
                break;
            case HEARTS:
                temp += "Hearts";
                break;
            case SPADES:
                temp += "Spades";
                break;
        }

        return temp;
    }

    /**
     * Create enum from integer for suits
     * @param suit Enum value to generate suit for
     * @return Returns enum value of the suit
     */
    private Suit createSuit(int suit){
        switch (suit){
            case 1:
                return Suit.SPADES;
            case 2:
                return Suit.CLUBS;
            case 3:
                return Suit.DIAMONDS;
            default:
                return Suit.HEARTS;
        }
    }
}
