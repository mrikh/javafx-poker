package org.rikh.model;

import org.rikh.utilities.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Class to keep track of the cards user has in his hand. This class was used in the week 7 exercise as well
 * but this was has been more heavily modified. Change the enum combination to pattern in this class to contain
 * card information for comparison as well. This was also heavily modified to introduce new methods.
 */
public class Hand{

    private Card[] hand = new Card[Constants.totalCardsInHand];

    /**
     * Constructor to create with specified values and sorts them.
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

        Arrays.sort(hand);
    }

    /**
     * Constructor to initialize the current hand with random cards.
     * @param cards Array of cards to check against if value already exists
     */
    public Hand(Card[] cards){

        hand = cards;
        //sort the hand to make things easier. If the future requires comparing which of the pairs is same
        Arrays.sort(hand);
    }

    /**
     * Get the pattern shown by the hand.
     * @return Returns an object of the pattern class containing our hand information. Combination, highest card and in some
     * cases secondary card
     */
    public Pattern getBestHand(){

        //TODO: Unnecessary to check for all combinations. Find better logic
        Pattern royalFlush = royalFlushPattern();
        Pattern straightFlush = straightFlushPattern();
        Pattern fourKind = fourKindPattern();
        Pattern flush = flushPattern();
        Pattern straight = straightPattern();
        Pattern fullHouse = fullHousePattern();
        Pattern triplet = tripletPattern();
        Pattern twoPair = twoPairPattern();
        Pattern pair = pairPattern();

        //check for hand and return appropriate hand depending on which is highest order of winning.
        if (royalFlush != null){
            return royalFlush;
        }else if (straightFlush != null){
            return  straightFlush;
        }else if (fourKind != null){
            return fourKind;
        }else if(flush != null){
            return flush;
        }else if (straight != null){
            return straight;
        }else if (fullHouse != null){
            return fullHouse;
        }else if(triplet != null){
            return triplet;
        }else if(twoPair != null){
            return twoPair;
        }else if(pair != null){
            return pair;
        }else{
            //Return nothing to ensure consistency.
            return new Pattern(Pattern.Combination.NOTHING, new Card(1, 1));
        }
    }

    /**
     * Convert to a readable string format
     */
    @Override
    public String toString() {
        return "Hand : " + Arrays.toString(hand);
    }

    /**
     * Getter method for the cards in the hand
     * @return Returns an array of card type objects.
     */
    public Card[] getCards(){
        return hand;
    }

    /**
     * Update the card in the users hand.
     * @param position Position of the card to update
     * @param card Card object to assign at the position
     */
    public void updateCard(int position, Card card){
        hand[position] = card;
        Arrays.sort(hand);
    }

    /*  ================= PRIVATE METHODS BELOW HERE ================= */

    /**
     * Determine if the hand is a royal flush
     * @return An object of class Pattern containing relevant information
     */
    private Pattern royalFlushPattern(){

        if (hand[hand.length - 1].getValue() != 13 && hand[0].getValue() != 1){
            //king at one end and ace in other is not present so it can't be royal flush
            return null;
        }

        Card.Suit suit = hand[hand.length - 1].getSuit();
        for (int i = hand.length - 1; i > 1; i--){
            //check if all the suits are the same and all follow the same sequence
            if ((hand[i].getValue() - hand[i - 1].getValue() != 1) || suit != hand[i].getSuit()){
                //return null if even if one thing doesnt match
                return null;
            }
        }
        return new Pattern(Pattern.Combination.ROYAL_FLUSH, hand[0]);
    }

    /**
     * Determine if its a straight flush pattern.
     * @return An object of class Pattern containing relevant information
     */
    private Pattern straightFlushPattern(){

        Card.Suit suit = hand[hand.length - 1].getSuit();
        for (int i = hand.length - 1; i >= 0; i--){
            if ((hand[i].getValue() - hand[i - 1].getValue() != 1) || suit != hand[i].getSuit()){
                return null;
            }
        }
        return new Pattern(Pattern.Combination.STRAIGHT_FLUSH, hand[hand.length - 1]);
    }

    /**
     * Determine if its a straight pattern.
     * @return An object of class Pattern containing relevant information
     */
    private Pattern straightPattern(){

        for (int i = hand.length - 1; i >= 0; i--){
            if ((hand[i].getValue() - hand[i - 1].getValue() != 1)){
                return null;
            }
        }
        return new Pattern(Pattern.Combination.STRAIGHT, hand[hand.length - 1]);
    }

    /**
     * Determine if its a flush pattern.
     * @return An object of class Pattern containing relevant information
     */
    private Pattern flushPattern(){

        Card.Suit suit = hand[hand.length - 1].getSuit();
        for (Card card : hand) {
            if (suit != card.getSuit()) {
                return null;
            }
        }
        return new Pattern(Pattern.Combination.FLUSH, hand[hand.length - 1]);
    }

    /**
     * Determine if its a four of a kind pattern.
     * @return An object of class Pattern containing relevant information
     */
    private Pattern fourKindPattern(){

        HashMap<Integer, Integer> map = convertToHashMap();

        for (Map.Entry<Integer, Integer> pair : map.entrySet()){
            Integer key = pair.getKey();
            Integer value = pair.getValue();

            if (value == 4){
                //since we don't care about the suit in this case, initializing with random suit
                return new Pattern(Pattern.Combination.FOUR_KIND, new Card(1, key));
            }
        }

        return null;
    }

    /**
     * Determine if its a full house pattern.
     * @return An object of class Pattern containing relevant information
     */
    private Pattern fullHousePattern(){

        HashMap<Integer, Integer> map = convertToHashMap();
        int tripletValue = -1;
        int pairValue = -1;

        for (Map.Entry<Integer, Integer> pair : map.entrySet()){
            Integer key = pair.getKey();
            Integer value = pair.getValue();

            if (value == 3){
                tripletValue = key;
            }else if(value == 2){
                pairValue = key;
            }
        }

        if (pairValue != -1 && tripletValue != -1){
            return new Pattern(Pattern.Combination.FULL_HOUSE, new Card(1, tripletValue), new Card(1, pairValue));
        }else{
            return null;
        }
    }

    /**
     * Determine if its a triplet pattern.
     * @return An object of class Pattern containing relevant information
     */
    private Pattern tripletPattern(){

        HashMap<Integer, Integer> map = convertToHashMap();

        for (Map.Entry<Integer, Integer> pair : map.entrySet()){
            Integer key = pair.getKey();
            Integer value = pair.getValue();

            if (value == 3){
                return new Pattern(Pattern.Combination.TRIPLETS, new Card(1, key));
            }
        }

        return null;
    }

    /**
     * Determine if its a two pair pattern.
     * @return An object of class Pattern containing relevant information
     */
    private Pattern twoPairPattern(){

        HashMap<Integer, Integer> map = convertToHashMap();
        int firstPair = -1;
        int secondPair = -1;

        for (Map.Entry<Integer, Integer> pair : map.entrySet()){
            Integer key = pair.getKey();
            Integer value = pair.getValue();

            if (value == 2 && firstPair == -1){
                firstPair = key;
            }else if (value == 2){
                //first card will always be greater
                if (key > firstPair){
                    secondPair = firstPair;
                    firstPair = key;
                }else{
                    secondPair = key;
                }
            }
        }


        if (firstPair != -1 && secondPair != -1){
            return new Pattern(Pattern.Combination.TWO_PAIRS, new Card(1, firstPair), new Card(1, secondPair));
        }else{
            return null;
        }
    }

    /**
     * Determine if its a single pair pattern.
     * @return An object of class Pattern containing relevant information
     */
    private Pattern pairPattern(){

        HashMap<Integer, Integer> map = convertToHashMap();

        for (Map.Entry<Integer, Integer> pair : map.entrySet()){
            Integer key = pair.getKey();
            Integer value = pair.getValue();

            if (value == 2){
                return new Pattern(Pattern.Combination.PAIR, new Card(1, key));
            }
        }

        return null;
    }

    /**
     * Private method to convert the hand to a hashmap with each key containing the count of that card in the hand.
     * @return Hashmap containing keys as card values and in the value part will be the number of times that card number appears
     */
    private HashMap<Integer, Integer> convertToHashMap(){

        HashMap<Integer, Integer> map = new HashMap<>();
        for (Card card : hand) {
            int value = card.getValue();
            if (map.get(value) == null) {
                map.put(value, 1);
            } else {
                map.put(value, map.get(value) + 1);
            }
        }

        return map;
    }
}
