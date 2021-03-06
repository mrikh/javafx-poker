package org.rikh.model;
import org.rikh.utilities.Constants;

import java.util.Random;

/**
 * Class to hold all card information. This class was used in the week 7 exercise and has been modified
 * to add the comparable interface.
 */
public class Card implements Comparable<Card>{

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
        value = random.nextInt(14 - 1) + 1;

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
     * Return a pretty format for the card class values
     */
    @Override
    public String toString() {

        String temp = "";

        switch (value){
            case 1:
                temp = Constants.kAce;
                break;
            case 11:
                temp = Constants.kJack;
                break;
            case 12:
                temp = Constants.kQueen;
                break;
            case 13:
                temp = Constants.kKing;
                break;
            default:
                temp += value;
        }

        temp += " of ";

        switch (suit){
            case CLUBS:
                temp += Constants.kClubs;
                break;
            case DIAMONDS:
                temp += Constants.kDiamonds;
                break;
            case HEARTS:
                temp += Constants.kHearts;
                break;
            case SPADES:
                temp += Constants.kSpades;
                break;
        }

        return temp;
    }

    /**
     * Interface method to compare two Card class objects. Ignores the suit. Since ace is the biggest card, we will use that first as
     * if we apply our direct logic otherwise, ace has the lowest assigned number. Simply changing it to 14 breaks the straight match logic
     * and is only really necessary for card comparisons so did it here.
     *
     * @param o Second object to compare against
     * @return Returns 1 if object of card is greater than argument card. -1 if less and 0 if equal
     */
    @Override
    public int compareTo(Card o) {

        if (this.value != o.value){
            if (this.value == 1){
                return 1;
            }else if (o.value == 1){
                return -1;
            }
        }

        return Integer.compare(this.value, o.value);
    }

    /*  ================= PRIVATE METHODS BELOW HERE ================= */

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
