package org.rikh.model;

import org.rikh.utilities.Constants;

/**
 * Class containing a particular hand's winning combination information
 */
public class Pattern implements Comparable<Pattern>{

    /**
     * Enum containing combination information. Associated values allow us to compare the enum values.
     */
    enum Combination{
        ROYAL_FLUSH(9), STRAIGHT_FLUSH(8), FOUR_KIND(7), FLUSH(6), STRAIGHT(5), FULL_HOUSE(4), TRIPLETS(3), TWO_PAIRS(2), PAIR(1), NOTHING(0);
        //value associated with the enum for easy comparison
        private int value;

        /**
         * Constructor to initialize value
         * @param i Value to set the associated value to
         */
        Combination(int i) {
            value = i;
        }

        /**
         * Method to get value associated with enum
         * @return Value with the current enum
         */
        public int getValue(){
            return value;
        }

        /**
         * Return value as a string
         */
        @Override
        public String toString() {
            switch (this){
                case ROYAL_FLUSH:
                    return Constants.kRoyalFlush;
                case STRAIGHT_FLUSH:
                    return Constants.kStraightFlush;
                case FOUR_KIND:
                    return Constants.kFourKind;
                case FLUSH:
                    return Constants.kFlush;
                case STRAIGHT:
                    return Constants.kStraight;
                case FULL_HOUSE:
                    return Constants.kFullHouse;
                case TWO_PAIRS:
                    return Constants.kTwoPair;
                case PAIR:
                    return Constants.kOnePair;
                case NOTHING:
                    return Constants.kNothing;
                case TRIPLETS:
                    return Constants.kTriplets;
            }
            return Constants.kNothing;
        }
    }

    private Combination combination;
    /**
     * Highest card in the combination
     */
    private Card highestCard;

    /**
     * Secondary card information if needed. Like in the case of two pairs and full house.
     */
    private Card otherCard;

    /**
     * Constructor to initialize the pattern.
     * @param combination Combination to initialize the pattern object with
     * @param card Card object to be passed as the highest card
     */
    public Pattern(Combination combination, Card card){
        this.combination = combination;
        highestCard = card;
    }

    /**
     * Constructor to initialize the class pattern with highest and other information card. The secondary card will contain a value
     * in the case of full house or two pairs or another future combination that may require information of 2 cards
     * @param combination Combination to initialize the pattern object with
     * @param card Highest card in the pattern
     * @param secondCard Secondary information card in the pattern.
     */
    public Pattern(Combination combination, Card card, Card secondCard){
        this.combination = combination;
        highestCard = card;
        otherCard = secondCard;
    }

    /**
     * Interface compulsory method to compare two objects of the current class.
     * @param o Object to compare the current object with.
     * @return 1 if current greater than passed object. -1 if current less than passed object and 0 if they are equal.
     */
    @Override
    public int compareTo(Pattern o) {
        return Integer.compare(this.combination.getValue(), o.combination.getValue());
    }

    /**
     * Seperate method made for the computer (currently) to determine if it has a high enough pattern to continue further. This method
     * can be changed without impacting the outside logic. Right now if its a pair with a value of at least jacks or a better hand that that
     * it will work.
     *
     * @return True if its a combination you won't fold for.
     */
    public boolean isQualifyingHand(){

        if (combination.getValue() == 1 && highestCard.getValue() > 10) {
            return true;
        }else {
            return combination.getValue() > 1;
        }
    }

    /**
     * Convert the Pattern to a readable string format
     * @return String in a pretty format with the description
     */
    @Override
    public String toString() {
        return combination.toString() + Constants.kWithHighestCard + highestCard.toString();
    }

    /**
     * Getter method for the highest card in the hand.
     * @return Object of class Card
     */
    public Card getHighestCard(){
        return highestCard;
    }

    /**
     * Getter method for the secondary information card in the hand
     * @return Object of class Card
     */
    public Card getSecondaryCard(){
        return otherCard;
    }
}
