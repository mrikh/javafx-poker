package org.rikh.model;

import java.util.ArrayList;

public class Pattern {

    /**
     * Enum to keep track of the pattern the current hand displays
     */
    public enum Combination{
        STRAIGHT(4), FLUSH(3), TRIPLETS(2), PAIRS(1), NOTHING(0);
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
                case FLUSH:
                    return "Flush";
                case PAIRS:
                    return "Pairs";
                case NOTHING:
                    return "Nothing";
                case STRAIGHT:
                    return "Straight";
                case TRIPLETS:
                    return "Triplets";
            }

            return "Nothing";
        }
    }

    private Card[] cards;
    private Combination combination = Combination.NOTHING;

    public Pattern(Combination combination, Card[] cards){
        this.combination = combination;
        this.cards = cards;
    }
}
