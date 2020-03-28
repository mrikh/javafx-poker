package org.rikh.views;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class PokerPane extends Pane {

    //use default values if nothing passed
    public double width = 1000.0;
    public double height = 1000.0;

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

        double centerX = width/2.0;

        setupPotArea();

        //opponent cards
        CardsPane opponentCardPane = new CardsPane(width, "c", null);
        double opponentStartX = centerX - opponentCardPane.getTotalWidth()/2.0;
        opponentCardPane.setLayoutX(opponentStartX);
        opponentCardPane.setLayoutY(30.0);

        getChildren().add(opponentCardPane);

        CardsPane playerCardPane = new CardsPane(width, "p", playerCards);
        double playerStartX = centerX - playerCardPane.getTotalWidth()/2.0;
        playerCardPane.setLayoutX(playerStartX);
        playerCardPane.setLayoutY(height - 30.0 - playerCardPane.getCardHeight());

        getChildren().add(playerCardPane);

        TokenPane opponentTokenPane = new TokenPane("c");
        opponentTokenPane.setLayoutX(width/2.0 - opponentTokenPane.getTotalWidth()/2.0);
        opponentTokenPane.setLayoutY(opponentCardPane.getLayoutY() + 10.0 + opponentCardPane.getCardHeight());
        getChildren().add(opponentTokenPane);

        TokenPane playerTokenPane = new TokenPane("p");
        playerTokenPane.setLayoutX(width/2.0 - playerTokenPane.getTotalWidth()/2.0);
        playerTokenPane.setLayoutY(playerCardPane.getLayoutY() - 10.0 - playerTokenPane.getTokenHeight());
        getChildren().add(playerTokenPane);
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
