package org.rikh.views;

import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.rikh.utilities.Constants;

/**
 * Class that inherits from FlowPane and initialize the tokens.
 * Done to have default methods and one constructor initialization of horizontal tokens.
 */
public class TokenPane extends FlowPane{

    //default spacing
    private double spacing = 10.0;
    //since both coins are the same initially, used this.
    private int tokenCount = Constants.initialPlayerCoins;

    /**
     * Constructor to initialize the tokens UI
     * @param tokenCount Count of the tokens to setup with
     */
    public TokenPane(int tokenCount){

        setHgap(spacing);
        updateCoins(tokenCount);
    }

    /**
     * Method to update the coins inside the layout
     * @param tokenCount Coins to set inside the layout
     */
    public void updateCoins(int tokenCount){

        //update the instance variable to contain the tokens passed.
        this.tokenCount = tokenCount;
        //Count of tokens multiplied by the spacing and diameter (2*radius) will give us the total width
        setPrefWrapLength(tokenCount * (spacing +  2 * Constants.tokenRadius));
        for (int i = 0; i < tokenCount; i++){
            Circle circle = new Circle();
            circle.setRadius(Constants.tokenRadius);
            circle.setStroke(Paint.valueOf(Constants.blackHex));
            circle.setFill(Paint.valueOf(Constants.lightGreenGex));
            getChildren().add(circle);
        }
    }

    /**
     * Method to return the height of each token
     * @return Double value containing the diameter of the token
     */
    public double getTokenHeight(){
        return 2.0 * Constants.tokenRadius;
    }

    /**
     * Method to return the total width occupied by the pane
     * @return Double value containing the total width.
     */
    public double getTotalWidth(){
        return tokenCount * (spacing +  2 * Constants.tokenRadius);
    }
}
