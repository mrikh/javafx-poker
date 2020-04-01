package org.rikh.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Class to keep track of the cards user has in his hand
 */
public class Hand{

    //array of type cards
    public static int capacity = 5;
    private Card[] hand = new Card[capacity];

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
     * @return Returns one of the enum values after checking the hand.
     */
    public Pattern getBestHand(){

        Pattern royalFlush = royalFlushPattern();
        Pattern straightFlush = straightFlushPattern();
        Pattern fourKind = fourKindPattern();
        Pattern flush = flushPattern();
        Pattern straight = straightPattern();
        Pattern fullHouse = fullHousePattern();
        Pattern triplet = tripletPattern();
        Pattern twoPair = twoPairPattern();
        Pattern pair = pairPattern();

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
            return new Pattern(Pattern.Combination.NOTHING, new Card(1, 1));
        }
    }

    @Override
    public String toString() {
        return "Hand : " + Arrays.toString(hand);
    }

    public Card[] getCards(){
        return hand;
    }

    public Card getCard(int position) { return hand[position]; }

    public void updateCard(int position, Card card){
        hand[position] = card;
        Arrays.sort(hand);
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

    //Hand pattern checks
    private Pattern royalFlushPattern(){

        //king at one end and ace in other is not present
        if (hand[hand.length - 1].getValue() != 13 && hand[0].getValue() != 1){
            return null;
        }

        Card.Suit suit = hand[hand.length - 1].getSuit();
        for (int i = hand.length - 1; i > 1; i--){
            if ((hand[i].getValue() - hand[i - 1].getValue() != 1) || suit != hand[i].getSuit()){
                return null;
            }
        }
        return new Pattern(Pattern.Combination.ROYAL_FLUSH, hand[0]);
    }

    private Pattern straightFlushPattern(){

        Card.Suit suit = hand[hand.length - 1].getSuit();
        for (int i = hand.length - 1; i >= 0; i--){
            if ((hand[i].getValue() - hand[i - 1].getValue() != 1) || suit != hand[i].getSuit()){
                return null;
            }
        }
        return new Pattern(Pattern.Combination.STRAIGHT_FLUSH, hand[hand.length - 1]);
    }

    private Pattern straightPattern(){

        for (int i = hand.length - 1; i >= 0; i--){
            if ((hand[i].getValue() - hand[i - 1].getValue() != 1)){
                return null;
            }
        }
        return new Pattern(Pattern.Combination.STRAIGHT, hand[hand.length - 1]);
    }

    private Pattern flushPattern(){

        Card.Suit suit = hand[hand.length - 1].getSuit();
        for (int i = 0; i < hand.length; i++){
            if (suit != hand[i].getSuit()){
                return null;
            }
        }
        return new Pattern(Pattern.Combination.FLUSH, hand[hand.length - 1]);
    }

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
                secondPair = key;
            }
        }

        if (firstPair != -1 && secondPair != -1){
            return new Pattern(Pattern.Combination.TWO_PAIRS, new Card(1, firstPair), new Card(1, secondPair));
        }else{
            return null;
        }
    }

    private Pattern pairPattern(){

        HashMap<Integer, Integer> map = convertToHashMap();
        int counter = 0;

        for (Map.Entry<Integer, Integer> pair : map.entrySet()){
            Integer key = pair.getKey();
            Integer value = pair.getValue();

            if (value == 2){
                return new Pattern(Pattern.Combination.PAIR, new Card(1, key));
            }
        }

        return null;
    }

    private HashMap<Integer, Integer> convertToHashMap(){

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < hand.length; i++){
            int value = hand[i].getValue();
            if (map.get(value) == null){
                map.put(value, 1);
            }else{
                map.put(value, map.get(value) + 1);
            }
        }

        return map;
    }
}
