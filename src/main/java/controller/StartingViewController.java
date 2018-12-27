package controller;


import client.connector.ServerConnectorImpl;
import client.request.RequestDirector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class StartingViewController {

    @FXML
    private Button joinButton;

    @FXML
    private Button connectButton;

    /**
     * Joining to the game after click on JOIN button
     */
    @FXML
    public void joinTheGame() {
        RequestDirector.getInstance().sendRequest("JOIN");
        joinButton.setDisable(true);
    }

    /**
     * Connecting with server after CONNCECT button clicked and disabling/enabling clicking of JOIN button
     */
    @FXML
    public void connectWithServer() {
        try {
            ServerConnectorImpl.getInstance().connectWithServer();
            RequestDirector.getInstance().runHandler();
            joinButton.setDisable(false);
            connectButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Connected successfully", ButtonType.OK);
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Connection failed. Try again later", ButtonType.OK);
            alert.showAndWait();
        }
    }

}
