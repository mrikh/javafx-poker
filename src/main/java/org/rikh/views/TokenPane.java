package org.rikh.views;

import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class TokenPane extends FlowPane{

    private double spacing = 10.0;
    private double defaultCoinRadius = 10.0;
    private int tokenCount = 10;

    public TokenPane(int tokenCount){

        setHgap(spacing);
        updateCoins(tokenCount);
    }

    public void updateCoins(int tokenCount){

        this.tokenCount = tokenCount;
        setPrefWrapLength(tokenCount * (spacing +  2 * defaultCoinRadius));
        for (int i = 0; i < tokenCount; i++){
            Circle circle = new Circle();
            circle.setRadius(defaultCoinRadius);
            circle.setStroke(Paint.valueOf("#000000"));
            circle.setFill(Paint.valueOf("#fdd023"));
            getChildren().add(circle);
        }
    }

    public double getTokenHeight(){
        return 2.0 * defaultCoinRadius;
    }

    public double getTotalWidth(){
        return tokenCount * (spacing +  2 * defaultCoinRadius);
    }
}
