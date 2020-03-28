package org.rikh.views;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import org.rikh.model.Hand;
import org.rikh.utilities.Constants;
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
    private double defaultCoinRadius = 10.0;

    private int defaultCoinCount = 10;

    public PokerPane(double width, double height, String[] playerCards){
        this.width = width;
        this.height = height;
        setStyle("-fx-background-color: green");
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

        setupPotArea();

        //opponent cards
        double opponentHeight = height/10.0;
        setupCards(opponentHeight, Constants.opponent, "c", null, false);

        //player cards
        double playerHeight = height/10.0 * 8.5;
        setupCards(playerHeight, Constants.player, "p", playerCards, true);
    }

    private void setupCards(double cardCenterY, String title, String id, String[] cards, boolean tokenAbove){

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
                card = setupCard(id + "c" + i, cardWidth, cardHeight, cards[i]);
            }catch (NullPointerException e){
                card = setupCard(id + "c" + i, cardWidth, cardHeight, "?");
            }
            cardPane.getChildren().add(card);
        }

        cardPane.setLayoutX(cardsStartX);
        cardPane.setLayoutY(cardStartY);

        getChildren().add(cardPane);
        setupTokens(tokenAbove ? cardStartY : cardStartY + cardHeight, id, tokenAbove);
    }

    private void setupPotArea(){

        Circle circle = new Circle();
        circle.setRadius(width/6.0);
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

    private void setupTokens(double height, String id, boolean tokenAbove){

        FlowPane tokenPane = new FlowPane();
        tokenPane.setPrefWrapLength(defaultCoinCount * (spacing +  2 * defaultCoinRadius));
        tokenPane.setHgap(spacing);

        for (int i = 0; i < defaultCoinCount; i++){
            Circle circle = new Circle();
            circle.setRadius(defaultCoinRadius);
            circle.setId(id + "t" + i);
            circle.setStroke(Paint.valueOf("#000000"));
            circle.setFill(Paint.valueOf("#fdd023"));
            tokenPane.getChildren().add(circle);
        }

        double tokenX = width/2.0 - (defaultCoinCount/2.0 * (spacing + 2 * defaultCoinRadius));
        double tokenY = tokenAbove ? (height - 10.0 - (2 * defaultCoinRadius)) : height + 10.0;

        tokenPane.setLayoutX(tokenX);
        tokenPane.setLayoutY(tokenY);

        getChildren().add(tokenPane);
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
