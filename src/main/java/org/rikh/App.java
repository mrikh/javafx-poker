package org.rikh;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.rikh.utilities.Constants;
import org.rikh.views.PokerPane;
import org.rikh.controller.PokerController;

import java.util.ArrayList;
import java.util.Optional;

/**
 * JavaFX Poker Game
 */
public class App extends Application {

    /**
     * Width of the screen. Initial values set to 0 for convenience.
     */
    private double width = 0.0;
    /**
     * Height of the screen.
     */
    private double height = 0.0;
    /**
     * Instance variable to keep track of the pane which contains all the components.
     */
    private PokerPane pane;
    /**
     * Controller class that will contain the logic and flow. Tried to keep it separate from UI components
     * as highly likely that they will change.
     */
    private PokerController controller;

    /**
     * Start method supplied by JavaFX
     * @param stage The place everything will be rendered.
     */
    @Override
    public void start(Stage stage) {

        //Get screen bounds
        Rectangle2D screenDimensions = Screen.getPrimary().getBounds();
        //initialize controller class
        controller = new PokerController();

        //Store dimensions in instance variable
        width = screenDimensions.getWidth();
        height = screenDimensions.getHeight();

        //Our scene which is subclassed from the Pane class.
        pane = new PokerPane(width, height, controller.playerCards(), null);

        //Initial button setup to decide who will go first.
        int betterHand = controller.getBetterQualifyingHand();
        if(betterHand == 1){
            //user starts and decides who goes first
            setupGoFirstButtons();
        }else{
            //computer won
            dealerTokenBet();
            controller.changeOpponentCards();
            startDiscard(false);
        }

        //Our scene which will hold the poker pane.
        Scene scene = new Scene(pane, screenDimensions.getWidth(), screenDimensions.getHeight());

        //Listener to detect width change
        scene.widthProperty().addListener((observableValue, number, t1) -> {
            width = stage.getWidth();
            //Update UI according to new width
            commonLayoutChangeUpdate();
        });

        //Listener to detect height change
        scene.heightProperty().addListener((observableValue, number, t1) -> {
            height = stage.getHeight();
            //Update UI according to height
            commonLayoutChangeUpdate();
        });

        //Setup the stage
        stage.setTitle(Constants.title);
        stage.setScene(scene);
        stage.setMinHeight(screenDimensions.getHeight()/2.0);
        stage.setMinWidth(screenDimensions.getWidth()/2.0);
        stage.show();
    }

    /**
     * Default method supplied by JavaFX
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    /*  ================= PRIVATE METHODS BELOW HERE ================= */

    /**
     * Reset method to reset the game at the end of every turn. OR end the game when someone runs out of tokens
     */
    private void reset(){

        if (controller.getPlayerToken() == 0 || controller.getOpponentToken() == 0){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("GAME OVER");
            alert.setOnCloseRequest(dialogEvent -> Platform.exit());
            alert.show();

        }else{
            controller.reset();
            commonLayoutChangeUpdate();
            pane.updatePlayerTokens(controller.getPlayerToken());
            pane.updateOpponentTokens(controller.getOpponentToken());
            pane.updatePotTokens(controller.getCoinsInPot());
        }
    }

    /**
     * This method contains all the UI changes that need to happen in the components.
     * Essentially i remove everything and re-add it again as unfortunately I was unable to move components after they were added once.
     * Future Work - Just move the elements instead of recreating them as it is expensive.
     */
    private void commonLayoutChangeUpdate(){

        pane.updateLayout(width, height, controller.playerCards(), controller.showCard ? controller.opponentCards(): null, controller.getPlayerToken(), controller.getOpponentToken(), controller.getCoinsInPot());

        /* Clear card selection as maintaining state required a lot more work. This situation of clearing only arises when someone
            resizes the window while card is highlighted.
         */
        controller.clearCards();

        //remove all buttons and re-add them depending on where you are in the flow.
        clearButtons();

        if (controller.showCard) {
            String message = controller.determineWinner(controller.shouldOpponentFold());
            pane.showFinalMessage(message, actionEvent -> reset());
        }else if (controller.completedDiscard){
            setupBetting();
        }else if (controller.startDiscard){
            startDiscard(true);
        }else{
            setupGoFirstButtons();
        }
    }

    /**
     * Setup buttons for the initial state
     */
    private void setupGoFirstButtons(){

        Button playerButton = setupButton("Go First", actionEvent -> {
            dealerTokenBet();
            clearButtons();
            startDiscard(true);
        });
        playerButton.setLayoutX(width/2.0 + pane.getCardPaneWidth() / 2.0 + 15.0);
        playerButton.setLayoutY(pane.getPlayerCardPaneY() + pane.getCardHeight() / 2.0);
        pane.getChildren().add(playerButton);

        Button opponentButton = setupButton("Go First", actionEvent -> {
            dealerTokenBet();
            clearButtons();
            controller.changeOpponentCards();
            startDiscard(false);
        });
        opponentButton.setLayoutX(width/2.0 + pane.getCardPaneWidth() / 2.0 + 15.0);
        opponentButton.setLayoutY(pane.getOpponentCardPaneY() + pane.getCardHeight() / 2.0);
        pane.getChildren().add(opponentButton);
    }

    /**
     * Convenience method to perform the first bet.
     */
    private void dealerTokenBet(){
        controller.updatePot(1,1);
        pane.updatePlayerTokens(controller.getPlayerToken());
        pane.updateOpponentTokens(controller.getOpponentToken());
        pane.updatePotTokens(controller.getCoinsInPot());
    }

    /**
     * Setup button based on passed parameters
     * @param text String to display on the button
     * @param action Action to peform on button click.
     * @return object of type Button
     */
    private Button setupButton(String text, EventHandler<ActionEvent> action){

        Button button = new Button();
        button.setOnAction(action);
        button.setText(text);
        return button;
    }

    /**
     * Method to be called after it is decided who goes first. Setup discard layout
     */
    private void startDiscard(boolean changeOpponentCards){

        controller.startDiscard = true;
        pane.showText("Please select cards to discard by tapping on them");

        Button doneButton = setupButton("Done", actionEvent -> {
            controller.changeCards(changeOpponentCards);
            pane.updateLayout(width, height, controller.playerCards(), null, controller.getPlayerToken(), controller.getOpponentToken(), controller.getCoinsInPot());
            //remove card click
            pane.cardClickAction = null;
            clearButtons();
            clearMessages();
            setupBetting();
        });

        //Card click event handler
        pane.cardClickAction = mouseEvent -> {

            if (mouseEvent.getSource() instanceof StackPane){
                StackPane stack = (StackPane) mouseEvent.getSource();
                //since the stack pane is assigned the id. We use this value to determine which card was clicked
                int pos = Integer.parseInt(stack.getId());

                // condition to check if we are allowed selection
                if (controller.canUpdateCardSelection(pos)){
                    boolean select = controller.updateCard(pos);

                    //updated the controller, now update the view.
                    pane.updateCardStatus(String.valueOf(pos), select);
                }else{
                    //Error alert for card count reached.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("You can only select up to 4 cards");
                    alert.show();
                }
            }
        };

        doneButton.setLayoutX(width/2.0 + pane.getCardPaneWidth() / 2.0 + 15.0);
        doneButton.setLayoutY(pane.getPlayerCardPaneY() + pane.getCardHeight() / 2.0);
        pane.getChildren().add(doneButton);

        //start listening for card click event. Want to control this as we don't want cards to be clickable throughout the flow.
        pane.allowSelection();
    }

    /**
     * Start the betting flow and update the UI
     */
    private void setupBetting(){
        //update the previous flow
        controller.completedDiscard = true;

        //start betting button
        Button start = setupButton("Start Betting", actionEvent -> {

            //input the betting amount, defaults to max amount.
            TextInputDialog dialog = new TextInputDialog("3");
            dialog.setTitle("Start Betting");
            dialog.setHeaderText("Add the tokens you want to bet.");
            dialog.setContentText("Add a number between 0-3: ");

            //show dialog and wait for input
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(input -> {
                try{
                    //convert input to integer and catch failure.
                    int value = Integer.parseInt(input);

                    //check if value between range
                    if (value >= 0 && value <= 3){
                        //remove 0 coins if computer chooses to hide and let user win.
                        int finalValue = 0;
                        //check if computer has a good enough hand.
                        boolean computerHide = controller.shouldOpponentFold();

                        //cant use 4 coins if user has 2. So we pick the smaller value of the two.
                        value = Math.min(value, controller.getPlayerToken());

                        if (!computerHide){
                            finalValue = value;
                        }
                        //update the pot
                        controller.updatePot(value, finalValue);
                        //update the tokens on UI
                        pane.updatePlayerTokens(controller.getPlayerToken());
                        pane.updateOpponentTokens(controller.getOpponentToken());
                        pane.updatePotTokens(controller.getCoinsInPot());

                        //logic boolean to see where you are in the flow
                        controller.showCard = true;
                        clearButtons();

                        //update cards of opponent if necessary
                        pane.updateLayout(width, height, controller.playerCards(), computerHide ? null : controller.opponentCards(), controller.getPlayerToken(), controller.getOpponentToken(), controller.getCoinsInPot());

                        //show message
                        String message = controller.determineWinner(computerHide);
                        pane.showFinalMessage(message, actionEvent1 -> reset());

                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Enter a value between 0 and 3");
                        alert.show();
                    }
                }catch(NumberFormatException e){

                    //Show error for number format exception. UI will force user to try again so no need to handle anything else
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Input a number");
                    alert.show();
                }
            });
        });

        start.setLayoutX(width/2.0 + pane.getCardPaneWidth() / 2.0 + 15.0);
        start.setLayoutY(pane.getPlayerCardPaneY() + pane.getCardHeight() / 2.0);
        pane.getChildren().add(start);
    }

    /**
     * Remove all buttons from the screen. Useful for resetting the layout.
     */
    private void clearButtons(){

        ArrayList<Node> buttons = new ArrayList<>();
        for (Node node : pane.getChildren()){
            if (node instanceof Button){
                if (node.getId() == null) {
                    buttons.add(node);
                }
            }
        }
        pane.getChildren().removeAll(buttons);
    }

    /**
     * Remove message displayed on screen
     */
    private void clearMessages(){

        ArrayList<Node> text = new ArrayList<>();
        for (Node node : pane.getChildren()){
            if (node instanceof Text){
                text.add(node);
            }
        }
        pane.getChildren().removeAll(text);
    }
}