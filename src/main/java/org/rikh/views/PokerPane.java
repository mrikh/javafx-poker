package org.rikh.views;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.rikh.utilities.Constants;

/**
 * Class that inherits from the default pane class. This was done to have the same initialization as the pane class and same methods.
 * But the UI should be auto created when using this class. Preferred using absolute positioning by making it relative to screen width
 * as spacing in grid layout seemed a bit weird for me. Was unable to accurately place elements and using maths to lay them out seemed easier.
 * Some of the positions are hit and trial based on what i found to look good.
 */
public class PokerPane extends Pane {

    //use default values if nothing passed
    public double width;
    public double height;

    /**
     * Event handler for the card click action. This is assigned a value when the appropriate flow is started from our app class.
     */
    public EventHandler<MouseEvent> cardClickAction;

    //Instance variables to keep track of UI elements we create to not have to find them every time on the UI.
    private TokenPane playerTokenPane;
    private TokenPane opponentTokenPane;
    private TokenPane potTokenPane;

    private CardsPane opponentCardPane;
    private CardsPane playerCardPane;

    /**
     * Constructor to create a normal pane with Poker related UI setup.
     * @param width Width of the pane to use
     * @param height Height of the pane to use
     * @param playerCards Array of string to display player cards. Pass null if you want to show ?
     * @param opponentCards Array of string to display the opponent cards. Pass null if you want to show ?
     */
    public PokerPane(double width, double height, String[] playerCards, String[] opponentCards){
        this.width = width;
        this.height = height;
        //set background color of the entire pane. Don't wanna put it inside initialsetup as no pointing in setting color again and again.
        setStyle("-fx-background-color: green");
        initialSetup(playerCards, opponentCards, Constants.initialPlayerCoins, Constants.initialOpponentCoins, 0);
    }

    /**
     * Method to update the positioning of elements based on passed screen width and height
     * @param width Width of the screen
     * @param height Height of the screen
     * @param playerCards Array of string to display player cards. Pass null if you want to show ?
     * @param opponentCards Array of string to display the opponent cards. Pass null if you want to show ?
     */
    public void updateLayout(double width, double height, String[] playerCards, String[] opponentCards, int playerTokens, int opponentTokens, int potTokens){
        getChildren().clear();
        this.width = width;
        this.height = height;
        initialSetup(playerCards, opponentCards, playerTokens, opponentTokens, potTokens);
    }

    /**
     * Update player tokens
     * @param tokens Value of tokens to set on the player side
     */
    public void updatePlayerTokens(int tokens){
        playerTokenPane.getChildren().clear();
        playerTokenPane.updateCoins(tokens);
    }

    /**
     * Update opponents Tokens
     * @param tokens Value of tokens to set on opponent side
     */
    public void updateOpponentTokens(int tokens){
        opponentTokenPane.getChildren().clear();
        opponentTokenPane.updateCoins(tokens);
    }

    /**
     * Update tokens in the pot
     * @param tokens Value of tokens to set in the pot
     */
    public void updatePotTokens(int tokens){
        potTokenPane.getChildren().clear();
        potTokenPane.updateCoins(tokens);
    }

    /**
     * Getter method to find out the width of the card pane. Used for laying out other elements
     * @return Double value of card width.
     */
    public double getCardPaneWidth(){
        return playerCardPane.getTotalWidth();
    }

    /**
     * Getter method to find out the player card y coordinate. Used for laying out elements
     * @return Double value of card pane y coordinate
     */
    public double getPlayerCardPaneY(){
        return playerCardPane.getLayoutY();
    }

    /**
     * Getter method to get y coordinate of opponent card. Used for element layout
     * @return Double value of opponent card pane
     */
    public double getOpponentCardPaneY(){
        return opponentCardPane.getLayoutY();
    }

    /**
     * Getter method to get card height
     * @return Double value of card height
     */
    public double getCardHeight(){
        return playerCardPane.getCardHeight();
    }

    /**
     * Generic method to display a message on the screen
     * @param message String to display
     */

    public void showText(String message){

        Text text = new Text(message);
        text.setFont(Font.font(20));
        text.setX(0);
        text.setY(height/2.0);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(width);
        getChildren().add(text);
    }

    /*  ================= PRIVATE METHODS BELOW HERE ================= */

    /**
     * Method to setup all elements on the screen.
     * @param playerCards String of player cards to display to the user. Pass null if you want to display ?
     * @param opponentCards String of opponent cards to display. Pass null if you want to display ?
     * @param playerTokens Count of the users tokens
     * @param opponentTokens Count of the opponents tokens
     * @param potTokens Count of the tokens in the pot.
     */
    private void initialSetup(String[] playerCards, String[] opponentCards, int playerTokens, int opponentTokens, int potTokens){

        double centerX = width/2.0;

        setupPotArea();

        //Opponent card area
        opponentCardPane = new CardsPane(width, opponentCards);
        //Used simple maths to figure out the starting X coordinate. Since we want it centered on screen. We take half the screen width and
        //shift it to the left by half the width of the pane
        double opponentStartX = centerX - opponentCardPane.getTotalWidth()/2.0;
        opponentCardPane.setLayoutX(opponentStartX);
        //30 pixels from top statically
        opponentCardPane.setLayoutY(30.0);
        getChildren().add(opponentCardPane);

        //Player card area.
        playerCardPane = new CardsPane(width, playerCards);
        double playerStartX = centerX - playerCardPane.getTotalWidth()/2.0;
        playerCardPane.setLayoutX(playerStartX);
        //The height is set to be 30 pixels from the bottom of the screen. Subtract the card height even further to move the starting point
        // by that much as it will draw starting from the point to the bottom of the screen. As coordinates move top to bottom.
        playerCardPane.setLayoutY(height - 30.0 - playerCardPane.getCardHeight());
        getChildren().add(playerCardPane);

        //Setup Opponent Token pane
        this.opponentTokenPane = new TokenPane(opponentTokens);
        this.opponentTokenPane.setLayoutX(width/2.0 - this.opponentTokenPane.getTotalWidth()/2.0);
        //to setup opponent token pane y coordinate, we get the y coordinate of the card pane and add the height with some spacing.
        this.opponentTokenPane.setLayoutY(opponentCardPane.getLayoutY() + 10.0 + opponentCardPane.getCardHeight());
        getChildren().add(this.opponentTokenPane);

        this.playerTokenPane = new TokenPane(playerTokens);
        this.playerTokenPane.setLayoutX(width/2.0 - this.playerTokenPane.getTotalWidth()/2.0);
        //for the player token, we get the y coordinate - token height and some spacing as we need to drop above the card pane. So we subtract
        this.playerTokenPane.setLayoutY(playerCardPane.getLayoutY() - 10.0 - this.playerTokenPane.getTokenHeight());
        getChildren().add(this.playerTokenPane);

        //Initially the pane is empty. We want it at the center of the screen.
        potTokenPane = new TokenPane(potTokens);
        potTokenPane.setLayoutY(height/2.0);
        potTokenPane.setLayoutX(width/2.0);
        getChildren().add(potTokenPane);

        Button quitButton = new Button();
        quitButton.setOnAction(actionEvent -> {
            Platform.exit();
        });
        quitButton.setText("Quit");
        quitButton.setId(Constants.quit);
        quitButton.setLayoutX(width - 75.0);
        quitButton.setLayoutY(25.0);
        getChildren().add(quitButton);
    }

    /**
     * This method will set up the victory message and continue button in the center of the screen.
     * @param message Message string to be displayed in the center of the screen when round is over.
     * @param continueAction Event handler for the button action.
     */
    public void showFinalMessage(String message, EventHandler<ActionEvent> continueAction){
        Text text = new Text(message);
        text.setFont(Font.font(20));
        text.setX(0);
        text.setY(height/2.0);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(width);
        getChildren().add(text);

        Button doneButton = new Button();
        doneButton.setText("Continue");
        doneButton.setOnAction(continueAction);
        doneButton.setLayoutY(height/2.0 + 40.0);
        doneButton.setLayoutX(width/2.0);
        getChildren().add(doneButton);
    }

    /**
     * Assign the card click listener to the appropriate cards.
     */
    public void allowSelection(){
        playerCardPane.startCardClickListen(cardClickAction);
    }

    /**
     * Method to update selection of the card on click.
     * @param id Id of the card to select
     * @param select Boolean to determine whether we want to select ot deselect the view.
     */
    public void updateCardStatus(String id, boolean select){
        playerCardPane.updateCardSelection(id, select);
    }

    /**
     * Method to setup the central pot area with white line and circle
     */
    private void setupPotArea(){

        Circle circle = new Circle();
        circle.setRadius(width/8.0);
        circle.setCenterX(width/2.0);
        circle.setCenterY(height/2.0);
        circle.setStrokeWidth(3.0);
        circle.setStroke(Paint.valueOf("#ffffff"));
        circle.setFill(Paint.valueOf("#00ff00"));
        getChildren().add(circle);

        Line line = new Line();
        line.setStartX(0);
        line.setStrokeWidth(3.0);
        line.setEndX(width);
        line.setStartY(height/2.0);
        line.setEndY(height/2.0);
        line.setStroke(Paint.valueOf("#ffffff"));
        getChildren().add(line);
    }
}
