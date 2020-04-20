package org.rikh.views;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.rikh.utilities.Constants;

/**
 * Class that inherits from FlowPane. Used to create the UI for the cards.
 * This is done as I wanted the layout to be setup horizontally next to each other without having to
 * manage each cards individual spacing mathematically. This is done similar to the poker pane so that we don't have create the UI every time
 * and initializing the class will setup the views automatically
 */
public class CardsPane extends FlowPane{

    //default spacing between cards
    private double spacing = 10.0;

    //instance variable for the height and width of the card
    private double cardWidth;
    private double cardHeight;

    /**
     * Constructor to initialize the card layout
     * @param screenWidth Width of the screen to drawn on
     * @param cards Array containing title of the cards.
     */
    public CardsPane(double screenWidth, String[] cards){

        //done as we want the card to be of a minimum size and not get too small
        cardWidth = Math.max(Constants.defaultCardWidth, screenWidth/14.0);
        cardHeight = Math.max(Constants.defaultCardHeight, screenWidth/11.0);

        //total width of the pane is cards multiplied by the spacing of each card. Total width of the card is the sum of the card width
        //and spacing between the cards
        setPrefWrapLength(Constants.totalCardsInHand * (spacing + cardWidth));
        //Horizontal spacing
        setHgap(spacing);

        for (int i = 0; i < Constants.totalCardsInHand; i++){
            StackPane card;
            try{
                //if value of the string at index is null, we put a ?. This will happen mostly for the opponents cards
                card = setupCard(String.valueOf(i), cardWidth, cardHeight, cards[i]);
            }catch (NullPointerException e){
                card = setupCard(String.valueOf(i), cardWidth, cardHeight, Constants.kQuestion);
            }
            getChildren().add(card);
        }
    }

    /**
     * Returns the total width of the card pane. This was necessary as we need width before the item is added on the scene
     * and the default get width methods returns 0 until the scene has been added.
     * @return Double value containing the total width of the pane.
     */
    public double getTotalWidth(){
        return Constants.totalCardsInHand * (spacing + cardWidth);
    }

    /**
     * Method to assign the click card handler to each card.
     * @param handler Event to perform on clicking cards.
     */
    public void startCardClickListen(EventHandler<MouseEvent> handler){
        for (Node node : getChildren()){
            if (node instanceof StackPane){
                node.setOnMouseClicked(handler);
            }
        }
    }

    /**
     * Update card selection status. Red border if selected otherwise black
     * @param id Id of the card to select.
     * @param select Boolean value containing information regarding wheter to select the card or not.
     */
    public void updateCardSelection(String id, boolean select) {

        //iterate over the view heirarchy to find the rectangle to set border of.
        for (Node node : getChildren()) {
            if (node instanceof StackPane) {
                StackPane stack = (StackPane) node;
                if (stack.getId().equalsIgnoreCase(id)) {
                    for (Node child : stack.getChildren()){
                        if (child instanceof Rectangle){
                            ((Rectangle)child).setStroke(select? Paint.valueOf(Constants.redHex) : Paint.valueOf(Constants.whiteHex));
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to get the height of the card.
     * @return Double value with the height of the card.
     */
    public double getCardHeight(){
        return cardHeight;
    }

    /*  ================= PRIVATE METHODS BELOW HERE ================= */

    /**
     * Setup the individual card layout
     * @param id Id of card to identify it uniquely
     * @param width Width of each card
     * @param height Height of each card
     * @param title Title to show on each card. Ideally the name of the card
     * @return Returns the stack pane containing the card and the title. Used a stack pane to center content over each other
     */
    private StackPane setupCard(String id, double width, double height, String title){

        StackPane stack = new StackPane();
        stack.setId(id);

        Rectangle rect = new Rectangle(width, height);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);

        Text text = new Text(title);
        text.setFont(Font.font(16.0));
        text.setWrappingWidth(width - 5.0);
        text.setTextAlignment(TextAlignment.CENTER);

        stack.getChildren().addAll(rect,text);
        return stack;
    }
}
