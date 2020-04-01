package org.rikh.views;

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

public class PokerPane extends Pane {

    //use default values if nothing passed
    public double width = 1000.0;
    public double height = 1000.0;

    public EventHandler<MouseEvent> cardClicked;

    private TokenPane playerTokens;
    private TokenPane opponentTokens;
    private TokenPane pot;

    private CardsPane opponentCardPane;
    private CardsPane playerCardPane;

    private int playerTokensCount = 10;
    private int opponentTokensCount = 10;
    private int potTokenCount = 0;

    public PokerPane(double width, double height, String[] playerCards, String[] opponentCards){
        this.width = width;
        this.height = height;
        setStyle("-fx-background-color: green");
        initialSetup(playerCards, opponentCards);
    }

    public void updateLayout(double width, double height, String[] playerCards, String[] opponentCards){
        getChildren().clear();
        this.width = width;
        this.height = height;
        initialSetup(playerCards, opponentCards);
    }

    public void updatePlayerTokens(int tokens){
        playerTokens.getChildren().clear();
        this.playerTokensCount = tokens;
        playerTokens.updateCoins(this.playerTokensCount);
    }

    public void updateOpponentTokens(int tokenQuantity){
        opponentTokens.getChildren().clear();
        this.opponentTokensCount = tokenQuantity;
        opponentTokens.updateCoins(this.opponentTokensCount);
    }

    public void updatePotTokens(int tokens){
        pot.getChildren().clear();
        this.potTokenCount = tokens;
        pot.updateCoins(this.potTokenCount);
    }

    public double getCardPaneWidth(){
        return playerCardPane.getTotalWidth();
    }

    public double getPlayerCardPaneY(){
        return playerCardPane.getLayoutY();
    }

    public double getOpponentCardPaneY(){
        return opponentCardPane.getLayoutY();
    }

    public double getCardHeight(){
        return playerCardPane.getCardHeight();
    }

    //Private methods below here
    private void initialSetup(String[] playerCards, String[] opponentCards){

        double centerX = width/2.0;

        setupPotArea();

        //opponent cards
        opponentCardPane = new CardsPane(width, "c", opponentCards);
        double opponentStartX = centerX - opponentCardPane.getTotalWidth()/2.0;
        opponentCardPane.setLayoutX(opponentStartX);
        opponentCardPane.setLayoutY(30.0);
        getChildren().add(opponentCardPane);

        playerCardPane = new CardsPane(width, "p", playerCards);
        double playerStartX = centerX - playerCardPane.getTotalWidth()/2.0;
        playerCardPane.setLayoutX(playerStartX);
        playerCardPane.setLayoutY(height - 30.0 - playerCardPane.getCardHeight());
        getChildren().add(playerCardPane);

        opponentTokens = new TokenPane(opponentTokensCount);
        opponentTokens.setLayoutX(width/2.0 - opponentTokens.getTotalWidth()/2.0);
        opponentTokens.setLayoutY(opponentCardPane.getLayoutY() + 10.0 + opponentCardPane.getCardHeight());
        getChildren().add(opponentTokens);

        playerTokens = new TokenPane(playerTokensCount);
        playerTokens.setLayoutX(width/2.0 - playerTokens.getTotalWidth()/2.0);
        playerTokens.setLayoutY(playerCardPane.getLayoutY() - 10.0 - playerTokens.getTokenHeight());
        getChildren().add(playerTokens);

        //initially empty
        pot = new TokenPane(potTokenCount);
        pot.setLayoutY(height/2.0);
        pot.setLayoutX(width/2.0);
        getChildren().add(pot);
    }

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

    public void allowSelection(){
        playerCardPane.startCardClickListen(cardClicked);
    }

    public void selectPlayerCard(String id){
        playerCardPane.selectCard(id);
    }

    public void deSelectPlayerCard(String id){
        playerCardPane.deSelectCard(id);
    }

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
