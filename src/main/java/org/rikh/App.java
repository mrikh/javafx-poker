package org.rikh;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.rikh.utilities.Constants;
import org.rikh.views.PokerPane;
import org.rikh.controller.PokerController;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        //Setup scene
        Rectangle2D screenDimensions = Screen.getPrimary().getBounds();
        PokerController controller = new PokerController();

        PokerPane pane = new PokerPane(screenDimensions.getWidth(), screenDimensions.getHeight(), controller.playerCards());

        Scene scene = new Scene(pane, screenDimensions.getWidth(), screenDimensions.getHeight());
        scene.widthProperty().addListener((observableValue, number, t1) -> {
            pane.updateLayout(stage.getWidth(), stage.getHeight(), controller.playerCards());
        });
        scene.heightProperty().addListener((observableValue, number, t1) -> {
            pane.updateLayout(stage.getWidth(), stage.getHeight(), controller.playerCards());
        });

        stage.setTitle(Constants.title);
        stage.setScene(scene);
        stage.setMinHeight(480.0);
        stage.setMinWidth(320.0);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}