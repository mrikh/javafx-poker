package org.rikh.views;

import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class TokenPane extends FlowPane{

    private double spacing = 10.0;
    private double defaultCoinRadius = 10.0;
    private int defaultCoinCount = 10;

    public TokenPane(String id){

        setPrefWrapLength(defaultCoinCount * (spacing +  2 * defaultCoinRadius));
        setHgap(spacing);

        for (int i = 0; i < defaultCoinCount; i++){
            Circle circle = new Circle();
            circle.setRadius(defaultCoinRadius);
            circle.setId(id + "t" + i);
            circle.setStroke(Paint.valueOf("#000000"));
            circle.setFill(Paint.valueOf("#fdd023"));
            getChildren().add(circle);
        }
    }

    public double getTokenHeight(){
        return 2.0 * defaultCoinRadius;
    }

    public double getTotalWidth(){
        return defaultCoinCount * (spacing +  2 * defaultCoinRadius);
    }
}
