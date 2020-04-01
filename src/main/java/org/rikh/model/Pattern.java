package org.rikh.model;

/**
 * Class containing a particular hand's winning combination information
 */
public class Pattern implements Comparable<Pattern>{

    /**
     * Enum containing combination information. Associated values allow us to compare the enum values.
     */
    public enum Combination{
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

        @Override
        /**
         * Return value as a string
         */
        public String toString() {
            switch (this){
                case ROYAL_FLUSH:
                    return "Royal Flush";
                case STRAIGHT_FLUSH:
                    return "Straight Flush";
                case FOUR_KIND:
                    return "Four of a Kind";
                case FLUSH:
                    return "Flush";
                case STRAIGHT:
                    return "Straight";
                case FULL_HOUSE:
                    return "Full House";
                case TWO_PAIRS:
                    return "Two Pairs";
                case PAIR:
                    return "One Pair";
                case NOTHING:
                    return "Nothing";
                case TRIPLETS:
                    return "Triplets";
            }
            return "Nothing";
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
        if (this.combination.getValue() > o.combination.getValue()){
            return 1;
        }else if (this.combination.getValue() < o.combination.getValue()){
            return -1;
        }else{
            return 0;
        }
    }

    /**
     * Seperate method made for the computer (currently) to determine if it has a high enough pattern to continue further. This method
     * can be changed without impacting the outside logic. Right now if its a pair with a value of at least jacks or a better hand that that
     * it will work.
     *
     * @return True if its a combination you won't fold for.
     */
    public boolean isDecentCombination(){

        if (combination.getValue() == 1 && highestCard.getValue() > 10) {
            return true;
        }else if (combination.getValue() > 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Convert the Patthern to a readable string format
     * @return
     */
    @Override
    public String toString() {
        return combination.toString() + " with highest card " + highestCard.toString();
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
