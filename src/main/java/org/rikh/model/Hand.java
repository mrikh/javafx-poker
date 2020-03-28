package org.rikh.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Class to keep track of the cards user has in his hand
 */
public class Hand {

    //array of type cards
    public static int capacity = 5;
    private Card[] hand = new Card[capacity];

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

    /**
     * Constructor to create with specified values
     * @param suit Suit to assign. 0 if random suit
     * @param values Array of values to create cards with
     */
    public Hand(int suit, int[] values){
        if (suit == 0){
            Random rand = new Random();
            suit = rand.nextInt(4 - 1) + 1;
        }

        for(int i = 0; i < values.length; i++){
            hand[i] = new Card(suit, values[i]);
        }
    }

    /**
     * Constructor to initialize the current hand with random cards.
     * @param previousHand Array of cards to check against if value already exists
     */
    public Hand(Card[] previousHand){

        for (int i = 0; i < hand.length; i++){

            Card card = new Card();
            while(cardExists(card, hand) || cardExists(card, previousHand)){
                card = new Card();
            }
            hand[i] = card;
        }

        //sort the hand to make things easier. If the future requires comparing which of the pairs is same
        for (int i = 0; i< hand.length; i++){
            for(int j = 0; j < hand.length; j++){
                if (hand[i].greaterThan(hand[j])){
                    Card temp = hand[j];
                    hand[j] = hand[i];
                    hand[i] = temp;
                }
            }
        }
    }

    /**
     * Get the pattern shown by the hand.
     * @return Returns one of the enum values after checking the hand.
     */
    public Combination getCombination(){
        boolean isStraight = true;
        boolean isFlush = true;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        //wanted to use one loop to do all checks
        for (int i = 0; i < hand.length; i++){
            //didnt want app to crash as we are using i + 1 value checks so put an if condition
            if (i != hand.length - 1) {
                if (hand[i + 1].getValue() - hand[i].getValue() != 1) {
                    isStraight = false;
                }
                if (hand[i + 1].getSuit() != hand[i].getSuit()) {
                    isFlush = false;
                }
            }

            //put values into hashmap to keep track of how many times a number comes
            int value = hand[i].getValue();
            if (map.get(value) == null){
                map.put(value, 1);
            }else{
                map.put(value, map.get(value) + 1);
            }
        }

        if (isStraight){
            return Combination.STRAIGHT;
        }else if (isFlush){
            return Combination.FLUSH;
        }else{
            //Iterate over hashmap to find triplets and pairs
            for (Map.Entry<Integer, Integer> pair : map.entrySet()){
                Integer key = pair.getKey();
                Integer value = pair.getValue();

                if (value == 3){
                    return Combination.TRIPLETS;
                }else if(value == 2){
                    return Combination.PAIRS;
                }
            }

            return Combination.NOTHING;
        }
    }

    @Override
    public String toString() {
        return "Hand : " + Arrays.toString(hand);
    }

    public Card[] getCards(){
        return hand;
    }

    /**
     * Check if the card is present inside the array that is passed
     * @param card Card to check if it exists
     * @param array Array to check inside for the card
     * @return Returns a boolean value. True if it exists else false
     */
    private boolean cardExists(Card card, Card[] array){
        for (int i = 0; i < array.length; i++){
            if (array[i] == null){
                continue;
            }

            if (card.toString().equalsIgnoreCase(array[i].toString())){
                return true;
            }
        }

        return false;
    }
}
