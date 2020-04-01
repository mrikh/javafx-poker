package org.rikh.model;

public class Pattern implements Comparable<Pattern>{

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
    private Card highestCard;

    //other card of necessary
    private Card otherCard;

    public Pattern(Combination combination, Card card){
        this.combination = combination;
        highestCard = card;
    }

    public Pattern(Combination combination, Card card, Card secondCard){
        this.combination = combination;
        highestCard = card;
        otherCard = secondCard;
    }

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

    public boolean isDecentCombination(){

        if (combination.getValue() == 1 && highestCard.getValue() > 10) {
            return true;
        }else if (combination.getValue() > 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return combination.toString() + " with highest card " + highestCard.toString();
    }

    public Card getHighestCard(){
        return highestCard;
    }

    public Card getSecondaryCard(){
        return otherCard;
    }
}
