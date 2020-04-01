package org.rikh.controller;
import org.rikh.model.Card;
import org.rikh.model.Hand;
import org.rikh.model.Pattern;
import org.rikh.utilities.Constants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Logic class for the program. Created to keep UI segregated and UI changes should not impact the logic part.
 * This will contain all card related values and token count. The app class will reference this to get all data related information.
 */
public class PokerController {

    /**
     * Instance of your hand
     */
    private Hand playerHand;

    /**
     * Instance of the computers hand
     */
    private Hand opponentHand;

    /**
     * Arraylist created to keep a track of the cards in deck.
     */
    private ArrayList<Card> deck = new ArrayList<>();

    /**
     * Players token. This will have the updated values during the entire run.
     */
    private int playerToken = Constants.initialPlayerCoins;
    /**
     * Opponents (computers) tokens. Updated value during the entire program run.
     */
    private int opponentToken = Constants.initialOpponentCoins;
    /**
     * Coins present in the pot at all times.
     */
    private int coinsInPot = 0;

    /**
     * Flow manager variable. If true, this means user is in the card discarding process
     */
    public boolean startDiscard = false;
    /**
     * Flow manager variable. This means that the user has done the discard part and started the betting flow.
     */
    public boolean completedDiscard = false;
    /**
     * Final flow variable. If true this means that the victory message is displayed.
     */
    public boolean showCard = false;

    /**
     * Keep track of what cards the user has chosen to be discarded. This will contain the card positioning dependent on the cards in hand.
     */
    private ArrayList<Integer> selectedCardPosition = new ArrayList<>();

    /**
     * Constructor to setup the initial variables and layout properties.
     */
    public PokerController(){

       initializeDecks();
    }

    /**
     * Method to reset the entire layout and the flow. This method is called after the user chooses to continue after winning or losing
     */
    public void reset(){

        initializeDecks();
        startDiscard = false;
        completedDiscard = false;
        showCard = false;
        clearCards();
    }

    /**
     * The logic initialization method. This will create the deck and assign cards to each player.
     */
    private void initializeDecks(){

        //generate deck
        int counter = 0;
        for(int i = 1; i <= 4; i++){
            for(int j = 1; j <= 13; j++){
                deck.add(new Card(i, j));
            }
        }

        //generate hands
        playerHand = new Hand(cardsForHand());
        opponentHand = new Hand(cardsForHand());
    }

    /**
     * Method that contains the logic for deciding the winner between two users.
     * @param gaveUp Boolean value that informs you if the opponent gave up or called your bet.
     * @return Returns the message to display on deciding the winner
     */
    public String determineWinner(boolean gaveUp){

        //Get the best possible hand for the two people in the game
        Pattern playerPattern = playerHand.getBestHand();
        Pattern opponentPattern = opponentHand.getBestHand();

        //Compare the pattern for the users
        int comparison = playerPattern.compareTo(opponentPattern);
        //local variable to keep track of coins
        int tempCoinsInPot = coinsInPot;
        //reset coins in the pot at end of the round. As they will be given away.
        coinsInPot = 0;

        //If opponent withdrew their hand.
        if (gaveUp){
            //add all the coins to the users hand.
            playerToken += tempCoinsInPot;
            return "Player wins cause opponent gave up";
        }else{
            if (comparison == 1){
                //if player wins
                playerToken += tempCoinsInPot;
                return "Player wins with a " + playerPattern.toString();
            }else if(comparison == -1){
                //if opponent wins
                opponentToken += tempCoinsInPot;
                return "Opponent wins with a " + opponentPattern.toString();
            }else{
                //check highest card as combination was a match
                Card playerHighestCard = playerPattern.getHighestCard();
                Card opponentHighestCard = opponentPattern.getHighestCard();

                if (playerHighestCard.compareTo(opponentHighestCard) == 1){
                    //player has the highest card
                    playerToken += tempCoinsInPot;
                    return "Player wins with a " + playerPattern.toString();
                }else if (playerHighestCard.compareTo(opponentHighestCard) == -1){
                    //opponent has the highest card
                    opponentToken += tempCoinsInPot;
                    return "Opponent wins with a " + opponentPattern.toString();
                }else{

                    //Check secondary card highest card is the same
                    Card playerSecondaryCard = playerPattern.getSecondaryCard();
                    Card opponentSecondaryCard = opponentPattern.getSecondaryCard();

                    //if anyone is null, its a draw as the value is not null. This will only be for 2 pairs or full house.
                    if(playerSecondaryCard == null || opponentSecondaryCard == null){
                        //Draw as these values are not present
                        playerToken += tempCoinsInPot/2.0;
                        opponentToken += tempCoinsInPot/2.0;
                        return "It is a draw!";
                    }else{
                        if (playerSecondaryCard.compareTo(opponentSecondaryCard) == 1){
                            //if player secondary card is bigger, player wins
                            playerToken += tempCoinsInPot;
                            return "Player wins with a " + playerPattern.toString() + " and secondary card with value of " + playerSecondaryCard.toString();
                        }else if (playerHighestCard.compareTo(opponentHighestCard) == -1){
                            //if opponent secondary card is bigger, he wins.
                            opponentToken += tempCoinsInPot;
                            return "Opponent wins with a " + playerPattern.toString() + " and secondary card with value of " + opponentSecondaryCard.toString();
                        }else{
                            //draw as secondary cards are also the same
                            playerToken += tempCoinsInPot/2.0;
                            opponentToken += tempCoinsInPot/2.0;
                            return "It is a draw!";
                        }
                    }
                }
            }
        }
    }

    /**
     * Clear selected cards.
     */
    public void clearCards(){
        selectedCardPosition.clear();
    }

    /**
     * Update card selection in array list.
     * @param position Position of card in hand to be selected
     * @return Returns true if successfully added the card and false otherwise
     */
    public boolean updateCard(int position){

        //check if passed position is in array list
        int index = selectedCardPosition.indexOf(position);
        if (index == -1){
            //position passed not found. Which means card wasn't selected before
            selectedCardPosition.add(position);
            return true;
        }else{
            //card selected so we remove it.
            selectedCardPosition.remove(index);
            return false;
        }
    }

    /**
     * Method to check if card can be selected
     * @return Returns true if successfully can add the card.
     */
    public boolean canUpdateCardSelection(int position){

        if (selectedCardPosition.contains(position)){
            //allow removal of card. But not addition.
            return true;
        }
        return selectedCardPosition.size() <= Constants.selectableCards;
    }

    /**
     * Logic for determining if opponent has to fold.
     * @return true if opponent should fold
     */
    public boolean shouldOpponentFold(){
        Pattern computer = opponentHand.getBestHand();
        return !computer.isDecentCombination();
    }

    /**
     * Change the cards in the players hands and opponenets hands.
     */
    public void changeCards(){
        Random rand = new Random();

        for (int i = 0; i < selectedCardPosition.size(); i++){
            int value = rand.nextInt(deck.size() - 1) + 1;
            Card temp = deck.remove(value);
            playerHand.updateCard(selectedCardPosition.get(i), temp);
        }

        //random number of cards to choose to replace.
        int number = rand.nextInt(Constants.selectableCards - 1) + 1;
        for (int i = 0; i < number; i++){
            //will always update in the same sequence but different number of cards
            int value = rand.nextInt(deck.size() - 1) + 1;
            Card temp = deck.remove(value);
            opponentHand.updateCard(i, temp);
        }
    }

    /**
     * Update the pot with passed values. Also changes the tokens that each player has.
     * @param playerToken Player tokens to remove
     * @param opponentToken Opponenet tokens to remove
     */
    public void updatePot(int playerToken, int opponentToken){

        this.playerToken = Math.max(0, this.playerToken - playerToken);
        this.opponentToken = Math.max(0, this.opponentToken - opponentToken);

        //max coins
        coinsInPot = (Constants.initialPlayerCoins + Constants.initialOpponentCoins) - (this.playerToken + this.opponentToken);
    }

    /**
     * Getter method for coins in the pot
     * @return Returns the coins in the pot.
     */
    public int getCoinsInPot(){
        return coinsInPot;
    }

    /**
     * Getter method for player coins
     * @return Returns player coins
     */
    public int getPlayerToken(){
        return playerToken;
    }

    /**
     * Getter method for opponent Coins
     * @return Returns opponents remaining coins
     */
    public int getOpponentToken(){
        return opponentToken;
    }

    /**
     * Getter method for player cards
     * @return Returns an array of strings that are properly formatted for the cards.
     */
    public String[] playerCards(){
        return cardsString(playerHand);
    }

    /**
     * Getter method for opponents cards
     * @return Returns an array of strings that are properly formatted for the cards.
     */
    public String[] opponentCards(){
        return cardsString(opponentHand);
    }

    /**
     * ================= PRIVATE METHODS BELOW HERE =================
     */

    /**
     * Method to generate 5 cards from the deck.
     * @return An array of 5 cards
     */
    private Card[] cardsForHand(){
        Card[] tempArray = new Card[Constants.totalCardsInHand];
        Random rand = new Random();
        for (int i = 0; i < Constants.totalCardsInHand; i++) {
            int value = rand.nextInt(deck.size() - 1) + 1;
            Card temp = deck.remove(value);
            tempArray[i] = temp;
        }
        return tempArray;
    }

    /**
     * Get array of card names
     * @param hand Hand to get card names for
     * @return Array of string containing card names
     */
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
