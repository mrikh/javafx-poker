package org.rikh.views;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.rikh.model.Hand;

import java.util.ArrayList;

public class CardsPane extends FlowPane{

    private double spacing = 10.0;
    private double defaultCardWidth = 50.0;
    private double defaultCardHeight = 80.0;

    private double cardWidth = 0.0;
    private double cardHeight = 0.0;

    public CardsPane(double screenWidth, String id, String[] cards){

        cardWidth = Math.max(defaultCardWidth, screenWidth/14.0);
        cardHeight = Math.max(defaultCardHeight, screenWidth/11.0);

        setPrefWrapLength(5 * (spacing + cardWidth));
        setHgap(spacing);

        for (int i = 0; i < Hand.capacity; i++){
            StackPane card;
            try{
                card = setupCard(String.valueOf(i), cardWidth, cardHeight, cards[i]);
            }catch (NullPointerException e){
                card = setupCard(String.valueOf(i), cardWidth, cardHeight, "?");
            }
            getChildren().add(card);
        }
    }

    public double getTotalWidth(){
        return 5 * (spacing + cardWidth);
    }

    public void startCardClickListen(EventHandler<MouseEvent> handler){
        for (Node node : getChildren()){
            if (node instanceof StackPane){
                node.setOnMouseClicked(handler);
            }
        }
    }

    public void selectCard(String id) {

        for (Node node : getChildren()) {
            if (node instanceof StackPane) {
                StackPane stack = (StackPane) node;
                if (stack.getId().equalsIgnoreCase(id)) {
                    for (Node child : stack.getChildren()){
                        if (child instanceof Rectangle){
                            ((Rectangle)child).setStroke(Paint.valueOf("#ff0000"));
                        }
                    }
                }
            }
        }
    }

    public void deSelectCard(String id) {

        for (Node node : getChildren()) {
            if (node instanceof StackPane) {
                StackPane stack = (StackPane) node;
                if (stack.getId().equalsIgnoreCase(id)) {
                    for (Node child : stack.getChildren()){
                        if (child instanceof Rectangle){
                            ((Rectangle)child).setStroke(Paint.valueOf("#000000"));
                        }
                    }
                }
            }
        }
    }

    public double getCardHeight(){
        return cardHeight;
    }

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

    private Text setupText(String title){

        Text text = new Text(title);
        text.setFont(Font.font(16));
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }
}
