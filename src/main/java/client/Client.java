package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.ViewManager;

/**
 * Setting an starting view for Client
 */
public class Client extends Application {

    private static final String TITLE="Chinese Checkers";

    private static Stage myStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(ViewManager.getInstance().getStartingView()));
        Scene scene = new Scene((Parent) loader.load());
        myStage.setScene(scene);
        myStage.setTitle(TITLE);
        myStage.show();
    }

    public static void setScene(Scene scene) {
        myStage.setScene(scene);
    }
}
