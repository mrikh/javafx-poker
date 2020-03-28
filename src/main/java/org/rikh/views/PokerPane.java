package org.rikh.views;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import org.rikh.model.Hand;
import org.rikh.utilities.Constants;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PokerPane extends Pane {

    //use default values if nothing passed
    private double width = 1000.0;
    private double height = 1000.0;

    private double spacing = 10.0;
    private double defaultCardWidth = 50.0;
    private double defaultCardHeight = 80.0;

    public PokerPane(double width, double height, String[] playerCards){
        this.width = width;
        this.height = height;
        initialSetup(playerCards);
    }

    public void updateLayout(double width, double height, String[] playerCards){
        getChildren().clear();
        this.width = width;
        this.height = height;
        initialSetup(playerCards);
    }

    //Private methods below here
    private void initialSetup(String[] playerCards){

        //opponent cards
        setupCards(height/10.0, Constants.opponent, "c", null);
        //player cards
        setupCards(height/10.0 * 8.0, Constants.player, "p", playerCards);
    }

    private void setupCards(double cardCenterY, String title, String id, String[] playerCards){

        double centerX = width/2.0;
        double cardWidth = Math.max(defaultCardWidth, width/14.0);
        double cardHeight = Math.max(defaultCardHeight, width/11.0);

        double cardsStartX = centerX - cardWidth/2.0 - 2 * (spacing + cardWidth);
        double cardStartY = cardCenterY - cardHeight/2.0;

        FlowPane cardPane = new FlowPane();
        cardPane.setPrefWrapLength(5 * (spacing + cardWidth));
        cardPane.setHgap(spacing);

        for (int i = 0; i < Hand.capacity; i++){

            StackPane card;
            try{
                card = setupCard(id + i, cardWidth, cardHeight, playerCards[i]);
            }catch (NullPointerException e){
                card = setupCard(id + i, cardWidth, cardHeight, "?");
            }
            cardPane.getChildren().add(card);
        }

        cardPane.setLayoutX(cardsStartX);
        cardPane.setLayoutY(cardStartY);

        getChildren().addAll(cardPane);
    }

    private StackPane setupCard(String id, double width, double height, String title){

        StackPane stack = new StackPane();

        Rectangle rect = new Rectangle(width, height);
        rect.setId(id);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);

        Text text = new Text(title);
        text.setFont(Font.font(16.0));
        text.setWrappingWidth(width - 5.0);
        text.setTextAlignment(TextAlignment.CENTER);

        stack.getChildren().addAll(rect,text);
        return stack;
    }

    private Text setupText(String title){

        Text text = new Text(title);
        text.setFont(Font.font(16));
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }
}
